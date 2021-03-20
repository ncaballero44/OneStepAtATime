package com.example.onestepatatime;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

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
            String therapistEmail=therapistEmailSearch.getText().toString();

            Database database=new Database();

//            final String[] therapistUserID = {""};
//            database.therapistsReference.orderByChild("email").equalTo(therapistEmail).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot)
//                {
//                        therapistUserID[0] =snapshot.getKey();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//            Toast.makeText(this, therapistUserID[0], Toast.LENGTH_SHORT).show();
            String therapistUserId=getTherapistIdFromEmail(therapistEmail);
            Toast.makeText(this, therapistUserId, Toast.LENGTH_SHORT).show();
        });
    }

    private String getTherapistIdFromEmail(String therapistEmail)
    {
        String therapistUserId="";

        File directory=new File(this.getFilesDir(),"ListsOfClientsAndTherapists");
        File therapistListFile=new File(directory,"AllTherapists.txt");

        String[] listOfTherapistIdsAndEmails=new String[(int)therapistListFile.length()];
        if(therapistListFile.exists())
        {
            String fileContents="";
            try
            {
                Scanner scanner=new Scanner(therapistListFile);
                if(scanner.hasNext())
                {
                    fileContents=scanner.useDelimiter("\\Z").next();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            listOfTherapistIdsAndEmails=fileContents.split("\n");
            listOfTherapistIdsAndEmails=new HashSet<String>(Arrays.asList(listOfTherapistIdsAndEmails)).toArray(new String[0]);

            for(int i=0; i<listOfTherapistIdsAndEmails.length;i++)
            {
                String[] therapistIdAndEmail=new String[3];
                therapistIdAndEmail=listOfTherapistIdsAndEmails[i].split("\t");
                if(therapistIdAndEmail[1].equals(therapistEmail))
                {
                    therapistUserId=therapistIdAndEmail[0];
                }
            }

        }

        return therapistUserId;
    }

    private void saveConnection()
    {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();

        //TODO get connection from listOfAllTherapists.txt


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
