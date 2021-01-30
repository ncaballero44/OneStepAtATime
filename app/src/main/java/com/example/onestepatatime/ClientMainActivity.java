package com.example.onestepatatime;

import androidx.appcompat.app.AppCompatActivity;

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

    public void initializeButtons()
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
    }
}