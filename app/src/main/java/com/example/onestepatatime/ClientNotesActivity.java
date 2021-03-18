package com.example.onestepatatime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_notes_activity);

        initializeElements();
        configureButtons();
    }


    @Override
    protected void onResume() {
        super.onResume();
        listOfNotes.setAdapter(null);

        ArrayList<Notes> notesList=ClientNoteTakingUtilities.getAllSavedNotesFromDatabaseStorage(this);
        if(notesList==null||notesList.size()==0)
        {
            Toast.makeText(this, "No notes found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ClientNotesAdapter notesAdapter=new ClientNotesAdapter(this, R.layout.note_item,notesList);
            listOfNotes.setAdapter(notesAdapter);
        }
    }
}
