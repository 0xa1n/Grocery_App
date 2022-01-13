package com.app.groccery_app.utils;

import java.util.Calendar;
import java.util.Date;

public class dateUtils {

    public static String convertToDate(int _day, int _month,int _year) {

//        Calendar calendar = Calendar.getInstance();
//        calendar.set(_year, _month, _day);

        return String.valueOf(_day)+String.valueOf(_month)+String.valueOf(_year);
    }

    public  static Date getDate(int _day, int _month,int _year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(_year, _month, _day);

        return calendar.getTime();
    }
}
