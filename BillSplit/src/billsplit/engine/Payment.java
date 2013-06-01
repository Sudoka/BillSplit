package billsplit.engine;

import java.util.ArrayList;


public class Payment extends BalanceChange {
	
	
	public Payment(String name, ArrayList<Participant> participants, ArrayList<Double> amounts) {
		super(name,participants,amounts);
	}
	
	public String toString() {
		String str = String.format("Payment(name=%s)",getName());
		return str;
	}
	
}