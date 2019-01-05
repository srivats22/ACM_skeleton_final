package com.example.android.acmmobiletest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Explore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
    }

    public void shellapply(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(
                "https://krb-sjobs.brassring.com/TGnewUI/Search/home/HomeWithPreLoad?PageType=JobDetails&noback=0&partnerid=30030&siteid=5565&jobid=1176624#jobDetails=1176624_5565"));
        startActivity(intent);
    }

    public void googlebs(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(
                "https://careers.google.com/jobs/results/6494166504505344-software-engineering-intern-bs-summer-2019/"));
        startActivity(intent);
    }

    public void googlems(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(
                "https://careers.google.com/jobs/results/4672100797054976-software-engineering-intern-ms-summer-2019/"));
        startActivity(intent);
    }
}
