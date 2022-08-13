package com.whaty.platform.entity;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.basic.MailInfo;
import com.whaty.platform.util.Page;

/**
 * 该类描述了管理员邮件管理的功能
 * 
 */
public abstract class BasicMailInfoManage {

		public BasicMailInfoManage(){}
		
		/**
		 * 添加邮件
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
		 * 修改邮件
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
		 * 更新邮件的状态
		 * 
		 * @param id
		 * @param sendStatus
		 * @return
		 * @throws PlatformException
		 */
		public abstract int updateMailMessageSendStatus(String id, String email, String sendStatus)
				throws PlatformException;

		/**
		 * 根据id获取MailMessage
		 * 
		 * @param id
		 * @return
		 * @throws PlatformException
		 */
		public abstract MailInfo getMailMessage(String id)
				throws PlatformException;
		
		/**
		 * 批量审核MailMessage
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
		 * 获得符合条件的mailMessage数
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
		 * 分页获得符合条件的mailMessage列表
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
		 * 从Excel表格中取出email
		 * 
		 * @param fileName
		 * @return
		 * @throws PlatformException
		 */
		public abstract String getEmails(String fileName) throws PlatformException;

		/**
		 * 批量删除email
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


