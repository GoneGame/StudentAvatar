package atk.studentavatar.models;

public class EventGeneral extends Event{
    private String title;
    private String date;
    private String time;
    private String description;

    private String location;

    public EventGeneral() {
        this.title = "";
    }

    public EventGeneral(String title, String date, String time, String description, String location) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.description = description;
        this.location = location;
    }

    public EventGeneral(String title)
    {
        this.title = title;
    }

    public void clearValues()
    {
        this.title = "";
        this.date = "";
        this.time = "";
        this.description = "";
        this.location = "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
