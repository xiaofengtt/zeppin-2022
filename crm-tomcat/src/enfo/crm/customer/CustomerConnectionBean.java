package enfo.crm.customer;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmBusiExBean;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.tools.EJBFactory;
import enfo.crm.tools.Utility;
import enfo.crm.vo.CustomerConnectionVO;
import enfo.crm.vo.CustomerVO;

@Component(value="customerConnection")
public class CustomerConnectionBean extends CrmBusiExBean implements CustomerConnectionLocal {
	/**
	 * 新增
	 */
	private static String addSql = "{?=call SP_ADD_TCUSTOMERSCONNECTION(?,?,?,?,?,?,?,?)}";

	/**
	 * 修改
	 */
	private static String modiSql = "{?=call SP_MODI_TCUSTOMERSCONNECTION(?,?,?,?,?,?,?,?,?)}";

	/**
	 * 查询
	 */
	private static String querySql = "{call SP_QUERY_TCUSTOMERSCONNECTION(?,?,?,?,?)}";

	/**
	 * 确认
	 */
	private static String checkSql = "{?=call SP_CHECK_TCUSTOMERSCONNECTION(?,?,?,?)}";
	
	/**
	 * 审核
	 */
	private static String recheckSql = "{?=call SP_RE_CHECK_TCUSTOMERSCONNECTION(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	/* (non-Javadoc) 
	 * @see enfo.crm.marketing.CustomerConnectionLocal#addCustomerConnection(enfo.crm.vo.CustomerConnectionVO)
	 */
	@Override 
	public Integer addCustomerConnection(CustomerConnectionVO vo) throws BusiException {
		Connection conn = null;
		CallableStatement stmt = null;
		try { 
			conn = CrmDBManager.getConnection();
			stmt = conn.prepareCall(
					        addSql,
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			stmt.registerOutParameter(1, java.sql.Types.INTEGER);
			stmt.setInt(2, vo.getCust_id().intValue());
			stmt.setInt(3, vo.getInput_man().intValue());
			stmt.setString(4, Utility.trimNull(vo.getCust_tel()));
			stmt.setString(5, Utility.trimNull(vo.getMobile()));
			stmt.setString(6, Utility.trimNull(vo.getO_tel()));
			stmt.setString(7, Utility.trimNull(vo.getH_tel()));
			stmt.setString(8, Utility.trimNull(vo.getBp()));
			stmt.setString(9, Utility.trimNull(vo.getApply_reason()));
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
	 * @see enfo.crm.marketing.CustomerConnectionLocal#modiCustomerConnection(enfo.crm.vo.CustomerConnectionVO)
	 */
	@Override
	public void modiCustomerConnection(CustomerConnectionVO vo) throws BusiException {
		Object[] params = new Object[9];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		params[2] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		params[3] = Utility.trimNull(vo.getCust_tel());
		params[4] = Utility.trimNull(vo.getMobile());
		params[5] = Utility.trimNull(vo.getO_tel());
		params[6] = Utility.trimNull(vo.getH_tel());
		params[7] = Utility.trimNull(vo.getBp());
		params[8] = Utility.trimNull(vo.getApply_reason());
		try {
			super.cudProc(modiSql, params);
		} catch (Exception e) {
			throw new BusiException("修改客户联系方式修改申请信息失败: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.CustomerConnectionLocal#checkCustomerConnection(enfo.crm.vo.CustomerConnectionVO)
	 */
	@Override
	public void checkCustomerConnection(CustomerConnectionVO vo) throws BusiException {
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getStatus(), new Integer(-2));
		params[2] = vo.getInput_man().intValue();
		params[3] = Utility.trimNull(vo.getCheck_reason());
		try {
			super.cudProcNoRet(checkSql, params);
		} catch (Exception e) {
			throw new BusiException("确认客户联系方式修改申请信息失败: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.CustomerConnectionLocal#recheckCustomerConnection(enfo.crm.vo.CustomerConnectionVO)
	 */
	@Override
	public void recheckCustomerConnection(CustomerConnectionVO cust_vo) throws BusiException {
		//先取一遍客户信息
		Integer cust_id = cust_vo.getCust_id();
		Map map = new HashMap();
		try {
			CustomerLocal cust_local = EJBFactory.getCustomer();
			
			CustomerVO vo = new CustomerVO();
			vo.setCust_id(cust_id);
			vo.setInput_man(cust_vo.getInput_man());
			List list = cust_local.listProcAll_m(vo);
			map = (Map) list.get(0);
			
			Object[] params = new Object[43];
			params[0] = Utility.parseInt(cust_vo.getSerial_no(), new Integer(0));
			params[1] = Utility.parseInt(cust_vo.getStatus(), new Integer(0));
			params[2] = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),0);
			params[3] = map.get("CUST_NAME");
			params[4] = vo.getCust_tel();
			params[5] = map.get("POST_ADDRESS");
			params[6] = map.get("POST_CODE");
			params[7] = map.get("CARD_TYPE");
			params[8] = map.get("H_CARD_ID");
			params[9] = Utility.parseInt(Utility.trimNull(map.get("AGE")), new Integer(0));
			params[10] = Utility.parseInt(Utility.trimNull(map.get("SEX")), new Integer(0));
			params[11] = vo.getO_tel();
			params[12] = vo.getH_tel();
			params[13] = vo.getMobile();
			params[14] = vo.getBp();
			params[15] = map.get("H_FAX");
			params[16] = map.get("H_E_MAIL");
			params[17] = Utility.parseInt(Utility.trimNull(map.get("CUST_TYPE")), new Integer(0));
			params[18] = map.get("TOUCH_TYPE");
			params[19] = map.get("CUST_SOURCE");
			params[20] = map.get("SUMMARY");
			params[21] = vo.getInput_man();
			params[22] = map.get("CUST_NO");
			params[23] = vo.getLegal_man();
			params[24] = vo.getLegal_address();
			params[25] = Utility.parseInt(Utility.trimNull(map.get("BIRTHDAY")), new Integer(0));
			params[26] = map.get("POST_ADDRESS2");
			params[27] = map.get("POST_CODE2");
			params[28] =
				Utility.parseInt(Utility.trimNull(map.get("PRINT_DEPLOY_BILL")), new Integer(0));
			params[29] = Utility.parseInt(Utility.trimNull(map.get("PRINT_POST_INFO")), new Integer(0));
			params[30] = Utility.parseInt(Utility.trimNull(map.get("IS_LINK")),new Integer(0));
			params[31] = Utility.parseInt(Utility.trimNull(map.get("SERVICE_MAN")),vo.getInput_man());
			params[32] = Utility.trimNull(map.get("VIP_CARD_ID"));
			params[33] = Utility.parseInt(Utility.trimNull(map.get("VIP_DATE")),new Integer(0));
			params[34] = Utility.trimNull(map.get("HGTZR_BH"));
			params[35] = Utility.trimNull(map.get("VOC_TYPE"));
			params[36] = Utility.parseInt(Utility.trimNull(map.get("CARD_VALID_DATE")), null);
			params[37] = Utility.trimNull(map.get("COUNTRY"),"9997CHN");
			params[38] = vo.getJg_cust_type();
			params[39] = Utility.trimNull(map.get("CONTACT_MAN"));
			
			params[40] = vo.getEast_jg_type();
			params[41] = vo.getJg_wtrlx2();
			params[42] = Utility.trimNull(cust_vo.getRe_check_reason());
			
//			Object[] params = new Object[3];
//			params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
//			params[1] = Utility.parseInt(vo.getStatus(), new Integer(-2));
//			params[2] = vo.getInput_man().intValue();
			try {
				super.cudProcNoRet(recheckSql, params);
			} catch (Exception e) {
				throw new BusiException("审核客户联系方式修改申请信息失败: " + e.getMessage());
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}


	/* (non-Javadoc)
	 * @see enfo.crm.marketing.CustomerConnectionLocal#queryCustomerConnection(enfo.crm.vo.CustomerConnectionVO)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List queryCustomerConnection(CustomerConnectionVO vo) throws BusiException {
		List list = null;
		Object[] params = new Object[5];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		params[2] = Utility.parseInt(vo.getFlagCheck(), new Integer(0));
		params[3] = vo.getStatus();
		params[4] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		try {
			list = super.listBySql(querySql, params);
		} catch (Exception e) {
			throw new BusiException("查询客户联系方式修改申请信息失败: " + e.getMessage());
		}
		return list;
	}
    
}