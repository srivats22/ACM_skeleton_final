package com.example.android.acmmobiletest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void createaccount(View view){
        Toast.makeText(this, "Account Created", Toast.LENGTH_SHORT).show();
    }
}
