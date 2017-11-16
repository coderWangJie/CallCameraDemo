package com.wangj.callcamerademo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoReadSMS_1 extends AppCompatActivity {
    String phoneNum = "18958356463";

    EditText etSMS1;
    EditText etSMS2;
    String SMS_ACTION = "android.provider.Telephony.SMS_RECEIVED";

    SmsReciver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_read_sms_1);
        setTitle("方式1——PDU");

        initView();
    }

    private void initView() {
        etSMS1 = (EditText) findViewById(R.id.et_sms1);
        etSMS2 = (EditText) findViewById(R.id.et_sms2);

        receiver = new SmsReciver();

        IntentFilter filter = new IntentFilter();
        filter.addAction(SMS_ACTION);
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        registerReceiver(receiver, filter);
    }

    public class SmsReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            SmsMessage msg;
            if (null != bundle) {
                Object[] smsObj = (Object[]) bundle.get("pdus");
                for (Object object : smsObj) {
                    msg = SmsMessage.createFromPdu((byte[]) object);
                    Date date = new Date(msg.getTimestampMillis());//时间
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String receiveTime = format.format(date);

                    etSMS1.setText("number:" + msg.getOriginatingAddress()
                            + "\nbody:" + msg.getDisplayMessageBody()
                            + "\ntime:" + receiveTime);

                    //在这里写自己的逻辑
                    ArrayList<String> list = (ArrayList<String>) getNumber(msg.getDisplayMessageBody());
                    if (msg.getOriginatingAddress().contains(phoneNum)
                            && list.size() >= 2) {
                        etSMS2.setText(list.get(1));
                    }

                }
            }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}