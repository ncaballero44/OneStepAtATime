package com.example.onestepatatime;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity
{
    EditText registerEmailEntry;
    EditText registerUsernameEntry;
    EditText registerFNameEntry;
    EditText registerLNameEntry;
    EditText registerPasswordEntry;
    EditText registerConfirmPasswordEntry;

    CheckBox therapistCheckbox;
    CheckBox clientCheckbox;

    Button confirmRegistrationButton;

    User newUser;

    FirebaseAuth fAuth;

    Database database;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        initialize();
        configureButtons();
    }

    private void initialize()
    {
        this.registerEmailEntry=findViewById(R.id.registerEmailEntry);
        this.registerUsernameEntry=findViewById(R.id.registerUsernameEntry);
        this.registerFNameEntry=findViewById(R.id.registerFNameEntry);
        this.registerLNameEntry=findViewById(R.id.registerLNameEntry);
        this.registerPasswordEntry=findViewById(R.id.registerPasswordEntry);
        this.registerConfirmPasswordEntry=findViewById(R.id.registerConfirmPasswordEntry);

        this.therapistCheckbox=findViewById(R.id.therapistCheckbox);
        this.clientCheckbox=findViewById(R.id.clientCheckbox);

        this.confirmRegistrationButton=findViewById(R.id.confirmRegistrationButton);

        this.fAuth=FirebaseAuth.getInstance();

        this.database=new Database();
    }

    private void configureButtons()
    {
        confirmRegistrationButton.setOnClickListener((view)->{
            String email=this.registerEmailEntry.getText().toString().trim();
            String username=this.registerUsernameEntry.getText().toString().trim();
            String firstName=this.registerFNameEntry.getText().toString().trim();
            String lastName=this.registerLNameEntry.getText().toString().trim();
            String password=this.registerPasswordEntry.getText().toString().trim();
            String confirmPassword=this.registerConfirmPasswordEntry.getText().toString().trim();

            boolean isTherapist=therapistCheckbox.isChecked();
            boolean isClient=clientCheckbox.isChecked();

            this.newUser=new User(email, username, firstName, lastName, password, confirmPassword, isTherapist, isClient);
            if (!newUser.isOnlyOneBoxChecked())
            {
                Toast.makeText(RegisterActivity.this, "Please only select to be either a client or therapist", Toast.LENGTH_SHORT).show();
                therapistCheckbox.toggle();
                clientCheckbox.toggle();
            }
            else if (!newUser.arePasswordsEqual())
            {
                Toast.makeText(RegisterActivity.this, "Please verify that the exact same password is entered in twice", Toast.LENGTH_SHORT).show();
            }
            else if (!newUser.areAllFieldsFilled())
            {
                Toast.makeText(RegisterActivity.this, "Please verify that all fields have an entry", Toast.LENGTH_SHORT).show();
            }
            else if (!newUser.isAtLeastOneBoxChecked())
            {
                Toast.makeText(RegisterActivity.this, "Please indicate whether you are a client or a therapist", Toast.LENGTH_SHORT).show();
            }
            else if(!newUser.isPasswordLongEnough())
            {
                Toast.makeText(RegisterActivity.this, "Please create a password with at least 8 characters", Toast.LENGTH_SHORT).show();
            }
            else
            {
                fAuth.createUserWithEmailAndPassword(newUser.email, newUser.password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            newUser.userID=fAuth.getCurrentUser().getUid().trim();
                            if(database.createNewUser(newUser))
                            {
                                Toast.makeText(RegisterActivity.this, "User created", Toast.LENGTH_SHORT).show();
                            }
                            finish();
                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this, "An error occurred. Please try again.\nError message: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                );
            }

        });
    }
}
