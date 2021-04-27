package com.example.onestepatatime;

import android.content.DialogInterface;
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

public class TherapistShareNoteActivity extends AppCompatActivity
{
    ListView therapistShareNoteTherapistConnectionsList;

    private void initializeElements()
    {
        this.therapistShareNoteTherapistConnectionsList=findViewById(R.id.therapistShareNoteTherapistConnectionsList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.therapist_share_note_activity);

        initializeElements();
        setTherapistClientConnectionsList();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setTherapistClientConnectionsList();
    }

    private void setTherapistClientConnectionsList()
    {
        therapistShareNoteTherapistConnectionsList.setAdapter(null);

        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();

        TherapistClientListUtilities clientListUtilities=new TherapistClientListUtilities();
        String[] connectedClientUsernameList=clientListUtilities.getAllConnectedClientsUsernames(this, currentUser.getUid());

        ArrayAdapter<String> clientUsernameAdapter=new ArrayAdapter<String>(this,R.layout.note_item,connectedClientUsernameList);
        therapistShareNoteTherapistConnectionsList.setAdapter(clientUsernameAdapter);


        therapistShareNoteTherapistConnectionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clientUsername=therapistShareNoteTherapistConnectionsList.getItemAtPosition(position).toString();

                AlertDialog.Builder builder=new AlertDialog.Builder(TherapistShareNoteActivity.this);
                builder.setMessage("Are you sure you want to send your note to "+clientUsername+"?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String noteTitleAndContent=getIntent().getStringExtra("NOTE_TITLE_AND_CONTENTS");
                        String[] noteTitleAndContentSeparated=noteTitleAndContent.split("\r\n\r\n",2);

                        String noteTitle=noteTitleAndContentSeparated[0];
                        String noteContent=noteTitleAndContentSeparated[1];

                        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                        FirebaseUser currentUser=firebaseAuth.getCurrentUser();

                        Notes sharedNote=new Notes(noteTitle,noteContent,currentUser.getUid());

                        saveSharedNote(sharedNote,clientUsername);
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

    private void saveSharedNote(Notes sharedNote, String clientUsername)
    {
        TherapistClientListUtilities therapistClientListUtilities=new TherapistClientListUtilities();
        String clientUserId=therapistClientListUtilities.getClientIdFromUsername(TherapistShareNoteActivity.this,clientUsername);
        Database database=new Database();
        final String[] therapistUsername={""};
        database.therapistsReference.child(sharedNote.ownerUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                therapistUsername[0]=snapshot.child("username").getValue().toString().trim();
                if(database.sendSharedNoteToTherapistAndClient(sharedNote.ownerUserID,clientUserId,sharedNote,clientUsername,therapistUsername[0]))
                {
                    finish();
                }
                else
                {
                    Toast.makeText(TherapistShareNoteActivity.this, "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
