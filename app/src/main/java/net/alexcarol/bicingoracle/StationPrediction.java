package net.alexcarol.bicingoracle;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

class StationPrediction implements Parcelable {
    public final int stationID;
    public final String address;
    public final LatLng latLng;
    public final double bikeProbability;
    public final boolean failure;

    public StationPrediction(int stationID, String address, double bikeProbability, boolean failure, LatLng latLng) {
        this.stationID = stationID;
        this.address = address;
        this.bikeProbability = bikeProbability;
        this.failure = failure;
        this.latLng = latLng;
    }

    protected StationPrediction(Parcel in) {
        stationID = in.readInt();
        address = in.readString();
        bikeProbability  = in.readDouble();
        failure = in.readByte() != 0;
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
        dest.writeInt(stationID);
        dest.writeString(address);
        dest.writeDouble(bikeProbability);
        dest.writeByte((byte) (failure ? 1 : 0));
        dest.writeParcelable(latLng, flags);
    }
}
