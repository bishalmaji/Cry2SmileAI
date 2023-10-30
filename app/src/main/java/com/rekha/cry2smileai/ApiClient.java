package com.rekha.cry2smileai;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Response;

public class ApiClient {
    private static final String BASE_URL = "https://crytosmileapi.onrender.com";

    // You can create a singleton pattern for the request queue
    private static RequestQueue requestQueue;

    private static synchronized RequestQueue getRequestQueue(Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

}

