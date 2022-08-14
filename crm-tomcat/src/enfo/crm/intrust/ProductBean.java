package enfo.crm.intrust;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.dao.IPageList;
import enfo.crm.dao.IntrustDBManager;
import enfo.crm.tools.Utility;
import enfo.crm.vo.GainTotalVO;
import enfo.crm.vo.ProductVO;
import net.sf.json.JSONArray;

@Component(value="product")
public class ProductBean extends enfo.crm.dao.IntrustBusiExBean implements ProductLocal {

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#productList(enfo.crm.vo.ProductVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList productList(ProductVO vo, String[] totalColumn,
			int pageIndex, int pageSize) throws BusiException {
		Object[] param = new Object[27];
		param[0] = new Integer(1);
		param[1] = vo.getProduct_code();
		param[2] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		param[3] = vo.getProduct_status();
		param[4] = "";
		param[5] = "";
		param[6] = new Integer(0);
		param[7] = vo.getProduct_name();
		param[8] = new Integer(0);
		param[9] = vo.getIntrust_flag1();
		param[10] = new Integer(0);
		param[11] = "";
		param[12] = "";
		param[13] = Utility.parseInt(vo.getStart_date(), new Integer(0));
		param[14] = Utility.parseInt(vo.getEnd_date(), new Integer(0));
		param[15] = new Integer(0);
		param[16] = new Integer(0);
		param[17] = new Integer(0);
		param[18] = new Integer(0);
		param[19] = new Integer(0);
		param[20] = new Integer(0);
		param[21] = new Integer(0);
		param[22] = new Integer(0);
		param[22] = Utility.parseInt(vo.getCheck_flag(), new Integer(0));
		param[23] = new Integer(-1);
		param[24] = new Integer(-1);
		param[25] = "";
		param[26] = Utility.parseInt(vo.getOpen_flag(), new Integer(0));
		try {
			return super
					.listProcPage(
							"{call SP_QUERY_TPRODUCT (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
							param,totalColumn, pageIndex, pageSize);
		} catch (BusiException e) {
			throw new BusiException("查询产品信息失败" + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#load(enfo.crm.vo.ProductVO)
	 */
	@Override
	public List load(ProductVO vo) throws BusiException {
		List rsList = null;

		Object[] param = new Object[1];
		param[0] = vo.getProduct_id();

		rsList = super.listBySql("{call SP_QUERY_TPRODUCT_ID(?)}", param);
		
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#queryPeriodDate1(enfo.crm.vo.ProductVO)
	 */
	@Override
	public List queryPeriodDate1(ProductVO vo) throws BusiException {
		List rsList = null;

		Object[] param = new Object[2];
		param[0] = vo.getProduct_id();
		param[1] = vo.getTask_type();

		rsList = super
				.listBySql("{call SP_QUERY_TPRODUCTGAINDATE(?,?)}", param);
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#queryByProductID1(enfo.crm.vo.ProductVO, int, int)
	 */
	@Override
	public IPageList queryByProductID1(ProductVO vo, int pageIndex, int pageSize)
			throws BusiException {
		Object[] param = new Object[1];
		param[0] = vo.getProduct_id();
		try {
			return super.listProcPage("{call SP_QUERY_TGAINTOTAL_ID(?)}",
					param, pageIndex, pageSize);
		} catch (BusiException e) {
			throw new BusiException("查询信息失败" + e.getMessage());
		}
	}

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#listGainTotal(enfo.crm.vo.GainTotalVO, int, int)
	 */
    @Override
	public IPageList listGainTotal(GainTotalVO vo, int page, int pagesize) throws Exception {
        Object[] params = new Object[6];
        params[0] = Utility.parseInt(vo.getBook_code(), new Integer(0));
        params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
        params[2] = Utility.trimNull(vo.getSy_type());
        params[3] = Utility.parseInt(vo.getSy_date(), new Integer(0));
        params[4] = Utility.parseInt(vo.getInput_man(), new Integer(0));
        params[5] = Utility.parseInt(vo.getSub_product_id(), new Integer(0));
        return super.listProcPage("{call SP_QUERY_TGAINTOTAL(?,?,?,?,?,?)}", params, page, pagesize);
    }
    
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#querytz1(enfo.crm.vo.ProductVO)
	 */
	@Override
	public List querytz1(ProductVO vo) throws Exception {
		List rsList = null;
		Object[] param = new Object[1];
		param[0] = vo.getProduct_id();

		rsList = super.listBySql("{call SP_QUERY_TPRODUCT_TZ(?)}", param);
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#rg_list(enfo.crm.vo.ProductVO)
	 */
	@Override
	public List rg_list(ProductVO vo) throws BusiException{
		List rsList = new ArrayList();
		Map map = new HashMap();
		Connection conn = null;
		CallableStatement stmt = null;

		try{
			conn = IntrustDBManager.getConnection();
			stmt = conn.prepareCall(
					"{call SP_QUERY_TPRODUCT_HTTOTAL (?,?,?,?,?,?,?)}",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1, vo.getProduct_id() == null ? 0 : vo.getProduct_id()
					.intValue());
			stmt.registerOutParameter(2, Types.INTEGER);
			stmt.registerOutParameter(3, Types.VARCHAR);
			stmt.registerOutParameter(4, Types.INTEGER);
			stmt.registerOutParameter(5, Types.VARCHAR);
			stmt.registerOutParameter(6, Types.INTEGER);
			stmt.registerOutParameter(7, Types.VARCHAR);
			stmt.execute();

			map.put("gr_count", new Integer(stmt.getInt(2)));
			map.put("gr_money", stmt.getBigDecimal(3));
			map.put("jg_count", new Integer(stmt.getInt(4)));
			map.put("jg_money", stmt.getBigDecimal(5));
			map.put("qualified_count",new Integer(stmt.getInt(6)));
			map.put("qualified_money", stmt.getBigDecimal(7));
			rsList.add(map);
		}
		catch(Exception e){
			throw new BusiException("查询认购" + e.getMessage());
		}
		finally{
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				throw new BusiException("数据库链接关闭异常：" + e.getMessage());
			}
		}

		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#list_sy(enfo.crm.vo.ProductVO)
	 */
	@Override
	public List list_sy(ProductVO vo) throws BusiException {
		List rsList = null;
		Object[] param = new Object[3];
		param[0] = Utility.parseInt(vo.getBook_code(), new Integer(0));
		param[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		param[2] = Utility.parseInt(vo.getInput_man(), new Integer(0));

		rsList = super.listBySql("{call SP_QUERY_TPRODUCT_PROVLEVEL (?,?,?)}",
				param);
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#listStatus1(enfo.crm.vo.ProductVO)
	 */
	@Override
	public List listStatus1(ProductVO vo) throws BusiException {
		List rsList = null;
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getBook_code(), new Integer(0));
		params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));

		rsList = super.listBySql("{call SP_QUERY_TBENIFITOR_STATUS (?,?)}",
				params);
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#list_cb(enfo.crm.vo.ProductVO, int, int)
	 */
	@Override
	public IPageList list_cb(ProductVO vo, int pageIndex, int pageSize)
			throws BusiException {
		List rsList = null;
		Object[] params = new Object[12];
		params[0] = vo.getBook_code();
		params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[2] = vo.getContract_bh();
		params[3] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		params[4] = Utility.parseInt(vo.getTrans_date_begin(), new Integer(0));
		params[5] = Utility.parseInt(vo.getTrans_date_end(), new Integer(0));
		params[6] = vo.getFrom_cust_name();
		params[7] = vo.getTo_cust_name();
		params[8] = vo.getTrans_type();
		params[9] = Utility.parseInt(vo.getCheck_flag(), new Integer(0));
		params[10] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[11] = vo.getContract_sub_bh();

		try {
			return super.listProcPage(
					"{call SP_QUERY_TBENCHANGES(?,?,?,?,?,?,?,?,?,?,?,?)}",
					params, pageIndex, pageSize);
		} catch (BusiException e) {
			throw new BusiException("查询信息失败" + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#list_xg(enfo.crm.vo.ProductVO)
	 */
	@Override
	public List list_xg(ProductVO vo) throws BusiException {
		List rsList = null;
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[1] = Utility.parseInt(vo.getCheck_flag(), new Integer(0));

		rsList = super.listBySql("{call SP_QUERY_TPRODUCTDETAIL(?,?)}", params);
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#list_tl(enfo.crm.vo.ProductVO)
	 */
	@Override
	public List list_tl(ProductVO vo) throws BusiException {
		List rsList = null;
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[1] = Utility.parseInt(vo.getBook_code(), new Integer(0));
		params[2] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		rsList = super.listBySql("{call SP_QUERY_TBANKRATECONFIG (?,?,?)}",
				params);
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#adddeletecity1(enfo.crm.vo.ProductVO)
	 */
	@Override
	public Integer adddeletecity1(ProductVO vo) throws Exception {
		Integer city_serial_no = new Integer(0);
		String sqlStr = "{?=call SP_ADD_TPRODUCTCITY(?,?,?,?,?,?)}";
        Object[] param = new Object[5];
        param[0] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
        param[1] = vo.getCity_name();
        param[2] = Utility.parseInt(vo.getInput_man(), new Integer(0));
        param[3] = Utility.trimNull(vo.getGov_prov_pegional());
        param[4] = Utility.trimNull(vo.getGov_pegional());
        city_serial_no = (Integer) super.cudProc(sqlStr, param, 7, java.sql.Types.INTEGER);
        
        return city_serial_no;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#querycity1(enfo.crm.vo.ProductVO)
	 */
	@Override
	public List querycity1(ProductVO vo) throws Exception {
		List rsList = null;
		Object[] param = new Object[2];
		param[0] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		param[1] = Utility.parseInt(vo.getCity_serial_no(), new Integer(0));
		rsList = super.listBySql("{call SP_QUERY_TPRODUCTCITY(?,?)}", param);
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#addDeleteDate1(enfo.crm.vo.ProductVO)
	 */
	@Override
	public void addDeleteDate1(ProductVO vo) throws Exception {
		String strSql = "{?=call SP_ADD_TPRODUCTDATE(?,?,?,?,?,?,?,?)}";

		Object[] param = new Object[8];
		param[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		param[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		param[2] = Utility.parseInt(vo.getTrade_date(), new Integer(0));
		param[3] = vo.getTrade_type();
		param[4] = vo.getTrade_type_name();
		param[5] = vo.getDescription();
		param[6] = Utility.parseBigDecimal(vo.getTrade_rate(),
				new BigDecimal(0));
		param[7] = Utility.parseInt(vo.getInput_man(), new Integer(0));

		super.cudProc(strSql, param);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#queryDate1(enfo.crm.vo.ProductVO)
	 */
	@Override
	public List queryDate1(ProductVO vo) throws Exception {
		List rsList = null;
		Object[] param = new Object[3];
		param[0] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		param[1] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		param[2] = vo.getTrade_type();
		rsList = super.listBySql("{call SP_QUERY_TPRODUCTDATE(?,?,?)}", param);
		return rsList;

	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#delete(enfo.crm.vo.ProductVO)
	 */
	@Override
	public void delete(ProductVO vo) throws Exception {
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		super.cudProc("{?=call SP_DEL_TMANRATECONFIG (?,?)}", params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#list_gfp(enfo.crm.vo.ProductVO)
	 */
	@Override
	public List list_gfp(ProductVO vo) throws Exception {
		List rsList = null;
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[1] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		rsList = super
				.listBySql("{call SP_QUERY_TMANRATECONFIG (?,?)}", params);
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#append_glf(enfo.crm.vo.ProductVO)
	 */
	@Override
	public void append_glf(ProductVO vo) throws Exception {
		Object[] params = new Object[5];
		params[0] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[1] = Utility.parseBigDecimal(vo.getBegin_price(),
				new java.math.BigDecimal(0));
		params[2] = Utility.parseBigDecimal(vo.getEnd_price(),
				new java.math.BigDecimal(0));
		params[3] = Utility.parseBigDecimal(vo.getDiv_rate(),
				new java.math.BigDecimal(0));
		params[4] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		super.cudProc("{?=call SP_ADD_TMANRATECONFIG (?,?,?,?,?)}", params);

	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#save_glf(enfo.crm.vo.ProductVO)
	 */
	@Override
	public void save_glf(ProductVO vo) throws Exception {
		Object[] params = new Object[5];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseBigDecimal(vo.getBegin_price(),
				new java.math.BigDecimal(0));
		params[2] = Utility.parseBigDecimal(vo.getEnd_price(),
				new java.math.BigDecimal(0));
		params[3] = Utility.parseBigDecimal(vo.getDiv_rate(),
				new java.math.BigDecimal(0));
		params[4] = Utility.parseInt(vo.getInput_man(), new Integer(0));

		super.cudProc("{?=call SP_MODI_TMANRATECONFIG (?,?,?,?,?)}", params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#list_manrateConfig(enfo.crm.vo.ProductVO)
	 */
	@Override
	public List list_manrateConfig(ProductVO vo) throws BusiException{
	      Object[] params = new Object[2];
	      params[0] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
	      params[1] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
	    
	      try {
				return super.listBySql("{call SP_QUERY_TMANRATECONFIG (?,?)}", params);
			} catch (BusiException e) {
				throw new BusiException("产品管理费提取分段信息查询失败：" + e.getMessage());
			}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#list_openDate(enfo.crm.vo.ProductVO)
	 */
	@Override
	public List list_openDate(ProductVO vo) throws BusiException{
		Object[] params = new Object[3];
		params[0] = vo.getSerial_no();
		params[1] = vo.getProduct_id();
		params[2] = vo.getInput_man();
		
	      try {
			return super.listBySql("{call SP_QUERY_TOPENDATE(?,?,?)}", params);
		} catch (BusiException e) {
			throw new BusiException("产品开放日信息查询失败：" + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#listSubProduct(enfo.crm.vo.ProductVO)
	 */
	@Override
	public List listSubProduct(ProductVO vo) throws BusiException{
		String listSql = "{call SP_QUERY_TSUBPRODUCT(?,?,?,?,?,?)}";
		List rsList = null;
		Object[] params = new Object[6];
        params[0] = vo.getProduct_id();
        params[1] = vo.getSub_product_id();
        params[2] = vo.getList_id();
        params[3] = vo.getList_name();
        params[4] = vo.getProduct_code();
        params[5] = vo.getProduct_name();
        rsList = super.listProcAll(listSql, params);
        return rsList;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#listSubProductForPage(enfo.crm.vo.ProductVO, int, int)
	 */
	@Override
	public IPageList listSubProductForPage(ProductVO vo, int pageIndex, int pageSize) throws BusiException{
		String listSql = "{call SP_QUERY_TSUBPRODUCT(?,?,?,?,?,?,?,?,?)}";
		IPageList rsList = null;
		Object[] params = new Object[9];
        params[0] = vo.getProduct_id();
        params[1] = vo.getSub_product_id();
        params[2] = vo.getList_id();
        params[3] = vo.getList_name();
        params[4] = vo.getProduct_code();
        params[5] = vo.getProduct_name();
        params[6] = vo.getCheck_flag();
        params[7] = vo.getIntrust_type1();
        params[8] = vo.getProduct_status();
        
        rsList = super.listProcPage(listSql,params,pageIndex, pageSize);
        return rsList;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#StatSubProductByProv(enfo.crm.vo.ProductVO)
	 */
	@Override
	public List StatSubProductByProv(ProductVO vo) throws BusiException{
		String listSql = "{call SP_STAT_PRODUCT_SUBPRODUCT_CRM(?)}";
		List rsList = null;
		Object[] params = new Object[1];
		params[0] = vo.getProduct_id();
		
        rsList = CrmDBManager.listProcAll(listSql, params);
        return rsList;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#listTask(enfo.crm.vo.ProductVO)
	 */
	@Override
	public List listTask(ProductVO vo) throws BusiException{
		String listSql = "{call SP_QUERY_TTASKJHB(?,?,?,?,?,?,?,?)}";
		List rsList = null;
		Object[] params = new Object[8];
		params[0] = vo.getSerial_no();
		params[1] = vo.getProduct_id();
		params[2] = vo.getStatus();	
		params[3] = vo.getTask_name();
		params[4] = vo.getStart_date();
		params[5] = vo.getEnd_date();
		params[6] = vo.getTask_type();
		params[7] = vo.getBook_code();
        rsList = super.listBySql(listSql, params);
        return rsList;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#listTask(enfo.crm.vo.ProductVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList listTask(ProductVO vo, String[] totalColumn,
			int pageIndex, int pageSize) throws BusiException{
		String listSql = "{call SP_QUERY_TTASKJHB(?,?,?,?,?,?,?,?)}";
		IPageList rsList = null;
		Object[] params = new Object[8];
		params[0] = vo.getSerial_no();
		params[1] = vo.getProduct_id();
		params[2] = vo.getStatus();	
		params[3] = vo.getTask_name();
		params[4] = vo.getStart_date();
		params[5] = vo.getEnd_date();
		params[6] = vo.getTask_type();
		params[7] = vo.getBook_code();
        rsList = super.listProcPage(listSql, params,totalColumn, pageIndex, pageSize);
        return rsList;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#appendTask(enfo.crm.vo.ProductVO)
	 */
	@Override
	public void appendTask(ProductVO vo) throws Exception {
		Object[] params = new Object[16];
		params[0] = vo.getProduct_id();
		params[1] = null;
		params[2] = new Integer(0);
		params[3] = vo.getTask_type();
		params[4] = vo.getTask_name();
		params[5] = vo.getTrade_date();
		params[6] = new Integer(7);
		params[7] = vo.getInput_man();
		params[8] = vo.getInput_man().toString();
		params[9] = new Integer(1);
		params[10] = new Integer(0);
		params[11] = new Integer(0);
		params[12] = vo.getInput_man();
		params[13] = new Integer(2);
		params[14] = new Integer(0);
		params[15] = vo.getSummary();
		super.cudProc("{?=call SP_ADD_TTASKJHB(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", params);

	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#modiTask(enfo.crm.vo.ProductVO)
	 */
	@Override
	public void modiTask(ProductVO vo) throws Exception {
		Object[] params = new Object[9];
		params[0] = vo.getSerial_no();
		params[1] = vo.getTrade_date();
		params[2] = new Integer(7);
		params[3] = vo.getInput_man();
		params[4] = vo.getInput_man().toString();
		params[5] = new Integer(0);
		params[6] = vo.getInput_man();
		params[7] = new Integer(2);
		params[8] = vo.getSummary();
		super.cudProc("{?=call SP_MODI_TTASKJHB (?,?,?,?,?,?,?,?,?)}", params);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#deleteTask(enfo.crm.vo.ProductVO)
	 */
	@Override
	public void deleteTask(ProductVO vo) throws Exception {
		Object[] params = new Object[2];
		params[0] = vo.getSerial_no();
		params[1] = vo.getInput_man();
		super.cudProc("{?=call SP_DEL_TTASKJHB(?,?)}", params);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#modiManager(enfo.crm.vo.ProductVO)
	 */
	@Override
	public void modiManager(ProductVO vo) throws Exception {
		Object[] params = new Object[3];
		params[0] = vo.getProduct_id();
		params[1] = vo.getService_man();
		params[2] = vo.getInput_man();
		CrmDBManager.cudProc("{?=call SP_MODI_PRODUCT_SERVICEMAN(?,?,?)}", params);
	}
	
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#queryProductLimit(enfo.crm.vo.ProductVO)
	 */   
    @Override
	public List queryProductLimit(ProductVO vo) throws Exception{
    	List rsList = null;
        Object[] param = new Object[2];
        param[0] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
        param[1] = Utility.parseInt(vo.getSub_product_id(), new Integer(0));
        try {
        	rsList = super.listBySql("{call SP_QUERY_TPRODUCTLIMIT(?,?)}", param);
        } catch (Exception e) {
            throw new BusiException("查询产品销售设置失败！" + e.getMessage());
        }
		
		return rsList;
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#updateProductLimit(enfo.crm.vo.ProductVO)
	 */      
    @Override
	public void updateProductLimit(ProductVO vo) throws Exception {
        Object[] param = new Object[29];
        
        param[0] = Utility.parseInt(Utility.trimNull(vo.getProduct_id()), new Integer(0));
        param[1] = Utility.parseInt(vo.getQualified_flag(), new Integer(0));
        param[2] = Utility.parseInt(vo.getQualified_num(), new Integer(0));
        param[3] = Utility.parseBigDecimal(vo.getQualified_money(), new BigDecimal(0));
        param[4] = Utility.parseInt(vo.getAsfund_flag(), new Integer(0));
        param[5] = Utility.parseInt(vo.getGain_flag(), new Integer(0));
        param[6] = Utility.parseInt(vo.getOpen_gain_flag(), new Integer(0));
        param[7] = Utility.parseInt(vo.getIs_bank_consign(), new Integer(2));
        
        param[8] = Utility.parseBigDecimal(vo.getJg_min_subamount(), new BigDecimal(0));
        param[9] = Utility.parseBigDecimal(vo.getGr_min_subamount(), new BigDecimal(0));
        param[10] = Utility.parseBigDecimal(vo.getJg_min_bidsamount(), new BigDecimal(0));
        param[11] = Utility.parseBigDecimal(vo.getGr_min_bidsamount(), new BigDecimal(0));
        param[12] = Utility.parseBigDecimal(vo.getGr_min_appbidsamount(), new BigDecimal(0));
        param[13] = Utility.parseBigDecimal(vo.getJg_min_appbidsamount(), new BigDecimal(0));
        param[14] = Utility.parseBigDecimal(vo.getMin_redeem_vol(), new BigDecimal(0));
        
        param[15] = Utility.parseInt(vo.getCoerce_redeem_flag(), new Integer(2));
        param[16] = Utility.parseInt(vo.getLarge_redeem_flag(), new Integer(2));
        param[17] = Utility.parseInt(vo.getLarge_redeem_condition(), new Integer(1));
        param[18] = Utility.parseBigDecimal(vo.getLarge_redeem_percent(), new BigDecimal(0));
        param[19] = Utility.parseInt(vo.getLarge_redeem_deal(), new Integer(0));
        param[20] = vo.getContract_terms();        
        param[21] = Utility.parseInt(vo.getInput_man(), new Integer(0));
        param[22] = vo.getPeriod_unit();
        param[23] = vo.getLot_decimal_flag();
		param[24] = vo.getRedeem_freeze();
		param[25] = Utility.parseInt(vo.getSub_product_id(), new Integer(0));
        param[26] = vo.getRg_bond_flag();
		param[27] = vo.getMin_redeem_vol2();
		param[28] = vo.getContract_single_flag();
        
        try {
            super.cudProc("{?=call SP_ADD_TPRODUCTLIMIT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", param);
        } catch (Exception e) {
            throw new BusiException("新增修改产品销售设置失败！" + e.getMessage());
        }
    }
    
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#modiCRMProduct(enfo.crm.vo.ProductVO)
	 */
	@Override
	public void modiCRMProduct(ProductVO vo) throws BusiException {
		Object[] params = new Object[19];
		params[0] = vo.getProduct_id();
		params[1] = vo.getProduct_code();
		params[2] = vo.getProduct_name();
		params[3] = vo.getRisk_level();
		params[4] = vo.getInput_man();
		params[5] = Utility.parseInt(vo.getR_channel_id(),new Integer(0));
		params[6] = Utility.parseBigDecimal(vo.getR_chanel_rate(), new BigDecimal(0));
		params[7] = Utility.parseInt(vo.getPre_start_date(),new Integer(0));
		params[8] = Utility.parseInt(vo.getPre_end_date(),new Integer(0));
		params[9] = vo.getPre_code();
		params[10] = Utility.parseBigDecimal(vo.getPre_max_rate(), new BigDecimal(0));
		params[11] = vo.getCommission_rate();
		params[12] = vo.getPre_start_time();
		params[13] = vo.getPre_end_time();
		params[14] = vo.getSub_product_id();
		params[15] = vo.getList_name();
		params[16] = vo.getTemplate_id();
		params[17] = vo.getPre_min_amount();
		params[18] = vo.getUp_to_show();
//		params[19] = vo.getProduct_cost();
//		params[20] = vo.getChannel_cost(); 
//		params[21] = vo.getDefault_prevaliddays();
//		params[22] = vo.getUse_tproduct();
//		params[23] = vo.getProportion();
		CrmDBManager.cudProc("{?=call SP_MODI_TPRODUCT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#listPageCrmProduct(enfo.crm.vo.ProductVO, int, int)
	 */
	@Override
	public IPageList listPageCrmProduct(ProductVO vo, int pageIndex, int pageSize) throws BusiException{
		String listSql = "{call SP_QUERY_TPRODUCT(?,?,?,?,?,?,?,?,?,?,?,?)}";

		Object[] params = new Object[12];
		params[0] = vo.getBook_code();
		params[1] = vo.getProduct_id();
		params[2] = vo.getProduct_code();
		params[3] = vo.getProduct_name();
		params[4] = vo.getProduct_status();
		params[5] = vo.getOpen_flag();
		params[6] = vo.getStart_date();
		params[7] = vo.getEnd_date();
		params[8] = vo.getCheck_flag();
		params[9] = vo.getIntrust_flag1();
		params[10] = vo.getSale_status();
		params[11] = vo.getInput_man();

		try {
			return CrmDBManager.listProcPage(listSql,
					params, pageIndex, pageSize);
		} catch (BusiException e) {
			throw new BusiException("查询信息失败" + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#listPageCrmSubProduct(enfo.crm.vo.ProductVO, int, int)
	 */
	@Override
	public IPageList listPageCrmSubProduct(ProductVO vo, int pageIndex, int pageSize) throws BusiException{
		String listSql = "{call SP_QUERY_TPRODUCT_SUBPRODUCT(?,?,?,?,?,?,?,?,?,?,?,?)}";

		Object[] params = new Object[12];
		params[0] = vo.getBook_code();
		params[1] = vo.getProduct_id();
		params[2] = vo.getProduct_code();
		params[3] = vo.getProduct_name();
		params[4] = vo.getProduct_status();
		params[5] = vo.getOpen_flag();
		params[6] = vo.getStart_date();
		params[7] = vo.getEnd_date();
		params[8] = vo.getCheck_flag();
		params[9] = vo.getIntrust_flag1();
		params[10] = vo.getSale_status();
		params[11] = vo.getInput_man();

		try {
			return CrmDBManager.listProcPage(listSql,
					params, pageIndex, pageSize);
		} catch (BusiException e) {
			throw new BusiException("查询信息失败" + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#listCrmProduct(enfo.crm.vo.ProductVO)
	 */
	@Override
	public List listCrmProduct(ProductVO vo) throws BusiException{
		String listSql = "{call SP_QUERY_TPRODUCT(?,?,?,?,?,?,?,?,?,?,?,?)}";
		List rsList = null;
		Object[] params = new Object[12];
		params[0] = vo.getBook_code();
		params[1] = vo.getProduct_id();
		params[2] = vo.getProduct_code();
		params[3] = vo.getProduct_name();
		params[4] = vo.getProduct_status();
		params[5] = vo.getOpen_flag();
		params[6] = vo.getStart_date();
		params[7] = vo.getEnd_date();
		params[8] = vo.getCheck_flag();
		params[9] = vo.getIntrust_flag1();
		params[10] = vo.getSale_status();
		params[11] = vo.getInput_man();

        rsList = CrmDBManager.listBySql(listSql, params);
        return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#modiCRMProductCheck(enfo.crm.vo.ProductVO)
	 */
	@Override
	public void modiCRMProductCheck(ProductVO vo) throws Exception {
		Object[] params = new Object[4];
		params[0] = vo.getProduct_id();
		params[1] = vo.getSale_status();
		params[2] = vo.getInput_man();
		params[3] = vo.getSub_product_id();
		CrmDBManager.cudProc("{?=call SP_CHECK_TPRODUCT(?,?,?,?)}", params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#query(java.lang.String, enfo.crm.vo.ProductVO, java.lang.String[], int, int)
	 */
	 @Override
	public IPageList query(String sQuery,ProductVO vo,String[] totalColumn,int pageIndex,int pageSize) throws Exception {
        String[] strQuery = parseQuery(sQuery);
        IPageList pageList = null;
        Object[] param = new Object[15];
        param[0] = vo.getBook_code();
        param[1] = strQuery[3];
        param[2] = new Integer(0);
        param[3] = strQuery[2];
        param[4] = strQuery[0];
        param[5] = strQuery[1];
        param[6] = vo.getInput_man();
        param[7] = strQuery[4];
        param[8] = vo.getProduct_from();
        param[9] = vo.getDepart_id();
        param[10] = Utility.parseInt(vo.getOpen_flag(), 0);
        param[11] = vo.getIntrust_flag1();//----2011-4-21 add---
        param[12] = new Integer(0);
        param[13] = new Integer(0);
        param[14] = vo.getInvest_field();
        pageList = CrmDBManager.listProcPage("{call SP_QUERY_TPRODUCT_VALID(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", param,totalColumn,pageIndex, pageSize);
        return pageList;
    }
	 
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#parseQuery(java.lang.String)
	 */
    @Override
	public String[] parseQuery(String srcQuery) {
        String intrust_type = "";
        String currency_id = "";
        String product_status = "";
        String product_code = "";
        String product_name = "";

        if ((srcQuery != null) && (!srcQuery.equals(""))) {
            int index = srcQuery.indexOf("$");
            intrust_type = srcQuery.substring(0, index);
            int index2 = srcQuery.indexOf("$", index + 1);
            currency_id = srcQuery.substring(index + 1, index2);
            int index3 = srcQuery.indexOf("$", index2 + 1);
            product_status = srcQuery.substring(index2 + 1, index3);
            int index4 = srcQuery.indexOf("$", index3 + 1);
            product_code = srcQuery.substring(index3 + 1, index4);
            product_name = srcQuery.substring(index4 + 1, srcQuery.length());
        }
        return (new String[] { intrust_type, currency_id, product_status,
                product_code, product_name });

    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#searchList(enfo.crm.vo.ProductVO)
	 */
	@Override
	public List searchList(ProductVO vo) throws BusiException{
	    List rsList = null;
	    Object[] param = new Object[27];
	    param[0] = new Integer(1);
		param[1] = vo.getProduct_code();
		param[2] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		param[3] = vo.getProduct_status();
		param[4] = "";
		param[5] = "";
		param[6] = new Integer(0);
		param[7] = vo.getProduct_name();
		param[8] = new Integer(0);
		param[9] = vo.getIntrust_flag1();
		param[10] = new Integer(0);
		param[11] = "";
		param[12] = "";
		param[13] = Utility.parseInt(vo.getStart_date(), new Integer(0));
		param[14] = Utility.parseInt(vo.getEnd_date(), new Integer(0));
		param[15] = new Integer(0);
		param[16] = new Integer(0);
		param[17] = new Integer(0);
		param[18] = new Integer(0);
		param[19] = new Integer(0);
		param[20] = new Integer(0);
		param[21] = new Integer(0);
		param[22] = new Integer(0);
		param[22] = Utility.parseInt(vo.getCheck_flag(), new Integer(0));
		param[23] = new Integer(-1);
		param[24] = new Integer(-1);
		param[25] = "";
		param[26] = Utility.parseInt(vo.getOpen_flag(), new Integer(0));
	    rsList = super.listBySql("{call SP_QUERY_TPRODUCT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",param);
	    return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#addMarketTrench(enfo.crm.vo.ProductVO)
	 */
	@Override
	public void addMarketTrench(ProductVO vo) throws Exception {
		Object[] params = new Object[7];
		params[0] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[1] = Utility.trimNull(vo.getChannel_type());
		params[2] = Utility.parseInt(vo.getR_channel_id(),new Integer(0));
		params[3] = Utility.parseBigDecimal(vo.getR_chanel_rate(), new BigDecimal(0));
		params[4] = Utility.trimNull(vo.getSummary());
		params[5] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		params[6] = Utility.parseInt(vo.getSub_product_id(),new Integer(0));
		
		try {
			CrmDBManager.cudProc("{?=call SP_ADD_TMARKET_TRENCH(?,?,?,?,?,?,?)}", params);
		} catch (Exception e) {
			throw new Exception("新增产品销售渠道信息失败："+e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#modiMarketTrench(enfo.crm.vo.ProductVO)
	 */
	@Override
	public void modiMarketTrench(ProductVO vo) throws Exception {
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseBigDecimal(vo.getR_chanel_rate(), new BigDecimal(0));
		params[2] = Utility.trimNull(vo.getSummary());
		params[3] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		
		try {
			CrmDBManager.cudProc("{?=call SP_MODI_TMARKET_TRENCH(?,?,?,?)}", params);
		} catch (Exception e) {
			throw new Exception("修改产品销售渠道信息失败："+e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#delMarketTrench(enfo.crm.vo.ProductVO)
	 */
	@Override
	public void delMarketTrench(ProductVO vo) throws Exception {
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		
		try {
			CrmDBManager.cudProc("{?=call SP_DEL_TMARKET_TRENCH(?,?)}", params);
		} catch (Exception e) {
			throw new Exception("删除产品销售渠道信息失败："+e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#queryMarketTrench(enfo.crm.vo.ProductVO)
	 */
	@Override
	public List queryMarketTrench(ProductVO vo) throws Exception {
		List list = null;
		Object[] params = new Object[5];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[2] = Utility.trimNull(vo.getChannel_type());
		params[3] = Utility.parseInt(vo.getR_channel_id(), new Integer(0));
		params[4] = Utility.parseInt(vo.getSub_product_id(), new Integer(0));
		
		list = CrmDBManager.listBySql("{call SP_QUERY_TMARKET_TRENCH(?,?,?,?,?)}", params);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#queryProductTable(enfo.crm.vo.ProductVO)
	 */
	@Override
	public List queryProductTable(ProductVO vo) throws BusiException {
		List list = null;
		Object[] params = new Object[6];
		params[0] = Utility.parseInt(vo.getBook_code(), new Integer(1));
		params[1] = Utility.trimNull(vo.getProduct_code());
		params[2] = Utility.trimNull(vo.getProduct_name());
		params[3] = Utility.parseInt(vo.getDepart_id(), new Integer(0));
		params[4] = Utility.parseInt(vo.getAdmin_manager(), new Integer(0));
		params[5] = Utility.parseInt(vo.getInput_man(), new Integer(888));

		try {

			list = super.listBySql(
					"{call SP_QUERY_TPRODUC_TABLE(?,?,?,?,?,?)}", params);
		} catch (Exception e) {
			throw new BusiException("查询产品表格信息失败: " + e.getMessage());
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#queryProductTableJosn(enfo.crm.vo.ProductVO)
	 */
	@Override
	public String queryProductTableJosn(ProductVO vo) throws BusiException {
		List list = null;
		Object[] params = new Object[7];
		params[0] = Utility.parseInt(vo.getBook_code(), new Integer(1));
		params[1] = Utility.trimNull(vo.getProduct_code());
		params[2] = Utility.trimNull(vo.getProduct_name());
		params[3] = Utility.parseInt(vo.getDepart_id(), new Integer(0));
		params[4] = Utility.parseInt(vo.getAdmin_manager(), new Integer(0));
		params[5] = Utility.parseInt(vo.getInput_man(), new Integer(888));
		params[6] = Utility.parseInt(vo.getSub_man(), new Integer(0));

		try {

			list = super.listBySql(
					"{call SP_QUERY_TPRODUC_TABLE(?,?,?,?,?,?,?)}", params);
		} catch (Exception e) {
			throw new BusiException("查询产品表格信息失败: " + e.getMessage());
		}
		String json = JSONArray.fromObject(list).toString();
		return json;
	}
	
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#adddeletecity(enfo.crm.vo.ProductVO)
	 */
    @Override
	public Integer adddeletecity(ProductVO vo) throws BusiException {
        try {
            Object[] param = new Object[5];
            Integer city_serial_no = null;
            param[0] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
            param[1] = Utility.trimNull(vo.getCity_name());
            param[2] = Utility.parseInt(vo.getInput_man(), new Integer(0));
            param[3] = Utility.trimNull(vo.getGov_prov_pegional());
            param[4] = Utility.trimNull(vo.getGov_pegional());
            city_serial_no = (Integer)super.cudProc("{?=call SP_ADD_TPRODUCTCITY(?,?,?,?,?,?)}", param, 7, java.sql.Types.INTEGER);
            return city_serial_no;	
        } catch (Exception e) {
            throw new BusiException("产品推荐地新增失败: " + e.getMessage());
        }

    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#modiProductCity(enfo.crm.vo.ProductVO)
	 */
    @Override
	public void modiProductCity(ProductVO vo)throws Exception{
    	Object[] params = new Object[5];
    	params[0] = Utility.parseInt(vo.getCity_serial_no(),new Integer(0));
    	params[1] = Utility.trimNull(vo.getGov_prov_pegional());
    	params[2] = Utility.trimNull(vo.getGov_pegional());
    	params[3] = Utility.parseInt(vo.getInput_man(),new Integer(0));
    	params[4] = Utility.trimNull(vo.getCity_name());
    	super.cud("{?= call SP_MODI_TPRODUCTCITY(?,?,?,?,?)}",params);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#queryDueProduct(enfo.crm.vo.ProductVO)
	 */
    @Override
	public List queryDueProduct(ProductVO vo) throws Exception {
        Object[] params = new Object[3];
        params[0] = vo.getService_man();
        params[1] = vo.getDaysBeforeDue();
        params[2] = vo.getInput_man();        
        return CrmDBManager.listBySql("{call SP_QUERY_DUE_PRODUCT(?,?,?)}", params);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#modiProductInvestment(enfo.crm.vo.ProductVO)
	 */
    @Override
	public void modiProductInvestment(ProductVO vo)throws Exception{
    	Object[] params = new Object[3];
    	params[0] = vo.getProduct_id();
        params[1] = vo.getInvest_field();
        params[2] = vo.getInput_man();
        CrmDBManager.cudProc("{?= call SP_MODI_TPRODUCT_INVESTMENT(?,?,?)}",params);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#modiProductPre(enfo.crm.vo.ProductVO)
	 */
    @Override
	public void modiProductPre(ProductVO vo)throws Exception{
    	String sql="";
    	if(vo.getSub_product_id().intValue()==0){
    		sql="UPDATE TPRODUCT SET PRE_STATUS = " + vo.getPreStatus() + "  WHERE PRODUCT_ID = " + vo.getProduct_id();  		
    	}else{
    		sql="UPDATE TSUBPRODUCT SET PRE_STATUS = " + vo.getPreStatus() + "  WHERE SUB_PRODUCT_ID = " + vo.getSub_product_id();
    		
    	}
    	CrmDBManager.executeSql(sql);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#queryPreproductProv(enfo.crm.vo.ProductVO, int, int)
	 */
	 @Override
	public IPageList queryPreproductProv(ProductVO vo, int pageIndex, int pageSize) throws Exception {
        Object[] params = new Object[5];
        params[0] = vo.getSerial_no();
        params[1] = vo.getPreproduct_id();
        params[2] = vo.getProv_flag();
        params[3] = vo.getProv_level();
        params[4] = vo.getInput_man();
        return CrmDBManager.listProcPage("{call SP_QUERY_TGAINLEVEL(?,?,?,?,?)}", params, pageIndex, pageSize);
    }
	 
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#modiPreproductProv(enfo.crm.vo.ProductVO)
	 */
    @Override
	public void modiPreproductProv(ProductVO vo)throws Exception{
    	Object[] params = new Object[8];
    	params[0] = vo.getSerial_no();
        params[1] = vo.getProv_flag();
        params[2] = vo.getProv_level();
        params[3] = vo.getProv_level_name();
        params[4] = vo.getLower_limit();
        params[5] = vo.getUpper_limit();
        params[6] = vo.getSummary();
        params[7] = vo.getInput_man();
        CrmDBManager.cudProc("{?= call SP_MODI_TGAINLEVEL(?,?,?,?,?,?,?,?)}",params);
    } 
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#modiPreproductProvRate(enfo.crm.vo.ProductVO)
	 */
    @Override
	public void modiPreproductProvRate(ProductVO vo)throws Exception{
    	Object[] params = new Object[5];
    	params[0] = vo.getRate_serial_no();
        params[1] = vo.getExp_rate();
        params[2] = vo.getStart_date();
        params[3] = vo.getEnd_date();
        params[4] = vo.getInput_man();
        CrmDBManager.cudProc("{?= call SP_MODI_TGAINLEVELRATE(?,?,?,?,?)}",params);
    } 
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#addPreproductProv(enfo.crm.vo.ProductVO)
	 */
    @Override
	public void addPreproductProv(ProductVO vo)throws Exception{
    	Object[] params = new Object[9];
    	params[0] = vo.getPreproduct_id();
        params[1] = vo.getProv_flag();
        params[2] = vo.getProv_level();
        params[3] = vo.getProv_level_name();
        params[4] = vo.getLower_limit();
        params[5] = vo.getUpper_limit();
        params[6] = vo.getSummary();
        params[7] = vo.getExp_rate();
        params[8] = vo.getInput_man();
        CrmDBManager.cudProc("{?= call SP_ADD_TGAINLEVEL(?,?,?,?,?,?,?,?,?)}", params);
    } 
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductLocal#delPreproductProv(enfo.crm.vo.ProductVO)
	 */
    @Override
	public void delPreproductProv(ProductVO vo)throws Exception{
    	Object[] params = new Object[2];
    	params[0] = vo.getSerial_no();
        params[1] = vo.getInput_man();
        CrmDBManager.cudProc("{?= call SP_DEL_TGAINLEVEL(?,?)}", params);
    } 
}