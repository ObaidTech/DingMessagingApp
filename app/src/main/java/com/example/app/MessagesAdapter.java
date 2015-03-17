package com.example.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by OBNinja on 11/9/14.
 */
public class MessagesAdapter extends BaseAdapter {

    Context Parent;
    ArrayList<Sms> messageList;

    public MessagesAdapter(ArrayList<Sms> aMessageList, Context aParent){
        setMessageList(aMessageList);
        setParent(aParent);
        insertDays(messageList);
    }

    public void insertDays(ArrayList<Sms> messageList){
        ArrayList<String> dateDividers = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("d MMMM, y");
        for(int position = 0; position < messageList.size(); position++){
            Sms aSms = messageList.get(position);
            calendar.setTimeInMillis(Long.parseLong(aSms.getTimeInMilliseconds()));
            if (!dateDividers.contains(formatter.format(calendar.getTime()))){
                dateDividers.add(formatter.format(calendar.getTime()));
                messageList.add(position,new Sms(formatter.format(calendar.getTime())));
            }
        }
    }

    public void setParent(Context parent) {
        Parent = parent;
    }

    public void setMessageList(ArrayList<Sms> messageList) {
        this.messageList = messageList;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int i) {
        return messageList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) Parent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v;
        if(messageList.get(i).getId() == "date"){
            v = inflater.inflate(R.layout.date_divider_layout,null);
            TextView dateView = (TextView) v.findViewById(R.id.date_divider_text);
            dateView.setText(messageList.get(i).get_date());
        }
        else {
            if (messageList.get(i).getFolderName().equals("sent")){
                v = inflater.inflate(R.layout.my_new_bubble_layout,null);
            }
            else {
                v = inflater.inflate(R.layout.other_new_bubble_layout,null);
            }
            TextView messageView = (TextView) v.findViewById(R.id.message_text);
            messageView.setText(messageList.get(i).getMsg());
            TextView dateView = (TextView) v.findViewById(R.id.sent_on);
            dateView.setText(messageList.get(i).getFormattedTime());
        }
        return v;
    }
}
