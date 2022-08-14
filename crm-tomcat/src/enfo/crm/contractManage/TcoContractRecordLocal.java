package enfo.crm.contractManage;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.TcoContractRecordVO;

public interface TcoContractRecordLocal extends IBusiExLocal {

	/**
		 * @ejb.interface-method view-type = "local"
		 * 客户维护管理-增加客户维护信息
		 * @param vo
		 * @throws BusiException
		 * 
	 */
	void append(TcoContractRecordVO vo) throws BusiException;

	/**
	 * 查询--分页（客户维护记录列表）
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	IPageList queryByPageList(TcoContractRecordVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * 查询---无分页（客户维护记录列表）
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	List queryByList(TcoContractRecordVO vo) throws BusiException;

	/**
	 * 修改--客户维护记录
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	void modi(TcoContractRecordVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 删除--客户维护记录
	 * @param vo
	 * @throws BusiException
	 */
	void delete(TcoContractRecordVO vo) throws BusiException;

}