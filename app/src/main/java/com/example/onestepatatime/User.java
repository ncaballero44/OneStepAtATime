package com.example.onestepatatime;

import java.util.HashMap;
import java.util.Map;

public class User
{
    public String email;
    public String username;
    public String firstName;
    public String lastName;
    public String password;
    public String confirmPassword;

    public boolean isTherapist;
    public boolean isClient;

    public String userID;

    public Map<String,Object> userInformation=new HashMap<>();

    public User(String email, String username, String firstName, String lastName, String password, String confirmPassword, boolean isTherapist, boolean isClient)
    {
        this.email=email;
        this.username=username;
        this.firstName=firstName;
        this.lastName=lastName;
        this.password=password;
        this.confirmPassword=confirmPassword;

        this.isTherapist=isTherapist;
        this.isClient=isClient;

        this.userInformation.put("email", email);
        this.userInformation.put("username", username);
        this.userInformation.put("firstName", firstName);
        this.userInformation.put("lastName",lastName);
        this.userInformation.put("password",password);
        this.userInformation.put("confirmPassword",confirmPassword);
        this.userInformation.put("isTherapist",isTherapist);
        this.userInformation.put("isClient",isClient);
        this.userInformation.put("userID",this.userID);
    }

    public boolean isOnlyOneBoxChecked()
    {
        return !isClient || !isTherapist;
    }

    public boolean arePasswordsEqual()
    {
        return password.equals(confirmPassword);
    }

    public boolean areAllFieldsFilled()
    {
        if (this.email.equals(""))
        {
            return false;
        }
        else if(this.username.equals(""))
        {
            return false;
        }
        else if(this.firstName.equals(""))
        {
            return false;
        }
        else if(this.lastName.equals(""))
        {
            return false;
        }
        else if(this.password.equals(""))
        {
            return false;
        }
        else if(this.confirmPassword.equals(""))
        {
            return false;
        }
        return true;
    }

    public boolean isAtLeastOneBoxChecked()
    {
        return isClient || isTherapist;
    }

    public boolean isPasswordLongEnough()
    {
        return password.length()>=8;
    }
}
