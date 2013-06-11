/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package billsplit.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.billsplit.R;

import billsplit.engine.Account;
import billsplit.engine.BalanceChange;
import billsplit.engine.Event;
import billsplit.engine.Participant;
import billsplit.engine.Transaction;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


/**
 * A list view example where the 
 * data for the list comes from an array of strings.
 */
public class ExistingTransactionsActivity extends Activity {

	List<BalanceChange> list;
	BalanceChangeAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_existing_transactions);
        // Use an existing ListAdapter that will map an array
        // of strings to TextViews
        list = (List<BalanceChange>) Event.currentEvent.getBalanceChanges();
     //   list.add(new Transaction(String.valueOf(Event.currentEvent.getBalanceChanges().size()+1),(ArrayList<Participant>) Event.currentEvent.getParticipants()));
        adapter = new BalanceChangeAdapter(this,R.layout.simple_list_item, list);
		
        ListView listView = (ListView)findViewById(R.id.existing_transaction_list);
        OnItemClickListener transactionClicked = new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position,
					long id) {
				if(v.getTag() instanceof Transaction){
					Intent intent = new Intent(getApplicationContext(),
							NewTransactionActivity.class);
					Transaction e = (Transaction) v.getTag();
					BalanceChange.current = e;
					//intent.putExtra(EventActivity.ARG_ID, e.getName());
					startActivity(intent);
				}else
				{
					Intent intent = new Intent(getApplicationContext(),
							PaymentActivity.class);
					BalanceChange e = (BalanceChange) v.getTag();
					BalanceChange.current = e;
					//intent.putExtra(EventActivity.ARG_ID, e.getName());
					startActivity(intent);
				}
				
			}
		};

		listView.setOnItemClickListener(transactionClicked);
        
        listView.setAdapter(adapter);
       // listView.getListView().setTextFilterEnabled(true);
    }
	
	public void add_new_transaction_clicked(View view){
		ArrayList<Participant> participants = (ArrayList<Participant>) Event.currentEvent.getParticipants();
		//Create a new transaction
		Transaction newTransaction = new Transaction("Transaction"+String.valueOf(Event.currentEvent.getBalanceChanges().size()+1), participants);
		//Set the newTransaction as the current transaction
		newTransaction.setDate(new Date());
		Transaction.current = newTransaction;
		
		Event.currentEvent.addBalanceChange(newTransaction);
		//Start the new transaction
		Intent intent = new Intent(this, NewTransactionActivity.class);
		startActivity(intent);
	}

	@Override
	public void onResume(){
		super.onResume();
		adapter.notifyDataSetChanged();
	}
}
