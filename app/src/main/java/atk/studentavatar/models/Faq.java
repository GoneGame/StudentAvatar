package atk.studentavatar.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Faq {

    public String uid;
    public String question;
    public String answer;

    public Faq() {
        // Default constructor required for calls to DataSnapshot.getValue(Faq.class)
    }

    public Faq(String uid, String question, String answer) {
        this.uid = uid;
        this.question = question;
        this.answer = answer;
    }
}
