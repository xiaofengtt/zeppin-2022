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
	
	public static String SITEINTO = "SITEINTO";		//��ѧվ�ֳ�

	public static String OTHER = "OTHER";
	
	public static String TOTAL_XUEFEE = "TOTAL_XUEFEE";//Ӧ��ѧ���ܶ�
	
	public static String TOTAL_XUEFEN = "TOTAL_XUEFEN";//��ѧ��
	
	public static String XUEFEE_BY_TIME = "XUEFEE_BY_TIME";//ÿ�νɷѽ��

	public static String typeShow(String type) throws TypeErrorException {
		if (type != null && type.equals(PERCREDIT))
			return "ÿѧ�ַ���";
		else if (type != null && type.equals(SITEINTO))
			return "��ѧվ�ֳ�";
		else if (type != null && type.equals(BOOKDISCOUNT))
			return "�̲��ۿ۱���";
		else if (type != null && type.equals(TESTPERCOURSE))
			return "ÿ�ſγ̿��Է���";		
		else if (type != null && type.equals(OTHER))
			return "��������";
		else if (type != null && type.equals(TOTAL_XUEFEE))
			return "Ӧ��ѧ���ܶ�";
		else if (type != null && type.equals(TOTAL_XUEFEN))
			return "��ѧ��";
		else if (type != null && type.equals(XUEFEE_BY_TIME))
			return "ÿ�νɷѽ��";
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
