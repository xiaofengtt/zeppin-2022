 
package enfo.crm.affair;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.ScheDulesVO;

@Component(value="scheDules")
public class ScheDulesBean extends enfo.crm.dao.CrmBusiExBean implements ScheDulesLocal {

	private static final String sql_add_tschedules = "{?=call SP_ADD_TSCHEDULES(?,?,?,?,?)}";

	private static final String sql_modi_tschedules = "{?=call SP_MODI_TSCHEDULES(?,?,?,?,?,?,?)}";

	private static final String sql_del_tschedules = "{?=call SP_DEL_TSCHEDULES(?,?)}";

	private static final String sql_get_tschedules = "{call SP_QUERY_TSCHEDULES(?,?,?,?,?,?)}";
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.ScheDulesLocal#append(enfo.crm.vo.ScheDulesVO)
	 */
	@Override
	public void append(ScheDulesVO vo) throws BusiException{
		Object[] params = new Object[5];

		params[0] = Utility.parseInt(vo.getSchedule_type(),null);
		params[1] = Utility.trimNull(vo.getSchedule_start_date());
		params[2] = Utility.trimNull(vo.getSchedule_end_date());
		params[3] = Utility.trimNull(vo.getContent());
		params[4] = Utility.parseInt(vo.getInput_man(),null);

		super.cudProc(sql_add_tschedules,params);	
	}

	/* (non-Javadoc)
	 * @see enfo.crm.affair.ScheDulesLocal#modi(enfo.crm.vo.ScheDulesVO)
	 */
	@Override
	public void modi(ScheDulesVO vo) throws BusiException{
		Object[] params = new Object[7];

		params[0] = Utility.parseInt(vo.getSerial_no(),null);
		params[1] = Utility.parseInt(vo.getSchedule_type(),null);
		params[2] = vo.getSchedule_start_date();
		params[3] = vo.getSchedule_end_date();
		params[4] = Utility.trimNull(vo.getContent());
		params[5] = Utility.parseInt(vo.getCheck_flag(),null);
		params[6] = Utility.parseInt(vo.getInput_man(),null);

		super.cudProc(sql_modi_tschedules,params);
	}


	/* (non-Javadoc)
	 * @see enfo.crm.affair.ScheDulesLocal#delete(enfo.crm.vo.ScheDulesVO)
	 */
	@Override
	public void delete(ScheDulesVO vo) throws BusiException{
		Object[] params = new Object[2];

		params[0] = Utility.parseInt(vo.getSerial_no(),null);
		params[1] = Utility.parseInt(vo.getInput_man(),null);

		super.cudProc(sql_del_tschedules,params);	
	}

	/* (non-Javadoc)
	 * @see enfo.crm.affair.ScheDulesLocal#pagelist_query(enfo.crm.vo.ScheDulesVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pagelist_query(ScheDulesVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		IPageList rsList = null;		
		Object[] params = new Object[6];

		params[0] = Utility.parseInt(vo.getSerial_no(),null);
		params[1] = Utility.parseInt(vo.getInput_man(),null);
		params[2] = Utility.parseInt(vo.getBegin_date(),null);
		params[3] = Utility.parseInt(vo.getEnd_date(),null);
		params[4] = Utility.parseInt(vo.getSchedule_type(),null);
		params[5] = Utility.parseInt(vo.getCheck_flag(),null);

		rsList = super.listProcPage(sql_get_tschedules,params,totalColumn,pageIndex,pageSize);		
		return rsList;		
	}

	/* (non-Javadoc)
	 * @see enfo.crm.affair.ScheDulesLocal#list_query(enfo.crm.vo.ScheDulesVO)
	 */
	@Override
	public List list_query(ScheDulesVO vo) throws BusiException{
		List list = null;		
		Object[] params = new Object[6];

		params[0] = Utility.parseInt(vo.getSerial_no(),null);
		params[1] = Utility.parseInt(vo.getInput_man(),null);
		params[2] = Utility.parseInt(vo.getBegin_date(),null);
		params[3] = Utility.parseInt(vo.getEnd_date(),null);
		params[4] = Utility.parseInt(vo.getSchedule_type(),null);
		params[5] = Utility.parseInt(vo.getCheck_flag(),null);

		list = super.listProcAll(sql_get_tschedules,params);
		return list;
	}
}