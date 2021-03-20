package com.example.onestepatatime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClientMainActivity extends AppCompatActivity
{
    Button calendarButton;
//    Button journalButton;
    Button worksheetsButton;
    Button notesJournalButton;
    Button therapistListButton;
    Button emergencyContactListButton;

    TextView welcomeMessage;

    Database database;
    FirebaseAuth fAuth;
    FirebaseUser currentUser;

    String currentUsername;

    private void initializeElements()
    {
        this.calendarButton=findViewById(R.id.calendarButtonClient);
//        this.journalButton=findViewById(R.id.journalButtonClient);
        this.worksheetsButton=findViewById(R.id.worksheetsButtonClient);
        this.notesJournalButton=findViewById(R.id.notesJournalButtonClient);
        this.therapistListButton=findViewById(R.id.therapistListButton);
        this.emergencyContactListButton=findViewById(R.id.emergencyContactListButtonClient);

        this.welcomeMessage=findViewById(R.id.welcomeMessageClient);

        this.database=new Database();
        this.fAuth=FirebaseAuth.getInstance();
        this.currentUser=fAuth.getCurrentUser();
    }

    private void configureButtons()
    {
        this.calendarButton.setOnClickListener((view)->{
            startActivity(new Intent(ClientMainActivity.this, ClientCalendarActivity.class));
        });

        this.worksheetsButton.setOnClickListener((view)->{
            startActivity(new Intent(ClientMainActivity.this, ClientWorksheetsActivity.class));
        });

        this.notesJournalButton.setOnClickListener((view)->{
            startActivity(new Intent(ClientMainActivity.this, ClientNotesActivity.class));
        });

        this.therapistListButton.setOnClickListener((view)->{
            startActivity(new Intent(ClientMainActivity.this, ClientTherapistListActivity.class));
        });

        this.emergencyContactListButton.setOnClickListener((view)->{
            startActivity(new Intent(ClientMainActivity.this, ClientEmergencyContactListActivity.class));
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_main_activity);
        initializeElements();
        configureButtons();
        appendUsernameToTextView();
    }

    private void appendUsernameToTextView()
    {
        if (this.currentUser!=null)
        {
            String currentUserID=this.currentUser.getUid();
            final String[] username = {""};
            database.root.child("client").child(currentUserID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    username[0] =snapshot.child("username").getValue().toString().trim();
                    welcomeMessage.append(username[0]);
                    currentUsername=username[0];
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else
        {
            Toast.makeText(ClientMainActivity.this, "Current user is null", Toast.LENGTH_SHORT).show();
        }


    }

}