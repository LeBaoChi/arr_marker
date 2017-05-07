//package com.example.lbochi.googlemap;
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.app.DialogFragment;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
///**
// * Created by Lê Bảo Chi on 5/6/2017.
// */
//
//public class EditarMark extends DialogFragment {
//    private static final String MARKER="title";
//    public static EditarMark newInstance(String title) {
//        EditarMark frag = new EditarMark();
//        Bundle args = new Bundle();
//        args.putString(MARKER, title);
//        frag.setArguments(args);
//        return frag;
//    }
//
//
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        final View dialogView = inflater.inflate(R.layout.dialog_marks, null);
//        final String markerTitle= getArguments().getString("thao");
//        final EditText etTitle= (EditText) dialogView.findViewById(R.id.test);
//
//
//        final AlertDialog dialoga= new AlertDialog.Builder(getActivity())
//                .setTitle("Change marker's title")
//                .setView(dialogView)
//                .setPositiveButton("Ok",null)
//                .setNegativeButton("Cancel",null)
//                .create();
//
//        dialoga.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialog) {
//                Button positive=dialoga.getButton(AlertDialog.BUTTON_POSITIVE);
//                Button negative=dialoga.getButton(AlertDialog.BUTTON_NEGATIVE);
//
//                positive.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ((MapsActivity)getActivity()).doChangeTitle(etTitle.getText());
//                        dialoga.dismiss();
//                    }
//                });
//                negative.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialoga.dismiss();
//                    }
//                });
//
//            }
//        });
//        return dialoga;
//    }
//
//}