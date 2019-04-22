package com.example.leave;


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


public class check extends Activity {
	 
	  private Button okbtn,fin;
	  private TextView show;
	  private ListView prefer;
	 String account,department,name,hr,pwd;
	 String substitute="",manager="",emp_id="",type="",time="",hruselea="";
	 String myname="",reason="";
	 String notes="";
	 String[] pre= new String[1];
	// pre= new String[1];
	
	 int count;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycheck);
         pre[0]="";
        // ���o��������
        okbtn=(Button)findViewById(R.id.okbtn);
        okbtn.setOnClickListener(checkok);
        fin=(Button)findViewById(R.id.fin);
        fin.setOnClickListener(finbtn);
        prefer=(ListView)findViewById(R.id.prefer);
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
         String result =dbleave2.executeQuery(account);
          //�N�z�ΥD�ަ��u�������
        //show.setText(account);
         try{  
        	 JSONArray jsonArray = new JSONArray(result);
        	
        	 if(jsonArray.length()==0){pre= new String[1];pre[0]="";}
        	 else{pre= new String[jsonArray.length()];
        	int k=0;
        	 
 			for(int i = 0; i < jsonArray.length(); i++) //�N�z�ΥD�ަ��u�������
 			  {	 JSONObject jsonData = jsonArray.getJSONObject(i);
 				emp_id=jsonData.getString("emp_id");
 				  substitute=jsonData.getString("substitute");
 				  manager=jsonData.getString("manager");
 				 type=jsonData.getString("type");
 				myname=jsonData.getString("name");
 				 reason=jsonData.getString("reason");
 				
 	    		hruselea=jsonData.getString("hruse");
 	    		 notes=jsonData.getString("notes");
 	    		show.setText(notes);
 	    		if(jsonData.getString("start_d").equals(jsonData.getString("end_d")))
 			   {
 				 time=jsonData.getString("start_d")+" ";
 				 time+=jsonData.getString("start_t").substring(0, 5)+"~";
 				 time+=jsonData.getString("end_t").substring(0, 5);
 			   }
 			 else{
 				  time=jsonData.getString("start_d")+" ";
 			 time+=jsonData.getString("start_t").substring(0, 5)+"~";
 			 time+=jsonData.getString("end_d")+" ";
 			 time+=jsonData.getString("end_t").substring(0, 5);
 			 }
 				if(substitute.contains("A")&&substitute.equals(account)
 				|| !substitute.contains("A")&&manager.equals(account))
 				 {  
 					
 				if(notes.equals("1"))
 				   {
 					pre[i]="�����а�:"+myname+"/"+type+"/"+reason+"\n"+time;
 	 		 		
 				   }
 				else{pre[i]=myname+"/"+type+"/"+reason+"\n"+time;}
 				 }
 				else{pre[i]="";}
 				
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
    		   String result =dbleave2.executeQuery(account);//�N�z�ΥD�ަ��u�������
    		   
    		   JSONArray jsonArray = new JSONArray(result);
    		   if(jsonArray.length()==0){pre= new String[1];pre[0]="";}
          	 else{pre= new String[jsonArray.length()];
       	     count=prefer.getCount(); 
         		for(int i = 0; i < count; i++) 
         	 		{if (prefer.isItemChecked(i)){//�N�z�ΥD�ަ��u�������
         				JSONObject jsonData = jsonArray.getJSONObject(i);
         				emp_id=jsonData.getString("emp_id");
       				  substitute=jsonData.getString("substitute");
       				  manager=jsonData.getString("manager");
       				 type=jsonData.getString("type");
       				myname=jsonData.getString("name");
       				 reason=jsonData.getString("reason");
       				String start_d=jsonData.getString("start_d");
       	    		hruselea=jsonData.getString("hruse");
       	    		String lea_id=jsonData.getString("lea_id");
       	    		String notes=jsonData.getString("notes");
         				//show.setText(start_d);
         				 if(substitute.contains("A")&&substitute.equals(account)
         				||substitute.contains("B")&&substitute.equals(account)
         				||substitute.contains("C")&&substitute.equals(account))
         	    		   { 
         					 if(notes.equals("1"))
           	  			   { 
           	  				 dbleaup.executeQuery(emp_id,name,null,lea_id,"2");
           	  			   }
         					if(notes.equals("")){
           	    			  dbleaup.executeQuery(emp_id,name,null,lea_id,""); //updateñ��
           	  			 	   }
         	    		   }
         	    		 if(!substitute.contains("A")&&manager.equals(account)
         	    		 ||!substitute.contains("B")&&manager.equals(account)
         	    		 ||!substitute.contains("C")&&manager.equals(account))
         	  		       {
         	    			 if(notes.equals("1"))
         	  			   { 
         	  				 dbleaup.executeQuery(emp_id,null,name,lea_id,"2");
         	  			   }
         	    			if(notes.equals("")){
         	    			  dbleaup.executeQuery(emp_id,null,name,lea_id,""); //updateñ��
         	  			 	   }
         	  			  
         	  			   String ans= dbemp.executeQuery(emp_id);//db emp ���u��ƪ�
         	  			 JSONArray jsonArray2 = new JSONArray(ans);
         	  			for(int k = 0; k < jsonArray2.length(); k++) 
         	  		  {//db emp ���u��ƪ�
         	  		  JSONObject jsonData2 = jsonArray2.getJSONObject(k);
         	  		  
         	  		  String hruse=jsonData2.getString("hruse");
         	  		String myhr=jsonData2.getString("hr");
         	  	//String hrremain=jsonData2.getString("hrremain");
         	  		    double a=Double.valueOf(hruselea).doubleValue()+Double.valueOf(hruse).doubleValue();
         	  		    String b=a+"";
         	  		    double c=Double.valueOf(hruse).doubleValue()-Double.valueOf(hruselea).doubleValue();
         	  		 String d=c+"";
         	  		 
         	  		 double e=Double.valueOf(myhr).doubleValue()-a;
         	  		String f=e+"";
         	  		double g=Double.valueOf(myhr).doubleValue()-c;	 
         	  			 String h=g+"";
         	  			 show.setText(type+"");
         	  		// show.setText(hruselea+"+"+hruse+"/"+b+" "+emp_id);
         	  			 if(type.equals("�S��")){
         	  				 if(notes.equals("1"))
         	  				 {
         	  				  dbempup.executeQuery(emp_id,d,h);//db update
         	  				 }
         	  				 else{
         	  					
         	  			     dbempup.executeQuery(emp_id,b,f);//db update
         	  				 } 
         	  			 
         	  			 }
         	  			 //////////////
         	  			String mycomp=jsonData2.getString("comp");
         	  			 double com=Double.valueOf(mycomp).doubleValue()-Double.valueOf(hruselea).doubleValue();
         	  			// String comp=com+"";
         	  			double com2=Double.valueOf(mycomp).doubleValue()+Double.valueOf(hruselea).doubleValue();
         	  			//String f=com2+"";
         	  			 if(type.equals("�ɥ�")){
         	  				 if(notes.equals("1"))
         	  				 {dbempcom.executeQuery(emp_id,com+"");
         	  				  //dbempup.executeQuery(emp_id,f);//db update
         	  				 }
         	  				 else{dbempcom.executeQuery(emp_id,com2+"");
         	  			    // dbempup.executeQuery(emp_id,e);//db update
         	  				 } 
         	  			 
         	  			 }
         	  			  
         	  			 ////////////
         	  			  
         	  		 } 
         	  		     }
         	 		}
         	 		
         	 		}
         		new AlertDialog.Builder(check.this)
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
        	Intent intent = new Intent(check.this, check.class);
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