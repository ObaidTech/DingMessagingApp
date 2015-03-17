package com.example.app;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by OBNinja on 11/9/14.
 */
public class BreadAdapter extends BaseAdapter {
    ArrayList<BreadSlices> MenuList;
    Context myParent;

    BreadAdapter(Context aParent, ArrayList<BreadSlices> aPassedMenuList){
        myParent = aParent;
        MenuList = aPassedMenuList;
    }

    @Override
    public int getCount() {
        return MenuList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) myParent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.fragment_list_layout,null);
        TextView MenuItemTitle = (TextView) v.findViewById(R.id.fragment_name);
        MenuItemTitle.setText(MenuList.get(i).getLabel());
        if(MenuList.get(i).isSelected()){
            MenuItemTitle.setTypeface(null, Typeface.BOLD);
        }
        ImageView MenuItemPicture = (ImageView) v.findViewById(R.id.fragment_icon);
        MenuItemPicture.setImageResource(MenuList.get(i).getImageId());
        return v;
    }
}
