package com.lthiago.guessthecelebrity;

import android.os.AsyncTask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class DownloadTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urls) {
        StringBuilder pageHTML = new StringBuilder();
        URL url;
        HttpURLConnection urlConnection;

        try {
            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            int data = reader.read();

            while (data != -1) {
                char current = (char) data;
                pageHTML.append(current);
                data = reader.read();
            }

            return pageHTML.toString(); //rever

        } catch (Exception e) {
            e.printStackTrace();
            return String.valueOf(e);
        }
    }
}
