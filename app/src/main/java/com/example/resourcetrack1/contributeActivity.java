package com.example.resourcetrack1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class contributeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribute);
    }

    public void contributeMe1(View view){

        Intent contri_intent = new Intent(this, com.example.resourcetrack1.contribute1.class);
        startActivity(contri_intent);
    }


}
