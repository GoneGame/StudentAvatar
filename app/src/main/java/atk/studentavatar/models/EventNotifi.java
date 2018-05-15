package atk.studentavatar.models;

public class EventNotifi extends Event {

    private String title;
    private String date;
    private String time;
    private String description;

    public EventNotifi() {
        this.title = "";
    }

    public EventNotifi(String title, String date, String time, String description) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.description = description;
    }

    public EventNotifi(String title) {
        this.title = title;
    }

    public void clearValues()
    {
        this.title = "";
        this.date = "";
        this.time = "";
        this.description = "";
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String getTime() {
        return time;
    }

    @Override
    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}
