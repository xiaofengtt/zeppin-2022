package enfo.crm.cash;

import java.math.BigDecimal;
import java.text.NumberFormat;

import enfo.crm.tools.Utility;

/**
 *  
 * @author Felix
 * @since 2008-5-20
 * @version 1.0
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */

public class Format
{

	public static String formatInt(long value)
	{
		return NumberFormat.getInstance().format(value);
	}
	
	public static String formatDouble(BigDecimal bd){
		if(bd==null){
			return "";
		}
		return formatMoneyFloat(bd.doubleValue());		
	}
	
	public static String formatMoneyFloat(double value)
	{
		NumberFormat nf = NumberFormat.getInstance();	
		nf.setMaximumFractionDigits(4);//tanhong 将3改为2位小数
		nf.setMinimumFractionDigits(0);
		return nf.format(value);
	}
		
	public static String formatMoney(double value)
	{
		return formatMoney(value, 2);
		//NumberFormat nf = NumberFormat.getInstance();
		//nf.setMaximumFractionDigits(2);
		//nf.setMinimumFractionDigits(2);
		//nf.setGroupingUsed(false);
		//return nf.format(value);
	}
	public static String formatMoney0(double value)
		{
			return formatMoney(value, 0);
			//NumberFormat nf = NumberFormat.getInstance();
			//nf.setMaximumFractionDigits(2);
			//nf.setMinimumFractionDigits(2);
			//nf.setGroupingUsed(false);
			//return nf.format(value);
		}
	public static String formatMoneyPrint(double value, int digit)
	{
		NumberFormat nf = NumberFormat.getInstance();
		
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(digit);
		nf.setGroupingUsed(false);
		return nf.format(value);
	}

	public static String formatMoney(double value, int digit)
	{
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(4);
		nf.setMinimumFractionDigits(digit);
		nf.setGroupingUsed(true);
		return nf.format(value);
	}

    public static String formatMoney2(java.math.BigDecimal value)
    {
        if (value == null)
            return "";
        if (value.doubleValue()==0)
            return "0.00";
        return formatMoney(value.doubleValue(), 2);
    }
    
	public static String formatMoney(java.math.BigDecimal value)
	{
		if (value == null)
			return "";
		if (value.doubleValue()==0)
			return "";
		return formatMoney(value.doubleValue(), 2);
	}
	public static String formatMoney0(java.math.BigDecimal value)
		{
			if (value == null)
				return "";
			if (value.doubleValue()==0)
				return "";
			return formatMoney(value.doubleValue(), 0);
		}

	public static String formatMoney(Integer value)
	{
		if (value == null)
			return "";
		return formatMoney(value.doubleValue(), 2);
	}

	public static String formatMoneyEx(double value)
	{
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(8);
		nf.setMinimumFractionDigits(4);
		return nf.format(value);
	}

	public static String formatMoney(
		double value,
		int maxDigitLength,
		int minDigitLength,
		int maxPointLength,
		int minPointLength)
	{
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(maxPointLength);
		nf.setMinimumFractionDigits(minPointLength);
		nf.setMaximumIntegerDigits(maxDigitLength);
		nf.setMinimumIntegerDigits(minDigitLength);
		return nf.format(value);
	}

	public static String formatPercent(double value, int digit)
	{
		
		return formatPercent(new java.math.BigDecimal(value),1,false);
	}

