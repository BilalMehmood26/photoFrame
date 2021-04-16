package com.example.naturephotoframe.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.naturephotoframe.R;
import com.example.naturephotoframe.Utils.CheckPurchase;

public class InAppPurchase extends AppCompatActivity {
    public RelativeLayout weekly, monthly, threeMonths, sixMonths, lifeTime;
    TextView mContinuebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_app_purchase);
        getSupportActionBar().hide();
        init();
    }

    public void init(){
        weekly = findViewById(R.id.weekly_card_view);
        monthly = findViewById(R.id.monthly_card_view);
        threeMonths = findViewById(R.id.three_months_card_view);
        sixMonths = findViewById(R.id.six_months_card_view);
        lifeTime = findViewById(R.id.life_time_card_view);
        mContinuebtn = findViewById(R.id.continue_with_ads);
        weekly.setOnClickListener(v -> {

            if(CheckPurchase.isBpClientReady && CheckPurchase.week1Detail != null){
                CheckPurchase.launch_billing_flow(InAppPurchase.this,CheckPurchase.week1Detail);
            }
        });
        monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(CheckPurchase.isBpClientReady && CheckPurchase.week4detail != null){
                    CheckPurchase.launch_billing_flow(InAppPurchase.this,CheckPurchase.week4detail);
                }
            }
        });

        threeMonths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(CheckPurchase.isBpClientReady && CheckPurchase.month3Detail != null){
                    CheckPurchase.launch_billing_flow(InAppPurchase.this,CheckPurchase.month3Detail);
                }
            }
        });

        sixMonths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckPurchase.isBpClientReady && CheckPurchase.month6Details != null){
                    CheckPurchase.launch_billing_flow(InAppPurchase.this,CheckPurchase.month6Details);
                }
            }
        });

        lifeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(CheckPurchase.isBpClientReady && CheckPurchase.lifetimeDetail != null){
                    CheckPurchase.launch_billing_flow(InAppPurchase.this,CheckPurchase.lifetimeDetail);
                }
            }
        });
        mContinuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InAppPurchase.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}