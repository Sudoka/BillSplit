package billsplit.ui;
import java.util.List;

import billsplit.engine.Item;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ItemAdapter extends ArrayAdapter<Item> {

	List<Item> itemList = null;
	
	public ItemAdapter(Context context, int layoutResourceId,
			List<Item> objects) {
		super(context, layoutResourceId, objects);
		itemList = objects;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		TextView txt = new TextView(getContext());
		txt.setText(itemList.get(position).getName() + "$"+itemList.get(position).getCost());
		
		txt.setTag(itemList.get(position));
		return txt;
		
	}

	
	
	
	

}
