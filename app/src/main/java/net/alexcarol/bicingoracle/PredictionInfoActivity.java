package net.alexcarol.bicingoracle;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PredictionInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction_info);

        final Parcelable[] parcelablePredictions = getIntent().getParcelableArrayExtra("stationPredictions");

        String output = "Result: \n";
        for (Parcelable p : parcelablePredictions) {
            StationPrediction s = (StationPrediction) p;
            output += "Address: " + s.address +
                    "\nBikes Available: " + s.bikes +
                    "\nSlots Available: " + s.freeslots +
                    "\nPosition: " + s.latLng.toString() +
                    "\n==================\n";
        }

        final TextView t = (TextView) findViewById(R.id.activity_prediction_info_text_view);
        t.setText(output);
    }
}

