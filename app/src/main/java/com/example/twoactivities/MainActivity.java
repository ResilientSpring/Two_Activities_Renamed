package com.example.twoactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    public static final String EXTRA_MESSAGE = "com.example.android.twoactivities.extra.MESSAGE";

    private EditText mMessageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMessageEditText = findViewById(R.id.editText_main);


        // Use findViewByID() to get references from the layout to the reply header and reply TextView elements.
        // Assign those view instances to the private variables
        mReplyHeadTextView = findViewById(R.id.text_header_reply);
        mReplyTextView = findViewById(R.id.text_message_reply);
    }

    public void launchSecondActivity(View view) {
        Log.d(LOG_TAG, "Button clicked!");

        Intent intent = new Intent(this, SecondActivity.class);
        String message = mMessageEditText.getText().toString();

        /*
        * getText():
        * Get and return string from the textview, and to be stored as type of Charsequence
        *
        * toString():
        * Convert Charsequence into String type
        *
        * References:
        * 1. https://developer.android.com/reference/android/widget/TextView#getText()
        * 2. https://developer.android.com/reference/java/lang/String#toString()
        * 3. https://web.archive.org/web/20201004125944/https://stackoverflow.com/questions/47150909/what-does-gettext-tostring-return
        * 4. https://web.archive.org/web/20201004125946/https://stackoverflow.com/questions/50539062/textview-gettext-tostring-vs-textview-tostring
        * */

        // Add that String message to the Intent as an extra
        // with the constant "EXTRA_MESSAGE" as the key and the string "message" as the value
        intent.putExtra(EXTRA_MESSAGE, message);
        //  startActivity(intent);
        startActivityForResult(intent, TEXT_REQUEST);
    }

    // Add a public constant to define the key for a particular type of response you're interested in.
    public static final int TEXT_REQUEST = 1;

    // Add two private variables to hold the reply header and reply TextView elements, respectively.
    private TextView mReplyHeadTextView;
    private TextView mReplyTextView;

    /*
    The three arguments to onActivityResult() contain all the information you need to handle the return data:
    the requestCode you set when you launched the Activity with startActivityForResult(),
    the resultCode set in the launched Activity (usually one of RESULT_OK or RESULT_CANCELED), and
    the Intent data that contains the data returned from the launch Activity. */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Add code to test for TEXT_REQUEST to make sure you process the right Intent result,
        // in case there are several.
        if (requestCode == TEXT_REQUEST) {

            // Also test for RESULT_OK, to make sure that the request was successful.
            if (resultCode == RESULT_OK) {

                // get the Intent extra from the response Intent (data).
                // Here the key for the extra is the constant EXTRA_REPLY from SecondActivity.
                String reply = data.getStringExtra(SecondActivity.EXTRA_REPLY);

                // Set the visibility of the reply header to true.
                mReplyHeadTextView.setVisibility(View.VISIBLE);

                // Set the reply TextView text to the reply, and set its visibility to true.
                mReplyTextView.setText(reply);
                mReplyTextView.setVisibility(View.VISIBLE);

            }
        /**
         * The Activity class defines the result codes.
         * The code can be RESULT_OK (the request was successful),
         * RESULT_CANCELED (the user cancelled the operation),
         * or RESULT_FIRST_USER (for defining your own result codes).
         * */

        }

    }
}