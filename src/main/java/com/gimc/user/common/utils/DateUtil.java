package com.gimc.user.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.time.FastDateFormat;

/**
 * 
 * @Description: 日期处理类
 * @author ShineU
 * @date 2015年10月12日 下午5:51:38
 * @version 1.0
 */
public class DateUtil {
    
    public static String _yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    
    public static String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    
    public static String _yyyyMMddHHmm = "yyyy-MM-dd HH:mm";
    public static String _MMddHHmm = "MM月dd日 HH:mm";
    public static String HHmm = "HH:mm";
    public static String yyyyMMddHHmm = "yyyyMMddHHmm";
    
    public static String _yyyyMMdd = "yyyy-MM-dd";
    public static String yyyyMMdd = "yyyyMMdd";
    
    public static String yyyy = "yyyy";
    public static String yyyyMM = "yyyyMM";
    
    private static FastDateFormat _yyyyMMddHHmmssDateFormate = FastDateFormat.getInstance(_yyyyMMddHHmmss);
    
    private static FastDateFormat yyyyMMddHHmmssDateFormate = FastDateFormat.getInstance(yyyyMMddHHmmss);
    
    private static FastDateFormat _yyyyMMddHHmmDateFormate = FastDateFormat.getInstance(_yyyyMMddHHmm);
    private static FastDateFormat _MMddHHmmFormate = FastDateFormat.getInstance(_MMddHHmm);

    private static FastDateFormat yyyyMMddHHmmDateFormate = FastDateFormat.getInstance(yyyyMMddHHmm);
    
    private static FastDateFormat _yyyyMMddDateFormate = FastDateFormat.getInstance(_yyyyMMdd);
    
    private static FastDateFormat yyyyMMddDateFormate = FastDateFormat.getInstance(yyyyMMdd);
    private static FastDateFormat yyyyDateFormate = FastDateFormat.getInstance(yyyy);
    private static FastDateFormat yyyyMMDateFormate = FastDateFormat.getInstance(yyyy);
    private static FastDateFormat HHmmFormate = FastDateFormat.getInstance(HHmm);

    /**
     * 
     * @Description: 日期转字符串
     * @param date
     * @param type
     * @return String
     */
    public static String formateDate2Str(Date date, String type) {
        String str = "";
        if (_yyyyMMddHHmmss.equals(type)) {
            str = _yyyyMMddHHmmssDateFormate.format(date);
        } else if (yyyyMMddHHmmss.equals(type)) {
            str = yyyyMMddHHmmssDateFormate.format(date);
        } else if (_yyyyMMddHHmm.equals(type)) {
            str = _yyyyMMddHHmmDateFormate.format(date);
        } else if (yyyyMMddHHmm.equals(type)) {
            str = yyyyMMddHHmmDateFormate.format(date);
        } else if (_yyyyMMdd.equals(type)) {
            str = _yyyyMMddDateFormate.format(date);
        } else if (yyyyMMdd.equals(type)) {
            str = yyyyMMddDateFormate.format(date);
        } else if (yyyyMM.equals(type)) {
            str = yyyyMMDateFormate.format(date);
        } else if (yyyy.equals(type)) {
            str = yyyyDateFormate.format(date);
        } else if (HHmm.equals(type)) {
            str = HHmmFormate.format(date);
        }else if (_MMddHHmm.equals(type)) {
            str = _MMddHHmmFormate.format(date);
        }
        return str;
    }
    
    /**
     * 
     * @Description: 去除时间标识
     * @param timeStr
     * @return String
     */
    public static String replaceTimeChar(String timeStr) {
        timeStr = timeStr.replace("-", "");
        timeStr = timeStr.replace(":", "");
        timeStr = timeStr.replace(" ", "");
        return timeStr;
    }
    
    public static Date getCurrentTime() {
        return new Date();
    }
    
    public static String currtimeToString12() {
		return formateDate2Str(new Date(), yyyyMMddHHmmss);
	}
    
    public static String currtimeToString_12() {
		return formateDate2Str(new Date(), _yyyyMMddHHmmss);
	}
    
