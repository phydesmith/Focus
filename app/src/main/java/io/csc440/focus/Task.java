package io.csc440.focus;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task implements Parcelable {

    String name = "";
    Boolean priority = false;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
    String date = "";
    Date lDate;

    public Task(String name, Boolean priority, String date){
        this.name = name;
        this.date = date;
        this.priority = priority;

    }

    public Task(String name, int priority, Long lDate){
        this.name = name;
        this.date = "";
        this.lDate = new Date(lDate);
        if (priority == 1){
            this.priority = true;
        }


    }

    public Task(){
        name = "";
        date = "";
        priority = false;
    }

    //Necessary boiler plate to avoid error with Parcelable implementation
    public int describeContents(){return 0;}

    //Writing task information to parcel
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(name);
        dest.writeByte((byte) (priority ? 1 : 0));
        dest.writeString(date);
    }

    //Takes in new Parcel to be read and unpacked.
    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>(){
        public Task createFromParcel(Parcel in){
            return new Task(in);
        }
        public Task[] newArray(int size){
            return new Task[size];
        }
    };

    //Reads new parcel for analysis.
    private Task(Parcel in){
        name = in.readString();
        priority = in.readByte() != 0;
        date = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getPriority() { return priority; }

    public void setPriority(Boolean priority) {
        this.priority = priority;
    }

    public String toString(){
        return "name: " + name + " date: " + date + " priority: " + priority;
    }

    public  String lToString(){
        return toString() + " " +  lDate;
    }

}
