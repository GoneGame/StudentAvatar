package atk.studentavatar.models;

public class Eventt {

    public String uid;
    public String title;
    public String relateTo;
    public String date;
    public String time;
    public String description;
    public boolean note;

    public Eventt() {
    }

    public Eventt(String title, String relateTo, String date, String time, String description, boolean note) {
        this.title = title;
        this.relateTo = relateTo;
        this.date = date;
        this.time = time;
        this.description = description;
        this.note = note;
    }
}