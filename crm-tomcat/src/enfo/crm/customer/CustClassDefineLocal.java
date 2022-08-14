package enfo.crm.customer;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.CustClassDefineVO;
import enfo.crm.vo.CustClassifyVO;

public interface CustClassDefineLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 添加客户分级定义
	 * @param vo
	 * @throws BusiException
	 */
	void append(CustClassDefineVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改客户分级定义
	 * @param vo
	 * @throws BusiException
	 */
	void modify(CustClassDefineVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除客户分级定义
	 * @param vo
	 * @throws BusiException
	 */
	void delete(CustClassDefineVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询客户分级定义
	 * @param vo
	 * @return list
	 * @throws BusiException
	 */
	List query(CustClassDefineVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询客户分类
	 * @param vo
	 * @return list
	 * @throws BusiException
	 */
	List query_custClassDefine(CustClassDefineVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 根据不同分类，查询不同类别客户所占比例
	 * @param vo
	 * @return list
	 * @throws BusiException
	 */
	List query_custClassify(CustClassifyVO vo) throws BusiException;

}