package com.whaty.platform.message.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.util.Page;
import com.whaty.platform.message.bean.MsgInfo;

/**
 * @author chenjian
 *
 */
public interface MessageDao {
	
	/**
	 * @param message
	 * @return
	 */
	public MsgInfo save(MsgInfo message);
	
	/**
	 * @param message
	 */
	public void delete(MsgInfo message);
	
	/**
	 * @param ids
	 */
	public void deleteMsgInfosByIds(final List ids);
	
	/**
	 * @param id
	 * @return
	 */
	public MsgInfo getMsgInfoById(String id);
	
	/**
	 * @param list
	 * @return
	 */
	public int updateReadedStatusByIds(final List list);
	
	/**
	 * @param detachedCriteria
	 * @return
	 */
	public List getMsgInfosList(final DetachedCriteria detachedCriteria);
	
	/**
	 * @param detachedCriteria
	 * @return
	 */
	public Integer getMsgInfosTotalCount(final DetachedCriteria detachedCriteria);
	
	/**
	 * @param detachedCriteria
	 * @param pageSize
	 * @param startIndex
	 * @return
	 */
	public Page getMsgInfosByPage(final DetachedCriteria detachedCriteria,
			final int pageSize, final int startIndex);

	public void markReadedMsgInfosByIds(List idList);

	public boolean getCurStatus(String userId);
	
	
}
