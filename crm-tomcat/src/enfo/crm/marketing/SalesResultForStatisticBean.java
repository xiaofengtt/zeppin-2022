package enfo.crm.marketing;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmBusiExBean;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.tools.Utility;
import enfo.crm.vo.SalesResultForStatisticVO;

@Component(value="salesResultForStatistic")
public class SalesResultForStatisticBean extends CrmBusiExBean implements SalesResultForStatisticLocal {

	/**
	 * 查询预约到账信息
	 */
	private static String querySql = "{call SP_QUERY_TSALESRESULTFORSTATISTIC(?,?,?,?,?,?,?,?,?,?,?)}";
	
	/**
	 * 查询预约到账信息
	 */
	private static String querySql_q = "{call SP_QUERY_TSALESRESULTFORSTATISTIC_Q(?,?,?,?,?,?,?,?,?,?,?)}";

	/**
	 * 查询预约到账信息
	 */
//	private static String querySqlMore = "{call SP_QUERY_TSALESRESULTFORSTATISTIC_Q(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static String querySqlMore = "{call SP_QUERY_TSALESRESULTFORSTATISTIC(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

	/**
	 * 新增
	 */
	private static String addSql = "{?=call SP_ADD_TSALESRESULTFORSTATISTIC(?,?,?,?,?,?,?,?,?,?,?,?)}";

	/**
	 * 修改
	 */
	private static String modiSql = "{?=call SP_MODI_TSALESRESULTFORSTATISTIC(?,?,?,?,?,?,?,?,?,?,?,?)}";

	/**
	 * 撤销
	 */
	private static String cancelSql = "{?=call SP_CANCEL_TSALESRESULTFORSTATISTIC(?,?,?)}";
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SalesResultForStatisticLocal#querySalesResultForStatistic(enfo.crm.vo.SalesResultForStatisticVO)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List querySalesResultForStatistic(SalesResultForStatisticVO vo) throws BusiException {
		
