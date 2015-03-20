package com.example.finalproject;



import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class OrderDbAdapter {
	
	// DO NOT REMOVE THIS ID
			public static final String KEY_ROWID = "_id";
			
			private DatabaseHelper mDbHelper;
			private SQLiteDatabase mDb;

			private static final int DATABASE_VERSION = 1;

			private final Context mCtx;

			// DATABASE NAME
			private static final String DATABASE_NAME = "OrderMenu";
				
			// TABLE NAME GOES HERE
			private static final String SQLITE_TABLE = "OrderMenu";

			// COLUMNS NAMES GO HERE
			public static final String KEY_ITEMNAME = "itemName";
			public static final String KEY_QUANTITY = "quantity";
			public static final String KEY_TOTALCOST = "totalCost";
			public static final String KEY_NAME = "clientName";
			public static final String KEY_COMMENT = "comment";
			
			// TABLE CREATION
			private static final String DATABASE_CREATE = 
					
					"CREATE TABLE if not exists "
							+ SQLITE_TABLE + 
							" (" 
							   + KEY_ROWID + " integer PRIMARY KEY autoincrement," 
							   
							   // YOU ADD COLUMNS HERE
										   + KEY_ITEMNAME + ","
										   + KEY_QUANTITY + ","
										   + KEY_TOTALCOST + ","
										   + KEY_NAME + ","
										   + KEY_COMMENT +
							");";
			
			
			// UTILITY TABLE HELPER CLASS -- LEAVE THIS ALONE
			
			private static class DatabaseHelper extends SQLiteOpenHelper {

				DatabaseHelper(Context context) {
					super(context, DATABASE_NAME, null, DATABASE_VERSION);
				}
				
				// DATABASE CREATION
				@Override
				public void onCreate(SQLiteDatabase db) {
					db.execSQL(DATABASE_CREATE);
				}
				
				// DATABASE CHANGE
				@Override
				public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
					db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
					onCreate(db);
				}
			}
			
			// LIFE CYCLE
			
			public OrderDbAdapter(Context ctx) {
				this.mCtx = ctx;
			}

			public OrderDbAdapter open() throws SQLException {
				mDbHelper = new DatabaseHelper(mCtx);
				mDb = mDbHelper.getWritableDatabase();
				
				
				return this;
			}

			public void close() {
				if (mDbHelper != null) {
					mDbHelper.close();
				}
			}
			
			// ADD NEW STUFF HERE
			
			// ACTIONS
			
			// ADD ROW
			public long createOrder(String itemName, String quantity, String totalCost, String clientName, String comment) {

				
				// INSERT
				ContentValues initialValues = new ContentValues();
				initialValues.put(KEY_ITEMNAME, itemName);
				initialValues.put(KEY_QUANTITY, quantity);
				initialValues.put(KEY_TOTALCOST, totalCost);
				initialValues.put(KEY_NAME, clientName);
				initialValues.put(KEY_COMMENT, comment);

				// parameters
				// mDb.insert(table, nullColumnHack, values);
				
				return mDb.insert(SQLITE_TABLE, null, initialValues);
			}
			
			public boolean editOrder(long id, String itemName, String quantity, String totalCost, String clientName, String comment) {
				
				// INSERT
				ContentValues editValues = new ContentValues();
				editValues.put(KEY_ROWID, id);
				editValues.put(KEY_ITEMNAME, itemName);
				editValues.put(KEY_QUANTITY, quantity);
				editValues.put(KEY_TOTALCOST, totalCost);
				editValues.put(KEY_NAME, clientName);
				editValues.put(KEY_COMMENT, comment);
				
				// parameters
				// mDb.insert(table, nullColumnHack, values);
				
				int i = mDb.update(SQLITE_TABLE, editValues, KEY_ROWID + "=" + id, null);
				return i > 0;
			}
			
			// DELETE ALL
			public boolean deleteAllOrders() {

				// DELETE
				// parameters
				// mDb.delete(table, whereClause, whereArgs)
				
				int doneDelete = 0;
				doneDelete = mDb.delete(SQLITE_TABLE, null, null);
				return doneDelete > 0;
			}
			
			// DELETE ONE ORDER
				public boolean deleteOrder(long id) {

					// DELETE
					// parameters
					// mDb.delete(table, whereClause, whereArgs)
					
					int doneDelete = 0;
					doneDelete = mDb.delete(SQLITE_TABLE, KEY_ROWID + " = "+id, null);
					return doneDelete > 0;
				}
				
				public Cursor fetchOrderByName(String inputText) throws SQLException {
					Cursor mCursor = null;
					if (inputText == null || inputText.length() == 0) {
						
						mCursor = mDb.query(SQLITE_TABLE, 
											new String[] { KEY_ROWID,
								
														   // CHANGE COLUMNS -- May be all or just a few
														   KEY_ITEMNAME,  
														   KEY_QUANTITY,
														   KEY_TOTALCOST,
														   KEY_NAME,
														   KEY_COMMENT
														   }, 
														   
											null, null, null, null, null);
					} else {
						mCursor = mDb.query(true, 
											SQLITE_TABLE, 
											new String[] { KEY_ROWID,
								
														   // CHANGE COLUMNS -- May be all or just a few					
														   KEY_ITEMNAME,
														   KEY_QUANTITY,
														   KEY_TOTALCOST,
														   KEY_NAME,
														   KEY_COMMENT },
														   
											// searching similar			   
											KEY_NAME + " like '%" + inputText+ "%'",
											
											// DO NOT CHANGE THIS
											null, null, null, null, null);
					}
					if (mCursor != null) {
						mCursor.moveToFirst();
					}
					return mCursor;
				}
				public Cursor fetchAllOrders() {

					// parameter descriptions
					// mDb.query(table, columns, selection, selectionArgs, groupBy, having, orderBy)

					Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] { KEY_ROWID,
							KEY_ITEMNAME, KEY_QUANTITY, KEY_TOTALCOST, KEY_NAME, KEY_COMMENT }, null, null, null, null, null);

					if (mCursor != null) {
						mCursor.moveToFirst();
					}
					return mCursor;
				}
				
				// SEEDING
				/*ArrayList listOrder =  new ArrayList();
				public void seed() {

					// the Context is the Activity where this is currently used
					String[] orderData = mCtx.getResources().getStringArray(R.array.order);
					
					// get string-array, parse and store
					for (String r : orderData) {
						String[] data = r.split(";");
						createOrder(data[0], data[1], data[2], data[3]);
					}
				}*/
}
