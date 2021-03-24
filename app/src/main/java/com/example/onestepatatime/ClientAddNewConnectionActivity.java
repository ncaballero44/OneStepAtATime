package com.example.onestepatatime;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class ClientAddNewConnectionActivity extends AppCompatActivity
{
    EditText therapistEmailSearch;
    Button searchForTherapistButton;

    private void initializeElements()
    {
        this.therapistEmailSearch=findViewById(R.id.therapistEmailSearch);
        this.searchForTherapistButton=findViewById(R.id.clientSearchForTherapistButton);
    }

    private void configureButtons()
    {
        this.searchForTherapistButton.setOnClickListener((view)->{
            String therapistEmail=therapistEmailSearch.getText().toString().trim();
            ClientTherapistListUtilities utilities=new ClientTherapistListUtilities();
            String therapistUserId=utilities.getTherapistIdFromEmail(this, therapistEmail);
//            Toast.makeText(this, therapistUserId, Toast.LENGTH_SHORT).show();

            saveConnection(therapistUserId);
        });
    }

    private void saveConnection(String therapistId)
    {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        Database database=new Database();
        if(database.saveClientTherapistConnection(therapistId,currentUser.getUid()))
        {
            Toast.makeText(this, "Connection successfully saved", Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
            Toast.makeText(this, "Connection failed to save. Please try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_add_new_therapist_connection);
        initializeElements();
        configureButtons();
    }
}
