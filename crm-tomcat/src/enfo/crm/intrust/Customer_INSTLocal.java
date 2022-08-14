package enfo.crm.intrust;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.CustomerVO;

public interface Customer_INSTLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws BusiException
	 */
	void cope_customers(CustomerVO vo) throws BusiException;
	void cope_customers_m(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * CRM�����ѯ �ͻ�������Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void synchro_customers(CustomerVO vo) throws BusiException;

}