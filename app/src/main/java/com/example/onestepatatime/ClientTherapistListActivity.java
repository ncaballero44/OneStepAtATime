package com.example.onestepatatime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
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



    private void getAllConnectedTherapists()
    {

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
