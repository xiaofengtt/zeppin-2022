package com.whaty.platform.vote.basic;

import java.util.List;

import com.whaty.platform.util.Page;
import com.whaty.platform.vote.util.exception.VoteException;

public abstract class VoteList {

	/**调查问卷列表
	 * @param page
	 * @param searchList
	 * @param orderList
	 * @return
	 * @throws VoteException
	 */
	public abstract List getVotePaperList(Page page,List searchList,List orderList) throws VoteException;
	
	
	public abstract int getVotePaperNum(List searchList) throws VoteException;
	
	/**调查问卷建议列表
	 * @param page
	 * @param searchList
	 * @param orderList
	 * @return
	 * @throws VoteException
	 */
	public abstract List getVotePaperSuggests(Page page,List searchList,List orderList) throws VoteException;
	
	public abstract int getVotePaperSuggestsNum(List searchList) throws VoteException;
	
}
