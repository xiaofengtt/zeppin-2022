package enfo.crm.marketing;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.CommissionRateVO;
import enfo.crm.vo.SaleParameterVO;

@Component(value="saleParameter")
public class SaleParameterBean extends enfo.crm.dao.CrmBusiExBean implements SaleParameterLocal {
	String sqlquery="{call SP_QUERY_TTEAMQUOTA(?,?,?,?,?,?)}";
	String sqlmodi="{?= call SP_MODI_TTEAMQUOTA(?,?,?,?,?,?,?,?)}";
	String sqlmodiSubscribe="{?= call SP_MODI_TTEAMQUOTA_SUBSCRIBE(?,?,?,?,?,?,?,?,?)}";
	
	String adjustmodi="{?= call SP_ADJUST_TEAMQUOTA(?,?,?,?,?,?,?,?)}";
	
	String sqlqueryQnum="{call SP_QUERY_QUALIFIED_NUM(?)}";
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SaleParameterLocal#modi(enfo.crm.vo.SaleParameterVO, java.lang.Integer)
	 */
	@Override
	public void modi(SaleParameterVO vo,Integer input_operatorCode) throws BusiException {
		Object[] params = new Object[8];
		params[0] = Utility.parseInt(vo.getSerial_NO(),new Integer(0));
		params[1] = Utility.parseInt(vo.getProductID(),new Integer(0));
		params[2] = vo.getTeamID();
		params[3] = vo.getQuotaMoney();
		params[4] = Utility.parseInt(vo.getQuota_qualified_num(),new Integer(0));
		params[5] = Utility.parseInt(input_operatorCode,new Integer(0));
		params[6] = Utility.parseInt(vo.getPreproduct_id(),new Integer(0));
		params[7] = Utility.parseInt(vo.getSub_product_id(),new Integer(0));
//		params[8] = Utility.parseInt(vo.getTz_qualified_num(),new Integer(0));
 		super.cudProc(sqlmodi,params);
 		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SaleParameterLocal#query(enfo.crm.vo.SaleParameterVO, java.lang.String[], java.lang.Integer, int, int)
	 */
	@Override
	public IPageList query(SaleParameterVO vo, String[] totalColumn,Integer input_operatorCode,int pageIndex, int pageSize) throws BusiException {
		Object[] param = new Object[6];
		IPageList rsList = null;
		param[0] = Utility.parseInt(vo.getTeamID(),new Integer(0));
		param[1] = Utility.parseInt(vo.getProductID(),new Integer(0));
		param[2] = Utility.parseInt(input_operatorCode,new Integer(0));
		param[3] = Utility.parseInt(vo.getPre_product_id(),new Integer(0));
		param[4] = Utility.parseInt(vo.getService_man(),new Integer(0));
		param[5] = Utility.parseInt(vo.getSub_product_id(),new Integer(0));
		rsList = super.listProcPage(sqlquery,param,totalColumn,pageIndex,pageSize);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SaleParameterLocal#queryQuota(enfo.crm.vo.SaleParameterVO, java.lang.Integer)
	 */
	@Override
	public List queryQuota(SaleParameterVO vo,Integer input_operatorCode) throws BusiException {
		Object[] param = new Object[6];
		List rsList = null;
		param[0] = Utility.parseInt(vo.getTeamID(),new Integer(0));
		param[1] = Utility.parseInt(vo.getProductID(),new Integer(0));
		param[2] = Utility.parseInt(input_operatorCode,new Integer(0));
		param[3] = Utility.parseInt(vo.getPre_product_id(),new Integer(0));
		param[4] = Utility.parseInt(vo.getService_man(),new Integer(0));
		param[5] = Utility.parseInt(vo.getSub_product_id(),new Integer(0));
		rsList = super.listBySql(sqlquery,param); 
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SaleParameterLocal#getQualified_num(enfo.crm.vo.SaleParameterVO)
	 */
	@Override
	public List getQualified_num(SaleParameterVO vo) throws BusiException {
		Object[] param = new Object[1];
		List rsList = null;
		param[0] = Utility.parseInt(vo.getProductID(),new Integer(0));
		rsList = super.listBySql(sqlqueryQnum,param); 
		return rsList; 
	}
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SaleParameterLocal#modiAlreadysale(enfo.crm.vo.SaleParameterVO, java.lang.Integer)
	 */
	@Override
	public void modiAlreadysale(SaleParameterVO vo,Integer input_operatorCode) throws BusiException {
		Object[] params = new Object[9];
		params[0] = vo.getSerial_NO();
		params[1] = vo.getProductID();
		params[2] = vo.getTeamID();
		params[3] = vo.getAlreadysale();
		params[4] = vo.getSerial_no_subscribe();
		params[5] = vo.getAlready_qualified_num();
		params[6] = Utility.parseInt(input_operatorCode,new Integer(0));
		params[7] = vo.getPreproduct_id();
		params[8] = Utility.parseInt(vo.getSub_product_id(),new Integer(0));
 		super.cudProc(sqlmodiSubscribe,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SaleParameterLocal#queryTeam(enfo.crm.vo.SaleParameterVO, java.lang.Integer)
	 */
	@Override
	public List queryTeam(SaleParameterVO vo,Integer input_operatorCode) throws BusiException {
		Object[] param = new Object[4];
		List rsList = null;
		param[0] = Utility.trimNull(vo.getTeamID());
		param[1] = Utility.trimNull(vo.getProductID());
		
		param[2] = Utility.parseInt(input_operatorCode,new Integer(0));
		param[3] = Utility.parseInt(vo.getPreproduct_id(),new Integer(0));
		rsList = super.listBySql(sqlquery,param);
		return rsList;
	}	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SaleParameterLocal#adjustQuota(enfo.crm.vo.SaleParameterVO, java.lang.Integer)
	 */
	@Override
	public void adjustQuota(SaleParameterVO vo,Integer input_operatorCode) throws BusiException {
		Object[] params = new Object[8];
		params[0] = vo.getFunction_id();
		params[1] = Utility.parseInt(vo.getProductID(),new Integer(0));
		params[2] = vo.getPreproduct_id();
		params[3] = vo.getTeamID();
		params[4] = vo.getQuotaMoney();
		params[5] = vo.getQuota_qualified_num();
		params[6] = Utility.parseInt(vo.getTeam_id2(),new Integer(0));
		params[7] = Utility.parseInt(input_operatorCode,new Integer(0));
		
 		super.cudProc(adjustmodi,params);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SaleParameterLocal#queryTeamHis(enfo.crm.vo.SaleParameterVO, java.lang.Integer)
	 */
	@Override
	public List queryTeamHis(SaleParameterVO vo,Integer input_operatorCode) throws BusiException {
		List rsList = super.listBySql("SELECT PREPRODUCT_ID, LIST_ID, MAX(ADJUST_TIME) AS ADJUST_TIME FROM HTEAMQUOTA " +
					" WHERE PREPRODUCT_ID = "+ vo.getPreproduct_id() +
					" GROUP BY PREPRODUCT_ID, LIST_ID ");
		return rsList;
	}	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SaleParameterLocal#queryTeamHisDetail(enfo.crm.vo.SaleParameterVO, java.lang.Integer)
	 */
	@Override
	public List queryTeamHisDetail(SaleParameterVO vo,Integer input_operatorCode) throws BusiException {
		List rsList = super.listBySql("SELECT A.*,B.TEAM_NAME FROM HTEAMQUOTA A,TManagerTeams B  WHERE A.TEAM_ID= B.TEAM_ID " +
				 " AND PREPRODUCT_ID = "+ vo.getPreproduct_id() +
				 " AND LIST_ID = "+ vo.getList_id());
		return rsList;
	}	
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SaleParameterLocal#queryTTeamValue(java.lang.Integer)
	 */
	@Override
	public List queryTTeamValue(Integer product_id)throws BusiException{
		
		List list = super.listBySql("SELECT DISTINCT A.TEAM_ID,A.TEAM_NAME,SUM(B.QUOTAMONEY) AS QUOTAMONEY,SUM(B.ALREADYSALE) AS ALREADYSALE,"+
				"SUM(B.QUOTA_QUALIFIED_NUM) AS QUOTA_QUALIFIED_NUM,SUM(B.ALREADY_QUALIFIED_NUM) AS ALREADY_QUALIFIED_NUM,SUM(B.TZ_QUALIFIED_NUM) AS TZ_QUALIFIED_NUM "+
                "FROM TManagerTeams A LEFT JOIN TTEAMQUOTA B ON (A.TEAM_ID = B.TEAM_ID AND B.PRODUCT_ID = "+product_id+") "+
                "LEFT JOIN HTEAMQUOTA C ON C.LIST_ID = 0 AND B.TEAM_ID = C.TEAM_ID AND B.PREPRODUCT_ID = C.PREPRODUCT_ID,"+
                "INTRUST..TPRODUCT P  WHERE ISNULL(B.PRODUCT_ID,"+product_id+") = P.PRODUCT_ID AND A.PARENT_ID = 0 OR A.PARENT_ID IS NULL "+
				"GROUP BY A.TEAM_ID,A.TEAM_NAME ORDER BY A.TEAM_ID,A.TEAM_NAME");
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SaleParameterLocal#queryTTeamValue1(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List queryTTeamValue1(Integer product_id,Integer leader,Integer sub_productId)throws BusiException{
		List list = null;
		Object[] param = new Object[5];
		param[0] = new Integer(0);
		param[1] = product_id;
		param[2] = leader;
		param[3] = new Integer(0);
		param[4] = sub_productId;
		list = super.listBySql("{call SP_QUERY_TTEAMQUOTA_SUBTEAM(?,?,?,?,?)}",param);
		return list;
	}
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SaleParameterLocal#queryPersonValue(enfo.crm.vo.SaleParameterVO)
	 */
	@Override
	public List queryPersonValue(SaleParameterVO vo)throws BusiException{
		 
		List list = null;
		Object[] param = new Object[5];
		param[0] = Utility.parseInt(vo.getTeamID(),new Integer(0));
		param[1] = Utility.parseInt(vo.getProductID(),new Integer(0));
		param[2] = Utility.parseInt(vo.getSub_product_id(),new Integer(0));
		param[3] = Utility.parseInt(vo.getPre_product_id(),new Integer(0));
		param[4] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		
		list = super.listBySql("{call SP_QUERY_TPERSONALQUOTA(?,?,?,?,?)}",param);
		
		return list;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SaleParameterLocal#modiPersonValue(enfo.crm.vo.SaleParameterVO)
	 */
	@Override
	public void modiPersonValue(SaleParameterVO vo)throws BusiException{
		List list = null;
		Object[] param = new Object[10];
		param[0] = Utility.parseInt(vo.getSerial_NO(),new Integer(0));
		param[1] = Utility.parseInt(vo.getProductID(),new Integer(0));
		param[2] = Utility.parseInt(vo.getSub_product_id(),new Integer(0));
		param[3] = Utility.parseInt(vo.getTeamID(),new Integer(0));
		param[4] = Utility.parseInt(vo.getService_man(),new Integer(0));
		param[5] = Utility.parseDecimal(Utility.trimNull(vo.getQuotaMoney()),new BigDecimal(0));
		param[6] = Utility.parseInt(vo.getQuota_qualified_num(),new Integer(0));
		param[7] = Utility.parseInt(vo.getPreproduct_id(),new Integer(0));		
		param[8] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		param[9] = Utility.parseInt(vo.getTz_qualified_num(),new Integer(0));
		super.cudProc("{ ? = call SP_MODI_TPERSONALQUOTA(?,?,?,?,?,?,?,?,?,?)}",param);
	}
    
     /* (non-Javadoc)
	 * @see enfo.crm.marketing.SaleParameterLocal#addCommissionRate(enfo.crm.vo.CommissionRateVO)
	 */
    @Override
	public void addCommissionRate(CommissionRateVO vo)throws Exception{
        Object[] params = new Object[10];
        params[0] = vo.getProductId();
        params[1] = vo.getSubproductId();
        params[2] = vo.getPeriod();
        params[3] = vo.getPeriodUnit();        
        params[4] = vo.getTradeStartMoney();
        params[5] = vo.getTradeEndMoney();
        params[6] = vo.getFeeRate();
        params[7] = vo.getFeeAmount();
        params[8] = Utility.trimNull(vo.getSummary());        
        params[9] = vo.getInputMan();
        super.cudProc("{?= call SP_ADD_TCOMMISSIONRATE(?,?,?,?,?,?,?,?,?,?)}",params);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.marketing.SaleParameterLocal#delCommissionRate(enfo.crm.vo.CommissionRateVO)
	 */
    @Override
	public void delCommissionRate(CommissionRateVO vo)throws Exception{
        Object[] params = new Object[3];
        params[0] = vo.getProductId();
        params[1] = vo.getSubproductId();      
        params[2] = vo.getInputMan();
        super.cudProc("{?= call SP_DEL_TCOMMISSIONRATE(?,?,?)}",params);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.marketing.SaleParameterLocal#queryCommissionRate(enfo.crm.vo.CommissionRateVO)
	 */
    @Override
	public List queryCommissionRate(CommissionRateVO vo)throws Exception{
        Object[] params = new Object[3];
        params[0] = vo.getProductId();
        params[1] = vo.getSubproductId();      
        params[2] = vo.getInputMan();
        return super.listBySql("{call SP_QUERY_TCOMMISSIONRATE(?,?,?)}",params);
    }
        
    /* (non-Javadoc)
	 * @see enfo.crm.marketing.SaleParameterLocal#queryManagerCommission(enfo.crm.vo.SaleParameterVO, int, int)
	 */
    @Override
	public IPageList queryManagerCommission(SaleParameterVO vo, int page, int pagesize) 
    		throws BusiException {
    	Object[] params = new Object[6];
        params[0] = vo.getProductID();
        params[1] = vo.getSub_product_id();   
        params[2] = vo.getService_man();
        params[3] = vo.getCust_id(); 
        params[4] = vo.getCust_name();
        params[5] = vo.getInput_man();
        return super.listProcPage("{call SP_QUERY_TManagerCOMMISSION(?,?,?,?,?,?)}",params, page, pagesize);   	
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.marketing.SaleParameterLocal#calManagerCommission(enfo.crm.vo.SaleParameterVO)
	 */
    @Override
	public void calManagerCommission(SaleParameterVO vo) 
    		throws BusiException {
    	Object[] params = new Object[3];
        params[0] = vo.getProductID();
        params[1] = vo.getSub_product_id();   
        params[2] = vo.getInput_man();
        super.cudProc("{? = call SP_CAL_TManagerCOMMISSION(?,?,?)}",params);   	
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.marketing.SaleParameterLocal#queryTeamOrPersonQuota(enfo.crm.vo.SaleParameterVO, java.lang.String[], int, int)
	 */
    @Override
	public IPageList queryTeamOrPersonQuota(SaleParameterVO vo, String[] totalColumn,int page, int pagesize)throws BusiException{
    	Object[] params = new Object[5];
    	params[0] = vo.getProductID();
        params[1] = vo.getSub_product_id();   
        params[2] = vo.getPre_product_id();
        params[3] = vo.getTeam_type(); 
        params[4] = vo.getTeamID();
        return super.listProcPage("{call SP_QUERY_TEAMQUOTA(?,?,?,?,?)}",params, page, pagesize);   
    }
    
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SaleParameterLocal#queryTTeamValue2(enfo.crm.vo.SaleParameterVO)
	 */
	@Override
	public List queryTTeamValue2(SaleParameterVO vo)throws BusiException{

		List list = null;
		
		Object[] param = new Object[6];
		param[0] = Utility.parseInt(vo.getTeamID(),new Integer(0));
		param[1] = Utility.parseInt(vo.getProductID(),new Integer(0));
		param[2] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		param[3] = Utility.parseInt(vo.getPre_product_id(),new Integer(0));
		param[4] = Utility.parseInt(vo.getSub_product_id(),new Integer(0));
		param[5] = Utility.parseInt(vo.getQueryAll(),new Integer(0));

		list = super.listBySql("{call SP_QUERY_TTEAMQUOTA_SUBTEAM2(?,?,?,?,?,?)}",param);
		
		return list;
	}
    /* (non-Javadoc)
	 * @see enfo.crm.marketing.SaleParameterLocal#queryPersonValueNew(enfo.crm.vo.SaleParameterVO)
	 */
	@Override
	public List queryPersonValueNew(SaleParameterVO vo)throws BusiException{
		
		List list = null;
		Object[] param = new Object[6];
		param[0] = Utility.parseInt(vo.getTeamID(),new Integer(0));
		param[1] = Utility.parseInt(vo.getProductID(),new Integer(0));
		param[2] = Utility.parseInt(vo.getSub_product_id(),new Integer(0));
		param[3] = Utility.parseInt(vo.getPre_product_id(),new Integer(0));
		param[4] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		param[5] = Utility.parseInt(vo.getQueryAll(),new Integer(0));
		list = super.listBySql("{call SP_QUERY_TPERSONALQUOTA(?,?,?,?,?,?)}",param);
		
		return list;
	}
}
