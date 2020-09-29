package org.fullstacklearning.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static boolean IsInPayPeriod(Date theDate, Date startDate,
			Date endDate) {
		//return (theDate.after(startDate)) && (theDate.before(endDate));
		return theDate.getTime()>=getBeginOfDay(startDate).getTime()
				&& theDate.getTime()<=getEndOfDay(endDate).getTime();
	}
	
	private static Date getBeginOfDay(Date d) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(d);
		ca.set(Calendar.HOUR,0);
		ca.set(Calendar.MINUTE,0);
		ca.set(Calendar.SECOND,0);
		return ca.getTime();
	}
	
	private static Date getEndOfDay(Date d) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(d);
		ca.set(Calendar.HOUR,23);
		ca.set(Calendar.MINUTE,59);
		ca.set(Calendar.SECOND,59);
		return ca.getTime();
	}
	
	public static Date getDate(int year, int month, int day) {
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.YEAR, year);
		ca.set(Calendar.MONTH, month-1);
		ca.set(Calendar.DAY_OF_MONTH, day);
		return ca.getTime();
	}
	
	public static String getYearMonthDayString(Date d) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(d);
		return ""+ca.get(Calendar.YEAR)+ca.get(Calendar.MONTH)+ca.get(Calendar.DAY_OF_MONTH);
	}
}
