package com.example.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by OBNinja on 11/9/14.
 */
public class ConversationAdapter extends BaseAdapter {

    Context Parent;
    ArrayList<Conversation> conversationlist;

    public ConversationAdapter(ArrayList<Conversation> aConversationList, Context aParent){
        setConversationlist(aConversationList);
        setParent(aParent);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) Parent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(i == 0){
            View v = inflater.inflate(R.layout.header_layout,null);
            return v;
        }else {
            i = i-1;
            View v = inflater.inflate(R.layout.dash_list_layout,null);
            TextView userNameView = (TextView) v.findViewById(R.id.user_name);
            if(!conversationlist.get(i).getName().equals("?")){
                userNameView.setText(conversationlist.get(i).getName());
            }
            else {
                userNameView.setText(conversationlist.get(i).getNumber());
            }
            TextView messageView = (TextView) v.findViewById(R.id.user_message);
            messageView.setText(conversationlist.get(i).getSmsCollection().get(0).getMsg());
            TextView dateView = (TextView) v.findViewById(R.id.message_date);
            dateView.setText(conversationlist.get(i).getSmsCollection().get(0).getFormattedTime());
            return v;
        }
    }

    public void setConversationlist(ArrayList<Conversation> conversationlist) {
        this.conversationlist = conversationlist;
        this.notifyDataSetChanged();
    }

    public ArrayList<Conversation> getConversationlist() {
        return conversationlist;
    }

    public void setParent(Context parent) {
        Parent = parent;
    }

    @Override
    public int getCount() {
        return conversationlist.size()+1;
    }

    @Override
    public Object getItem(int i) {
        return conversationlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

}
