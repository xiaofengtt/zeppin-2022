package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.TMoneyDataVO;

public interface TMoneyDataLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页查询 资金录入情况
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listForPage(TMoneyDataVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页查询 资金录入情况
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listForPagePZ(TMoneyDataVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询 资金录入情况
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listAll(TMoneyDataVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 认购资金录入 添加 使用CRM过程
	 * @param vo
	 * @throws BusiException
	 */
	void append(TMoneyDataVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 认购资金录入 编辑
	 * @param vo
	 * @throws BusiException
	 */
	void modify(TMoneyDataVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 认购资金录入 删除
	 * @param vo
	 * @throws BusiException
	 */
	void delete(TMoneyDataVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 认购资金录入初审
	 * @param vo
	 * @throws BusiException
	 */
	void input_check(TMoneyDataVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 认购资金审核
	 * @param vo
	 * @throws BusiException
	 */
	void check(TMoneyDataVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * @param vo
	 * @throws BusiException
	 */
	List getTmoneydata(String serial_no_list) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 自动生成各种合同信息
	 * @return
	 * @throws Exception
	 */
	void combineContract(TMoneyDataVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param serial_no
	 * @param cust_id
	 * @param cust_name
	 * @param product_id
	 * @throws Exception
	 */
	void modiSubproductid(Integer serial_no, Integer sub_product_id, Integer product_id) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 搜索待补全合同 分页
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listContractMendForPage(TMoneyDataVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 搜索待补全合同
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listContractMend(TMoneyDataVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void checkPurchanseContractMend(TMoneyDataVO vo) throws Exception;

	/** 
	 * @ejb.interface-method view-type = "local"
	 * 认购合同生成恢复查询(分页)
	 */
	IPageList queryContractRestore(TMoneyDataVO vo, int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 认购合同生成恢复
	 */
	void combineMoneyDataRestore(TMoneyDataVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param serial_no
	 * @param cust_id
	 * @param cust_name
	 * @param product_id
	 * @throws Exception
	 */
	void modiWTR(Integer serial_no, Integer cust_id, String cust_name) throws Exception;

}