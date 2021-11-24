package gr.codelearn.util;

import gr.codelearn.base.AbstractLogEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateParser extends AbstractLogEntity {
    public static Date convertFromString(String dateStr) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
        formatter.setLenient(false);
        return formatter.parse(dateStr);
    }
}
