package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.CustomerInfoVO;

public interface CustomerInfoLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 客户信息查询_load一条记录 SP_QUERY_TCUSTOMERINFO_LOAD
	 * 
	 * @IN_CUST_ID INT
	 */
	List load(CustomerInfoVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 
	 * 查询客户资料的基本信息——分页显示(多参数)
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @throws BusiException
	 * @return rsList
	 */
	IPageList listProcAllExt(CustomerInfoVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @throws BusiException
	 * @return rsList
	 */
	IPageList listRelation(CustomerInfoVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_ADD_TCUSTFAMILYINFO
	 * 
	 */
	Integer appendRela(CustomerInfoVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_DEL_TCUSTFAMILYINFO
	 * 
	 */
	void deleteRela(CustomerInfoVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 根据证件号码、客户编号搜索查询用 
	 * SP_QUERY_TCUSTOMERINFO_NO
	 * 
	 * @IN_BOOK_CODE INT,
	 * @IN_CUST_NO VARCHAR(8), -- 客户编号
	 * @IN_CARD_ID VARCHAR(20), -- 证件号码
	 * @IN_VIP_CARD_ID VARCHAR(20) = NULL, -- VIP卡编号
	 * @IN_HGTZR_BH VARCHAR(20) = NULL, -- 合格投资人编号
	 * @IN_CUST_NAME VARCHAR(80) = NULL, -- 客户名称
	 * @IN_IS_LINK INT --是否为关联方
	 */
	List queryByCustNo(CustomerInfoVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 查询家庭列表，包含客户信息，即：家庭及其成员列表 SP_QUERY_TCUSTFAMILYINFO1
	 */
	List listRelation1(CustomerInfoVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @throws BusiException
	 * @return rsList
	 */
	IPageList listCustBank(CustomerInfoVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 
	 * @param vo
	 * @throws BusiException
	 * @return rsList
	 */
	List loadCustBank(CustomerInfoVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_DEL_TCUSTFAMILYINFO
	 * 
	 */
	void deleteCustBank(CustomerInfoVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_ADD_TCUSTBANKACCT
	 * 
	 */
	void addCustBank(CustomerInfoVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_MODI_TCUSTBANKACCT
	 * 
	 */
	void modiCustBank(CustomerInfoVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 查询企业信息列表
	 * 
	 * SP_QUERY_TENTCUSTINFO
	 * 
	 * @IN_BOOK_CODE INT,
	 * @IN_CUST_ID INT,
	 * @IN_CUST_TYPE VARCHAR(10),
	 * @IN_CUST_NAME VARCHAR(60),
	 * @IN_CUST_CODE VARCHAR(20)
	 * @IN_IS_LINK INT = 0
	 * @IN_JT_FLAG = NULL,
	 * @IN_CARD_CODE = NULL
	 */
	List listEntCust(CustomerInfoVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	List loadEntCust(Integer cust_id) throws Exception;

}