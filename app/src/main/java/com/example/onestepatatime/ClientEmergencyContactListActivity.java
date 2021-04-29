package com.example.onestepatatime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        setEmergencyContactsList();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        setEmergencyContactsList();
    }

    private void initializeElements()
    {
        this.emergencyContactList=findViewById(R.id.emergencyContactList);
        this.addNewContactButton=findViewById(R.id.addNewContactButton);

        this.addNewContactButton.setOnClickListener((view)->{
            startActivity(new Intent(ClientEmergencyContactListActivity.this,ClientAddNewEmergencyContactEntryActivity.class));
        });
    }

    private void setEmergencyContactsList()
    {
        emergencyContactList.setAdapter(null);

        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();

        EmergencyContactListUtilities emergencyContactListUtilities=new EmergencyContactListUtilities();
        String[] listOfEmergencyContacts=emergencyContactListUtilities.getAllEmergencyContactNames(this,currentUser.getUid());

        ArrayAdapter<String> listOfEmergencyContactsAdapter=new ArrayAdapter<String>(this, R.layout.note_item,listOfEmergencyContacts);
        emergencyContactList.setAdapter(listOfEmergencyContactsAdapter);

        emergencyContactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
