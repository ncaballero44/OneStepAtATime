package com.example.onestepatatime;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class ClientTherapistListUtilities
{
    public String[] getAllConnectedTherapistsUsernames(Context context, String clientUserId)
    {
        File directory=new File(context.getFilesDir(),"ListsOfClientsAndTherapists");
        File therapistListFile=new File(directory,"AllTherapists.txt");

        String[] listOfAllTherapists=new String[(int)therapistListFile.length()];

        if(therapistListFile.exists()) {
            String fileContents = "";
            try
            {
                Scanner scanner=new Scanner(therapistListFile);
                if(scanner.hasNext())
                {
                    fileContents=scanner.useDelimiter("\\Z").next();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            listOfAllTherapists=fileContents.split("\n");
            listOfAllTherapists=new HashSet<String>(Arrays.asList(listOfAllTherapists)).toArray(new String[0]);
        }
        String[] listOfConnectedTherapistUsernames=new String[listOfAllTherapists.length];
        ArrayList<Object> connectedTherapistUsernames=new ArrayList<>();
        String[] listOfConnectedTherapistUserIds=getAllConnectedTherapistUserIds(context,clientUserId);
        for(int i=0;i<listOfAllTherapists.length;i++)
        {
            for(int j=0;j<listOfConnectedTherapistUserIds.length;j++)
            {
                String[] therapistInformation=listOfAllTherapists[i].split("\t");
                if(therapistInformation[0].equals(listOfConnectedTherapistUserIds[j]))
                {
                    connectedTherapistUsernames.add(therapistInformation[1]);
                }
            }
        }
        Object[] tempArray=connectedTherapistUsernames.toArray();

        listOfConnectedTherapistUsernames=Arrays.copyOf(tempArray,tempArray.length,String[].class);
        return listOfConnectedTherapistUsernames;
    }

    public String[] getAllConnectedTherapistUserIds(Context context, String currentUserId)
    {
        File directory=new File(context.getFilesDir(),currentUserId);
        File therapistListFile=new File(directory,currentUserId+"_ConnectedTherapistUserIds.txt");
        String[] connectedTherapistUserIds=new String[(int)therapistListFile.length()];

        if(therapistListFile.exists())
        {
            String fileContents="";
            try
            {
                Scanner scanner=new Scanner(therapistListFile);
                if(scanner.hasNext())
                {
                    fileContents=scanner.useDelimiter("\\Z").next();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            connectedTherapistUserIds=fileContents.split("\n");
            connectedTherapistUserIds=new HashSet<String>(Arrays.asList(connectedTherapistUserIds)).toArray(new String[0]);
        }

        return connectedTherapistUserIds;
    }

    public String getTherapistIdFromEmail(Context context, String therapistEmail)
    {
        String therapistUserId="";

        File directory=new File(context.getFilesDir(),"ListsOfClientsAndTherapists");
        File therapistListFile=new File(directory,"AllTherapists.txt");

        String[] listOfTherapistIdsAndEmails=new String[(int)therapistListFile.length()];
        if(therapistListFile.exists())
        {
            String fileContents="";
            try
            {
                Scanner scanner=new Scanner(therapistListFile);
                if(scanner.hasNext())
                {
                    fileContents=scanner.useDelimiter("\\Z").next();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            listOfTherapistIdsAndEmails=fileContents.split("\n");
            listOfTherapistIdsAndEmails=new HashSet<String>(Arrays.asList(listOfTherapistIdsAndEmails)).toArray(new String[0]);

            for(int i=0; i<listOfTherapistIdsAndEmails.length;i++)
            {
                String[] therapistIdAndEmail=new String[3];
                therapistIdAndEmail=listOfTherapistIdsAndEmails[i].split("\t");
                if(therapistIdAndEmail[2].equals(therapistEmail))
                {
                    therapistUserId=therapistIdAndEmail[0];
                }
            }
        }
        return therapistUserId;
    }



}
