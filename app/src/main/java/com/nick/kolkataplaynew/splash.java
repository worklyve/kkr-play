package com.nick.kolkataplaynew;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView textView = findViewById(R.id.textView);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fadein);
        animation1.setDuration(1500);
        textView.startAnimation(animation1);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {

                Intent intent = new Intent(splash.this,MainActivity.class);
                startActivity(intent);
                finish();


            } }, 2000);
    }
}