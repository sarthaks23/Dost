package com.sanjyog.www.tabbedacttest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.sanjyog.www.tabbedacttest.Utils.Utils;

/**
 * Created by Sarthak on 12/24/2015.
 */
public class add_a_dost extends Fragment {
    private ListView mListView;
    private EditText mContactSearch;
    private TextView mContactSuggestions, mSearchButton;
    private LinearLayout mSearchSuggestions;


    public add_a_dost() {
        /* Required empty public constructor */
    }

    /**
     * Create fragment and pass bundle with data as it's arguments
     * Right now there are not arguments...but eventually there will be.
     */
    public static add_a_dost newInstance() {
        add_a_dost fragment = new add_a_dost();
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
        View rootView = inflater.inflate(R.layout.add_a_dost, container, false);
        initializeScreen(rootView);
        final CheckBox checkBox = (CheckBox) rootView.findViewById(R.id.emailCheckBox);
        Intent intent = getActivity().getIntent();
        final String user = Utils.encodeEmail(intent.getStringExtra("email"));

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String input = mContactSearch.getText().toString();
                mContactSuggestions.setText(input);

                /*if(input!="") {
                    mSearchSuggestions.addView(checkBox, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                }*/
               checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                   @Override
                   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                       if(isChecked == true && input!="" && input.contains("@")){
                           AddToMyContacts(Utils.encodeEmail(user) , Utils.encodeEmail(input));
                           Toast.makeText(getContext(), "New Dost Added!",Toast.LENGTH_LONG).show();
                           mContactSearch.setText("");
                           checkBox.setChecked(false);
                           mContactSuggestions.setText("");
                       }
                       else if(!input.contains("@")){
                           Toast.makeText(getContext(), "An Error Occurred! Try Again.",Toast.LENGTH_LONG).show();
                           mContactSearch.setError("Not a Valid Email");
                           checkBox.setChecked(false);
                       }
                   }
               });

            }

        });


        /**
         * Set interactive bits, such as click events and adapters
         */
       /* mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/

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
        mListView = (ListView) rootView.findViewById(R.id.list_view_active_lists);
        mContactSearch = (EditText) rootView.findViewById(R.id.dost_search_bar);
        mContactSuggestions = (TextView) rootView.findViewById(R.id.emailBox);
        mSearchButton = (TextView) rootView.findViewById(R.id.dost_search_button);
        mSearchSuggestions = (LinearLayout) rootView.findViewById(R.id.contactSuggestion);
    }

    public void AddToMyContacts(String user, final String inputUN){
        final Firebase mRef = new Firebase(Constants.FIREBASE_URL_USERS).child(user + Constants.FIREBASE_LOCATION_USER_CONTACTS);
        //Firebase mRefContactDatabse = new Firebase(Constants.FIREBASE_URL_USER_ARRAY);

        mRef.child(inputUN).setValue(Utils.decodeEmail(inputUN));

        /*mRefContactDatabse.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(Utils.encodeEmail(inputUN))){
                    Contact myContact = (Contact) dataSnapshot.child(inputUN).getValue();
                    mRef.child(inputUN).setValue(myContact);
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/

    }
}