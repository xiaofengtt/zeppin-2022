  
package enfo.crm.intrust;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.dao.IPageList;
import enfo.crm.dao.IntrustDBManager;
import enfo.crm.tools.Utility;
import enfo.crm.vo.TMoneyDataVO;

@Component(value="tMoneyData")
public class TMoneyDataBean extends enfo.crm.dao.CrmBusiExBean implements TMoneyDataLocal {
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.TMoneyDataLocal#listForPage(enfo.crm.vo.TMoneyDataVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList listForPage(
			TMoneyDataVO vo,
			String[] totalColumn,
			int pageIndex,
			int pageSize) throws BusiException{
		Object[] params = new Object[9];
		params[0] = vo.getSerial_no();
        params[1] = vo.getProduct_id();
        params[2] = vo.getSub_product_id();
        params[3] = vo.getCust_id();
        params[4] = vo.getCust_name();
        params[5] = vo.getCheck_flag();//11初审未审，1未审核，2已审核
        params[6] = vo.getComplete_flag();
        params[7] = vo.getInput_man();
        params[8] = vo.getPz_flag();
		return	CrmDBManager.listProcPage("{call SP_QUERY_TMONEYDATA(?,?,?,?,?,?,?,?,?)}"
									, params, totalColumn, pageIndex, pageSize);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.TMoneyDataLocal#listForPagePZ(enfo.crm.vo.TMoneyDataVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList listForPagePZ(
			TMoneyDataVO vo,
			String[] totalColumn,
			int pageIndex,
			int pageSize) throws BusiException{
		Object[] params = new Object[7];
		params[0] = vo.getSerial_no();
        params[1] = vo.getProduct_id();
        params[2] = vo.getSub_product_id();
        params[3] = vo.getCust_id();
        params[4] = vo.getCust_name();
      //  params[5] = vo.getCheck_flag();//11初审未审，1未审核，2已审核
       // params[6] = vo.getComplete_flag();
        params[5] =Utility.parseInt(vo.getInput_man(),new Integer(0));
        params[6] = vo.getPz_flag();
		return	CrmDBManager.listProcPage("{call SP_QUERY_TMONEYDATA_PZ(?,?,?,?,?,?,?)}"
									, params, totalColumn, pageIndex, pageSize);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.TMoneyDataLocal#listAll(enfo.crm.vo.TMoneyDataVO)
	 */
	@Override
	public List listAll(TMoneyDataVO vo) throws BusiException{
		Object[] params = new Object[9];
		params[0] = vo.getSerial_no();
        params[1] = vo.getProduct_id();
        params[2] = vo.getSub_product_id();
        params[3] = vo.getCust_id();
        params[4] = vo.getCust_name();
        params[5] = vo.getCheck_flag();//11初审未审，1未审核，2已审核
        params[6] = vo.getComplete_flag();
        params[7] = vo.getInput_man();
        params[8] = vo.getPz_flag();
        return super.listBySql("{call SP_QUERY_TMONEYDATA(?,?,?,?,?,?,?,?,?)}", params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.TMoneyDataLocal#append(enfo.crm.vo.TMoneyDataVO)
	 */
	@Override
	public void append(TMoneyDataVO vo) throws BusiException{
		String appendSql = "{?=call SP_ADD_TMONEYDATA_CRM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";		
        Object[] params = new Object[25];
        params[0] = vo.getBook_code();
        params[1] = vo.getProduct_id();
        params[2] = vo.getSub_product_id();
        params[3] = vo.getCust_id();
        params[4] = vo.getJk_date();
        params[5] = vo.getJk_type();
        params[6] = vo.getTo_money();
        params[7] = vo.getTo_amount();
        params[8] = vo.getSummary();
        params[9] = vo.getCity_serialno();
        params[10] = vo.getInput_man();
        params[11] = vo.getCust_name();
        params[12] = vo.getCust_type();
        params[13] = vo.getSbf_serial_no();
        params[14] = vo.getPre_serial_no();
        params[15] = vo.getDz_time();
        params[16] = vo.getOnway_flag();
        params[17] = vo.getFee_money();
        params[18] = vo.getBank_id();
        params[19] = vo.getBank_sub_name();
        params[20] = vo.getBank_acct();
        params[21] = vo.getProv_flag();
        params[22] = vo.getProv_level();
        params[23] = vo.getMoney_origin();
        params[24] = vo.getSub_money_origin();
        CrmDBManager.cudProc(appendSql, params);	
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.TMoneyDataLocal#modify(enfo.crm.vo.TMoneyDataVO)
	 */
	@Override
	public void modify(TMoneyDataVO vo) throws BusiException{
        Object[] params = new Object[17];
        params[0] = vo.getSerial_no();
        params[1] = vo.getSub_product_id();
        params[2] = vo.getJk_date();
        params[3] = vo.getJk_type();
        params[4] = vo.getTo_money();
        params[5] = vo.getTo_amount();
        params[6] = vo.getSummary();
        params[7] = vo.getCity_serialno();
        params[8] = vo.getInput_man();
        params[9] = vo.getFee_money();
        params[10] = vo.getBank_id();
        params[11] = vo.getBank_sub_name();
        params[12] = vo.getBank_acct();
        params[13] = vo.getProv_flag();
        params[14] = vo.getProv_level();
        params[15] = vo.getMoney_origin();
        params[16] = vo.getSub_money_origin();
        CrmDBManager.cudProc("{?=call SP_MODI_TMONEYDATA_CRM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", params);	
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.TMoneyDataLocal#delete(enfo.crm.vo.TMoneyDataVO)
	 */
	@Override
	public void delete(TMoneyDataVO vo) throws BusiException{
        Object[] params = new Object[2];
        params[0] = vo.getSerial_no();
        params[1] = vo.getInput_man();	
        CrmDBManager.cudProc("{?=call SP_DEL_TMONEYDATA(?,?)}", params);	
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.TMoneyDataLocal#input_check(enfo.crm.vo.TMoneyDataVO)
	 */
	@Override
	public void input_check(TMoneyDataVO vo) throws BusiException{
        Object[] params = new Object[3];        
        params[0] = vo.getSerial_no();
        params[1] = vo.getCheck_flag();
        params[2] = vo.getInput_man();        
        CrmDBManager.cudProc("{?=call SP_CHECK_TMONEYDATA_INPUT(?,?,?)}", params);			
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.TMoneyDataLocal#check(enfo.crm.vo.TMoneyDataVO)
	 */
	@Override
	public void check(TMoneyDataVO vo) throws BusiException{
        Object[] params = new Object[3];        
        params[0] = vo.getSerial_no();
        params[1] = vo.getInput_man();
        params[2] = vo.getCheck_flag();        
        IntrustDBManager.cudProc("{?=call SP_CHECK_TMONEYDATA(?,?,?)}", params);			
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.TMoneyDataLocal#getTmoneydata(java.lang.String)
	 */
	@Override
	public List getTmoneydata(String serial_no_list) throws BusiException{
		Object[] params = new Object[1];
	    params[0] = serial_no_list;	    
	    return IntrustDBManager.listBySql("{call SP_QUERY_TMONEYDATA_LIST(?)}", params);
	}
	
	 /* (non-Javadoc)
	 * @see enfo.crm.intrust.TMoneyDataLocal#combineContract(enfo.crm.vo.TMoneyDataVO)
	 */
    @Override
	public void combineContract(TMoneyDataVO vo) throws Exception {    	
        Object[] params = new Object[30];        
        params[0] = vo.getProduct_id();
        params[1] = Utility.parseInt(vo.getSub_product_id(),new Integer(0));
        params[2] = vo.getSerial_no_list();
        params[3] = vo.getContract_sub_bh();
        params[4] = vo.getQs_date();
        params[5] = vo.getProv_flag();
        params[6] = vo.getProv_level();
        params[7] = vo.getBank_id();
        params[8] = vo.getBank_sub_name();
        params[9] = vo.getBank_acct();
        
        params[10] = vo.getGain_acct();
        params[11] = vo.getInput_man();
        params[12] = vo.getBank_acct_type();
        params[13] = vo.getBonus_flag();
        params[14] = vo.getBonus_rate();
        params[15] = vo.getWith_bank_flag();
        params[16] = vo.getHt_bank_id();
        params[17] = vo.getHt_bank_sub_name();
        params[18] = vo.getWith_security_flag();
        params[19] = vo.getWith_private_flag();
        params[20] = vo.getCity_serialno();
        if ("new".equalsIgnoreCase(vo.getApplyreach_flag())) params[21] = new Integer(1);
        params[22] = new Integer(1); //新合同
        params[23] = new Integer(0); //TBENIFITOR.SERIAL_NO
        params[24] = new Integer(0);
        params[25] = new Integer(0); //委托人custid
        params[26] = null; //合同期限
        params[27] = null; //合同期限的单位
        params[28] = vo.getMoney_origin();
        params[29] = vo.getSub_money_origin();
    	
        try{
        	String combineSql = "{?=call SP_COMBINE_TMONEYDATA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        	IntrustDBManager.cudProc(combineSql, params);
        }catch(Exception e){
			throw new BusiException("生成认购合同失败," + e.getMessage());
		}
    }
    
    
    
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.TMoneyDataLocal#modiSubproductid(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void modiSubproductid(Integer serial_no,Integer sub_product_id,Integer product_id)throws Exception{
		String sql = "UPDATE TMONEYDATA SET SUB_PRODUCT_ID ="+sub_product_id+" WHERE SERIAL_NO="+serial_no +" AND PRODUCT_ID = "+ product_id;
		Connection conn = null;
		Statement stmt = null;
		try {
			conn =IntrustDBManager.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			} finally {
				if(stmt != null)
					stmt.close();
				if(conn != null)
					conn.close();
			}
	}
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.TMoneyDataLocal#listContractMendForPage(enfo.crm.vo.TMoneyDataVO, java.lang.String[], int, int)
	 */
    @Override
	public IPageList listContractMendForPage(
			TMoneyDataVO vo,
			String[] totalColumn,
			int pageIndex,
			int pageSize) throws BusiException{
		Object[] params = new Object[7];
        params[0] = Utility.parseInt(vo.getBook_code(), new Integer(1));
        params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
        params[2] = Utility.parseInt(vo.getSub_product_id(),new Integer(0));
        params[3] = vo.getContract_bh();
        params[4] = vo.getContract_sub_bh();
        params[5] = Utility.parseInt(vo.getCheck_flag(), new Integer(0));
        params[6] = vo.getInput_man();
		return	super.listProcPage("{call SP_QUERY_TCONTRACT_MEND(?,?,?,?,?,?,?)}"
									, params, totalColumn, pageIndex, pageSize);
	}
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.TMoneyDataLocal#listContractMend(enfo.crm.vo.TMoneyDataVO)
	 */
    @Override
	public List listContractMend(TMoneyDataVO vo) throws BusiException{
    	Object[] params = new Object[7];
        params[0] = Utility.parseInt(vo.getBook_code(), new Integer(1));
        params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
        params[2] = Utility.parseInt(vo.getSub_product_id(),new Integer(0));
        params[3] = vo.getContract_bh();
        params[4] = vo.getContract_sub_bh();
        params[5] = Utility.parseInt(vo.getCheck_flag(), new Integer(0));
        params[6] = vo.getInput_man();
        return super.listBySql("{call SP_QUERY_TCONTRACT_MEND(?,?,?,?,?,?,?)}", params);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.TMoneyDataLocal#checkPurchanseContractMend(enfo.crm.vo.TMoneyDataVO)
	 */
    @Override
	public void checkPurchanseContractMend(TMoneyDataVO vo)throws Exception {
        Object[] params = new Object[4];
        params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
        params[1] = vo.getCheck_flag();
        params[2] = Utility.trimNull(vo.getCheck_summary());
        params[3] = Utility.parseInt(vo.getInput_man(), new Integer(0));        
        CrmDBManager.cudProc("{?=call SP_CHECK_TCONTRACT_MEND(?,?,?,?)}", params);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.TMoneyDataLocal#queryContractRestore(enfo.crm.vo.TMoneyDataVO, int, int)
	 */
	@Override
	public IPageList queryContractRestore(TMoneyDataVO vo,int pageIndex,int pageSize) throws Exception {
		IPageList pageList = null;
		Object[] param = new Object[6];
		param[0] = Utility.parseInt(vo.getBook_code(), new Integer(0));
		param[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		param[2] = Utility.parseInt(vo.getSub_product_id(), new Integer(0));
		param[3] = Utility.trimNull(vo.getContract_bh());
		param[4] = Utility.trimNull(vo.getContract_sub_bh());
		param[5] = vo.getInput_man();
        try {
        	pageList = super.listProcPage("{call SP_QUERY_TCONTRACT_RESTORE(?,?,?,?,?,?)}", param,pageIndex,pageSize);
        } catch (BusiException e) {
            throw new BusiException("认购合同生成恢复查询失败"+e.getMessage());
        }
        return pageList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.TMoneyDataLocal#combineMoneyDataRestore(enfo.crm.vo.TMoneyDataVO)
	 */
	@Override
	public void combineMoneyDataRestore(TMoneyDataVO vo) throws Exception {
		Object[] param = new Object[3];
		param[0] = Utility.parseInt(vo.getContract_id(), new Integer(0)); 
		param[1] = Utility.parseInt(vo.getHt_type(), new Integer(0));
		param[2] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		
		try { 
			IntrustDBManager.cudProc("{?=call SP_COMBINE_TMONEYDATA_RESTORE(?,?,?)}", param);
		} catch (Exception e) {
			throw new BusiException("认购合同生成恢复: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.TMoneyDataLocal#modiWTR(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public void modiWTR(Integer serial_no,Integer cust_id,String cust_name)throws Exception{
		String sql = "UPDATE TMONEYDATA SET CUST_ID ="+cust_id+" WHERE SERIAL_NO="+serial_no;
		Connection conn = null;
		Statement stmt = null;
		try {
			conn =IntrustDBManager.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			} finally {
				if(stmt != null)
					stmt.close();
				if(conn != null)
					conn.close();
			}
	}
}