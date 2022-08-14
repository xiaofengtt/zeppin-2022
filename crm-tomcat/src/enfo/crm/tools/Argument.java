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
		sb.append("<option value=\"\">��ѡ��</option>");
		return sb;
	}

	public static StringBuffer newStringBufferBit() {
		StringBuffer sb = new StringBuffer(200);
		sb.append("<option value=\"-1\">��ѡ��</option>");
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

	//���ݲ���ֵ�õ���������
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
	
	
	//�����ֵ���type_content��ѯtype_value
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

	//��TSYSCONTROL�и���flag_type��ȡvalueֵ
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

	//�����ݽ�ɫ�����ײ�ѯ����Ա
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

	//�����ݽ�ɫ�����ײ�ѯ����Ա
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

	//���ݿͻ���Ϣ���Һ�ͬ��ϵ��
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
					str1="��ͥ�绰:"+rslist.getString("PhoneHome")+"  ";
				}
				if(rslist.getString("Moblie")!=null&&!rslist.getString("Moblie").equals("")){
					str2="�ֻ�:"+rslist.getString("Moblie")+"  ";
				}
				if(rslist.getString("PhoneOffice")!=null&&!rslist.getString("PhoneOffice").equals("")){
					str3="�칫�ҵ绰:"+rslist.getString("PhoneOffice")+"  ";
				}
				if(rslist.getString("Address")!=null&&!rslist.getString("Address").equals("")){
					str4="��ַ:"+rslist.getString("Address");
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
	//���ݱ�־���Ͳ�ѯϵͳ����
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

	//	���ݱ�־���Ͳ�ѯϵͳ����
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

	//bottom_flagΪ1�Ĳ�����Ϣ
	public static String getDepartValue(Integer bottom_flag) throws Exception {
		String listSql = "{call SP_QUERY_TSYSCONTROL(?,?,?)}";
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=\"-1\">��ѡ��</option>");
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
	 * ��ȡ��������ĵǼ�������Ϣ autor by lk
	 *
	 * @param check_flag
	 * @return
	 * @throws Exception
	 */
	public static String getPrecompanyCheckFlagValue(Integer check_flag,
			Integer company_id) throws Exception {
		String listSql = "{call SP_QUERY_TPRECOMPANY(?,?,?,?)}";
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=\"0\">��ѡ��</option>");
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

	//���ݲ���id��ѯ��ѯ��������
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

	//��������id����ѯ��������
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

	//����role_id����ѯ��ɫ����
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

	//��������id����ѯ��������
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
			appendOptions(sb, 0, "ȫ��", book_code);
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

	//	���ݲ���ֵ�õ���������:�б�
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

	
	//	���ݲ���ֵ�õ���������:�б�
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

	
	
	
	//���ݲ���ֵ�õ���������:�б� intrust��
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
	//���ݲ���ֵ�õ������ֵ���б�
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
	//���ݲ���ֵ�õ������ֵ���б�
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

	//	���������ʽ���Դ
	public static String getJr_Money_Source(String money_source)
			throws Exception {

		return getDictParamOptions_intrust(8202, money_source);
	}
	
	//	���������ʽ���Դ
	public static String getJg_Money_Source(String money_source)
			throws Exception {

		return getDictParamOptions_intrust(8201, money_source);
	}
	
	//	�г�����
	public static String getSeidOptions(String value) throws Exception {
		return getDictParamOptions(1130, value);
	}

	//	֤ȯ����
	public static String getStockTypeOptions(String value) throws Exception {
		return getDictParamOptions(1129, value);
	}

	//���ݲ���Աid��ѯ����Ա����
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

	//���ݲ���Աid��ѯ����Ա��Ϣ
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

	//	��ҵ
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

	//	������ҵ�����ȡ��ҵ������ҵ add by tsg 2008-05-28
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

	// ����
	public static String getCurrencyOptions(String value) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=\"\">��ѡ��</option>");
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

	// ��ɫ
	public static String getRoleId(int role_id) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=\"\">��ѡ��</option>");
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

	// �õ����в��������б�---���ظ���
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

	//	���ݲ�ƷID�ͱ���ID��ѯ��������
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

