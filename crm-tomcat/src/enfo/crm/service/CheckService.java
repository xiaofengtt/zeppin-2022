/*
 * �������� 2008-6-6
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.service;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import enfo.crm.affair.ScheDulesLocal;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.system.MessageLocal;
import enfo.crm.tools.EJBFactory;
import enfo.crm.tools.Utility;
import enfo.crm.util.DwrDecoder;
import enfo.crm.vo.ScheDulesVO;
import enfo.crm.workflow.ImportFlowWorkLocal;

/**
 * @author guifeng
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class CheckService {


	/**ADD BY YZJ 2008-07-25
	 *�����ʲ�����Ż�ȡ��ֵ�� �۾ɷ��� ʹ�����ޣ�������λ
	 *SP_QUERY_FA_ASSETTYPE @IN_BOOK_CODE	TINYINT	����
							@IN_Parent_NO	Varchar(10)	��������
							@IN_Type_ID	INT	���ID
							@IN_Type_NO	Varchar(10)	�����
	*/

	public Object[] getScrapRateByAssetTypeNo(
		Integer book_code,
		String type_no)
		throws Exception {

		Object[] param = new Object[5];
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rslist =
			stmt.executeQuery(
				"{call SP_QUERY_FA_ASSETTYPE("
					+ book_code
					+ ",'',0,'"
					+ type_no
					+ "')}");
		try {
			while (rslist.next()) {
				param[0] = (BigDecimal) rslist.getBigDecimal("NET_RATE");
				param[1] = rslist.getString("DEPR_TYPE");
				param[2] = rslist.getString("DEPR_METHOD");
				param[3] = new Integer(rslist.getInt("USE_LIMIT"));
				param[4] = rslist.getString("UNIT");
				break;
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return param;
	}

	/**ADD BY YZJ 2008-07-25
	 *�����ʲ�����Ż�ȡ��ֵ��
	 *SP_QUERY_FA_ASSETTYPE @IN_BOOK_CODE	TINYINT	����
							@IN_Parent_NO	Varchar(10)	��������
							@IN_Type_ID	INT	���ID
							@IN_Type_NO	Varchar(10)	�����
	*/

	public Integer getBottomFlagByAssetTypeNo(
		Integer book_code,
		String type_no)
		throws Exception {
		Integer bottom_flag = new Integer(0);
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rslist =
			stmt.executeQuery(
				"{call SP_QUERY_FA_ASSETTYPE("
					+ book_code
					+ ",'',0,'"
					+ type_no
					+ "')}");
		try {
			while (rslist.next()) {
				bottom_flag =
					(rslist.getBoolean("BOTTOM_FLAG"))
						? new Integer(1)
						: new Integer(0);
				break;
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return bottom_flag;
	}

	/**
	 * ADD BY TSG 2008
	 * ��������Ŀ�Ƿ���� �������ڿ�Ŀ�������������
	 * SP_QUERY_TSUBJECT @IN_BOOK_CODE TINYINT,
	                     @IN_PARENT_ID INT,
	                     @IN_SUB_CODE VARCHAR(24),
	                     @IN_BOTTOM_FLAG BIT,
	                     @IN_INPUT_MAN SMALLINT
	                     
	 * @param book_code ����
	 * @param sub_code  ��Ŀ����
	 * @param subcode_type ��Ŀ��� 
	 * */
	public String[] checkIsRightSubcode(
		Integer book_code,
		String sub_code,
		String subcode_type)
		throws Exception {
		String[] sub = { "", "" };
		sub[0] = subcode_type;
		if (sub_code == null || sub_code.equals(""))
			return sub;
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		String sql =
			"{call SP_QUERY_TSUBJECT("
				+ book_code
				+ ",0,'"
				+ sub_code
				+ "',2,0)}";
		//Utility.debugln("------------------sql:"+sql);		
		ResultSet rslist = stmt.executeQuery(sql);
		try {
			while (rslist.next()) {
				sub[1] = rslist.getString("SUB_NAME");
				break;
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sub;
	}

	/**
	 * ADD BY TSG 20080801
	 * ��������Ͷ�ʺ�ͬ����Ƿ����
	 * SP_QUERY_AUTH_CONTRACT_BH @IN_BOOK_CODE TINYINT,
	                             @IN_BUSI_ID VARCHAR(10),----120301 ���120302 ���ޣ�120305Ͷ��
	                             @IN_CONTRACT_SUB_BH VARCHAR(100)
	
	 * @param book_code ����
	 * @param contract_sub_code  ʵ�ʺ�ͬ���
	 * @param  contract_id ��ͬID 
	 * */
	public int checkInvestContractSubCode(
		Integer book_code,
		String busi_id,
		String contract_sub_code,
		Integer contract_id)
		throws Exception {

		int size = 0;
		if (contract_sub_code == null || contract_sub_code.equals(""))
			return 0;
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		String sql =
			"{call SP_QUERY_AUTH_CONTRACT_BH("
				+ book_code
				+ ",'"
				+ busi_id
				+ "','"
				+ contract_sub_code
				+ "')}";
		ResultSet rslist = stmt.executeQuery(sql);

		try {
			while (rslist.next()) {
				if (contract_id != null && contract_id.intValue() > 0) { //�༭��֤
					if (rslist.getInt("CONTRACT_ID")
						!= contract_id.intValue()) {
						//�༭ʱ����ǰ��ͬ�ĺ�ͬ���������������ͬ�ĺ�ͬ���ʱ
						size++;
						break;
					}
				} else if (
					contract_id == null
						|| (contract_id != null
							&& contract_id.intValue() == 0)) { //�½���֤	
					size++;
					break;
				}

			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return size;

	}

	/**
	 * ADD BY TSG 200800902
	 * ��ȡ�ʲ������ʽ�Ŀ�Ŀ��Ϣ
	 * SP_QUERY_FA_VARYTYPE  @IN_BOOK_CODE	TINYINT	����
							@IN_Vary_Flag	TINYINT	�䶯��־��1���ӣ�2����,3�䶯��
							@IN_Vary_Type_Name	Varchar(30)	�䶯�������
							@IN_Account_Flag	TINYINT	�����־:0�����л�ƺ���1���л�ƺ���2ȫ��	2
							@IN_Remark	VARCHAR(200)	��ע
							@IN_Vary_Type	INT	�䶯���
	                        @IN_SERIAL_NO	INT	���	0
	
	 * @param book_code ����
	 * @param vary_type ������
	 * @return s ��Ŀ�ַ�����Ϣ
	 * */
	public String[] getVaryTypeSubcodeMsg(Integer book_code, String vary_type)
		throws Exception {

		String[] s = { "", "", "", "", "", "", "", "", "", "" };
		if (vary_type == null || vary_type.equals(""))
			return s;
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		String sql =
			"{call SP_QUERY_FA_VARYTYPE("
				+ book_code
				+ ",0,'',2,'','"
				+ vary_type
				+ "',0)}";
		ResultSet rslist = stmt.executeQuery(sql);

		try {
			while (rslist.next()) {
				s[0] = rslist.getBoolean("ACCOUNT_FLAG") ? "1" : "0";
				s[1] = String.valueOf(rslist.getInt("VARY_FLAG"));
				s[2] = String.valueOf(rslist.getString("ENTER_SUBCODE"));
				s[3] = String.valueOf(rslist.getString("ENTER_SUBNAME"));
				s[4] = String.valueOf(rslist.getString("TEMP_SUBCODE"));
				s[5] = String.valueOf(rslist.getString("TEMP_SUBNAME"));
				s[6] = String.valueOf(rslist.getString("INCOME_SUBCODE"));
				s[7] = String.valueOf(rslist.getString("INCOME_SUBNAME"));
				s[8] = String.valueOf(rslist.getString("FARE_SUBCODE"));
				s[9] = String.valueOf(rslist.getString("FARE_SUBNAME"));
				break;

			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return s;

	}

	/**
	 * add by tsg 2008-11-06
	 * ������Ϣ�Ķ�ȡ״̬
	 * */
	public String setMessageReadStatus(
		Integer serial_no,
		Integer read_flag,
		Integer input_man) {
		try {
			MessageLocal local = EJBFactory.getMessage();
			local.modiMessageReadFlag(serial_no, read_flag, input_man);
		} catch (Exception e) {
			return "0";
		}
		return "1";

	}


	//��ȡ�ͻ�������Ϣ��Ҫ�ϼƵ�item��Ϣ add by guifeng
	public List getCustFinanceItemIds(
		Integer book_code,
		Integer item_id,
		Integer item_type,
		String column_no)
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
						+ column_no
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
	 * ADD BY  TSG 2009-04-03
	 * ����SERVICE_ID�жϸ÷����Ƿ�����FTP��Ϣ
	 * */
	public String checkIsFTP(Integer service_id) throws Exception {
		String ret = "0";
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		String sql =
			"SELECT * FROM BC_SERVICE WHERE   ISNULL(WEB_FTP_ID,0) <> 0 AND ISNULL(BANK_FTP_ID,0) <> 0 AND  SERVICE_ID = "
				+ service_id;

		//Utility.debugln("------------------sql:"+sql);		
		ResultSet rslist = stmt.executeQuery(sql);
		try {
			while (rslist.next()) {
				ret = "1";
				break;
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return ret;
	}

	/**
	 * ADD BY TSG 2009-05-05
	 * ����֧ƱSERIAL_NO ��ȡ֧Ʊ������Ϣ
	 * */
	public String[] getChequeNoInfo(Integer cheque_serial_no)
		throws Exception {

		String[] no = new String[2];
		no[0] = "";
		no[1] = "";
		if (cheque_serial_no == null)
			return no;
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt =
			conn.prepareCall(
				"{call SP_QUERY_TCHEQUEYE_NO(?,?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);

		stmt.setInt(1, cheque_serial_no.intValue());
		stmt.registerOutParameter(2, java.sql.Types.VARCHAR);
		stmt.registerOutParameter(3, java.sql.Types.VARCHAR);
		stmt.execute();
		no[0] = stmt.getString(2);
		no[1] = stmt.getString(3);
		return no;
	}
	
	//��ȡ�ͻ�������Ϣ��Ҫ�ϼƵ�item��Ϣ add by guifeng
	public List getCustFinanceItemIds1(Integer book_code, Integer item_id,Integer item_type,String row_no)
		throws Exception {

		List list = new ArrayList();
		if (item_id == null)
		   return list;
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		String sql = "{call SP_QUERY_TCUSTFINANCEITEM_ITEM_ID("+ book_code+","+ item_id+ ","+ item_type+ ")}";
	   ResultSet rslist = stmt.executeQuery(sql);
		try {
			while (rslist.next()) {
				String s =  rslist.getString("ITEM_ID") +"$" + rslist.getString("POSITIVE_UNION")
								+"$" + rslist.getString("IS_POSITIVE") +"$" + rslist.getString("IS_UNION") 
								+ "$" + row_no + "$" + item_id;
				list.add(s);
		   }
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
	   return list;
	}
	
	//	��ѯ�������ʵĿͻ��б�
	public String getDbCustList(Integer book_code,String busi_id,String dbfs_type,String contract_bh) throws Exception {
		String listSql = "{call SP_QUERY_TDKDBCUSTINFO(?,?,?,?)}";
		StringBuffer sb = new StringBuffer();
		StringBuffer sb_sub = new StringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt =
			conn.prepareCall(
				listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, book_code.intValue());
		stmt.setString(2, busi_id);
		stmt.setString(3, dbfs_type);
		stmt.setString(4, DwrDecoder.unescape(contract_bh.toString()));
		ResultSet rs = stmt.executeQuery();
		sb.append("[");
			try {
				while (rs.next()) {
					sb_sub.append("{").append("'cust_id':'").append(rs.getInt("CUST_ID")).append("'").append(",");
					sb_sub.append("'cust_name':'").append(rs.getString("CUST_NAME")).append("'");
					sb_sub.append("}");
					sb_sub.append(",");	
				}
			} finally {
				rs.close();
				stmt.close();
				conn.close();
			}
		sb.append(sb_sub.toString().substring(0,sb_sub.toString().length()-1));
		sb.append("]");
		return sb.toString();
	}
	/**
	 * �ճ�����
	 * @param serial_no
	 * @param begin_date
	 * @param end_date
	 * @param schedule_type
	 * @param check_flag
	 * @param imput_man
	 * @return
	 * @throws Exception
	 */
	public String getScheDulesList(Integer serial_no,Integer begin_date,Integer end_date,Integer schedule_type,Integer check_flag,Integer imput_man) throws Exception{
		ScheDulesVO vo = new ScheDulesVO();
		StringBuffer sb = new StringBuffer();
		StringBuffer sb_sub = new StringBuffer();
		List list = null;
		Map map = null;
		Iterator iterator = null; 
		ScheDulesLocal local = null;
		Date v_startDate = null;
		Date v_endDate = null;
		Locale aLocale = Locale.US;  
		DateFormat format = new SimpleDateFormat("d MMM yyyy hh:mm:ss z",new DateFormatSymbols(aLocale));
		format.setTimeZone(TimeZone.getTimeZone("GMT"));  
		try{
			local = EJBFactory.getScheDules();
		
			vo.setSerial_no(serial_no);
			vo.setBegin_date(begin_date);
			vo.setEnd_date(end_date);
			vo.setSchedule_type(schedule_type);
			vo.setCheck_flag(check_flag);
			vo.setInput_man(imput_man);
			
			list = local.list_query(vo);
			
			iterator = list.iterator();
			
			sb.append("[");
				while (iterator.hasNext()) {
					map = (Map)iterator.next();
					
					sb_sub.append("{").append("'serial_no':'").append(map.get("SERIAL_NO")).append("'").append(",");
					sb_sub.append("'id':'").append(map.get("SERIAL_NO")).append("'").append(",");
					sb_sub.append("'title':'").append(map.get("SCHEDULE_NAME")+"-"+map.get("CONTENT")).append("'").append(",");
					sb_sub.append("'start':'").append(format.format(map.get("START_DATE"))).append("'").append(",");
					sb_sub.append("'end':'").append(format.format(map.get("END_DATE"))).append("'").append(",");
					sb_sub.append("'schedule_type':'").append(map.get("SCHEDULE_TYPE")).append("'").append(",");
					sb_sub.append("'schedule_name':'").append(map.get("SCHEDULE_NAME")).append("'").append(",");
					sb_sub.append("'schedule_date':'").append(map.get("SCHEDULE_DATE")).append("'").append(",");
					sb_sub.append("'op_code':'").append(map.get("OP_CODE")).append("'").append(",");
					sb_sub.append("'op_name':'").append(map.get("OP_NAME")).append("'").append(",");
					sb_sub.append("'content':'").append(map.get("CONTENT")).append("'").append(",");

					sb_sub.append("'check_flag':'").append(map.get("CHECK_FLAG")).append("'");
					sb_sub.append("}");
					sb_sub.append(",");	
				}
				if(sb_sub.toString().length() > 0){	
					sb.append(sb_sub.toString().substring(0,sb_sub.toString().length()-1));
				}
			sb.append("]");
			
			return sb.toString();
		}finally{
			local.remove();
		}
	}
	
	public String updateScheDulesList(Integer serial_no,String begin_date,String end_date,Integer schedule_type,Integer op_code) throws Exception{
		try {
			ScheDulesVO vo = new ScheDulesVO();
			ScheDulesLocal local = EJBFactory.getScheDules();
			
			vo.setSerial_no(serial_no);
			vo.setSchedule_start_date(begin_date);
			vo.setSchedule_end_date(end_date);
			vo.setSchedule_type(schedule_type);
			vo.setInput_man(op_code);
			
			local.modi(vo);
		} catch (Exception e) {
			//throw new Exception("�����ճ�ʧ��" +e.getMessage());
			return "�����ճ�ʧ��" +e.getMessage();
		}
		return "1";
		
	}
	/**
	 * ��������ͼʱ����
	 * @param ProjectId
	 * @param NodesArray
	 * @param LinesArray
	 * @param NodesStr
	 * @param LinesStr
	 * @param input_operatorCode
	 * @return
	 * @throws Exception
	 */
	public String setWorkFlowStr(String ProjectId,String[] NodesArray,String[] LinesArray,String NodesStr,String LinesStr,Integer input_operatorCode) throws Exception{
		ImportFlowWorkLocal fwlocal = EJBFactory.getImportFlowWork();	
		if(NodesArray == null || LinesArray == null){
			return "0";
		}else if(NodesArray.length == 0 || LinesArray.length == 0){
			return "0";
		}
		for(int i=0;i<NodesArray.length;i++){
			NodesArray[i] = DwrDecoder.unescape(Utility.trimNull(NodesArray[i]));
		}
		for(int i=0;i<LinesArray.length;i++){
			LinesArray[i] = DwrDecoder.unescape(Utility.trimNull(LinesArray[i]));
		}
		NodesStr = DwrDecoder.unescape(Utility.trimNull(NodesStr));
		LinesStr = DwrDecoder.unescape(Utility.trimNull(LinesStr));
		fwlocal.import_workflow_nodes(ProjectId,NodesArray,input_operatorCode);//����״̬��
		fwlocal.import_workflow_lines(ProjectId,LinesArray,input_operatorCode);//����״̬��֮�������
		fwlocal.del_nodes_lines(ProjectId,NodesStr,LinesStr,input_operatorCode);//
		fwlocal.update_flow_nodelineobject(ProjectId,input_operatorCode);
		fwlocal.remove();
		return "1";	
	}

}
