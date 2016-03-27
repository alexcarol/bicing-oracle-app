package net.alexcarol.bicingoracle;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;

public class TimeUtils {
    public static long getTimestamp(int year, int month, int day, int hour, int minute) {
        final SimpleTimeZone timezone = new SimpleTimeZone(1, "Europe/Madrid");
        Calendar c = new GregorianCalendar(timezone);
        c.set(year, month, day, hour, minute);

        final Date dateTime = c.getTime();

        return dateTime.getTime() / 1000;
    }
}
