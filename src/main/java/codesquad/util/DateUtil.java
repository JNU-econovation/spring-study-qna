package codesquad.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final String DEFAULT_FORMAT = "YYYY-MM-dd HH:mm";

    public static String getFormattedDate(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static String getFormattedDate(Date date) {
        return getFormattedDate(date, DEFAULT_FORMAT);
    }
}
