package enfo.crm.cont;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.ContractManagementVO;

public interface ContractManagementLocal extends IBusiExLocal {

	/**
	 * 查询--分页（合同登记列表）
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	IPageList queryByPageList(ContractManagementVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * 查询---无分页（合同登记列表）
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	List queryByList(ContractManagementVO vo) throws BusiException;

	/**
	 * 添加--合同登记
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	Integer addContManagement(ContractManagementVO vo) throws BusiException;

	/**
	 * 新增合同统计
	 * @ejb.interface-method view-type="local"
	 * @param product_id : 产品ID
	 * @param service_man : 客户经理
	 * @param flag : 合同状态
	 * @param input_man : 操作员
	 * @param summary : 备注
	 * @throws BusiException
	 */
	void addContractStat(Integer product_id, Integer service_man, Integer flag, Integer input_man, String summary,
			Integer serial_no) throws BusiException;

	/**
	 * 查询合同统计
	 * @ejb.interface-method view-type="local"
	 * @param product_id : 产品ID
	 * @return
	 * @throws BusiException
	 */
	List queryContractStat(Integer product_id) throws BusiException;

	/**
	 * 查询客户经理名下的合同状态处理结果
	 * @ejb.interface-method view-type="local"
	 * @param service_man
	 * @return
	 * @throws BusiException
	 */
	List queryContractStatResult(Integer service_man) throws BusiException;

	/**
	 * 修改--合同登记
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	void modiContManagement(ContractManagementVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 删除--合同登记
	 * @param vo
	 * @throws BusiException
	 */
	void deleteContManagement(ContractManagementVO vo) throws BusiException;

}