package enfo.crm.tools;

/**
 * <p>
 * Title: System arguments
 * </p>
 * <p>
 * Description: System arguments for business options
 * </p>
 * <p>
 * Copyright: Copyright (c) Singlee Software 2003
 * </p>
 * <p>
 * Company: Singlee Software
 * </p>
 *
 * @author <a href="mailto:zhou_bin@singlee.com"> </a>
 * @version 1.0
 */

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import enfo.crm.affair.ServiceManageLocal;
import enfo.crm.affair.ServiceTaskLocal;
import enfo.crm.affair.TcustmanagersLocal;
import enfo.crm.affair.TmanagerteamsLocal;
import enfo.crm.callcenter.CallCenterLocal;
import enfo.crm.callcenter.SmsRecordLocal;
import enfo.crm.contractManage.TcoTeamInfoLocal;
import enfo.crm.customer.CustClassDetailLocal;
import enfo.crm.customer.CustGroupLocal;
import enfo.crm.customer.CustomerContactLocal;
import enfo.crm.customer.CustomerLocal;
import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.dao.IntrustDBManager;
import enfo.crm.intrust.ContractLocal;
import enfo.crm.intrust.CustomerInfoLocal;
import enfo.crm.intrust.IntrustManagerManOperLocal;
import enfo.crm.intrust.ProductLocal;
import enfo.crm.marketing.QuestionnaireLocal;
import enfo.crm.system.DepartmentLocal;
import enfo.crm.system.DictparamLocal;
import enfo.crm.system.FaqsLocal;
import enfo.crm.system.MenuInfoLocal;
import enfo.crm.system.OperatorLocal;
import enfo.crm.system.RoleLocal;
import enfo.crm.util.JsonUtil;
import enfo.crm.vo.CCVO;
import enfo.crm.vo.ContractVO;
import enfo.crm.vo.CustClassDetailVO;
import enfo.crm.vo.CustGroupVO;
import enfo.crm.vo.CustomerContactVO;
import enfo.crm.vo.CustomerInfoVO;
import enfo.crm.vo.CustomerVO;
import enfo.crm.vo.DictparamVO;
import enfo.crm.vo.FaqsVO;
import enfo.crm.vo.OperatorVO;
import enfo.crm.vo.ProductVO;
import enfo.crm.vo.QuestionnaireVO;
import enfo.crm.vo.SendSMSVO;
import enfo.crm.vo.ServiceManageVO;
import enfo.crm.vo.ServiceTaskVO;
import enfo.crm.vo.TcoTeamInfoVO;
import enfo.crm.vo.TcustmanagersVO;
import enfo.crm.vo.TmanagerteammembersVO;
import enfo.crm.vo.TmanagerteamsVO;
import enfo.crm.web.SmsClient;

public class Argument {
	private static Hashtable role_table = null;

	private static Hashtable department_table = null;

	private static Hashtable operator_table = null;

	public static String randomUUID()
	{
		StringBuffer sb = new StringBuffer(200);
		sb.append(UUID.randomUUID().toString());
		return sb.toString();
	
	}
	
	public static StringBuffer newStringBuffer() {
		StringBuffer sb = new StringBuffer(200);
		sb.append("<option value=\"\">请选择</option>");
		return sb;
	}

	public static StringBuffer newStringBufferBit() {
		StringBuffer sb = new StringBuffer(200);
		sb.append("<option value=\"-1\">请选择</option>");
		return sb;
	}

	public static void appendOptions(StringBuffer sb, int value, String text,
			int defvalue) {
		sb.append("<option");
		sb.append((defvalue == value) ? " selected" : "");
		sb.append(" value=\"" + value + "\">");
		sb.append(text);
		sb.append("</option>");
	}

	public static void appendOptions(StringBuffer sb, Integer value,
			String text, Integer defvalue) {

		sb.append("<option ");

		if ((defvalue != null) && (defvalue.intValue() == value.intValue())) {
			sb.append(" selected");
		}
		sb.append(" value=\"" + value + "\">");
		sb.append(text);
		sb.append("</option>");
	}

	public static void appendOptions(StringBuffer sb, int value, String text,
			Integer defvalue) {
		sb.append("<option");
		if ((defvalue != null) && (defvalue.intValue() == value))
			sb.append(" selected ");
		sb.append(" value=\"" + value + "\">");
		sb.append(text);
		sb.append("</option>");
	}

	public static void appendOptions(StringBuffer sb, String value,
			String text, String defvalue) {
		sb.append("<option");
		if (defvalue != null)
			sb
					.append((value.trim().compareTo(defvalue.trim()) == 0) ? " selected"
							: "");
		sb.append(" value=\"" + value + "\">");
		sb.append(text);
		sb.append("</option>");
	}

	//根据参数值得到参数名称
	public static String getDictParamName(int dict, String value)
			throws Exception {
		String listSql = "{call SP_QUERY_TDICTPARAM_VALUE  (?,?)}";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setString(1, value);
		stmt.registerOutParameter(2, java.sql.Types.VARCHAR);

		stmt.executeUpdate();
		String dictName = stmt.getString(2);
		stmt.close();
		conn.close();
		return dictName;
	}
	
	
	//根据字典表的type_content查询type_value
	public static String getTdictparamValueByContent(int type_id,String type_content) throws Exception{
		
		Connection conn = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		String type_value="";
		try {
			conn =CrmDBManager.getConnection();
			stmt = conn.prepareCall(
					"{call SP_QUERY_TDICTPARAM_CONTENT(?,?)}",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			stmt.setInt(1,type_id);
			stmt.setString(2,type_content);
			
			rs = stmt.executeQuery();
			
			if(rs.next())
				type_value = rs.getString("TYPE_VALUE");
		} catch(Exception e){
			return null;
		}finally {
			if(rs != null)
				rs.close();
			if(stmt != null)
				stmt.close();
			if(conn != null)
				conn.close();
		}
		return type_value;
	}

	//从TSYSCONTROL中根据flag_type获取value值
	public static int getValueOfTSysControlByFlagType(String flag_type,
			String defaultVlue) throws Exception {
		int return_value = Utility.parseInt(defaultVlue, 0);
		String listSql = "{call SP_QUERY_TSYSCONTROL_VALUE (?)}";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setString(1, flag_type);
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			return_value = rs.getInt("VALUE");
			break;
		}
		rs.close();
		stmt.close();
		conn.close();
		return return_value;
	}

	public static String getPageSizeOptions(int pagesize) {
		StringBuffer sb = new StringBuffer(200);
		//appendOptions(sb, 8, "8", pagesize);
		appendOptions(sb, 10, "10", pagesize);
		appendOptions(sb, 20, "20", pagesize);
		appendOptions(sb, 50, "50", pagesize);
		appendOptions(sb, 100, "100", pagesize);
		appendOptions(sb, 200, "200", pagesize);
		if (pagesize != 10 && pagesize != 20 && pagesize != 50
				&& pagesize != 100 && pagesize != 200)
			appendOptions(sb, 0, "ALL", 0);
		else
			appendOptions(sb, 0, "ALL", pagesize);
		return sb.toString();
	}

