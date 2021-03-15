package com.example.onestepatatime;

import android.widget.Button;
import android.app.Activity;
import android.widget.EditText;

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
public class LoginActivityTest
{
    @Rule
    public IntentsTestRule<LoginActivity> loginActivityTestRule=
            new IntentsTestRule<>(LoginActivity.class);
    private EditText usernameEntry;
    private EditText passwordEntry;
//   @Before
//   public void setUp()
//   {
//       usernameEntry=(EditText)LoginActivity.findViewById(R.id.usernameEntry);
//   }
//
//    private EditText passwordEntry=(EditText) LoginActivity.findViewById(R.id.passwordEntry);



    @Test
    public void doesUsernameEntryGetUsernameString()
    {
        String testUsername="username";

        usernameEntry.setText(testUsername);
        onView(withId(R.id.loginButton)).perform(click());
//        Assert.assertEquals(testUsername,LoginActivity.username);
    }
}
