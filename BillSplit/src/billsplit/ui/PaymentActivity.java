package billsplit.ui;
import billsplit.engine.Event;
import billsplit.engine.Item;

import com.billsplit.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
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

    private void generateParticipants() {
		layout.removeAllViews();
		
		for (int i = 0; i < Event.currentEvent.getParticipants().size(); i++) {

			Button btnPart = new Button(getApplicationContext());
			btnPart.setText(Event.currentEvent.getParticipants().get(i).getName());
			btnPart.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Button btn = (Button)v;
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

}
