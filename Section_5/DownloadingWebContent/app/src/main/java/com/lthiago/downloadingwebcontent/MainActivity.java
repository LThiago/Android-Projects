package com.lthiago.downloadingwebcontent;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();
        String result = null;
        try {
            result = task.execute("https://www.zappycode.com").get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("Result", result);
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            StringBuilder result = new StringBuilder();
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
                    result.append(current);
                    data = reader.read();
                }

                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return "Failed";
            }
        }
    }
}
