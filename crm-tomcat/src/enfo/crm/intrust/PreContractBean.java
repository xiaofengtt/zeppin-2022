 
package enfo.crm.intrust;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.dao.IPageList;
import enfo.crm.dao.IntrustDBManager;
import enfo.crm.tools.Utility;
import enfo.crm.vo.PreContractVO;

@Component(value="preContract")
public class PreContractBean extends enfo.crm.dao.IntrustBusiExBean implements PreContractLocal {
	private static final String SP_QUERY_TPRECONTRACT_ALL="{call SP_QUERY_TPRECONTRACT_ALL(?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String SP_ADD_TPRECONTRACT = "{?=call SP_ADD_TPRECONTRACT (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String SP_DEL_TPRECONTRACT = "{?=call SP_DEL_TPRECONTRACT (?,?,?)}";
	private static final String SP_QUERY_TPRECONTRACT_LOAD = "{call SP_QUERY_TPRECONTRACT_LOAD(?)}";
	private static final String SP_MODI_TPRECONTRACT = "{?=call SP_MODI_TPRECONTRACT (?,?,?,?,?,?,?,?,?,?,?,?,?)}";		
	private static final String SP_QUERY_TPRECONTRACT_VALID = "{call SP_QUERY_TPRECONTRACT_VALID (?,?,?,?,?,?,?,?,?,?)}";
	private static final String SP_QUERY_TPRECONTRACT = "{call SP_QUERY_TPRECONTRACT (?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String SP_QUERY_TPRECONTRACT_CUST = "{call SP_QUERY_TPRECONTRACT_CUST (?,?,?,?,?)}";
	private static final String SP_QUERY_TPRECONTRACTBYCUSTID = "{call SP_QUERY_TPRECONTRACTBYCUSTID(?,?,?)}";
	private static final String SP_QUERY_TCUSTREGINFO_COMM = "{call SP_QUERY_TCUSTREGINFO_COMM(?,?,?,?,?)}";

	private static final String SP_ADD_TCUSTREGINFO = "{?=call SP_ADD_TCUSTREGINFO(?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String SP_MODI_TCUSTREGINFO = "{?=call SP_MODI_TCUSTREGINFO(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String SP_DEL_TCUSTREGINFO = "{?=call SP_DEL_TCUSTREGINFO(?,?)}";
	private static final String SP_QUERY_TCUSTREGINFO = "{call SP_QUERY_TCUSTREGINFO(?,?,?,?,?,?,?,?,?,?,?,?)}";

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#append(enfo.crm.vo.PreContractVO)
	 */	
	@Override
	public Object[] append(PreContractVO vo) throws BusiException{
		Object[] rsList = new Object[3];
		int ret;
        try {
            Connection conn = CrmDBManager.getConnection();
            CallableStatement stmt = conn.prepareCall(
            		"{?=call SP_ADD_TPRECONTRACT (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            stmt.registerOutParameter(1, java.sql.Types.INTEGER);
            
            stmt.setInt(2, vo.getPre_product_id().intValue());
            stmt.setInt(3, vo.getCust_id().intValue());
            stmt.setBigDecimal(4, vo.getPre_money());
            stmt.setInt(5, vo.getLink_man().intValue());
            stmt.setInt(6, Utility.parseInt(vo.getValid_days(),0).intValue());            
            stmt.setString(7, vo.getPre_type());
            stmt.setString(8, vo.getSummary()); 
            stmt.setInt(9, Utility.parseInt(vo.getPre_num(),0).intValue());
            stmt.setInt(10, vo.getInput_man().intValue());
            stmt.setInt(11, Utility.parseInt(vo.getPre_date(),0).intValue());
            stmt.setInt(12, Utility.parseInt(vo.getExp_reg_date(),0).intValue());
            stmt.setString(13, vo.getCust_source());            
            stmt.setString(14, Utility.trimNull(vo.getTest_code())); // pre_code
            stmt.setString(16, null); // 渠道类别(5500)
            stmt.setString(17, null); // 渠道费用
            stmt.setString(18, null); // 预约类别(1182)用于区分预约的级别
            stmt.setInt(19, vo.getProduct_id().intValue());
            stmt.setInt(20, vo.getSub_product_id().intValue());
            
            stmt.registerOutParameter(14, java.sql.Types.VARCHAR); 
            stmt.registerOutParameter(15, java.sql.Types.INTEGER); 
            
           // stmt.setString(17, Utility.trimNull(vo.getTest_code()));
            stmt.executeUpdate();
            ret = stmt.getInt(1);
            rsList[0] = stmt.getString(14);
            rsList[1] = new Integer(stmt.getInt(15));

            
            stmt.close();
            conn.close();
            if (ret != 100) {
                throw new BusiException(ret);
            }
        } catch (Exception e) {
            throw new BusiException("预约失败: " + e.getMessage());
        }
		//Object[] rsList = super.cudProc(SP_ADD_TPRECONTRACT,params,outParamPos,outParamType);

		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#delete(enfo.crm.vo.PreContractVO)
	 */
	@Override
	public void delete(PreContractVO vo) throws BusiException {
		try {			
			Object[] params = new Object[3];
	
			params[0] = Utility.parseInt(vo.getSerial_no(),new Integer(0));
			params[1] = new Integer(1);
			params[2] = Utility.parseInt(vo.getInput_man(),new Integer(0));
	
			CrmDBManager.cudProc(SP_DEL_TPRECONTRACT,params);

		} catch (Exception e) {
			throw new BusiException("预约失败: " + e.getMessage());
		}
	}
		
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#load(enfo.crm.vo.PreContractVO)
	 */
	@Override
	public List  load(PreContractVO vo)throws BusiException{
		Object[] params = new Object[1];
		params[0] = vo.getSerial_no();
		return CrmDBManager.listProcAll(SP_QUERY_TPRECONTRACT_LOAD,params);		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#loadFromIntrust(enfo.crm.vo.PreContractVO)
	 */
	@Override
	public List  loadFromIntrust(PreContractVO vo)throws BusiException{
		Object[] params = new Object[1];
		params[0] = vo.getSerial_no();
		return IntrustDBManager.listProcAll(SP_QUERY_TPRECONTRACT_LOAD,params);		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#loadPreproduct(java.lang.Integer)
	 */	
	@Override
	public List loadPreproduct(Integer preproduct_id) throws BusiException{
		return CrmDBManager.listBySql(
				"{call SP_QUERY_TPREPRODUCT_LOAD("+preproduct_id+")}");		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#save(enfo.crm.vo.PreContractVO)
	 */
	@Override
	public void save(PreContractVO vo) throws BusiException {		
			Object[] params = new Object[14];
			params[0] = Utility.parseInt(vo.getSerial_no(),new Integer(0));
			params[1] = Utility.parseBigDecimal(vo.getPre_money(),new BigDecimal(0));
			params[2] = Utility.parseInt(vo.getLink_man(),new Integer(0));
			params[3] = Utility.parseInt(vo.getValid_days(),new Integer(0));
			params[4] = vo.getPre_type();
			params[5] = vo.getSummary();
			params[6] = Utility.parseInt(vo.getPre_num(),new Integer(0));
			params[7] = Utility.parseInt(vo.getInput_man(),new Integer(0));
			params[8] = Utility.parseInt(vo.getPre_date(),new Integer(0));
			params[9] = vo.getExp_reg_date();
			params[10] = vo.getCust_source();
			params[11] = Utility.trimNull(vo.getPer_code());
			params[12] = Utility.parseInt(vo.getMoney_status(),new Integer(2/*0*/)); // 2未缴款
			params[13] = Utility.parseInt(vo.getCust_id(),new Integer(0));
			CrmDBManager.cudProc("{?=call SP_MODI_TPRECONTRACT2 (?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",params);	
	}

	//参数:产品ID$预约号$业务员
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#list()
	 */
	@Override
	public void list() throws Exception {
		query(new PreContractVO());
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#query(enfo.crm.vo.PreContractVO)
	 */
	@Override
	public List query(PreContractVO vo) throws BusiException {
		List rsList = null;			
		Object[] params = new Object[10];

		params[0] = Utility.parseInt(vo.getBook_code(),new Integer(0));
		params[1] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
		params[2] = Utility.trimNull(vo.getPre_code());
		params[3] = new Integer(0);
		params[4] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		params[5] = vo.getCust_name();
		params[6] = vo.getCust_type();
		params[7] = vo.getMax_reg_money();
		params[8] = vo.getMin_reg_money();
		params[9] = vo.getCust_source();

		rsList = super.listBySql(SP_QUERY_TPRECONTRACT_VALID,params);

		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#query_crm(enfo.crm.vo.PreContractVO)
	 */
	@Override
	public List query_crm(PreContractVO vo) throws BusiException{
		List rsList = null;			
		Object[] params = new Object[12];
		String sqlStr = "{call SP_QUERY_TPRECONTRACT_VALID_CRM (?,?,?,?,?,?,?,?,?,?,?,?)}";
		
		params[0] = Utility.parseInt(vo.getBook_code(),new Integer(0));
		params[1] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
		params[2] = Utility.trimNull(vo.getPre_code());
		params[3] = new Integer(0);
		params[4] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		params[5] = vo.getCust_name();
		params[6] = vo.getCust_type();
		params[7] = vo.getMax_reg_money();
		params[8] = vo.getMin_reg_money();
		params[9] = vo.getCust_source();
		params[10] = vo.getCust_group_id();
		params[11] = vo.getClassdetail_id();
		
		rsList = CrmDBManager.listBySql(sqlStr, params);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#preContract_page_query(enfo.crm.vo.PreContractVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList preContract_page_query(PreContractVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		IPageList rsList = null;			
		Object[] params = new Object[10];

		params[0] = Utility.parseInt(vo.getBook_code(),new Integer(0));
		params[1] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
		params[2] = Utility.trimNull(vo.getPre_code());
		params[3] = new Integer(0);
		params[4] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		params[5] = vo.getCust_name();
		params[6] = vo.getCust_type();
		params[7] = vo.getMax_reg_money();
		params[8] = vo.getMin_reg_money();
		params[9] = vo.getCust_source();
		
		rsList = super.listProcPage(SP_QUERY_TPRECONTRACT_VALID,params,totalColumn,pageIndex,pageSize);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#preContract_page_query_crm(enfo.crm.vo.PreContractVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList preContract_page_query_crm(PreContractVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		Object[] params = new Object[17];
		String sqlStr = "{call SP_QUERY_TPRECONTRACT_VALID(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

		params[0] = Utility.parseInt(vo.getBook_code(),new Integer(0));
		params[1] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
		params[2] = Utility.trimNull(vo.getPre_code());
		params[3] = new Integer(0);
		params[4] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		params[5] = vo.getCust_name();
		params[6] = vo.getCust_type();
		params[7] = vo.getMax_reg_money();
		params[8] = vo.getMin_reg_money();
		params[9] = vo.getCust_source();
		params[10] = vo.getCust_group_id();
		params[11] = vo.getClassdetail_id();
		params[12] = vo.getMoney_status(); // 缴款状态 0全部 1已缴款 2未缴款
		params[13] = Utility.parseInt(vo.getSub_product_id(), new Integer(0)); // 子产品ID
		params[14] = Utility.parseInt(vo.getPre_product_id(), new Integer(0)); // 预发行产品ID 
		params[15] = vo.getServ_man(); // 客户经理
		params[16] = vo.getPre_status();
		return CrmDBManager.listProcPage(sqlStr,params,totalColumn,pageIndex,pageSize);
	}
	   
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#queryAll(enfo.crm.vo.PreContractVO)
	 */
	@Override
	public List queryAll(PreContractVO vo) throws Exception {
		List rsList = null;		
		Object[] param = new Object[11];

		param[0] = Utility.parseInt(vo.getBook_code(),new Integer(0));
		param[1] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
		param[2] = Utility.trimNull(vo.getPre_code());
		param[3] = new Integer(0);
		param[4] = vo.getCust_name();
		param[5] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		param[6] = vo.getCust_no();
		param[7] = vo.getCust_type();
		param[8] = vo.getMax_reg_money();
		param[9] = vo.getMin_reg_money();
		param[10] = vo.getCust_source();

		rsList = super.listBySql(SP_QUERY_TPRECONTRACT,param);
		return rsList;
	}
	
	   
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#queryListAll(enfo.crm.vo.PreContractVO, int, int)
	 */
	@Override
	public IPageList queryListAll(PreContractVO vo,int page, int pagesize) throws Exception{
		 Object[] param = new Object[12];
		 
	        param[0] =Utility.parseInt(vo.getPre_product_id(),new Integer(0));
	        param[1] =vo.getPre_product_name();
	        param[2] =vo.getProduct_id();
	        param[3] = vo.getSub_product_id();
	        param[4] =vo.getCust_name();
	        param[5] = vo.getDate1();
	        param[6] = vo.getDate2();
	        param[7] = vo.getMoney1();
	        param[8] = vo.getMoney2();
	        param[9] = vo.getPre_status();
	        param[10] =vo.getPre_type();
	        param[11] = Utility.parseInt(vo.getInput_man(),new Integer(0));
	        
	        
		  return CrmDBManager.listProcPage(SP_QUERY_TPRECONTRACT_ALL,param, page, pagesize);
	}
	
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#queryAll(enfo.crm.vo.PreContractVO, int, int)
	 */
    @Override
	public IPageList queryAll(PreContractVO vo, int page, int pagesize) throws Exception {   
        Object[] param = new Object[11];

        param[0] = Utility.parseInt(vo.getBook_code(),new Integer(0));
        param[1] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
        param[2] = Utility.trimNull(vo.getPre_code());
        param[3] = new Integer(0);
        param[4] = vo.getCust_name();
        param[5] = Utility.parseInt(vo.getInput_man(),new Integer(0));
        param[6] = vo.getCust_no();
        param[7] = vo.getCust_type();
        param[8] = vo.getMax_reg_money();
        param[9] = vo.getMin_reg_money();
        param[10] = vo.getCust_source();

        return super.listProcPage(SP_QUERY_TPRECONTRACT,param, page, pagesize);    
    }
    
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#queryByCust(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List queryByCust(
		Integer input_bookCode,
		Integer product_id,
		String cust_name,
		String card_id,
		Integer input_man)
		throws BusiException {		
			List rsList = null;	
			Object[] param = new Object[5];
	
			param[0] = input_bookCode;
			param[1] = Utility.parseInt(product_id,new Integer(0));
			param[2] = cust_name;
			param[3] = card_id;
			param[4] = Utility.parseInt(input_man,new Integer(0));
	
			rsList = super.listBySql(SP_QUERY_TPRECONTRACT_CUST,param);
			return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#queryPrecontractByCustID(enfo.crm.vo.PreContractVO)
	 */
	@Override
	public List queryPrecontractByCustID(PreContractVO vo) throws BusiException {
		List rsList = null;	

		Object[] param = new Object[3];
		param[0] = Utility.parseInt(vo.getBook_code(),new Integer(0));
		param[1] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
		param[2] = Utility.parseInt(vo.getCust_id(),new Integer(0));

		rsList = super.listBySql(SP_QUERY_TPRECONTRACTBYCUSTID,param);
		return rsList;			
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#getNext()
	 */
	@Override
	public boolean getNext() throws Exception{
		return false;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#getNext1()
	 */
	@Override
	public boolean getNext1() throws Exception{
		return false;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#getNext2()
	 */
	@Override
	public boolean getNext2() throws Exception{
		return false;
	}
		
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#append_reginfo(enfo.crm.vo.PreContractVO)
	 */
	@Override
	public void append_reginfo(PreContractVO vo) throws BusiException {
		  if (vo.getBook_code().intValue() <= 0)
			  throw new BusiException("帐套代码不合法.");
		  if (vo.getCust_id().intValue() <= 0)
			  throw new BusiException("客户ID号不合法.");
		
		  Object[] params = new Object[11];
  
		  params[0] = Utility.parseInt(vo.getBook_code(),new Integer(0));
		  params[1] = Utility.parseInt(vo.getCust_id(),new Integer(0));
		  params[2] = Utility.parseBigDecimal(vo.getReg_money(),new BigDecimal(0));;
		  params[3] = vo.getSummary();
		  params[4] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		  params[5] = Utility.parseInt(vo.getReg_date(),new Integer(0));
		  params[6] = Utility.parseInt(vo.getReg_valid_days(),new Integer(0));
		  params[7] = Utility.trimNull(vo.getInvest_type());
		  params[8] = Utility.trimNull(vo.getInvest_type_name());
		  params[9] = Utility.trimNull(vo.getCust_source());
		  
		  params[10]= Utility.parseInt(vo.getLink_man(),new Integer(0));
		  
		  super.cudProc(SP_ADD_TCUSTREGINFO,params);
	  }
	  
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#save_reginfo(enfo.crm.vo.PreContractVO)
	 */
	@Override
	public void save_reginfo(PreContractVO vo) throws BusiException {			
		Object[] params = new Object[14];

		params[0] = Utility.parseInt(vo.getCust_id(),new Integer(0));
		params[1] = Utility.parseBigDecimal(vo.getReg_money(),new BigDecimal(0));
		params[2] = Utility.parseBigDecimal(vo.getRg_money(),new BigDecimal(0));
		params[3] = Utility.parseInt(vo.getLast_rg_date(),new Integer(0));
		params[4] = Utility.parseInt(vo.getLast_product_id(),new Integer(0));
		params[5] = Utility.parseBigDecimal(vo.getLast_money(),new BigDecimal(0));
		params[6] = vo.getSummary();
		params[7] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		params[8] = Utility.parseInt(vo.getReg_date(),new Integer(0));
		params[9] = Utility.parseInt(vo.getReg_valid_days(),new Integer(0));
//		params[9] = Utility.parseInt(vo.getValid_days(),new Integer(0));
		params[10] = Utility.trimNull(vo.getInvest_type(),"");
		params[11] = Utility.trimNull(vo.getInvest_type_name(),"");
		params[12] = Utility.trimNull(vo.getCust_source(),"");

		params[13] = Utility.parseInt(vo.getLink_man(),new Integer(0));

		super.cudProc(SP_MODI_TCUSTREGINFO,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#delete_reginfo(enfo.crm.vo.PreContractVO)
	 */
	@Override
	public void delete_reginfo(PreContractVO vo) throws BusiException {
		Object[] params = new Object[2];

		params[0] = Utility.parseInt(vo.getCust_id(),new Integer(0));
		params[1] = Utility.parseInt(vo.getInput_man(),new Integer(0));

		super.cudProc(SP_DEL_TCUSTREGINFO,params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#reginfo_list()
	 */
	@Override
	public List reginfo_list() throws Exception {
		return query_reginfo(new PreContractVO());
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#query_reginfo(enfo.crm.vo.PreContractVO)
	 */	
	@Override
	public List  query_reginfo(PreContractVO vo) throws BusiException {
		List rsList = null;	
		Object[] params = new Object[12];

		params[0] = Utility.parseInt(vo.getBook_code(),new Integer(0));
		params[1] = Utility.parseInt(vo.getCust_id(),new Integer(0));
		params[2] = Utility.trimNull(vo.getCust_name());
		params[3] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		params[4] = Utility.parseInt(vo.getInt_flag(),new Integer(0));
		params[5] = Utility.trimNull(vo.getCust_source());
		params[6] = Utility.trimNull(vo.getCust_type());
		params[7] = vo.getInvest_type();
		params[8] = vo.getMax_reg_money();
		params[9] = vo.getMin_reg_money();
		params[10] = vo.getReg_date();
		params[11] = vo.getReg_status();

		rsList = super.listBySql(SP_QUERY_TCUSTREGINFO,params);
		return rsList;	
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#query_reginfo_crm(enfo.crm.vo.PreContractVO)
	 */
	@Override
	public List query_reginfo_crm(PreContractVO vo) throws BusiException{
		String sqlStr = "{call SP_QUERY_TCUSTREGINFO_CRM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		List rsList = null;	
		Object[] params = new Object[15];

		params[0] = Utility.parseInt(vo.getBook_code(),new Integer(0));
		params[1] = Utility.parseInt(vo.getCust_id(),new Integer(0));
		params[2] = Utility.trimNull(vo.getCust_name());
		params[3] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		params[4] = Utility.parseInt(vo.getInt_flag(),new Integer(0));
		params[5] = Utility.trimNull(vo.getCust_source());
		params[6] = Utility.trimNull(vo.getCust_type());
		params[7] = vo.getInvest_type();
		params[8] = vo.getMax_reg_money();
		params[9] = vo.getMin_reg_money();
		params[10] = vo.getReg_date();
		params[11] = vo.getReg_status();
		params[12] = vo.getCust_group_id();
		params[13] = vo.getClassdetail_id();
		params[14] = Utility.parseInt(vo.getLink_man(),new Integer(0));
		
		rsList = CrmDBManager.listProcAll(sqlStr, params);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#query_reginfo(enfo.crm.vo.PreContractVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList query_reginfo(PreContractVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException  {
		IPageList rsList = null;	
		Object[] params = new Object[12];

		params[0] = Utility.parseInt(vo.getBook_code(),new Integer(0));
		params[1] = Utility.parseInt(vo.getCust_id(),new Integer(0));
		params[2] = Utility.trimNull(vo.getCust_name());
		params[3] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		params[4] = Utility.parseInt(vo.getInt_flag(),new Integer(0));
		params[5] = Utility.trimNull(vo.getCust_source());
		params[6] = Utility.trimNull(vo.getCust_type());
		params[7] = vo.getInvest_type();
		params[8] = vo.getMax_reg_money();
		params[9] = vo.getMin_reg_money();
		params[10] = vo.getReg_date();
		params[11] = vo.getReg_status();

		rsList = super.listProcPage(SP_QUERY_TCUSTREGINFO,params,totalColumn,pageIndex,pageSize);
		return rsList;	
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#query_reginfo_crm(enfo.crm.vo.PreContractVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList query_reginfo_crm(PreContractVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		String sqlStr = "{call SP_QUERY_TCUSTREGINFO_CRM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		IPageList rsList = null;	
		Object[] params = new Object[16];

		params[0] = Utility.parseInt(vo.getBook_code(),new Integer(0));
		params[1] = Utility.parseInt(vo.getCust_id(),new Integer(0));
		params[2] = Utility.trimNull(vo.getCust_name());
		params[3] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		params[4] = Utility.parseInt(vo.getInt_flag(),new Integer(0));
		params[5] = Utility.trimNull(vo.getCust_source());
		params[6] = Utility.trimNull(vo.getCust_type());
		params[7] = vo.getInvest_type();
		params[8] = vo.getMax_reg_money();
		params[9] = vo.getMin_reg_money();
		params[10] = vo.getReg_date();
		params[11] = vo.getReg_status();
		params[12] = vo.getCust_group_id();
		params[13] = vo.getClassdetail_id();
		params[14] = Utility.parseInt(vo.getLink_man(),new Integer(0));
		params[15] = Utility.trimNull(vo.getCust_no());
		
		rsList = CrmDBManager.listProcPage(sqlStr, params,totalColumn,pageIndex,pageSize);
		return rsList;
		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#getRegInfo_cust_list()
	 */
	@Override
	public boolean getRegInfo_cust_list() throws Exception {
		return false;
	}
	

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#query_refinfo_comm(enfo.crm.vo.PreContractVO)
	 */
	@Override
	public List query_refinfo_comm(PreContractVO vo) throws BusiException {
		List rsList = null;	
		Object[] param = new Object[5];

		param[0] = Utility.parseInt(vo.getBook_code(),new Integer(0));
		param[1] = Utility.parseInt(vo.getCust_id(),new Integer(0));
		param[2] = vo.getCust_name();
		param[3] = Utility.parseInt(vo.getInt_flag(),new Integer(0));
		param[4] = Utility.parseInt(vo.getInput_man(),new Integer(0));

		rsList = super.listBySql(SP_QUERY_TCUSTREGINFO_COMM,param);
		return rsList;		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#getOutListReginfo(enfo.crm.vo.PreContractVO)
	 */
	@Override
	public List getOutListReginfo(PreContractVO vo) throws BusiException {
		String sql = "{call SP_QUERY_TCUSTREGINFO_COMM(?,?,?,?,?)}";

		List rsList = null;	
		Object[] param = new Object[5];

		param[0] = Utility.parseInt(vo.getBook_code(),new Integer(0));
		param[1] = Utility.parseInt(vo.getCust_id(),new Integer(0));
		param[2] = vo.getCust_name();
		param[3] = Utility.parseInt(vo.getInt_flag(),new Integer(0));
		param[4] = Utility.parseInt(vo.getInput_man(),new Integer(0));

		rsList = super.listBySql(sql,param);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#getOutExcel_presell(enfo.crm.vo.PreContractVO)
	 */
	@Override
	public List getOutExcel_presell(PreContractVO vo)
		throws BusiException {
			String sql = "{call SP_QUERY_TPRECONTRACT_VALID (?,?,?,?,?,?,?,?,?,?)}";

			List rsList = null;	
			Object[] param = new Object[10];	
	
			param[0] = Utility.parseInt(vo.getBook_code(),new Integer(0));
			param[1] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
			param[2] = Utility.trimNull(vo.getPre_code());
			param[3] = new Integer(0);
			param[4] = Utility.parseInt(vo.getInput_man(),new Integer(0));
			param[5] = vo.getCust_name();
			param[6] = vo.getCust_type();
			param[7] = vo.getMax_reg_money();
			param[8] = vo.getMin_reg_money();
			param[9] = vo.getCust_source();
			
	
			rsList = super.listBySql(sql,param);
			return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#queryPreNum(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public String queryPreNum(Integer preproduct_id, Integer product_id)throws Exception{         
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = CrmDBManager.getConnection();
			stmt = conn.createStatement();
			String sql = "";
			if (preproduct_id.intValue()==0) 
				sql = "SELECT ISNULL(MAX(PRE_CODE),'000') AS NUM FROM TPRECONTRACT WHERE PRODUCT_ID=" + product_id 
					+ " UNION SELECT ISNULL(MAX(PRE_CODE),'000') AS NUM FROM intrust..TPRECONTRACT WHERE PRODUCT_ID="+product_id 
					+ " ORDER BY NUM DESC";
			else 
				sql = "SELECT ISNULL(MAX(PRE_CODE),'000') AS NUM FROM TPRECONTRACT WHERE PREPRODUCT_ID=" + preproduct_id;
			
			rs = stmt.executeQuery(sql);
			String strNum = "";
			if (rs.next()) {
				strNum = rs.getString("NUM");
			}			

			// 假设预约号不会超过三位数
			int pre_num = Utility.parseInt(strNum, 0) + 1;
			if (pre_num < 10) // 一位数
				strNum = "00"+pre_num;
			else if (pre_num < 100) // 两位数
				strNum = "0"+pre_num;
			else // 三位数
				strNum = ""+pre_num;

			return strNum;
			
		} catch (Exception e) {
			throw new BusiException("预约登记号码查询失败:" + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}		
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#queryMicroPreNum(java.lang.Integer)
	 */
	@Override
	public String queryMicroPreNum(Integer product_id)throws Exception{         
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String micro_pre_num ="";
		try {
			conn = IntrustDBManager.getConnection();
			stmt = conn.createStatement();
			rs =
				stmt.executeQuery(
					"SELECT SUM(PRE_NUM) AS NUM  FROM TPRECONTRACT WHERE PRE_MONEY < 3000000  AND PRODUCT_ID = " + product_id);

			while (rs.next()) {
				micro_pre_num = Utility.trimNull(rs.getString("NUM"));
				break;
			}
		} catch (Exception e) {
			throw new BusiException("小额预约(300W以下)份数查询失败:" + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}	
		return micro_pre_num;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#modiMoneyStatus(enfo.crm.vo.PreContractVO)
	 */
	@Override
	public void modiMoneyStatus(PreContractVO vo) throws BusiException {
		try {
			String update_sql = "UPDATE TPRECONTRACT SET MONEY_STATUS = 1 WHERE SERIAL_NO = " + vo.getSerial_no();	
			super.executeSql(update_sql);

		} catch (Exception e) {
			throw new BusiException("修改缴款状态: " + e.getMessage());
		}
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#getProductPreList(enfo.crm.vo.PreContractVO)
	 */
	@Override
	public List getProductPreList(PreContractVO vo) throws BusiException {
		String sql = "{call SP_STAT_PRODUCTPRE(?)}";
		List rsList = null;	
		Object[] param = new Object[1];
		param[0] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		rsList = CrmDBManager.listBySql(sql,param);
		return rsList;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#getProductPreList(enfo.crm.vo.PreContractVO, int, int)
	 */
	@Override
	public IPageList getProductPreList(PreContractVO vo,int pageIndex,int pageSize) throws BusiException{
		IPageList rsList = null;	
		String sqlStr = "{call SP_STAT_PRODUCTPRE(?)}";
		Object[] params = new Object[1];
		params[0] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		rsList = CrmDBManager.listProcPage(sqlStr, params,pageIndex,pageSize);
		return rsList;
		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#importPreCustCon(enfo.crm.vo.PreContractVO)
	 */
	@Override
	public void importPreCustCon(PreContractVO vo)throws BusiException{
		Object[] params = new Object[12];
		params[0] = vo.getCustomer_id();
		params[1] = vo.getCust_name();
		params[2] = vo.getSex_name();
		params[3] = vo.getMobile();
		params[4] = vo.getPre_money();
		params[5] = vo.getPre_date();
		params[6] = vo.getProduct_name();
		params[7] = vo.getProduct_code();
		params[8] = vo.getAddress();
		params[9] = vo.getEmail();
		params[10] = vo.getInput_man();
		params[11] = vo.getFile_name();
		CrmDBManager.cudProc("{?= call SP_IMPORT_PRECUSTOMERCON(?,?,?,?,?,?,?,?,?,?,?,?)}",params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#importByListPreContract(enfo.crm.vo.PreContractVO)
	 */
	@Override
	public List importByListPreContract(PreContractVO vo)throws BusiException{
		Object[] params = new Object[4];
		params[0] = Utility.trimNull(vo.getCust_name());
		params[1] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
		params[2] = Utility.trimNull(vo.getProduct_name());
		params[3] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		return CrmDBManager.listBySql("{call SP_QUERY_PRECUSTOMERCON(?,?,?,?)}",params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#importListProcPagePreContract(enfo.crm.vo.PreContractVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList importListProcPagePreContract(PreContractVO vo,
			String[]totalColumn,int pageIndex,int pageSize)throws BusiException{
		Object[] params = new Object[8];
		params[0] = Utility.trimNull(vo.getCust_name());
		params[1] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
		params[2] = Utility.trimNull(vo.getProduct_name());
		params[3] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		params[4] = Utility.parseInt(vo.getStart_date(),new Integer(0));
		params[5] = Utility.parseInt(vo.getEnd_date(),new Integer(0));
		params[6] = Utility.trimNull(vo.getProduct_code());
		params[7] = Utility.parseInt(vo.getTurn_flag(),new Integer(0));
		return CrmDBManager.listProcPage("{call SP_QUERY_PRECUSTOMERCON(?,?,?,?,?,?,?,?)}",
				params,totalColumn,pageIndex,pageSize);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#updateImportPreContract(enfo.crm.vo.PreContractVO)
	 */
	@Override
	public void updateImportPreContract(PreContractVO vo)throws BusiException{
		Object[] params = new Object[5];
		String updateSql = "{?= call SP_MODI_PRECUSTOMERCON(?,?,?,?,?)}";
		params[0] = Utility.parseInt(vo.getCust_id(),new Integer(0));
		params[1] = Utility.parseInt(vo.getCustomer_id(),new Integer(0));
		params[2] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
		params[3] = Utility.trimNull(vo.getPre_code());
		params[4] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		CrmDBManager.cudProc(updateSql,params);
	}
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#statPreContract2(enfo.crm.vo.PreContractVO)
	 */
    @Override
	public List statPreContract2(PreContractVO vo)throws BusiException{
        Object[] params = new Object[2];
        params[0] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
        params[1] = Utility.parseInt(vo.getPre_product_id(), new Integer(0));
        return CrmDBManager.listBySql("{call SP_STAT_TPRECONTRACT2(?,?)}", params);
    }  
        
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PreContractLocal#checkPreContract(enfo.crm.vo.PreContractVO)
	 */
    @Override
	public void checkPreContract(PreContractVO vo)throws BusiException{
    	String sql = "{? = call SP_CHECK_TPRECONTRACT(?,?,?)}";
    	Object[] params = new Object[3];
    	params[0] = Utility.parseInt(vo.getSerial_no(),new Integer(0));
    	params[1] = new Integer(2); // 审核通过
    	params[2] = Utility.parseInt(vo.getInput_man(),new Integer(0));
    	CrmDBManager.cudProc(sql,params);
    }
}