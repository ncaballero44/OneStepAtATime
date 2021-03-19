package com.example.onestepatatime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

public class TherapistNotesActivity extends AppCompatActivity
{
    private ListView listOfNotes;
    private Button addNewNoteButton;

    private void initializeElements()
    {
        this.listOfNotes=findViewById(R.id.therapistNotesList);
        this.addNewNoteButton=findViewById(R.id.addNewNoteButton);

    }

    private void configureButtons()
    {
        this.addNewNoteButton.setOnClickListener((view)->{
            startActivity(new Intent(this, TherapistNoteTakingActivity.class));
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.therapist_notes_activity);
    }
}
