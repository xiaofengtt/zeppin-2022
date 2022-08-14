 
package enfo.crm.intrust;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.dao.IPageList;
import enfo.crm.dao.IntrustDBManager;
import enfo.crm.tools.Utility;
import enfo.crm.vo.MoneyDetailVO;

@Component(value="moneyDetail")
public class MoneyDetailBean extends enfo.crm.dao.IntrustBusiExBean implements MoneyDetailLocal {

	/**
	 * 查询客户缴款数据详细信息
	 */
	private static final String listSql =	"{call SP_QUERY_TMONEYDETAIL (?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	/**
	 * 查询缴款合同信息
	 */
	private static final String listContractJkBaseSql = "{call SP_QUERY_TCONTRACT_JKBASE(?,?,?,?,?,?)}";
	
	/**
	 * 增加缴款信息
	 */
	 private static final String appendSql = "{?=call SP_ADD_TMONEYDETAIL (?,?,?,?,?,?,?,?,?,?,?)}";
	 
	/**
	 * 修改缴款信息
	 */
	 private static final String modiSql = "{?=call SP_MODI_TMONEYDETAIL (?,?,?,?,?,?,?,?)}";
	 
	/**
	 * 删除缴款信息
	 */
	 private static final String delSql = "{?=call SP_DEL_TMONEYDETAIL (?,?)}";
	/**
	 * 查询客户缴款数据详细信息
	 */
	private static final String listDetailSql =	"{call SP_QUERY_TCUSTOMER_TRADE_DETAIL (?,?,?,?)}";
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.MoneyDetailLocal#listBySql(enfo.crm.vo.MoneyDetailVO)
	 */
	@Override
	public List listBySql(MoneyDetailVO vo) throws BusiException {
		Object[] params = new Object[19];
		params[0] = vo.getBook_code();
		params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[2] = vo.getContract_bh();
		params[3] = Utility.parseInt(vo.getCheck_flag(), new Integer(0));
		params[4] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		params[5] = new Integer(0);
		params[6] = vo.getCust_name();
		params[7] = vo.getCust_no();
		params[8] =
			Utility.parseBigDecimal(
				vo.getMin_to_money(),
				new java.math.BigDecimal(0));
		params[9] =
			Utility.parseBigDecimal(
				vo.getMax_to_money(),
				new java.math.BigDecimal(0));
		params[10] = new Integer(0);
		params[11] = Utility.parseInt(vo.getCheck_flag(), new Integer(0));
		params[12] = Utility.parseInt(vo.getCard_lost_flag(), new Integer(0));
		params[13] = vo.getContract_sub_bh();//null;
		
        params[14] = Utility.parseInt(vo.getCell_flag(), new Integer(1));
        params[15] = Utility.parseInt(vo.getCell_id(), new Integer(0));
        params[16] = Utility.parseInt(vo.getDepart_id(), new Integer(0));
        params[17] = Utility.parseInt(vo.getIntrust_flag(), new Integer(0));
        params[18] = Utility.parseInt(vo.getSub_product_id(), new Integer(0));
		return super.listProcAll("{call SP_QUERY_TMONEYDETAIL (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.MoneyDetailLocal#query_page(enfo.crm.vo.MoneyDetailVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList query_page(MoneyDetailVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
        Object[] params = new Object[19];
		
		params[0] = vo.getBook_code();
		params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[2] = vo.getContract_bh();
		params[3] = Utility.parseInt(vo.getCheck_flag(), new Integer(0));
		params[4] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		params[5] = new Integer(0);
		params[6] = vo.getCust_name();
		params[7] = vo.getCust_no();
		params[8] =
			Utility.parseBigDecimal(
				vo.getMin_to_money(),
				new java.math.BigDecimal(0));
		params[9] =
			Utility.parseBigDecimal(
				vo.getMax_to_money(),
				new java.math.BigDecimal(0));
		params[10] = new Integer(0);
		params[11] = Utility.parseInt(vo.getCheck_flag(), new Integer(0));
		params[12] = Utility.parseInt(vo.getCard_lost_flag(), new Integer(0));
		params[13] = vo.getContract_sub_bh();//null;
        
        params[14] = Utility.parseInt(vo.getCell_flag(), new Integer(1));
        params[15] = Utility.parseInt(vo.getCell_id(), new Integer(0));
        params[16] = Utility.parseInt(vo.getDepart_id(), new Integer(0));
        params[17] = Utility.parseInt(vo.getIntrust_flag(), new Integer(0));
        params[18] = Utility.parseInt(vo.getSub_product_id(), new Integer(0));
		try {
			return 
                super.listProcPage("{call SP_QUERY_TMONEYDETAIL (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
                                        params,totalColumn,pageIndex,pageSize);
		} catch (BusiException e) {
			throw new BusiException("缴款信息查询失败:" + e.getMessage());
		}			
	}
    
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.MoneyDetailLocal#queryContractJkBase(enfo.crm.vo.MoneyDetailVO)
	 */
	@Override
	public Object[] queryContractJkBase(MoneyDetailVO vo) throws BusiException{
			Object[] ret = new Object[4];
	        Connection conn = null;
	        CallableStatement stmt = null;
	        	
			try {
				conn = IntrustDBManager.getConnection();
				stmt = conn.prepareCall(listContractJkBaseSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
		        if (vo.getProduct_id() .intValue()!= 0){
		        	  stmt.setInt(1, vo.getProduct_id().intValue());
		        }		         
		        else{
		            stmt.setInt(1, 0);
		        }
		        stmt.setString(2, vo.getContract_bh());
		        stmt.registerOutParameter(3, java.sql.Types.VARCHAR);
		        stmt.registerOutParameter(4, java.sql.Types.DECIMAL);
		        stmt.registerOutParameter(5, java.sql.Types.DECIMAL);
		        stmt.registerOutParameter(6, java.sql.Types.VARCHAR);
		        stmt.executeUpdate();		      

	        	ret[0] = stmt.getString(3);
	        	ret[1] = stmt.getBigDecimal(4);
	        	ret[2] = stmt.getBigDecimal(5);
	        	ret[3] = stmt.getString(6);
			}
			catch (Exception e) {
				throw new BusiException("查询缴款合同信息失败:" + e.getMessage());
			}
			finally {
	            try {
					stmt.close();
					conn.close();
				} catch (SQLException e1) {
					throw new BusiException("关闭数据流失败:" + e1.getMessage());
				}
	            
	        }		  
			return ret;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.MoneyDetailLocal#append(enfo.crm.vo.MoneyDetailVO)
	 */
	@Override
	public Integer append(MoneyDetailVO vo) throws Exception {
        Object[] params = new Object[10];
        
        params[0] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
        params[1] = vo.getContract_bh();
        params[2] = Utility.parseBigDecimal(vo.getTo_money(), new java.math.BigDecimal( 0));
        params[3] = Utility.parseBigDecimal(vo.getTo_money(), new java.math.BigDecimal(0));
        params[4] = vo.getJk_type();
        params[5] = vo.getSummary();
        params[6] = Utility.parseInt(vo.getInput_man(), new Integer(0));
        params[7] = Utility.parseInt(vo.getList_id(), new Integer(0));
        params[8] = Utility.parseInt(vo.getDz_date(), new Integer(0));
        params[9] = Utility.parseBigDecimal(vo.getFee_money(), new java.math.BigDecimal(0));
        try {
            return (Integer)super.cudProc(appendSql, params, 12, Types.INTEGER);
        } catch (BusiException e) {
            throw new BusiException("新建缴款失败:" + e.getMessage());
        }
    } 
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.MoneyDetailLocal#modi(enfo.crm.vo.MoneyDetailVO)
	 */
	@Override
	public void modi(MoneyDetailVO vo) throws BusiException{
	        Object[] params = new Object[8];
	        params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
	        params[1] = Utility.parseBigDecimal(vo.getTo_money(), new java.math.BigDecimal(0));
	        params[2] = Utility.parseBigDecimal(vo.getTo_money(), new java.math.BigDecimal(0));
	        params[3] = vo.getJk_type();
	        params[4] = vo.getSummary();
	        params[5] = Utility.parseInt(vo.getInput_man(), new Integer(0));
	        params[6] = Utility.parseInt(vo.getDz_date(), new Integer(0));
	        params[7] = Utility.parseBigDecimal(vo.getFee_money(), new java.math.BigDecimal(0));
	        
	        try {
	            super.cudProc(modiSql, params);
	        } catch (BusiException e) {
	            throw new BusiException("修改缴款失败:" + e.getMessage());
	        }
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.MoneyDetailLocal#delete(enfo.crm.vo.MoneyDetailVO)
	 */
    @Override
	public void delete(MoneyDetailVO vo) throws Exception {
        Object[] params = new Object[2];
        params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
        params[1] = Utility.parseInt(vo.getInput_man(), new Integer(0));
        try {
            super.cudProc(delSql, params);
        } catch (BusiException e) {
            throw new BusiException("删除缴款失败:" + e.getMessage());
        }
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.MoneyDetailLocal#load(enfo.crm.vo.MoneyDetailVO)
	 */
    @Override
	public List load(MoneyDetailVO vo) throws BusiException{
    	List list = new ArrayList();
    	String sqlStr = "{call SP_QUERY_TMONEYDETAIL_LOAD(?)}";
		Object[] params = new Object[1];
		params[0] = vo.getSerial_no();
		
		try {
			list = super.listProcAll(sqlStr, params);
		} catch (BusiException e) {
			throw new BusiException("搜索缴款信息失败:" + e.getMessage());
		}
		return list;    
    }
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.MoneyDetailLocal#listTradeDetail(enfo.crm.vo.MoneyDetailVO)
	 */
	@Override
	public List listTradeDetail(MoneyDetailVO vo) throws BusiException {
		List list = new ArrayList();
		Object[] params = new Object[4];
		params[0] = vo.getBook_code();
		params[1] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		params[2] = Utility.parseInt(vo.getStart_date(), new Integer(0));
		params[3] = Utility.parseInt(vo.getEnd_date(), new Integer(0));
//		params[4] = vo.getBen_card_no();
		list = super.listProcAll(listDetailSql, params);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.MoneyDetailLocal#listForRebate(enfo.crm.vo.MoneyDetailVO)
	 */
	@Override
	public List listForRebate(MoneyDetailVO vo) throws BusiException{
		List list = new ArrayList();
		Object[] params = new Object[2];
		
		params[0] = vo.getProduct_id();
		params[1] = vo.getSerial_no();
		list = super.listProcAll("{call SP_QUERY_TMONEYDETAIL_REBATE(?,?)}", params);
		return list;
	}
}