package atk.studentavatar;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

public class NotificationJobCreator implements JobCreator {
    @Nullable
    @Override
    public Job create(@NonNull String tag) {

        Log.d("job","in Job create");
        if(tag.equals(MakeNotificationJob.NOTIFICATION_JOB_CREATOR_TAG1))
        {
            Log.d("job","got tag");
            return new MakeNotificationJob();
        }
        else if(tag.equals(MakeNotificationJob.NOTIFICATION_JOB_CREATOR_TAG2))
        {
            return new MakeNotificationJob();
        }
        else
        {
            Log.d("job","no taog");
            return null;
        }
    }
}
