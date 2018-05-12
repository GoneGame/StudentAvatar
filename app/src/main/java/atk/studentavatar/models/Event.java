package atk.studentavatar.models;

public class Event {
    private String title;
    private String location;
    private String date;
    private String time;
    private String description;
    private int type;

    /*
    *   1-general
    *   2-club
    *   3-unit
    *   4-notification
    * */
    public Event() {
    }

    //for event type 1, 2, 3
    public Event(String title, String location, String date, String time, String description, int type) {
        this.title = title;
        this.location = location;
        this.date = date;
        this.time = time;
        this.description = description;
        this.type = type;
    }

    //for event type 4
    public Event(String title, String date, String time, String description, int type) {
        this.title = title;
        this.location = "";
        this.date = date;
        this.time = time;
        this.description = description;
        this.type = type;
    }

    //to test recycler view
    public Event(String title) {
        this.title = title;
        this.location = "";
        this.date = "";
        this.time = "";
        this.description = "";
        this.type = 1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

