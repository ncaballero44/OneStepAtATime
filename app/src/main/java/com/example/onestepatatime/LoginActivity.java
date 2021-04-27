package com.example.onestepatatime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity
{
    Button loginButton;
    Button registerButton;
    EditText emailEntry;
    EditText passwordEntry;
    CheckBox therapistCheckbox;
    CheckBox clientCheckbox;

    Database database;

    FirebaseAuth fAuth;


    public void initializeElements()
    {
        this.loginButton=findViewById(R.id.loginButton);
        this.registerButton=findViewById(R.id.registerButton);
        this.emailEntry=findViewById(R.id.loginEmailEntry);
        this.passwordEntry=findViewById(R.id.passwordEntry);
        this.therapistCheckbox=findViewById(R.id.loginTherapistCheckbox);
        this.clientCheckbox=findViewById(R.id.loginClientCheckbox);
    }

    public void databaseInitialization()
    {
        this.database=new Database();
        this.fAuth=FirebaseAuth.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        databaseInitialization();
        initializeElements();
        configureButtons();
    }

    private void configureButtons()
    {
        this.registerButton.setOnClickListener((view)->{
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        this.loginButton.setOnClickListener((view)->{
            String email=emailEntry.getText().toString().trim();
            String password=passwordEntry.getText().toString().trim();

            if (!(clientCheckbox.isChecked() || therapistCheckbox.isChecked()))
            {
                Toast.makeText(LoginActivity.this, "Please select a role to log in as", Toast.LENGTH_SHORT).show();
            }
            else if ((clientCheckbox.isChecked()&&therapistCheckbox.isChecked()))
            {
                Toast.makeText(LoginActivity.this, "Please select only one role to log in as", Toast.LENGTH_SHORT).show();
                clientCheckbox.toggle();
                therapistCheckbox.toggle();
            }
            else
            {
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            if(clientCheckbox.isChecked())
                            {
                                FirebaseUser currentUser=fAuth.getCurrentUser();
                                Database database=new Database();
                                database.clientReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.child(currentUser.getUid()).getValue()!=null)
                                        {
                                            Toast.makeText(LoginActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), ClientMainActivity.class));
                                        }
                                        else
                                        {
                                            Toast.makeText(LoginActivity.this, "User not found. Please ensure login credentials are correct or create a new account if you are a new user", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                            else if(therapistCheckbox.isChecked())
                            {
                                FirebaseUser currentUser=fAuth.getCurrentUser();
                                Database database=new Database();
                                database.therapistsReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.child(currentUser.getUid()).getValue()!=null)
                                        {
                                            Toast.makeText(LoginActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), TherapistMainActivity.class));
                                        }
                                        else
                                        {
                                            Toast.makeText(LoginActivity.this, "User not found. Please ensure login credentials are correct or create a new account if you are a new user", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }

                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Authentication failed: "+task.getException(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }



}
