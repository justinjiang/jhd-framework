package com.jhd.utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
	
	public static final String DF_YMDHMS = "yyyy-MM-dd HH:mm:ss";
	public static final String DF_YMDHMS_ = "yyyy/MM/dd HH:mm:ss";
	public static final String DF_YMDHM = "yyyy-MM-dd HH:mm";
	public static final String DF_YMDHM_ = "yyyy/MM/dd HH:mm";
	public static final String DF_YMD = "yyyy-MM-dd";
	public static final String DF_HMS = "HH:mm:ss";	
	public static final String DF_MD_STRING = "MM月dd号";
	public static final String DF_YMD_STRING="yyyy年MM月dd日";
	public static final String DF_YMDHMS_STRING="yyyyMMddHHmmss";
	public static final String DF_YMD_SLASH="yyyy/MM/dd";

	/**
	 * 日期转化为字符串格式
	 *
	 * @param date
	 * @param format
     * @return
     */
	public static String dateToString(Date date, String format) {
		if (date == null){
			return "";
		}
		synchronized (date) {
			SimpleDateFormat df = new SimpleDateFormat(format);
			return df.format(date);
		}
	}

	/**
	 * 日期格式转化为字符串格式(默认格式)
	 * @param date
	 * @return
     */
	public static String dateToString(Date date) {
		return dateToString(date, DF_YMD);
	}
	
	/**
	 * 字符串格式转化为日期格式(通用格式)
	 * @param string
	 * @param format
     * @return
     */
	public static Date stringToDate(String string, String format) {
		Date dd = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		try {
			dd = simpleDateFormat.parse(string.trim());
		} catch (Exception e) {
			dd = null;
		}
		return dd;
	}
	/**
	 * 由yyyy-MM-dd格式的字符串返回日期
	 *
	 * @param string
	 * @return
	 */
	public static Date stringToDate(String string) {
		return stringToDate(string, DF_YMD);
	}
	public static Date stringToDateTime(String string) {
		if(string.trim().length()==10){
			string=string + " 00:00:00";
		}
		return stringToDate(string, DF_YMDHMS);
	}
	
	// (计算两个日期相隔的分钟数)
	public static int nMinsBetweenTwoDate(String firstString,
			String secondString) {
		SimpleDateFormat df = new SimpleDateFormat(DF_YMDHMS);
		Date firstDate = null;
		Date secondDate = null;
		try {
			firstDate = df.parse(firstString);
			secondDate = df.parse(secondString);
		} catch (Exception e) {
			// (日期型字符串格式错误)
		}
		int nMin = (int) ((secondDate.getTime() - firstDate.getTime()) / (60 * 1000));
		return nMin;
	}

	public static int getYear() {
		Calendar calendar = new GregorianCalendar();
		Date trialTime = new Date();
		calendar.setTime(trialTime);
		return calendar.get(Calendar.YEAR);
	}
	//得到日期中的年份
	public static int getYear(Date date){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}
	
	public static int getMonth() {
		Calendar calendar = new GregorianCalendar();
		Date trialTime = new Date();
		calendar.setTime(trialTime);
		return calendar.get(Calendar.MONTH) + 1;
	}
	
	public static int getMonth(Date date){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	public static int getDay() {
		Calendar calendar = new GregorianCalendar();
		Date trialTime = new Date();
		calendar.setTime(trialTime);
		return calendar.get(Calendar.DATE);
	}

	public static int getHour() {
		Calendar calendar = new GregorianCalendar();
		Date trialtime = new Date();
		calendar.setTime(trialtime);
		return calendar.get(Calendar.HOUR);
	}
	public static int getHour24() {
		Calendar calendar = new GregorianCalendar();
		Date trialtime = new Date();
		calendar.setTime(trialtime);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	public static int getMinute() {
		Calendar calendar = new GregorianCalendar();
		Date trialtime = new Date();
		calendar.setTime(trialtime);
		return calendar.get(Calendar.MINUTE);
	}
	
	public static String getTime() {
		return dateToString(now(), DF_HMS);
	}
	public static String getTime(Date trialTime) {
		return dateToString(trialTime, DF_HMS);
	}

	public static String getCurrentDate_String() {
		return dateToString(now(), DF_YMD);
	}

	public static String getCurrentDateTime() {
		return dateToString(now(), DF_YMDHMS);
	}

	public static Date now(){
		return new Date();
	}

	public static String today(){
		return dateToString(now(), DF_YMD);
	}
	
	/**
	 * 通过时间判断是上午、中午、下午或者晚上
	 * @return
	 */
	public static String checkTimeBucket(String time){
		if(time == null || time.equals("")){
			return "";
		}
		int date = new Integer(time.split(":")[0]);
		String result = "";
		if(date<11)
			result = "上午";
		else if(date>=11 && date<=14)
			result = "中午";
		else if(date>14 && date<18)
			result = "下午";
		else
			result = "晚上";
			
		return result;
	}

	//（格式化日期字符串 如：6月25日）
	public static String formatDateyearMonth(String date){
		Date thisDate = stringToDate(date,DF_YMD);
		Calendar c = Calendar.getInstance();
		c.setTime(thisDate);
		return (c.get(Calendar.MONTH) +1)+"月"+c.get(Calendar.DAY_OF_MONTH)+"日";
	}

	// (检验字符串 "yyyy-MM-dd")
	public static boolean checkDateTime(String basicDate) {
		if ((basicDate == null) || (basicDate.length() != 10)) {
			return false;
		}
		SimpleDateFormat df = new SimpleDateFormat(DF_YMD);
		Date tmpDate = null;
		try {
			tmpDate = df.parse(basicDate);
		} catch (Exception e) {
			return false;
			// (日期型字符串格式错误)
		}
		return true;
	}

	// (当前日期加减n天后的日期，返回String (yyyy-mm-dd))
	public static String nDaysAfter(int n) {
		SimpleDateFormat df = new SimpleDateFormat(DF_YMD);
		Calendar rightNow = Calendar.getInstance();
		// rightNow.add(Calendar.DAY_OF_MONTH,-1);
		rightNow.add(Calendar.DAY_OF_MONTH, +n);
		return df.format(rightNow.getTime());
	}

	// (当前日期加减n天后的日期，返回Date)
	public static Date nDaysAfterNowDate(int n) {
		Calendar rightNow = Calendar.getInstance();
		// rightNow.add(Calendar.DAY_OF_MONTH,-1);
		rightNow.add(Calendar.DAY_OF_MONTH, +n);
		return rightNow.getTime();
	}
	
	public static Date nDaysAfterDate(Date basicDate, int n) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(basicDate);
		rightNow.add(Calendar.DAY_OF_MONTH, +n);
		return rightNow.getTime();
	}

	// (给定一个日期型字符串，返回加减n天后的日期型字符串)
	public static String nDaysAfterOneDateString(String basicDate, int n) {
		SimpleDateFormat df = new SimpleDateFormat(DF_YMD);
		Date tmpDate = null;
		try {
			tmpDate = df.parse(basicDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		long nDay = (tmpDate.getTime() / (24 * 60 * 60 * 1000) + 1 + n)
				* (24 * 60 * 60 * 1000);
		tmpDate.setTime(nDay);

		return df.format(tmpDate);
	}
	// (给定一个日期型字符串，返回加减n天后的日期型字符串)
	public static String nDaysAfterBeforeNow(String basicDate, int n) {
		SimpleDateFormat df = new SimpleDateFormat(DF_YMD);
		Date tmpDate = null;
		try {
			Calendar c = Calendar.getInstance();
			tmpDate = df.parse(basicDate);
			c.setTime(tmpDate);
			c.add(Calendar.DAY_OF_YEAR, n);
			Date nowDate = c.getTime();
			if(new Date().after(nowDate)){
				return df.format(new Date());
			}else{
				return df.format(nowDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ( 给定一个日期，返回加减n天后的日期，返回Date)
	public static Date nDaysAfterOneDate(Date basicDate, int n) {
		long nDay = (basicDate.getTime() / (24 * 60 * 60 * 1000) + 1 + n)
				* (24 * 60 * 60 * 1000);
		basicDate.setTime(nDay);

		return basicDate;
	}

	// (当前日期加减n个月后的日期，返回String (yyyy-mm-dd))
	public static String nMonthsAftertoday(int n) {
		SimpleDateFormat df = new SimpleDateFormat(DF_YMD);
		Calendar rightNow = Calendar.getInstance();
		// rightNow.add(Calendar.DAY_OF_MONTH,-1);
		rightNow.add(Calendar.MONTH, +n);
		return df.format(rightNow.getTime());
	}
	public static Date nMinutesAfterNowDate(int n) {
		Calendar rightNow = Calendar.getInstance();
		// rightNow.add(Calendar.DAY_OF_MONTH,-1);
		rightNow.add(Calendar.MINUTE, +n);
		return rightNow.getTime();
	}
	public static Date nMinutesAfterDate(Date now, int n) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(now);
		// rightNow.add(Calendar.DAY_OF_MONTH,-1);
		rightNow.add(Calendar.MINUTE, +n);
		return rightNow.getTime();
	}
	// (当前日期加减n个月后的日期，返回Date)
	public static Date nMonthsAfterNowDate(int n) {
		Calendar rightNow = Calendar.getInstance();
		// rightNow.add(Calendar.DAY_OF_MONTH,-1);
		rightNow.add(Calendar.MONTH, +n);
		return rightNow.getTime();
	}

	// (当前日期加减n个月后的日期，返回Date)
	public static Date nMonthsAfterOneDate(Date basicDate, int n) {
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(basicDate);
		rightNow.add(Calendar.MONTH, +n);
		return rightNow.getTime();
	}

	// (当前日期加减n个月后的日期，返回String)
	public static String nMonthsAfterOneDateString(Date basicDate, int n) {
		SimpleDateFormat df = new SimpleDateFormat(DF_YMD);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(basicDate);
		rightNow.add(Calendar.MONTH, +n);
		return df.format(rightNow.getTime());
	}
	//当前时间加n小时后返回Date
	public static Date nHoursAfterOneDate(Date nowDate,int n){
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(nowDate);
		rightNow.add(Calendar.HOUR, +n);
		return rightNow.getTime();
	}

	// (计算两个日期相隔的天数)
	public static int nDaysBetweenTwoDate(Date firstDate, Date secondDate) {
		int nDay = (int) ((secondDate.getTime() - firstDate.getTime()) / (24 * 60 * 60 * 1000));
		return nDay;
	}

	// (计算两个日期相隔的天数)
	public static int nDaysBetweenTwoDate(String firstString,
			String secondString) {
		SimpleDateFormat df = new SimpleDateFormat(DF_YMD);
		Date firstDate = null;
		Date secondDate = null;
		try {
			firstDate = df.parse(firstString);
			secondDate = df.parse(secondString);
		} catch (Exception e) {
			// (日期型字符串格式错误)
		}
		int nDay = (int) ((secondDate.getTime() - firstDate.getTime()) / (24 * 60 * 60 * 1000));
		return nDay;
	}


	public static int nMinsBetweenTwoTime(String firstString,
			Date secondDate){
		
		SimpleDateFormat df = new SimpleDateFormat(DF_YMDHM);
		Date firstDate = null;
		try {
			firstDate = df.parse(firstString);
		} catch (Exception e) {
			// (日期型字符串格式错误)
		}
		int nMin = (int) ((firstDate.getTime() - secondDate.getTime()) / (60 * 1000));
		return nMin;
	}
	
	public static int nMinsBetweenTwoDate(Date startDate, Date endDate) {
		return (int) ((endDate.getTime() - startDate.getTime()) / (60*1000));
	}

	public static int nSecsBetweenTwoDate(Date startDate, Date endDate) {
		return (int) ((endDate.getTime() - startDate.getTime()) / (1000));
	}

	/**
	 * 当前时间某个范围的随机时间(分钟） 
	 * @param startDate
	 * @param mins
	 * @return
	 */
	public static Date nMinsBeforeDateRandam(Date startDate,long mins){
		long n=startDate.getTime();
		long m=mins*60*1000;
		m=Math.round(m*(1-Math.random()*Math.random())*0.2+m*Math.random()*0.6+m*Math.random()*Math.random()*0.2);
		n=n-m;
		return new Date(n);
	}
	
	/**
	 * 获取当前日期
	 * 
	 * @return
	 */
	public static Date getCurrentDate_Date() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static Time getCurrentTime() {
		return new Time(System.currentTimeMillis());
	}

	/**
	 * 获取当前时间戳
	 * 
	 * @return
	 */
	public static Timestamp getTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}



	/**
	 * 返回yyyy-MM-dd hh24:mm:ss格式的字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString_full(Date date) {
		if (date == null)
			return "";

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				DF_YMDHMS);
		return simpleDateFormat.format(date);
	}

	
	/**
	 * 返回yyyy-MM-dd hh24:mm:ss格式的字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString_HM(Date date) {
		if (date == null)
			return "";

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm");
		return simpleDateFormat.format(date);
	}
	


	public static java.sql.Date DateToSQLDate(Date pDate) {
		java.sql.Date dd = new  java.sql.Date(pDate.getTime());
//		SimpleDateFormat   sdf   =   new   SimpleDateFormat(DF_YMDHMS);   
//		  System.out.println(sdf.format(dd));  
		return dd;
	}


	/*
     * 
     * 
     */
	public static Date getDateAfter2Hour(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, -2);
		return cal.getTime();
	}
	public static Date getDateAfterHour(Date date,int nHour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, nHour);
		return cal.getTime();
	}
	public static Date getDateAfterSecond(Date date,int nSecond) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, nSecond);
		return cal.getTime();
	}
	/**
	 * 计算两个日期相隔的天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getDaysBetween(Date startDate, Date endDate) {
		Calendar calendarStartDate = Calendar.getInstance();
		Calendar calendarEndDate = Calendar.getInstance();

		// 设日历为相应日期
		calendarStartDate.setTime(startDate);
		calendarEndDate.setTime(endDate);
		if (startDate.after(endDate)) {
			Calendar swap = calendarStartDate;
			calendarStartDate = calendarEndDate;
			calendarEndDate = swap;
		}

		int days = calendarEndDate.get(Calendar.DAY_OF_YEAR)
				- calendarStartDate.get(Calendar.DAY_OF_YEAR);
		int y2 = calendarEndDate.get(Calendar.YEAR);
		while (calendarStartDate.get(Calendar.YEAR) < y2) {
			days += calendarStartDate.getActualMaximum(Calendar.DAY_OF_YEAR);
			calendarStartDate.add(Calendar.YEAR, 1);
		}

		return days;
	}

	/**
	 * 计算两个日期相隔年数(不比较月、日)
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getYearsBetween(Date startDate, Date endDate) {
		Calendar calendarStartDate = Calendar.getInstance();
		Calendar calendarEndDate = Calendar.getInstance();

		// 设日历为相应日期
		calendarStartDate.setTime(startDate);
		calendarEndDate.setTime(endDate);
		return calendarEndDate.get(Calendar.YEAR)
				- calendarStartDate.get(Calendar.YEAR);
	}

	/**
	 * 计算两个日期相隔的月数(不足整月的算一个月)
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getMonthsBetween(Date startDate, Date endDate) {
		Calendar calendarStartDate = Calendar.getInstance();
		Calendar calendarEndDate = Calendar.getInstance();

		// 设日历为相应日期
		calendarStartDate.setTime(startDate);
		calendarEndDate.setTime(endDate);
		if (startDate.after(endDate)) {
			Calendar swap = calendarStartDate;
			calendarStartDate = calendarEndDate;
			calendarEndDate = swap;
		}

		int months = calendarEndDate.get(Calendar.MONTH)
				- calendarStartDate.get(Calendar.MONTH)
				+ (calendarEndDate.get(Calendar.YEAR) - calendarStartDate
						.get(Calendar.YEAR)) * 12;

		if (getEndDateByMonths(startDate, months).compareTo(endDate) < 0)
			months += 1;

		return months;
	}

	/**
	 * 计算两个日期相隔的月数(不比较日)
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getActualMonths(Date startDate, Date endDate) {
		Calendar calendarStartDate = Calendar.getInstance();
		Calendar calendarEndDate = Calendar.getInstance();

		// 设日历为相应日期
		calendarStartDate.setTime(startDate);
		calendarEndDate.setTime(endDate);
		if (startDate.after(endDate)) {
			Calendar swap = calendarStartDate;
			calendarStartDate = calendarEndDate;
			calendarEndDate = swap;
		}

		int months = calendarEndDate.get(Calendar.MONTH)
				- calendarStartDate.get(Calendar.MONTH)
				+ (calendarEndDate.get(Calendar.YEAR) - calendarStartDate
						.get(Calendar.YEAR)) * 12;

		return months;
	}

	/**
	 * 根据起始日和相隔天数计算终止日
	 * 
	 * @param startDate
	 * @param days
	 * @return
	 */
	public static Date getEndDateByDays(Date startDate, int days) {
		Calendar calendarStartDate = Calendar.getInstance();
		calendarStartDate.setTime(startDate);
		calendarStartDate.add(Calendar.DAY_OF_YEAR, days);

		return calendarStartDate.getTime();
	}

	/**
	 * 根据起始日和相隔月数计算终止日
	 * 
	 * @param startDate
	 * @param months
	 * @return
	 */
	public static Date getEndDateByMonths(Date startDate, int months) {
		Calendar calendarStartDate = Calendar.getInstance();
		calendarStartDate.setTime(startDate);
		calendarStartDate.add(Calendar.MONTH, months);

		return calendarStartDate.getTime();
	}

	/**
	 * 根据起始日和期限计算终止日
	 * 
	 * @param startDate
	 * @param term
	 *            YYMMDD格式的贷款期限
	 * @return
	 */
	public static Date getEndDateByTerm(Date startDate, String term) {
		int years = Integer.parseInt(term.substring(0, 2));
		int months = Integer.parseInt(term.substring(2, 4));
		int days = Integer.parseInt(term.substring(4, 6));
		return getEndDateByDays(getEndDateByMonths(startDate, years * 12
				+ months), days);
	}

	/**
	 * 根据终止日和相隔天数计算起始日
	 * 
	 * @param endDate
	 * @param days
	 * @return
	 */
	public static Date getStartDateByDays(Date endDate, int days) {
		Calendar calendarEndDate = Calendar.getInstance();
		calendarEndDate.setTime(endDate);
		calendarEndDate.add(Calendar.DAY_OF_YEAR, 0 - days);

		return calendarEndDate.getTime();
	}

	/**
	 * 根据终止日和相隔月数计算起始日
	 * 
	 * @param endDate
	 * @param months
	 * @return
	 */
	public static Date getStartDateByMonths(Date endDate, int months) {
		Calendar calendarEndDate = Calendar.getInstance();
		calendarEndDate.setTime(endDate);
		calendarEndDate.add(Calendar.MONTH, 0 - months);

		return calendarEndDate.getTime();
	}

	/**
	 * 判断两个日期是否对日
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean isSameDate(Date startDate, Date endDate) {
		Calendar calendarStartDate = Calendar.getInstance();
		Calendar calendarEndDate = Calendar.getInstance();
		if(startDate==null && endDate==null) return true;
        if(startDate==null || endDate==null) return false;
		// 设日历为相应日期
		calendarStartDate.setTime(startDate);
		calendarEndDate.setTime(endDate);
		if (startDate.after(endDate)) {
			Calendar swap = calendarStartDate;
			calendarStartDate = calendarEndDate;
			calendarEndDate = swap;
		}

		if (calendarStartDate.get(Calendar.DATE) == calendarEndDate
				.get(Calendar.DATE))
			return true;

		if (calendarStartDate.get(Calendar.DATE) > calendarEndDate
				.get(Calendar.DATE)) {
			if (calendarEndDate.get(Calendar.DATE) == calendarEndDate
					.getActualMaximum(Calendar.DATE))
				return true;
		}

		return false;
	}

	/**
	 * 判断日期是否与指定的日期对日
	 * 
	 * @param date
	 * @param dd
	 * @return
	 */
	public static boolean isSameDate(Date date, String dd) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = Integer.parseInt(dd);

		if (calendar.get(Calendar.DATE) == day)
			return true;

		if (calendar.get(Calendar.DATE) < day) {
			if (calendar.get(Calendar.DATE) == calendar
					.getActualMaximum(Calendar.DATE))
				return true;
		}

		return false;
	}

	/**
	 * 判断两个日期是否同一个月
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean isSameMonth(Date startDate, Date endDate) {
		if (startDate == null || endDate == null)
			return false;

		Calendar calendarStartDate = Calendar.getInstance();
		Calendar calendarEndDate = Calendar.getInstance();

		// 设日历为相应日期
		calendarStartDate.setTime(startDate);
		calendarEndDate.setTime(endDate);

		if (calendarStartDate.get(Calendar.YEAR) == calendarEndDate
				.get(Calendar.YEAR)
				&& calendarStartDate.get(Calendar.MONTH) == calendarEndDate
						.get(Calendar.MONTH))
			return true;

		return false;
	}

	/**
	 * 得到本月第一天的日期
	 * 
	 * @param today
	 * @return
	 */
	public static Date getFirstDate(Date today) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.set(Calendar.DATE, 1);

		return calendar.getTime();
	}

	/**
	 * 得到本月最后一天的日期
	 * 
	 * @param today
	 * @return
	 */
	public static Date getLastDate(Date today) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));

		return calendar.getTime();
	}

	public static long compareDate(Date sourceDate, Date targetDate) {
		long ret = -1;

		if (sourceDate == null && targetDate == null) {
			ret = 0;
		} else if (sourceDate == null) {
			ret = -1;
		} else if (targetDate == null) {
			ret = 1;
		} else {
			if (sourceDate.getYear() > targetDate.getYear()) {
				ret = 1;
			} else if (sourceDate.getYear() < targetDate.getYear()) {
				ret = -1;
			} else {
				if (sourceDate.getMonth() > targetDate.getMonth()) {
					ret = 1;
				} else if (sourceDate.getMonth() < targetDate.getMonth()) {
					ret = -1;
				} else {
					if (sourceDate.getDate() > targetDate.getDate()) {
						ret = 1;
					} else if (sourceDate.getDate() < targetDate.getDate()) {
						ret = -1;
					} else {
						ret = 0;
					}
				}
			}
		}
		return ret;
	}

	public static int getWeekNumByDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat(DF_YMD);
		try {
			Date d = format.parse(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			int num = cal.get(Calendar.DAY_OF_WEEK) - 1;
			return num;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	//是否月末
    public static boolean isEndOfMonth(Date nowday){   
        Calendar  ca=Calendar.getInstance();   
        ca.setTime(nowday);   
        if(ca.get(Calendar.DATE)==ca.getActualMaximum(Calendar.DAY_OF_MONTH))   
          return true;
        else
          return false;
    }

	//是否月末
    public static boolean isEndOfMonth(){   
        Calendar  ca=Calendar.getInstance();   
        ca.setTime(getCurrentDate_Date());   
        if(ca.get(Calendar.DATE)==ca.getActualMaximum(Calendar.DAY_OF_MONTH))   
          return true;
        else
          return false;
    }
	//今天是否月中(两周)
    public static boolean isMiddleOfMonth(){   
        int d=getDay();
        if(d==15){
        	return true;
        }else{
        	return false;
        }
    }
	//今天是否周末
    public static boolean isEndOfWeek(){   
    	return getWeekNumByDate(getCurrentDate_String())==0;
    }
    public static int getWeekday(String date){
    	Date d = stringToDate(date);
    	return d.getDay();
    }
	//今天是否1个半月
    public static boolean isOneAndHelfMonth(){   
    	int m=getMonth();
    	int d=getDay();
    	if(m==2 && d==15){
    		return true;
    	}
    	if(m==3 && d==31){
    		return true;
    	}
    	if(m==5 && d==15){
    		return true;
    	}
    	if(m==6 && d==30){
    		return true;
    	}
    	if(m==8 && d==15){
    		return true;
    	}
    	if(m==9 && d==30){
    		return true;
    	}
    	if(m==11 && d==15){
    		return true;
    	}
    	if(m==12 && d==31){
    		return true;
    	}
    	return false;
    }
	//今天是否双月
    public static boolean isDoubleMonth(){   
    	int m=getMonth();
    	Date nowdate=getCurrentDate_Date();
    	if(m==2 || m==4 || m==6 || m==8 || m==10 || m==12){
    		return isEndOfMonth(nowdate);
    	}
    	return false;
    }
    
    /**
     * 获取当天是周几
     * @param today
     * @return
     */
    public static int getWeek(Date today){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
//        return calendar.get(Calendar.DAY_OF_WEEK)-1;
        switch(calendar.get(Calendar.DAY_OF_WEEK)){
        case 1:
            return 0;
        case 2:
            return 1;
        case 3:
            return 2;
        case 4:
            return 3;
        case 5:
            return 4;
        case 6:
            return 5;
        case 7:
            return 6;
        }
        return 0;
    }
    
    /*
     * 得到出发时间为周几
     */
    public static String getWeekDay(String depDate) {
        String today = "";
        String[] week = new String[] { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        SimpleDateFormat dateFormat = new SimpleDateFormat(DF_YMD);
        try {
            Date dDate = dateFormat.parse(depDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dDate);
            int dw = cal.get(Calendar.DAY_OF_WEEK) - 1;
            today = week[dw];
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return today;
    }
    
  //返回指定时间的前后n小时的时间
	public static Date addNHours(Date date,int hours){
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTime(date);
		currentDate.add(Calendar.HOUR_OF_DAY, hours);
		return currentDate.getTime();
	}

	public static int[] dateSpan(Date beginDate, Date endDate){
		Calendar b_cal = Calendar.getInstance();
		b_cal.setTime(beginDate);
		Calendar e_cal = Calendar.getInstance();
		e_cal.setTime(endDate);
		int year = e_cal.get(Calendar.YEAR) - b_cal.get(Calendar.YEAR);
		int month = e_cal.get(Calendar.MONTH) - b_cal.get(Calendar.MONTH);
		if (month<0 && year>0){
			year--;
			month += 12;
		}
		int day = e_cal.get(Calendar.DATE) - b_cal.get(Calendar.DATE);
		if (day<0 && month>0){
			month--;
			day += 30;
		}
		int hour = e_cal.get(Calendar.HOUR) - b_cal.get(Calendar.HOUR);
		if (hour<0 && day>0){
			day--;
			hour += 24;
		}
		int minute = e_cal.get(Calendar.MINUTE) - b_cal.get(Calendar.MINUTE);
		if (minute<0 && hour>0){
			hour--;
			minute += 60;
		}
		int second = e_cal.get(Calendar.SECOND) - b_cal.get(Calendar.SECOND);
		if (second<0 && minute>0){
			minute--;
			second += 60;
		}
		int[] ymdhms = new int[6];
		ymdhms[0] = year;
		ymdhms[1] = month;
		ymdhms[2] = day;
		ymdhms[3] = hour;
		ymdhms[4] = minute;
		ymdhms[5] = second;
		return ymdhms;
	}
	public static int[] dateSpanEx(Date beginDate, Date endDate){
		long i = (endDate.getTime() - beginDate.getTime())/1000;
		int l = (int) i;
		System.out.println( l );
		int second = l % 60;
		int minute = (l / 60) % 60;
		int hour = l / 3600;
		int day = l / 86400;
		int month = day / 30;
		int year = month / 12;
		int[] ymdhms = new int[6];
		ymdhms[0] = year;
		ymdhms[1] = month;
		ymdhms[2] = day;
		ymdhms[3] = hour;
		ymdhms[4] = minute;
		ymdhms[5] = second;
		return ymdhms;
	}
	public static String formatDateSpan(Date beginDate, Date endDate){
		int[] ymdhms = dateSpan(beginDate , endDate);
		int y = ymdhms[0];
		if (y>0){
			if (y>10) return "10年前";
			else return y+"年前";
		}
		int m = ymdhms[1];
		if (m>0){
			return m+"个月前";
		}
		int d = ymdhms[2];
		if (d>0){
			return d+"天前";
		}
		
		int h = ymdhms[3];
		if (h>0){
			return h+"小时前";
		}
		int mi = ymdhms[4];
		if (mi>0){
			return mi+"分钟前";
		}
		int s = ymdhms[5];
		return s+"秒前";
	}

	public static void main(String[] args){
		Date cur = DateUtils.stringToDateTime("2015-07-05 11:30:00");
		Date now = DateUtils.getCurrentDate_Date();
		System.out.println( DateUtils.getWeek(cur) + "" + DateUtils.getWeekday("2015-07-05"));
//		System.out.println( DateUtils.dateToString(now, "MMddHHmmddsss") );
//		System.out.println(DateUtils.stringToDate_full("2015-06-08 09:08:00:00:00"));
//		System.out.println( DateUtils.nMinsBetweenTwoDate(now, cur) );
//		Date d = DateUtils.stringToDate("2015-04-20 8:40", "yyyy-MM-dd HH:mm");
//		System.out.println(d);
//		System.out.println( DateUtils.dateToString_full(d) );
	}
}
