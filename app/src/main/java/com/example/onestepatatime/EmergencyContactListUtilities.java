package com.example.onestepatatime;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class EmergencyContactListUtilities
{
    public String[] getAllEmergencyContactNames(Context context, String currentUserId)
    {
        File directory=new File(context.getFilesDir(),currentUserId);
        File listOfEmergencyContactsFile=new File(directory,currentUserId+"_ListOfEmergencyContacts.txt");
        String[] listOfEmergencyContacts=new String[(int)listOfEmergencyContactsFile.length()];

        if(listOfEmergencyContactsFile.exists()) {
            String fileContents = "";
            try
            {
                Scanner scanner=new Scanner(listOfEmergencyContactsFile);
                if(scanner.hasNext())
                {
                    fileContents=scanner.useDelimiter("\\Z").next();
                }
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            listOfEmergencyContacts=fileContents.split("\n");
            listOfEmergencyContacts=new HashSet<String>(Arrays.asList(listOfEmergencyContacts)).toArray(new String[0]);
        }

        return listOfEmergencyContacts;
    }
}
