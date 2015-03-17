package com.example.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MessagesActivity extends Activity {

    ListView messagesListView;
    ArrayList<Sms> Smses;
    MessagesAdapter convoAdapter;
    String SenderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.conversation_layout);
        messagesListView = (ListView) findViewById(R.id.conversation_list_view);
        Intent whyWasICalled = getIntent();
        Smses = whyWasICalled.getParcelableArrayListExtra("messages");
        SenderName = whyWasICalled.getStringExtra("SenderName");
        // Toast.makeText(getApplicationContext(),Smses.toString(),Toast.LENGTH_SHORT).show();
        Collections.reverse(Smses);
        convoAdapter = new MessagesAdapter(Smses,this);
        messagesListView.setAdapter(convoAdapter);
        convoAdapter.notifyDataSetChanged();
        messagesListView.setSelection(convoAdapter.getCount()-1);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        if(!SenderName.equals("?")){
            actionBar.setTitle(SenderName);
        }
        else {
            actionBar.setTitle(Smses.get(0).getAddress());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void readContacts()
    {
        ArrayList<Contact> Contactslist;
        ContentResolver cr = this.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        String phone = null;
        int phoneType = 0;
        String emailContact = null;
        String emailType = null;
        String image_uri = "";
        Bitmap bitmap = null;
        if (cur.getCount() >0)
        {
            Contactslist = new ArrayList<Contact>();
            while (cur.moveToNext())
            {
                Contact aPerson = new Contact(this);
                String id = cur.getString(cur .getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                image_uri = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                {
                    aPerson.setDisplayname(name);
                    aPerson.setContactID(id);
                    Cursor pCur = cr.query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] {id}, null);
                    HashMap<Integer, String> phoneNumberCollection = new HashMap<Integer, String>();
                    while (pCur.moveToNext())
                    {
                        phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phoneType = pCur.getInt(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                        phoneNumberCollection.put(phoneType,phone);
                    }
                    aPerson.setPhoneNumbers(phoneNumberCollection);
                    pCur.close();
                    Cursor emailCur = cr.query( ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[] {id }, null);
                    HashMap<String, String> emailCollection = new HashMap<String, String>();
                    while (emailCur.moveToNext())
                    {
                        emailContact = emailCur.getString(emailCur .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        emailType = emailCur.getString(emailCur .getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                        emailCollection.put(emailType, emailContact);
                    }
                    aPerson.setEmails(emailCollection);
                    emailCur.close();
                }
                if (image_uri != null)
                {
                    try
                    {
                        bitmap = MediaStore.Images.Media .getBitmap(getContentResolver(), Uri.parse(image_uri));
                        aPerson.setDisplayPicture(bitmap);
                    }
                    catch (FileNotFoundException e)
                    {
                        aPerson.setDisplayPicture(null);
                    }
                    catch (IOException e)
                    {
                        aPerson.setDisplayPicture(null);
                    }
                }
                Contactslist.add(aPerson);
            }
            // textDetail.setText(sb);
            // CONTACT LIST IS GENERATED

        }
    }

}
