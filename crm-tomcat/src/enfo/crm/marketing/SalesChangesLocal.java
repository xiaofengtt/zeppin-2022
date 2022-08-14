package enfo.crm.marketing;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.SalesChangesVO;

public interface SalesChangesLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ����ת������Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Integer addSalesChanges(SalesChangesVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * �޸�ת������Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void modiSalesChanges(SalesChangesVO vo) throws BusiException;
	
	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ����ת������Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void cancelSalesChanges(SalesChangesVO vo) throws BusiException;
	
	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ȷ��ת������Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void checkSalesChanges(SalesChangesVO vo) throws BusiException;
	
	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ���ת������Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void recheckSalesChanges(SalesChangesVO vo) throws BusiException;


	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ��ѯת������Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	@SuppressWarnings("rawtypes")
	List querySalesChanges(SalesChangesVO vo) throws BusiException;

}