/**
 * @author Felix
 * @Date 2007-12-20	
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enfo.crm.affair.TcustmanagersLocal;
import enfo.crm.dao.IntrustDBManager;
import enfo.crm.intrust.IntrustCustomerInfoLocal;
import enfo.crm.tools.EJBFactory;
import enfo.crm.tools.Utility;
public class CustomerInfo 
{
	public String findSameCustInfo(String cust_info,Integer flag,Integer cust_id)
	{
		String findName = "";
		IntrustCustomerInfoLocal customer = null;	
		try		
		{
			 //flag 1,受益人及委托人客户的名称,2企业客户名称，3受益人及委托人客户的证件号,4企业客户证件号
		    customer = EJBFactory.getIntrustCustomerInfo();
			customer.setCust_name(cust_info);
			customer.setCust_id(cust_id);
			customer.queryCustByName(flag);
			if(customer.getNextForList())
			{
				if(flag.intValue()==1 || flag.intValue()==2)
					findName=customer.getCust_name();
				else if (flag.intValue()==3 || flag.intValue()==4)
					findName=customer.getCard_id();	
					
			}		
			else
				findName = "";
		}
		catch(Exception e)
		{
				return e.getMessage();
		}
		finally 
		{
		    try {
                customer.remove();
            } catch (Exception e1) {                
                e1.printStackTrace();
            } 					
		}		
		return findName;
	}
	//读取客户财务信息需要合计的item信息 add by guifeng
	public List getCustFinanceItemIds(Integer book_code, Integer item_id,Integer item_type,String row_no)
		throws Exception {

		List list = new ArrayList();
		if (item_id == null)
		   return list;
		Connection conn = IntrustDBManager.getConnection();
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
//	根据parentid获得其下所对应的记录   
	 public Map getDealManListByDepartId(Integer depart_id) throws Exception{   
	 	Map map = new HashMap();   
		
		Connection conn = IntrustDBManager.getConnection();
		Statement stmt = conn.createStatement();
		String sql = "{call SP_QUERY_TOPERATOR(0,"+ depart_id+ ")}";
		ResultSet rslist = stmt.executeQuery(sql);
		try {
			while (rslist.next()) {
				map.put(rslist.getObject("OP_CODE"),rslist.getString("OP_NAME"));
		   }
		}finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return map;
				
	 }   
	 
	//读取客户财务信息需要合计的item信息 add by guifeng
	public List getCustFinanceItemIds1(Integer book_code, Integer item_id,Integer item_type,String row_no)
		throws Exception {

		List list = new ArrayList();
		if (item_id == null)
		   return list;
		Connection conn = IntrustDBManager.getConnection();
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
	/**
	 * 读取客户财务数据最新交易日期 add by taochen
	 * @param book_code
	 * @param cust_id
	 * @return
	 * @throws Exception
	 */
	public String getCustFinanceLastTradeDate(Integer book_code,Integer cust_id)throws Exception {
		int lastyear = Utility.getCurrentYear()-1;
		String sql = "{call SP_QUERY_TCUSTFINANCEINFO_LOAD("+book_code+","+cust_id+",'',"+lastyear+"0101,"+lastyear+"1231,0,-1)}";
		Connection conn = IntrustDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rslist = stmt.executeQuery(sql);
		String retList = "";
		try {
			while (rslist.next()) {
				retList = rslist.getString("FINANCE_DATE")+"_"+rslist.getString("PERIOD_UNIT")+"_"+rslist.getString("COMBINE_FLAG");
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		
		 return retList;
	}
	/**
	 * 查找客户的财务报表数据是否有年初余额记录
	 * @param cust_id
	 * @param finance_date
	 * @return
	 * @throws Exception
	 */
	public boolean isCustFinanceItemBalance1(Integer cust_id,Integer finance_date)throws Exception{
		String sqlStr = "SELECT MAX(ITEM_BALANCE1)AS MAX_ITEM_BALANCE1 FROM TCUSTFINANCEINFO WHERE CUST_ID = "+cust_id+" AND FINANCE_DATE = "+finance_date;
		
		Connection conn = IntrustDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rslist = stmt.executeQuery(sqlStr);
		String rsList = "";
		boolean retflag = false;
		
		try {
			while (rslist.next()) {
				retflag = true;
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		
		return retflag;
	}
	
	/**
	 * 根据源客户经理获取同级目标客户经理
	 * @param sourceManagerID
	 * @return
	 * @throws Exception
	 */
	public Map queryManagerManBySourceManagerId(Integer sourceManagerID) throws Exception{
		Map map = null;
		Map mapVal = new HashMap();
		
		TcustmanagersLocal tcustmanagers_Bean = EJBFactory.getTcustmanagers();
		List tagertManagersList = tcustmanagers_Bean.getManagerList_sameLevel(sourceManagerID);
		if(tagertManagersList != null && tagertManagersList.size() != 0){
			map = new HashMap();
			for (int i = 0; i < tagertManagersList.size(); i++) {
				mapVal = (Map)tagertManagersList.get(i);
				map.put(mapVal.get("MANAGERID"), mapVal .get("MANAGERNAME"));
			}
		}
		tcustmanagers_Bean.remove();
		return map;
	}
}
