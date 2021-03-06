package com.mangwalo.nsh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.mangwalo.nsh.R;
import com.squareup.picasso.Picasso;

/**
 * Created by LENOVO on 11-01-2019.
 */

public class Splash extends AppCompatActivity {
    private int SPLASH_TIME = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView image = findViewById(R.id.image);
        Picasso.get().load("https://i.dlpng.com/static/png/151888_preview.png").into(image);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(SPLASH_TIME);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(0,0);
                }
            }
        };
        timer.start();
    }
}
