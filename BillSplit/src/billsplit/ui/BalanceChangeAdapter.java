package billsplit.ui;
import java.text.SimpleDateFormat;
import java.util.List;
import com.billsplit.R;

import billsplit.engine.BalanceChange;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BalanceChangeAdapter extends ArrayAdapter<BalanceChange> {

	List<BalanceChange> balancesList = null;
	private int layoutResourceId;
	private Context context;
	
	public BalanceChangeAdapter(Context context, int layoutResourceId,
			List<BalanceChange> objects) {
		super(context, layoutResourceId, objects);
		balancesList = objects;
		this.layoutResourceId = layoutResourceId;
        this.context = context;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		/*TextView txt = new TextView(getContext());
		txt.setText(balancesList.get(position).getName());
		
		txt.setTag(balancesList.get(position));
		return txt;
		*/
		View v = convertView;
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        v = inflater.inflate(layoutResourceId, parent, false);
        
        
		TextView balanceChangeName = (TextView) v.findViewById(R.id.layout_txtItemName);
		balanceChangeName.setText(balancesList.get(position).getName());
		
		TextView balanceChangeDate = (TextView) v.findViewById(R.id.layout_txtDate);
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");   
		balanceChangeDate.setText(sdf.format(balancesList.get(position).getDate()));
		
		v.setTag(balancesList.get(position));
		return v;
		
	}

	
	
	
	

}