	public static String formatPercent(java.math.BigDecimal value, int type, boolean bUnit)
	{
		if (value == null)
			return "";
		if(value.compareTo(new BigDecimal(0))==0)
			return "";
				
		StringBuffer buff = new StringBuffer();
		java.math.BigDecimal returnvalue=new java.math.BigDecimal(0);
		String srevalue="";
		
		switch (type)
		{
			case 0 : //月利率
			{
				returnvalue=value.multiply(new java.math.BigDecimal(1000)).setScale(6, BigDecimal.ROUND_CEILING);
				srevalue=formatDouble(returnvalue);
				
				buff.append(srevalue+ "‰");//去掉.setScale(6, BigDecimal.ROUND_CEILING)
				if (bUnit)
					buff.append("(月)");
				break;
			}	
			case 1 : //年利率
			{
				returnvalue=value.multiply(new java.math.BigDecimal(100)).setScale(6, BigDecimal.ROUND_CEILING);
				srevalue=formatDouble(returnvalue);
				
				buff.append(srevalue + "%");
				if (bUnit)
					buff.append("(年)");
				break;
			}	
			case 2 : //日利率
			{
				returnvalue=value.multiply(new java.math.BigDecimal(10000)).setScale(6, BigDecimal.ROUND_CEILING);
				srevalue=formatDouble(returnvalue);
				buff.append(srevalue + "<img border=\"0\" src=\"/images/wan.gif\" width=\"22\" height=\"12\">");
				if (bUnit)
					buff.append("(日)");
				break;
			}	
		}
		return Utility.trimZero(buff.toString());
	}

	public static String formatPercent(
		double value,
		int maxDigitLength,
		int minDigitLength,
		int maxPointLength,
		int minPointLength)
	{
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMaximumFractionDigits(maxPointLength);
		nf.setMaximumIntegerDigits(maxDigitLength);
		nf.setMinimumFractionDigits(minPointLength);
		nf.setMinimumIntegerDigits(minDigitLength);
		return Utility.trimZero(nf.format(value));
	}

	// 格式化日期
	public static String formatDate(int date)
	{
		if (date <= 19000000)
			return "";
		StringBuffer strTime = new StringBuffer(15);
		String strDate = Integer.toString(date);
		String strYear = strDate.substring(0, 4);
		strTime.append(strYear);
		strTime.append("/");
		String strMonth = strDate.substring(4, 6);
		strTime.append(strMonth);
		strTime.append("/");
		String str = strDate.substring(6, 8);
		strTime.append(str);
		return strTime.toString();
	}
	//格式化日期
	 public static String formatDateLine(int date)
	 {
		 if (date <= 19000000)
			 return "";
		 StringBuffer strTime = new StringBuffer(15);
		 String strDate = Integer.toString(date);
		 String strYear = strDate.substring(0, 4);
		 strTime.append(strYear);
		 strTime.append("-");
		 String strMonth = strDate.substring(4, 6);
		 strTime.append(strMonth);
		 strTime.append("-");
		 String str = strDate.substring(6, 8);
		 strTime.append(str);
		 return strTime.toString();
	 }
	public static String formatDateLine(Integer idate) {
			int date = 19000000;
			if (idate != null)
				date = idate.intValue();

			if (date <= 19000000)
				return "";
			StringBuffer strTime = new StringBuffer(15);
			String strDate = Integer.toString(date);
			String strYear = strDate.substring(0, 4);
			strTime.append(strYear);
			strTime.append("-");
			String strMonth = strDate.substring(4, 6);
			strTime.append(strMonth);
			strTime.append("-");
			String str = strDate.substring(6, 8);
			strTime.append(str);
			return strTime.toString();
		}
//	格式化日期
	 public static String formatDateRQ(int date)
	 {
		 if (date <= 19000000)
			 return "";
		 StringBuffer strTime = new StringBuffer(15);
		 String strDate = Integer.toString(date);
		 
		 String strMonth = strDate.substring(4, 6);
		 strTime.append(strMonth);
		 String str = strDate.substring(6, 8);
		 strTime.append(str);
		 return strTime.toString();
	 }
	// 中文格式化日期
	public static String formatDateCn(Integer date)
	{
		if (date == null)
			return "";
			
		return formatDateLine(date);
	}

