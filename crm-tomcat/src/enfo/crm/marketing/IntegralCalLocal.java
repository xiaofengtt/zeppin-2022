package enfo.crm.marketing;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.IntegralCalVO;

public interface IntegralCalLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 计算积分
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Object[] integralCal(IntegralCalVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 积分日志查询
	 * @param vo
	 * @return
	 * @throws BusiException 
	 */
	IPageList queryIntegralLog(IntegralCalVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 积分规则 查询
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryRule(IntegralCalVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 积分规则 查询
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryRuleDtl(IntegralCalVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 积分规则 查询
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryRuleAmount(IntegralCalVO vo) throws BusiException;

}