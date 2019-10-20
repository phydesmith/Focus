package io.csc440.focus;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task implements Serializable {

    String name = "";
    Boolean priority = false;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
    String date = "";

    public Task(String name, Boolean priority, String date){
        this.name = name;
        this.priority = priority;
        this.date = sdf.format(date);
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

    public Boolean getPriority() {
        return priority;
    }

    public void setPriority(Boolean priority) {
        this.priority = priority;
    }
}
