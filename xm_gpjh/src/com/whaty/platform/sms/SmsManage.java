package com.whaty.platform.sms;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;


public abstract class SmsManage {

//	private SmsManagerPriv priv;
//
//	/**
//	 * @return the priv
//	 */
//	public SmsManagerPriv getPriv() {
//		return priv;
//	}
//
//	/**
//	 * @param priv
//	 *            the priv to set
//	 */
//	public void setPriv(SmsManagerPriv priv) {
//		this.priv = priv;
//	}

	/**
	 * ���Ͷ���,�绰������","�����
	 * 
	 * @param phone
	 * @param content
	 * @return
	 */
	public abstract String sendMessage(String phone, String content)
			throws PlatformException;
	/**
	 * ���Ͷ���,�绰������","�����
	 * @param msgId
	 * @param phone
	 * @param content
	 * @return
	 * @throws PlatformException
	 */
	public abstract String sendMessage(String msgId ,String phone, String content)
	throws PlatformException;

	/**
	 * ���Ͷ���
	 * 
	 * @param message
	 * @return
	 * @throws PlatformException
	 */
//	public abstract String sendMessage(SmsMessage message)
//			throws PlatformException;

//	/**
//	 * ��Ӷ���
//	 * 
//	 * @param targets
//	 * @param content
//	 * @param status
//	 * @param sender
//	 * @param time
//	 * @return
//	 * @throws PlatformException
//	 */
//	public abstract int addSmsMessage(String targets, String content,
//			String sender, String scope) throws PlatformException;
//
//	/**
//	 * ��Ӷ���
//	 * 
//	 * @param targets
//	 * @param content
//	 * @param sender
//	 * @param scope
//	 * @param siteId
//	 * @return
//	 * @throws PlatformException
//	 */
//	public abstract int addSmsMessage(String targets, String content,
//			String sender, String scope, String siteId)
//			throws PlatformException;
//
//	/**
//	 * 
//	 * @param targets
//	 * @param content
//	 * @param sender
//	 * @param scope
//	 * @param siteId
//	 * @param type
//	 * @param setTime
//	 *            ��ʱʱ��
//	 * @return
//	 * @throws PlatformException
//	 */
//	public abstract int addSmsMessage(String targets, String content,
//			String sender, String scope, String siteId, String type,
//			String setTime) throws PlatformException;
//
//	public abstract int addSystemSmsMessage(String targets, String content,
//			String sender, String scope, String siteId, String type,
//			String setTime) throws PlatformException;
//	/**
//	 * 
//	 * @param targets
//	 * @param content
//	 * @param sender
//	 * @param scope
//	 * @param siteId
//	 * @param type
//	 * @param setTime
//	 * @param sendStatus �ط�״̬
//	 * @return
//	 * @throws PlatformException
//	 */
//	public abstract int addSystemSmsMessage(String targets, String content,
//			String sender, String scope, String siteId, String type,
//			String setTime,String sendStatus) throws PlatformException;
//
//	/**
//	 * ��Ӷ���
//	 * 
//	 * @param targets
//	 * @param content
//	 * @param sender
//	 * @param scope
//	 * @param siteId
//	 * @param teaId
//	 * @return
//	 * @throws PlatformException
//	 */
//	public abstract int addSmsMessage(String targets, String content,
//			String sender, String scope, String siteId, String teaId)
//			throws PlatformException;
//
//	/**
//	 * �޸Ķ���
//	 * 
//	 * @param id
//	 * @param targets
//	 * @param content
//	 * @param status
//	 * @param sender
//	 * @param time
//	 * @return
//	 * @throws PlatformException
//	 */
//	public abstract int updateSmsMessage(String id, String targets,
//			String content, String status, String sender, String time,
//			String scope) throws PlatformException;
//
//	public abstract int updateSmsMessage(String id, String targets,
//			String content, String status, String sender, String time,
//			String scope, String type, String setTime) throws PlatformException;
//
//	public abstract int updateSmsMessage(String id, String targets,
//			String content) throws PlatformException;
//
//	/**
//	 * ���¶��ŵ�״̬����
//	 * 
//	 * @param id
//	 * @param sendStatus
//	 * @return
//	 * @throws PlatformException
//	 */
//	public abstract int updateSmsMessageSendStatus(String id, String mobile,
//			String sendStatus) throws PlatformException;
//
//	/**
//	 * ���id��ȡSmsMessage
//	 * 
//	 * @param id
//	 * @return
//	 * @throws PlatformException
//	 */
//	public abstract SmsMessage getSmsMessage(String id)
//			throws PlatformException;
//
//	/**
//	 * ��÷�������SmsMessage��
//	 * 
//	 * @param id
//	 * @param targets
//	 * @param content
//	 * @param status
//	 * @param sender
//	 * @param startTime
//	 * @param endTime
//	 * @param scope
//	 * @param checker
//	 * @param siteId
//	 * @return
//	 * @throws PlatformException
//	 */
//	public abstract int getSmsMessagesNum(String id, String targets,
//			String content, String status, String sender, String startTime,
//			String endTime, String scope, String checker, String siteId)
//			throws PlatformException;
//
//	public abstract int getSmsMessagesNum(String id, String targets,
//			String content, String status, String sender, String startTime,
//			String endTime, String scope, String checker, String siteId,
//			String teaId) throws PlatformException;
//
//	public abstract int getSmsMessagesNum(String id, String targets,
//			String content, String status, String sender, String startTime,
//			String endTime, String scope, String checker, String siteId,
//			String teaId, String type) throws PlatformException;
//
//	/**
//	 * ��ҳ��÷�������SmsMessage�б�
//	 * 
//	 * @param page
//	 * @param id
//	 * @param targets
//	 * @param content
//	 * @param status
//	 * @param sender
//	 * @param startTime
//	 * @param endTime
//	 * @param scope
//	 * @param checker
//	 * @param siteId
//	 * @return
//	 * @throws PlatformException
//	 */
//	public abstract List getSmsMessagesList(Page page, String id,
//			String targets, String content, String status, String sender,
//			String startTime, String endTime, String scope, String checker,
//			String siteId) throws PlatformException;
//
//	public abstract List getSmsMessagesList(Page page, String id,
//			String targets, String content, String status, String sender,
//			String startTime, String endTime, String scope, String checker,
//			String siteId, String teaId) throws PlatformException;
//
//	public abstract List getSmsMessagesList(Page page, String id,
//			String targets, String content, String status, String sender,
//			String startTime, String endTime, String scope, String checker,
//			String siteId, String teaId, String type) throws PlatformException;
//
//	/**
//	 * �����SmsMessage
//	 * 
//	 * @param checker
//	 * @param smsMessages
//	 * @return
//	 * @throws PlatformException
//	 */
//	public abstract int checkSmsMessage(String checker, List smsMessages)
//			throws PlatformException;
//
//	public abstract int reSendSmsMessage(String checker, List smsMessages)
//			throws PlatformException;
//
//	public abstract int rejectSmsMessage(String checker, String[] msgIds,
//			String[] notes) throws PlatformException;
//
//	public abstract List getSmsMessageNumBySite(Page pageover, String site_id,
//			String start_time, String end_time) throws PlatformException;
//
	/**
	 * ��Excel�����ȡ���ƶ�����
	 * 
	 * @param fileName
	 * @return
	 * @throws PlatformException
	 */
	public abstract String getMobiles(String fileName) throws PlatformException;
//
//	/**
//	 * ��ɾ�����
//	 * 
//	 * @param smsMessages
//	 * @return
//	 * @throws PlatformException
//	 */
//	public abstract int deleteSmsMessage(List smsMessages)
//			throws PlatformException;
//
//	// SmsSystemPoint ϵͳ���ŷ��͵�
//	public abstract List getSmsSystemPoints(Page page, String id)
//			throws PlatformException;
//
//	public abstract int getSmsSystemPointsNum(String id)
//			throws PlatformException;
//
//	public abstract SmsSystemPoint getSmsSystemPoint(String id)
//			throws PlatformException;
//
//	public abstract int updateSmsSystemPoint(String id, String content)
//			throws PlatformException;
//	
//	public abstract int addSmsSystemPoint(String id,String name, String content,String status)
//	throws PlatformException;
//
//	public abstract int updateSmsSystemPointStatus(String id, String status)
//			throws PlatformException;
//    /**
//     * ����Ϣ״̬�޸� 0Ϊ���ͳɹ�;1Ϊ���ŷ���ʧ����Ҫ���·���
//     * @param id
//     * @param sendStatus
//     * @return
//     * @throws PlatformException
//     */
//	public abstract int updateSmsMessageStatus(String id,String sendStatus)throws PlatformException;
//		
}
