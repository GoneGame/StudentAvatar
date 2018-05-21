package atk.studentavatar.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import atk.studentavatar.R;
import atk.studentavatar.models.Faq;

public class FaqViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;

    public FaqViewHolder(View itemView) {
        super(itemView);

        titleView = itemView.findViewById(R.id.faq_title);
    }

    public void bindToFaq(Faq faq) {
        titleView.setText(faq.question);
    }
}
