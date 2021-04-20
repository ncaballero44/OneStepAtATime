package com.example.onestepatatime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ClientWorksheetsActivity extends AppCompatActivity
{
    private static final int STORAGE_PERMISSION_CODE=100;

    Button syncButton;

    private void configureButtons()
    {
        this.syncButton=findViewById(R.id.clientSyncButton);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_worksheets_activity);

        configureButtons();
        writeToDownloadsFolder();
    }

    private void writeToDownloadsFolder()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
        }
        else
        {
            try
            {
                ContentValues values = new ContentValues();
                values.put(MediaStore.MediaColumns.DISPLAY_NAME, "test");
                values.put(MediaStore.MediaColumns.MIME_TYPE, "text/plain");
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/One Step At A Time Shared Files");

                Uri uri=getContentResolver().insert(MediaStore.Downloads.getContentUri("external"),values);

                OutputStream outputStream=getContentResolver().openOutputStream(uri);
                outputStream.write("This is a test.".getBytes());
                outputStream.close();
                Toast.makeText(this, "File created", Toast.LENGTH_SHORT).show();
            }
            catch (FileNotFoundException e)
            {
                Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
            }
            catch (IOException e)
            {
                Toast.makeText(this, "Failed to create file", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
