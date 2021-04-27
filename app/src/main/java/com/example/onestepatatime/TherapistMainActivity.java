package com.example.onestepatatime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class TherapistMainActivity extends AppCompatActivity
{

    Button sharedNotesButton;
    Button notesAssessmentButton;
    Button clientListButton;

    TextView welcomeMessage;

    Database database;
    FirebaseAuth fAuth;
    FirebaseUser currentUser;

    String currentUsername;

    private void initializeElements()
    {
        this.sharedNotesButton=findViewById(R.id.worksheetsButtonTherapist);
        this.notesAssessmentButton=findViewById(R.id.notesAssessmentButtonTherapist);
        this.clientListButton=findViewById(R.id.clientListButton);

        this.welcomeMessage=findViewById(R.id.welcomeMessageTherapist);
    }

    private void initializeDatabase()
    {
        this.database=new Database();
        this.fAuth=FirebaseAuth.getInstance();
        this.currentUser=fAuth.getCurrentUser();
    }

    private void configureButtons()
    {
        this.sharedNotesButton.setOnClickListener((view)->{
            startActivity(new Intent(TherapistMainActivity.this, TherapistWorksheetsActivity.class));
        });

        this.notesAssessmentButton.setOnClickListener((view)->{
            startActivity(new Intent(TherapistMainActivity.this, TherapistNotesActivity.class));
        });

        this.clientListButton.setOnClickListener((view)->{
            startActivity(new Intent(TherapistMainActivity.this, TherapistClientListActivity.class));
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.therapist_main_activity);

        updateClientList();

        initializeElements();
        initializeDatabase();

        configureButtons();

        appendUsernameToTextView();

        updateClientConnectionCollectionFile();
        convertUserIdsToUsernames();
    }

    private void appendUsernameToTextView()
    {
        if (this.currentUser!=null)
        {
            String currentUserID=this.currentUser.getUid();
            final String[] username = {""};
            database.root.child("therapist").child(currentUserID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    if(snapshot.child("username").getValue()!=null)
                    {
                        username[0] =snapshot.child("username").getValue().toString().trim();
                        welcomeMessage.setText("Welcome back\n"+username[0]);
                        currentUsername=username[0];
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else
        {
            Toast.makeText(TherapistMainActivity.this, "Current user is null", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateClientList()
    {
        Database database=new Database();

        final String[] listOfClients = {""};

        database.clientReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(DataSnapshot clientUserId:snapshot.getChildren())
                {
                    listOfClients[0]=listOfClients[0]+clientUserId.getKey()+"\t";
                    listOfClients[0]=listOfClients[0]+clientUserId.child("username").getValue(String.class)+"\t";
                    listOfClients[0]=listOfClients[0]+clientUserId.child("email").getValue(String.class)+"\n";

                }
                String[] listOfClientUserIdsAndEmails=listOfClients[0].split("\n");
                listOfClientUserIdsAndEmails=new HashSet<String>(Arrays.asList(listOfClientUserIdsAndEmails)).toArray(new String[0]);

                File directory=new File(getApplicationContext().getFilesDir(),"ListsOfClientsAndTherapists");
                File clientListFile=new File(directory,"AllClients.txt");
                if(!directory.exists())
                {
                    directory.mkdir();
                }
                try
                {
                    FileWriter writer=new FileWriter(clientListFile,false);
                    for(int i=0;i<listOfClientUserIdsAndEmails.length;i++)
                    {
                        writer.write(listOfClientUserIdsAndEmails[i]+"\n");
                    }
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateClientConnectionCollectionFile()
    {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        Database database=new Database();

        DatabaseReference listOfConnectedClientsReference=database.therapistsReference.child(currentUser.getUid()).child("connectedClients");
        listOfConnectedClientsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                String allConnectedClientIds="";
                for(DataSnapshot currentClientUserId:snapshot.getChildren())
                {
                    if(currentClientUserId.getValue(String.class).equals("connected"))
                    {
                        allConnectedClientIds=allConnectedClientIds+currentClientUserId.getKey()+"\n";
                    }
                }
                String[] listOfClientIds=allConnectedClientIds.split("\n");
                listOfClientIds=new HashSet<String>(Arrays.asList(listOfClientIds)).toArray(new String[0]);

                File directory=new File(getApplicationContext().getFilesDir(),currentUser.getUid());
                File clientListFile=new File(directory,currentUser.getUid()+"_ConnectedClientUserIds.txt");
                if(!directory.exists())
                {
                    directory.mkdir();
                }

                try
                {
                    FileWriter writer=new FileWriter(clientListFile,false);
                    for(int i=0;i<listOfClientIds.length;i++)
                    {
                        writer.write(listOfClientIds[i]+"\n");
                    }
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void convertUserIdsToUsernames()
    {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        File directory=new File(getApplicationContext().getFilesDir(),currentUser.getUid());

        File clientUserIdListFile=new File(directory,currentUser.getUid()+"_ConnectedClientUserIds.txt");
        String[] listOfClientUserIds=new String[(int)clientUserIdListFile.length()];

        if(clientUserIdListFile.exists())
        {
            String fileContents="";
            try
            {
                Scanner scanner=new Scanner(clientUserIdListFile);
                if(scanner.hasNext())
                {
                    fileContents=scanner.useDelimiter("\\Z").next();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            listOfClientUserIds=fileContents.split("\n");
            listOfClientUserIds=new HashSet<String>(Arrays.asList(listOfClientUserIds)).toArray(new String[0]);
        }

        File clientUsernameList=new File(directory,currentUser.getUid()+"_ConnectedClientsUsernames.txt");
        Database database=new Database();

        try
        {
            FileWriter writer=new FileWriter(clientUsernameList,false);

            for(int i=0;i<listOfClientUserIds.length;i++)
            {
                DatabaseReference currentClientUsernameReference=database.clientReference.child(listOfClientUserIds[i]).child("username");
                final String[] currentClientUsername = {""};
                currentClientUsernameReference.addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        currentClientUsername[0]=snapshot.getValue(String.class);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                writer.write(currentClientUsername[0]+"\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
