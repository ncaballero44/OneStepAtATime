package com.example.onestepatatime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.*;


public class LoginActivity extends AppCompatActivity
{
    Button loginButton;
    Button registerButton;
    EditText emailEntry;
    EditText passwordEntry;

    String testDataRead;

   Database database=new Database();


    public void initializeElements()
    {
        this.loginButton=findViewById(R.id.loginButton);
        this.registerButton=findViewById(R.id.registerButton);
        this.emailEntry=findViewById(R.id.loginEmailEntry);
        this.passwordEntry=findViewById(R.id.passwordEntry);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initializeElements();
        testDatabase();
        configureButtons();
    }

    private void configureButtons()
    {
        this.registerButton.setOnClickListener((view)->{
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        this.loginButton.setOnClickListener((view)->{
            String email=emailEntry.getText().toString();
            String password=passwordEntry.getText().toString();

            Toast.makeText(LoginActivity.this, "Email: " + email + " Password: "+password, Toast.LENGTH_SHORT).show();
            //TODO login authentication
        });
    }

    private void testDatabase()
    {
        database.root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                testDataRead=snapshot.child("therapist").child("20000000").child("email").getValue(String.class);
                Toast.makeText(LoginActivity.this, testDataRead, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
