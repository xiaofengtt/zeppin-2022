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
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.PreMoneyDetailVO;

@Component(value="preMoneyDetail")
public class PreMoneyDetailBean extends CrmBusiExBean implements PreMoneyDetailLocal {
	/**
	 * 新增预约到账信息
	 */
	private static String addSql = "{?=call SP_ADD_TPREMONEYDETAIL(?,?,?,?,?,?)}";

	/**
	 * 删除预约到账信息
	 */
	private static String delSql = "{?=call SP_DEL_TPREMONEYDETAIL(?,?)}";

	/**
	 * 修改预约到账信息
	 */
	private static String modiSql = "{?=call SP_MODI_TPREMONEYDETAIL(?,?,?,?,?,?)}";

	/**
	 * 查询预约到账信息
	 */
	private static String querySql = "{call SP_QUERY_TPREMONEYDETAIL(?,?,?,?,?,?,?,?,?,?)}";

	/**
	 * 退款预约到账信息
	 */
	private static String refundSql = "{?= call SP_REFUND_PREMONEYDETAIL(?,?,?,?,?)}";

	/**
	 * 查询资金证实书信息
	 */
	private static String queryMoneySql = "{call SP_QUERY_TPREMONEYDETAIL_SERIAL (?,?)}";
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreMoneyDetailLocal#addPreMoneyDetail(enfo.crm.vo.PreMoneyDetailVO)
	 */
	@Override
	public Integer addPreMoneyDetail(PreMoneyDetailVO vo) throws BusiException {
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
//			stmt.setInt(8,vo.getSbf_serial_no().intValue());
//			stmt.setString(9,vo.getJk_account());
//			stmt.setString(10,vo.getJk_bank_id());
//			stmt.setString(11,vo.getJk_bank_branch());
			stmt.executeUpdate();
			int ret = stmt.getInt(1);			
			if (ret < 0) throw new BusiException(ret);
			
			return new Integer(ret);
			
		} catch (Exception e) {
			throw new BusiException("新增预约到账信息失败: " + e.getMessage());
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
	 * @see enfo.crm.marketing.PreMoneyDetailLocal#delPreMoneyDetail(enfo.crm.vo.PreMoneyDetailVO)
	 */
	@Override
	public void delPreMoneyDetail(PreMoneyDetailVO vo) throws BusiException {
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getInput_man(), new Integer(888));

		try {
			super.cudProc(delSql, params);
		} catch (Exception e) {
			throw new BusiException("删除预约到账信息失败: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreMoneyDetailLocal#modiPreMoneyDetail(enfo.crm.vo.PreMoneyDetailVO)
	 */
	@Override
	public void modiPreMoneyDetail(PreMoneyDetailVO vo) throws BusiException {
		Object[] params = new Object[6];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.trimNull(vo.getDz_time());
		params[2] = Utility
				.parseBigDecimal(vo.getDz_money(), new BigDecimal(0));
		params[3] = Utility.trimNull(vo.getJk_type());
		params[4] = Utility.parseInt(vo.getInput_man(), new Integer(888));
		params[5] = Utility.parseInt(vo.getIs_onway(), new Integer(0));
//		params[6] = vo.getJk_account();
//		params[7] = vo.getJk_bank_id();
//		params[8] = vo.getJk_bank_branch();
		try {
			super.cudProc(modiSql, params);
		} catch (Exception e) {
			throw new BusiException("修改预约到账信息失败: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreMoneyDetailLocal#refundPreMoneyDetail(enfo.crm.vo.PreMoneyDetailVO)
	 */
	@Override
	public void refundPreMoneyDetail(PreMoneyDetailVO vo) throws BusiException {
		Object[] params = new Object[5];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getPre_serial_no(), new Integer(0));
		params[2] = Utility.parseBigDecimal(vo.getRefund_money(),
				new BigDecimal(0));
		params[3] = Utility.parseInt(vo.getRefund_date(), new Integer(0));
		params[4] = Utility.parseInt(vo.getInput_man(), new Integer(888));
//		params[5] = vo.getModiquota();
		try {
			super.cudProc(refundSql, params);
		} catch (Exception e) {
			throw new BusiException("退款预约到账信息失败: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreMoneyDetailLocal#queryPreMoneyDetail(enfo.crm.vo.PreMoneyDetailVO)
	 */
	@Override
	public List queryPreMoneyDetail(PreMoneyDetailVO vo) throws BusiException {
		List list = null;
		Object[] params = new Object[10];
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
		try {

			list = super.listBySql(querySql, params);
		} catch (Exception e) {
			throw new BusiException("查询预约到账信息失败: " + e.getMessage());
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreMoneyDetailLocal#queryPreMoneyDetail(enfo.crm.vo.PreMoneyDetailVO, int, int)
	 */
	@Override
	public IPageList queryPreMoneyDetail(PreMoneyDetailVO vo, int pageIndex,
			int pageSize) throws BusiException {
		IPageList rsList = null;
		Object[] params = new Object[10];
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

		try {
			rsList = super.listProcPage(querySql, params, pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("查询预约到账信息失败: " + e.getMessage());
		}
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreMoneyDetailLocal#updateSMS(enfo.crm.vo.PreMoneyDetailVO)
	 */
	@Override
	public void updateSMS(PreMoneyDetailVO vo) throws BusiException {
		StringBuffer sqlStr = new StringBuffer(" UPDATE TPREMONEYDETAIL SET SMS1_COUNT = ISNULL(SMS1_COUNT,0)+");
		if ("".equals(Utility.trimNull(vo.getSms1_customer()))) {
		    sqlStr.append("0");
		} else {
		    sqlStr.append("1");
		}
		if ("".equals(Utility.trimNull(vo.getSms2_serviceman()))) {
		    sqlStr.append(", SMS2_COUNT = ISNULL(SMS2_COUNT,0)+0"); 
		} else {
		    sqlStr.append(", SMS2_COUNT = ISNULL(SMS2_COUNT,0)+1");
		}
		sqlStr.append(", SMS1_CUSTOMER = '"+Utility.trimNull(vo.getSms1_customer())+"'");
		sqlStr.append(", SMS2_SERVICEMAN = '"+Utility.trimNull(vo.getSms2_serviceman())+"'");
		sqlStr.append(" WHERE SERIAL_NO = "+ vo.getSerial_no());
				try {
			super.executeSql(sqlStr.toString());
		} catch (BusiException e) {
			throw new BusiException("修改到账短信信息失败:" + e.getMessage());
		}
	}
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreMoneyDetailLocal#relateContract(java.lang.Integer, java.lang.Integer)
	 */
    @Override
	public void relateContract(Integer serial_no,Integer contract_serial_no) throws Exception {
        try {
            super.executeSql("UPDATE TPREMONEYDETAIL SET CONTRACT_SERIAl_NO = "+ contract_serial_no +" WHERE SERIAL_NO = " + serial_no);
        } catch (BusiException e) {
            throw new BusiException("删除缴款失败:" + e.getMessage());
        }
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.marketing.PreMoneyDetailLocal#queryMoneyConfirmation(enfo.crm.vo.PreMoneyDetailVO)
	 */
	@Override
	public List queryMoneyConfirmation(PreMoneyDetailVO vo) throws BusiException {
		List list = null;
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		
		try {
			list = super.listBySql(queryMoneySql, params);
		} catch (Exception e) {
			throw new BusiException("查询资金证实书信息失败: " + e.getMessage());
		}
		return list;
	} 
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreMoneyDetailLocal#queryMoneyDataSerial(enfo.crm.vo.PreMoneyDetailVO)
	 */
	@Override
	public List queryMoneyDataSerial(PreMoneyDetailVO vo) throws BusiException {
		List list = null;
		String proc="{call SP_QUERY_TMONEYDATA_SERIAL (?,?)}";
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		
		try {
			list = super.listBySql(proc, params);
		} catch (Exception e) {
			throw new BusiException("查询资金证实书信息失败: " + e.getMessage());
		}
		return list;
	}
}