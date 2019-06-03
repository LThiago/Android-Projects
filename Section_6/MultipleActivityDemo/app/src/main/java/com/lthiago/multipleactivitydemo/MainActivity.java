package com.lthiago.multipleactivitydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView namesListView = findViewById(R.id.namesListView);
        final ArrayList<String> namesList = new ArrayList<>(asList("Viviane", "Lucas", "Rubia", "Volney", "Heitor", "Jordy", "William", "Raique", "CK"));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, namesList);
        namesListView.setAdapter(arrayAdapter);
        namesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra("name", namesList.get(position));
                startActivity(intent);
            }
        });

    }
}
