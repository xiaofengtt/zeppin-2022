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
public class OtherChargeType {
	
	public static String PERCREDIT = "PERCREDIT";

	public static String BOOKDISCOUNT = "BOOKDISCOUNT";
	
	public static String TESTPERCOURSE = "TESTPERCOURSE";
	
	public static String SITEINTO = "SITEINTO";		//教学站分成

	public static String OTHER = "OTHER";

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
		else
			throw new TypeErrorException("Charge Type error!");
	}

	public static List types() {
		ArrayList list = new ArrayList();
		list.add(PERCREDIT);
		list.add(SITEINTO);
		//list.add(BOOKDISCOUNT);
		//list.add(TESTPERCOURSE);
		//list.add(OTHER);
		return list;
	}

}
