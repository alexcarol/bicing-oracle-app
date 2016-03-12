package net.alexcarol.bicingoracle;

import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class BicingOracleApiParser {
    @NonNull
    public static StationState[] parseStationStates(String requestResult) {
        try {
            JSONObject jsonObject = new JSONObject(requestResult);
            JSONArray stations = jsonObject.getJSONArray("stations");

            int length = stations.length();
            StationState[] stationStates = new StationState[length];

            for (int i = 0; i < length; ++i) {
                JSONObject jsonStationState = stations.getJSONObject(i);

                stationStates[i] = new StationState(
                    jsonStationState.getString("address"),
                    jsonStationState.getInt("slots"),
                    jsonStationState.getInt("bikes"),
                    new LatLng(jsonStationState.getDouble("lat"), jsonStationState.getDouble("lon"))
                );

            }

            return stationStates;
        } catch (JSONException e) {
            ExceptionLogger.getInstance().log("BicingOracleApiParser", "Error parsing json", e);

            return new StationState[]{};
        }
    }

    @NonNull
    private String getFakeApiResult() {
        return "{\"stations\":[{\"address\":\"Gran via 123\", \"slots\":2, \"bikes\":7, \"lat\":12.2, \"lon\":12.1},{\"address\":\"Gran via 144\", \"slots\":1, \"bikes\":5, \"lat\":122, \"lon\":12.2}]}";
    }
}
