package com.sanjyog.www.tabbedacttest;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;
import com.sanjyog.www.tabbedacttest.Utils.Utils;
import com.sanjyog.www.tabbedacttest.model.Convo;

import java.util.HashMap;



/**
 * Created by Sarthak on 12/24/2015.
 */
public class Add_Convo extends DialogFragment {
    EditText mNewConvoName;
    EditText mNewConvoWith;
    String mEncodedEmail;

    /**
     * Public static constructor that creates fragment and
     * passes a bundle with data into it when adapter is created
     */
    public static Add_Convo newInstance(String encodedEmail) {
        Add_Convo add_convo = new Add_Convo();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_ENCODED_EMAIL, encodedEmail);
        add_convo.setArguments(bundle);
        return add_convo;
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mEncodedEmail = getActivity().getIntent().getStringExtra("email");
                //getArguments().getString(Constants.KEY_ENCODED_EMAIL);
    }

    /**
     * Open the keyboard automatically when the dialog fragment is opened
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /* Use the Builder class for convenient dialog construction */
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomTheme_Dialog);
        /* Get the layout inflater */
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_new_convo, null);
        mNewConvoName = (EditText) rootView.findViewById(R.id.new_convo);
        mNewConvoWith = (EditText) rootView.findViewById(R.id.convo_with);

        /**
         * Call addMeal() when user taps "Done" keyboard action
         */
        mNewConvoWith.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    addNewConvo();
                }
                return true;
            }
        });

        /* Inflate and set the layout for the dialog */
        /* Pass null as the parent view because its going in the dialog layout */
        builder.setView(rootView)
                /* Add action buttons */
                .setPositiveButton(R.string.positive_button_create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        addNewConvo();
                    }
                });

        return builder.create();
    }

    public void addNewConvo() {

        String userEnteredName = mNewConvoName.getText().toString();
        String ConvoWith = Utils.encodeEmail(mNewConvoWith.getText().toString());
        String owner = "sarthaks23";

        Intent intent = getActivity().getIntent();
        String extend = Utils.encodeEmail(intent.getStringExtra("email"));

        if(!userEnteredName.equals("")){
            //Firebase convosRef = new Firebase(Constants.FIREBASE_URL_ACTIVE_CONVOS);
            Firebase convosRef = new Firebase(Constants.FIREBASE_URL_USERS).child(extend + Constants.FIREBASE_USER_ACTIVE_CONVO_EXTENSION);
            //Firebase newConvoRef = convosRef.push();
            Firebase newConvoRef = convosRef.child(extend+Constants.FIREBASE_USER_CONVO_WITH_EXTENSION+ConvoWith);

            Firebase otherConvosRef = new Firebase(Constants.FIREBASE_URL_USERS).child(ConvoWith + Constants.FIREBASE_USER_ACTIVE_CONVO_EXTENSION);
            //Firebase newConvoRef = convosRef.push();
            Firebase newOtherConvoRef = otherConvosRef.child(ConvoWith+Constants.FIREBASE_USER_CONVO_WITH_EXTENSION+extend);

            final String convoID = newConvoRef.getKey();

            HashMap<String, Object> timestamp = new HashMap<>();
            timestamp.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

            Convo newConvo = new Convo(userEnteredName, mNewConvoWith.getText().toString(), timestamp);
            newConvoRef.setValue(newConvo);

            Convo newClientConvo = new Convo( userEnteredName,mEncodedEmail, timestamp);
            newOtherConvoRef.setValue(newClientConvo);

            Toast.makeText(getContext(), "New Conversation Created", Toast.LENGTH_LONG).show();

            Add_Convo.this.getDialog().cancel();


        }
    }

    /**
     * Add new meal
     */

}

