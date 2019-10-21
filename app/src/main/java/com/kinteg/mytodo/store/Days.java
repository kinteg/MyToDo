package com.kinteg.mytodo.store;

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


    private Date today() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return dateFormat.parse(dateFormat.format(new Date()));
    }

    public Date getStartTime() throws ParseException {
        return new Date(today().getTime() + start);
    }

    public Date getEndTime() throws ParseException {
        return new Date(today().getTime() + end);
    }
}
