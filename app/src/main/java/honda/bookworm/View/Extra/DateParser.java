package honda.bookworm.View.Extra;

import android.text.format.DateUtils;

public class DateParser {

    private static final int MILLISECONDS_IN_MINUTE = 60000;

    public static String parseDateString(long dateMillis){
        long now = System.currentTimeMillis();
        String message = "";

        if(now - dateMillis < MILLISECONDS_IN_MINUTE) {
            message = "Less then a minute ago";
        }
        else{
            message = DateUtils.getRelativeTimeSpanString(dateMillis, now, DateUtils.MINUTE_IN_MILLIS).toString();
        }

        return message;
    }
}
