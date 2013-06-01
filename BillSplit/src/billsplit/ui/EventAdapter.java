package billsplit.ui;
import java.util.List;

import com.billsplit.R;

import billsplit.engine.Event;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EventAdapter extends ArrayAdapter<Event> {

	List<Event> eventsList = null;
	private int layoutResourceId;
	private Context context;
	
	public EventAdapter(Context context, int layoutResourceId,
			List<Event> objects) {
		super(context, layoutResourceId, objects);
		eventsList = objects;
		this.layoutResourceId = layoutResourceId;
        this.context = context;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        v = inflater.inflate(layoutResourceId, parent, false);
        
        
		TextView txt = (TextView) v.findViewById(R.id.item_text);
		txt.setText(eventsList.get(position).getName());
		
		txt.setTag(eventsList.get(position));
		return v;
		
	}

	
	
	
	

}
