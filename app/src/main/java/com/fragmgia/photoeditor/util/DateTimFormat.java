package com.fragmgia.photoeditor.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by trungnguyens93gmail.com on 5/9/17.
 */
public class DateTimFormat {
    public static String getCurrentTime() {
        SimpleDateFormat
            simpleDateFormat = new SimpleDateFormat(ConstantManager.DATE_TIME_FORMAT, Locale.US);
        return  simpleDateFormat.format(new Date());
    }
}
