package com.dereck.A03;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class queryway extends Activity implements OnClickListener,Runnable{
	private static final String TAG= "queryway"; 
	private Button tbutton;
	private Button pbutton;
	//private mydialog md;
	private ProgressDialog pd;
	private String message ="";
	private static boolean wayflag;
	private View textEntryView;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.queryway);
        
        tbutton = (Button)findViewById(R.id.Button03);
        pbutton = (Button)findViewById(R.id.Button04);   
        tbutton.setOnClickListener(this);//onclick method registered.
        pbutton.setOnClickListener(this);        
    }

    public void onClick(View view)
    {
    	String ms = null;
    	switch (view.getId())
    	{
    	case R.id.Button03:
    		//md = new mydialog(queryway.this,"time");
    		//md.show();
    		ms = "Query in time";
    		queryway.wayflag = true;
    		break;
    	case R.id.Button04:
    		//md = new mydialog(queryway.this,"price");
    		//md.show();   
    		ms = "Query in price";
    		queryway.wayflag = false;
    		break;
    	}

        LayoutInflater factory = LayoutInflater.from(this);
        textEntryView = factory.inflate(R.layout.myquerytypein, null);
	      new AlertDialog.Builder(queryway.this)
          .setIcon(R.drawable.icon)
          .setTitle(ms)
          .setView(textEntryView)
          .setPositiveButton("QUERY", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int whichButton) {
            		pd = ProgressDialog.show(queryway.this,"","Querying...",true,false);
            		Thread thread = new Thread(queryway.this);
            		thread.start();            	  
              }
          })
          .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int whichButton) {
            	  
              }
          }).show();    	
    }
	public void run() {
		//query records table
		//query way construction
		Log.d(TAG, "run:" + queryway.wayflag);
		String selection = "";
    	EditText sEdit =(EditText)textEntryView.findViewById(R.id.myquerytypeinEditText01);		
    	EditText xEdit =(EditText)textEntryView.findViewById(R.id.myquerytypeinEditText02);		

    	String keyword = sEdit.getText().toString();	
    	String keyword1 = xEdit.getText().toString();
    
		if(queryway.wayflag == true)//in time
		{
	    	if(keyword!=""&&keyword1!=""&& Long.valueOf(keyword)<Long.valueOf(keyword1) ){
	        	selection = recordsproviderinterface.CREATE +"<="+ Long.valueOf(keyword1) +" and " + 
	        	recordsproviderinterface.CREATE + ">=" + Long.valueOf(keyword);
	    	}    				
		}
		else if(queryway.wayflag == false)//in price
		{
	    	if(keyword!=""&&keyword1!=""&& Long.valueOf(keyword)<Long.valueOf(keyword1) ){
	        	selection = recordsproviderinterface.PRICE +"<="+ Long.valueOf(keyword1) +" and " + 
	        	recordsproviderinterface.PRICE + ">=" + Long.valueOf(keyword);
	    	}    				 				
		}
		else throw new IllegalArgumentException("queryway.wayflag is wrong!");

		Log.d(TAG,"selection=" + selection);	
    	Cursor tCursor = managedQuery(getIntent().getData(), recordsproviderinterface.RECORDS_TABLE, selection, null, null);
        if(tCursor.getCount()>0){
        	tCursor.moveToFirst();
        	while(!tCursor.isAfterLast()){
                message += "Name��"+ tCursor.getString(1)+"\n"
                        + "Price��"+ tCursor.getString(2) +"\n"
                        + "Time��"+ tCursor.getString(3) +"\n"
                        + "-----------------------\n";
                tCursor.moveToNext();
        	} 
        }else{
        	message ="Empty!";	
        } 		
		handler.sendEmptyMessage(0);
	}
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg)
		{
			pd.cancel();
            new AlertDialog.Builder(queryway.this)
            .setIcon(R.drawable.icon)
            .setTitle("Result")
            .setMessage(message)
            .setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            }).show();			
		}		
	};
}
