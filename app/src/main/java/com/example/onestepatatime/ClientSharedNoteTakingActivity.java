package com.example.onestepatatime;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ClientSharedNoteTakingActivity extends AppCompatActivity
{
    TextView notesTitleShared;
    TextView notesContentShared;

    Notes sharedNote;

    private String passedNoteFileName;

    private void initializeElements()
    {
        this.notesTitleShared=findViewById(R.id.notesTitleShared);
        this.notesContentShared=findViewById(R.id.notesContentShared);
    }

   @Override
    protected void onCreate(Bundle savedInstanceState)
   {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.client_shared_note_taking_activity);
       initializeElements();

       this.passedNoteFileName=getIntent().getStringExtra("NOTE_FILE");

       if(this.passedNoteFileName!=null && !this.passedNoteFileName.isEmpty()) {
           Database database = new Database();
           FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
           FirebaseUser currentUser = firebaseAuth.getCurrentUser();
           this.sharedNote = new Notes(this.passedNoteFileName, "", currentUser.getUid());

           DatabaseReference reference = database.clientReference.child(currentUser.getUid()).child("sharedNotes").child(this.passedNoteFileName);

           reference.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   if (snapshot.getValue() != null) {
                       String content = snapshot.getValue().toString();
                       notesContentShared.setText(content);
                   }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });
           this.notesTitleShared.setText(sharedNote.noteTitle);
       }
   }


}
