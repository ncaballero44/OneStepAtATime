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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_main_activity);
    }
}