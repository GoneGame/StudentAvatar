package atk.studentavatar.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import atk.studentavatar.R;
import atk.studentavatar.models.General;

public class CalendarViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView dateView;

    public CalendarViewHolder(View itemView) {
        super(itemView);

        titleView = itemView.findViewById(R.id.guide_title);
        dateView = itemView.findViewById(R.id.guide_date);
    }

    public void bindToGeneral(General general) {
        titleView.setText(general.name);
        dateView.setText(general.date);
    }
}
