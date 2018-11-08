package atk.studentavatar.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Event implements Serializable{

    public String uid;
    public String title;
    public String relateTo;
    public List<String> date;
    public String time;
    public String desc;
    public String location;
    public boolean note;

    public Event() {
    }

    public Event(String title, String relateTo, List<String> date, String time, String desc, String location, boolean note) {
        this.title = title;
        this.relateTo = relateTo;
        this.date = date;
        this.time = time;
        this.desc = desc;
        this.location = location;
        this.note = note;
    }
}