package com.example.naturephotoframe.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.naturephotoframe.R;
import com.example.naturephotoframe.Utils.CheckPurchase;
import com.example.naturephotoframe.Utils.Common;
import com.google.android.gms.ads.AdView;

public class MyWork extends AppCompatActivity {
    private FrameLayout adContainerView;
    private AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work);
        adContainerView = findViewById(R.id.ad_view_container);
        adContainerView.post(new Runnable() {
            @Override
            public void run() {
                if (!CheckPurchase.isPurchase) {
                    Common.loadBanner(getApplicationContext(),adContainerView,adView);
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),ExitScreen.class));
    }
}