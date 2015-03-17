package com.example.app;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.ContactsContract;

import java.util.ArrayList;

/**
 * Created by OBNinja on 11/10/14.
 */
public class Conversation {
    String number;
    ArrayList<Sms> smsCollection;
    String name;
    Context aParent;

    public Conversation(Context passedParent,String aNumber, ArrayList<Sms> aSmsCollection){
        aParent = passedParent;
        setNumber(aNumber);
        name = getContactDisplayNameByNumber(aNumber);
        setSmsCollection(aSmsCollection);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setaParent(Context aParent) {
        this.aParent = aParent;
    }

    public Context getaParent() {
        return aParent;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setSmsCollection(ArrayList<Sms> smsCollection) {
        this.smsCollection = smsCollection;
    }

    public ArrayList<Sms> getSmsCollection() {
        return smsCollection;
    }

    public String getNumber() {
        return number;
    }

    public String getContactDisplayNameByNumber(String number) {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
        String name = "?";

        ContentResolver contentResolver = aParent.getContentResolver();
        Cursor contactLookup = contentResolver.query(uri, new String[] {BaseColumns._ID,
                ContactsContract.PhoneLookup.DISPLAY_NAME }, null, null, null);

        try {
            if (contactLookup != null && contactLookup.getCount() > 0) {
                contactLookup.moveToNext();
                name = contactLookup.getString(contactLookup.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                //String contactId = contactLookup.getString(contactLookup.getColumnIndex(BaseColumns._ID));
            }
        } finally {
            if (contactLookup != null) {
                contactLookup.close();
            }
        }

        return name;
    }
}
