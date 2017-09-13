package com.wangj.callcamerademo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ActivityB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_b);

    }

    public void myButton(View view) {
        Intent intent = new Intent();
        intent.putExtra("name", "WangJie");
        setResult(321, intent);
        finish();
    }
}
