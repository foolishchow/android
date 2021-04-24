package me.foolishchow.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import me.foolishchow.android.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding inflate = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(inflate.getRoot());

        inflate.subscriptedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TextViewActivity.class));
            }
        });

        inflate.lineView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LineViewActivity.class));
            }
        });


        inflate.datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DatePickerActivity.class));
            }
        });
    }
}