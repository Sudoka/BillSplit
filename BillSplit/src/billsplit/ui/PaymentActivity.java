package billsplit.ui;
import billsplit.engine.Event;
import billsplit.engine.Item;
import billsplit.engine.Participant;
import billsplit.engine.Transaction;

import com.billsplit.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class PaymentActivity extends Activity {

	RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

		layout = (RelativeLayout) findViewById(R.id.payment_participantsContainer);
		
        Toast.makeText(this, Item.currentItem.getName(), Toast.LENGTH_LONG).show();
        
        EditText name = (EditText)findViewById(R.id.payment_txtItemName);
        EditText cost = (EditText)findViewById(R.id.payment_txtItemCost);
        EditText unassigned = (EditText)findViewById(R.id.payment_txtUnassigned);
        
        name.setText(Item.currentItem.getName());
        cost.setText("$"+String.valueOf(Item.currentItem.getCost()));
        unassigned.setText("$"+String.valueOf(Item.currentItem.getCost()));
        
        Button splitButton = (Button)findViewById(R.id.payment_btnSplitEvenly);
		splitButton.setVisibility(View.INVISIBLE);
		
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
    	Button splitButton = (Button)findViewById(R.id.payment_btnSplitEvenly);
		
		for(int i=0;i<layout.getChildCount();i++){
			ToggleButton btnPart = (ToggleButton)layout.getChildAt(i);
			if(btnPart.isChecked()){
				atLeastOneChecked = true;
				break;
			}
		}
		
		if(atLeastOneChecked){
			splitButton.setVisibility(View.VISIBLE);
		}else{
			splitButton.setVisibility(View.INVISIBLE);
		}
	}
    private void generateParticipants() {
		layout.removeAllViews();
		
		for (int i = 0; i < Event.currentEvent.getParticipants().size(); i++) {

			ToggleButton btnPart = new ToggleButton(getApplicationContext());
			btnPart.setTextOn(Event.currentEvent.getParticipants().get(i).getName());
			btnPart.setTextOff(Event.currentEvent.getParticipants().get(i).getName());
			btnPart.setText(Event.currentEvent.getParticipants().get(i).getName());
			btnPart.setTag(i);
			btnPart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			        checkParticipantsChecked();
			    }

				
			});
			
			btnPart.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//showParticipantDialog(Integer.parseInt((String) btn.getText()));//change to participant ID
					
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
    
    public void button_split_evenly_clicked(View view){
    	for(int i=0;i<layout.getChildCount();i++){
    		
			ToggleButton btnPart = (ToggleButton)layout.getChildAt(i);
			if(btnPart.isChecked()){
				Participant p = Transaction.current.getParticipants().get((Integer) btnPart.getTag());
				Transaction.current.debtAddParticipant(Item.currentItem, p);
				//btnPart.setText(btnPart.getText()+" $"+String.valueOf(Transaction.current.));
			}
    	}
    	
    }
    

}
