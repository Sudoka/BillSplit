package billsplit.ui;

import java.util.ArrayList;
import java.util.List;

import com.billsplit.R;

//import com.billsplit.R;
import billsplit.engine.*;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CreateSelectEventActivity extends Activity {

	EventAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_create_select);

		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
		
		String GID = settings.getString(SettingsActivity.KEY_USER_GID,SettingsActivity.DEFAULT_VALUE_USER_GID);

		String userName = settings.getString(SettingsActivity.KEY_USER_NAME, SettingsActivity.DEFAULT_VALUE_USER_NAME);
		
		//Toast.makeText(getApplicationContext(), GID, Toast.LENGTH_LONG).show();
		Account.setCurrentAccount(Account.createNewAccount(GID, userName));
		
		  //Test code 
		/*
		List<Event> list = new ArrayList<Event>(); 
		  Event e1 = new Event("TEST", "Event 1"); 
		  list.add(e1); 
		  Event e2 = new Event("TEST", "Event 2"); 
		  list.add(e2); 
		  EventAdapter adapter = new EventAdapter(this, android.R.layout.simple_list_item_1, list );
		 */
		
		
		
		if (Account.getCurrentAccount() != null) {
			 adapter = new EventAdapter(this,R.layout.simple_list_item, Account.getCurrentAccount().getEvents());
		} else {
			List<Event> list = new ArrayList<Event>();
			 adapter = new EventAdapter(this,R.layout.simple_list_item, list);
		}
		
		ListView events = (ListView) findViewById(R.id.events_list);
		OnItemClickListener eventClicked = new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position,
					long id) {
				Intent intent = new Intent(getApplicationContext(),
						EventActivity.class);
				//TextView txt = (TextView) v.findViewById(R.id.item_text);
				Event e = (Event) v.getTag();
				Event.currentEvent = e;
				intent.putExtra(EventActivity.ARG_ID, e.getName());
				startActivity(intent);
			}
		};

		events.setOnItemClickListener(eventClicked);
		events.setAdapter(adapter);

	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.events, menu);
		return true;
	}

	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	        case R.id.action_settings:
	        	Intent settings = new Intent(this, SettingsActivity.class);
	        	startActivity(settings);
	        	return true;
	        	
	        
	        }
	        return false;
	 }
	public void btn_create_event_Clicked(View v) {
		String eventName = "New Event "+ String.valueOf(Account.getCurrentAccount().getEvents().size()+1);

		//Event newEvent = new Event(Account.getCurrentAccount().getGID(),"New Event"+ Account.getCurrentAccount().getEvents().size()+1);
		Event newEvent = Account.getCurrentAccount().createEvent(eventName);
	//	Account.getCurrentAccount().saveAccount();
		Event.currentEvent = newEvent;
		newEvent.addParticipant(new Participant(Account.getCurrentAccount()));
		newEvent.addParticipant(new Participant("Person"+String.valueOf(newEvent.getParticipants().size())));
		Intent intent = new Intent(this, EventActivity.class);
		intent.putExtra(EventActivity.ARG_ID, eventName);
		startActivity(intent);
	}
	
	@Override
	protected void onResume() {
		//ListView events = (ListView) findViewById(R.id.events_list);
		super.onResume();
		adapter.notifyDataSetChanged();
		Account.getCurrentAccount().updateAccount();
		
		TextView lblOWed = (TextView)findViewById(R.id.createselect_lbl_Owed);
		TextView lblOWing = (TextView)findViewById(R.id.createselect_lbl_Owing);
		TextView lblBalance = (TextView)findViewById(R.id.createselect_lbl_balance);
		
		lblOWed.setText("Owed: $"+String.valueOf(Account.getCurrentAccount().getTotalOwed()));
		lblOWing.setText("Owing: $"+String.valueOf(Account.getCurrentAccount().getTotalYouOwe()));
		lblBalance.setText("Balance: $"+String.valueOf(Account.getCurrentAccount().getNetBalance()));
		if(Account.getCurrentAccount().getNetBalance()>=0){
			//positive balance
			
		}
		else{
			//negative balance
		}
		
		
		
	}

}
