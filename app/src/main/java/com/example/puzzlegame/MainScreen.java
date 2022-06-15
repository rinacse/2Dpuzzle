package com.example.puzzlegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import static java.lang.System.exit;

public class MainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
    }



    public void closeApplication(View view) {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    public void rateAs(View view) {
        startActivity(new Intent(this, AboutUsActivity.class));
    }

    public void crosspuzzle(View view) {
        startActivity(new Intent(this, cross.class));
    }

    public void selectgame(View view) {
    startActivity(new Intent(this,gridview.class));
    }
}