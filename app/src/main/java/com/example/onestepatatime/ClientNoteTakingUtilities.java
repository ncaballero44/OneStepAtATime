package com.example.onestepatatime;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;



public class ClientNoteTakingUtilities
{
    public boolean writeToLocalFile(Context context, Notes newNote)
    {
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        String fileName=newNote.noteTitle+".txt";

        FileOutputStream fileOutputStream;
//            ObjectOutputStream objectOutputStream;

        try
        {
            fileOutputStream=context.openFileOutput(fileName,context.MODE_PRIVATE);
//                objectOutputStream=new ObjectOutputStream(fileOutputStream);
//                objectOutputStream.writeBytes(newNote.noteContents);
//                objectOutputStream.close();
            fileOutputStream.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }

        File localFile=context.getFileStreamPath(fileName);
        try
        {
            FileWriter fileWriter=new FileWriter(localFile,false);
            fileWriter.write(newNote.noteContents);
            fileWriter.flush();
            fileWriter.close();
        }catch (Exception e)
        {

        }

        return  localFile.exists();
    }
}