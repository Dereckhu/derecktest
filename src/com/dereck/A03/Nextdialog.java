package com.dereck.A03;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class Nextdialog extends Dialog implements OnClickListener{
	private Button yesbutton;
	private Button nobutton;
	public Nextdialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nextdialog);
		yesbutton = (Button)findViewById(R.id.nextdialogButton01);
		nobutton = (Button)findViewById(R.id.nextdialogButton02);
		yesbutton.setOnClickListener(this);
		nobutton.setOnClickListener(this);
	}
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.nextdialogButton01://yes add another one
			//clean the editText buffer
			break;
		case R.id.nextdialogButton02://no,i don't add anymore
			//delete the add activity in the activity stack
			break;
		}
		cancel();
	}	
	
}