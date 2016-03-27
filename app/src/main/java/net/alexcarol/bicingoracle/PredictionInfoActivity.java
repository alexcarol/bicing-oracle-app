package net.alexcarol.bicingoracle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.model.LatLng;

public class PredictionInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction_info);

        final Intent intent = getIntent();
        LatLng chosenPosition = intent.getParcelableExtra("latLng");

        long timestamp = intent.getLongExtra("timestamp", -2);

        fillThing(timestamp, chosenPosition);

    }

    private void fillThing(long timestamp, LatLng position) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                final StationState[] states = BicingOracleApiParser.parseStationStates(response);
                String output = "Result: \n";
                for (StationState s : states) {
                    output += "Address: " + s.address +
                            "\nBikes Available: " + s.bikes +
                            "\nSlots Available: " + s.freeslots +
                            "\nPosition: " + s.latLng.toString() +
                            "\n==================\n";
                }

                final TextView t = (TextView) findViewById(R.id.activity_prediction_info_text_view);
                t.setText(output);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO change it on the actual release, users should never see something like this -> also, on Error retry instead
                Toast.makeText(
                        PredictionInfoActivity.this,
                        "Problem getting bicing data : " + error.toString(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        };

        BicingOracleApi.bicingOracleApiRequest(timestamp, position, responseListener, errorListener, this.getBaseContext());
    }
}

