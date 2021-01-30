package com.example.onestepatatime;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

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
    }
}
