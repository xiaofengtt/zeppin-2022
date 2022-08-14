 
package enfo.crm.marketing;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.ActivityTaskVO;

@Component(value="activityTask")
public class ActivityTaskBean extends enfo.crm.dao.CrmBusiExBean implements ActivityTaskLocal {

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.ActivityTaskLocal#append(enfo.crm.vo.ActivityTaskVO)
	 */
	@Override
	public void append(ActivityTaskVO vo) throws BusiException {
		String sqlStr = "{?=call SP_ADD_TActivitiesTask(?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[8];

		params[0] = vo.getActivitySerial_no();
		params[1] = vo.getTaskTitle();
		params[2] = vo.getActivityTaskType();
		params[3] = vo.getContent();
		params[4] = vo.getBeginDate();
		params[5] = vo.getEndDate();
		params[6] = vo.getExecutor();
		params[7] = vo.getInputMan();

		super.cudProc(sqlStr, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.ActivityTaskLocal#modi(enfo.crm.vo.ActivityTaskVO)
	 */
	@Override
	public void modi(ActivityTaskVO vo) throws BusiException {
		String sqlStr = "{?=call SP_MODI_TActivitiesTask(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[13];

		params[0] = vo.getSerial_no();
		params[1] = vo.getTaskTitle();
		params[2] = vo.getActivityTaskType();
		params[3] = vo.getContent();
		params[4] = vo.getBeginDate();
		params[5] = vo.getEndDate();
		params[6] = vo.getExecutor();
		params[7] = vo.getCompleteTime();
		params[8] = vo.getRemark();
		params[9] = vo.getTaskFlag();
		params[10] = vo.getCheckMan();
		params[11] = vo.getCheckOptions();
		params[12] = vo.getInputMan();

		super.cudProc(sqlStr, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.ActivityTaskLocal#delete(enfo.crm.vo.ActivityTaskVO)
	 */
	@Override
	public void delete(ActivityTaskVO vo) throws BusiException {
		String sqlStr = "{?=call SP_DEL_TActivitiesTask(?,?)}";
		Object[] params = new Object[2];

		params[0] = vo.getSerial_no();
		params[1] = vo.getInputMan();

		super.cudProc(sqlStr, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.ActivityTaskLocal#pageList_query(enfo.crm.vo.ActivityTaskVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pageList_query(ActivityTaskVO vo, String[] totalColumn,
			int pageIndex, int pageSize) throws BusiException {
		String sqlStr = "{call SP_QUERY_TActivitiesTask(?,?,?,?,?,?,?,?,?,?,?)}";
		IPageList rsList = null;
		Object[] params = new Object[11];

		params[0] = Utility.parseInt(Utility.trimNull(vo.getSerial_no()),
				new Integer(0));
		params[1] = vo.getTaskTitle();
		params[2] = vo.getActivitySerial_no();
		params[3] = vo.getActivityTaskType();
		params[4] = vo.getBeginDateQuery();
		params[5] = vo.getEndDateQuery();
		params[6] = vo.getExecutor();
		params[7] = vo.getManagerCode();
		params[8] = vo.getCompleteDateUp();
		params[9] = vo.getCompleteDateDown();
		params[10] = vo.getTaskFlag();

		rsList = super.listProcPage(sqlStr, params, totalColumn, pageIndex,
				pageSize);
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.ActivityTaskLocal#query(enfo.crm.vo.ActivityTaskVO)
	 */
	@Override
	public List query(ActivityTaskVO vo) throws BusiException {
		String sqlStr = "{call SP_QUERY_TActivitiesTask(?,?,?,?,?,?,?,?,?,?,?)}";
		List rsList = null;
		Object[] params = new Object[11];

		params[0] = Utility.parseInt(Utility.trimNull(vo.getSerial_no()),
				new Integer(0));
		params[1] = vo.getTaskTitle();
		params[2] = vo.getActivitySerial_no();
		params[3] = vo.getActivityTaskType();
		params[4] = vo.getBeginDateQuery();
		params[5] = vo.getEndDateQuery();
		params[6] = vo.getExecutor();
		params[7] = vo.getManagerCode();
		params[8] = vo.getCompleteDateUp();
		params[9] = vo.getCompleteDateDown();
		params[10] = vo.getTaskFlag();

		rsList = super.listProcAll(sqlStr, params);
		return rsList;
	}
}