	// 中文格式化日期
	public static String formatDateCn(int date)
	{
		if (date <= 19000000)
			return "";
		StringBuffer strTime = new StringBuffer(15);
		String strDate = Integer.toString(date);
		String strYear = strDate.substring(0, 4);
		strTime.append(strYear);
		strTime.append("年");
		String strMonth = strDate.substring(4, 6);
		strTime.append(strMonth);
		strTime.append("月");
		String str = strDate.substring(6, 8);
		strTime.append(str);
		strTime.append("日");
		return strTime.toString();
	}

	// 中文格式化日期
	public static String formatDateCn(java.util.Date date)
	{
		if (date == null)
			return "";
		return formatDateCn(Utility.getDateInt(date));
	}

	// 中文格式化日期时间
	public static String formatDatetimeCn(java.sql.Timestamp time)
	{
		if (time == null)
			return "";
		String s = time.toString();
		int i = s.indexOf(".");
		if (i > 0)
			s = s.substring(0, i);
		return s;
	}

	//美式格式化日期
	public static String formatDateUS(Integer date)
	{
		if (date == null)
			return "";
		java.util.Calendar b = java.util.Calendar.getInstance();
		b.set(
			Utility.getDatePart(date.intValue(), 1),
			Utility.getDatePart(date.intValue(), 2),
			Utility.getDatePart(date.intValue(), 3));
		java.util.Date stdate = stdate = b.getTime();

		java.text.DateFormat a = java.text.DateFormat.getDateInstance(java.text.DateFormat.LONG, java.util.Locale.US);
		return a.format(stdate);
	}

	// 中文格式化日期时间
	public static String formatDateCn(java.sql.Timestamp time)
	{
		if (time == null)
			return "";
		
		return formatDateCn(Utility.getDateInt(new java.util.Date(time.getTime())));
	}
	
	//格式化凭证编号
	public static String formatPz(String value)
	{
		int strlen;
		String temp = "0000";		
		StringBuffer item3 = new StringBuffer(4);
		strlen = value.length();		
		item3.append(temp.substring(0,4-strlen));
		item3.append(value);
		return item3.toString();		
	}

	// 功能 :　将字符串按HTML方式编码
	// 输入 :  标准字符串文本
	// 输出 :  HTML文本
	public static String formatHtml(String sourcestr)
	{
		int strlen;
		String restring = "", destr = "";
		strlen = sourcestr.length();
		for (int i = 0; i < strlen; i++)
		{
			char ch = sourcestr.charAt(i);
			switch (ch)
			{
				case '<' :
					destr = "&lt;";
					break;
				case '>' :
					destr = "&gt;";
					break;
				case '\"' :
					destr = "&quot;";
					break;
				case '&' :
					destr = "&amp;";
					break;
				case 13 :
					destr = "<br>";
					break;
				case 32 :
					destr = "&nbsp;";
					break;
				default :
					destr = "" + ch;
					break;
			}
			restring = restring + destr;
		}
		return restring;
	}
	
	/**
	 * 2007/12/13 lzhd 把Integer类型的合同编号按照长度6格式化为形如“0000**”的字符串
	 * @param value
	 * @return
	 */
	public static String formatContract_bh(Integer value){
		
		int strlen;
		String temp = "000000";		
		StringBuffer item3 = new StringBuffer(6);
		strlen = value.toString().length();		
		item3.append(temp.substring(0,6-strlen));
		item3.append(value);
		return item3.toString();		

	}
	public static String formatMoneyD(String source){
		if(source != null){
		
			StringBuffer sbu = new StringBuffer();
			int lenOfsource=source.length();
			int i;
			int posStart;
		            
			for(posStart=0;(i=source.indexOf(",",posStart))>=0;posStart=i+1)
			{
				sbu.append(source.substring(posStart,i));
			}
			if(posStart<lenOfsource)
			{
				sbu.append(source.substring(posStart));
			}
	  
			return sbu.toString();
		}else{
			return "";
		}	
	}

