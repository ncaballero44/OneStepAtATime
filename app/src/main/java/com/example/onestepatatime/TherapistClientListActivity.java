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

public class TherapistClientListActivity extends AppCompatActivity
{
    ListView therapistClientConnectionsList;
    Button addNewClientConnectionButton;

    private void initializeElements()
    {
        this.therapistClientConnectionsList=findViewById(R.id.therapistClientConnectionsList);
        this.addNewClientConnectionButton=findViewById(R.id.addNewClientConnectionButton);
    }

    private void configureButtons()
    {
        this.addNewClientConnectionButton.setOnClickListener((view)->{
            startActivity(new Intent(this, TherapistAddNewConnectionActivity.class));
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.therapist_client_list_activity);
        initializeElements();
        configureButtons();
        setClientTherapistConnectionsList();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setClientTherapistConnectionsList();
    }

    private void setClientTherapistConnectionsList()
    {
        therapistClientConnectionsList.setAdapter(null);

        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();

        TherapistClientListUtilities clientListUtilities=new TherapistClientListUtilities();
        String[] therapistUsernameList=clientListUtilities.getAllConnectedClientsUsernames(this, currentUser.getUid());

        ArrayAdapter<String> clientUsernameAdapter=new ArrayAdapter<String>(this, R.layout.note_item, therapistUsernameList);
        therapistClientConnectionsList.setAdapter(clientUsernameAdapter);

        therapistClientConnectionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clientUsername=therapistClientConnectionsList.getItemAtPosition(position).toString();

                Intent viewTherapistInfoIntent=new Intent(getApplicationContext(), ClientInfoDisplayActivity.class);
                viewTherapistInfoIntent.putExtra("CLIENT_USERNAME",clientUsername);
                startActivity(viewTherapistInfoIntent);
            }
        });
    }
}