    /**
     * 
     * @Description:字符串 转日期
     * @param str
     * @param type
     * @return Date
     */
    public static Date formateStr2Date(String str, String type) {
        Date date = null;
        try {
            if (_yyyyMMddHHmmss.equals(type)) {
                date = _yyyyMMddHHmmssDateFormate.parse(str);
            } else if (_yyyyMMddHHmm.equals(type)) {
                date = _yyyyMMddHHmmDateFormate.parse(str);
            } else if (_yyyyMMdd.equals(type)) {
                date = _yyyyMMddDateFormate.parse(str);
            }  else if (yyyyMM.equals(type)) {
                date = yyyyMMDateFormate.parse(str);
            }   else if (yyyyMMdd.equals(type)) {
                date = yyyyMMddDateFormate.parse(str);
            }  else if (yyyy.equals(type)) {
                date = yyyyDateFormate.parse(str);
            } else {
                date = _yyyyMMddDateFormate.parse(str);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    
    /**
     * 
     * @Description: 获取日期字符串的数字
     * @param dateStr
     * @return long
     */
    public static long getDateStrNumber(String dateStr) {
        dateStr = dateStr.replaceAll("-", "");
        dateStr = dateStr.replaceAll(" ", "");
        dateStr = dateStr.replaceAll(":", "");
        return Long.parseLong(dateStr);
    }
    
    /**
     * 
     * @Description: 获取当前日期
     * @return Date
     */
    public static Date getCurrentDate() {
        return new Date();
    }
    public static String currDateToString() {
		return formateDate2Str(new Date(), _yyyyMMdd);
	}
    
    
    public static final String[] zodiacArr = {"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};
    
    public static final String[] constellationArr = {"水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座",
        "天蝎座", "射手座", "魔羯座"};
    
    public static final String[] constellationSymbol = {" ♒ ", " ♓", "♈", "♉", " ♊", " ♋ ", " ♌ ", " ♍ ", " ♎ ", " ♏ ",
        " ♐ ", " ♑ "};
    
    public static final int[] constellationEdgeDay = {20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22};
    
    /**
     * 
     * @Description: 根据日期获取生肖
     * @param date
     * @return String
     */
    public static String getZodica(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return zodiacArr[cal.get(Calendar.YEAR) % 12];
    }
    
    /**
     * 
     * @Description: 根据日期获取星座
     * @param date
     * @return String
     */
    public static String getConstellation(Date date) {
        if (date == null) {
            return "";
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day < constellationEdgeDay[month]) {
            month = month - 1;
        }
        if (month >= 0) {
            return constellationArr[month];
        }
        // default to return 魔羯
        return constellationArr[11];
    }
    
    /**
     * 
     * @Description: 根据日期获取星座的字符象征符
     * @param date
     * @return String
     */
    public static String getConstellationSymbol(Date date) {
        if (date == null) {
            return "";
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day < constellationEdgeDay[month]) {
            month = month - 1;
        }
        if (month >= 0) {
            return constellationSymbol[month];
        }
        return constellationSymbol[11];
    }
    
    /**
     * 
     * @Description: 两个日期相差月份
     * @param date1
     * @param date2
     * @throws ParseException
     * @return int
     */
    public static int getMonthSpace(String date1, String date2) throws ParseException {
        int result = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(sdf.parse(date1));
        c2.setTime(sdf.parse(date2));
        result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        return result == 0 ? 1 : Math.abs(result);
    }
    
    public static String getNaturalAgeDes(Calendar calendarBirth, Calendar calendarNow) {
        String str = "";
        int[] as = getNaturalAge(calendarBirth, calendarNow);
        boolean isOverYear = false;
        boolean isOverMonth = false;
        if (as[0] > 0) {
            str = str + as[0] + "岁";
            isOverYear = true;
        }
        if (as[1] > 0) {
            str = str + as[1] + "个月";
            isOverMonth = true;
        }
        if (as[2] > 0) {
            if (!isOverYear && !isOverMonth) {
                str = "第" + as[2] + "天";
            } else {
                str = str + "零" + as[2] + "天";
            }
            
        }
        return str;
    }
    
    /**
     * @Description: 获取两个日期相差年 月 日
     * @param calendarBirth
     * @param calendarNow
     * @return int[] 年 月 日
     */
    public static int[] getNaturalAge(Calendar calendarBirth, Calendar calendarNow) {
        int diffYears = 0, diffMonths, diffDays;
        int dayOfBirth = calendarBirth.get(Calendar.DAY_OF_MONTH);
        int dayOfNow = calendarNow.get(Calendar.DAY_OF_MONTH);
        if (dayOfBirth <= dayOfNow) {
            diffMonths = getMonthsOfAge(calendarBirth, calendarNow);
            diffDays = dayOfNow - dayOfBirth;
            if (diffMonths == 0)
                diffDays++;
        } else {
            if (isEndOfMonth(calendarBirth)) {
                if (isEndOfMonth(calendarNow)) {
                    diffMonths = getMonthsOfAge(calendarBirth, calendarNow);
                    diffDays = 0;
                } else {
                    calendarNow.add(Calendar.MONTH, -1);
                    diffMonths = getMonthsOfAge(calendarBirth, calendarNow);
                    diffDays = dayOfNow + 1;
                }
            } else {
                if (isEndOfMonth(calendarNow)) {
                    diffMonths = getMonthsOfAge(calendarBirth, calendarNow);
                    diffDays = 0;
                } else {
                    calendarNow.add(Calendar.MONTH, -1);// 上个月
                    diffMonths = getMonthsOfAge(calendarBirth, calendarNow);
                    // 获取上个月最大的一天
                    int maxDayOfLastMonth = calendarNow.getActualMaximum(Calendar.DAY_OF_MONTH);
                    if (maxDayOfLastMonth > dayOfBirth) {
                        diffDays = maxDayOfLastMonth - dayOfBirth + dayOfNow;
                    } else {
                        diffDays = dayOfNow;
                    }
                }
            }
        }
        // 计算月份时，没有考虑年
        diffYears = diffMonths / 12;
        diffMonths = diffMonths % 12;
        return new int[] {diffYears, diffMonths, diffDays};
    }
    
    /**
     * 获取两个日历的月份之差
     * 
     * @param calendarBirth
     * @param calendarNow
     * @return
     */
    public static int getMonthsOfAge(Calendar calendarBirth, Calendar calendarNow) {
        return (calendarNow.get(Calendar.YEAR) - calendarBirth.get(Calendar.YEAR)) * 12
            + calendarNow.get(Calendar.MONTH) - calendarBirth.get(Calendar.MONTH);
    }
    
    /**
     * 判断这一天是否是月底
     * 
     * @param calendar
     * @return
     */
    public static boolean isEndOfMonth(Calendar calendar) {
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        if (dayOfMonth == calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
            return true;
        return false;
    }
    
    
 // 获得本周一与当前日期相差的天数  
    public static  int getMondayPlus() {  
        Calendar cd = Calendar.getInstance();  
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);  
        if (dayOfWeek == 1) {  
            return -6;  
        } else {  
            return 2 - dayOfWeek;  
        }  
    } 
    
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
 // 获得当前周- 周一的日期  
    public static  String getCurrentMonday() {  
        int mondayPlus = getMondayPlus();  
        GregorianCalendar currentDate = new GregorianCalendar();  
        currentDate.add(GregorianCalendar.DATE, mondayPlus);  
        Date monday = currentDate.getTime();  
        String preMonday = dateFormat.format(monday);  
        return preMonday;  
    }  
  
    // 获得当前周- 周日  的日期  
    public static String getPreviousSunday() {  
        int mondayPlus = getMondayPlus();  
        GregorianCalendar currentDate = new GregorianCalendar();  
        currentDate.add(GregorianCalendar.DATE, mondayPlus +6);  
        Date monday = currentDate.getTime();  
        String preMonday = dateFormat.format(monday);  
        return preMonday;  
    }  
    // 获得当前月--开始日期  
    public static String getMinMonthDate(String date) {  
        Calendar calendar = Calendar.getInstance();  
        try {  
            calendar.setTime(dateFormat.parse(date));  
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));  
            return dateFormat.format(calendar.getTime());  
        } catch (java.text.ParseException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    // 获得当前月--结束日期  
    public static String getMaxMonthDate(String date){  
        Calendar calendar = Calendar.getInstance();  
        try {  
            calendar.setTime(dateFormat.parse(date));  
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
            return dateFormat.format(calendar.getTime());  
        }  catch (java.text.ParseException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }


    /**
     * 日期转星期
     *
     * @param datetime
     * @return
     */
    public static String dateToWeek(Date datetime) {
       // SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历

        cal.setTime(datetime);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    public static void main(String[] args) throws ParseException {
    	String a = getCurrentMonday();
    	String b = getPreviousSunday();
    	String c = getMinMonthDate(currDateToString());
    	String d = getMaxMonthDate(currDateToString());
    	System.out.println(a);
    	System.out.println(b);
    	System.out.println(c);
    	System.out.println(d);
    	System.out.println( dateToWeek(new Date()));
    	System.out.println( formateDate2Str(new Date(),DateUtil._MMddHHmm));

    }
}
