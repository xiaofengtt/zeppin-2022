package enfo.crm.marketing;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.CustIntegralVO;

public interface CustIntegralLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询客户积分
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList queryCustIntegral(CustIntegralVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改客户积分
	 * @param vo
	 * @throws BusiException
	 */
	void addCustIntegral(CustIntegralVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改客户积分 手动
	 * @param vo
	 * @throws BusiException
	 */
	void addCustIntegralByHand(CustIntegralVO vo) throws BusiException;

}