package com.example.android.acmmobiletest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    public void membership(View view) {
        Intent intent = new Intent(this, Membership.class);
        startActivity(intent);
    }

    public void calendar(View view){
        Intent intent = new Intent(this, Calendar.class);
        startActivity(intent);
    }

    public void projects(View view){
        Intent intent = new Intent(this, Projects.class);
        startActivity(intent);
    }

    public void tutoring(View view){
        Intent intent = new Intent(this, Tutoring.class);
        startActivity(intent);
    }

    public void officehours(View view){
        Intent intent = new Intent(this, Office.class);
        startActivity(intent);
    }

    public void explore(View view){
        Intent intent = new Intent(this, Explore.class);
        startActivity(intent);
    }

    public void about(View view){
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }
}
