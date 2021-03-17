package com.example.onestepatatime;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NotesActivity extends AppCompatActivity
{

    Button saveNotesButton;
    Button undoNotesButton;
    Button deleteNotesButton;

    EditText notesTitle;
    EditText notesContent;

    private void initializeElements()
    {
        this.saveNotesButton=findViewById(R.id.saveNotesButton);
        this.undoNotesButton=findViewById(R.id.undoNotesButton);
        this.deleteNotesButton=findViewById(R.id.deleteNotesButton);

        this.notesTitle=findViewById(R.id.notesTitle);
        this.notesContent=findViewById(R.id.notesContent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_taking_activity);
        initializeElements();
    }
}
