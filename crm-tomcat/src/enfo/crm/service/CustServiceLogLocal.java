package enfo.crm.service;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.CustServiceLogVO;

public interface CustServiceLogLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 新增客户维护记录
	 * @param vo
	 */
	void append(CustServiceLogVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改客户维护记录
	 * @param vo
	 * 
	 */
	void modi(CustServiceLogVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除客户维护记录
	 * @param vo
	*/
	void delete(CustServiceLogVO vo) throws BusiException;

}