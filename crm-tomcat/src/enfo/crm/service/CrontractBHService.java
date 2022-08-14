/*
 * �������� 2009-12-7
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
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enfo.crm.intrust.ContractLocal;
import enfo.crm.intrust.PreContractLocal;
import enfo.crm.tools.EJBFactory;
import enfo.crm.tools.Utility;
import enfo.crm.vo.ContractVO;
import enfo.crm.vo.PreContractVO;
import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.dao.IntrustDBManager;

/**
 * @author enfo
 * 
 * ��������������ע�͵�ģ��Ϊ ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class CrontractBHService {
	/**
	 * crontract_type��ͬ���ͣ�1,�Ϲ���ͬ��2 �깺��ͬ 3����ͨ�����ͬ�� 4�����޺�ͬ�� 5��Ͷ�ʺ�ͬ
	 */
	public String findIfExistSameBH(Integer book_code, Integer contract_type,
			Integer product_id, String contract_sub_bh) {
		//���ؽ��
		String str = "";

		//��ú�ͬ����
		ContractLocal contract = null;
		ContractVO vo = new ContractVO();

		//��ѯ����
		vo.setBook_code(book_code);
		vo.setProduct_id(product_id);
		vo.setContract_sub_bh(contract_sub_bh);
		vo.setContract_type(contract_type);

		try {
			contract = EJBFactory.getContract();
			Integer flag = contract.isExistSameContractBH(vo);

			if (flag.intValue() == 1)
				str = "<font size='2' color='red'> �ú�ͬ����Ѵ��ڣ�</font>";
			else if (flag.intValue() == 0)
				str = "<font size='2' color='green'> �ú�ͬ��ſ���ʹ�ã�</font>";
				
			if (contract_sub_bh == null || contract_sub_bh.trim().length() == 0) 
				str = "";
		} catch (Exception e) {
			return str = e.getMessage();
		} finally {
			try {
				if (contract != null) contract.remove();
			} catch (Exception e) {
			}

			return str;
		}
	}

	public String getSameBHContractInfo(Integer contract_type,Integer product_id,String contract_sub_bh)throws Exception{
//		���ؽ��
		String str = "";

		//��ú�ͬ����
		ContractLocal contract = null;
		ContractVO vo = new ContractVO();

		//��ѯ����
		vo.setProduct_id(product_id);
		vo.setContract_sub_bh(contract_sub_bh);
		vo.setContract_type(contract_type);

		try {
			contract = EJBFactory.getContract();
			str = contract.getSameBHContractInfo(vo);

			}
		 catch (Exception e) {
			return str = e.getMessage();
		} finally {
			try {
				contract.remove();
			} catch (Exception e) {
			}

			return str;
		}
	}
	
	/**
	 * ����ԤԼSerialNo �õ�ԤԼ��Ϣ
	 * 
	 * @param pre_serial_no
	 * @return
	 * @throws Exception
	 */
	public String[] getPreContractInfo(String pre_serial_no) throws Exception {
		String[] returnValue = new String[6];
		List rsList = null;
		Map map = null;

		String[] import_and_pre = Utility.splitString(pre_serial_no,"$");
		
		//��ȡ����
		PreContractLocal preContract = EJBFactory.getPreContract();//ԤԼ
		PreContractVO pre_vo = new PreContractVO();

		//�õ�ԤԼ��Ϣ
		if(Utility.parseInt(import_and_pre[1],0)==0){
			pre_vo.setSerial_no(Utility.parseInt(import_and_pre[0],new Integer(0)));
			rsList = preContract.loadFromIntrust(pre_vo);
	
			if (rsList.size() > 0) {
				map = (Map) rsList.get(0);
			}
	
			if (map != null) {
				returnValue[0] = Utility.parseInt(
						Utility.trimNull(map.get("CUST_ID")), new Integer(0))
						.toString();
				returnValue[1] = Utility.trimNull(map.get("CUST_NAME"));
				returnValue[2] = Utility.trimNull(map.get("CUST_NO"));
				returnValue[3] = Utility.trimNull(map.get("PRE_CODE"));
				returnValue[4] = Utility.parseDecimal(
						Utility.trimNull(map.get("PRE_MONEY")), null, 2, "1")
						.toString();
                returnValue[5] = Utility.trimNull(map.get("SUB_PRODUCT_ID"));
			}
		}else{ //���е����ԤԼ��Ϣ
			pre_vo.setCust_id(Utility.parseInt(import_and_pre[0],new Integer(0)));
			rsList = preContract.importByListPreContract(pre_vo);
			if (rsList.size() > 0) {
				map = (Map) rsList.get(0);
			}
	
			if (map != null) {
				returnValue[0] = "0";
				returnValue[1] = Utility.trimNull(map.get("CUST_NAME"));
				returnValue[2] = Utility.trimNull(map.get(""));
				returnValue[3] = Utility.trimNull(map.get("PRE_DATE"));
				returnValue[4] = Utility.parseDecimal(
						Utility.trimNull(map.get("PRE_MONEY")), null, 2, "1")
						.toString();
                returnValue[5] = Utility.trimNull(map.get("SUB_PRODUCT_ID"));
			}
		}
		return returnValue;
	}
	
	
	/**
	 * ����ԤԼSerialNo �õ�ԤԼ��Ϣcrm
	 * 
	 * @param pre_serial_no
	 * @return
	 * @throws Exception
	 */
	public String[] getPreContractInfoCRM(String pre_serial_no) throws Exception {
		String[] returnValue = new String[6];
		List rsList = null;
		Map map = null;

		String[] import_and_pre = Utility.splitString(pre_serial_no,"$");
		
		//��ȡ����
		PreContractLocal preContract = EJBFactory.getPreContract();//ԤԼ
		PreContractVO pre_vo = new PreContractVO();

		//�õ�ԤԼ��Ϣ
		if(Utility.parseInt(import_and_pre[1],0)==0){
			pre_vo.setSerial_no(Utility.parseInt(import_and_pre[0],new Integer(0)));
			rsList = preContract.load(pre_vo);
	
			if (rsList.size() > 0) {
				map = (Map) rsList.get(0);
			}
	
			if (map != null) {
				returnValue[0] = Utility.parseInt(
						Utility.trimNull(map.get("CUST_ID")), new Integer(0))
						.toString();
				returnValue[1] = Utility.trimNull(map.get("CUST_NAME"));
				returnValue[2] = Utility.trimNull(map.get("CUST_NO"));
				returnValue[3] = Utility.trimNull(map.get("PRE_CODE"));
				returnValue[4] = Utility.parseDecimal(
						Utility.trimNull(map.get("PRE_MONEY")), null, 2, "1")
						.toString();
                returnValue[5] = Utility.trimNull(map.get("SUB_PRODUCT_ID"));
			}
		}else{ //���е����ԤԼ��Ϣ
			pre_vo.setCust_id(Utility.parseInt(import_and_pre[0],new Integer(0)));
			rsList = preContract.importByListPreContract(pre_vo);
			if (rsList.size() > 0) {
				map = (Map) rsList.get(0);
			}
	
			if (map != null) {
				returnValue[0] = "0";
				returnValue[1] = Utility.trimNull(map.get("CUST_NAME"));
				returnValue[2] = Utility.trimNull(map.get(""));
				returnValue[3] = Utility.trimNull(map.get("PRE_DATE"));
				returnValue[4] = Utility.parseDecimal(
						Utility.trimNull(map.get("PRE_MONEY")), null, 2, "1")
						.toString();
                returnValue[5] = Utility.trimNull(map.get("SUB_PRODUCT_ID"));
			}
		}
		return returnValue;
	}
	
	/**
	 * ��ѯ��Ȼ�˺�ͬ��������
	 * 
	 * @param bookCode
	 * @param product_id
	 * @return
	 * @throws BusiException
	 */
	public String[] getSaleInfo(Integer bookCode, Integer product_id)
			throws BusiException {
		String sqlStr = "{?=call SP_QUERY_SALEINFO(?,?,?,?,?)}";
		Object[] params = new Object[2];
		String[] result = new String[3];
		Object[] object = null;

		params[0] = bookCode;
		params[1] = product_id;

		int[] outParamPos = new int[] { 4, 5, 6 };
		int[] outParamType = new int[] { Types.INTEGER, Types.INTEGER,
				Types.INTEGER };

		object = IntrustDBManager.cudProc(sqlStr, params, outParamPos,
				outParamType);
		result[0] = Utility.trimNull(object[0]);
		result[1] = Utility.trimNull(object[1]);
		result[2] = Utility.trimNull(object[2]);
		return result;
	}
	
	/**
	 * ����Ԥ�Ǽǽ���ȡ�����Ŷӵ��ŶӸ�������Ϣ
	 * @param booCode����
	 * @param regMoney�ǼǶ��
	 */
	public String[] getTeamInfoByRegMoney(Integer booCode, BigDecimal regMoney) throws BusiException{
		Object[] params = new Object[2];
		String[] result = new String[3];
		Map map = null;
		params[0] = Utility.parseInt(booCode, new Integer(0));
		params[1] = Utility.parseBigDecimal(regMoney, new BigDecimal(0));
		List list = CrmDBManager.listBySql("{call SP_QUERY_TEAMINFOBYREGMONEY(?,?)}", params);
		if(list != null && list.size() != 0){
			map = (Map)list.get(0);
		
			result[0] = Utility.trimNull(map.get("TEAM_NAME"));
			result[1] = Utility.trimNull(map.get("LEADER"));
			result[2] = Utility.trimNull(map.get("LEADER_NAME"));
		}
		return result;
	}
	
	/**
	 * ȡ���깺��Ϣ
	 * @return
	 */
	public String[] getSgContractInfo(Integer serial_no) throws Exception {
		String querySgContractSql = "{call SP_QUERY_TCONTRACT_LOAD(?)}";
		String[] items = new String[9];
			
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt =
			conn.prepareCall(
					querySgContractSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		stmt.setInt(1, serial_no.intValue());
		
		ResultSet rslist = stmt.executeQuery();
		
		try {
			while (rslist.next()) {
				items[0] = Utility.trimNull(rslist.getObject("BANK_ID"));
				items[1] = Utility.trimNull(rslist.getObject("BANK_SUB_NAME"));
				items[2] = Utility.trimNull(rslist.getObject("BANK_ACCT"));
				items[3] = Utility.trimNull(rslist.getObject("BANK_ACCT_TYPE"));
				items[4] = Utility.trimNull(rslist.getObject("GAIN_ACCT"));
				items[5] = Utility.trimNull(rslist.getObject("BONUS_FLAG"));
				items[6] = Utility.trimNull(rslist.getObject("BONUS_RATE"));
				items[7] = Utility.trimNull(rslist.getObject("PROV_FLAG"));
				items[8] = Utility.trimNull(rslist.getObject("PROV_LEVEL"));
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return items;
	}
	
	/**
	 * ����TCustomers�ͻ���Ϣ����TCustomerContacts�ͻ���Ϣ
	 * @return
	 */	
	public List getCustOptions(String cust_id) throws Exception {
		List list=new ArrayList();
		String str1="";
		String str2="";
		String str3="";
		String str4="";
		Connection conn = IntrustDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rslist = stmt.executeQuery("select ContactID,CUST_ID,Contactor,PhoneHome,PhoneOffice,Moblie,Address from " +
				"EFCRM..TCustomerContacts where CUST_ID="+cust_id);
		try{
			while (rslist.next()) {
				Map map = new HashMap();
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
				String str=rslist.getString("Contactor")+" | "+str1+str2+str3+str4;
				map.put(String.valueOf(rslist.getInt("ContactID")),str);
				list.add(map);			
			}
		}finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return list;
	}	
}