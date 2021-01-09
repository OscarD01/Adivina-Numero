package com.example.adivinanumero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HallOfFame extends AppCompatActivity {

    Button btnBack;

    private String[] intentMessageSplit;
    private static ArrayList<Result> arrayOfResults = new ArrayList<Result>();
    //private  Bitmap resultBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_of_fame);


        // Construct the data source
        //ArrayList<Result> arrayOfResults = new ArrayList<Result>();
        // Create the adapter to convert the array to views
        ResultAdapter adapter = new ResultAdapter(this, arrayOfResults);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.theList);
        listView.setAdapter(adapter);

        Intent intent = getIntent();
        Bitmap resultBitmap = (Bitmap) intent.getParcelableExtra(MainActivity.EXTRA_BITMAP);
        String intentMessage = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String[] intentMessageSplit = intentMessage.split(",");
        // Add item to adapter
        Result newResult = new Result(intentMessageSplit[0], Integer.parseInt(intentMessageSplit[1]), Integer.parseInt(intentMessageSplit[2]), resultBitmap);
        arrayOfResults.add(newResult);
        //adapter.add(arrayOfResults.get(0));



        //ListView list = (ListView) findViewById(R.id.theList);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                returnMain();
            }
        });



        /*
        ArrayList<String> names = new ArrayList<>();
        Intent intent = getIntent();
        String intentMessage = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String[] intentMessageSplit = intentMessage.split(",");

        names.add("\n               " + intentMessageSplit[0] + "          " + intentMessageSplit[1] + "           " + intentMessageSplit[2] +"     segundos");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, names);
        list.setAdapter(adapter);
         */
    }

    public void returnMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}