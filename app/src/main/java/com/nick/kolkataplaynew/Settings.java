package com.nick.kolkataplaynew;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void feedback(View view) {
        Intent Email = new Intent(Intent.ACTION_SEND);
        Email.setType("text/email");
        Email.putExtra(Intent.EXTRA_EMAIL,
                new String[]{"liveworq@gmail.com"});  //developer 's email
        Email.putExtra(Intent.EXTRA_SUBJECT,
                "Add your Subject"); // Email 's Subject
        Email.putExtra(Intent.EXTRA_TEXT, "" + "");  //Email 's Greeting text
        startActivity(Intent.createChooser(Email, " "));
    }

    public void rateus(View view) {
        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {

        }
    }

    public void shareapp(View view) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Minimalism");
            String shareMessage= "\nDownload the Kolkata play app\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }

    public void admin(View view) {
        Intent intent = new Intent(this, admin.class);
        startActivity(intent);

    }
}