package com.rine.citypicker.model;

import java.io.Serializable;

/**
 * 历史城市
 * @author rine
 * @version 1.0(2019/1/17)
 */
public class HisCity extends City implements Serializable {

    public HisCity(String name, String province, String code) {
        super(name, province, "历史城市", code);
    }
}
