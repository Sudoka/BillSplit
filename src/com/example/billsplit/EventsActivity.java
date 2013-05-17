package com.example.billsplit;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class EventsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.events, menu);
		return true;
	}
	
	public void btn_create_event_Clicked(View v)
	{
		String ID = "[CREATE_NEW]";
		
		Intent intent = new Intent(this, EventMainActivity.class);
		intent.putExtra(EventMainActivity.ARG_ID, ID);
		startActivity(intent);
	}

}
