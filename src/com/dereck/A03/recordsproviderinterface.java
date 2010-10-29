package com.dereck.A03;

import android.net.Uri;
import android.provider.BaseColumns;

public class recordsproviderinterface implements BaseColumns {
	public recordsproviderinterface(){
	}

	//URI
	public static final String AUTHORITY = "com.dereck.A03.record";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/records");
	//tables
	//records table
	public static final String NAME = "name";
	public static final String PRICE = "price";
	public static final String CREATE = "time";
	public static final String[] RECORDS_TABLE ={
		_ID,//0
		NAME,//1
		PRICE,//2
		CREATE,//3
	};

}

