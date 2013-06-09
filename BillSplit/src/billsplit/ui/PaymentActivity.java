package billsplit.ui;

import billsplit.engine.Event;
import billsplit.engine.Item;
import billsplit.engine.Participant;
import billsplit.engine.Transaction;

import com.billsplit.R;
import com.billsplit.R.layout;
import com.billsplit.R.menu;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.text.InputType;

public class PaymentActivity extends Activity {
	RelativeLayout layout;
	boolean manualInputEntered=false;
	double unassignedAmount;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

		layout = (RelativeLayout) findViewById(R.id.item_participantsContainer);
		
        EditText unassigned = (EditText)findViewById(R.id.item_txtUnassigned);
        
        unassigned.setText("$0.00");
        //unassignedAmount = Item.currentItem.getCost();
        	
		Button doneButton = (Button)findViewById(R.id.item_btnDone);
		//doneButton.setVisibility(View.GONE);
		
        generateParticipants();
        // Show the Up button in the action bar.
        setupActionBar();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.payment, menu);
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

    private void checkParticipantsChecked() {
    	boolean atLeastOneChecked = false;
    	Button splitButton = null;//(Button)findViewById(R.id.item_btnSplitEvenly);
		
		for(int i=0;i<layout.getChildCount();i++){
			ParticipantView btnPart = (ParticipantView)layout.getChildAt(i);
			if(btnPart.isChecked()){
				atLeastOneChecked = true;
				break;
			}
		}
		
		if(atLeastOneChecked){
			splitButton.setVisibility(View.VISIBLE);
		}else{
			splitButton.setVisibility(View.GONE);
		}
	}
    private void generateParticipants() {
		layout.removeAllViews();
		
		for (int i = 0; i < Event.currentEvent.getParticipants().size(); i++) {

			ParticipantView btnPart = new ParticipantView(getApplicationContext());
			btnPart.isCheckable = true;
			btnPart.setName(Event.currentEvent.getParticipants().get(i).getName());
			btnPart.setAmount(Transaction.current.debtGetTotalAmountParticipant(Event.currentEvent.getParticipants().get(i)));
			btnPart.setTag(Event.currentEvent.getParticipants().get(i));
			btnPart.setOnLongClickListener(new View.OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					
					final ParticipantView part = (ParticipantView)v;
					//part.toogleCheck();
					final EditText input = new EditText(PaymentActivity.this);
					
					input.setHint("Type amount: 0.0");
					input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

					AlertDialog.Builder alert;
					alert = new AlertDialog.Builder(PaymentActivity.this);
					alert.setView(input);
					alert.setTitle("Amount");

					alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						
							if(!manualInputEntered){
								Transaction.current.debtResetItem(Item.currentItem);
								uncheckAllParticipants();
								//checkParticipantsChecked();
							}
							Participant p = (Participant)part.getTag();
							try{
							Transaction.current.debtSet(Item.currentItem, p, Double.parseDouble(input.getText().toString()));
							part.setAmount(Double.parseDouble(input.getText().toString()));
							//unassignedAmount -= Double.parseDouble(input.getText().toString());
							EditText unassigned = (EditText)findViewById(R.id.item_txtUnassigned);
					        unassigned.setText("$"+String.valueOf(Transaction.current.debtGetItemDebtRemaining(Item.currentItem)));
					        manualInputEntered = true;
					        Button doneButton = (Button)findViewById(R.id.item_btnDone);
					        if(Transaction.current.debtGetItemDebtRemaining(Item.currentItem)==0){
					        	doneButton.setVisibility(View.VISIBLE);
					        }
					        else
					        {
					        	doneButton.setVisibility(View.GONE);
					        }
							}catch(Exception e){
								Toast.makeText(PaymentActivity.this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
							}
							//myEvent.getParticipantByName(part.getName().toString()).setName(input.getText().toString());
							//part.setName(input.getText().toString());
							
					  }

					
					});

					alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					  public void onClick(DialogInterface dialog, int whichButton) {
					    // Canceled.
					  }
					});
					
					alert.show();
					
					return true;
				}
			});

			btnPart.setOnClickListener(new OnClickListener() {
			    
				@Override
				public void onClick(View v) {
					if(!manualInputEntered){
						ParticipantView part = (ParticipantView)v;
						part.toogleCheck();
						//checkParticipantsChecked();
					}
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
    
    private void uncheckAllParticipants() {
    	for(int i=0;i<layout.getChildCount();i++){
    		ParticipantView btnPart = (ParticipantView)layout.getChildAt(i);
    		btnPart.setChecked(false);
    	}
		
	}
}
