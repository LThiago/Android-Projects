package com.example.languagepreferences;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("com.example.languagepreferences", Context.MODE_PRIVATE);
        textView = findViewById(R.id.textView);
        String language = sharedPreferences.getString("language", "error");

        assert language != null;
        if (language.equals("error")) {
            //  Initial Alert
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_btn_speak_now)
                    .setTitle("Choose a language")
                    .setMessage("Which language would you like to use?")
                    .setPositiveButton("English", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setLanguage("english");
                        }
                    })
                    .setNegativeButton("Portuguese", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setLanguage("portuguese");
                        }
                    })
                    .show();
        } else {
            setLanguage(language);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.english:
                sharedPreferences.edit().putString("language", "english").apply();
                textView.setText("Hello World!");
                return true;
            case R.id.portuguese:
                sharedPreferences.edit().putString("language", "portuguese").apply();
                textView.setText("Olá Mundo!");
                return true;
            default:
                return false;
        }
    }

    private void setLanguage(String language) {
        sharedPreferences.edit().putString("language", language).apply();
        if (language.equals("english")) {
            textView.setText("Hello World!");
        } else if (language.equals("portuguese")) {
            textView.setText("Olá Mundo!");
        }
    }
}