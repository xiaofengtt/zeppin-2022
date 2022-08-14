 
package enfo.crm.affair;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.RemindersVO;

@Component(value="reminders")
public class RemindersBean extends enfo.crm.dao.CrmBusiExBean implements RemindersLocal {
	private static final String sql_add_reminders = "{?=call SP_ADD_TREMINDERS(?,?,?)}";

	private static final String sql_modi_reminders = "{?=call SP_MODI_TREMINDERS(?,?,?,?,?)}";

	private static final String sql_del_reminders = "{?=call SP_DEL_TREMINDERS(?,?)}";

	private static final String sql_get_reminders = "{call SP_QUERY_TREMINDERS(?,?,?,?,?)}";
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.RemindersLocal#append(enfo.crm.vo.RemindersVO)
	 */
	@Override
	public void append(RemindersVO vo) throws BusiException{
		Object[] params = new Object[3];

		params[0] = Utility.parseInt(vo.getSchedule_date(),null);
		params[1] = Utility.trimNull(vo.getContent());
		params[2] = Utility.parseInt(vo.getInput_man(),null);

		super.cudProc(sql_add_reminders,params);	
	}

	/* (non-Javadoc)
	 * @see enfo.crm.affair.RemindersLocal#modi(enfo.crm.vo.RemindersVO)
	 */
	@Override
	public void modi(RemindersVO vo) throws BusiException{
		Object[] params = new Object[5];

		params[0] = Utility.parseInt(vo.getSerial_no(),null);
		params[1] = Utility.parseInt(vo.getSchedule_date(),null);
		params[2] = Utility.trimNull(vo.getContent());
		params[3] = Utility.parseInt(vo.getCheck_flag(),null);
		params[4] = Utility.parseInt(vo.getInput_man(),null);

		super.cudProc(sql_modi_reminders,params);
	}


	/* (non-Javadoc)
	 * @see enfo.crm.affair.RemindersLocal#delete(enfo.crm.vo.RemindersVO)
	 */
	@Override
	public void delete(RemindersVO vo) throws BusiException{
		Object[] params = new Object[2];

		params[0] = Utility.parseInt(vo.getSerial_no(),null);
		params[1] = Utility.parseInt(vo.getInput_man(),null);

		super.cudProc(sql_del_reminders,params);	
	}

	/* (non-Javadoc)
	 * @see enfo.crm.affair.RemindersLocal#pagelist_query(enfo.crm.vo.RemindersVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pagelist_query(RemindersVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		IPageList rsList = null;		
		Object[] params = new Object[5];

		params[0] = Utility.parseInt(vo.getSerial_no(),null);
		params[1] = Utility.parseInt(vo.getInput_man(),null);
		params[2] = Utility.parseInt(vo.getBegin_date(),null);
		params[3] = Utility.parseInt(vo.getEnd_date(),null);
		params[4] = Utility.parseInt(vo.getCheck_flag(),null);

		rsList = super.listProcPage(sql_get_reminders,params,totalColumn,pageIndex,pageSize);		
		return rsList;		
	}

	/* (non-Javadoc)
	 * @see enfo.crm.affair.RemindersLocal#list_query(enfo.crm.vo.RemindersVO)
	 */
	@Override
	public List list_query(RemindersVO vo) throws BusiException{
		List list = null;		
		Object[] params = new Object[5];

		params[0] = Utility.parseInt(vo.getSerial_no(),null);
		params[1] = Utility.parseInt(vo.getInput_man(),null);
		params[2] = Utility.parseInt(vo.getBegin_date(),null);
		params[3] = Utility.parseInt(vo.getEnd_date(),null);
		params[4] = Utility.parseInt(vo.getCheck_flag(),null);

		list = super.listProcAll(sql_get_reminders,params);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.RemindersLocal#pagelistStaffSchedule(enfo.crm.vo.RemindersVO, int, int)
	 */
	@Override
	public IPageList pagelistStaffSchedule(RemindersVO vo, int pageIndex,int pageSize) throws BusiException{
		Object[] params = new Object[5];
		params[0] = vo.getOp_code();		
		params[1] = vo.getBegin_date();
		params[2] = vo.getEnd_date();
		params[3] = vo.getSchedule_type();
		params[4] = vo.getInput_man();	
		return super.listProcPage("{call SP_QUERY_TSCHEDULES_LEADER(?,?,?,?,?)}", params, pageIndex, pageSize);		
	}
}