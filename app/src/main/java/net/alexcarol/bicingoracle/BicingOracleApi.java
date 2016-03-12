package net.alexcarol.bicingoracle;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;

class BicingOracleApi {
    public StationState[] getBicingData(int year, int month, int day, int hour, int minute, LatLng position) {
        final SimpleTimeZone timezone = new SimpleTimeZone(1, "Europe/Madrid");
        Calendar c = new GregorianCalendar(timezone);
        c.set(year, month, day, hour, minute);

        final Date dateTime = c.getTime();
        long timestamp = dateTime.getTime() / 1000;

        return new StationState[]{
                new StationState("Address 1", 1, 5, new LatLng(12, 13)),
                new StationState("Address 2", 2, 3, new LatLng(12, 13)),
        };
    }
}
