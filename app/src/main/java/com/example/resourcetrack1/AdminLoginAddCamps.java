package com.example.resourcetrack1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class AdminLoginAddCamps extends AppCompatActivity {

    public  static ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login_add_camps);
    }

    public void loginMe(View view){

        EditText name = (EditText) findViewById(R.id.etName);
        EditText password = (EditText) findViewById(R.id.etPassword);

        String s1 = name.getText().toString();
        String s2=password.getText().toString();


        if( s1.equals("Admin") && s2.equals("HelpPeople"))
        {
           // System.out.print("success");
            TextView msg = (TextView) findViewById(R.id.validation_message);
            msg.setText("");
            name.setText("");
            password.setText("");
            Intent intent = new Intent(this, com.example.resourcetrack1.AddCamps.class);
            startActivity(intent);
        }
        else
        {
            //System.out.print("failure");
            TextView msg = (TextView) findViewById(R.id.validation_message);
            msg.setText("Incorrect credentials , please try again ");
            name.setText("");
            password.setText("");
        }
    }

    public  void removeMe(View view)
    {
        EditText name = (EditText) findViewById(R.id.etName);
        EditText password = (EditText) findViewById(R.id.etPassword);

        String s1 = name.getText().toString();
        String s2=password.getText().toString();


        if( s1.equals("Admin") && s2.equals("HelpPeople"))
        {
            // System.out.print("success");
            TextView msg = (TextView) findViewById(R.id.validation_message);
            msg.setText("");
            name.setText("");
            password.setText("");
            Intent intent = new Intent(this, com.example.resourcetrack1.DeleteCamps.class);
            startActivity(intent);
        }
        else
        {
            //System.out.print("failure");
            TextView msg = (TextView) findViewById(R.id.validation_message);
            msg.setText("Incorrect credentials , please try again ");
            name.setText("");
            password.setText("");
        }
    }
}
