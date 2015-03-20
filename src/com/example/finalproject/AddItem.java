package com.example.finalproject;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddItem extends ActionBarActivity {

	public static final int ADD_PRESSED = 0;
	public static final int GET_CLIENT = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_item);
		
		Intent data = getIntent();
		EditText et1 = (EditText) findViewById(R.id.itemName);
		EditText et2 = (EditText) findViewById(R.id.cost);
		EditText et3 = (EditText) findViewById(R.id.stock);
		
		if (data != null)
		{
			et1.setText(data.getStringExtra("itemName"));
			et2.setText(data.getStringExtra("cost"));
			et3.setText(data.getStringExtra("stock"));
		}
	}
	
	
	public void addPressed(View v)
	{
		Intent data = getIntent();
		
		EditText et1 = (EditText) findViewById(R.id.itemName);
		EditText et2 = (EditText) findViewById(R.id.cost);
		EditText et3 = (EditText) findViewById(R.id.stock);
		String s1 = et1.getText().toString();
		String s2 = et2.getText().toString();
		String s3 = et3.getText().toString();
		if ((s1.equals("")) || (s2.equals("")) || (s3.equals("")))
		{
			if (s1.equals(""))
				{
				Toast.makeText(this, "Item name must have a value", Toast.LENGTH_LONG).show();
				}
			else if (s2.equals(""))
			{
				Toast.makeText(this, "Cost must have a value", Toast.LENGTH_LONG).show();
			}
			else if (s3.equals(""))
			{
				Toast.makeText(this, "Stock must have a value", Toast.LENGTH_LONG).show();
			}
			else if ((s1.equals("")) && (s2.equals("")) && (s3.equals("")))
			{
			Toast.makeText(this, "Item name must have a value", Toast.LENGTH_LONG).show();
			}
		}
		else
		{
			data.putExtra("itemName", s1);
			data.putExtra("cost", s2);
			data.putExtra("stock", s3);
			setResult(ADD_PRESSED, data);
			finish();
		}
	}
	
	public void cancelPressed(View v)
	{
		Toast.makeText(this, "Cancel", Toast.LENGTH_LONG).show();
		finish();
	}
	
	@Override
    public void onBackPressed()
    {
        Toast.makeText(this, "Back to Item List", Toast.LENGTH_LONG).show();
        //Intent intent = oldIntent;
        //setResult(BACK_PRESSED, intent);
        super.onBackPressed();
        finish();
            
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_item, menu);
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
