package com.example.onestepatatime;

import android.content.Context;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Notes implements Serializable
{
    public String noteTitle;
    public String noteContents;
    public String ownerUserID;

    public Notes(String noteTitle, String noteContents, String ownerUserID)
    {
        this.noteTitle=noteTitle;
        this.noteContents=noteContents;
        this.ownerUserID=ownerUserID;
    }

}
