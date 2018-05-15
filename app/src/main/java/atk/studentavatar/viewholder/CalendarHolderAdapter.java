package atk.studentavatar.viewholder;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

        String title, subtitle, location, time, locationCheck;

        title = events.get(position).getTitle();
        subtitle = events.get(position).getName();
        locationCheck = events.get(position).getLocation();
        location = "Location: " + locationCheck;
        time = "Time: " + events.get(position).getTime();

        holder.txt_title.setText(title);
        /*
        if(subtitle != null)
        {
            Log.d("Within Holder adapter", subtitle);
            Log.d("Within Holder adapter", location);
            Log.d("Within Holder adapter", time);
        }*/

        if(title != null && !title.equals(""))
        {
            holder.txt_title.setText(title);

            if(locationCheck != null)
            {
                holder.txt_location.setText(location);
            }
            else
            {
                holder.txt_location.setVisibility(View.GONE);
            }

            holder.txt_time.setText(time);

            if(subtitle != null && !subtitle.equals(""))
            {
                holder.txt_sub.setText(subtitle);
            }
            else
            {
                holder.txt_sub.setText("");
                holder.txt_sub.setVisibility(View.INVISIBLE);
            }
        }
        else
        {
            holder.cardView.setVisibility(View.GONE);
        }

        //String s = Resources.getSystem().getString(R.string.titleText, ...)
    }

    @Override
    public int getItemCount() {
        return events.size();
    }


    //link the view here, find view stuff
    public class CalendarViewHolder extends RecyclerView.ViewHolder
    {
        public TextView txt_title, txt_location, txt_time, txt_sub;
        public CardView cardView;

        public CalendarViewHolder(View itemView) {
            super(itemView);

            txt_title = itemView.findViewById(R.id.TextView_title);
            txt_sub = itemView.findViewById(R.id.TextView_subtitle);
            txt_location = itemView.findViewById(R.id.TextView_location);
            txt_time = itemView.findViewById(R.id.TextView_time);
            cardView = itemView.findViewById(R.id.card_item);
        }
    }
}
