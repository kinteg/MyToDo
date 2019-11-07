package com.kinteg.mytodo.store;

import android.provider.ContactsContract;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public enum Days {

    TODAY(0, 8640000),
    TOMORROW(86400000, 86400000 * 2),
    UPCOMING(86400000 * 2 + 1, 0),
    WITHOUT_DATE(0, 0),
    PAST(-86400000, 0);

    private long start, end;

    Days(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }


    public Date today()  {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            return dateFormat.parse(dateFormat.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Date getStartTime()  {
        return new Date(today().getTime() + start);
    }

    public Date getEndTime()  {
        return new Date(today().getTime() + end);
    }

}
