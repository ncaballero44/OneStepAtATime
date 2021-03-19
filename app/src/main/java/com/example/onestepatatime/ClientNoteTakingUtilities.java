package com.example.onestepatatime;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ClientNoteTakingUtilities
{
    public String[] getAllNotesAndJournalEntries(Context context, String userID)
    {
        File directory=new File(context.getFilesDir(),userID);
        File manifestFile=new File(directory,userID+"_Manifest.txt");
        String[] listOfFiles=new String[(int)manifestFile.length()];
        if(manifestFile.exists())
        {
            String content="";
            try
            {
                content = new Scanner(manifestFile).useDelimiter("\\Z").next();
            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            listOfFiles=content.split("\n");
        }

        return listOfFiles;
    }
}