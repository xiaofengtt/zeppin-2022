 
package enfo.crm.marketing;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.ActivityFeeVO;

@Component(value="activityFee")
public class ActivityFeeBean extends enfo.crm.dao.CrmBusiExBean implements ActivityFeeLocal {

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.ActivityFeeLocal#append(enfo.crm.vo.ActivityFeeVO)
	 */
	@Override
	public void append(ActivityFeeVO vo) throws BusiException{
		String sqlStr = "{?=call SP_ADD_TActivitiesFee(?,?,?,?,?)}";
		Object[] params = new Object[5];
		
		params[0] = vo.getActive_serial_no();
		params[1] = vo.getFee_items();
		params[2] = vo.getFee_amount();
		params[3] = vo.getRemark();
		params[4] = vo.getInput_man();
		
		super.cudProc(sqlStr,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.ActivityFeeLocal#modi(enfo.crm.vo.ActivityFeeVO)
	 */
	
	@Override
	public void modi(ActivityFeeVO vo) throws BusiException{
		String sqlStr = "{?=call SP_MODI_TActivitiesFee(?,?,?,?,?)}";
		Object[] params = new Object[5];
		
		params[0] = vo.getSerial_no();
		params[1] = vo.getFee_items();
		params[2] = vo.getFee_amount();
		params[3] = vo.getRemark();
		params[4] = vo.getInput_man();
		
		super.cudProc(sqlStr,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.ActivityFeeLocal#delete(enfo.crm.vo.ActivityFeeVO)
	 */
	@Override
	public void delete(ActivityFeeVO vo) throws BusiException{
		String sqlStr = "{?=call SP_DEL_TActivitiesFee(?,?)}";
		Object[] params = new Object[2];
		
		params[0] = vo.getSerial_no();
		params[1] = vo.getInput_man();
		
		super.cudProc(sqlStr,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.ActivityFeeLocal#pageList_query(enfo.crm.vo.ActivityFeeVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pageList_query(ActivityFeeVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException {
		String sqlStr = "{call SP_QUERY_TActivitiesFee(?,?,?,?,?)}";
		IPageList rsList = null;		
		Object[] params = new Object[5];
		
		params[0] = Utility.parseInt(Utility.trimNull(vo.getSerial_no()),new Integer(0));
		params[1] = vo.getActive_serial_no();
		params[2] = vo.getFee_items();
		params[3] = vo.getFee_amount_up();
		params[4] = vo.getFee_amount_down();
	
		rsList = super.listProcPage(sqlStr,params,totalColumn,pageIndex,pageSize);		
		return rsList;	
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.ActivityFeeLocal#query(enfo.crm.vo.ActivityFeeVO)
	 */
	@Override
	public List query(ActivityFeeVO vo) throws BusiException{
		String sqlStr = "{call SP_QUERY_TActivitiesFee(?,?,?,?,?)}";
		List rsList = null;
		Object[] params = new Object[5];
		
		params[0] = Utility.parseInt(Utility.trimNull(vo.getSerial_no()),new Integer(0));
		params[1] = vo.getActive_serial_no();
		params[2] = vo.getFee_items();
		params[3] = vo.getFee_amount_up();
		params[4] = vo.getFee_amount_down();
		
		rsList = super.listProcAll(sqlStr,params);
		return rsList;	
	}	
}