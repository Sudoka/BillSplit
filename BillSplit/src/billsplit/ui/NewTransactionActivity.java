package billsplit.ui;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import billsplit.engine.DataCapture;
import billsplit.engine.Item;
import billsplit.engine.Transaction;

import com.billsplit.R;

import edu.sfsu.cs.orange.ocr.CaptureActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class NewTransactionActivity extends Activity {
	private static final String TAG = "TransactionActivity";
	public static DataCapture dataCapture;
	private boolean isOCRdone;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_transaction);
		// Show the Up button in the action bar.
		setupActionBar();
		isOCRdone = false;
	}

	//Once we come back from the OCR world, lets read some data from file and add 
	//it to the list of items in the transaction
	//TODO: Transaction.current is updated here 
	@Override
	protected void onResume() {
		super.onResume();
		if(isOCRdone){
			ArrayList<Item> newItems = getItemList();
			for(Item item : newItems){
				Transaction.current.addItem(item);
			}
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
	
	public void btn_done_clicked(View view)
	{
		finish();
	}
	
	public void ibtn_keyboard_clicked(View view)
	{
		Intent intent = new Intent(this, ManualInputActivity.class);
		startActivity(intent);
	}
	
	public void ibtn_camera_clicked(View view)
	{
		isOCRdone = true;
		Intent intent = new Intent(this, CaptureActivity.class);
	    startActivity(intent);
	}
	public ArrayList<billsplit.engine.Item> getItemList(){
		String itemListString = load("ItemList.txt");
		Log.e(TAG, "itemListString=" + itemListString);
		dataCapture = new DataCapture(itemListString);
		
		//get an item from the dataCapture object
		return dataCapture.getItemList();
	}
	private String load(String filename){
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
