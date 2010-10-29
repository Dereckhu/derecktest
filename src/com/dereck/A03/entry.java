package com.dereck.A03;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
//import android.widget.EditText;

public class entry extends Activity implements OnClickListener {
	private Button addbutton;
	private Button querywaybutton;
	
	//private EditText totalcount;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);
        if(getIntent().getData() == null)
        {
        	getIntent().setData(recordsproviderinterface.CONTENT_URI);
        }
        addbutton = (Button)findViewById(R.id.Button01);
        querywaybutton = (Button)findViewById(R.id.Button02);
        //totalcount = (EditText)findViewById(R.id.TextView02);
        
        addbutton.setOnClickListener(this);//onclick method registered.
        querywaybutton.setOnClickListener(this);
    }
    
    public void onClick(View view)
    {
    	switch (view.getId())
    	{
    	case R.id.Button01:
    		//start add activity
    		//getIntent().setAction(Intent.ACTION_EDIT);
    		startActivity(new Intent(Intent.ACTION_EDIT,getIntent().getData()));
    		break;
    	case R.id.Button02:
    		//start queryway activity
    		//getIntent().setAction(Intent.ACTION_INSERT);
    		startActivity(new Intent(Intent.ACTION_INSERT,getIntent().getData()));
    		break;
    	}
    }
}