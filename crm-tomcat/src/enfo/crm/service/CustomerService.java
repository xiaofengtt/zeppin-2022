/*
 * �������� 2009-11-21
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import enfo.crm.util.DwrDecoder;

import javax.servlet.http.HttpServletRequest;

import com.enfo.webservice.client.SAPClient;

import enfo.crm.customer.CustClassDefineLocal;
import enfo.crm.customer.CustClassDetailLocal;
import enfo.crm.customer.CustomerClassLocal;
import enfo.crm.customer.CustomerLocal;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.tools.Argument;
import enfo.crm.tools.EJBFactory;
import enfo.crm.tools.Utility;
import enfo.crm.vo.CustClassDefineVO;
import enfo.crm.vo.CustClassDetailVO;
import enfo.crm.vo.CustomerClassVO;
import enfo.crm.vo.CustomerVO;

public class CustomerService {

	/**
	 * ����ͻ����ơ����������Ƿ��ظ�
	 * @author dingyj
	 * @param cust_info
	 * @param flag 1,�����˼�ί���˿ͻ�������,2��ҵ�ͻ����ƣ�3�����˼�ί���˿ͻ���֤����,4��ҵ�ͻ�֤����
	 * @param cust_id
	 * @return
	 */
	public String findSameCustInfo1(String cust_info,Integer flag,Integer cust_id,String card_id) {
		String cust_name = DwrDecoder.unescape(cust_info); //����
		String findName = "";
		String findCardId = "";
		String findService_name = "";
		String temp = "";
		int num = 0;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = CrmDBManager.getConnection();
			stmt = con.createStatement();
			String sql =
				"{call SP_QUERY_TCustomers_NAME("
					+ "'" + cust_name + "'," + cust_id + "," + flag + ",'" + card_id + "')}";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				 if(flag.intValue()==1){
					num = 1;
					findName = rs.getString("CUST_NAME").trim();
					
					String findCardIdTemp = Utility.trimNull(rs.getString("CARD_ID"),"").trim();
					if(findCardIdTemp.length()<=11){
						int pos = findCardIdTemp.length()-4;
						if (pos<0) pos=0; //֤�����벻��4λʱ��֤������ȫ��*����
						findCardId = findCardIdTemp.substring(0,pos)+"****";
					}else{
						findCardId = findCardIdTemp.substring(0,11)+"******";
					}
					findService_name = Utility.trimNull(rs.getString("SERVICE_MAN_NAME"),"").trim();
					temp = "����Ϊ'"+findName+"',֤����Ϊ' " + findCardId  + "'�ͻ�����Ϊ'" + findService_name + "'�Ŀͻ��Ѿ����ڣ����ƽ�����Ա����";
				}else
					findName = rs.getString("CUST_ID");
			}
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if(num!=0)return temp;
			return findName;
		}
	}
	/**
	 * ����ͻ����ơ����������Ƿ��ظ�
	 * @author dingyj
	 * @param cust_info
	 * @param flag 1,�����˼�ί���˿ͻ�������,2��ҵ�ͻ����ƣ�3�����˼�ί���˿ͻ���֤����,4��ҵ�ͻ�֤����
	 * @param cust_id
	 * @return
	 */
	public String findSameCustInfo(String cust_info,Integer flag,Integer cust_id,String card_id) {
		String cust_name = DwrDecoder.unescape(cust_info); //����
		String findName = "";
		String findCardId = "";
		String temp = "";
		int num = 0;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = CrmDBManager.getConnection();
			stmt = con.createStatement();
			String sql =
				"{call SP_QUERY_TCustomers_NAME("
					+ "'" + cust_name + "'," + cust_id + "," + flag + ",'" + card_id + "')}";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				if(flag.intValue() == 1 || flag.intValue() == 2){
					findName = rs.getString("CUST_NAME").trim();
				}else if("3".equalsIgnoreCase(rs.getString("SAME_TYPE").trim())){
					num = 3;
					findName = rs.getString("CUST_NAME").trim();
					findCardId = rs.getString("CARD_ID").trim();
					temp = "����Ϊ'"+findName+"',֤����Ϊ' " + findCardId + "'�Ŀͻ��Ѿ����ڣ��޷����棡";
				}else if("2".equalsIgnoreCase(rs.getString("SAME_TYPE").trim())){
					num = 2;
					findCardId = rs.getString("CARD_ID").trim();
					temp = "֤����Ϊ'" + findCardId + "'�Ŀͻ��Ѿ����ڣ����ȷ�ϱ��ͻ����Ѵ��ڿͻ�����ͬһ�ͻ����������";
				}else if("1".equalsIgnoreCase(rs.getString("SAME_TYPE").trim())){
					num = 1;
					findName = rs.getString("CUST_NAME").trim();
					temp = "����Ϊ'" + findName + "'�Ŀͻ��Ѿ����ڣ����ȷ�ϱ��ͻ����Ѵ��ڿͻ�����ͬһ�ͻ����������";
				}else
					findName = rs.getString("CUST_ID");
			}
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if(num!=0)return temp;
			return findName;
		}
	}

	/**
	 * ��ÿͻ�����
	 * @author dingyj
	 * @since 2009-12-31
	 * @param classId ��level_idΪ����ʱid=define_id,Ϊż��ʱid=detail_id
	 * @param level_id �ڵ�ȼ�
	 * @return items
	 */
	public String[] getCustClassDefine(Integer classId,Integer parentId, int level_id,Integer cn_no)
		throws Exception {
		List list = new ArrayList();
		Map map = new HashMap();
		String item = "";
		CustClassDefineLocal define_local = EJBFactory.getCustClassDefine();
		CustClassDetailLocal detail_local = EJBFactory.getCustClassDetail();
		CustClassDefineVO define_vo = new CustClassDefineVO();
		CustClassDetailVO detail_vo = new CustClassDetailVO();

		if (level_id % 2 != 0) {
			if (level_id == 1) {
				define_vo.setLevel_id(new Integer(1));
			} else {
				define_vo.setLevel_id(new Integer(0));
			}

			define_vo.setParent_value(classId);
			define_vo.setCD_no(cn_no);
			list = define_local.query(define_vo); 
		} else {
			detail_vo.setClass_define_id(classId);
			list = detail_local.query(detail_vo); 
		}

		String[] items = new String[list.size() + 1];
		items[0] = classId.toString()+ "_" + parentId.toString(); //��һ����ΪKEY����������

		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			String test = Utility.trimNull(map.get("CLASSDEFINE_ID"));

				
				if (level_id % 2 != 0) {
					item =
						Utility.trimNull(map.get("CLASSDEFINE_ID"))
							+ "$"
							+ Utility.trimNull(map.get("CLASSDEFINE_NAME"))
							+ "$"
							+ Utility.parseInt(Utility.trimNull(map.get("PARENT_VALUE")),new Integer(0))
							+ "$"
							+ "2"
							+ "$"
							+ level_id;
				} else { 
					item =
					Utility.trimNull(map.get("CLASSDETAIL_ID"))
							+ "$"
							+ Utility.trimNull(map.get("CLASSDETAIL_NAME"))
							+ "$"
							+ Utility.parseInt(Utility.trimNull(map.get("CLASSDEFINE_ID")),new Integer(0))
							+ "$"
							+ Utility.trimNull(map.get("BOTTOM_FLAG"))
							+ "$"
							+ level_id;
					
				}

			items[i + 1] = item;
		}

		return items;
	}

	/**
	 * ��ȡ�ͻ�������Ϣ��Ҫ�ϼƵ�item��Ϣ add by guifeng
	 * @param book_code
	 * @param item_id
	 * @param item_type
	 * @param row_no
	 * @return
	 * @throws Exception
	 */
	public List getCustFinanceItemIds(
		Integer book_code,
		Integer item_id,
		Integer item_type,
		String row_no)
		throws Exception {

		List list = new ArrayList();
		if (item_id == null)
			return list;
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		String sql =
			"{call SP_QUERY_TCUSTFINANCEITEM_ITEM_ID("
				+ book_code
				+ ","
				+ item_id
				+ ","
				+ item_type
				+ ")}";
		ResultSet rslist = stmt.executeQuery(sql);
		try {
			while (rslist.next()) {
				String s =
					rslist.getString("ITEM_ID")
						+ "$"
						+ rslist.getString("POSITIVE_UNION")
						+ "$"
						+ rslist.getString("IS_POSITIVE")
						+ "$"
						+ rslist.getString("IS_UNION")
						+ "$"
						+ row_no
						+ "$"
						+ item_id;
				list.add(s);
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return list;
	}

	/**
	 * ����parentid�����������Ӧ�ļ�¼   
	 * @param depart_id
	 * @return
	 * @throws Exception
	 */
	public Map getDealManListByDepartId(Integer depart_id) throws Exception {
		Map map = new HashMap();

		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		String sql = "{call SP_QUERY_TOPERATOR(0," + depart_id + ")}";
		ResultSet rslist = stmt.executeQuery(sql);
		try {
			while (rslist.next()) {
				map.put(
					rslist.getObject("OP_CODE"),
					rslist.getString("OP_NAME"));
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
			return map;
		}

	}

	/**
	 * ��ÿͻ����е�����
	 * @param cust_id
	 * @return items
	 * @throws Exception
	 */
	public String[] getCustomerClass(int cust_id) throws Exception {
		List list = new ArrayList();
		CustomerClassLocal local = EJBFactory.getCustomerClass();
		CustomerClassVO vo = new CustomerClassVO();
		vo.setCust_id(new Integer(cust_id));
		list = local.list_leibiao1(vo);
		String items[] = new String[list.size()];
		Map map = new HashMap();
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			items[i] =
				map.get("CLASSDEFINE_ID") + "$" + map.get("CLASSDETAIL_ID");
		}
		return items;
	}

	/**
	 * �޸Ŀͻ��ȼ�
	 * @param cust_id
	 * @param define_id
	 * @param detail_id
	 * @param input_man
	 * @throws Exception
	 */
	public void modifyCustomerClass(
		Integer cust_id,
		Integer define_id,
		Integer detail_id,
		Integer input_man)
		throws Exception {
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		String sql =
			"{call SP_MODI_CustomerClass("
				+ cust_id
				+ ","
				+ define_id
				+ ","
				+ detail_id
				+ ","
				+ input_man
				+ ")}";
		try {
			stmt.executeUpdate(sql);
		}catch(Exception e){
			throw e;
		}
		finally {
			stmt.close();
			conn.close();
		}
	}
	
	public String finaSameTopicSerialNo(Integer ques_id,Integer topic_serial){
		String ret = "";
		
		return ret;
	}
	/**
	 * ���ͻ���š��ͻ����Ʒ��͸�SAPϵͳ ����ר��
	 * @throws Exception 
	 */
	public void sendCustToSAP(Integer cust_id,Integer input_operatorCode) throws Exception{
		UtilityService util = new UtilityService();
		CustomerLocal cust_local = EJBFactory.getCustomer();
		CustomerVO cust_vo = new CustomerVO();
		
		cust_vo.setCust_id(cust_id);
		cust_vo.setInput_man(input_operatorCode);
		
		List list = cust_local.listProcAll(cust_vo);
		Map map = new HashMap();
		
		if(list.size()>0){
			map = (Map) list.get(0);
			
			String kunnr = Utility.trimNull(map.get("CUST_NO"));
			String name = Utility.trimNull(map.get("CUST_NAME"));
			
			if(kunnr.length()>0&&name.length()>0){
				try{
					//��URL��ַ
					//String custUrl = Utility.trimNull(Argument.getDictContent("800401"));
					//if(custUrl.length()>0){
					//	SAPClient.setCustUrl(custUrl);					
					//}
					SAPClient.setCustUrl("http://sapprd.huaao-trust.com:8000/sap/bc/srt/rfc/sap/zwebservice1/900/zwebservice1/zwebservice1?sap-language=zh&sap-user=java&sap-password=java123");
					String ret = SAPClient.sendCustomerInfo(kunnr,name);
					if(ret.length()==0){
						ret = "��";
					}
					
					String summary = "SAPͨѶ����ֵ��"+ret;
					util.insertLog(new Integer(99998),"SAP�ͻ���ϢͨѶ",input_operatorCode,summary);
				}
				catch(Exception e){
					String summary = "SAPͨѶ����"+e.getMessage();
					util.insertLog(new Integer(99998),"SAP�ͻ���ϢͨѶ",input_operatorCode,summary);
				}
			}	
		}
	}
	
	
	/**
	 * ��ÿͻ�����	
	 * @param classId ��level_idΪ����ʱid=define_id,Ϊż��ʱid=detail_id
	 * @param level_id �ڵ�ȼ�
	 * @return items
	 */
	public String[] getCustClassDefine2(Integer classId,Integer parentId, int level_id,Integer cn_no,int tableFlag,Integer detailId)
		throws Exception {
		List list = new ArrayList();
		Map map = new HashMap();
		String item = "";
		CustClassDefineLocal define_local = EJBFactory.getCustClassDefine();
		CustClassDetailLocal detail_local = EJBFactory.getCustClassDetail();
		CustClassDefineVO define_vo = new CustClassDefineVO();
		CustClassDetailVO detail_vo = new CustClassDetailVO();
		
		if (level_id == 1) {
			if (level_id == 1) {
				define_vo.setLevel_id(new Integer(1));
			} else {
				define_vo.setLevel_id(new Integer(0));
			}

			define_vo.setParent_value(classId);
			define_vo.setCD_no(cn_no);
			list = define_local.query(define_vo); 
		} else {
			
			if(tableFlag==1)
			{
				detail_vo.setClass_define_id(parentId);
				detail_vo.setClass_detail_id(classId); 
				
			}else{
				detail_vo.setClass_define_id(classId);

				detail_vo.setClass_detail_id(detailId); 
			}	
			
			list = detail_local.query2(detail_vo); 
		}

		String[] items = new String[list.size() + 1];
		items[0] = classId.toString()+ "_" + parentId.toString(); //��һ����ΪKEY����������
		int table_flag = 0;
		String testValue = "";
		String testId = "0";
		
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			table_flag = Utility.parseInt(Utility.trimNull(map.get("TABLE_FLAG")), 1);
				
				if (level_id == 1) {
					item =
						Utility.trimNull(map.get("CLASSDEFINE_ID"))
							+ "$"
							+ Utility.trimNull(map.get("CLASSDEFINE_NAME"))
							+ "$"
							+ Utility.parseInt(Utility.trimNull(map.get("PARENT_VALUE")),new Integer(0))
							+ "$"
							+ "2"
							+ "$"
							+ level_id
							+ "$"
							+ "2"
							+ "$"
							+ "0";
				} else { 
					if(table_flag==2){ //���
						
						if(tableFlag==2)
							testValue = Utility.trimNull(map.get("PARENT_ID"));
						else
							testValue = Utility.trimNull(map.get("PARENT_VALUE"));
					
						testId = Utility.trimNull(map.get("CLASSDEFINE_ID"));
					}else{ //ֵ��
						testId = Utility.trimNull(map.get("CLASSDETAIL_ID"));
						testValue = Utility.trimNull(map.get("CLASSDEFINE_ID"));
					}	

					item = testId
							+ "$"
							+ Utility.trimNull(map.get("CLASSDETAIL_NAME"))
							+ "$"
							+ Utility.parseInt(testValue,new Integer(0))
							+ "$"
							+ Utility.trimNull(map.get("BOTTOM_FLAG"))
							+ "$"
							+ level_id
							+ "$"
							+ table_flag
							+ "$"
							+ Utility.trimNull(map.get("CLASSDETAIL_ID"));
				}
			items[i + 1] = item;
		}

		return items;
	}

}
