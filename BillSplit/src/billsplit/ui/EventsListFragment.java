package billsplit.ui;

import android.os.Bundle;
import android.app.ListFragment;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EventsListFragment extends ListFragment {

	String[] items;

	/*
	 * @Override public View onCreateView(LayoutInflater inflater, ViewGroup
	 * container, Bundle savedInstanceState) { return
	 * inflater.inflate(R.layout.activity_events_list_fragment, container,
	 * false); }
	 */

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String[] itemsTmp = { "Apartment Rent", "Camping Trip", "Dinner",
				"Party", "Event X", "Event Y", "Event Z" };
		items = itemsTmp;
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, items));

		// uncomment these lines to enable personalized format
		// setListAdapter(new ArrayAdapter<String>(getActivity(),
		// R.layout.simple_list_item, R.id.item_text, items));

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO: go to next screen when item is selected

		String ID = items[position];

		Intent intent = new Intent(getActivity(), EventActivity.class);
		intent.putExtra(EventActivity.ARG_ID, ID);
		startActivity(intent);

	}

}
