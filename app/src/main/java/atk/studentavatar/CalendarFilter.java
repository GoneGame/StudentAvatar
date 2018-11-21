package atk.studentavatar;

public class CalendarFilter {

    public boolean general;
    public boolean unit;
    public boolean club;

    public CalendarFilter(boolean general, boolean unit, boolean club) {
        this.general = general;
        this.unit = unit;
        this.club = club;
    }

    public CalendarFilter() {
        this.general = true;
        this.unit = false;
        this.club = false;
    }

    public boolean eventTypeFil(String s)
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
