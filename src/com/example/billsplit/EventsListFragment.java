package com.example.billsplit;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class EventsListFragment extends ListFragment {

	/*@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
	        Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_events_list_fragment, container, false);
	}
*/
	
	 @Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	        super.onActivityCreated(savedInstanceState);
		String[] items = { "Apartment Rent", "Camping Trip", "Dinner", "Party", "Event X", "Event Y", "Event Z" };
		
		setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, items));
		
		//uncomment these lines to enable personalized format
		//setListAdapter(new ArrayAdapter<String>(getActivity(),
          //      R.layout.simple_list_item, R.id.item_text, items));

	}
	
}
