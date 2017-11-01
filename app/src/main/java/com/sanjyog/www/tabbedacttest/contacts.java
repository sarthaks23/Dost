package com.sanjyog.www.tabbedacttest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.sanjyog.www.tabbedacttest.ContactAdapter.ContactAdapter;
import com.sanjyog.www.tabbedacttest.Utils.Utils;

/**
 * Created by Sarthak on 12/24/2015.
 */
public class contacts extends Fragment {
    private ListView mListView;
    private ContactAdapter mContactAdapter;

    public contacts() {
        /* Required empty public constructor */
    }

    /**
     * Create fragment and pass bundle with data as it's arguments
     * Right now there are not arguments...but eventually there will be.
     */
    public static contacts newInstance() {
        contacts fragment = new contacts();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * Initalize UI elements
         */
        View rootView = inflater.inflate(R.layout.contacts, container, false);
        initializeScreen(rootView);


        Intent intent = getActivity().getIntent();
        final String user = Utils.encodeEmail(intent.getStringExtra("email"));

        Firebase refConvoName = new Firebase(Constants.FIREBASE_URL_USERS).child(Utils.encodeEmail(user) + Constants.FIREBASE_LOCATION_USER_CONTACTS);

        mContactAdapter = new ContactAdapter(getActivity(), String.class, R.layout.single_contact, refConvoName);

        mListView.setAdapter(mContactAdapter);


        /**
         * Set interactive bits, such as click events and adapters
         */
        mListView.setClickable(false);

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {



                return true;
            }
        });

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /**
     * Link layout elements from XML
     */
    private void initializeScreen(View rootView) {
        mListView = (ListView) rootView.findViewById(R.id.list_view_active_contact);
    }
}