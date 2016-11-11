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
        void onDialogPositiveClick(DialogFragment dialog);
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


        // Use the Builder class for convenient dialogInfo construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_username, null,false);

        tvUsername = (TextView)rootView.findViewById(R.id.usernameText);
        tvServer = (TextView)rootView.findViewById(R.id.serverText);
        tvGroup= (TextView)rootView.findViewById(R.id.groupnameText);

        // Inflate and set the layout for the dialogInfo
        // Pass null as the parent view because its going in the dialogInfo layout
        builder.setView(inflater.inflate(R.layout.dialog_username, null))
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick(SetInfoDialog.this);
                    }
                });
        return builder.create();
    }

    public String GetUsername(){
        if (!tvUsername.getText().toString().isEmpty())
            return tvUsername.getText().toString();
        return "Invitado";
    }

    public String GetServer(){
        if (!tvServer.getText().toString().isEmpty())
            return tvServer.getText().toString();
        return "104.154.240.176:18003/";
    }

    public String GetGroup(){
        if (!tvGroup.getText().toString().isEmpty())
            return tvGroup.getText().toString();
        return "test1";
    }
}
