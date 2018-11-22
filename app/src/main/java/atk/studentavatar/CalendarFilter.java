package atk.studentavatar;

import java.util.ArrayList;

public class CalendarFilter {

    public boolean general;
    public boolean unit;
    public boolean club;

    public ArrayList<String> idList;

    public String lel;

    public CalendarFilter(boolean general, boolean unit, boolean club, ArrayList<String> idList, String lel) {
        this.general = general;
        this.unit = unit;
        this.club = club;
        this.idList = idList;
        this.lel = lel;
    }

    public CalendarFilter() {
        this.general = true;
        this.unit = false;
        this.club = false;
        this.idList = new ArrayList<>();
        this.lel = "";
    }

    public boolean eventFilter(String s)
    {
        String[] a = {"gene", "unit", "club"};
        boolean[] ref = {this.general, this.unit, this.club};

        //if received is true then if btn on,
        for(int i = 0; i < a.length; i++)
        {
            if(a[i].equals(s))
            {
                return ref[i];
            }
        }

        return true;
    }
}