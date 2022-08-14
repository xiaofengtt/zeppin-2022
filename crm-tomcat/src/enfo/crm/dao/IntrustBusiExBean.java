package enfo.crm.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import enfo.crm.dao.supper.RemoveAware;

/**
 * 
 * <b>����:</b> ��״̬�ỰBean����.
 * <pre><b>����:</b> 
 *   ��װ�ײ�ejbʵ�ֺ����ݿ����ʵ��
 * </pre>
 * 
 * @author <a href="mailto:littcai@hotmail.com">���Ĵ�ײ�</a>
 * @since 2007-8-10
 * @version 1.0
 *
 */
public class IntrustBusiExBean extends RemoveAware{
	
	/**
	 * ֱ��ִ�и��²�����SQL���.
	 * @param updateSql ��Ҫִ�е�SQL���
	 */
	public void executeSql(String updateSql) throws BusiException {
		IntrustDBManager.executeSql(updateSql);
	}

	/**
	 * 
	 * ֱ��ִ��SQL���²����ķ���
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
	 * ����ִ�и��²�����SQL���,ͬһ��������
	 * @param sql ���µ�SQL����
	 */
	public void batchUpdate(String[] sqls) throws BusiException {
		IntrustDBManager.batchUpdate(sqls);
	}

	/**
	 * ��ɾ�ĵĴ洢���̲���,�洢������һ�����͵��������ֵ.
	 * 
	 * @param params ����
	 * @param procSql �洢����
	 */
	public void cudProc(String procSql, Object[] params) throws BusiException {
		IntrustDBManager.cudProc(procSql, params);
	}


	/**
	 * ��ɾ�ĵĴ洢���̲���,�洢������һ�����͵��������ֵ.
	 * 
	 * @param params ����
	 * @param procSql �洢����
	 */
	public Integer execProc(String procSql, Object[] params) throws BusiException {
		return IntrustDBManager.execProc(procSql, params);
	}
	
	/**
	 * ��ɾ�ĵĴ洢���̲���.
	 * 
	 * @param params ����
	 * @param procSql �洢����
	 */
	public void cudProcNoRet(String procSql, Object[] params)
		throws BusiException {
		IntrustDBManager.cudProcNoRet(procSql, params);
	}

	/**
	 * 
	 * ����������Ĵ洢���̵���
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
	 * ����������Ĵ洢���̵���
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
	 * ��ѯȫ���Ĳ���
	 * @param procSql �洢����
	 * @param params ����
	 */
	public List listProcAll(String procSql, Object[] params)
		throws BusiException {
		return IntrustDBManager.listProcAll(procSql, params);

	}

	/**
	 * ��ҳ��ѯ�Ĳ���
	 * @param procSql �洢����
	 * @param params ����
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
	 * ��ҳ��ѯ�Ĳ���
	 * @param procSql �洢����
	 * @param params ����
	 * @param totalColumn ��Ҫ�ϼƵ��ֶ����� 
	 * 
	 *  
	 * add by tsg 2009-06-05(���棡����)
	 *  ע��totalColumn������ֵ�Ǵ洢���̷��ؽ���������������������ͳ���У���totalColumn=null 
	 *                 �����ɸ�ֵtotalColumn={""},����ҳ���׳������ݿ�����쳣�� - Invalid column name .��
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
	 * ֱ��ͨ��SQL�����в�ѯ����.
	 * 
	 * @param listSql ��ѯSQL���
	 * @return List
	 */
	public List listBySql(String listSql) throws BusiException {
		return IntrustDBManager.listBySql(listSql);
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
	public List listBySql(String listSql, Object[] params)
		throws BusiException {
		return IntrustDBManager.listBySql(listSql, params);
	}

	/**
	 * JDBC��ҳ��ѯ.
	 * @param pageIndex ҳ��
	 * @param listSql ��ѯSQL���
	 * @param pageSize ÿҳ��ʾ��
	 * @return
	 */
	public IPageList listSqlPage(String listSql, int pageIndex, int pageSize)
		throws BusiException {
		return IntrustDBManager.listSqlPage(listSql, pageIndex, pageSize);
	}

	/**
	 * ����SQL����ѯ��¼��.
	 * @param listCountSql ��ѯ��¼����SQL���
	 * @return
	 */
	public int listSqlCount(String listCountSql) throws BusiException {
		return IntrustDBManager.listSqlCount(listCountSql);
	}
	
	/**
	 * ���µķ�װ
	 * 
	 * @param updateSql
	 *            �洢����
	 * @param params
	 *            ��������
	 * @throws Exception
	 */
	//hesl 2011.4.29
	public void update(String updateSql, Object[] params) throws Exception {
		this.cud(updateSql, params);
	}
	
	/**
	 * ��ɾ�ĵķ�װ
	 * 
	 * @param cudSQl
	 *            �洢����
	 * @param params
	 *            ��������
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
					//NULLֵ�������͵ģ�������Object������Ч�����԰�����ΪString����
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
		if (errorCode < 0) //ֻ���д����ʱ��Ų���
			IntrustDBManager.handleResultCode(errorCode);
	}

	@Override
	public void remove() {
	}
}
