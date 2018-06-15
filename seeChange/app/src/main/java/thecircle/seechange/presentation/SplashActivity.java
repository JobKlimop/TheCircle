package thecircle.seechange.presentation;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import thecircle.seechange.R;

public class SplashActivity extends AppCompatActivity {
    protected int _splashTime = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashActivity.this,
                        ChatActivity.class));
                finish();
            }
        }, secondsDelayed * 2000);
    }
}
