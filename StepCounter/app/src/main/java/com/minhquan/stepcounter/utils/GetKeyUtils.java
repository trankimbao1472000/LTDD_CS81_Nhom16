package com.minhquan.stepcounter.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class GetKeyUtils {
    public static String getKey(){
        Calendar today = new GregorianCalendar();
        SimpleDateFormat sp = new SimpleDateFormat("dd/MM/yy|hh:mm:ss");
        return sp.format(today.getTime());
    }
}
