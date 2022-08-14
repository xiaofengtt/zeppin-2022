package enfo.crm.contractManage;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.TcoContractVO;

public interface TcoContractLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 合同管理-增加合同信息
	 * @param vo
	 * @throws BusiException
	 * 
	 */
	Integer append(TcoContractVO vo) throws BusiException;

	/**
	 * 查询--分页（合同登记列表）
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	IPageList queryByPageList(TcoContractVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * 查询---无分页（合同登记列表）
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	List queryByList(TcoContractVO vo) throws BusiException;

	/**
	 * 修改--合同登记
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	void modi(TcoContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 删除--合同登记
	 * @param vo
	 * @throws BusiException
	 */
	void delete(TcoContractVO vo) throws BusiException;

	/**
	 * 审核--合同登记
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	void check(TcoContractVO vo) throws BusiException;

}