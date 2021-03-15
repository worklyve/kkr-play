package com.nick.kolkataplaynew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;
    private InterstitialAd mInterstitialAdwall;
    private InterstitialAd mInterstitialpointstable;
    private InterstitialAd schedule;
    private InterstitialAd squad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-6352838501065055/4119415049");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAdwall = new InterstitialAd(this);
        mInterstitialAdwall.setAdUnitId("ca-app-pub-6352838501065055/6554006694");
        mInterstitialAdwall.loadAd(new AdRequest.Builder().build());

        mInterstitialpointstable = new InterstitialAd(this);
        mInterstitialpointstable.setAdUnitId("ca-app-pub-6352838501065055/5240925028");
        mInterstitialpointstable.loadAd(new AdRequest.Builder().build());

        schedule = new InterstitialAd(this);
        schedule.setAdUnitId("ca-app-pub-6352838501065055/1301680011");
        schedule.loadAd(new AdRequest.Builder().build());

        squad = new InterstitialAd(this);
        squad.setAdUnitId("ca-app-pub-6352838501065055/7380003208");
        squad.loadAd(new AdRequest.Builder().build());

        FirebaseMessaging.getInstance().subscribeToTopic("news");




    }


    public void gotowallpaper(View view) {
        if (mInterstitialAdwall.isLoaded()) {
            mInterstitialAdwall.show();
        } else {
            Intent intent = new Intent(this, wallpaper.class);
            startActivity(intent);
        }
        mInterstitialAdwall.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                Intent intent = new Intent(MainActivity.this, wallpaper.class);
                startActivity(intent);
                mInterstitialAdwall.loadAd(new AdRequest.Builder().build());
            }

        });
    }

    public void gotosquad(View view) {
        if (squad.isLoaded()) {
            squad.show();
        } else {
            Intent intent = new Intent(this, squad.class);
            startActivity(intent);
        }
        squad.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                Intent intent = new Intent(MainActivity.this, squad.class);
                startActivity(intent);
                squad.loadAd(new AdRequest.Builder().build());
            }

        });
    }

    public void gotoschedule(View view) {
        if (schedule.isLoaded()) {
            schedule.show();
        } else {
            Intent intent = new Intent(this, schedule.class);
            startActivity(intent);
        }
        schedule.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                Intent intent = new Intent(MainActivity.this, schedule.class);
                startActivity(intent);
                schedule.loadAd(new AdRequest.Builder().build());
            }

        });
    }

    public void gotonews(View view) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Intent intent = new Intent(this, Latestnews.class);
            startActivity(intent);
        }
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                Intent intent = new Intent(MainActivity.this, Latestnews.class);
                startActivity(intent);
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });
    }

    public void gotopointstable(View view) {
        if (mInterstitialpointstable.isLoaded()) {
            mInterstitialpointstable.show();
        } else {
            Intent intent = new Intent(this, pointstable.class);
            startActivity(intent);
        }
        mInterstitialpointstable.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                Intent intent = new Intent(MainActivity.this, pointstable.class);
                startActivity(intent);
                mInterstitialpointstable.loadAd(new AdRequest.Builder().build());
            }

        });
    }

    public void gotosettings(View view) {
        Intent intent = new Intent(MainActivity.this, Settings.class);
        startActivity(intent);

    }
}