package enfo.crm.customer;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.CustomerConnectionVO;

public interface CustomerConnectionLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * �����ͻ���ϵ��ʽ�޸���Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Integer addCustomerConnection(CustomerConnectionVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * �޸Ŀͻ���ϵ��ʽ�޸���Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void modiCustomerConnection(CustomerConnectionVO vo) throws BusiException;
	
	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ȷ�Ͽͻ���ϵ��ʽ�޸���Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void checkCustomerConnection(CustomerConnectionVO vo) throws BusiException;
	
	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ��˿ͻ���ϵ��ʽ�޸���Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void recheckCustomerConnection(CustomerConnectionVO vo) throws BusiException;


	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ��ѯ�ͻ���ϵ��ʽ�޸���Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	@SuppressWarnings("rawtypes")
	List queryCustomerConnection(CustomerConnectionVO vo) throws BusiException;

}