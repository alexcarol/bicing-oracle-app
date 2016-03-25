package net.alexcarol.bicingoracle;

import com.google.android.gms.maps.model.LatLng;

class StationState {
    public final String address;
    public final int freeslots;
    public final int bikes;
    public final LatLng latLng;

    public StationState(String Address, int freeslots, int bikes, LatLng latLng) {
        address = Address;
        this.freeslots = freeslots;
        this.bikes = bikes;
        this.latLng = latLng;
    }
}
