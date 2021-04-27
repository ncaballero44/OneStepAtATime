package com.example.onestepatatime;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//Displays the shared notes of a client
public class ClientSharedNotesActivity extends AppCompatActivity
{
    private ListView listOfSharedNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_shared_notes_activity);
        this.listOfSharedNotes=findViewById(R.id.clientSharedNotesList);
        getAllSharedNotes();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        getAllSharedNotes();
    }

    private void updateManifestFromDatabase()
    {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        File directory=new File(this.getFilesDir(),currentUser.getUid());
        if(!directory.exists())
        {
            directory.mkdir();
        }
        File sharedManifestFile=new File(directory,currentUser.getUid()+"_Shared_Manifest.txt");
        Database database=new Database();
        DatabaseReference reference=database.clientReference.child(currentUser.getUid()).child("sharedNotes");
        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                String manifestFileContents="";
                for(DataSnapshot noteTitle:snapshot.getChildren())
                {
                    manifestFileContents+=noteTitle.getKey()+"\n";
                }
                try
                {
                    FileWriter writer=new FileWriter(sharedManifestFile,false);
                    writer.write(manifestFileContents);
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getAllSharedNotes()
    {
        listOfSharedNotes.setAdapter(null);

        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        updateManifestFromDatabase();

        SharedNoteListUtilities sharedNoteListUtilities=new SharedNoteListUtilities();
        String[] sharedNotesList=sharedNoteListUtilities.getAllSharedNoteEntries(this,firebaseUser.getUid());
        ArrayAdapter<String> sharedNotesAdapter=new ArrayAdapter<String>(this,R.layout.note_item,sharedNotesList);
        listOfSharedNotes.setAdapter(sharedNotesAdapter);

        listOfSharedNotes.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String noteTitle=listOfSharedNotes.getItemAtPosition(position).toString();

                Intent viewNoteIntent=new Intent(getApplicationContext(), ClientSharedNoteTakingActivity.class);
                viewNoteIntent.putExtra("NOTE_FILE",noteTitle);
                startActivity(viewNoteIntent);
            }
        });
    }
}
