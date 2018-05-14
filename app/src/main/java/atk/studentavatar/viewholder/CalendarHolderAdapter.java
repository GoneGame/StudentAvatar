package atk.studentavatar.viewholder;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import atk.studentavatar.R;
import atk.studentavatar.models.Event;
import atk.studentavatar.models.EventClub;

//this adapter is for calendarCardViewActivity, after a date is selected

public class CalendarHolderAdapter extends RecyclerView.Adapter<CalendarHolderAdapter.CalendarViewHolder> {

    private Context context;
    private List<Event> events;
    //probably a list of events happening on that day

    //put context and information about date
    public CalendarHolderAdapter(Context context, List<Event> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public CalendarHolderAdapter.CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_card_item, parent, false);
        return new CalendarViewHolder(view);
    }


    //set values of card view here
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {

        holder.txt_title.setText(events.get(position).getTitle());
        /*
        String s;

        //s = Resources.getSystem().getString(R.string.titleText, )

        if(events.get(position).getName().isEmpty() || events.get(position).getName().contentEquals(""))
        {
            s = Resources.getSystem().getString(R.string.titleText, events.get(position).getTitle());
            holder.txt_title.setText(s);
        }
        else
        {
            s = Resources.getSystem().getString(R.string.titleText, events.get(position).getTitle().concat("\nunder(" + events.get(position).getName() + ")"));
            holder.txt_title.setText(s);
        }

        s = Resources.getSystem().getString(R.string.locationText, events.get(position).getLocation());
        holder.txt_location.setText(s);

        s = Resources.getSystem().getString(R.string.timeText, events.get(position).getTime());
        holder.txt_time.setText(s);*/
    }

    @Override
    public int getItemCount() {
        return events.size();
    }


    //link the view here, find view stuff
    public class CalendarViewHolder extends RecyclerView.ViewHolder
    {
        public TextView txt_title, txt_location, txt_time;
        public CardView cardView;

        public CalendarViewHolder(View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.TextView_title);
            txt_location = itemView.findViewById(R.id.TextView_location);
            txt_time = itemView.findViewById(R.id.TextView_time);
            cardView = itemView.findViewById(R.id.card_item);
        }
    }
}
