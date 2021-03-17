package com.example.onestepatatime;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public String isTherapistOrClient(String userID)
    {
        final String[] userType = {""};
        therapistsReference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {
                    userType[0] ="therapist";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

        clientReference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {
                    userType[0]="client";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return userType[0];
    }
}
