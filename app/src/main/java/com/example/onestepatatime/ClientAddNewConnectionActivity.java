package com.example.onestepatatime;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

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

            final String[] therapistUserID = {""};
            database.therapistsReference.orderByChild("email").equalTo(therapistEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                        therapistUserID[0] =snapshot.getKey();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            Toast.makeText(this, therapistUserID[0], Toast.LENGTH_SHORT).show();
            finish();
        });
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
