package billsplit.ui;

import java.util.ArrayList;
import java.util.List;

import com.billsplit.R;

//import com.billsplit.R;
import billsplit.engine.*;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CreateSelectEventActivity extends Activity {

	EventAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_create_select);

		
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
			 adapter = new EventAdapter(this,android.R.layout.simple_list_item_1, Account.getCurrentAccount().getEvents());
		} else {
			List<Event> list = new ArrayList<Event>();
			 adapter = new EventAdapter(this,android.R.layout.simple_list_item_1, list);
		}
		
		ListView events = (ListView) findViewById(R.id.events_list);
		OnItemClickListener eventClicked = new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position,
					long id) {
				Intent intent = new Intent(getApplicationContext(),
						EventActivity.class);
				Event e = (Event) v.getTag();
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

	public void btn_create_event_Clicked(View v) {
		String eventName = "New Event "+ String.valueOf(Account.getCurrentAccount().getEvents().size()+1);

		//Event newEvent = new Event(Account.getCurrentAccount().getGID(),"New Event"+ Account.getCurrentAccount().getEvents().size()+1);
		Event newEvent = Account.getCurrentAccount().createEvent(eventName);
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
	}

}
