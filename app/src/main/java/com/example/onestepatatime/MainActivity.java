package com.example.onestepatatime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    Button clientButton;
    Button therapistButton;
    //TODO Create a login button. Add to Main Activity xml file

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeButtons();

        configureClientButton();
        configureTherapistButton();
    }

    private void initializeButtons()
    {
        this.clientButton=(Button) findViewById(R.id.clientButton);
        this.therapistButton=(Button) findViewById(R.id.therapistButton);
    }

    private void configureClientButton()
    {
        this.clientButton.setOnClickListener((view)->{
            startActivity(new Intent(MainActivity.this, ClientMainActivity.class));
        });
    }

    private void configureTherapistButton()
    {
        this.therapistButton.setOnClickListener((view)->{
            startActivity(new Intent(MainActivity.this, TherapistMainActivity.class));
        });
    }


}
