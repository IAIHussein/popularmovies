package com.iaihussein.popularmovies.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.iaihussein.popularmovies.R;

import org.json.JSONObject;

/**
 * Created by iaihussein on 10/21/16.
 */

public abstract class InternetConnection {
    Context mContext;
    String mURL;
    private ProgressDialog mProgressView;

    public InternetConnection(Context context, String url) {
        mContext = context;
        mURL = url;
        mProgressView = new ProgressDialog(context);
        if (CheckInternet.isConnectingToInternet(context)) {
            mProgressView.show();
            getApiDat();
        } else {
            Toast.makeText(mContext, R.string.error_Connection,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public abstract void handleError(VolleyError volleyError);

    public abstract void handleSuccess(JSONObject jsonObject);


    void getApiDat() {
        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, mURL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                handleSuccess(jsonObject);
                mProgressView.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                handleError(volleyError);
                mProgressView.dismiss();
            }
        });
        MySingleton.getInstance(mContext).addToRequestQueue(mJsonObjectRequest);
    }

}
