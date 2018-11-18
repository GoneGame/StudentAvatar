package atk.studentavatar.viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.flags.impl.DataUtils;

import atk.studentavatar.R;
import atk.studentavatar.models.Event;

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
        txt_relateTo.setText(event.relateTo.substring(0, 4));
        txt_time.setText(event.time);
        txt_location.setText(event.location);
        txt_desc.setText(event.desc);
    }
}
