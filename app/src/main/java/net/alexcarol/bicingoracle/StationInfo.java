package net.alexcarol.bicingoracle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StationInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_info);


        String stationId = this.getIntent().getStringExtra("stationId");

        TextView textView = (TextView) findViewById(R.id.data);
        textView.setText("Id: " + stationId +"\nBicis: 1\nLlocs lliures: 3\n");
    }
}
