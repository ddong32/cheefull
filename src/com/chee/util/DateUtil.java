package com.chee.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.regex.Pattern;

public class DateUtil {
    public static final String DEFAULT_ZONE_ID = "Asia/Hong_Kong";
    public static final String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE_10 = "yyyy-MM-dd";
    public static final String FORMAT_TIME_10 = "HH:mm:ss";
    public static final String REGEX_FORMAT_DATE_10 = "\\d{4}-\\d{2}-\\d{2}";
    public static final String REGEX_FORMAT_DATE_TIME_19 = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
    public static final String FORMAT_DATE_TIME_14 = "yyyyMMddHHmmss";
    private static final SimpleDateFormat sfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private static ConcurrentSkipListSet<String> sdf_set = new ConcurrentSkipListSet();
    private static HashMap<String, Pattern> patn_map = new HashMap();

    public static Date formatDate(String strDate) {
        try {
            if ((strDate == null) || (strDate.length() < 1)) {
                return null;
            }
            SimpleDateFormat sfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sfDate.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Timestamp formatString(String strDate) {
        try {
            if ((strDate == null) || (strDate.length() < 1)) {
                return null;
            }
            Date date = sfDate.parse(strDate);
            return new Timestamp(date.getTime());
        } catch (Exception localException) {
        }
        return null;
    }

    public static String DateToString(Date date, String dateFormat) {
        String result = "";
        if (date != null) {
            SimpleDateFormat sdf;
            if ((dateFormat != null) && (!"".equals(dateFormat))) {
                try {
                    sdf = new SimpleDateFormat(dateFormat);
                } catch (Exception e) {
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                }
            } else {
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }
            result = sdf.format(date);
        }
        return result;
    }

    public static Date parse(Object obj) {
        try {
            if (obj == null) {
                return null;
            }
            String dateString = obj.toString().trim();
            if (dateString.length() == 0) {
                return null;
            }
            if (dateString.length() == 10) {
                dateString = dateString + " 00:00:00";
            }
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return df.parse(dateString);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static final Timestamp nowTime(String zoneId) {
        TimeZone.setDefault(TimeZone.getTimeZone(zoneId));
        long l = System.currentTimeMillis();
        return new Timestamp(l);
    }

    public static final Timestamp nowTimestamp() {
        return nowTime("Asia/Hong_Kong");
    }

    public static final String formatDate(Date date, String dateFormat, String zoneId) {
        SimpleDateFormat sdf = null;
        TimeZone zone = TimeZone.getTimeZone(zoneId);
        if (dateFormat != null) {
            sdf = new SimpleDateFormat(dateFormat);
        } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        if (zone != null) {
            sdf.setTimeZone(zone);
        }
        return sdf.format(date);
    }

    public static final String formatDate(Date date, String dateFormat) {
        String result = "";
        if (date != null) {
            SimpleDateFormat sdf = null;
            if (dateFormat != null) {
                sdf = new SimpleDateFormat(dateFormat);
            } else {
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }
            result = sdf.format(date);
        }
        return result;
    }

    public static final String timestampToString(Timestamp ts, String dateFormat) {
        if (ts == null) {
            return "";
        }
        return formatDate(ts, dateFormat, "Asia/Hong_Kong");
    }

    public static String getDatefolder() {
        String strDate = "";
        strDate = dateFormat.format(new Date());
        return strDate;
    }

//    public static String getYear() {
//        String strYear = "";
//        Calendar calendar = Calendar.getInstance();
//        strYear = calendar.get(1);
//        return strYear;
//    }

//    public static String getMonth() {
//        String strMonth = "";
//        Calendar calendar = Calendar.getInstance();
//        strMonth = calendar.get(2) + 1;
//        strMonth = 1 == strMonth.length() ? "0" + strMonth : strMonth;
//        return strMonth;
//    }
//
//    public static Timestamp constructDate(String strDate, String HHmmss) {
//        try {
//            if ((strDate == null) || (strDate.length() < 1)) {
//                return null;
//            }
//            try {
//                String dateTemp = strDate + " " + HHmmss;
//                Date date = sfDate.parse(dateTemp);
//                return new Timestamp(date.getTime());
//            } catch (ParseException ex) {
//                return null;
//            }
//        } catch (Exception localException) {
//            return null;
//        }
//    }

    public static String getMonday(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(7, 2);
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    public static String getFriday(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(7, 6);
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    public static String afterNDay(Date nowDate, int n, String dateFormat) {
        Calendar c = Calendar.getInstance();
        DateFormat df = null;
        if (dateFormat != null) {
            df = new SimpleDateFormat(dateFormat);
        } else {
            df = new SimpleDateFormat("yyyy-MM-dd");
        }
        c.setTime(nowDate);
        c.add(5, n);
        Date d2 = c.getTime();
        String s = df.format(d2);
        return s;
    }

    public static String getSeqWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String week = Integer.toString(c.get(3));
        if (week.length() == 1) {
            week = "0" + week;
        }
        String year = Integer.toString(c.get(1));
        return year + "-" + week;
    }

    public static boolean isSameDate(Date date, Date otherDate) {
        String strDateOne = DateToString(date, null);
        String strOtherDate = DateToString(otherDate, null);
        boolean isSame;
        if (strDateOne.equals(strOtherDate)) {
            isSame = true;
        } else {
            isSame = false;
        }
        return isSame;
    }

    public static int betweenDays(String beginDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = new Date();
        Date d2 = new Date();
        long between = 0L;
        int betweenDays = 0;
        try {
            d1 = sdf.parse(beginDate);
            d2 = sdf.parse(endDate);
            if (d1.getTime() > d2.getTime()) {
                between = d1.getTime() - d2.getTime();
            } else {
                between = d2.getTime() - d1.getTime();
            }
            betweenDays = (int) (between / 86400000L);
            if (d1.getTime() > d2.getTime()) {
                betweenDays = 0 - betweenDays;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return betweenDays;
    }

    public static List<String> betweenDayList(String beginTime, String endTime) {
        List betweenDayList = new ArrayList();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        try {
            start.setTime(format.parse(beginTime));
            end.setTime(format.parse(endTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
        while ((start.before(end)) || (start.equals(end))) {
            betweenDayList.add(format.format(start.getTime()));
            start.add(5, 1);
        }
        return betweenDayList;
    }

    public static long getSSbetweenNowAndDate(Date date) {
        long oldTime = 0L;
        long nowTime = 0L;
        try {
            if (date != null) {
                oldTime = sfDate.parse(formatDate(date, "yyyy-MM-dd HH:mm:ss")).getTime();
                nowTime = sfDate.parse(formatDate(new Date(), "yyyy-MM-dd HH:mm:ss")).getTime();
            }
            if (nowTime > oldTime) {
                return nowTime - oldTime;
            }
        } catch (Exception localException) {
        }
        return 0L;
    }

    public static String getCurrDatetimeStr() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static Date string2Date(String str, String formatStr) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        if (!sdf_set.contains(formatStr)) {
            return null;
        }
        try {
            return new SimpleDateFormat(formatStr).parse(str);
        } catch (ParseException localParseException) {
        }
        return null;
    }

    static {
        sdf_set.add("yyyy-MM-dd");
        sdf_set.add("HH:mm:ss");
        sdf_set.add("yyyyMMddHHmmss");
        sdf_set.add("yyyy-MM-dd HH:mm:ss");

        patn_map.put("yyyy-MM-dd", Pattern.compile("\\d{4}-\\d{2}-\\d{2}"));
        patn_map.put("yyyy-MM-dd HH:mm:ss", Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"));
    }

    public class ManageWeek {
        public ManageWeek() {
        }

        boolean isSameWeekDates(Date date1, Date date2) {
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(date1);
            cal2.setTime(date2);
            int subYear = cal1.get(1) - cal2.get(1);
            if (subYear == 0) {
                if (cal1.get(3) == cal2.get(3)) {
                    return true;
                }
            } else if ((1 == subYear) && (11 == cal2.get(2))) {
                if (cal1.get(3) == cal2.get(3)) {
                    return true;
                }
            } else if ((-1 == subYear) && (11 == cal1.get(2)) && (cal1.get(3) == cal2.get(3))) {
                return true;
            }
            return false;
        }
    }
}
