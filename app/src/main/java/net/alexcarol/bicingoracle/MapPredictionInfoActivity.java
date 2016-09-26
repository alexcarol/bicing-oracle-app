package net.alexcarol.bicingoracle;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapPredictionInfoActivity extends FragmentActivity implements OnMapReadyCallback {
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
        String output = "";

        final Parcelable[] parcelablePredictions = getIntent().getParcelableArrayExtra("stationPredictions");
        LatLng lastLocation = new LatLng(41.387148, 2.170122);

        for (Parcelable p : parcelablePredictions) {
            StationPrediction s = (StationPrediction) p;
            final BitmapDescriptor icon = getIcon(s.bikeProbability, s.failure);
            String text;
            if (s.failure) {
                text = "Probabilitat: desconeguda";
            } else {
                text = "Probabilitat: " + ((int) (s.bikeProbability * 100)) + "%";
            }
            googleMap.addMarker(new MarkerOptions()
                            .position(s.latLng)
                            .title(s.address)
                            .snippet(text)
                            .icon(icon)
            );
            lastLocation = s.latLng;

            if (s.failure) {
                if (output.equals("")) {
                    output = "" + s.stationID;
                } else {
                    output += ", " + s.stationID;
                }
            }
        }
        if (!output.equals("")) {
            Toast.makeText(
                    this,
                    "Error per les estacions " + output + ", prova d'aquí una estona",
                    Toast.LENGTH_SHORT
            ).show();
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLocation, 15));
    }

    @NonNull
    private BitmapDescriptor getIcon(double bikeProbability, boolean failure) {
        if (failure) {
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
        }

        return BitmapDescriptorFactory.defaultMarker((float) (BitmapDescriptorFactory.HUE_AZURE * bikeProbability));
    }
}
