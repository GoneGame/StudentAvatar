package atk.studentavatar.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import atk.studentavatar.R;
import atk.studentavatar.models.General;

public class CalendarViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView locationView;
    public TextView descriptionView;

    public CalendarViewHolder(View itemView) {
        super(itemView);

        titleView = itemView.findViewById(R.id.general_title);
        locationView = itemView.findViewById(R.id.general_location);
        descriptionView = itemView.findViewById(R.id.general_description);
    }

    public void bindToGeneral(General general) {
        titleView.setText(general.title);
//        titleView.setText(general.location);
//        titleView.setText(general.description);
    }
}
