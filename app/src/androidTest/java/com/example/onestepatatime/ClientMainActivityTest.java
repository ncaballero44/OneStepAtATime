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
}
