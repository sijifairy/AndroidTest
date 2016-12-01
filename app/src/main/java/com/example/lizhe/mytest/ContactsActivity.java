package com.example.lizhe.mytest;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/**
 * Reference https://developer.android.com/training/contacts-provider/index.html.
 */
public class ContactsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    /*
         * Defines an array that contains column names to move from
         * the Cursor to the ListView.
         */
    @SuppressLint("InlinedApi")
    private final static String[] FROM_COLUMNS = {
            Build.VERSION.SDK_INT
                    >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Contacts.DISPLAY_NAME
    };
    @SuppressLint("InlinedApi")
    private static final String[] PROJECTION =
            {
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.LOOKUP_KEY,
                    Build.VERSION.SDK_INT
                            >= Build.VERSION_CODES.HONEYCOMB ?
                            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                            ContactsContract.Contacts.DISPLAY_NAME

            };
    // The column index for the _ID column
    private static final int CONTACT_ID_INDEX = 0;
    // The column index for the LOOKUP_KEY column
    private static final int LOOKUP_KEY_INDEX = 1;


    // Defines the text expression
    @SuppressLint("InlinedApi")
    private static final String SELECTION =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?" :
                    ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?";
    // Defines the text expression
    @SuppressLint("InlinedApi")
    private static final String SELECTION_COMBINATION = "(" + ContactsContract.CommonDataKinds.Email.ADDRESS + " LIKE ? " + "AND " +
            ContactsContract.Data.MIMETYPE + " = '" + ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE + "'" + ")"
            + " OR"
            + "(" + ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME + " LIKE ? " + "AND " +
            ContactsContract.Data.MIMETYPE + " = '" + ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE + "'" + ")"
            + " OR"
            + "(" + ContactsContract.CommonDataKinds.Phone.NUMBER + " LIKE ? " + "AND " +
            ContactsContract.Data.MIMETYPE + " = '" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'" + ")";
    // Defines a variable for the search string
    private String mSearchString = "";
    // Defines the array to hold values that replace the ?
    private String[] mSelectionArgs = {mSearchString, mSearchString, mSearchString};

    /*
     * Defines an array that contains resource ids for the layout views
     * that get the Cursor column contents. The id is pre-defined in
     * the Android framework, so it is prefaced with "android.R.id"
     */
    private final static int[] TO_IDS = {
            android.R.id.text1
    };
    // Define global mutable variables
    // Define a ListView object
    ListView mContactsList;
    // Define variables for the contact the user selects
    // The contact's _ID value
    long mContactId;
    // The contact's LOOKUP_KEY
    String mContactKey;
    // A content URI for the selected contact
    Uri mContactUri;
    // An adapter that binds the result Cursor to the ListView
    private SimpleCursorAdapter mCursorAdapter;

    private EditText mSearch;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        // Gets the ListView from the View list of the parent activity
        mContactsList = (ListView) findViewById(R.id.list);
        mSearch = (EditText) findViewById(R.id.search);
        // Gets a CursorAdapter
        mCursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.layout_contacts_list_item,
                null,
                FROM_COLUMNS, TO_IDS,
                0);
        // Sets the adapter for the ListView
        mContactsList.setAdapter(mCursorAdapter);
        // Set the item click listener to be the current fragment.
        mContactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                // Get the Cursor
//                Cursor cursor = adapterView.getAdapter().getCursor();
//                // Move to the selected contact
//                cursor.moveToPosition(position);
//                // Get the _ID value
//                mContactId = getLong(CONTACT_ID_INDEX);
//                // Get the selected LOOKUP KEY
//                mContactKey = getString(CONTACT_KEY_INDEX);
//                // Create the contact's content Uri
//                mContactUri = ContactsContract.Contacts.getLookupUri(mContactId, mContactKey);
//                /*
//                 * You can use mContactUri as the content URI for retrieving
//                 * the details for a contact.
//                 */
            }
        });

        getLoaderManager().initLoader(0, null, this);

        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("contacts", "search on text changed " + s.toString());
                mSearchString = s.toString();
                getLoaderManager().restartLoader(0, null, ContactsActivity.this);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        /*
         * Makes search string into pattern and
         * stores it in the selection array
         */
        time = System.currentTimeMillis();
        mSelectionArgs[0] = "%" + mSearchString.replace(" ", "%") + "%";
        mSelectionArgs[1] = "%" + mSearchString.replace(" ", "%") + "%";
        mSelectionArgs[2] = "%" + mSearchString.replace(" ", "%") + "%";
        // Starts the query
        return new CursorLoader(
                this,
                ContactsContract.Data.CONTENT_URI,
                PROJECTION,
                SELECTION_COMBINATION,
                mSelectionArgs,
                null
        );
    }

//    @Override
//    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
//        /*
//         * Appends the search string to the base URI. Always
//         * encode search strings to ensure they're in proper
//         * format.
//         */
//        time = System.currentTimeMillis();
//        Uri contentUri = Uri.withAppendedPath(
//                ContactsContract.Contacts.CONTENT_FILTER_URI,
//                Uri.encode(mSearchString));
//        // Starts the query
//        return new CursorLoader(
//                ContactsActivity.this,
//                contentUri,
//                PROJECTION,
//                null,
//                null,
//                null
//        );
//    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Put the result Cursor in the adapter for the ListView
        Toast.makeText(ContactsActivity.this, "query cost time in mills is " + (System.currentTimeMillis() - time), Toast.LENGTH_SHORT).show();
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Delete the reference to the existing Cursor
        mCursorAdapter.swapCursor(null);
    }
}
