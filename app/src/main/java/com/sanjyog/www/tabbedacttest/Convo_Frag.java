package com.sanjyog.www.tabbedacttest;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.sanjyog.www.tabbedacttest.IndivConvoScreen.indivConvoScreen;
import com.sanjyog.www.tabbedacttest.Utils.Utils;
import com.sanjyog.www.tabbedacttest.activeConvos.ActiveConvoAdapter;
import com.sanjyog.www.tabbedacttest.model.Convo;


/**
 * Created by Sarthak on 12/24/2015.
 */
public class Convo_Frag extends Fragment {
    public static final String latestMessage = null;
    private ListView mListView;
    private ActiveConvoAdapter mActiveConvoAdapter;

    public Convo_Frag() {
        /* Required empty public constructor */
    }

    /**
     * Create fragment and pass bundle with data as it's arguments
     * Right now there are not arguments...but eventually there will be.
     */
    public static Convo_Frag newInstance() {
        Convo_Frag fragment = new Convo_Frag();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
        View rootView = inflater.inflate(R.layout.convo_screen, container, false);
        initializeScreen(rootView);

        Intent intent = getActivity().getIntent();
        final String user = Utils.encodeEmail(intent.getStringExtra("email"));

        //Initialize Firebase reference for @id/convo_name
        Firebase refConvoName = new Firebase(Constants.FIREBASE_URL_USERS).child(user + Constants.FIREBASE_USER_ACTIVE_CONVO_EXTENSION);

        mActiveConvoAdapter = new ActiveConvoAdapter(getActivity(), Convo.class, R.layout.single_convo_item, refConvoName);
        mListView.setAdapter(mActiveConvoAdapter);



        /**
         * Set interactive bits, such as click events and adapters
         */
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Convo selectedConvo = mActiveConvoAdapter.getItem(position);
                if (selectedConvo != null) {
                    Intent my_intent = new Intent(getActivity(), indivConvoScreen.class);
                    String mListId = mActiveConvoAdapter.getRef(position).getKey();

                    my_intent.putExtra("mListID", mActiveConvoAdapter.getRef(position).getKey());
                    my_intent.putExtra("userInfo", user);


                    //Toast.makeText(getContext(), "Selection process is working.", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getContext(), "Key is: " + mListId, Toast.LENGTH_LONG).show();
                    startActivity(my_intent);
                }
            }
        });


        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActiveConvoAdapter.cleanup();
    }


    /**
     * Link layout elements from XML
     */
    private void initializeScreen(View rootView) {
        mListView = (ListView) rootView.findViewById(R.id.list_view_active_lists);
    }
}