package com.example.onestepatatime;

import android.content.Context;
import android.net.Uri;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ClientNoteTakingUtilities
{

    public void createTextFile(Context context,Notes newNote)
    {
        String fileName=newNote.noteTitle+".txt";

        FileOutputStream fileOutputStream;
        try
        {
            fileOutputStream=context.openFileOutput(fileName,context.MODE_PRIVATE);
            fileOutputStream.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static ArrayList<Notes> getAllSavedNotesFromDatabaseStorage(Context context)
    {
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = fAuth.getCurrentUser();

        StorageReference notesAndJournalReference = storageRef.child("client").child(currentUser.getUid()).child("notesAndJournalEntries/");

        ArrayList<File> fileList=new ArrayList<>();
        notesAndJournalReference.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult)
            {
                for(StorageReference file:listResult.getItems())
                {
                    File tempFile=null;
                    try {
                        tempFile=File.createTempFile("temp",".txt");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Uri destinationUri=Uri.fromFile(tempFile);
                    FileDownloadTask fileDownloadTask=file.getFile(destinationUri);
                    File finalTempFile = tempFile;
                    fileDownloadTask.addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                        }
                    });
                    fileList.add(finalTempFile);
                }
            }
        });
        ArrayList<Notes> notesList=new ArrayList<>();
        for(int i=0;i<fileList.size();i++)
        {
            File tempFile=fileList.get(i);
            String fileName=tempFile.getName();
            String fileContents="e";
            try
            {
                FileReader fileReader=new FileReader(tempFile);
                fileContents=fileReader.toString();
            }catch (FileNotFoundException e)
            {
                e.printStackTrace();
                return null;
            }
            Notes tempNote=new Notes(fileName,fileContents);
            notesList.add(tempNote);

        }

        return notesList;
    }
}