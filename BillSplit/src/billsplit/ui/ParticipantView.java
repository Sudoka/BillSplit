package billsplit.ui;

import com.billsplit.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.*;

public class ParticipantView extends LinearLayout {

	String name;
	double amount;
	public boolean isCheckable = false;
	private boolean isChecked;

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		if (isCheckable) {
			this.isChecked = isChecked;
			if (isChecked) {
				setBackgroundResource(android.R.drawable.checkbox_on_background);
			} else {
				setBackgroundResource(android.R.drawable.btn_default_small);
			}
		}
	}

	private void initialize() {
		setName("name");
		setAmount(0);
	}

	public ParticipantView(Context context) {
		super(context);
		setOrientation(LinearLayout.VERTICAL);
		setBackgroundResource(android.R.drawable.btn_default_small);

		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (layoutInflater != null) {
			layoutInflater.inflate(R.layout.participant, this, true);
		}
		initialize();
	}

	public void toogleCheck() {
		if (isCheckable) {
			setChecked(!isChecked);
		}

	}

	public void setName(String n) {
		name = n;
		TextView name = (TextView) findViewById(R.id.firstTextView);
		name.setText(n);
	}

	public void setAmount(double a) {
		amount = a;
		TextView amount = (TextView) findViewById(R.id.secondTextView);
		amount.setText("$ " + String.format( "%.2f", a ));
	}

	public String getName() {
		return name;
	}

	public double getAmount() {
		return amount;
	}

}
