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

public class SummaryActivity extends ActionBarActivity {

	
	public static final int ADD_PRESSED = 0;
	
	private ClientsDbAdapter dbHelper;
	private SimpleCursorAdapter dataAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_summary);
		
		// INSTANCE DB ADAPTER
		dbHelper = new ClientsDbAdapter(this);
		
		// OPEN
		dbHelper.open();

		
		// DO ACTIONS
		// Clean all data
		//dbHelper.deleteAllClients();

		//System.out.println("SEED DB");
		// Add some data
		//dbHelper.seed();
		
		
		// QUERY
		// print contents of DB
		Cursor cursor = dbHelper.fetchAllClients();
		
		dataAdapter = new SimpleCursorAdapter (this, R.layout.client_info, cursor, 
				new String[] { ClientsDbAdapter.KEY_NAME, ClientsDbAdapter.KEY_ADDRESS, ClientsDbAdapter.KEY_PHONE },
				new int[] { R.id.name, R.id.address, R.id.phone }, 
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
				String name = cursor.getString(cursor.getColumnIndexOrThrow(ClientsDbAdapter.KEY_NAME));
				
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
				return dbHelper.fetchClientsByName(constraint.toString());
			}
		});

	}

	/*public void updateDatabase()
	{
		Cursor cursor = dbHelper.fetchAllClients();
		dataAdapter = new SimpleCursorAdapter (this, R.layout.client_info, cursor, 
				new String[] { ClientsDbAdapter.KEY_NAME, ClientsDbAdapter.KEY_ADDRESS, ClientsDbAdapter.KEY_PHONE },
				new int[] { R.id.name, R.id.address, R.id.phone }, 
				0);
		
		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(dataAdapter);
		
		dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
			public Cursor runQuery(CharSequence constraint) {
				return dbHelper.fetchClientsByName(constraint.toString());
			}
		});					
	}*/
	
	@Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
    {
    	super.onCreateContextMenu(menu, v, menuInfo);
    	MenuInflater inflater = getMenuInflater();
    	
    	// places the contents of the XML to the menu
    	inflater.inflate(R.menu.client_menu, menu);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
      AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
      switch (item.getItemId()) {
      case R.id.add:
    	  ListView listView = (ListView) findViewById(R.id.listView1);
    	  Cursor cursor = (Cursor) listView.getItemAtPosition(info.position);
    	  
    	  Intent data = new Intent(this, LastSummaryActivity.class);

    	  String s1 = cursor.getString(cursor.getColumnIndexOrThrow(ClientsDbAdapter.KEY_NAME));
    	  data.putExtra("id", info.id);
    	  data.putExtra("name", s1);
    	  
    	  Toast.makeText(this, "Client: " + s1, Toast.LENGTH_LONG).show();
    	  
    	  startActivity(data);
    	return true;
      case R.id.cancel:
    	  Toast.makeText(this, "Cancel", Toast.LENGTH_LONG).show();
    	  finish();
    	return true;
      default:
        return super.onContextItemSelected(item);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.summary, menu);
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