	//　根据角色和帐套查询操作员
	public static String getRoledOperatorOptions(Integer book_code,
			int role_id, Integer op_code) throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rslist = stmt.executeQuery("{call SP_QUERY_TOPERATOR_BYROLE("
				+ role_id + ")}");
		try {
			while (rslist.next()) {
				appendOptions(sb, rslist.getInt("op_code"), rslist
						.getString("op_name"), op_code);
			}
			//appendOptions(sb,888,"888",op_code);
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//　根据角色和帐套查询操作员
	public static String getRoledOperatorOptions_intrust(Integer book_code,
			int role_id, Integer op_code) throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rslist = stmt.executeQuery("{call SP_QUERY_TOPERATOR_BYROLE("
				+ book_code + ", " + role_id + ")}");
		try {
			while (rslist.next()) {
				appendOptions(sb, rslist.getInt("op_code"), rslist
						.getString("op_name"), op_code);
			}
			//appendOptions(sb,888,"888",op_code);
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//根据客户信息查找合同联系人
	public static String getCustOptions(Integer cust_id,Integer contact_id) throws Exception {
		String str1="";
		String str2="";
		String str3="";
		String str4="";
		StringBuffer sb = newStringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rslist = stmt.executeQuery("select ContactID,CUST_ID,Contactor,PhoneHome,PhoneOffice,Moblie,Address from " +
				"EFCRM..TCustomerContacts where CUST_ID="+cust_id);
		try{
			while (rslist.next()) {
				if(rslist.getString("PhoneHome")!=null&&!rslist.getString("PhoneHome").equals("")){
					str1="家庭电话:"+rslist.getString("PhoneHome")+"  ";
				}
				if(rslist.getString("Moblie")!=null&&!rslist.getString("Moblie").equals("")){
					str2="手机:"+rslist.getString("Moblie")+"  ";
				}
				if(rslist.getString("PhoneOffice")!=null&&!rslist.getString("PhoneOffice").equals("")){
					str3="办公室电话:"+rslist.getString("PhoneOffice")+"  ";
				}
				if(rslist.getString("Address")!=null&&!rslist.getString("Address").equals("")){
					str4="地址:"+rslist.getString("Address");
				}
				appendOptions(sb,rslist.getInt("ContactID"),rslist.getString("Contactor")+" | "+str1+str2+
						str3+str4,contact_id);
			}
		}finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}
	//根据标志类型查询系统参数
	public static int getSyscontrolValue(String flag_type) throws Exception {
		String listSql = "{call SP_QUERY_TSYSCONTROL (?,?,?,?)}";
		int ret = 0;
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setString(1, flag_type);
		stmt.setString(2, "");
		stmt.setInt(3, 0);
		stmt.setString(4, "");
		ResultSet rs = stmt.executeQuery();
		try {
			if (rs.next())
				ret = rs.getInt("VALUE");
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return ret;
	}

	//	根据标志类型查询系统参数
	public static int getSyscontrolValue_intrust(String flag_type)
			throws Exception {
		String listSql = "{call SP_QUERY_TSYSCONTROL (?,?,?,?)}";
		int ret = 0;
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setString(1, flag_type);
		stmt.setString(2, "");
		stmt.setInt(3, 0);
		stmt.setString(4, "");
		ResultSet rs = stmt.executeQuery();
		try {
			if (rs.next())
				ret = rs.getInt("VALUE");
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return ret;
	}

	//bottom_flag为1的部门信息
	public static String getDepartValue(Integer bottom_flag) throws Exception {
		String listSql = "{call SP_QUERY_TSYSCONTROL(?,?,?)}";
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=\"-1\">请选择</option>");
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, 0);
		stmt.setInt(2, 0);
		stmt.setInt(3, bottom_flag.intValue());
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				appendOptions(sb, rs.getInt("DEPART_ID"), rs
						.getString("DEPART_NAME"), bottom_flag);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	/**
	 * 获取已审待批的登记批复信息 autor by lk
	 *
	 * @param check_flag
	 * @return
	 * @throws Exception
	 */
	public static String getPrecompanyCheckFlagValue(Integer check_flag,
			Integer company_id) throws Exception {
		String listSql = "{call SP_QUERY_TPRECOMPANY(?,?,?,?)}";
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=\"0\">请选择</option>");
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setString(1, "");
		stmt.setString(2, "");
		stmt.setInt(3, 0);
		stmt.setInt(4, check_flag.intValue());
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				appendOptions(sb, rs.getInt("COMPANY_ID"), rs
						.getString("COMPANY_NAME"), company_id);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//根据部门id查询查询部门名称
	public static String getDepartOptions(Integer depart_id) throws Exception {

		String sb = "";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TDEPARTMENT(?,?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, depart_id.intValue());
		stmt.setInt(2, 0);
		stmt.setInt(3, 0);
		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {

				sb = rslist.getString("DEPART_NAME");
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb;
	}

	public static String getDepartOptions1(Integer depart_id) throws Exception {

		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TDEPARTMENT(?,?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, 0);
		stmt.setInt(2, 0);
		stmt.setInt(3, 0);
		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				appendOptions(sb, rslist.getInt("DEPART_ID"), rslist
						.getString("DEPART_NAME"), depart_id.intValue());
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//根据帐套id，查询帐套名称
	public static String getBook_name(Integer book_code) throws Exception {
		String sb = "";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TACCTBOOK(?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				sb = rslist.getString("BOOK_NAME");
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb;
	}

	//根据role_id，查询角色名称
	public static String getRole_name(Integer role_id) throws Exception {
		String sb = "";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall("{call SP_QUERY_TROLE(?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, role_id.intValue());
		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				sb = rslist.getString("ROLE_NAME");
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb;
	}

	//根据帐套id，查询帐套名称
	public static String getOpBook_Code(Integer op_code, Integer book_code)
			throws Exception {
		String listSql = "{call SP_QUERY_TOPBOOK(?,?)}";
		StringBuffer sb = new StringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		if (op_code == null)
			stmt.setInt(1, 0);
		else
			stmt.setInt(1, op_code.intValue());
		if (book_code == null)
			stmt.setInt(2, 0);
		else
			stmt.setInt(2, book_code.intValue());

		ResultSet rs = stmt.executeQuery();
		try {
			appendOptions(sb, 0, "全部", book_code);
			while (rs.next()) {
				appendOptions(sb, rs.getInt("BOOK_CODE"), rs
						.getString("BOOK_NAME"), book_code);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//	根据参数值得到参数名称:列表
	public static String getDictParamOptions(int dict, String value)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TDICTPARAM(?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, dict);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				appendOptions(sb, rs.getString("TYPE_VALUE"), rs
						.getString("TYPE_CONTENT"), value);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	
	//	根据参数值得到参数名称:列表
	public static String getTmacro(String macro_name,String macro_code,Integer inputman)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TMACRO(?,?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setString(1,macro_name);
		stmt.setString(2,macro_code);
		stmt.setInt(3,inputman.intValue());
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				appendOptions(sb, rs.getString("MACRO_CODE"), rs
						.getString("MACRO_NAME"), macro_code);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	
	
	
	//根据参数值得到参数名称:列表 intrust库
	public static String getDictParamOptions_intrust(int dict, String value)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TDICTPARAM(?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, dict);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				appendOptions(sb, rs.getString("TYPE_VALUE"), rs
						.getString("TYPE_CONTENT"), value);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}
	//根据参数值得到参数字典表列表
	public static List getDictParamList(int dict, String value) throws Exception {
		List list = new ArrayList();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TDICTPARAM(?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, dict);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				Map map = new HashMap();
				if (!(rs.getString("TYPE_VALUE").equals(value))) {
					map.put("TYPE_VALUE", rs.getObject("TYPE_VALUE"));
					map.put("TYPE_CONTENT", rs.getObject("TYPE_CONTENT"));
					map.put("ADDITIVE_VALUE", rs.getObject("ADDITIVE_VALUE"));
					list.add(map);
				}
			}
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (conn!=null) conn.close();
		}
		return list;
	}
	//根据参数值得到参数字典表列表
	public static List getDictParamListIntrust(int dict, String value) throws Exception {
		List list = new ArrayList();
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TDICTPARAM(?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, dict);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				Map map = new HashMap();
				if (!(rs.getString("TYPE_VALUE").equals(value))) {
					map.put("TYPE_VALUE", rs.getObject("TYPE_VALUE"));
					map.put("TYPE_CONTENT", rs.getObject("TYPE_CONTENT"));
					map.put("ADDITIVE_VALUE", rs.getObject("ADDITIVE_VALUE"));
					list.add(map);
				}
			}
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (conn!=null) conn.close();
		}
		return list;
	}

	public static String getGovRegional(Integer type_id, String type_value,
			Integer serial_no) throws Exception {
		List list = new ArrayList();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TDICTPARAM_9999(?,?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		stmt.setInt(1, Utility.parseInt(type_id.intValue(), 0));
		stmt.setString(2, Utility.trimNull(type_value));
		stmt.setInt(3, Utility.parseInt(serial_no.intValue(), 0));
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				Map map = new HashMap();
				map.put(rs.getString("TYPE_VALUE"), rs
						.getString("TYPE_CONTENT"));
				list.add(map);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return JsonUtil.object2json(list);
	}

	//	个人信托资金来源
	public static String getJr_Money_Source(String money_source)
			throws Exception {

		return getDictParamOptions_intrust(8202, money_source);
	}
	
	//	机构信托资金来源
	public static String getJg_Money_Source(String money_source)
			throws Exception {

		return getDictParamOptions_intrust(8201, money_source);
	}
	
	//	市场代码
	public static String getSeidOptions(String value) throws Exception {
		return getDictParamOptions(1130, value);
	}

	//	证券类型
	public static String getStockTypeOptions(String value) throws Exception {
		return getDictParamOptions(1129, value);
	}

	//根据操作员id查询操作员姓名
	public static String getOpNameByOpCode(Integer op_code) throws Exception {
		if (op_code == null || op_code.intValue() == 0)
			return "";
		OperatorLocal local = EJBFactory.getOperator();
		OperatorVO vo = new OperatorVO();
		vo.setOp_code(op_code);
		List list = local.listOpAll(vo);
		Map rowMap = null;
		String name = "";
		if (list.size() > 0) {
			rowMap = (Map) list.get(0);
			name = Utility.trimNull(rowMap.get("OP_NAME"));
		}
		local.remove();
		return name;
	}

	//根据操作员id查询操作员信息
	public static List getOperatorInfoByOpCode(Integer op_code)
			throws Exception {
		List list = new ArrayList();
		if (op_code == null || op_code.intValue() == 0)
			return list;
		OperatorLocal local = EJBFactory.getOperator();
		OperatorVO vo = new OperatorVO();
		vo.setOp_code(op_code);
		list = local.listOpAll(vo);

		local.remove();
		return list;
	}

	//	行业
	public static String getVocationOptions(String dict, String value)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TVOCATIONINFO(?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setString(1, dict);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				appendOptions(sb, rs.getString("VOCATION_CODE"), rs
						.getString("VOCATION_NAME"), value);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//	按照行业代码获取行业名称行业 add by tsg 2008-05-28
	public static String getVocationName(String vocation_code) throws Exception {
		String vocation_name = "";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TVOCATIONINFO(?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setString(1, vocation_code);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				vocation_name = rs.getString("VOCATION_NAME");
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return vocation_name;
	}

	// 币种
	public static String getCurrencyOptions(String value) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=\"\">请选择</option>");
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TCURRENCY(null)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				Argument.appendOptions(sb, rs.getString("CURRENCY_ID"), rs
						.getString("CURRENCY_NAME"), value);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	// 角色
	public static String getRoleId(int role_id) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=\"\">请选择</option>");
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall("{call SP_QUERY_TROLE(0)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		//stmt.setInt(1,role_id);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				Argument.appendOptions(sb, rs.getInt("ROLE_ID"), rs
						.getString("ROLE_NAME"), role_id);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	public static String getDictContent(String type_value) throws Exception {
		if (type_value == null)
			return null;
		String ret = "";
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("{call SP_QUERY_DICTCONTENT ('"
				+ type_value + "')}");
		try {
			if (rs.next())
				ret = rs.getString("TYPE_CONTENT");
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return ret;
	}

	// 得到所有参数名称列表---无重复项
	public static String getAllDictParamOptions(Integer value) throws Exception {
		StringBuffer sb = newStringBuffer();
		String listSql = "{call SP_QUERY_TDICTPARAM_LIST()}";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				Argument.appendOptions(sb, rslist.getInt("type_id"), rslist
						.getInt("type_id")
						+ "_" + rslist.getString("type_name"), value);
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	public static String getOpName(Integer op_code) throws Exception {
		return getOpNameByOpCode(op_code);
	}

	public static synchronized void initHashTable(boolean doInit, Integer status)
			throws Exception {
		if (!doInit) {
			role_table = null;
			department_table = null;
			operator_table = null;
			return;
		}

		if (role_table == null) {
			role_table = new Hashtable();
			RoleLocal local = EJBFactory.getRole();
			List list = local.listBySql(null);
			Map map = null;
			for (int i = 0; i < list.size(); i++) {
				map = (Map) list.get(i);
				role_table.put(map.get("ROLE_ID"), map.get("ROLE_NAME"));
			}
			local.remove();
		}

		if (department_table == null) {
			department_table = new Hashtable();
			DepartmentLocal local = EJBFactory.getDepartment();
			List list = local.listBySql(null);
			Map map = null;
			for (int i = 0; i < list.size(); i++) {
				map = (Map) list.get(i);
				department_table.put(map.get("DEPART_ID"), map
						.get("DEPART_NAME"));
			}
			local.remove();
		}

		if (operator_table == null) {
			//Utility.debugln("do operator_table");
			operator_table = new Hashtable();
			OperatorVO vo = new OperatorVO();
			vo.setStatus(status);
			OperatorLocal local = EJBFactory.getOperator();
			List list = local.listOpAll(vo);
			Map map = null;
			for (int i = 0; i < list.size(); i++) {
				map = (Map) list.get(i);
				operator_table.put(map.get("OP_CODE"), map.get("OP_NAME"));
				//Utility.debugln("in Argument OP_CODE is " +
				// map.get("OP_CODE"));
				//Utility.debugln("in Argument OP_NAME is " +
				// map.get("OP_NAME"));
			}
			local.remove();
		}
	}

	//	根据产品ID和币种ID查询开户银行
	public static String getbankOption(Integer book_code,Integer product_id,Integer serial_no)
			throws Exception {

		String listSql = "{call SP_QUERY_TSUBBANKINFO1021_BANKLIST (?,?)}";
		StringBuffer sb = newStringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setInt(2, product_id.intValue());
		//stmt.setString(3, currency_id);
		ResultSet rs = stmt.executeQuery();

		try {
			while (rs.next())
				if (Utility.trimNull(rs.getString("bank_acct")).length() > 0) {
					appendOptions(sb, rs.getInt("serial_no"), rs
							.getString("acct_type_name")
							+ "-"
							+ rs.getString("bank_name")
							+ "-"
							+ rs.getString("bank_acct"), serial_no);
				}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

//	 根据产品ID和币种ID查询开户银行
	public static String getbankOption(Integer book_code, Integer product_id,
			String currency_id, Integer serial_no) throws Exception {

		String listSql = "{call SP_QUERY_TSUBBANKINFO1021_BANKLIST (?,?,?)}";
		StringBuffer sb = newStringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setInt(2, product_id.intValue());
		stmt.setString(3, currency_id);
		ResultSet rs = stmt.executeQuery();

		try {
			while (rs.next())
				if(Utility.trimNull(rs.getString("bank_acct")).length()>0){
					appendOptions(sb, rs.getInt("serial_no"), rs
							.getString("bank_name")
							+ "-" + rs.getString("bank_acct")
							+ "-" + rs.getString("acct_type_name"), serial_no);
				}


		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	public static String getOperatorValidOptions(Integer op_code)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TOPERATOR_VALID()}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				int op_code1 = rslist.getInt("op_code");
				String op_name1 = rslist.getString("op_name");
				Argument.appendOptions(sb, op_code1, op_name1, op_code);
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	public static String getOperatorName(Integer op_code) throws Exception {
		String op_name = "";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TOPERATOR (?,?,?,?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		if (op_code.intValue() > 0)
			stmt.setInt(1, op_code.intValue());
		else
			stmt.setInt(1, 9999999);
		stmt.setInt(2, 0);
		stmt.setInt(3, 0);
		stmt.setString(4, "");
		stmt.setInt(5, 1);

		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				op_name = rslist.getString("op_name");
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return op_name;
	}

	public static String getOperatorOptions(Integer op_code) throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TOPERATOR (?,?,?,?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, 0);
		stmt.setInt(2, 0);
		stmt.setInt(3, 0);
		stmt.setString(4, "");
		stmt.setInt(5, 1);

		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				appendOptions(sb, rslist.getInt("OP_CODE"), rslist
						.getString("OP_NAME"), op_code);
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	/**
	 * add by tsg 2008-05-29 选择营业部编号 SP_QUERY_TFUNDACCT_BYID
	 *
	 * @IN_BOOK_CODE TINYINT,
	 * @IN_FLAG INT,
	 * @IN_DEPART_CODE INT = 0,
	 * @IN_FUND_ACCT VARCHAR(10) = ''
	 */
	public static String getDepartNameByProductIDOptions(Integer book_code,
			int default_value) throws Exception {
		String listSql = "{call SP_QUERY_TFUNDACCT_BYID(?,?)}";
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setInt(2, 3);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				appendOptions(sb, rs.getInt("DEPART_CODE"), rs
						.getString("DEPART_NAME"), default_value);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//	选择同一产品的同一营业部的资金账号
	public static String getFundAcctByProductIDOptions(Integer book_code,
			Integer depart_code, String default_value) throws Exception {
		String listSql = "{call SP_QUERY_TFUNDACCT_BYID(?,?,?)}";
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setInt(2, 1);
		stmt.setInt(3, depart_code.intValue());
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				appendOptions(sb, rs.getString("FUND_ACCT"), rs
						.getString("FUND_ACCT"), default_value);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//选择同一产品的同一营业部的同一资金账号的证券账号
	public static String getSecurityAcctByProductIDOptions(Integer book_code,
			Integer depart_code, String fund_acct, String default_value)
			throws Exception {
		String listSql = "{call SP_QUERY_TFUNDACCT_BYID(?,?,?,?)}";
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setInt(2, 2);
		stmt.setInt(3, depart_code.intValue());
		stmt.setString(4, fund_acct);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				appendOptions(sb, rs.getString("SECURITY_ACCT"), rs
						.getString("SECURITY_ACCT"), default_value);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	public static String getDepart(Integer depart_id) throws Exception {
		if (depart_id == null)
			return null;
		initHashTable(true, new Integer(0));
		return (String) department_table.get(depart_id);
	}

	// 叶子部门列表 //
	public static String getDepartname(Integer depart_id) throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TDEPARTMENT(0,0,'',0)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				//if (rslist.getInt("bottom_flag") == 1)
				Argument.appendOptions(sb, rslist.getInt("DEPART_ID"), rslist
						.getString("DEPART_NAME"), depart_id);
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//	获取角色信息
	public static String getRolename(Integer role_id) throws Exception {
		String listSql = "{call SP_QUERY_TROLE(0)}";
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=\"0\">请选择</option>");
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				Argument.appendOptions(sb, rs.getInt("ROLE_ID"), rs
						.getString("ROLE_NAME"), role_id);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//获取相关部门信息
	public static String getName(Integer depart_id) throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TDEPARTMENT(0,0,0)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		//stmt.setInt(1, depart_id.intValue());
		//stmt.setInt(2, 0);
		//stmt.setInt(3, 0);
		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				Argument.appendOptions(sb, rslist.getInt("DEPART_ID"), rslist
						.getString("DEPART_NAME"), depart_id);
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//	根据参数值得到ADDITIVE_VALUE
	public static String getADDITIVE_VALUE(String value) throws Exception {
		String listSql = "{call SP_QUERY_DICTCONTENT (?)}";
		String ret = "";

		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setString(1, value);
		ResultSet rs = stmt.executeQuery();
		try {
			if (rs.next())
				ret = rs.getString("ADDITIVE_VALUE");
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return ret;
	}

	public static String getCurrencyName(String value) throws Exception {

		String sb = "";
		String sql = "{call SP_QUERY_TCURRENCY(?)}";
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(sql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setString(1, value);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				sb = rs.getString("CURRENCY_NAME");
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb;
	}

	/**
	 * @author lym
	 * @date 20100226
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String getCurrencyName1(String value) throws Exception {

		String sb = "";
		String sql = "{call SP_QUERY_TCURRENCY(?)}";
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(sql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setString(1, value);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				sb = rs.getString("CURRENCY_NAME");
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb;
	}

	/**
	 * add by tsg 20080617 如果参数值为NULL 或'',则不进行查询
	 */
	public static String getSingleCurrencyName(String value) throws Exception {
		if (value == null || value.equals(""))
			return "";
		String sb = "";
		String sql = "{call SP_QUERY_TCURRENCY(?)}";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(sql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setString(1, value);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				sb = rs.getString("CURRENCY_NAME");
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb;
	}

	/**
	 *
	 * 贷款标志1普通贷款2同业拆借
	 */
	public static String getDkflagOptions(Integer flag) {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 1, "普通贷款", flag);
		appendOptions(sb, 2, "同业拆出", flag);
		appendOptions(sb, 3, "买入返售信贷资产", flag);
		appendOptions(sb, 4, "买入返售证券", flag);
		appendOptions(sb, 5, "长期债权投资", flag);
		appendOptions(sb, 12, " 同业拆入", flag);
		appendOptions(sb, 13, " 卖出回购信贷资产", flag);
		appendOptions(sb, 14, " 卖出回购证券", flag);
		return sb.toString();
	}

	//	企业合同状态
	public static String getContractStatusOptions(String value)
			throws Exception {
		return getDictParamOptions(1136, value);
	}

	// 客户类别
	public static String getCustomerTypeOptions(String value) throws Exception {
		return getDictParamOptions(1120, value);
	}

	//	客户来源
	public static String getCustomerSourceOptions(String value)
			throws Exception {
		return getDictParamOptions(1110, value);
	}

	// 企业性质
	public static String getCompanyTypeOptions(String value) throws Exception {
		return getDictParamOptions(1121, value);
	}

	// 借款用途
	public static String getPurposeTypeOptions(String value) throws Exception {
		return getDictParamOptions(1122, value);
	}

	//	借款用途
	public static String getPurposeTypName(String value) throws Exception {
		return getDictParamName(1122, value);
	}

	// 股东类别
	public static String getHolderOptions(String value) throws Exception {
		return getDictParamOptions(1105, value);
	}

	//	个人家属类别\职位类别
	public static String getPartnerOptions(String value) throws Exception {
		return getDictParamOptions(1106, value);
	}

	// 浮动方式
	public static String getFloatTypeOptions(String value) throws Exception {
		return getDictParamOptions(1123, value);
	}

	// 浮动周期
	public static String getFloatPeriodOptions(String value) throws Exception {
		return getDictParamOptions(1124, value);
	}

	// 质量级别
	public static String getQualityLevelOptions(String value) throws Exception {
		return getDictParamOptions(1125, value);
	}

	//	贷款利率类别
	public static String getRateTypeOptions(String value) throws Exception {
		return getDictParamOptions2(1301, value);
	}

	//	贷款利率类别
	public static String getRateTypeName(String value) throws Exception {
		return getDictParamName(1301, value);
	}

	// 人民币计息周期
	public static String getRmbPeriodOptions(Integer value) throws Exception {
		StringBuffer sb = newStringBuffer();
		for (int i = 1; i < 13; i++)
			appendOptions(sb, i, Integer.toString(i), value);
		return sb.toString();
	}

	//	贷款类型1短期贷款2长期贷款
	public static String getLoanTypeOptions(Integer flag) throws Exception {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 0, "短期", flag);
		appendOptions(sb, 1, "中长期", flag);
		appendOptions(sb, 2, "长期", flag);
		return sb.toString();
	}

	//	客户类别---没有“请选择”
	public static String getCustTypeOptions2(Integer value) throws Exception {
		StringBuffer sb = new StringBuffer(200);
		appendOptions(sb, 0, "请选择", value);
		appendOptions(sb, 1, "个人", value);
		appendOptions(sb, 2, "机构", value);
		return sb.toString();
	}

	//	客户类别
	public static String getCustTypeOptions(Integer value) throws Exception {
		StringBuffer sb = new StringBuffer(200);
		appendOptions(sb, 0, "请选择", value);
		appendOptions(sb, 1, "个人", value);
		appendOptions(sb, 2, "机构", value);
		return sb.toString();
	}

	// 客户类别明细
	public static String getCustTypeDKDXOptions(Integer value) throws Exception {
		StringBuffer sb = new StringBuffer(200);
		appendOptions(sb, 0, "请选择", value);
		appendOptions(sb, 1, "农户", value);
		appendOptions(sb, 2, "城镇居民", value);
		appendOptions(sb, 3, "工商个体", value);
		return sb.toString();
	}

	//	个人职业类型---没有请选择
	public static String getGrzyOptions2(String value) throws Exception {
		return getDictParamOptions2(1142, value);
	}

	//	联系方式
	public static String getTouchTypeOptions(String value) throws Exception {
		return getDictParamOptions(1109, value);
	}

	// 客户国籍
	public static String getCountry(String value) throws Exception {
		return getDictParamOptions(9997, value);
	}

	//	根据参数值得到参数名称:列表---没有请选择
	public static String getDictParamOptions2(int dict, String value)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TDICTPARAM(?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, dict);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				appendOptions(sb, rs.getString("TYPE_VALUE"), rs
						.getString("TYPE_CONTENT"), value);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	// 机构客户类型
	public static String getJgCustType(String value) throws Exception {
		return getDictParamOptions(9921, value);
	}

	//	机构行业类型---没有请选择
	public static String getJghyOptions2(String value) throws Exception {
		return getDictParamOptions2(2142, value);
	}

	//	开户银行
	public static String getBankOptions(String value) throws Exception {
		return getDictParamOptions_intrust(1103, value);
	}

	//	个人证件类型---没有请选择
	public static String getCardTypeOptions2(String value) throws Exception {
		return getDictParamOptions2(1108, value);
	}

	// 证件类型
	public static String getCardType(String value) throws Exception {
		return getDictParamOptions(1108, value);
	}

	//	机构证件类型---没有请选择
	public static String getCardTypeJgOptions2(Object value) throws Exception {
		String val = "";
		if (value != null)
			val = value.toString();
		return getDictParamOptions2(2108, val);
	}

	//	信用级别
	public static String getCreditLevelOptions(String value) throws Exception {
		return getDictParamOptions(2118, value);
	}

	//余值处理方式
	public static String getClbzOptions(String value) throws Exception {
		return getDictParamOptions(1138, value);
	}

	//	存贷款贷款标志
	public static String getCreditTypeOptions(int flag) throws Exception {
		StringBuffer sb = newStringBuffer();
		//appendOptions(sb, 1, "存款", flag);
		appendOptions(sb, 2, "贷款", flag);
		//appendOptions(sb, 3, "金融机构", flag);
		return sb.toString();
	}

	/**
	 * 币种,没有请选择
	 *
	 * @param value
	 *            当前值
	 *
	 * @return
	 * @throws Exception
	 */
	public static String getCurrencyOptions2(String value) throws Exception {
		StringBuffer sb = new StringBuffer(200);
		sb.append("<option value=\" \">请选择</option>");
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TCURRENCY(NULL)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				Argument.appendOptions(sb, rs.getString("CURRENCY_ID"), rs
						.getString("CURRENCY_ID")
						+ "-" + rs.getString("CURRENCY_NAME"), value);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	public static java.lang.String getLiHiFlagOptions(int flag) {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 1, "LIBOR", flag);
		appendOptions(sb, 2, "HIBOR", flag);
		appendOptions(sb, 3, "EURBOR", flag);
		return sb.toString();
	}

	/*
	 * // 叶子部门列表 public static String getDepartName(Integer depart_id) throws
	 * Exception { StringBuffer sb = newStringBuffer(); Connection conn =
	 * DBManager.getConnection(); CallableStatement stmt = conn.prepareCall(
	 * "{call SP_QUERY_TDEPARTMENT(0,0,0)}", ResultSet.TYPE_SCROLL_INSENSITIVE,
	 * ResultSet.CONCUR_READ_ONLY); ResultSet rslist = stmt.executeQuery(); try {
	 * while (rslist.next()) { //if (rslist.getInt("bottom_flag") == 1)
	 * Argument.appendOptions( sb, rslist.getInt("depart_id"),
	 * rslist.getString("depart_name"), depart_id); } } finally {
	 * rslist.close(); stmt.close(); conn.close(); } return sb.toString(); }
	 */
	//根据role_id，查询角色名称
	public static String getRole_name2(Integer role_id) throws Exception {
		StringBuffer sb = newStringBuffer();
		sb.append("<option value=\"-1\">请选择</option>");
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall("{call SP_QUERY_TROLE(?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, role_id.intValue());
		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				appendOptions(sb, rslist.getInt("role_id"), rslist
						.getString("role_name"), role_id);
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//	审核标志0未审核1已审核
	public static String getCheckFlagOptions(Integer flag) {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "请选择", flag);
		appendOptions(sb, 1, "未审核", flag);
		appendOptions(sb, 2, "已审核", flag);
		return sb.toString();
	}

	public static String getSaleCheckFlagOptions(Integer flag) {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, -9, "未设置", flag);
		appendOptions(sb, 1, "未审核", flag);
		appendOptions(sb, 2, "已审核", flag);
		return sb.toString();
	}

	public static String getCheckFlagName(Integer flag) {
		if (flag == null)
			return null;
		switch (flag.intValue()) {
		case 1:
			return "未审核";
		case 2:
			return "已审核";
		case 3:
			return "作废";
		default:
			return "未知";
		}
	}

	public static String getCheckFlagName2(Integer flag) {
		if (flag == null)
			return null;
		switch (flag.intValue()) {
		case 1:
			return "未审核";
		case 2:
			return "已审核";
		case 3:
			return "审核不通过";
		default:
			return "未知";
		}
	}

	public static String getGdName(Integer book_code, String sec_id,
			String value) throws Exception {
		String listSql = "{call SP_QUERY_TSECURITYACCT (?,NULL,NULL,?,NULL,?)}";
		String ret = "";

		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setString(2, sec_id);
		stmt.setString(3, value);
		ResultSet rs = stmt.executeQuery();
		try {
			if (rs.next())
				ret = rs.getString("SECURITY_ACCT");
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return ret;
	}

	public static String getZQDM(String seid, String value) throws Exception {
		String listSql = "{call SP_QUERY_TSTOCKINFO (NULL,?,NULL,?)}";
		String ret = "";

		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setString(1, value);
		stmt.setString(2, seid);
		ResultSet rs = stmt.executeQuery();
		try {
			if (rs.next())
				ret = rs.getString("STOCK_CODE");
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return ret;
	}

	public static String getZQNAME(String seid, String value) throws Exception {
		String listSql = "{call SP_QUERY_TSTOCKINFO (NULL,?,NULL,?)}";
		String ret = "";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setString(1, value);
		stmt.setString(2, seid);
		ResultSet rs = stmt.executeQuery();
		try {
			if (rs.next())
				ret = rs.getString("STOCK_NAME");
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return ret;
	}

	public static java.math.BigDecimal getGP_BALANCE(Integer bookcode,
			String stock_code, String fund_acct, String security_acct,
			String seid) throws Exception {
		String listSql = "{call SP_QUERY_TSTOCKAMOUNT1(?,?,NULL,?,?,?,?)}";
		java.math.BigDecimal ret = new java.math.BigDecimal(0);
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, bookcode.intValue());
		stmt.setString(2, stock_code);
		stmt.setString(3, fund_acct);
		stmt.setString(4, security_acct);
		stmt.setString(5, seid);
		stmt.setInt(6, 1);
		ResultSet rs = stmt.executeQuery();
		try {
			if (rs.next())
				ret = rs.getBigDecimal("BALANCE");
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return ret;
	}

	public static synchronized java.util.Date get_Day(java.util.Date date, int i) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, i);
		return gc.getTime();
	}

	public static int getCurrentDate(java.util.Date date) {
		return Utility.getDateInt(date);
	}

	//	交害文件附加标志
	public static String getAddtionalOptions(String value) throws Exception {
		return getDictParamOptions(1132, value);
	}

	//	余额方向1借2贷
	public static String getDirectionOptions(int flag) {
		StringBuffer sb = newStringBufferBit();
		appendOptions(sb, 1, "借", flag);
		appendOptions(sb, 0, "贷", flag);
		return sb.toString();
	}

	//	科目类别
	public static String getSubTypeOptions(String value) throws Exception {
		return getDictParamOptions(2001, value);
	}

	//取得操作员下拉菜单
	public static String getOperatorValidOptions(Integer op_code,
			Integer book_code) throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TOPERATOR_VALID()}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				int op_code1 = rslist.getInt("op_code");
				String op_name1 = rslist.getString("op_name");
				int iBook_code = rslist.getInt("book_code");
				if (book_code.intValue() == iBook_code)
					Argument.appendOptions(sb, op_code1, op_name1, op_code);
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//租赁 的还款方式
	public static String getHKtypeOptions(Integer flag) {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 100, "等额还款", flag);
		appendOptions(sb, 200, "等本还款", flag);
		appendOptions(sb, 300, "实际利率法", flag);
		appendOptions(sb, 400, "直线法", flag);
		return sb.toString();
	}

	//	租赁 的还款方式
	public static String getHKtypeName(Integer flag) {
		StringBuffer retValue = null;
		if (flag == null)
			return "";
		if (flag.intValue() == 100)
			retValue = new StringBuffer("等额还款");
		else if (flag.intValue() == 200)
			retValue = new StringBuffer("等本还款");
		else if (flag.intValue() == 300)
			retValue = new StringBuffer("实际利率法");
		else if (flag.intValue() == 400)
			retValue = new StringBuffer("直线法");
		return retValue.toString();
	}

	//获取币种名称
	public static String getCurrencyJc(String value) throws Exception {
		if (value == null)
			return null;
		if (value.equals(""))
			return "";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TCURRENCY(?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setString(1, value);
		ResultSet rs = stmt.executeQuery();
		try {
			if (rs.next())
				return rs.getString("CURRENCY_JC");
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return "";
	}

	public static Integer[] getSub_flagArray(String sub_flag) throws Exception {
		Integer[] flagArray = new Integer[4];
		if (sub_flag == null || sub_flag.equals("") || sub_flag.length() != 4) {
			return null;
		}
		for (int i = 0; i < sub_flag.length(); i++) {
			flagArray[i] = Utility.parseInt(sub_flag.substring(i, i + 1), null);
		}
		return flagArray;
	}

	//取得辅助类型
	public static String getFz_type(String fz_type) throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall("{call SP_QUERY_TFZ_TYPE()}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				String fz_type1 = rslist.getString("FZ_TYPE");
				String fz_type_name = rslist.getString("FZ_TYPE_NAME");
				Argument.appendOptions(sb, fz_type1, fz_type_name, fz_type);
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	public static String getDkflagName(Integer flag) {
		if (flag == null)
			return "";
		if (flag.intValue() == 1)
			return "普通贷款";
		else if (flag.intValue() == 2)
			return "同业拆出";
		else if (flag.intValue() == 3)
			return "买入返售信贷资产";
		else if (flag.intValue() == 4)
			return "买入返售证券";
		else if (flag.intValue() == 5)
			return "长期债权投资";
		else if (flag.intValue() == 12)
			return "同业拆入";
		else if (flag.intValue() == 13)
			return "卖出回购信贷资产";
		else if (flag.intValue() == 14)
			return "卖出回购证券";
		else
			return null;
	}

	//	根据科目代码得到名称
	public static String getSubcodeOptions(String sub_code,
			Integer input_bookCode) throws Exception {
		String listSql = "{call SP_QUERY_TSUBJECT_LIST(?)}";
		StringBuffer sb = new StringBuffer(200);

		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		if (input_bookCode != null)
			stmt.setInt(1, input_bookCode.intValue());
		else
			stmt.setInt(1, 0);

		ResultSet rs = stmt.executeQuery();
		String sub_name = "";

		try {
			while (rs.next())
				appendOptions(sb, rs.getString("SUB_CODE"), rs
						.getString("SUB_NAME"), sub_code);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//	凭证类型
	public static String getPzTypeOptions(String value) throws Exception {
		return getDictParamOptions2(2003, value);
	}

	//凭证来源
	public static String getPzSourceTypeOptions(String value) throws Exception {
		return getDictParamOptions2(1002, value);
	}

	//租赁类型1短期租赁2长期租赁
	public static String getDirectionTypeOptions(Integer flag) throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 1, "显示借方合计", flag);
		appendOptions(sb, 2, "显示贷方合计", flag);
		return sb.toString();
	}

	public static String getPunishLabel(boolean rmb, Integer new_flag) {
		if (new_flag == null)
			return "";

		if (rmb && (new_flag.intValue() == 2))
			return "<img border='0' src='/images/wan.gif'>(天)";
		else
			return "%(上浮)";
	}

	public static String getBusiManPrint(Integer book_code) {
		if (book_code == null)
			return "";
		else if (book_code.intValue() == 1)
			return "执行经理";
		else if (book_code.intValue() == 2)
			return "业务员";
		else
			return "";
	}

	public static String getDepartPrint(Integer book_code) {
		if (book_code == null)
			return "";
		else if (book_code.intValue() == 1)
			return "资金托管部";
		else if (book_code.intValue() == 2)
			return "计财部";
		else
			return "";
	}

	public static String getCopytoPrint(Integer book_code) {
		if (book_code == null)
			return "";
		else if (book_code.intValue() == 1)
			return "执行经理";
		else if (book_code.intValue() == 2)
			return "资产管理总部";
		else
			return "";
	}

	public static String getLoanTypeName(Integer flag) throws Exception {
		if (flag == null)
			return null;
		if (flag.intValue() == 0)
			return "短期";
		else if (flag.intValue() == 1)
			return "中长期";
		else if (flag.intValue() == 2)
			return "长期";
		return null;
	}

	//	股权投资业务类别
	public static String getEntInvestTypeOptions(String value) throws Exception {
		return getDictParamOptions(1303, value);
	}

	//	行业
	public static String getVocationOptions(String value) throws Exception {
		return getDictParamOptions(1135, value);
	}

	// 外币浮动周期
	public static String getWbFdPeriodOptions(Integer value) throws Exception {
		StringBuffer sb = newStringBuffer();
		for (int i = 1; i < 13; i++)
			appendOptions(sb, i, Integer.toString(i), value);
		return sb.toString();
	}

	// 具有菜单权限的操作员
	public static String getFuncOperatorOptions(Integer book_code,
			String menu_id, int func_id, Integer operator) throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn
				.prepareCall("{call SP_QUERY_OPERATOR_BYFUNC (?, ?, ?)}");
		if (book_code == null)
			stmt.setInt(1, 0);
		else
			stmt.setInt(1, book_code.intValue());
		stmt.setString(2, menu_id);
		stmt.setInt(3, func_id);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				appendOptions(sb, rs.getInt("OP_CODE"),
						rs.getString("OP_NAME"), operator);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//记帐类别
	// modfiy by whf 根据用户操作，发现 记输入日期以后的帐到该记帐日，记输入日期以前的帐到该记帐日 这两项会产生问题
	//update by tsg 20090628 前面两项注释，后面两项的参数不能变化，value依然传2 3
	public static String getPostTypeOptions(int flag) {
		StringBuffer sb = new StringBuffer();
		//appendOptions(sb, 0, "记输入日期以后的帐到该记帐日", flag);
		//appendOptions(sb, 1, "记输入日期以前的帐到该记帐日", flag);
		appendOptions(sb, 2, "按凭证预设记账日记帐[全部]", flag);
		appendOptions(sb, 3, "按凭证预设记账日记帐[输入日期为止]", flag);
		return sb.toString();
	}

	//	资产编号信息列表
	public static String getCapitalNoOptions(String capital_no,
			String capital_name, Integer input_man) throws Exception {
		StringBuffer sb = newStringBuffer();
		String listSql = "{call SP_QUERY_TCAPITALINFO (?,?,?,?,?,?,?,?,?,?,?)}";
		ResultSet rslist = null;
		Connection conn = null;
		CallableStatement stmt = null;
		try {
			conn = CrmDBManager.getConnection();
			stmt = conn.prepareCall(listSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			stmt.setInt(1, 1);
			stmt.setInt(2, 0);
			stmt.setString(3, "");
			stmt.setString(4, "");
			stmt.setString(5, capital_name);
			stmt.setString(6, "");
			stmt.setString(7, "");
			stmt.setInt(8, 0);
			stmt.setString(9, "");
			stmt.setInt(10, input_man.intValue());
			stmt.setString(11, capital_no);

			rslist = stmt.executeQuery();

			Integer serialno = new Integer(0);
			String totalvalue = "";
			if (rslist != null) {
				int icount = 0;
				while (rslist.next()) {
					String capitalno = rslist.getString("CAPITAL_NO");
					String capitalname = rslist.getString("CAPITAL_NAME");
					appendOptions(sb, capitalno, capitalname, capital_no);
				}
			}
		} finally {
			if (rslist != null)
				rslist.close();

			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//	项目投资业务类别
	public static String getInvestTypeOptions(String value) throws Exception {
		return getDictParamOptions(1203, value);
	}

	//	资产性质
	public static String getCapitalUseOptions(String value) throws Exception {
		return getDictParamOptions(1911, value);
	}

	public static String getCurrency3(String value) throws Exception {
		StringBuffer sb = new StringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TCURRENCY(null)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				appendOptions(sb, rs.getString("CURRENCY_ID"), rs
						.getString("CURRENCY_NAME"), value);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	/**
	 * 币种,指显示四种
	 *
	 * @param value
	 *            当前值
	 *
	 * @return
	 * @throws Exception
	 */
	public static String getCurrencyOptions5(String value) throws Exception {
		StringBuffer sb = new StringBuffer(200);
		sb.append("<option value=\" \">请选择</option>");
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TCURRENCY(?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setString(1, "");
		stmt.setInt(2, 1);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				Argument.appendOptions(sb, rs.getString("CURRENCY_ID"), rs
						.getString("CURRENCY_ID")
						+ "-" + rs.getString("CURRENCY_NAME"), value);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	public static String getChargeLabel(boolean rmb) {
		if (rmb)
			return "‰(月)";
		else
			return "%(年)";
	}

	/**
	 * ADD BY TSG 20080618 根据pay_sbf_serial_no获取银行名称
	 */
	public static String getBankNameBySerialNo(Integer pay_sbf_serial_no)
			throws Exception {

		String listSql = "{call SP_QUERY_TSUBBANKINFO1021_BANKLIST(?)}";
		String bank_name = "";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, 1);
		ResultSet rs = stmt.executeQuery();
		int serial_no = 0;
		try {
			while (rs.next()) {
				serial_no = rs.getInt("SERIAL_NO");
				if (serial_no == pay_sbf_serial_no.intValue()) {
					bank_name = rs.getString("BANK_NAME")
							+ rs.getString("SUB_NAME");
				}
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return bank_name;
	}

	/**
	 * ADD BY TSG 20080618 根据pay_sbf_serial_no获取银行名称
	 */
	public static String getBankAcctBySerialNo(Integer pay_sbf_serial_no)
			throws Exception {
		String listSql = "{call SP_QUERY_TSUBBANKINFO1021_BANKLIST (?)}";
		String bank_acct = "";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, 1);
		ResultSet rs = stmt.executeQuery();
		int serial_no = 0;
		try {
			while (rs.next()) {
				serial_no = rs.getInt("SERIAL_NO");
				if (serial_no == pay_sbf_serial_no.intValue()) {
					bank_acct = rs.getString("BANK_ACCT");
				}
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return bank_acct;
	}
	/**
	 * 根据pay_sbf_serial_no获取银行信息
	 * @param pay_sbf_serial_no
	 * @return
	 * @throws Exception
	 */
	public static Map getBankInfo1021BySerialNo(Integer pay_sbf_serial_no) throws Exception {
		String listSql = "{call SP_QUERY_TSUBBANKINFO1021_SERIAL (?)}";
		String bank_acct = "";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, pay_sbf_serial_no.intValue());
		ResultSet rs = stmt.executeQuery();
		Map map=new HashMap();
		try {
			if (rs.next()) {
				map.put("BANK_NAME",rs.getString("BANK_NAME"));
				map.put("SUB_BANK_NAME",rs.getString("SUB_BANK_NAME"));
				map.put("BANK_ACCT",rs.getString("BANK_ACCT"));
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return map;
	}

	/**
	 *
	 * @author Lzhd 得到一级科目
	 */

	public static String getFristSubOptions(Integer book_code,
			String sub_code1, Integer input_man) throws Exception {
		String listSql = "{call SP_QUERY_TSUBJECT_BY_LEVEL(?,?,?,?)}";

		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setInt(2, 1);
		stmt.setString(3, "");
		stmt.setInt(4, input_man.intValue());

		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				appendOptions(sb, rs.getString("SUB_CODE"), rs
						.getString("SUB_CODE")
						+ "-" + rs.getString("SUB_NAME"), sub_code1);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//	投资增减标志
	public static String getZjFlagOptions(String invest_type, Integer flag)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 1, "投资股本变更", flag);
		appendOptions(sb, 2, "注册股本变更", flag);
		return sb.toString();
	}

	/**
	 * update by tsg 20081127 SP_QUERY_TCOMMCREDIT_YHKBH
	 *
	 * @IN_BOOK_CODE INT,
	 * @IN_DEAL_FLAG VARCHAR(10),
	 * @IN_YHK_FLAG INT, -- 0 用款 1 还款
	 * @IN_FLAG INT = 1 -- 1 正常还款 2 提前还款
	 * @IN_FLAG为新加的参数, 目前存储过程修改后,缺省情况下,还款的时候,只显示本会计周期内需要还款的合同. 如果选择提前还款标志,
	 *                      则显示所有需要还款的合同.
	 * @IN_CUST_NAME VARCHAR()
	 */
	public static String getContractValidBhOptions(Integer book_code,
			String deal_flag, int yfk_flag, int pre_pay_flag, String cust_name,
			String defaulvalue) throws Exception {
		StringBuffer sb = newStringBuffer();
		String listSql = "{call SP_QUERY_TCOMMCREDIT_YHKBH (?,?,?,?,?)}";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setString(2, deal_flag);
		stmt.setInt(3, yfk_flag); //1 用款 2 还款
		stmt.setInt(4, pre_pay_flag); //-- 1 正常还款 2 提前还款
		stmt.setString(5, cust_name);
		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				Argument.appendOptions(sb, rslist.getInt("CONTRACT_ID") + "$"
						+ rslist.getString("CONTRACT_BH"), rslist
						.getString("CONTRACT_SUB_BH")
						+ "_" + rslist.getString("CUST_NAME"), defaulvalue);
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	public static String getContractValidBhOptions1(Integer book_code,
			String deal_flag, int yfk_flag, int pre_pay_flag, String cust_name,
			String defaulvalue) throws Exception {
		StringBuffer sb = newStringBuffer();
		String listSql = "{call SP_QUERY_TCOMMCREDIT_YHKBH (?,?,?,?,?)}";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setString(2, deal_flag);
		stmt.setInt(3, yfk_flag); //1 用款 2 还款
		stmt.setInt(4, pre_pay_flag); //-- 1 正常还款 2 提前还款
		stmt.setString(5, cust_name);
		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				Argument.appendOptions(sb, rslist.getString("CONTRACT_BH"),
						rslist.getString("CONTRACT_SUB_BH") + "_"
								+ rslist.getString("CUST_NAME"), defaulvalue);
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//	科目级次一，二，三，四，五，六
	public static String getSubjectLevelOptions(int flag) throws Exception {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 0, "末级", flag);
		appendOptions(sb, 1, "一级", flag);
		appendOptions(sb, 2, "二级", flag);
		appendOptions(sb, 3, "三级", flag);
		appendOptions(sb, 4, "四级", flag);
		appendOptions(sb, 5, "五级", flag);
		appendOptions(sb, 6, "六级", flag);
		appendOptions(sb, 7, "七级", flag);
		return sb.toString();
	}

	//科目类别
	public static String getSubjectTypeOptions(String subtype) throws Exception {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, "200101", "信托资产类", subtype);
		appendOptions(sb, "200102", "信托负债类", subtype);
		appendOptions(sb, "200103", "信托权益类", subtype);
		appendOptions(sb, "200104", "信托损益类", subtype);
		appendOptions(sb, "200105", "表外科目", subtype);

		return sb.toString();
	}

	//根据用户id读取用户名称
	public static String getUserNameByUserId(Integer id) throws Exception {
		String listSql = "{call SP_QUERY_TUSERINFO(?)}";
		String user_name = "";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, id.intValue());
		ResultSet rs = stmt.executeQuery();
		int user_id = 0;
		try {
			while (rs.next()) {
				user_name = rs.getString("USER_NAME");
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return user_name;
	}

	//	明细帐查询时，科目起始列表
	public static String getDetailSubjectOptions(Integer book_code,
			String star_sub_code, String end_sub_code, String defultValue,
			Integer start_date, Integer end_date, Integer input_man)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		String listSql = "{call SP_QUERY_SUBLIST(?,?,?,?,?,?)}";
		ResultSet rslist = null;
		Connection conn = null;
		CallableStatement stmt = null;

		try {
			conn = CrmDBManager.getConnection();
			stmt = conn.prepareCall(listSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1, book_code.intValue());

			stmt.setString(2, star_sub_code);
			stmt.setString(3, end_sub_code);

			if (start_date != null)
				stmt.setInt(4, start_date.intValue());
			else
				stmt.setInt(4, 0);

			if (end_date != null)
				stmt.setInt(5, end_date.intValue());
			else
				stmt.setInt(5, 0);

			if (input_man != null)
				stmt.setInt(6, input_man.intValue());
			else
				stmt.setInt(6, 0);

			rslist = stmt.executeQuery();

			String subvalue = "";
			String totalvalue = "";
			if (rslist != null) {
				int icount = 0;
				while (rslist.next()) {
					subvalue = Utility.trimNull(rslist.getString("SUB_CODE"));
					totalvalue = Utility.trimNull(rslist.getString("SUB_CODE"))
							+ "-"
							+ Utility.trimNull(rslist.getString("SUB_NAME"));
					appendOptions(sb, subvalue, totalvalue, defultValue);
				}
			}
		} finally {
			if (rslist != null)
				rslist.close();

			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//	明细帐查询时，科目起始列表
	public static String getSubjectDetailFirstSubCode(Integer book_code,
			String star_sub_code, String end_sub_code, String defultValue,
			Integer start_date, Integer end_date, Integer input_man)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		String listSql = "{call SP_QUERY_SUBLIST(?,?,?,?,?,?)}";
		ResultSet rslist = null;
		Connection conn = null;
		CallableStatement stmt = null;

		try {
			conn = CrmDBManager.getConnection();
			stmt = conn.prepareCall(listSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1, book_code.intValue());

			stmt.setString(2, star_sub_code);
			stmt.setString(3, end_sub_code);

			if (start_date != null)
				stmt.setInt(4, start_date.intValue());
			else
				stmt.setInt(4, 0);

			if (end_date != null)
				stmt.setInt(5, end_date.intValue());
			else
				stmt.setInt(5, 0);

			if (input_man != null)
				stmt.setInt(6, input_man.intValue());
			else
				stmt.setInt(6, 0);

			rslist = stmt.executeQuery();

			String subvalue = "";
			String totalvalue = "";
			if (rslist != null) {
				int icount = 0;
				if (rslist.next()) {
					subvalue = Utility.trimNull(rslist.getString("SUB_CODE"));
					sb = new StringBuffer(subvalue);
				}
			}
		} finally {
			if (rslist != null)
				rslist.close();

			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//	根据产品ID和币种ID查询开户银行
	public static String getbankName(Integer book_code, Integer serial_no)
			throws Exception {

		String listSql = "{call SP_QUERY_TSUBBANKINFO1021_BANKLIST (?)}";
		String sb = "";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		ResultSet rs = stmt.executeQuery();

		try {
			while (rs.next()) {
				Integer temp_serial_bo = new Integer(rs.getInt("SERIAL_NO"));
				if (temp_serial_bo.intValue() == serial_no.intValue()) {
					sb = rs.getString("BANK_NAME") + "$"
							+ rs.getString("BANK_ACCT");
					break;
				}

			}

		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb;
	}

	//合同处理标志
	public static String getDealFlagOptions(String value) throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TDICTPARAM(1127)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				String typevalue = rs.getString("TYPE_VALUE");

				if (typevalue != null) {
					appendOptions(sb, rs.getString("TYPE_VALUE"), rs
							.getString("TYPE_CONTENT"), value);
				}
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	/**
	 * add by tsg 2008-01-15 对获取1205,1206选项
	 *
	 */
	public static String getAllDictParamOptions1(Integer value)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 1205, "1205_营业收入", value);
		appendOptions(sb, 1206, "1206_费用支出", value);
		return sb.toString();
	}

	//	得到固定计息日标志
	public static String getFixInterestDayOptions(int flag) throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "非固定日", flag);
		appendOptions(sb, 1, "3月21日", flag);
		appendOptions(sb, 2, "6月21日", flag);
		appendOptions(sb, 3, "9月21日", flag);
		appendOptions(sb, 4, "12月21日", flag);
		return sb.toString();
	}

	//	得到固定计息日标志
	public static String getFixInterestDayOptionsName(int flag)
			throws Exception {
		String day = "";
		if (flag == 1)
			day = "3月21日";
		else if (flag == 2)
			day = "6月21日";
		else if (flag == 3)
			day = "9月21日";
		else if (flag == 4)
			day = "12月21日";
		else
			day = "非固定日";

		return day;
	}

	//	利息类别
	public static String getSubjectBondTypeOptions(String value)
			throws Exception {
		return getDictParamOptions(2005, value);
	}

	/**
	 * add by tsg 2008-01-15 对获取1205,1206选项
	 *
	 */

	//业务编号
	public static String getBusiid1Options(String value) throws Exception {
		return getDictParamOptions(1205, value);
	}

	public static String getBusiid2Options(String value) throws Exception {
		return getDictParamOptions(1206, value);
	}

	//业务类别1收入2费用
	public static String getBusiTypeOptions(Integer flag) throws Exception {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 0, "收入", flag);
		appendOptions(sb, 1, "费用", flag);
		return sb.toString();
	}

	public static String getChequeyBankName(Integer book_code, String value,
			Integer serial_no) throws Exception {
		String listSql = "{call SP_QUERY_TSUBBANKINFO1021(?,?,?,?,?)}";
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setString(2, value);
		stmt.setString(3, null);
		stmt.setString(4, null);
		stmt.setInt(5, 0);
		ResultSet rs = stmt.executeQuery();

		try {
			while (rs.next())
				appendOptions(sb, rs.getInt("serial_no"), rs
						.getString("bank_acct")
						+ "_" + rs.getString("bank_name"), serial_no);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	public static String getChequeyBankNameOnUserCheckDel(Integer book_code,
			String value, Integer serial_no) throws Exception {
		String listSql = "{call SP_QUERY_TSUBBANKINFO1021(?,?,?,?,?)}";
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setString(2, null);
		stmt.setString(3, null);
		stmt.setString(4, null);
		stmt.setInt(5, 0);
		ResultSet rs = stmt.executeQuery();

		try {
			while (rs.next())
				appendOptions(sb, rs.getInt("serial_no"), rs
						.getString("bank_acct")
						+ "_" + rs.getString("bank_name"), serial_no);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	public static String getChequeTypeOptions(String value) throws Exception {
		return getDictParamOptions(2104, value);
	}

	//读取对账标志
	public static String getDZFlagOptions(Integer flag) throws Exception {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 1, "未对帐", flag);
		appendOptions(sb, 2, "已对平", flag);
		appendOptions(sb, 3, "未对平", flag); //add duanpeng 20070110
		return sb.toString();
	}

	public static String getCheque_noOption(Integer book_code,
			Integer serial_no, Integer pay_sbf_serial_no) throws Exception {
		String listSql = "{call SP_QUERY_TCHEQUEYE(?,?,?,?,?,?,?,?)}";
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setInt(2, 0);
		stmt.setString(3, "");
		if (pay_sbf_serial_no != null)
			stmt.setInt(4, pay_sbf_serial_no.intValue());
		else
			stmt.setInt(4, 0);

		stmt.setString(5, "");
		stmt.setInt(6, 0);
		stmt.setInt(7, 0);
		stmt.setInt(8, 0);
		ResultSet rs = stmt.executeQuery();

		try {
			while (rs.next())
				appendOptions(sb, rs.getInt("SERIAL_NO"), rs
						.getInt("SERIAL_NO")
						+ "-"
						+ rs.getString("CHEQUE_TYPE_NAME")
						+ "-起始/结束号码："
						+ rs.getString("START_NO")
						+ "/"
						+ rs.getString("END_NO"), serial_no);
		} finally {

			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();

	}

	//	明细帐查询时，科目起始列表
	public static String getSubjectDetailFirstSubCode(int book_code,
			String star_sub_code, String end_sub_code, String defultValue,
			Integer start_date, Integer end_date, Integer input_man)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		String listSql = "{call SP_QUERY_SUBLIST(?,?,?,?,?,?)}";
		ResultSet rslist = null;
		Connection conn = null;
		CallableStatement stmt = null;

		try {
			conn = CrmDBManager.getConnection();
			stmt = conn.prepareCall(listSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1, book_code);

			stmt.setString(2, star_sub_code);
			stmt.setString(3, end_sub_code);

			if (start_date != null)
				stmt.setInt(4, start_date.intValue());
			else
				stmt.setInt(4, 0);

			if (end_date != null)
				stmt.setInt(5, end_date.intValue());
			else
				stmt.setInt(5, 0);

			if (input_man != null)
				stmt.setInt(6, input_man.intValue());
			else
				stmt.setInt(6, 0);

			rslist = stmt.executeQuery();

			String subvalue = "";
			String totalvalue = "";
			if (rslist != null) {
				int icount = 0;
				if (rslist.next()) {
					subvalue = Utility.trimNull(rslist.getString("SUB_CODE"));
					sb = new StringBuffer(subvalue);
				}
			}
		} finally {
			if (rslist != null)
				rslist.close();

			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//明细帐查询时，科目起始列表
	public static String getDetailSubjectOptions(int book_code,
			String star_sub_code, String end_sub_code, String defultValue,
			Integer start_date, Integer end_date, Integer input_man)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		String listSql = "{call SP_QUERY_SUBLIST(?,?,?,?,?,?)}";
		ResultSet rslist = null;
		Connection conn = null;
		CallableStatement stmt = null;

		try {
			conn = CrmDBManager.getConnection();
			stmt = conn.prepareCall(listSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1, book_code);

			stmt.setString(2, star_sub_code);
			stmt.setString(3, end_sub_code);

			if (start_date != null)
				stmt.setInt(4, start_date.intValue());
			else
				stmt.setInt(4, 0);

			if (end_date != null)
				stmt.setInt(5, end_date.intValue());
			else
				stmt.setInt(5, 0);

			if (input_man != null)
				stmt.setInt(6, input_man.intValue());
			else
				stmt.setInt(6, 0);

			rslist = stmt.executeQuery();

			String subvalue = "";
			String totalvalue = "";
			if (rslist != null) {
				int icount = 0;
				while (rslist.next()) {
					subvalue = Utility.trimNull(rslist.getString("SUB_CODE"));
					totalvalue = Utility.trimNull(rslist.getString("SUB_CODE"))
							+ "-"
							+ Utility.trimNull(rslist.getString("SUB_NAME"));
					appendOptions(sb, subvalue, totalvalue, defultValue);
				}
			}
		} finally {
			if (rslist != null)
				rslist.close();

			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	public static String getJstypeOptions(String value) throws Exception {
		return getDictParamOptions(2103, value);
	}

	/**
	 * add by tsg 20080711
	 *
	 * 日期格式转换 如2001-01-01转换成20010101
	 */
	public static String conversionOfDateFormat(String strDate) {
		String newStr = "";
		char[] s = strDate.toCharArray();
		for (int i = 0; i < s.length; i++) {
			if (s[i] != '-')
				newStr += s[i];
		}
		return newStr;
	}

	//	固定资产---折旧方法
	public static String getDeprMethodOptions(String value) throws Exception {
		return getDictParamOptions(1120, value);
	}

	//	审核标志0未审核1已审核
	public static String getVaryFlagOptions(Integer flag, Integer serial_no) {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 1, "增加", flag);
		appendOptions(sb, 2, "减少", flag);
		if (serial_no.intValue() > 0)
			appendOptions(sb, 3, "变动", flag);
		return sb.toString();
	}

	public static String getVaryFlag(Integer flag) {
		if (flag == null)
			return null;
		switch (flag.intValue()) {
		case 1:
			return "增加";
		case 2:
			return "减少";
		case 3:
			return "变动";
		default:
			return "未知";
		}
	}

	//	审核标志0未审核1已审核
	public static String getAccountFlagOptions(Integer flag) {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 2, "全部", flag);
		appendOptions(sb, 0, "不进行会计核算", flag);
		appendOptions(sb, 1, "进行会计核算", flag);
		return sb.toString();
	}

	//	固定资产---折旧方法
	public static String getDeprMethodOptions1(String value) throws Exception {
		return getDictParamOptions(1913, value);
	}

	//　获取资产类别 tsg 20080724
	public static String getAssetTypeOptions(Integer book_code,
			String asset_type) throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rslist = stmt.executeQuery("{call SP_QUERY_FA_ASSETTYPE("
				+ book_code + ",'',0,'')}");
		try {
			while (rslist.next()) {
				appendOptions(sb, rslist.getString("TYPE_NO"), rslist
						.getString("TYPE_NAME"), asset_type);
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//	审核标志0未审核1已审核
	public static String getReferFlagOptions(Integer flag) {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 2, "全部", flag);
		appendOptions(sb, 0, "未提交", flag);
		appendOptions(sb, 1, "已提交", flag);
		return sb.toString();
	}

	//	审核标志0未审核1已审核
	public static String getcheckFlagOptions(Integer flag) {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 3, "全部", flag);
		appendOptions(sb, 0, "未审核", flag);
		appendOptions(sb, 1, "审核通过", flag);
		appendOptions(sb, 2, "审核不通过", flag);
		return sb.toString();
	}

	// 叶子部门列表
	public static String getDepartNameByDepartId(Integer depart_id)
			throws Exception {
		String sb = "";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall("{call SP_QUERY_TDEPARTMENT("
				+ depart_id + ",0,0)}", ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				sb = rslist.getString("depart_name");
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb;
	}

	public static String getAssetOptions(Integer book_code, String asset_no,
			String asset_name, Integer card_id) throws Exception {
		String listSql = "{call SP_QUERY_FA_FIXEDASSET_LIST(?,?,?)}";
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setString(2, asset_no);
		stmt.setString(3, asset_name);
		ResultSet rs = stmt.executeQuery();

		try {
			while (rs.next())
				appendOptions(sb, rs.getInt("card_id"), rs
						.getString("asset_no")
						+ "_" + rs.getString("asset_name"), card_id);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	public static String getOperatorOptionsByRoleId(Integer role_id,
			Integer op_code) throws Exception {
		if (role_id.intValue() == 0)
			return "";
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TOPERATOR (?,?,?,?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, 0);
		stmt.setInt(2, 0);
		stmt.setInt(3, role_id.intValue());
		stmt.setString(4, "");
		stmt.setInt(5, 1);

		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				appendOptions(sb, rslist.getInt("OP_CODE"), rslist
						.getString("OP_NAME"), op_code);
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	public static List getOperatorListByRoleId(Integer role_id)
			throws Exception {
		if (role_id.intValue() == 0)
			return null;
		List list = new ArrayList();
		Map map = null;
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TOPERATOR (?,?,?,?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, 0);
		stmt.setInt(2, 0);
		stmt.setInt(3, role_id.intValue());
		stmt.setString(4, "");
		stmt.setInt(5, 1);

		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				map = new HashMap();
				map.put("MOBILE", rslist.getString("MOBILE"));
				map.put("OP_NAME", rslist.getString("OP_NAME"));
				map.put("OP_CODE", rslist.getInt("OP_CODE") + "");
				list.add(map);
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return list;
	}
	
	public static int getPreproductBySerno(int serial_no,Integer product_id,Integer inputMan)
			throws Exception {
		int serial =0;
		Map map = null;
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TPRECONTRACT (?,?,?,?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, serial_no);
		stmt.setInt(2, product_id.intValue());
		stmt.setString(3, "");
		stmt.setString(4, "");
		stmt.setInt(5, inputMan.intValue());
		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				serial = rslist.getInt("SERIAL_NO");
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return serial;
	}
	

	public static String getOperatorOptionsByDepart(Integer depart_id,
			Integer op_code) throws Exception {
		if (depart_id.intValue() == 0)
			return "";
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TOPERATOR (?,?,?,?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, 0);
		stmt.setInt(2, depart_id.intValue());
		stmt.setInt(3, 0);
		stmt.setString(4, "");
		stmt.setInt(5, 1);

		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				appendOptions(sb, rslist.getInt("OP_CODE"), rslist
						.getString("OP_NAME"), op_code);
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	// 叶子部门列表
	//获取有折旧信息的部门
	public static String getDeprDepartName(Integer book_code, Integer depart_id)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall("{call SP_QUERY_FA_SUBJECT("
				+ book_code + ",'')}", ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				//if (rslist.getInt("bottom_flag") == 1)
				Argument.appendOptions(sb, rslist.getInt("DEPART_ID"), rslist
						.getString("DEPART_NAME"), depart_id);
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//	计息周期
	public static String getJxPeriodOptions(Integer flag) throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 1, "月", flag);
		appendOptions(sb, 3, "季", flag);
		appendOptions(sb, 6, "半年", flag);
		appendOptions(sb, 12, "年", flag);
		return sb.toString();
	}

	//	固定资产折旧审核生成凭证方式
	public static String getVoucherTypeOptions(Integer flag) throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 1, "按资产生成凭证", flag);
		appendOptions(sb, 2, "按部门生成凭证", flag);
		return sb.toString();
	}

	//	获取列标志
	public static String getColsTypeOptions(Integer col_flag) throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "所有", col_flag);
		appendOptions(sb, 1, "左列", col_flag);
		appendOptions(sb, 2, "右列", col_flag);
		return sb.toString();
	}

	//	获取现金流量流入流出标志
	public static String getCashInOutOptions(Integer in_Out) throws Exception {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 1, "流入", in_Out);
		appendOptions(sb, 2, "流出", in_Out);
		appendOptions(sb, 3, "其他", in_Out);
		return sb.toString();
	}

	/**
	 * lzhd 2007/12/10 update by tsg 2008-01-08 改成通用的
	 * 根据type_id中获取从TINTEGERPARAM中获取的下拉列表
	 */

	public static String getTableOptions1(int type_id, Integer defaultValue)
			throws Exception {
		StringBuffer sb = newStringBuffer();

		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TINTEGERPARAM (?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, type_id);
		//对应表下拉框应该从TINTEGERPARAM参数表统一获取, TYPE_ID = 1101
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {

				appendOptions(sb, rs.getInt("TYPE_VALUE"), rs
						.getString("CONTENT"), defaultValue);
			}

		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//根据type_value读取计息周期名称
	public static String getJx_periodName(int type_value) throws Exception {
		if (type_value == 0)
			return "";
		String listSql = "{call SP_QUERY_TINTEGERPARAM(2002,?)}";
		String ret = "";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, type_value);
		ResultSet rs = stmt.executeQuery();
		try {
			if (rs.next())
				ret = rs.getString("CONTENT");
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return ret;
	}

	/**
	 * tsg 2008-11-5
	 *
	 * 根据type_id和type_value中获取从TINTEGERPARAM中获取content值
	 */

	public static String getValueOfTIntegerParam(int type_id, int type_value)
			throws Exception {
		String content = "";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TINTEGERPARAM_VALUE (?,?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, type_id);
		stmt.setInt(2, type_value);
		stmt.registerOutParameter(3, java.sql.Types.VARCHAR);
		try {
			stmt.execute();
			content = (String) stmt.getObject(3);
		} finally {
			stmt.close();
			conn.close();
		}

		if (content == null)
			return "";
		else
			return content;
	}

	/**
	 * tsg 2008-11-5
	 *
	 * 根据type_value中获取从TDICTPARAM中获取content值
	 */

	public static String getValueOfTDictParam(String type_value)
			throws Exception {
		String content = "";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TDICTPARAM_VALUE  (?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setString(1, type_value);
		stmt.registerOutParameter(2, java.sql.Types.VARCHAR);
		try {
			stmt.execute();
			content = (String) stmt.getObject(2);
		} finally {
			stmt.close();
			conn.close();
		}

		if (content == null)
			return "";
		else
			return content;
	}

	/**
	 * add by tsg 2007-11-12
	 */
	public static String getItemOptions1(Integer book_code, Integer item_id,
			String item_status, Integer op_code, String item_code)
			throws Exception {
		String listSql = "{call SP_QUERY_TITEM_NOPRO (?,?,?,?,?,?,?,?,?,?)}";

		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setString(2, item_code);
		stmt.setInt(3, 0);
		stmt.setInt(4, 0);
		stmt.setString(5, "");
		stmt.setString(6, "");
		stmt.setString(7, "");
		if (op_code == null)
			stmt.setInt(8, 0);
		else
			stmt.setInt(8, op_code.intValue());
		stmt.setString(9, "");
		stmt.setString(10, Utility.trimNull(item_status));
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				appendOptions(sb, rs.getInt("ITEM_ID"), rs
						.getString("ITEM_NAME"), item_id);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	public static String getOPERNAME(Integer book_code, Integer op_code)
			throws Exception {
		String listSql = "{call SP_QUERY_TOPERATOR (?)}";
		String ret = "";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, op_code.intValue());
		ResultSet rs = stmt.executeQuery();
		try {
			if (rs.next())
				ret = rs.getString("OP_NAME");
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return ret;
	}

	/**
	 * 2008-10-29 YZJ
	 *
	 * @param value
	 * @param sub_code
	 * @return
	 * @throws Exception
	 */
	public static String getCash_type(String value, String sub_code)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=\"\">  </option>");
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TDICTPARAM_FZ_1('',1,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		if (sub_code.equals(""))
			stmt.setString(1, null);
		else
			stmt.setString(1, sub_code);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				Argument.appendOptions(sb, rs.getString("FZ_TYPE"), rs
						.getString("FZ_TYPE_NAME"), value);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	/*
	 * flag 0 -- 查询全部 1 -- 推介期 2 -- 正常期 3 -- 已结束产品 10 -- 除不成立状态外的全部产品 11 --
	 * 推介及正常期产品 12 -- 推介期已经审核的资金信托产品 13 -- 除不成立状态外的财产信托产品 14 -- 已经财务成立审核的集合资金信托
	 * 15 -- 正常期及结束期产品 16 -- 正常期的单一产品和推介期的集合产品 17 -- 推介及正常期产品的资金信托产品 18 --
	 * 财产信托推介期的产品 19 -- 可以结束处理的产品
	 */
	public static String getProductListOptions(Integer book_code,
			Integer product_id, String product_code, Integer op_code, int flag)
			throws Exception {
		String listSql = "{call SP_QUERY_TPRODUCT_LIST (?,?,?,?)}";

		StringBuffer sb = new StringBuffer();

		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue()); 

		stmt.setString(2, product_code);
		stmt.setInt(3, flag);
		if (op_code == null)
			stmt.setInt(4, 0);
		else
			stmt.setInt(4, op_code.intValue());
		ResultSet rs = stmt.executeQuery();
		try {
			appendOptions(sb, 0, "全部产品", product_id);
			while (rs.next()) {
				appendOptions(sb, rs.getInt("PRODUCT_ID"), rs
						.getString("PRODUCT_CODE")
						+ "-" + rs.getString("PRODUCT_NAME"), product_id);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}
	
	/*
	 * sale_status: 产品可销售状态 1默认值不可销售 2可销售
	 * product_status: '0'所有预约期、正常期的产品; 其他，可预约的产品
	 */
	public static String getProductListOptions2(Integer sale_status, String product_status, Integer input_man, String selected)
			throws Exception {
		String listSql = "{call SP_QUERY_TPRODUCT_PRE (?,?,?)}";

		StringBuffer sb = new StringBuffer();

		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, sale_status.intValue());
		stmt.setString(2, product_status);
		stmt.setInt(3, input_man.intValue());
		
		ResultSet rs = stmt.executeQuery();
		try {
			//appendOptions(sb, "0-0", "全部产品", selected);
			while (rs.next()) {
				String text = "";
				String product_code = rs.getString("PRODUCT_CODE");
				if ("".equals(product_code)) {
					text = rs.getString("PRODUCT_NAME");
				} else {
					text = product_code + "-" +rs.getString("PRODUCT_NAME");
				}
					
				appendOptions(sb, rs.getInt("PREPRODUCT_ID")+"-"+rs.getInt("PRODUCT_ID"), text, selected);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	public static String getProductFlag(Integer product_id, String flag_name)throws Exception {
		String flag = "";
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall("{CALL SP_QUERY_TPRODUCT_ID(?)}");
		stmt.setInt(1, product_id.intValue());
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			flag = rs.getString(flag_name.toUpperCase());
		}

		rs.close();
		stmt.close();
		conn.close();

		return flag;
	}

	/**
	 * 从TINTEGERPARAM表中获取CONTENT
	 */
	public static String getTintegerContent(Integer type_id, Integer type_value)
			throws Exception {
		String returnName = "";
		Connection conn = IntrustDBManager.getConnection();
		Statement stmt = conn.createStatement();
		String sql = "SELECT * FROM TINTEGERPARAM WHERE TYPE_ID=" + type_id
				+ " AND TYPE_VALUE=" + type_value;
		ResultSet rslist = stmt.executeQuery(sql);
		try {
			while (rslist.next()) {
				returnName = Utility.trimNull(rslist.getString("CONTENT"));
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return returnName;
	}

	public static String getCustomerFlag(Integer cust_id, Integer input_man,String flag_name)throws Exception{
		String flag = "";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall("{call SP_QUERY_TCustomers_LOAD (?,?)}");
		stmt.setInt(1, cust_id.intValue());
		stmt.setInt(2, input_man.intValue());
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			flag = rs.getString(flag_name.toUpperCase());
		}

		rs.close();
		stmt.close();
		conn.close();

		return flag;
	}
	
	public static List getCustmoerFlags(Integer cust_id, Integer input_man) throws Exception {
		List list = new ArrayList();
		Connection conn =  CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TCustomers_LOAD (?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, cust_id.intValue());
		stmt.setInt(2, input_man.intValue());
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				Map map = new HashMap();
				
					map.put("CUST_NAME", rs.getObject("CUST_NAME"));
					map.put("CUST_TYPE", rs.getObject("CUST_TYPE"));
					map.put("VOC_TYPE",rs.getObject("VOC_TYPE"));
					map.put("CARD_TYPE",rs.getObject("CARD_TYPE"));
					map.put("CARD_ID",rs.getObject("CARD_ID"));
					map.put("GOV_PROV_REGIONAL",rs.getObject("GOV_PROV_REGIONAL"));
					map.put("GOV_REGIONAL",rs.getObject("GOV_REGIONAL"));
					list.add(map);
			
			}
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (conn!=null) conn.close();
		}
		return list;
	}
	
	

	//查询贷款申请记录的下拉列表
	/**
	 * SP_QUERY_TPRECOMMCREDIT_LIST
	 *
	 * @IN_BOOK_CODE TINYINT,
	 * @IN_CHECK_FLAG TINYINT =0, --审核标志：1未审批2审批中3审批通过4审批不通过5已签订合同
	 * @IN_SERIAL_NO INT =0,
	 * @IN_INPUT_MAN INT = 0
	 *
	 */
	public static String getPreCommCreditListOptions(Integer book_code,
			Integer serial_no, int check_flag, Integer input_man)
			throws Exception {
		String listSql = "{call SP_QUERY_TPRECOMMCREDIT_LIST (?,?,?,?)}";
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setInt(2, check_flag);
		stmt.setInt(3, 0);
		stmt.setInt(4, input_man.intValue());
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				appendOptions(sb, rs.getInt("SERIAL_NO"), "- "
						+ rs.getString("APPLY_NO") + "-"
						+ Utility.trimNull(rs.getString("PRODUCT_NAME")) + "-"
						+ rs.getString("CUST_NAME"), serial_no);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//诉讼案件类别
	public static String getSs_typeOptions(String value) throws Exception {
		return getDictParamOptions(2204, value);
	}

	//诉讼原业务类别
	public static String getOldBusi_idOptions(String value) throws Exception {
		return getDictParamOptions(2203, value);
	}

	//	诉讼阶段 2205 wangc 20060829
	public static String getSs_statusOptions(String value) throws Exception {
		return getDictParamOptions(2205, value);
	}

	//执行阶段 2206 wangc 20060829
	public static String getZx_statusOptions(String value) throws Exception {
		return getDictParamOptions(2206, value);
	}

	//	诉讼列表 20060901 wangc
	public static String getLawsuitOptions(Integer book_code,
			Integer df_serial_no, Integer input_man) throws Exception {
		String listSql = "{call SP_QUERY_TINVESTSSINFO_LIST(?,?)}";
		StringBuffer sb = newStringBuffer();
		Connection conn = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = CrmDBManager.getConnection();
			stmt = conn.prepareCall(listSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1, book_code.intValue());
			stmt.setInt(2, input_man.intValue());
			rs = stmt.executeQuery();
			while (rs.next())
				appendOptions(sb, rs.getInt("serial_no"), rs
						.getString("contract_sub_bh")
						+ "-"
						+ rs.getString("ss_status_name")
						+ "-"
						+ rs.getString("zx_status_name")
						+ "-"
						+ rs.getString("ss_bh"), df_serial_no);
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
		return sb.toString();
	}

	public static String getOldBusi_idName(String value) throws Exception {
		return getDictParamName(2203, value);
	}

	public static String getSs_typeName(String value) throws Exception {
		return getDictParamName(2204, value);
	}

	public static String getZx_statusName(String value) throws Exception {
		return getDictParamName(2206, value);
	}

	public static String getSs_statusName(String value) throws Exception {
		return getDictParamName(2205, value);
	}

	public static String getProductName(Integer product_id) throws Exception {
		String ret = "";
		if (product_id == null)
			return null;
		Connection conn = IntrustDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("{call SP_QUERY_TPRODUCT_ID ("
				+ product_id + ")}");
		try {
			if (rs.next())
				ret = rs.getString("PRODUCT_NAME");
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return ret;
	}

	public static String getProductJC(Integer product_id) throws Exception {
		String ret = "";
		if (product_id == null)
			return null;
		Connection conn = IntrustDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("{call SP_QUERY_TPRODUCT_ID ("
				+ product_id + ")}");
		try {
			if (rs.next())
				ret = rs.getString("PRODUCT_JC");
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return ret;
	}
	
	public static String getContractSubBh(Integer product_id,String contract_bh) throws Exception {
		String ret = "";
		if (product_id == null || contract_bh==null)
			return ret;
		contract_bh = contract_bh.replaceFirst("'", "''");
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = IntrustDBManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT CONTRACT_SUB_BH FROM TCONTRACT WHERE PRODUCT_ID="
					+ product_id + " AND CONTRACT_BH='"+contract_bh+"'");
			
			if (rs.next())
				ret = rs.getString("CONTRACT_SUB_BH");			
			
		} finally {
			if (rs!=null)
				rs.close();
			if (stmt!=null)
				stmt.close();
			if (conn!=null)
				conn.close();
		}
		
		return ret;
	}
	
	public static String getDeal_flagName(String value) throws Exception {
		return getDictParamName(1127, value);
	}

	public static String getStatusName(String value) throws Exception {
		return getDictParamName(1136, value);
	}

	public static java.math.BigDecimal getCust_balance(Integer bookcode,
			Integer cust_id, Integer finance_date, Integer item_type,
			Integer item_id, int item_flag) throws Exception {
		String listSql = "{call SP_QUERY_TCUSTFINANCEINFO(?,?,?,?,?)}";
		java.math.BigDecimal ret = new java.math.BigDecimal(0);
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, bookcode.intValue());
		stmt.setInt(2, cust_id.intValue());
		stmt.setInt(3, finance_date.intValue());
		stmt.setInt(4, item_type.intValue());
		stmt.setInt(5, item_id.intValue());
		ResultSet rs = stmt.executeQuery();
		try {
			if (rs.next())
				if (item_flag == 1) {
					ret = rs.getBigDecimal("ITEM_BALANCE1");
				} else if (item_flag == 2) {
					ret = rs.getBigDecimal("ITEM_BALANCE2");
				}

		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return ret;
	}

	/**
	 * add by tsg 2008-12-15 根据user_Id获取用户的logo图片信息
	 */
	public static String[] getUserLogoInfo(int user_id) throws Exception {
		String listSql = "{call SP_QUERY_TUSERINFO(?)}";
		//String image_path="";
		String logo = "";
		String top_image = "";
		String[] s = new String[2];
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, user_id);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				logo = Utility.trimNull(rs.getString("INDEX_IMAGE_TOP"));
				top_image = Utility.trimNull(rs.getString("INDEX_IMAGE_MID"));
				s[0] = logo;
				s[1] = top_image;
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return s;
	}

	//性别 2008-12-18 ADD YZJ
	public static String getSexOptions(Integer flag) throws Exception {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 1, "男", flag);
		appendOptions(sb, 2, "女", flag);
		return sb.toString();
	}

	//查询已经财务审核的合同列表
	public static String getContractCheckOptions(Integer book_code,
			String cust_name, String contract_sub_bh, String defaulvalue)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		String listSql = "{call SP_QUERY_TCOMMCREDIT_HK_RECORD(?,?,?,?)}";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setString(2, cust_name);
		stmt.setString(3, contract_sub_bh);
		stmt.setString(4, "");
		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				Argument.appendOptions(sb, rslist.getString("CONTRACT_BH"),
						rslist.getString("CONTRACT_SUB_BH") + "_"
								+ rslist.getString("CUST_NAME"), defaulvalue);
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}
	
	
//	查询信息模板
	public static String[] getSmsContent(Integer autosendid)
			throws Exception {
		String temid = "";
		String content = "";
		String[] s = new String[2];
		String listSql = "{call SP_QUERY_TSMSTEMPLATES(?,?)}";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, 0);
		stmt.setInt(2, autosendid.intValue());
		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				temid = rslist.getString("TempID");
				content=rslist.getString("Content");
				s[0] = temid;
				s[1] = content;
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return s;
	}
	public static Integer[] getSmsifneedCust(Integer tempid,Integer autosendid)
			throws Exception {
		Integer needcust = new Integer(0);
		Integer needproduct = new Integer(0);
		Integer[] retvalue = new Integer[2];
		String listSql = "{call SP_QUERY_TSMSTEMPLATES(?,?)}";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1,tempid.intValue());
		stmt.setInt(2, autosendid.intValue());
		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				needcust = (Integer)rslist.getObject("NeedCust");
				needproduct =(Integer)rslist.getObject("NeedProduct");
				retvalue[0] = needcust;
				retvalue[1] = needproduct;
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return retvalue;
}

	public static String getDbfsTypeOptions(String value) throws Exception {
		return getDictParamOptions(1126, value);
	}

	//	查询担保性质的客户列表
	public static String getDbCustListOptions(Integer book_code,
			String busi_id, String dbfs_type, String contract_bh)
			throws Exception {
		String listSql = "{call SP_QUERY_TDKDBCUSTINFO(?,?,?,?)}";
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setString(2, busi_id);
		stmt.setString(3, dbfs_type);
		stmt.setString(4, contract_bh);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				appendOptions(sb, rs.getInt("CUST_ID"), rs
						.getString("CUST_NAME"), new Integer(0));
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//	根据产品ID和币种ID查询开户银行 (没有‘请选择’200-01-07 ADD YUZJ)
	public static String getbankOption5(Integer book_code, Integer serial_no)
			throws Exception {

		String listSql = "{call SP_QUERY_TSUBBANKINFO1021_BANKLIST (?)}";
		StringBuffer sb = new StringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		//stmt.setInt(2, product_id.intValue());
		//stmt.setString(3, currency_id);
		ResultSet rs = stmt.executeQuery();

		try {
			while (rs.next())
				appendOptions(sb, rs.getInt("serial_no"), rs
						.getString("bank_name")
						+ "-" + rs.getString("bank_acct"), serial_no);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	/**
	 * ADD BY TSG 2009-03-03 根据业务类别和对象类型获取服务列表 SP_QUERY_BOC_SERVICE
	 *
	 * @BOCOP_TYPE VARCHAR(16),
	 * @OBJECT_TYPE INT
	 */
	public static String getServicesOptions(int bocop_type, int object_type,
			Integer service_id) throws Exception {

		String listSql = "{call SP_QUERY_BOC_SERVICE(?,?)}";
		StringBuffer sb = new StringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, bocop_type);
		stmt.setInt(2, object_type);
		ResultSet rs = stmt.executeQuery();

		try {
			while (rs.next())
				appendOptions(sb, rs.getInt("SERVICE_ID"), rs
						.getString("SERVICE_NAME"), service_id);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	/**
	 * ADD BY TSG 2009-03-16 获取唯一标识符 SP_BACK_UNIQUEIDENTIFIER_VALUE
	 *
	 * @IN_BOOK_CODE INT
	 */
	public static String getNewId(Integer book_code) throws Exception {
		String group_id = "";
		if (book_code == null)
			return group_id;
		String listSql = "{call SP_BACK_UNIQUEIDENTIFIER_VALUE(?)}";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		ResultSet rs = stmt.executeQuery();

		try {
			while (rs.next())
				group_id = Utility.trimNull(rs.getString("GUID"));
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return group_id;
	}
	
	/**
	 * ADD BY zxj 2015-06-02 根据子产品id获取此产品名称
	 *
	 * @IN_SUB_PRODUCT_ID INT
	 */
	public static String getNamebysubpid(Integer sub_product_id) throws Exception {
		String list_name = "";
		if (sub_product_id == null || sub_product_id.intValue()==0)
			return list_name;
		Connection conn = IntrustDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery("SELECT * FROM TSUBPRODUCT WHERE SUB_PRODUCT_ID = "+sub_product_id);
			
			while (rs.next())
				list_name = Utility.trimNull(rs.getString("LIST_NAME"));
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return list_name;
	}

	/**
	 * ADD BY TSG 2009-03-11 获取银行简码信息
	 */

	public static String getBankCode(Integer bank_id) throws Exception {
		String bank_code = "";
		if (bank_id == null || bank_id.intValue() == 0)
			return bank_code;
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = null;

		try {
			rs = stmt.executeQuery("SELECT * FROM BC_BANK WHERE BANK_ID = "
					+ bank_id);

			while (rs.next()) {
				bank_code = Utility.trimNull(rs.getString("BANK_CODE"));
				break;
			}
		} catch (Exception e) {
			throw new BusiException("读取银行简码信息错误:" + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
		return bank_code;
	}

	/**
	 * ADD BY TSG 2009-03-18 根据服务ID获取FTPID信息
	 *
	 * @param service_id
	 *            服务ID
	 * @param deal_flag
	 *            1 上传，2 下载
	 */

	public static Integer getFtpIdByServiceId(Integer service_id, int deal_flag)
			throws Exception {
		Integer ftp_id = new Integer(0);
		if (service_id == null || service_id.intValue() == 0)
			return ftp_id;
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = null;
		String bank_code = "";
		try {
			rs = stmt
					.executeQuery("SELECT * FROM BC_SERVICE WHERE SERVICE_ID = "
							+ service_id);

			while (rs.next()) {
				if (deal_flag == 1)
					ftp_id = new Integer(Utility.parseInt(rs
							.getInt("BANK_FTP_ID"), 0));
				else if (deal_flag == 2)
					ftp_id = new Integer(Utility.parseInt(rs
							.getInt("WEB_FTP_ID"), 0));
				break;

			}
		} catch (Exception e) {
			throw new BusiException("读取银行简码信息错误:" + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
		return ftp_id;
	}

	/**
	 * ADD BY TSG 2009-03-18 根据服务ID获取银行ID信息
	 */

	public static Integer getBankIdByServiceId(Integer service_id)
			throws Exception {
		Integer bank_id = new Integer(0);
		if (service_id == null || service_id.intValue() == 0)
			return bank_id;
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = null;

		try {
			rs = stmt
					.executeQuery("SELECT * FROM BC_SERVICE WHERE SERVICE_ID = "
							+ service_id);

			while (rs.next()) {
				bank_id = new Integer(Utility.parseInt(rs.getInt("BANK_ID"), 0));
				break;
			}
		} catch (Exception e) {
			throw new BusiException("读取银行ID信息错误:" + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
		return bank_id;
	}

	/**
	 * ADD BY TSG 2009-03-23 读取文件信息
	 */

	public static String getFileInfo(String file_name) throws Exception {
		String file_info = " $ ";
		if (file_name == null || file_name.equals(""))
			return file_info;
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = null;

		try {
			rs = stmt
					.executeQuery("SELECT * FROM BC_SERVICE_TRACE WHERE FILES_NAME = '"
							+ Utility.trimNull(file_name) + "'");

			while (rs.next()) {
				file_info = Utility.trimNull(rs.getString("FILES_NAME"))
						+ " $ "
						+ String.valueOf(Utility.parseInt(rs
								.getInt("REQUEST_TIME"), 0));
				break;
			}
		} catch (Exception e) {
			throw new BusiException("读文件名信息信息错误:" + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}

		return file_info;
	}

	/**
	 * ADD BY TSG 2009-04-20 批扣银行列表
	 *
	 */
	public static String getBatchBankOptions(Integer default_bank_id)
			throws Exception {

		String listSql = "SELECT * FROM BC_BANK";
		StringBuffer sb = new StringBuffer();
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(listSql);
		try {
			while (rs.next())
				appendOptions(sb, rs.getInt("BANK_ID"), rs
						.getString("BANK_CODE")
						+ "_" + rs.getString("BANK_NAME"), default_bank_id);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	/**
	 * ADD BY TSG 2009-04-20 获取批扣对象类别列表
	 *
	 */
	public static String getObjectTypeOptions(Integer default_object_type)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "公司生成", default_object_type);
		appendOptions(sb, 1, "银行返回", default_object_type);
		return sb.toString();
	}

	/**
	 * ADD BY TSG 2009-04-20 获取处理类别列表--
	 * 0表示全部；1表示银行扣款成功，返回处理也成功；2表示银行扣款成功，返回处理有错且已解决；3表示银行扣款成功，返回处理有错且未解决；4银行批扣失败
	 *
	 */
	public static String getDealTypeOptions(Integer default_object_type)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "全部", default_object_type);
		appendOptions(sb, 1, "银行成功，返回处理也成功", default_object_type);
		appendOptions(sb, 2, "银行成功，返回处理有错且已解决", default_object_type);
		appendOptions(sb, 3, "银行成功，返回处理有错且未解决", default_object_type);
		appendOptions(sb, 4, "银行批扣失败", default_object_type);
		return sb.toString();
	}

	/**
	 * ADD BY TSG 2009-04-23 获取当前会计周期
	 */
	public static Integer getCurrentAcctMonth(Integer input_bookCode)
			throws Exception {
		Integer return_value = new Integer(0);
		String listSql = "SELECT * FROM TACCTBOOK WHERE BOOK_CODE = "
				+ input_bookCode;
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(listSql);
		while (rs.next()) {
			return_value = new Integer(Utility.parseInt(rs
					.getInt("CURRENT_MONTH"), 0));
			break;
		}
		rs.close();
		stmt.close();
		conn.close();
		return return_value;
	}

	/**
	 * ADD BY TSG 2009-05-05 获取银行对应的科目代码
	 */
	public static String getBankSubCode(Integer book_code, Integer serial_no)
			throws Exception {
		String listSql = "{call SP_QUERY_TSUBBANKINFO1021(?,?,?,?,?)}";
		String sub_code = "";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setString(2, "");
		stmt.setString(3, null);
		stmt.setString(4, null);
		if (serial_no == null)
			stmt.setInt(5, 0);
		else
			stmt.setInt(5, serial_no.intValue());
		ResultSet rs = stmt.executeQuery();

		try {
			while (rs.next())
				sub_code = Utility.trimNull(rs.getString("SUB_CODE"));

		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sub_code;
	}

	/**
	 * ADD BY TSG 2009-05-05 获取银行id
	 */
	public static Integer getBankSerialNoBySubCode(Integer book_code,
			String sub_code) throws Exception {
		String listSql = "{call SP_QUERY_TSUBBANKINFO1021(?,?,?,?,?)}";
		Integer serial_no = new Integer(0);
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setString(2, sub_code);
		stmt.setString(3, null);
		stmt.setString(4, null);
		stmt.setInt(5, 0);
		ResultSet rs = stmt.executeQuery();

		try {
			while (rs.next())
				serial_no = Utility.parseInt(Utility.trimNull(rs
						.getString("SERIAL_NO")), new Integer(0));
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return serial_no;
	}

	/**
	 * ADD BY TSG 2009-05-21 业务类别
	 */
	public static String getBusiTypeOptions1(Integer flag) throws Exception {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 1, "用款", flag);
		appendOptions(sb, 2, "还款", flag);
		return sb.toString();
	}

	/**
	 * ADD BY TSG 2009-05-21 获取担保方式名称
	 */
	public static String getDbfsName(int flag) {
		String dbfs_name = "";
		if (flag == 0)
			dbfs_name = "信用";
		else if (flag == 1)
			dbfs_name = "保证";
		else if (flag == 2)
			dbfs_name = "抵押";
		else if (flag == 3)
			dbfs_name = "质押";

		return dbfs_name;

	}

	/**
	 * ADD BY TSG 2009-05-21 获取交易代码信息
	 */
	public static String getTradeCodeInfo(String code) {
		String code_name = "";
		if (code.equals("010001"))
			code_name = "基本信息建立";
		else if (code.equals("010002"))
			code_name = "基本信息查询";
		else if (code.equals("010003"))
			code_name = "基本信息修改";
		else if (code.equals("010004"))
			code_name = "生效/失效";
		else if (code.equals("010005"))
			code_name = "资金构成信息建立";
		else if (code.equals("010006"))
			code_name = "资金构成信息撤销";
		else if (code.equals("010007"))
			code_name = "资金构成信息查询";
		else if (code.equals("010008"))
			code_name = "资金构成信息修改";
		else if (code.equals("020001"))
			code_name = "贷款信息登记";
		else if (code.equals("020002"))
			code_name = "贷款信息登记反交易";
		else if (code.equals("020003"))
			code_name = "贷款信息查询";
		else if (code.equals("020004"))
			code_name = "贷款基本信息修改";
		else if (code.equals("020005"))
			code_name = "贷款归还信息登记";
		else if (code.equals("020006"))
			code_name = "贷款归还信息登记反交易";
		else if (code.equals("020007"))
			code_name = "贷款归还信息查询";
		else if (code.equals("020008"))
			code_name = "贷款归还信息修改";
		else if (code.equals("070003"))
			code_name = "实时批量贷款导入";
		else if (code.equals("070004"))
			code_name = "实时批量还款导入";

		return code_name;
	}

	/**
	 * add by tsg 2008-12-15 根据user_Id获取用户的logo图片信息
	 */
	public static String getFileName(Integer ywlb) throws Exception {
		String listSql = "{call SP_GET_MI_FILES_NAME(?)}";
		String file_name = "";
		String[] s = new String[2];
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, ywlb.intValue());
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				file_name = Utility.trimNull(rs.getString("FILES_NAME"));
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return file_name;
	}

	/**
	 * add by tsg 2009-06-05 信托版移植 得到所有财务指标类别 add by nizh 2009-03-13
	 */

	public static String getAllCwzbOptions(Integer value, String type_name)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		String listSql = "{call SP_QUERY_TDICTPARAM('2207',?)}";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setString(1, type_name);
		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				int type_value = rslist.getInt("TYPE_VALUE");
				Argument.appendOptions(sb, rslist.getInt("TYPE_VALUE"),
						type_value + "_" + rslist.getString("TYPE_CONTENT"),
						value);
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	public static String getDictParamString(Integer dict, int type)
			throws Exception {
		StringBuffer s = new StringBuffer();
		DictparamLocal dictParam = EJBFactory.getDictparam();
		DictparamVO vo = new DictparamVO();
		vo.setType_id(dict);
		List list = dictParam.listDictparamAll(vo);
		Map map = null;
		try {
			int i = 0;
			String split = "";
			for (int j = 0; j < list.size(); j++) {
				map = (Map) list.get(j);
				String type_value = Utility.trimNull(map.get("TYPE_VALUE"));
				String additive_value = Utility.trimNull(map
						.get("ADDITIVE_VALUE"));
				String type_content = Utility.trimNull(map.get("TYPE_CONTENT"));
				if (i != 0)
					split = "$";
				//如果传1返回获取参数值
				if (type == 1) {
					i++;
					s.append(split + type_value);
				}
				//如果传2返回获取参数名称
				else if (type == 2) {
					////营运资金不显示,无公式不显示
					//Utility.debug(dictParam.getType_content()+"--111--"+dictParam.getAdditive_value());
					if (!"22070205".equals(type_value)
							&& !"".equals(Utility.trimNull(additive_value))) {
						i++;
						s.append(split + type_content);
					}
				}

			}

		} finally {
			dictParam.remove();
		}
		return s.toString();
	}

	/**
	 * 合同信息汇总表-合同状态
	 *
	 */
	public static String getCreditStatusOptions(Integer statFlag)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 0, "正常", statFlag);
		appendOptions(sb, 1, "逾期", statFlag);
		appendOptions(sb, 6, "结清", statFlag);
		appendOptions(sb, 7, "撤销", statFlag);
		return sb.toString();
	}

	/**
	 * 合同信息汇总表-担保方式
	 *
	 */
	public static String getDBFSTypeOptions(String type) throws Exception {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, "112601", "信用", type);
		appendOptions(sb, "112602", "保证", type);
		appendOptions(sb, "112603", "质押", type);
		appendOptions(sb, "112604", "抵押", type);
		appendOptions(sb, "112605", "联保", type);
		return sb.toString();
	}

	/**
	 * 合同信息汇总表-合同状态
	 *
	 */
	public static String getCreditStatusName(Integer statFlag) {
		int flagValue = (statFlag == null ? -1 : statFlag.intValue());
		if (flagValue == 0)
			return "正常";
		else if (flagValue == 1)
			return "保证";
		else if (flagValue == 6)
			return "抵押";
		else if (flagValue == 7)
			return "质押";
		else
			return "";
	}

	/**
	 * 合同信息汇总表-担保方式
	 *
	 */
	public static String getDBFSTypeName(String type) {
		if (type.equals("112601"))
			return "信用";
		else if (type.equals("112602"))
			return "保证";
		else if (type.equals("112603"))
			return "质押";
		else if (type.equals("112604"))
			return "抵押";
		else if (type.equals("112605"))
			return "联保";
		else
			return "";
	}

	//产品状态
	public static String getProductAllStatusOptions(String value)
			throws Exception {
		StringBuffer sb = newStringBuffer();

		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TDICTPARAM(1102)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				String typevalue = rs.getString("TYPE_VALUE");

				if (typevalue != null) {
					appendOptions(sb, rs.getString("TYPE_VALUE"), rs
							.getString("TYPE_CONTENT"), value);
				}
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//产品发行方式
	public static String getOpenFlag(Integer flag) throws Exception {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 2, "封闭式", flag);
		appendOptions(sb, 1, "开放式", flag);
		return sb.toString();
	}

	//集合？单一
	public static String getIntrust_flag1(Integer flag) throws Exception {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 1, "单一", flag);
		appendOptions(sb, 2, "集合", flag);
		return sb.toString();
	}

	/**
	 * 诉讼标志
	 *
	 * @author dingyj
	 * @since 2009-11-23
	 * @param flag
	 * @return
	 */
	public static String getRepeatCustomerOptions(Integer flag) {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 1, "证件号码", flag);
		appendOptions(sb, 2, "客户名称", flag);
		return sb.toString();
	}

	/**
	 * 获得评分体系
	 *
	 * @author dingyj
	 * @since 2009-11-24
	 * @param gradeid
	 * @return
	 */
	public static String getCrmGradeIDOptions(Integer gradeid) throws Exception {
		StringBuffer sb = new StringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TGRADEINFO (?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, 0);
		ResultSet rs = stmt.executeQuery();
		appendOptions(sb, 0, "请选择", gradeid);
		try {
			while (rs.next())
				appendOptions(sb, rs.getInt("GRADE_ID"), rs
						.getString("GRADE_NAME"), gradeid);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}

		return sb.toString();
	}

	/**
	 * 获得评级体系名称
	 *
	 * @author dingyj
	 * @since 2009-11-24
	 * @param type_id
	 * @param type_value
	 * @return tintegerparamValue
	 */
	public static String getTintegerparamValue(Integer type_id,
			Integer type_value) throws Exception {
		String tintegerparamValue = null;
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TINTEGERPARAM_VALUE (?,?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		try {
			stmt.setInt(1, type_id.intValue());
			stmt.setInt(2, type_value.intValue());
			stmt.registerOutParameter(3, java.sql.Types.VARCHAR);
			stmt.execute();
			tintegerparamValue = stmt.getString(3);

		} finally {
			stmt.close();
			conn.close();
		}
		return tintegerparamValue;

	}

	/**
	 * 根据客户编号获得客户名称
	 *
	 * @author dingyj
	 * @since 2009-11-25
	 * @param cust_id
	 * @return
	 * @throws Exception
	 */
	public static String getCustomerName(Integer cust_id, Integer input_man)
			throws Exception {
		CustomerLocal local = EJBFactory.getCustomer();
		CustomerVO vo = new CustomerVO();
		vo.setCust_id(cust_id);
		vo.setInput_man(input_man);
		List list = local.listByControl(vo);
		Map map = new HashMap();
		if (list != null && list.size() > 0)
			map = (Map) list.get(0);
		String name = Utility.trimNull(map.get("CUST_NAME"));
		return name;
	}

	/**
	 * 客户评级审核标志 列表
	 *
	 * @author dingyj
	 * @since 2009-11-25
	 * @param flag
	 * @throws Exception
	 * @return sb
	 */
	public static String getCrmGradeCheckOptions(Integer flag) throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "--选择标志--", flag);
		appendOptions(sb, 1, "未审批", flag);
		appendOptions(sb, 2, "审批中", flag);
		appendOptions(sb, 3, "审批通过", flag);
		return sb.toString();
	}

	/**
	 * 获得系统参数
	 *
	 * @author dingyj
	 * @since 2009-11-26
	 * @param flag_type
	 * @throws Exception
	 * @return ret
	 */
	public static int getSyscontrolValue_1(String flag_type) throws Exception {
		String listSql = "{call SP_QUERY_TSYSCONTROL_VALUE (?,?)}";
		int ret = 0;
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		try {
			stmt.setString(1, flag_type);
			stmt.registerOutParameter(2, Types.INTEGER);
			stmt.execute();
			ret = stmt.getInt(2);
		} finally {
			stmt.close();
			conn.close();
		}
		return ret;
	}

	/**
	 * 获得联系方式
	 *
	 * @author dingyj
	 * @since 2009-11-26
	 * @param type_id
	 * @param value
	 * @throws Exception
	 * @return sb
	 */
	public static String getContactOption(Integer type_id, String value)
			throws Exception {
		Map map = new HashMap();
		StringBuffer sb = newStringBuffer();
		DictparamLocal local = EJBFactory.getDictparam();
		DictparamVO vo = new DictparamVO();
		vo.setType_id(type_id);
		List list = local.listDictparamAll(vo);
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			appendOptions(sb, map.get("TYPE_CONTENT").toString(), map.get(
					"TYPE_CONTENT").toString(), value);
		}
		return sb.toString();
	}

	public static String getALLTEL(String h_tel, String o_tel, String mobile,
			String bp) throws Exception {
		String alltel = "";
		if (!h_tel.equals(""))
			alltel = alltel + h_tel;
		else
			alltel = "";
		if (alltel.equals(""))
			alltel = o_tel;
		else {
			if (!o_tel.equals(""))
				alltel = alltel + "/" + o_tel;
		}
		if (alltel.equals(""))
			alltel = mobile;
		else {
			if (!mobile.equals(""))
				alltel = alltel + "/" + mobile;
		}
		if (alltel.equals(""))
			alltel = bp;
		else {
			if (!bp.equals(""))
				alltel = alltel + "/" + bp;
		}
		return alltel;
	}

	// 预约份数
	public static String getPreNumOptions(Integer pre_num) throws Exception {
		StringBuffer sb = newStringBuffer();
		for (int i = 1; i < 10; i++)
			appendOptions(sb, i, "" + i, pre_num);
		return sb.toString();
	}

	//预约方式
	public static String getPreTypeOptions(String value) throws Exception {
		return getDictParamOptions_intrust(1112, value);
	}

	/**
	 * 操作员编号与名称
	 *
	 * @param bottom_flag
	 * @return
	 * @throws Exception
	 */
	public static String getOperator_Value(Integer bottom_flag)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		Map map = null;
		TcustmanagersVO vo = new TcustmanagersVO();
		TcustmanagersLocal tcustmanagers_Bean = EJBFactory.getTcustmanagers();
		List list = tcustmanagers_Bean.operator_query(vo);

		sb.append("<option value=\"\">请选择</option>");
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			appendOptions(sb, Utility.parseInt(map.get("OP_CODE").toString(),
					new Integer(0)), Utility.trimNull(map.get("OP_NAME")),
					bottom_flag);
		}
		return sb.toString();
	}
	
	/**
	 * 操作员编号与名称
	 *
	 * @param bottom_flag
	 * @return
	 * @throws Exception
	 */
	public static String getOperator_Values(Integer bottom_flag)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		Map map = null;
		OperatorLocal operator = EJBFactory.getOperator();
		OperatorVO operatorVo=new OperatorVO();
		operatorVo.setStatus(new Integer(1));

		List operator_list = operator.listOpAll(operatorVo);

		sb.append("<option value=\"\">请选择</option>");
		for (int i = 0; i < operator_list.size(); i++) {
			map = (Map) operator_list.get(i);
			appendOptions(sb, Utility.parseInt(map.get("OP_CODE").toString(),
					new Integer(0)), Utility.trimNull(map.get("OP_NAME")),
					bottom_flag);
		}
		return sb.toString();
	}
	/**
	 * 客户经理编号与名称
	 *
	 * @param bottom_flag
	 * @return
	 * @throws Exception
	 */
	public static String getManager_Value(Integer bottom_flag) throws Exception {
		StringBuffer sb = new StringBuffer();
		Map map = null;
		TcustmanagersVO vo = new TcustmanagersVO();
		TcustmanagersLocal tcustmanagers_Bean = EJBFactory.getTcustmanagers();
		List list = tcustmanagers_Bean.list_query(vo);

		sb.append("<option value=\"\">请选择</option>");
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			appendOptions(sb, Utility.parseInt(Utility.trimNull(map
					.get("ManagerID")), new Integer(0)), Utility.trimNull(map
					.get("ManagerName")), bottom_flag);
		}
		return sb.toString();
	}
	
	/**
	 * 客户经理编号与名称
	 *
	 * @param bottom_flag
	 * @return
	 * @throws Exception
	 */
	public static String getManager_Names(String name) throws Exception {
		StringBuffer sb = new StringBuffer();
		Map map = null;
		TcustmanagersVO vo = new TcustmanagersVO();
		TcustmanagersLocal tcustmanagers_Bean = EJBFactory.getTcustmanagers();
		List list = tcustmanagers_Bean.list_query(vo);

		sb.append("<option value=\"\">请选择</option>");
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			appendOptions(sb, Utility.trimNull(map.get("ManagerName")), 
					Utility.trimNull(map.get("ManagerName")), name);
		}
		return sb.toString();
	}
	
	/**
	 * 团队编号与名称
	 *
	 * @param bottom_flag
	 * @return
	 * @throws Exception
	 */
	public static String getTeam1_Value(Integer bottom_flag) throws Exception {
		StringBuffer sb = new StringBuffer();
		Map map = null;
		TmanagerteamsVO vo = new TmanagerteamsVO();
		TmanagerteamsLocal tmanagerteams_Bean = EJBFactory.getTmanagerteams();
		List list = tmanagerteams_Bean.list_query(vo);

		sb.append("<option value=\"\">请选择</option>");
		sb.append("<option value=\"0\">无父团队</option>");
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			appendOptions(sb, Utility.parseInt(Utility.trimNull(map
					.get("TEAM_ID")), new Integer(0)), Utility.trimNull(map
					.get("TEAM_NAME")), bottom_flag);
		}
		return sb.toString();
	}
	
    /**
     * 营销部门
     * @return
     * @throws Exception
     */
	public static String getTeam_mark() throws Exception {
		StringBuffer sb = new StringBuffer();
		Map map = null;
		TmanagerteamsVO vo = new TmanagerteamsVO();
		TmanagerteamsLocal tmanagerteams_Bean = EJBFactory.getTmanagerteams();
		List list = tmanagerteams_Bean.list_query(vo);

		sb.append("<option value=\"\">请选择</option>");
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			int mark_flag = Utility.parseInt(Utility.trimNull(map.get("MARK_FLAG")), new Integer(0));
			if (mark_flag == 1) {
				appendOptions(sb, Utility.parseInt(Utility.trimNull(map.get("TEAM_ID")), new Integer(0)),
						Utility.trimNull(map.get("TEAM_NAME")), null);
			}
		}
		return sb.toString();
	}
	/**
	 * 按授权获取有权限的客户经理编号与名称
	 *
	 * @param bottom_flag
	 * @return
	 * @throws Exception
	 */
	public static String getManagerListAuth(Integer input_man,Integer service_man,Integer all_flag) throws Exception {
		StringBuffer sb = new StringBuffer();
		Map map = null;
		TcustmanagersVO vo = new TcustmanagersVO();
		TcustmanagersLocal tcustmanagers_Bean = EJBFactory.getTcustmanagers();
		List list = tcustmanagers_Bean.getManagerListAuth(input_man,all_flag);

		sb.append("<option value=\"\">请选择</option>");
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			appendOptions(sb, Utility.parseInt(Utility.trimNull(map
					.get("ManagerID")), new Integer(0)), Utility.trimNull(map
					.get("ManagerName")), service_man);
		}
		return sb.toString();
	}
	/**
	 * 获取团队列表
	 *
	 * @param bottom_flag
	 * @return
	 * @throws Exception
	 */
	public static String getManagerTeams(Integer team_id) throws Exception {
		StringBuffer sb = newStringBuffer();
		Map map = null;
		TmanagerteamsVO vo = new TmanagerteamsVO();
		TmanagerteamsLocal tmanagerteams_Bean = EJBFactory.getTmanagerteams();
		List list = tmanagerteams_Bean.list_query(vo);

		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			appendOptions(sb, Utility.parseInt(Utility.trimNull(map.get("TEAM_ID")),new Integer(0)), Utility.trimNull(map
					.get("TEAM_NAME")), team_id);
		}
		return sb.toString();
	}
	/**
	 * 客户经理编号与名称
	 *
	 * @param bottom_flag
	 * @return
	 * @throws Exception
	 */
	public static String getManager_Value_forTree(Integer bottom_flag)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		Map map = null;
		TcustmanagersVO vo = new TcustmanagersVO();
		TcustmanagersLocal tcustmanagers_Bean = EJBFactory.getTcustmanagers();
		List list = tcustmanagers_Bean.getManagerList_forTree();

		sb.append("<option value=\"\">请选择</option>");
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			appendOptions(sb, Utility.parseInt(Utility.trimNull(map
					.get("ManagerID")), new Integer(0)), Utility.trimNull(map
					.get("ManagerName")), bottom_flag);
		}
		return sb.toString();
	}

	/**
	 * 客户经理ID与名称
	 *
	 * @param bottom_flag
	 * @return
	 * @throws Exception
	 */
	public static String getManager_C_Value(Integer bottom_flag)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		Map map = null;
		CCVO vo = new CCVO();
		CallCenterLocal CallCenter_Bean = EJBFactory.getCallCenter();
		List list = CallCenter_Bean.l_query_cc_detail(vo);

		sb.append("<option value=\"\">请选择</option>");
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			appendOptions(sb, Utility.parseInt(map.get("ManagerID").toString(),
					new Integer(0)), Utility.trimNull(map.get("ManagerName")),
					bottom_flag);
		}
		return sb.toString();
	}

	//根据客户经理编号获得客户经理名称
	public static String getManager_Name(Integer managerID) throws Exception {
		//防止搜全部
		if (managerID.intValue() > 0) {
			Map map = null;
			TcustmanagersVO vo = new TcustmanagersVO();
			vo.setManagerid(managerID);
			TcustmanagersLocal tcustmanagers_Bean = EJBFactory
					.getTcustmanagers();
			List list = tcustmanagers_Bean.list_query(vo);

			if (list.size() > 0) {
				map = (Map) list.get(0);
				return (Utility.trimNull(map.get("ManagerName")));
			}
		}

		return "";

	}

	public static String getOperator_Name(Integer OperatorCode)
			throws Exception {
		Map map = null;
		TcustmanagersVO vo = new TcustmanagersVO();
		vo.setManagerid(OperatorCode);
		TcustmanagersLocal tcustmanagers_Bean = EJBFactory.getTcustmanagers();
		List list = tcustmanagers_Bean.operator_query(vo);

		if (list.size() > 0) {
			map = (Map) list.get(0);
			return (Utility.trimNull(map.get("OP_NAME")));
		}

		return "";
	}

	//	合同预约方式
	public static String getPreFlagOptions(Integer value) throws Exception {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 1, "已预约", value);
		appendOptions(sb, 2, "非预约", value);
		return sb.toString();
	}

	//	指定代定标志
	public static String getPreflag(Integer value) throws Exception {
		if (value.intValue() == 1)
			return "已预约";
		else if (value.intValue() == 2)
			return "非预约";
		else
			return "";
	}

	public static String getTouchCheckBoxOptions(int dict, String name,
			String[] value) throws Exception {
		StringBuffer sb = new StringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TDICTPARAM(?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, dict);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				appendCheckBoxOptions(sb, rs.getString("TYPE_VALUE"), rs
						.getString("TYPE_CONTENT"), name, value);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();

	}

	public static void appendCheckBoxOptions(StringBuffer sb, String value,
			String text, String name, String[] defvalue) {
		int findflag = 0;
		if (defvalue != null) {
			for (int i = 0; i < defvalue.length; i++) {
				if (value.equals(defvalue[i]))
					findflag = 1;
			}
		}
		if (findflag == 0)
			sb.append("<input type='checkbox' value='" + value
					+ "' class='flatcheckbox' name='" + name + "'>" + text
					+ "&nbsp;");
		if (findflag == 1)
			sb.append("<input type='checkbox' value='" + value
					+ "' checked class='flatcheckbox' name='" + name + "'>"
					+ text + "&nbsp;");
	}

	/**
	 * 获得星期
	 *
	 * @author dingyj
	 * @since 2009-11-27
	 * @param value
	 * @return
	 */
	public static String getWeekOption(Integer value) throws Exception {
		int val = value.intValue();
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 0, "星期日", val);
		appendOptions(sb, 1, "星期一", val);
		appendOptions(sb, 2, "星期二", val);
		appendOptions(sb, 3, "星期三", val);
		appendOptions(sb, 4, "星期四", val);
		appendOptions(sb, 5, "星期五", val);
		appendOptions(sb, 6, "星期六", val);
		return sb.toString();
	}

	/**
	 * 解析服务和联系方式选项
	 *
	 * @author dingyj
	 * @since 2009-11-27
	 * @param val
	 *            结果值
	 * @param list
	 *            所有集合
	 * @param type
	 *            获取字段(map中获取的字段)
	 * @return str
	 * @throws Exception
	 */
	public static int[] getDictCheck(int val, List list, String type)
			throws Exception {
		int[] str = new int[list.size()];
		int[] items = new int[list.size()];
		Map map = new HashMap();
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			items[i] = Utility.parseInt(Utility.trimNull(map.get(type)),
					new Integer(0)).intValue();
		}
		for (int j = 0; j < items.length; j++) {
			if ((items[j] & val) > 0) {
				str[j] = items[j];
			}
		}
		return str;
	}

	//-----------------------------------taochen
	// BEGIN--------------------------------------
	/**
	 * CRM 服务类别查询用下拉
	 *
	 * @param ServiceType
	 * @return
	 * @throws Exception
	 */
	public static String getService_typeName(Integer ServiceType)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		Map map = null;
		ServiceManageLocal serviceManage = EJBFactory.getServiceManage();
		ServiceManageVO vo = new ServiceManageVO();

		List rsList = serviceManage.query_TServiceDefine(vo);

		sb.append("<option value=\"0\">请选择</option>");
		for (int i = 0; i < rsList.size(); i++) {
			map = (Map) rsList.get(i);
			appendOptions(sb, Utility.parseInt(map.get("ServiceType")
					.toString(), new Integer(0)), Utility.trimNull(map
					.get("ServiceTypeName")), ServiceType);
		}
		return sb.toString();
	}

	/**
	 * 服务状态选择下拉框
	 */
	public static String getService_status(int flag) {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "请选择", flag);
		appendOptions(sb, 1, "未处理", flag);
		appendOptions(sb, 2, "待处理", flag);
		appendOptions(sb, 3, "处理中", flag);
		appendOptions(sb, 4, "已处理", flag);
		appendOptions(sb, 9, "作废", flag);
		return sb.toString();
	}

	/**
	 * 得到服务状态名字
	 */
	public static String getService_status_name(int flag) {
		String service_status_name = "";

		switch (flag) {
		case 0:
			service_status_name = "请选择";
			break;
		case 1:
			service_status_name = "未处理";
			break;
		case 2:
			service_status_name = "待处理";
			break;
		case 3:
			service_status_name = "处理中";
			break;
		case 4:
			service_status_name = "已处理";
			break;
		case 8:
			service_status_name = "失败";
			break;
		case 9:
			service_status_name = "作废";
			break;
		}

		return service_status_name;
	}

	/**
	 * 客户满意度列表
	 */
	public static String getCustomerSatifaction(int flag) {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "请选择", flag);
		appendOptions(sb, 1, "1", flag);
		appendOptions(sb, 2, "2", flag);
		appendOptions(sb, 3, "3", flag);
		appendOptions(sb, 4, "4", flag);
		appendOptions(sb, 5, "5", flag);
		return sb.toString();
	}

	/**
	 * 收益分配方式
	 */
	public static String getBonus_flag(Integer flag) {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 1, "现金", flag);
		appendOptions(sb, 2, "转份额", flag);
		return sb.toString();
	}

	//费用缴款方式：1从本金扣，2另外交,0不交
	public static String getFeeJkTypeName(int flag) {
		if (flag == 0)
			return "无需缴纳";
		else if (flag == 1)
			return "从本金扣除";
		else if (flag == 2)
			return "额外缴纳";
		else
			return "";
	}

	//费用缴款方式：1从本金扣，2另外交,0不交
	public static String getFeeJkTypeOptions(int flag) {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "无需缴纳", flag);
		appendOptions(sb, 1, "从本金扣除", flag);
		appendOptions(sb, 2, "额外缴纳", flag);
		return sb.toString();
	}

	//	租赁类型1短期租赁2长期租赁
	public static String getProductUnitName(Integer flag) throws Exception {
		if (flag == null)
			return "";
		switch (flag.intValue()) {
		case 1:
			return "天";
		case 2:
			return "个月";
		case 3:
			return "年";
		case 4:
			return "无固定限期";
		default:
			return "无固定限期";
		}
	}

	//银行账号类型
	public static String getBankAcctType(String value) throws Exception {
		return getDictParamOptions_intrust(9920, value);
	}

	//	缴款方式
	public static String getJkTypeOptions(String value) throws Exception {
		return getDictParamOptions_intrust(1114, value);
	}

	//渠道合作方式
	public static String getChannelCooperationOptions(String value)
			throws Exception {
		return getDictParamOptions_intrust(5502, value);
	}

	//	产品的有效预约单
	public static String getPreContractOptions(Integer book_code,
			Integer product_id, String serial_no, Integer input_man,
			String cust_name) throws Exception {
		String listSql = "{call SP_QUERY_TPRECONTRACT_VALID (?,?,?,?,?,?,?,?,?,?)}";

		StringBuffer sb = newStringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setInt(2, product_id.intValue());
		stmt.setString(3, "");
		stmt.setInt(4, 0);
		stmt.setInt(5, input_man.intValue());
		stmt.setString(6, cust_name);
		stmt.setInt(7, 0);
		stmt.setBigDecimal(8, new BigDecimal(0));
		stmt.setBigDecimal(9, new BigDecimal(0));
		stmt.setString(10, "");
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				appendOptions(sb, rs.getString("SERIAL_NO")+"$0", rs
						.getString("PRE_CODE")
						+ " - " + rs.getString("CUST_NAME"), serial_no);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}
	
//  产品的有效预约单
    public static String getPreContractOptions(Integer book_code,
            Integer product_id, Integer sub_product_id, String serial_no, Integer input_man,
            String cust_name) throws Exception {
        String listSql = "{call SP_QUERY_TPRECONTRACT_VALID (?,?,?,?,?,?,?,?,?,?,?)}";

        StringBuffer sb = newStringBuffer();
        Connection conn = IntrustDBManager.getConnection();
        CallableStatement stmt = conn.prepareCall(listSql,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, book_code.intValue());
        stmt.setInt(2, product_id.intValue());
        stmt.setString(3, "");
        stmt.setInt(4, 0);
        stmt.setInt(5, input_man.intValue());
        stmt.setString(6, cust_name);
        stmt.setInt(7, 0);
        stmt.setBigDecimal(8, new BigDecimal(0));
        stmt.setBigDecimal(9, new BigDecimal(0));
        stmt.setString(10, "");
        stmt.setInt(11, sub_product_id.intValue());
        ResultSet rs = stmt.executeQuery();
        try {
            while (rs.next())
                appendOptions(sb, rs.getString("SERIAL_NO")+"$0", rs
                        .getString("PRE_CODE")
                        + " - " + rs.getString("CUST_NAME"), serial_no);
        } finally {
            rs.close();
            stmt.close();
            conn.close();
        }
        return sb.toString();
    }
    
    public static String getPreContractOptionsCrm(Integer book_code,
            Integer product_id, Integer sub_product_id, String serial_no, Integer input_man,
            String cust_name) throws Exception {
        String listSql = "{call SP_QUERY_TPRECONTRACT_VALID (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

        StringBuffer sb = newStringBuffer();
        Connection conn =CrmDBManager.getConnection();
        CallableStatement stmt = conn.prepareCall(listSql,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, book_code.intValue());
        stmt.setInt(2, product_id.intValue());
        stmt.setString(3, "");
        stmt.setInt(4, 0);
        stmt.setInt(5, input_man.intValue());
        stmt.setString(6, cust_name);
        stmt.setInt(7, 0);
        stmt.setBigDecimal(8, new BigDecimal(0));
        stmt.setBigDecimal(9, new BigDecimal(0));
        stmt.setString(10, "");
        stmt.setInt(11, 0);
		stmt.setInt(12, 0);
		stmt.setInt(13, 0);
        stmt.setInt(14, sub_product_id.intValue());
        stmt.setInt(15, 0);
        ResultSet rs = stmt.executeQuery();
        try {
            while (rs.next())
                appendOptions(sb, rs.getString("SERIAL_NO")+"$0", rs
                        .getString("PRE_CODE")
                        + " - " + rs.getString("CUST_NAME"), serial_no);
        } finally {
            rs.close();
            stmt.close();
            conn.close();
        }
        return sb.toString();
    }
    
	public static String getPreContractOptions2(Integer book_code,
			Integer product_id, String serial_no, Integer input_man,
			String cust_name) throws Exception {
		String listSql = "{call SP_QUERY_PRECUSTOMERCON (?,?,?,?)}";

		StringBuffer sb = new StringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		stmt.setString(1, cust_name);
		stmt.setInt(2, product_id.intValue());
		stmt.setString(3,"");
		stmt.setInt(4, input_man.intValue());
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				appendOptions(sb, rs.getString("CUST_ID")+"$1", Utility.trimNull("导入")
						+ " - " + rs.getString("CUST_NAME"), Utility.trimNull(serial_no));
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//账号
	public static String getCustBankAcctOptions(Integer cust_id,
			String bank_id, String card_id, String inputBank_acct)
			throws Exception {
		String listSql = "{call SP_QUERY_TCUSTBANKACCT(?,?,?)}";

		StringBuffer sb = newStringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, cust_id.intValue());
		stmt.setString(2, bank_id);
		stmt.setString(3, card_id);

		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				appendOptions(sb, rs.getString("BANK_ACCT"), rs
						.getString("BANK_ACCT"), inputBank_acct);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//合同推介地
	public static String getCitynameOptions(Integer product_id,
			Integer serial_no) throws Exception {
		String listSql = "{call SP_QUERY_TPRODUCTCITY(?,?)}";

		StringBuffer sb = new StringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, product_id.intValue());
		stmt.setInt(2, 0);
		ResultSet rs = stmt.executeQuery();

		try {
			if (rs.next()) {
				sb.append("<option value=\"\">请选择</option>");
				appendOptions(sb, rs.getInt("SERIAL_NO"), rs
						.getString("CITY_NAME"), serial_no);
			}
			while (rs.next()) {
				appendOptions(sb, rs.getInt("SERIAL_NO"), rs
						.getString("CITY_NAME"), serial_no);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}

		return sb.toString();
	}

	public static String getApply(Integer flag) {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, new Integer(2), "已认购", flag);
		appendOptions(sb, new Integer(1), "未认购", flag);
		return sb.toString();
	}

	//	申购时 选择已认购过的合同
	public static String getApplyContract(Integer book_code,
			Integer product_id, Integer input_man, String contract_bh)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		String listSql = "{call SP_QUERY_TCONTRACT(?,?,?,?,?,?)}";

		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setInt(2, product_id.intValue());
		stmt.setString(3, "");
		stmt.setString(4, "");
		stmt.setInt(5, 3);
		stmt.setInt(6, input_man.intValue());

		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				Argument.appendOptions(sb, rslist.getString("Contract_bh"),
						rslist.getString("Contract_bh") + " - "
								+ rslist.getString("Contract_sub_bh"),
						contract_bh);
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

//  选择已认购过的合同
    public static String getApplyContract2(Integer book_code,
            Integer product_id, Integer input_man, String contract_bh)
            throws Exception {
        StringBuffer sb = new StringBuffer();
        String listSql = "{call SP_QUERY_TCONTRACT(?,?,?,?,?,?)}";

        Connection conn = IntrustDBManager.getConnection();
        CallableStatement stmt = conn.prepareCall(listSql,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, book_code.intValue());
        stmt.setInt(2, product_id.intValue());
        stmt.setString(3, "");
        stmt.setString(4, "");
        stmt.setInt(5, 3); // 3这个标记表示 没有限制
        stmt.setInt(6, input_man.intValue());
        
        ResultSet rslist = stmt.executeQuery();
        try {
            while (rslist.next()) {  
                if (sb.length()!=0) sb.append(",");
                	sb.append("{");
                sb.append("\"CONTRACT_BH\":\""+rslist.getString("Contract_bh")+"\"");
                sb.append(",\"CONTRACT_SUB_BH\":\""+rslist.getString("Contract_sub_bh")+"\"");
                sb.append("}");
            }
        } finally {
            rslist.close();
            stmt.close();
            conn.close();
        }
        sb.insert(0,"[");
        sb.append("]");
        return sb.toString();
    }
    
	//　	选择合同编号后，根据产品ID号、合同编号从受益人信息表中(TBenifitor)查询出受益人ID号(distinct)
	public static String getFromCustIdOptions(Integer book_code,
			Integer product_id, String contract_bh, Integer serial_no,
			Integer input_man) throws Exception {
		String listSql = "{call SP_QUERY_TBENIFITOR_ONE(?,?,?,?)}";
		//原先的SP_QUERY_TBENIFITOR_ONE改为查询已缴款的过程 SP_QUERY_TBENIFITOR_ONE

		StringBuffer sb = newStringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setInt(2, product_id.intValue());
		stmt.setString(3, contract_bh);
		stmt.setInt(4, input_man.intValue());
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				appendOptions(sb, rs.getInt("SERIAL_NO"), rs
						.getString("CUST_NO")
						+ " - "
						+ rs.getString("CUST_NAME")
						+ " -证件号码: "
						+ rs.getString("CARD_ID")
						+ " -受益期限: "
						+ new Integer(rs.getInt("VALID_PERIOD")), serial_no);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//	只取某一产品下的银行账号
	public static String getpay_SbfOption2(Integer book_code,
			Integer serial_no, Integer product_id) throws Exception {
		String listSql = "{call SP_QUERY_TSUBBANKINFO1021(?,?)}";
		StringBuffer sb = newStringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setString(2, "");

		ResultSet rs = stmt.executeQuery();

		try {
			while (rs.next()) {
				if (serial_no == null || serial_no.intValue() == 0) {
					if (product_id != null) {
						if (product_id.intValue() == rs.getInt("PRODUCT_ID")) {

							serial_no = new Integer(rs.getInt("SERIAL_NO"));
							appendOptions(sb, new Integer(rs
									.getInt("SERIAL_NO")), rs
									.getString("BANK_ACCT")
									+ "-"
									+ new Integer(rs.getInt("PRODUCT_ID"))
											.toString()
									+ "-"
									+ rs.getString("BANK_NAME"), serial_no);
						}
					}
				}

			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//  收益优先级 1优先；2一般；3劣后
    public static String getProvFlagOptions(Integer selected) {
        StringBuffer sb = newStringBuffer();
        appendOptions(sb, "1", "优先", String.valueOf(selected));
        appendOptions(sb, "2", "一般", String.valueOf(selected));
        appendOptions(sb, "3", "劣后", String.valueOf(selected));
        return sb.toString();
    }
    
	//收益优先级别
	public static String getProvlevelOptions(String value) throws Exception {
		return getDictParamOptions_intrust(1204, value);
	}

	public static String getBankName(String value) throws Exception {
		return getDictParamName_intrust(1103, value);
	}

	//根据参数值得到参数名称
	public static String getDictParamName_intrust(int dict, String value)
			throws Exception {
		String listSql = "{call SP_QUERY_TDICTPARAM_VALUE  (?,?)}";
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setString(1, value);
		stmt.registerOutParameter(2, java.sql.Types.VARCHAR);

		stmt.executeUpdate();
		String dictName = stmt.getString(2);
		stmt.close();
		conn.close();
		return dictName;
	}

	/**
	 * 统计方式 时间
	 */
	public static String getStatFlagOptions(int flag) {
		StringBuffer sb = new StringBuffer();

		appendOptions(sb, 0, "请选择", flag);
		appendOptions(sb, 1, "日", flag);
		appendOptions(sb, 2, "周", flag);
		appendOptions(sb, 3, "月", flag);
		appendOptions(sb, 4, "季", flag);
		appendOptions(sb, 5, "年", flag);
		return sb.toString();
	}

	/////////////显示为“万”位，保存的时候为“个”位保存/////////////////////
	public static String getMoneyType(BigDecimal money) throws Exception {
		if (money != null)
			money = money.multiply(new BigDecimal(0.0001)).setScale(3, 1);
		return money.toString();
	}

	public static BigDecimal getMoneyType2(BigDecimal money) throws Exception {
		if (money != null)
			money = money.multiply(new BigDecimal(10000));
		return money;
	}

	/**
	 * ADD BY tangshg 2009-11-29 查询子产品列表 SP_QUERY_TSUBPRODUCT
	 *
	 * @IN_PRODUCT_ID INT,
	 * @IN_SUB_PRODUCT_ID INT = 0,
	 * @IN_LIST_ID INT = 0
	 */
	public static String getSubProductOptions(Integer product_id,
			Integer sub_product_id, Integer defalt_sub_product_id)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TSUBPRODUCT(?,?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		product_id = Utility.parseInt(Utility.trimNull(product_id),
				new Integer(0));
		sub_product_id = Utility.parseInt(Utility.trimNull(sub_product_id),
				new Integer(0));
		stmt.setInt(1, product_id.intValue());
		stmt.setInt(2, sub_product_id.intValue());
		stmt.setInt(3, 0);
		ResultSet rs = stmt.executeQuery();
		try {
			
			while (rs.next())
				appendOptions(sb, rs.getInt("SUB_PRODUCT_ID"), rs
						.getString("LIST_NAME"), defalt_sub_product_id);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}
	//通过CRM库来取子产品信息
	public static String getCRMSubProductOptions(Integer product_id,
			Integer sub_product_id, Integer defalt_sub_product_id)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TSUBPRODUCT(?,?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		product_id = Utility.parseInt(Utility.trimNull(product_id),
				new Integer(0));
		sub_product_id = Utility.parseInt(Utility.trimNull(sub_product_id),
				new Integer(0));
		stmt.setInt(1, product_id.intValue());
		stmt.setInt(2, sub_product_id.intValue());
		stmt.setInt(3, 0);
		ResultSet rs = stmt.executeQuery();
		try {
			
			while (rs.next())
				appendOptions(sb, rs.getInt("SUB_PRODUCT_ID"), rs
						.getString("LIST_NAME"), defalt_sub_product_id);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//查询子产品列表 SP_QUERY_TSUBPRODUCT,条件增加了子产品状态
	public static String getSubProductOptions(Integer product_id,Integer sub_product_id
			,int check_flag,String sub_product_status, Integer defalt_sub_product_id)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TSUBPRODUCT(?,?,?,?,?,?,?,?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		product_id = Utility.parseInt(Utility.trimNull(product_id),
				new Integer(0));
		sub_product_id = Utility.parseInt(Utility.trimNull(sub_product_id),
				new Integer(0));
		stmt.setInt(1, product_id.intValue());
		stmt.setInt(2, sub_product_id.intValue());
		stmt.setInt(3, 0);
		stmt.setString(4, "");
		stmt.setString(5, "");
		stmt.setString(6, "");
		stmt.setInt(7, check_flag);
		stmt.setString(8, "");
		stmt.setString(9, sub_product_status);
		ResultSet rs = stmt.executeQuery();
		try {
			
			while (rs.next())
				appendOptions(sb, rs.getInt("SUB_PRODUCT_ID"), rs
						.getString("LIST_NAME"), defalt_sub_product_id);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}
	/** ADD BY tangshg 2009-11-29 查询字产品列表 SP_QUERY_TSUBPRODUCT
	 *
	 * @IN_PRODUCT_ID INT,
	 * @IN_SUB_PRODUCT_ID INT = 0,
	 * @IN_LIST_ID INT = 0 查询所有信托财产类型的子产品
	 */
	public static String getSubProductOptions2(Integer product_id,
			Integer sub_product_id, Integer defalt_sub_product_id,
			int check_flag) throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TSUBPRODUCT(?,?,?,?,?,?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		product_id = Utility.parseInt(Utility.trimNull(product_id),
				new Integer(0));
		sub_product_id = Utility.parseInt(Utility.trimNull(sub_product_id),
				new Integer(0));
		stmt.setInt(1, product_id.intValue());
		stmt.setInt(2, sub_product_id.intValue());
		stmt.setInt(3, 0);
		stmt.setString(4, "");
		stmt.setString(5, "");
		stmt.setString(6, "");
		stmt.setInt(7, check_flag);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				appendOptions(sb, rs.getInt("SUB_PRODUCT_ID"), rs
						.getString("LIST_NAME"), defalt_sub_product_id);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}
    
	//-----------------------------------taochen
	// END--------------------------------------

    public static String getSubProductName(Integer product_id, Integer sub_product_id)
    throws Exception {
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = IntrustDBManager.getConnection();
            stmt = conn.prepareCall(
                    "{call SP_QUERY_TSUBPRODUCT(?,?,?)}",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            product_id = Utility.parseInt(Utility.trimNull(product_id),
                    new Integer(0));
            sub_product_id = Utility.parseInt(Utility.trimNull(sub_product_id),
                    new Integer(0));
            stmt.setInt(1, product_id.intValue());
            stmt.setInt(2, sub_product_id.intValue());
            stmt.setInt(3, 0);
            rs = stmt.executeQuery();
            
            if (rs.next())
               return rs.getString("LIST_NAME");
            else 
                return "";
        } finally {
            if (rs!=null)
                rs.close();
            if (stmt!=null)
                stmt.close();
            if (conn!=null)
                conn.close();
        }
    }
    
	/**
	 * 获得联系人上司选项
	 *
	 * @author dingyj
	 * @param cust_id
	 * @return
	 */
	public static String getBossOption(Integer cust_id, Integer input_man,
			Integer value) throws Exception {
		StringBuffer sb = newStringBuffer();
		CustomerContactLocal local = EJBFactory.getCustomerContact();
		CustomerContactVO vo = new CustomerContactVO();
		vo.setCust_id(cust_id);
		vo.setReceiveContactWay(new Integer(0));
		vo.setReceiveService(new Integer(0));
		vo.setInsertMan(input_man);
		List list = local.queryCustomerContact(vo);
		Map map = new HashMap();
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			appendOptions(sb, new Integer(map.get("ContactID").toString()), map
					.get("Contactor").toString(), value);
		}
		return sb.toString();
	}

	/**
	 * 获得客户群组的选项
	 *
	 * @author dingyj
	 * @since 2009-12-3
	 * @param group_id
	 * @param value
	 * @return
	 */
	public static String getCustGroupOption(Integer group_id, Integer value)
			throws Exception {
		StringBuffer sb = new StringBuffer();

		sb.append("<option value=\"" + value + "\">请选择</option>");
		//if (group_id != new Integer(0) && !group_id.equals(new Integer(0))) {
		CustGroupLocal local = EJBFactory.getCustGroup();
		CustGroupVO vo = new CustGroupVO();
		vo.setGroupId(group_id);
		List list = local.queryAll(vo);
		if (list != null) {
			Map map = new HashMap();
			for (int i = 0; i < list.size(); i++) {
				map = (Map) list.get(i);
				appendOptions(sb, new Integer(map.get("GROUPID").toString()),
						map.get("GROUPNAME").toString(), value);
			}
		}
		//}
		return sb.toString();
	}

	public static String getCustGroupOption2(Integer cust_id, Integer value)
			throws Exception {
		StringBuffer sb = new StringBuffer();

		sb.append("<option>请选择客户群组</option>");
		//if (group_id != new Integer(0) && !group_id.equals(new Integer(0))) {
		CustGroupLocal local = EJBFactory.getCustGroup();
		CustGroupVO vo = new CustGroupVO();
		List list = local.queryByCustId(cust_id);
		if (list != null) {
			Map map = new HashMap();
			for (int i = 0; i < list.size(); i++) {
				map = (Map) list.get(i);
				appendOptions(sb, Utility.parseInt(Utility.trimNull(map
						.get("GroupID")), new Integer(0)), Utility.trimNull(map
						.get("GroupName")), value);
			}
		}
		//}
		return sb.toString();
	}

	//客户等级选择
	public static String getCustClassOption(Integer classDefineID,
			Integer classDetailID) throws Exception {
		StringBuffer sb = new StringBuffer();

		CustClassDetailLocal local = EJBFactory.getCustClassDetail();
		CustClassDetailVO vo = new CustClassDetailVO();
		vo.setClass_define_id(classDefineID);

		List list = local.query(vo);
		if (list != null) {
			Map map = new HashMap();
			for (int i = 0; i < list.size(); i++) {
				map = (Map) list.get(i);

				appendOptions(sb, Utility.parseInt(Utility.trimNull(map
						.get("CLASSDETAIL_ID")), new Integer(0)), Utility
						.trimNull(map.get("CLASSDETAIL_NAME")), classDetailID);
			}
		}

		return sb.toString();
	}
	//callcenter代理服务器IP
	public static String getCTIServerIP() throws Exception {
		return getValueOfTDictParam("800103");
	}
	//代理服务器端口：21900
	public static String getCTIServerPort() throws Exception {
		return getValueOfTDictParam("800104");
	}
	//callcenter数据库服务器IP地址
	public static String getCallCenterDbIP() throws Exception {
		return getValueOfTDictParam("800105");
	}
	//callcenter数据库服务器名称
	public static String getCallCenterDbName() throws Exception {
		return getValueOfTDictParam("800106");
	}
	//callcenter数据库服务器登录名
	public static String getCallCenterDbUser() throws Exception {
		return getValueOfTDictParam("800107");
	}
	//callcenter数据库服务器登录密码
	public static String getCallCenterDbPassword() throws Exception {
		return getValueOfTDictParam("800108");
	}
	/**
	 * 获得接受的联系方式
	 *
	 * @author dingyj
	 * @since 2009-12-9
	 * @param value
	 *            选择的联系方式的总和
	 * @return
	 * @throws Exception
	 */
	public static String getContactCheckBox(int value) throws Exception {
		StringBuffer sb = new StringBuffer();
		DictparamLocal dict_local = EJBFactory.getDictparam();
		DictparamVO dict_vo = new DictparamVO();
		dict_vo.setType_id(new Integer(1109));
		//获得字典表的联系方式
		List list = dict_local.listDictparamAll(dict_vo);
		//根据总和获得解析后的数组
		int[] items = getDictCheck(value, list, "ADDITIVE_VALUE");
		return getCheckBox(sb, items, list, "receiveContactWay",
				"ADDITIVE_VALUE", "TYPE_CONTENT");
	}

	/**
	 * 获得服务项
	 *
	 * @author dingyj
	 * @since 2009-12-9
	 * @param value
	 *            选择的服务项的总和
	 * @return
	 * @throws Exception
	 */
	public static String getServiceCheckBox(int value) throws Exception {
		StringBuffer sb = new StringBuffer();
		ServiceManageLocal service_local = EJBFactory.getServiceManage();
		ServiceManageVO service_vo = new ServiceManageVO();
		service_vo.setSerial_no(new Integer(0));
		service_vo.setServiceType(new Integer(0));
		service_vo.setIsValid(new Integer(1)); //启用的
		service_vo.setInputMan(new Integer(0));
		//获得启用的服务
		List list = service_local.query_TServiceDefine(service_vo);
		//根据服务总和获得解析后的数组
		int[] items = getDictCheck(value, list, "ServiceType");
		return getCheckBox(sb, items, list, "receiveServices", "ServiceType",
				"ServiceTypeName");
	}

	/**
	 * 呼叫方式选择下拉框
	 *
	 * @param flag
	 * @return
	 */
	public static String getDirection(int flag) {
		StringBuffer sb = new StringBuffer();

		appendOptions(sb, 0, "请选择", flag);
		appendOptions(sb, 1, "主叫接听", flag);
		appendOptions(sb, 2, "被叫接听", flag);
		return sb.toString();
	}

	/**
	 * 组合复选框
	 *
	 * @author dingyj
	 * @since 2009-12-9
	 * @param sb
	 * @param items
	 *            解析后的数组
	 * @param list
	 *            所有的信息集合
	 * @param name
	 *            名称
	 * @param value
	 *            值
	 * @param text
	 *            文本
	 * @return sb
	 */
	public static String getCheckBox(StringBuffer sb, int[] items, List list,
			String name, String value, String text) {
		Iterator it = list.iterator();
		Map map = new HashMap();
		int j = 0;
		int newline = value == "ADDITIVE_VALUE" ? 6 : 4; //如果是联系方式，6项再换行，否则4项换行
		while (it.hasNext()) {
			j++;
			map = (Map) it.next();
			boolean flag = false;
			for (int i = 0; i < items.length; i++) {
				if (new Integer(items[i]).equals(new Integer(map.get(value)
						.toString()))
						&& items[i] > 0) {
					flag = true;
					break;
				}
			}
			sb.append("<input type='checkbox' name=\"" + name + "\" value=\""
					+ map.get("" + value + "") + "\" ");
			if (flag)
				sb.append("checked ");
			sb.append("/>" + map.get("" + text + "")
					+ "&nbsp;&nbsp;&nbsp;&nbsp;");
			if (j % newline == 0)
				sb.append("</br></br>");
		}
		return sb.toString();
	}

	public static String getContector(Integer contectID) throws Exception {
		//防止搜全部
		if (contectID.intValue() > 0) {
			Map map = null;
			CustomerVO vo = new CustomerVO();
			vo.setContactID(contectID);
			CustomerLocal Customer = EJBFactory.getCustomer();
			List list = Customer.listByControl(vo);

			if (list.size() > 0) {
				map = (Map) list.get(0);
				return (Utility.trimNull(map.get("Contector")));
			}
		}

		return "";

	}

	/**
	 * 获得已缴款的合同编号信息
	 *
	 * @since 2016-04-28
	 */
	public static String getContract(Integer book_code, Integer product_id,
			String contract_bh, Integer input_man,Integer sub_product_id) throws Exception {
		String listSql = "{call SP_QUERY_TCONTRACT_YJK(?,?,?,?)}";

		String value = "";
		if (product_id != null)
			value = contract_bh;
		if (sub_product_id==null) sub_product_id=new Integer(0);
		StringBuffer sb = newStringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setInt(2, product_id.intValue());
		stmt.setInt(3, input_man.intValue());
		stmt.setInt(4, sub_product_id.intValue());
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				appendOptions(sb, rs.getString("CONTRACT_BH"), rs
						.getString("CONTRACT_SUB_BH")
						+ " - " + rs.getString("CUST_NAME"), value);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}
	/**
	 * 获得已缴款的合同编号信息
	 *
	 * @author dingyj
	 * @since 2009-12-11
	 */
	public static String getContract(Integer book_code, Integer product_id,
			String contract_bh, Integer input_man) throws Exception {
		String listSql = "{call SP_QUERY_TCONTRACT_YJK(?,?,?)}";

		String value = "";
		if (product_id != null)
			value = contract_bh;
		
		StringBuffer sb = newStringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setInt(2, product_id.intValue());
		stmt.setInt(3, input_man.intValue());
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				appendOptions(sb, rs.getString("CONTRACT_BH"), rs
						.getString("CONTRACT_SUB_BH")
						+ " - " + rs.getString("CUST_NAME"), value);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}
	//获得已缴款的合同编号信息,从CRM库中取
	public static String getContractCrm(Integer book_code, Integer product_id,
			String contract_bh, Integer input_man,Integer sub_product_id) throws Exception {
		String listSql = "{call SP_QUERY_TCONTRACT_YJK(?,?,?,?)}";

		String value = "";
		if (product_id != null)
			value = contract_bh;
		if (sub_product_id==null) sub_product_id=new Integer(0);
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setInt(2, product_id.intValue());
		stmt.setInt(3, input_man.intValue());
		stmt.setInt(4, sub_product_id.intValue());
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				appendOptions(sb, rs.getString("CONTRACT_BH"), rs
						.getString("CONTRACT_SUB_BH")
						+ " - " + rs.getString("CUST_NAME"), value);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	/**
	 * 获得已缴款的合同编号信息
	 *
	 * @param book_code
	 * @param product_id
	 * @param input_man
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String getContract2(Integer book_code, Integer product_id,
			 Integer input_man,String value) throws Exception {
		String listSql = "{call SP_QUERY_TCONTRACT_REBATE(?,?)}";
		StringBuffer sb = newStringBuffer();

		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		stmt.setInt(1, book_code.intValue());
		stmt.setInt(2, product_id.intValue());

		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				appendOptions(sb, rs.getString("CONTRACT_BH"), rs
						.getString("CONTRACT_SUB_BH")
						+ "-" + rs.getString("CUST_NAME"), value);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	/**
	 * 受益权转让类别
	 *
	 * @author dingyj
	 * @since 2009-12-18
	 */
	public static String getTransTypeOptions(String value) throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, "181501", "转让", value);
		appendOptions(sb, "181502", "继承", value);
		appendOptions(sb, "181503", "捐赠", value);

		return sb.toString();
	}

	/**
	 * 获得受益付款方式
	 *
	 * @author dingyj
	 * @since 2009-12-18
	 * @param type_value
	 * @return
	 * @throws Exception
	 */
	public static String getDictContentIntrust(String type_value)
			throws Exception {
		if (type_value == null)
			return null;
		String ret = "";
		Connection conn = IntrustDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("{call SP_QUERY_DICTCONTENT ('"
				+ type_value + "')}");
		try {
			if (rs.next())
				ret = rs.getString("TYPE_CONTENT");
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return ret;
	}

	/**
	 * 根据客户ID获得产品名称
	 *
	 * @author dingyj
	 * @since 2009-12-28
	 * @param cust_id
	 * @return ret;
	 */
	public static String getProductNameByCustId(Integer cust_id)
			throws Exception {
		String ret = "";
		if (cust_id == null)
			return null;
		Connection conn = IntrustDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("{call SP_QUERY_TCONTRACT_CUST_ID ("
				+ cust_id + ")}");
		try {
			if (rs.next())
				ret = rs.getString("PRODUCT_NAME");
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return ret;
	}

	/**
	 * 获得证件类型（个人+机构）
	 *
	 * @author dingyj
	 * @since 2010-1-6
	 * @return
	 * @throws Exception
	 */
	public static String getCardTypeJgOrGrOptions(String card_type)
			throws Exception {
		String gr = getDictParamOptions2(1108, card_type);
		String jg = getDictParamOptions2(2108, card_type);
		String jgsub = jg.substring(29, jg.length());
		return gr + jgsub;
	}

	/**
	 * 获得委托人标志
	 *
	 * @author dingyj
	 * @since 2010-1-6
	 * @return sb
	 * @throws Exception
	 */
	public static String getClientFlagList(Integer wtr_flag) throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "委托人和受益人", wtr_flag);
		appendOptions(sb, 1, "受益人", wtr_flag);
		appendOptions(sb, 2, "委托人", wtr_flag);
		return sb.toString();
	}

	/**
	 * 获得客户邮箱
	 *
	 * @author dingyj
	 * @since 2010-1-11
	 * @return email
	 * @throws Exception
	 */
	public static String getEmailName(Integer cust_id,
			Integer input_operatorCode) throws Exception {
		CustomerLocal local = EJBFactory.getCustomer();
		CustomerVO vo = new CustomerVO();
		String email = "";
		try {

			vo.setCust_id(cust_id);
			vo.setInput_man(input_operatorCode);
			List list = local.listProcAll(vo);
			if (list != null && list.size() > 0) {
				email = Utility.trimNull(((Map) list.get(0)).get("E_MAIL"));
			}

		} finally {
			local.remove();
		}
		return email;
	}

	/**
	 * 获得客户手机
	 *
	 * @param cust_id
	 * @param input_operatorCode
	 * @return
	 * @throws Exception
	 */
	public static String getCustomerModile(Integer cust_id,
			Integer input_operatorCode) throws Exception {
		CustomerLocal local = EJBFactory.getCustomer();
		CustomerVO vo = new CustomerVO();
		String modile = "";
		try {
			vo.setCust_id(cust_id);
			vo.setInput_man(input_operatorCode);
			List list = local.listProcAll(vo);
			if (list != null && list.size() > 0) {
				modile = Utility.trimNull(((Map) list.get(0)).get("MOBILE"));
			}

		} finally {
			local.remove();
		}
		return modile;
	}

	/**
	 * 得到客户公司名称
	 *
	 * @author dingyj
	 * @since 2010-1-11
	 * @return company_name
	 * @throws Exception
	 */
	public static String getCompanyName(Integer user_id) throws Exception {
		String company_name = "";
		String listSql = "{call SP_QUERY_TUSERINFO(?)}";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, user_id.intValue());
		ResultSet rs = stmt.executeQuery();
		try {
			if (rs.next())
				company_name = rs.getString("USER_NAME");
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return company_name;
	}

	public static String getLevelID(Integer level_id) throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "请选择", level_id);
		appendOptions(sb, 10, "认购份额", level_id);
		appendOptions(sb, 20, "受益份额", level_id);
		return sb.toString();
	}

	/**
	 * 得到活动信息列表
	 *
	 * @param activitySerialNo
	 * @return
	 * @throws Exception
	 */
	public static String getActivityOptions(Integer activitySerialNo)
			throws Exception {
		if (activitySerialNo == null) {
			return null;
		}

		StringBuffer sb = newStringBuffer();
		int activeFlag = 0;
		String activeFlagName = null;
		String listSql = "{call SP_QUERY_TActivitiesLoad(?)}";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, 0);
		ResultSet rs = stmt.executeQuery();

		try {
			while (rs.next()) {
				activeFlag = rs.getInt("ACTIVE_FLAG");
				activeFlagName = getActivityFlagName(new Integer(activeFlag));
				appendOptions(sb, rs.getInt("SERIAL_NO"), rs
						.getString("ACTIVE_CODE")
						+ " - "
						+ rs.getString("ACTIVE_THEME")
						+ " - "
						+ activeFlagName, activitySerialNo);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}

		return sb.toString();
	}

	/**
	 * 得到活动信息标志意义
	 *
	 * @param flag
	 * @return
	 */
	public static String getActivityFlagName(Integer flag) {
		if (flag == null)
			return null;
		switch (flag.intValue()) {
		case 1:
			return "未处理";
		case 2:
			return "进行中";
		case 3:
			return "已完结";
		default:
			return "新建";
		}
	}

	/**
	 * 得到活动任务标志意义
	 *
	 * @param flag
	 * @return
	 */
	public static String getActivityTaskFlagName(Integer flag) {
		if (flag == null)
			return null;
		switch (flag.intValue()) {
		case 1:
			return "未处理";
		case 2:
			return "待审核";
		case 3:
			return "已审核";
		default:
			return "未处理";
		}
	}

	//	客户类别
	public static String getWTCustOptions(Integer value) throws Exception {
		StringBuffer sb = new StringBuffer(200);
		appendOptions(sb, 0, "请选择", value);
		appendOptions(sb, 1, "事实客户", value);
		appendOptions(sb, 2, "潜在客户 ", value);
		return sb.toString();
	}

	public static String getWTName(Integer flag) {
		if (flag == null)
			return null;
		switch (flag.intValue()) {
		case 1:
			return "事实客户";
		case 2:
			return "潜在客户";
		case 3:
			return "注销客户";
		default:
			return "未知";
		}
	}

	/**
	 * 获得员工邮箱
	 *
	 * @param opCode
	 * @return
	 * @throws Exception
	 */
	public static String getOpEmailName(Integer opCode) throws Exception {
		String email = "";
		OperatorLocal operator = EJBFactory.getOperator();
		OperatorVO vo = new OperatorVO();

		vo.setStatus(new Integer(1));
		vo.setOp_code(opCode);

		List operator_list = operator.listOpAll(vo);
		Map map = null;

		if (operator_list.size() > 0) {
			map = (Map) operator_list.get(0);
			email = Utility.trimNull(map.get("EMAIL"));
		}

		operator.remove();
		return email;
	}

	/**
	 * 获得员工邮箱组
	 *
	 * @author taochen
	 *
	 * 更改所生成类型注释的模板为 窗口 > 首选项 > Java > 代码生成 > 代码和注释
	 */
	public static String getOpEmailGroup(String opCodeGroup) throws Exception {
		String emailGroup = "";
		String[] opCodeArray = Utility.splitString(opCodeGroup, "$");
		Integer opCode;
		String email = "";

		for (int i = 0; i < opCodeArray.length; i++) {
			opCode = Utility.parseInt(Utility.trimNull(opCodeArray[i]),
					new Integer(0));

			if (opCode.intValue() > 0) {
				email = getOpEmailName(opCode);
				if (email.length() > 0) {
					emailGroup += email + ';';
				}
			}
		}

		if (emailGroup.length() > 0) {
			emailGroup = emailGroup.substring(0, emailGroup.length() - 1);
		}

		return emailGroup;
	}

	public static String getMyMenuViewStr(String menu_id, Integer op_code)
			throws Exception {
		OperatorLocal op_local = EJBFactory.getOperator();
		String tempView = op_local.listMenuView(menu_id, op_code);
		return tempView;
	}

	public static Map getMenuViewMap(String menu_id, String viewStr)
			throws Exception {
		MenuInfoLocal menu = EJBFactory.getMenuInfo();
		Map fieldsMap = menu.listMenuView(menu_id, viewStr);
		return fieldsMap;
	}

	public static String getMenuViewStr(String menu_id, String viewStr)
			throws Exception {
		MenuInfoLocal menu = EJBFactory.getMenuInfo();
		Map fieldsMap = menu.listMenuView(menu_id, viewStr);
		return JsonUtil.object2json(fieldsMap);
	}

	//获取问卷列表
	public static String getQuesInfoOption(Integer v_ques_id) throws Exception {
		StringBuffer sb = newStringBuffer();
		QuestionnaireLocal quesInfoLocal = EJBFactory.getQuestionnaire();
		QuestionnaireVO vo = new QuestionnaireVO();

		Integer ques_id = new Integer(0);
		String ques_no = "";
		String ques_title = "";

		vo.setStatus(new Integer(1));
		List list = quesInfoLocal.queryQuestInfo(vo);
		Map map = null;

		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			map = (Map) iterator.next();

			ques_id = Utility.parseInt(Utility.trimNull(map.get("QUES_ID")),
					new Integer(0));
			ques_no = Utility.trimNull(map.get("QUES_NO"));
			ques_title = Utility.trimNull(map.get("QUES_TITLE"));

			appendOptions(sb, ques_id, ques_no + " - " + ques_title, v_ques_id);
		}

		return sb.toString();
	}

	/**
	 * 根据CUST_ID 得到客户Email
	 *
	 * @param cust_id
	 * @return
	 * @throws Exception
	 */
	public static String[] getCustEmailName(Integer cust_id) throws Exception {
		String email = "";
		String cust_name = "";
		String[] ret = new String[2];
		String sqlStr = "SELECT CUST_NAME,E_mail FROM TCUSTOMERS WHERE CUST_ID ="
				+ cust_id;
		List rsList = enfo.crm.dao.CrmDBManager.listBySql(sqlStr);

		if (rsList.size() > 0) {
			Map map = (Map) rsList.get(0);

			if (map != null) {
				email = Utility.trimNull(map.get("E_mail"));
				cust_name = Utility.trimNull(map.get("CUST_NAME"));

				ret[0] = cust_name;
				ret[1] = email;
			}
		}
		return ret;
	}

	/**
	 * 根据CUST_ID组 得到客户Email组
	 *
	 * @param custIdGroup
	 * @return
	 * @throws Exception
	 */
	public static String[] getCustEmailGroup(String custIdGroup)
			throws Exception {
		String emailGroup = "";//Email 组
		String email = "";//Email 单个
		String[] custIdArray = custIdGroup.split("_");
		Integer custId;
		String cust_name_alert = "";//没有 Email的 警报
		String[] ret = null;//getCustEmailName 返回值
		String[] ret2 = new String[2];//本方法返回值

		for (int i = 0; i < custIdArray.length; i++) {
			custId = Utility.parseInt(Utility.trimNull(custIdArray[i]),
					new Integer(0));

			if (custId.intValue() > 0) {
				ret = getCustEmailName(custId);
				email = ret[1];
				emailGroup += email + ';';

				if (email.length() == 0) {
					cust_name_alert = cust_name_alert + ret[0] + ",";
				}
			}
		}

		if (emailGroup.length() > 0) {
			emailGroup = emailGroup.substring(0, emailGroup.length() - 1);
		}

		if (cust_name_alert.length() > 0) {
			cust_name_alert = cust_name_alert.substring(0, cust_name_alert
					.length() - 1);
		}

		ret2[0] = emailGroup;
		ret2[1] = cust_name_alert;

		return ret2;
	}

	public static String getTopicValue(Integer topic_id) throws Exception {
		QuestionnaireLocal local = EJBFactory.getQuestionnaire();
		QuestionnaireVO vo = new QuestionnaireVO();
		StringBuffer sb = newStringBuffer();

		String s_topic_value_no = "";
		String s_topic_value = "";
		Integer s_score = new Integer(0);

		vo.setTopic_id(topic_id);
		List rsList = local.queryTTopicScroe(vo);
		Map map = null;

		Iterator iterator = rsList.iterator();
		while (iterator.hasNext()) {
			map = (Map) iterator.next();

			s_topic_value_no = Utility.trimNull(map.get("TOPIC_VALUE_NO"));
			s_topic_value = Utility.trimNull(map.get("TOPIC_VALUE"));
			//s_score = Utility.parseInt(Utility.trimNull(map.get("Score")),new
			// Integer(0));

			appendOptions(sb, s_topic_value, s_topic_value_no + " . "
					+ s_topic_value, topic_id.toString());
		}

		return sb.toString();
	}

	/**
	 * 为客户服务任务选择角色
	 *
	 * @param actorId
	 * @return
	 */
	public static String getServiceActorOptions(Integer actorId) {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 1, "管理者", actorId);
		appendOptions(sb, 2, "执行者", actorId);

		return sb.toString();
	}

	/**
	 * 获取调查任务列表
	 *
	 * @param quesTask_id
	 * @return
	 * @throws Exception
	 */
	public static String getQuesTaskOptions(Integer quesTask_id)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		ServiceTaskLocal local = EJBFactory.getServiceTask();
		ServiceTaskVO vo = new ServiceTaskVO();
		String taskTitle = "";
		Integer taskId = new Integer(0);

		appendOptions(sb, new Integer(0), "请选择", quesTask_id);

		vo.setServiceType(new Integer(64));
		List rsList = local.query(vo);

		if (rsList.size() > 0) {
			Map map = null;
			Iterator it = rsList.iterator();

			while (it.hasNext()) {
				map = (Map) it.next();

				taskTitle = Utility.trimNull(map.get("ServiceTitle"));
				taskId = Utility.parseInt(Utility
						.trimNull(map.get("Serial_no")), new Integer(0));

				appendOptions(sb, taskId, taskTitle, quesTask_id);
			}
		}

		return sb.toString();
	}

	/**
	 * 获取任务列表
	 *
	 * @param quesTask_id
	 * @return
	 * @throws Exception
	 */
	public static String getTaskOptions(Integer serviceType, Integer task_id)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		ServiceTaskLocal local = EJBFactory.getServiceTask();
		ServiceTaskVO vo = new ServiceTaskVO();
		String taskTitle = "";
		Integer taskId = new Integer(0);

		appendOptions(sb, new Integer(0), "请选择", task_id);

		vo.setServiceType(serviceType);
		List rsList = local.query(vo);

		if (rsList.size() > 0) {
			Map map = null;
			Iterator it = rsList.iterator();

			while (it.hasNext()) {
				map = (Map) it.next();

				taskTitle = Utility.trimNull(map.get("ServiceTitle"));
				taskId = Utility.parseInt(Utility
						.trimNull(map.get("Serial_no")), new Integer(0));

				appendOptions(sb, taskId, taskTitle, task_id);
			}
		}

		return sb.toString();
	}

	public static InputStream getImageStream(Integer cust_id) throws Exception {
		String strSql = "select ImageIdentification from tcustomers where cust_id ="
				+ cust_id;
		InputStream in = null;

		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = null;

		try {
			rs = stmt.executeQuery(strSql);

			while (rs.next()) {
				in = rs.getBinaryStream("ImageIdentification");
			}
		} catch (Exception e) {
			throw new BusiException("读取客户身份证图片失败:" + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}

		return in;
	}

	//未缴款的合同
	public static String getUnJkContractOptions(Integer productid,
			String contractbh) throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rslist = stmt.executeQuery("{call SP_QUERY_TCONTRACT_UNJK("
				+ productid.toString() + ")}");
		try {
			while (rslist.next()) {
				Argument.appendOptions(sb, rslist.getString("contract_bh"),
						rslist.getString("contract_sub_bh") + "-"
								+ rslist.getString("GAIN_ACCT"), contractbh);
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	public static String getBenifitorOptions(Integer book_code,
			Integer product_id, String contract_bh, Integer list_id)
			throws Exception {
		String listSql = "{call SP_QUERY_TBENIFITOR_LIST(?,?,?,?,?)}";
		StringBuffer sb = newStringBuffer();
		String value = "";

		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setInt(2, product_id.intValue());
		stmt.setString(3, contract_bh);
		stmt.setInt(4, 0);
		stmt.setInt(5, 0);

		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				appendOptions(sb, rs.getInt("list_id"), rs.getString("list_id")
						+ "-" + rs.getString("CUST_NAME"), list_id);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}
	//获取客户的受益银行账号户名
	public static String getBenAccCustName(Integer cust_id)
			throws Exception {
		String listSql = "SELECT DISTINCT CUST_ACCT_NAME FROM TBENIFITOR WHERE CUST_ID=?";
		String value = "";
		Connection conn = IntrustDBManager.getConnection();
		PreparedStatement stmt = conn.prepareStatement(listSql);
		stmt.setInt(1, cust_id.intValue());
		
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				value=value+Utility.trimNull(rs.getString("CUST_ACCT_NAME"))+";";
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		if (value!=null && value.length()>0)//去掉最后的分号
			value=value.substring(0,value.length()-1);
		return value;
	}

	/**
	 * 获取CUST_NAME和 CUST_TYPE
	 *
	 * @param cust_id
	 * @return
	 */
	public static String[] getCustInfoById(Integer cust_id) {
		String[] ret = new String[2];
		String sqlStr = "SELECT CUST_NAME,CUST_TYPE" + " FROM TCUSTOMERS "
				+ "WHERE CUST_ID =" + cust_id;

		List rsList = null;
		try {
			rsList = enfo.crm.dao.CrmDBManager.listBySql(sqlStr);
		} catch (BusiException e) {
			System.err.println(e.getMessage());
		}

		if (rsList.size() > 0) {
			Map map = (Map) rsList.get(0);

			if (map != null) {
				ret[0] = Utility.trimNull(map.get("CUST_NAME")).trim();
				ret[1] = Utility.trimNull(map.get("CUST_TYPE")).trim();
			}
		}

		return ret;
	}

	/**
	 * 获取PARTN_NAME和 PARTN_TYPE2_FLAG(渠道/媒体)
	 *
	 * @param partn_id
	 * @return
	 */
	public static String[] getPartnerInfoById(Integer partn_id) {
		String[] ret = new String[2];
		String sqlStr = "SELECT PARTN_NAME,PARTN_TYPE2_FLAG"
				+ " FROM TPARTNER " + "WHERE PARTN_ID =" + partn_id;
		List rsList = null;
		try {
			rsList = enfo.crm.dao.CrmDBManager.listBySql(sqlStr);
		} catch (BusiException e) {
			System.err.println(e.getMessage());
		}

		if (rsList.size() > 0) {
			Map map = (Map) rsList.get(0);

			if (map != null) {
				ret[0] = Utility.trimNull(map.get("PARTN_NAME")).trim();
				ret[1] = Utility.trimNull(map.get("PARTN_TYPE2_FLAG")).trim();
			}
		}

		return ret;
	}
	

	/**
	 * 获得省级行政区域
	 *
	 * @param type_id
	 * @param type_value
	 * @param serial_no
	 * @return
	 * @throws Exception
	 */
	public static String getCustodianNameLis(Integer type_id,
			String type_value, Integer serial_no, String value)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TDICTPARAM_9999(?,?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		stmt.setInt(1, Utility.parseInt(type_id.intValue(), 0));
		stmt.setString(2, Utility.trimNull(type_value));
		stmt.setInt(3, Utility.parseInt(serial_no.intValue(), 0));
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				while (rs.next())
					appendOptions(sb, rs.getString("TYPE_VALUE"), rs
							.getString("TYPE_CONTENT"), value);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	// 产品期限单位类别
	public static String getPeriodValidUnitOptions(Integer flag)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		//appendOptions(sb, -1, "请选择", flag);
		appendOptions(sb, 0, "无固定限期", flag);
		appendOptions(sb, 1, "天", flag);
		appendOptions(sb, 2, "月", flag);
		appendOptions(sb, 3, "年", flag);

		return sb.toString();
	}

	public static String getChannelOptions(Integer bookCode,
			String channelCode, String channelName, Integer value)
			throws Exception {
		String listSql = "{call SP_QUERY_TCHANNEL(?,?,?,?)}";
		StringBuffer sb = newStringBuffer();
		Object[] params = new Object[4];
		List rsList = new ArrayList();

		params[0] = bookCode;
		params[1] = new Integer(0);
		params[2] = channelCode;
		params[3] = channelName;

		try {
			rsList = IntrustDBManager.listProcAll(listSql, params);
		} catch (BusiException e) {
			throw new Exception("渠道查询失败");
		}

		if (rsList.size() > 0) {
			HashMap rsMap = new HashMap();
			Integer r_channelID = new Integer(0);
			String r_channelCode = "";
			String r_channelName = "";
			Iterator it = rsList.iterator();

			while (it.hasNext()) {
				rsMap = (HashMap) it.next();
				r_channelID = Utility.parseInt(Utility.trimNull(rsMap
						.get("CHANNEL_ID")), new Integer(0));
				r_channelCode = Utility.trimNull(rsMap.get("CHANNEL_CODE"));
				r_channelName = Utility.trimNull(rsMap.get("CHANNEL_NAME"));

				appendOptions(sb, r_channelID, r_channelCode + " - "
						+ r_channelName, value);
			}
		}
		return sb.toString();
	}

	/**
	 * 根据CUST_ID 取得客户群组名称
	 *
	 * @param cust_id
	 * @return
	 * @throws BusiException
	 */
	public static String getCustGroups(Integer cust_id) throws BusiException {
		String listSql = "SELECT A.GroupName FROM TCustGroups A,TCustGroupMembers B WHERE A.GroupID = B.GroupID AND B.CUST_ID ="
				+ cust_id;
		List rsList = CrmDBManager.listBySql(listSql);
		StringBuffer custGroupNames = new StringBuffer("");
		String ret = "";

		if (rsList != null && rsList.size() > 0) {
			Iterator it = rsList.iterator();
			HashMap rsMap = new HashMap();

			while (it.hasNext()) {
				rsMap = (HashMap) it.next();

				custGroupNames.append(Utility.trimNull(rsMap.get("GroupName"))
						+ ",");
			}

			ret = custGroupNames.substring(0, custGroupNames.length() - 1);
		}

		return ret;

	}

	// 推介期及正常期产品
	public static String getProductOptions3(Integer book_code,
			Integer product_id, Integer op_code) throws Exception {
		return getProductListOptions(book_code, product_id, "", op_code, 11);
	}

	// 产品编号 列表
	public static String getPreProductCodeOptions(Integer book_code,
			Integer product_id, Integer op_code, int flag) throws Exception {
		String listSql = "{call SP_QUERY_TPRODUCT_LIST (?,?,?,?)}";

		StringBuffer sb = newStringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setString(2, "");
		stmt.setInt(3, flag);
		if (op_code == null)
			stmt.setInt(4, 0);
		else
			stmt.setInt(4, op_code.intValue());

		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				appendOptions(sb, rs.getInt("PRODUCT_ID"), rs
						.getString("PRODUCT_CODE"), product_id);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	/**
	 * 获得客户信息必填项
	 *
	 * @param menu_id
	 * @return
	 * @throws Exception
	 */
	public static List getRequiredItem(String menu_id) throws Exception {
		List list = new ArrayList();
		// 先获得该员工所属的责任组
		IntrustManagerManOperLocal local = EJBFactory
				.getIntrustManagerManOperLocal();
		Connection conn = IntrustDBManager.getConnection();
		Statement stmt = conn.createStatement();
		String listSql = "SELECT FIELD_NAME FROM TCHECKTABLE A, TCHECKDATA B WHERE A.MODULE_CODE='"
				+ menu_id
				+ "' AND A.SERIAL_NO=B.SERIAL_NO AND B.IS_FILLED=1";
		ResultSet rs = stmt.executeQuery(listSql);
		try {
			while (rs.next()) {
				list.add(Utility.trimNull(rs.getString("FIELD_NAME")));
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return list;
	}

	// 证件类型
	public static String getCardTypeName(String value) throws Exception {
		return getDictParamName(1108, value);
	}

	public static String getCardTypeJgName(String value) throws Exception {
		return getDictParamName(2108, value);
	}

	//客户信息--风险等级
	public static String getRiskGrade(String value) throws Exception {
		return getDictParamOptions2(3203, value);
	}

	/**
	 *
	 * add by tsg 2007-09-30
	 *
	 * 获取关联类型
	 *
	 */
	public static String getLinkTypeOptions(Integer flag) throws Exception {
		return getIntegerParamOptions(1302, flag);
	}

	/**
	 * 获取IntegerParam表中的参数 没有请选择 add by guifeng 2009.04 modi by nizh 20090629
	 * 改为有请选择（项目设置 后一个下拉选其他20，30 all catalog_flag会默认传1 和第一次进去传0 不一样）
	 */
	public static String getIntegerParamOptions(int type_id,
			Integer defaultValue) throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TINTEGERPARAM (?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, type_id);
		// 对应表下拉框应该从TINTEGERPARAM参数表统一获取, TYPE_ID
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {

				appendOptions(sb, rs.getInt("TYPE_VALUE"), rs
						.getString("CONTENT"), defaultValue);
			}

		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//	资产编号信息列表
	public static String getCapitalNoOptions_intrust(String capital_no,
			String capital_name, Integer input_man) throws Exception {
		StringBuffer sb = newStringBuffer();
		String listSql = "{call SP_QUERY_TCAPITALINFO (?,?,?,?,?,?,?,?,?,?,?,?)}";
		ResultSet rslist = null;
		Connection conn = null;
		CallableStatement stmt = null;
		try {
			conn = IntrustDBManager.getConnection();
			stmt = conn.prepareCall(listSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			stmt.setInt(1, 1);
			stmt.setInt(2, 0);
			stmt.setInt(3, 0);

			stmt.setString(4, "");
			stmt.setString(5, "");
			stmt.setString(6, capital_name);
			stmt.setString(7, "");
			stmt.setString(8, "");
			stmt.setInt(9, 0);

			stmt.setString(10, "");
			stmt.setInt(11, input_man.intValue());
			stmt.setString(12, capital_no);

			rslist = stmt.executeQuery();

			Integer serialno = new Integer(0);
			String totalvalue = "";
			if (rslist != null) {
				int icount = 0;
				while (rslist.next()) {
					String capitalno = rslist.getString("CAPITAL_NO");
					String capitalname = rslist.getString("CAPITAL_NAME");
					appendOptions(sb, capitalno, capitalname, capital_no);
				}
			}
		} finally {
			if (rslist != null)
				rslist.close();

			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	/**
	 * 授权集编号与名称
	 *
	 * @param bottom_flag
	 * @return
	 * @throws Exception
	 */
	public static String getAuthorization_Value(Integer bottom_flag, List list)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		Map map = null;
		String[] totalColumn = new String[0];

		sb.append("<option value=\"\">请选择</option>");
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			appendOptions(sb, Utility.parseInt(Utility.trimNull(map
					.get("CA_ID")), new Integer(0)), Utility.trimNull(map
					.get("CA_NAME")), bottom_flag);
		}
		return sb.toString();
	}

	/**
	 * 根据授权集编号取得授权集名称
	 *
	 * @param cust_id
	 * @return
	 * @throws BusiException
	 */
	public static String getAuthorizationName(Integer ca_id)
			throws BusiException {
		String listSql = "SELECT CA_NAME from TAuthorization where CA_ID ="
				+ ca_id;
		List rsList = CrmDBManager.listBySql(listSql);
		String authorizationName = "";
		HashMap rsMap = new HashMap();
		if (rsList != null && rsList.size() > 0) {
			Iterator it = rsList.iterator();
			rsMap = (HashMap) rsList.get(0);
			authorizationName = Utility.trimNull(rsMap.get("CA_NAME"));
		}
		return authorizationName;

	}

	/**
	 * 根据授权集编号取得授权集所属客户经理ID
	 *
	 * @param cust_id
	 * @return
	 * @throws BusiException
	 */
	public static Integer getAuthorizationManagerID(Integer ca_id)
			throws BusiException {
		String listSql = "SELECT ManagerID from TAuthorization where CA_ID ="
				+ ca_id;
		List rsList = CrmDBManager.listBySql(listSql);
		Integer managerID = new Integer(0);
		HashMap rsMap = new HashMap();
		if (rsList != null && rsList.size() > 0) {
			Iterator it = rsList.iterator();
			rsMap = (HashMap) rsList.get(0);
			managerID = Utility.parseInt(Utility.trimNull(rsMap
					.get("ManagerID")), new Integer(0));
		}
		return managerID;

	}

	/**
	 * 根据客户经理编号取得客户经理名称
	 *
	 * @param cust_id
	 * @return
	 * @throws BusiException
	 */
	//	public static String getManagerName(Integer managerID) throws
	// BusiException{
	//		String listSql = "SELECT ManagerName from TCustManagers where ManagerID
	// ="+managerID;
	//		List rsList = CrmDBManager.listBySql(listSql);
	//		String managerName = "";
	//		HashMap rsMap = new HashMap();
	//		if(rsList!=null&&rsList.size()>0){
	//			Iterator it = rsList.iterator();
	//			rsMap=(HashMap)rsList.get(0);
	//			managerName=Utility.trimNull(rsMap.get("ManagerName"));
	//		}
	//		return managerName;
	//
	//	}
	/**
	 * 取得启用状态名称
	 *
	 * @param cust_id
	 * @return
	 * @throws BusiException
	 */
	public static String getStatusName(Integer status) throws BusiException {
		String statusName = "";
		if (status.intValue() == 1) {
			statusName = "已启用";
		} else {
			statusName = "已禁用";
		}
		return statusName;

	}

	/**
	 * 查询出该客户经理(当前操作员)的所有同级或者下级经理，包括所有同一级别或者下一级别的主经理，不包括自身
	 *
	 * @param bottom_flag
	 * @return
	 * @throws Exception
	 */
	public static String getManagerListFromTCustManagerTree(Integer input_man,
			List list) throws Exception {
		StringBuffer sb = new StringBuffer();
		Map map = null;

		sb.append("<option value=\"\">请选择</option>");
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			appendOptions(sb, Utility.parseInt(Utility.trimNull(map
					.get("MANAGERID")), new Integer(0)), Utility.trimNull(map
					.get("MANAGERNAME")), input_man);
		}
		return sb.toString();
	}

	/**
	 * 从TCustManagers表中获得客户经理的值，生成下拉框，因为和TCustManagerTree里面的字段名不一样，所以不得以又增加一个方法
	 *
	 * @param bottom_flag
	 * @return
	 * @throws Exception
	 */
	public static String getManagerListFromTCustManagers(Integer input_man,
			List list) throws Exception {
		StringBuffer sb = new StringBuffer();
		Map map = null;

		sb.append("<option value=\"\">请选择</option>");
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			appendOptions(sb, Utility.parseInt(Utility.trimNull(map
					.get("ManagerID")), new Integer(0)), Utility.trimNull(map
					.get("ManagerName")), input_man);
		}
		return sb.toString();
	}

	/**
	 * 从TCustManagers表中获得客户经理的值，生成下拉框，因为和TCustManagerTree里面的字段名不一样，所以不得以又增加一个方法
	 *
	 * @param bottom_flag
	 * @return
	 * @throws Exception
	 */
	public static String getCustClassDefine(Integer classDefineID, List list)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		Map map = null;

		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			appendOptions(sb, Utility.parseInt(Utility.trimNull(map
					.get("CLASSDEFINE_ID")), new Integer(0)), Utility
					.trimNull(map.get("CLASSDEFINE_NAME")), classDefineID);
		}
		return sb.toString();
	}

	public static String getAttachmentTypeValue(String value) throws Exception {
		return getAttachmentTypeDictParamOptions(1010, value);
	}

	public static String getAttachmentTypeDictParamOptions(int dict,
			String value) throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TDICTPARAM(?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, dict);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				appendOptions(sb, (rs.getString("TYPE_VALUE").substring(4, 6)),
						rs.getString("TYPE_CONTENT"), value);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	public static String getNonProductValue(String value) throws Exception {
		return getDictParamOptions(1155, value);
	}

	/**
	 * 获得非信托产品名称
	 *
	 * @author my
	 * @since 2011-05-13
	 * @param
	 * @return ret;
	 */
	public static String getNonProductNameByCustId(Integer nonproduct_id)
			throws Exception {
		String strSql = "SELECT NONPRODUCT_NAME FROM TNONPRODUCT WHERE NONPRODUCT_ID = "
				+ nonproduct_id;
		String ret = "";
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(strSql);
		try {
			while (rs.next())
				ret = Utility.trimNull(rs.getString("NONPRODUCT_NAME"));
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return ret;
	}

	public static String getValidPriodUnit(Integer q_valid_priod_unit)
			throws Exception {
		String vaidPriodUnit = "";
		switch (q_valid_priod_unit.intValue()) {
		case 1:
			vaidPriodUnit = "天";
			break;
		case 2:
			vaidPriodUnit = "月";
			break;
		case 3:
			vaidPriodUnit = "季";
			break;
		case 4:
			vaidPriodUnit = "年";
			break;
		default:
			vaidPriodUnit = "无";
			break;
		}
		return vaidPriodUnit;
	}

	public static String getFormatDictParamOptions(BigDecimal date)
			throws Exception {
		BigDecimal bigLoanAmount = new BigDecimal("");
		BigDecimal q_expect_rate = bigLoanAmount.multiply(date);
		NumberFormat percent = NumberFormat.getPercentInstance();
		//建立百分比格式化引用
		return percent.format(q_expect_rate);
	}

	//非信托产品名称
	public static String getNonproductName(Integer value) throws Exception {
		String strSql = "SELECT * FROM TNONPRODUCT WHERE CHECK_FLAG = 2";
		StringBuffer sb = new StringBuffer();
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(strSql);
		try {
			while (rs.next()) {
				appendOptions(sb, Utility.parseInt(rs
						.getString("NONPRODUCT_ID"), new Integer(0)), rs
						.getString("NONPRODUCT_NAME"), value);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	public static String getBankID(String value) throws Exception {
		return getDictParamOptions_intrust(1803, value);
	}

	//	非信托合同状态
	public static String getNonproductStatusOptions(String value)
			throws Exception {
		return getDictParamOptions(1201, value);
	}

	//	非信托有有受益状态
	public static String getQuotientStatusOptions(String value)
			throws Exception {
		return getDictParamOptions(1211, value);
	}

	//	非信托有有收益状态
	public static String getProfitStatusOptions(String value) throws Exception {
		return getDictParamOptions(1116, value);
	}

	//非信托合同编号
	public static String getSubscribeBh(String value, Integer nonproductId)
			throws Exception {
		String strSql = "SELECT * FROM TSUBSCRIBE WHERE (NONPRODUCT_ID = "
				+ nonproductId + ") AND (CHECK_FLAG = 2)";
		StringBuffer sb = new StringBuffer();
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(strSql);
		try {
			while (rs.next()) {
				appendOptions(sb, rs.getString("SUBSCRIBE_ID"), rs
						.getString("SUBSCRIBE_BH"), value);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	// 非信托产品期限单位类别
	public static String getPeriodValidUnitOptions1(Integer flag)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "无固定限期", flag);
		appendOptions(sb, 1, "天", flag);
		appendOptions(sb, 2, "月", flag);
		appendOptions(sb, 3, "季", flag);
		appendOptions(sb, 4, "年", flag);

		return sb.toString();
	}

	// 变动业务类别
	public static String getChgType(Integer flag) {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, new Integer(1), "认购", flag);
		appendOptions(sb, new Integer(2), "申购", flag);
		appendOptions(sb, new Integer(3), "赎回", flag);
		appendOptions(sb, new Integer(4), "份额转增", flag);
		return sb.toString();
	}
	
	// 变动业务类别
	public static String getChgType1(Integer flag) {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, new Integer(1), "认购", flag);
		appendOptions(sb, new Integer(2), "申购", flag);
		return sb.toString();
	}

	//	非信托有有收益状态
	public static String getChannelname(String value) throws Exception {
		return getDictContent(value);
	}

	//渠道合作方式
	public static String getChanneCooperationName(String value)
			throws Exception {
		return getDictContentIntrust(value);
	}

	// 汇总方式
	public static String getGroup_type(Integer flag) {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, new Integer(1), "按周", flag);
		appendOptions(sb, new Integer(2), "按月", flag);
		appendOptions(sb, new Integer(3), "按季", flag);
		appendOptions(sb, new Integer(4), "按年", flag);
		return sb.toString();
	}

	// 是否为汇总产品
	public static String getGroupProduct(Integer flag) {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, new Integer(1), "是", flag);
		appendOptions(sb, new Integer(2), "否", flag);
		return sb.toString();
	}

	public static String getChannel(Integer type_value) throws Exception {
		if (type_value == null)
			return null;
		String ret = "";
		Connection conn = IntrustDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt
				.executeQuery("SELECT * FROM TCHANNEL WHERE CHANNEL_ID="
						+ type_value);
		try {
			if (rs.next())
				ret = rs.getString("CHANNEL_NAME");
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return ret;
	}

	public static String getTeam(Integer type_value) throws Exception {
		if (type_value == null)
			return null;
		String ret = "";
		Integer value = Utility.parseInt(type_value, null);
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt
				.executeQuery("SELECT A.TEAM_ID FROM TMANAGERTEAMS A, TMANAGERTEAMMEMBERS B WHERE A.TEAM_ID=B.TEAM_ID AND (LEADER = "
						+ value + " OR MANAGERID = " + value + ")");
		try {
			if (rs.next())
				ret = rs.getString("TEAM_ID");
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return ret;
	}
	
	//渠道列表
	public static String getChannelName(Integer value) throws Exception {
		String strSql = "SELECT * FROM TCHANNEL";
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=\"0\">请选择</option>");
		Connection conn = IntrustDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(strSql);
		try {
			while (rs.next()) {
				appendOptions(sb, rs.getInt("CHANNEL_ID"), rs
						.getString("CHANNEL_NAME"), value);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//	 资产来源
	public static String getZclbFlagName(Integer flag) {
		String retValue = "非信托资产";
		if (flag == null)
			return retValue;
		if (flag.intValue() == 1)
			retValue = "信托资产";
		if (flag.intValue() == 3)
			retValue = "非信托资产";
		return retValue;
	}

	//团队列表
	public static String getTeamName(Integer value) throws Exception {
		String strSql = "{call SP_QUERY_TMANAGERTEAMS(?,?,?,?,?,?)}";
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=\"0\">请选择</option>");
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(strSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, 0);
		stmt.setString(2, "");
		stmt.setString(3, "");
		stmt.setInt(4, 0);
		stmt.setInt(5, 0);
		stmt.setString(6, "");
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				appendOptions(sb, rs.getInt("TEAM_ID"), rs
						.getString("TEAM_NAME"), value);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	public static String getProductName1(Integer value) throws Exception {
		String strSql = "SELECT * FROM TPRODUCT WHERE PRODUCT_STATUS = '110202' AND INTRUST_FLAG1 = 2";
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=\"0\">请选择</option>");
		Connection conn = IntrustDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(strSql);
		try {
			while (rs.next()) {
				appendOptions(sb, rs.getInt("PRODUCT_ID"), rs
						.getString("PRODUCT_NAME"), value);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	public static String getProductProvFlag(Integer product_id,
			Integer sub_product_id, Integer value) throws Exception {

		StringBuffer sb = new StringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("{CALL SP_QUERY_TGAINLEVELFLAG("
				+ product_id + "," + sub_product_id + ")}");

		try {
			while (rs.next()) {
				appendOptions(sb, rs.getInt("PROV_FLAG"), rs
						.getString("PROV_FLAG_NAME"), value);
			}
		} catch (Exception e) {
			throw new BusiException("查询产品已设置受益优先级别失败" + e.getMessage());
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}

		return sb.toString();

	}

	public static String getProductProvLevel(Integer product_id,
			Integer sub_product_id, Integer prov_flag, String defaultValue)
			throws Exception {

		StringBuffer sb = new StringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs =
			stmt.executeQuery("{CALL SP_QUERY_TGAINLEVELPROV("+ product_id +","+ sub_product_id +","+ prov_flag +")}");
		try {
			while (rs.next()) {
				appendOptions(sb, rs.getString("PROV_LEVEL"), rs
						.getString("PROV_LEVEL_NAME"), defaultValue);
			}
		} catch (Exception e) {
			throw new BusiException("查询产品已设置收益级别失败" + e.getMessage());
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}

		return sb.toString();
	}

	public static String getPreproductProvFlag(Integer preproduct_id)
			throws Exception {

		StringBuffer sb = new StringBuffer();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		sb.append("[");
		
		try {
			conn = CrmDBManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("{CALL SP_QUERY_TGAINLEVELFLAG("+ preproduct_id +")}"); 
			
			while (rs.next()) {
				if (sb.length() > 1) sb.append(",");
				
				sb.append("{\"PROV_FLAG\":");
				sb.append(rs.getString("PROV_FLAG"));
				sb.append(",\"PROV_FLAG_NAME\":\"");
				sb.append(rs.getString("PROV_FLAG_NAME"));
				sb.append("\"}");
			}
			sb.append("]");
			return sb.toString();
			
		} catch (Exception e) {
			throw new BusiException("查询预发行产品已设置受益优先级失败" + e.getMessage());
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (conn!=null) conn.close();
		}		
	}
	

	/**
	 * @param product_id
	 * @return
	 */
	public static String getProductProvFlag(Integer product_id) throws Exception{
		StringBuffer sb = new StringBuffer();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		sb.append("[");
		
		try {
			conn = CrmDBManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("{CALL SP_QUERY_TGAINLEVELFLAGBYPROID("+ product_id +")}"); 
			
			while (rs.next()) {
				if (sb.length() > 1) sb.append(",");
				
				sb.append("{\"PROV_FLAG\":");
				sb.append(rs.getString("PROV_FLAG"));
				sb.append(",\"PROV_FLAG_NAME\":\"");
				sb.append(rs.getString("PROV_FLAG_NAME"));
				sb.append("\"}");
			}
			sb.append("]");
			return sb.toString();
			
		} catch (Exception e) {
			throw new BusiException("查询产品已设置受益优先级失败" + e.getMessage());
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (conn!=null) conn.close();
		}
	}
	
	public static String getPreproductProvLevel(Integer preproduct_id, Integer prov_flag, Integer input_man)
			throws Exception {

		StringBuffer sb = new StringBuffer();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		sb.append("[");
		
		try {
			conn = CrmDBManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("{CALL SP_QUERY_TGAINLEVEL(0,"+ preproduct_id +","+ prov_flag +",'',"+input_man+")}"); 
			
			while (rs.next()) {
				if (sb.length() > 1) sb.append(",");
				
				sb.append("{\"PROV_FLAG\":");
				sb.append(rs.getString("PROV_FLAG"));
				sb.append(",\"PROV_FLAG_NAME\":\"");
				sb.append(rs.getString("PROV_FLAG_NAME"));
				sb.append("\",\"PROV_LEVEL\":\"");
				sb.append(rs.getString("PROV_LEVEL"));
				sb.append("\",\"PROV_LEVEL_NAME\":\"");
				sb.append(rs.getString("PROV_LEVEL_NAME"));
				sb.append("\",\"LOWER_LIMIT\":");
				sb.append(rs.getString("LOWER_LIMIT"));
				sb.append(",\"UPPER_LIMIT\":");
				sb.append(rs.getString("UPPER_LIMIT"));
				sb.append(",\"GAIN_RATE\":");
				sb.append(rs.getString("GAIN_RATE"));
				sb.append("}");
			}
			
			sb.append("]");
			return sb.toString();
			
		} catch (Exception e) {
			throw new BusiException("查询预发行产品已设置收益级别失败" + e.getMessage());
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (conn!=null) conn.close();
		}		
	}
	
	
	public static String getProductProvLevel(Integer product_id, Integer prov_flag, Integer input_man) throws Exception {
		StringBuffer sb = new StringBuffer();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		sb.append("[");
		
		try {
			conn = CrmDBManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("{CALL SP_QUERY_TGAINLEVELBYPROID(0,"+ product_id +","+ prov_flag +",'',"+input_man+")}"); 
			
			while (rs.next()) {
				if (sb.length() > 1) sb.append(",");
				
				sb.append("{\"PROV_FLAG\":");
				sb.append(rs.getString("PROV_FLAG"));
				sb.append(",\"PROV_FLAG_NAME\":\"");
				sb.append(rs.getString("PROV_FLAG_NAME"));
				sb.append("\",\"PROV_LEVEL\":\"");
				sb.append(rs.getString("PROV_LEVEL"));
				sb.append("\",\"PROV_LEVEL_NAME\":\"");
				sb.append(rs.getString("PROV_LEVEL_NAME"));
				sb.append("\",\"LOWER_LIMIT\":");
				sb.append(rs.getString("LOWER_LIMIT"));
				sb.append(",\"UPPER_LIMIT\":");
				sb.append(rs.getString("UPPER_LIMIT"));
				sb.append(",\"GAIN_RATE\":");
				sb.append(rs.getString("GAIN_RATE"));
				sb.append("}");
			}
			
			sb.append("]");
			return sb.toString();
			
		} catch (Exception e) {
			throw new BusiException("查询产品已设置收益级别失败" + e.getMessage());
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (conn!=null) conn.close();
		}		
	}
	
	public static String getProductAsfundFlag(Integer product_id,
			Integer input_man) throws Exception {

		String ret = "";
		Connection conn = IntrustDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt
				.executeQuery("SELECT ASFUND_FLAG FROM TPRODUCTLIMIT WHERE PRODUCT_ID="
						+ product_id);
		//"{CALL SP_ADD_TPRODUCTLIMIT("+ product_id
		// +",0,0,0,0,0,0,2,"+input_man+")}");

		try {
			while (rs.next()) {
				ret = rs.getString("ASFUND_FLAG");
			}
		} catch (Exception e) {
			throw new BusiException("查询产品已设置受益优先级别失败" + e.getMessage());
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return ret;

	}

	//	民族
	public static String getNationOptions(String value) throws Exception {
		return getDictParamOptions(1161, value);
	}

	//健康状况
	public static String getHealthOptions(String value) throws Exception {
		return getDictParamOptions(1162, value);
	}

	//教育程度
	public static String getEducationOptions(String value) throws Exception {
		return getDictParamOptions(1163, value);
	}

	//单位性质
	public static String getCompanyCharacterOptions(String value)
			throws Exception {
		return getDictParamOptions(1164, value);
	}

	//其他投资状况(1165)
	public static String getOtherInvestmentOptions(String value)
			throws Exception {
		return getDictParamOptions(1165, value);
	}

	//类型偏好(1166)
	public static String getTypePrefOptions(String value) throws Exception {
		return getDictParamOptions(1166, value);
	}

	//投向偏好
	public static String getInvestPrefOptions(String value) throws Exception {
		return getDictParamOptions(1167, value);
	}

	//服务偏好(1169)
	public static String getServicePrefOptions(String value) throws Exception {
		return getDictParamOptions(1169, value);
	}

	//兴趣偏好
	public static String getHobbyPrefOptions(String value) throws Exception {
		return getDictParamOptions(1168, value);
	}

	//期限偏好(1170)
	public static String getTimeLimitPrefOptions(String value) throws Exception {
		return getDictParamOptions(1170, value);
	}

	//风险偏好(1172)
	public static String getRiskPrefOptions(String value) throws Exception {
		return getDictParamOptions(1172, value);
	}

	public static String getContractLinkMan(Integer pre_serial_no)
			throws Exception {

		String ret = "";
		Connection conn = IntrustDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt
				.executeQuery("SELECT LINK_MAN FROM TPRECONTRACT WHERE SERIAL_NO = "
						+ pre_serial_no);

		try {
			while (rs.next()) {
				ret = rs.getString("LINK_MAN");
			}
		} catch (Exception e) {
			throw new BusiException("查询合同销售人员失败" + e.getMessage());
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return ret;

	}

	/**
	 * 获得配置信息-无选择项
	 *
	 * @param type_id
	 * @param defaultValue
	 * @return
	 * @throws Exception
	 */
	public static String getTableOptions2(int type_id, Integer defaultValue)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TINTEGERPARAM (?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, type_id);
		// 对应表下拉框应该从TINTEGERPARAM参数表统一获取, TYPE_ID = 1101
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {

				appendOptions(sb, rs.getInt("TYPE_VALUE"), rs
						.getString("CONTENT"), defaultValue);
			}

		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	/*
	 *
	 */
	public static String getCRMProductListOptions(Integer sale_status,
			Integer product_id, String product_status, Integer open_flag,
			Integer input_man) throws Exception {
		String listSql = "{call SP_QUERY_TPRODUCT_LIST (?,?,?,?)}";

		StringBuffer sb = new StringBuffer();

		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, sale_status.intValue());

		stmt.setString(2, product_status);
		stmt.setInt(3, open_flag.intValue());
		stmt.setInt(4, input_man.intValue());
		ResultSet rs = stmt.executeQuery();
		try {
			appendOptions(sb, 0, "全部产品", product_id);
			while (rs.next()) {
				appendOptions(sb, rs.getInt("PRODUCT_ID"), rs
						.getString("PRODUCT_CODE")
						+ "-" + rs.getString("PRODUCT_NAME"), product_id);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//产品--风险等级
	public static String getProductRiskGrade(String value) throws Exception {
		return getDictParamOptions2(1172, value);
	}

	/**
	 * 项目组编号与名称
	 *
	 * @param bottom_flag
	 * @return
	 * @throws Exception
	 */
	public static String getTeam_Value(Integer bottom_flag) throws Exception {
		StringBuffer sb = new StringBuffer();
		Map map = null;
		TcoTeamInfoVO vo = new TcoTeamInfoVO();
		TcoTeamInfoLocal tcoTeamInfoLocal = EJBFactory.getTcoTeamInfo();
		List list = tcoTeamInfoLocal.queryByList(vo);

		sb.append("<option value=\"\">请选择</option>");
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			appendOptions(sb, Utility.parseInt(Utility.trimNull(map
					.get("TEAM_ID")), new Integer(0)), Utility.trimNull(map
					.get("TEAM_NAME")), bottom_flag);
		}
		return sb.toString();
	}

	public static String getProductFlag(Integer product_id) throws Exception {
		
		String value="";
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn
				.prepareCall("{CALL SP_QUERY_TPRODUCT_ID(?)}");
		stmt.setInt(1, product_id.intValue());
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			value = Utility.trimNull(rs.getString("SUB_FLAG"))
					+"$"+Utility.trimNull(rs.getString("INTRUST_FLAG1"))
					+"$"+Utility.trimNull(rs.getString("ASFUND_FLAG"))
					+"$"+Utility.trimNull(rs.getString("OPEN_FLAG"))
					+"$"+Utility.trimNull(rs.getString("IS_LOCAL"))
					+"$"+Utility.trimNull(rs.getString("PRE_START_DATE"))
					+"$"+Utility.trimNull(rs.getString("PRE_END_DATE"))
					+"$"+Utility.trimNull(rs.getString("PRODUCT_STATUS"))
					+"$"+Utility.trimNull(rs.getString("PRE_CODE"));
		}
		rs.close();
		stmt.close();
		conn.close();
		return value;
	}

	/**
	 * add by jiangyp 2011-05-24查询字产品列表 SP_QUERY_TSUBPRODUCT
	 *
	 * @IN_PRODUCT_ID INT,
	 * @IN_SUB_PRODUCT_ID INT = 0 ,
	 * @IN_LIST_ID INT = 0 ,
	 * @IN_LIST_NAME NVARCHAR(60) = '', --子产品名称
	 * @IN_PRODUCT_CODE VARCHAR(6) = '', --产品编号
	 * @IN_PRODUCT_NAME NVARCHAR(60) = '', --产品名称
	 * @IN_CHECK_FLAG INT = 0, --
	 * @IN_INTRUST_TYPE1 NVARCHAR(20) = '' --信托财产类型(1138),查全部财产权子产品时传(113899)
	 *                   根据信托财产类型查询子产品
	 */
	public static String getSubProductOptions2(Integer product_id,
			Integer sub_product_id, Integer defalt_sub_product_id,
			int check_flag, String intrust_type1) throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TSUBPRODUCT(?,?,?,?,?,?,?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		product_id = Utility.parseInt(Utility.trimNull(product_id),
				new Integer(0));
		sub_product_id = Utility.parseInt(Utility.trimNull(sub_product_id),
				new Integer(0));
		stmt.setInt(1, product_id.intValue());
		stmt.setInt(2, sub_product_id.intValue());
		stmt.setInt(3, 0);
		stmt.setString(4, "");
		stmt.setString(5, "");
		stmt.setString(6, "");
		stmt.setInt(7, check_flag);
		stmt.setString(8, intrust_type1);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				appendOptions(sb, rs.getInt("SUB_PRODUCT_ID"), rs
						.getString("LIST_NAME"), defalt_sub_product_id);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	/**
	 * 获取CHANNEL_NAME(银行代销)
	 *
	 * @param partn_id
	 * @return
	 */
	public static String[] getChannelInfoById(Integer channel_id) {
		String[] ret = new String[2];
		String sqlStr = "SELECT CHANNEL_ID,CHANNEL_NAME" + " FROM TCHANNEL "
				+ "WHERE CHANNEL_ID =" + channel_id;
		List rsList = null;
		try {
			rsList = enfo.crm.dao.IntrustDBManager.listBySql(sqlStr);
		} catch (BusiException e) {
			System.err.println(e.getMessage());
		}

		if (rsList.size() > 0) {
			Map map = (Map) rsList.get(0);

			if (map != null) {
				ret[0] = Utility.trimNull(map.get("CHANNEL_NAME")).trim();
			}
		}

		return ret;
	}

	//渠道类别
	public static String getChannelTypeName(String value) throws Exception {
		return getDictContent(value);
	}

	// 信托类型
	public static String getIntrustTypeOptions(String value) throws Exception {
		return getDictParamOptions(1104, value);
	}

	//	 受益状态标志，目前只有受益人查询中用到，去掉“冻结、质押”
	public static String getBenStatusOptions(String value) throws Exception {
		StringBuffer sb = new StringBuffer(200);
		appendOptions(sb, "121101", "正常", value);
		appendOptions(sb, "121102", "挂失", value);
		appendOptions(sb, "121104", "中止", value);
		appendOptions(sb, "121105", "到期结算", value);
		return sb.toString();
	}

	/**
	 * ADD BY TSG 2011-09-21 评分科目列表
	 *
	 */
	public static String getScoreSubjectOptions(Integer subject_id)
			throws Exception {

		String listSql = "SELECT * FROM TSCORESUBJECT";
		StringBuffer sb = new StringBuffer();
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(listSql);
		sb.append("<option value=\"0\">请选择</option>");

		try {
			while (rs.next())
				appendOptions(sb, rs.getInt("SUBJECT_ID"), rs
						.getString("SUBJECT_NO")
						+ "_" + rs.getString("SUBJECT_NAME"), subject_id);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	/**
	 * ADD BY TSG 2011-09-21 打分项列表
	 *
	 */
	public static String getOperandOptions(Integer operand_id, Integer scoring,
			Integer source) throws Exception {
		String listSql = "";
		if (scoring.intValue() != 0 && source != null)
			listSql = "SELECT * FROM TSCOREOPERAND WHERE SCORING=" + scoring
					+ " AND SOURCE=" + source;
		else
			listSql = "SELECT * FROM TSCOREOPERAND";
		StringBuffer sb = new StringBuffer();
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(listSql);
		sb.append("<option value=\"0\">请选择</option>");

		try {
			while (rs.next())
				appendOptions(sb, rs.getInt("OPERAND_ID"), rs
						.getString("OPERAND_NO")
						+ "_" + rs.getString("OPERAND_NAME"), operand_id);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	/**
	 * ADD BY TSG 2011-09-21 数据来源表
	 *
	 */
	public static String getSourceTable(String table_name) throws Exception {
		String listSql = "SELECT TABLE_NAME,TABLE_NAME_CN FROM TDataTABLE GROUP BY TABLE_NAME,TABLE_NAME_CN";

		StringBuffer sb = new StringBuffer();
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(listSql);
		sb.append("<option value=\"0\">请选择</option>");

		try {
			while (rs.next())
				appendOptions(sb, rs.getString("TABLE_NAME"), rs
						.getString("TABLE_NAME_CN"), table_name);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	/**
	 * ADD BY TSG 2011-09-21 数据来源字段
	 *
	 */
	public static String getSourceField(String table_name, String field_name)
			throws Exception {
		String listSql = "SELECT * FROM TDataFIELD WHERE TABLE_NAME = '"
				+ table_name + "'";

		StringBuffer sb = new StringBuffer();
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(listSql);
		sb.append("<option value=\"0\">请选择</option>");

		try {
			while (rs.next())
				appendOptions(sb, rs.getString("FIELD_NAME"), rs
						.getString("FIELD_NAME_CN"), field_name);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	/**
	 * ADD BY TSG 2011-09-21 人工打分选项
	 *
	 */
	public static String getManualScoring(Integer operand_id, String value)
			throws Exception {
		String listSql = "SELECT * FROM TMANUALSCORING WHERE OPERAND_ID = '"
				+ operand_id + "'";

		StringBuffer sb = new StringBuffer();
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(listSql);
		sb.append("<option value=\"0\">请选择</option>");

		try {
			while (rs.next())
				appendOptions(sb, rs.getString("SCORE"), rs
						.getString("OPERATION_VALUE")
						+ "--" + rs.getString("SCORE") + "分", value);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	/**
	 * 产品分类
	 *
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String getClassType1Options(String value) throws Exception {
		return getDictParamOptions(1213, value);
	}

	/**
	 * 销售状态
	 *
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String getPre_status(String value) throws Exception {
		return getDictParamOptions(1102, value);
	}

	/**
	 * 根据知识库编号获得知识库分类名称
	 *
	 * @author guifeng
	 * @since 2011-12-23
	 * @param faq_class_no
	 * @param input_man
	 * @return
	 * @throws Exception
	 */
	public static String getFaqClassName(String faq_class_no, Integer input_man)
			throws Exception {
		String name = null;
		FaqsVO vo = new FaqsVO();
		FaqsLocal faqs = EJBFactory.getFaqs();
		vo.setFaq_class_no(faq_class_no);
		vo.setInput_man(input_man);
		List list = faqs.listFaqClass(vo);
		Map map = new HashMap();
		if (list != null && list.size() > 0) {
			map = (Map) list.get(0);
			name = Utility.trimNull(map.get("FAQ_CLASS_NAME"));
		}
		return name;
	}

	/**
	 *
	 * @author guifeng
	 * @since 2011-12-26
	 * @return
	 * @throws Exception
	 */
	public static String getPreProductListOptions(Integer pre_product_id,
			String producut_name, String status, Integer input_man)
			throws Exception {
		String listSql = "{call SP_QUERY_TPREPRODUCT (?,?,?,?)}";

		StringBuffer sb = new StringBuffer();

		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, 0);

		stmt.setString(2, producut_name);
		stmt.setString(3, status);
		stmt.setInt(4, input_man.intValue());
		ResultSet rs = stmt.executeQuery();
		try {
			appendOptions(sb, 0, "请选择", pre_product_id);
			while (rs.next()) {
				appendOptions(sb, rs.getInt("PREPRODUCT_ID"), rs
						.getString("PREPRODUCT_ID")
						+ "-" + rs.getString("PREPRODUCT_NAME"), pre_product_id);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	/**
	 *
	 * 由产品id获取预发行产品id
	 */
	public static Integer getPreProduct_id(Integer product_id) throws Exception {
		Integer pre_product_id = null;
		if (product_id == null || product_id.intValue() == 0)
			return null;
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = null;
		try {
			rs = stmt
					.executeQuery("SELECT PREPRODUCT_ID FROM TPREPRODUCT WHERE BIND_PRODUCT_ID = "
							+ product_id);
			while (rs.next()) {
				pre_product_id = Utility.parseInt(Utility.trimNull(rs
						.getString("PREPRODUCT_ID")), new Integer(0));
				break;
			}
		} catch (Exception e) {
			throw new BusiException("读取预发行信息失败:" + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
		return pre_product_id;
	}
	
	/**
	 *
	 * 由预发行产品id获取预发行产品名称
	 */
	public static String getPreProduct_name(Integer preproduct_id) throws Exception {
		String pre_product_name = null;
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = null;
		try {
			rs = stmt
					.executeQuery("SELECT PREPRODUCT_NAME FROM TPREPRODUCT WHERE PREPRODUCT_ID = "
							+ preproduct_id);
			while (rs.next()) {
				pre_product_name = Utility.trimNull(rs.getString("PREPRODUCT_NAME"));
				break;
			}
		} catch (Exception e) {
			throw new BusiException("读取预发行信息失败:" + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
		return pre_product_name;
	}
	

	/**
	 *
	 * 由预发行产品id获取预发行绑定子产品id
	 */
	public static Integer getBinsupByid(Integer preproduct_id) throws Exception {
		Integer bind_subproduct_id = null;
		if(preproduct_id==null || preproduct_id.intValue()==0){
			return bind_subproduct_id;
		}
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = null;
		try {
			rs = stmt
					.executeQuery("SELECT * FROM TPREPRODUCT WHERE PREPRODUCT_ID = "
							+ preproduct_id);
			while (rs.next()) {
				bind_subproduct_id = Utility.parseInt(Utility.trimNull(rs.getString("BIND_SUB_PRODUCT_ID")),new Integer(0));
				break;
			}
		} catch (Exception e) {
			throw new BusiException("读取预发行信息失败:" + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
		return bind_subproduct_id;
	}

	public static String getContractList(Integer book_code, Integer product_id,
			Integer sub_product_id, Integer cust_id, String value)
			throws Exception {

		StringBuffer sb = newStringBuffer();

		appendOptions(sb, "new", "新建合同", value);

		Connection conn = IntrustDBManager.getConnection();
		String querySql = "{call SP_QUERY_TCONTRACT1(?,?,?,?)}";
		CallableStatement stmt = conn.prepareCall(querySql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		stmt.setInt(1, Utility.parseInt(Utility.trimNull(book_code), 0));
		stmt.setInt(2, Utility.parseInt(Utility.trimNull(product_id), 0));
		stmt.setInt(3, Utility.parseInt(Utility.trimNull(sub_product_id), 0));
		stmt.setInt(4, Utility.parseInt(Utility.trimNull(cust_id), 0));

		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				appendOptions(sb, Utility.trimNull(rslist
						.getString("SERIAL_NO"), "0")
						+ "$"
						+ Utility.trimNull(rslist.getString("CONTRACT_BH")),
						"追加到合同编号"
								+ Utility.trimNull(rslist
										.getString("CONTRACT_SUB_BH"))
								+ "受益份额为"
								+ Utility.trimNull(rslist
										.getString("BEN_AMOUNT")) + "的合同",
						value);
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}

		return sb.toString();
	}

	public static String getRequiredItemJson(String menu_id, String busi_type,
			Integer is_filled) throws Exception {
		List list = new ArrayList();
		Connection conn = IntrustDBManager.getConnection();
		String listSql = "SELECT * FROM TCHECKDATA WHERE MODULE_CODE = ? AND BUSI_TYPE = ? AND IS_FILLED = ?";
		PreparedStatement stmt = conn.prepareStatement(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setString(1, menu_id);
		stmt.setString(2, busi_type);
		stmt.setInt(3, is_filled.intValue());
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				Map map = new HashMap();
				map.put("FIELD_NAME", Utility.trimNull(rs
						.getString("FIELD_NAME")));
				map.put("DATA_TYPE", Utility
						.trimNull(rs.getString("DATA_TYPE")));
				map.put("MIN_LEN", Utility.trimValue(rs.getString("MIN_LEN"),
						"0"));
				map.put("MAX_LEN", Utility.trimNull(rs.getString("MAX_LEN")));
				map.put("ERRORMSG", Utility.trimNull(rs.getString("ERRORMSG")));
				list.add(map);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return JsonUtil.object2json(list);
	}

	/**
	 * 单纯的发送短信
	 *
	 * @param smsUser  插入用户
	 * @param phoneNumber 接收短信手机号码
	 * @param smsContent 发送短信内容
	 * @param sendLevel 发送级别
	 * @param putType 提交方式
	 * @param serialNo_details 明细任务ID
	 * @param input_man 操作员
	 * @return
	 * @throws Exception
	 */
	public static String sendSMSSimple(String smsUser, String phoneNumber,
			String smsContent, Integer sendLevel, String putType,
			Integer serialNo_details, Integer input_man) throws Exception {
		Integer smsIndex = new Integer(0);
		String[] sendBackInfo = null;
		String s_result = "";
		try {
			SmsRecordLocal smsRecordLocal = EJBFactory.getSmsRecord();
			//1.保存短信记录
			SendSMSVO vo_sms = new SendSMSVO();
			vo_sms.setSmsUser(smsUser);
			vo_sms.setPhoneNumber(phoneNumber);
			vo_sms.setSmsContent(smsContent);
			vo_sms.setSendLevel(sendLevel);
			vo_sms.setPutType(putType);
			vo_sms.setSerial_no_detail(serialNo_details);
			vo_sms.setInputOperator(input_man);
			smsIndex = smsRecordLocal.append(vo_sms);
			vo_sms.setUserDefineNo(smsIndex);

			//2.发送短信
			try {
				vo_sms.setBat_serial_no(new Integer(1));
				vo_sms.setSmsIndex(smsIndex);
				vo_sms.setSmstotal(new Integer(1));
				sendBackInfo = SmsClient.sendMessage(vo_sms);
			} catch (Exception e) {
				vo_sms.setSmsIndex(smsIndex);
				vo_sms.setStatus(new Integer(2));
				vo_sms.setStatusName("提交失败");
				vo_sms.setInputOperator(input_man);
				smsRecordLocal.modi(vo_sms);
				smsRecordLocal.remove();
				return "2|请检查短信发送平台！";
			}
			vo_sms.setSmsIndex(smsIndex);
			vo_sms.setStatus(Utility.parseInt(sendBackInfo[0], new Integer(0)));
			vo_sms.setStatusName(sendBackInfo[1]);
			vo_sms.setInputOperator(input_man);
			smsRecordLocal.modi(vo_sms);
			int ret = Utility.parseInt(sendBackInfo[0], new Integer(0))
					.intValue();
			if (ret == 3 || ret == 1) {
				s_result = "1|提交短信成功！";
			} else {
				s_result = sendBackInfo[0] + "|" + sendBackInfo[1];
			}
			smsRecordLocal.remove();
		} catch (Exception e) {
			return "2|" + e.getMessage();
		}
		return s_result;
	}

	public static void addSysMessage(Integer involvedCustId, String msgTitle, String msgText, Integer inputMan) 
		throws BusiException {
		try {
			OperatorLocal op = EJBFactory.getOperator();
			OperatorVO vo = new OperatorVO();
			vo.setInvolved_cust_id(involvedCustId);
			vo.setMsg_title(msgTitle);
			vo.setMsg_text(msgText);
			vo.setOp_code(inputMan);			
			op.addSysMessage(vo);
			
			op.remove();
			
		} catch (Exception e) {
			throw new BusiException("生成新系统消息失败:" + e.getMessage());
		}
	}
	
	// 获取员工列表
	public static String getIntrustOptions(Integer op_code, Integer book_code)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		String listSql = "{call SP_QUERY_TOPERATOR (?,?,?,?,?,?)}";
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, 0);
		stmt.setInt(2, 0);
		stmt.setInt(3, 0);
		stmt.setString(4, "");
		stmt.setInt(5, 0);
		stmt.setInt(6, book_code.intValue());

		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				Argument.appendOptions(sb, rs.getInt("OP_CODE"), rs
						.getString("OP_NAME"), op_code);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//查询信托表 中的 员工信息
	public static String getInturstOperatorName(Integer op_code) throws Exception {
		String op_name = "";
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TOPERATOR (?,?,?,?,?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		if (op_code.intValue() > 0)
			stmt.setInt(1, op_code.intValue());
		else
			stmt.setInt(1, 9999999);
		stmt.setInt(2, 0);
		stmt.setInt(3, 0);
		stmt.setString(4, "");
		stmt.setInt(5, 1);
		stmt.setInt(6, 0);
		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				op_name = rslist.getString("op_name");
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return op_name;
	}

	//查询信托表 中的 部门信息
	public static String getInturstDepartmentName(Integer depart_id) throws Exception {
		String depart_name = "";
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TDEPARTMENT (?,?,?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		if (depart_id.intValue() > 0)
			stmt.setInt(1, depart_id.intValue());
		else
			stmt.setInt(1, 9999999);
		stmt.setInt(2, 0);
		stmt.setInt(3, 0);
		stmt.setInt(4, 0);

		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				depart_name = rslist.getString("DEPART_NAME");
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return depart_name;
	}

//	查询客户名称
	public static String getCustnameByids(String cust_ids) throws Exception {
		String cust_name = "";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TCUSTOMERSBYIDS (?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		if (cust_ids!=null && !"".equals(cust_ids)){
			stmt.setString(1, cust_ids);
		}else{
			return cust_name;
		}
		ResultSet rslist = stmt.executeQuery();
		try {
			String s="";
			while (rslist.next()) {
				s=rslist.getString("CUST_NAME");
				if (s!=null)
					cust_name += s+",";
			}
			if (cust_name.length()>0)
				cust_name=cust_name.substring(0,cust_name.length()-1);
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return cust_name;
	}
	
	public static String queryMarketTrench(Integer product_id, Integer sub_product_id,String value) throws Exception {
		List list = new ArrayList();
		Map mapV = new HashMap();
		StringBuffer sb = newStringBuffer();
		String key = "";
		String val = "";
		try {
			ProductLocal local = EJBFactory.getProduct();
			ProductVO vo = new ProductVO();
			vo.setProduct_id(product_id);
			vo.setSub_product_id(sub_product_id);
			list = local.queryMarketTrench(vo);
			for (int i = 0; i < list.size(); i++) {
				mapV = (Map)list.get(i);
				key = Utility.trimNull(mapV.get("CHANNEL_TYPE"))+"@"+Utility.trimNull(mapV.get("CHANNEL_ID"))+"@"+Utility.trimNull(Utility.parseDecimal(Utility.trimNull(mapV.get("CHANNEL_FARE_RATE")), new BigDecimal(0),4,"1"));
				val = Utility.trimNull(mapV.get("CHANNEL_TYPE_NAME"))+"-"+Utility.trimNull(mapV.get("CHANNEL_NAME")) +"[费率："+Utility.trimNull(Utility.parseDecimal(Utility.trimNull(mapV.get("CHANNEL_FARE_RATE")), new BigDecimal(0),4,"100"))+"]";
				appendOptions(sb,key,val,value);
			}
		} catch (Exception e) {
			throw new BusiException("获取用户信息失败:" + e.getMessage());
		}
		return sb.toString();
	}
	/**
	 * 根据TPREMONEYDETAIL表pre_serial_no获得到账记录
	 *
	 * @param bottom_flag
	 * @return
	 * @throws Exception
	 */
	public static String getPreMoneyDetailList(Integer pre_serial_no)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		enfo.crm.marketing.PreMoneyDetailLocal local = EJBFactory.getPreMoneyDetail();
		enfo.crm.vo.PreMoneyDetailVO preVo = new enfo.crm.vo.PreMoneyDetailVO();
		preVo.setPre_serial_no(pre_serial_no);
		List preList = local.queryPreMoneyDetail(preVo);
		if(preList.size() > 1){
			sb = newStringBuffer();
		}
		for (int i = 0; i < preList.size(); i++) {
			Map map = (Map) preList.get(i);
			appendOptions(sb, Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")), new Integer(0)), 
					Utility.trimNull(map.get("JK_TYPE"))
					+"-"+ Utility.trimNull(map.get("JK_TYPE_NAME")) 
					+"-"+Utility.trimNull(map.get("DZ_DATE"))
					+"-"+Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("DZ_MONEY")),new BigDecimal(0))),
							new Integer(0));
		}
		return sb.toString();
	}
	/**
	 * 根据CUST_ID获得TCUSTBANKACCT帐号信息
	 *
	 * @param bottom_flag
	 * @return
	 * @throws Exception
	 */
	public static String getCustBankList(Integer cust_id)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		CustomerInfoLocal customer = EJBFactory.getCustomerInfo();
		CustomerInfoVO vo = new CustomerInfoVO();
		vo.setCust_id(cust_id);
		List list = customer.listCustBank(vo,0,0).getRsList();
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			appendOptions(sb, Utility.trimNull(map.get("BANK_ID")), 
					Utility.trimNull(map.get("BANK_NAME")) 
						+"-"+Utility.trimNull(map.get("BANK_ACCT")),"");
		}
		return sb.toString();
	}
	
	//获取邮箱服务器，账号以及密码
	public static Map getMailMessage() throws Exception{
		Map map = new HashMap();
		Connection conn = CrmDBManager.getConnection();
		Statement state = conn.createStatement();
		ResultSet rs = state.executeQuery("select * from TSYSTEMINFO");
		while(rs.next()){
			map.put("SMTP_SERVER",rs.getString("SMTP_SERVER"));
			map.put("SMTP_USER",rs.getString("SMTP_USER"));
			map.put("SMTP_USERPWD",rs.getString("SMTP_USERPWD"));
			map.put("MAIL_FROM",rs.getString("MAIL_FROM"));
		}
		return map;
	}
	/**
	 * TCUSTOMERINFO表委托人类型选择
	 *
	 * @param jg_wtrlx2
	 * @return
	 */
	public static String getInstitutionsOptions(Integer jg_wtrlx2) {
		StringBuffer sb = new StringBuffer();		
		appendOptions(sb, 1, "金融性公司", jg_wtrlx2);
		appendOptions(sb, 2, "政府", jg_wtrlx2);
		appendOptions(sb, 3, "非金融性公司", jg_wtrlx2);
		appendOptions(sb, 4, "境外金融性公司", jg_wtrlx2);
		return sb.toString();
	}
	
	//客户投诉方式
	public static String getComplaintOptions(Integer comp_type){
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "请选择", comp_type);
		appendOptions(sb, 1, "电话", comp_type);
		appendOptions(sb, 2, "短信", comp_type);
		appendOptions(sb, 3, "Email", comp_type);
		appendOptions(sb, 4, "其他", comp_type);
		return sb.toString();
	}
	
	//投诉回复方式
	public static String getReplyOptions(Integer reply_type){
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "请选择", reply_type);
		appendOptions(sb, 1, "电话", reply_type);
		appendOptions(sb, 2, "短信", reply_type);
		appendOptions(sb, 3, "Email", reply_type);
		appendOptions(sb, 4, "信件", reply_type);
		return sb.toString();
	}
	
	//投诉处理状态
	public static String getDisposeOptions(Integer do_status){
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "请选择", do_status);
		appendOptions(sb, 1, "未完成", do_status);
		appendOptions(sb, 2, "完成", do_status);
		appendOptions(sb, 3, "转交相关部门", do_status);
		return sb.toString();
	}
	
	//投诉处理转交方式
	public static String getForwardOptions(Integer forward_type){
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "请选择", forward_type);
		appendOptions(sb, 1, "电话", forward_type);
		appendOptions(sb, 2, "Email", forward_type);
		appendOptions(sb, 3, "其他", forward_type);
		return sb.toString();
	}
	/**
	 * 根据查询语句 或者 表名及where条件判断是否存在指定的数据
	 * @param querysSql
	 * @param queryTableName
	 * @param queryCondition
	 * @param sqlFlag true时候是根据querySql进行查询，否则根据queryTableName和queryCondition进行拼接。
	 * @return
	 * @throws BusiException
	 */
	public static boolean isExistsBySql(String querysSql, String queryTableName, String queryCondition, boolean sqlFlag) 
		throws BusiException{
		if(!sqlFlag){
			querysSql = "SELECT 1 FROM " + queryTableName + " WHERE 1=1 " + queryCondition;
		}
		try{
			String existCount =Utility.trimNull(ConfigUtil.getSqlResult(querysSql));	
			if(existCount != null && !"".equals(existCount) && Integer.parseInt(existCount)>0){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			throw new BusiException("读取数据库信息错误:" + e.getMessage());
		}
	}
	
	public static String getContractUnrealStatusOptions(Integer selected) {
        StringBuffer sb = new StringBuffer();
        appendOptions(sb, 0, "请选择", selected);
        appendOptions(sb, 1, "未转正式认购", selected);
        appendOptions(sb, 2, "已转认购", selected);
        return sb.toString();
    }
    
	//   预发行产品列表
    public static String getCRMPreproductListOptions(Integer preproductId, String preproductName, 
            String status, Integer inputMan) throws Exception {
        String listSql = "{call SP_QUERY_TPREPRODUCT(?,?,?,?)}";

        StringBuffer sb = newStringBuffer();

        Connection conn = CrmDBManager.getConnection();
        CallableStatement stmt = conn.prepareCall(listSql,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, 0);
        stmt.setString(2, preproductName);
        stmt.setString(3, status);
        stmt.setInt(4, inputMan.intValue());
        ResultSet rs = stmt.executeQuery();
        try {
            while (rs.next()) {
                appendOptions(sb, rs.getInt("PREPRODUCT_ID"), rs.getString("PREPRODUCT_NAME"), preproductId);
            }
        } finally {
            rs.close();
            stmt.close();
            conn.close();
        }
        return sb.toString();
    }
    
    //查询项目经理领导团队有产品配额的产品信息
//    public static String getTeamProductOptions(Integer inputMan)throws Exception{
//    	StringBuffer sb = newStringBuffer();
//    	Connection conn = CrmDBManager.getConnection();
//    	Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery("{call SP_QUERY_TTEAM_PRODUCT("+ inputMan +")}");
//        while(rs.next()){
//        	appendOptions(sb,rs.getString("PRODUCT_ID"),rs.getString("PRODUCT_NAME"),0);
//        }
//        return "";
//    }
    
    public static String getPreMoneyClassOptions(Integer selected) {
        StringBuffer sb = new StringBuffer();
        appendOptions(sb, 0, "请选择预约金额", selected);
        appendOptions(sb, 1, "金额&ge;300万", selected);
        appendOptions(sb, 2, "金额&lt;300万", selected);
        return sb.toString();
    }
    
    
    public static Integer getFirstProduct(Integer book_code, Integer op_code,
            String product_status) throws Exception {
        String listSql = "{call SP_QUERY_TPRODUCT (?,?,?,?,?,?,?)}";
        Integer r = null;
        Connection conn = IntrustDBManager.getConnection();
        CallableStatement stmt = conn.prepareCall(listSql,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, book_code.intValue());
        stmt.setString(2, "");
        stmt.setInt(3, 0);
        stmt.setString(4, product_status);
        stmt.setString(5, "");
        stmt.setString(6, "");
        if (op_code == null)
            stmt.setInt(7, 0);
        else
            stmt.setInt(7, op_code.intValue());
        ResultSet rs = stmt.executeQuery();
        try {
            if (rs.next())
                r = new Integer(rs.getInt("PRODUCT_ID"));
        } finally {
            rs.close();
            stmt.close();
            conn.close();
        }
        return r;
    }
    
//  根据操作员id查询操作员姓名
    public static String getIntrustOpName(Integer op_code) throws Exception {
        if (op_code == null || op_code.intValue() <= 0)
            return "";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
        	conn = IntrustDBManager.getConnection();
        	stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT OP_NAME FROM TOPERATOR WHERE OP_CODE="+op_code);
            if (rs.next()) 
                return rs.getString("OP_NAME");
            else 
            	return "";

        } finally {
            if (rs!=null)
                rs.close();
            if (stmt!=null)
                stmt.close();
            if (conn!=null)
                conn.close();
        }
    }
    
//  选择受益付款方式显示受益份额
    public static BigDecimal getAmount(Integer book_code, Integer product_id,
            String contract_bh, Integer from_cust_id, String jk_type)
            throws Exception {
        String listSql = "{call SP_QUERY_TBENIFITOR_THREE(?,?,?,?,?)}";

        BigDecimal r = null;
        Connection conn = IntrustDBManager.getConnection();
        CallableStatement stmt = conn.prepareCall(listSql,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, book_code.intValue());
        stmt.setInt(2, product_id.intValue());
        stmt.setString(3, contract_bh);
        stmt.setInt(4, from_cust_id.intValue());
        stmt.setString(5, jk_type);

        ResultSet rs = stmt.executeQuery();
        try {
            while (rs.next()) {
                r = rs.getBigDecimal("BEN_AMOUNT");
            }
        } finally {
            rs.close();
            stmt.close();
            conn.close();
        }
        return r;
    }
    
//  产品编号
    public static String getProductCode(Integer product_id) throws Exception {
        String ret = "";
        if (product_id == null)
            return null;
        StringBuffer sb = newStringBuffer();
        Connection conn = IntrustDBManager.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("{call SP_QUERY_TPRODUCT_ID ("
                + product_id + ")}");
        try {
            if (rs.next())
                ret = rs.getString("PRODUCT_CODE");
        } finally {
            rs.close();
            stmt.close();
            conn.close();
        }
        return ret;
    }
    
//  得到企业客户名称
    public static String getEntCustomerName(Integer cust_id) throws Exception {
        String s = "";
        if (cust_id == null || cust_id.intValue()<=0)
            return "";
        
        try {
            CustomerInfoLocal local = EJBFactory.getCustomerInfo();
            List list = local.loadEntCust(cust_id);
            if (list.size() > 0) {
                Map map = (Map)list.get(0);
                s = (String)map.get("CUST_NAME");
            } else {
                s = "";
            }
            
            local.remove(); //
            return s;
        } catch (Exception e) {
            return null;
        }
    }
    
//  兑付类别
    public static String getFpFlagOptions(int value) throws Exception {
        StringBuffer sb = newStringBuffer();
        appendOptions(sb, 4, "已兑付", value);
        appendOptions(sb, 1, "未兑付", value);
        return sb.toString();
    }
    
//  根据角色和帐套查询操作员
    public static String getIntrustRoledOperatorOptions(Integer book_code,
            int role_id, Integer op_code) throws Exception {
        StringBuffer sb = newStringBuffer();
        Connection conn = IntrustDBManager.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rslist = stmt.executeQuery("{call SP_QUERY_TOPERATOR_BYROLE("
                + book_code + ", " + role_id + ")}");
        try {
            while (rslist.next()) {
                appendOptions(sb, rslist.getInt("op_code"), rslist
                        .getString("op_name"), op_code);
            }
        } finally {
            rslist.close();
            stmt.close();
            conn.close();
        }
        return sb.toString();
    }
    
//  选择受益人ID号后，根据产品ID号、合同编号、受益人ID号从受益人信息表中(TBenifitor)查询出受益付款方式
    public static String getJkTypeOptions(Integer book_code,
            Integer product_id, String contract_bh, Integer from_cust_id,
            String jk_type) throws Exception {
        String listSql = "{call SP_QUERY_TBENIFITOR_TWO(?,?,?,?)}";

        String value = "";
        if (jk_type != null)
            value = jk_type;

        StringBuffer sb = newStringBuffer();
        Connection conn = IntrustDBManager.getConnection();
        CallableStatement stmt = conn.prepareCall(listSql,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, book_code.intValue());
        stmt.setInt(2, product_id.intValue());
        stmt.setString(3, contract_bh);
        stmt.setInt(4, from_cust_id.intValue());

        ResultSet rs = stmt.executeQuery();
        try {
            while (rs.next())
                appendOptions(sb, rs.getString("JK_TYPE"), rs
                        .getString("JK_TYPE_NAME"), value);
        } finally {
            rs.close();
            stmt.close();
            conn.close();
        }
        return sb.toString();
    }
    
    public static String[] getDictparamValue(int dict) throws Exception{
        StringBuffer sb = new StringBuffer(); 
        Connection conn = IntrustDBManager.getConnection();
        CallableStatement stmt = conn.prepareCall(
                "{call SP_QUERY_TDICTPARAM(?)}",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, dict);
        ResultSet rs = stmt.executeQuery();

        try {
            while (rs.next()){
                sb.append(rs.getString("TYPE_VALUE")).append("#");
            }
        } finally {
            rs.close();
            stmt.close();
            conn.close();
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString().split("#");
    }
    
//   产品状态
    public static String getTypeContentByValue(String type_value)
            throws Exception {

        String typevalue = "";
        String procSql = "{?=call SP_QUERY_TDICTPARAM_VALUE(?,?)}";
        Integer SERIAL_NO = null;
        Connection conn = null;
        CallableStatement stmt = null;
        try {
            conn = IntrustDBManager.getConnection();
            stmt = conn.prepareCall(procSql);
            stmt.registerOutParameter(1, Types.VARCHAR);
            stmt.setString(2, type_value);
            stmt.registerOutParameter(3, Types.VARCHAR);
            stmt.execute();
            typevalue = (String) stmt.getObject(3);

            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        } catch (Exception e) {
            throw new Exception("数据库查询错误：" + e.getMessage());
        } finally {
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        }

        return typevalue;

    }
    
    // 获取机构委托人的类型（EAST）列表
    public static String getJgWtrlx2List(String selected) {
        StringBuffer sb = newStringBuffer(); 
        appendOptions(sb, "1", "金融性公司", selected);
        appendOptions(sb, "2", "政府", selected);
        appendOptions(sb, "3", "非金融性公司", selected);
        appendOptions(sb, "4", "境外金融性公司", selected);
        return sb.toString();
    }
    
    // 获取产品的期限
    public static Integer[] getProductValidPeriod(Integer productId, Integer subproductId) 
        throws Exception {
        Integer[] retval = new Integer[2];
        if (productId == null || productId.intValue() <= 0)
            return retval;
        
        String sql = "";
        if (subproductId == null || subproductId.intValue() <= 0)
            sql = "SELECT VALID_PERIOD,PERIOD_UNIT FROM TPRODUCT WHERE PRODUCT_ID="+productId;
        else 
            sql = "SELECT CASE WHEN S.VALID_PERIOD IS NULL THEN P.VALID_PERIOD ELSE S.VALID_PERIOD END AS VALID_PERIOD, P.PERIOD_UNIT" +
                    " FROM TPRODUCT P LEFT JOIN TSUBPRODUCT S ON P.PRODUCT_ID=S.PRODUCT_ID"
                    + " WHERE P.PRODUCT_ID="+ productId+" AND S.SUB_PRODUCT_ID="+subproductId;
                
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {            
            conn = IntrustDBManager.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                retval[0] =  (Integer)rs.getObject("VALID_PERIOD");
                retval[1] = (Integer)rs.getObject("PERIOD_UNIT");
            }
           
            return retval;

        } finally {
            if (rs!=null)
                rs.close();
            if (stmt!=null)
                stmt.close();
            if (conn!=null)
                conn.close();
        }
    }
    
    
    // 获取产品的开始|截止日期
    public static Integer[] getProductStartOrEnddate(Integer productId, Integer subproductId) 
        throws Exception {
    	String start_date ="";
    	String end_date  = "";
    	Integer[] retval = new Integer[2];
        if (productId == null || productId.intValue() <= 0)
            return retval;
        
        String sql = "";
        if (subproductId == null || subproductId.intValue() <= 0)
            sql = "SELECT START_DATE,END_DATE FROM TPRODUCT WHERE PRODUCT_ID="+productId;
        else 
            sql = "SELECT S.START_DATE,S.END_DATE" +
                    " FROM TPRODUCT P LEFT JOIN TSUBPRODUCT S ON P.PRODUCT_ID=S.PRODUCT_ID"
                    + " WHERE P.PRODUCT_ID="+ productId+" AND S.SUB_PRODUCT_ID="+subproductId;
                
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {            
            conn = IntrustDBManager.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
            	retval[0] =  (Integer)rs.getObject("START_DATE");
                retval[1] = (Integer)rs.getObject("END_DATE");
            }
           
            return retval;

        } finally {
            if (rs!=null)
                rs.close();
            if (stmt!=null)
                stmt.close();
            if (conn!=null)
                conn.close();
        }
    }
    
    //  获取产品对应的合同前缀
    public static String getContractSubBhPrefix(Integer productId)
            throws Exception {
    	String retval = "";
    	Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = IntrustDBManager.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT PRE_CODE FROM TPRODUCT WHERE PRODUCT_ID="+productId);
            if (rs.next()) {
                retval =  Utility.trimNull(rs.getString("PRE_CODE"));
            }
            return retval;
            
        } finally {
            if (rs!=null)
                rs.close();
            if (stmt!=null)
                stmt.close();
            if (conn!=null)
                conn.close();
        }
    }
    
//  获取产品对应的合同前缀
    public static Map getEditableFields(String menuId) throws Exception {
    	Map map = new HashMap();
    	Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = CrmDBManager.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT FIELD FROM TFIELDSDIM WHERE MENU_ID='"+menuId+"'");
            while (rs.next()) 
            	map.put(rs.getString("FIELD"), new Boolean(true));
            
            return map;
            
        } finally {
            if (rs!=null)
                rs.close();
            if (stmt!=null)
                stmt.close();
            if (conn!=null)
                conn.close();
        }
    }

    //  获取产品对应的合同前缀
    public static String getMergeCheckStatusList(Integer selected) {
    	StringBuffer sb = new StringBuffer(); 
    	appendOptions(sb, 0, "全部", selected);
        appendOptions(sb, 1, "未审核", selected);
        appendOptions(sb, 2, "已审核", selected);
        return sb.toString();
    }
    
    //cell下拉框选择项---收益账户批量编辑
    public static String getDictParamSelect(int dict,String sql)throws Exception{
    	String selOptions = "请选择\n";
    	StringBuffer sb = newStringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				sql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, dict);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()){
			selOptions = selOptions + rs.getString("TYPE_CONTENT") + "-" + rs.getString("TYPE_VALUE") + "\n";
		}
    	return selOptions;
    }

    public static String getFieldDimList(String menuId) throws Exception {
        menuId = menuId.replaceAll("'", "''");

        StringBuffer sb = new StringBuffer(); 
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = CrmDBManager.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT FIELD FROM TFIELDSDIM WHERE MENU_ID='"+menuId+"' ORDER BY PARAM_TYPE_ID");
            while (rs.next()) {
        	    if (sb.length()!=0)
        		    sb.append("$");
        	    sb.append(rs.getString("FIELD"));
            }
            return sb.toString();
            
        } finally {
            if (rs!=null)
                rs.close();
            if (stmt!=null)
                stmt.close();
            if (conn!=null)
                conn.close();
        }
    }
    
    public static String getDictParamValue(String typeValue) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = CrmDBManager.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select type_content from tdictparam where type_value="+Utility.escapeSqlStr(typeValue));
            if (rs.next()) 
            	return Utility.trimNull(rs.getString(1));        	    
        	else
    			return "";
        } finally {
            if (rs!=null)
                rs.close();
            if (stmt!=null)
                stmt.close();
            if (conn!=null)
                conn.close();
        }
    }
    
    // 获取机构委托人的类型
    public static String getJgWtrlx2(String lx) {
        if ("1".equals(lx)) 
        	return "金融性公司";
        else if ("2".equals(lx)) 
        	return "政府";
        else if ("3".equals(lx)) 
        	return "非金融性公司";
        else if ("4".equals(lx)) 
        	return "境外金融性公司";
        else
        	return "";
 
    }
	/**
	 * 获取团队成员列表
	 *
	 * @param bottom_flag
	 * @return
	 * @throws Exception
	 */
	public static String getManagerTeamMemberList(Integer team_id,Integer manager_id) throws Exception {
		StringBuffer sb = newStringBuffer();
		Map map = null;
		TmanagerteammembersVO vo = new TmanagerteammembersVO();
		TmanagerteamsLocal tmanagerteams_Bean = EJBFactory.getTmanagerteams();
		vo.setTeam_id(team_id);
		List list = tmanagerteams_Bean.list_query(vo);

		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			appendOptions(sb, Utility.parseInt(Utility.trimNull(map.get("MANAGERID")),new Integer(0)), Utility.trimNull(map
					.get("MANAGERNAME")), manager_id);
		}
		return sb.toString();
	}
	
	 //  获取客户信息真实性标记列表
    public static String getCustInfoTrueFlagList(Integer selected) {
    	StringBuffer sb = new StringBuffer(); 
    	appendOptions(sb, 0, "请选择", selected);
        appendOptions(sb, 1, "未核查", selected);
        appendOptions(sb, 2, "真实", selected);
        appendOptions(sb, 3, "待核查", selected);
        return sb.toString();
    }
    
	public static String getProductInFL(Integer book_code,Integer cust_id,String value) throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall("{call SP_QUERY_PRODUCT_FL(?,null,null,?)}",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setInt(2, cust_id.intValue());
		ResultSet rs = stmt.executeQuery();
		try{
			while(rs.next()){
				appendOptions(sb,rs.getObject("PRODUCT_ID") + "$" + rs.getObject("CONTRACT_BH"),rs.getObject("PRODUCT_CODE") + "-" + rs.getObject("PRODUCT_NAME") + "-" + rs.getObject("CONTRACT_BH"),value);
			}
		}finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}
    
    //  获取员工日程类型列表
    public static String getScheduleTypeList(String selected) throws Exception {
    	 StringBuffer sb = new StringBuffer(); 
    	 appendOptions(sb, "0", "全部", selected);
    	 
         Connection conn = null;
         CallableStatement stmt = null;
         ResultSet rs = null;

         try {
         	conn = CrmDBManager.getConnection();
            stmt = conn.prepareCall("{call SP_QUERY_TDICTPARAM(?)}", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setInt(1, 3042); // 3042 -- 日程类别
            rs = stmt.executeQuery();
            
             while (rs.next()){
             	appendOptions(sb, rs.getString("TYPE_VALUE"), rs.getString("TYPE_CONTENT"), selected);
             }
             return sb.toString();
             
         } finally {
         	if (rs!=null)
         		rs.close();
         	if (stmt!=null)
         		stmt.close();
         	if (conn!=null)
         		conn.close();
         }

    }
    
	// 产品期限单位类别
	public static String getTinipapamOptions_intrust(int type_id, String type_value)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, "0", "请选择", type_value);
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TINIPARAM(?,NULL)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, type_id);
		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				appendOptions(sb, rslist.getString("TYPE_VALUE"), rslist
						.getString("CONTENT"), type_value);
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}
	
	// 取input_man的下属成员	成员目前包括：团队中的成员，客户经理树中的成员
	public static String getOperatorMembers(Integer input_man, Integer selected)
		throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "全部", selected);
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_GET_OPERATOR_MEMBERS(?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, input_man.intValue());
		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				appendOptions(sb, rslist.getInt("OP_CODE"), rslist
						.getString("OP_NAME"), selected);
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}
	
	//查询所有团队或团队中的所有成员
	public static String getTeamOrTeammembers(Integer teamType)throws Exception{
		StringBuffer sb = new StringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TEAM_TEAMMEMBERS(?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, teamType.intValue());
		ResultSet rslist = stmt.executeQuery();
		try {
			if(teamType.intValue()==1){
				while(rslist.next()){
					appendOptions(sb, rslist.getInt("MANAGERID"), rslist
							.getString("MANAGERNAME"),0);
					}
				}
			else if(teamType.intValue()==2){
				while(rslist.next()){
					appendOptions(sb, rslist.getInt("TEAM_ID"), rslist
							.getString("TEAM_NAME"),0);
					}
				}
		}finally{
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}
	
	public static String getDictOptionsWithLevelIntrust(Integer type_id, String type_value,Integer defaultValue)
	throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TDICTPARAM_TREE2 (?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setObject(1, type_id);
		stmt.setObject(2, type_value);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
		
				appendOptions(sb, rs.getInt("TYPE_VALUE"), rs
						.getString("TYPE_CONTENT"), defaultValue);
			}
		
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}
	
	public static String getDictOptionsWithLevel(Integer type_id, String type_value,Integer defaultValue)
	throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TDICTPARAM_TREE2 (?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setObject(1, type_id);
		stmt.setObject(2, type_value);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
		
				appendOptions(sb, rs.getInt("TYPE_VALUE"), rs
						.getString("TYPE_CONTENT"), defaultValue);
			}
		
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}
	
	/**
	 * 获得信息必填项
	 * 
	 * @param menu_id
	 * @return
	 * @throws Exception
	 */
	public static List getRequiredItem(String menu_id, String busi_type,
			Integer is_filled) throws Exception {
		List list = new ArrayList();
		Connection conn = IntrustDBManager.getConnection();
		String listSql = "SELECT * FROM TCHECKDATA WHERE MODULE_CODE = ? AND BUSI_TYPE = ? AND IS_FILLED = ?";
		PreparedStatement stmt = conn.prepareStatement(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setString(1, menu_id);
		stmt.setString(2, busi_type);
		stmt.setInt(3, is_filled.intValue());
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				list.add(Utility.trimNull(rs.getString("FIELD_NAME")));
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return list;
	}
	
	/**
	 * 得到打印模板下拉列表 TPRINTTEMPLATE
	 */
	public static String getPrintTemplateList(String catalog_code, String template_code)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		PreparedStatement stmt = conn.prepareStatement(
				"SELECT TEMPLATE_CODE,TEMPLATE_NAME FROM TPRINTTEMPLATE WHERE CHECK_FLAG=2 AND CATALOG_CODE='"+catalog_code+"'");
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next())
				appendOptions(sb, rs.getString("TEMPLATE_CODE"), rs.getString("TEMPLATE_NAME"),
						template_code);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}
	/**
	 * 通过模板代码取得打印模板
	 */
	public static String getPrintTemplate(String template_code) throws Exception{
		Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs=null;
        String Template_content = "";
        try {
        	conn = IntrustDBManager.getConnection();
            stmt = conn.prepareCall("{call SP_QUERY_TPRINTTEMPLATE (?,?,?,?)}",
    				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        	stmt.setObject(1, new Integer(0));
    		stmt.setObject(2, "");
    		stmt.setObject(3, template_code);
    		stmt.setObject(4, "");
    		rs = stmt.executeQuery();
            if (rs.next()){
            	Template_content=rs.getString("TEMPLATE_CONTENT");
            }
        } catch (Exception e) {
            throw new BusiException("打印模板查询失败"+e.getMessage());
        }finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (conn!=null) conn.close();
		}
        return Template_content;
	}
	/**
	 * 通过模板ID取得打印模板
	 */
	public static String getPrintTemplateById(Integer template_id) throws Exception{
		Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs=null;
        String Template_content = "";
        try {
        	conn = IntrustDBManager.getConnection();
            stmt = conn.prepareCall("{call SP_QUERY_TPRINTTEMPLATE (?,?,?,?)}",
    				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        	stmt.setObject(1, template_id);
    		stmt.setObject(2, "");
    		stmt.setObject(3, "");
    		stmt.setObject(4, "");
    		rs = stmt.executeQuery();
            if (rs.next()){
            	Template_content=rs.getString("TEMPLATE_CONTENT");
            }
        } catch (Exception e) {
            throw new BusiException("打印模板查询失败"+e.getMessage());
        }finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (conn!=null) conn.close();
		}
        return Template_content;
	}
	/**
	 * 通过模板分类取得打印模板下拉列表
	 */
	public static String getPrintTemplate(String catalog_code,String default_template) throws Exception{
		StringBuffer sb = new StringBuffer();
		Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs=null;
        try {
        	conn = CrmDBManager.getConnection();
            stmt = conn.prepareCall("{call SP_QUERY_TPRINTTEMPLATE (?,?,?,?)}",
    				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        	stmt.setObject(1, new Integer(0));
    		stmt.setObject(2, catalog_code);
    		stmt.setObject(3, "");
    		stmt.setObject(4, "");
    		rs = stmt.executeQuery();
    		while (rs.next()){
    			appendOptions(sb, rs.getString("TEMPLATE_ID"), rs.getString("TEMPLATE_NAME"),
    					default_template);
    		}
        } catch (Exception e) {
            throw new BusiException("打印模板查询失败:"+e.getMessage());
        }finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (conn!=null) conn.close();
		}
        return sb.toString();
	}
	/**
	 * 取得产品的预约默认有效天数
	 */
	public static int getDefaultPreValidDays(Integer product_id)
			throws Exception {
		int validDays = 0;
		Connection conn = CrmDBManager.getConnection();
		PreparedStatement stmt = conn.prepareStatement(
				"SELECT ISNULL(DEFAULT_PREVALIDDAYS,0) DEFAULT_PREVALIDDAYS FROM TPRODUCT WHERE PRODUCT_ID="+product_id);
		ResultSet rs = stmt.executeQuery();
		try {
			if (rs.next()){
				validDays= rs.getInt("DEFAULT_PREVALIDDAYS");
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return validDays;
	}

	//查找CRM中产品集合
	public static String getProductListOptionsCRM(Integer sale_status,String product_status,
			Integer open_flag, Integer input_man, String product_code, String product_name,Integer product_id)
			throws Exception {
		String listSql = "{call SP_QUERY_TPRODUCT_LIST (?,?,?,?,?,?)}";

		StringBuffer sb = new StringBuffer();

		Connection conn = CrmDBManager.getConnection();
		PreparedStatement stmt = conn.prepareStatement(listSql);
		
		stmt.setInt(1, sale_status.intValue());
		stmt.setString(2, product_status);
		stmt.setInt(3, open_flag.intValue());
		stmt.setInt(4, input_man.intValue());
		stmt.setString(5, product_code);
		stmt.setString(6, product_name);
		
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				appendOptions(sb, rs.getInt("PRODUCT_ID"), rs
						.getString("PRODUCT_CODE")
						+ "-" + rs.getString("PRODUCT_NAME"), product_id);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}
	//对lisi进行层级排序
	public static List getTeamListBySort(List list){
		List newlist = new ArrayList();

		for(int i=0; i<list.size(); i++){
			Map map = (Map)list.get(i);
			Integer team_id = Utility.parseInt(Utility.trimNull(map.get("TEAM_ID")),new Integer(0));
			int son_num = Utility.parseInt(map.get("SON_NUM").toString(),0);
			newlist.add(map);
			list.remove(i);
			i--;
			if(son_num!=0){		
				newlist.addAll(getTeamListRank(list,team_id));
			}
		}
		return newlist;
	}
	//根据team_id按层级查找所有的下属团队
	public static List getTeamListRank(List list,Integer Par_id){
		List newlist = new ArrayList();
		for(int i=0; i<list.size(); i++){
			Map map = (Map)list.get(i);
			Integer team_id = Utility.parseInt(Utility.trimNull(map.get("TEAM_ID")),new Integer(0));
			int parent_id = Utility.parseInt(map.get("PARENT_ID").toString(),0);
			int son_num = Utility.parseInt(map.get("SON_NUM").toString(),0);
			if(Par_id.intValue()==parent_id){
				newlist.add(map);
				list.remove(i);
				i--;
				if(son_num!=0){
					newlist.addAll(getTeamListRank(list,team_id));
				}
			}
		}
		return newlist;
	}
	//查上级teamID 
	public static Integer getParentIdByTeamId(Integer teamId) throws Exception{
		if (teamId == null){
			return null;
		}
		Integer parentId = new Integer(0);
	
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt
				.executeQuery("select PARENT_ID from TManagerTeams where TEAM_ID = "+teamId);
		try {
			if (rs.next())
				parentId = new Integer(rs.getInt("PARENT_ID"));
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		
		return parentId;
	}
	/**通过操作员查teamId
	 * 
	 * @param input_opcode
	 * @return ret[0]存放TeamID，ret[1]存放是否末级团队，0非末级，1末级
	 * @throws Exception
	 */
	public static Integer[] getTeamID(Integer input_opcode) throws Exception {
		if (input_opcode == null)
			return null;
		Integer[] ret = new Integer[2];
		Integer value = Utility.parseInt(input_opcode, null);
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt
				.executeQuery("SELECT TEAM_ID,CASE WHEN (RIGHT_ID>LEFT_ID+1) THEN 0 ELSE 1 END ISENDTEAM FROM TManagerTeams WHERE LEADER="+input_opcode+" OR LEADER2="+input_opcode);
		try {
			if (rs.next()){
				ret[0] = new Integer(rs.getInt("TEAM_ID"));
				ret[1] = new Integer(rs.getInt("ISENDTEAM"));
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return ret;
	}
	public static String getPreContractCheckBoxCrm(Integer book_code,Integer product_id, Integer sub_product_id, String serial_no, Integer input_man,String cust_name) throws Exception {
		String listSql = "{call SP_QUERY_TPRECONTRACT_VALID (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

		StringBuffer sb = new StringBuffer();
		Connection conn =CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setInt(2, product_id.intValue());
		stmt.setString(3, "");
		stmt.setInt(4, 0);
		stmt.setInt(5, input_man.intValue());
		stmt.setString(6, cust_name);
		stmt.setInt(7, 0);
		stmt.setBigDecimal(8, new BigDecimal(0));
		stmt.setBigDecimal(9, new BigDecimal(0));
		stmt.setString(10, "");
		stmt.setInt(11, 0);
		stmt.setInt(12, 0);
		stmt.setInt(13, 0);
		stmt.setInt(14, sub_product_id.intValue());
		stmt.setInt(15, 0);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()){
				sb.append("<input style='border: none' type='checkbox' name='productc' ");
				sb.append(" value=\"" +  rs.getString("SERIAL_NO")+"$0&"+rs.getInt("CUST_ID")+"\"");
				sb.append(" onclick='javascript:selectPrecodeItem(this)'>");
				sb.append(rs.getString("PRE_CODE")+" - " +rs.getString("CUST_NAME")+" - "+rs.getInt("PRE_MONEY"));
				sb.append("<br />");
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}
	
	/*
	 * 查询指定短信模板中用到的宏
	 * */
	public static List getMac_Tem(Integer tempID,Integer value, Integer opCode) throws Exception{
		List list = new ArrayList();
		 String listSql = "{call SP_QUERY_TMACRO_TEMPLATE (?,?,?)}";
	        Connection conn = CrmDBManager.getConnection();
	        CallableStatement stmt = conn.prepareCall(listSql,
	                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        stmt.setInt(1, tempID.intValue());
	        stmt.setInt(2, value.intValue());
	        stmt.setInt(3, opCode.intValue());
	        ResultSet rs = stmt.executeQuery();
	        try {
	           while(rs.next()){
	           	Map map = new HashMap(); 
					map.put("MACRO_NAME", rs.getObject("MACRO_NAME"));
					map.put("MACRO_CODE", rs.getObject("MACRO_CODE"));
					list.add(map);
	           }
	        } finally {
	            rs.close();
	            stmt.close();
	            conn.close();
	        }
	        return list;
	}
	
	/* * 查询认购金额
	 * */
	public static BigDecimal getRgmoney(Integer cust_id,Integer product_id) throws Exception{
		
		BigDecimal rgmoney = new BigDecimal(0);
		Connection conn = IntrustDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt
				.executeQuery("select sum(RG_MONEY) AS SUMRG from TCONTRACT where CUST_ID = "+cust_id+" and PRODUCT_ID="+product_id+" group by RG_MONEY");
		
	        try {
	        	if (rs.next()){
	        		rgmoney = rs.getBigDecimal("SUMRG");
				}
	        } finally {
	            rs.close();
	            stmt.close();
	            conn.close();
	        }
	        return rgmoney;
	}
	//收益账号 flag   1- 查受益账户 2-查合同编号
	public static String getBenefitAccount(Integer custId, int flag, Integer inputMan,String benAccount) throws Exception {
		String listSql = "{call SP_QUERY_CASH_BENACCT (?,?,?)}";

		StringBuffer sb = newStringBuffer();
		Connection conn =CrmDBManager.getConnection();
		PreparedStatement stmt =conn.prepareStatement(listSql);
		stmt.setInt(1, custId.intValue());
		stmt.setInt(2, flag);
		stmt.setInt(3, inputMan.intValue());
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()){
				appendOptions(sb, rs.getString("BANK_ACCT"), rs.getString("TYPE_CONTENT"), benAccount);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	/**
	 * 受益率调整，获取收益率值
	 * @param product_id
	 * @param prov_flag
	 * @param prov_level
	 * @return
	 * @throws BusiException
	 */
	public static BigDecimal getGainRate(String product_id,String prov_flag,String prov_level) throws BusiException {
		BigDecimal gain_rate = new BigDecimal(0);
		List level_gain_rate = CrmDBManager.listBySql("SELECT TOP 1 B.GAIN_RATE FROM INTRUST..TGAINLEVEL A LEFT JOIN INTRUST..TGAINLEVELRATE B ON A.SERIAL_NO = B.DF_SERIAL_NO WHERE A.PRODUCT_ID = ? AND A.PROV_FLAG = ? AND A.PROV_LEVEL = ? ORDER BY B.START_DATE DESC", new Object[]{
				product_id,
				prov_flag,
				prov_level
		});		
		if(level_gain_rate != null && level_gain_rate.size() >0){
			Map map_gain_rate = (Map)level_gain_rate.get(0);
			gain_rate = Utility.parseDecimal(Utility.trimNull(map_gain_rate.get("GAIN_RATE")),new BigDecimal(0.00));
			gain_rate = gain_rate.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		return gain_rate;
	}
	
	public static String getDoubleRecordTypeOptions(String defvalue)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		Map<String, String> typeMap = new HashMap<String, String>();
		typeMap.put("91001", "本地专区双录");
		typeMap.put("91002", "移动双录");
		typeMap.put("91003", "远程双录");
		
		for(Map.Entry<String, String> entry : typeMap.entrySet()) {
			String value = entry.getKey();
			String text = entry.getValue();
			sb.append("<option");
			if (defvalue != null)
				sb.append((value.trim().compareTo(defvalue.trim()) == 0) ? " selected"
								: "");
			sb.append(" value=\"" + value + "\">");
			sb.append(text);
			sb.append("</option>");
		}
		
		return sb.toString();
	}
	
	public static String getDoubleRecordTypeOptionText(String defvalue)
			throws Exception {
		Map<String, String> typeMap = new HashMap<String, String>();
		typeMap.put("91001", "本地专区双录");
		typeMap.put("91002", "移动双录");
		typeMap.put("91003", "远程双录");
		
		return typeMap.get(defvalue);
	}
	
	/**
	 * 查询首次认购时间
	 * @param contract_vo
	 * @return
	 * @throws Exception
	 */
	public static String getFirstRGTime(ContractVO contract_vo)
			throws Exception {
		String frgtime = "";
		ContractLocal contract_local = EJBFactory.getContract();
		List contract_list = contract_local.queryContractByCustID(contract_vo);
		if(contract_list != null && contract_list.size() > 0) {
			int[] arr = new int[contract_list.size()];
			Map<Integer, String> map = new HashMap<>();
			for(int i=0; i<contract_list.size(); i++){
				Map contract_map = (Map)contract_list.get(i);
				Integer time = Utility.parseInt(Utility.trimNull(contract_map.get("QS_DATE")), new Integer(0));
				arr[i] = time;
				frgtime = Format.formatDateCn(Utility.parseInt(Utility.trimNull(contract_map.get("QS_DATE")), new Integer(0)));
				map.put(time, frgtime);
			}
			Arrays.sort(arr);
			frgtime = map.get(arr[0]);
		}
		return frgtime;
	}
	
	/**
	 * 查询最近一次认购信息
	 * 产品名称+ 合同编号
	 * @param contract_vo
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getRGInfo(ContractVO contract_vo)
			throws Exception {
//		String frgtime = "";
		Map<String, String> result = new HashMap<>();
		ContractLocal contract_local = EJBFactory.getContract();
		List contract_list = contract_local.queryContractByCustID(contract_vo);
		if(contract_list != null && contract_list.size() > 0) {
			int[] arr = new int[contract_list.size()];
			Map<Integer, Map<String, String>> map = new HashMap<>();
			for(int i=0; i<contract_list.size(); i++){
				Map<String, String> result1 = new HashMap<>();
				Map contract_map = (Map)contract_list.get(i);
				Integer time = Utility.parseInt(Utility.trimNull(contract_map.get("QS_DATE")), new Integer(0));
				arr[i] = time;
				String productName = Utility.trimNull(contract_map.get("PRODUCT_NAME"));
				String contractSubBH = Utility.trimNull(contract_map.get("CONTRACT_SUB_BH"));
				
				result1.put("PRODUCT_NAME", productName);
				result1.put("CONTRACT_SUB_BH", contractSubBH); 
				map.put(time, result1);
			}
			Arrays.sort(arr); 
			result = map.get(arr[arr.length-1]);
		}
		return result;
	}
	
	/**
	 * 双录信息状态
	 * @param defvalue
	 * @return
	 * @throws Exception
	 */
	public static String getDoubleRecordStatusOptions(String defvalue)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		Map<String, String> typeMap = new HashMap<String, String>();
		typeMap.put("-2", "未录入");
		typeMap.put("-1", "待审核");
		typeMap.put("0", "未通过");
		typeMap.put("1", "已通过");
		
		for(Map.Entry<String, String> entry : typeMap.entrySet()) {
			String value = entry.getKey();
			String text = entry.getValue();
			sb.append("<option");
			if (defvalue != null)
				sb.append((value.trim().compareTo(defvalue.trim()) == 0) ? " selected"
								: "");
			sb.append(" value=\"" + value + "\">");
			sb.append(text);
			sb.append("</option>");
		}
		
		return sb.toString();
	}

	/**
	 * 客户联系方式修改申请状态
	 * @param defvalue
	 * @return
	 * @throws Exception
	 */
	public static String getCustomersInfoRecordStatusOptions(String defvalue)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		Map<String, String> typeMap = new HashMap<String, String>();
		typeMap.put("1", "待确认");
		typeMap.put("2", "确认未通过");
		typeMap.put("3", "待审核");
		typeMap.put("4", "审核未通过");
		typeMap.put("5", "已通过通过");
		
		for(Map.Entry<String, String> entry : typeMap.entrySet()) {
			String value = entry.getKey();
			String text = entry.getValue();
			sb.append("<option");
			if (defvalue != null)
				sb.append((value.trim().compareTo(defvalue.trim()) == 0) ? " selected"
								: "");
			sb.append(" value=\"" + value + "\">");
			sb.append(text);
			sb.append("</option>");
		}
		
		return sb.toString();
	}
	

	/**
	 * 判断操作员是否是符合条件的角色
	 * @param op_code
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public static Boolean checkRoleIdByOpCode(Integer op_code, Integer roleId) throws Exception {
		if (op_code == null || op_code.intValue() == 0)
			return false;
		OperatorLocal local = EJBFactory.getOperator();
		OperatorVO vo = new OperatorVO();
		vo.setOp_code(op_code);
		List list = local.listSerno(vo);
		Map rowMap = null;
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				rowMap = (Map) list.get(i);
				String opRoleId = Utility.trimNull(rowMap.get("ROLE_ID"));
				if(opRoleId.equals(roleId.toString())) {
					return true;
				}
			}
		}
		local.remove();
		return false;
	}
	
	
	/**
	 * 双录信息状态
	 * @param defvalue
	 * @return
	 * @throws Exception
	 */
	public static String getSalecChangesStatusOptions(String defvalue)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		Map<String, String> typeMap = new HashMap<String, String>();
		typeMap.put("1", "待确认");
		typeMap.put("2", "确认未通过");
		typeMap.put("3", "待审核");
		typeMap.put("4", "审核未通过");
		typeMap.put("5", "已通过");
		typeMap.put("6", "已撤销");
		
		for(Map.Entry<String, String> entry : typeMap.entrySet()) {
			String value = entry.getKey();
			String text = entry.getValue();
			sb.append("<option");
			if (defvalue != null)
				sb.append((value.trim().compareTo(defvalue.trim()) == 0) ? " selected"
								: "");
			sb.append(" value=\"" + value + "\">");
			sb.append(text);
			sb.append("</option>");
		}
		
		return sb.toString();
	}
	
	/**
	 * 到帐产品类型
	 * @param defvalue
	 * @return
	 * @throws Exception
	 */
	public static String getSalecChangesResultProductTypeOptions(String defvalue)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		Map<String, String> typeMap = new HashMap<String, String>();
//		typeMap.put("1", "直销(工作台）");
		typeMap.put("2", "小账户");
		typeMap.put("3", "特殊销量");
		typeMap.put("4", "家族信托");
		typeMap.put("5", "其他");
		
		for(Map.Entry<String, String> entry : typeMap.entrySet()) {
			String value = entry.getKey();
			String text = entry.getValue();
			sb.append("<option");
			if (defvalue != null)
				sb.append((value.trim().compareTo(defvalue.trim()) == 0) ? " selected"
								: "");
			sb.append(" value=\"" + value + "\">");
			sb.append(text);
			sb.append("</option>");
		}
		
		return sb.toString();
	}
	
	/**
	 * 到帐产品类型-More
	 * @param defvalue
	 * @return
	 * @throws Exception
	 */
	public static String getSalecChangesResultProductTypeOptionsMore(String defvalue)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		Map<String, String> typeMap = new HashMap<String, String>();
		typeMap.put("1", "直销(工作台）");
		typeMap.put("2", "小账户");
		typeMap.put("3", "特殊销量");
		typeMap.put("4", "家族信托");
		typeMap.put("5", "其他");
		
		for(Map.Entry<String, String> entry : typeMap.entrySet()) {
			String value = entry.getKey();
			String text = entry.getValue();
			sb.append("<option");
			if (defvalue != null)
				sb.append((value.trim().compareTo(defvalue.trim()) == 0) ? " selected"
								: "");
			sb.append(" value=\"" + value + "\">");
			sb.append(text);
			sb.append("</option>");
		}
		
		return sb.toString();
	}
	
	/**
	 * 到帐产品类型
	 * @param defvalue
	 * @return
	 * @throws Exception
	 */
	public static String getSalecChangesResultProductTypeName(String defvalue)
			throws Exception {
		Map<String, String> typeMap = new HashMap<String, String>();
//		typeMap.put("1", "直销(工作台）");
		typeMap.put("2", "小账户");
		typeMap.put("3", "特殊销量");
		typeMap.put("4", "家族信托");
		typeMap.put("5", "其他");
		
		if(defvalue != null) {
			return typeMap.get(defvalue);
		}
		return "";
	}
}