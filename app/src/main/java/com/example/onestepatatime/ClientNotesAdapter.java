package com.example.onestepatatime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ClientNotesAdapter extends ArrayAdapter<Notes>
{

    public ClientNotesAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Notes> noteList)
    {
        super(context, resource, noteList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.note_item,null);
        }

        Notes currentNote=getItem(position);
        if (currentNote!=null)
        {
            TextView title=(TextView)convertView.findViewById(R.id.listNoteTitle);
            TextView content=(TextView)convertView.findViewById(R.id.listNoteContent);

            title.setText(currentNote.noteTitle);
            if(currentNote.noteContents.length()>50)
            {
                content.setText(currentNote.noteContents.substring(0,50));
            }
            else
            {
                content.setText(currentNote.noteContents);
            }

        }
        return convertView;
    }
}
