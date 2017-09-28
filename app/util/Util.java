package util;

import java.sql.Timestamp;
import java.util.Calendar;

public class Util {

    public static Timestamp subtractMinutesCurrTime(int minutes)
    {
        final long ONE_MINUTE_IN_MILLIS=60000;
        Calendar date = Calendar.getInstance();
        long t= date.getTimeInMillis();
        return new Timestamp(t - (minutes * ONE_MINUTE_IN_MILLIS));
    }
}
