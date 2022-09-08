package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

public final class Utils {
    private Utils() {}
    
    public static java.util.Date sqlDateToDate(final java.sql.Date sqlDate) {
        return sqlDate == null ? null : new java.util.Date(sqlDate.getTime());
    }
    
    public static java.sql.Date dateToSqlDate(final java.util.Date date) {
        return date == null ? null : new java.sql.Date(date.getTime());
    }
    
    public static Optional<String> buildDate(Date myDate) {
    	Optional<String> date = Optional.empty();
        try {
        	date = getMyDate(myDate.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
        return date;
    }

    private static Optional<String> getMyDate(String myDate) {
    	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    	Date date = null;
    	Optional<String> returnValue = Optional.of("");
    	try {
    		date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH).parse(myDate);
    		returnValue = Optional.of(dateFormat.format(date));
    	} catch (ParseException e) {
    		returnValue = Optional.of(myDate);
    	}
    	return returnValue;
    }

}