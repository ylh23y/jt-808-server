package com.yun.jt808.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Pattern;

/**
* @Description: 日期工具
* @author James
* @date 2021年4月26日 下午2:48:56
 */
public class DateUtil{

	/**
	 * yyyy-MM-dd
	 */
	public static String datePattern = "yyyy-MM-dd";
	/**
	 * yyyyMMdd
	 */
	public static String datePattern_1 = "yyyyMMdd";
	/**
	 * yyyyMMddHHmmss
	 */
	public static String datePattern_2 = "yyyyMMddHHmmss";
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static String datePattern_3 = "yyyy-MM-dd HH:mm:ss";

	public static String timePattern = "HH:mm:ss";

	public static String datetimePattern = datePattern + " " + timePattern;

	
	/**
	 * 将时间字符串解析成日期
	 * @param timeStr 字符串时间
	 * @param format 格式格式
	 * @return 时间对象
	 */
	public static Date parseStringToDate(String timeStr,String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(timeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 默认构造方法
	 */
	private DateUtil() {
	}

	/**
	 * 缺省的日期格式 (yyyy-MM-dd)
	 * 
	 * @return 在页面中显示的日期格式
	 */
	public static String getDatePattern() {
		return datePattern;
	}

	/**
	 * 根据日期格式，返回日期按datePattern格式转换后的字符串
	 * 
	 * @param aDate
	 *            日期对象
	 * @return 格式化后的日期的页面显示字符串
	 */
	public static final String getDate(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(datePattern);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}
	
	public static final String getyyyyMM(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";
		if (aDate != null) {
			df = new SimpleDateFormat("yyyy-MM");
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * 根据日期格式，返回日期按datePattern格式转换后的字符串
	 * 
	 * @param aDate
	 *            日期对象
	 * @return 格式化后的日期的页面显示字符串
	 */
	public static final String getDateNow(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(datePattern_1);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}
	/**
	 * 根据日期格式，返回日期按datePattern格式转换后的字符串
	 * 
	 * @param aDate
	 *            日期对象
	 * @return 格式化后的日期的页面显示字符串
	 */
	public static final String getDateNowExt(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(datePattern_2);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * 根据日期格式，返回日期按datePattern格式转换后的字符串
	 * 
	 * @param aDate
	 *            日期对象
	 * @return 格式化后的日期的页面显示字符串
	 */
	public static final String getDateNow3(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(datePattern_3);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}
	/**
	 * 按照日期格式，将字符串解析为日期对象
	 * 
	 * @param aMask
	 *            输入字符串的格式
	 * @param strDate
	 *            一个按aMask格式排列的日期的字符串描述
	 * @return Date 对象
	 * @see java.text.SimpleDateFormat
	 */
	public static final Date convertStringToDate(String aMask, String strDate) {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);
		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			// log.error("ParseException: " + pe);
		}
		return (date);
	}
	
	/**
	 * 字符串转换日期
	 * 
	 * @param date
	 * 
	 * @param pattern
	 * 
	 * @return
	 * 
	 * @throws ParseException
	 */
	public static Date stringToDate1(String pattern, String date) {
		if(date != null){
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			try {
				return df.parse(date);
			} catch (ParseException e) {
				DateFormat df1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'CST' yyyy",Locale.US);
				try {
					return df1.parse(date);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/**
	 * 根据默认格式获取当前时间
	 * 
	 * @param theTime
	 *            当前时间
	 * @return 格式化的时间
	 */
	public static String getDateToString(Date theTime) {
		return dateToString(datePattern, theTime);
	}
	/**
	 * 字符串转换日期
	 * 
	 * @param date
	 * 
	 * @param pattern
	 * 
	 * @return
	 * 
	 * @throws ParseException
	 */
	public static String dateToString(String pattern, Date date) {
		if(date != null){
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.format(date);
		}
		return null;
	}
	

	/**
	 * 根据默认格式获取当前时间
	 * 
	 * @param theTime
	 *            当前时间
	 * @return 格式化的时间
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(datetimePattern, theTime);
	}

	/**
	 * 根据默认格式获取当前时间
	 * 
	 * @return 日期格式的时间
	 */
	public static Calendar getToday() {
		SimpleDateFormat df = new SimpleDateFormat(datePattern);
		String todayAsString = df.format(new Date());
		Calendar cal = new GregorianCalendar();
		cal.setTime(strtoDate(todayAsString));
		return cal;
	}

	/**
	 * 根据给定格式格式化日期
	 * 
	 * @param aMask
	 *            参数模板为用户自定义模板例如:yyyy-MM-dd HH:mm:SS
	 * @param aDate
	 *            日期对象
	 * @return 返回一个根据模板将日期类型转换的字符串
	 * 
	 * @see java.text.SimpleDateFormat
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * 将日期对象转换成代年日月时分的字符串格式
	 * 
	 * @param date
	 *            日期类型
	 * @return 返回类型为String 格式为yyyy-MM-dd HH:mm:ss
	 */
	public static final String getDateTime(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(datetimePattern);
		String returnValue = "";
		if (date != null) {
			returnValue = df.format(date);
		}
		return (returnValue);
	}

	/**
	 * 根据日期格式，返回日期按datePattern格式转换后的字符串
	 * 
	 * @param date
	 *            日期
	 * @return 格式转换后的字符串
	 */
	public static final String dateToStr(Date date) {
		return getDateTime(datePattern, date);
	}

	/**
	 * 按照日期格式，将字符串解析为日期对象
	 * 
	 * @param strDate
	 *            (格式 yyyy-MM-dd)
	 * @return 日期对象
	 * 
	 */
	public static Date strtoDate(String strDate) {
		return convertStringToDate(datePattern, strDate);
	}
	/**
	 * 按照日期格式，将字符串解析为日期对象
	 * 
	 * @param strDate
	 *            (格式 yyyy-MM-dd)
	 * @return 日期对象
	 * 
	 */
	public static Date strtoDate1(String strDate) {
		return convertStringToDate(datePattern_3, strDate);
	}
	/**
	 * 按照日期格式，将字符串解析为日期对象
	 * 
	 * @param strDate
	 *            (格式 yyyy-MM-dd)
	 * @return 日期对象
	 * 
	 */
	public static Date strtoDate2(String strDate) {
		return stringToDate1(datePattern_3, strDate);
	}
	/**
	 * 校验两个日期大小
	 * 
	 * @param start
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return 大于：true，小于：false
	 */
	public static boolean checkTime(Date start, Date end) {
		if ((start.getTime() - end.getTime()) >= 0){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 获取日期年
	 * 
	 * @param date
	 *            时间
	 * @return 年份
	 */
	public static String getCurrentYearStr(Date date) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy");
		String str = bartDateFormat.format(date);
		return str;
	}

	/**
	 * 得到两个日期之间相差多少天
	 * 
	 * @param firstDate
	 *            firstDate
	 * @param secondDate
	 *            secondDate
	 * @return 相差天数
	 */
	public static int dateMinus(Date firstDate, Date secondDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fir = sdf.format(firstDate);
		String sec = sdf.format(secondDate);
		int minus = DateUtil.dateMinus(fir, sec);
		return minus;
	}

	/**
	 * 得到两个日期之间相差多少天
	 * 
	 * @param frontdate
	 *            相对比较小的日期 yyyy-mm-dd
	 * @param afterdate
	 *            相对比较大的日期 yyyy-mm-dd
	 * @return dateMinus
	 */
	public static int dateMinus(String frontdate, String afterdate) {
		String[] front = frontdate.split("-");
		String[] after = afterdate.split("-");
		String frontYear = null;
		String frontMon = null;
		String frontDay = null;
		String afterYear = null;
		String aftertMon = null;
		String afterDay = null;
		frontYear = front[0];
		frontMon = front[1].replaceAll("0", "");
		frontDay = front[2];
		if (Integer.parseInt(frontDay) < 10) {
			afterDay = after[2].replaceAll("0", "");
		} else {
			afterDay = after[2];
		}
		afterYear = after[0];

		aftertMon = after[1];
		if (Integer.parseInt(aftertMon) < 10) {
			aftertMon = after[1].replaceAll("0", "");
		}

		afterDay = after[2];

		// 注释：30=3 的功能
		if (Integer.parseInt(afterDay) < 10) {
			afterDay = after[2].replaceAll("0", "");
		}
		Calendar cal = Calendar.getInstance();

		cal.set(Integer.parseInt(frontYear), Integer.parseInt(frontMon) - 1,
				Integer.parseInt(frontDay));
		Date tempd1 = cal.getTime();
		cal.set(Integer.parseInt(afterYear), Integer.parseInt(aftertMon) - 1,
				Integer.parseInt(afterDay));
		Date tempd2 = cal.getTime();
		long minus = tempd2.getTime() - tempd1.getTime();
		return (int) (minus / 60 / 60 / 24 / 1000);
	}

	/**
	 * 两个日期之间相隔的天数
	 * 
	 * @param dateFrom
	 *            开始时间
	 * @param dateEnd
	 *            结束时间
	 * @return 天数
	 */
	public static long getDaysBetweenTwoDates(Date dateFrom, Date dateEnd) {
		if (dateFrom == null || dateEnd == null) {
			return 0;
		}
		long begin = dateFrom.getTime();
		long end = dateEnd.getTime();
		long inter = end - begin;
		if (inter < 0) {
			inter = inter * (-1);
		}
		long dateMillSec = 24 * 60 * 60 * 1000;
		long dateCnt = inter / dateMillSec;
		long remainder = inter % dateMillSec;
		if (remainder != 0) {
			dateCnt++;
		}
		return dateCnt;
	}

	/**
	 * 一个日期型，减去或加上一个整形，求出日期结果
	 * 
	 * @param dateFormate
	 *            日期
	 * @param num
	 *            天数（可为负数）
	 * @return 日期结果
	 */
	public static String dateNum(String dateFormate, int num) {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.add(Calendar.DATE, num);
		java.util.Date date = cal.getTime();
		return getDateTime(dateFormate, date);
	}

	/**
	 * 功能: 比较指定两日期如果str1晚于str2则return false; 精确到秒
	 * 
	 * @param str1
	 *            日期1
	 * @param str2
	 *            日期2
	 * @return boolean
	 */
	public static final boolean secondCompare(String str1, String str2) {
		boolean bea = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date date1;
		java.util.Date date0;
		try {
			date1 = sdf.parse(str1);
			date0 = sdf.parse(str2);
			if (date0.after(date1)) {
				bea = true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			bea = false;
		}
		return bea;
	}

	/**
	 * 功能: 比较指定两日期如果str1晚于str2则return flase; 精确到天
	 * 
	 * @param str1
	 *            日期1
	 * @param str2
	 *            日期2
	 * @return boolean
	 */
	public static final boolean dateCompare(String str1, String str2) {
		boolean bea = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date1;
		java.util.Date date0;
		try {
			date1 = sdf.parse(str1);
			date0 = sdf.parse(str2);
			if (date0.after(date1)) {
				bea = true;
			}
		} catch (ParseException e) {
			bea = false;
		}
		return bea;
	}

	/**
	 * 功能:计算两个日期相隔的具体时间
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endtime
	 *            结束时间
	 * @return 几天几小时几分几秒
	 */
	public static final String getBetweenDayTime(Date beginTime, Date endtime) {
		if (beginTime == null || endtime == null) {
			return "0天";
		}
		long l = endtime.getTime() - beginTime.getTime();
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		StringBuffer sb = new StringBuffer();
		if (day != 0) {
			sb.append(day + "天");
		}
		if (hour != 0) {
			sb.append(hour + "小时");
		}
		if (min != 0) {
			sb.append(min + "分");
		}
		if (s != 0) {
			sb.append(s + "秒");
		}
		return sb.toString();
	}

	/**
	 * 返回两个日期相差多少秒
	 * 
	 * @param beginTime
	 *            开始日期
	 * @param endtime
	 *            结束日期
	 * @return 相差多少秒
	 */
	public static final long getSeconds(Date beginTime, Date endtime) {
		long l = endtime.getTime() - beginTime.getTime();
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		return (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
	}

	/**
	 * 按照日期格式，将字符串解析为日期对象
	 * 
	 * @param strDate
	 *            (格式 yyyy-MM-dd HH:mm:ss)
	 * @return 日期对象
	 */
	public static Date convertStringToDateTime(String strDate) {
		return convertStringToDate(datetimePattern, strDate);
	}

	/**
	 * 判断指定日期的年份是否是闰年
	 * 
	 * @return 否是闰年
	 */
	public static boolean isLeapYear() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return isLeapYear(year);
	}

	/**
	 * 判断指定年份是否是闰年
	 * 
	 * @param year
	 *            指定年份
	 * @return 否是闰年
	 */
	public static boolean isLeapYear(int year) {
		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
		 * 3.能被4整除同时能被100整除则不是闰年
		 */
		if ((year % 400) == 0){
			return true;
		}else if ((year % 4) == 0) {
			if ((year % 100) == 0){
				return false;
			}else{
				return true;
			}
		} else{
			return false;
		}
	}

	/**
	 * 判断指定日期的年份是否是闰年
	 * 
	 * @param date
	 *            指定日期。
	 * @return 是否闰年
	 */
	public static boolean isLeapYear(Date date) {
		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
		 * 3.能被4整除同时能被100整除则不是闰年
		 */

		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		int year = gc.get(Calendar.YEAR);
		return isLeapYear(year);
	}

	/**
	 * 判断指定日期的年份是否是闰年
	 * 
	 * @param gc
	 *            指定日期。
	 * @return 是否闰年
	 */
	public static boolean isLeapYear(Calendar gc) {
		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
		 * 3.能被4整除同时能被100整除则不是闰年
		 */
		int year = gc.get(Calendar.YEAR);
		return isLeapYear(year);
	}

	/**
	 * 取得指定日期的所处星期的第一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的所处星期的第一天
	 */
	public static Date getFirstDayOfWeek(Date date) {
		/**
		 * 详细设计： 1.如果date是星期日，则减0天 2.如果date是星期一，则减1天 3.如果date是星期二，则减2天
		 * 4.如果date是星期三，则减3天 5.如果date是星期四，则减4天 6.如果date是星期五，则减5天
		 * 7.如果date是星期六，则减6天
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY):
			gc.add(Calendar.DATE, 0);
			break;
		case (Calendar.MONDAY):
			gc.add(Calendar.DATE, -1);
			break;
		case (Calendar.TUESDAY):
			gc.add(Calendar.DATE, -2);
			break;
		case (Calendar.WEDNESDAY):
			gc.add(Calendar.DATE, -3);
			break;
		case (Calendar.THURSDAY):
			gc.add(Calendar.DATE, -4);
			break;
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, -5);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, -6);
			break;
		default:
			break;
		}
		return gc.getTime();
	}

	/**
	 * 取得指定日期的所处星期的第一天
	 * 
	 * @param gc
	 *            参数为Calendar类型
	 * @return 指定日期的所处星期的第一天
	 */
	public static Calendar getFirstDayOfWeek(Calendar gc) {
		/**
		 * 详细设计： 1.如果date是星期日，则减0天 2.如果date是星期一，则减1天 3.如果date是星期二，则减2天
		 * 4.如果date是星期三，则减3天 5.如果date是星期四，则减4天 6.如果date是星期五，则减5天
		 * 7.如果date是星期六，则减6天
		 */
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY):
			gc.add(Calendar.DATE, 0);
			break;
		case (Calendar.MONDAY):
			gc.add(Calendar.DATE, -1);
			break;
		case (Calendar.TUESDAY):
			gc.add(Calendar.DATE, -2);
			break;
		case (Calendar.WEDNESDAY):
			gc.add(Calendar.DATE, -3);
			break;
		case (Calendar.THURSDAY):
			gc.add(Calendar.DATE, -4);
			break;
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, -5);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, -6);
			break;
		default:
			break;
		}
		return gc;
	}

	/**
	 * 取得指定日期的所处星期的最后一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return Date类型 指定日期的所处星期的最后一天
	 */
	public static Date getLastDayOfWeek(Date date) {
		/**
		 * 详细设计： 1.如果date是星期日，则加6天 2.如果date是星期一，则加5天 3.如果date是星期二，则加4天
		 * 4.如果date是星期三，则加3天 5.如果date是星期四，则加2天 6.如果date是星期五，则加1天
		 * 7.如果date是星期六，则加0天
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY):
			gc.add(Calendar.DATE, 6);
			break;
		case (Calendar.MONDAY):
			gc.add(Calendar.DATE, 5);
			break;
		case (Calendar.TUESDAY):
			gc.add(Calendar.DATE, 4);
			break;
		case (Calendar.WEDNESDAY):
			gc.add(Calendar.DATE, 3);
			break;
		case (Calendar.THURSDAY):
			gc.add(Calendar.DATE, 2);
			break;
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, 1);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, 0);
			break;
		default:
			break;
		}
		return gc.getTime();
	}

	/**
	 * 取得指定日期的下一个星期的第一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return Date类型 指定日期的下一个星期的第一天
	 */
	public static Date getFirstDayOfNextWeek(Date date) {
		// 1.调用getNextWeek设置当前时间 2.以1为基础，调用getFirstDayOfWeek
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.setTime(DateUtil.getNextWeek(gc.getTime()));
		gc.setTime(DateUtil.getFirstDayOfWeek(gc.getTime()));
		return gc.getTime();
	}

	/**
	 * 取得指定日期的下一个星期的第一天
	 * 
	 * @param gc
	 *            指定日期。
	 * @return Calendar类型 指定日期的下一个星期的第一天
	 */
	public static Calendar getFirstDayOfNextWeek(Calendar gc) {
		// 详细设计： 1.调用getNextWeek设置当前时间 2.以1为基础，调用getLastDayOfWeek
		gc.setTime(DateUtil.getNextWeek(gc.getTime()));
		gc.setTime(DateUtil.getFirstDayOfWeek(gc.getTime()));
		return gc;
	}

	/**
	 * 取得指定日期的下一个星期的最后一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的下一个星期的最后一天
	 */
	public static Date getLastDayOfNextWeek(Date date) {
		// 详细设计： 1.调用getNextWeek设置当前时间 2.以1为基础，调用getLastDayOfWeek
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.setTime(DateUtil.getNextWeek(gc.getTime()));
		gc.setTime(DateUtil.getLastDayOfWeek(gc.getTime()));
		return gc.getTime();
	}

	/**
	 * 取得指定日期的下一个星期
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的下一个星期
	 */
	public static Date getNextWeek(Date date) {
		// 1.指定日期加7天
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, 7);
		return gc.getTime();
	}

	/**
	 * 取得指定日期的下一个星期
	 * 
	 * @param gc
	 *            指定日期。
	 * @return 指定日期的下一个星期
	 */
	public static Calendar getNextWeek(Calendar gc) {
		// 1.指定日期加7天
		gc.add(Calendar.DATE, 7);
		return gc;
	}

	/**
	 * 取得指定日期的上一个星期
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的上一个星期
	 */
	public static Date getPreviousWeek(Date date) {
		// 1.指定日期减7天
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, -7);
		return gc.getTime();
	}

	/**
	 * 取得指定日期的上一个星期
	 * 
	 * @param gc
	 *            指定日期。
	 * @return 指定日期的上一个星期
	 */
	public static Calendar getPreviousWeek(Calendar gc) {
		// 1.指定日期减7天
		gc.add(Calendar.DATE, -7);
		return gc;
	}

	/**
	 * 获得给定日期所在的星期几
	 * 
	 * @param date
	 *            给定日期
	 * @return 整数类型
	 */
	public static int getWeekForDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;

	}

	/***************************************************************************
	 * 获得给定日期所在的星期几
	 * 
	 * @param gc
	 *            给定日期
	 * @return 整数类型
	 */
	public static int getWeekForDate(Calendar gc) {
		return gc.get(Calendar.DAY_OF_WEEK) - 1;
	}

	// end－－－－－－－－－－－－－－－－－－－与星期有关的方法－－－－－－－－－－－－－－－－－－－－－－－－//

	// －－－－－－－－－－－－－－－－－－－与月份有关的方法－－－－－－－－－－－－－－－－－－－－－－－－//
	/**
	 * 取得指定日期的所处月份的第一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return Date类型指定日期的所处月份的第一天
	 */
	public static Date getFirstDayOfMonth(Date date) {
		/**
		 * 详细设计： 1.设置为1号
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.set(Calendar.DAY_OF_MONTH, 1);
		return gc.getTime();
	}

	/**
	 * 取得指定日期的所处月份的第一天
	 * 
	 * @param gc
	 *            指定日期。
	 * @return Calendar类型 指定日期的所处月份的第一天
	 */
	public static Calendar getFirstDayOfMonth(Calendar gc) {
		/**
		 * 详细设计： 1.设置为1号
		 */
		gc.set(Calendar.DAY_OF_MONTH, 1);
		return gc;
	}

	/**
	 * 取得指定日期的所处月份的最后一天
	 * 
	 * @param date
	 *            指定日期
	 * @return 日期类型 指定日期的所处月份的最后一天
	 */
	public static Date getLastDayOfMonth(Date date) {
		/**
		 * 详细设计： 1.如果date在1月，则为31日 2.如果date在2月，则为28日 3.如果date在3月，则为31日
		 * 4.如果date在4月，则为30日 5.如果date在5月，则为31日 6.如果date在6月，则为30日
		 * 7.如果date在7月，则为31日 8.如果date在8月，则为31日 9.如果date在9月，则为30日
		 * 10.如果date在10月，则为31日 11.如果date在11月，则为30日 12.如果date在12月，则为31日
		 * 1.如果date在闰年的2月，则为29日
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.MONTH)) {
		case 0:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 1:
			gc.set(Calendar.DAY_OF_MONTH, 28);
			break;
		case 2:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 3:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 4:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 5:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 6:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 7:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 8:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 9:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 10:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 11:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		default:
			break;
		}
		// 检查闰年
		if ((gc.get(Calendar.MONTH) == Calendar.FEBRUARY)
				&& (isLeapYear(gc.get(Calendar.YEAR)))) {
			gc.set(Calendar.DAY_OF_MONTH, 29);
		}
		return gc.getTime();
	}

	/**
	 * 取得指定日期的所处月份的最后一天
	 * 
	 * @param gc
	 *            指定日期日历
	 * @return Calendar类型指定日期的所处月份的最后一天
	 */
	public static Calendar getLastDayOfMonth(Calendar gc) {
		/**
		 * 详细设计： 1.如果date在1月，则为31日 2.如果date在2月，则为28日 3.如果date在3月，则为31日
		 * 4.如果date在4月，则为30日 5.如果date在5月，则为31日 6.如果date在6月，则为30日
		 * 7.如果date在7月，则为31日 8.如果date在8月，则为31日 9.如果date在9月，则为30日
		 * 10.如果date在10月，则为31日 11.如果date在11月，则为30日 12.如果date在12月，则为31日
		 * 1.如果date在闰年的2月，则为29日
		 */
		switch (gc.get(Calendar.MONTH)) {
		case 0:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 1:
			gc.set(Calendar.DAY_OF_MONTH, 28);
			break;
		case 2:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 3:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 4:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 5:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 6:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 7:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 8:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 9:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 10:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 11:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		default:
			break;
		}
		// 检查闰年
		if ((gc.get(Calendar.MONTH) == Calendar.FEBRUARY)
				&& (isLeapYear(gc.get(Calendar.YEAR)))) {
			gc.set(Calendar.DAY_OF_MONTH, 29);
		}
		return gc;
	}

	/**
	 * 取得指定日期的下一个月的第一天
	 * @param date 指定日期。
	 * @return 指定日期的下一个月的第一天
	 */
	public static Date getFirstDayOfNextMonth(Date date) {
		/**
		 * 详细设计： 1.调用getNextMonth设置当前时间 2.以1为基础，调用getFirstDayOfMonth
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.setTime(DateUtil.getNextMonth(gc.getTime()));
		gc.setTime(DateUtil.getFirstDayOfMonth(gc.getTime()));
		return gc.getTime();
	}

	/**
	 * 取得指定日期的下一个月的第一天
	 * @param gc 指定日期。
	 * @return 指定日期的下一个月的第一天
	 */
	public static Calendar getFirstDayOfNextMonth(Calendar gc) {
		// 1.调用getNextMonth设置当前时间 2.以1为基础，调用getLastDayOfMonth
		gc.setTime(DateUtil.getNextMonth(gc.getTime()));
		gc.setTime(DateUtil.getFirstDayOfMonth(gc.getTime()));
		return gc;
	}

	/**
	 * 取得指定日期的下一个月的最后一天
	 * @param date 指定日期。
	 * @return 指定日期的下一个月的最后一天
	 */
	public static Date getLastDayOfNextMonth(Date date) {
		// 1.调用getNextMonth设置当前时间 2.以1为基础，调用getLastDayOfMonth
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.setTime(DateUtil.getNextMonth(gc.getTime()));
		gc.setTime(DateUtil.getLastDayOfMonth(gc.getTime()));
		return gc.getTime();
	}

	/**
	 * 取得指定日期的下一个月
	 * @param date 指定日期。
	 * @return 指定日期的下一个月
	 */
	public static Date getNextMonth(Date date) {
		// 1.指定日期的月份加1
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.MONTH, 1);
		return gc.getTime();
	}

	/**
	 * 取得指定日期的下一个月
	 * @param gc 指定日期。
	 * @return 指定日期的下一个月
	 */
	public static Calendar getNextMonth(Calendar gc) {
		// 1.指定日期的月份加1
		gc.add(Calendar.MONTH, 1);
		return gc;
	}

	/**
	 * 取得指定日期的上一个月
	 * @param date 指定日期。
	 * @return 指定日期的上一个月
	 */
	public static Date getPreviousMonth(Date date) {
		// 1.指定日期的月份减1
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.MONTH, -1);
		return gc.getTime();
	}

	/**
	 * 取得指定日期的上一个月
	 * @param gc 指定日期。
	 * @return 指定日期的上一个月
	 */
	public static Calendar getPreviousMonth(Calendar gc) {
		// 1.指定日期的月份减1
		gc.add(Calendar.MONTH, -1);
		return gc;
	}

	/**
	 * 取得指定日期的下一天
	 * @param date  指定日期。
	 * @return 指定日期的下一天
	 */
	public static Date getNextDay(Date date) {
		// 1.指定日期加1天
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, 1);
		return gc.getTime();
	}

	/**
	 * 取得指定日期的下一天
	 * @param gc  指定日期。
	 * @return 指定日期的下一天
	 */
	public static Calendar getNextDay(Calendar gc) {
		// 1.指定日期加1天
		gc.add(Calendar.DATE, 1);
		return gc;
	}

	/**
	 * 取得指定日期的前一天
	 * @param date 指定日期。
	 * @return 指定日期的前一天
	 */
	public static Date getPreviousDay(Date date) {
		/**
		 * 详细设计： 1.指定日期减1天
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, -1);
		return gc.getTime();
	}

	/**
	 * 取得指定日期的前一天
	 * @param gc 指定日期。
	 * @return 指定日期的前一天
	 */
	public static Calendar getPreviousDay(Calendar gc) {
		/**
		 * 详细设计： 1.指定日期减1天
		 */
		gc.add(Calendar.DATE, -1);
		return gc;
	}

	/**
	 * 给定日期类型获得日期所在年
	 * @param date 指定日期
	 * @return 所在年
	 */
	public static String getYearForDateTime(Date date) {
		String strDate = getDateTime(datetimePattern, date).trim();
		return strDate.substring(0, 4);
	}

	/**
	 * 给定日期类型获得日期所在月份
	 * @param date 指定日期
	 * @return 所在月份
	 */
	public static String getMonthForDateTime(Date date) {
		String strDate = getDateTime(datetimePattern, date).trim();
		return strDate.substring(5, 7);
	}

	/**
	 * 给定日期类型获得日期所在日
	 * @param date 指定日期
	 * @return 所在日
	 */
	public static String getDateForDateTime(Date date) {
		String strDate = getDateTime(datetimePattern, date).trim();
		return strDate.substring(8, 10);
	}

	/**
	 * 给定日期类型获得日期所在时
s	 * @param date 指定日期
	 * @return 所在时
	 */
	public static String getHourForDateTime(Date date) {
		String strDate = getDateTime(datetimePattern, date).trim();
		return strDate.substring(11, 13);
	}

	/**
	 * 给定日期类型获得日期所在秒
	 * @param date 指定日期
	 * @return 所在秒
	 */
	public static String getMinuteForDateTime(Date date) {
		String strDate = getDateTime(datetimePattern, date).trim();
		return strDate.substring(14, 16);
	}

	/**
	 * 给定一个日期，返回加减n天后的日期，返回Date nDaysAfterOneDate
	 * @param basicDate Date
	 * @param n int
	 * @return Date
	 */
	public static Date nDaysAfterOneDate(Date basicDate, int n) {
		long nDay = (basicDate.getTime() / (24 * 60 * 60 * 1000) + n)
				* (24 * 60 * 60 * 1000);
		Date newDate = new Date();
		newDate.setTime(nDay);
		return newDate;
	}

	/**
	 * 将String按照标准格式化成Date
	 * @param value String日期
	 * @return Date日期
	 */
	public static Date convert(Object value) {
		SimpleDateFormat df = new SimpleDateFormat(datePattern);
		if (value == null){
			return null;
		}
			
		if (((String) value).trim().length() == 0){
			return null;
		}
			

		if (value instanceof String) {
			try {
				return df.parse((String) value);
			} catch (Exception ex) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 将String按照标准格式化成Date
	 * @param value String日期
	 * @return 判断日期加时间的格式
	 */
	public static final boolean stringToDate(String aMask, String strDate) {
		String strRegex = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-9]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
		Pattern p = Pattern.compile(strRegex);
		boolean flag = false;
		try {
			if (strDate.length() == 19) {
				flag = true;
				if (flag) {
					flag = p.matcher(strDate).matches();
				}
			} else {
				flag = false;
			}
		} catch (Exception pe) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 将String按照标准格式化成Date
	 * @param value String日期
	 * @return 判断日期格式
	 */

	public static final boolean stringToTime(String aMask, String strDate) {
		boolean flag = false;
		String strRegex = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		Pattern p = Pattern.compile(strRegex);
		try {
			if (strDate.length() == 10) {
				flag = true;
				if (flag) {
					flag = p.matcher(strDate).matches();
				}
			} else {
				flag = false;
			}
		} catch (Exception pe) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 将Date按照标准格式化成long
	 * @param value String日期
	 * @return ss秒数
	 */
	public static final long getBetweenSS(Date beginTime, Date endtime) {
		if (beginTime == null || endtime == null) {
			return 0;
		}
		long l = endtime.getTime() - beginTime.getTime();
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		if (day != 0) {
			day = day * 24 * 60 * 60;
		}
		if (hour != 0) {
			hour = hour * 60 * 60;
		}
		if (min != 0) {
			min = min * 60;
		}
		return day + hour + min + s;
	}

	/**
	 * 求出昨天的日期，零点开始
	 * @param dateFormate 输出的日期格式
	 * @param num 天数（可为负数）
	 * @return 日期结果
	 */
	public static String yeDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yeday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		yeday += " 00:00:00";
		return yeday;
	}
	
	/**
	 * 根据传入的时间获取下一天日期
	 * @param date 输出的日期格式
	 * @return 日期结果
	 */
	public static String nextDate(String date) {
		Date d=getNextDay(convertStringToDate("yyyy-MM-dd", date));
		return getDate(d);
	}
	
	/**
	 * 根据传入的时间和格式进行时间字符串的返回
	 * @param String 输出的日期格式
	 * @return 日期结果
	 */
	public static String dateFormat(Date date,String format) {
		SimpleDateFormat sdf =new SimpleDateFormat(format);
		String content=sdf.format(date);
		return content;
	}
	
	/**
	 * 
	 * 根据传入的日期时间获取时间
	 * @param date
	 * @return
	 *
	 */
	public static String getTime(String date) {
		return date.substring(date.indexOf(" ")+1, date.length());
	}

	/**
	 * 
	 * 获取季度首末月份
	 * @param month 当前月份  
	 * @param isQuarterStart true首，false末  
	 *
	 */
	private static int getQuarterInMonth(int month, boolean isQuarterStart) { 
		 int months[] = { 1, 4, 7, 10 };   
		 if (!isQuarterStart) {   
		     months = new int[] { 3, 6, 9, 12 };   
		 }   
		 if (month >= 1 && month <= 3){
			 return months[0];
		 } else if (month >= 4 && month <= 6) {
			 return months[1];
		 } else if (month >= 7 && month <= 9) {
			 return months[2]; 
		 }else {
			 return months[3];   
		 } 
	}  
	
	/**
	 * 
	 * 获取季度中首季度日期
	 * @param date 当前日期  
	 *
	 */
	public static Date getQuarterFirstDate(Date date){
		Calendar calendar = new GregorianCalendar(); 
		int month = getQuarterInMonth(date.getMonth()+1, true);  
		calendar.set(Calendar.MONTH, month-1);   
		calendar.set(Calendar.DAY_OF_MONTH, 1);   
		return calendar.getTime();
	}
	
	/**
	 * 
	 * 获取季度中末季度日期
	 * @param date 当前日期  
	 *
	 */
	public static Date getQuarterLastDate(Date date){
		Calendar calendar = new GregorianCalendar(); 
		int month = getQuarterInMonth(date.getMonth()+1, false);  
		calendar.set(Calendar.MONTH, month);   
		calendar.set(Calendar.DAY_OF_MONTH, 0);   
		return calendar.getTime();
	}
	
	/**
	 * 按照日期格式，将字符串解析为日期对象
	 * 
	 * @param strDate
	 *            (格式 yyyy-MM-dd)
	 * @return 日期对象
	 * 
	 */
	public static String dateAddPattern(Date strDate,int dataNum) {
		return convertDateAdd(datePattern, strDate,dataNum);
	}
	/**
	 * 按照日期格式，将字符串解析为日期对象
	 * 
	 * @param strDate
	 *            (格式 yyyy-MM-dd)
	 * @return 日期对象
	 * 
	 */
	public static String dateAddPattern3(Date strDate,int dataNum) {
		return convertDateAdd(datePattern_3, strDate,dataNum);
	}
	/**
	 * 日期天数的增加
	 * @param date
	 * 日期
	 * @param dataNum 
	 * 增加天数
	 * @return
	 */
	public static String convertDateAdd(String aMask,Date date, int dataNum) {
		// 设置时间
		Calendar cal = Calendar.getInstance();
		String dateFor = "";
		if (date != null) {
			// 设置日期
			cal.setTime(date);
			// 增加 DataNum天
			cal.add(Calendar.DAY_OF_YEAR, dataNum);
			dateFor = new SimpleDateFormat(aMask).format(cal
					.getTime());
		}
		return dateFor;
	}
	
	/**
	 * 计算两个日期之前相隔的天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int dateDaysBetween(Date startDate, Date endDate) {
		if (startDate != null && endDate != null) {
			return 0;
		}
		Date stDate = DateUtil.strtoDate(dateAddPattern3(startDate, 0));
		Date enDate = DateUtil.strtoDate(dateAddPattern3(endDate, 0));
		Calendar cal = Calendar.getInstance();
		cal.setTime(stDate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(enDate);
		long time2 = cal.getTimeInMillis();
		long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(betweenDays));
	}
}