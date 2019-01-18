package com.rine.citypicker.adapter;

import com.rine.citypicker.model.City;

public interface OnPickListener {
    void onPick(int position, City data);
    void onLocate();
    void onCancel();
}
