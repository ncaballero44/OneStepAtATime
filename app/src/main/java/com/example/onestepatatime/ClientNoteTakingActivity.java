package com.example.onestepatatime;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class ClientNoteTakingActivity extends AppCompatActivity
{

    Button saveNotesButton;
    Button undoNotesButton;
    Button deleteNotesButton;

    EditText notesTitle;
    EditText notesContent;

    Notes newNote;

    private void initializeElements()
    {
        this.saveNotesButton=findViewById(R.id.saveNotesButton);
        this.undoNotesButton=findViewById(R.id.undoNotesButton);
        this.deleteNotesButton=findViewById(R.id.deleteNotesButton);

        this.notesTitle=findViewById(R.id.notesTitle);
        this.notesContent=findViewById(R.id.notesContent);
    }

    private void configureButtons()
    {
        configureSaveButton();
    }

    private void configureSaveButton()
    {
        this.saveNotesButton.setOnClickListener((view)->{
            String notesTitle=this.notesTitle.getText().toString();
            String notesContent=this.notesContent.getText().toString();
            this.newNote=new Notes(notesTitle,notesContent);

            FirebaseStorage storage=FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();

            String fileName=newNote.noteTitle+".txt";

            FileOutputStream fileOutputStream;
            try
            {
                fileOutputStream=this.openFileOutput(fileName,this.MODE_PRIVATE);
                fileOutputStream.close();
            }catch (IOException e)
            {
                e.printStackTrace();
            }

            File localFile=this.getFileStreamPath(fileName);
            try
            {
                FileWriter fileWriter=new FileWriter(localFile,false);
                fileWriter.write(newNote.noteContents);
                fileWriter.flush();
                fileWriter.close();
            }catch (Exception e)
            {

            }
            if(localFile.exists()) {
                Uri fileToUpload = Uri.fromFile(localFile);
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = fAuth.getCurrentUser();
                if (currentUser != null) {
                    StorageReference notesAndJournalReference = storageRef.child("client").child(currentUser.getUid()).child("notesAndJournalEntries/" + fileToUpload.getLastPathSegment());
                    UploadTask uploadTask = notesAndJournalReference.putFile(fileToUpload);

                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(ClientNoteTakingActivity.this, "File successfully saved.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });

                }

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_note_taking_activity);
        initializeElements();
        configureButtons();
    }
}