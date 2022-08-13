/**
 * 
 */
package com.zeppin.util.cryptogram;

/**
 * @author Administrator
 * 
 */
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

//import org.jboss.util.Null;

public class generatePwd implements IGeneratePwd {
	/*
	 * （非 Javadoc）
	 * 
	 * @see com.zeppin.util.cryptogram.IGeneratePwd#getPwd()
	 */
	@Override
	public String getPwd() throws InterruptedException {
		// TODO 自动生成的方法存根
		String pwdString = null;
		Thread.sleep(1);// 防止运算过快产生相同密码
		pwdString = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());

		return pwdString;

		// YY = pwdString.substring(0, 2);
		// MM = pwdString.substring(2, 4);
		// DD = pwdString.substring(4, 6);
		// hh = pwdString.substring(6, 8);
		// min = pwdString.substring(8, 10);
		// sec = pwdString.substring(10, 12);
		// Msec = pwdString.substring(12);
		// // 做乱序排列
		// pwdString = Msec.substring(0, 1) + DD + Msec.substring(1, 2) + MM
		// + Msec.substring(2, 3) + YY + hh + min + sec;
		//
		// return pwdString;
	}

	@Override
	public String getSecString(String pwdString) throws Exception {

		String YY = null;// 年份两位
		String MM = null;// 月份两位
		String DD = null;// 日两位
		String hh = null;// 小时两位
		String min = null;// 分钟两位
		String sec = null;// 秒两位
		String Msec = null;// 毫秒三位

		YY = pwdString.substring(0, 2);
		MM = pwdString.substring(2, 4);
		DD = pwdString.substring(4, 6);
		hh = pwdString.substring(6, 8);
		min = pwdString.substring(8, 10);
		sec = pwdString.substring(10, 12);
		Msec = pwdString.substring(12);
		Random random = new Random();
		// 做乱序排列
		String pwd = Msec.substring(0, 1) + DD + Msec.substring(1, 2) + MM + Msec.substring(2, 3) + YY + hh + min + sec + random.nextInt(10);

		return pwd;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * com.zeppin.util.cryptogram.IGeneratePwd#getOralString(java.lang.String)
	 */
	@Override
	public String getOralString(String pwd) throws Exception {
		// TODO 自动生成的方法存根
		String oraString = null;
		String YY = null;// 年份两位
		String MM = null;// 月份两位
		String DD = null;// 日两位
		String hh = null;// 小时两位
		String min = null;// 分钟两位
		String sec = null;// 秒两位
		String Msec = null;// 毫秒三位

		if (pwd.trim().length() == 16) {
			// 恢复正常顺序
			pwd = pwd.substring(0, 15);
			Msec = pwd.substring(0, 1) + pwd.substring(3, 4) + pwd.substring(6, 7);
			DD = pwd.substring(1, 3);
			MM = pwd.substring(4, 6);
			YY = pwd.substring(7, 9);
			hh = pwd.substring(9, 11);
			min = pwd.substring(11, 13);
			sec = pwd.substring(13);
			oraString = YY + MM + DD + hh + min + sec + Msec;
		}

		return oraString;

	}

}