    public static String formatBankNo(String bankNo){
        StringBuffer sb = new StringBuffer();
        
        if(bankNo!=null){
            while(bankNo.length()>4){
                sb.append(bankNo.substring(0,4)).append(" ");
                bankNo = bankNo.substring(4);
            }
            sb.append(bankNo);
        }
        
        return sb.toString();
    }
    //金额转成中文
	public static String formatMoneyCN(java.math.BigDecimal value){
		String str = "";
		String number[] = new String[]{"零 ","壹 ","贰 ","叁 ","肆 ","伍 ","陆 ","柒 ","捌 ","玖 " };
		String units[] = new String[]{"元","拾","佰","仟","万","拾","佰","仟","亿","拾","佰","仟",};
		double temp = value.doubleValue();
		int i = 0;
		//整数部分
		while(temp >= 10){
			int n = (int)(temp % 10);
			temp = temp / 10L;
			str = str.trim()+units[i++].trim()+number[n].trim();
		}
		str = str.trim()+units[i++].trim()+number[(int)temp].trim();
		StringBuffer sb = new StringBuffer(str.trim());
		sb.reverse();
		str = sb.toString();
		//小数部分
		int dolt1 = (int)( value.doubleValue()*100%10);//分
		int dolt2 = ((int)( value.doubleValue()*100%100))/10;//角
		if(dolt1 == 0 && dolt2 == 0){
			str = str +"整";
		}else if(dolt1 != 0){
			if(dolt2 != 0) 
				str = str + number[dolt2].trim()+"角"+number[dolt1].trim()+"分";
			else 
				str = str + "零"+number[dolt1].trim()+"分";
		}else if(dolt2 != 0){
			str = str + number[dolt2].trim()+"角";
		}
		//去除多余的汉字并在合适的位置加上零
		while(str.indexOf("零亿")!= -1 || str.indexOf("零万")!= -1 || str.indexOf("零仟")!= -1 || str.indexOf("零佰")!= -1 || str.indexOf("零拾")!= -1 ||str.indexOf("零元")!= -1 ){
			str = str.replaceAll("零亿", "亿");
			str = str.replaceAll("零万", "万");
			str = str.replaceAll("零仟", "零");
			str = str.replaceAll("零佰", "零");
			str = str.replaceAll("零拾", "零");
			if(str.startsWith("零元")) 
				str = str.replaceAll("零元", "");
			else 
				str = str.replaceAll("零元", "元");
		}
		if(str.indexOf("亿万") != -1){
			str = str.replaceAll("亿万", "亿零");
		}
		if(str.indexOf("万仟") != -1){
			str = str.replaceAll("万仟", "万零");
		}
		String req = ".*佰亿.仟.*";
		if(str.matches(req)){
			str = str.replaceAll(str.substring(str.indexOf("佰亿")+2,str.indexOf("仟")), "零"+str.substring(str.indexOf("佰亿")+2,str.indexOf("仟")));
		}
		req = ".*拾亿.仟.*";
		if(str.matches(req)){
			str = str.replaceAll(str.substring(str.indexOf("拾亿")+2,str.indexOf("仟")), "零"+str.substring(str.indexOf("拾亿")+2,str.indexOf("仟")));
		}
		req = ".*佰万.仟.*";
		if(str.matches(req)){
			str = str.replaceAll(str.substring(str.indexOf("佰万")+2,str.indexOf("仟")), "零"+str.substring(str.indexOf("佰万")+2,str.indexOf("仟")));
		}
		req = ".*拾万.仟.*";
		if(str.matches(req)){
			str = str.replaceAll(str.substring(str.indexOf("拾万")+2,str.indexOf("仟")), "零"+str.substring(str.indexOf("拾万")+2,str.indexOf("仟")));
		}
		if(str.indexOf("零零") != -1){
			str = str.replaceAll("零零", "零");
		}
		return str;
	} 
	 
/*	public static void main(String[] args)
	{

	}*/
}
