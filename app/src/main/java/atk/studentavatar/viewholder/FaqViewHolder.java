package atk.studentavatar.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import atk.studentavatar.R;
import atk.studentavatar.models.Faq;

public class FaqViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
//    public TextView locationView;
//    public TextView descriptionView;

    public FaqViewHolder(View itemView) {
        super(itemView);

        titleView = itemView.findViewById(R.id.faq_title);
//        locationView = itemView.findViewById(R.id.general_location);
//        descriptionView = itemView.findViewById(R.id.general_description);
    }

    public void bindToFaq(Faq faq) {
        titleView.setText(faq.question);
//        titleView.setText(general.location);
//        titleView.setText(general.description);
    }
}
