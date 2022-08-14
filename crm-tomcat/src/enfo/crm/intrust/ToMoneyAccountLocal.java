package enfo.crm.intrust;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.ToMoneyAccountVO;

public interface ToMoneyAccountLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询资金到账信息
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList query_page(ToMoneyAccountVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 资金到账信息审核
	 * @param vo
	 * @throws BusiException
	 */
	void check(ToMoneyAccountVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 资金到账审核恢复信息
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listRestoreCheck(ToMoneyAccountVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 资金到账信息恢复
	 * @param vo
	 * @throws BusiException
	 */
	void restoreCheck(ToMoneyAccountVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 业务经理审核
	 */
	void checkByManage(ToMoneyAccountVO vo) throws Exception;

}