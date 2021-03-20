package com.example.onestepatatime;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class TherapistNoteTakingActivity extends AppCompatActivity
{
    Button saveNotesButton;
    Button undoNotesButton;
    Button deleteNotesButton;

    EditText notesTitle;
    EditText notesContent;

    Notes newNote=null;

    private String passedNoteFileName;

    private void initializeElements()
    {
        this.saveNotesButton=findViewById(R.id.saveNotesButtonTherapist);
        this.undoNotesButton=findViewById(R.id.undoNotesButtonTherapist);
        this.deleteNotesButton=findViewById(R.id.deleteNotesButtonTherapist);

        this.notesTitle=findViewById(R.id.notesTitleTherapist);
        this.notesContent=findViewById(R.id.notesContentTherapist);
    }

    private void configureButtons()
    {
        configureSaveButton();

        this.undoNotesButton.setOnClickListener((view)->{
            Toast.makeText(this, "Undoing changes", Toast.LENGTH_SHORT).show();
            finish();
        });

        this.deleteNotesButton.setOnClickListener((view)->{
            FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
            FirebaseUser currentUser=firebaseAuth.getCurrentUser();

            String newNoteTitle=this.notesTitle.getText().toString();
            Database database=new Database();
            database.therapistsReference.child(currentUser.getUid()).child("notesAndAssessments").child(newNoteTitle).removeValue();
            finish();
        });
    }

    private void configureSaveButton()
    {
        this.saveNotesButton.setOnClickListener((view)->
        {
            FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
            FirebaseUser currentUser=firebaseAuth.getCurrentUser();

            String newNoteTitle=this.notesTitle.getText().toString();
            String newNoteContent=this.notesContent.getText().toString();
            if(newNoteTitle.length()==0)
            {
                Toast.makeText(this, "Please enter in a title", Toast.LENGTH_SHORT).show();
            }
            else if(newNoteContent.length()==0)
            {
                Toast.makeText(this, "Please enter in some content", Toast.LENGTH_SHORT).show();
            }
            else if(!newNoteTitle.equals(this.passedNoteFileName)&&this.passedNoteFileName!=null)
            {
                Database database=new Database();
                database.therapistsReference.child(currentUser.getUid()).child("notesAndAssessments").child(this.passedNoteFileName).removeValue();
                saveNote();
            }
            else
            {
                saveNote();
            }
        });

    }

    private void saveNote()
    {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        String newNoteTitle=this.notesTitle.getText().toString();
        String newNoteContent=this.notesContent.getText().toString();
        newNote=new Notes(newNoteTitle,newNoteContent,currentUser.getUid());

        Database database=new Database();

        if(database.sendNotesContentToTherapistDatabaseProfile(currentUser,newNote))
        {
            File directory=new File(this.getFilesDir(),currentUser.getUid());
            if(!directory.exists())
            {
                directory.mkdir();
            }

            try
            {
                File manifestFile=new File(directory,currentUser.getUid()+"_Manifest.txt");
                FileWriter writer=new FileWriter(manifestFile,true);
                writer.append(newNote.noteTitle+"\n");
                writer.flush();
                writer.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            Toast.makeText(this, "Note/Assessment successfully saved", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.therapist_note_taking_activity);
        initializeElements();
        configureButtons();

        this.passedNoteFileName=getIntent().getStringExtra("NOTE_FILE");

        if(this.passedNoteFileName!=null && !this.passedNoteFileName.isEmpty())
        {
            Database database=new Database();
            FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
            FirebaseUser currentUser=firebaseAuth.getCurrentUser();
            this.newNote=new Notes(this.passedNoteFileName,"",currentUser.getUid());

            DatabaseReference reference=database.therapistsReference.child(currentUser.getUid()).child("notesAndAssessments").child(this.passedNoteFileName);

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String content =snapshot.getValue().toString();
                    notesContent.setText(content);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error)
                {

                }
            });
            this.notesTitle.setText(newNote.noteTitle);
        }
    }
}
