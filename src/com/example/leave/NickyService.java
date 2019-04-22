package com.example.leave;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import java.util.Date;
import android.content.Intent;
import org.json.JSONArray;
import org.json.JSONObject;



//�~��android.app.Service
@SuppressLint("NewApi")
public class NickyService extends Service {
    private Handler handler = new Handler();
    String account="q";
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Intent getIntent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    public void onStart(Intent intent, int startId) {
		//intent=this.getIntent();
		account = intent.getStringExtra("ACCOUNT");
        handler.postDelayed(showTime, 1000);
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(showTime);
        super.onDestroy();
    }
    private void testNotification() {
	    Notification.Builder builder = new Notification.Builder(this);
	    builder.setSmallIcon(R.drawable.ic_launcher)
	        .setPriority(Notification.PRIORITY_HIGH)
	        .setOngoing(false);
	    builder.setLights(Color.GREEN, 1000, 1000);
	    
	    builder.setContentText("��ñ��");
	    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	    manager.notify(1, builder.build());
	}
    private Runnable showTime = new Runnable() {
        public void run() {
            //log�ثe�ɶ�
          //  Log.i("time:", account);
            
           
            try {	
       		
            
           String result = dbleanoti.executeQuery(account);     
                JSONArray jsonArray = new JSONArray(result);
               if(jsonArray.length()>0) {
                   // JSONObject jsonData = jsonArray.getJSONObject(i);
                   testNotification();
                        handler.postDelayed(this, 1000);
                }
            } catch(Exception e) {}
            /* */
           
          
        }
    };
}