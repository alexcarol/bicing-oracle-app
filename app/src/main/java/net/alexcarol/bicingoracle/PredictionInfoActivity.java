package net.alexcarol.bicingoracle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.SimpleTimeZone;

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

        fillThing(year, month, day, hour, minute, chosenPosition);

    }

    private void fillThing(int year, int month, int day, int hour, int minute, LatLng position) {
        long timestamp = getTimestamp(year, month, day, hour, minute);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String host = getHost();
        String url =  "http://"+ host +"/prediction?time="
                + timestamp + "&lat=" + position.latitude + "&lon=" + position.longitude;

        StringRequest stringRequest = new StringRequest(
            Request.Method.GET,
            url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    final StationState[] states = BicingOracleApiParser.parseStationStates(response);
                    String output = "Result: \n";
                    for (StationState s:states) {
                        output += "Address: " + s.address +
                                "\nBikes Available: " + s.bikes +
                                "\nSlots Available: " + s.freeslots +
                                "\nPosition: " + s.latLng.toString() +
                                "\n==================\n";
                    }

                    final TextView t = (TextView) findViewById(R.id.activity_prediction_info_text_view);
                    t.setText(output);                    }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    final TextView t = (TextView) findViewById(R.id.activity_prediction_info_text_view);

                    // TODO change it on the actual release, users should never see something like this -> also, on Error retry instead
                    t.setText("Problem getting bicing data : " + error.toString());
                }
            }
        );

        requestQueue.add(stringRequest);
    }

    private String getHost() {
        String host = "local.docker";

        Properties properties = new Properties();
        try {
            properties.load(getBaseContext().getAssets().open("config.properties"));
            host = properties.getProperty("bicingHost");
        } catch (IOException e) {
            ExceptionLogger.getInstance().log("PredictionInfoActivity", "Error acquiring properties", e);
        }

        return host;
    }

    private long getTimestamp(int year, int month, int day, int hour, int minute) {
        final SimpleTimeZone timezone = new SimpleTimeZone(1, "Europe/Madrid");
        Calendar c = new GregorianCalendar(timezone);
        c.set(year, month, day, hour, minute);

        final Date dateTime = c.getTime();
        return dateTime.getTime() / 1000;
    }
}

