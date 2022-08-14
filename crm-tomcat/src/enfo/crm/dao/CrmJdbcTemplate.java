package enfo.crm.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;

import enfo.crm.dao.supper.DbKit;
import enfo.crm.tools.ApplicationContextUtil;

public class CrmJdbcTemplate {
	
	private static ApplicationContext context;

	static{
		context = ApplicationContextUtil.getApplicationContext();
	}
	
	/**
	 * 
	 * 获得可用的数据库连接
	 * 
	 * @return
	 * @throws Exception
	 */
	public synchronized static Connection getConnection() throws Exception {
		DataSource ds = (DataSource) context.getBean("crmDataSource");
		return DbKit.get(ds);
	}
	
	/**
	 * 关闭数据库连接
	 * 
	 * @throws SQLException
	 */
	public final static void closeConnection() throws SQLException {
		DbKit.close();
	}
	
	/**
	 * 关闭ResultSet
	 * 
	 * @param rs
	 * @throws SQLException
	 */
	public final static void closeResultSet(ResultSet rs) throws SQLException {
		if (rs != null)
			rs.close();
	}

	/**
	 * 关闭CallableStatement
	 * 
	 * @param stmt
	 * @throws SQLException
	 */
	public final static void closeCallableStatement(CallableStatement stmt)
			throws SQLException {
		if (stmt != null)
			stmt.close();
	}

	/**
	 * 回调函数方式调用存储过程.
	 * 
	 * 
	 * @param procSql
	 *            存储过程语句
	 * @param action
	 *            存储过程的回调函数
	 * 
	 * @throws BusiException
	 * @return Object
	 */
	public static Object execute(String procSql, CallableStatementCallback action) throws BusiException {
		Connection conn = null;
		CallableStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareCall(procSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			return action.doInCallableStatement(stmt);
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
	 * 回调函数方式调用SQL语句.
	 * 
	 * 
	 * @param sql
	 *            SQL语句
	 * @param action
	 *            SQL的回调函数
	 * 
	 * @throws BusiException
	 * @return Object
	 */
	public static Object execute(String sql, PreparedStatementCallback action) throws BusiException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			return action.doInPreparedStatement(stmt);
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
	 * 数据库连接.
	 * 
	 * 
	 * @param action
	 *            数据库连接的回调函数
	 * 
	 * @throws BusiException
	 * @return Object
	 */
	public static Object execute(ConnectionCallback action) throws BusiException {
		Connection conn = null;
		try {
			return action.doInConnection(getConnection());
		} catch (Exception e) {
			throw new BusiException("数据库操作异常！", e);
		} finally {
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
	 * 
	 * <b>标题:</b> 数据库连接的回调函数.
	 * 
	 * <pre>
	 * <b>描述:</b> 
	 *   数据库连接的回调函数
	 * </pre>
	 * 
	 * @author <a href="mailto:littcai@hotmail.com">空心大白菜</a>
	 * @since 2007-8-10
	 * @version 1.0
	 *
	 */
	public interface ConnectionCallback {

		public Object doInConnection(Connection conn) throws SQLException;

	}

	/**
	 * 
	 * <b>标题:</b> 存储过程调用的回调函数.
	 * 
	 * <pre>
	 * <b>描述:</b> 
	 *   存储过程调用的回调函数
	 * </pre>
	 * 
	 * @author <a href="mailto:littcai@hotmail.com">空心大白菜</a>
	 * @since 2007-8-10
	 * @version 1.0
	 *
	 */
	public interface CallableStatementCallback {

		Object doInCallableStatement(CallableStatement cs) throws SQLException;
	}

	/**
	 * 
	 * <b>标题:</b> SQL语句调用的回调函数.
	 * 
	 * <pre>
	 * <b>描述:</b> 
	 *   SQL语句调用的回调函数
	 * </pre>
	 * 
	 * @author <a href="mailto:littcai@hotmail.com">空心大白菜</a>
	 * @since 2007-8-10
	 * @version 1.0
	 *
	 */
	public interface PreparedStatementCallback {

		Object doInPreparedStatement(PreparedStatement ps) throws SQLException;
	}

}
