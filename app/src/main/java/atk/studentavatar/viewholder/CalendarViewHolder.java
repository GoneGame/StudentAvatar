package atk.studentavatar.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import atk.studentavatar.R;
import atk.studentavatar.models.Event;
import atk.studentavatar.models.General;

public class CalendarViewHolder extends RecyclerView.ViewHolder {

    public TextView txt_title, txt_time, txt_relateTo, txt_location, txt_desc, txt_note;

    public CalendarViewHolder(View itemView) {
        super(itemView);

        //titleView = itemView.findViewById(R.id.guide_title);
        //dateView = itemView.findViewById(R.id.guide_date);
        txt_title = itemView.findViewById(R.id.TextView_title);

    }

    public void bindToCalendar(Event event) {
        //titleView.setText(general.name);
        //dateView.setText(general.date);
        txt_title.setText(event.title);
    }
}
