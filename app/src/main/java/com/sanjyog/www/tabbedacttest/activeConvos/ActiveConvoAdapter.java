package com.sanjyog.www.tabbedacttest.activeConvos;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;
import com.sanjyog.www.tabbedacttest.R;
import com.sanjyog.www.tabbedacttest.Utils.Utils;
import com.sanjyog.www.tabbedacttest.model.Convo;

import java.util.Date;

/**
 * Created by Sarthak on 12/31/2015.
 */
public class ActiveConvoAdapter extends FirebaseListAdapter<Convo> {

    /**
     * Public constructor that initializes private instance variables when adapter is created
     */

    public ActiveConvoAdapter(Activity activity, Class<Convo> modelClass, int modelLayout, Query ref){
        super(activity, modelClass, modelLayout, ref);
        this.mActivity = activity;
    }

    /**
     * Protected method that populates the view attached to the adapter (list_view_active_lists)
     * with items inflated from single_active_list.xml
     * populateView also handles data changes and updates the listView accordingly
     */

    @Override
    protected void populateView(View view, Convo convo){
        /**
         * Grab the needed Textvews and strings
         */
        TextView textViewConvoName = (TextView) view.findViewById(R.id.Convo_name);
        TextView textViewCreatedByUser = (TextView) view.findViewById(R.id.creator);
        TextView textViewTimestamp = (TextView) view.findViewById(R.id.last_used_date);

        //Set TextView Values
        textViewConvoName.setText(convo.getConvo_Name());
        textViewCreatedByUser.setText(convo.getOwner());
        textViewTimestamp.setText(Utils.SIMPLE_DATE_FORMAT.format(new Date(convo.getTimestampLong())));

    }
}
