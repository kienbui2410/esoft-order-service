package com.esoft.orderservice.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String convertDateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyymmdd");
        return dateFormat.format(date);
    }
}
