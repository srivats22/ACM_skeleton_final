package com.example.android.acmmobiletest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Membership extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);
    }

    public void Gpay(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(
                "https://pay.google.com/payments/u/0/home#"));
        startActivity(intent);
    }

    public void paypal(View view) {
        Intent intent = new Intent((Intent.ACTION_VIEW));
        intent.setData(Uri.parse("https://www.paypal.com/us/webapps/mpp/send-money-online"));
        startActivity(intent);
    }

    public void venmo(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://venmo.com/"));
        startActivity(intent);
    }
}