//	 ���ݲ�ƷID�ͱ���ID��ѯ��������
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
	 * add by tsg 2008-05-29 ѡ��Ӫҵ����� SP_QUERY_TFUNDACCT_BYID
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

	//	ѡ��ͬһ��Ʒ��ͬһӪҵ�����ʽ��˺�
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

	//ѡ��ͬһ��Ʒ��ͬһӪҵ����ͬһ�ʽ��˺ŵ�֤ȯ�˺�
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

	// Ҷ�Ӳ����б� //
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

	//	��ȡ��ɫ��Ϣ
	public static String getRolename(Integer role_id) throws Exception {
		String listSql = "{call SP_QUERY_TROLE(0)}";
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=\"0\">��ѡ��</option>");
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

	//��ȡ��ز�����Ϣ
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

	//	���ݲ���ֵ�õ�ADDITIVE_VALUE
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
	 * add by tsg 20080617 �������ֵΪNULL ��'',�򲻽��в�ѯ
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
	 * �����־1��ͨ����2ͬҵ���
	 */
	public static String getDkflagOptions(Integer flag) {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 1, "��ͨ����", flag);
		appendOptions(sb, 2, "ͬҵ���", flag);
		appendOptions(sb, 3, "���뷵���Ŵ��ʲ�", flag);
		appendOptions(sb, 4, "���뷵��֤ȯ", flag);
		appendOptions(sb, 5, "����ծȨͶ��", flag);
		appendOptions(sb, 12, " ͬҵ����", flag);
		appendOptions(sb, 13, " �����ع��Ŵ��ʲ�", flag);
		appendOptions(sb, 14, " �����ع�֤ȯ", flag);
		return sb.toString();
	}

	//	��ҵ��ͬ״̬
	public static String getContractStatusOptions(String value)
			throws Exception {
		return getDictParamOptions(1136, value);
	}

	// �ͻ����
	public static String getCustomerTypeOptions(String value) throws Exception {
		return getDictParamOptions(1120, value);
	}

	//	�ͻ���Դ
	public static String getCustomerSourceOptions(String value)
			throws Exception {
		return getDictParamOptions(1110, value);
	}

	// ��ҵ����
	public static String getCompanyTypeOptions(String value) throws Exception {
		return getDictParamOptions(1121, value);
	}

	// �����;
	public static String getPurposeTypeOptions(String value) throws Exception {
		return getDictParamOptions(1122, value);
	}

	//	�����;
	public static String getPurposeTypName(String value) throws Exception {
		return getDictParamName(1122, value);
	}

	// �ɶ����
	public static String getHolderOptions(String value) throws Exception {
		return getDictParamOptions(1105, value);
	}

	//	���˼������\ְλ���
	public static String getPartnerOptions(String value) throws Exception {
		return getDictParamOptions(1106, value);
	}

	// ������ʽ
	public static String getFloatTypeOptions(String value) throws Exception {
		return getDictParamOptions(1123, value);
	}

	// ��������
	public static String getFloatPeriodOptions(String value) throws Exception {
		return getDictParamOptions(1124, value);
	}

	// ��������
	public static String getQualityLevelOptions(String value) throws Exception {
		return getDictParamOptions(1125, value);
	}

	//	�����������
	public static String getRateTypeOptions(String value) throws Exception {
		return getDictParamOptions2(1301, value);
	}

	//	�����������
	public static String getRateTypeName(String value) throws Exception {
		return getDictParamName(1301, value);
	}

	// ����Ҽ�Ϣ����
	public static String getRmbPeriodOptions(Integer value) throws Exception {
		StringBuffer sb = newStringBuffer();
		for (int i = 1; i < 13; i++)
			appendOptions(sb, i, Integer.toString(i), value);
		return sb.toString();
	}

	//	��������1���ڴ���2���ڴ���
	public static String getLoanTypeOptions(Integer flag) throws Exception {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 0, "����", flag);
		appendOptions(sb, 1, "�г���", flag);
		appendOptions(sb, 2, "����", flag);
		return sb.toString();
	}

	//	�ͻ����---û�С���ѡ��
	public static String getCustTypeOptions2(Integer value) throws Exception {
		StringBuffer sb = new StringBuffer(200);
		appendOptions(sb, 0, "��ѡ��", value);
		appendOptions(sb, 1, "����", value);
		appendOptions(sb, 2, "����", value);
		return sb.toString();
	}

	//	�ͻ����
	public static String getCustTypeOptions(Integer value) throws Exception {
		StringBuffer sb = new StringBuffer(200);
		appendOptions(sb, 0, "��ѡ��", value);
		appendOptions(sb, 1, "����", value);
		appendOptions(sb, 2, "����", value);
		return sb.toString();
	}

	// �ͻ������ϸ
	public static String getCustTypeDKDXOptions(Integer value) throws Exception {
		StringBuffer sb = new StringBuffer(200);
		appendOptions(sb, 0, "��ѡ��", value);
		appendOptions(sb, 1, "ũ��", value);
		appendOptions(sb, 2, "�������", value);
		appendOptions(sb, 3, "���̸���", value);
		return sb.toString();
	}

	//	����ְҵ����---û����ѡ��
	public static String getGrzyOptions2(String value) throws Exception {
		return getDictParamOptions2(1142, value);
	}

	//	��ϵ��ʽ
	public static String getTouchTypeOptions(String value) throws Exception {
		return getDictParamOptions(1109, value);
	}

	// �ͻ�����
	public static String getCountry(String value) throws Exception {
		return getDictParamOptions(9997, value);
	}

	//	���ݲ���ֵ�õ���������:�б�---û����ѡ��
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

	// �����ͻ�����
	public static String getJgCustType(String value) throws Exception {
		return getDictParamOptions(9921, value);
	}

	//	������ҵ����---û����ѡ��
	public static String getJghyOptions2(String value) throws Exception {
		return getDictParamOptions2(2142, value);
	}

	//	��������
	public static String getBankOptions(String value) throws Exception {
		return getDictParamOptions_intrust(1103, value);
	}

	//	����֤������---û����ѡ��
	public static String getCardTypeOptions2(String value) throws Exception {
		return getDictParamOptions2(1108, value);
	}

	// ֤������
	public static String getCardType(String value) throws Exception {
		return getDictParamOptions(1108, value);
	}

	//	����֤������---û����ѡ��
	public static String getCardTypeJgOptions2(Object value) throws Exception {
		String val = "";
		if (value != null)
			val = value.toString();
		return getDictParamOptions2(2108, val);
	}

	//	���ü���
	public static String getCreditLevelOptions(String value) throws Exception {
		return getDictParamOptions(2118, value);
	}

	//��ֵ����ʽ
	public static String getClbzOptions(String value) throws Exception {
		return getDictParamOptions(1138, value);
	}

	//	���������־
	public static String getCreditTypeOptions(int flag) throws Exception {
		StringBuffer sb = newStringBuffer();
		//appendOptions(sb, 1, "���", flag);
		appendOptions(sb, 2, "����", flag);
		//appendOptions(sb, 3, "���ڻ���", flag);
		return sb.toString();
	}

	/**
	 * ����,û����ѡ��
	 *
	 * @param value
	 *            ��ǰֵ
	 *
	 * @return
	 * @throws Exception
	 */
	public static String getCurrencyOptions2(String value) throws Exception {
		StringBuffer sb = new StringBuffer(200);
		sb.append("<option value=\" \">��ѡ��</option>");
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
	 * // Ҷ�Ӳ����б� public static String getDepartName(Integer depart_id) throws
	 * Exception { StringBuffer sb = newStringBuffer(); Connection conn =
	 * DBManager.getConnection(); CallableStatement stmt = conn.prepareCall(
	 * "{call SP_QUERY_TDEPARTMENT(0,0,0)}", ResultSet.TYPE_SCROLL_INSENSITIVE,
	 * ResultSet.CONCUR_READ_ONLY); ResultSet rslist = stmt.executeQuery(); try {
	 * while (rslist.next()) { //if (rslist.getInt("bottom_flag") == 1)
	 * Argument.appendOptions( sb, rslist.getInt("depart_id"),
	 * rslist.getString("depart_name"), depart_id); } } finally {
	 * rslist.close(); stmt.close(); conn.close(); } return sb.toString(); }
	 */
	//����role_id����ѯ��ɫ����
	public static String getRole_name2(Integer role_id) throws Exception {
		StringBuffer sb = newStringBuffer();
		sb.append("<option value=\"-1\">��ѡ��</option>");
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

	//	��˱�־0δ���1�����
	public static String getCheckFlagOptions(Integer flag) {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "��ѡ��", flag);
		appendOptions(sb, 1, "δ���", flag);
		appendOptions(sb, 2, "�����", flag);
		return sb.toString();
	}

	public static String getSaleCheckFlagOptions(Integer flag) {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, -9, "δ����", flag);
		appendOptions(sb, 1, "δ���", flag);
		appendOptions(sb, 2, "�����", flag);
		return sb.toString();
	}

	public static String getCheckFlagName(Integer flag) {
		if (flag == null)
			return null;
		switch (flag.intValue()) {
		case 1:
			return "δ���";
		case 2:
			return "�����";
		case 3:
			return "����";
		default:
			return "δ֪";
		}
	}

	public static String getCheckFlagName2(Integer flag) {
		if (flag == null)
			return null;
		switch (flag.intValue()) {
		case 1:
			return "δ���";
		case 2:
			return "�����";
		case 3:
			return "��˲�ͨ��";
		default:
			return "δ֪";
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

	//	�����ļ����ӱ�־
	public static String getAddtionalOptions(String value) throws Exception {
		return getDictParamOptions(1132, value);
	}

	//	����1��2��
	public static String getDirectionOptions(int flag) {
		StringBuffer sb = newStringBufferBit();
		appendOptions(sb, 1, "��", flag);
		appendOptions(sb, 0, "��", flag);
		return sb.toString();
	}

	//	��Ŀ���
	public static String getSubTypeOptions(String value) throws Exception {
		return getDictParamOptions(2001, value);
	}

	//ȡ�ò���Ա�����˵�
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

	//���� �Ļ��ʽ
	public static String getHKtypeOptions(Integer flag) {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 100, "�ȶ��", flag);
		appendOptions(sb, 200, "�ȱ�����", flag);
		appendOptions(sb, 300, "ʵ�����ʷ�", flag);
		appendOptions(sb, 400, "ֱ�߷�", flag);
		return sb.toString();
	}

	//	���� �Ļ��ʽ
	public static String getHKtypeName(Integer flag) {
		StringBuffer retValue = null;
		if (flag == null)
			return "";
		if (flag.intValue() == 100)
			retValue = new StringBuffer("�ȶ��");
		else if (flag.intValue() == 200)
			retValue = new StringBuffer("�ȱ�����");
		else if (flag.intValue() == 300)
			retValue = new StringBuffer("ʵ�����ʷ�");
		else if (flag.intValue() == 400)
			retValue = new StringBuffer("ֱ�߷�");
		return retValue.toString();
	}

	//��ȡ��������
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

	//ȡ�ø�������
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
			return "��ͨ����";
		else if (flag.intValue() == 2)
			return "ͬҵ���";
		else if (flag.intValue() == 3)
			return "���뷵���Ŵ��ʲ�";
		else if (flag.intValue() == 4)
			return "���뷵��֤ȯ";
		else if (flag.intValue() == 5)
			return "����ծȨͶ��";
		else if (flag.intValue() == 12)
			return "ͬҵ����";
		else if (flag.intValue() == 13)
			return "�����ع��Ŵ��ʲ�";
		else if (flag.intValue() == 14)
			return "�����ع�֤ȯ";
		else
			return null;
	}

	//	���ݿ�Ŀ����õ�����
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

	//	ƾ֤����
	public static String getPzTypeOptions(String value) throws Exception {
		return getDictParamOptions2(2003, value);
	}

	//ƾ֤��Դ
	public static String getPzSourceTypeOptions(String value) throws Exception {
		return getDictParamOptions2(1002, value);
	}

	//��������1��������2��������
	public static String getDirectionTypeOptions(Integer flag) throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 1, "��ʾ�跽�ϼ�", flag);
		appendOptions(sb, 2, "��ʾ�����ϼ�", flag);
		return sb.toString();
	}

	public static String getPunishLabel(boolean rmb, Integer new_flag) {
		if (new_flag == null)
			return "";

		if (rmb && (new_flag.intValue() == 2))
			return "<img border='0' src='/images/wan.gif'>(��)";
		else
			return "%(�ϸ�)";
	}

	public static String getBusiManPrint(Integer book_code) {
		if (book_code == null)
			return "";
		else if (book_code.intValue() == 1)
			return "ִ�о���";
		else if (book_code.intValue() == 2)
			return "ҵ��Ա";
		else
			return "";
	}

	public static String getDepartPrint(Integer book_code) {
		if (book_code == null)
			return "";
		else if (book_code.intValue() == 1)
			return "�ʽ��йܲ�";
		else if (book_code.intValue() == 2)
			return "�ƲƲ�";
		else
			return "";
	}

	public static String getCopytoPrint(Integer book_code) {
		if (book_code == null)
			return "";
		else if (book_code.intValue() == 1)
			return "ִ�о���";
		else if (book_code.intValue() == 2)
			return "�ʲ������ܲ�";
		else
			return "";
	}

	public static String getLoanTypeName(Integer flag) throws Exception {
		if (flag == null)
			return null;
		if (flag.intValue() == 0)
			return "����";
		else if (flag.intValue() == 1)
			return "�г���";
		else if (flag.intValue() == 2)
			return "����";
		return null;
	}

	//	��ȨͶ��ҵ�����
	public static String getEntInvestTypeOptions(String value) throws Exception {
		return getDictParamOptions(1303, value);
	}

	//	��ҵ
	public static String getVocationOptions(String value) throws Exception {
		return getDictParamOptions(1135, value);
	}

	// ��Ҹ�������
	public static String getWbFdPeriodOptions(Integer value) throws Exception {
		StringBuffer sb = newStringBuffer();
		for (int i = 1; i < 13; i++)
			appendOptions(sb, i, Integer.toString(i), value);
		return sb.toString();
	}

	// ���в˵�Ȩ�޵Ĳ���Ա
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

	//�������
	// modfiy by whf �����û����������� �����������Ժ���ʵ��ü����գ�������������ǰ���ʵ��ü����� ��������������
	//update by tsg 20090628 ǰ������ע�ͣ���������Ĳ������ܱ仯��value��Ȼ��2 3
	public static String getPostTypeOptions(int flag) {
		StringBuffer sb = new StringBuffer();
		//appendOptions(sb, 0, "�����������Ժ���ʵ��ü�����", flag);
		//appendOptions(sb, 1, "������������ǰ���ʵ��ü�����", flag);
		appendOptions(sb, 2, "��ƾ֤Ԥ������ռ���[ȫ��]", flag);
		appendOptions(sb, 3, "��ƾ֤Ԥ������ռ���[��������Ϊֹ]", flag);
		return sb.toString();
	}

	//	�ʲ������Ϣ�б�
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

	//	��ĿͶ��ҵ�����
	public static String getInvestTypeOptions(String value) throws Exception {
		return getDictParamOptions(1203, value);
	}

	//	�ʲ�����
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
	 * ����,ָ��ʾ����
	 *
	 * @param value
	 *            ��ǰֵ
	 *
	 * @return
	 * @throws Exception
	 */
	public static String getCurrencyOptions5(String value) throws Exception {
		StringBuffer sb = new StringBuffer(200);
		sb.append("<option value=\" \">��ѡ��</option>");
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
			return "��(��)";
		else
			return "%(��)";
	}

	/**
	 * ADD BY TSG 20080618 ����pay_sbf_serial_no��ȡ��������
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
	 * ADD BY TSG 20080618 ����pay_sbf_serial_no��ȡ��������
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
	 * ����pay_sbf_serial_no��ȡ������Ϣ
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
	 * @author Lzhd �õ�һ����Ŀ
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

	//	Ͷ��������־
	public static String getZjFlagOptions(String invest_type, Integer flag)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 1, "Ͷ�ʹɱ����", flag);
		appendOptions(sb, 2, "ע��ɱ����", flag);
		return sb.toString();
	}

	/**
	 * update by tsg 20081127 SP_QUERY_TCOMMCREDIT_YHKBH
	 *
	 * @IN_BOOK_CODE INT,
	 * @IN_DEAL_FLAG VARCHAR(10),
	 * @IN_YHK_FLAG INT, -- 0 �ÿ� 1 ����
	 * @IN_FLAG INT = 1 -- 1 �������� 2 ��ǰ����
	 * @IN_FLAGΪ�¼ӵĲ���, Ŀǰ�洢�����޸ĺ�,ȱʡ�����,�����ʱ��,ֻ��ʾ�������������Ҫ����ĺ�ͬ. ���ѡ����ǰ�����־,
	 *                      ����ʾ������Ҫ����ĺ�ͬ.
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
		stmt.setInt(3, yfk_flag); //1 �ÿ� 2 ����
		stmt.setInt(4, pre_pay_flag); //-- 1 �������� 2 ��ǰ����
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
		stmt.setInt(3, yfk_flag); //1 �ÿ� 2 ����
		stmt.setInt(4, pre_pay_flag); //-- 1 �������� 2 ��ǰ����
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

	//	��Ŀ����һ�����������ģ��壬��
	public static String getSubjectLevelOptions(int flag) throws Exception {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 0, "ĩ��", flag);
		appendOptions(sb, 1, "һ��", flag);
		appendOptions(sb, 2, "����", flag);
		appendOptions(sb, 3, "����", flag);
		appendOptions(sb, 4, "�ļ�", flag);
		appendOptions(sb, 5, "�弶", flag);
		appendOptions(sb, 6, "����", flag);
		appendOptions(sb, 7, "�߼�", flag);
		return sb.toString();
	}

	//��Ŀ���
	public static String getSubjectTypeOptions(String subtype) throws Exception {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, "200101", "�����ʲ���", subtype);
		appendOptions(sb, "200102", "���и�ծ��", subtype);
		appendOptions(sb, "200103", "����Ȩ����", subtype);
		appendOptions(sb, "200104", "����������", subtype);
		appendOptions(sb, "200105", "�����Ŀ", subtype);

		return sb.toString();
	}

	//�����û�id��ȡ�û�����
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

	//	��ϸ�ʲ�ѯʱ����Ŀ��ʼ�б�
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

	//	��ϸ�ʲ�ѯʱ����Ŀ��ʼ�б�
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

	//	���ݲ�ƷID�ͱ���ID��ѯ��������
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

	//��ͬ�����־
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
	 * add by tsg 2008-01-15 �Ի�ȡ1205,1206ѡ��
	 *
	 */
	public static String getAllDictParamOptions1(Integer value)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 1205, "1205_Ӫҵ����", value);
		appendOptions(sb, 1206, "1206_����֧��", value);
		return sb.toString();
	}

	//	�õ��̶���Ϣ�ձ�־
	public static String getFixInterestDayOptions(int flag) throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "�ǹ̶���", flag);
		appendOptions(sb, 1, "3��21��", flag);
		appendOptions(sb, 2, "6��21��", flag);
		appendOptions(sb, 3, "9��21��", flag);
		appendOptions(sb, 4, "12��21��", flag);
		return sb.toString();
	}

	//	�õ��̶���Ϣ�ձ�־
	public static String getFixInterestDayOptionsName(int flag)
			throws Exception {
		String day = "";
		if (flag == 1)
			day = "3��21��";
		else if (flag == 2)
			day = "6��21��";
		else if (flag == 3)
			day = "9��21��";
		else if (flag == 4)
			day = "12��21��";
		else
			day = "�ǹ̶���";

		return day;
	}

	//	��Ϣ���
	public static String getSubjectBondTypeOptions(String value)
			throws Exception {
		return getDictParamOptions(2005, value);
	}

	/**
	 * add by tsg 2008-01-15 �Ի�ȡ1205,1206ѡ��
	 *
	 */

	//ҵ����
	public static String getBusiid1Options(String value) throws Exception {
		return getDictParamOptions(1205, value);
	}

	public static String getBusiid2Options(String value) throws Exception {
		return getDictParamOptions(1206, value);
	}

	//ҵ�����1����2����
	public static String getBusiTypeOptions(Integer flag) throws Exception {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 0, "����", flag);
		appendOptions(sb, 1, "����", flag);
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

	//��ȡ���˱�־
	public static String getDZFlagOptions(Integer flag) throws Exception {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 1, "δ����", flag);
		appendOptions(sb, 2, "�Ѷ�ƽ", flag);
		appendOptions(sb, 3, "δ��ƽ", flag); //add duanpeng 20070110
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
						+ "-��ʼ/�������룺"
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

	//	��ϸ�ʲ�ѯʱ����Ŀ��ʼ�б�
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

	//��ϸ�ʲ�ѯʱ����Ŀ��ʼ�б�
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
	 * ���ڸ�ʽת�� ��2001-01-01ת����20010101
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

	//	�̶��ʲ�---�۾ɷ���
	public static String getDeprMethodOptions(String value) throws Exception {
		return getDictParamOptions(1120, value);
	}

	//	��˱�־0δ���1�����
	public static String getVaryFlagOptions(Integer flag, Integer serial_no) {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 1, "����", flag);
		appendOptions(sb, 2, "����", flag);
		if (serial_no.intValue() > 0)
			appendOptions(sb, 3, "�䶯", flag);
		return sb.toString();
	}

	public static String getVaryFlag(Integer flag) {
		if (flag == null)
			return null;
		switch (flag.intValue()) {
		case 1:
			return "����";
		case 2:
			return "����";
		case 3:
			return "�䶯";
		default:
			return "δ֪";
		}
	}

	//	��˱�־0δ���1�����
	public static String getAccountFlagOptions(Integer flag) {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 2, "ȫ��", flag);
		appendOptions(sb, 0, "�����л�ƺ���", flag);
		appendOptions(sb, 1, "���л�ƺ���", flag);
		return sb.toString();
	}

	//	�̶��ʲ�---�۾ɷ���
	public static String getDeprMethodOptions1(String value) throws Exception {
		return getDictParamOptions(1913, value);
	}

	//����ȡ�ʲ���� tsg 20080724
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

	//	��˱�־0δ���1�����
	public static String getReferFlagOptions(Integer flag) {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 2, "ȫ��", flag);
		appendOptions(sb, 0, "δ�ύ", flag);
		appendOptions(sb, 1, "���ύ", flag);
		return sb.toString();
	}

	//	��˱�־0δ���1�����
	public static String getcheckFlagOptions(Integer flag) {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 3, "ȫ��", flag);
		appendOptions(sb, 0, "δ���", flag);
		appendOptions(sb, 1, "���ͨ��", flag);
		appendOptions(sb, 2, "��˲�ͨ��", flag);
		return sb.toString();
	}

	// Ҷ�Ӳ����б�
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

	// Ҷ�Ӳ����б�
	//��ȡ���۾���Ϣ�Ĳ���
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

	//	��Ϣ����
	public static String getJxPeriodOptions(Integer flag) throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 1, "��", flag);
		appendOptions(sb, 3, "��", flag);
		appendOptions(sb, 6, "����", flag);
		appendOptions(sb, 12, "��", flag);
		return sb.toString();
	}

	//	�̶��ʲ��۾��������ƾ֤��ʽ
	public static String getVoucherTypeOptions(Integer flag) throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 1, "���ʲ�����ƾ֤", flag);
		appendOptions(sb, 2, "����������ƾ֤", flag);
		return sb.toString();
	}

	//	��ȡ�б�־
	public static String getColsTypeOptions(Integer col_flag) throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "����", col_flag);
		appendOptions(sb, 1, "����", col_flag);
		appendOptions(sb, 2, "����", col_flag);
		return sb.toString();
	}

	//	��ȡ�ֽ���������������־
	public static String getCashInOutOptions(Integer in_Out) throws Exception {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 1, "����", in_Out);
		appendOptions(sb, 2, "����", in_Out);
		appendOptions(sb, 3, "����", in_Out);
		return sb.toString();
	}

	/**
	 * lzhd 2007/12/10 update by tsg 2008-01-08 �ĳ�ͨ�õ�
	 * ����type_id�л�ȡ��TINTEGERPARAM�л�ȡ�������б�
	 */

	public static String getTableOptions1(int type_id, Integer defaultValue)
			throws Exception {
		StringBuffer sb = newStringBuffer();

		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TINTEGERPARAM (?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, type_id);
		//��Ӧ��������Ӧ�ô�TINTEGERPARAM������ͳһ��ȡ, TYPE_ID = 1101
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

	//����type_value��ȡ��Ϣ��������
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
	 * ����type_id��type_value�л�ȡ��TINTEGERPARAM�л�ȡcontentֵ
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
	 * ����type_value�л�ȡ��TDICTPARAM�л�ȡcontentֵ
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
	 * flag 0 -- ��ѯȫ�� 1 -- �ƽ��� 2 -- ������ 3 -- �ѽ�����Ʒ 10 -- ��������״̬���ȫ����Ʒ 11 --
	 * �ƽ鼰�����ڲ�Ʒ 12 -- �ƽ����Ѿ���˵��ʽ����в�Ʒ 13 -- ��������״̬��ĲƲ����в�Ʒ 14 -- �Ѿ����������˵ļ����ʽ�����
	 * 15 -- �����ڼ������ڲ�Ʒ 16 -- �����ڵĵ�һ��Ʒ���ƽ��ڵļ��ϲ�Ʒ 17 -- �ƽ鼰�����ڲ�Ʒ���ʽ����в�Ʒ 18 --
	 * �Ʋ������ƽ��ڵĲ�Ʒ 19 -- ���Խ�������Ĳ�Ʒ
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
			appendOptions(sb, 0, "ȫ����Ʒ", product_id);
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
	 * sale_status: ��Ʒ������״̬ 1Ĭ��ֵ�������� 2������
	 * product_status: '0'����ԤԼ�ڡ������ڵĲ�Ʒ; ��������ԤԼ�Ĳ�Ʒ
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
			//appendOptions(sb, "0-0", "ȫ����Ʒ", selected);
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
	 * ��TINTEGERPARAM���л�ȡCONTENT
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
	
	

	//��ѯ���������¼�������б�
	/**
	 * SP_QUERY_TPRECOMMCREDIT_LIST
	 *
	 * @IN_BOOK_CODE TINYINT,
	 * @IN_CHECK_FLAG TINYINT =0, --��˱�־��1δ����2������3����ͨ��4������ͨ��5��ǩ����ͬ
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

	//���ϰ������
	public static String getSs_typeOptions(String value) throws Exception {
		return getDictParamOptions(2204, value);
	}

	//����ԭҵ�����
	public static String getOldBusi_idOptions(String value) throws Exception {
		return getDictParamOptions(2203, value);
	}

	//	���Ͻ׶� 2205 wangc 20060829
	public static String getSs_statusOptions(String value) throws Exception {
		return getDictParamOptions(2205, value);
	}

	//ִ�н׶� 2206 wangc 20060829
	public static String getZx_statusOptions(String value) throws Exception {
		return getDictParamOptions(2206, value);
	}

	//	�����б� 20060901 wangc
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
	 * add by tsg 2008-12-15 ����user_Id��ȡ�û���logoͼƬ��Ϣ
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

	//�Ա� 2008-12-18 ADD YZJ
	public static String getSexOptions(Integer flag) throws Exception {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 1, "��", flag);
		appendOptions(sb, 2, "Ů", flag);
		return sb.toString();
	}

	//��ѯ�Ѿ�������˵ĺ�ͬ�б�
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
	
	
//	��ѯ��Ϣģ��
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

	//	��ѯ�������ʵĿͻ��б�
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

	//	���ݲ�ƷID�ͱ���ID��ѯ�������� (û�С���ѡ��200-01-07 ADD YUZJ)
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
	 * ADD BY TSG 2009-03-03 ����ҵ�����Ͷ������ͻ�ȡ�����б� SP_QUERY_BOC_SERVICE
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
	 * ADD BY TSG 2009-03-16 ��ȡΨһ��ʶ�� SP_BACK_UNIQUEIDENTIFIER_VALUE
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
	 * ADD BY zxj 2015-06-02 �����Ӳ�Ʒid��ȡ�˲�Ʒ����
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
	 * ADD BY TSG 2009-03-11 ��ȡ���м�����Ϣ
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
			throw new BusiException("��ȡ���м�����Ϣ����:" + e.getMessage());
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
	 * ADD BY TSG 2009-03-18 ���ݷ���ID��ȡFTPID��Ϣ
	 *
	 * @param service_id
	 *            ����ID
	 * @param deal_flag
	 *            1 �ϴ���2 ����
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
			throw new BusiException("��ȡ���м�����Ϣ����:" + e.getMessage());
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
	 * ADD BY TSG 2009-03-18 ���ݷ���ID��ȡ����ID��Ϣ
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
			throw new BusiException("��ȡ����ID��Ϣ����:" + e.getMessage());
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
	 * ADD BY TSG 2009-03-23 ��ȡ�ļ���Ϣ
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
			throw new BusiException("���ļ�����Ϣ��Ϣ����:" + e.getMessage());
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
	 * ADD BY TSG 2009-04-20 ���������б�
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
	 * ADD BY TSG 2009-04-20 ��ȡ���۶�������б�
	 *
	 */
	public static String getObjectTypeOptions(Integer default_object_type)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "��˾����", default_object_type);
		appendOptions(sb, 1, "���з���", default_object_type);
		return sb.toString();
	}

	/**
	 * ADD BY TSG 2009-04-20 ��ȡ��������б�--
	 * 0��ʾȫ����1��ʾ���пۿ�ɹ������ش���Ҳ�ɹ���2��ʾ���пۿ�ɹ������ش����д����ѽ����3��ʾ���пۿ�ɹ������ش����д���δ�����4��������ʧ��
	 *
	 */
	public static String getDealTypeOptions(Integer default_object_type)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "ȫ��", default_object_type);
		appendOptions(sb, 1, "���гɹ������ش���Ҳ�ɹ�", default_object_type);
		appendOptions(sb, 2, "���гɹ������ش����д����ѽ��", default_object_type);
		appendOptions(sb, 3, "���гɹ������ش����д���δ���", default_object_type);
		appendOptions(sb, 4, "��������ʧ��", default_object_type);
		return sb.toString();
	}

	/**
	 * ADD BY TSG 2009-04-23 ��ȡ��ǰ�������
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
	 * ADD BY TSG 2009-05-05 ��ȡ���ж�Ӧ�Ŀ�Ŀ����
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
	 * ADD BY TSG 2009-05-05 ��ȡ����id
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
	 * ADD BY TSG 2009-05-21 ҵ�����
	 */
	public static String getBusiTypeOptions1(Integer flag) throws Exception {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 1, "�ÿ�", flag);
		appendOptions(sb, 2, "����", flag);
		return sb.toString();
	}

	/**
	 * ADD BY TSG 2009-05-21 ��ȡ������ʽ����
	 */
	public static String getDbfsName(int flag) {
		String dbfs_name = "";
		if (flag == 0)
			dbfs_name = "����";
		else if (flag == 1)
			dbfs_name = "��֤";
		else if (flag == 2)
			dbfs_name = "��Ѻ";
		else if (flag == 3)
			dbfs_name = "��Ѻ";

		return dbfs_name;

	}

	/**
	 * ADD BY TSG 2009-05-21 ��ȡ���״�����Ϣ
	 */
	public static String getTradeCodeInfo(String code) {
		String code_name = "";
		if (code.equals("010001"))
			code_name = "������Ϣ����";
		else if (code.equals("010002"))
			code_name = "������Ϣ��ѯ";
		else if (code.equals("010003"))
			code_name = "������Ϣ�޸�";
		else if (code.equals("010004"))
			code_name = "��Ч/ʧЧ";
		else if (code.equals("010005"))
			code_name = "�ʽ𹹳���Ϣ����";
		else if (code.equals("010006"))
			code_name = "�ʽ𹹳���Ϣ����";
		else if (code.equals("010007"))
			code_name = "�ʽ𹹳���Ϣ��ѯ";
		else if (code.equals("010008"))
			code_name = "�ʽ𹹳���Ϣ�޸�";
		else if (code.equals("020001"))
			code_name = "������Ϣ�Ǽ�";
		else if (code.equals("020002"))
			code_name = "������Ϣ�ǼǷ�����";
		else if (code.equals("020003"))
			code_name = "������Ϣ��ѯ";
		else if (code.equals("020004"))
			code_name = "���������Ϣ�޸�";
		else if (code.equals("020005"))
			code_name = "����黹��Ϣ�Ǽ�";
		else if (code.equals("020006"))
			code_name = "����黹��Ϣ�ǼǷ�����";
		else if (code.equals("020007"))
			code_name = "����黹��Ϣ��ѯ";
		else if (code.equals("020008"))
			code_name = "����黹��Ϣ�޸�";
		else if (code.equals("070003"))
			code_name = "ʵʱ���������";
		else if (code.equals("070004"))
			code_name = "ʵʱ���������";

		return code_name;
	}

	/**
	 * add by tsg 2008-12-15 ����user_Id��ȡ�û���logoͼƬ��Ϣ
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
	 * add by tsg 2009-06-05 ���а���ֲ �õ����в���ָ����� add by nizh 2009-03-13
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
				//�����1���ػ�ȡ����ֵ
				if (type == 1) {
					i++;
					s.append(split + type_value);
				}
				//�����2���ػ�ȡ��������
				else if (type == 2) {
					////Ӫ���ʽ���ʾ,�޹�ʽ����ʾ
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
	 * ��ͬ��Ϣ���ܱ�-��ͬ״̬
	 *
	 */
	public static String getCreditStatusOptions(Integer statFlag)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 0, "����", statFlag);
		appendOptions(sb, 1, "����", statFlag);
		appendOptions(sb, 6, "����", statFlag);
		appendOptions(sb, 7, "����", statFlag);
		return sb.toString();
	}

	/**
	 * ��ͬ��Ϣ���ܱ�-������ʽ
	 *
	 */
	public static String getDBFSTypeOptions(String type) throws Exception {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, "112601", "����", type);
		appendOptions(sb, "112602", "��֤", type);
		appendOptions(sb, "112603", "��Ѻ", type);
		appendOptions(sb, "112604", "��Ѻ", type);
		appendOptions(sb, "112605", "����", type);
		return sb.toString();
	}

	/**
	 * ��ͬ��Ϣ���ܱ�-��ͬ״̬
	 *
	 */
	public static String getCreditStatusName(Integer statFlag) {
		int flagValue = (statFlag == null ? -1 : statFlag.intValue());
		if (flagValue == 0)
			return "����";
		else if (flagValue == 1)
			return "��֤";
		else if (flagValue == 6)
			return "��Ѻ";
		else if (flagValue == 7)
			return "��Ѻ";
		else
			return "";
	}

	/**
	 * ��ͬ��Ϣ���ܱ�-������ʽ
	 *
	 */
	public static String getDBFSTypeName(String type) {
		if (type.equals("112601"))
			return "����";
		else if (type.equals("112602"))
			return "��֤";
		else if (type.equals("112603"))
			return "��Ѻ";
		else if (type.equals("112604"))
			return "��Ѻ";
		else if (type.equals("112605"))
			return "����";
		else
			return "";
	}

	//��Ʒ״̬
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

	//��Ʒ���з�ʽ
	public static String getOpenFlag(Integer flag) throws Exception {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 2, "���ʽ", flag);
		appendOptions(sb, 1, "����ʽ", flag);
		return sb.toString();
	}

	//���ϣ���һ
	public static String getIntrust_flag1(Integer flag) throws Exception {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 1, "��һ", flag);
		appendOptions(sb, 2, "����", flag);
		return sb.toString();
	}

	/**
	 * ���ϱ�־
	 *
	 * @author dingyj
	 * @since 2009-11-23
	 * @param flag
	 * @return
	 */
	public static String getRepeatCustomerOptions(Integer flag) {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 1, "֤������", flag);
		appendOptions(sb, 2, "�ͻ�����", flag);
		return sb.toString();
	}

	/**
	 * ���������ϵ
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
		appendOptions(sb, 0, "��ѡ��", gradeid);
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
	 * ���������ϵ����
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
	 * ���ݿͻ���Ż�ÿͻ�����
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
	 * �ͻ�������˱�־ �б�
	 *
	 * @author dingyj
	 * @since 2009-11-25
	 * @param flag
	 * @throws Exception
	 * @return sb
	 */
	public static String getCrmGradeCheckOptions(Integer flag) throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "--ѡ���־--", flag);
		appendOptions(sb, 1, "δ����", flag);
		appendOptions(sb, 2, "������", flag);
		appendOptions(sb, 3, "����ͨ��", flag);
		return sb.toString();
	}

	/**
	 * ���ϵͳ����
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
	 * �����ϵ��ʽ
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

	// ԤԼ����
	public static String getPreNumOptions(Integer pre_num) throws Exception {
		StringBuffer sb = newStringBuffer();
		for (int i = 1; i < 10; i++)
			appendOptions(sb, i, "" + i, pre_num);
		return sb.toString();
	}

	//ԤԼ��ʽ
	public static String getPreTypeOptions(String value) throws Exception {
		return getDictParamOptions_intrust(1112, value);
	}

	/**
	 * ����Ա���������
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

		sb.append("<option value=\"\">��ѡ��</option>");
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			appendOptions(sb, Utility.parseInt(map.get("OP_CODE").toString(),
					new Integer(0)), Utility.trimNull(map.get("OP_NAME")),
					bottom_flag);
		}
		return sb.toString();
	}
	
	/**
	 * ����Ա���������
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

		sb.append("<option value=\"\">��ѡ��</option>");
		for (int i = 0; i < operator_list.size(); i++) {
			map = (Map) operator_list.get(i);
			appendOptions(sb, Utility.parseInt(map.get("OP_CODE").toString(),
					new Integer(0)), Utility.trimNull(map.get("OP_NAME")),
					bottom_flag);
		}
		return sb.toString();
	}
	/**
	 * �ͻ�������������
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

		sb.append("<option value=\"\">��ѡ��</option>");
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			appendOptions(sb, Utility.parseInt(Utility.trimNull(map
					.get("ManagerID")), new Integer(0)), Utility.trimNull(map
					.get("ManagerName")), bottom_flag);
		}
		return sb.toString();
	}
	
	/**
	 * �ͻ�������������
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

		sb.append("<option value=\"\">��ѡ��</option>");
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			appendOptions(sb, Utility.trimNull(map.get("ManagerName")), 
					Utility.trimNull(map.get("ManagerName")), name);
		}
		return sb.toString();
	}
	
	/**
	 * �Ŷӱ��������
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

		sb.append("<option value=\"\">��ѡ��</option>");
		sb.append("<option value=\"0\">�޸��Ŷ�</option>");
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			appendOptions(sb, Utility.parseInt(Utility.trimNull(map
					.get("TEAM_ID")), new Integer(0)), Utility.trimNull(map
					.get("TEAM_NAME")), bottom_flag);
		}
		return sb.toString();
	}
	
    /**
     * Ӫ������
     * @return
     * @throws Exception
     */
	public static String getTeam_mark() throws Exception {
		StringBuffer sb = new StringBuffer();
		Map map = null;
		TmanagerteamsVO vo = new TmanagerteamsVO();
		TmanagerteamsLocal tmanagerteams_Bean = EJBFactory.getTmanagerteams();
		List list = tmanagerteams_Bean.list_query(vo);

		sb.append("<option value=\"\">��ѡ��</option>");
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
	 * ����Ȩ��ȡ��Ȩ�޵Ŀͻ�������������
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

		sb.append("<option value=\"\">��ѡ��</option>");
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			appendOptions(sb, Utility.parseInt(Utility.trimNull(map
					.get("ManagerID")), new Integer(0)), Utility.trimNull(map
					.get("ManagerName")), service_man);
		}
		return sb.toString();
	}
	/**
	 * ��ȡ�Ŷ��б�
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
	 * �ͻ�������������
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

		sb.append("<option value=\"\">��ѡ��</option>");
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			appendOptions(sb, Utility.parseInt(Utility.trimNull(map
					.get("ManagerID")), new Integer(0)), Utility.trimNull(map
					.get("ManagerName")), bottom_flag);
		}
		return sb.toString();
	}

	/**
	 * �ͻ�����ID������
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

		sb.append("<option value=\"\">��ѡ��</option>");
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			appendOptions(sb, Utility.parseInt(map.get("ManagerID").toString(),
					new Integer(0)), Utility.trimNull(map.get("ManagerName")),
					bottom_flag);
		}
		return sb.toString();
	}

	//���ݿͻ������Ż�ÿͻ���������
	public static String getManager_Name(Integer managerID) throws Exception {
		//��ֹ��ȫ��
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

	//	��ͬԤԼ��ʽ
	public static String getPreFlagOptions(Integer value) throws Exception {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 1, "��ԤԼ", value);
		appendOptions(sb, 2, "��ԤԼ", value);
		return sb.toString();
	}

	//	ָ��������־
	public static String getPreflag(Integer value) throws Exception {
		if (value.intValue() == 1)
			return "��ԤԼ";
		else if (value.intValue() == 2)
			return "��ԤԼ";
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
	 * �������
	 *
	 * @author dingyj
	 * @since 2009-11-27
	 * @param value
	 * @return
	 */
	public static String getWeekOption(Integer value) throws Exception {
		int val = value.intValue();
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, 0, "������", val);
		appendOptions(sb, 1, "����һ", val);
		appendOptions(sb, 2, "���ڶ�", val);
		appendOptions(sb, 3, "������", val);
		appendOptions(sb, 4, "������", val);
		appendOptions(sb, 5, "������", val);
		appendOptions(sb, 6, "������", val);
		return sb.toString();
	}

	/**
	 * �����������ϵ��ʽѡ��
	 *
	 * @author dingyj
	 * @since 2009-11-27
	 * @param val
	 *            ���ֵ
	 * @param list
	 *            ���м���
	 * @param type
	 *            ��ȡ�ֶ�(map�л�ȡ���ֶ�)
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
	 * CRM ��������ѯ������
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

		sb.append("<option value=\"0\">��ѡ��</option>");
		for (int i = 0; i < rsList.size(); i++) {
			map = (Map) rsList.get(i);
			appendOptions(sb, Utility.parseInt(map.get("ServiceType")
					.toString(), new Integer(0)), Utility.trimNull(map
					.get("ServiceTypeName")), ServiceType);
		}
		return sb.toString();
	}

	/**
	 * ����״̬ѡ��������
	 */
	public static String getService_status(int flag) {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "��ѡ��", flag);
		appendOptions(sb, 1, "δ����", flag);
		appendOptions(sb, 2, "������", flag);
		appendOptions(sb, 3, "������", flag);
		appendOptions(sb, 4, "�Ѵ���", flag);
		appendOptions(sb, 9, "����", flag);
		return sb.toString();
	}

	/**
	 * �õ�����״̬����
	 */
	public static String getService_status_name(int flag) {
		String service_status_name = "";

		switch (flag) {
		case 0:
			service_status_name = "��ѡ��";
			break;
		case 1:
			service_status_name = "δ����";
			break;
		case 2:
			service_status_name = "������";
			break;
		case 3:
			service_status_name = "������";
			break;
		case 4:
			service_status_name = "�Ѵ���";
			break;
		case 8:
			service_status_name = "ʧ��";
			break;
		case 9:
			service_status_name = "����";
			break;
		}

		return service_status_name;
	}

	/**
	 * �ͻ�������б�
	 */
	public static String getCustomerSatifaction(int flag) {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "��ѡ��", flag);
		appendOptions(sb, 1, "1", flag);
		appendOptions(sb, 2, "2", flag);
		appendOptions(sb, 3, "3", flag);
		appendOptions(sb, 4, "4", flag);
		appendOptions(sb, 5, "5", flag);
		return sb.toString();
	}

	/**
	 * ������䷽ʽ
	 */
	public static String getBonus_flag(Integer flag) {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 1, "�ֽ�", flag);
		appendOptions(sb, 2, "ת�ݶ�", flag);
		return sb.toString();
	}

	//���ýɿʽ��1�ӱ���ۣ�2���⽻,0����
	public static String getFeeJkTypeName(int flag) {
		if (flag == 0)
			return "�������";
		else if (flag == 1)
			return "�ӱ���۳�";
		else if (flag == 2)
			return "�������";
		else
			return "";
	}

	//���ýɿʽ��1�ӱ���ۣ�2���⽻,0����
	public static String getFeeJkTypeOptions(int flag) {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "�������", flag);
		appendOptions(sb, 1, "�ӱ���۳�", flag);
		appendOptions(sb, 2, "�������", flag);
		return sb.toString();
	}

	//	��������1��������2��������
	public static String getProductUnitName(Integer flag) throws Exception {
		if (flag == null)
			return "";
		switch (flag.intValue()) {
		case 1:
			return "��";
		case 2:
			return "����";
		case 3:
			return "��";
		case 4:
			return "�޹̶�����";
		default:
			return "�޹̶�����";
		}
	}

	//�����˺�����
	public static String getBankAcctType(String value) throws Exception {
		return getDictParamOptions_intrust(9920, value);
	}

	//	�ɿʽ
	public static String getJkTypeOptions(String value) throws Exception {
		return getDictParamOptions_intrust(1114, value);
	}

	//����������ʽ
	public static String getChannelCooperationOptions(String value)
			throws Exception {
		return getDictParamOptions_intrust(5502, value);
	}

	//	��Ʒ����ЧԤԼ��
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
	
