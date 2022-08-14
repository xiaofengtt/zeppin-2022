package enfo.crm.customer;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.CustomerContactVO;

public interface CustomerContactLocal extends IBusiExLocal {

	/**
	 * �����ϵ��
	 */
	String appendSql = "{?=call SP_ADD_TCustomerContacts(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	/**
	 * �޸���ϵ��
	 */
	String modiSql = "{?=call SP_MODI_TCustomerContacts(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	/**
	 * ɾ����ϵ��
	 */
	String delSql = "{?=call SP_DEL_TCustomerContacts(?,?)}";
	/**
	 * ��ѯ��ϵ��
	 */
	String listSql = "{call SP_QUERY_TCUSTOMERCONTACTS(?,?,?,?,?,?,?,?,?,?)}";

	/**
	 * @ejb.interface-method view-type = "local"
	 * �����ϵ��
	 * <pre>
	 * SP_ADD_TCustomerContacts
	 * <pre>
	 * @param vo
	 * @throws BusiException
	 */
	void appendCustomerContact(CustomerContactVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸���ϵ��
	 * <pre>
	 * SP_MODI_TCustomerContacts
	 * <pre>
	 * @param vo
	 * @throws BusiException
	 */
	void modiCustomerContact(CustomerContactVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ����ϵ��
	 * <pre>
	 * SP_DEL_TCustomerContacts
	 * <pre>
	 * @param vo
	 * @throws BusiException
	 */
	void delCustomerContact(CustomerContactVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ��ϵ��-��ҳ��ʾ
	 * <pre>
	 * SP_QUERY_TCUSTOMERCONTACTS
	 * <pre>
	 * @param vo
	 * @throws BusiException
	 */
	IPageList queryCustomerContact(CustomerContactVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ��ϵ��-�б���ʾ
	 * <pre>
	 * SP_QUERY_TCUSTOMERCONTACTS
	 * <pre>
	 * @param vo
	 * @throws BusiException
	 */
	List queryCustomerContact(CustomerContactVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ò�ѯ����
	 * @param vo
	 * @return
	 */
	Object[] getQueryParams(CustomerContactVO vo);

}