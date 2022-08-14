package enfo.crm.callcenter;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.CCVO;

public interface CallCenterLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param page
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList statCCCheckRecords(CCVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ҳ��ѯ��ϯ�绰��ϸ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	IPageList query_cc_detail(CCVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ��ϯ�绰��ϸ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List l_query_cc_detail(CCVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param page
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList statCCSeatCallDetail(CCVO vo, int page, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws BusiException
	 * 	IN_Direction		INTEGER			����1���н���2���в���
		IN_CallTime			DATETIME		ͨ����ʼʱ�䣨������
		IN_CallLength		INTEGER			ͨ��ʱ�����룩
		IN_ManagerID		INTEGER			�ͻ�����ID��TCustManagers.ManagerID����������
		IN_Extension		INTEGER			�ֻ�����
		IN_CUST_ID			INTEGER			�ͻ�ID��TCustomers.CUST_ID����������ͨ���Է��ǿͻ�ʱ��������CUST_ID�����ǿͻ���δ����Ϊ�ͻ�ʱ��Ϊ��
		IN_ContactID		INTEGER			��ϵ��ID(TCustomerContacts.ContactID)
		IN_PhoneNumber		NVARCHAR(50)	�Է��绰����
		IN_BusinessID		INTEGER			����ͨ�����漰ҵ��ID
		IN_Content			NVARCHAR(MAX)	ͨ������
		IN_Status			INTEGER			״̬1�������2���λỰ������9��������
		IN_CallCenterID		BIGINT			CallCenterϵͳ�б�ʶ��HelpCenter.calllog.id��
		IN_INPUT_MAN		INTEGER			����Ա
	
	 */
	Integer addCallRecords(CCVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws BusiException
	 */
	void modiCallRecords(CCVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 * 
	 * 	IN_Serial_no	BIGINT			��ţ�������
		IN_Direction	INTEGER			����1���н���2���в���
		IN_CallTime		INTEGER			ͨ����ʼ���ڣ�YYYYMMDD��
		IN_ManagerID	INTEGER			�ͻ�����ID��TCustManagers.ManagerID��
		IN_Extension	INTEGER			�ֻ�����
		IN_CUST_ID		INTEGER			�ͻ�ID��TCustomers.CUST_ID����������
		IN_PhoneNumber	NVARCHAR(30)	�Է��绰����
		IN_Content		NVARCHAR(MAX)	ͨ������
		IN_Status		INTEGER			״̬1�������2���λỰ������9��������
		IN_INPUT_MAN	INTEGER			����Ա
	
	 */
	IPageList listCallRecords(CCVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listCustByPhone(CCVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listCustByPhone2(CCVO vo, int pageIndex, int pageSize) throws BusiException;

}