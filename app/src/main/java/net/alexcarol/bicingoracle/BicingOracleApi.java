package net.alexcarol.bicingoracle;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.Properties;

class BicingOracleApi {
    public static void bicingOracleApiRequest(
            long timestamp,
            LatLng position,
            Response.Listener<String> responseListener,
            Response.ErrorListener errorListener,
            Context context
    ) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String host = getHost(context);
        System.out.println("Timestamp: " + timestamp + " lat " + position.latitude + " lon " + position.longitude);
        String url = "http://" + host + "/prediction?time="
                + timestamp + "&lat=" + position.latitude + "&lon=" + position.longitude;
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                responseListener,
                errorListener
        );

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                3,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        requestQueue.add(stringRequest);
    }

    private static String getHost(Context context) {
        String host = "local.docker";

        Properties properties = new Properties();
        try {
            properties.load(context.getAssets().open("config.properties"));
            host = properties.getProperty("bicingHost");
        } catch (IOException e) {
            ExceptionLogger.getInstance().log("PredictionInfoActivity", "Error acquiring properties", e);
        }

        return host;
    }


}
