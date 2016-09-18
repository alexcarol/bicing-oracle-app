package net.alexcarol.bicingoracle;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

public class ChooseLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng latLng;
    private int year;
    private int month;
    private int dayOfMonth;
    private int hourOfDay;
    private int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        enableCurrentLocation();

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                ChooseLocationActivity.this.latLng = latLng;

                showDatePicker();
            }
        });
    }

    private void showDatePicker() {
        final DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                System.out.print("date:" + year + "-" + monthOfYear + "-" + dayOfMonth);
                ChooseLocationActivity.this.year = year;
                ChooseLocationActivity.this.month = monthOfYear;
                ChooseLocationActivity.this.dayOfMonth = dayOfMonth;

                showTimePicker();
            }
        };

        final Calendar c = Calendar.getInstance();

        final DatePickerDialog datePickerDialog = new DatePickerDialog(
                ChooseLocationActivity.this,
                onDateSetListener,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimePicker() {
        final TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                System.out.print("time:" + hourOfDay + ":" + minute);
                ChooseLocationActivity.this.hourOfDay = hourOfDay;
                ChooseLocationActivity.this.minute = minute;

                Toast.makeText(
                        ChooseLocationActivity.this,
                        "Realitzant la predicció...",
                        Toast.LENGTH_SHORT
                ).show();

                predict();
            }
        };

        final Calendar c = Calendar.getInstance();

        final TimePickerDialog timePickerDialog = new TimePickerDialog(
                ChooseLocationActivity.this,
                onTimeSetListener,
                c.get(Calendar.HOUR_OF_DAY) + 2,
                c.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    private void predict() {
        long timestamp = TimeUtils.getTimestamp(year, month, dayOfMonth, hourOfDay, minute);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.print("prediction: " + response);
                final StationPrediction[] predictions = BicingOracleApiParser.parseStationStates(response);

                final Intent intent = new Intent(ChooseLocationActivity.this, MapPredictionInfoActivity.class);
                intent.putExtra("stationPredictions", predictions);
                startActivity(intent);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO change it on the actual release, users should never see something like this -> also, on Error retry instead
                Toast.makeText(
                        ChooseLocationActivity.this,
                        "Problem getting bicing data : " + error.toString(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        };
        BicingOracleApi.bicingOracleApiRequest(
                timestamp,
                latLng,
                responseListener,
                errorListener,
                ChooseLocationActivity.this
        );
    }

    private void enableCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            defaultCentering();


            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Location location =  mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        LatLng l = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(l, 16));

        // TODO remove, this is only in the meantime
        defaultCentering();
    }

    private void defaultCentering() {
        // Plaça Catalunya
        LatLng barcelona = new LatLng(41.387148, 2.170122);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(barcelona, 12));
    }


}
