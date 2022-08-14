package enfo.crm.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import enfo.crm.dao.supper.RemoveAware;

/**
 * 
 * <b>标题:</b> 无状态会话Bean超类.
 * <pre><b>描述:</b> 
 *   封装底层ejb实现和数据库操作实现
 * </pre>
 * 
 * @author <a href="mailto:littcai@hotmail.com">空心大白菜</a>
 * @since 2007-8-10
 * @version 1.0
 *
 */
public class IntrustBusiExBean extends RemoveAware{
	
	/**
	 * 直接执行更新操作的SQL语句.
	 * @param updateSql 需要执行的SQL语句
	 */
	public void executeSql(String updateSql) throws BusiException {
		IntrustDBManager.executeSql(updateSql);
	}

	/**
	 * 
	 * 直接执行SQL更新操作的方法
	 * 
	 * @param updateSql
	 * @param params
	 * @throws BusiException
	 */
	public void executeSql(String updateSql, Object[] params)
		throws BusiException {
		IntrustDBManager.executeSql(updateSql, params);
	}

	/**
	 * 批量执行更新操作的SQL语句,同一个事务中
	 * @param sql 更新的SQL数组
	 */
	public void batchUpdate(String[] sqls) throws BusiException {
		IntrustDBManager.batchUpdate(sqls);
	}

	/**
	 * 增删改的存储过程操作,存储过程有一个整型的输出参数值.
	 * 
	 * @param params 参数
	 * @param procSql 存储过程
	 */
	public void cudProc(String procSql, Object[] params) throws BusiException {
		IntrustDBManager.cudProc(procSql, params);
	}


	/**
	 * 增删改的存储过程操作,存储过程有一个整型的输出参数值.
	 * 
	 * @param params 参数
	 * @param procSql 存储过程
	 */
	public Integer execProc(String procSql, Object[] params) throws BusiException {
		return IntrustDBManager.execProc(procSql, params);
	}
	
	/**
	 * 增删改的存储过程操作.
	 * 
	 * @param params 参数
	 * @param procSql 存储过程
	 */
	public void cudProcNoRet(String procSql, Object[] params)
		throws BusiException {
		IntrustDBManager.cudProcNoRet(procSql, params);
	}

	/**
	 * 
	 * 有输出参数的存储过程调用
	 * 
	 * @param procSql
	 * @param params
	 * @param outParamPos
	 * @param outParamType
	 * @return
	 * @throws BusiException
	 */
	public Object cudProc(
		String procSql,
		Object[] params,
		int outParamPos,
		int outParamType)
		throws BusiException {
		return IntrustDBManager.cudProc(procSql, params, outParamPos, outParamType);
	}

	/**
	 * 
	 * 有输出参数的存储过程调用
	 * 
	 * @param procSql
	 * @param params
	 * @param outParamPos
	 * @param outParamType
	 * @return
	 * @throws BusiException
	 */
	public Object[] cudProc(
		String procSql,
		Object[] params,
		int[] outParamPos,
		int[] outParamType)
		throws BusiException {
		return IntrustDBManager.cudProc(procSql, params, outParamPos, outParamType);
	}

	/**
	 * 查询全部的操作
	 * @param procSql 存储过程
	 * @param params 参数
	 */
	public List listProcAll(String procSql, Object[] params)
		throws BusiException {
		return IntrustDBManager.listProcAll(procSql, params);

	}

	/**
	 * 分页查询的操作
	 * @param procSql 存储过程
	 * @param params 参数
	 */
	public IPageList listProcPage(
		String procSql,
		Object[] params,
		int pageIndex,
		int pageSize)
		throws BusiException {
		return IntrustDBManager.listProcPage(procSql, params, pageIndex, pageSize);
	}

	/**
	 * 分页查询的操作
	 * @param procSql 存储过程
	 * @param params 参数
	 * @param totalColumn 需要合计的字段数组 
	 * 
	 *  
	 * add by tsg 2009-06-05(警告！！！)
	 *  注：totalColumn的数组值是存储过程返回结果集的列名，如果函数无统计列，将totalColumn=null 
	 *                 而不可赋值totalColumn={""},否则页面抛出［数据库操作异常！ - Invalid column name .］
	 */
	public IPageList listProcPage(
		String procSql,
		Object[] params,
		String[] totalColumn,
		int pageIndex,
		int pageSize)
		throws BusiException {
		return IntrustDBManager.listProcPage(
			procSql,
			params,
			totalColumn,
			pageIndex,
			pageSize);
	}

	/**
	 * 直接通过SQL语句进行查询操作.
	 * 
	 * @param listSql 查询SQL语句
	 * @return List
	 */
	public List listBySql(String listSql) throws BusiException {
		return IntrustDBManager.listBySql(listSql);
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
	public List listBySql(String listSql, Object[] params)
		throws BusiException {
		return IntrustDBManager.listBySql(listSql, params);
	}

	/**
	 * JDBC分页查询.
	 * @param pageIndex 页码
	 * @param listSql 查询SQL语句
	 * @param pageSize 每页显示数
	 * @return
	 */
	public IPageList listSqlPage(String listSql, int pageIndex, int pageSize)
		throws BusiException {
		return IntrustDBManager.listSqlPage(listSql, pageIndex, pageSize);
	}

	/**
	 * 根据SQL语句查询记录数.
	 * @param listCountSql 查询记录数的SQL语句
	 * @return
	 */
	public int listSqlCount(String listCountSql) throws BusiException {
		return IntrustDBManager.listSqlCount(listCountSql);
	}
	
	/**
	 * 更新的封装
	 * 
	 * @param updateSql
	 *            存储过程
	 * @param params
	 *            参数数组
	 * @throws Exception
	 */
	//hesl 2011.4.29
	public void update(String updateSql, Object[] params) throws Exception {
		this.cud(updateSql, params);
	}
	
	/**
	 * 增删改的封装
	 * 
	 * @param cudSQl
	 *            存储过程
	 * @param params
	 *            参数数组
	 * @throws Exception
	 */
	//hesl 2011.4.29
	public void cud(String cudSl, Object[] params) throws Exception {
		int errorCode = 0;
		Connection conn = null;
		CallableStatement stmt = null;
		try {
			conn = IntrustDBManager.getConnection();
			stmt = conn.prepareCall(cudSl);
			stmt.registerOutParameter(1, java.sql.Types.INTEGER);
			for (int i = 0; i < params.length; i++) {
				if (params[i] == null)
					//NULL值是无类型的，但是用Object传递无效，所以把它作为String参数
					stmt.setString(i + 2, null);
				else
					stmt.setObject(i + 2, params[i]);
			}
			stmt.executeUpdate();

			errorCode = stmt.getInt(1);
			stmt.close();
			conn.close();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {

			}
			try {
				if (conn != null && !conn.isClosed())
					conn.close();
			} catch (SQLException e) {

			}
		}
		if (errorCode < 0) //只有有错误的时候才捕获
			IntrustDBManager.handleResultCode(errorCode);
	}

	@Override
	public void remove() {
	}
}
