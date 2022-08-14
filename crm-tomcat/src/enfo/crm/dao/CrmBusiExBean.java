package enfo.crm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class CrmBusiExBean extends RemoveAware{
	
	/**
	 * ֱ��ִ�и��²�����SQL���.
	 * @param updateSql ��Ҫִ�е�SQL���
	 */
	public void executeSql(String updateSql) throws BusiException {
		CrmDBManager.executeSql(updateSql);
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
		CrmDBManager.executeSql(updateSql, params);
	}

	/**
	 * ����ִ�и��²�����SQL���,ͬһ��������
	 * @param sql ���µ�SQL����
	 */
	public void batchUpdate(String[] sqls) throws BusiException {
		CrmDBManager.batchUpdate(sqls);
	}

	/**
	 * ��ɾ�ĵĴ洢���̲���,�洢������һ�����͵��������ֵ.
	 * 
	 * @param params ����
	 * @param procSql �洢����
	 */
	public void cudProc(String procSql, Object[] params) throws BusiException {
		CrmDBManager.cudProc(procSql, params);
	}


	/**
	 * ��ɾ�ĵĴ洢���̲���,�洢������һ�����͵��������ֵ.
	 * 
	 * @param params ����
	 * @param procSql �洢����
	 */
	public Integer execProc(String procSql, Object[] params) throws BusiException {
		return CrmDBManager.execProc(procSql, params);
	}
	
	/**
	 * ��ɾ�ĵĴ洢���̲���.
	 * 
	 * @param params ����
	 * @param procSql �洢����
	 */
	public void cudProcNoRet(String procSql, Object[] params)
		throws BusiException {
		CrmDBManager.cudProcNoRet(procSql, params);
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
		return CrmDBManager.cudProc(procSql, params, outParamPos, outParamType);
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
		return CrmDBManager.cudProc(procSql, params, outParamPos, outParamType);
	}

	/**
	 * ��ѯȫ���Ĳ���
	 * @param procSql �洢����
	 * @param params ����
	 */
	public List listProcAll(String procSql, Object[] params)
		throws BusiException {
		return CrmDBManager.listProcAll(procSql, params);

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
		return CrmDBManager.listProcPage(procSql, params, pageIndex, pageSize);
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
		return CrmDBManager.listProcPage(
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
		return CrmDBManager.listBySql(listSql);
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
		return CrmDBManager.listBySql(listSql, params);
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
		return CrmDBManager.listSqlPage(listSql, pageIndex, pageSize);
	}

	/**
	 * ����SQL����ѯ��¼��.
	 * @param listCountSql ��ѯ��¼����SQL���
	 * @return
	 */
	public int listSqlCount(String listCountSql) throws BusiException {
		return CrmDBManager.listSqlCount(listCountSql);
	}
	
	/**
	 * 
	 * ֱ��ͨ��SQL�����в�ѯ����
	 * 
	 * @param mspSql
	 * @param params
	 * @return
	 * @throws BusiException
	 */
	public Map mapBySql(String listSql, Object[] params)
		throws BusiException {
		List list = CrmDBManager.listBySql(listSql, params);
		Map map = new HashMap();
		if(list != null  && list.size() > 0){
			map = (Map) list.get(0);
		}
		return map;
	}

	@Override
	public void remove() {
	}
}
