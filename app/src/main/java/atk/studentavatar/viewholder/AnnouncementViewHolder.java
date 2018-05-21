package atk.studentavatar.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import atk.studentavatar.R;
import atk.studentavatar.models.Announcement;

public class AnnouncementViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView authorView;
    public ImageView starView;
    public TextView numStarsView;
    public TextView bodyView;

    public AnnouncementViewHolder(View itemView) {
        super(itemView);

        titleView = itemView.findViewById(R.id.announcement_title);
        authorView = itemView.findViewById(R.id.announcement_author);
        starView = itemView.findViewById(R.id.star);
        numStarsView = itemView.findViewById(R.id.announcement_num_stars);
        bodyView = itemView.findViewById(R.id.announcement_body);
    }

    public void bindToAnnouncement(Announcement announcement, View.OnClickListener starClickListener) {
        titleView.setText(announcement.title);
        authorView.setText(announcement.author);
        numStarsView.setText(String.valueOf(announcement.starCount));
        bodyView.setText(announcement.body);

        starView.setOnClickListener(starClickListener);
    }
}
