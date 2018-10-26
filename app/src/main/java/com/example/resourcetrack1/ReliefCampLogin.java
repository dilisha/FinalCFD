package com.example.resourcetrack1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ReliefCampLogin extends AppCompatActivity {

    private EditText id,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relief_camp_login);
    }
    public void login(View view) {
        id=(EditText) findViewById(R.id.editText2);
        password=(EditText) findViewById(R.id.editText);

        String s1 = id.getText().toString();
        String s2 = password.getText().toString();

        if(s1.equals("Relief") && s2.equals("helpprovider"))
        {
            TextView msg = (TextView) findViewById(R.id.validation_message);
            msg.setText("");
            Intent intent;
            intent = new Intent(this, com.example.resourcetrack1.ProvideHelp.class);
            startActivity(intent);
        }
        else
        {
            TextView msg = (TextView) findViewById(R.id.validation_message);
            msg.setText("Incorrect Credentials");
            id.setText("");
            password.setText("");
        }
    }
}
