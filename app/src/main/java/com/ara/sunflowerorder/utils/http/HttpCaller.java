package com.ara.sunflowerorder.utils.http;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;

import com.ara.sunflowerorder.R;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.app.AlertDialog.THEME_HOLO_DARK;
import static com.ara.sunflowerorder.utils.http.HttpRequest.POST;


/**
 * Created by sathishbabur on 1/31/2018.
 */

public class HttpCaller extends AsyncTask<HttpRequest, String, HttpResponse> {


    private static final String UTF_8 = "UTF-8";
    private Context context;
    private ProgressDialog progressDialog;
    private String progressMessage;

    public HttpCaller(Context context, String progressMessage) {
        super();
        this.context = context;
        this.progressMessage = progressMessage;

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context,
                THEME_HOLO_DARK);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(progressMessage);
        progressDialog.show();
    }

    @Override
    protected HttpResponse doInBackground(HttpRequest... httpRequests) {
        HttpResponse httpResponse = new HttpResponse();
        if (!isNetworkConnected()) {
            httpResponse = new HttpResponse();
            httpResponse.setErrorMessage("No Network Connection Available, please " +
                    "check Network is Enabled or not?");
            return httpResponse;
        }
        HttpRequest httpRequest = httpRequests[0];

        OkHttpClient client = new OkHttpClient();
        try {

            Request request;
            if (httpRequest.getMethodType() == POST) {
                request = new Request.Builder()
                        .url(httpRequest.getUrl())
                        .post(httpRequest.getRequestBody())
                        .build();
            } else {
                request = new Request.Builder()
                        .url(httpRequest.getUrl())
                        .get()
                        .build();
            }
            Response response = client.newCall(request).execute();

            String message = response.body().string();
            if (response.isSuccessful()) {
                httpResponse.setSuccessMessage(message);
            } else {
                httpResponse.setErrorMessage(message);
            }
            response.body().close();
            return httpResponse;

        } catch (Exception e) {
            progressDialog.dismiss();
            Log.e("Http URL", e.toString());
            httpResponse.setErrorMessage(e.getMessage());
        }
        return httpResponse;
    }


    @Override
    protected void onPostExecute(HttpResponse response) {

        super.onPostExecute(response);
        progressDialog.dismiss();
        context = null;
        onResponse(response);
    }

    public void onResponse(HttpResponse response) {
    }
}