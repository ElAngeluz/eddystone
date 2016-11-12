package com.google.sample.eddystonevalidator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by angeluz on 11/3/2016.
 */

public class SetInfoDialog extends DialogFragment {

    private TextView tvUsername;
    private TextView tvServer;
    private TextView tvGroup;

    /* The activity that creates an instance of this dialogInfo fragment must
         * implement this interface in order to receive event callbacks.
         * Each method passes the DialogFragment in case the host needs to query it. */
    public interface InfoDialogListener {

        void onDialogPositiveClick(String _User, String _Server, String _Group);
    }
    // Use this instance of the interface to deliver action events
    InfoDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the InfoDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the InfoDialogListener so we can send events to the host
            mListener = (InfoDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + "must implement InfoDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle("Data")
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                View rootView = getActivity().getLayoutInflater().inflate(R.layout.dialog_username, null,false);
                                tvUsername = (TextView)rootView.findViewById(R.id.usernameText);
                                tvServer = (TextView)rootView.findViewById(R.id.serverText);
                                tvGroup= (TextView)rootView.findViewById(R.id.groupnameText);

                                if (tvUsername.getText().toString().isEmpty())
                                    tvUsername.setText(getResources().getString(R.string.usernameDefault));

                                if (tvServer.getText().toString().isEmpty())
                                    tvServer.setText(getResources().getString(R.string.serverDefault));

                                if (tvGroup.getText().toString().isEmpty())
                                    tvGroup.setText(getResources().getString(R.string.groupDefault));

                                mListener.onDialogPositiveClick(
                                        tvUsername.getText().toString(),
                                        tvServer.getText().toString(),
                                        tvGroup.getText().toString());
                            }
                        }
                )
                .setView(getActivity().getLayoutInflater().inflate(R.layout.dialog_username, null))
                .create();

    }

    static SetInfoDialog newInstance(int num) {
        SetInfoDialog f = new SetInfoDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

}
