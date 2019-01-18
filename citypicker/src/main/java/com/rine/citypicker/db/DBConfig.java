package com.rine.citypicker.db;

/**
 * 数据键值
 * @version 1.0(2019/1/16)
 * @author rine
 */
public class DBConfig {
    //V1和V2在每次更新数据库时，调换，还要将assets的名字更换为LATEST_DB_NAME的名字相同
    public static final String DB_NAME_V1 = "china_cities_v2.db";
    public static final String DB_NAME_V2 = "china_cities.db";
    public static final String LATEST_DB_NAME = DB_NAME_V2;

    public static final String TABLE_NAME = "city";
public static final String COLUMN_C_NAME = "c_name";
    public static final String COLUMN_C_PROVINCE = "c_province_code";
    public static final String COLUMN_C_PINYIN = "c_pinyin";
    public static final String COLUMN_C_CODE = "id";
}
