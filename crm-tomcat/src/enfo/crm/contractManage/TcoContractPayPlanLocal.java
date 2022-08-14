package enfo.crm.contractManage;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.TcoContractPayPlanVO;

public interface TcoContractPayPlanLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 合同管理-增加收款计划
	 * @param vo
	 * @throws BusiException
	 */
	void append(TcoContractPayPlanVO vo) throws BusiException;

	/**
	 * 查询---无分页（收款计划列表）
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	List queryByList(TcoContractPayPlanVO vo) throws BusiException;

	/**
	 * 查询---分页（收款跟踪）
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	IPageList queryUnReceive(TcoContractPayPlanVO vo, String[] totalNumber, int pageIndex, int pageSize)
			throws BusiException;
	
	IPageList queryUnReceive(TcoContractPayPlanVO vo, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * 修改--收款计划
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */

	void modi(TcoContractPayPlanVO vo) throws BusiException;

	/**
	 * 修改--开票
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	void setInvoice(TcoContractPayPlanVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 删除--收款计划
	 * @param vo
	 * @throws BusiException
	 */
	void delete(TcoContractPayPlanVO vo) throws BusiException;

}