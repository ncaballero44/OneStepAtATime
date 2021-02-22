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
        this.calendarButton=(Button) findViewById(R.id.calendarButtonTherapist);
        this.assessmentButton=(Button) findViewById(R.id.assessmentButton);
        this.worksheetsButton=(Button) findViewById(R.id.worksheetsButtonTherapist);
        this.notesButton=(Button) findViewById(R.id.notesButtonTherapist);
        this.clientListButton=(Button) findViewById(R.id.clientListButton);
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
