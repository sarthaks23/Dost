package com.sanjyog.www.tabbedacttest.IndivConvoScreen;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;
import com.sanjyog.www.tabbedacttest.Add_Convo;
import com.sanjyog.www.tabbedacttest.Constants;
import com.sanjyog.www.tabbedacttest.R;


import java.util.HashMap;

/**
 * Created by Sarthak on 12/30/2015.
 */
public class settings_expander extends Add_Convo {
    private static final String LOG_TAG = indivConvoScreen.class.getSimpleName();
    TextView mTextViewSettings, mTextViewRenameConvo, mTextViewRemoveConvo;
    EditText mReConvoName;
    String mListID;

    public static settings_expander newInstance() {
        settings_expander settingsExpander = new settings_expander();
        Bundle bundle = new Bundle();
        settingsExpander.setArguments(bundle);
        return settingsExpander;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent intent = getActivity().getIntent();
        mListID = intent.getStringExtra("mListID");
        //Toast.makeText(getContext(), "Dialog Recieved IntentExtra mListID", Toast.LENGTH_LONG).show();

    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /* Use the Builder class for convenient dialog construction */
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomTheme_Dialog);
        /* Get the layout inflater */
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_indiv_settings, null);
        mTextViewRenameConvo = (TextView) rootView.findViewById(R.id.rename_convo);
        mReConvoName = (EditText) rootView.findViewById(R.id.re_convo_name);
        mTextViewRemoveConvo = (TextView) rootView.findViewById(R.id.removeConvo);

        mReConvoName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    addNewConvo();
                }
                return true;
            }
        });
        builder.setView(rootView)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addNewConvo();
                    }
                });

        builder.setView(rootView);

        return builder.create();

    }

    public void addNewConvo() {
        Intent intent = getActivity().getIntent();
        String userInfo = intent.getStringExtra("userInfo");

        String[] stringSplitter = mListID.split("_ConvoWith_");

        Firebase ref = new Firebase(Constants.FIREBASE_URL_USERS).child(userInfo + Constants.FIREBASE_USER_ACTIVE_CONVO_EXTENSION).child(mListID);
        Firebase clientRef = new Firebase(Constants.FIREBASE_URL_USERS).child(stringSplitter[1] + Constants.FIREBASE_USER_ACTIVE_CONVO_EXTENSION).child(stringSplitter[1] + Constants.FIREBASE_USER_CONVO_WITH_EXTENSION + stringSplitter[0]);
                //new Firebase(Constants.FIREBASE_URL_ACTIVE_CONVOS).child(mListID);
        final String inputConvoName = mReConvoName.getText().toString();
        if(!inputConvoName.equals("")){
            HashMap<String, Object> updatedProperties = new HashMap<String, Object>();
            updatedProperties.put("convo_Name", inputConvoName);

            HashMap<String, Object> newTimeStamp = new HashMap<String, Object>();
            newTimeStamp.put("timestamp", ServerValue.TIMESTAMP);

            updatedProperties.put("timestamp", newTimeStamp);

            ref.updateChildren(updatedProperties);
            clientRef.updateChildren(updatedProperties);

            Toast.makeText(getContext(), "Convo was renamed!", Toast.LENGTH_LONG).show();

            settings_expander.this.getDialog().cancel();
        }
    }
}
