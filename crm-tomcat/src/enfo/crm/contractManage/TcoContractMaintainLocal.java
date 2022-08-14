package enfo.crm.contractManage;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.TcoContractMaintainVO;

public interface TcoContractMaintainLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 维护合同管理-增加维护合同信息
	 * @param vo
	 * @throws BusiException
	 * 
	 */
	Integer append(TcoContractMaintainVO vo) throws BusiException;

	/**
	 * 查询--分页（维护合同登记列表）
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	IPageList queryByPageList(TcoContractMaintainVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * 查询---无分页（维护合同登记列表）
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	List queryByList(TcoContractMaintainVO vo) throws BusiException;

	/**
	 * 修改--维护合同登记
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */

	void modi(TcoContractMaintainVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 删除--维护合同登记
	 * @param vo
	 * @throws BusiException
	 */
	void delete(TcoContractMaintainVO vo) throws BusiException;

	/**
	 * 审核--维护合同登记
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	void check(TcoContractMaintainVO vo) throws BusiException;

	/**
	 * 查询---分页（维护费收款跟踪）
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	IPageList queryUnReceive(TcoContractMaintainVO vo, String[] totalNum, int pageIndex, int pageSize)
			throws BusiException;
	
	IPageList queryUnReceive(TcoContractMaintainVO vo, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * 修改--开票
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	void setInvoice(TcoContractMaintainVO vo) throws BusiException;

}