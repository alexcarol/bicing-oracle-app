package net.alexcarol.bicingoracle;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapPredictionInfoActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_prediction_info);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final Parcelable[] parcelablePredictions = getIntent().getParcelableArrayExtra("stationPredictions");
        LatLng lastLocation = new LatLng(41.387148, 2.170122);;
        for (Parcelable p : parcelablePredictions) {
            StationPrediction s = (StationPrediction) p;
            final BitmapDescriptor icon = getIcon(s.bikes, s.freeslots);
            mMap.addMarker(new MarkerOptions()
                    .position(s.latLng)
                    .title(s.address)
                    .snippet("Bikes: " + s.bikes + " slots: " + s.freeslots)
                    .icon(icon)
            );
            lastLocation = s.latLng;
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLocation, 15));
    }

    @NonNull
    private BitmapDescriptor getIcon(int bikes, int freeslots) {

        return BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_AZURE * bikes/ (bikes + freeslots)
        );
    }
}
