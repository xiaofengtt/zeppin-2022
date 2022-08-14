package enfo.crm.customer;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.CustClassDetailVO;

public interface CustClassDetailLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 添加客户分级定义之明细
	 * @param vo
	 * @throws BusiException
	 */
	void append(CustClassDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改客户分级定义之明细
	 * @param vo
	 * @throws BusiException
	 */
	void modify(CustClassDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除客户分级定义之明细
	 * @param vo
	 * @throws BusiException
	 */
	void delete(CustClassDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询客户分级定义之明细
	 * @param vo
	 * @return list
	 * @throws BusiException
	 */
	List query(CustClassDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询客户分布树
	 * @param vo
	 * @return list
	 * @throws BusiException
	 */
	List query2(CustClassDetailVO vo) throws BusiException;

}