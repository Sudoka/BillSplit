package billsplit.ui;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        
        
		TextView txt = (TextView) v.findViewById(R.id.layout_txtItemName);
		txt.setText(eventsList.get(position).getName());
		
		TextView date = (TextView) v.findViewById(R.id.layout_txtDate);
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.US);   
		//date.setText(sdf.format(new Date(0)));
		date.setText(sdf.format(eventsList.get(position).getCreatedDate()));
		
		v.setTag(eventsList.get(position));
		return v;
		
	}

	
	
	
	

}
