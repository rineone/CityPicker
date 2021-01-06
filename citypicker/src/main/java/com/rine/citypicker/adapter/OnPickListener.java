package com.rine.citypicker.adapter;

import android.view.View;

import com.rine.citypicker.model.City;

import androidx.fragment.app.DialogFragment;

public interface OnPickListener {
    void onPick(int position, City data);
    void onLocate();
    void onCancel();
    void onGetView(View view, DialogFragment dialogFragment);
}
