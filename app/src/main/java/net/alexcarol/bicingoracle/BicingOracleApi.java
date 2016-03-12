package net.alexcarol.bicingoracle;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;

class BicingOracleApi {
    public StationState[] getBicingData(long timestamp, LatLng position) {
        return new StationState[]{
                new StationState("Address 1", 1, 5, new LatLng(12, 13)),
                new StationState("Address 2", 2, 3, new LatLng(12, 13)),
        };
    }
}
