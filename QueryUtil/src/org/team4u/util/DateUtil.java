package org.team4u.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

public class DateUtil {

	private static final int[] dayArray = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	private static SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public final static int HOUR = 10;

	public final static int DAY = 11;

	public final static int MINUTE = 12;

	public final static int SECOND = 13;

	public final static int MILLISECOND = 14;

	public static java.sql.Date convertUtilDateToSQLDate(java.util.Date date) {
		if (date == null)
			return null;
		Calendar cl = Calendar.getInstance();

		cl.setTime(date);
		java.sql.Date jd = new java.sql.Date(cl.getTimeInMillis());
		return jd;
	}

	public static java.util.Date convertSQLDateToUtilDate(java.sql.Date date) {
		if (date == null)
			return null;
		Calendar cl = Calendar.getInstance();

		cl.setTime(date);
		java.util.Date jd = new java.util.Date(cl.getTimeInMillis());
		return jd;
	}

	public static Date stringToDate(String date) {
		if (date == null || "".equalsIgnoreCase(date))
			return null;

		Calendar cd = Calendar.getInstance();
		StringTokenizer token = new StringTokenizer(date, "-/ :.");
		if (token.hasMoreTokens()) {
			cd.set(Calendar.YEAR, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.YEAR, 1970);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.MONTH, Integer.parseInt(token.nextToken()) - 1);
		} else {
			cd.set(Calendar.MONTH, 0);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.DAY_OF_MONTH, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.DAY_OF_MONTH, 1);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.HOUR_OF_DAY, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.HOUR_OF_DAY, 0);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.MINUTE, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.MINUTE, 0);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.SECOND, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.SECOND, 0);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.MILLISECOND, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.MILLISECOND, 0);
		}

		return cd.getTime();
	}

	public static String dateToString(Date date) {
		if (date == null)
			return "";
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		return cl.get(Calendar.YEAR) + "-" + (cl.get(Calendar.MONTH) + 1) + "-"
				+ cl.get(Calendar.DAY_OF_MONTH);
		/*
		 * SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		 * return formatter.format(date).trim();
		 */
	}

	public static String dateTo0String(Date date) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date).trim();

	}

	public static Date getFirstDateOfCurrentMonth() {
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.DAY_OF_MONTH, 1);
		return cl.getTime();
	}

	public static Date getFirstDateOfMonth(String date) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(stringToDate(date));
		cl.set(Calendar.DAY_OF_MONTH, 1);
		return cl.getTime();
	}

	public static Date getLastDateOfMonth(String date) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(stringToDate(date));
		int year = cl.get(Calendar.YEAR);
		int month = cl.get(Calendar.MONTH) + 1;
		int day = 0;
		if (month < 1 || month > 12) {
			return null;
		}

		if (month == 2) {
			if (isLeapYear(year)) {
				day = 29;
			} else {
				day = dayArray[month - 1];
			}
		} else {
			day = dayArray[month - 1];
		}

		cl.set(Calendar.DAY_OF_MONTH, day);

		return cl.getTime();
	}

	public static boolean isLeapYear(int year) {

		if ((year % 400) == 0)
			return true;
		else if ((year % 4) == 0) {
			if ((year % 100) == 0)
				return false;
			else
				return true;
		} else
			return false;
	}

	/**
	 * 获取当前时间之前的时间
	 * 
	 * @param type
	 * @param value
	 * @return
	 */
	public static Date getDateTimeBeforeNow(int type, int value) {
		return getDateTimeBefore(new Date(), type, value);
	}

	/**
	 * 获取当前时间之前的时间
	 * 
	 * @param type
	 * @param value
	 * @return
	 */
	public static Date getDateTimeBefore(Date date, int type, int value) {
		long time = date.getTime() / 1000;

		switch (type) {
		case DAY:
			time = time - 24 * 60 * 60 * value;
			break;
		case HOUR:
			time = time - 60 * 60 * value;
			break;
		case MINUTE:
			time = time - 60 * value;
			break;
		case SECOND:
			time = time - value;
			break;
		default:
			break;
		}

		return new Date(time * 1000);
	}

	public static Date getDateTimeAfterNow(int type, long value) {
		return getDateTimeAfter(new Date(), type, value);
	}

	public static Date getDateTimeAfter(Date date, int type, long value) {
		long nextTime = date.getTime() / 1000;

		switch (type) {
		case DAY:
			nextTime = nextTime + 24 * 60 * 60 * value;
			break;
		case HOUR:
			nextTime = nextTime + 60 * 60 * value;
			break;
		case MINUTE:
			nextTime = nextTime + 60 * value;
			break;
		case SECOND:
			nextTime = nextTime + value;
			break;
		default:
			break;
		}

		return new Date(nextTime * 1000);
	}

	/*
	 * Get the Next Date Write by Jeffy pan 2004-10-21 Date Format:YYYY-MM-DD
	 * YYYY:M:D YYYY/M/DD
	 */
	public static Date getNextDate(String date) {

		Calendar cd = Calendar.getInstance();
		StringTokenizer token = new StringTokenizer(date, "-/ :.");
		if (token.hasMoreTokens()) {
			cd.set(Calendar.YEAR, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.YEAR, 1970);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.MONTH, Integer.parseInt(token.nextToken()) - 1);
		} else {
			cd.set(Calendar.MONTH, 0);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.DAY_OF_MONTH, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.DAY_OF_MONTH, 1);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.HOUR_OF_DAY, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.HOUR_OF_DAY, 0);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.MINUTE, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.MINUTE, 0);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.SECOND, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.SECOND, 0);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.MILLISECOND, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.MILLISECOND, 0);
		}

		long nextTime = cd.getTimeInMillis() + 24 * 60 * 60 * 1000;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return new Date(nextTime);
	}

	public static boolean isToday(Date date) {

		Calendar today = Calendar.getInstance();
		today.setTime(new Date());
		Calendar day = Calendar.getInstance();
		day.setTime(date);

		if (today.get(Calendar.YEAR) == day.get(Calendar.YEAR)
				&& today.get(Calendar.MONTH) == day.get(Calendar.MONTH)
				&& today.get(Calendar.DAY_OF_MONTH) == day.get(Calendar.DAY_OF_MONTH))
			return true;
		else
			return false;
	}

	public static String formatTimeStamp(Date date) {
		return timeStampFormat.format(date);
	}

	// public static Date parseTimeStamp(String date) throws AWOAException{
	// try {
	// return timeStampFormat.parse(date);
	// } catch (ParseException e) {
	// throw new AWOAException(AWOAException.SYS_ERROR, "日期格式不正确");
	// }
	//
	// }
	public static Date StringToDate(String sDate, int nFormat) {
		Date resDate = new Date();
		try {
			if (sDate == null || sDate.equals("")) {
				return null;
			}

			String sFormat = "";

			if (nFormat == 1) {
				sFormat = "yyyy-MM-dd";
			} else if (nFormat == 2) {
				sFormat = "yyyy-MM-dd HH:mm:ss";
			} else {
				sFormat = "yyyy-MM-dd";
			}

			SimpleDateFormat simDateFormat = new SimpleDateFormat(sFormat);
			resDate = simDateFormat.parse(sDate);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return resDate;
	}

	public static String DateToString(Date dDate, int nFormat) {
		String resString = "";
		String sDay = "";
		try {
			String sFormat = "";

			if (nFormat == 1) {
				sFormat = "yyyy-MM-dd";
			} else if (nFormat == 2) {
				sFormat = "yyyy-MM-dd HH:mm:ss";
			} else if (nFormat == 3) {
				sFormat = "yyyy-MM-dd EEE";
			} else {
				sFormat = "yyyy-MM-dd";
			}

			SimpleDateFormat simDateFormat = new SimpleDateFormat(sFormat);
			resString = simDateFormat.format(dDate);
		} catch (Exception e) {
			return resString;
		}
		return resString;
	}

	public static Date parseTimeStamp(String date) throws Exception {
		try {
			return timeStampFormat.parse(date);
		} catch (ParseException e) {
			throw new Exception("日期格式不正确");
		}

	}

	/**
	 * 一天中的天数
	 */
	public static long millionSecondsOfDay = 86400000;

	/**
	 * 得到两个日期之间的天数,两头不算,取出数据后，可以根据需要再加
	 * 
	 * @author Vincent
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getDay(Date date1, Date date2) {
		Long d2 = date2.getTime();
		Long d1 = date1.getTime();
		return (int) ((d2 - d1) / millionSecondsOfDay);
	}

	/**
	 * 计算日期加天数
	 * 
	 * @author Vincent
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDay(Date date, int days) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, days);
		return c.getTime();
	}

	/**
	 * 根据指定日期格式格式化日期
	 * 
	 * @author Vincent
	 * @param date
	 * @param formater
	 * @return
	 */
	public static String format(Date date, String formater) {
		SimpleDateFormat sdf = new SimpleDateFormat(formater);
		return sdf.format(date);
	}

	/**
	 * 格式化日期
	 * 
	 * @author Vincent
	 * @param date
	 * @param formater
	 * @return
	 */
	public static Date parse(String date, String formater) {
		SimpleDateFormat sdf = new SimpleDateFormat(formater);
		Date result = null;
		try {
			result = sdf.parse(date.trim());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据日期取出是星期几
	 * 
	 * @author Vincent
	 * @param date
	 *            Date
	 * @return int 返回1-7
	 */
	public static int getWeekOfDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return (calendar.get(Calendar.DAY_OF_WEEK) - 1) == 0 ? 7 : calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * format "yyyy-MM-dd HH:mm:ss"
	 */
	public static String dateToString(Date date, String format) {
		if (date == null)
			return null;
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date).trim();

	}

	public static void main(String[] args) {
		String date = DateUtil.DateToString(getDateTimeBeforeNow(DateUtil.DAY, 30), 1);
		System.out.println(date);
	}
}
