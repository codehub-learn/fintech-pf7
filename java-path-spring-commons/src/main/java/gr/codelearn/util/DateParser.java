package gr.codelearn.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateParser {
    public static Date convertFromString(String dateStr) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
        formatter.setLenient(false);
        return formatter.parse(dateStr);
    }
}
