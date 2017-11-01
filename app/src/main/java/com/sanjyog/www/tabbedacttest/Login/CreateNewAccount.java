package com.sanjyog.www.tabbedacttest.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.sanjyog.www.tabbedacttest.BaseActivity;
import com.sanjyog.www.tabbedacttest.Constants;
import com.sanjyog.www.tabbedacttest.R;

import com.sanjyog.www.tabbedacttest.Utils.Utils;
import com.sanjyog.www.tabbedacttest.model.User;

import java.util.Map;

public class CreateNewAccount extends BaseActivity {
    private static final String LOG_TAG = CreateNewAccount.class.getSimpleName();
    private ProgressDialog mAuthProgressDialog;
    private EditText mEditTextUsernameCreate, mEditTextEmailCreate, mEditTextPasswordCreate, mEditTextPasswordCheck;
    private String mUserName, mEmail, mPassword, mPasswordCheck;
    private Firebase mFirebase;
    private String myEncodedEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        mFirebase = new Firebase(Constants.FIREBASE_URL);

        initializeScreen();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public void initializeScreen() {
        mEditTextUsernameCreate = (EditText) findViewById(R.id.edit_text_username_create);
        mEditTextEmailCreate = (EditText) findViewById(R.id.edit_text_email_create);
        mEditTextPasswordCreate = (EditText) findViewById(R.id.edit_text_password_create);
        mEditTextPasswordCheck = (EditText) findViewById(R.id.edit_text_password_reenter);


        LinearLayout linearLayoutCreateNewAccountActivity = (LinearLayout) findViewById(R.id.linear_layout_create_account_activity);
        initializeBackground(linearLayoutCreateNewAccountActivity);

        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle(getResources().getString(R.string.progress_dialog_loading));
        mAuthProgressDialog.setMessage(getResources().getString(R.string.progress_dialog_creating_user_with_firebase));
        mAuthProgressDialog.setCancelable(false);
    }

    public void ReturnToSignIn(View view) {
        Intent intent = new Intent(this, MyLogin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void Register(View view) {

        mUserName = mEditTextUsernameCreate.getText().toString();
        mEmail = mEditTextEmailCreate.getText().toString();
        mPassword = mEditTextPasswordCreate.getText().toString();
        mPasswordCheck = mEditTextPasswordCheck.getText().toString();


        boolean validUserName = isUserNameValid(mUserName);
        boolean validEmail = isEmailValid(mEmail);
        boolean validPassword = isPasswordValid(mPassword);

        if (!validUserName || !validEmail || !validPassword) return;

        mAuthProgressDialog.show();

        if(mPassword == mPasswordCheck) {

            mFirebase.createUser(mEmail, mPassword, new Firebase.ValueResultHandler<Map<String, Object>>() {
                @Override
                public void onSuccess(Map<String, Object> result) {
                    mAuthProgressDialog.dismiss();
                    Log.i(LOG_TAG, getString(R.string.log_message_auth_successful));

                    //String uid = result.get("uid").toString();
                    //Toast.makeText(getApplicationContext(), myEncodedEmail, Toast.LENGTH_LONG).show();
                    String unprocessedEmail = mEditTextEmailCreate.getText().toString();
                    myEncodedEmail = Utils.encodeEmail(unprocessedEmail);
                    createUserInFirebaseHelper(myEncodedEmail);
                    Restore_Views();
                    Intent signIn = new Intent(getApplicationContext(), MyLogin.class);
                    startActivity(signIn);
                }

                @Override
                public void onError(FirebaseError firebaseError) {
                    Log.d(LOG_TAG, getString(R.string.log_error_occurred) + firebaseError);
                    mAuthProgressDialog.dismiss();

                    if (firebaseError.getCode() == FirebaseError.EMAIL_TAKEN) {
                        mEditTextEmailCreate.setError(getString(R.string.error_email_taken));
                    } else {
                        showErrorToast(firebaseError.getMessage());
                    }

                }
            });
        }

        else{
            mEditTextPasswordCheck.setError("Password does not match previous");
        }
    }

    private void Restore_Views(){
        mEditTextUsernameCreate.setText("");
        mEditTextPasswordCreate.setText("");
        mEditTextEmailCreate.setText("");
    }

    private void createUserInFirebaseHelper(final String encodedEmail) {

        final Firebase userLocation = new Firebase(Constants.FIREBASE_URL_USERS).child(encodedEmail);
        final Firebase userArrayLocation = new Firebase(Constants.FIREBASE_URL_USER_ARRAY).child(encodedEmail);

        //Toast.makeText(getApplicationContext(), uid, Toast.LENGTH_LONG).show();

        userLocation.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    User newUser = new User(mUserName, mEmail, mPassword);
                    userLocation.setValue(newUser);
                    userArrayLocation.setValue(newUser);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d(LOG_TAG, getString(R.string.log_error_occurred) + firebaseError.getMessage());
            }
        });

    }

    private boolean isEmailValid(String email) {
        boolean isGoodEmail = (email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches());

        if (!isGoodEmail) {
            mEditTextEmailCreate.setError(getResources().getString(R.string.error_cannot_be_empty));
            return false;
        }
        return isGoodEmail;
    }

    private boolean isUserNameValid(String userName) {
        if (userName.equals("")) {
            mEditTextUsernameCreate.setError(getResources().getString(R.string.error_cannot_be_empty));
            return false;
        }
        return true;
    }

    private boolean isPasswordValid(String Password) {
        if (Password.length() < 6) {
            mEditTextPasswordCreate.setError(getResources().getString(R.string.error_invalid_password_not_valid));
            return false;
        }

        return true;
    }

    private void showErrorToast(String message) {
        Toast.makeText(CreateNewAccount.this, message, Toast.LENGTH_LONG).show();
    }

}
