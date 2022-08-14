package enfo.crm.marketing;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.SalesResultForStatisticVO;

public interface SalesResultForStatisticLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 查询信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	@SuppressWarnings("rawtypes")
	List querySalesResultForStatistic(SalesResultForStatisticVO vo) throws BusiException;
	
	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 查询信息q
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	@SuppressWarnings("rawtypes")
	List querySalesResultForStatistic_q(SalesResultForStatisticVO vo) throws BusiException;
	
	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 查询信息-更多
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	@SuppressWarnings("rawtypes")
	List querySalesResultForStatisticMore(SalesResultForStatisticVO vo) throws BusiException;
	
	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 新增到账结果信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Integer addSalesResultForStatistic(SalesResultForStatisticVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 修改转到账结果信息
	 * @param vo
	 * @throws BusiException
	 */
	void modiSalesResultForStatistic(SalesResultForStatisticVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 确认转到账结果信息
	 * @param vo
	 * @throws BusiException
	 */
	void cancelSalesResultForStatistic(SalesResultForStatisticVO vo) throws BusiException;
}