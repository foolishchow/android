package me.foolishchow.android;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import me.foolishchow.android.widget.SubScriptedTextView;

public class TextViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        SubScriptedTextView viewById = findViewById(R.id.text);
        viewById.setBottomSubScript("bottom --- ");
    }
}