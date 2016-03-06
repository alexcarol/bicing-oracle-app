package net.alexcarol.bicingoracle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class PredictionInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction_info);

        final Intent intent = getIntent();
        LatLng chosenPosition = (LatLng) intent.getParcelableExtra("latLng");
        int year, month, day, hour, minute;
        year = intent.getIntExtra("year", -2);
        month = intent.getIntExtra("month", -2);
        day = intent.getIntExtra("day", -2);
        hour = intent.getIntExtra("hour", -2);
        minute = intent.getIntExtra("minute", -2);


        String message;
        if (chosenPosition != null) {
            message = "position: " + chosenPosition.toString();
        } else {
            message = "No lat and longitude provided";
        }

        StationState[] states = getBicingData(year, month, day, hour, minute, chosenPosition);

        String output = "";
        for (StationState s:states) {
            output += "Address: " + s.address +
                    "\nBikes Available: " + s.bikes +
                    "\nSlots Available: " + s.freeslots +
                    "\nPosition: " + s.latLng.toString() +
                    "\n==================\n";
        }

        final TextView t = (TextView) findViewById(R.id.activity_prediction_info_text_view);
        t.setText(output);

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private StationState[] getBicingData(int year, int month, int day, int hour, int minute, LatLng chosenPosition) {
        return new StationState[]{
                new StationState("Address 1", 1, 5, new LatLng(12, 13)),
                new StationState("Address 2", 2, 3, new LatLng(12, 13)),
        };
    }
}

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
