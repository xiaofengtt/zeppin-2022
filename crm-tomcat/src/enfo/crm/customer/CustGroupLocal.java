package enfo.crm.customer;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.CustGroupVO;

public interface CustGroupLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 添加客户群组
	 * @param vo
	 * @throws BusiException
	 */
	void appendCustGroup(CustGroupVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改客户群组
	 * @param vo
	 * @throws BusiException
	 */
	void modiCustGroup(CustGroupVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除客户群组
	 * @param vo
	 * @throws BusiException
	 */
	void delCustGroup(CustGroupVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询所有客户群组以树形结构显示
	 * @param vo(GROUPID,INPUTMAN)
	 * @return list
	 * @throws BusiException
	 */
	List queryAll(CustGroupVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询所有客户群组
	 * @param cust_id
	 * @return
	 * @throws BusiException
	 */
	List queryByCustId(Integer cust_id) throws BusiException;

}