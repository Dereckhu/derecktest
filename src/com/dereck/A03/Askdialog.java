package com.dereck.A03;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class Askdialog extends Dialog implements OnClickListener{
	private Button okbutton;
	public Askdialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.askdialog);
		okbutton = (Button)findViewById(R.id.askdialogButton01);
		okbutton.setOnClickListener(this);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		cancel();
	}	
	
}
