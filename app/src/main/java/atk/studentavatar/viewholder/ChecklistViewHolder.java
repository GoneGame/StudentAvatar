package atk.studentavatar.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import atk.studentavatar.R;
import atk.studentavatar.models.Checklist;

public class ChecklistViewHolder extends RecyclerView.ViewHolder {

    public TextView taskView;
    public TextView descriptionView;

    public ChecklistViewHolder(View itemView) {
        super(itemView);

        taskView = itemView.findViewById(R.id.checklist_title);
        descriptionView = itemView.findViewById(R.id.checklist_description);
    }

    public void bindToChecklist(Checklist checklist) {
        taskView.setText(checklist.task);
        descriptionView.setText(checklist.description);
    }
}
