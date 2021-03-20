package com.example.onestepatatime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TherapistNotesActivity extends AppCompatActivity
{
    private ListView listOfNotes;
    private Button addNewNoteButton;

    private void initializeElements()
    {
        this.listOfNotes=findViewById(R.id.therapistNotesList);
        this.addNewNoteButton=findViewById(R.id.therapistAddNewNoteButton);

    }

    private void configureButtons()
    {
        this.addNewNoteButton.setOnClickListener((view)->{
            startActivity(new Intent(this, TherapistNoteTakingActivity.class));
        });
    }

    private void updateManifestFromDatabase()
    {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        File directory=new File(this.getFilesDir(),currentUser.getUid());
        File manifestFile=new File(directory,currentUser.getUid()+"_Manifest.txt");
        if(!directory.exists())
        {
            directory.mkdir();
        }
        String content="";

        Database database=new Database();
        DatabaseReference reference=database.therapistsReference.child(currentUser.getUid()).child("notesAndJournals");

        String finalContent = content;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot noteTitle:snapshot.getChildren())
                {
                    if(!finalContent.contains(noteTitle.getKey()))
                    {
                        try
                        {
                            FileWriter writer=new FileWriter(manifestFile,true);
                            writer.append(noteTitle.getKey()+"\n");
                            writer.flush();
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void getAllNotesAndJournals()
    {
        listOfNotes.setAdapter(null);

        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        updateManifestFromDatabase();
        TherapistNoteTakingUtilities noteTakingUtilities=new TherapistNoteTakingUtilities();
        String[] notesList=noteTakingUtilities.getAllNotesAndJournalEntries(this, firebaseUser.getUid());
        ArrayAdapter<String> notesAdapter=new ArrayAdapter<String>(this,R.layout.note_item,notesList);
        listOfNotes.setAdapter(notesAdapter);

        listOfNotes.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String filename=listOfNotes.getItemAtPosition(position).toString();

                Intent viewNoteIntent=new Intent(getApplicationContext(),TherapistNoteTakingActivity.class);
                viewNoteIntent.putExtra("NOTE_FILE",filename);
                startActivity(viewNoteIntent);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.therapist_notes_activity);
        initializeElements();
        configureButtons();
        getAllNotesAndJournals();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        getAllNotesAndJournals();
    }
}
