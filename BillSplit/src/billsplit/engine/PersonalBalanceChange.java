package billsplit.engine;


public class PersonalBalanceChange {
	public PersonalBalanceChange(Participant p, double a) {
		this.amount = a;
		this.person = p;
	}
	public double amount;
	public Participant person;
}