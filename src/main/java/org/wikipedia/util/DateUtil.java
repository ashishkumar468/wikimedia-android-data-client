package org.wikipedia.util;

import android.content.Context;
import android.support.annotation.NonNull;

import org.wikipedia.feed.model.UtcDate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public final class DateUtil {

    public static SimpleDateFormat getIso8601DateFormatShort() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat;
    }

    public static SimpleDateFormat getIso8601DateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ROOT);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat;
    }

    public static SimpleDateFormat getIso8601LocalDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ROOT);
    }

    public static String getMonthOnlyDateString(@NonNull Date date) {
        return getDateStringWithSkeletonPattern(date, "MMMM d");
    }

    public static String getMonthOnlyWithoutDayDateString(@NonNull Date date) {
        return getDateStringWithSkeletonPattern(date, "MMMM");
    }

    public static String getExtraShortDateString(@NonNull Date date) {
        return getDateStringWithSkeletonPattern(date, "MMM d");
    }

    public static String getDateStringWithSkeletonPattern(@NonNull Date date, @NonNull String pattern) {
        return new SimpleDateFormat(android.text.format.DateFormat.getBestDateTimePattern(Locale.getDefault(), pattern), Locale.getDefault()).format(date);
    }

    public static String getShortDateString(@NonNull Context context, @NonNull Date date) {
        // todo: consider allowing TWN date formats. It would be useful to have but might be
        //       difficult for translators to write correct format specifiers without being able to
        //       test them. We should investigate localization support in date libraries such as
        //       Joda-Time and how TWN solves this classic problem.
        DateFormat dateFormat = android.text.format.DateFormat.getMediumDateFormat(context);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }

    public static UtcDate getUtcRequestDateFor(int age) {
        return new UtcDate(age);
    }

    public static Calendar getDefaultDateFor(int age) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.add(Calendar.DATE, -age);
        return calendar;
    }

    public static Date getHttpLastModifiedDate(@NonNull String dateStr) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.parse(dateStr);
    }

    public static String getReadingListsLastSyncDateString(@NonNull String dateStr) throws ParseException {
        return getDateStringWithSkeletonPattern(getIso8601DateFormat().parse(dateStr), "d MMM yyyy HH:mm");
    }

    @NonNull public static String yearToStringWithEra(int year) {
        Calendar cal = new GregorianCalendar(year, 1, 1);
        return getDateStringWithSkeletonPattern(cal.getTime(), year < 0 ? "y GG" : "y");
    }

    private DateUtil() {
    }
}
