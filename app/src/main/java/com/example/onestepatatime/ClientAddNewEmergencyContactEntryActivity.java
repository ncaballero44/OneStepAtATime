package com.example.onestepatatime;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ClientAddNewEmergencyContactEntryActivity extends AppCompatActivity
{
    EditText clientAddEmergencyNameEntry;
    EditText clientAddEmergencyPhoneNumberEntry;
    EditText clientAddEmergencyRelationshipEntry;

    Button confirmEmergencyContactButton;

    String contactName="";
    String contactPhoneNumber="";
    String contactRelationship="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_add_emergency_contact);
        initializeElements();

        configureButton();
    }

    private void initializeElements()
    {
        this.clientAddEmergencyNameEntry=findViewById(R.id.clientAddEmergencyNameEntry);
        this.clientAddEmergencyPhoneNumberEntry=findViewById(R.id.clientAddEmergencyPhoneNumberEntry);
        this.clientAddEmergencyRelationshipEntry=findViewById(R.id.clientAddEmergencyRelationshipEntry);

        this.confirmEmergencyContactButton=findViewById(R.id.confirmEmergencyContactButton);
    }

    private void getContentsOfEntries()
    {
        this.contactName=this.clientAddEmergencyNameEntry.getText().toString().trim();
        this.contactPhoneNumber=this.clientAddEmergencyPhoneNumberEntry.getText().toString().trim();
        this.contactRelationship=this.clientAddEmergencyRelationshipEntry.getText().toString().trim();
    }

    private void configureButton()
    {
        this.confirmEmergencyContactButton.setOnClickListener((view)->{
            getContentsOfEntries();
            EmergencyContact newEmergencyContact=new EmergencyContact(this.contactName,this.contactPhoneNumber,this.contactRelationship);
            if(newEmergencyContact.areAllFieldsFilled())
            {
                Database database=new Database();
                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                FirebaseUser currentUser=firebaseAuth.getCurrentUser();
                if(database.saveNewEmergencyContactToDatabase(newEmergencyContact,currentUser.getUid()))
                {
                    Toast.makeText(ClientAddNewEmergencyContactEntryActivity.this, "Emergency contact saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            else
            {
                Toast.makeText(ClientAddNewEmergencyContactEntryActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
