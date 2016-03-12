package net.alexcarol.bicingoracle;

import com.google.android.gms.maps.model.LatLng;

class StationState {
    public String address;
    public int freeslots;
    public int bikes;
    public LatLng latLng;

    public StationState(String Address, int freeslots, int bikes, LatLng latLng) {
        address = Address;
        this.freeslots = freeslots;
        this.bikes = bikes;
        this.latLng = latLng;
    }
}
