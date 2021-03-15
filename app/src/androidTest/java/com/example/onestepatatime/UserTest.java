package com.example.onestepatatime;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.*;
import org.junit.runner.RunWith;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)

public class UserTest
{
    User testUser;
    public void initializeTestUser()
    {
        this.testUser=new User("email@gmail.com", "BobJohn2", "Bob", "John", "password123", "password123", false, true);
    }

    public void initializeUserWithAllEmptyFields()
    {
        this.testUser=new User("", "", "", "", "", "", false, false);
    }

    @Test
    public void userObjectCreation()
    {
        initializeTestUser();
        assertThat(testUser.email.equals("email@gmail.com")).isTrue();
        assertThat(testUser.username.equals("BobJohn2")).isTrue();
        assertThat(testUser.firstName.equals("Bob")).isTrue();
        assertThat(testUser.lastName.equals("John")).isTrue();
        assertThat(testUser.password.equals("password123")).isTrue();
        assertThat(testUser.confirmPassword.equals("password123")).isTrue();
        assertThat(!testUser.isTherapist).isTrue();
        assertThat(testUser.isClient).isTrue();
    }

    @Test
    public void isOnlyOneBoxChecked_ShouldReturnTrue()
    {
        initializeTestUser();
        assertThat(testUser.isOnlyOneBoxChecked()).isTrue();
    }

    @Test
    public void arePasswordsEqual_ShouldReturnTrue()
    {
        initializeTestUser();
        assertThat(testUser.arePasswordsEqual()).isTrue();
    }

    @Test
    public void anyFieldNull_ShouldReturnTrue()
    {
        initializeTestUser();
        assertThat(testUser.areAllFieldsFilled()).isTrue();
    }

    @Test
    public void noBoxesChecked_ShouldReturnFalse()
    {
        initializeUserWithAllEmptyFields();
        assertThat(testUser.isAtLeastOneBoxChecked()).isFalse();
    }

    @Test
    public void emptyFields_ShouldReturnFalse()
    {
        initializeUserWithAllEmptyFields();
        assertThat(testUser.areAllFieldsFilled()).isFalse();
    }

    @Test
    public void emptyEmail_ShouldReturnFalse()
    {
        initializeTestUser();
        testUser.email="";
        assertThat(testUser.areAllFieldsFilled()).isFalse();
    }

    @Test
    public void emptyUsername_ShouldReturnFalse()
    {
        initializeTestUser();
        testUser.username="";
        assertThat(testUser.areAllFieldsFilled()).isFalse();
    }

    @Test
    public void emptyFirstName_ShouldReturnFalse()
    {
        initializeTestUser();
        testUser.firstName="";
        assertThat(testUser.areAllFieldsFilled()).isFalse();
    }

    @Test
    public void emptyLastName_ShouldReturnFalse()
    {
        initializeTestUser();
        testUser.lastName="";
        assertThat(testUser.areAllFieldsFilled()).isFalse();
    }

    @Test
    public void emptyPassword_ShouldReturnFalse()
    {
        initializeTestUser();
        testUser.password="";
        assertThat(testUser.areAllFieldsFilled()).isFalse();
    }

    @Test
    public void emptyConfirmPassword_ShouldReturnFalse()
    {
        initializeTestUser();
        testUser.confirmPassword="";
        assertThat(testUser.areAllFieldsFilled()).isFalse();
    }

    @Test
    public void bothBoxesChecked_ShouldReturnFalse()
    {
        initializeTestUser();
        testUser.isTherapist=true;
        assertThat(testUser.isOnlyOneBoxChecked()).isFalse();
    }

    @Test
    public void passwordIsLongEnough_ShouldReturnTrue()
    {
        initializeTestUser();
        assertThat(testUser.isPasswordLongEnough()).isTrue();
    }

    @Test
    public void passwordIsNotLongEnough_ShouldReturnFalse()
    {
        initializeUserWithAllEmptyFields();
        assertThat(testUser.isPasswordLongEnough()).isFalse();
    }
}
