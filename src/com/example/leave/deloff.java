package com.example.leave;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Intent;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class deloff extends Activity {
	 
	  private Button okbtn,fin;
	  private TextView show;
	  private ListView prefer;
	 String account,department,name,hr,pwd;
	 String substitute="",manager="",emp_id="",type="",time="",hruselea="";
	 String myname="",reason="";
		
	 String[] pre= new String[1];
	 Date cDate = new Date();
	 String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
	
	 int count;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daydel);
        pre[0]="";
        // ���o��������
        okbtn=(Button)findViewById(R.id.okbtndel);
        okbtn.setOnClickListener(checkok);
        fin=(Button)findViewById(R.id.fin);
        fin.setOnClickListener(finbtn);
        prefer=(ListView)findViewById(R.id.preferdel);
       show=(TextView)findViewById(R.id.show);
       
    // �H�h��d�� �إ�  ArrayAdapter
      
        // ���o  bundle 
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
         account=bundle.getString("ACCOUNT");
         department=bundle.getString("DEPARTMENT");
         name=bundle.getString("NAME");
         hr=bundle.getString("HR");
         pwd=bundle.getString("PWD");
         
        
         try{   //dbleasel
        	
        	 String result =dbleasel.executeQuery(account,fDate);
        	 JSONArray jsonArray = new JSONArray(result);
        	show.setText("a"+":"+account);
        	if(jsonArray.length()==0){pre= new String[1];pre[0]="";}
        	 else{pre= new String[jsonArray.length()];
        
 			for(int i = 0; i < jsonArray.length(); i++) 
 			  {	 JSONObject jsonData = jsonArray.getJSONObject(i);
 				emp_id=jsonData.getString("emp_id");
 				 
 				 type=jsonData.getString("type");
 				myname=jsonData.getString("name");
 				 reason=jsonData.getString("reason");
 			
 				 pre[i]=myname+"/"+type+"/"+reason+"\n"+time;
 			  }
         }
         }
       
		catch(Exception e){}
         ArrayAdapter<String> adapterBalls = new ArrayAdapter<String>(this,
        	       android.R.layout.simple_list_item_multiple_choice, pre);  
        	                 
        	       prefer.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); // �]�w�i�h��
        	                
        	       // �]�w  ListView ����ƨӷ�
        	       prefer.setAdapter(adapterBalls);  
        	                 
        	       count = adapterBalls.getCount();  // ���o��������`��
        	          
        	       // �]�w lstPrefer ���� ItemClick �ƥ� listener �� lstPreferListener
        	       prefer.setOnItemClickListener(lstPreferListener);
    }
  
    private Button.OnClickListener checkok=new Button.OnClickListener(){
    	public void onClick(View v){
    		try{
    		  String result =dbleasel.executeQuery(account,fDate);
    		   JSONArray jsonArray = new JSONArray(result);
    		   if(jsonArray.length()==0){pre= new String[1];pre[0]="";}
          	 else{pre= new String[jsonArray.length()];
          
         		for(int i = 0; i < jsonArray.length(); i++) 
         	 	 	{if (prefer.isItemChecked(i)){
         				JSONObject jsonData = jsonArray.getJSONObject(i);
         				emp_id=jsonData.getString("emp_id");
       				  substitute=jsonData.getString("substitute");
       				  manager=jsonData.getString("manager");
       				 type=jsonData.getString("type");
       				myname=jsonData.getString("name");
       				 reason=jsonData.getString("reason");
       				String lea_id=jsonData.getString("lea_id");
       	    		hruselea=jsonData.getString("hruse");
       	    		
         			 if(!type.contains("�S��"))
         	    		{
         	    			
         	    		if(!manager.contains("A") || !manager.contains("B")
         	    		   || !manager.contains("C"))
         	    		  {
         	    		String r2 =dbcheck.executeQuery(emp_id);
         	        	JSONArray jsonArr2 = new JSONArray(r2);
         	        	for(int j = 0; j < jsonArr2.length(); j++) 
             	 	 	{
         	        	JSONObject jsonData2 = jsonArr2.getJSONObject(j);
         	        	String cmanager=jsonData2.getString("manager");
         	        	show.setText(cmanager);
             			dbleaud.executeQuery(emp_id,lea_id,"1",cmanager);
             	 	 	}
         	    			// 	    
         	    			}
         	    		else{ dbleadel.executeQuery(emp_id,lea_id);}
         	    		}
         	    		   
         	    	 else
         	  		     {
         	  		       
         	  		   if(!manager.contains("A")|| !manager.contains("B")
             	    	   || !manager.contains("C"))
         	  		   {String r2 =dbcheck.executeQuery(emp_id);
        	        	JSONArray jsonArr2 = new JSONArray(r2);
         	  			for(int j = 0; j < jsonArr2.length(); j++) 
             	 	 	{
         	        	JSONObject jsonData2 = jsonArr2.getJSONObject(j);
         	        	String cmanager=jsonData2.getString("manager");
         	        	show.setText(cmanager);
             			dbleaud.executeQuery(emp_id,lea_id,"1",cmanager);
             	 	 	}
         	  		   }
         	  		   else{
         	  			   dbleadel.executeQuery(emp_id,lea_id);
         	  			   }
         	  			   
         	  			 
         	  		     }/**/
         	  		     
         	 		}
         	 		}
         		 new AlertDialog.Builder(deloff.this)
    	      		.setTitle("�T�{����")
    	      		.setIcon(R.drawable.ic_launcher)
    	      		.setMessage("ñ�֧���!")
    	      		.show();
          	 }
         		
    			 
    		}
    	 
    		catch(Exception e){}
    		
    		//refresh();
    	}
    	};
    	private void refresh() {
        	//finish();
        	Intent intent = new Intent(deloff.this, deloff.class);
        	startActivity(intent);
    	}
    
    	   //  �w�q  onItemClick ��k
        private ListView.OnItemClickListener lstPreferListener=
          	new ListView.OnItemClickListener(){
        	@Override
         	public void onItemClick(AdapterView<?> parent, View v,
        		int position, long id) {
       			
       		}
         };
    	private Button.OnClickListener finbtn=new Button.OnClickListener(){
        	public void onClick(View v){
        		finish();
        	}};
  
}