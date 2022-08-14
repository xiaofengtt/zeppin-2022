 
package enfo.crm.intrust;
import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.TaskInfoVO;

@Component(value="taskInfo")
public class TaskInfoBean extends enfo.crm.dao.IntrustBusiExBean implements TaskInfoLocal {
	private static final String queryOpinfoSql = "{call SP_QUERY_TTASKINFO_COMM (?,?,?,?)}";
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.TaskInfoLocal#queryOpinfo(enfo.crm.vo.TaskInfoVO, int, int)
	 */ 
	@Override
	public IPageList queryOpinfo(
			TaskInfoVO vo,
			int pageIndex, 
			int pageSize)  
			throws BusiException {
			IPageList pageList = null;
			Object[] params = new Object[4];
			params[0] = Utility.parseInt(vo.getOp_code(), new Integer(0));
			params[1] = Utility.parseInt(vo.getRead_flag(), new Integer(0));
			params[2] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
			params[3] = Utility.parseInt(vo.getRead_flag1(), new Integer(0));
			pageList = super.listProcPage(queryOpinfoSql, params, pageIndex, pageSize);
			return pageList;
		}
}