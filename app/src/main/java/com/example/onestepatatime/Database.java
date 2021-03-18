package com.example.onestepatatime;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

    public boolean sendClientFileURLToDatabase(String fileURL, String userID, String fileName)
    {
        final boolean[] successful = {false};
        clientReference.child(userID).child("textFileURLs").child(fileName).setValue(fileURL).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                successful[0] =true;
            }
        });
        return successful[0];
    }
}
