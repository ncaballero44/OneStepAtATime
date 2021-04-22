package com.example.onestepatatime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class ClientMainActivity extends AppCompatActivity
{
    Button calendarButton;
    Button sharedNotesButton;
    Button notesJournalButton;
    Button therapistListButton;
    Button emergencyContactListButton;

    TextView welcomeMessage;

    Database database;
    FirebaseAuth fAuth;
    FirebaseUser currentUser;

    String currentUsername;

    private void initializeElements()
    {
        this.sharedNotesButton=findViewById(R.id.sharedNotesButtonClient);
        this.notesJournalButton=findViewById(R.id.notesJournalButtonClient);
        this.therapistListButton=findViewById(R.id.therapistListButton);
        this.emergencyContactListButton=findViewById(R.id.emergencyContactListButtonClient);

        this.welcomeMessage=findViewById(R.id.welcomeMessageClient);

        this.database=new Database();
        this.fAuth=FirebaseAuth.getInstance();
        this.currentUser=fAuth.getCurrentUser();
    }

    private void configureButtons()
    {
        this.sharedNotesButton.setOnClickListener((view)->{
            startActivity(new Intent(ClientMainActivity.this, ClientSharedNotesActivity.class));
        });

        this.notesJournalButton.setOnClickListener((view)->{
            startActivity(new Intent(ClientMainActivity.this, ClientNotesActivity.class));
        });

        this.therapistListButton.setOnClickListener((view)->{
            startActivity(new Intent(ClientMainActivity.this, ClientTherapistListActivity.class));
        });

        this.emergencyContactListButton.setOnClickListener((view)->{
            startActivity(new Intent(ClientMainActivity.this, ClientEmergencyContactListActivity.class));
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_main_activity);

        updateTherapistList();

        initializeElements();
        configureButtons();
        appendUsernameToTextView();

        updateTherapistConnectionCollectionFile();
        convertUserIdsToUsernames();
        updateManifestFromDatabase();
    }

    private void appendUsernameToTextView()
    {
        if (this.currentUser!=null)
        {
            String currentUserID=this.currentUser.getUid();
            final String[] username = {""};
            database.root.child("client").child(currentUserID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    username[0] =snapshot.child("username").getValue().toString().trim();
                    welcomeMessage.setText("Welcome back\n"+username[0]);
                    currentUsername=username[0];
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else
        {
            Toast.makeText(ClientMainActivity.this, "Current user is null", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTherapistList()
    {
        Database database=new Database();

        final String[] listOfTherapists = {""};

        database.therapistsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(DataSnapshot therapistUserId:snapshot.getChildren())
                {
                    listOfTherapists[0]=listOfTherapists[0]+therapistUserId.getKey()+"\t";
                    listOfTherapists[0]=listOfTherapists[0]+therapistUserId.child("username").getValue(String.class)+"\t";
                    listOfTherapists[0]=listOfTherapists[0]+therapistUserId.child("email").getValue(String.class)+"\n";

                }
                String[] listOfTherapistUserIdsAndEmails=listOfTherapists[0].split("\n");
                listOfTherapistUserIdsAndEmails=new HashSet<String>(Arrays.asList(listOfTherapistUserIdsAndEmails)).toArray(new String[0]);

                File directory=new File(getApplicationContext().getFilesDir(),"ListsOfClientsAndTherapists");
                File therapistListFile=new File(directory,"AllTherapists.txt");
                if(!directory.exists())
                {
                    directory.mkdir();
                }
                try
                {
                    FileWriter writer=new FileWriter(therapistListFile,false);
                    for(int i=0;i<listOfTherapistUserIdsAndEmails.length;i++)
                    {
                        writer.write(listOfTherapistUserIdsAndEmails[i]+"\n");
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

    private void updateTherapistConnectionCollectionFile()
    {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        Database database=new Database();

        DatabaseReference listOfConnectedTherapistsReference=database.clientReference.child(currentUser.getUid()).child("connectedTherapists");
        listOfConnectedTherapistsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                String allConnectedTherapistIds="";
                for(DataSnapshot currentTherapistUserId:snapshot.getChildren())
                {
                    if(currentTherapistUserId.getValue(String.class).equals("connected"))
                    {
                        allConnectedTherapistIds=allConnectedTherapistIds+currentTherapistUserId.getKey()+"\n";
                    }
                }
                String[] listOfTherapistIds=allConnectedTherapistIds.split("\n");
                listOfTherapistIds=new HashSet<String>(Arrays.asList(listOfTherapistIds)).toArray(new String[0]);

                File directory=new File(getApplicationContext().getFilesDir(),currentUser.getUid());
                File therapistListFile=new File(directory,currentUser.getUid()+"_ConnectedTherapistUserIds.txt");
                if(!directory.exists())
                {
                    directory.mkdir();
                }

                try
                {
                    FileWriter writer=new FileWriter(therapistListFile,false);
                    for(int i=0;i<listOfTherapistIds.length;i++)
                    {
                        writer.write(listOfTherapistIds[i]+"\n");
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

        File therapistUserIdListFile=new File(directory,currentUser.getUid()+"_ConnectedTherapistUserIds.txt");
        String[] listOfTherapistUserIds=new String[(int)therapistUserIdListFile.length()];

        if(therapistUserIdListFile.exists())
        {
            String fileContents="";
            try
            {
                Scanner scanner=new Scanner(therapistUserIdListFile);
                if(scanner.hasNext())
                {
                    fileContents=scanner.useDelimiter("\\Z").next();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            listOfTherapistUserIds=fileContents.split("\n");
            listOfTherapistUserIds=new HashSet<String>(Arrays.asList(listOfTherapistUserIds)).toArray(new String[0]);
        }

        File therapistUsernameList=new File(directory,currentUser.getUid()+"_ConnectedTherapistsUsernames.txt");
        Database database=new Database();

        try
        {
            FileWriter writer=new FileWriter(therapistUsernameList,false);

            for(int i=0;i<listOfTherapistUserIds.length;i++)
            {
                DatabaseReference currentTherapistUsernameReference=database.therapistsReference.child(listOfTherapistUserIds[i]).child("username");
                final String[] currentTherapistUsername = {""};
                currentTherapistUsernameReference.addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        currentTherapistUsername[0]=snapshot.getValue(String.class);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                writer.write(currentTherapistUsername[0]+"\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateManifestFromDatabase()
    {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        File directory=new File(this.getFilesDir(),currentUser.getUid());
        if(!directory.exists())
        {
            directory.mkdir();
        }
        File sharedManifestFile=new File(directory,currentUser.getUid()+"_Shared_Manifest.txt");
        Database database=new Database();
        DatabaseReference reference=database.clientReference.child(currentUser.getUid()).child("sharedNotes");
        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                String manifestFileContents="";
                for(DataSnapshot noteTitle:snapshot.getChildren())
                {
                    manifestFileContents+=noteTitle.getKey()+"\n";
                }
                try
                {
                    FileWriter writer=new FileWriter(sharedManifestFile,false);
                    writer.write(manifestFileContents);
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

}