//  ��Ʒ����ЧԤԼ��
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
				appendOptions(sb, rs.getString("CUST_ID")+"$1", Utility.trimNull("����")
						+ " - " + rs.getString("CUST_NAME"), Utility.trimNull(serial_no));
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//�˺�
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

	//��ͬ�ƽ��
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
				sb.append("<option value=\"\">��ѡ��</option>");
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
		appendOptions(sb, new Integer(2), "���Ϲ�", flag);
		appendOptions(sb, new Integer(1), "δ�Ϲ�", flag);
		return sb.toString();
	}

	//	�깺ʱ ѡ�����Ϲ����ĺ�ͬ
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

//  ѡ�����Ϲ����ĺ�ͬ
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
        stmt.setInt(5, 3); // 3�����Ǳ�ʾ û������
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
    
	//��	ѡ���ͬ��ź󣬸��ݲ�ƷID�š���ͬ��Ŵ���������Ϣ����(TBenifitor)��ѯ��������ID��(distinct)
	public static String getFromCustIdOptions(Integer book_code,
			Integer product_id, String contract_bh, Integer serial_no,
			Integer input_man) throws Exception {
		String listSql = "{call SP_QUERY_TBENIFITOR_ONE(?,?,?,?)}";
		//ԭ�ȵ�SP_QUERY_TBENIFITOR_ONE��Ϊ��ѯ�ѽɿ�Ĺ��� SP_QUERY_TBENIFITOR_ONE

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
						+ " -֤������: "
						+ rs.getString("CARD_ID")
						+ " -��������: "
						+ new Integer(rs.getInt("VALID_PERIOD")), serial_no);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//	ֻȡĳһ��Ʒ�µ������˺�
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

	//  �������ȼ� 1���ȣ�2һ�㣻3�Ӻ�
    public static String getProvFlagOptions(Integer selected) {
        StringBuffer sb = newStringBuffer();
        appendOptions(sb, "1", "����", String.valueOf(selected));
        appendOptions(sb, "2", "һ��", String.valueOf(selected));
        appendOptions(sb, "3", "�Ӻ�", String.valueOf(selected));
        return sb.toString();
    }
    
	//�������ȼ���
	public static String getProvlevelOptions(String value) throws Exception {
		return getDictParamOptions_intrust(1204, value);
	}

	public static String getBankName(String value) throws Exception {
		return getDictParamName_intrust(1103, value);
	}

	//���ݲ���ֵ�õ���������
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
	 * ͳ�Ʒ�ʽ ʱ��
	 */
	public static String getStatFlagOptions(int flag) {
		StringBuffer sb = new StringBuffer();

		appendOptions(sb, 0, "��ѡ��", flag);
		appendOptions(sb, 1, "��", flag);
		appendOptions(sb, 2, "��", flag);
		appendOptions(sb, 3, "��", flag);
		appendOptions(sb, 4, "��", flag);
		appendOptions(sb, 5, "��", flag);
		return sb.toString();
	}

	/////////////��ʾΪ����λ�������ʱ��Ϊ������λ����/////////////////////
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
	 * ADD BY tangshg 2009-11-29 ��ѯ�Ӳ�Ʒ�б� SP_QUERY_TSUBPRODUCT
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
	//ͨ��CRM����ȡ�Ӳ�Ʒ��Ϣ
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

	//��ѯ�Ӳ�Ʒ�б� SP_QUERY_TSUBPRODUCT,�����������Ӳ�Ʒ״̬
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
	/** ADD BY tangshg 2009-11-29 ��ѯ�ֲ�Ʒ�б� SP_QUERY_TSUBPRODUCT
	 *
	 * @IN_PRODUCT_ID INT,
	 * @IN_SUB_PRODUCT_ID INT = 0,
	 * @IN_LIST_ID INT = 0 ��ѯ�������вƲ����͵��Ӳ�Ʒ
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
	 * �����ϵ����˾ѡ��
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
	 * ��ÿͻ�Ⱥ���ѡ��
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

		sb.append("<option value=\"" + value + "\">��ѡ��</option>");
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

		sb.append("<option>��ѡ��ͻ�Ⱥ��</option>");
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

	//�ͻ��ȼ�ѡ��
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
	//callcenter���������IP
	public static String getCTIServerIP() throws Exception {
		return getValueOfTDictParam("800103");
	}
	//����������˿ڣ�21900
	public static String getCTIServerPort() throws Exception {
		return getValueOfTDictParam("800104");
	}
	//callcenter���ݿ������IP��ַ
	public static String getCallCenterDbIP() throws Exception {
		return getValueOfTDictParam("800105");
	}
	//callcenter���ݿ����������
	public static String getCallCenterDbName() throws Exception {
		return getValueOfTDictParam("800106");
	}
	//callcenter���ݿ��������¼��
	public static String getCallCenterDbUser() throws Exception {
		return getValueOfTDictParam("800107");
	}
	//callcenter���ݿ��������¼����
	public static String getCallCenterDbPassword() throws Exception {
		return getValueOfTDictParam("800108");
	}
	/**
	 * ��ý��ܵ���ϵ��ʽ
	 *
	 * @author dingyj
	 * @since 2009-12-9
	 * @param value
	 *            ѡ�����ϵ��ʽ���ܺ�
	 * @return
	 * @throws Exception
	 */
	public static String getContactCheckBox(int value) throws Exception {
		StringBuffer sb = new StringBuffer();
		DictparamLocal dict_local = EJBFactory.getDictparam();
		DictparamVO dict_vo = new DictparamVO();
		dict_vo.setType_id(new Integer(1109));
		//����ֵ�����ϵ��ʽ
		List list = dict_local.listDictparamAll(dict_vo);
		//�����ܺͻ�ý����������
		int[] items = getDictCheck(value, list, "ADDITIVE_VALUE");
		return getCheckBox(sb, items, list, "receiveContactWay",
				"ADDITIVE_VALUE", "TYPE_CONTENT");
	}

	/**
	 * ��÷�����
	 *
	 * @author dingyj
	 * @since 2009-12-9
	 * @param value
	 *            ѡ��ķ�������ܺ�
	 * @return
	 * @throws Exception
	 */
	public static String getServiceCheckBox(int value) throws Exception {
		StringBuffer sb = new StringBuffer();
		ServiceManageLocal service_local = EJBFactory.getServiceManage();
		ServiceManageVO service_vo = new ServiceManageVO();
		service_vo.setSerial_no(new Integer(0));
		service_vo.setServiceType(new Integer(0));
		service_vo.setIsValid(new Integer(1)); //���õ�
		service_vo.setInputMan(new Integer(0));
		//������õķ���
		List list = service_local.query_TServiceDefine(service_vo);
		//���ݷ����ܺͻ�ý����������
		int[] items = getDictCheck(value, list, "ServiceType");
		return getCheckBox(sb, items, list, "receiveServices", "ServiceType",
				"ServiceTypeName");
	}

	/**
	 * ���з�ʽѡ��������
	 *
	 * @param flag
	 * @return
	 */
	public static String getDirection(int flag) {
		StringBuffer sb = new StringBuffer();

		appendOptions(sb, 0, "��ѡ��", flag);
		appendOptions(sb, 1, "���н���", flag);
		appendOptions(sb, 2, "���н���", flag);
		return sb.toString();
	}

	/**
	 * ��ϸ�ѡ��
	 *
	 * @author dingyj
	 * @since 2009-12-9
	 * @param sb
	 * @param items
	 *            �����������
	 * @param list
	 *            ���е���Ϣ����
	 * @param name
	 *            ����
	 * @param value
	 *            ֵ
	 * @param text
	 *            �ı�
	 * @return sb
	 */
	public static String getCheckBox(StringBuffer sb, int[] items, List list,
			String name, String value, String text) {
		Iterator it = list.iterator();
		Map map = new HashMap();
		int j = 0;
		int newline = value == "ADDITIVE_VALUE" ? 6 : 4; //�������ϵ��ʽ��6���ٻ��У�����4���
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
		//��ֹ��ȫ��
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
	 * ����ѽɿ�ĺ�ͬ�����Ϣ
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
	 * ����ѽɿ�ĺ�ͬ�����Ϣ
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
	//����ѽɿ�ĺ�ͬ�����Ϣ,��CRM����ȡ
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
	 * ����ѽɿ�ĺ�ͬ�����Ϣ
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
	 * ����Ȩת�����
	 *
	 * @author dingyj
	 * @since 2009-12-18
	 */
	public static String getTransTypeOptions(String value) throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, "181501", "ת��", value);
		appendOptions(sb, "181502", "�̳�", value);
		appendOptions(sb, "181503", "����", value);

		return sb.toString();
	}

	/**
	 * ������渶�ʽ
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
	 * ���ݿͻ�ID��ò�Ʒ����
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
	 * ���֤�����ͣ�����+������
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
	 * ���ί���˱�־
	 *
	 * @author dingyj
	 * @since 2010-1-6
	 * @return sb
	 * @throws Exception
	 */
	public static String getClientFlagList(Integer wtr_flag) throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "ί���˺�������", wtr_flag);
		appendOptions(sb, 1, "������", wtr_flag);
		appendOptions(sb, 2, "ί����", wtr_flag);
		return sb.toString();
	}

	/**
	 * ��ÿͻ�����
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
	 * ��ÿͻ��ֻ�
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
	 * �õ��ͻ���˾����
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
		appendOptions(sb, 0, "��ѡ��", level_id);
		appendOptions(sb, 10, "�Ϲ��ݶ�", level_id);
		appendOptions(sb, 20, "����ݶ�", level_id);
		return sb.toString();
	}

	/**
	 * �õ����Ϣ�б�
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
	 * �õ����Ϣ��־����
	 *
	 * @param flag
	 * @return
	 */
	public static String getActivityFlagName(Integer flag) {
		if (flag == null)
			return null;
		switch (flag.intValue()) {
		case 1:
			return "δ����";
		case 2:
			return "������";
		case 3:
			return "�����";
		default:
			return "�½�";
		}
	}

	/**
	 * �õ�������־����
	 *
	 * @param flag
	 * @return
	 */
	public static String getActivityTaskFlagName(Integer flag) {
		if (flag == null)
			return null;
		switch (flag.intValue()) {
		case 1:
			return "δ����";
		case 2:
			return "�����";
		case 3:
			return "�����";
		default:
			return "δ����";
		}
	}

	//	�ͻ����
	public static String getWTCustOptions(Integer value) throws Exception {
		StringBuffer sb = new StringBuffer(200);
		appendOptions(sb, 0, "��ѡ��", value);
		appendOptions(sb, 1, "��ʵ�ͻ�", value);
		appendOptions(sb, 2, "Ǳ�ڿͻ� ", value);
		return sb.toString();
	}

	public static String getWTName(Integer flag) {
		if (flag == null)
			return null;
		switch (flag.intValue()) {
		case 1:
			return "��ʵ�ͻ�";
		case 2:
			return "Ǳ�ڿͻ�";
		case 3:
			return "ע���ͻ�";
		default:
			return "δ֪";
		}
	}

	/**
	 * ���Ա������
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
	 * ���Ա��������
	 *
	 * @author taochen
	 *
	 * ��������������ע�͵�ģ��Ϊ ���� > ��ѡ�� > Java > �������� > �����ע��
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

	//��ȡ�ʾ��б�
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
	 * ����CUST_ID �õ��ͻ�Email
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
	 * ����CUST_ID�� �õ��ͻ�Email��
	 *
	 * @param custIdGroup
	 * @return
	 * @throws Exception
	 */
	public static String[] getCustEmailGroup(String custIdGroup)
			throws Exception {
		String emailGroup = "";//Email ��
		String email = "";//Email ����
		String[] custIdArray = custIdGroup.split("_");
		Integer custId;
		String cust_name_alert = "";//û�� Email�� ����
		String[] ret = null;//getCustEmailName ����ֵ
		String[] ret2 = new String[2];//����������ֵ

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
	 * Ϊ�ͻ���������ѡ���ɫ
	 *
	 * @param actorId
	 * @return
	 */
	public static String getServiceActorOptions(Integer actorId) {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 1, "������", actorId);
		appendOptions(sb, 2, "ִ����", actorId);

		return sb.toString();
	}

	/**
	 * ��ȡ���������б�
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

		appendOptions(sb, new Integer(0), "��ѡ��", quesTask_id);

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
	 * ��ȡ�����б�
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

		appendOptions(sb, new Integer(0), "��ѡ��", task_id);

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
			throw new BusiException("��ȡ�ͻ����֤ͼƬʧ��:" + e.getMessage());
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

	//δ�ɿ�ĺ�ͬ
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
	//��ȡ�ͻ������������˺Ż���
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
		if (value!=null && value.length()>0)//ȥ�����ķֺ�
			value=value.substring(0,value.length()-1);
		return value;
	}

	/**
	 * ��ȡCUST_NAME�� CUST_TYPE
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
	 * ��ȡPARTN_NAME�� PARTN_TYPE2_FLAG(����/ý��)
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
	 * ���ʡ����������
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

	// ��Ʒ���޵�λ���
	public static String getPeriodValidUnitOptions(Integer flag)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		//appendOptions(sb, -1, "��ѡ��", flag);
		appendOptions(sb, 0, "�޹̶�����", flag);
		appendOptions(sb, 1, "��", flag);
		appendOptions(sb, 2, "��", flag);
		appendOptions(sb, 3, "��", flag);

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
			throw new Exception("������ѯʧ��");
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
	 * ����CUST_ID ȡ�ÿͻ�Ⱥ������
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

	// �ƽ��ڼ������ڲ�Ʒ
	public static String getProductOptions3(Integer book_code,
			Integer product_id, Integer op_code) throws Exception {
		return getProductListOptions(book_code, product_id, "", op_code, 11);
	}

	// ��Ʒ��� �б�
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
	 * ��ÿͻ���Ϣ������
	 *
	 * @param menu_id
	 * @return
	 * @throws Exception
	 */
	public static List getRequiredItem(String menu_id) throws Exception {
		List list = new ArrayList();
		// �Ȼ�ø�Ա��������������
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

	// ֤������
	public static String getCardTypeName(String value) throws Exception {
		return getDictParamName(1108, value);
	}

	public static String getCardTypeJgName(String value) throws Exception {
		return getDictParamName(2108, value);
	}

	//�ͻ���Ϣ--���յȼ�
	public static String getRiskGrade(String value) throws Exception {
		return getDictParamOptions2(3203, value);
	}

	/**
	 *
	 * add by tsg 2007-09-30
	 *
	 * ��ȡ��������
	 *
	 */
	public static String getLinkTypeOptions(Integer flag) throws Exception {
		return getIntegerParamOptions(1302, flag);
	}

	/**
	 * ��ȡIntegerParam���еĲ��� û����ѡ�� add by guifeng 2009.04 modi by nizh 20090629
	 * ��Ϊ����ѡ����Ŀ���� ��һ������ѡ����20��30 all catalog_flag��Ĭ�ϴ�1 �͵�һ�ν�ȥ��0 ��һ����
	 */
	public static String getIntegerParamOptions(int type_id,
			Integer defaultValue) throws Exception {
		StringBuffer sb = newStringBuffer();
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TINTEGERPARAM (?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, type_id);
		// ��Ӧ��������Ӧ�ô�TINTEGERPARAM������ͳһ��ȡ, TYPE_ID
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

	//	�ʲ������Ϣ�б�
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
	 * ��Ȩ�����������
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

		sb.append("<option value=\"\">��ѡ��</option>");
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			appendOptions(sb, Utility.parseInt(Utility.trimNull(map
					.get("CA_ID")), new Integer(0)), Utility.trimNull(map
					.get("CA_NAME")), bottom_flag);
		}
		return sb.toString();
	}

	/**
	 * ������Ȩ�����ȡ����Ȩ������
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
	 * ������Ȩ�����ȡ����Ȩ�������ͻ�����ID
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
	 * ���ݿͻ�������ȡ�ÿͻ���������
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
	 * ȡ������״̬����
	 *
	 * @param cust_id
	 * @return
	 * @throws BusiException
	 */
	public static String getStatusName(Integer status) throws BusiException {
		String statusName = "";
		if (status.intValue() == 1) {
			statusName = "������";
		} else {
			statusName = "�ѽ���";
		}
		return statusName;

	}

	/**
	 * ��ѯ���ÿͻ�����(��ǰ����Ա)������ͬ�������¼�������������ͬһ���������һ���������������������
	 *
	 * @param bottom_flag
	 * @return
	 * @throws Exception
	 */
	public static String getManagerListFromTCustManagerTree(Integer input_man,
			List list) throws Exception {
		StringBuffer sb = new StringBuffer();
		Map map = null;

		sb.append("<option value=\"\">��ѡ��</option>");
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			appendOptions(sb, Utility.parseInt(Utility.trimNull(map
					.get("MANAGERID")), new Integer(0)), Utility.trimNull(map
					.get("MANAGERNAME")), input_man);
		}
		return sb.toString();
	}

	/**
	 * ��TCustManagers���л�ÿͻ������ֵ��������������Ϊ��TCustManagerTree������ֶ�����һ�������Բ�����������һ������
	 *
	 * @param bottom_flag
	 * @return
	 * @throws Exception
	 */
	public static String getManagerListFromTCustManagers(Integer input_man,
			List list) throws Exception {
		StringBuffer sb = new StringBuffer();
		Map map = null;

		sb.append("<option value=\"\">��ѡ��</option>");
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			appendOptions(sb, Utility.parseInt(Utility.trimNull(map
					.get("ManagerID")), new Integer(0)), Utility.trimNull(map
					.get("ManagerName")), input_man);
		}
		return sb.toString();
	}

	/**
	 * ��TCustManagers���л�ÿͻ������ֵ��������������Ϊ��TCustManagerTree������ֶ�����һ�������Բ�����������һ������
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
	 * ��÷����в�Ʒ����
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
			vaidPriodUnit = "��";
			break;
		case 2:
			vaidPriodUnit = "��";
			break;
		case 3:
			vaidPriodUnit = "��";
			break;
		case 4:
			vaidPriodUnit = "��";
			break;
		default:
			vaidPriodUnit = "��";
			break;
		}
		return vaidPriodUnit;
	}

	public static String getFormatDictParamOptions(BigDecimal date)
			throws Exception {
		BigDecimal bigLoanAmount = new BigDecimal("");
		BigDecimal q_expect_rate = bigLoanAmount.multiply(date);
		NumberFormat percent = NumberFormat.getPercentInstance();
		//�����ٷֱȸ�ʽ������
		return percent.format(q_expect_rate);
	}

	//�����в�Ʒ����
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

	//	�����к�ͬ״̬
	public static String getNonproductStatusOptions(String value)
			throws Exception {
		return getDictParamOptions(1201, value);
	}

	//	��������������״̬
	public static String getQuotientStatusOptions(String value)
			throws Exception {
		return getDictParamOptions(1211, value);
	}

	//	��������������״̬
	public static String getProfitStatusOptions(String value) throws Exception {
		return getDictParamOptions(1116, value);
	}

	//�����к�ͬ���
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

	// �����в�Ʒ���޵�λ���
	public static String getPeriodValidUnitOptions1(Integer flag)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "�޹̶�����", flag);
		appendOptions(sb, 1, "��", flag);
		appendOptions(sb, 2, "��", flag);
		appendOptions(sb, 3, "��", flag);
		appendOptions(sb, 4, "��", flag);

		return sb.toString();
	}

	// �䶯ҵ�����
	public static String getChgType(Integer flag) {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, new Integer(1), "�Ϲ�", flag);
		appendOptions(sb, new Integer(2), "�깺", flag);
		appendOptions(sb, new Integer(3), "���", flag);
		appendOptions(sb, new Integer(4), "�ݶ�ת��", flag);
		return sb.toString();
	}
	
	// �䶯ҵ�����
	public static String getChgType1(Integer flag) {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, new Integer(1), "�Ϲ�", flag);
		appendOptions(sb, new Integer(2), "�깺", flag);
		return sb.toString();
	}

	//	��������������״̬
	public static String getChannelname(String value) throws Exception {
		return getDictContent(value);
	}

	//����������ʽ
	public static String getChanneCooperationName(String value)
			throws Exception {
		return getDictContentIntrust(value);
	}

	// ���ܷ�ʽ
	public static String getGroup_type(Integer flag) {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, new Integer(1), "����", flag);
		appendOptions(sb, new Integer(2), "����", flag);
		appendOptions(sb, new Integer(3), "����", flag);
		appendOptions(sb, new Integer(4), "����", flag);
		return sb.toString();
	}

	// �Ƿ�Ϊ���ܲ�Ʒ
	public static String getGroupProduct(Integer flag) {
		StringBuffer sb = newStringBuffer();
		appendOptions(sb, new Integer(1), "��", flag);
		appendOptions(sb, new Integer(2), "��", flag);
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
	
	//�����б�
	public static String getChannelName(Integer value) throws Exception {
		String strSql = "SELECT * FROM TCHANNEL";
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=\"0\">��ѡ��</option>");
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

	//	 �ʲ���Դ
	public static String getZclbFlagName(Integer flag) {
		String retValue = "�������ʲ�";
		if (flag == null)
			return retValue;
		if (flag.intValue() == 1)
			retValue = "�����ʲ�";
		if (flag.intValue() == 3)
			retValue = "�������ʲ�";
		return retValue;
	}

	//�Ŷ��б�
	public static String getTeamName(Integer value) throws Exception {
		String strSql = "{call SP_QUERY_TMANAGERTEAMS(?,?,?,?,?,?)}";
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=\"0\">��ѡ��</option>");
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
		sb.append("<option value=\"0\">��ѡ��</option>");
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
			throw new BusiException("��ѯ��Ʒ�������������ȼ���ʧ��" + e.getMessage());
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
			throw new BusiException("��ѯ��Ʒ���������漶��ʧ��" + e.getMessage());
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
			throw new BusiException("��ѯԤ���в�Ʒ�������������ȼ�ʧ��" + e.getMessage());
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
			throw new BusiException("��ѯ��Ʒ�������������ȼ�ʧ��" + e.getMessage());
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
			throw new BusiException("��ѯԤ���в�Ʒ���������漶��ʧ��" + e.getMessage());
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
			throw new BusiException("��ѯ��Ʒ���������漶��ʧ��" + e.getMessage());
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
			throw new BusiException("��ѯ��Ʒ�������������ȼ���ʧ��" + e.getMessage());
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return ret;

	}

	//	����
	public static String getNationOptions(String value) throws Exception {
		return getDictParamOptions(1161, value);
	}

	//����״��
	public static String getHealthOptions(String value) throws Exception {
		return getDictParamOptions(1162, value);
	}

	//�����̶�
	public static String getEducationOptions(String value) throws Exception {
		return getDictParamOptions(1163, value);
	}

	//��λ����
	public static String getCompanyCharacterOptions(String value)
			throws Exception {
		return getDictParamOptions(1164, value);
	}

	//����Ͷ��״��(1165)
	public static String getOtherInvestmentOptions(String value)
			throws Exception {
		return getDictParamOptions(1165, value);
	}

	//����ƫ��(1166)
	public static String getTypePrefOptions(String value) throws Exception {
		return getDictParamOptions(1166, value);
	}

	//Ͷ��ƫ��
	public static String getInvestPrefOptions(String value) throws Exception {
		return getDictParamOptions(1167, value);
	}

	//����ƫ��(1169)
	public static String getServicePrefOptions(String value) throws Exception {
		return getDictParamOptions(1169, value);
	}

	//��Ȥƫ��
	public static String getHobbyPrefOptions(String value) throws Exception {
		return getDictParamOptions(1168, value);
	}

	//����ƫ��(1170)
	public static String getTimeLimitPrefOptions(String value) throws Exception {
		return getDictParamOptions(1170, value);
	}

	//����ƫ��(1172)
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
			throw new BusiException("��ѯ��ͬ������Աʧ��" + e.getMessage());
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return ret;

	}

	/**
	 * ���������Ϣ-��ѡ����
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
		// ��Ӧ��������Ӧ�ô�TINTEGERPARAM������ͳһ��ȡ, TYPE_ID = 1101
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
			appendOptions(sb, 0, "ȫ����Ʒ", product_id);
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

	//��Ʒ--���յȼ�
	public static String getProductRiskGrade(String value) throws Exception {
		return getDictParamOptions2(1172, value);
	}

	/**
	 * ��Ŀ����������
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

		sb.append("<option value=\"\">��ѡ��</option>");
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
	 * add by jiangyp 2011-05-24��ѯ�ֲ�Ʒ�б� SP_QUERY_TSUBPRODUCT
	 *
	 * @IN_PRODUCT_ID INT,
	 * @IN_SUB_PRODUCT_ID INT = 0 ,
	 * @IN_LIST_ID INT = 0 ,
	 * @IN_LIST_NAME NVARCHAR(60) = '', --�Ӳ�Ʒ����
	 * @IN_PRODUCT_CODE VARCHAR(6) = '', --��Ʒ���
	 * @IN_PRODUCT_NAME NVARCHAR(60) = '', --��Ʒ����
	 * @IN_CHECK_FLAG INT = 0, --
	 * @IN_INTRUST_TYPE1 NVARCHAR(20) = '' --���вƲ�����(1138),��ȫ���Ʋ�Ȩ�Ӳ�Ʒʱ��(113899)
	 *                   �������вƲ����Ͳ�ѯ�Ӳ�Ʒ
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
	 * ��ȡCHANNEL_NAME(���д���)
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

	//�������
	public static String getChannelTypeName(String value) throws Exception {
		return getDictContent(value);
	}

	// ��������
	public static String getIntrustTypeOptions(String value) throws Exception {
		return getDictParamOptions(1104, value);
	}

	//	 ����״̬��־��Ŀǰֻ�������˲�ѯ���õ���ȥ�������ᡢ��Ѻ��
	public static String getBenStatusOptions(String value) throws Exception {
		StringBuffer sb = new StringBuffer(200);
		appendOptions(sb, "121101", "����", value);
		appendOptions(sb, "121102", "��ʧ", value);
		appendOptions(sb, "121104", "��ֹ", value);
		appendOptions(sb, "121105", "���ڽ���", value);
		return sb.toString();
	}

	/**
	 * ADD BY TSG 2011-09-21 ���ֿ�Ŀ�б�
	 *
	 */
	public static String getScoreSubjectOptions(Integer subject_id)
			throws Exception {

		String listSql = "SELECT * FROM TSCORESUBJECT";
		StringBuffer sb = new StringBuffer();
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(listSql);
		sb.append("<option value=\"0\">��ѡ��</option>");

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
	 * ADD BY TSG 2011-09-21 ������б�
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
		sb.append("<option value=\"0\">��ѡ��</option>");

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
	 * ADD BY TSG 2011-09-21 ������Դ��
	 *
	 */
	public static String getSourceTable(String table_name) throws Exception {
		String listSql = "SELECT TABLE_NAME,TABLE_NAME_CN FROM TDataTABLE GROUP BY TABLE_NAME,TABLE_NAME_CN";

		StringBuffer sb = new StringBuffer();
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(listSql);
		sb.append("<option value=\"0\">��ѡ��</option>");

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
	 * ADD BY TSG 2011-09-21 ������Դ�ֶ�
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
		sb.append("<option value=\"0\">��ѡ��</option>");

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
	 * ADD BY TSG 2011-09-21 �˹����ѡ��
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
		sb.append("<option value=\"0\">��ѡ��</option>");

		try {
			while (rs.next())
				appendOptions(sb, rs.getString("SCORE"), rs
						.getString("OPERATION_VALUE")
						+ "--" + rs.getString("SCORE") + "��", value);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	/**
	 * ��Ʒ����
	 *
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String getClassType1Options(String value) throws Exception {
		return getDictParamOptions(1213, value);
	}

	/**
	 * ����״̬
	 *
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String getPre_status(String value) throws Exception {
		return getDictParamOptions(1102, value);
	}

	/**
	 * ����֪ʶ���Ż��֪ʶ���������
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
			appendOptions(sb, 0, "��ѡ��", pre_product_id);
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
	 * �ɲ�Ʒid��ȡԤ���в�Ʒid
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
			throw new BusiException("��ȡԤ������Ϣʧ��:" + e.getMessage());
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
	 * ��Ԥ���в�Ʒid��ȡԤ���в�Ʒ����
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
			throw new BusiException("��ȡԤ������Ϣʧ��:" + e.getMessage());
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
	 * ��Ԥ���в�Ʒid��ȡԤ���а��Ӳ�Ʒid
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
			throw new BusiException("��ȡԤ������Ϣʧ��:" + e.getMessage());
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

		appendOptions(sb, "new", "�½���ͬ", value);

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
						"׷�ӵ���ͬ���"
								+ Utility.trimNull(rslist
										.getString("CONTRACT_SUB_BH"))
								+ "����ݶ�Ϊ"
								+ Utility.trimNull(rslist
										.getString("BEN_AMOUNT")) + "�ĺ�ͬ",
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
	 * �����ķ��Ͷ���
	 *
	 * @param smsUser  �����û�
	 * @param phoneNumber ���ն����ֻ�����
	 * @param smsContent ���Ͷ�������
	 * @param sendLevel ���ͼ���
	 * @param putType �ύ��ʽ
	 * @param serialNo_details ��ϸ����ID
	 * @param input_man ����Ա
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
			//1.������ż�¼
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

			//2.���Ͷ���
			try {
				vo_sms.setBat_serial_no(new Integer(1));
				vo_sms.setSmsIndex(smsIndex);
				vo_sms.setSmstotal(new Integer(1));
				sendBackInfo = SmsClient.sendMessage(vo_sms);
			} catch (Exception e) {
				vo_sms.setSmsIndex(smsIndex);
				vo_sms.setStatus(new Integer(2));
				vo_sms.setStatusName("�ύʧ��");
				vo_sms.setInputOperator(input_man);
				smsRecordLocal.modi(vo_sms);
				smsRecordLocal.remove();
				return "2|������ŷ���ƽ̨��";
			}
			vo_sms.setSmsIndex(smsIndex);
			vo_sms.setStatus(Utility.parseInt(sendBackInfo[0], new Integer(0)));
			vo_sms.setStatusName(sendBackInfo[1]);
			vo_sms.setInputOperator(input_man);
			smsRecordLocal.modi(vo_sms);
			int ret = Utility.parseInt(sendBackInfo[0], new Integer(0))
					.intValue();
			if (ret == 3 || ret == 1) {
				s_result = "1|�ύ���ųɹ���";
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
			throw new BusiException("������ϵͳ��Ϣʧ��:" + e.getMessage());
		}
	}
	
	// ��ȡԱ���б�
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

	//��ѯ���б� �е� Ա����Ϣ
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

	//��ѯ���б� �е� ������Ϣ
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

//	��ѯ�ͻ�����
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
				val = Utility.trimNull(mapV.get("CHANNEL_TYPE_NAME"))+"-"+Utility.trimNull(mapV.get("CHANNEL_NAME")) +"[���ʣ�"+Utility.trimNull(Utility.parseDecimal(Utility.trimNull(mapV.get("CHANNEL_FARE_RATE")), new BigDecimal(0),4,"100"))+"]";
				appendOptions(sb,key,val,value);
			}
		} catch (Exception e) {
			throw new BusiException("��ȡ�û���Ϣʧ��:" + e.getMessage());
		}
		return sb.toString();
	}
	/**
	 * ����TPREMONEYDETAIL��pre_serial_no��õ��˼�¼
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
	 * ����CUST_ID���TCUSTBANKACCT�ʺ���Ϣ
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
	
	//��ȡ������������˺��Լ�����
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
	 * TCUSTOMERINFO��ί��������ѡ��
	 *
	 * @param jg_wtrlx2
	 * @return
	 */
	public static String getInstitutionsOptions(Integer jg_wtrlx2) {
		StringBuffer sb = new StringBuffer();		
		appendOptions(sb, 1, "�����Թ�˾", jg_wtrlx2);
		appendOptions(sb, 2, "����", jg_wtrlx2);
		appendOptions(sb, 3, "�ǽ����Թ�˾", jg_wtrlx2);
		appendOptions(sb, 4, "��������Թ�˾", jg_wtrlx2);
		return sb.toString();
	}
	
	//�ͻ�Ͷ�߷�ʽ
	public static String getComplaintOptions(Integer comp_type){
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "��ѡ��", comp_type);
		appendOptions(sb, 1, "�绰", comp_type);
		appendOptions(sb, 2, "����", comp_type);
		appendOptions(sb, 3, "Email", comp_type);
		appendOptions(sb, 4, "����", comp_type);
		return sb.toString();
	}
	
	//Ͷ�߻ظ���ʽ
	public static String getReplyOptions(Integer reply_type){
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "��ѡ��", reply_type);
		appendOptions(sb, 1, "�绰", reply_type);
		appendOptions(sb, 2, "����", reply_type);
		appendOptions(sb, 3, "Email", reply_type);
		appendOptions(sb, 4, "�ż�", reply_type);
		return sb.toString();
	}
	
	//Ͷ�ߴ���״̬
	public static String getDisposeOptions(Integer do_status){
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "��ѡ��", do_status);
		appendOptions(sb, 1, "δ���", do_status);
		appendOptions(sb, 2, "���", do_status);
		appendOptions(sb, 3, "ת����ز���", do_status);
		return sb.toString();
	}
	
	//Ͷ�ߴ���ת����ʽ
	public static String getForwardOptions(Integer forward_type){
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "��ѡ��", forward_type);
		appendOptions(sb, 1, "�绰", forward_type);
		appendOptions(sb, 2, "Email", forward_type);
		appendOptions(sb, 3, "����", forward_type);
		return sb.toString();
	}
	/**
	 * ���ݲ�ѯ��� ���� ������where�����ж��Ƿ����ָ��������
	 * @param querysSql
	 * @param queryTableName
	 * @param queryCondition
	 * @param sqlFlag trueʱ���Ǹ���querySql���в�ѯ���������queryTableName��queryCondition����ƴ�ӡ�
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
			throw new BusiException("��ȡ���ݿ���Ϣ����:" + e.getMessage());
		}
	}
	
	public static String getContractUnrealStatusOptions(Integer selected) {
        StringBuffer sb = new StringBuffer();
        appendOptions(sb, 0, "��ѡ��", selected);
        appendOptions(sb, 1, "δת��ʽ�Ϲ�", selected);
        appendOptions(sb, 2, "��ת�Ϲ�", selected);
        return sb.toString();
    }
    
	//   Ԥ���в�Ʒ�б�
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
    
    //��ѯ��Ŀ�����쵼�Ŷ��в�Ʒ���Ĳ�Ʒ��Ϣ
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
        appendOptions(sb, 0, "��ѡ��ԤԼ���", selected);
        appendOptions(sb, 1, "���&ge;300��", selected);
        appendOptions(sb, 2, "���&lt;300��", selected);
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
    
//  ���ݲ���Աid��ѯ����Ա����
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
    
//  ѡ�����渶�ʽ��ʾ����ݶ�
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
    
//  ��Ʒ���
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
    
//  �õ���ҵ�ͻ�����
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
    
//  �Ҹ����
    public static String getFpFlagOptions(int value) throws Exception {
        StringBuffer sb = newStringBuffer();
        appendOptions(sb, 4, "�ѶҸ�", value);
        appendOptions(sb, 1, "δ�Ҹ�", value);
        return sb.toString();
    }
    
//  ���ݽ�ɫ�����ײ�ѯ����Ա
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
    
//  ѡ��������ID�ź󣬸��ݲ�ƷID�š���ͬ��š�������ID�Ŵ���������Ϣ����(TBenifitor)��ѯ�����渶�ʽ
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
    
//   ��Ʒ״̬
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
            throw new Exception("���ݿ��ѯ����" + e.getMessage());
        } finally {
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        }

        return typevalue;

    }
    
    // ��ȡ����ί���˵����ͣ�EAST���б�
    public static String getJgWtrlx2List(String selected) {
        StringBuffer sb = newStringBuffer(); 
        appendOptions(sb, "1", "�����Թ�˾", selected);
        appendOptions(sb, "2", "����", selected);
        appendOptions(sb, "3", "�ǽ����Թ�˾", selected);
        appendOptions(sb, "4", "��������Թ�˾", selected);
        return sb.toString();
    }
    
    // ��ȡ��Ʒ������
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
    
    
    // ��ȡ��Ʒ�Ŀ�ʼ|��ֹ����
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
    
    //  ��ȡ��Ʒ��Ӧ�ĺ�ͬǰ׺
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
    
//  ��ȡ��Ʒ��Ӧ�ĺ�ͬǰ׺
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

    //  ��ȡ��Ʒ��Ӧ�ĺ�ͬǰ׺
    public static String getMergeCheckStatusList(Integer selected) {
    	StringBuffer sb = new StringBuffer(); 
    	appendOptions(sb, 0, "ȫ��", selected);
        appendOptions(sb, 1, "δ���", selected);
        appendOptions(sb, 2, "�����", selected);
        return sb.toString();
    }
    
    //cell������ѡ����---�����˻������༭
    public static String getDictParamSelect(int dict,String sql)throws Exception{
    	String selOptions = "��ѡ��\n";
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
    
    // ��ȡ����ί���˵�����
    public static String getJgWtrlx2(String lx) {
        if ("1".equals(lx)) 
        	return "�����Թ�˾";
        else if ("2".equals(lx)) 
        	return "����";
        else if ("3".equals(lx)) 
        	return "�ǽ����Թ�˾";
        else if ("4".equals(lx)) 
        	return "��������Թ�˾";
        else
        	return "";
 
    }
	/**
	 * ��ȡ�Ŷӳ�Ա�б�
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
	
	 //  ��ȡ�ͻ���Ϣ��ʵ�Ա���б�
    public static String getCustInfoTrueFlagList(Integer selected) {
    	StringBuffer sb = new StringBuffer(); 
    	appendOptions(sb, 0, "��ѡ��", selected);
        appendOptions(sb, 1, "δ�˲�", selected);
        appendOptions(sb, 2, "��ʵ", selected);
        appendOptions(sb, 3, "���˲�", selected);
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
    
    //  ��ȡԱ���ճ������б�
    public static String getScheduleTypeList(String selected) throws Exception {
    	 StringBuffer sb = new StringBuffer(); 
    	 appendOptions(sb, "0", "ȫ��", selected);
    	 
         Connection conn = null;
         CallableStatement stmt = null;
         ResultSet rs = null;

         try {
         	conn = CrmDBManager.getConnection();
            stmt = conn.prepareCall("{call SP_QUERY_TDICTPARAM(?)}", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setInt(1, 3042); // 3042 -- �ճ����
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
    
	// ��Ʒ���޵�λ���
	public static String getTinipapamOptions_intrust(int type_id, String type_value)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, "0", "��ѡ��", type_value);
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
	
	// ȡinput_man��������Ա	��ԱĿǰ�������Ŷ��еĳ�Ա���ͻ��������еĳ�Ա
	public static String getOperatorMembers(Integer input_man, Integer selected)
		throws Exception {
		StringBuffer sb = new StringBuffer();
		appendOptions(sb, 0, "ȫ��", selected);
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
	
	//��ѯ�����Ŷӻ��Ŷ��е����г�Ա
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
	 * �����Ϣ������
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
	 * �õ���ӡģ�������б� TPRINTTEMPLATE
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
	 * ͨ��ģ�����ȡ�ô�ӡģ��
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
            throw new BusiException("��ӡģ���ѯʧ��"+e.getMessage());
        }finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (conn!=null) conn.close();
		}
        return Template_content;
	}
	/**
	 * ͨ��ģ��IDȡ�ô�ӡģ��
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
            throw new BusiException("��ӡģ���ѯʧ��"+e.getMessage());
        }finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (conn!=null) conn.close();
		}
        return Template_content;
	}
	/**
	 * ͨ��ģ�����ȡ�ô�ӡģ�������б�
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
            throw new BusiException("��ӡģ���ѯʧ��:"+e.getMessage());
        }finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (conn!=null) conn.close();
		}
        return sb.toString();
	}
	/**
	 * ȡ�ò�Ʒ��ԤԼĬ����Ч����
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

	//����CRM�в�Ʒ����
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
	//��lisi���в㼶����
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
	//����team_id���㼶�������е������Ŷ�
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
	//���ϼ�teamID 
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
	/**ͨ������Ա��teamId
	 * 
	 * @param input_opcode
	 * @return ret[0]���TeamID��ret[1]����Ƿ�ĩ���Ŷӣ�0��ĩ����1ĩ��
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
	 * ��ѯָ������ģ�����õ��ĺ�
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
	
	/* * ��ѯ�Ϲ����
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
	//�����˺� flag   1- �������˻� 2-���ͬ���
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
	 * �����ʵ�������ȡ������ֵ
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
		typeMap.put("91001", "����ר��˫¼");
		typeMap.put("91002", "�ƶ�˫¼");
		typeMap.put("91003", "Զ��˫¼");
		
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
		typeMap.put("91001", "����ר��˫¼");
		typeMap.put("91002", "�ƶ�˫¼");
		typeMap.put("91003", "Զ��˫¼");
		
		return typeMap.get(defvalue);
	}
	
	/**
	 * ��ѯ�״��Ϲ�ʱ��
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
	 * ��ѯ���һ���Ϲ���Ϣ
	 * ��Ʒ����+ ��ͬ���
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
	 * ˫¼��Ϣ״̬
	 * @param defvalue
	 * @return
	 * @throws Exception
	 */
	public static String getDoubleRecordStatusOptions(String defvalue)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		Map<String, String> typeMap = new HashMap<String, String>();
		typeMap.put("-2", "δ¼��");
		typeMap.put("-1", "�����");
		typeMap.put("0", "δͨ��");
		typeMap.put("1", "��ͨ��");
		
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
	 * �ͻ���ϵ��ʽ�޸�����״̬
	 * @param defvalue
	 * @return
	 * @throws Exception
	 */
	public static String getCustomersInfoRecordStatusOptions(String defvalue)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		Map<String, String> typeMap = new HashMap<String, String>();
		typeMap.put("1", "��ȷ��");
		typeMap.put("2", "ȷ��δͨ��");
		typeMap.put("3", "�����");
		typeMap.put("4", "���δͨ��");
		typeMap.put("5", "��ͨ��ͨ��");
		
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
	 * �жϲ���Ա�Ƿ��Ƿ��������Ľ�ɫ
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
	 * ˫¼��Ϣ״̬
	 * @param defvalue
	 * @return
	 * @throws Exception
	 */
	public static String getSalecChangesStatusOptions(String defvalue)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		Map<String, String> typeMap = new HashMap<String, String>();
		typeMap.put("1", "��ȷ��");
		typeMap.put("2", "ȷ��δͨ��");
		typeMap.put("3", "�����");
		typeMap.put("4", "���δͨ��");
		typeMap.put("5", "��ͨ��");
		typeMap.put("6", "�ѳ���");
		
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
	 * ���ʲ�Ʒ����
	 * @param defvalue
	 * @return
	 * @throws Exception
	 */
	public static String getSalecChangesResultProductTypeOptions(String defvalue)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		Map<String, String> typeMap = new HashMap<String, String>();
