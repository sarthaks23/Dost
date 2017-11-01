package com.sanjyog.www.tabbedacttest;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.client.Firebase;

/**
 * Created by Sarthak on 1/3/2016.
 */
public class remove_convo extends DialogFragment {
    private LinearLayout mLinearLayoutRemove;
    private String mListID;
    /**
     * Public static constructor that creates fragment and
     * passes a bundle with data into it when adapter is created
     */

    public static remove_convo newInstance() {
        remove_convo removeConvo = new remove_convo();
        Bundle bundle = new Bundle();
        removeConvo.setArguments(bundle);
        return removeConvo;
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent intent = getActivity().getIntent();
        mListID = intent.getStringExtra("mListID");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

                /* Use the Builder class for convenient dialog construction */
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomTheme_Dialog);
        /* Get the layout inflater */
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_remove_convo, null);
        mLinearLayoutRemove = (LinearLayout) rootView.findViewById(R.id.removeClickable);

        /* Inflate and set the layout for the dialog */
        /* Pass null as the parent view because its going in the dialog layout */

        builder.setView(rootView)
                .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        removeConvo();
                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                remove_convo.this.getDialog().cancel();
            }
        });


        return builder.create();

    }

    public void removeConvo() {
        Intent intent = getActivity().getIntent();
        String userInfo = intent.getStringExtra("userInfo");
        String[] stringSplitter = mListID.split("_ConvoWith_");

        Firebase mListToRemove = new Firebase(Constants.FIREBASE_URL_USERS).child(userInfo + Constants.FIREBASE_USER_ACTIVE_CONVO_EXTENSION).child(mListID);
        Firebase mClientListToRemove = new Firebase(Constants.FIREBASE_URL_USERS).child(stringSplitter[1] + Constants.FIREBASE_USER_ACTIVE_CONVO_EXTENSION).child(stringSplitter[1] + Constants.FIREBASE_USER_CONVO_WITH_EXTENSION + stringSplitter[0]);
        Firebase mConvoToRemove = new Firebase(Constants.FIREBASE_URL_USERS).child(stringSplitter[1] + Constants.FIREBASE_USER_MESSAGE_TO_EXTENSION + stringSplitter[0]);
        Firebase mClientConvoToRemove = new Firebase(Constants.FIREBASE_URL_USERS).child(stringSplitter[0] + Constants.FIREBASE_USER_MESSAGE_TO_EXTENSION   + stringSplitter[1]);



        mListToRemove.removeValue();
        mClientListToRemove.removeValue();
        mConvoToRemove.removeValue();
        mClientConvoToRemove.removeValue();

        Toast.makeText(getContext(), "Convo was removed!", Toast.LENGTH_LONG).show();
    }


}
