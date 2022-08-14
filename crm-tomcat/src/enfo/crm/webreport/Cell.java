/**
 * <p>Title: CellHelper</p> 
 * <p>CreateTime: 2005-4-15</p>
 * @author <a href="mailto:caiyuan@singlee.com.cn">Cai Yuan</a>
 * @version 1.0 
 * 
 */
package enfo.crm.webreport;

import enfo.crm.dao.IntrustDBManager;
import java.sql.*;
import enfo.crm.dao.*;
import enfo.crm.tools.*; //由于使用的系统连接数据库接口所以导入了这个包，抽象出来的时候可去掉
import java.util.*;

public class Cell {

	protected static sun.jdbc.rowset.CachedRowSet rowset;

	public synchronized static Connection getConnection() throws Exception {
		Class
			.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver")
			.newInstance();
		String url =
			"jdbc:microsoft:sqlserver://128.8.28.79:1433;DatabaseName=EFCRM";
		Connection conn = DriverManager.getConnection(url, "sa", "000000");
		return conn;
	}

	public static sun.jdbc.rowset.CachedRowSet queryProc(String procedure)
		throws Exception {
		int pos;
		String proc_left, proc_right;
		String proc_final = "";
		//**********先根据语句取出参数*****************
		pos = procedure.indexOf("(") + 1;
		proc_left = procedure.substring(0, pos); //取的存储过程的左半边名称
		//取得参数字符串
		proc_right = procedure.substring(pos);
		pos = proc_right.indexOf(")");
		proc_right = proc_right.substring(0, pos);
		//根据逗号取得存储过程的参数		
		String[] param = proc_right.split( ",");
		for (int i = 0; i < param.length; i++) {
			proc_final = "?," + proc_final;
		}
		//去掉最后一个逗号
		pos = proc_final.lastIndexOf(",");
		if (pos > 0)
			proc_final = proc_final.substring(0, pos);
		proc_final = "{" + proc_left + proc_final + ")}";

		Connection conn = null;
		CallableStatement stmt = null;
		ResultSet rslist = null;

		try {
			conn = CrmDBManager.getConnection();
			stmt =
				conn.prepareCall(
					proc_final,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			for (int i = 0; i < param.length; i++) {
				stmt.setObject((i + 1), param[i].trim());
			}

			rslist = stmt.executeQuery();
			rowset = new sun.jdbc.rowset.CachedRowSet();
			rowset.close();
			rowset.populate(rslist);
		} finally {
			if (rslist != null)
				rslist.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
		return rowset;
	}

	public static sun.jdbc.rowset.CachedRowSet querySql(String sql)
		throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rslist = null;

		try {
			conn = CrmDBManager.getConnection();
			stmt = conn.createStatement();
			rslist = stmt.executeQuery(sql.toString());
			rowset = new sun.jdbc.rowset.CachedRowSet();
			rowset.close();
			rowset.populate(rslist);
		} finally {
			if (rslist != null)
				rslist.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
		return rowset;
	}

	public boolean getNext() throws Exception {
		return rowset.next();
	}

	// 计算记录数
	public static int getRowCount() throws Exception {
		rowset.last();
		int rowCount = rowset.getRow();
		rowset.beforeFirst();
		return rowCount;
	}

	//更新菜单信息
	public static boolean updateMenu(
		int rpt_id,
		String rpt_title,
		String rpt_type_name,
		Integer input_man)
		throws Exception {
		String listSql = "{call SP_ADD_REPORTMENU(?,?,?,?)}";

		Connection conn = null;
		CallableStatement stmt = null;
		try {
			conn = CrmDBManager.getConnection();
			stmt = conn.prepareCall(listSql);
			stmt.setInt(1, rpt_id);
			stmt.setString(2, rpt_title);
			stmt.setString(3, rpt_type_name);
			if (input_man == null)
				stmt.setInt(4, 0);
			else
				stmt.setInt(4, input_man.intValue());

			stmt.execute();
			return true;
		} catch (Exception e) {
			throw new Exception("保存报表菜单信息失败: " + e.getMessage());
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

	//-----------------------------返回报表输入页的参数值的列表----------------------------------------			
	public static String[] getParamsList(
		String paramFlagArray,
		Integer op_code,
		Integer book_code)
		throws Exception {
		String[] inputArray = CellHelper.splitString(paramFlagArray, ",");
		String[] resultArray = new String[inputArray.length];

		int inputInt = 0;

		for (int i = 0; i < inputArray.length; i++) {
			if (inputArray[i] != null && !inputArray[i].equals(" ")) {
				inputInt = Integer.parseInt(inputArray[i]);
				if(inputInt>=6000&&inputInt<=6002)
					resultArray[i] = getProductCodeOptions(inputInt,book_code,op_code);
				else if(inputInt>=6003&&inputInt<=6005)
					resultArray[i] = getProductIdOptions(inputInt,book_code,op_code);
				//分帐套产品列表---单一：6010~6012，集合6013~6015
				else if(inputInt>=6010&&inputInt<=6015)
					resultArray[i] = getProductCodeOptions4(inputInt,book_code,op_code);
				//不分帐套产品列表
				else if(inputInt>=6030&&inputInt<=6032)
					resultArray[i] = getProductCodeOptions2(inputInt,book_code,op_code);
				else if(inputInt>=6033&&inputInt<=6035)
					resultArray[i] = getProductIdOptions2(inputInt,book_code,op_code);
				//有证券业务的产品列表
				else if(inputInt>=6060&&inputInt<=6062)
					resultArray[i] = getProductCodeOptions3(inputInt,book_code,op_code);
				else if(inputInt>=6063&&inputInt<=6065)
					resultArray[i] = getProductIdOptions3(inputInt,book_code,op_code);
				//币种
				else if (inputInt >= 7203 && inputInt <= 7205)
					resultArray[i] = getMoneyOptions(inputInt);
				//币种---不包含人民币
				else if (inputInt >= 7206 && inputInt <= 7208)
					resultArray[i] = getMoneyOptions2(inputInt);
				//销售人员
				else if (inputInt >= 9001 && inputInt <= 9002)
					resultArray[i] = getSaleManOptions(inputInt);
				//基金经理
				else if (inputInt >= 9003 && inputInt <= 9005)
					resultArray[i] = getManagerOptions(inputInt, book_code);
				//营业部
				else if (inputInt >= 9006 && inputInt <= 9008)
					resultArray[i] = getSecuDepartOptions(inputInt);
				//字典表中定义的参数
				else if (inputInt > 10000 && inputInt < 20000) {
					inputInt = inputInt - 10000;
					resultArray[i] = getDictParamOptions(inputInt);
				} else if (inputInt >= 20001 && inputInt <= 20100) {
					// 预留100个角色( role_id 从1至99 100表示不按照角色查询)					
					int role_id = inputInt - 20000;
					resultArray[i] =
						getOperatorOptionsByRole(book_code.intValue(), role_id);
				}

				//INI表取
				else
					resultArray[i] = getIniParamOptions(inputInt);
			}
		}
		return resultArray;
	}

	//币种
	public static String getMoneyOptions(int inputInt) throws Exception {
		String listSql = "SELECT CURRENCY_ID,CURRENCY_NAME FROM TCURRENCY";

		StringBuffer sb = new StringBuffer(200);
		if (inputInt == 7204)
			sb.append("-" + "全部" + "\t");
		else if (inputInt == 7205)
			sb.append("0" + "-" + "全部" + "\t");

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = CrmDBManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(listSql);

			while (rs.next()) {
				sb.append(
					rs.getString("CURRENCY_ID")
						+ "-"
						+ rs.getString("CURRENCY_NAME")
						+ "\t");
			}
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

	//币种---不包含人民币
	public static String getMoneyOptions2(int inputInt) throws Exception {
		String listSql = "SELECT CURRENCY_ID,CURRENCY_NAME FROM TCURRENCY";

		StringBuffer sb = new StringBuffer(200);
		if (inputInt == 7207)
			sb.append("-" + "全部" + "\t");
		else if (inputInt == 7208)
			sb.append("0" + "-" + "全部" + "\t");

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = CrmDBManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(listSql);

			while (rs.next()) {
				if (!rs.getString("CURRENCY_ID").equals("01"))
					sb.append(
						rs.getString("CURRENCY_ID")
							+ "-"
							+ rs.getString("CURRENCY_NAME")
							+ "\t");
			}
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

	/**
	 * add by tsg 2008-12-08
	 * 按角色查操作员列表
	 * */
	public static String getOperatorOptionsByRole(int book_code, int role_id)
		throws Exception {

		String listSql = ""; //按角色查询
		if (role_id != 100)
			listSql = "{call SP_QUERY_TOPERATOR_BYROLE(?,?)}";
		else //查询所有
			listSql = "SELECT OP_CODE,OP_NAME FROM TOPERATOR";

		StringBuffer sb = new StringBuffer(200);
		sb.append("0" + "-" + "全部" + "\t");

		Connection conn = null;
		CallableStatement operator_byrole_stmt = null;
		Statement alloperator_stmt = null;
		ResultSet rs = null;
		try {
			conn = CrmDBManager.getConnection();
			if (role_id != 100) { //按角色查询
				operator_byrole_stmt = conn.prepareCall(listSql);
				operator_byrole_stmt.setInt(1, book_code);
				operator_byrole_stmt.setInt(2, role_id);
				rs = operator_byrole_stmt.executeQuery();
			} else { //查询所有
				alloperator_stmt = conn.createStatement();
				rs = alloperator_stmt.executeQuery(listSql);
			}
			while (rs.next()) {
				sb.append(
					rs.getString("OP_CODE")
						+ "-"
						+ rs.getString("OP_NAME")
						+ "\t");
			}
		} finally {
			if (rs != null)
				rs.close();
			if (operator_byrole_stmt != null)
				operator_byrole_stmt.close();
			if (alloperator_stmt != null)
				alloperator_stmt.close();
			if (conn != null)
				conn.close();
		}
		return sb.toString();
	}

	//销售人员
	public static String getSaleManOptions(int inputInt) throws Exception {
		String listSql = "{call SP_QUERY_SALEMAN()}";

		StringBuffer sb = new StringBuffer(200);
		if (inputInt == 9001)
			sb.append("-" + "全部" + "\t");
		else if (inputInt == 9002)
			sb.append("0" + "-" + "全部" + "\t");

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = CrmDBManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(listSql);

			while (rs.next()) {
				sb.append(
					rs.getString("OP_CODE")
						+ "-"
						+ rs.getString("OP_NAME")
						+ "\t");
			}
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
	//基金经理
	public static String getManagerOptions(int inputInt, Integer book_code)
		throws Exception {
		String listSql = "{call SP_QUERY_FUNDMANAGER(?,0)}";

		StringBuffer sb = new StringBuffer(200);
		if (inputInt == 9004)
			sb.append("-" + "全部" + "\t");
		else if (inputInt == 9005)
			sb.append("0" + "-" + "全部" + "\t");

		Connection conn = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = CrmDBManager.getConnection();
			stmt =
				conn.prepareCall(
					listSql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			stmt.setInt(1, book_code.intValue());
			rs = stmt.executeQuery();

			while (rs.next()) {
				sb.append(
					rs.getString("MAN_ID")
						+ "-"
						+ rs.getString("MAN_NAME")
						+ "\t");
			}
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

	//营业部
	public static String getSecuDepartOptions(int inputInt) throws Exception {
		String listSql = "{call SP_QUERY_TSECUDEPART(1,0)}";

		StringBuffer sb = new StringBuffer(200);
		if (inputInt == 9007)
			sb.append("-" + "全部" + "\t");
		else if (inputInt == 9008)
			sb.append("0" + "-" + "全部" + "\t");

		Connection conn = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = CrmDBManager.getConnection();
			stmt =
				conn.prepareCall(
					listSql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			rs = stmt.executeQuery();

			while (rs.next()) {
				sb.append(
					rs.getString("DEPART_CODE")
						+ "-"
						+ rs.getString("DEPART_NAME")
						+ "\t");
			}
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

	//--------------------------根据预定义得到参数名称:INI----------------------

	public static String getIniParamOptions(int type_id) throws Exception {
		StringBuffer sb = new StringBuffer(200);
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt =
			conn.prepareCall(
				"{call SP_QUERY_TINIPARAM(?,NULL)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, type_id);
		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				sb.append(
					rslist.getString("TYPE_VALUE")
						+ "-"
						+ rslist.getString("CONTENT")
						+ "\t");
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	//-------------------------------------------------------------------- 
	//------------------------------字典表中定义的参数---------------------
	//根据参数值得到参数名称:列表
	public static String getDictParamOptions(int dict) throws Exception {
		StringBuffer sb = new StringBuffer(200);
		sb.append("0" + "-" + "全部" + "\t");
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt =
			conn.prepareCall(
				"{call SP_QUERY_TDICTPARAM(?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, dict);
		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next())
				sb.append(
					rslist.getString("TYPE_VALUE")
						+ "-"
						+ rslist.getString("TYPE_CONTENT")
						+ "\t");
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}
	//--------------------------------------------------------------------
	
//	正常期产品
	   // 110201 不成立
	   // 110202 推介期
	   // 110203 正常期
	   // 110204 产品终止
	   public static String getProductCodeOptions(int inputInt,Integer book_code, Integer op_code) throws Exception
	   {
		   String listSql = "{call SP_QUERY_TPRODUCT (?,?,?,?,?,?,?)}";

		   StringBuffer sb = new StringBuffer(200);
		   if(inputInt==6001)
				sb.append("-"+"全部"+"\t");
		   else if(inputInt==6002)
				sb.append("0"+"-"+"全部"+"\t");

		   Connection conn = null;
		   CallableStatement stmt = null;
		   ResultSet rs = null;
		   try
		   {
			 	conn = IntrustDBManager.getConnection();
			 	stmt = conn.prepareCall(listSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		  	 	stmt.setInt(1, book_code.intValue());
		   	 	stmt.setString(2, "");
			   	stmt.setInt(3, 0);
			   	stmt.setString(4, "");
		   		stmt.setString(5, "");
		   		stmt.setString(6, "");
		   		if (op_code == null)
			   		stmt.setInt(7, 0);
		   		else
			   		stmt.setInt(7, op_code.intValue());
		   	 	rs = stmt.executeQuery();

			   while (rs.next())
			   {
					   sb.append(rs.getString("PRODUCT_CODE")+"-"+rs.getString("PRODUCT_NAME")+"\t");
			   }
		   }
		   finally
		   {
				if(rs!=null) rs.close();
				if(stmt!=null)	stmt.close();
				if(conn!=null)	conn.close();
		   }
		   return sb.toString();
	   }
	   
	   public static String getProductIdOptions(int inputInt,Integer book_code, Integer op_code) throws Exception
	   {
		   String listSql = "{call SP_QUERY_TPRODUCT (?,?,?,?,?,?,?)}";

		   StringBuffer sb = new StringBuffer(200);
		   if(inputInt==6004)
				sb.append("-"+"全部"+"\t");
		   else if(inputInt==6005)
				sb.append("0"+"-"+"全部"+"\t");

		   Connection conn = null;
		   CallableStatement stmt = null;
		   ResultSet rs = null;
		   try
		   {
				conn = IntrustDBManager.getConnection();
				stmt = conn.prepareCall(listSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

				stmt.setInt(1, book_code.intValue());
				stmt.setString(2, "");
				stmt.setInt(3, 0);
				stmt.setString(4, "");
				stmt.setString(5, "");
				stmt.setString(6, "");
				if (op_code == null)
					stmt.setInt(7, 0);
				else
					stmt.setInt(7, op_code.intValue());
				rs = stmt.executeQuery();

			   while (rs.next())
			   {
					   sb.append(rs.getString("PRODUCT_ID")+"-("+ rs.getString("PRODUCT_CODE") +")"+rs.getString("PRODUCT_NAME")+"\t");
			   }
		   }
		   finally
		   {
				if(rs!=null) rs.close();
				if(stmt!=null)	stmt.close();
				if(conn!=null)	conn.close();
		   }
		   return sb.toString();
	   }
	   
//	 分帐套产品列表---单一：6010~6012，集合6013~6015
	   public static String getProductCodeOptions4(int inputInt,Integer book_code, Integer op_code) throws Exception
	   {
		   String listSql = "{call SP_QUERY_TPRODUCT (?,?,?,?,?,?,?)}";

		   StringBuffer sb = new StringBuffer(200);
		   if(inputInt==6011||inputInt==6014)
				sb.append("-"+"全部"+"\t");
		   else if(inputInt==6012||inputInt==6015)
				sb.append("0"+"-"+"全部"+"\t");

		   Connection conn = null;
		   CallableStatement stmt = null;
		   ResultSet rs = null;
		   try
		   {
			 	conn = IntrustDBManager.getConnection();
			 	stmt = conn.prepareCall(listSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		  	 	stmt.setInt(1, book_code.intValue());
		   	 	stmt.setString(2, "");
			   	stmt.setInt(3, 0);
			   	stmt.setString(4, "");
		   		stmt.setString(5, "");
		   		stmt.setString(6, "");
		   		if (op_code == null)
			   		stmt.setInt(7, 0);
		   		else
			   		stmt.setInt(7, op_code.intValue());
		   	 	rs = stmt.executeQuery();

		   	   String value = "";

			   while (rs.next())
			   {
					  value = rs.getString("ITEM_CODE").substring(1,2);
					  if(inputInt>=6010&&inputInt<=6012&&value.equals("1"))
					   	sb.append(rs.getString("PRODUCT_CODE")+"-"+rs.getString("PRODUCT_NAME")+"\t");
					  else if(inputInt>=6013&&inputInt<=6015&&value.equals("2"))
					   	sb.append(rs.getString("PRODUCT_CODE")+"-"+rs.getString("PRODUCT_NAME")+"\t");
			   }
		   }
		   finally
		   {
				if(rs!=null) rs.close();
				if(stmt!=null)	stmt.close();
				if(conn!=null)	conn.close();
		   }
		   return sb.toString();
	   }
//	 不分帐套的产品列表！
	   public static String getProductCodeOptions2(int inputInt,Integer book_code,Integer op_code) throws Exception
	   {
			/*String listSql = "SELECT PRODUCT_ID, PRODUCT_NAME FROM TPRODUCT WHERE BOOK_CODE = "+book_code.intValue();
				*
				*/
		   String listSql = "{call SP_QUERY_TPRODUCT_LIST (?,?,?,?)}";

		   StringBuffer sb = new StringBuffer(200);
		   if(inputInt==6031)
				sb.append("-"+"全部"+"\t");
		   else if(inputInt==6032)
				sb.append("0"+"-"+"全部"+"\t");

			Connection conn = null;
			CallableStatement stmt = null;
			ResultSet rs = null;
			try
			{
					conn = IntrustDBManager.getConnection();
					stmt = conn.prepareCall(listSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

					stmt.setInt(1, book_code.intValue());
					stmt.setString(2, "");
					stmt.setInt(3, 0);

					if (op_code == null)
						stmt.setInt(4, 0);
					else
						stmt.setInt(4, op_code.intValue());
					rs = stmt.executeQuery();

				   while (rs.next())
				   {
					sb.append(rs.getString("PRODUCT_CODE")+"-"+rs.getString("PRODUCT_NAME")+"\t");
				   }
			 }
			 finally
			 {
					if(rs!=null) rs.close();
					if(stmt!=null)	stmt.close();
					if(conn!=null)	conn.close();
			}
		    return sb.toString();
	   }
	   
	   public static String getProductIdOptions2(int inputInt,Integer book_code,Integer op_code) throws Exception
	   {
			/*String listSql = "SELECT PRODUCT_ID, PRODUCT_NAME FROM TPRODUCT WHERE BOOK_CODE = "+book_code.intValue();
		 	*
		 	*/
		   String listSql = "{call SP_QUERY_TPRODUCT_LIST (?,?,?,?)}";

		   StringBuffer sb = new StringBuffer(200);
		   if(inputInt==6034)
				sb.append("-"+"全部"+"\t");
		   else if(inputInt==6035)
				sb.append("0"+"-"+"全部"+"\t");

			Connection conn = null;
			CallableStatement stmt = null;
			ResultSet rs = null;
			try
			{
				conn = IntrustDBManager.getConnection();
				stmt = conn.prepareCall(listSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

				stmt.setInt(1, book_code.intValue());
				stmt.setString(2, "");
				stmt.setInt(3, 0);

				if (op_code == null)
					stmt.setInt(4, 0);
				else
					stmt.setInt(4, op_code.intValue());
				rs = stmt.executeQuery();

				while (rs.next())
				{
					sb.append(rs.getString("PRODUCT_ID")+"-"+rs.getString("PRODUCT_NAME")+"\t");
				}
			 }
			finally
			{
				if(rs!=null) rs.close();
				if(stmt!=null)	stmt.close();
				if(conn!=null)	conn.close();
			}
		   return sb.toString();
	   }
	   
//	 有证券业务的产品列表！
	   public static String getProductCodeOptions3(int inputInt,Integer book_code,Integer op_code) throws Exception
	   {
		   /*String listSql = "SELECT PRODUCT_CODE, PRODUCT_NAME FROM TPRODUCT WHERE BOOK_CODE = "+book_code.intValue();
			      listSql = listSql+ " AND PRODUCT_ID IN ( SELECT PRODUCT_ID FROM TFUNDACCT WHERE DEPART_CODE <> 0 ) ORDER BY PRODUCT_CODE";
		   */
		   String listSql = "{call SP_QUERY_TPRODUCT_LIST (?,?,?,?)}";

		   StringBuffer sb = new StringBuffer(200);
		   if(inputInt==6061)
				sb.append("-"+"全部"+"\t");
		   else if(inputInt==6062)
				sb.append("0"+"-"+"全部"+"\t");

		   Connection conn = null;
		   CallableStatement stmt = null;
		   ResultSet rs = null;
		   try
		   {
				conn = IntrustDBManager.getConnection();
				stmt = conn.prepareCall(listSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

				stmt.setInt(1, book_code.intValue());
				stmt.setString(2, "");
				stmt.setInt(3, 27);

				if (op_code == null)
					stmt.setInt(4, 0);
				else
					stmt.setInt(4, op_code.intValue());
				rs = stmt.executeQuery();
			   while (rs.next())
			   {
					   sb.append(rs.getString("PRODUCT_CODE")+"-"+rs.getString("PRODUCT_NAME")+"\t");
			   }
		   }
		   finally
		   {
				if(rs!=null) rs.close();
				if(stmt!=null)	stmt.close();
				if(conn!=null)	conn.close();
		   }
		   return sb.toString();
	   }
	   
	   public static String getProductIdOptions3(int inputInt,Integer book_code,Integer op_code) throws Exception
	   {
		   /*String listSql = "SELECT PRODUCT_ID, PRODUCT_NAME FROM TPRODUCT WHERE BOOK_CODE = "+book_code.intValue();
			   	  listSql = listSql+ " AND PRODUCT_ID IN ( SELECT PRODUCT_ID FROM TFUNDACCT WHERE DEPART_CODE <> 0 ) ORDER BY PRODUCT_ID";
		   */
		   String listSql = "{call SP_QUERY_TPRODUCT_LIST (?,?,?,?)}";
		   StringBuffer sb = new StringBuffer(200);
		   if(inputInt==6064)
				sb.append("-"+"全部"+"\t");
		   else if(inputInt==6065)
				sb.append("0"+"-"+"全部"+"\t");

			Connection conn = null;
			CallableStatement stmt = null;
			ResultSet rs = null;
			try
			{
					conn = IntrustDBManager.getConnection();
					stmt = conn.prepareCall(listSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

					stmt.setInt(1, book_code.intValue());
					stmt.setString(2, "");
					stmt.setInt(3, 27);

					if (op_code == null)
						stmt.setInt(4, 0);
					else
						stmt.setInt(4, op_code.intValue());
					rs = stmt.executeQuery();
				   while (rs.next())
				   {
						sb.append(rs.getString("PRODUCT_ID")+"-"+rs.getString("PRODUCT_NAME")+"\t");
				   }
			   }
			   finally
			   {
					if(rs!=null) rs.close();
					if(stmt!=null)	stmt.close();
					if(conn!=null)	conn.close();
		   }
		   return sb.toString();
	   }

}