//		typeMap.put("1", "ֱ��(����̨��");
		typeMap.put("2", "С�˻�");
		typeMap.put("3", "��������");
		typeMap.put("4", "��������");
		typeMap.put("5", "����");
		
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
	 * ���ʲ�Ʒ����-More
	 * @param defvalue
	 * @return
	 * @throws Exception
	 */
	public static String getSalecChangesResultProductTypeOptionsMore(String defvalue)
			throws Exception {
		StringBuffer sb = newStringBuffer();
		Map<String, String> typeMap = new HashMap<String, String>();
		typeMap.put("1", "ֱ��(����̨��");
		typeMap.put("2", "С�˻�");
		typeMap.put("3", "��������");
		typeMap.put("4", "��������");
		typeMap.put("5", "����");
		
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
	 * ���ʲ�Ʒ����
	 * @param defvalue
	 * @return
	 * @throws Exception
	 */
	public static String getSalecChangesResultProductTypeName(String defvalue)
			throws Exception {
		Map<String, String> typeMap = new HashMap<String, String>();
//		typeMap.put("1", "ֱ��(����̨��");
		typeMap.put("2", "С�˻�");
		typeMap.put("3", "��������");
		typeMap.put("4", "��������");
		typeMap.put("5", "����");
		
		if(defvalue != null) {
			return typeMap.get(defvalue);
		}
		return "";
	}
}