package enfo.crm.service;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.CustServiceLogVO;

public interface CustServiceLogLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * �����ͻ�ά����¼
	 * @param vo
	 */
	void append(CustServiceLogVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸Ŀͻ�ά����¼
	 * @param vo
	 * 
	 */
	void modi(CustServiceLogVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ���ͻ�ά����¼
	 * @param vo
	*/
	void delete(CustServiceLogVO vo) throws BusiException;

}