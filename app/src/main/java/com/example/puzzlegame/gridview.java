package com.example.puzzlegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import static java.lang.System.exit;

public class gridview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);
        getSupportActionBar().hide();
    }

    public void backButton(View view) {
        exit(0);
    }

    public void imgselect(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void imgselect2(View view) {
        startActivity(new Intent(this, MainActivity2.class));
    }

    public void imgselect3(View view) {
        startActivity(new Intent(this, MainActivity3.class));
    }
}