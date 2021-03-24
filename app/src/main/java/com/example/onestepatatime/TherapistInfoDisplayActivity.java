package com.example.onestepatatime;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class TherapistInfoDisplayActivity extends AppCompatActivity
{
    TextView therapistUsername;
    TextView therapistFirstName;
    TextView therapistLastName;
    TextView therapistEmail;

    private void initializeElements()
    {
        this.therapistUsername=findViewById(R.id.therapistUsername);
        this.therapistFirstName=findViewById(R.id.therapistFirstName);
        this.therapistLastName=findViewById(R.id.therapistLastName);
        this.therapistEmail=findViewById(R.id.therapistEmail);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.therapist_info_display_activity);
        initializeElements();

        ClientTherapistListUtilities clientTherapistListUtilities=new ClientTherapistListUtilities();
        String passedTherapistUsername=getIntent().getStringExtra("THERAPIST_USERNAME");
        String therapistUserId=clientTherapistListUtilities.getTherapistIdFromUsername(this, passedTherapistUsername);

        Database database=new Database();
        DatabaseReference therapistReference=database.therapistsReference.child(therapistUserId);

        therapistReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                therapistUsername.setText(snapshot.child("username").getValue(String.class));
                therapistFirstName.setText(snapshot.child("firstName").getValue(String.class));
                therapistLastName.setText(snapshot.child("lastName").getValue(String.class));
                therapistEmail.setText(snapshot.child("email").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
