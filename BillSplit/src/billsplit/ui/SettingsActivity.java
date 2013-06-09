package billsplit.ui;

import com.billsplit.R;
import billsplit.engine.Account;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.widget.Toast;

public class SettingsActivity extends PreferenceActivity {
	
	
	static public String KEY_USER_NAME = "USER_NAME";
	static public String KEY_USER_GID = "USER_GID";
	static public String DEFAULT_VALUE_USER_NAME = "Anonymous";
	static public String DEFAULT_VALUE_USER_GID = "anonymous@billsplit.com";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
    
    
    public static class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener  {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.pref_general);
        }

		@Override
		public void onSharedPreferenceChanged(
				SharedPreferences sharedPreferences, String key) {
			if (key.equals(SettingsActivity.KEY_USER_NAME)) {
	            String newValue = sharedPreferences.getString(key, "");
	            Account.getCurrentAccount().setName(newValue);
	            //Toast.makeText(getActivity(), newValue, Toast.LENGTH_LONG).show();
	        }
			if (key.equals(SettingsActivity.KEY_USER_GID)) {
	            String newValue = sharedPreferences.getString(key, "");
	            Account.getCurrentAccount().setGID(newValue);
	            //Toast.makeText(getActivity(), newValue, Toast.LENGTH_LONG).show();
	        }
			
		}
		
		@Override
		public void onResume() {
		    super.onResume();
		    getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

		}

		@Override
		public void onPause() {
		    getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
		    super.onPause();
		}
       
    }

}