package net.alexcarol.bicingoracle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class StationInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_info);


        Intent intent = this.getIntent();
        String stationId = intent.getStringExtra("stationId");
        int hour= intent.getIntExtra("hour", -2);
        int minute = intent.getIntExtra("minute", -3);

        TextView textView = (TextView) findViewById(R.id.data);
        textView.setText("Id: " + stationId +"\nBicis: 1\nLlocs lliures: 3\nHora: " + hour + ":" + minute);
    }
}
