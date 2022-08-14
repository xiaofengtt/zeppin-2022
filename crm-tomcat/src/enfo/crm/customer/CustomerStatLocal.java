package enfo.crm.customer;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.CustomerStatVO;
import enfo.crm.vo.CustomerVO;
import enfo.crm.vo.TcustmanagersVO;

public interface CustomerStatLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 认购额度明细
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List getStatCustContract(CustomerStatVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 认购额度明细 已迁移至CRM
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List getStatCustContract2(CustomerStatVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 认购额度明细 分页 
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList getStatCustContract_page(CustomerStatVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 认购额度明细 分页  已迁移至CRM
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList getStatCustContract_page2(CustomerStatVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 客户认购额度分析(按客户经理作汇总) 分页 
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList getStatCustContract_page(CustomerStatVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 流失客户明细
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List getStatCustomerLevel(CustomerStatVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 流失客户明细 分页
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException 
	 */
	IPageList getStatCustomerLevel_page(CustomerStatVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * 客户量分析
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws BusiException
	 */
	List getVolueStat(CustomerStatVO vo) throws BusiException;

	/**
	 * 客户年龄分布显示图
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws BusiException
	 */
	List getAgeDistribution(CustomerStatVO vo) throws BusiException;

	/**
	 * 客户类别分析显示图
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws BusiException
	 */
	List getCategoryStat(CustomerStatVO vo) throws BusiException;

	/**
	 * 客户来源分析显示图
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws BusiException
	 */
	List getSourceStat(CustomerStatVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 按省级地区分布－客户数量
	 * @return
	 * @throws BusiException
	 */
	List getProvinceStat(Integer begin_date, Integer end_date) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页查询介期产品团队认购情况
	 * @param vo
		@IN_SERIAL_NO	int
		@IN_PRODUCT_ID  INT
		@IN_TEAM_ID	int
		@IN_LINK_MAN_NAME NVARCHAR(40)
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList queryTeamProduct(TcustmanagersVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页返回客户经理统计客户情况
	 * @param vo
		@IN_TEAM_ID     INT
		@IN_MANAGERNAME	nvarchar
		@IN_LEADER_NAME	varchar
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pagelist_TeamManager(TcustmanagersVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * 营销成本分析-客户
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws BusiException
	 */
	IPageList getSaleCostCust(CustomerVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * 营销成本分析-产品
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws BusiException
	 */
	IPageList getSaleCostProduct(CustomerVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 直销客户图表统计分析－表格显示
	 * @param begin_date
	 * @param end_date
	 * @param input_man
	 * @return
	 * @throws BusiException
	 */
	List queryDircetCustomerStat(Integer begin_date, Integer end_date, Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 直销客户图表统计分析--图表显示（折线图）
	 * @param begin_date
	 * @param end_date
	 * @param input_man
	 * @return
	 * @throws BusiException
	 */
	List querySellingStat(Integer begin_date, Integer end_date, Integer input_man) throws BusiException;

	/**
	 * 客户量分析--分页显示
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws BusiException
	 */
	IPageList getPageVolueStat(CustomerStatVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

}