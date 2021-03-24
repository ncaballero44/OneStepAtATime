package com.example.onestepatatime;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ClientInfoDisplayActivity extends AppCompatActivity
{
    TextView clientUsername;
    TextView clientFirstName;
    TextView clientLastName;
    TextView clientEmail;

    private void initializeElements()
    {
        this.clientUsername=findViewById(R.id.clientUsername);
        this.clientFirstName=findViewById(R.id.clientFirstName);
        this.clientLastName=findViewById(R.id.clientLastName);
        this.clientEmail=findViewById(R.id.clientEmail);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_info_display_activity);
        initializeElements();

        TherapistClientListUtilities therapistClientListUtilities=new TherapistClientListUtilities();
        String passedClientUsername=getIntent().getStringExtra("CLIENT_USERNAME");
        String clientUserId=therapistClientListUtilities.getClientIdFromUsername(this, passedClientUsername);

        Database database=new Database();
        DatabaseReference clientReference=database.clientReference.child(clientUserId);

        clientReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                clientUsername.setText(snapshot.child("username").getValue(String.class));
                clientFirstName.setText(snapshot.child("firstName").getValue(String.class));
                clientLastName.setText(snapshot.child("lastName").getValue(String.class));
                clientEmail.setText(snapshot.child("email").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
