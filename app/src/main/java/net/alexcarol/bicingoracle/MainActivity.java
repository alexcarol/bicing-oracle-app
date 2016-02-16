package net.alexcarol.bicingoracle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);


        Button predictButton = (Button) findViewById(R.id.predict);
        final TextView textView = (TextView) findViewById(R.id.stationId);
        final MainActivity m = this;

        /**
         * TODO
         */
        predictButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(m, StationInfoActivity.class);
                intent.putExtra("stationId", textView.getText().toString());
                int hour;
                int minute;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    hour = timePicker.getHour();
                    minute = timePicker.getMinute();
                } else {
                    hour = timePicker.getCurrentHour();
                    minute = timePicker.getCurrentMinute();
                }

                intent.putExtra("hour", hour);
                intent.putExtra("minute", minute);

                startActivity(intent);
            }
        });

    }
}
