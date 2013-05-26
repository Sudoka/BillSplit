package billsplit.ui;
import java.util.List;

import billsplit.engine.Event;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EventAdapter extends ArrayAdapter<Event> {

	List<Event> eventsList = null;
	
	public EventAdapter(Context context, int layoutResourceId,
			List<Event> objects) {
		super(context, layoutResourceId, objects);
		eventsList = objects;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		TextView txt = new TextView(getContext());
		txt.setText(eventsList.get(position).getName());
		
		txt.setTag(eventsList.get(position));
		return txt;
		
	}

	
	
	
	

}
