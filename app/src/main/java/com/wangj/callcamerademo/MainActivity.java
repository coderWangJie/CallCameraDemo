package com.wangj.callcamerademo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_call_on_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CallOnActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_call_on_webview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CallOnWebView.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_callBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityB.class);
                startActivityForResult(intent, 123);
            }
        });

        findViewById(R.id.btn_autoReadSMS1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AutoReadSMS_1.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_autoReadSMS2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AutoReadSMS_2.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("WangJ", "requestCode: " + requestCode);
        Log.e("WangJ", "resultCode: " + resultCode);
        if (requestCode == 123
                && resultCode == 321) {
            Log.e("WangJ", "intent.getString: " + data.getStringExtra("name"));
        }
    }
}
