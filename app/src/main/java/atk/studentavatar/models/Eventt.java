package atk.studentavatar.models;

import java.util.Map;

public class Eventt {

    public String uid;
    public String title;
    public String relateTo;
    public Map<String, Boolean> date;
    public String time;
    public String description;
    public boolean note;

    public Eventt() {
    }

    public Eventt(String title, String relateTo, Map<String, Boolean> date, String time, String description, boolean note) {
        this.title = title;
        this.relateTo = relateTo;
        this.date = date;
        this.time = time;
        this.description = description;
        this.note = note;
    }
}