package billsplit.ui;

import billsplit.engine.*;
import com.billsplit.R;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	private static final String[] DUMMY_CREDENTIALS = new String[] {
			"foo@example.com:hello", "bar@example.com:world" };

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mGID;
	private String mUserName;

	// UI references.
	private EditText mGIDView;
	private EditText mUserNameView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);
		
		setContentView(R.layout.activity_login);

		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
		
		String currentUserEmail = settings.getString(SettingsActivity.KEY_USER_GID,SettingsActivity.DEFAULT_VALUE_USER_GID);

		String currentUserName = settings.getString(SettingsActivity.KEY_USER_NAME, SettingsActivity.DEFAULT_VALUE_USER_NAME);
		
		
		if(!currentUserEmail.equals(SettingsActivity.DEFAULT_VALUE_USER_GID) && !currentUserName.equals(SettingsActivity.DEFAULT_VALUE_USER_NAME))
		{
			Intent intent = new Intent();
            intent.setClass(this,  CreateSelectEventActivity.class);
            startActivity(intent);
            finish();
		}
		
		
		
		
		// Set up the login form.
		mGID = getIntent().getStringExtra(EXTRA_EMAIL);
		mGIDView = (EditText) findViewById(R.id.login_GID);
		mGIDView.setText(mGID);
		//GlobalAccount acc = (GlobalAccount)getApplication();
		//mEmailView.setText(acc.getMyInternalValue());
		//acc.setMyInternalValue("CHANGED");

		mUserNameView = (EditText) findViewById(R.id.login_UserName);
		mUserNameView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});
 
		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		
		
		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mGIDView.setError(null);
		mUserNameView.setError(null);

		// Store values at the time of the login attempt.
		mGID = mGIDView.getText().toString();
		mUserName = mUserNameView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mUserName)) {
			mUserNameView.setError(getString(R.string.error_field_required));
			focusView = mUserNameView;
			cancel = true;
		} else if (mUserName.length() < 4) {
			mUserNameView.setError(getString(R.string.error_invalid_password));
			focusView = mUserNameView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mGID)) {
			mGIDView.setError(getString(R.string.error_field_required));
			focusView = mGIDView;
			cancel = true;
		} else if (!mGID.contains("@")) {
			mGIDView.setError(getString(R.string.error_invalid_email));
			focusView = mGIDView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
			  SharedPreferences.Editor editor = settings.edit();
			  editor.putString(SettingsActivity.KEY_USER_NAME, mUserName);
			  editor.putString(SettingsActivity.KEY_USER_GID, mGID);
			  
			  editor.commit();
			  
			 
			//  Toast.makeText(getApplicationContext(),  settings.getString("USER_GID", ""), Toast.LENGTH_LONG).show();
			  
			  Intent intent = new Intent();
	          intent.setClass(this,  CreateSelectEventActivity.class);
	          startActivity(intent);
	            
			  finish();
			/*
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask();
			mAuthTask.execute((Void) null);*/
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
	
	public void getStarted_Clicked(View v){
		//Account.test();
		Intent events = new Intent(this, CreateSelectEventActivity.class);
        startActivity(events); 
	}
	
	public void viewTutorial_Clicked(View v){
		Intent events = new Intent(this, TutorialActivity.class);
        startActivity(events); 
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			
			try {
				// Simulate network access.
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return false;
			}
			
			
			
			/*
			for (String credential : DUMMY_CREDENTIALS) {
				String[] pieces = credential.split(":");
				if (pieces[0].equals(mEmail)) {
					// Account exists, return true if the password matches.
					return pieces[1].equals(mPassword);
				}
			}
*/
			// TODO: register the new account here.
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				finish();
			} else {
				mUserNameView
						.setError(getString(R.string.error_incorrect_password));
				mUserNameView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
}
