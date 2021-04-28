package com.example.onestepatatime;

public class EmergencyContact
{
    String contactName="";
    String contactPhoneNumber="";
    String contactRelationship="";

    public EmergencyContact(String contactName, String contactPhoneNumber, String contactRelationship)
    {
        this.contactName=contactName;
        this.contactPhoneNumber=contactPhoneNumber;
        this.contactRelationship=contactRelationship;
    }

    public boolean areAllFieldsFilled()
    {
        if(this.contactName.equals(""))
        {
            return false;
        }
        else if(this.contactPhoneNumber.equals(""))
        {
            return false;
        }
        else if(this.contactRelationship.equals(""))
        {
            return false;
        }
        return true;
    }
}
