package com.example.onestepatatime;

import android.content.Context;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Notes implements Serializable
{
//    public long dateTime;
    public String noteTitle;
    public String noteContents;
    public String ownerUserID;

    public Notes(String noteTitle, String noteContents, String ownerUserID)
    {
//        this.dateTime=dateTime;
        this.noteTitle=noteTitle;
        this.noteContents=noteContents;
        this.ownerUserID=ownerUserID;
    }

//    public String getDateTimeFormatted(Context context)
//    {
//        SimpleDateFormat formattedDate=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", context.getResources().getConfiguration().locale);
//        formattedDate.setTimeZone(TimeZone.getDefault());
//        return formattedDate.format(new Date(this.dateTime));
//    }
}
