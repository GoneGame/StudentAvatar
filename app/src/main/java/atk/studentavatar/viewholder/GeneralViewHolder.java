package atk.studentavatar.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import atk.studentavatar.R;
import atk.studentavatar.models.General;

public class GeneralViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;

    public GeneralViewHolder(View itemView) {
        super(itemView);

        titleView = itemView.findViewById(R.id.guide_title);
    }

    public void bindToGeneral(General general) {
        titleView.setText(general.name);
    }
}
