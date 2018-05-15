package atk.studentavatar.models;

public class EventClub extends Event {
    private String title;
    private String date;
    private String time;
    private String description;

    private String location;
    private String name;

    public EventClub() {
        this.title = "";
        this.name = "";
    }

    public EventClub(String title, String date, String time, String description, String location, String name) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.description = description;
        this.location = location;
        this.name = name;
    }

    public EventClub(String title) {
        this.title = title;
    }

    public void clearValues()
    {
        this.title = "";
        this.date = "";
        this.time = "";
        this.description = "";
        this.location = "";
        this.name = "";
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

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
