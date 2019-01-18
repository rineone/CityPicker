package com.rine.citypicker.util;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedUtil {

    //存储的sharedpreferences文件名
    private static final String FILE_NAME = "CityPicker";
    //存储历史城市数据
    public static final String HIS_FILE_KEY = "HisFileCityKey";

    /**
     * 保存数据到文件
     * @param context
     * @param key
     * @param data
     */
    public static void saveData(Context context, String key,Object data){

        String type = data.getClass().getSimpleName();
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();

        if ("Integer".equals(type)){
            editor.putInt(key, (Integer)data);
        }else if ("Boolean".equals(type)){
            editor.putBoolean(key, (Boolean)data);
        }else if ("String".equals(type)){
            editor.putString(key, (String)data);
        }else if ("Float".equals(type)){
            editor.putFloat(key, (Float)data);
        }else if ("Long".equals(type)){
            editor.putLong(key, (Long)data);
        }

        editor.commit();
    }

    /**
     * 从文件中读取数据
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static Object getData(Context context, String key, Object defValue){

        String type = defValue.getClass().getSimpleName();
        SharedPreferences sharedPreferences = context.getSharedPreferences
                (FILE_NAME, Context.MODE_PRIVATE);

        //defValue为为默认值，如果当前获取不到数据就返回它
        if ("Integer".equals(type)){
            return sharedPreferences.getInt(key, (Integer)defValue);
        }else if ("Boolean".equals(type)){
            return sharedPreferences.getBoolean(key, (Boolean)defValue);
        }else if ("String".equals(type)){
            return sharedPreferences.getString(key, (String)defValue).toString();
        }else if ("Float".equals(type)){
            return sharedPreferences.getFloat(key, (Float)defValue);
        }else if ("Long".equals(type)){
            return sharedPreferences.getLong(key, (Long)defValue);
        }
        return null;
    }

    /**
     * 从文件中读取数据
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static String getData(Context context, String key, String defValue){
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences
                    (FILE_NAME, Context.MODE_PRIVATE);
            String res = sharedPreferences.getString(key,  defValue).toString();
            return res;
        }catch (Exception e){
            return "0";
        }
    }



    public static void deleteData(Context context, String key)
    {
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }
}
