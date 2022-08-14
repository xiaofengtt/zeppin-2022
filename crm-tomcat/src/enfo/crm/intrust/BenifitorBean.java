
package enfo.crm.intrust;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.dao.IPageList;
import enfo.crm.dao.IntrustDBManager;
import enfo.crm.tools.Utility;
import enfo.crm.vo.BenifitorVO;

@Component(value="benifitor")
public class BenifitorBean extends enfo.crm.dao.IntrustBusiExBean implements BenifitorLocal {

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#load(enfo.crm.vo.BenifitorVO)
	 */
	@Override
	public List load(BenifitorVO vo) throws BusiException {
		String sql = "{call SP_QUERY_TBENIFITOR (?,?,?,?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[12];
		List rsList = null;

		params[0] = Utility.parseInt(vo.getBook_code(), new Integer(0));
		params[1] = vo.getSerial_no();
		params[2] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[3] = vo.getContract_bh();
		params[4] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		params[5] = vo.getInput_man();
		params[6] = "";
		params[7] = "";
		params[8] = "";
		params[9] = new Integer(0);
		params[10] = "";
		params[11] = vo.getContract_sub_bh();

		rsList = super.listProcAll(sql, params);
		return rsList;
	}
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#getBenchange(enfo.crm.vo.BenifitorVO)
	 */
	@Override
	public List getBenchange(BenifitorVO vo) throws BusiException {
		String sql = "{call SP_QUERY_TBENIFITOR_ONE (?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[9];
		List rsList = null;

		params[0] = Utility.parseInt(vo.getBook_code(), new Integer(0));
		params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[2] = vo.getContract_bh();
		params[3] = vo.getInput_man();
		params[4] = new Integer(0);
		params[5] = vo.getCust_name();
		params[6] = vo.getCard_id();
		params[7] = vo.getContract_sub_bh();
		params[8] = new Integer(1); 
		 

		rsList = CrmDBManager.listProcAll(sql, params);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#loadFromCRM(enfo.crm.vo.BenifitorVO)
	 */
	@Override
	public List loadFromCRM(BenifitorVO vo) throws BusiException {
		String sql = "{call SP_QUERY_TBENIFITOR (?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[14];
		List rsList = null;
		params[0] = Utility.parseInt(vo.getBook_code(), new Integer(1));
		params[1] = vo.getSerial_no();
		params[2] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[3] = vo.getContract_bh();
		params[4] =vo.getContract_sub_bh();  
		params[5] = "" ;
		params[6] ="";
		params[7] = "";
		params[8] = new Integer(0);
		params[9] = "";
		params[10] = "";
		params[11] = vo.getInput_man();
		params[12] = vo.getExport_flag();
		params[13] = vo.getExport_summary();
		rsList = CrmDBManager.listProcAll(sql, params);
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#loadBenacct(enfo.crm.vo.BenifitorVO)
	 */
	@Override
	public List loadBenacct(BenifitorVO vo)throws BusiException{
		String sql = "{call SP_QUERY_TMODIBENACCTDETAIL_BYSERIAL (?,?)}";
		Object[] params = new Object[2];
		List rsList = null; 
		 
		params[0] = vo.getSerial_no(); 
		params[1] = vo.getInput_man(); 
		
		rsList = CrmDBManager.listProcAll(sql, params);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#query(enfo.crm.vo.BenifitorVO)
	 */
	@Override
	public List query(BenifitorVO vo) throws BusiException {
		List rsList = null;
		Object[] params = new Object[12];
		String sql = "{call SP_QUERY_TBENIFITOR(?,?,?,?,?,?,?,?,?,?,?,?)}";

		params[0] = Utility.parseInt(vo.getBook_code(), new Integer(0));
		params[1] = new Integer(0);
		params[2] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[3] = vo.getContract_bh();
		params[4] = vo.getCust_id();
		params[5] = vo.getInput_man();
		params[6] = vo.getCust_name();
		params[7] = vo.getProv_level();
		params[8] = vo.getCust_no();
		params[9] = Utility.parseInt(vo.getList_id(), new Integer(0));
		params[10] = vo.getBen_status();
		params[11] = vo.getContract_sub_bh();

		rsList = super.listProcAll(sql, params);
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#append(enfo.crm.vo.BenifitorVO)
	 */
	@Override
	public void append(BenifitorVO vo) throws BusiException {
		String sql =
			"{?=call SP_ADD_TBENIFITOR (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[17];

		params[0] = vo.getBook_code();
		params[1] = vo.getProduct_id();
		params[2] = vo.getContract_bh();
		params[3] = vo.getCust_id();
		params[4] = vo.getBen_amount();
		params[5] = vo.getProv_level();
		params[6] = vo.getJk_type();
		params[7] = vo.getBank_id();
		params[8] = vo.getBank_acct();
		params[9] = vo.getInput_man();
		params[10] = vo.getBank_sub_name();
		params[11] = vo.getValid_period();
		params[12] = vo.getBen_date();
		params[13] = vo.getAcct_user_name();
		params[14] = vo.getBack_amount();
		params[15] = vo.getBank_acct_type();
		params[16] = vo.getBonus_flag();

		super.cudProc(sql, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#save(enfo.crm.vo.BenifitorVO)
	 */
	@Override
	public void save(BenifitorVO vo) throws BusiException {
		String sql =
			"{?=call SP_MODI_TBENIFITOR (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[22];

		params[0] = vo.getSerial_no();
		params[1] = vo.getBen_amount();
		params[2] = vo.getProv_level();
		params[3] = vo.getBank_id();
		params[4] = vo.getBank_acct();
		params[5] = vo.getJk_type();
		params[6] = vo.getInput_man();
		params[7] = vo.getBank_sub_name();
		params[8] = vo.getValid_period();
		params[9] = vo.getBen_date();
		params[10] = vo.getAcct_user_name();
		params[11] = vo.getBen_money();
		params[12] = vo.getBank_acct_type();
		params[13] = vo.getBonus_flag();
		params[14] = Utility.trimNull(vo.getBonus_rate(), 0);
		params[15] = vo.getBzj_flag();
		params[16] = vo.getMoney_origin(); 
		params[17] = vo.getSub_money_origin();
		params[18] = vo.getProv_flag();
		params[19] = vo.getBank_province();
		params[20] = vo.getBank_city();
		params[21] = "";

		super.cudProc(sql, params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#saveByCrm(enfo.crm.vo.BenifitorVO)
	 */
	@Override
	public void saveByCrm(BenifitorVO vo) throws BusiException {
		String sql =
			"{?=call SP_MODI_TBENIFITOR (?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[13];

		params[0] = vo.getSerial_no();
		params[1] = vo.getBen_amount();
		params[2] = vo.getProv_level();
		params[3] = vo.getBank_id();
		params[4] = vo.getBank_acct();
		params[5] = vo.getJk_type();
		params[6] = vo.getInput_man();
		params[7] = vo.getBank_sub_name();
		params[8] = vo.getBen_date();
		params[9] = vo.getAcct_user_name();
		params[10] = vo.getBen_money();
		params[11] = vo.getBank_acct_type();
		params[12] = vo.getBonus_flag();
		
		CrmDBManager.cudProc(sql, params);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#delete(enfo.crm.vo.BenifitorVO)
	 */
	@Override
	public void delete(BenifitorVO vo) throws BusiException {
		String sql = "{?=call SP_DEL_TBENIFITOR (?,?)}";
		Object[] params = new Object[2];

		params[0] = vo.getSerial_no();
		params[1] = vo.getInput_man();

		super.cudProc(sql, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#queryModi(enfo.crm.vo.BenifitorVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList queryModi(
		BenifitorVO vo,
		String[] totalColumn,
		int pageIndex,
		int pageSize)
		throws BusiException {
		IPageList rsList = null;
		Object[] params = new Object[13];
		String sql =
			"{call SP_QUERY_TBENIFITOR_MODIUNCHECK (?,?,?,?,?,?,?,?,?,?,?,?,?)}";

		params[0] = vo.getFunction_id();
		params[1] = vo.getBook_code();
		params[2] = vo.getSerial_no();
		params[3] = vo.getContract_bh();
		params[4] = vo.getCust_no();
		params[5] = vo.getProduct_id();
		params[6] = vo.getProduct_name();
		params[7] = vo.getInput_man();
		params[8] = vo.getCust_name();
		params[9] = vo.getContract_sub_bh();
		
		params[10] = vo.getSub_product_id();
		params[11] = Utility.parseInt(vo.getStart_date(),new Integer(0));
		params[12] = Utility.parseInt(vo.getEnd_date(),new Integer(0));
		
		rsList = super.listProcPage(sql, params, totalColumn, pageIndex, pageSize);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#queryModi1(enfo.crm.vo.BenifitorVO)
	 */
	@Override
	public List queryModi1(BenifitorVO vo)throws BusiException {
		Object[] params = new Object[13];
		String sql =
			"{call SP_QUERY_TBENIFITOR_MODIUNCHECK (?,?,?,?,?,?,?,?,?,?,?,?,?)}";

		params[0] = vo.getFunction_id();
		params[1] = vo.getBook_code();
		params[2] = vo.getSerial_no();
		params[3] = vo.getContract_bh();
		params[4] = vo.getCust_no();
		params[5] = vo.getProduct_id();
		params[6] = vo.getProduct_name();
		params[7] = vo.getInput_man();
		params[8] = vo.getCust_name();
		params[9] = vo.getContract_sub_bh();
		
		params[10] = vo.getSub_product_id();
		params[11] = Utility.parseInt(vo.getStart_date(),new Integer(0));
		params[12] = Utility.parseInt(vo.getEnd_date(),new Integer(0));
		
		return super.listBySql(sql,params);
	}
	
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#queryModiAcctDetail(enfo.crm.vo.BenifitorVO, int, int)
	 */
    @Override
	public IPageList queryModiAcctDetail(BenifitorVO vo, int page, int pagesize) throws Exception {
        Object[] params = new Object[12];
        params[0] = vo.getBook_code();
        params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
        params[2] = vo.getContract_bh();
        params[3] = vo.getCust_name();
        params[4] = vo.getOld_bank_id();
        params[5] = vo.getOld_bank_acct();
        params[6] = vo.getNew_bank_id();
        params[7] = vo.getNew_bank_acct();
        params[8] = vo.getInput_man();
        params[9] = vo.getCust_no();
        params[10] = vo.getContract_sub_bh();
        params[11] = vo.getSub_product_id();
        return CrmDBManager.listProcPage(
                "{call SP_QUERY_TMODIBENACCTDETAIL(?,?,?,?,?,?,?,?,?,?,?,?)}",
                params, page, pagesize);
    }
     
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#queryModiAcctDetail(enfo.crm.vo.BenifitorVO)
	 */
    @Override
	public List queryModiAcctDetail(BenifitorVO vo) throws Exception {
        Object[] params = new Object[12];
        params[0] = vo.getBook_code();
        params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
        params[2] = vo.getContract_bh();
        params[3] = vo.getCust_name();
        params[4] = vo.getOld_bank_id();
        params[5] = vo.getOld_bank_acct();
        params[6] = vo.getNew_bank_id();
        params[7] = vo.getNew_bank_acct();
        params[8] = vo.getInput_man();
        params[9] = vo.getCust_no();
        params[10] = vo.getContract_sub_bh();
        params[11] = vo.getSub_product_id();
        return super.listBySql(
                "{call SP_QUERY_TMODIBENACCTDETAIL(?,?,?,?,?,?,?,?,?,?,?,?)}",
                params);
    }
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#save1(enfo.crm.vo.BenifitorVO)
	 */
	@Override
	public void save1(BenifitorVO vo) throws BusiException {
		String sql = "{?=call SP_MODI_TBENIFITOR_BANK (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[20];
		params[0] = vo.getSerial_no();
		params[1] = vo.getBank_id();
		params[2] = vo.getBank_sub_name();
		params[3] = vo.getBank_acct();
		params[4] = vo.getCust_acct_name();
		params[5] = null;
		params[6] = null;
		params[7] = null;
		params[8] = null;
		params[9] =
			Utility.parseInt(vo.getModi_bank_date(), Utility.getCurrentDate());
		params[10] = vo.getAcct_chg_reason();
		params[11] = vo.getBank_acct_type();
		params[12] = vo.getBonus_flag();

		params[13] = Utility.parseBigDecimal(vo.getBonus_rate(),new BigDecimal(0));
		params[14] = vo.getBank_province();
		params[15] = vo.getBank_city();
		params[16] = vo.getInput_man();
		params[17] = vo.getJk_type();
		params[18] = vo.getDf_product_id();
		params[19] = vo.getDf_contract_bh();
		
		super.cudProc(sql, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#QueryBenifitor(enfo.crm.vo.BenifitorVO)
	 */
	@Override
	public List QueryBenifitor(BenifitorVO vo) throws BusiException {
		List list = new ArrayList();
		Object[] params = new Object[4];
		params[0] = vo.getBook_code();
		params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[2] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		params[3] = vo.getProduct_status();
//		params[4] = vo.getBank_acct();
		list = super.listBySql(
				"{call SP_QUERY_TBENIFITOR_CUST_ID (?,?,?,?)}",
				params);
		return list;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#listChangeDetail(enfo.crm.vo.BenifitorVO)
	 */
	@Override
	public List listChangeDetail(BenifitorVO vo) throws BusiException {
		List list = new ArrayList();
		Object[] params = new Object[3];
		params[0] = vo.getBook_code();
		params[1] = vo.getCust_id();
		params[2] = vo.getProduct_id();
		list =
			super.listBySql("{call SP_QUERY_TCUSTBENCHANGES (?,?,?)}", params);
		return list;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#queryDetail(enfo.crm.vo.BenifitorVO)
	 */
	@Override
	public List queryDetail(BenifitorVO vo) throws BusiException {
		List list = new ArrayList();
		Object[] params = new Object[14];
		params[0] = Utility.parseInt(vo.getBook_code(), new Integer(0));
		params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[2] = vo.getContract_bh();
		params[3] = Utility.parseInt(vo.getList_id(), new Integer(0));
		params[4] = vo.getCust_no();
		params[5] = vo.getCust_name();
		params[6] = vo.getProv_level();
		params[7] = vo.getInput_man();
		params[8] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		params[9] = vo.getSy_cust_no();
		params[10] = vo.getBen_status();
		params[11] = new Integer(0);
		params[12] = vo.getContract_sub_bh();
		params[13] = vo.getCust_type();
		list =
			super.listBySql(
				"{call SP_QUERY_TBENIFITOR_DETAIL (?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				params);
		return list;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#listLostDetail(enfo.crm.vo.BenifitorVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList listLostDetail(
		BenifitorVO vo,
		String[] totalColumn,
		int pageIndex,
		int pageSize)
		throws Exception {
		Object[] params = new Object[13];
		params[0] = vo.getBook_code();
		params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[2] = vo.getContract_bh();
		params[3] = Utility.parseInt(vo.getList_id(), new Integer(0));
		params[4] = vo.getBen_card_no();
		params[5] = vo.getCust_name();
		params[6] = vo.getProv_level();
		params[7] = vo.getInput_man();
		params[8] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		params[9] = Utility.parseInt(vo.getFunction_id(), new Integer(0));
		params[10] = vo.getCust_no();
		params[11] = Utility.parseInt(vo.getBen_lost_flag(), new Integer(0));
		params[12] = vo.getContract_sub_bh();
		return super.listProcPage(
			"{call SP_QUERY_TBENIFITOR_LOST_DETAIL (?,?,?,?,?,?,?,?,?,?,?,?,?)}",
			params,
			totalColumn,
			pageIndex,
			pageSize);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#modBenifitorLevel(enfo.crm.vo.BenifitorVO)
	 */
	@Override
	public void modBenifitorLevel(BenifitorVO vo) throws Exception {
		Object[] params = new Object[3];
		params[0] = vo.getSerial_no();
		params[1] = vo.getProv_level();
		params[2] = vo.getInput_man();
		try {
			super.cudProc("{?=call SP_MODI_TBENIFITOR_LEVEL(?,?,?)}", params);
		} catch (Exception e) {
			throw new BusiException("受益级别修改失败: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#modBenifitorValidPeriod(enfo.crm.vo.BenifitorVO)
	 */
	@Override
	public void modBenifitorValidPeriod(BenifitorVO vo) throws Exception {
		Object[] params = new Object[4];
		params[0] = vo.getSerial_no();
		params[1] = vo.getValid_period();
		params[2] = vo.getBen_end_date();
		params[3] = vo.getInput_man();
		try {
			super.cudProc("{?=call SP_MODI_TBENIFITOR_4VALID_PERIOD(?,?,?,?)}", params);
		} catch (Exception e) {
			throw new BusiException("受益期限、到期日期修改失败: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#getTotalBenAmount(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public BigDecimal getTotalBenAmount(Integer cust_id,Integer product_id) throws Exception{
		BigDecimal returnValue = new BigDecimal(0.00);
		String sqlStr =  
				"SELECT SUM(BEN_AMOUNT) AS TOTLE_AMOUNT FROM TBENIFITOR  WHERE CUST_ID = "+ cust_id+" AND PRODUCT_ID =" + product_id;
				
		Connection conn = IntrustDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = null;
		
		try {
			rs = stmt.executeQuery(sqlStr);
			while (rs.next()) {
				returnValue = Utility.parseBigDecimal(rs.getBigDecimal("TOTLE_AMOUNT"),new BigDecimal(0.00));
			}
		} 
		catch (SQLException e) {
			throw new BusiException("查询客户受益份额失败:" + e.getMessage());
		}
		finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
		
		return returnValue;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#check1(enfo.crm.vo.BenifitorVO)
	 */
	@Override
	public void check1(BenifitorVO vo) throws BusiException{
		String sqlStr = "{?=call SP_CHECK_TBENIFITOR_ACCT(?,?,?)}";
		Object[] params = new Object[3];
		params[0] = vo.getSerial_no();
		params[1] = vo.getCheck_flag();
		params[2] = vo.getInput_man();
		
		try {
			super.cudProc(sqlStr, params);
		} catch (BusiException e) {
			throw new BusiException("受益人账号修改审核失败:" + e.getMessage());
		}
	}
	
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#query(enfo.crm.vo.BenifitorVO, java.lang.String[], int, int)
	 */
    @Override
	public IPageList query(BenifitorVO vo,String[] totalColumn,int pageIndex,int pageSize) throws Exception {
    	
        Object[] params = new Object[12];
        IPageList rsList = null;
		String sql ="{call SP_QUERY_TBENIFITOR (?,?,?,?,?,?,?,?,?,?,?,?)}";
		
        params[0] = Utility.parseInt(vo.getBook_code(), new Integer(0));
        params[1] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
        params[2] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
        params[3] = vo.getContract_bh();
        params[4] = vo.getCust_id();
        params[5] = vo.getInput_man();
        params[6] = vo.getCust_name();
        params[7] = vo.getProv_level();
        params[8] = vo.getCust_no();
        params[9] = Utility.parseInt(vo.getList_id(), new Integer(0));
        params[10] = vo.getBen_status();
        params[11] = vo.getContract_sub_bh();
        rsList =
			super.listProcPage(sql, params, totalColumn, pageIndex, pageSize);
		return rsList;
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#load1(enfo.crm.vo.BenifitorVO)
	 */

    @Override
	public List load1(BenifitorVO vo) throws Exception {
        Object[] params = new Object[12];
        List rsList = null;
        
        params[0] = Utility.parseInt(vo.getBook_code(), new Integer(0));
        params[1] = vo.getSerial_no();
        params[2] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
        params[3] = vo.getContract_bh();
        params[4] = Utility.parseInt(vo.getCust_id(), new Integer(0));
        params[5] = vo.getInput_man();
        params[6] = "";
        params[7] = "";
        params[8] = "";
        params[9] = new Integer(0);
        params[10] = "";
        params[11] = vo.getContract_sub_bh();
        rsList = super.listProcAll("{call SP_QUERY_TBENIFITOR (?,?,?,?,?,?,?,?,?,?,?,?)}", params);
        return rsList;
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#modi_prov_level(enfo.crm.vo.BenifitorVO)
	 */
    @Override
	public void modi_prov_level(BenifitorVO vo) throws Exception {
        Object[] params = new Object[5];
        params[0] = vo.getSerial_no();
        params[1] = vo.getProv_flag();
        params[2] = vo.getProv_level();
        params[3] = vo.getProv_change_date();
        params[4] = vo.getInput_man();
        try {
            super.cud("{?=call SP_MODI_TBENIFITOR_LEVEL(?,?,?,?,?)}", params);
        } catch (Exception e) {
            throw new BusiException("受益级别修改失败: " + e.getMessage());
        }
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#QueryBenifitorProv(enfo.crm.vo.BenifitorVO, int, int)
	 */

    @Override
	public IPageList QueryBenifitorProv(BenifitorVO vo,int pageIndex,int pageSize) throws Exception {
        Object[] params = new Object[9];
        IPageList pageList = null;
        params[0] = vo.getBook_code();
        params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
        params[2] = vo.getContract_sub_bh();
        params[3] = vo.getCust_no();
        params[4] = vo.getCust_name();
        params[5] = Utility.parseInt(vo.getProv_flag(), new Integer(0));
        params[6] = vo.getProv_level();
        params[7] = Utility.parseInt(vo.getCheck_flag(), new Integer(0));
        params[8] = Utility.parseInt(vo.getSub_product_id(),new Integer(0));
        return super.listProcPage("{call SP_QUERY_TBENIFITOR_PROV(?,?,?,?,?,?,?,?,?)}", params,pageIndex, pageSize);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#checkBenifitorProv(enfo.crm.vo.BenifitorVO)
	 */
    @Override
	public void checkBenifitorProv(BenifitorVO vo) throws Exception {
        Object[] params = new Object[4];
        params[0] = vo.getBook_code();
        params[1] = vo.getSerial_no();
        params[2] = vo.getInput_man();
        params[3] = vo.getCheck_flag();
        try {
            super.update(
                    "{?=call SP_CHECK_TBENIFITOR_LEVEL(?,?,?,?)}",
                    params);
        } catch (Exception e) {
            throw new BusiException("收益级别审核失败: " + e.getMessage());
        }
    }
    
    

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#changeBenBatch(enfo.crm.vo.BenifitorVO)
	 */
    @Override
	public void changeBenBatch(BenifitorVO vo) throws Exception {
        Object[] params = new Object[5];
        params[0] = vo.getSerial_no();
        params[1] = vo.getInput_man();
        params[2] = vo.getModi_bank_date();
        params[3] = vo.getSummary();
        params[4] = vo.getBonus_flag();
        try {
            super.update(
                    "{?=call SP_MODI_TBENIFITOR_BANK_SYFPFS(?,?,?,?,?)}",
                    params);
        } catch (Exception e) {
            throw new BusiException("收益分配调整失败: " + e.getMessage());
        }
    }
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#listBenifitorbyht(enfo.crm.vo.BenifitorVO)
	 */
    @Override
	public List listBenifitorbyht(BenifitorVO vo) throws Exception {
        Object[] params = new Object[2];
        params[0] = Utility.parseInt(vo.getProduct_id(), new Integer(1));
        params[1] = vo.getContract_bh();
        return super.listBySql("{call SP_QUERY_TBENIFITOR_BYHT(?,?)}", params);
    } 
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.BenifitorLocal#queryBenifitorbyhtPrint(enfo.crm.vo.BenifitorVO)
	 */
    @Override
	public List queryBenifitorbyhtPrint(BenifitorVO vo) throws Exception{
    	Object[] params = new Object[2];
        params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
        params[1] = Utility.parseInt(vo.getInput_man(), new Integer(0));
        return super.listBySql("{call SP_QUERY_TBENIFITOR_CONTRACTSERIAL(?,?)}", params);
    }
}