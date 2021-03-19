package com.example.onestepatatime;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class ClientNoteTakingUtilities
{
    public String[] getAllNotesAndJournalEntries(Context context, String userID)
    {
        File directory=new File(context.getFilesDir(),userID);
        File manifestFile=new File(directory,userID+"_Manifest.txt");
        File manifestFileOutput=new File(directory,userID+"_Manifest_Output.txt");

        String[] listOfFiles=new String[(int)manifestFile.length()];
        if(manifestFile.exists())
        {
            String content="";
            try
            {
                Scanner scanner=new Scanner(manifestFile);
                if(scanner.hasNext())
                {
                    content = scanner.useDelimiter("\\Z").next();
                }

            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            listOfFiles=content.split("\n");
            listOfFiles=new HashSet<String>(Arrays.asList(listOfFiles)).toArray(new String[0]);
        }

        return listOfFiles;
    }
}