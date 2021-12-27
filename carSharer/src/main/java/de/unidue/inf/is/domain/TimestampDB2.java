package de.unidue.inf.is.domain;

import java.sql.Timestamp;

public class TimestampDB2 extends Timestamp {

    public TimestampDB2(long time) {
        super(time);
    }

    @Override
    public String toString() {

        String originalTimeStampFormat = super.toString();

        String appropriateString;

        String[] stringSplitAtSpace = originalTimeStampFormat.split(" ");

        String yearMonthDay = stringSplitAtSpace[0];

        String hourMinuteSecond = stringSplitAtSpace[1];

        hourMinuteSecond = hourMinuteSecond.replace(':', '.');

        return yearMonthDay + "-" + hourMinuteSecond;

    }
}
