package com.whaty.platform.message.service.imp;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.util.Page;
import com.whaty.platform.message.bean.MsgInfo;
import com.whaty.platform.message.dao.MessageDao;
import com.whaty.platform.message.service.MessageService;

public class MessageServiceImp implements MessageService {

	private MessageDao messageDao;

	/**
	 * @return the messageDao
	 */
	public MessageDao getMessageDao() {
		return messageDao;
	}

	/**
	 * @param messageDao the messageDao to set
	 */
	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public void delete(MsgInfo message) {
		// TODO Auto-generated method stub
		
	}

	public void deleteMsgInfosByIds(List ids) {
		this.messageDao.deleteMsgInfosByIds(ids);
		
	}

	public MsgInfo getMsgInfoById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Page getMsgInfosByPage(DetachedCriteria detachedCriteria,
			int pageSize, int startIndex) {
		return this.getMessageDao().getMsgInfosByPage(detachedCriteria, pageSize, startIndex);
	}

	public List getMsgInfosList(DetachedCriteria detachedCriteria) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getMsgInfosTotalCount(DetachedCriteria detachedCriteria) {
		// TODO Auto-generated method stub
		return null;
	}

	public MsgInfo save(MsgInfo message) {
		return this.getMessageDao().save(message);
	}

	public int updateReadedStatusByIds(List list) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void markReadedMsgInfosByIds(List idList) {
		this.messageDao.markReadedMsgInfosByIds(idList);
		
	}

	public boolean getCurStatus(String userId) {
		return this.getMessageDao().getCurStatus(userId);
	}
	
	
}
