package atk.studentavatar.models;

import java.util.Map;

public class Event {

    public String uid;
    public String title;
    public String relateTo;
    public Map<String, Boolean> date;
    public String datealt;
    public String time;
    public String timenote;
    public String desc;
    public String location;
    public String guiderel;
    public boolean note;

    public Event() {
    }

    public Event(String title, String relateTo, Map<String, Boolean> date, String datealt, String time, String timenote, String desc, String location, String guiderel, boolean note) {
        this.title = title;
        this.relateTo = relateTo;
        this.date = date;
        this.datealt = datealt;
        this.time = time;
        this.timenote = timenote;
        this.desc = desc;
        this.location = location;
        this.guiderel = guiderel;
        this.note = note;
    }
}