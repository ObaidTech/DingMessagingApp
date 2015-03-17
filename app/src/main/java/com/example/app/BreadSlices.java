package com.example.app;

/**
 * Created by OBNinja on 11/9/14.
 */
public class BreadSlices {
    int imageId;
    String label;
    String notification;
    boolean selected;

    public BreadSlices(int aPictureResourceId, String aLabel){
        setImageId(aPictureResourceId);
        setLabel(aLabel);
        selected = false;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getNotification() {
        return notification;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
