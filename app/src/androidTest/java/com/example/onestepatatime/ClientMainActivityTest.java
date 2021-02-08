package com.example.onestepatatime;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.*;
import org.junit.runner.RunWith;

import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.Intents.intended;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class ClientMainActivityTest
{
    @Rule
    public IntentsTestRule<ClientMainActivity> mainClientActivityTestRule=
            new IntentsTestRule<>(ClientMainActivity.class);

    @Test
    public void doesClientCalendarButtonWork_TransitionToClientCalendarActivity()
    {
        onView(withId(R.id.calendarButtonClient)).perform(click());
        intended(hasComponent(ClientCalendarActivity.class.getName()));
    }

    @Test
    public void doesClientJournalButtonWork_TransitionToClientJournalActivity()
    {
        onView(withId(R.id.journalButtonClient)).perform(click());
        intended(hasComponent(ClientJournalActivity.class.getName()));
    }

    @Test
    public void doesClientWorksheetsButtonWork_TransitionsToClientWorksheetActivity()
    {
        onView(withId(R.id.worksheetsButtonClient)).perform(click());
        intended(hasComponent(ClientWorksheetsActivity.class.getName()));
    }

    @Test
    public void doesClientNotesButtonWork_TransitionsToClientNotesActivity()
    {
        onView(withId(R.id.notesButtonClient)).perform(click());
        intended(hasComponent(ClientNotesActivity.class.getName()));
    }

    @Test
    public void doesTherapistListButtonWork_TransitionsToClientTherapistListActivity()
    {
        onView(withId(R.id.therapistListButton)).perform(click());
        intended(hasComponent(ClientTherapistListActivity.class.getName()));
    }

    @Test
    public void doesClientEmergencyContactListButtonWork_TransitionsToClientEmergencyContactListActivity()
    {
        onView(withId(R.id.emergencyContactListButtonClient)).perform(click());
        intended(hasComponent(ClientEmergencyContactListActivity.class.getName()));
    }
}
