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
public class MainActivityTest
{
    @Rule
    public IntentsTestRule<MainActivity> mainActivityTestRule=
            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void doesTherapistButtonWork_TransitionsToTherapistActivity()
    {
        onView(withId(R.id.therapistButton)).perform(click());
        intended(hasComponent(TherapistMainActivity.class.getName()));
    }

    @Test
    public void doesClientButtonWork_TransitionsToClientActivity()
    {
        onView(withId(R.id.clientButton)).perform(click());
        intended(hasComponent(ClientMainActivity.class.getName()));
    }
}
