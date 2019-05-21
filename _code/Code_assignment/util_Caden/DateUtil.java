package com.ldu.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @Description: Time formatted tool class
 */

public class DateUtil {

	/**
	 * Time format : year-month-day
	 */
	public static final String FORMAT1 = "yyyy-MM-dd";
	/**
	 * Time format: year-month-day hour: minute     24-hour system     HH lowercase is 12-hour system
	 */
	public static final String FORMAT2 = "yyyy-MM-dd HH:mm";
	/**
	 * Time format : Year - month - day hour: minute: second       24 hour system
	 */
	public static final String FORMAT3 = "yyyy-MM-dd HH:mm:ss";

	/**
	 * Get current date (year, month, day)
	 * @return
	 */
	public static String getNowDay(){
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT1);
		Date date = new Date();
		String _time = sdf.format(date);
		return _time;
	}

	/**
	 * Get current time (year-month-day  hour: minute)
	 * @return
	 */
	public static String getNowDate(){
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT2);
		Date date = new Date();
		String _time = sdf.format(date);
		return _time;
	}

	/**
	 * Get current time (year-month-day  hour: minute: second)
	 * @return
	 */
	public static String getNowTime(){
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT3);
		Date date = new Date();
		String _time = sdf.format(date);
		return _time;
	}

	/**
	 *According to the given date, get the day before the date(year-month-day).
	 * @param time
	 * @return
	 */
	public static String getDayBeginDate(long time){
		Long three = 24 * 60 * 60 * 1000l;
		Long threeDay = time - three;
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT1);
		Date threeTime = new Date(threeDay);
		String _time = sdf.format(threeTime);
		return _time;
	}

	/**
	 * Get the date three days in advance according to the given date (year-month-day)
	 * @param time
	 * @return
	 */
	public static String getThreeBeginDate(long time){
		Long three = 3 * 24 * 60 * 60 * 1000l;
		Long threeDay = time - three;
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT1);
		Date threeTime = new Date(threeDay);
		String _time = sdf.format(threeTime);
		return _time;
	}

	/**
	 * Get the date a month ago based on the given date(year-month-day)
	 * @param time
	 * @return
	 */
	public static String getMonthBeginDate(long time){
		Long month = 30 * 24 * 60 * 60 * 1000l;
		Long threeDay = time - month;
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT1);
		Date threeTime = new Date(threeDay);
		String _time = sdf.format(threeTime);
		return _time;
	}

	/**
	 * Get the time three days in advance according to the given time (year - month - day hour: minutes)
	 * @param time
	 * @return
	 */
	public static String getThreeDayBeginTime(long time){
		Long three = 3 * 24 * 60 * 60 * 1000l;
		Long threeDay = time - three;
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT3);
		Date threeTime = new Date(threeDay);
		String _time = sdf.format(threeTime);
		return _time;
	}

	/**
	 * Get the time ten days ago according to the given time (year - month - day time: minutes: seconds)
	 * @param time
	 * @return
	 */
	public static String getTenBeginTime(long time){
		Long three = 10*24 * 60 * 60 * 1000l;
		Long threeDay = time - three;
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT3);
		Date threeTime = new Date(threeDay);
		String _time = sdf.format(threeTime);
		return _time;
	}

	/**
	 * Get time of 10 days according to the given time.
	 * @param time
	 * @return
	 */
	public static Date getDateSecond(long time){
		Long month = 10 * 1000l;
		Long threeDay = time - month;
		Date threeTime = new Date(threeDay);
		return threeTime;
	}

	/**
	 * According to the given time to get three days before the time.
	 * @param time
	 * @return
	 */
	public static Date getThreeDayBeginTime(Date time){
		Long three = 3 * 24 * 60 * 60 * 1000l;
		Long threeDay = time.getTime() - three;
		Date threeTime = new Date(threeDay);
		return threeTime;
	}
	/**
	 * Formatting time, remove the back 0
	 * @param date
	 * @return
	 */
	public static String formatTimeNew(Date date){
		if(date == null)
			return null;

		String checkTime = String.valueOf(date);

		if(checkTime != null && !"".equals(checkTime) && checkTime.length()>19){
			checkTime = checkTime.substring(0, 19);
		}
		return checkTime;
	}

	/**
	 * Format time, keep to minutes.
	 * @param date
	 * @return
	 */
	public static String formatTime(Date date){
		if(date == null)
			return null;

		String checkTime = String.valueOf(date);

		if(checkTime != null && !"".equals(checkTime) && checkTime.length()>19){
			checkTime = checkTime.substring(0, 16);
		}

		return checkTime;
	}

	/**
	 * Format time, get date.
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date){
		if(date == null)
			return null;

		String checkTime = String.valueOf(date);

		if(checkTime != null && !"".equals(checkTime) && checkTime.length()>19){
			checkTime = checkTime.substring(0, 10);
		}
		return checkTime;
	}

	/**
	 * Format time, get date
	 * @param date
	 * @return
	 */
	public static String formatDate(String date){
		if(date == null)
			return null;

		String checkTime = String.valueOf(date);

		if(checkTime != null && !"".equals(checkTime) && checkTime.length()>19){
			checkTime = checkTime.substring(0, 10);
		}
		return checkTime;
	}

	/**
	 * Format time, keep to second
	 * @param date
	 * @return
	 */
	public static String formatTime(String date){
		if(date == null)
			return null;

		String checkTime = String.valueOf(date);

		if(checkTime != null && !"".equals(checkTime) && checkTime.length()>19){
			checkTime = checkTime.substring(0, 19);
		}
		return checkTime;
	}


	/**
	 * Get n days after the given time
	 * @param time
	 * @param lastTime
	 * @return
	 */
	public static String getLastTime(String time,int lastTime){
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT1);
		Date date;
		String newTime = time;
		try {
			date = sdf.parse(time);
			Long lastTimeDay = lastTime * 24 * 60 * 60 * 1000l;
			Long lastDay = date.getTime() + lastTimeDay;
			Date newDate = new Date(lastDay);
			newTime = sdf.format(newDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newTime;
	}
}