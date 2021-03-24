package com.example.onestepatatime;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TherapistAddNewConnectionActivity extends AppCompatActivity
{
    EditText clientEmailSearch;
    Button searchForClientButton;

    private void initializeElements()
    {
        this.clientEmailSearch=findViewById(R.id.clientEmailSearch);
        this.searchForClientButton=findViewById(R.id.therapistSearchForClientButton);
    }

    private void configureButtons()
    {
        this.searchForClientButton.setOnClickListener((view)->{
            String clientEmail=clientEmailSearch.getText().toString().trim();
            TherapistClientListUtilities utilities=new TherapistClientListUtilities();
            String clientUserId=utilities.getClientIdFromEmail(this, clientEmail);
//            Toast.makeText(this, clientUserId, Toast.LENGTH_SHORT).show();

            saveConnection(clientUserId);
        });
    }

    private void saveConnection(String clientId)
    {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        Database database=new Database();
        if(database.saveClientTherapistConnection(currentUser.getUid(),clientId))
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.therapist_add_new_client_connection_activity);
        initializeElements();
        configureButtons();
    }
}
