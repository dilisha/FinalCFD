package com.example.resourcetrack1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CallForHelpMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_for_help_menu);
    }
    public void callForHelp(View view) {

        Intent contributeIntent;
        contributeIntent = new Intent(this, com.example.resourcetrack1.CallForHelp.class);
        startActivity(contributeIntent);
    }
    public void reliefCampLogin(View view) {

        Intent contributeIntent;
        contributeIntent = new Intent(this, com.example.resourcetrack1.ReliefCampLogin.class);
        startActivity(contributeIntent);
    }
}
