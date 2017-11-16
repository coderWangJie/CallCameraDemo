package com.wangj.callcamerademo;

import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoReadSMS_2 extends AppCompatActivity {
    String phoneNum = "18958356463";

    private Uri URI_SMS = Uri.parse("content://sms");

    EditText etSMS1;
    EditText etSMS2;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_read_sms_2);
        setTitle("方式2——查数据库");

        initView();
    }

    private void initView() {
        etSMS1 = (EditText) findViewById(R.id.et_sms1);
        etSMS2 = (EditText) findViewById(R.id.et_sms2);

        SMSObserver smsObserver = new SMSObserver(mHandler);
        getContentResolver().registerContentObserver(URI_SMS, true, smsObserver);

    }

    class SMSObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public SMSObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);

            HashMap<String, String> msg = getNewSMS();
            etSMS1.setText("address:" + msg.get("address")
                    + "\nperson:" + msg.get("person")
                    + "\nbody:" + msg.get("body"));

            ArrayList<String> list = (ArrayList<String>) getNumber(msg.get("body"));
            if (list.size() >= 2) {
                etSMS2.setText(list.get(1));
            }

        }

        private HashMap<String, String> getNewSMS() {
            HashMap<String, String> map = new HashMap<>();
            String where = "address like '%" + phoneNum + "'";
            Cursor cursor = getContentResolver().query(URI_SMS,
                    new String[]{"address", "person", "body"},
                    where, null, "date desc");
            if (cursor != null
                    && cursor.getCount() > 0) {
                cursor.moveToFirst();
                map.put("address", cursor.getString(cursor.getColumnIndex("address")));
                map.put("person", cursor.getString(cursor.getColumnIndex("person")));
                map.put("body", cursor.getString(cursor.getColumnIndex("body")));
            }
            if (cursor != null
                    && !cursor.isClosed()) {
                cursor.close();
            }
            return map;
        }

        public List<String> getNumber(String str){
            Pattern p = Pattern.compile("[0-9]+");
            Matcher m = p.matcher(str);
            List<String> result=new ArrayList<>();
            while(m.find()){
                result.add(m.group());
            }
            return result;
        }

    }
}