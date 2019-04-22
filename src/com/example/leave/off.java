package com.example.leave;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.content.Intent;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;



public class off extends Activity {
	  private Spinner mychoice,sub;
	  private EditText date1;
	  private EditText date2;
	  private EditText time1;
	  private EditText time2;
	  private EditText reason;
	  private TextView ans;
	  private TextView tt;
	  private Button confirm;
	  String sub_n="",manager="";
	  String[] mysub= new String[1];
	 
	  //private Button back;
	  String account,department,name,hr;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dayoff);
         //ans.setText("rew");
        // 取得  bundle
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
         account=bundle.getString("ACCOUNT");
         department=bundle.getString("DEPARTMENT");
         name=bundle.getString("NAME");
         hr=bundle.getString("HR");
         ans=(TextView)findViewById(R.id.ans);
         mysub[0]="a";
         
        
        
        // 取得介面元件
        tt=(TextView)findViewById(R.id.tt);  
        time1=(EditText)findViewById(R.id.time1);
        time2=(EditText)findViewById(R.id.time2);
        date1=(EditText)findViewById(R.id.date1);
        date2=(EditText)findViewById(R.id.date2);
        reason=(EditText)findViewById(R.id.reason);
       
         sub=(Spinner)findViewById(R.id.sub);
        mychoice=(Spinner) findViewById(R.id.choice);  
        confirm=(Button)findViewById(R.id.confirm);
       // back=(Button)findViewById(R.id.back);
        //reason.setInputType(InputType.TYPE_NULL);
        confirm.setOnClickListener(btnTranListener);
      
       // ans.setText(mysub[0]);
        String s=department+" ," +name+" ,特休剩餘:"+ hr + " 小時\n" ;
            tt.setText(s);
           
            sub=(Spinner) findViewById(R.id.sub);
            try{
          	  
                String result =dbcheck.executeQuery(account);
                	mysub[0]="a";
                JSONArray jsonArray = new JSONArray(result);
               
                String[] mysub= new String[jsonArray.length()];
                for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                mysub[i]=jsonData.getString("sub_n");
                //ans.setText(jsonData.getString("sub_n"));
                }
                ArrayAdapter<String> adaptersub=new ArrayAdapter<String>
    			(this,android.R.layout.simple_spinner_item,mysub);

            // 設定Spinner顯示的格式
            adaptersub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // 設定Spinner的資料來源
            sub.setAdapter(adaptersub);  
            
            // 設定 sub 元件 ItemSelected 事件的 listener 為  subListener
            sub.setOnItemSelectedListener(subListener); 
                
                
                }
                catch(Exception e){}
            // 建立ArrayAdapter
            
              
            ///day off type
            ArrayAdapter<CharSequence> adapterBalls = ArrayAdapter.createFromResource(
                    this, R.array.absent,android.R.layout.simple_spinner_item);   
            adapterBalls.setDropDownViewResource(android.R.layout.
    			 simple_spinner_dropdown_item);
            mychoice.setAdapter(adapterBalls);  
            mychoice.setOnItemSelectedListener(spnPreferListener); 
          
            date1.setOnFocusChangeListener(new View.OnFocusChangeListener() {  

                @Override  
                public void onFocusChange(View v, boolean hasFocus) {  
                     // TODO Auto-generated method stub  
                    if(hasFocus){  
                        showDatePickerDialog(); 
                    }  
                 }  
          });  
            
            date1.setOnClickListener(new View.OnClickListener() {  

                @Override  
                public void onClick(View v) {  
                    // TODO Auto-generated method stub  
                         showDatePickerDialog();
                }  
           });  
            
            
            
            date2.setOnFocusChangeListener(new View.OnFocusChangeListener() {  

                @Override  
                public void onFocusChange(View v, boolean hasFocus) {  
                     // TODO Auto-generated method stub  
                    if(hasFocus){  
                    	showDatePickerDialog2(); 
                    }   
                 }  
          });  
            
            date2.setOnClickListener(new View.OnClickListener() {  

                @Override  
                public void onClick(View v) {  
                    // TODO Auto-generated method stub  
                	showDatePickerDialog2();
                }  
           });  
        
            
            
            
            //////
            time1.setOnFocusChangeListener(new View.OnFocusChangeListener() {  

                @Override  
                public void onFocusChange(View v, boolean hasFocus) {  
                     // TODO Auto-generated method stub  
                    if(hasFocus){  
                    	showtime(); 
                    }  
                 }  
          });  
            
            time1.setOnClickListener(new View.OnClickListener() {  

                @Override  
                public void onClick(View v) {  
                    // TODO Auto-generated method stub  
                	showtime();
                }  
           });  
        
            time2.setOnFocusChangeListener(new View.OnFocusChangeListener() {  

                @Override  
                public void onFocusChange(View v, boolean hasFocus) {  
                     // TODO Auto-generated method stub  
                    if(hasFocus){  
                    	showtime2(); 
                    }  
                 }  
          });  
            
            time2.setOnClickListener(new View.OnClickListener() {  

                @Override  
                public void onClick(View v) {  
                    // TODO Auto-generated method stub  
                	showtime2();
                }  
           });  
         
    }
   
   //   confirm.setOnClickListener(btnTranListener);
   /* private Button.OnClickListener backbtn=new Button.OnClickListener(){
    	public void onClick(View v){
    		finish();
    	}
    	};*/
    private Button.OnClickListener btnTranListener=new Button.OnClickListener(){
    	public void onClick(View v){
    		String c=mychoice.getSelectedItem().toString();
    		String[] d1=date1.getText().toString().split("-");		
    		String[] d2=date2.getText().toString().split("-");
    		String[] t1=time1.getText().toString().split(":");
    		String[] t2=time2.getText().toString().split(":");   	
    		double totaltime=0;
    		int count=0;//分
    		int co=0;//時
    		int myday=0;
    		int mymon=0,myyear=0;
    		int tmp1=Integer.parseInt(t1[1]),tmp2=Integer.parseInt(t2[1]);
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    		  Calendar cal = Calendar.getInstance();
    		  Calendar cal2 = Calendar.getInstance();
    		String td=date1.getText().toString();
    		String td2=date2.getText().toString();
    		try {
				cal.setTime(format.parse(td));
				cal2.setTime(format.parse(td2));
			} 
    		catch (ParseException e1) {e1.printStackTrace();}
    	    int dayOfWeek1 = cal.get(Calendar.DAY_OF_WEEK);
    	    int dayOfWeek2 = cal2.get(Calendar.DAY_OF_WEEK);
    	    for(int y=0;y<1;y++){
    	    	 try{ String tim=time1.getText().toString();
    	    	 
    	    		 String result =dbleacom.executeQuery(account,td,tim);
    	    		   
    	        	 JSONArray jsonArray = new JSONArray(result);
    	        	 int a=jsonArray.length();
    	        	// ans.setText(result);
    	        	 if(jsonArray.length()>0){
    	        		 String str="重複請假";
    	     	    	mytoast(str);
    	     	    	date1.setText("");
    	        	    }
    	        	
    	            }
    	        catch(Exception e){}
    	    if(dayOfWeek1==7|| dayOfWeek1==1){
    	    	String str="起始時間非上班時間";
    	    	mytoast(str);
    	    	date1.setText("");
    	    	break;
    	    	}
    	    else if(dayOfWeek2==7|| dayOfWeek2==1){
    	    	String str="結束時間非上班時間";
    	    	mytoast(str);
    	    	date2.setText("");
    	    	break;
    	    	}
    	   // tt.setText(dayOfWeek+"");
    	    else if(Integer.parseInt(t1[0])<8 ||Integer.parseInt(t1[0])>17){
    			String str="起始時間非上班時間";
    	    	mytoast(str);
        		time1.setText(""); 
        		break;
    		}
    		else if(Integer.parseInt(t2[0])<8 ||Integer.parseInt(t2[0])>18)
    		{String str="結束時間非上班時間";
	    	 mytoast(str);
    		 time2.setText("");
    		 break;
    		}
    		else if(c.equals("假別"))
			{  String str="請選擇假別。";
	    	       mytoast(str);
			  
        	   break;
			} 
    		else if(Integer.parseInt(t2[0])>=Integer.parseInt(t1[0]))
    		  {count=(Integer.parseInt(t2[0])-Integer.parseInt(t1[0]))*60;}
    		else
    		{
    			count=(Integer.parseInt(t2[0])-8+17-Integer.parseInt(t1[0]))*60;	
    		}
    		
    		if(tmp2==0){tmp2=60;count=count-60;} 
    		count=count+tmp2-tmp1;
    		if(Integer.parseInt(t1[0])<12 &&Integer.parseInt(t2[0])>13){count=count-60;}
    		if(count>480){count=480;}
    		
    			co=count/60;
    			count=count%60;
    		
    		 if(d1[0].equals(d2[0]) &&d1[1].equals(d2[1])&&d1[2].equals(d2[2])){
    			 if(co==0 && count<30){
    			String str="至少請30分鐘";
   	    	    mytoast(str);
        		time2.setText("");
        		break;
               }
    			 else{
    				 if(count>0 &&count<30){count=30;}
     	        	if(count>31 &&count<60){co++;count=0;}
    				 ans.setText(co +"小時"+count+"分" );
    				// mydialog();
    			 }
    			
    			
    		}
    		
    		 else{
    			   if(count>0 &&count<30){count=30;}
    	        	if(count>31 &&count<60){co++;count=0;}
    			 if(d1[0].equals(d2[0]) &&d1[1].equals(d2[1]))
    			   {
    			    myday=Integer.parseInt(d2[2])-Integer.parseInt(d1[2]);
    			    ans.setText(myday+"天 "+co +"小時"+count+"分");
    			    //mydialog();
    			   }
    		else if(d1[0].equals(d2[0]))
    			{
    			 
    			mymon=((Integer.parseInt(d2[1])-Integer.parseInt(d1[1]))*30);
    			mymon+=Integer.parseInt(d2[2])-Integer.parseInt(d1[2]);
			    for(int i=Integer.parseInt(d1[1]);i<Integer.parseInt(d2[1]);i++)
    			  {
    				if(i==1 || i==3||i==5 || i==7||i==8||i==10||i==12)
    					mymon++;
    				if(i==2)mymon-=2;
    			  }
    			myday=mymon%30;
    			mymon=mymon/30;
    			
    			ans.setText(mymon+"月"+myday+"天 "+co +"小時"+count+"分");
    			// mydialog();
    			} 
    		else
    		   {
    			myyear=Integer.parseInt(d2[0])-Integer.parseInt(d1[0]);
    			if(Integer.parseInt(d2[1])<Integer.parseInt(d1[1]))
    			{
    				mymon=12-Integer.parseInt(d1[1])+Integer.parseInt(d2[1]);
    				myyear--;
    				mymon=mymon*30;
    				mymon+=Integer.parseInt(d2[2])-Integer.parseInt(d1[2]);
    				for(int i=Integer.parseInt(d1[1]);i<Integer.parseInt(d2[1]);i++)
      			  {
      				if(i==1 || i==3||i==5 || i==7||i==8||i==10||i==12)
      					mymon++;
      				if(i==2)mymon-=2;
      			  }
    				myday=mymon%30;
    				mymon=mymon/30;
    				ans.setText(myyear+"年"+mymon+"月"+myday+"天 "+co +"小時"+count+"分");
    				// mydialog();
    			}
    			else
    			{
    			 mymon=Integer.parseInt(d2[1])+Integer.parseInt(d1[1]);
    			 mymon=mymon*30;
 				 mymon+=Integer.parseInt(d2[2])-Integer.parseInt(d1[2]);
 				for(int i=Integer.parseInt(d1[1]);i<Integer.parseInt(d2[1]);i++)
  			  {
  				if(i==1 || i==3||i==5 || i==7||i==8||i==10||i==12)
  					mymon++;
  				if(i==2)mymon-=2;
  			  }
 				 myday=mymon%30;
				mymon=mymon/30;
 				ans.setText(myyear+"年"+mymon+"月"+myday+"天 "+co +"小時"+count+"分");
				//mydialog();
    			}
    			
    		   }
    			 if(c.equals("特休")&& Integer.parseInt(hr)<co+count/60.0+myday*8+mymon*30*8+myyear*365*8)
    				{  String str1="特休時數不夠。";
    			       //mytoast(str1);
    				   date2.setText("");
    				   time2.setText("");
    				   break;
    				}

    		 }
     			
     		
    		
    	
    try {
    	 
String start_d=date1.getText().toString();
String end_d=date2.getText().toString();
String start_t=time1.getText().toString();
String end_t=time2.getText().toString();
String rea=reason.getText().toString();
String substitute="";
String manager="";
String result =dbcheck.executeQuery(account);
JSONArray jsonArray = new JSONArray(result);
String str=sub.getSelectedItem().toString();
for(int i = 0; i < jsonArray.length(); i++) {
JSONObject jsonData = jsonArray.getJSONObject(i);
if(str.equals(jsonData.getString("sub_n")))
{
substitute=jsonData.getString("substitute");
manager=jsonData.getString("manager");
}

}

totaltime=co+count/60.0+myday*8+mymon*30*8+myyear*365*8;
String t=Double.toString(totaltime);
mytoast(hr+":"+totaltime+"");

dbin.executeQuery(account,name,department,c,start_d,start_t,end_d,end_t,rea,substitute,manager,t); 
mydialog();
      
    }
    catch(Exception e) {}
    	
    	}
    	}
    };
    private void mydialog(){
    	new AlertDialog.Builder(off.this)
  		.setTitle("確認視窗")
  		.setIcon(R.drawable.ic_launcher)
  		.setMessage("請假完成!")
  		.show(); 
    }
    private void mytoast(String str)
      {
    	Toast toast=Toast.makeText(off.this, str, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
      }
    private void showtime() {
    	Calendar c = Calendar.getInstance(); 
    	new TimePickerDialog(off.this, new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
              String tmp="",tmp2="";
            	if(hourOfDay<10){tmp="0"+hourOfDay;}else tmp=""+hourOfDay;
            	if(minute<10){tmp2="0"+minute;}else tmp2=""+minute;
                time1.setText(tmp + ":" + tmp2);
            }
           
        },  c.get(Calendar.HOUR), c.get(Calendar.MINUTE), false).show();
    }
    private void showtime2() {
    	Calendar c = Calendar.getInstance(); 
    	new TimePickerDialog(off.this, new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            	 String tmp="",tmp2="";
             	if(hourOfDay<10){tmp="0"+hourOfDay;}else tmp=""+hourOfDay;
             	if(minute<10){tmp2="0"+minute;}else tmp2=""+minute;
                 time2.setText(tmp + ":" + tmp2);
            }
        },  c.get(Calendar.HOUR), c.get(Calendar.MINUTE), false).show();
    }
  
    private void showDatePickerDialog() {
        Calendar c = Calendar.getInstance(); 
       new DatePickerDialog(off.this, new DatePickerDialog.OnDateSetListener() {

           @Override
           public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
               // TODO Auto-generated method stub
        	   monthOfYear++;
        	   String tmp="",tmp2="";
        	   if(monthOfYear<10){tmp="0"+monthOfYear;}else tmp=""+monthOfYear;
           	if(dayOfMonth<10){tmp2="0"+dayOfMonth;}else tmp2=""+dayOfMonth;
               date1.setText(year+"-"+(tmp)+"-"+tmp2); 
             
           } 
       }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

   }


    private void showDatePickerDialog2() {
        Calendar c = Calendar.getInstance(); 
       new DatePickerDialog(off.this, new DatePickerDialog.OnDateSetListener() {

           @Override
           public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
               // TODO Auto-generated method stub
        	   monthOfYear++;
        	   String tmp="",tmp2="";
        	   if(monthOfYear<10){tmp="0"+monthOfYear;}else tmp=""+monthOfYear;
           	if(dayOfMonth<10){tmp2="0"+dayOfMonth;}else tmp2=""+dayOfMonth;
               date2.setText(year+"-"+(tmp)+"-"+tmp2);
           }
       }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

   }
    private Spinner.OnItemSelectedListener subListener=
        	new Spinner.OnItemSelectedListener(){
    			@Override
    			public void onItemSelected(AdapterView<?> parent, View v,
    					int position, long id) {
    				//String sel=parent.getSelectedItem().toString();				
    			}
    			@Override
    			public void onNothingSelected(AdapterView<?> parent) {
    				// TODO Auto-generated method stub				
    			}			
        };  
    //  定義  onItemSelected 方法
    private Spinner.OnItemSelectedListener spnPreferListener=
    	new Spinner.OnItemSelectedListener(){
            @Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub				
			}			
    }; 
    
}