package billsplit.engine;

import java.util.ArrayList;


public class Payment extends BalanceChange {
	
	
	public Payment() {}
	
	public Payment(ArrayList<Participant> participants, ArrayList<Double> amounts) {
		super(participants,amounts);
	}
	
}