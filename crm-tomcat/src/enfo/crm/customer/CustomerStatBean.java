 
package enfo.crm.customer;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.CustomerStatVO;
import enfo.crm.vo.CustomerVO;
import enfo.crm.vo.TcustmanagersVO;

@Component(value="customerStat")
public class CustomerStatBean extends enfo.crm.dao.CrmBusiExBean implements CustomerStatLocal {
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerStatLocal#getStatCustContract(enfo.crm.vo.CustomerStatVO)
	 */
	@Override
	public List getStatCustContract(CustomerStatVO vo) throws BusiException{
		Object[] params = new Object[2];		
		params[0] = vo.getCust_type();
		params[1] = vo.getInputMan();		
		return super.listProcAll("{call SP_STAT_CUST_CONTRACT(?,?)}",params);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerStatLocal#getStatCustContract2(enfo.crm.vo.CustomerStatVO)
	 */
	@Override
	public List getStatCustContract2(CustomerStatVO vo) throws BusiException{
		Object[] params = new Object[7];		
		params[0] = vo.getFunc_id();
		params[1] = vo.getProductId();
		params[2] = vo.getCust_type();
		params[3] = vo.getOrderBy();
		params[4] = vo.getInputMan();
		params[5] = vo.getGroupId();
		params[6] = vo.getClassDetailId();		
		return super.listProcAll("{call SP_STAT_CUST_CONTRACT2(?,?,?,?,?,?,?)}", params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerStatLocal#getStatCustContract_page(enfo.crm.vo.CustomerStatVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList getStatCustContract_page(CustomerStatVO vo,String[] totalColumn,int pageIndex,int pageSize)throws BusiException{
		Object[] params = new Object[2];		
		params[0] = vo.getCust_type();
		params[1] = vo.getInputMan();		
		return super.listProcPage("{call SP_STAT_CUST_CONTRACT(?,?)}", params, totalColumn, pageIndex, pageSize);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerStatLocal#getStatCustContract_page2(enfo.crm.vo.CustomerStatVO, int, int)
	 */
	@Override
	public IPageList getStatCustContract_page2(CustomerStatVO vo,int pageIndex,int pageSize) throws BusiException{
		Object[] params = new Object[10];		
		params[0] = vo.getFunc_id();
		params[1] = vo.getProductId();
		params[2] = vo.getCust_type();
		params[3] = vo.getOrderBy();
		params[4] = vo.getInputMan();
		params[5] = vo.getGroupId();
		params[6] = vo.getClassDetailId();
		params[7] = vo.getStatScope();
		params[8] = vo.getRgDateStart();
		params[9] = vo.getRgDateEnd();
		return super.listProcPage("{call SP_STAT_CUST_CONTRACT2(?,?,?,?,?,?,?,?,?,?)}", params, pageIndex, pageSize);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerStatLocal#getStatCustContract_page(enfo.crm.vo.CustomerStatVO, int, int)
	 */
	@Override
	public IPageList getStatCustContract_page(CustomerStatVO vo,int pageIndex,int pageSize) throws BusiException{
		Object[] params = new Object[10];		
		params[0] = vo.getFunc_id();
		params[1] = vo.getProductId();
		params[2] = vo.getCust_type();
		params[3] = vo.getOrderBy();
		params[4] = vo.getInputMan();
		params[5] = vo.getGroupId();
		params[6] = vo.getClassDetailId();
		params[7] = vo.getStatScope();
		params[8] = vo.getRgDateStart();
		params[9] = vo.getRgDateEnd();
		return super.listProcPage("{call SP_STAT_CUST_CONTRACT2(?,?,?,?,?,?,?,?,?,?)}", params, pageIndex, pageSize);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerStatLocal#getStatCustomerLevel(enfo.crm.vo.CustomerStatVO)
	 */
	@Override
	public List getStatCustomerLevel(CustomerStatVO vo) throws BusiException{
		Object[] params = new Object[2];		
		params[0] = vo.getFunc_id();
		params[1] = vo.getInputMan();		
		return super.listProcAll("{call SP_STAT_CUSTOMER_LEVEL(?,?)}",params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerStatLocal#getStatCustomerLevel_page(enfo.crm.vo.CustomerStatVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList getStatCustomerLevel_page(CustomerStatVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		Object[] params = new Object[4];		
		params[0] = vo.getFunc_id();
		params[1] = vo.getInputMan();
		params[2] = vo.getStartTime();			
		params[3] = vo.getStatScope();
		return super.listProcPage("{call SP_STAT_CUSTOMER_LEVEL(?,?,?,?)}", params, totalColumn, pageIndex, pageSize);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerStatLocal#getVolueStat(enfo.crm.vo.CustomerStatVO)
	 */
	@Override
	public  List getVolueStat(CustomerStatVO vo)throws BusiException{
		Object[]params = new Object[4];
		params[0] = vo.getProductId();
		params[1] = vo.getStartTime();
		params[2] = vo.getEndTime();
		params[3] = vo.getChangFlag();
		return super.listBySql("{call SP_QUERY_TCUSTOMER_VOLUE(?,?,?,?)}", params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerStatLocal#getAgeDistribution(enfo.crm.vo.CustomerStatVO)
	 */
	@Override
	public List getAgeDistribution(CustomerStatVO vo)throws BusiException{
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(vo.getStartTime(),new Integer(0));
		params[1] = Utility.parseInt(vo.getEndTime(),new Integer(0));
		params[2] = Utility.parseInt(vo.getStatScope(),new Integer(0));
		params[3] = vo.getInputMan();
		return super.listBySql("{call SP_QUERY_TCUSTOMER_AGE_DISTRIBUTION(?,?,?,?)}", params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerStatLocal#getCategoryStat(enfo.crm.vo.CustomerStatVO)
	 */
	@Override
	public List getCategoryStat(CustomerStatVO vo)throws BusiException{
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(vo.getStartTime(),new Integer(0));
		params[1] = Utility.parseInt(vo.getEndTime(),new Integer(0));
		params[2] = Utility.parseInt(vo.getStatScope(),new Integer(0));
		params[3] = vo.getInputMan();
		return super.listBySql("{call SP_QUERY_TCUSTOMER_TYPE_STATISTICS(?,?,?,?)}",params);		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerStatLocal#getSourceStat(enfo.crm.vo.CustomerStatVO)
	 */
	@Override
	public List getSourceStat(CustomerStatVO vo)throws BusiException{
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(vo.getEndTime(), new Integer(0));
		params[1] = Utility.parseInt(vo.getStatScope(), new Integer(0));
		params[2] = vo.getInputMan();
		return super.listBySql("{call SP_QUERY_TCUSTOMER_SOURCE(?,?,?)}",params);		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerStatLocal#getProvinceStat(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List getProvinceStat(Integer begin_date,Integer end_date)throws BusiException{
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(begin_date,new Integer(0));
		params[1] = Utility.parseInt(end_date,new Integer(0));
		return super.listBySql("{call SP_QUERY_TCUSTOMER_PROVINCE_STATISTICS(?,?)}", params);
	}
		
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerStatLocal#queryTeamProduct(enfo.crm.vo.TcustmanagersVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList queryTeamProduct(TcustmanagersVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		Object[] params = new Object[7];
		params[0] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
		params[1] = Utility.parseInt(vo.getTeam_id(),new Integer(0));
		params[2] = vo.getLeader_name();
		params[3] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		params[4] = Utility.parseInt(vo.getProduct_flag(), new Integer(2));
		params[5] = Utility.parseInt(vo.getTeam_flag(), new Integer(2));
		params[6] = Utility.parseInt(vo.getSale_flag(), new Integer(2));		
		return super.listProcPage("{call SP_QUERY_TPRODUCT_MARKETING(?,?,?,?,?,?,?)}",params,totalColumn,pageIndex,pageSize);		
	}
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerStatLocal#pagelist_TeamManager(enfo.crm.vo.TcustmanagersVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pagelist_TeamManager(TcustmanagersVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		Object[] params = new Object[5];
		params[0] = Utility.parseInt(vo.getTeam_id(),new Integer(0));
		params[1] = vo.getManagername();
		params[2] = vo.getLeader_name();
		params[3] = vo.getStartdate();
		params[4] = vo.getEnddate();
		
		return super.listProcPage("{call SP_QUERY_TCUSTOMERS_MANAGER(?,?,?,?,?)}",params,totalColumn,pageIndex,pageSize);		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerStatLocal#getSaleCostCust(enfo.crm.vo.CustomerVO, int, int)
	 */
	@Override
	public IPageList getSaleCostCust(CustomerVO vo,int pageIndex,int pageSize)throws BusiException{
		Object[] params = new Object[3];
		params[0] = vo.getCust_name();
		params[1] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
		params[2] = Utility.parseInt(vo.getInput_man(),new Integer(0));		
		return super.listProcPage("{call SP_QUERY_SALE_COST(?,?,?)}",params,pageIndex,pageSize);		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerStatLocal#getSaleCostProduct(enfo.crm.vo.CustomerVO, int, int)
	 */
	@Override
	public IPageList getSaleCostProduct(CustomerVO vo,int pageIndex,int pageSize) throws BusiException{
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
		params[1] = Utility.parseInt(vo.getCell_id(),new Integer(0));
		params[2] = Utility.parseInt(vo.getInput_man(),new Integer(0));		
		return super.listProcPage("{call SP_QUERY_SALE_COST_PRODUCT(?,?,?)}",params,pageIndex,pageSize);		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerStatLocal#queryDircetCustomerStat(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List queryDircetCustomerStat(Integer begin_date,Integer end_date,Integer input_man)throws BusiException{
		Object[] param = new Object[2];
		param[0] = Utility.parseInt(end_date, new Integer(0));
		param[1] = Utility.parseInt(input_man, new Integer(0));
		return super.listBySql("{call SP_QUERY_TCUSTOMER_DIRECT_STATISTIC(?,?)}", param);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerStatLocal#querySellingStat(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List querySellingStat(Integer begin_date,Integer end_date,Integer input_man)throws BusiException{
		Object[] param = new Object[3];
		param[0] = Utility.parseInt(begin_date, new Integer(0));
		param[1] = Utility.parseInt(end_date, new Integer(0));
		param[2] = Utility.parseInt(input_man, new Integer(0));
		return super.listBySql("{call SP_QUERY_DIRECT_SELLING_STAT(?,?,?)}", param);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerStatLocal#getPageVolueStat(enfo.crm.vo.CustomerStatVO, java.lang.String[], int, int)
	 */
	@Override
	public  IPageList getPageVolueStat(CustomerStatVO vo,String[] totalColumn,int pageIndex,int pageSize)throws BusiException{
		Object[] params = new Object[6];
		params[0] = vo.getProductId();
		params[1] = vo.getStartTime();
		params[2] = vo.getEndTime();
		params[3] = vo.getChangFlag();
		params[4] = vo.getStatScope();
		params[5] = vo.getInputMan();
		return super.listProcPage("{call SP_QUERY_TCUSTOMER_VOLUE(?,?,?,?,?,?)}", params,totalColumn, pageIndex, pageSize);
	}
}