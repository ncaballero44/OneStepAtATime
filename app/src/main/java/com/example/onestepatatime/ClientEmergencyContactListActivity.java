package com.example.onestepatatime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

public class ClientEmergencyContactListActivity extends AppCompatActivity
{
    ListView emergencyContactList;
    Button addNewContactButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_emergency_contact_list_activity);
        initializeElements();
    }

    private void initializeElements()
    {
        this.emergencyContactList=findViewById(R.id.emergencyContactList);
        this.addNewContactButton=findViewById(R.id.addNewContactButton);

        this.addNewContactButton.setOnClickListener((view)->{
            startActivity(new Intent(ClientEmergencyContactListActivity.this,ClientAddNewEmergencyContactEntryActivity.class));
        });
    }
}
