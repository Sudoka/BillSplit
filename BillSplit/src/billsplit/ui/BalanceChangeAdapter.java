package billsplit.ui;
import java.util.List;

import billsplit.engine.BalanceChange;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BalanceChangeAdapter extends ArrayAdapter<BalanceChange> {

	List<BalanceChange> balancesList = null;
	
	public BalanceChangeAdapter(Context context, int layoutResourceId,
			List<BalanceChange> objects) {
		super(context, layoutResourceId, objects);
		balancesList = objects;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		TextView txt = new TextView(getContext());
		txt.setText(balancesList.get(position).getName());
		
		txt.setTag(balancesList.get(position));
		return txt;
		
	}

	
	
	
	

}
