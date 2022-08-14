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
	 * ��ѯȫ���Ĳ���.
	 * @param procSql �洢����
	 * @param params ����
	 * @throws BusiException
	 */
	public static List listProcAll(String procSql, Object[] params)
		throws BusiException {
		final Object[] params_final = params;

		return (List) execute(procSql, new CallableStatementCallback() {
			public Object doInCallableStatement(CallableStatement cs)
				throws SQLException {
				List resultList = new ArrayList(); //���������	

				if (params_final != null) {
					for (int i = 0; i < params_final.length; i++) {
						cs.setObject(i + 1, params_final[i]);				
					}
				}
				ResultSet rs = cs.executeQuery();
				//��������
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
				//�ر�����
				rs.close();
				return resultList;
			}
		});
	}

	/**
	 * ��ѯȫ���Ĳ���
	 * @param procSql �洢����
	 * @param params ����
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
				IPageList pageList = new JdbcPageList(); //���������	
				pageList.setPageIndex(pageIndex_final);
				pageList.setPageSize(pageSize_final);

				for (int i = 0; i < params_final.length; i++) //ѭ��������ֵ
					{
					cs.setObject(i + 1, params_final[i]);
				}

				ResultSet rs = cs.executeQuery();
				//��������
				pageList.populate(rs);
				//�ر�����
				rs.close();
				return pageList;
			}
		});
	}

	/**
	 * ��ѯȫ���Ĳ���
	 * @param procSql �洢����
	 * @param params ����
	 * @param totalColumn ��Ҫ�ϼƵ��ֶ�����
	 * @param pageIndex ��ǰҳ��
	 * @param pageSize ҳ����ʾ��С
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
				IPageList pageList = new JdbcPageList(); //���������	
				pageList.setTotalColumn(totalColumn_final);
				pageList.setPageIndex(pageIndex_final);
				pageList.setPageSize(pageSize_final);

				for (int i = 0; i < params_final.length; i++) //ѭ��������ֵ
					{
					cs.setObject(i + 1, params_final[i]);
				}

				ResultSet rs = cs.executeQuery();
				//��������
				pageList.populate(rs);
				//�ر�����
				rs.close();
				return pageList;
			}
		});
	}

	/**
	 * ��ɾ�ĵĴ洢���̲���,�洢������һ�����͵��������ֵ.
	 * 
	 * @param params ����
	 * @param procSql �洢����
	 * 
	 * @throws BusiException
	 */
	public static void cudProc(String procSql, Object[] params)
		throws BusiException {
		Integer errorCode = new Integer(0); //�������

		final Object[] params_final = params;

		errorCode =
			(Integer) execute(procSql, new CallableStatementCallback() {
			public Object doInCallableStatement(CallableStatement cs)
				throws SQLException {
				cs.registerOutParameter(1, java.sql.Types.INTEGER);
				for (int i = 0; i < params_final.length; i++) //ѭ��������ֵ
					{
					cs.setObject(i + 2, params_final[i]);
				}
				cs.execute();
				return new Integer(cs.getInt(1)); //�洢���̷���ֵ��������룩		
			}
		});	
		//���ݴ������ִ���洢���̷��ص�ҵ���쳣
		handleResultCode(errorCode.intValue());
	}
	
	/**
	 * ��ɾ�ĵĴ洢���̲���,�洢������һ�����͵��������ֵ.
	 * 
	 * @param params ����
	 * @param procSql �洢����
	 * @ ������ͬ ��չ
	 * 
	 * @throws BusiException
	 */
	public static Integer execProc(String procSql, Object[] params)
		throws BusiException {
		Integer errorCode = new Integer(0); //�������

		final Object[] params_final = params;

		errorCode =
			(Integer) execute(procSql, new CallableStatementCallback() {
			public Object doInCallableStatement(CallableStatement cs)
				throws SQLException {
				cs.registerOutParameter(1, java.sql.Types.INTEGER);
				for (int i = 0; i < params_final.length; i++) //ѭ��������ֵ
					{
					cs.setObject(i + 2, params_final[i]);
				}
				cs.execute();
				return new Integer(cs.getInt(1)); //�洢���̷���ֵ��������룩		
			}
		});	
		return errorCode ;
	}
	

	/**
	 * ��ɾ�ĵĴ洢���̲���.
	 * 
	 * @param params ����
	 * @param procSql �洢����
	 */
	public static void cudProcNoRet(String procSql, Object[] params)
		throws BusiException {
		final Object[] params_final = params;

		execute(procSql, new CallableStatementCallback() {
			public Object doInCallableStatement(CallableStatement cs)
				throws SQLException {
				cs.registerOutParameter(1, java.sql.Types.INTEGER);
				for (int i = 0; i < params_final.length; i++) //ѭ��������ֵ
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
	 * ����������Ĵ洢���̵���
	 * 
	 * @param params ����
	 * @param procSql �洢����
	 * @param outParamPos ���������λ��
	 * @param outParamType �������������
	 * @return �������
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

				if (i >= tempOutParamPos) //������һ������������ڣ�����ʵ�ʵ����������λ�ý�����1
					trueIndex++;
				if (params[i] == null)
					//NULLֵ�������͵ģ�������Object������Ч�����԰�����ΪString����
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
		//���ݴ������ִ���洢���̷��ص�ҵ���쳣
		handleResultCode(errorCode);
		return ret;
	}

	/**
	 * 
	 * ����������Ĵ洢���̵���
	 * 
	 * @param params ����
	 * @param procSql �洢����
	 * @param outParamPos ���������λ��
	 * @param outParamType �������������
	 * @return �������
	 * @throws BusiException
	 * 
	 * 2009-12-08 taochen&lzd
	 * �����ж� ����������Ƶ�λ���Ƿ��������ռ�������ռ����+1
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
					j++) //�����ж������������ڣ�����ʵ�ʵ����������λ�ý�����N
					{
					int tempOutParamPos = outParamPos[j] - 2;
					if (i >= tempOutParamPos||i+j==tempOutParamPos)
						trueIndex++;
				}

				stmt.setObject(trueIndex, params[i]);
			}
			//ע���������
			for (int j = 0; j < outParamPos.length; j++) {
				stmt.registerOutParameter(outParamPos[j], outParamType[j]);
			}

			stmt.executeUpdate();

			errorCode = stmt.getInt(1);
			//����������
			for (int j = 0; j < outParamPos.length; j++) {
				ret[j] = stmt.getObject(outParamPos[j]);
			}
			stmt.close();
			conn.close();
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
		//���ݴ������ִ���洢���̷��ص�ҵ���쳣
		handleResultCode(errorCode);
		return ret;
	}

	/**
	 * ֱ��ִ�и��²�����SQL���.
	 * @param updateSql ��Ҫִ�е�SQL���
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
	 * ֱ��ִ��SQL���²����ķ���
	 * 
	 * @param updateSql ��Ҫִ�е�SQL���
	 * @param params	����
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
	 * ����ִ�и��²�����SQL���,ͬһ��������
	 * @param sql ���µ�SQL����
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

			//�ر�����			
			stmt.close();
			conn.close();

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
	 * ֱ��ͨ��SQL�����в�ѯ����.
	 * 
	 * @param listSql ��ѯSQL���
	 * @return List
	 */
	public static List listBySql(String listSql) throws BusiException {
		return (List) execute(listSql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement ps)
				throws SQLException {
				List resultList = new ArrayList(); //���������	
				ResultSet rs = ps.executeQuery();
				//��������
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
				//�ر�����
				rs.close();
				return resultList;
			}
		});
	}

	/**
	 * 
	 * ֱ��ͨ��SQL�����в�ѯ����
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
				List resultList = new ArrayList(); //���������	

				if (params_final != null) {
					for (int i = 0; i < params_final.length; i++) {
						ps.setObject(i + 1, params_final[i]);
					}
				}
				ResultSet rs = ps.executeQuery();
				//��������
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
				//�ر�����
				rs.close();
				return resultList;
			}
		});
	}

	/**
	 * JDBC��ҳ��ѯ.
	 * @param pageIndex ҳ��
	 * @param listSql ��ѯSQL���
	 * @param pageSize ÿҳ��ʾ��
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
				IPageList pageList = new JdbcPageList(); //���������	
				pageList.setPageIndex(pageIndex_final);
				pageList.setPageSize(pageSize_final);

				ResultSet rs = ps.executeQuery();
				//��������
				pageList.populate(rs);
				//�ر�����
				rs.close();
				return pageList;
			}
		});
	}

	/**
	 * ����SQL����ѯ��¼��.
	 * 
	 * @param listCountSql ��ѯ��¼����SQL���
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
				//�ر�����
				rs.close();
				return new Integer(count);
			}
		});
		return count.intValue();
	}
}
