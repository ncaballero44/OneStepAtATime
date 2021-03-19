package com.example.onestepatatime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ClientNotesActivity extends AppCompatActivity
{
    private ListView listOfNotes;
    private Button addNewNoteButton;

    private void initializeElements()
    {
        this.listOfNotes=findViewById(R.id.clientNotesList);
        this.addNewNoteButton=findViewById(R.id.addNewNoteButton);

    }

    private void configureButtons()
    {
        this.addNewNoteButton.setOnClickListener((view)->{
            startActivity(new Intent(ClientNotesActivity.this, ClientNoteTakingActivity.class));
        });
    }

    private void getAllNotesAndJournals()
    {
        listOfNotes.setAdapter(null);


        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        ClientNoteTakingUtilities noteTakingUtilities=new ClientNoteTakingUtilities();
        String[] notesList=noteTakingUtilities.getAllNotesAndJournalEntries(this, firebaseUser.getUid());
        ArrayAdapter<String> notesAdapter=new ArrayAdapter<String>(this,R.layout.note_item,notesList);
        listOfNotes.setAdapter(notesAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_notes_activity);

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
