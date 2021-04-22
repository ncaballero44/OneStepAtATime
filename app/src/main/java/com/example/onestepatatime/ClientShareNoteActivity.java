package com.example.onestepatatime;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ClientShareNoteActivity extends AppCompatActivity
{
    ListView clientShareNoteTherapistConnectionsList;

    private void initializeElements()
    {
        this.clientShareNoteTherapistConnectionsList=findViewById(R.id.clientShareNoteTherapistConnectionsList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_share_note_activity);

        initializeElements();
        setClientTherapistConnectionsList();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setClientTherapistConnectionsList();
    }

    private void setClientTherapistConnectionsList()
    {
        clientShareNoteTherapistConnectionsList.setAdapter(null);

        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();

        ClientTherapistListUtilities therapistListUtilities=new ClientTherapistListUtilities();
        String[] therapistUsernameList=therapistListUtilities.getAllConnectedTherapistsUsernames(this, currentUser.getUid());

        ArrayAdapter<String> therapistUsernameAdapter=new ArrayAdapter<String>(this, R.layout.note_item, therapistUsernameList);
        clientShareNoteTherapistConnectionsList.setAdapter(therapistUsernameAdapter);

        clientShareNoteTherapistConnectionsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String therapistUsername=clientShareNoteTherapistConnectionsList.getItemAtPosition(position).toString();

                AlertDialog.Builder builder=new AlertDialog.Builder(ClientShareNoteActivity.this);
                builder.setMessage("Are you sure you want to send your note to "+therapistUsername+"?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        String noteTitleAndContent=getIntent().getStringExtra("NOTE_TITLE_AND_CONTENTS");
                        String[] noteTitleAndContentSeparated=noteTitleAndContent.split("\r\n\r\n",2);

                        String noteTitle=noteTitleAndContentSeparated[0];
                        String noteContent=noteTitleAndContentSeparated[1];//+"\n\nShared with: "+ therapistUsername;

                        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                        FirebaseUser currentUser=firebaseAuth.getCurrentUser();

                        Notes sharedNote=new Notes(noteTitle,noteContent,currentUser.getUid());


                        saveSharedNote(sharedNote,therapistUsername);



                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
            }
        });
    }

    private void saveSharedNote(Notes sharedNote, String therapistUsername)
    {
        ClientTherapistListUtilities clientTherapistListUtilities=new ClientTherapistListUtilities();
        String therapistUserId=clientTherapistListUtilities.getTherapistIdFromUsername(ClientShareNoteActivity.this, therapistUsername);
        Database database=new Database();
        final String[] clientUsername={""};
        database.clientReference.child(sharedNote.ownerUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                clientUsername[0]=snapshot.child("username").getValue().toString().trim();
                if(database.sendSharedNoteToTherapistAndClient(therapistUserId,sharedNote.ownerUserID,sharedNote, clientUsername[0],therapistUsername))
                {
                    finish();
                }
                else
                {
                    Toast.makeText(ClientShareNoteActivity.this, "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}
