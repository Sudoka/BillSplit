package billsplit.ui;
import java.util.HashMap;
import java.util.List;

import com.billsplit.R;

import billsplit.engine.Item;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ItemAdapter extends ArrayAdapter<Item> {

	List<Item> itemList = null;
	HashMap<Item, Boolean> itemBoolsList;
	private Context context;
	private int layoutResourceId;
	
	public ItemAdapter(Context context, int layoutResourceId,
			List<Item> objects, HashMap<Item,Boolean> itembools) {
		super(context, layoutResourceId, objects);
		itemList = objects;
		itemBoolsList = itembools;
		this.context = context;
		this.layoutResourceId = layoutResourceId;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		/*TextView txt = new TextView(getContext());
		txt.setText(itemList.get(position).getName() + "$"+itemList.get(position).getCost());
		
		txt.setTag(itemList.get(position));
		return txt;
		*/
		View v = convertView;
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        v = inflater.inflate(layoutResourceId, parent, false);
        
        
		TextView name = (TextView) v.findViewById(R.id.layout_txtItemName);
		name.setText(itemList.get(position).getName());
		
		TextView price = (TextView) v.findViewById(R.id.layout_txtPrice);
		price.setText("$ "+String.valueOf(itemList.get(position).getCost()));
		v.setTag(itemList.get(position));
		
		if(itemBoolsList.get(itemList.get(position))){
			v.setBackgroundColor(0x0000FF00);
		}
		
		return v;
	}

	
	
	
	

}
