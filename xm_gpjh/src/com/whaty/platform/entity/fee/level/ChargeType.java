/**
 * 
 */
package com.whaty.platform.entity.fee.level;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.TypeErrorException;

/**
 * @author chenjian
 *
 */
public class ChargeType {
	
	public static String PERCREDIT = "PERCREDIT";

	public static String BOOKDISCOUNT = "BOOKDISCOUNT";
	
	public static String TESTPERCOURSE = "TESTPERCOURSE";
	
	public static String SITEINTO = "SITEINTO";		//教学站分成

	public static String OTHER = "OTHER";
	
	public static String TOTAL_XUEFEE = "TOTAL_XUEFEE";//应缴学费总额
	
	public static String TOTAL_XUEFEN = "TOTAL_XUEFEN";//总学分
	
	public static String XUEFEE_BY_TIME = "XUEFEE_BY_TIME";//每次缴费金额

	public static String typeShow(String type) throws TypeErrorException {
		if (type != null && type.equals(PERCREDIT))
			return "每学分费用";
		else if (type != null && type.equals(SITEINTO))
			return "教学站分成";
		else if (type != null && type.equals(BOOKDISCOUNT))
			return "教材折扣比例";
		else if (type != null && type.equals(TESTPERCOURSE))
			return "每门课程考试费用";		
		else if (type != null && type.equals(OTHER))
			return "其他费用";
		else if (type != null && type.equals(TOTAL_XUEFEE))
			return "应缴学费总额";
		else if (type != null && type.equals(TOTAL_XUEFEN))
			return "总学分";
		else if (type != null && type.equals(XUEFEE_BY_TIME))
			return "每次缴费金额";
		else
			throw new TypeErrorException("Charge Type error!");
	}

	public static List types() {
		ArrayList list = new ArrayList();
		list.add(PERCREDIT);
		list.add(SITEINTO);
		list.add(TOTAL_XUEFEE);
		list.add(TOTAL_XUEFEN);
		list.add(XUEFEE_BY_TIME);
		//list.add(BOOKDISCOUNT);
		//list.add(TESTPERCOURSE);
		//list.add(OTHER);
		return list;
	}

}
