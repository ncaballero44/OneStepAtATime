package com.example.onestepatatime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

public class ClientTherapistListActivity extends AppCompatActivity
{
    ListView clientTherapistConnectionsList;
    Button addNewConnectionButton;

    private void initializeElements()
    {
        this.clientTherapistConnectionsList=findViewById(R.id.clientTherapistConnectionsList);
        this.addNewConnectionButton=findViewById(R.id.addNewConnectionButton);
    }

    private void configureButtons()
    {
        this.addNewConnectionButton.setOnClickListener((view)->{
            startActivity(new Intent(this, ClientAddNewConnectionActivity.class));
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_therapist_list_activity);
        initializeElements();
        configureButtons();
    }
}
