package enfo.crm.customer;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.CustomerConnectionVO;

public interface CustomerConnectionLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 新增客户联系方式修改信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Integer addCustomerConnection(CustomerConnectionVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 修改客户联系方式修改信息
	 * @param vo
	 * @throws BusiException
	 */
	void modiCustomerConnection(CustomerConnectionVO vo) throws BusiException;
	
	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 确认客户联系方式修改信息
	 * @param vo
	 * @throws BusiException
	 */
	void checkCustomerConnection(CustomerConnectionVO vo) throws BusiException;
	
	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 审核客户联系方式修改信息
	 * @param vo
	 * @throws BusiException
	 */
	void recheckCustomerConnection(CustomerConnectionVO vo) throws BusiException;


	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 查询客户联系方式修改信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	@SuppressWarnings("rawtypes")
	List queryCustomerConnection(CustomerConnectionVO vo) throws BusiException;

}