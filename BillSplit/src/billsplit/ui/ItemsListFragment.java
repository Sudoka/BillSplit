package billsplit.ui;

import com.billsplit.R;
import com.billsplit.R.layout;
import com.billsplit.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ItemsListFragment extends ListFragment {

	String [] items;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String[] itemsTmp = { "Item 1", "Item 2", "Item 3",
				"Item 4", "Item 5", "Item 6", "Item 7" };
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
