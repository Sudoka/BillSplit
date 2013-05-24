package billsplit.ui;

import com.billsplit.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class CreateSelectEventActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_create_select);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.events, menu);
		return true;
	}
	
	public void btn_create_event_Clicked(View v)
	{
		String ID = "[CREATE_NEW]";
		
		Intent intent = new Intent(this, EventActivity.class);
		intent.putExtra(EventActivity.ARG_ID, ID);
		startActivity(intent);
	}

}
