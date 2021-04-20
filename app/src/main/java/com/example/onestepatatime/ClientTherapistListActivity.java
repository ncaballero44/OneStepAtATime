package com.example.onestepatatime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

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
        setClientTherapistConnectionsList();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setClientTherapistConnectionsList();
    }

    private void setClientTherapistConnectionsList()
    {
        clientTherapistConnectionsList.setAdapter(null);

        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();

        ClientTherapistListUtilities therapistListUtilities=new ClientTherapistListUtilities();
        String[] therapistUsernameList=therapistListUtilities.getAllConnectedTherapistsUsernames(this, currentUser.getUid());

        ArrayAdapter<String> therapistUsernameAdapter=new ArrayAdapter<String>(this, R.layout.note_item, therapistUsernameList);
        clientTherapistConnectionsList.setAdapter(therapistUsernameAdapter);

        clientTherapistConnectionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String therapistUsername=clientTherapistConnectionsList.getItemAtPosition(position).toString();

                Intent viewTherapistInfoIntent=new Intent(getApplicationContext(), TherapistInfoDisplayActivity.class);
                viewTherapistInfoIntent.putExtra("THERAPIST_USERNAME",therapistUsername);
                startActivity(viewTherapistInfoIntent);
            }
        });
    }
}
