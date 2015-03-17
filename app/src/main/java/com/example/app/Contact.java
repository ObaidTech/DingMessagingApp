package com.example.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by OBNinja on 11/11/14.
 */
public class Contact {
    Bitmap displayPicture;
    String displayname;
    HashMap<Integer, String> phoneNumbers;
    HashMap<String, String> emails;
    String contactID;
    Context ParentActivity;

    public Contact(Context ParentActivity){};

    public Contact(String aDisplayName, HashMap<Integer, String> aPhoneNumberCollection, HashMap<String, String> anEmailCollection, String aContactID){
        setDisplayname(aDisplayName);
        setPhoneNumbers(aPhoneNumberCollection);
        setEmails(anEmailCollection);
        setContactID(aContactID);
    }

    public void setParentActivity(Context parentActivity) {
        ParentActivity = parentActivity;
    }

    public void setEmails(HashMap<String, String> emails) {
        this.emails = emails;
    }

    public void setDisplayPicture(Bitmap adisplayPicture) {
        if (displayPicture != null){
            this.displayPicture = adisplayPicture;
        }
        else {
            this.displayPicture = BitmapFactory.decodeResource(ParentActivity.getResources(), R.drawable.dp);
        }
    }

    public Bitmap getDisplayPicture() {
        return displayPicture;
    }

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }


    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public void setPhoneNumbers(HashMap<Integer, String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public HashMap<String, String> getEmails() {
        return emails;
    }

    public String getDisplayname() {
        return displayname;
    }

    public HashMap<Integer, String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public String getContactID() {
        return contactID;
    }

    public Context getParentActivity() {
        return ParentActivity;
    }
}
