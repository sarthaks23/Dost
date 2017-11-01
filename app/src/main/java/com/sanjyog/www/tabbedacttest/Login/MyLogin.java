package com.sanjyog.www.tabbedacttest.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import com.firebase.client.ValueEventListener;
import com.sanjyog.www.tabbedacttest.BaseActivity;
import com.sanjyog.www.tabbedacttest.Constants;
import com.sanjyog.www.tabbedacttest.R;
import com.sanjyog.www.tabbedacttest.Test_act;
import com.sanjyog.www.tabbedacttest.Utils.Utils;
import com.sanjyog.www.tabbedacttest.model.User;



public class MyLogin extends BaseActivity{
    private static final String LOG_TAG = MyLogin.class.getSimpleName();
    private ProgressDialog mAuthProgressDialog;
    private EditText mEditTextEmailInput, mEditTextPasswordInput;


    private Firebase mFirebaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_login);


        mFirebaseRef = new Firebase(Constants.FIREBASE_URL);

        initializeScreen();

        /**
         * Call signInPassword() when user taps "Done" keyboard action
         */
        mEditTextPasswordInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {

                if(actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction()== KeyEvent.ACTION_DOWN){
                    //Toast.makeText(getApplicationContext(), "Sign In in Process...", Toast.LENGTH_LONG).show();
                    String myEncodedEmail = Utils.encodeEmail(mEditTextEmailInput.getText().toString());
                    SignInPassword(myEncodedEmail);
                }

                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }



    public void Sign_Up(View view){
        Intent intent = new Intent(this, CreateNewAccount.class);
        startActivity(intent);
    }

    public void Sign_In(View view){
        String myEncodedEmail = Utils.encodeEmail(mEditTextEmailInput.getText().toString());
        SignInPassword(myEncodedEmail);
    }

    public void initializeScreen(){
        mEditTextPasswordInput = (EditText) findViewById(R.id.edit_text_password);
        mEditTextEmailInput = (EditText) findViewById(R.id.edit_text_email);

        LinearLayout linearLayoutLoginActivity = (LinearLayout) findViewById(R.id.linear_layout_login_activity);
        initializeBackground(linearLayoutLoginActivity);

        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle(getString(R.string.progress_dialog_loading));
        mAuthProgressDialog.setMessage(getString(R.string.progress_dialog_authenticating_with_firebase));
        mAuthProgressDialog.setCancelable(false);

        //setupGoogleSignIn();

    }

    public void SignInPassword(String EncodedEmail){

        Firebase mUserRef = new Firebase(Constants.FIREBASE_URL_USERS).child(EncodedEmail);
        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);
                //if(currentUser != null) {
                    if ((currentUser.getPassword()).equals(mEditTextPasswordInput.getText().toString())) {
                        Intent signIn = new Intent(getApplicationContext(), Test_act.class);
                        signIn.putExtra("email", mEditTextEmailInput.getText().toString());
                        startActivity(signIn);
                        finish();
                    }
                    if(!(currentUser.getPassword()).equals(mEditTextPasswordInput.getText().toString())){
                        mEditTextPasswordInput.setError("Incorrect Password");
                        mAuthProgressDialog.dismiss();
                        return;
                    }
                //}
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d(LOG_TAG, getString(R.string.log_error_occurred) + firebaseError.getMessage());
            }
        });

        String email = mEditTextEmailInput.getText().toString();
        String password = mEditTextPasswordInput.getText().toString();

        if(email.equals("")){
            mEditTextEmailInput.setError(getString(R.string.error_cannot_be_empty));
            return;
        }

        mAuthProgressDialog.show();



    }

    private void setAuthenticatedUserPasswordProvider(AuthData authData){
        final String unprocessedEmail = authData.getProviderData().get(Constants.FIREBASE_PROPERTY_EMAIL).toString().toLowerCase();

        mEncodedEmail = Utils.encodeEmail(unprocessedEmail);
    }

    private void showErrorToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }


}
