package com.example.onestepatatime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ClientMainActivity extends AppCompatActivity
{
    Button calendarButton;
    Button journalButton;
    Button worksheetsButton;
    Button notesButton;
    Button therapistListButton;
    Button emergencyContactListButton;

    private void initializeButtons()
    {
        this.calendarButton=(Button) findViewById(R.id.calendarButtonClient);
        this.journalButton=(Button) findViewById(R.id.journalButtonClient);
        this.worksheetsButton=(Button) findViewById(R.id.worksheetsButtonClient);
        this.notesButton=(Button) findViewById(R.id.notesButtonClient);
        this.therapistListButton=(Button) findViewById(R.id.therapistListButton);
        this.emergencyContactListButton=(Button) findViewById(R.id.emergencyContactListButtonClient);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_main_activity);

        initializeButtons();
        configureClientCalendarButton();
        configureClientJournalButton();
        configureClientWorksheetsButton();
        configureNotesButton();
        configureTherapistListButton();
        configureEmergencyContactListButton();
    }

    private void configureClientCalendarButton()
    {
        this.calendarButton.setOnClickListener((view)->{
            startActivity(new Intent(ClientMainActivity.this, ClientCalendarActivity.class));
        });
    }

    private void configureClientJournalButton()
    {
        this.journalButton.setOnClickListener((view)->{
            startActivity(new Intent(ClientMainActivity.this, ClientJournalActivity.class));
        });
    }

    private void configureClientWorksheetsButton()
    {
        this.worksheetsButton.setOnClickListener((view)->{
            startActivity(new Intent(ClientMainActivity.this, ClientWorksheetsActivity.class));
        });
    }

    private void configureNotesButton()
    {
        this.notesButton.setOnClickListener((view)->{
            startActivity(new Intent(ClientMainActivity.this, ClientNotesActivity.class));
        });
    }

    private void configureTherapistListButton()
    {
        this.therapistListButton.setOnClickListener((view)->{
            startActivity(new Intent(ClientMainActivity.this, ClientTherapistListActivity.class));
        });
    }

    private void configureEmergencyContactListButton()
    {
        this.emergencyContactListButton.setOnClickListener((view)->{
            startActivity(new Intent(ClientMainActivity.this, ClientEmergencyContactListActivity.class));
        });
    }
}