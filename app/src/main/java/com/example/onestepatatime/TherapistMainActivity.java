package com.example.onestepatatime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class TherapistMainActivity extends AppCompatActivity
{
    Button calendarButton;
    Button assessmentButton;
    Button worksheetsButton;
    Button notesButton;
    Button clientListButton;

    TextView welcomeMessage;

    Database database;
    FirebaseAuth fAuth;
    FirebaseUser currentUser;

    String currentUsername;

    private void initializeElements()
    {
        this.calendarButton=findViewById(R.id.calendarButtonTherapist);
        this.assessmentButton=findViewById(R.id.assessmentButton);
        this.worksheetsButton=findViewById(R.id.worksheetsButtonTherapist);
        this.notesButton=findViewById(R.id.notesButtonTherapist);
        this.clientListButton=findViewById(R.id.clientListButton);

        this.welcomeMessage=findViewById(R.id.welcomeMessageTherapist);
    }

    private void initializeDatabase()
    {
        this.database=new Database();
        this.fAuth=FirebaseAuth.getInstance();
        this.currentUser=fAuth.getCurrentUser();
    }

    private void configureButtons()
    {
        this.calendarButton.setOnClickListener((view)->{
            startActivity(new Intent(TherapistMainActivity.this, TherapistCalendarActivity.class));
        });

        this.assessmentButton.setOnClickListener((view)->{
            startActivity(new Intent(TherapistMainActivity.this, TherapistAssessmentActivity.class));
        });

        this.worksheetsButton.setOnClickListener((view)->{
            startActivity(new Intent(TherapistMainActivity.this, TherapistWorksheetsActivity.class));
        });

        this.notesButton.setOnClickListener((view)->{
            startActivity(new Intent(TherapistMainActivity.this, TherapistNotesActivity.class));
        });

        this.clientListButton.setOnClickListener((view)->{
            startActivity(new Intent(TherapistMainActivity.this, TherapistClientListActivity.class));
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.therapist_main_activity);

        initializeElements();
        initializeDatabase();
        configureButtons();
        appendUsernameToTextView();
    }

    private void appendUsernameToTextView()
    {
        if (this.currentUser!=null)
        {
            String currentUserID=this.currentUser.getUid();
            final String[] username = {""};
            database.root.child("therapist").child(currentUserID).addValueEventListener(new ValueEventListener() {
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
            Toast.makeText(TherapistMainActivity.this, "Current user is null", Toast.LENGTH_SHORT).show();
        }
    }

}