		List list = null;
		Object[] params = new Object[11];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getPre_serial_no(), new Integer(0));
		params[2] = Utility.parseInt(vo.getPre_product_id(), new Integer(0));
		params[3] = Utility.parseInt(vo.getStart_date(), new Integer(0));
		params[4] = Utility.parseInt(vo.getEnd_date(), new Integer(0));
		params[5] = Utility.trimNull(vo.getPre_status());
		params[6] = Utility.trimNull(vo.getCust_name());
		params[7] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		params[8] = Utility.trimNull(vo.getPre_level());
		params[9] = Utility.trimNull(vo.getTeam_id());
		params[10] = Utility.parseInt(vo.getPre_product_type(), new Integer(0));
		try {

			list = super.listBySql(querySql, params);
		} catch (Exception e) {
			throw new BusiException("查询预约到账信息失败: " + e.getMessage());
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SalesResultForStatisticLocal#querySalesResultForStatistic_q(enfo.crm.vo.SalesResultForStatisticVO)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List querySalesResultForStatistic_q(SalesResultForStatisticVO vo) throws BusiException {
		
		List list = null;
		Object[] params = new Object[11];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getPre_serial_no(), new Integer(0));
		params[2] = Utility.parseInt(vo.getPre_product_id(), new Integer(0));
		params[3] = Utility.parseInt(vo.getStart_date(), new Integer(0));
		params[4] = Utility.parseInt(vo.getEnd_date(), new Integer(0));
		params[5] = Utility.trimNull(vo.getPre_status());
		params[6] = Utility.trimNull(vo.getCust_name());
		params[7] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		params[8] = Utility.trimNull(vo.getPre_level());
		params[9] = Utility.trimNull(vo.getTeam_id());
		params[10] = Utility.parseInt(vo.getPre_product_type(), new Integer(0));
		try {

			list = super.listBySql(querySql_q, params);
		} catch (Exception e) {
			throw new BusiException("查询预约到账信息失败: " + e.getMessage());
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SalesResultForStatisticLocal#querySalesResultForStatisticMore(enfo.crm.vo.SalesResultForStatisticVO)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List querySalesResultForStatisticMore(SalesResultForStatisticVO vo) throws BusiException {
		
		List list = null;
		Object[] params = new Object[14];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getPre_serial_no(), new Integer(0));
		params[2] = Utility.parseInt(vo.getPre_product_id(), new Integer(0));
		params[3] = Utility.parseInt(vo.getStart_date(), new Integer(0));
		params[4] = Utility.parseInt(vo.getEnd_date(), new Integer(0));
		params[5] = Utility.trimNull(vo.getPre_status());
		params[6] = Utility.trimNull(vo.getCust_name());
		params[7] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		params[8] = Utility.trimNull(vo.getPre_level());
		params[9] = Utility.trimNull(vo.getTeam_id());
		params[10] = Utility.parseInt(vo.getPre_product_type(), new Integer(0));
		params[11] = Utility.trimNull(vo.getFk_tpremoneydetail());
		params[12] = null;
		params[13] = Utility.parseInt(vo.getStatus(), null);
		try {

			list = super.listBySql(querySqlMore, params);
		} catch (Exception e) {
			throw new BusiException("查询预约到账信息失败: " + e.getMessage());
		}
		return list;
	}

	/* (non-Javadoc) 
	 * @see enfo.crm.marketing.SalesResultForStatisticLocal#addSalesResultForStatistic(enfo.crm.vo.SalesResultForStatisticVO)
	 */
	@Override
	public Integer addSalesResultForStatistic(SalesResultForStatisticVO vo) throws BusiException {
		Connection conn = null;
		CallableStatement stmt = null;
		try { 
			conn = CrmDBManager.getConnection();
			stmt = conn.prepareCall(
					        addSql,
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			stmt.registerOutParameter(1, java.sql.Types.INTEGER);
			stmt.setInt(2, vo.getPre_serial_no().intValue());
			stmt.setString(3, vo.getDz_time());
			stmt.setBigDecimal(4, vo.getDz_money());
			stmt.setString(5, vo.getJk_type());
			stmt.setInt(6, vo.getInput_man().intValue());
			stmt.setInt(7, vo.getIs_onway().intValue());
			stmt.setString(8, vo.getRg_product_name());
			stmt.setInt(9, vo.getRg_cust_type());
			stmt.setString(10, vo.getCust_name());
			stmt.setInt(11, vo.getPre_product_type());
			stmt.setString(12, vo.getPre_product_type_name());
			stmt.setInt(13, vo.getRg_service_man());
			stmt.executeUpdate();
			int ret = stmt.getInt(1);			
			if (ret < 0) throw new BusiException(ret);
			
			return new Integer(ret);
			
		} catch (Exception e) {
			throw new BusiException("新增到帐结果信息失败: " + e.getMessage());
		} finally {
			try {
				if (stmt!=null) stmt.close();
			} catch (Exception e) {
				
			}
			
			try {
				if (conn!=null) conn.close();
			} catch (Exception e) {
				
			}			
		}		
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SalesResultForStatisticLocal#modiSalesResultForStatistic(enfo.crm.vo.SalesResultForStatisticVO)
	 */
	@Override
	public void modiSalesResultForStatistic(SalesResultForStatisticVO vo) throws BusiException {
		Object[] params = new Object[12];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.trimNull(vo.getDz_time());
		params[2] = Utility
				.parseBigDecimal(vo.getDz_money(), new BigDecimal(0));
		params[3] = Utility.trimNull(vo.getJk_type());
		params[4] = Utility.parseInt(vo.getInput_man(), new Integer(888));
		params[5] = Utility.parseInt(vo.getIs_onway(), new Integer(0));
		params[6] = Utility.trimNull(vo.getRg_product_name());
		params[7] = Utility.parseInt(vo.getRg_cust_type(), new Integer(0));
		params[8] = Utility.trimNull(vo.getCust_name());
		params[9] = Utility.parseInt(vo.getPre_product_type(), new Integer(0));
		params[10] = Utility.trimNull(vo.getPre_product_type_name());
		params[11] = Utility.parseInt(Utility.trimNull(vo.getRg_service_man()), new Integer(0));
		try {
			super.cudProc(modiSql, params);
		} catch (Exception e) {
			throw new BusiException("修改到帐结果信息失败: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SalesResultForStatisticLocal#cancelSalesResultForStatistic(enfo.crm.vo.SalesResultForStatisticVO)
	 */
	@Override
	public void cancelSalesResultForStatistic(SalesResultForStatisticVO vo) throws BusiException {
		// TODO Auto-generated method stub
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getStatus(), new Integer(-2));
		params[2] = vo.getInput_man().intValue();
		try {
			super.cudProcNoRet(cancelSql, params);
		} catch (Exception e) {
			throw new BusiException("撤销到帐结果信息失败: " + e.getMessage());
		}
	}
}