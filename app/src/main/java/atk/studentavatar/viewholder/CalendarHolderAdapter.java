package atk.studentavatar.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import atk.studentavatar.R;
import atk.studentavatar.models.General;

//this adapter is for calendarListFragment, after a date is selected

public class CalendarHolderAdapter extends RecyclerView.Adapter<CalendarHolderAdapter.CalendarViewHolder> {

    private Context context;
    //probably a list of events happening on that day

    //put context and information about date
    public CalendarHolderAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CalendarHolderAdapter.CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_list_items, parent, false);
        return null;
    }


    //set values of card view here
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        int poss = position;

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    //link the view here, find view stuff
    public class CalendarViewHolder extends RecyclerView.ViewHolder
    {

        public CalendarViewHolder(View itemView) {
            super(itemView);
        }
    }
}
