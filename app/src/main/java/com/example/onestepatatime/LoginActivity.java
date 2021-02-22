package com.example.onestepatatime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity
{
    Button loginButton;
    EditText usernameEntry;
    EditText passwordEntry;
    public static String username;
    public static String password;

    public void initializeElements()
    {
        this.loginButton=(Button) findViewById(R.id.loginButton);
        this.usernameEntry=(EditText) findViewById(R.id.usernameEntry);
        this.passwordEntry=(EditText) findViewById(R.id.passwordEntry);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initializeElements();
        configureLoginButton();
    }

    private void configureLoginButton()
    {
        this.loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                username=usernameEntry.getText().toString();
                password=passwordEntry.getText().toString();
                Toast toast=Toast.makeText(getApplicationContext(),"Username: "+ username + " Password: "+ password, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

}
