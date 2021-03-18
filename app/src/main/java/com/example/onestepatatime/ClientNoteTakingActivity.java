package com.example.onestepatatime;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

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

            if(notesTitle.length()==0)
            {
                Toast.makeText(ClientNoteTakingActivity.this, "Please enter in a title", Toast.LENGTH_SHORT).show();
            }
            else
            {
                this.newNote=new Notes(notesTitle,notesContent);

                FirebaseStorage storage=FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();

                ClientNoteTakingUtilities noteTakingUtilities=new ClientNoteTakingUtilities();
                noteTakingUtilities.createTextFile(this,newNote);

                File localFile=this.getFileStreamPath(newNote.noteTitle+".txt");
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
                    if (currentUser != null)
                    {
                        StorageReference notesAndJournalReference = storageRef.child("client").child(currentUser.getUid()).child("notesAndJournalEntries/" + fileToUpload.getLastPathSegment());
                        UploadTask uploadTask = notesAndJournalReference.putFile(fileToUpload);
                        Task<Uri> urlTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if(!task.isSuccessful())
                                {
                                    throw task.getException();
                                }
                                return notesAndJournalReference.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if(task.isSuccessful())
                                {
                                    Uri downloadUri=task.getResult();
                                    if(downloadUri==null)
                                    {
                                        Toast.makeText(ClientNoteTakingActivity.this, "downloadUri null", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        String urlDownload=downloadUri.toString();
                                        Database database=new Database();
                                        database.sendClientFileURLToDatabase(urlDownload,currentUser.getUid(),newNote.noteTitle);
                                        finish();
                                    }
                                }
                            }
                        });

                    }

                }
                else
                {
                    Toast.makeText(ClientNoteTakingActivity.this, "File was not found.", Toast.LENGTH_SHORT).show();
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