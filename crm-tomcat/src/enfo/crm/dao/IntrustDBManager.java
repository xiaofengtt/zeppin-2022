package enfo.crm.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntrustDBManager extends IntrustJdbcTemplate {
	public final static int ERROR_SUCCESS = 100;
	
	public static ResultSet executeQuery(String sql) throws Exception {
		Statement stmt = getConnection().createStatement();
		return stmt.executeQuery(sql);
	}

	public static void handleResultCode(int errorCode) throws BusiException {
		if (errorCode > -1)
			return;
		throw new BusiException(BusiException.getErrorMsg(errorCode));
	}

	public static boolean existRecord(
		String table,
		String field,
		Object value,
		Integer book_code)
		throws Exception {
		String sql = "SELECT * FROM " + table + " WHERE " + field + "=?";
		if (book_code != null && book_code.intValue() != 0)
			sql = sql + " AND BOOK_CODE=" + book_code;
		Connection conn = getConnection();
		ResultSet r;
		try {
			CallableStatement stmt =
				conn.prepareCall(
					sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.setObject(1, value);
			r = stmt.executeQuery();
			return r.next();
		} finally {
			conn.close();
		}
	}

//	public static void main(String[] args) {
//		
//		try {
//			getDebugConnection();  
//			Utility.debugln("----");
//		} catch (Exception e) {
//			 
//			e.printStackTrace();
//		}


	//}

	/**
	 * 查询全部的操作.
	 * @param procSql 存储过程
	 * @param params 参数
	 * @throws BusiException
	 */
	public static List listProcAll(String procSql, Object[] params)
		throws BusiException {
		final Object[] params_final = params;

		return (List) execute(procSql, new CallableStatementCallback() {
			public Object doInCallableStatement(CallableStatement cs)
				throws SQLException {
				List resultList = new ArrayList(); //结果集缓存	

				if (params_final != null) {
					for (int i = 0; i < params_final.length; i++) {
						cs.setObject(i + 1, params_final[i]);				
					}
				}
				ResultSet rs = cs.executeQuery();
				//缓存结果集
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				while (rs.next()) {
					Map rowMap = new HashMap(columnCount);
					for (int i = 1; i <= columnCount; i++) {
						switch (rsmd.getColumnType(i)) {
							case java.sql.Types.TIMESTAMP :
								rowMap.put(
									rsmd.getColumnName(i),
									rs.getTimestamp(i));
								break;
							case java.sql.Types.CLOB :
								rowMap.put(
									rsmd.getColumnName(i),
									rs.getString(i));
								break;
							default :
								rowMap.put(
									rsmd.getColumnName(i),
									rs.getObject(i));
						}
					}
					resultList.add(rowMap);
				}
				//关闭连接
				rs.close();
				return resultList;
			}
		});
	}

	/**
	 * 查询全部的操作
	 * @param procSql 存储过程
	 * @param params 参数
	 */
	public static IPageList listProcPage(
		String procSql,
		Object[] params,
		int pageIndex,
		int pageSize)
		throws BusiException {
		final Object[] params_final = params;

		final int pageIndex_final = pageIndex;
		final int pageSize_final = pageSize;

		return (IPageList) execute(procSql, new CallableStatementCallback() {
			public Object doInCallableStatement(CallableStatement cs)
				throws SQLException {
				IPageList pageList = new JdbcPageList(); //结果集缓存	
				pageList.setPageIndex(pageIndex_final);
				pageList.setPageSize(pageSize_final);

				for (int i = 0; i < params_final.length; i++) //循环参数赋值
					{
					cs.setObject(i + 1, params_final[i]);
				}

				ResultSet rs = cs.executeQuery();
				//缓存结果集
				pageList.populate(rs);
				//关闭连接
				rs.close();
				return pageList;
			}
		});
	}

	/**
	 * 查询全部的操作
	 * @param procSql 存储过程
	 * @param params 参数
	 * @param totalColumn 需要合计的字段数组
	 * @param pageIndex 当前页码
	 * @param pageSize 页面显示大小
	 */
	public static IPageList listProcPage(
		String procSql,
		Object[] params,
		String[] totalColumn,
		int pageIndex,
		int pageSize)
		throws BusiException {
		final Object[] params_final = params;
		final String[] totalColumn_final = totalColumn;

		final int pageIndex_final = pageIndex;
		final int pageSize_final = pageSize;

		return (IPageList) execute(procSql, new CallableStatementCallback() {
			public Object doInCallableStatement(CallableStatement cs)
				throws SQLException {
				IPageList pageList = new JdbcPageList(); //结果集缓存	
				pageList.setTotalColumn(totalColumn_final);
				pageList.setPageIndex(pageIndex_final);
				pageList.setPageSize(pageSize_final);

				for (int i = 0; i < params_final.length; i++) //循环参数赋值
					{
					cs.setObject(i + 1, params_final[i]);
				}

				ResultSet rs = cs.executeQuery();
				//缓存结果集
				pageList.populate(rs);
				//关闭连接
				rs.close();
				return pageList;
			}
		});
	}

	/**
	 * 增删改的存储过程操作,存储过程有一个整型的输出参数值.
	 * 
	 * @param params 参数
	 * @param procSql 存储过程
	 * 
	 * @throws BusiException
	 */
	public static void cudProc(String procSql, Object[] params)
		throws BusiException {
		Integer errorCode = new Integer(0); //错误代码

		final Object[] params_final = params;

		errorCode =
			(Integer) execute(procSql, new CallableStatementCallback() {
			public Object doInCallableStatement(CallableStatement cs)
				throws SQLException {
				cs.registerOutParameter(1, java.sql.Types.INTEGER);
				for (int i = 0; i < params_final.length; i++) //循环参数赋值
					{
					cs.setObject(i + 2, params_final[i]);
				}
				cs.execute();
				return new Integer(cs.getInt(1)); //存储过程返回值（错误代码）		
			}
		});	
		//根据错误代码局处理存储过程返回的业务异常
		handleResultCode(errorCode.intValue());
	}
	
	/**
	 * 增删改的存储过程操作,存储过程有一个整型的输出参数值.
	 * 
	 * @param params 参数
	 * @param procSql 存储过程
	 * @ 与上相同 扩展
	 * 
	 * @throws BusiException
	 */
	public static Integer execProc(String procSql, Object[] params)
		throws BusiException {
		Integer errorCode = new Integer(0); //错误代码

		final Object[] params_final = params;

		errorCode =
			(Integer) execute(procSql, new CallableStatementCallback() {
			public Object doInCallableStatement(CallableStatement cs)
				throws SQLException {
				cs.registerOutParameter(1, java.sql.Types.INTEGER);
				for (int i = 0; i < params_final.length; i++) //循环参数赋值
					{
					cs.setObject(i + 2, params_final[i]);
				}
				cs.execute();
				return new Integer(cs.getInt(1)); //存储过程返回值（错误代码）		
			}
		});	
		return errorCode ;
	}
	

	/**
	 * 增删改的存储过程操作.
	 * 
	 * @param params 参数
	 * @param procSql 存储过程
	 */
	public static void cudProcNoRet(String procSql, Object[] params)
		throws BusiException {
		final Object[] params_final = params;

		execute(procSql, new CallableStatementCallback() {
			public Object doInCallableStatement(CallableStatement cs)
				throws SQLException {
				cs.registerOutParameter(1, java.sql.Types.INTEGER);
				for (int i = 0; i < params_final.length; i++) //循环参数赋值
					{
					cs.setObject(i + 2, params_final[i]);
				}
				cs.execute();
				return null;
			}
		});
	}

	/**
	 * 
	 * 有输出参数的存储过程调用
	 * 
	 * @param params 参数
	 * @param procSql 存储过程
	 * @param outParamPos 输出参数的位置
	 * @param outParamType 输出参数的类型
	 * @return 输出参数
	 * @throws BusiException
	 */
	public static Object cudProc(
		String procSql,
		Object[] params,
		int outParamPos,
		int outParamType)
		throws BusiException {
		int errorCode = 0;
		Object ret = null;
		Connection conn = null;
		CallableStatement stmt = null;
		//Utility.debugln("procSql:="+procSql);
		try {
			conn = IntrustDBManager.getConnection();
			stmt = conn.prepareCall(procSql);
			stmt.registerOutParameter(1, java.sql.Types.INTEGER);
			int trueIndex = 0;
			int tempOutParamPos = outParamPos - 2;
			for (int i = 0; i < params.length; i++) {
				trueIndex = i + 2;

				if (i >= tempOutParamPos) //由于有一个输出参数存在，后面实际的输入参数的位置将向后调1
					trueIndex++;
				if (params[i] == null)
					//NULL值是无类型的，但是用Object传递无效，所以把它作为String参数
					stmt.setString(trueIndex, null);
				else
					stmt.setObject(trueIndex, params[i]);
			}

			stmt.registerOutParameter(outParamPos, outParamType);

			stmt.executeUpdate();

			errorCode = stmt.getInt(1);

			ret = stmt.getObject(outParamPos);
			stmt.close();
			conn.close();
		} catch (Exception e) {
			throw new BusiException("数据库操作异常！", e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new BusiException("关闭数据库连接异常！");
				}

			}
			if (conn != null) {
				try {
					if (!conn.isClosed())
						conn.close();
				} catch (SQLException e) {
					throw new BusiException("关闭数据库连接异常！");
				}
			}
		}
		//根据错误代码局处理存储过程返回的业务异常
		handleResultCode(errorCode);
		return ret;
	}

	/**
	 * 
	 * 有输出参数的存储过程调用
	 * 
	 * @param params 参数
	 * @param procSql 存储过程
	 * @param outParamPos 输出参数的位置
	 * @param outParamType 输出参数的类型
	 * @return 输出参数
	 * @throws BusiException
	 * 
	 * 2009-12-08 taochen&lzd
	 * 增加判断 输入参数后移的位置是否被输出参数占掉，如果占掉，+1
	 */
	public static Object[] cudProc(
		String procSql,
		Object[] params,
		int[] outParamPos,
		int[] outParamType)
		throws BusiException {
		int errorCode = 0;
		Object[] ret = new Object[outParamPos.length];
		Connection conn = null;
		CallableStatement stmt = null;
		//Utility.debugln("delete_deleteSql:="+procSql);
		try {
			conn = IntrustDBManager.getConnection();
			stmt = conn.prepareCall(procSql);
			stmt.registerOutParameter(1, java.sql.Types.INTEGER);
			int trueIndex;

			for (int i = 0; i < params.length; i++) {
				trueIndex = i + 2;
				for (int j = 0;
					j < outParamPos.length;
					j++) //由于有多个输出参数存在，后面实际的输入参数的位置将向后调N
					{
					int tempOutParamPos = outParamPos[j] - 2;
					if (i >= tempOutParamPos||i+j==tempOutParamPos)
						trueIndex++;
				}

				stmt.setObject(trueIndex, params[i]);
			}
			//注册输出参数
			for (int j = 0; j < outParamPos.length; j++) {
				stmt.registerOutParameter(outParamPos[j], outParamType[j]);
			}

			stmt.executeUpdate();

			errorCode = stmt.getInt(1);
			//获得输出参数
			for (int j = 0; j < outParamPos.length; j++) {
				ret[j] = stmt.getObject(outParamPos[j]);
			}
			stmt.close();
			conn.close();
		} catch (Exception e) {
			throw new BusiException("数据库操作异常！", e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new BusiException("关闭数据库连接异常！");
				}

			}
			if (conn != null) {
				try {
					if (!conn.isClosed())
						conn.close();
				} catch (SQLException e) {
					throw new BusiException("关闭数据库连接异常！");
				}
			}
		}
		//根据错误代码局处理存储过程返回的业务异常
		handleResultCode(errorCode);
		return ret;
	}

	/**
	 * 直接执行更新操作的SQL语句.
	 * @param updateSql 需要执行的SQL语句
	 */
	public static void executeSql(String updateSql) throws BusiException {
		execute(updateSql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement ps)
				throws SQLException {
				ps.execute();
				return null;
			}
		});
	}

	/**
	 * 
	 * 直接执行SQL更新操作的方法
	 * 
	 * @param updateSql 需要执行的SQL语句
	 * @param params	参数
	 * @throws BusiException
	 */
	public static void executeSql(String updateSql, Object[] params)
		throws BusiException {
		final Object[] params_final = params;

		execute(updateSql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement ps)
				throws SQLException {
				if (params_final != null) {
					for (int i = 0; i < params_final.length; i++) {
						ps.setObject(i + 1, params_final[i]);
					}
				}
				ps.execute();
				return null;
			}
		});
	}

	/**
	 * 批量执行更新操作的SQL语句,同一个事务中
	 * @param sql 更新的SQL数组
	 */
	public static void batchUpdate(String[] sqls) throws BusiException {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = IntrustDBManager.getConnection();
			for (int i = 0; i < sqls.length; i++) {
				stmt = conn.createStatement();
				stmt.addBatch(sqls[i]);
			}
			stmt.executeBatch();

			//关闭连接			
			stmt.close();
			conn.close();

		} catch (Exception e) {
			throw new BusiException("数据库操作异常！", e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new BusiException("关闭数据库连接异常！");
				}

			}
			if (conn != null) {
				try {
					if (!conn.isClosed())
						conn.close();
				} catch (SQLException e) {
					throw new BusiException("关闭数据库连接异常！");
				}
			}
		}
	}

	/**
	 * 直接通过SQL语句进行查询操作.
	 * 
	 * @param listSql 查询SQL语句
	 * @return List
	 */
	public static List listBySql(String listSql) throws BusiException {
		return (List) execute(listSql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement ps)
				throws SQLException {
				List resultList = new ArrayList(); //结果集缓存	
				ResultSet rs = ps.executeQuery();
				//缓存结果集
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				while (rs.next()) {
					Map rowMap = new HashMap(columnCount);
					for (int i = 1; i <= columnCount; i++) {
						switch (rsmd.getColumnType(i)) {
							case java.sql.Types.TIMESTAMP :
								rowMap.put(
									rsmd.getColumnName(i),
									rs.getTimestamp(i));
								break;
							case java.sql.Types.CLOB :
								rowMap.put(
									rsmd.getColumnName(i),
									rs.getString(i));
								break;
							default :
								rowMap.put(
									rsmd.getColumnName(i),
									rs.getObject(i));
						}
					}
					resultList.add(rowMap);
				}
				//关闭连接
				rs.close();
				return resultList;
			}
		});
	}

	/**
	 * 
	 * 直接通过SQL语句进行查询操作
	 * 
	 * @param listSql
	 * @param params
	 * @return
	 * @throws BusiException
	 */
	public static List listBySql(String listSql, Object[] params)
		throws BusiException {
		final Object[] params_final = params;

		return (List) execute(listSql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement ps)
				throws SQLException {
				List resultList = new ArrayList(); //结果集缓存	

				if (params_final != null) {
					for (int i = 0; i < params_final.length; i++) {
						ps.setObject(i + 1, params_final[i]);
					}
				}
				ResultSet rs = ps.executeQuery();
				//缓存结果集
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				while (rs.next()) {
					Map rowMap = new HashMap(columnCount);
					for (int i = 1; i <= columnCount; i++) {
						switch (rsmd.getColumnType(i)) {
							case java.sql.Types.TIMESTAMP :
								rowMap.put(
									rsmd.getColumnName(i),
									rs.getTimestamp(i));
								break;
							case java.sql.Types.CLOB :
								rowMap.put(
									rsmd.getColumnName(i),
									rs.getString(i));
								break;
							case java.sql.Types.BOOLEAN :
								rowMap.put(
										rsmd.getColumnName(i),
										new Boolean(rs.getBoolean(i)));
									break;
							default :
								rowMap.put(
									rsmd.getColumnName(i),
									rs.getObject(i));
						}
					}
					resultList.add(rowMap);
				}
				//关闭连接
				rs.close();
				return resultList;
			}
		});
	}

	/**
	 * JDBC分页查询.
	 * @param pageIndex 页码
	 * @param listSql 查询SQL语句
	 * @param pageSize 每页显示数
	 * @return
	 */
	public static IPageList listSqlPage(
		String listSql,
		int pageIndex,
		int pageSize)
		throws BusiException {
		final int pageIndex_final = pageIndex;
		final int pageSize_final = pageSize;

		return (IPageList) execute(listSql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement ps)
				throws SQLException {
				IPageList pageList = new JdbcPageList(); //结果集缓存	
				pageList.setPageIndex(pageIndex_final);
				pageList.setPageSize(pageSize_final);

				ResultSet rs = ps.executeQuery();
				//缓存结果集
				pageList.populate(rs);
				//关闭连接
				rs.close();
				return pageList;
			}
		});
	}

	/**
	 * 根据SQL语句查询记录数.
	 * 
	 * @param listCountSql 查询记录数的SQL语句
	 * @return count
	 */
	public static int listSqlCount(String listCountSql) throws BusiException {
		Integer count = new Integer(0);

		count =
			(Integer) execute(listCountSql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement ps)
				throws SQLException {
				int count = 0;
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					count = rs.getInt(1);
				}
				//关闭连接
				rs.close();
				return new Integer(count);
			}
		});
		return count.intValue();
	}
}
