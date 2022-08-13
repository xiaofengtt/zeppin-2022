package com.whaty.platform.leaveword;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.leaveword.basic.LeaveWord;
import com.whaty.platform.leaveword.basic.ReplyLeaveWord;
import com.whaty.platform.leaveword.user.LeaveWordManagerPriv;
import com.whaty.platform.util.Page;

public abstract class LeaveWordManage {

	private LeaveWordManagerPriv priv;

	/**
	 * @return the priv
	 */
	public LeaveWordManagerPriv getPriv() {
		return priv;
	}

	/**
	 * @param priv
	 *            the priv to set
	 */
	public void setPriv(LeaveWordManagerPriv priv) {
		this.priv = priv;
	}

	public abstract int addLeaveWord(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;

	public abstract LeaveWord getLeaveWord(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;

	public abstract LeaveWord getLeaveWord(String id) throws PlatformException;
	
	public abstract ReplyLeaveWord getReplyLeaveWord(
			HttpServletRequest request, HttpServletResponse response)
			throws PlatformException;

	public abstract int replyLeaveWord(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;

	public abstract int updateReplyLeaveWord(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;

	public abstract int delLeaveWord(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;

	public abstract int changeLeaveWordLimitTimes(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;
	
	public abstract int changeLeaveWordMessStatus(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;

	public abstract int delReplyLeaveWord(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;

	public abstract int delAllLeaveWord(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;

	public abstract List getLeaveWordList(Page page,
			HttpServletRequest request, HttpServletResponse response)
			throws PlatformException;

	public abstract List getReplyLeaveWordList(Page page,
			HttpServletRequest request, HttpServletResponse response)
			throws PlatformException;

	public abstract int getLeaveWordListNum(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;

	public abstract int getReplyLeaveWordListNum(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;

	public abstract List getLeaveWordStat(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;
}
