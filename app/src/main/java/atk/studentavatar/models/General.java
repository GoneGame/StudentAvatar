package atk.studentavatar.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class General {

    public String uid;
    public String header;
    public String title;
    public String location;
    public String description;
    public String name;
    public String date;

    public General() {
        // Default constructor required for calls to DataSnapshot.getValue(General.class)
    }

    public General(String uid, String header, String title, String location, String description, String name, String date) {
        this.uid = uid;
        this.header = header;
        this.title = title;
        this.location = location;
        this.description = description;
        this.name = name;
        this.date= date;
    }
}
