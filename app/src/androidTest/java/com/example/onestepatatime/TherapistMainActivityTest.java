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
public class TherapistMainActivityTest
{
    @Rule
    public IntentsTestRule<TherapistMainActivity> mainTherapistActivityTestRule=
            new IntentsTestRule<>(TherapistMainActivity.class);

    @Test
    public void doesTherapistCalendarButtonWork_TransitionToTherapistCalendarActivity()
    {
        onView(withId(R.id.calendarButtonTherapist)).perform(click());
        intended(hasComponent(TherapistCalendarActivity.class.getName()));
    }

    @Test
    public void doesTherapistAssessmentButtonWork_TransitionToTherapistAssessmentActivity()
    {
        onView(withId(R.id.assessmentButton)).perform(click());
        intended(hasComponent(TherapistAssessmentActivity.class.getName()));
    }

    @Test
    public void doesTherapistWorksheetsButtonWork_TransitionToTherapistWorksheetsActivity()
    {
        onView(withId(R.id.worksheetsButtonTherapist)).perform(click());
        intended(hasComponent(TherapistWorksheetsActivity.class.getName()));
    }

    @Test
    public void doesTherapistNotesButtonWork_TransitionToTherapistNotesActivity()
    {
        onView(withId(R.id.notesButtonTherapist)).perform(click());
        intended(hasComponent(TherapistNotesActivity.class.getName()));
    }

    @Test
    public void doesClientListButtonWork_TransitionToTherapistClientListActivity()
    {
        onView(withId(R.id.clientListButton)).perform(click());
        intended(hasComponent(TherapistClientListActivity.class.getName()));
    }
}
