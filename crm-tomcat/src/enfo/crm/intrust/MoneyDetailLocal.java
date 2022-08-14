package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.MoneyDetailVO;

public interface MoneyDetailLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询客户缴款数据详细信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	List listBySql(MoneyDetailVO vo) throws BusiException;

	/**
	 *  @ejb.interface-method view-type = "local"
	 * 查询客户缴款数据详细信息 分页
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList query_page(MoneyDetailVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询缴款合同信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Object[] queryContractJkBase(MoneyDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 新建缴款
	 * @param vo
	 * @throws Exception
	 */
	Integer append(MoneyDetailVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改缴款
	 * @param vo
	 * @throws BusiException
	 */
	void modi(MoneyDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除缴款
	 * @param vo
	 * @throws Exception
	 */
	void delete(MoneyDetailVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 搜索缴款
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List load(MoneyDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询客户交易明细,打印客户对账单用
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	List listTradeDetail(MoneyDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 为发行期退款查询缴款记录
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listForRebate(MoneyDetailVO vo) throws BusiException;

}