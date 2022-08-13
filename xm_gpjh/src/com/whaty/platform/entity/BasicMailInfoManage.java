package com.whaty.platform.entity;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.basic.MailInfo;
import com.whaty.platform.util.Page;

/**
 * ���������˹���Ա�ʼ�����Ĺ���
 * 
 */
public abstract class BasicMailInfoManage {

		public BasicMailInfoManage(){}
		
		/**
		 * ����ʼ�
		 * 
		 * @param targets
		 * @param content
		 * @param sender
		 * @param scope
		 * @param siteId
		 * @return
		 * @throws PlatformException
		 */
		public abstract int addMailMessage(String targets, String content,
				String sender, String scope, String siteId)
				throws PlatformException;
		
		/**
		 * �޸��ʼ�
		 * 
		 * @param id
		 * @param targets
		 * @param content
		 * @param status
		 * @param sender
		 * @param time
		 * @return
		 * @throws PlatformException
		 */
		public abstract int updateMailMessage(String id, String targets,
				String content, String status, String sender, String time,
				String scope) throws PlatformException;	
		
		/**
		 * �����ʼ���״̬
		 * 
		 * @param id
		 * @param sendStatus
		 * @return
		 * @throws PlatformException
		 */
		public abstract int updateMailMessageSendStatus(String id, String email, String sendStatus)
				throws PlatformException;

		/**
		 * ����id��ȡMailMessage
		 * 
		 * @param id
		 * @return
		 * @throws PlatformException
		 */
		public abstract MailInfo getMailMessage(String id)
				throws PlatformException;
		
		/**
		 * �������MailMessage
		 * 
		 * @param checker
		 * @param mailMessages
		 * @return
		 * @throws PlatformException
		 */
		public abstract int checkMailMessage(String checker, List smsMessages)
				throws PlatformException;
		
		public abstract int checkMailMessage(String checker, List smsMessages,String dirconfig)
		throws PlatformException;

		public abstract int rejectMailMessage(String checker, String[] msgIds,String[] notes)
				throws PlatformException;
		
		/**
		 * ��÷���������mailMessage��
		 * 
		 * @param id
		 * @param targets
		 * @param content
		 * @param status
		 * @param sender
		 * @param startTime
		 * @param endTime
		 * @param scope
		 * @param checker
		 * @param siteId
		 * @return
		 * @throws PlatformException
		 */
		public abstract int getMailMessagesNum(String id, String targets,
				String content, String status, String sender, String startTime,
				String endTime, String scope, String checker, String siteId)
				throws PlatformException;

		/**
		 * ��ҳ��÷���������mailMessage�б�
		 * 
		 * @param page
		 * @param id
		 * @param targets
		 * @param content
		 * @param status
		 * @param sender
		 * @param startTime
		 * @param endTime
		 * @param scope
		 * @param checker
		 * @param siteId
		 * @return
		 * @throws PlatformException
		 */
		public abstract List getMailMessagesList(Page page, String id,
				String targets, String content, String status, String sender,
				String startTime, String endTime, String scope, String checker,
				String siteId) throws PlatformException;
		
		
		/**
		 * ��Excel�����ȡ��email
		 * 
		 * @param fileName
		 * @return
		 * @throws PlatformException
		 */
		public abstract String getEmails(String fileName) throws PlatformException;

		/**
		 * ����ɾ��email
		 * 
		 * @param smsMessages
		 * @return
		 * @throws PlatformException
		 */
		public abstract int deleteMailMessage(List smsMessages)
				throws PlatformException;
		
		
		public abstract List getMailMessageNumBySite(Page pageover,String site_id,String start_time,String end_time)
		throws PlatformException;
}


