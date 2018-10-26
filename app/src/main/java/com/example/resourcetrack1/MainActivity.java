package com.example.resourcetrack1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void contributeMe(View view) {

        Intent contributeIntent;
        contributeIntent = new Intent(this, com.example.resourcetrack1.contributeActivity.class);
        startActivity(contributeIntent);
    }

    public void reliefCampsMe(View view){

        Intent campIntent;
        campIntent = new Intent(this , com.example.resourcetrack1.ReliefCamps.class);
        startActivity(campIntent);
    }
    public void callForHelpMenu(View view) {

        Intent contributeIntent;
        contributeIntent = new Intent(this, com.example.resourcetrack1.CallForHelpMenu.class);
        startActivity(contributeIntent);
    }

}
