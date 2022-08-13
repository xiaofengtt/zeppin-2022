package com.whaty.platform.entity.web.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlDom4J {
	public String chinaToUnicode(String str) {
		String result = "";
		for (int i = 0; i < str.length(); i++) {
			int chr1 = (char) str.charAt(i);
			result += "\\u" + Integer.toHexString(chr1);
		}
		return result;
	}

	public static void main(String[] args) {
		XmlDom4J test = new XmlDom4J();
		System.out.println(test.chinaToUnicode("教材"));

//		String str = "11111111111111";
//		String reg = "^(\\d{11})$|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$";;
//		System.out.println(str.matches(reg));
			
//		test.unicodeToChinese("\u6279\u91cf\u6dfb\u52a0");
//		System.out.println(test.checkCardNoBirthday("131684198502280379", "1985-02-28"));
//		List<String> list = new ArrayList<String>();
//		list.add("1");
//		list.add("2");
//		System.out.println(list.remove("2"));
//		for (String string : list) {
//			System.out.println(string+"<<<<<<<<");
//		}
	}

	public boolean checkCardNoBirthday(String cardNo , String birthday){
		String verify18PatternStr = "(\\d{6})" + "(\\d{8})" + "(\\d{3})" + "([\\d[xX]]{1})";
		Pattern verify18Pattern = Pattern.compile(verify18PatternStr);
		String verify15PatternStr = "(\\d{6})" + "(\\d{6})" + "(\\d{3})";
		Pattern verify15Pattern = Pattern.compile(verify15PatternStr);
		Matcher m = null;
		String birthdayNum = "";
		if(cardNo.length()==15){
		    m = verify15Pattern.matcher(cardNo);
		 
		} else{
			  m = verify18Pattern.matcher(cardNo);

		}
	    if(m.matches()==false) {
		      return false;
		}
		birthdayNum = m.group(2);
		   String FullBirthdayNum = null;
		    if(cardNo.length()==15)//在生日号码前加“19”
		      FullBirthdayNum = "19" + birthdayNum;
		    else
		      FullBirthdayNum = birthdayNum;
		 
		    String year = FullBirthdayNum.substring(0, 4);
		    String month = FullBirthdayNum.substring(4, 6);
		    String date = FullBirthdayNum.substring(6, 8);
		    String str = year + "-" + month + "-" + date;
		    if(birthday.equals(str)){
		    	return true;
		    }else{
		    	return false;
		    }
	}
	public void unicodeToChinese(String str) {

		for (char c : str.toCharArray()) {
			System.out.print(c);
		}
	}

}
