package enfo.crm.customer;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.CustomerContactVO;

public interface CustomerContactLocal extends IBusiExLocal {

	/**
	 * 添加联系人
	 */
	String appendSql = "{?=call SP_ADD_TCustomerContacts(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	/**
	 * 修改联系人
	 */
	String modiSql = "{?=call SP_MODI_TCustomerContacts(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	/**
	 * 删除联系人
	 */
	String delSql = "{?=call SP_DEL_TCustomerContacts(?,?)}";
	/**
	 * 查询联系人
	 */
	String listSql = "{call SP_QUERY_TCUSTOMERCONTACTS(?,?,?,?,?,?,?,?,?,?)}";

	/**
	 * @ejb.interface-method view-type = "local"
	 * 添加联系人
	 * <pre>
	 * SP_ADD_TCustomerContacts
	 * <pre>
	 * @param vo
	 * @throws BusiException
	 */
	void appendCustomerContact(CustomerContactVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改联系人
	 * <pre>
	 * SP_MODI_TCustomerContacts
	 * <pre>
	 * @param vo
	 * @throws BusiException
	 */
	void modiCustomerContact(CustomerContactVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除联系人
	 * <pre>
	 * SP_DEL_TCustomerContacts
	 * <pre>
	 * @param vo
	 * @throws BusiException
	 */
	void delCustomerContact(CustomerContactVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询联系人-分页显示
	 * <pre>
	 * SP_QUERY_TCUSTOMERCONTACTS
	 * <pre>
	 * @param vo
	 * @throws BusiException
	 */
	IPageList queryCustomerContact(CustomerContactVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询联系人-列表显示
	 * <pre>
	 * SP_QUERY_TCUSTOMERCONTACTS
	 * <pre>
	 * @param vo
	 * @throws BusiException
	 */
	List queryCustomerContact(CustomerContactVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 获得查询参数
	 * @param vo
	 * @return
	 */
	Object[] getQueryParams(CustomerContactVO vo);

}