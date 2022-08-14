package enfo.crm.contractManage;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.TcoContractPointsVO;

public interface TcoContractPointsLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 合同管理-增加合同要点
	 * @param vo
	 * @throws BusiException
	 */
	void append(TcoContractPointsVO vo) throws BusiException;

	/**
	 * 查询---无分页（合同要点列表）
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	List queryByList(TcoContractPointsVO vo) throws BusiException;

	/**
	 * 修改--合同要点
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	void modi(TcoContractPointsVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 删除--合同要点
	 * @param vo
	 * @throws BusiException
	 */
	void delete(TcoContractPointsVO vo) throws BusiException;

}