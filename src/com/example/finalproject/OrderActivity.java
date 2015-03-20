package com.example.finalproject;

import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

public class OrderActivity extends ActionBarActivity {
	
	public static final int ADD_PRESSED = 0;
	public static final int BACK_PRESSED = 3;
	public static final int EDIT_PRESSED = 4;
	
	private OrderDbAdapter dbHelper;
	private SimpleCursorAdapter dataAdapter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		
		// INSTANCE DB ADAPTER
				dbHelper = new OrderDbAdapter(this);
				
				// OPEN
				dbHelper.open();

				
				// DO ACTIONS
				// Clean all data
				//dbHelper.deleteAllOrders();

				//System.out.println("SEED DB");
				// Add some data
				//dbHelper.seed();
				
				// QUERY
				// print contents of DB
				Cursor cursor = dbHelper.fetchAllOrders();
				
				dataAdapter = new SimpleCursorAdapter (this, R.layout.order_info, cursor, 
						new String[] { OrderDbAdapter.KEY_ITEMNAME, OrderDbAdapter.KEY_QUANTITY, OrderDbAdapter.KEY_TOTALCOST, OrderDbAdapter.KEY_NAME, OrderDbAdapter.KEY_COMMENT },
						new int[] { R.id.itemName, R.id.quantityText, R.id.totalCost, R.id.clientName, R.id.commentText }, 
						0);
				
				ListView listView = (ListView) findViewById(R.id.listView1);
				listView.setAdapter(dataAdapter);
				
				// NOTE: REGISTER listview for context menu
		        registerForContextMenu(listView);
		        
		     // clicking an item in the listview
				listView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> listView, View view,
							int position, long id) {
						// Get the cursor, positioned to the corresponding row in the
						// result set
						Cursor cursor = (Cursor) listView.getItemAtPosition(position);

						// Get the client's name from this row in the database.
						String name = cursor.getString(cursor.getColumnIndexOrThrow(OrderDbAdapter.KEY_NAME));
						
						// display in toast
						Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
					}
				});
				
				// this detects changes in the EditText as you type
				EditText myFilter = (EditText) findViewById(R.id.myFilter);
				myFilter.addTextChangedListener(new TextWatcher() {

					public void afterTextChanged(Editable s) {
					}

					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
					}

					public void onTextChanged(CharSequence s, int start, int before,
							int count) {

						// this tells the adapter to trigger the filter
						dataAdapter.getFilter().filter(s.toString());
					}
				});
				
				// indicates what query will be run when filtering
				dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
					public Cursor runQuery(CharSequence constraint) {
						return dbHelper.fetchOrderByName(constraint.toString());
					}
				});		
	}
	
	public void updateDatabase()
	{
		Cursor cursor = dbHelper.fetchAllOrders();
		dataAdapter = new SimpleCursorAdapter (this, R.layout.order_info, cursor, 
				new String[] { OrderDbAdapter.KEY_ITEMNAME, OrderDbAdapter.KEY_QUANTITY, OrderDbAdapter.KEY_TOTALCOST, OrderDbAdapter.KEY_NAME, OrderDbAdapter.KEY_COMMENT },
				new int[] { R.id.itemName, R.id.quantityText, R.id.totalCost, R.id.clientName, R.id.commentText}, 
				0);
		
		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(dataAdapter);
		
		dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
			public Cursor runQuery(CharSequence constraint) {
				return dbHelper.fetchOrderByName(constraint.toString());
			}
		});					
	}
	
	public void addPressed(View v)
	{
		Intent intent = new Intent(this, AddOrder.class);
		startActivityForResult(intent, ADD_PRESSED);
	}
	
	//Intent oldIntent;
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {

		if (intent == null)
		{
			return;
		}
        if (requestCode == ADD_PRESSED)
        {
        	if (resultCode == ADD_PRESSED) {
            	//oldIntent = intent;
            	
            	String s1 = intent.getStringExtra("itemName");
            	String s2 = intent.getStringExtra("quantity");
            	String s3 = intent.getStringExtra("totalCost");
            	String s4 = intent.getStringExtra("clientName");
            	String s5 = intent.getStringExtra("comment");
            	
            	if ((s1.equals("")) || (s2.equals("")) || (s4.equals("")))
				{
					return;
				}
				
				dbHelper.createOrder(s1, s2, s3, s4, s5);
				updateDatabase();
				Toast.makeText(this, "Added " + s1, Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == EDIT_PRESSED)
        {
        	if (resultCode == ADD_PRESSED)
        	{
        	//	oldIntent = intent;
        		String s1 = intent.getStringExtra("itemName");
            	String s2 = intent.getStringExtra("quantity");
            	String s3 = intent.getStringExtra("totalCost");
            	String s4 = intent.getStringExtra("clientName");
            	String s5 = intent.getStringExtra("comment");
            	long l1 = intent.getLongExtra("id", -1);
            	
            	if ((s1.equals("")) || (s2.equals("")) || (s4.equals("")))
				{
					return;
				}
				
				dbHelper.editOrder(l1, s1, s2, s3, s4, s5);
				updateDatabase();
				Toast.makeText(this, "Edited " + s1, Toast.LENGTH_LONG).show();
        	}
        }
    }
	
	@Override
    public void onBackPressed()
    {
            Toast.makeText(this, "Back to Main Menu", Toast.LENGTH_LONG).show();
            super.onBackPressed();
            finish();
    }
	
	@Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
    {
    	super.onCreateContextMenu(menu, v, menuInfo);
    	MenuInflater inflater = getMenuInflater();
    	
    	// places the contents of the XML to the menu
    	inflater.inflate(R.menu.order_menu, menu);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
      AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
      switch (item.getItemId()) {
      case R.id.edit:
    	  Intent intent = new Intent (this, AddOrder.class);
    	  ListView listView = (ListView) findViewById(R.id.listView1);
    	  Cursor cursor = (Cursor) listView.getItemAtPosition(info.position);
    	  
    	  intent.putExtra("id", info.id);
    	  intent.putExtra("itemName", cursor.getString(cursor.getColumnIndexOrThrow(OrderDbAdapter.KEY_ITEMNAME)));
    	  intent.putExtra("quantity", cursor.getString(cursor.getColumnIndexOrThrow(OrderDbAdapter.KEY_QUANTITY)));
    	  intent.putExtra("clientName", cursor.getString(cursor.getColumnIndexOrThrow(OrderDbAdapter.KEY_NAME)));
    	  intent.putExtra("comment", cursor.getString(cursor.getColumnIndexOrThrow(OrderDbAdapter.KEY_COMMENT)));
    	
    	  startActivityForResult(intent, EDIT_PRESSED);
    	return true;
      case R.id.delete:
    	// delete the currently selected row
    	  ListView listView1 = (ListView) findViewById(R.id.listView1);
    	  Cursor cursor1 = (Cursor) listView1.getItemAtPosition(info.position);
    	  String s1 = cursor1.getString(cursor1.getColumnIndexOrThrow(OrderDbAdapter.KEY_NAME));
    	  String s2 = cursor1.getString(cursor1.getColumnIndexOrThrow(OrderDbAdapter.KEY_ITEMNAME));
    	  
    	  Toast.makeText(this, "Deleted " + s1 + "'s " + s2 + " Order.", Toast.LENGTH_LONG).show();
    	  dbHelper.deleteOrder(info.id);
    	  updateDatabase();
        return true;
      
      default:
        return super.onContextItemSelected(item);
      }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.order, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
