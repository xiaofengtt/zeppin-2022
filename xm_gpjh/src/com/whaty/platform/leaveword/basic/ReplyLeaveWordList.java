package com.whaty.platform.leaveword.basic;

import java.util.List;

import com.whaty.platform.util.Page;

public interface ReplyLeaveWordList {
	public List getReplyLeaveWordList(Page page,List searchList,List orderList);
	public int getReplyLeaveWordListNum(List searchList,List orderList);
}
