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
import enfo.crm.vo.SalesChangesVO;

@Component(value="salesChanges")
public class SalesChangesBean extends CrmBusiExBean implements SalesChangesLocal {
	/**
	 * 新增
	 */
	private static String addSql = "{?=call SP_ADD_TSALESCHANGES(?,?,?,?,?,?)}";

	/**
	 * 修改
	 */
	private static String modiSql = "{?=call SP_MODI_TSALESCHANGES(?,?,?,?,?)}";

	/**
	 * 查询
	 */
	private static String querySql = "{call SP_QUERY_TSALESCHANGES(?,?,?,?,?,?,?,?,?,?)}";

	/**
	 * 撤销
	 */
	private static String cancelSql = "{?=call SP_CANCEL_TSALESCHANGES(?,?,?,?)}";

	/**
	 * 确认
	 */
	private static String checkSql = "{?=call SP_CHECK_TSALESCHANGES(?,?,?,?)}";
	
	/**
	 * 审核
	 */
	private static String recheckSql = "{?=call SP_RE_CHECK_TSALESCHANGES(?,?,?,?)}";
	/* (non-Javadoc) 
	 * @see enfo.crm.marketing.SalesChangesLocal#addSalesChanges(enfo.crm.vo.SalesChangesVO)
	 */
	@Override
	public Integer addSalesChanges(SalesChangesVO vo) throws BusiException {
		Connection conn = null;
		CallableStatement stmt = null;
		try { 
			conn = CrmDBManager.getConnection();
			stmt = conn.prepareCall(
					        addSql,
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			stmt.registerOutParameter(1, java.sql.Types.INTEGER);
			stmt.setInt(2, vo.getSerial_no());
			stmt.setInt(3, vo.getInput_man().intValue());
			stmt.setInt(4, vo.getFrom_service_man());
			stmt.setInt(5, vo.getTo_service_man());
			stmt.setBigDecimal(6, vo.getZr_money());
			stmt.setString(7, vo.getChange_reason());
			stmt.executeUpdate();
			int ret = stmt.getInt(1);			
			if (ret < 0) throw new BusiException(ret);
			
			return new Integer(ret);
			
		} catch (Exception e) {
			throw new BusiException("新增转销量信息失败: " + e.getMessage());
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
	 * @see enfo.crm.marketing.SalesChangesLocal#modiSalesChanges(enfo.crm.vo.SalesChangesVO)
	 */
	@Override
	public void modiSalesChanges(SalesChangesVO vo) throws BusiException {
		Object[] params = new Object[5];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		params[2] = Utility.parseInt(vo.getTo_service_man(), new Integer(0));
		params[3] = Utility
				.parseBigDecimal(vo.getZr_money(), new BigDecimal(0));
		params[4] = Utility.trimNull(vo.getChange_reason());
		try {
			super.cudProc(modiSql, params);
		} catch (Exception e) {
			throw new BusiException("修改转销量申请信息失败: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SalesChangesLocal#cancelSalesChanges(enfo.crm.vo.SalesChangesVO)
	 */
	@Override
	public void cancelSalesChanges(SalesChangesVO vo) throws BusiException {
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getStatus(), new Integer(-2));
		params[2] = vo.getInput_man().intValue();
		params[3] = Utility.trimNull(vo.getCheck_reason());
		try {
			super.cudProcNoRet(cancelSql, params);
		} catch (Exception e) {
			throw new BusiException("确认转销量申请信息失败: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SalesChangesLocal#checkSalesChanges(enfo.crm.vo.SalesChangesVO)
	 */
	@Override
	public void checkSalesChanges(SalesChangesVO vo) throws BusiException {
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getStatus(), new Integer(-2));
		params[2] = vo.getInput_man().intValue();
		params[3] = Utility.trimNull(vo.getCheck_reason());
		try {
			super.cudProcNoRet(checkSql, params);
		} catch (Exception e) {
			throw new BusiException("确认转销量申请信息失败: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SalesChangesLocal#recheckSalesChanges(enfo.crm.vo.SalesChangesVO)
	 */
	@Override
	public void recheckSalesChanges(SalesChangesVO vo) throws BusiException {
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getStatus(), new Integer(-2));
		params[2] = vo.getInput_man().intValue();
		params[3] = Utility.trimNull(vo.getRe_check_reason());
		
		try {
			super.cudProcNoRet(recheckSql, params);
		} catch (Exception e) {
			throw new BusiException("审核转销量申请信息失败: " + e.getMessage());
		}

	}


	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SalesChangesLocal#querySalesChanges(enfo.crm.vo.SalesChangesVO)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List querySalesChanges(SalesChangesVO vo) throws BusiException {
		List list = null;
		Object[] params = new Object[10];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getPre_serial_no(), new Integer(0));
		params[2] = Utility.parseInt(vo.getPre_product_id(), new Integer(0));
		params[3] = Utility.trimNull(vo.getCust_name());
		params[4] = Utility.parseInt(vo.getInput_man(), new Integer(0));
//		params[5] = null;
		params[6] = Utility.trimNull(vo.getTeam_id());
		params[7] = Utility.parseInt(vo.getFrom_service_man(), new Integer(0));
		params[8] = Utility.parseInt(vo.getTo_service_man(), new Integer(0));
		params[9] = Utility.parseInt(vo.getStatus(), new Integer(0));
		try {
			list = super.listBySql(querySql, params);
		} catch (Exception e) {
			throw new BusiException("查询转销量申请信息失败: " + e.getMessage());
		}
		return list;
	}
    
}