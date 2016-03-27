package net.alexcarol.bicingoracle;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

class StationPrediction implements Parcelable {
    public final String address;
    public final int freeslots;
    public final int bikes;
    public final LatLng latLng;

    public StationPrediction(String Address, int freeslots, int bikes, LatLng latLng) {
        address = Address;
        this.freeslots = freeslots;
        this.bikes = bikes;
        this.latLng = latLng;
    }

    protected StationPrediction(Parcel in) {
        address = in.readString();
        freeslots = in.readInt();
        bikes = in.readInt();
        latLng = in.readParcelable(LatLng.class.getClassLoader());
    }

    public static final Creator<StationPrediction> CREATOR = new Creator<StationPrediction>() {
        @Override
        public StationPrediction createFromParcel(Parcel in) {
            return new StationPrediction(in);
        }

        @Override
        public StationPrediction[] newArray(int size) {
            return new StationPrediction[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeInt(freeslots);
        dest.writeInt(bikes);
        dest.writeParcelable(latLng, flags);
    }
}
