package com.example.onestepatatime;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class EmergencyContactInfoDisplayActivity extends AppCompatActivity
{
    TextView contactName;
    TextView contactPhoneNumber;
    TextView contactRelationship;

    private void initializeElements()
    {
        this.contactName=findViewById(R.id.contactName);
        this.contactPhoneNumber=findViewById(R.id.contactPhoneNumber);
        this.contactRelationship=findViewById(R.id.contactRelationship);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_contact_info_display);
        initializeElements();

        String passedContactName=getIntent().getStringExtra("CONTACT_NAME");

        Database database=new Database();
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        this.contactName.setText(passedContactName);
        database.clientReference.child(currentUser.getUid()).child("emergencyContacts").child(passedContactName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                contactPhoneNumber.setText(snapshot.child("phoneNumber").getValue(String.class));
                contactRelationship.setText(snapshot.child("relationship").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
