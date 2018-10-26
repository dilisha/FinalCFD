//class for database object

package com.example.resourcetrack1;

import android.location.Location;

public class CampDetails {

    //latitude + longitude + address
    @com.google.gson.annotations.SerializedName("Location")
    private String location;

    //contact number
    @com.google.gson.annotations.SerializedName("PhoneNo")
    private Number phone;

    //id
    @com.google.gson.annotations.SerializedName("id")
    private String cid;

    @com.google.gson.annotations.SerializedName("complete")
    private boolean mComplete;


    public CampDetails(){

    }

    public CampDetails(String location , Number phone , String id) {

        this.setLocation(location);
        this.setPhno(phone);
        this.setId(id);
    }

    //get and set functions


    public String getLocation(){ return location;}

    public final void setLocation(String l){

        location = l;
    }

    public Number getPhno(){ return phone ;}

    public final void setPhno(Number phno){

        phone = phno;
    }

    public String getId(){return cid;}

    public final void setId(String id){
        cid = id;
    }

    /**
     * Indicates if the item is marked as completed
     */
    public boolean isComplete() {
        return mComplete;
    }

    /**
     * Marks the item as completed or incompleted
     */
    public void setComplete(boolean complete) {
        mComplete = complete;
    }



    @Override
    public boolean equals(Object o) {
        return o instanceof CampDetails && ((CampDetails) o).cid == cid;
    }

}
