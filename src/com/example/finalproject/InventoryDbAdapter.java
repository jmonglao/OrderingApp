package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InventoryDbAdapter {

	// DO NOT REMOVE THIS ID
			public static final String KEY_ROWID = "_id";
			
			private DatabaseHelper mDbHelper;
			private SQLiteDatabase mDb;

			private static final int DATABASE_VERSION = 1;

			private final Context mCtx;

			// DATABASE NAME
			private static final String DATABASE_NAME = "Inventory";
				
			// TABLE NAME GOES HERE
			private static final String SQLITE_TABLE = "Inventory";

			// COLUMNS NAMES GO HERE
			public static final String KEY_NAME = "name";
			public static final String KEY_COST = "cost";
			public static final String KEY_STOCK = "stock";

			// TABLE CREATION
			private static final String DATABASE_CREATE = 
					
					"CREATE TABLE if not exists "
							+ SQLITE_TABLE + 
							" (" 
							   + KEY_ROWID + " integer PRIMARY KEY autoincrement," 
							   
							   // YOU ADD COLUMNS HERE
										   + KEY_NAME + ","
										   + KEY_COST + ","
										   + KEY_STOCK + 
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
			
			public InventoryDbAdapter(Context ctx) {
				this.mCtx = ctx;
			}

			public InventoryDbAdapter open() throws SQLException {
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
			public long createItem(String name, String cost, String stock) {

				
				// INSERT
				ContentValues initialValues = new ContentValues();
				initialValues.put(KEY_NAME, name);
				initialValues.put(KEY_COST, cost);
				initialValues.put(KEY_STOCK, stock);

				// parameters
				// mDb.insert(table, nullColumnHack, values);
				
				return mDb.insert(SQLITE_TABLE, null, initialValues);
			}
			
			public boolean editItem(long id, String name, String cost, String stock) {

				
				// INSERT
				ContentValues editValues = new ContentValues();
				editValues.put(KEY_ROWID, id);
				editValues.put(KEY_NAME, name);
				editValues.put(KEY_COST, cost);
				editValues.put(KEY_STOCK, stock);

				// parameters
				// mDb.insert(table, nullColumnHack, values);
				
				int i = mDb.update(SQLITE_TABLE, editValues, KEY_ROWID + " = " + id, null);
				return i > 0;
			}

			// DELETE ALL
			public boolean deleteAllItems() {

				// DELETE
				// parameters
				// mDb.delete(table, whereClause, whereArgs)
				
				int doneDelete = 0;
				doneDelete = mDb.delete(SQLITE_TABLE, null, null);
				return doneDelete > 0;

			}
			
			// DELETE ONE ITEM
				public boolean deleteItem(long id) {

					// DELETE
					// parameters
					// mDb.delete(table, whereClause, whereArgs)
					
					int doneDelete = 0;
					doneDelete = mDb.delete(SQLITE_TABLE, KEY_ROWID + " = " + id, null);
					return doneDelete > 0;

				}

			public Cursor fetchItemByName(String inputText) throws SQLException {
				Cursor mCursor = null;
				if (inputText == null || inputText.length() == 0) {
					
					mCursor = mDb.query(SQLITE_TABLE, 
										new String[] { KEY_ROWID,
							
													   // CHANGE COLUMNS -- May be all or just a few
													   KEY_NAME,
													   KEY_COST,
													   KEY_STOCK },
													   
										null, null, null, null, null);

				} else {
					mCursor = mDb.query(true, 
										SQLITE_TABLE, 
										new String[] { KEY_ROWID,
							
													   // CHANGE COLUMNS -- May be all or just a few					
													   KEY_NAME,
													   KEY_COST,
													   KEY_STOCK },
													   
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
			
			//TEST THIS FOR GETCOUNT OF ROW
		/*	public int getCount(long id) {

				Cursor cursor= mDb.rawQuery("SELECT COUNT (*) FROM " + SQLITE_TABLE + " WHERE " + KEY_ROWID + "=?",
				         new String[] { String.valueOf(id) });
				int count = 0;
				if(null != cursor)
				    if(cursor.getCount() > 0){
				      cursor.moveToFirst();    
				      count = cursor.getInt(0);
				    }
				    //cursor.close();
				    return count;
				}*/

				

			public Cursor fetchAllItems() {

				// parameter descriptions
				// mDb.query(table, columns, selection, selectionArgs, groupBy, having, orderBy)

				Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] { KEY_ROWID,
						KEY_NAME, KEY_COST, KEY_STOCK }, null, null, null, null, null);

				if (mCursor != null) {
					mCursor.moveToFirst();
				}
				return mCursor;
			}
			
			
			// SEEDING
			/*ArrayList list =  new ArrayList();
			public void seed() {

				// the Context is the Activity where this is currently used
				String[] clientData = mCtx.getResources().getStringArray(R.array.clients);
				
				// get string-array, parse and store
				for (String r : clientData) {
					String[] data = r.split(";");
					createClient(data[0], data[1]);
				}
			}*/
	
}
