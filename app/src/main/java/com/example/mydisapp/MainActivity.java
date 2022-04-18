package com.example.mydisapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "com.example.myfirstapp.NAME";
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static final String EXTRA_WISH = "com.example.myfirstapp.WISH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Called when the user taps the Send button
    public void sendMessage(View view) {
        Intent intent = new Intent(this, WifiDirectActivity.class);
        EditText editTextName = (EditText) findViewById(R.id.editTextName);
        EditText editTextMsg = (EditText) findViewById(R.id.editTextMsg);
        EditText editTextWish = (EditText) findViewById(R.id.editTextWish);
        String name = editTextName.getText().toString();
        String message = editTextMsg.getText().toString();
        String wish = editTextWish.getText().toString();
        intent.putExtra(EXTRA_NAME, name);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_WISH, wish);
        startActivity(intent);
    }
}