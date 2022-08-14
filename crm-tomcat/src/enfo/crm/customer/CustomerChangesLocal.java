package enfo.crm.customer;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.CustomerChangesVO;

public interface CustomerChangesLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 根据客户ID获得客户修改明细信息
	 * @param cust_id
	 * @return list
	 * @throws BusiException
	 */
	List queryCustChangesByCustId(CustomerChangesVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	void importCustomerInfo(String[] sqls) throws Exception;

}