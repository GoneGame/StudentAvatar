package atk.studentavatar.viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import atk.studentavatar.R;
import atk.studentavatar.models.Club;
import atk.studentavatar.models.Event;
import atk.studentavatar.models.Unit;

public class CalendarViewHolder extends RecyclerView.ViewHolder {

    public TextView txt_title, txt_time, txt_relateTo, txt_location, txt_desc, txt_note;
    public Button btn_guiderel;

    public CardView cardView;

    public CalendarViewHolder(View itemView) {
        super(itemView);

        txt_title = itemView.findViewById(R.id.TextView_title);
        txt_relateTo = itemView.findViewById(R.id.TextView_relateTo);
        txt_time = itemView.findViewById(R.id.TextView_time);
        txt_location = itemView.findViewById(R.id.TextView_location);
        txt_desc = itemView.findViewById(R.id.TextView_desc);
        btn_guiderel = itemView.findViewById(R.id.Button_gotoGuide);

        cardView = itemView.findViewById(R.id.card_item);
    }

    public void bindToCalendar(Event event) {
        txt_title.setText(event.title);
        getFullRelate(event.relateTo);
        txt_time.setText(event.time);
        txt_location.setText(event.location);
        txt_desc.setText(event.desc);
    }

    public void getFullRelate(String rel)
    {
        String cate = rel.substring(0, 4);
        if(!cate.equals("gene"))
        {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            Query query;

            query = ref.child(cate).child(rel.substring(5));

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(cate.equals("unit"))
                    {
                        Unit unit = dataSnapshot.getValue(Unit.class);
                        txt_relateTo.setText(unit.code + " " + unit.name);
                    }
                    else if(cate.equals("club"))
                    {
                        Club club = dataSnapshot.getValue(Club.class);
                        txt_relateTo.setText(club.name);
                    }
                    else
                    {
                        txt_relateTo.setText("unknown");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    txt_relateTo.setText("unknown");
                }
            });
        }
        else
        {
            txt_relateTo.setText("general");
        }
    }
}
