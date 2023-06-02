package com.appdroid.ignitesolassignment.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.appdroid.ignitesolassignment.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    TextView projectName;
    Timer timer;
    Animation zoom_in_anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        timer = new Timer();
        zoom_in_anim = AnimationUtils.loadAnimation(this,R.anim.zoom_in_anim);
        projectName = findViewById(R.id.projectName);
        projectName.setAnimation(zoom_in_anim);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        },1500);

    }
}