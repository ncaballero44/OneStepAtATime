package com.example.onestepatatime;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Database
{
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    public DatabaseReference root=firebaseDatabase.getReference();
    public DatabaseReference therapistsReference=firebaseDatabase.getReference("therapist");
    public DatabaseReference clientReference=firebaseDatabase.getReference("client");


    public boolean createNewUser(User newUser)
    {
        final boolean[] successful = {false};
        if (newUser.isTherapist)
        {
            therapistsReference.child(newUser.userID).setValue(newUser.userInformation).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    successful[0] =true;
                }
            });
        }
        else
        {
            clientReference.child(newUser.userID).setValue(newUser.userInformation).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    successful[0] =true;
                }
            });
        }

        return successful[0];
    }

    public boolean sendNotesContentToClientDatabaseProfile(FirebaseUser currentUser, Notes newNote)
    {
        final boolean[] successful = {true};
        clientReference.child(currentUser.getUid()).child("notesAndJournals").child(newNote.noteTitle).setValue(newNote.noteContents).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                successful[0]=false;
            }
        });
        return successful[0];
    }

    public boolean sendNotesContentToTherapistDatabaseProfile(FirebaseUser currentUser, Notes newNote)
    {
        final boolean[] successful = {true};
        therapistsReference.child(currentUser.getUid()).child("notesAndAssessments").child(newNote.noteTitle).setValue(newNote.noteContents).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                successful[0]=false;
            }
        });
        return successful[0];
    }

    public boolean saveClientTherapistConnection(String therapistId, String clientId)
    {
        final boolean[] successful = {true};
        DatabaseReference listOfConnectedTherapistsReference=clientReference.child(clientId).child("connectedTherapists").child(therapistId);
        listOfConnectedTherapistsReference.setValue("connected").addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                successful[0]=false;
            }
        });
        DatabaseReference listOfConnectedClientsReference=therapistsReference.child(therapistId).child("connectedClients").child(clientId);
        listOfConnectedClientsReference.setValue("connected").addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                successful[0]=false;
            }
        });
        return successful[0];
    }

}
