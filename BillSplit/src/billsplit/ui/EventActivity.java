package billsplit.ui;

import billsplit.engine.GlobalAccount;

import com.billsplit.R;

//import edu.sfsu.cs.orange.ocr.CaptureActivity;

import android.os.Bundle;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class EventActivity extends Activity {

	public static final String ARG_ID = "ID_ARG";
	String EventID;
	RelativeLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);
		// Show the Up button in the action bar.
		layout = (RelativeLayout) findViewById(R.id.participantsContainer);

		// childCount = layout.getChildCount();
		NumberPicker numParticipants = (NumberPicker) findViewById(R.id.picker_participants);
		numParticipants.setMaxValue(20);
		numParticipants.setMinValue(1);
		numParticipants.setValue(2);
		generateParticipants(2);
		numParticipants
				.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		numParticipants
				.setOnScrollListener(new NumberPicker.OnScrollListener() {
					@Override
					public void onScrollStateChange(NumberPicker picker,
							int scrollState) {

						if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE)
							;
						// generateParticipants(picker.getValue());
					}
				});

		numParticipants
				.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
					@Override
					public void onValueChange(NumberPicker picker, int oldVal,
							int newVal) {

						generateParticipants(picker.getValue());
					}
				});
		EventID = (String) getIntent().getSerializableExtra(ARG_ID);

		TextView txtID = (TextView) findViewById(R.id.txtID);
		GlobalAccount acc = (GlobalAccount) getApplication();
		txtID.setText(EventID + acc.getMyInternalValue());
		setupActionBar();
	}

	private void generateParticipants(int newVal) {
		layout.removeAllViews();
		for (int i = 0; i < newVal; i++) {

			Button btnPart = new Button(getApplicationContext());
			btnPart.setText(String.valueOf(i));
			btnPart.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Button btn = (Button)v;
					showParticipantDialog(Integer.parseInt((String) btn.getText()));//change to participant ID
					
				}
			});
			// setting image resource
			// imageView.setImageResource(R.drawable.ic_camera);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT,
					RelativeLayout.TRUE);
			params.topMargin = i * 90;
			layout.addView(btnPart, params);
		}
	}

	protected void showParticipantDialog(int id) {
		DialogFragment newFragment = ParticipantDetailsDialog.newInstance(id);
		
        newFragment.show(getFragmentManager(), "dialog");
    }



    public static class ParticipantDetailsDialog extends DialogFragment {
        
		static ParticipantDetailsDialog newInstance(int id) {
			ParticipantDetailsDialog dialog = new ParticipantDetailsDialog();
			Bundle params = new Bundle();
			params.putInt("ID", id);
			dialog.setArguments(params);
            return dialog;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.dialog_participant_details, container, false);
            View tv = v.findViewById(R.id.txtName);
          //  ((TextView)tv).setText("This is "+getArguments().getInt("ID"));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            return v;
        }
    }


	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/** Called when the user clicks the OCR button */
	public void createTransaction(View view) {
		// Do something in response to button
		Intent intent = new Intent(this, NewTransactionActivity.class);
		startActivity(intent);
	}

	public void btn_new_transaction_clicked(View view) {
		Intent intent = new Intent(this, NewTransactionActivity.class);
		startActivity(intent);
	}

	public void btn_existing_transactions_clicked(View view) {
		Intent intent = new Intent(this, ExistingTransactionsActivity.class);
		startActivity(intent);
	}
}
