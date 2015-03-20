package com.example.finalproject;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Toast.makeText(this, "Welcome, Customer!", Toast.LENGTH_LONG).show();
	}
	
	public void clientButton(View v)
	{
		Toast.makeText(this, "Client", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(this, ClientActivity.class);
		
		startActivity(intent);
	}
	
	public void itemButton(View v)
	{
		Toast.makeText(this, "Inventory", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(this, InventoryActivity.class);
		
		startActivity(intent);
	}
	
	public void orderButton(View v)
	{
		Toast.makeText(this, "Order", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(this, OrderActivity.class);
		
		startActivity(intent);
		
	}
	
	public void summaryButton(View v)
	{
		Toast.makeText(this, "Summary", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(this, SummaryActivity.class);
		
		startActivity(intent);
	}
	
	public void exitButton(View v)
	{
		Toast.makeText(this, "Exit", Toast.LENGTH_LONG).show();
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
