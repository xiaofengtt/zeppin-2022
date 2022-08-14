/*
 * 创建日期 2011-12-25
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package enfo.crm.marketing;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmBusiExBean;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.PreContractCrmVO;

@Component(value="preContractCrm")
public class PreContractCrmBean extends CrmBusiExBean implements PreContractCrmLocal {
	private static final String SP_ADD_TPRECONTRACT = "{?=call SP_ADD_TPRECONTRACT (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

	private static final String SP_DEL_TPRECONTRACT = "{?=call SP_DEL_TPRECONTRACT (?,?,?)}";

	private static final String SP_MODI_TPRECONTRACT = "{?=call SP_MODI_TPRECONTRACT (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

	private static final String SP_QUERY_TPRECONTRACT = "{call SP_QUERY_TPRECONTRACT (?,?,?,?,?,?,?,?,?,?,?,?,?)}";

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreContractCrmLocal#queryPreContractCrm(enfo.crm.vo.PreContractCrmVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList queryPreContractCrm(PreContractCrmVO vo,
			String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException {
		Object[] params = new Object[15];
		String sqlStr = "{call SP_QUERY_TPRECONTRACT (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getPre_product_id(), new Integer(0));
		params[2] = Utility.trimNull(vo.getProduct_name());
		params[3] = Utility.trimNull(vo.getCust_name());
		params[4] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		params[5] = Utility.parseInt(vo.getTeam_id(), new Integer(0));
		params[6] = Utility.parseInt(vo.getDate1(), new Integer(0));
		params[7] = Utility.parseInt(vo.getDate2(), new Integer(0));
		params[8] = Utility.parseBigDecimal(vo.getMoney1(), new BigDecimal(0));
		params[9] = Utility.parseBigDecimal(vo.getMoney2(), new BigDecimal(0));
		params[10] = Utility.trimNull(vo.getPre_status());
		params[11] = Utility.trimNull(vo.getChannel_type());
		params[12] = Utility.parseBigDecimal(vo.getChannel_money1(), new BigDecimal(0));
		params[13] = Utility.parseBigDecimal(vo.getChannel_money2(), new BigDecimal(0));
		params[14] = Utility.trimNull(vo.getPre_level());

		return CrmDBManager.listProcPage(sqlStr, params, totalColumn,
				pageIndex, pageSize);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreContractCrmLocal#queryPreContractCrm(enfo.crm.vo.PreContractCrmVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList queryPreContractCrm_m(PreContractCrmVO vo,
			String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException {
		Object[] params = new Object[17];
		String sqlStr = "{call SP_QUERY_TPRECONTRACT_M (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getPre_product_id(), new Integer(0));
		params[2] = Utility.trimNull(vo.getProduct_name());
		params[3] = Utility.trimNull(vo.getCust_name());
		params[4] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		params[5] = Utility.parseInt(vo.getTeam_id(), new Integer(0));
		params[6] = Utility.parseInt(vo.getDate1(), new Integer(0));
		params[7] = Utility.parseInt(vo.getDate2(), new Integer(0));
		params[8] = Utility.parseBigDecimal(vo.getMoney1(), new BigDecimal(0));
		params[9] = Utility.parseBigDecimal(vo.getMoney2(), new BigDecimal(0));
		params[10] = Utility.trimNull(vo.getPre_status());
		params[11] = Utility.trimNull(vo.getChannel_type());
		params[12] = Utility.parseBigDecimal(vo.getChannel_money1(), new BigDecimal(0));
		params[13] = Utility.parseBigDecimal(vo.getChannel_money2(), new BigDecimal(0));
		params[14] = Utility.trimNull(vo.getPre_level());
		params[15] = vo.getSl_status();
		params[16] = vo.getSl_type(); 

		return CrmDBManager.listProcPage(sqlStr, params, totalColumn,
				pageIndex, pageSize);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreContractCrmLocal#queryMoneyData(enfo.crm.vo.PreContractCrmVO)
	 */
	@Override
	public List queryMoneyData(PreContractCrmVO vo)
			throws BusiException {
		Object[] params = new Object[2];
		String sqlStr = "{call SP_QUERY_TMONEYDATA_PRECONTRACT(?,?)}";
		params[0] = Utility.parseInt(vo.getPre_serial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		return CrmDBManager.listBySql(sqlStr, params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreContractCrmLocal#listPreContractCrm(enfo.crm.vo.PreContractCrmVO)
	 */
	@Override
	public List listPreContractCrm(PreContractCrmVO vo) throws BusiException {
		Object[] params = new Object[15];
		String sqlStr = "{call SP_QUERY_TPRECONTRACT (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getPre_product_id(), new Integer(0));
		params[2] = Utility.trimNull(vo.getProduct_name());
		params[3] = Utility.trimNull(vo.getCust_name());
		params[4] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		params[5] = Utility.parseInt(vo.getTeam_id(), new Integer(0));
		params[6] = Utility.parseInt(vo.getDate1(), new Integer(0));
		params[7] = Utility.parseInt(vo.getDate2(), new Integer(0));
		params[8] = Utility.parseBigDecimal(vo.getMoney1(), new BigDecimal(0));
		params[9] = Utility.parseBigDecimal(vo.getMoney2(), new BigDecimal(0));
		params[10] = Utility.trimNull(vo.getPre_status());
		params[11] = Utility.trimNull(vo.getChannel_type());
		params[12] = Utility.parseBigDecimal(vo.getChannel_money1(), new BigDecimal(0));
		params[13] = Utility.parseBigDecimal(vo.getChannel_money2(), new BigDecimal(0));
		params[14] = Utility.trimNull(vo.getPre_level());

		return CrmDBManager.listBySql(sqlStr, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreContractCrmLocal#append(enfo.crm.vo.PreContractCrmVO)
	 */

	@Override
	public Object[] append(PreContractCrmVO vo) throws BusiException {
		Object[] rsList = new Object[2];
		int ret;
		try {
			Connection conn = CrmDBManager.getConnection();
			CallableStatement stmt = conn
					.prepareCall(
							"{?=call SP_ADD_TPRECONTRACT (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			// Utility.debug(
			// vo.getProduct_id()+"---"+vo.getCust_id()+"---"+vo.getLink_man()+"---"+vo.getValid_days()+"---"+vo.getPre_num());
			stmt.registerOutParameter(1, java.sql.Types.INTEGER);
			stmt.setInt(2, vo.getPre_product_id().intValue());
			stmt.setInt(3, vo.getCust_id().intValue());
			stmt.setBigDecimal(4, vo.getPre_money());
			stmt.setInt(5, vo.getLink_man().intValue());
			stmt.setInt(6, vo.getValid_days().intValue());
			stmt.setString(7, vo.getPre_type());
			stmt.setString(8, vo.getSummary());
			stmt.setInt(9, vo.getPre_num().intValue());
			stmt.setInt(10, vo.getInput_man().intValue());
			stmt.setInt(11, vo.getPre_date().intValue());
			stmt.setInt(12, Utility.parseInt(vo.getExp_reg_date(), 0)
					.intValue());
			stmt.setString(13, vo.getCust_source());
			stmt.registerOutParameter(14, java.sql.Types.VARCHAR);
			stmt.registerOutParameter(15, java.sql.Types.INTEGER);
			stmt.setString(16, vo.getChannel_type());
			stmt.setBigDecimal(17, vo.getChannel_fare());
			stmt.setString(18, vo.getPre_level());
			stmt.setInt(19, Utility.parseInt(vo.getProduct_id(), new Integer(0)).intValue());
			stmt.setInt(20, Utility.parseInt(vo.getSub_product_id(), new Integer(0)).intValue());
			stmt.setInt(21, Utility.parseInt(vo.getTeam_id(), new Integer(0)).intValue());
			stmt.executeUpdate();
			ret = stmt.getInt(1);
			//rsList[0] = stmt.getString(14);
			//rsList[1] = new Integer(stmt.getInt(15));
			stmt.close();
			conn.close();
			if (ret != 100) {
				throw new BusiException(ret);
			}
		} catch (Exception e) {
			throw new BusiException("预约失败: " + e.getMessage());
		}
		//Object[] rsList =
		// super.cudProc(SP_ADD_TPRECONTRACT,params,outParamPos,outParamType);

		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreContractCrmLocal#save(enfo.crm.vo.PreContractCrmVO)
	 */
	@Override
	public void save(PreContractCrmVO vo) throws BusiException {
		Object[] params = new Object[16];

		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseBigDecimal(vo.getPre_money(),
				new BigDecimal(0));
		params[2] = Utility.parseInt(vo.getLink_man(), new Integer(0));
		params[3] = Utility.parseInt(vo.getValid_days(), new Integer(0));
		params[4] = vo.getPre_type();
		params[5] = vo.getSummary();
		params[6] = Utility.parseInt(vo.getPre_num(), new Integer(0));
		params[7] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		params[8] = Utility.parseInt(vo.getPre_date(), new Integer(0));
		params[9] = vo.getExp_reg_date();
		params[10] = vo.getCust_source();
		params[11] = Utility.trimNull(vo.getPer_code());
		params[12] = Utility.parseBigDecimal(vo.getPay_money(), new BigDecimal(
				0));
		params[13] = Utility.trimNull(vo.getChannel_type());
		params[14] = Utility.parseBigDecimal(vo.getChannel_fare(),
				new BigDecimal(0));
		params[15] = Utility.trimNull(vo.getPre_level());
		super.cudProc(SP_MODI_TPRECONTRACT, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreContractCrmLocal#delete(enfo.crm.vo.PreContractCrmVO)
	 */
	@Override
	public void delete(PreContractCrmVO vo) throws BusiException {
		try {
			Object[] params = new Object[3];
			params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
			params[1] = Utility.parseInt(vo.getDel_flag(), new Integer(0));
			params[2] = Utility.parseInt(vo.getInput_man(), new Integer(0));
			super.cudProc(SP_DEL_TPRECONTRACT, params);

		} catch (Exception e) {
			throw new BusiException("预约失败: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreContractCrmLocal#getProductPreList(enfo.crm.vo.PreContractCrmVO)
	 */
	@Override
	public List getProductPreList(PreContractCrmVO vo) throws BusiException {
		String sql = "{call SP_STAT_TPRECONTRACT(?,?,?,?)}";
		Object[] param = new Object[4];
		param[0] = Utility.parseInt(vo.getPre_product_id(), new Integer(0));
		param[1] = Utility.trimNull(vo.getProduct_name());
		param[2] = Utility.trimNull(vo.getStatus());
		param[3] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		return CrmDBManager.listBySql(sql, param);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreContractCrmLocal#getProductPreList(enfo.crm.vo.PreContractCrmVO, int, int)
	 */
	@Override
	public IPageList getProductPreList(PreContractCrmVO vo, int pageIndex,
			int pageSize) throws BusiException {
		String sqlStr = "{call SP_STAT_TPRECONTRACT(?,?,?,?)}";
		Object[] param = new Object[4];
		param[0] = Utility.parseInt(vo.getPre_product_id(), new Integer(0));
		param[1] = Utility.trimNull(vo.getProduct_name());
		param[2] = Utility.trimNull(vo.getStatus());
		param[3] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		return CrmDBManager.listProcPage(sqlStr, param, pageIndex, pageSize);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreContractCrmLocal#getProductPreInfo(enfo.crm.vo.PreContractCrmVO)
	 */
	@Override
	public List getProductPreInfo(PreContractCrmVO vo) throws BusiException {
		Object[] params = new Object[1];
		params[0] = Utility.parseInt(vo.getPre_product_id(), new Integer(0));
		return CrmDBManager.listBySql("{call SP_QUERY_TPRECONTRACT_TOTAL(?)}", params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreContractCrmLocal#getProductPreInfoByproid(enfo.crm.vo.PreContractCrmVO)
	 */
	@Override
	public List getProductPreInfoByproid(PreContractCrmVO vo) throws BusiException {
		Object[] params = new Object[1];
		params[0] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		return CrmDBManager.listBySql("{call SP_QUERY_TPRECONTRACTBYPROID_TOTAL(?)}", params);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreContractCrmLocal#getProductPreTotal(enfo.crm.vo.PreContractCrmVO)
	 */
	@Override
	public List getProductPreTotal(PreContractCrmVO vo) throws BusiException {
		String sql = "{call SP_QUERY_TEAMPRE(?,?,?,?)}";
		Object[] param = new Object[4];
		param[0] = Utility.parseInt(vo.getTeam_id(), new Integer(0));
		param[1] = Utility.trimNull(vo.getManager_name());
		param[2] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		param[3] = Utility.parseInt(vo.getQuery_date(), new Integer(0));
		return CrmDBManager.listBySql(sql, param);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreContractCrmLocal#getPreMoneyDetialList(enfo.crm.vo.PreContractCrmVO)
	 */
	@Override
	public List getPreMoneyDetialList(PreContractCrmVO vo) throws BusiException {
		String sql = "{call SP_QUERY_TPREMONEYDETAIL(?,?,?,?)}";
		Object[] param = new Object[4];
		param[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		param[1] = Utility.parseInt(vo.getPre_serial_no(), new Integer(0));
		param[2] = Utility.parseInt(vo.getPre_product_id(), new Integer(0));
		param[3] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		return CrmDBManager.listBySql(sql, param);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreContractCrmLocal#addPreMoneyDetial(enfo.crm.vo.PreContractCrmVO)
	 */
	@Override
	public void addPreMoneyDetial(PreContractCrmVO vo) throws BusiException {
		String sql = "{call SP_ADD_TPREMONEYDETAIL(?,?,?,?,?)}";
		Object[] params = new Object[5];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getDz_date(), new Integer(0));
		params[2] = Utility
				.parseBigDecimal(vo.getDz_money(), new BigDecimal(0));
		params[3] = Utility.trimNull(vo.getJk_type());
		params[4] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		super.cudProc(sql, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreContractCrmLocal#modiPreMoneyDetial(enfo.crm.vo.PreContractCrmVO)
	 */
	@Override
	public void modiPreMoneyDetial(PreContractCrmVO vo) throws BusiException {
		String sql = "{call SP_MODI_TPREMONEYDETAIL(?,?,?,?,?)}";
		Object[] params = new Object[5];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getDz_date(), new Integer(0));
		params[2] = Utility
				.parseBigDecimal(vo.getDz_money(), new BigDecimal(0));
		params[3] = Utility.trimNull(vo.getJk_type());
		params[4] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		super.cudProc(sql, params);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreContractCrmLocal#modiPreContractHT(java.math.BigDecimal, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void modiPreContractHT(BigDecimal ht_money,Integer cust_id,Integer product_id,Integer sub_product_id,Integer inputman,Integer crmPreNo) throws BusiException {
		String sql = "{?=call SP_MODI_TPRECONTRACT_4HTMONEY(?,?,?,?,?,?,?)}";
		Object[] params = new Object[7];
		params[0] = new Integer(0);
		params[1] = ht_money;
		params[2] = cust_id;
		params[3] = product_id;
		params[4] = sub_product_id;
		params[5] = crmPreNo;
		params[6] = inputman;
		super.cudProc(sql, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreContractCrmLocal#refundInfo(enfo.crm.vo.PreContractCrmVO)
	 */
	@Override
	public void refundInfo(PreContractCrmVO vo) throws BusiException {
		String sql = "{call SP_REFUND_PREMONEYDETAIL(?,?,?,?,?)}";
		Object[] params = new Object[5];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getPre_serial_no(), new Integer(0));
		params[2] = Utility.parseBigDecimal(vo.getRefund_money(),
				new BigDecimal(0));
		params[3] = Utility.parseInt(vo.getRefund_date(), new Integer(0));
		params[4] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		super.cudProc(sql, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreContractCrmLocal#deletePreMoneyDetial(enfo.crm.vo.PreContractCrmVO)
	 */
	@Override
	public void deletePreMoneyDetial(PreContractCrmVO vo) throws BusiException {
		String sql = "{call SP_DEL_TPREMONEYDETAIL(?,?)}";
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		super.cudProc(sql, params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreContractCrmLocal#syncPreContract(enfo.crm.vo.PreContractCrmVO)
	 */
	@Override
	public Integer syncPreContract(PreContractCrmVO vo) throws BusiException{
		Integer bind_serial_no = new Integer(0);
		String sqlStr = "{?=call SP_SYNC_TPRECONTRACT(?,?,?)}";//同步预约信息	
	
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getSerial_no(),new Integer(0));
		params[1] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		
		bind_serial_no = (Integer) super.cudProc(sqlStr,params,4,Types.INTEGER);
		return bind_serial_no;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreContractCrmLocal#setContractNo(java.lang.Integer, java.lang.String)
	 */
    @Override
	public void setContractNo(Integer serial_no,String contract_no)throws BusiException{
    	List list = super.listBySql("SELECT CONTRACT_NO FROM TPRECONTRACT WHERE SERIAL_NO = " +serial_no );
    	Map map_single = (Map)list.get(0);
    	String contractNo = Utility.trimNull(map_single.get("CONTRACT_NO"));
    	contract_no = contractNo + contract_no;
    	super.executeSql("UPDATE TPRECONTRACT SET CONTRACT_NO = '" + contract_no + "' WHERE SERIAL_NO = " + serial_no);
    }
}

