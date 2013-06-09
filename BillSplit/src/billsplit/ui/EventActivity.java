package billsplit.ui;

import java.util.ArrayList;
import java.util.Date;

import billsplit.engine.Event;
import billsplit.engine.GlobalAccount;
import billsplit.engine.Participant;
import billsplit.engine.Transaction;

import com.billsplit.R;

//import edu.sfsu.cs.orange.ocr.CaptureActivity;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.text.InputType;

public class EventActivity extends Activity {

	public static final String ARG_ID = "ID_ARG";
	String EventID;
	RelativeLayout layout;
	static Event myEvent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);
		
		myEvent = Event.currentEvent; //replace this with serializable object
		// Show the Up button in the action bar.
		layout = (RelativeLayout) findViewById(R.id.participantsContainer);
		
		// childCount = layout.getChildCount();
		NumberPicker numParticipants = (NumberPicker) findViewById(R.id.picker_participants);
		numParticipants.setMaxValue(20);
		numParticipants.setMinValue(1);
		numParticipants.setValue(myEvent.getParticipants().size());
		
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

						if(newVal > oldVal){
							for(int i=0;i<newVal-oldVal;i++){
								myEvent.addParticipant(new Participant("Person"+String.valueOf(myEvent.getParticipants().size())));
							}
						}
						
						if(newVal < oldVal){
							
							}for(int i=0;i<oldVal-newVal;i++){
								Participant toBeRemoved = ((ArrayList<Participant>)myEvent.getParticipants()).get(myEvent.getParticipants().size()-1);
								myEvent.removeParticipant(toBeRemoved);
							}
						
						//generateParticipants(picker.getValue());
						generateParticipants();
					}
				});
		EventID = (String) getIntent().getSerializableExtra(ARG_ID);

		TextView lblName = (TextView) findViewById(R.id.lblName);
		
		lblName.setText(EventID);
		
		EditText txtName = (EditText)findViewById(R.id.txtName);
		
		//GlobalAccount acc = (GlobalAccount) getApplication();
		
		
		
		setupActionBar();
	}

	private void generateParticipants() {
		layout.removeAllViews();
		
		for (int i = 0; i < myEvent.getParticipants().size(); i++) {

			ParticipantView btnPart = new ParticipantView(getApplicationContext());
			
			//Button btnPart = new Button(getApplicationContext());
			
			/*String styledText = "<big> <font color='#008000'>"
		            + myEvent.getParticipants().get(i).getName() + "</font> </big>" + "<br />" 
		            + "<small>" + "$ 0.00" + "</small>";
			btnPart.setText(Html.fromHtml(styledText));
		    */
			//btnPart.setText(myEvent.getParticipants().get(i).getName());
			btnPart.setName(myEvent.getParticipants().get(i).getName());
			btnPart.setAmount(myEvent.getParticipants().get(i).getBalance());
			//btnPart.isCheckable = true;
			//LinearLayout txt = (LinearLayout)btnPart.findViewById(R.id.customButtonLayout);
			//btnPart.setClickable(true);
			//btnPart.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
			btnPart.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					
					final ParticipantView part = (ParticipantView)v;
					//part.toogleCheck();
					final EditText input = new EditText(EventActivity.this);
					
					input.setText(part.getName());

					AlertDialog.Builder alert;
					alert = new AlertDialog.Builder(EventActivity.this);
					alert.setView(input);
					alert.setTitle("Person Name");

					alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
							myEvent.getParticipantByName(part.getName().toString()).setName(input.getText().toString());
							part.setName(input.getText().toString());
					  }
					});

					alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					  public void onClick(DialogInterface dialog, int whichButton) {
					    // Canceled.
					  }
					});
					
					alert.show();
					/*
					Button btn = (Button)v;
					showParticipantDialog((String) btn.getText());//change to participant ID
					*/
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

	protected void showParticipantDialog(String name) {
		DialogFragment newFragment = ParticipantDetailsDialog.newInstance(name);
		
        newFragment.show(getFragmentManager(), "dialog");
    }



    public static class ParticipantDetailsDialog extends DialogFragment {
        
		static ParticipantDetailsDialog newInstance(String name) {
			ParticipantDetailsDialog dialog = new ParticipantDetailsDialog();
			Bundle params = new Bundle();
			params.putString("name", name);
			dialog.setArguments(params);
            return dialog;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.dialog_participant_details, container, false);
            View tv = v.findViewById(R.id.txtName);
           ((TextView)tv).setText(getArguments().getString("name"));
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

	protected void doEventNameSave(String name) {
		
		TextView lblName = (TextView) findViewById(R.id.lblName);
		myEvent.setName(name);
		lblName.setText(name);
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
	
	public void lblName_clicked(View view){
		final EditText input = new EditText(this);
		
		final TextView lblName = (TextView) findViewById(R.id.lblName);
		input.setText(lblName.getText());

		AlertDialog.Builder alert;
		alert = new AlertDialog.Builder(this);
		alert.setView(input);
		alert.setTitle("Event Name");

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		//  String value = input.getText();
		  myEvent.setName(input.getText().toString());
		  lblName.setText(input.getText().toString());
		//  alert.
		  }
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});
		
		alert.show();
	}

	/** Called when the user clicks the OCR button */
	public void createTransaction(View view) {
		// Do something in response to button
		Intent intent = new Intent(this, NewTransactionActivity.class);
		startActivity(intent);
	}

	public void btn_new_transaction_clicked(View view) {
		//Get a list of participants from the current event
		ArrayList<Participant> participants = (ArrayList<Participant>) myEvent.getParticipants();
		//Create a new transaction
		Transaction newTransaction = new Transaction("Transaction"+String.valueOf(myEvent.getBalanceChanges().size()+1), participants);
		//Set the newTransaction as the current transaction
		newTransaction.setDate(new Date());
		Transaction.current = newTransaction;
		
		myEvent.addBalanceChange(newTransaction);
		//Start the new transaction
		Intent intent = new Intent(this, NewTransactionActivity.class);
		startActivity(intent);
	}

	public void btn_existing_transactions_clicked(View view) {
		Intent intent = new Intent(this, ExistingTransactionsActivity.class);
		startActivity(intent);
	}
	
	@Override
	public void onResume(){
		super.onResume();
		Event.currentEvent.updateBalances();
		generateParticipants();
		if(myEvent.getBalanceChanges().size()>0){
			//System.out.println(myEvent.getBalanceChanges().size());
			NumberPicker numParticipants = (NumberPicker) findViewById(R.id.picker_participants);
			numParticipants.setEnabled(false);
		}
	}
}
