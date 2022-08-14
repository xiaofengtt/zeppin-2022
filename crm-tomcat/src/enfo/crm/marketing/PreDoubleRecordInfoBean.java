package enfo.crm.marketing;

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
import enfo.crm.vo.PreDoubleRecordInfoVO;

@Component(value="preDoubleRecordInfo")
public class PreDoubleRecordInfoBean extends CrmBusiExBean implements PreDoubleRecordInfoLocal {
	/**
	 * 新增双录录入信息
	 */
	private static String addSql = "{?=call SP_ADD_TPREDOUBLERECORDINFO(?,?,?,?,?)}";

	/**
	 * 修改双录录入信息
	 */
	private static String modiSql = "{?=call SP_MODI_TPREDOUBLERECORDINFO(?,?,?,?,?)}";

	/**
	 * 查询双录录入信息
	 */
	private static String querySql = "{call SP_QUERY_TPREDOUBLERECORDINFO(?,?,?,?,?)}";

	/**
	 * 审核
	 */
	private static String checkSql = "{?=call SP_CHECK_TPREDOUBLERECORDINFO(?,?,?)}";
	
	/**
	 * 撤销审核
	 */
	private static String uncheckSql = "{?=call SP_UNCHECK_TPREDOUBLERECORDINFO(?,?,?)}";
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreDoubleRecordInfoLocal#addPreDoubleRecordInfo(enfo.crm.vo.PreDoubleRecordInfoVO)
	 */
	@Override
	public Integer addPreDoubleRecordInfo(PreDoubleRecordInfoVO vo) throws BusiException {
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
			stmt.setString(3, vo.getSl_time());
			stmt.setString(4, vo.getSl_type());
			stmt.setString(5, vo.getSl_type_name());
			stmt.setInt(6, vo.getInput_man().intValue());
			stmt.executeUpdate();
			int ret = stmt.getInt(1);			
			if (ret < 0) throw new BusiException(ret);
			
			return new Integer(ret);
			
		} catch (Exception e) {
			throw new BusiException("新增双录信息失败: " + e.getMessage());
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
	 * @see enfo.crm.marketing.PreDoubleRecordInfoLocal#modiPreDoubleRecordInfo(enfo.crm.vo.PreDoubleRecordInfoVO)
	 */
	@Override
	public void modiPreDoubleRecordInfo(PreDoubleRecordInfoVO vo) throws BusiException {
		Object[] params = new Object[5];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.trimNull(vo.getSl_time());
		params[2] = Utility.trimNull(vo.getSl_type());
		params[3] = Utility.trimNull(vo.getSl_type_name());
		params[4] = vo.getInput_man().intValue();
		try {
			super.cudProc(modiSql, params);
		} catch (Exception e) {
			throw new BusiException("修改双录录入信息失败: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreDoubleRecordInfoLocal#checkPreDoubleRecordInfo(enfo.crm.vo.PreDoubleRecordInfoVO)
	 */
	@Override
	public void checkPreDoubleRecordInfo(PreDoubleRecordInfoVO vo) throws BusiException {
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getStatus(), new Integer(-2));
//		params[2] = Utility.trimNull(vo.getSl_time());
//		params[3] = Utility.trimNull(vo.getSl_type());
//		params[4] = Utility.trimNull(vo.getSl_type_name());
		params[2] = vo.getInput_man().intValue();
		try {
			super.cudProcNoRet(checkSql, params);
		} catch (Exception e) {
			throw new BusiException("审核双录录入信息失败: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreDoubleRecordInfoLocal#uncheckPreDoubleRecordInfo(enfo.crm.vo.PreDoubleRecordInfoVO)
	 */
	@Override
	public void uncheckPreDoubleRecordInfo(PreDoubleRecordInfoVO vo) throws BusiException {
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getStatus(), new Integer(-2));
		params[2] = vo.getInput_man().intValue();
		try {
			super.cudProcNoRet(uncheckSql, params);
		} catch (Exception e) {
			throw new BusiException("审核双录录入信息失败: " + e.getMessage());
		}
	}


	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreDoubleRecordInfoLocal#queryPreDoubleRecordInfo(enfo.crm.vo.PreDoubleRecordInfoVO)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List queryPreDoubleRecordInfo(PreDoubleRecordInfoVO vo) throws BusiException {
		List list = null;
		Object[] params = new Object[5];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getPre_serial_no(), new Integer(0));
		params[2] = vo.getSl_time();
		params[3] = vo.getStatus();
		params[4] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		try {
			list = super.listBySql(querySql, params);
		} catch (Exception e) {
			throw new BusiException("查询双录录入信息失败: " + e.getMessage());
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.PreDoubleRecordInfoLocal#queryPreDoubleRecordInfo(enfo.crm.vo.PreDoubleRecordInfoVO, int, int)
	 */
	@Override
	public IPageList queryPreDoubleRecordInfo(PreDoubleRecordInfoVO vo, int pageIndex,
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
			throw new BusiException("查询双录录入信息失败: " + e.getMessage());
		}
		return rsList;
	}
    
}