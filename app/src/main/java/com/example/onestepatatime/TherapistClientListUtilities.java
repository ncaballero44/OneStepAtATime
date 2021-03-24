package com.example.onestepatatime;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class TherapistClientListUtilities
{
    public String[] getAllConnectedClientsUsernames(Context context, String clientUserId)
    {
        File directory=new File(context.getFilesDir(),"ListsOfClientsAndTherapists");
        File clientListFile=new File(directory,"AllClients.txt");

        String[] listOfAllClients=new String[(int)clientListFile.length()];

        if(clientListFile.exists()) {
            String fileContents = "";
            try
            {
                Scanner scanner=new Scanner(clientListFile);
                if(scanner.hasNext())
                {
                    fileContents=scanner.useDelimiter("\\Z").next();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            listOfAllClients=fileContents.split("\n");
            listOfAllClients=new HashSet<String>(Arrays.asList(listOfAllClients)).toArray(new String[0]);
        }
        String[] listOfConnectedClientUsernames=new String[listOfAllClients.length];
        ArrayList<Object> connectedClientUsernames=new ArrayList<>();
        String[] listOfConnectedClientUserIds=getAllConnectedClientUserIds(context,clientUserId);
        for(int i=0;i<listOfAllClients.length;i++)
        {
            for(int j=0;j<listOfConnectedClientUserIds.length;j++)
            {
                String[] clientInformation=listOfAllClients[i].split("\t");
                if(clientInformation[0].equals(listOfConnectedClientUserIds[j]))
                {
                    connectedClientUsernames.add(clientInformation[1]);
                }
            }
        }
        Object[] tempArray=connectedClientUsernames.toArray();

        listOfConnectedClientUsernames=Arrays.copyOf(tempArray,tempArray.length,String[].class);
        return listOfConnectedClientUsernames;
    }

    public String[] getAllConnectedClientUserIds(Context context, String currentUserId)
    {
        File directory=new File(context.getFilesDir(),currentUserId);
        File clientListFile=new File(directory,currentUserId+"_ConnectedClientUserIds.txt");
        String[] connectedClientUserIds=new String[(int)clientListFile.length()];

        if(clientListFile.exists())
        {
            String fileContents="";
            try
            {
                Scanner scanner=new Scanner(clientListFile);
                if(scanner.hasNext())
                {
                    fileContents=scanner.useDelimiter("\\Z").next();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            connectedClientUserIds=fileContents.split("\n");
            connectedClientUserIds=new HashSet<String>(Arrays.asList(connectedClientUserIds)).toArray(new String[0]);
        }

        return connectedClientUserIds;
    }

    public String getClientIdFromEmail(Context context, String clientEmail)
    {
        String clientUserId="";

        File directory=new File(context.getFilesDir(),"ListsOfClientsAndTherapists");
        File clientListFile=new File(directory,"AllClients.txt");

        String[] listOfClientIdsAndEmails=new String[(int)clientListFile.length()];
        if(clientListFile.exists())
        {
            String fileContents="";
            try
            {
                Scanner scanner=new Scanner(clientListFile);
                if(scanner.hasNext())
                {
                    fileContents=scanner.useDelimiter("\\Z").next();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            listOfClientIdsAndEmails=fileContents.split("\n");
            listOfClientIdsAndEmails=new HashSet<String>(Arrays.asList(listOfClientIdsAndEmails)).toArray(new String[0]);

            for(int i=0; i<listOfClientIdsAndEmails.length;i++)
            {
                String[] clientIdAndEmail=new String[3];
                clientIdAndEmail=listOfClientIdsAndEmails[i].split("\t");
                if(clientIdAndEmail[2].equals(clientEmail))
                {
                    clientUserId=clientIdAndEmail[0];
                }
            }
        }
        return clientUserId;
    }

    public String getClientIdFromUsername(Context context, String clientUsername)
    {
        String clientUserId="";

        File directory=new File(context.getFilesDir(),"ListsOfClientsAndTherapists");
        File clientListFile=new File(directory,"AllClients.txt");

        String[] listOfClientIdsAndEmails=new String[(int)clientListFile.length()];
        if(clientListFile.exists())
        {
            String fileContents="";
            try
            {
                Scanner scanner=new Scanner(clientListFile);
                if(scanner.hasNext())
                {
                    fileContents=scanner.useDelimiter("\\Z").next();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            listOfClientIdsAndEmails=fileContents.split("\n");
            listOfClientIdsAndEmails=new HashSet<String>(Arrays.asList(listOfClientIdsAndEmails)).toArray(new String[0]);

            for(int i=0; i<listOfClientIdsAndEmails.length;i++)
            {
                String[] clientIdAndEmail=new String[3];
                clientIdAndEmail=listOfClientIdsAndEmails[i].split("\t");
                if(clientIdAndEmail[1].equals(clientUsername))
                {
                    clientUserId=clientIdAndEmail[0];
                }
            }
        }
        return clientUserId;
    }
}
