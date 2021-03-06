package com.app.lunavista.asyntask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import com.app.lunavista.interfaces.ListenerPostData;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AsyncPostDataResponse extends AsyncTask<String, Void, String> {
    String url;
    int method;
    Context context;
    ProgressDialog dialog;
    List<NameValuePair> requestData;
    ListenerPostData listener;

    public AsyncPostDataResponse(Context context, int method,
                                 List<NameValuePair> requestData, String url) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.method = method;
        this.requestData = requestData;
        this.url = url;
        this.listener = (ListenerPostData) context;
        this.execute(new String[]{});
    }


    public AsyncPostDataResponse(android.support.v4.app.Fragment _context, int method,
                                 List<NameValuePair> requestData, String url) {
        // TODO Auto-generated constructor stub
        this.context = _context.getActivity();
        this.method = method;
        this.requestData = requestData;
        this.url = url;
        this.listener = (ListenerPostData) _context;
        this.execute(new String[]{});
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        //AppUtils.onKeyBoardDown(context);

        dialog = ProgressDialog.show(context, "", "Processing...");

        if (requestData == null) {
            requestData = new ArrayList<NameValuePair>(2);

        }
        Log.e("requestData", url + "***" + requestData.toString());
    }

    @Override
    protected String doInBackground(String... params) {
        // TODO Auto-generated method stub
        String responce = "";
        HttpClient httpclient = new DefaultHttpClient();
        HttpConnectionParams
                .setConnectionTimeout(httpclient.getParams(), 30000);
        HttpPost httppost = new HttpPost(url);
        try {
            httppost.setEntity(new UrlEncodedFormEntity(requestData, HTTP.UTF_8));
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                responce = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (ClientProtocolException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return responce;
    }

    @Override
    protected void onPostExecute(String result) {

        super.onPostExecute(result);
        try {
            Log.e("Response", "***" + result);
            if (result != null) {
                JSONObject jObject = new JSONObject(result);
                listener.onPostRequestSucess(method, result);
            } else {
                listener.onPostRequestFailed(method, "Null data from server.");
                Toast.makeText(context,
                        "Problem to connect server please try later.",
                        Toast.LENGTH_LONG).show();
            }
            if (dialog != null)
                dialog.cancel();
        } catch (Exception e) {
            e.printStackTrace();
            listener.onPostRequestFailed(method, "Invalid data: " + e.getMessage());
            if (dialog != null)
                dialog.cancel();
        }
    }
}
