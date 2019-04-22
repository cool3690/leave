package com.example.leave;
import org.json.JSONObject;
import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


@SuppressLint("NewApi")
public class MainActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      
        findViews();  
        setListeners();
        
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()  
        .detectDiskReads()  
        .detectDiskWrites()  
        .detectNetwork()  
        .penaltyLog() 
        .build());  
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()  
        .detectLeakedSqlLiteObjects()   
        .penaltyLog()  
        .penaltyDeath()  
        .build());
        //testNotification();
       
        
		
    } 
    
    private Button login,signature,del;
    private EditText acc;
    private EditText pwd;
    private TextView show;
    private void findViews() {
    	
    	login = (Button)findViewById(R.id.login);
    	signature = (Button)findViewById(R.id.signature);
    	del = (Button)findViewById(R.id.del);
    	acc=(EditText)findViewById(R.id.acc);
    	pwd=(EditText)findViewById(R.id.pwd);
    	show=(TextView)findViewById(R.id.show);
    	SharedPreferences remdname=getPreferences(Activity.MODE_PRIVATE);
    	String name_str=remdname.getString("emp_id", "");
    	String pass_str=remdname.getString("passwd", "");
    	acc.setText(name_str);
    	pwd.setText(pass_str);
    	
   	 Intent intent2 = new Intent(MainActivity.this, NickyService.class);
      
   	intent2.putExtra("ACCOUNT", name_str);
       startService(intent2);
       
    }
    
    private void setListeners() {
    	login.setOnClickListener(getDBRecord);
    	signature.setOnClickListener(getsign);
    	del.setOnClickListener(getdel);
    }
    
    private Button.OnClickListener getDBRecord = new Button.OnClickListener() {
        public void onClick(View v) {
            // TODO Auto-generated method stub
        	String b="com.example.leave.off";
        	mydb(b);
        }
    };
    private Button.OnClickListener getsign = new Button.OnClickListener() {
        public void onClick(View v) {
            // TODO Auto-generated method stub
        	String b="com.example.leave.check";
        	mydb(b);
        }
    };
    private Button.OnClickListener getdel = new Button.OnClickListener() {
        public void onClick(View v) {
            // TODO Auto-generated method stub
        	String b="com.example.leave.deloff";
        	mydb(b);
        }
    };
    private void testNotification() {
	    Notification.Builder builder = new Notification.Builder(this);
	    builder.setSmallIcon(R.drawable.ic_launcher)
	        .setPriority(Notification.PRIORITY_HIGH)
	        .setOngoing(true);
	    builder.setLights(Color.GREEN, 1000, 1000);
	    
	    builder.setContentText("hirrrr");
	    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	    manager.notify(1, builder.build());
	}
    public void mydb(String b){ 
    	 try {	
    		 String emp_id=acc.getText().toString();
         	 String passwd=pwd.getText().toString();
    		 SharedPreferences remdname=getPreferences(Activity.MODE_PRIVATE);
    		 SharedPreferences.Editor edit=remdname.edit();
    		 edit.putString("emp_id", acc.getText().toString());
    		 edit.putString("passwd", pwd.getText().toString());
    		 edit.commit();
    		
        String result = db.executeQuery(emp_id,passwd);     
             JSONArray jsonArray = new JSONArray(result);
             for(int i = 0; i < jsonArray.length(); i++) {
                 JSONObject jsonData = jsonArray.getJSONObject(i);
                 String str=jsonData.getString("emp_id");
                 String account=acc.getText().toString();
                 
                 	 show.setText(emp_id);
                 	
                    String department=jsonData.getString("department");
                    String name=jsonData.getString("name");
                    String hr=jsonData.getString("hrremain");
                    String pwd=jsonData.getString("pwd");
                    Intent intent=new Intent(); 
            		intent.setClass(MainActivity.this,Class.forName(b));    		
            		//Class.forName(b)
            		Bundle bundle=new Bundle();
            		bundle.putString("ACCOUNT", account);
            		bundle.putString("DEPARTMENT", department);
            		bundle.putString("NAME", name);
            		bundle.putString("HR", hr);
            		bundle.putString("PWD", pwd);
            		
            		intent.putExtras(bundle);
            		
            		// 執行附帶資料的 Intent
            		startActivity(intent);
            		
               
             }
         } catch(Exception e) {}
    }
}