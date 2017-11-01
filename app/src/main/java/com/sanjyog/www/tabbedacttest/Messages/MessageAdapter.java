package com.sanjyog.www.tabbedacttest.Messages;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;
import com.sanjyog.www.tabbedacttest.R;
import com.sanjyog.www.tabbedacttest.model.Message;

/**
 * Created by Sarthak on 2/17/2016.
 */
public class MessageAdapter extends FirebaseListAdapter<Message> {

    private String mUsername;


    public MessageAdapter(Activity activity, Class<Message> modelClass, int modelLayout, Query ref, String mUsername){
        super(activity, modelClass, modelLayout, ref);
        this.mActivity = activity;
        this.mUsername = mUsername;
    }

    @Override
    protected void populateView(View v, Message message) {

        TextView Message = (TextView) v.findViewById(R.id.MessageBox);

        Message.setText(message.getMessage());

        if((message.getSender()).equals(mUsername)){

            Message.setTextColor(Color.parseColor("#FF5722"));
            Message.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);


        }

        else if(!(message.getSender()).equals(mUsername)){
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) Message.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            Message.setTextColor(Color.parseColor("#388E3C"));
            Message.setLayoutParams(params);
            Message.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);

        }



    }
}
