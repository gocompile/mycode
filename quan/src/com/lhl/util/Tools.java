package com.lhl.util;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Tools {
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {

			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {

			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	// MD5加密
	public static String encodeByMD5(String originString) {

		if (originString != null) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] results = md.digest(originString.getBytes());
				String resultString = byteArrayToHexString(results);
				return resultString.toUpperCase();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	private static String byteArrayToHexString(byte[] b) {

		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {

		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	// 时间对比
	public static boolean BalanceDate(String day1, String day2)
			throws Exception {

		boolean flag = true;
		DateFormat df = DateFormat.getDateInstance();
		try {
			flag = df.parse(day1).before(df.parse(day2));
		} catch (ParseException e) {
			throw e;
		}
		return flag;
	}

	public static String clearHtml(String str) {

		if (isNotEmpty(str)) {
			return str.replaceAll("<[.[^<]]*>", "").replaceAll("[\\n|\\r]", "")
					.replaceAll("&nbsp;", "");
		} else {
			return str;
		}
	}

	public static String formateHtml(String html) {

		html = html.replaceAll(" ", "&nbsp;");
		html = html.replaceAll("<", "&lt;");
		html = html.replaceAll("\n", "<br>");
		return html;
	}

	// 获取字符长度
	public static int getRealLength(String str) {

		int len = 0;
		String[] arrayVal = str.split("");
		for (int i = 1; i < arrayVal.length; i++) {
			if (arrayVal[i].matches("[^\\x00-\\xff]")) // 全角
				len += 2;
			else
				len += 1;
		}
		return len;
	}

	/**
	 * 判断不为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {

		if (null != str && !"".equals(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {

		if (null == str || "".equals(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 以友好的方式显示时间
	 * 
	 * @param sdate
	 * @return
	 */
	public static String friendly_time(String sdate) {

		if (isEmpty(sdate)) {
			return "";
		}
		Date time = toDate(sdate);
		if (time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();

		// 判断是否是同一天
		String curDate = dateFormater2.get().format(cal.getTime());
		String paramDate = dateFormater2.get().format(time);
		if (curDate.equals(paramDate)) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "分钟前";
			else
				ftime = hour + "小时前";
			return ftime;
		}

		long lt = time.getTime() / 86400000;
		long ct = cal.getTimeInMillis() / 86400000;
		int days = (int) (ct - lt);
		if (days == 0) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "分钟前";
			else
				ftime = hour + "小时前";
		} else if (days == 1) {
			ftime = "昨天";
		} else if (days == 2) {
			ftime = "前天";
		} else if (days > 2 && days <= 10) {
			ftime = days + "天前";
		} else if (days > 10) {
			ftime = dateFormater2.get().format(time);
		}
		return ftime;
	}

	/**
	 * 判断给定字符串时间是否为今日
	 * 
	 * @param sdate
	 * @return boolean
	 */
	public static boolean isToday(String sdate) {

		boolean b = false;
		Date time = toDate(sdate);
		Date today = new Date();
		if (time != null) {
			String nowDate = dateFormater2.get().format(today);
			String timeDate = dateFormater2.get().format(time);
			if (nowDate.equals(timeDate)) {
				b = true;
			}
		}
		return b;
	}

	/**
	 * 将字符串转位日期类型
	 * 
	 * @param sdate
	 * @return
	 */
	public static Date toDate(String sdate) {

		try {
			return dateFormater.get().parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String pathRelative2Absolutely(String con) {
		if (null != con) {
			con = con.replace("../", Constant.WEBSTIE);
			return con;
		} else {
			return "";
		}

	}
}
