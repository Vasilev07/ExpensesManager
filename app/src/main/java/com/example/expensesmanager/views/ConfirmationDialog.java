package com.example.expensesmanager.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.expensesmanager.R;

public class ConfirmationDialog extends DialogFragment {
    private static final String TAG = "ConfirmationDialog";

    public interface OnInputListener{
        void sendInput(String input);
    }
    public OnInputListener mOnInputListener;
    private TextView mActionOk, mActionCancel;
    //vars

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.confirmation_dialog, container, false);
        mActionCancel = view.findViewById(R.id.action_cancel);
        mActionOk = view.findViewById(R.id.action_ok);

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });


        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mOnInputListener.sendInput(input);
                // delete the
                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnInputListener = (OnInputListener) getActivity();
        }catch (ClassCastException e){
            /*Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );*/
        }
    }
}