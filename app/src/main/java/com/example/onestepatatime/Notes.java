package com.example.onestepatatime;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Notes
{
    public long dateTime;
    public String noteTitle;
    public String noteContents;

    public Notes(long dateTime, String noteTitle, String noteContents)
    {
        this.dateTime=dateTime;
        this.noteTitle=noteTitle;
        this.noteContents=noteContents;
    }

    public String getDateTimeFormatted(Context context)
    {
        SimpleDateFormat formattedDate=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", context.getResources().getConfiguration().locale);
        formattedDate.setTimeZone(TimeZone.getDefault());
        return formattedDate.format(new Date(this.dateTime));
    }
}
