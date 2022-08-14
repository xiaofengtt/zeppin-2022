package enfo.crm.marketing;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.SalesResultForStatisticVO;

public interface SalesResultForStatisticLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ��ѯ��Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	@SuppressWarnings("rawtypes")
	List querySalesResultForStatistic(SalesResultForStatisticVO vo) throws BusiException;
	
	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ��ѯ��Ϣq
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	@SuppressWarnings("rawtypes")
	List querySalesResultForStatistic_q(SalesResultForStatisticVO vo) throws BusiException;
	
	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ��ѯ��Ϣ-����
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	@SuppressWarnings("rawtypes")
	List querySalesResultForStatisticMore(SalesResultForStatisticVO vo) throws BusiException;
	
	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * �������˽����Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Integer addSalesResultForStatistic(SalesResultForStatisticVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * �޸�ת���˽����Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void modiSalesResultForStatistic(SalesResultForStatisticVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ȷ��ת���˽����Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void cancelSalesResultForStatistic(SalesResultForStatisticVO vo) throws BusiException;
}