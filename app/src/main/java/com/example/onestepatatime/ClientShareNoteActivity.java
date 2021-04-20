package com.example.onestepatatime;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

//                Intent viewTherapistInfoIntent=new Intent(getApplicationContext(), TherapistInfoDisplayActivity.class);
//                viewTherapistInfoIntent.putExtra("THERAPIST_USERNAME",therapistUsername);
//                startActivity(viewTherapistInfoIntent);
                AlertDialog.Builder builder=new AlertDialog.Builder(ClientShareNoteActivity.this);

                builder.setMessage("Are you sure you want to send your note to "+therapistUsername+"?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        String noteTitleAndContent=getIntent().getStringExtra("NOTE_TITLE_AND_CONTENTS");
                        String[] noteTitleAndContentSeparated=noteTitleAndContent.split("\r\n\r\n",2);
//                        Toast.makeText(ClientShareNoteActivity.this, "Title: "+noteTitleAndContentSeparated[0]+" Content: "+noteTitleAndContentSeparated[1], Toast.LENGTH_SHORT).show();
                        //TODO take title and content and put it into the database under a shared child for both the therapist and client
                        finish();
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
}
