package com.example.naturephotoframe.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.naturephotoframe.R;
import com.example.naturephotoframe.Utils.CheckPurchase;
import com.example.naturephotoframe.Utils.Common;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.nativead.NativeAd;

public class Splash extends AppCompatActivity {

    ImageView imageView;
    ProgressBar progressBar;
    private InterstitialAd mInterstitialAd;
    private NativeAd nativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        MobileAds.setRequestConfiguration(new RequestConfiguration.Builder().build());
        try {
            if (!CheckPurchase.isPurchase) {
                Common.loadInterstitialAdMob(getApplicationContext());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
        }, 2000);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Splash.this, PrivacyPolicy.class));
                showInterstitial();
                finish();
            }
        });

    }

    public void showInterstitial() {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(Splash.this);
        }
    }


}