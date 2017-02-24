package com.mfz.dynamicdb;

/**
 * @author  fanzhenmeng
 * date 2017-2-22
 */
public class DynamicDataSourceHolder {
    public static ThreadLocal<String> dsKey = new ThreadLocal<String>();

    public static  String MASTER_KEY = "master";
    public static  String SLAVE_KEY = "slave";

    public static void setDsKeyMaster(){
        dsKey.set(MASTER_KEY);
    }

    public static void setDsKeySlave(){
        dsKey.set(SLAVE_KEY);
    }

    public static String getDsKey(){
        return  dsKey.get();
    }
}
