package com.example.app;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Sms implements Parcelable{
    private String _id;
    private String _address;
    private String _msg;
    private String _readState; //"0" for have not read sms and "1" for have read sms
    private String _time;
    private String _folderName;
    private String _date;

    public Sms(String aID, String aAddress, String aMsg, String aReadState, String aTime, String aFolderName){
        setId(aID);
        setAddress(aAddress);
        setMsg(aMsg);
        setReadState(aReadState);
        setTime(aTime);
        setFolderName(aFolderName);
    }

    public Sms( String aDate){
        setId("date");
        set_date(aDate);
    }

    public Sms(){}

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public String getId(){
        return _id;
    }
    public String getAddress(){
        return _address;
    }
    public String getMsg(){
        return _msg;
    }
    public String getReadState(){
        return _readState;
    }
    public String getTimeInMilliseconds(){
        return _time;
    }
    public String getFormattedTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
        calendar.setTimeInMillis(Long.parseLong(_time));
        return formatter.format(calendar.getTime());
    }
    public String getFolderName(){
        return _folderName;
    }


    public void setId(String id){
        _id = id;
    }
    public void setAddress(String address){
        _address = address;
    }
    public void setMsg(String msg){
        _msg = msg;
    }
    public void setReadState(String readState){
        _readState = readState;
    }
    public void setTime(String time){
        _time = time;
    }
    public void setFolderName(String folderName){
        _folderName = folderName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(_address);
        parcel.writeString(_msg);
        parcel.writeString(_readState);
        parcel.writeString(_time);
        parcel.writeString(_folderName);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Sms createFromParcel(Parcel parcel) {
            return new Sms(parcel.readString(),parcel.readString(),parcel.readString(),parcel.readString(),parcel.readString(),parcel.readString());
        }

        @Override
        public Sms[] newArray(int i) {
            return new Sms[i];
        }
    };
}