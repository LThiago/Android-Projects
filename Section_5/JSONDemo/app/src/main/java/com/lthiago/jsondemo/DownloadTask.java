package com.lthiago.jsondemo;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class DownloadTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... urls) {

        StringBuilder result = new StringBuilder();
        URL url;
        HttpURLConnection httpURLConnection;

        try {
            url = new URL(urls[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            int data = inputStreamReader.read();

            while (data != -1) {
                char current = (char) data;
                result.append(current);
                data = inputStreamReader.read();
            }

            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            String weatherInfo = jsonObject.getString("weather");
            /*Log.i("Weather", weatherInfo);*/
            JSONArray jsonArray = new JSONArray(weatherInfo);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonPart = jsonArray.getJSONObject(i);

                /*Log.i("main", jsonPart.getString("main"));
                Log.i("description", jsonPart.getString("description"));*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
