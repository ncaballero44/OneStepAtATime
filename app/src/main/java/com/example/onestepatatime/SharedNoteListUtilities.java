package com.example.onestepatatime;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class SharedNoteListUtilities
{
    public String[] getAllSharedNoteEntries(Context context, String userId)
    {
        File directory=new File(context.getFilesDir(),userId);
        File sharedManifestFile=new File(directory,userId+"_Shared_Manifest.txt");

        String[] listOfNotes=new String[(int)sharedManifestFile.length()];

        if(sharedManifestFile.exists())
        {
            String contentsOfManifestFile="";
            try
            {
                Scanner scanner=new Scanner(sharedManifestFile);
                if(scanner.hasNext())
                {
                    contentsOfManifestFile=scanner.useDelimiter("\\Z").next();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            listOfNotes=contentsOfManifestFile.split("\n");
            listOfNotes=new HashSet<String>(Arrays.asList(listOfNotes)).toArray(new String[0]);
        }

        return listOfNotes;
    }
}
