package billsplit.engine;

import android.app.Application;

public class GlobalAccount extends Application {

	String myInternalValue;
	public String getMyInternalValue() {
		return myInternalValue;
	}
	public void setMyInternalValue(String myInternalValue) {
		this.myInternalValue = myInternalValue;
	}
	public void onCreate(){
		super.onCreate();
		myInternalValue = "Initialized";
	}
}
