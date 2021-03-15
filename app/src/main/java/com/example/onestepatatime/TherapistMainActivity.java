package com.example.onestepatatime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class TherapistMainActivity extends AppCompatActivity
{
    Button calendarButton;
    Button assessmentButton;
    Button worksheetsButton;
    Button notesButton;
    Button clientListButton;

    public void initializeButtons()
    {
        this.calendarButton=findViewById(R.id.calendarButtonTherapist);
        this.assessmentButton=findViewById(R.id.assessmentButton);
        this.worksheetsButton=findViewById(R.id.worksheetsButtonTherapist);
        this.notesButton=findViewById(R.id.notesButtonTherapist);
        this.clientListButton=findViewById(R.id.clientListButton);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.therapist_main_activity);

        initializeButtons();
        configureCalendarButton();
        configureAssessmentButton();
        configureWorksheetsButton();
        configureNotesButton();
        configureClientListButton();
    }

    private void configureCalendarButton()
    {
        this.calendarButton.setOnClickListener((view)->{
            startActivity(new Intent(TherapistMainActivity.this, TherapistCalendarActivity.class));
        });
    }

    private void configureAssessmentButton()
    {
        this.assessmentButton.setOnClickListener((view)->{
            startActivity(new Intent(TherapistMainActivity.this, TherapistAssessmentActivity.class));
        });
    }

    private void configureWorksheetsButton()
    {
        this.worksheetsButton.setOnClickListener((view)->{
            startActivity(new Intent(TherapistMainActivity.this, TherapistWorksheetsActivity.class));
        });
    }

    private void configureNotesButton()
    {
        this.notesButton.setOnClickListener((view)->{
            startActivity(new Intent(TherapistMainActivity.this, TherapistNotesActivity.class));
        });
    }

    private void configureClientListButton()
    {
        this.clientListButton.setOnClickListener((view)->{
            startActivity(new Intent(TherapistMainActivity.this, TherapistClientListActivity.class));
        });
    }
}
