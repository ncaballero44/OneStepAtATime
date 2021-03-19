package com.example.onestepatatime;

import android.os.AsyncTask;
import android.os.Environment;

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

public class DownloadFileFromUrl extends AsyncTask<String, Array, String>
{
    String currentUrl;
    String fileName;
    public DownloadFileFromUrl(String currentURL, String fileName) 
    {
        this.currentUrl=currentURL;
        this.fileName=fileName;
    }

    @Override
    protected String doInBackground(String... UrlAndFileName)
    {

       int count;

       try
       {
           URL currentUrl=new URL(this.currentUrl);
           URLConnection connection=currentUrl.openConnection();
//           connection.connect();

           InputStream inputStream=new BufferedInputStream(currentUrl.openStream(),8192);

           OutputStream outputStream=new FileOutputStream(Environment.getExternalStorageDirectory().toString()+this.fileName);

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
        return null;
    }

    @Override
    protected void onProgressUpdate(Array... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
