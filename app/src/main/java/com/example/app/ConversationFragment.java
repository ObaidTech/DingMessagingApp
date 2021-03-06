package com.example.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ConversationFragment extends Fragment {

    ConversationAdapter convoAdapter;
    ArrayList<Conversation> convoList;
    ListView conversationList;
    ArrayList<Sms> UnsortedSmses;
    Activity ParentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.dashboard_layout, container, false);
        conversationList = (ListView) view.findViewById(R.id.recent_messages_list);
        UnsortedSmses = new ArrayList<Sms>();
        convoList = new ArrayList<Conversation>();
        if (convoAdapter != null){
            conversationList.setAdapter(convoAdapter);
        }
        else{
            convoAdapter = new ConversationAdapter(convoList, getActivity().getBaseContext());
            conversationList.setAdapter(convoAdapter);
        }
        conversationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // To be changed into Activity
                Conversation aSelectedConvo = (Conversation) conversationList.getItemAtPosition(i);
                ArrayList<Sms> allMessages = aSelectedConvo.getSmsCollection();
                Intent intent = new Intent(getActivity().getBaseContext(), MessagesActivity.class);
                intent.putParcelableArrayListExtra("messages",allMessages);
                intent.putExtra("SenderName",aSelectedConvo.getName());
                startActivity(intent);
            }
        });
        return view;
    }

    public void refreshFragmentProcesses(){
        // Grab Things
        new GrabSmses(ParentActivity).execute();
        new SortSmses(ParentActivity).execute();
    }

    public void setParentActivity(Activity parentActivity) {
        ParentActivity = parentActivity;
    }

    public Activity getParentActivity() {
        return ParentActivity;
    }

    public void setConvoList(ArrayList<Conversation> aconvoList) {
        this.convoList = aconvoList;
    }

    public ConversationAdapter getConvoAdapter() {
        return convoAdapter;
    }

    public void readContacts(Context Parent){
        ContentResolver cr = Parent.getContentResolver(); //Activity/Application android.content.Context
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if(cursor.moveToFirst())
        {
            ArrayList<String> alContacts = new ArrayList<String>();
            do
            {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                if(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",new String[]{ id }, null);
                    while (pCur.moveToNext())
                    {
                        // pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.));
                        String contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        alContacts.add(contactNumber);
                        break;
                    }
                    // pCur.close();
                }

            } while (cursor.moveToNext()) ;
            // Toast.makeText(Parent,alContacts.get(5).toString(),Toast.LENGTH_LONG).show();
        }
    }
    private class SortSmses extends AsyncTask<String, Void, String> {
        Context myParent;

        SortSmses(Context aParent){
            myParent = aParent;
        }

        @Override
        protected String doInBackground(String... strings) {
            ArrayList<String> PhoneNumbers;
            PhoneNumbers = new ArrayList<String>();
            for (Sms anSms : UnsortedSmses){
                if(!PhoneNumbers.contains(anSms.getAddress())){
                    PhoneNumbers.add(anSms.getAddress());
                }
            }
            for (String aNumber : PhoneNumbers){
                if (aNumber != null){
                    ArrayList<Sms> convoSmses = new ArrayList<Sms>();
                    for (Sms anSms : UnsortedSmses){
                        if (anSms != null){
                            try {
                                if(aNumber.equals(anSms.getAddress())){
                                    convoSmses.add(anSms);
                                }
                            }
                            catch (Exception e){

                            }
                        }
                    }
                    Conversation convo = new Conversation(myParent,aNumber,convoSmses);
                    convoList.add(convo);
                }
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("Executed")){
                convoAdapter.notifyDataSetChanged();
            }
        }
    }


    private class GrabSmses extends AsyncTask<String, Void, String> {
        Activity parentActivity;

        public GrabSmses(Activity aParent){
            parentActivity = aParent;
        }
        @Override
        protected String doInBackground(String... params) {
            Sms objSms;
            Uri message = Uri.parse("content://sms/");
            ContentResolver cr = parentActivity.getContentResolver();

            Cursor c = cr.query(message, null, null, null, null);
            parentActivity.startManagingCursor(c);
            int totalSMS = c.getCount();

            if (c.moveToFirst()) {
                for (int i = 0; i < totalSMS; i++) {

                    objSms = new Sms();
                    objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")));
                    objSms.setAddress(c.getString(c
                            .getColumnIndexOrThrow("address")));
                    objSms.setMsg(c.getString(c.getColumnIndexOrThrow("body")));
                    objSms.setReadState(c.getString(c.getColumnIndex("read")));
                    objSms.setTime(c.getString(c.getColumnIndexOrThrow("date")));
                    if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                        objSms.setFolderName("inbox");
                    } else {
                        objSms.setFolderName("sent");
                    }

                    UnsortedSmses.add(objSms);
                    c.moveToNext();
                }
            }
            // c.close();
            return "Executed";
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }



}
