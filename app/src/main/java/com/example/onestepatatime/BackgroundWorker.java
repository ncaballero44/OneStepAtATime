package com.example.onestepatatime;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundWorker extends AsyncTask<String, Void, String>
{
    Context context;

    AlertDialog alertDialog;

    BackgroundWorker (Context ctx)
    {
        this.context=ctx;
    }
    @Override
    protected String doInBackground(String... params)
    {
        String command=params[0];
        String username=params[1];
        String password=params[2];
        String result="";
        String line="";
        String login_URL="http://localhost/login.php";
        if(command.equals("login"))
        {
            try
            {
                URL url=new URL(login_URL);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream=httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String postData= URLEncoder.encode("username", "UTF-8")+"="+URLEncoder.encode(username, "UTF-8")+"&"+
                        URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(postData);

                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream= httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                while((line=bufferedReader.readLine())!=null)
                {
                    result+=line;
                    System.out.println(result);
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;
            } catch (MalformedURLException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                result=e.getMessage();
            }
        }
        return result;
    }

    @Override
    protected void onPreExecute()
    {
        alertDialog=new AlertDialog.Builder(this.context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result)
    {
        alertDialog.setMessage(result);

        alertDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
