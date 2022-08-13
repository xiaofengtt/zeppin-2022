/**
 * 
 */
package com.whaty.platform.logmanage;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.TypeErrorException;

/**
 * @author wq
 *
 */
public class LogPriority {
	public final static String INFO = "INFO";
	public final static String DEBUG = "DEBUG";
	public final static String WARNING = "WARNING";
	public final static String ERROR = "ERROR";
	
	public static List getPriorityList() {
		ArrayList list = new ArrayList();
		list.add(INFO);
		list.add(DEBUG);
		list.add(WARNING);
		list.add(ERROR);
		return list;
	}
	
	public static String priorityShow(String priority) throws TypeErrorException {
		if (priority != null && priority.equals(INFO))
			return "普通信息";
		else if (priority != null && priority.equals(DEBUG))
			return "调试";
		else if (priority != null && priority.equals(WARNING))
			return "警告";
		else if (priority != null && priority.equals(ERROR))
			return "错误";
		else
			throw new TypeErrorException("Cashout priority error!");
	}
}
