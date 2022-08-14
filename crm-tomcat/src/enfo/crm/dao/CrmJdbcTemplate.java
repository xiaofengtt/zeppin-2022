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
	 * ��ÿ��õ����ݿ�����
	 * 
	 * @return
	 * @throws Exception
	 */
	public synchronized static Connection getConnection() throws Exception {
		DataSource ds = (DataSource) context.getBean("crmDataSource");
		return DbKit.get(ds);
	}
	
	/**
	 * �ر����ݿ�����
	 * 
	 * @throws SQLException
	 */
	public final static void closeConnection() throws SQLException {
		DbKit.close();
	}
	
	/**
	 * �ر�ResultSet
	 * 
	 * @param rs
	 * @throws SQLException
	 */
	public final static void closeResultSet(ResultSet rs) throws SQLException {
		if (rs != null)
			rs.close();
	}

	/**
	 * �ر�CallableStatement
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
	 * �ص�������ʽ���ô洢����.
	 * 
	 * 
	 * @param procSql
	 *            �洢�������
	 * @param action
	 *            �洢���̵Ļص�����
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
			throw new BusiException("���ݿ�����쳣��", e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new BusiException("�ر����ݿ������쳣��");
				}

			}
			if (conn != null) {
				try {
					if (!conn.isClosed())
						conn.close();
				} catch (SQLException e) {
					throw new BusiException("�ر����ݿ������쳣��");
				}
			}
		}
	}

	/**
	 * �ص�������ʽ����SQL���.
	 * 
	 * 
	 * @param sql
	 *            SQL���
	 * @param action
	 *            SQL�Ļص�����
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
			throw new BusiException("���ݿ�����쳣��", e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new BusiException("�ر����ݿ������쳣��");
				}

			}
			if (conn != null) {
				try {
					if (!conn.isClosed())
						conn.close();
				} catch (SQLException e) {
					throw new BusiException("�ر����ݿ������쳣��");
				}
			}
		}
	}

	/**
	 * ���ݿ�����.
	 * 
	 * 
	 * @param action
	 *            ���ݿ����ӵĻص�����
	 * 
	 * @throws BusiException
	 * @return Object
	 */
	public static Object execute(ConnectionCallback action) throws BusiException {
		Connection conn = null;
		try {
			return action.doInConnection(getConnection());
		} catch (Exception e) {
			throw new BusiException("���ݿ�����쳣��", e);
		} finally {
			if (conn != null) {
				try {
					if (!conn.isClosed())
						conn.close();
				} catch (SQLException e) {
					throw new BusiException("�ر����ݿ������쳣��");
				}
			}
		}

	}

	/**
	 * 
	 * <b>����:</b> ���ݿ����ӵĻص�����.
	 * 
	 * <pre>
	 * <b>����:</b> 
	 *   ���ݿ����ӵĻص�����
	 * </pre>
	 * 
	 * @author <a href="mailto:littcai@hotmail.com">���Ĵ�ײ�</a>
	 * @since 2007-8-10
	 * @version 1.0
	 *
	 */
	public interface ConnectionCallback {

		public Object doInConnection(Connection conn) throws SQLException;

	}

	/**
	 * 
	 * <b>����:</b> �洢���̵��õĻص�����.
	 * 
	 * <pre>
	 * <b>����:</b> 
	 *   �洢���̵��õĻص�����
	 * </pre>
	 * 
	 * @author <a href="mailto:littcai@hotmail.com">���Ĵ�ײ�</a>
	 * @since 2007-8-10
	 * @version 1.0
	 *
	 */
	public interface CallableStatementCallback {

		Object doInCallableStatement(CallableStatement cs) throws SQLException;
	}

	/**
	 * 
	 * <b>����:</b> SQL�����õĻص�����.
	 * 
	 * <pre>
	 * <b>����:</b> 
	 *   SQL�����õĻص�����
	 * </pre>
	 * 
	 * @author <a href="mailto:littcai@hotmail.com">���Ĵ�ײ�</a>
	 * @since 2007-8-10
	 * @version 1.0
	 *
	 */
	public interface PreparedStatementCallback {

		Object doInPreparedStatement(PreparedStatement ps) throws SQLException;
	}

}
