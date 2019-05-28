package com.beep.youseesd.view;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.beep.youseesd.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class TourRateDialogView extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View viewRoot = inflater.inflate(R.layout.dialog_rate_tour, null);

        //do something with your view
        builder.setView(viewRoot);
        return builder.create();
    }
}
