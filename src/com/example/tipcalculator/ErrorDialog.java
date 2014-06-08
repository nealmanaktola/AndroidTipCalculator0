package com.example.tipcalculator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;


public class ErrorDialog extends DialogFragment {
	
    public ErrorDialog() {
        // Empty constructor required for DialogFragment
    }

    public static ErrorDialog newInstance(String title,String message) {
        ErrorDialog frag = new ErrorDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("error", message);
        frag.setArguments(args);
        return frag;
    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        String message = getArguments().getString("error");
        
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                  // on success
            }
        });
        return alertDialogBuilder.create();
   }

}
