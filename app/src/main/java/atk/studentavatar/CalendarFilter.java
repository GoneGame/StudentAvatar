package atk.studentavatar;

import java.util.ArrayList;

public class CalendarFilter {

    public boolean event;
    public boolean unit;
    public boolean club;

    public CalendarFilter(boolean event, boolean unit, boolean club) {
        this.event = event;
        this.unit = unit;
        this.club = club;
    }

    public CalendarFilter() {
        this.event = true;
        this.unit = false;
        this.club = false;
    }

    public boolean complexCompare()
    {
        return true;
    }
}
