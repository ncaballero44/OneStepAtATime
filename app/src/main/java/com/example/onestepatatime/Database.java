package com.example.onestepatatime;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database
{
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    public DatabaseReference root=firebaseDatabase.getReference();
    public DatabaseReference therapistsReference=root.child("therapist");
    public DatabaseReference clientReference=root.child("client");
}
