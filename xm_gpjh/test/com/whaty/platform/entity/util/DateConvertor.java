package com.whaty.platform.entity.util;

public class DateConvertor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String issueDate = "2009-03-05";
		System.out.println(formatStr(issueDate));
	}

	/**
	 * 格式化日期
	 * 
	 * @param str
	 *            源字符串中的字符
	 * @return
	 */
	public static String formatStr(String str) {
		StringBuffer sb = new StringBuffer();
		int pos1 = str.indexOf("-");
		int pos2 = str.lastIndexOf("-");

		for (int i = 0; i < 4; i++) {
			sb.append(formatDigit(str.charAt(i)));
		}
		sb.append("年");
		if (getMidLen(str, pos1, pos2) == 1) {
			sb.append(formatDigit(str.charAt(5)) + "月");

			if (str.charAt(7) != '0') {
				if (getLastLen(str, pos2) == 1) {
					sb.append(formatDigit(str.charAt(7)) + "日");
				}
				if (getLastLen(str, pos2) == 2) {
					if (str.charAt(7) != '1' && str.charAt(8) != '0') {
						sb.append(formatDigit(str.charAt(7)) + "十"
								+ formatDigit(str.charAt(8)) + "日");
					} else if (str.charAt(7) != '1' && str.charAt(8) == '0') {
						sb.append(formatDigit(str.charAt(7)) + "十日");
					} else if (str.charAt(7) == '1' && str.charAt(8) != '0') {
						sb.append("十" + formatDigit(str.charAt(8)) + "日");
					} else {
						sb.append("十日");
					}
				}
			} else {
				sb.append(formatDigit(str.charAt(8)) + "日");
			}
		}
		if (getMidLen(str, pos1, pos2) == 2) {
			if (str.charAt(5) != '0' && str.charAt(6) != '0') {
				sb.append("十" + formatDigit(str.charAt(6)) + "月");

				if (getLastLen(str, pos2) == 1) {
					sb.append(formatDigit(str.charAt(8)) + "日");
				}
				if (getLastLen(str, pos2) == 2) {
					if (str.charAt(8) != '0') {
						if (str.charAt(8) != '1' && str.charAt(9) != '0') {
							sb.append(formatDigit(str.charAt(8)) + "十"
									+ formatDigit(str.charAt(9)) + "日");
						} else if (str.charAt(8) != '1' && str.charAt(9) == '0') {
							sb.append(formatDigit(str.charAt(8)) + "十日");
						} else if (str.charAt(8) == '1' && str.charAt(9) != '0') {
							sb.append("十" + formatDigit(str.charAt(9)) + "日");
						} else {
							sb.append("十日");
						}
					} else {
						sb.append(formatDigit(str.charAt(9)) + "日");
					}
				}
			} else if (str.charAt(5) != '0' && str.charAt(6) == '0') {
				sb.append("十月");
				if (getLastLen(str, pos2) == 1) {
					sb.append(formatDigit(str.charAt(8)) + "日");
				}
				if (getLastLen(str, pos2) == 2) {
					if (str.charAt(8) != '0') {
						if (str.charAt(8) != '1' && str.charAt(9) != '0') {
							sb.append(formatDigit(str.charAt(8)) + "十"
									+ formatDigit(str.charAt(9)) + "日");
						} else if (str.charAt(8) != '1' && str.charAt(9) == '0') {
							sb.append(formatDigit(str.charAt(8)) + "十日");
						} else if (str.charAt(8) == '1' && str.charAt(9) != '0') {
							sb.append("十" + formatDigit(str.charAt(9)) + "日");
						} else {
							sb.append("十日");
						}
					} else {
						sb.append(formatDigit(str.charAt(9)) + "日");
					}
				}
			} else {
				sb.append(formatDigit(str.charAt(6)) + "月");

				if (getLastLen(str, pos2) == 1) {
					sb.append(formatDigit(str.charAt(8)) + "日");
				}
				if (getLastLen(str, pos2) == 2) {
					if (str.charAt(8) != '0') {
						if (str.charAt(8) != '1' && str.charAt(9) != '0') {
							sb.append(formatDigit(str.charAt(8)) + "十"
									+ formatDigit(str.charAt(9)) + "日");
						} else if (str.charAt(8) != '1' && str.charAt(9) == '0') {
							sb.append(formatDigit(str.charAt(8)) + "十日");
						} else if (str.charAt(8) == '1' && str.charAt(9) != '0') {
							sb.append("十" + formatDigit(str.charAt(9)) + "日");
						} else {
							sb.append("十日");
						}
					} else {
						sb.append(formatDigit(str.charAt(9)) + "日");
					}
				}
			}
		}

		return sb.toString();
	}

	/**
	 * 将源字符串中的阿拉伯数字格式化为汉字
	 * 
	 * @param sign
	 *            源字符串中的字符
	 * @return
	 */
	public static char formatDigit(char sign) {
		if (sign == '0')
			sign = '○';
		if (sign == '1')
			sign = '一';
		if (sign == '2')
			sign = '二';
		if (sign == '3')
			sign = '三';
		if (sign == '4')
			sign = '四';
		if (sign == '5')
			sign = '五';
		if (sign == '6')
			sign = '六';
		if (sign == '7')
			sign = '七';
		if (sign == '8')
			sign = '八';
		if (sign == '9')
			sign = '九';

		return sign;
	}

	/**
	 * 获得月份字符串的长度
	 * 
	 * @param str
	 *            待转换的源字符串
	 * @param pos1
	 *            第一个"-"的位置
	 * @param pos2
	 *            第二个"-"的位置
	 * @return
	 */
	public static int getMidLen(String str, int pos1, int pos2) {
		return str.substring(pos1 + 1, pos2).length();
	}

	/**
	 * 获得日期字符串的长度
	 * 
	 * @param str
	 *            待转换的源字符串
	 * @param pos2
	 *            第二个"-"的位置
	 * @return
	 */
	public static int getLastLen(String str, int pos2) {
		return str.substring(pos2 + 1).length();
	}
}