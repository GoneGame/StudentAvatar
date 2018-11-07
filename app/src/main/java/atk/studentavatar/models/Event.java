package atk.studentavatar.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Event implements Serializable{

    public String uid;
    public String title;
    public String relateTo;
    public Map<String, Boolean> date;
    public String time;
    public String desc;
    public String location;
    public boolean note;

    public Event() {
    }

    public Event(String title, String relateTo, Map<String, Boolean> date, String time, String desc, String location,boolean note) {
        this.title = title;
        this.relateTo = relateTo;
        this.date = date;
        this.time = time;
        this.desc = desc;
        this.location = location;
        this.note = note;
    }

    public Event(String uid, String title, String relateTo, Map<String, Boolean> date, String time, String desc, String location,boolean note) {
        this.uid = uid;
        this.title = title;
        this.relateTo = relateTo;
        this.date = date;
        this.time = time;
        this.desc = desc;
        this.location = location;
        this.note = note;
    }

    public void reset()
    {
        this.uid = "";
        this.title = "";
        this.relateTo = "";
        this.date = new HashMap<String, Boolean>();
        this.time = "";
        this.desc = "";
        this.location = "";
        this.note = false;
    }
}