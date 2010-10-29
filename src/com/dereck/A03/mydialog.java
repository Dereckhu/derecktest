package com.dereck.A03;



import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class mydialog extends Dialog implements OnClickListener,Runnable{
	private Button savebutton;
	private ProgressDialog pd;
	private String queryway;//"time"-"price"
	private String message ="";
	public mydialog(Context context,String way) {
		super(context);
		queryway = way;
	}
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mydialog);
		savebutton = (Button)findViewById(R.id.mydialogButton01);
		
		savebutton.setOnClickListener(this);
	}
	public void onClick(View view)
	{
    	switch (view.getId())
    	{
    	case R.id.mydialogButton01:
    		//start a progress indication dialog
    		pd = ProgressDialog.show(getContext(), "Working...", "Query table.",true,false);;
    		Thread thread = new Thread(this);
    		thread.start();
    		break;
    	}		
	}
	public void run() {
		//query the table
		//String selection = "";
		if(queryway == "time")//in time
		{
        	//selection = null;		
		}
		else if(queryway == "price")//in price
		{
			//selection = null;	
		}
		//else here can add a exeption pop!
		//Cursor tCursor = managedQuery(getIntent().getData(), ContactColumn.PROJECTION, selection, null, null);	
        /*if(tCursor.getCount()>0){
        	tCursor.moveToFirst();
        	while(!tCursor.isAfterLast()){
                message += "��˾��"+ tCursor.getString(4)+"\n"
                        + "����"+ tCursor.getString(ContactColumn.NAME_COLUMN) +"\n"
                        + "�绰��"+ tCursor.getString(ContactColumn.MOBILE_COLUMN) +"\n"
                        + "���䣺"+ tCursor.getString(ContactColumn.EMAIL_COLUMN) +"\n"
                        + "-----------------------\n";
                tCursor.moveToNext();
        	} 
        }else{
        	message ="�Ҳ����ü�¼��";	
        }*/
		message = "underconstruction!";
		handler.sendEmptyMessage(0);		
	}
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg)
		{
			pd.cancel();
			//popup the data list
            new AlertDialog.Builder(getContext())
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
