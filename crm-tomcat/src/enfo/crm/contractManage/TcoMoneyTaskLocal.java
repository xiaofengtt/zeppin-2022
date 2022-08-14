package enfo.crm.contractManage;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.TcoMoneyTaskVO;

public interface TcoMoneyTaskLocal extends IBusiExLocal {

	/**
	 * 查询--分页（收款计划提醒列表）
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	IPageList queryByPageList(TcoMoneyTaskVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * 查询---无分页（合同登记列表）
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	List queryByList(TcoMoneyTaskVO vo) throws BusiException;

	/**
	 * 生成收款提醒记录
	 * @param updateSql
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	void createMoneyTask(TcoMoneyTaskVO vo) throws BusiException;

}