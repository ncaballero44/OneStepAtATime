package com.example.onestepatatime;

import android.os.AsyncTask;
import android.os.Environment;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class GetUrlAndFileNameFromDatabase extends AsyncTask<DatabaseReference, Array, String>
{
    DatabaseReference textFileURLsReference;
    public GetUrlAndFileNameFromDatabase(DatabaseReference textFileURLsReference)
    {
        this.textFileURLsReference=textFileURLsReference;
    }

    @Override
    protected String doInBackground(DatabaseReference... databaseReferences)
    {
        final String[] alreadyInDatabase = {""};
        DatabaseReference textFileURLsReference=databaseReferences[0];
        textFileURLsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot: snapshot.getChildren())
                {
                    String currentURL=postSnapshot.getValue().toString();
                    String fileName=postSnapshot.getKey()+".txt";
                    File localFile=new File("/data/user/0/com.example.onestepatatime/files/"+fileName);
                    if(!localFile.exists())
                    {
                        int count;

                        try
                        {
                            URL currentUrl=new URL(currentURL);
                            URLConnection connection=currentUrl.openConnection();
                            connection.connect();

                            InputStream inputStream=new BufferedInputStream(currentUrl.openStream(),8192);

                            OutputStream outputStream=new FileOutputStream(Environment.getExternalStorageDirectory().toString()+fileName);

                            byte data[]=new byte[1024];

                            while((count=inputStream.read(data))!=-1)
                            {
                                outputStream.write(data,0,count);
                            }
                            outputStream.flush();
                            outputStream.close();
                            inputStream.close();
                        } catch (MalformedURLException e)
                        {
                            e.printStackTrace();
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    alreadyInDatabase[0] = alreadyInDatabase[0] +"\t"+localFile.getName();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return alreadyInDatabase[0];
    }
}
