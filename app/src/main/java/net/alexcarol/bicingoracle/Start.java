package net.alexcarol.bicingoracle;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        final Activity t = this;

        Button legacy = (Button) findViewById(R.id.legacy);
        legacy.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(t, MainActivity.class));
                    }
                }
        );

        Button map = (Button) findViewById(R.id.map);
        map.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(t, ChooseLocationActivity.class));
                    }
                }
        );
    }
}
