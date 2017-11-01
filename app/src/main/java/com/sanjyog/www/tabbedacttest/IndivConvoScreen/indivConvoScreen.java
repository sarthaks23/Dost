package com.sanjyog.www.tabbedacttest.IndivConvoScreen;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import com.sanjyog.www.tabbedacttest.Constants;
import com.sanjyog.www.tabbedacttest.Messages.MessageAdapter;
import com.sanjyog.www.tabbedacttest.R;
import com.sanjyog.www.tabbedacttest.Utils.Utils;
import com.sanjyog.www.tabbedacttest.model.Convo;
import com.sanjyog.www.tabbedacttest.model.Message;
import com.sanjyog.www.tabbedacttest.remove_convo;

public class indivConvoScreen extends AppCompatActivity {

    private static final String LOG_TAG = indivConvoScreen.class.getSimpleName();
    private Firebase mActiveConvoref;
    private ListView mListView;
    private Convo mConvo;
    private String mListID;
    private ImageView sendButton;
    private EditText mInputMessage;
    private MessageAdapter mMessageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indiv_convo_screen);


        Intent intent = getIntent();
        String userInfo = intent.getStringExtra("userInfo");
        mListID = intent.getStringExtra("mListID");


        //Toast.makeText(getApplicationContext(), "mListID: " + mListID, Toast.LENGTH_LONG).show();
        if(mListID == null){
            finish();
            return;
        }
            mActiveConvoref = new Firebase(Constants.FIREBASE_URL_USERS).child(userInfo + Constants.FIREBASE_USER_ACTIVE_CONVO_EXTENSION).child(mListID);
                    //new Firebase(Constants.FIREBASE_URL_ACTIVE_CONVOS).child(mListID);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sendButton = (ImageView) findViewById(R.id.send_button);
        mInputMessage = (EditText) findViewById(R.id.inputMessage);
        mListView = (ListView) findViewById(R.id.MessageScreen);

        String[] stringSplitter = mListID.split(Constants.FIREBASE_USER_CONVO_WITH_EXTENSION);
        String sender = stringSplitter[0];
        final String reciever = stringSplitter[1];

        mActiveConvoref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Convo convo = snapshot.getValue(Convo.class);
                if (convo == null) {
                    finish();
                    return;
                }
                View view = mListView.getChildAt(mListView.getChildCount());
                mConvo = convo;
                setTitle(convo.getConvo_Name() + ": " + Utils.decodeEmail(reciever));

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] stringSplitter = mListID.split(Constants.FIREBASE_USER_CONVO_WITH_EXTENSION);
                String sender = stringSplitter[0];
                String reciever = stringSplitter[1];
                sendMessage(mInputMessage, sender, reciever);
            }
        });



        Firebase firebaseMessageLink = new Firebase(Constants.FIREBASE_URL_USERS).child(sender + Constants.FIREBASE_USER_MESSAGE_TO_EXTENSION + reciever);
        mMessageAdapter = new MessageAdapter(this, Message.class, R.layout.single_message_item, firebaseMessageLink, sender);

        mListView.setAdapter(mMessageAdapter);

        mListView.setSelection(mMessageAdapter.getCount()-1);




    }

    public void RenameConvoDialog(View view) {
        /* Create an instance of the dialog fragment and show it */
        android.support.v4.app.DialogFragment dialogFragment = settings_expander.newInstance();
        dialogFragment.show(indivConvoScreen.this.getSupportFragmentManager(), "New Conversation");
    }
    public void RemoveConvoDialog(View view) {
        /* Create an instance of the dialog fragment and show it */
        android.support.v4.app.DialogFragment dialogFragment = remove_convo.newInstance();
        dialogFragment.show(indivConvoScreen.this.getSupportFragmentManager(), "New Conversation");
    }

    public void sendMessage(EditText editText, String sender, String reciever){
        String MessageToSend = editText.getText().toString();
        Message myMessage = new Message(sender, MessageToSend);


        if(!MessageToSend.equals("")) {

            Firebase ChatRef = new Firebase(Constants.FIREBASE_URL_USERS).child((Utils.encodeEmail(sender)) + Constants.FIREBASE_USER_MESSAGE_TO_EXTENSION + (Utils.encodeEmail(reciever)));
            Firebase NewMessage = ChatRef.push();

            Firebase OtherChatRef = new Firebase(Constants.FIREBASE_URL_USERS).child((Utils.encodeEmail(reciever)) + Constants.FIREBASE_USER_MESSAGE_TO_EXTENSION + (Utils.encodeEmail(sender)));
            Firebase OtherNewMessage = OtherChatRef.push();

            NewMessage.setValue(myMessage);
            OtherNewMessage.setValue(myMessage);

            editText.setText("");
        }

        else{
            Toast.makeText(getApplicationContext(), "Cannot Send Blank Message", Toast.LENGTH_SHORT).show();
        }

        //TextView textView = (TextView) mListView.getChildAt(0).findViewById(R.id.MessageBox);
        //Toast.makeText(getApplicationContext(), textView.getText(), Toast.LENGTH_LONG).show();

    }

}
