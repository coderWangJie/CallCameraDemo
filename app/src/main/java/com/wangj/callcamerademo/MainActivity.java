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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("WangJ", "requestCode: " + requestCode);
        Log.e("WangJ", "resultCode: " + resultCode);
        Log.e("WangJ", "intent.getString: " + data.getStringExtra("name"));
    }
}
