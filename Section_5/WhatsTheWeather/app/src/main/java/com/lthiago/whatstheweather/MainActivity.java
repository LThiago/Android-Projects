package com.lthiago.whatstheweather;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    EditText cityEditText;
    TextView weatherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityEditText = findViewById(R.id.cityEditText);
        weatherTextView = findViewById(R.id.weatherTextView);
    }

    public void getWeather(View view) {
        try {

            DownloadTask downloadTask = new DownloadTask();

            String cityName = URLEncoder.encode(cityEditText.getText().toString(), "UTF-8");

            downloadTask.execute(generateURL(cityName));

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(cityEditText.getWindowToken(), 0);

        } catch (Exception e) {

            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Could not find weather.", Toast.LENGTH_SHORT).show();

        }
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

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
                Toast.makeText(getApplicationContext(), "Could not find weather.", Toast.LENGTH_SHORT).show();
                return null;

            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject jsonObject = new JSONObject(s);
                String weatherInfo = jsonObject.getString("weather");
                JSONArray jsonArray = new JSONArray(weatherInfo);

                String message = "";

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonPart = jsonArray.getJSONObject(i);

                    String main = jsonPart.getString("main");
                    String description = jsonPart.getString("description");

                    if (!main.equals("") && !description.equals("")) {
                        message += main + ": " + description + "\r\n";
                    }
                }

                if (!message.equals("")) {
                    weatherTextView.setText(message);
                } else {
                    Toast.makeText(getApplicationContext(), "Could not find weather.", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {

                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Could not find weather.", Toast.LENGTH_SHORT).show();


            }
        }
    }

    private String generateURL(String cityName) {
        return String.format("https://openweathermap.org/data/2.5/weather?q=%s&appid=b6907d289e10d714a6e88b30761fae22", cityName);
    }
}
