package enfo.crm.callcenter;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.SendSMSVO;

public interface SmsRecordLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ���淢�Ͷ�����Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Integer append(SendSMSVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �༭���Ͷ�����Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void modi(SendSMSVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ������־��Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query(SendSMSVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ҳ��ѯ������־��Ϣ
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList query_page(SendSMSVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ������Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	IPageList queryMessageList(SendSMSVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ������Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryMessage(SendSMSVO vo) throws BusiException;

	/**
	/**
	 * @ejb.interface-method view-type = "local"
	 * ���淢����Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Integer appendMessage(SendSMSVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ���淢����Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void modiMessage(SendSMSVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ���·��ͼ�¼״̬
	 * @param vo
	 * @throws BusiException
	 */
	void updateResult(SendSMSVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ��������Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void checkMessage(SendSMSVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ��������Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void deleteMessage(SendSMSVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ������ϸ��¼
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryMessageDetail(SendSMSVO vo) throws BusiException;
	
	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ������ϸ��¼
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryMessageDetail_m(SendSMSVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �����ͻ���ϸ��¼
	 * @param vo
	 * @throws BusiException
	 */
	void addMessageCustid(SendSMSVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ��������Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void deleteMessageDetail(SendSMSVO vo) throws BusiException;

	/**
	 * ���Ͷ���
	 * @param vo
	 * @return
	 */
	public void sendSms(Integer serial_no, Integer input_man) throws BusiException;
	
	/**
	 * @ejb.interface-method view-type = "local"
	 * ���Ͷ���
	 * @param vo
	 * @return
	 */
	void sendSms(Integer serial_no, Integer input_man, Integer product_id) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �����ʼ�
	 * @param vo
	 * @return
	 */
	void sendEmail(SendSMSVO vo) throws BusiException;

}