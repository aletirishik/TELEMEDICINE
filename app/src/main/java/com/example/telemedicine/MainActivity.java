package com.example.telemedicine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

// src/main/java/your/package/name/SplashScreenActivity.java
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientURI;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, Home.class);
            startActivity(intent);
            finish();
        }, SPLASH_DELAY);
    }
}
