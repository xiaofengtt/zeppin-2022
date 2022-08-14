package enfo.crm.contractManage;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.TcoContractMoneyDetailVO;

public interface TcoContractMoneyDetailLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 合同管理-增加合同信息
	 * @param vo
	 * @throws BusiException
	 * 
	 */
	void append(TcoContractMoneyDetailVO vo) throws BusiException;

	/**
	 * 查询---无分页（到账明细列表）
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	List queryByList(TcoContractMoneyDetailVO vo) throws BusiException;

	/**
	 * 修改--到账明细
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	void modi(TcoContractMoneyDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 删除--到账明细
	 * @param vo
	 * @throws BusiException
	 */
	void delete(TcoContractMoneyDetailVO vo) throws BusiException;

}