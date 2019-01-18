package com.rine.citypicker.adapter;

import com.rine.citypicker.model.City;

public interface InnerListener {
    void dismiss(int position, City data);
    void locate();
}
