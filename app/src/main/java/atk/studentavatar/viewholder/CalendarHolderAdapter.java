package atk.studentavatar.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import atk.studentavatar.R;
import atk.studentavatar.models.General;

public class CalendarHolderAdapter extends RecyclerView.Adapter<CalendarHolderAdapter.CalendarViewHolder> {

    //put context and information about date
    public CalendarHolderAdapter() {
    }

    @NonNull
    @Override
    public CalendarHolderAdapter.CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder
    {

        public CalendarViewHolder(View itemView) {
            super(itemView);
        }
    }
}
