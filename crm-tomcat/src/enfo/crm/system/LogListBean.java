 
package enfo.crm.system;
import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.LogListVO;

@Component(value="logList")
public class LogListBean extends enfo.crm.dao.CrmBusiExBean implements LogListLocal  {

	private static final String listSql =
		"{call SP_QUERY_TLOGLIST(?,?,?,?,?)}";
	//查询日志
	private static final String addLogSql = "{?=call SP_ADD_TLOGLIST(?,?,?,?)}";
	//添加日志
	private static final String backSql = "{?=call SP_BACKUP_DATABASE(?,?,?,?)}";
	//添加备份的日志
	private static final String listcustSql = "{call SP_QUERY_TLOGQUERYCUST(?,?,?)}";
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.LogListLocal#listLogList(enfo.crm.vo.LogListVO, int, int)
	 */
	@Override
	public IPageList listLogList(
		LogListVO vo,
		int pageIndex,
		int pageSize)
		throws BusiException {
		IPageList rsList = null;
		Object[] params = new Object[5];
		params[0] = Utility.parseInt(vo.getOp_code(), new Integer(0)); //操作员编号
		params[1] = Utility.parseInt(vo.getStart_date(), new Integer(0)); //其实日期
		params[2] = Utility.parseInt(vo.getEnd_date(), new Integer(0)); //结束日期
		params[3] = Utility.trimNull(vo.getSummary()); //操作说明
		params[4] = Utility.parseInt(vo.getBusi_flag(), new Integer(0)); //业务类别
		rsList = super.listProcPage(listSql, params, pageIndex, pageSize);
		return rsList;
	}

	//在操作日志中查询数据备份的信息

	/* (non-Javadoc)
	 * @see enfo.crm.system.LogListLocal#parseQuery(java.lang.String)
	 */
	@Override
	public String[] parseQuery(String srcQuery) {
		String startDate = "";
		String endDate = "";
		String opCode = "";

		if (srcQuery != null && (!srcQuery.equals(""))) {
			int index = srcQuery.indexOf("$");
			startDate = srcQuery.substring(0, index);
			srcQuery = srcQuery.substring(index + 1);
			index = srcQuery.indexOf("$");
			endDate = srcQuery.substring(0, index);
			opCode = srcQuery.substring(index + 1);
		}
		return (new String[] { startDate, endDate, opCode });
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.LogListLocal#addLog(java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.String)
	 */
	@Override
	public void addLog(
		Integer busi_flag,
		String busi_name,
		Integer input_opCode,
		String summary)
		throws BusiException {
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(busi_flag, null);
		params[1] = Utility.trimNull(busi_name);
		params[2] = Utility.parseInt(input_opCode, null);
		params[3] = Utility.trimNull(summary);
		super.cudProc(addLogSql, params);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.system.LogListLocal#backLog(java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public void backLog(
		Integer busi_flag,
		String backup_flag,
		String comment,
		Integer input_man)
		throws BusiException {
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(busi_flag, 0);
		params[1] = Utility.trimNull(backup_flag);
		params[2] = Utility.trimNull(comment);
		params[3] = Utility.parseInt(input_man, 0);
		super.cudProc(backSql, params);
	}
	
	

	/* (non-Javadoc)
	 * @see enfo.crm.system.LogListLocal#getCustLog(enfo.crm.vo.LogListVO, int, int)
	 */
	@Override
	public IPageList getCustLog(
		LogListVO vo,
		int pageIndex,
		int pageSize)
		throws BusiException {
		IPageList rsList = null;
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(vo.getOp_code(), new Integer(0)); //操作员编号
		params[1] = Utility.trimNull(vo.getStart_date1()); //其实日期
		params[2] = Utility.trimNull(vo.getEnd_date1()); //结束日期
		rsList = super.listProcPage(listcustSql, params, pageIndex, pageSize);
		return rsList;
	}
	
}