package com.sanjyog.www.tabbedacttest;

/**
 * Created by Sarthak on 12/24/2015.
 */
/**
 * Constants class store most important strings and paths of the app
 */
public final class Constants {

    /**
     * Constants related to locations in Firebase, such as the name of the node
     * where active lists are stored (ie "activeLists")
     */
    public static final String FIREBASE_LOCATION_ACTIVE_CONVOS = "activeConvos";
    public static final String FIREBASE_LOCATION_USERS = "users";
    public static final String FIREBASE_USER_ACTIVE_CONVO_EXTENSION = "_ActiveConvos";
    public static final String FIREBASE_USER_CONVO_WITH_EXTENSION = "_ConvoWith_";
    public static final String FIREBASE_USER_MESSAGE_TO_EXTENSION = "_MessageTo_";
    public static final String FIREBASE_LOCATION_USER_ARRAY = "userArray";
    public static final String FIREBASE_LOCATION_USER_CONTACTS = "_contacts";


    /**
     * Constants for Firebase object properties
     */
    public static final String FIREBASE_PROPERTY_CONVO_NAME = "Convo_Name";
    public static final String FIREBASE_PROPERTY_TIMESTAMP = "timestamp";
    public static final String FIREBASE_PROPERTY_TIMESENT = "timesent";
    public static final String FIREBASE_PROPERTY_EMAIL = "email";

    /**
     * Constants for Firebase URL
     */
    public static final String FIREBASE_URL = BuildConfig.UNIQUE_FIREBASE_ROOT_URL;
    public static final String FIREBASE_URL_ACTIVE_CONVOS = FIREBASE_URL + "/" + FIREBASE_LOCATION_ACTIVE_CONVOS;
    public static final String FIREBASE_URL_USERS = FIREBASE_URL + "/" + FIREBASE_LOCATION_USERS;
    public static final String FIREBASE_URL_USER_ARRAY = FIREBASE_URL + "/" + FIREBASE_LOCATION_USER_ARRAY;



    /**
     * Constants for bundles, extras and shared preferences keys
     */
    public static final String KEY_LIST_ID = "ListID";
    public static final String KEY_PROVIDER = "PROVIDER";
    public static final String KEY_ENCODED_EMAIL = "ENCODED_EMAIL";

    public static final String PASSWORD_PROVIDER = "password";
    public static final String GOOGLE_PROVIDER = "google";
    public static final String PROVIDER_DATA_DISPLAY_NAME = "displayName";


}