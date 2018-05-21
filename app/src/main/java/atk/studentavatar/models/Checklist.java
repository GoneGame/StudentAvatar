package atk.studentavatar.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Checklist {

    public String uid;
    public String task;
    public String description;
    public String body;
    public String link;

    public Checklist() {
        // Default constructor required for calls to DataSnapshot.getValue(Checklist.class)
    }

    public Checklist(String uid, String task, String description, String body, String link) {
        this.uid = uid;
        this.task = task;
        this.description = description;
        this.body = body;
        this.link = link;
    }
}
