package billsplit.ui;

<<<<<<< HEAD
import com.billsplit.R;
=======
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import billsplit.engine.DataCapture;

import com.example.billsplit.R;
>>>>>>> tatenda

import edu.sfsu.cs.orange.ocr.CaptureActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
 
public class TransactionActivity extends Activity {
	private static final String TAG = "TransactionActivity";
	public static DataCapture dataCapture;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
<<<<<<< HEAD
		setContentView(R.layout.activity_new_transaction);
=======
		//dataCapture = new DataCapture();
		setContentView(R.layout.activity_transaction);
>>>>>>> tatenda
		// Show the Up button in the action bar.
		setupActionBar();
	}
 

	@Override
	protected void onResume() {
		super.onResume();
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
		getMenuInflater().inflate(R.menu.transaction, menu);
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
	public void initiateOCR(View view) {	    
		Intent intent = new Intent(this, CaptureActivity.class);
	    startActivity(intent);
	}
	public void initializeDataCapture(View view){
		String itemListString = load("ItemList.txt");
		Log.e(TAG, "itemListString=" + itemListString);
		dataCapture = new DataCapture(itemListString);
		
		//get an item from the dataCapture object
		ArrayList<billsplit.engine.Item> itemList = dataCapture.getItemList();
		billsplit.engine.Item item0 = itemList.get(0);
		
	    TextView items = (TextView) findViewById(R.id.editText1);
	    items.setText(item0.getName());
	}
	private String load(String filename)
	{
	    try
	    {
	        FileInputStream fis = openFileInput(filename);
	        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
	        String line = null, input="";
	        while ((line = reader.readLine()) != null)
	            input += line;
	        reader.close();
	        fis.close();
	        //toast("File successfully loaded.");
	        return input;
	    }
	    catch (Exception ex)
	    {
	        //toast("Error loading file: " + ex.getLocalizedMessage());
	        return "";
	    }
	}
}
