package com.sanjyog.www.tabbedacttest.ContactAdapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;
import com.sanjyog.www.tabbedacttest.R;
import com.sanjyog.www.tabbedacttest.model.User;

/**
 * Created by Sarthak on 2/18/2016.
 */
public class ContactAdapter extends FirebaseListAdapter<String> {

    public ContactAdapter(Activity activity, Class<String> modelClass, int modelLayout, Query ref){
        super(activity, modelClass, modelLayout, ref);
        this.mActivity = activity;
    }

    @Override
    protected void populateView(View v, String model) {
        TextView textView = (TextView) v.findViewById(R.id.Contact_Message_Box);

        textView.setText(model);
    }
}
