package com.example.onestepatatime;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class NoteTakingUtilities
{
    public static final String FILE_EXTENSION=".bin";

    public static boolean saveNotes(Context context, Notes notes)
    {
        String filename=notes.getDateTimeFormatted(context) +FILE_EXTENSION;//Might need String.valueOf()

        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;

        try
        {
            fileOutputStream=context.openFileOutput(filename,context.MODE_PRIVATE);
            objectOutputStream=new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(notes);
            fileOutputStream.close();
            objectOutputStream.close();
        }catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
