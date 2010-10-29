package com.dereck.A03;

import com.dereck.A03.DBHelper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class recordsprovider extends ContentProvider {
	private static final String TAG= "recordsprovider"; 
	private DBHelper dbHelper;
	private SQLiteDatabase contactsDB;
	public static final int RECORDS = 1;
	public static final int RECORD_ID = 2;
	private static final UriMatcher uriMatcher;	
	
	static{
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(recordsproviderinterface.AUTHORITY,"records",RECORDS);
		uriMatcher.addURI(recordsproviderinterface.AUTHORITY,"records/#",RECORD_ID);
	}

	@Override
	public boolean onCreate() {
		dbHelper = new DBHelper(getContext());
		contactsDB = dbHelper.getWritableDatabase();
		Log.d(TAG+"create","onCreate"+contactsDB.toString());
		return (contactsDB == null)? false : true;
	}
	
	@Override
	public int delete(Uri uri, String where, String[] selectionArgs) {
		int count = 0;
		switch (uriMatcher.match(uri)) {
			case RECORDS://delete the table
				count = contactsDB.delete(DBHelper.RECORDS_TABLE, where, selectionArgs);
				break;
			case RECORD_ID://delete a row

				break;
			default: throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		return count;
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		if (uriMatcher.match(uri) != RECORDS) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
			Log.d(TAG+"insert","initialValues is not null");
		} else {
			values = new ContentValues();
		}
		
		if (values.containsKey(recordsproviderinterface.NAME) == false) {
			values.put(recordsproviderinterface.NAME, "");
		}
		if (values.containsKey(recordsproviderinterface.PRICE) == false) {
			values.put(recordsproviderinterface.PRICE, "");
		}
		if (values.containsKey(recordsproviderinterface.CREATE) == false) {
			values.put(recordsproviderinterface.CREATE, "");
		}
		
		Log.d(TAG+"insert",values.toString());
		
		long rowId = contactsDB.insert(DBHelper.RECORDS_TABLE, null, values);
		if (rowId > 0) {
			Uri noteUri = ContentUris.withAppendedId(recordsproviderinterface.CONTENT_URI,rowId);
			getContext().getContentResolver().notifyChange(noteUri, null);
			Log.d(TAG+"insert",noteUri.toString());
			return noteUri;
		}else{
		    throw new SQLException("Failed to insert row into " + uri);
		}		

	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(DBHelper.RECORDS_TABLE);
		
		switch (uriMatcher.match(uri)) {
			case RECORD_ID:
				qb.appendWhere(recordsproviderinterface._ID + "=" + uri.getPathSegments().get(1));
				break;
		}
		String orderBy;
		if (TextUtils.isEmpty(sortOrder)) {
			orderBy = recordsproviderinterface._ID;
		} else {
			orderBy = sortOrder;
		}
		Cursor c = qb.query(contactsDB,projection,
							selection, selectionArgs,
							null, null,orderBy);
		return c;	
	}

	@Override
	public int update(Uri uri, ContentValues values, String where, String[] selectionArgs) {

		Log.d(TAG+"update",values.toString());
		Log.d(TAG+"update",uri.toString());
		
		int count;		
		switch (uriMatcher.match(uri)) {
			case RECORDS:
				Log.d(TAG+"update",RECORDS+"");
				count = contactsDB.update(DBHelper.RECORDS_TABLE, values, where, selectionArgs);
				break;
			case RECORD_ID:
				String contactID = uri.getPathSegments().get(1);
				Log.d(TAG+"update",contactID+"");
				count = contactsDB.update(DBHelper.RECORDS_TABLE,values,
						recordsproviderinterface._ID + "=" + contactID, 
						selectionArgs);
				break;
			default: throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		return count;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case RECORDS:
		return "vnd.android.cursor.dir/vnd.A03.records";
		case RECORD_ID:
		return "vnd.android.cursor.item/vnd.A03.contacts";
		default:
		throw new IllegalArgumentException("Unsupported URI: " + uri);
	}
}

}
