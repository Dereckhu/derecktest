package com.dereck.A03;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class add extends Activity implements OnClickListener,Runnable{
	private static final String TAG= "add"; 
	private Button savebutton;
	private ProgressDialog pd;
	private EditText nameText;
	private EditText priceText;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        
        nameText = (EditText)findViewById(R.id.EditText01);
        priceText = (EditText)findViewById(R.id.EditText02);
        savebutton = (Button)findViewById(R.id.Button05);
        savebutton.setOnClickListener(this);
    }
    
    public void onClick(View view)
    {
    	if(view.getId() == R.id.Button05)
    	{
			String text = nameText.getText().toString();
			if(text.length()==0){	
				/*md = new Askdialog(add.this);
				md.show();*/
	            new AlertDialog.Builder(add.this)
	            .setIcon(R.drawable.icon)
	            .setTitle("Error")
	            .setMessage("The name can not be empty!")
	            .setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int whichButton) {
	                }
	            }).show();				
			}else{
	    		pd = ProgressDialog.show(this,"","Insertting...",true,false);
	    		Thread thread = new Thread(this);
	    		thread.start();
			}
    	}
    }

	public void run() {
		//add item into table
		//construct the contentValue
		ContentValues values = new ContentValues();
        values.put(recordsproviderinterface.NAME, nameText.getText().toString());
        values.put(recordsproviderinterface.PRICE, priceText.getText().toString());
        Long now = Long.valueOf(System.currentTimeMillis());
        values.put(recordsproviderinterface.CREATE, now.toString());
        Log.d(TAG+"insert","name is "+nameText.getText().toString()+";price is "+
        		priceText.getText().toString()+" currenttime is "+now);
		getContentResolver().insert(getIntent().getData(), values);
		//here to check if insertion successful.//underconstruction!
		//��������ﵯ���Ի��򣬻����ڴ��̱߳�ע�������⣨RUN���н�����ᱻע�������Ҫ����һ������߳���ȥִ�е����Ի���ĳ�ʽ��
		handler.sendEmptyMessage(0);
	}
	private Handler handler = new Handler(){//���ｫһ��HANDLER�󶨵����ACTIVITY INSTANCE���ڵ��̡߳�
		@Override
		public void handleMessage(Message msg)
		{
			pd.cancel();
			//ask user to add another one?
			/*Nextdialog nextdialog = new Nextdialog(add.this);
			nextdialog.show();*/
            new AlertDialog.Builder(add.this)
            .setIcon(R.drawable.icon)
            .setTitle("Question")
            .setMessage("Do you want to type in anotherone?")
            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	nameText.setText("");
                	priceText.setText("");
                }
            })
            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	finish();
                }
            }).show();			
		}
	};
}
