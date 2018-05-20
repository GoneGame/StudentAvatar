package atk.studentavatar.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START general_class]
@IgnoreExtraProperties
public class General {

    public String uid;
    public String header;
    public String title;
    public String location;
    public String description;
    public String name;

    public General() {
        // Default constructor required for calls to DataSnapshot.getValue(General.class)
    }

    public General(String uid, String header, String title, String location, String description, String name) {
        this.uid = uid;
        this.header = header;
        this.title = title;
        this.location = location;
        this.description = description;
        this.name = name;
    }
}
// [END general_class]
