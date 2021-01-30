package com.example.onestepatatime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    Button clientButton;
    Button therapistButton;

    public void initializeButtons()
    {
        this.clientButton=(Button) findViewById(R.id.clientButton);
        this.therapistButton=(Button) findViewById(R.id.therapistButton);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeButtons();
    }
}
