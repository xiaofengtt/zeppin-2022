package enfo.crm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class CrmBusiExBean extends RemoveAware{
	
	/**
	 * 直接执行更新操作的SQL语句.
	 * @param updateSql 需要执行的SQL语句
	 */
	public void executeSql(String updateSql) throws BusiException {
		CrmDBManager.executeSql(updateSql);
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
		CrmDBManager.executeSql(updateSql, params);
	}

	/**
	 * 批量执行更新操作的SQL语句,同一个事务中
	 * @param sql 更新的SQL数组
	 */
	public void batchUpdate(String[] sqls) throws BusiException {
		CrmDBManager.batchUpdate(sqls);
	}

	/**
	 * 增删改的存储过程操作,存储过程有一个整型的输出参数值.
	 * 
	 * @param params 参数
	 * @param procSql 存储过程
	 */
	public void cudProc(String procSql, Object[] params) throws BusiException {
		CrmDBManager.cudProc(procSql, params);
	}


	/**
	 * 增删改的存储过程操作,存储过程有一个整型的输出参数值.
	 * 
	 * @param params 参数
	 * @param procSql 存储过程
	 */
	public Integer execProc(String procSql, Object[] params) throws BusiException {
		return CrmDBManager.execProc(procSql, params);
	}
	
	/**
	 * 增删改的存储过程操作.
	 * 
	 * @param params 参数
	 * @param procSql 存储过程
	 */
	public void cudProcNoRet(String procSql, Object[] params)
		throws BusiException {
		CrmDBManager.cudProcNoRet(procSql, params);
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
		return CrmDBManager.cudProc(procSql, params, outParamPos, outParamType);
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
		return CrmDBManager.cudProc(procSql, params, outParamPos, outParamType);
	}

	/**
	 * 查询全部的操作
	 * @param procSql 存储过程
	 * @param params 参数
	 */
	public List listProcAll(String procSql, Object[] params)
		throws BusiException {
		return CrmDBManager.listProcAll(procSql, params);

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
		return CrmDBManager.listProcPage(procSql, params, pageIndex, pageSize);
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
		return CrmDBManager.listProcPage(
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
		return CrmDBManager.listBySql(listSql);
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
		return CrmDBManager.listBySql(listSql, params);
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
		return CrmDBManager.listSqlPage(listSql, pageIndex, pageSize);
	}

	/**
	 * 根据SQL语句查询记录数.
	 * @param listCountSql 查询记录数的SQL语句
	 * @return
	 */
	public int listSqlCount(String listCountSql) throws BusiException {
		return CrmDBManager.listSqlCount(listCountSql);
	}
	
	/**
	 * 
	 * 直接通过SQL语句进行查询操作
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
