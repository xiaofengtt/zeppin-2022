package com.whaty.platform.leaveword.basic;

import java.util.List;

import com.whaty.platform.util.Page;

public interface LeaveWordList {
	public List getLeaveWordList(Page page, List searchList, List orderList);

	public int getLeaveWordListNum(List searchList, List orderList);

	public List getLeaveWordStat(List searchList, List orderList);
}
