package enfo.crm.system;
import java.sql.Types;

import org.springframework.stereotype.Component;

import enfo.crm.dao.CrmDBManager;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.HolidayVO;

@Component(value="holiday")
public class HolidayBean extends enfo.crm.dao.CrmBusiExBean implements HolidayLocal {
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.HolidayLocal#queryHolidays(enfo.crm.vo.HolidayVO, int, int)
	 */
	@Override
	public IPageList queryHolidays(HolidayVO localVO, int page,int pageSize) throws Exception{
		String querySql = "{CALL SP_QUERY_THOLIDAYS(?, ?, ?)}";
		Object[] params = new Object[3];
		params[0] = localVO.getHoliday_id();
		params[1] = localVO.getHoliday_name();
		params[2] = localVO.getInput_man();
		return CrmDBManager.listProcPage(querySql, params, page, pageSize);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.system.HolidayLocal#addHolidays(enfo.crm.vo.HolidayVO)
	 */
	@Override
	public Integer addHolidays(HolidayVO localVO) throws Exception{
		String addSql = "{ ? = CALL SP_ADD_THOLIDAYS(?, ?, ?, ?, ?, ?, ?, ?)}";
		Object[] params = new Object[7];
		params[0] = localVO.getHoliday_name();
		params[1] = localVO.getHoliday_day();
		params[2] = localVO.getCal_flag();
		params[3] = localVO.getCreat_task();
		params[4] = localVO.getSms_greeting();
		params[5] = localVO.getEmail_greeting();
		params[6] = localVO.getInput_man();
		return (Integer) CrmDBManager.cudProc(addSql, params, 9, Types.INTEGER);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.system.HolidayLocal#modiHoliday(enfo.crm.vo.HolidayVO)
	 */
	@Override
	public void modiHoliday(HolidayVO localVO) throws Exception{
		String modiSql = "{? = CALL SP_MODI_THOLIDAYS(?, ?, ?, ?, ?, ?, ?, ?)}";
		Object[] params = new Object[8];
		params[0] = localVO.getHoliday_id();
		params[1] = localVO.getHoliday_name();
		params[2] = localVO.getHoliday_day();
		params[3] = localVO.getCal_flag();
		params[4] = localVO.getCreat_task();
		params[5] = localVO.getSms_greeting();
		params[6] = localVO.getEmail_greeting();
		params[7] = localVO.getInput_man();
		CrmDBManager.cudProc(modiSql, params);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.system.HolidayLocal#delHoliday(enfo.crm.vo.HolidayVO)
	 */
	@Override
	public void delHoliday(HolidayVO localVO) throws Exception{
		String delSql = "{? = CALL SP_DEL_THOLIDAYS(?, ?)}";
		Object[] params = new Object[2];
		params[0] = localVO.getHoliday_id();
		params[1] = localVO.getInput_man();
		CrmDBManager.cudProc(delSql, params);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.system.HolidayLocal#queryHolidayDate(enfo.crm.vo.HolidayVO, int, int)
	 */
	@Override
	public IPageList queryHolidayDate(HolidayVO localVO, int page,int pageSize) throws Exception{
		String querySql = "{CALL SP_QUERY_THOLIDAYDATE(?, ?, ?)}";
		Object[] params = new Object[3];
		params[0] = localVO.getSerial_no();
		params[1] = localVO.getHoliday_id();
		params[2] = localVO.getInput_man();
		
		return CrmDBManager.listProcPage(querySql, params, page, pageSize);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.system.HolidayLocal#modiHolidayDate(enfo.crm.vo.HolidayVO)
	 */
	@Override
	public void modiHolidayDate(HolidayVO localVO) throws Exception{
		String modiSql = "{? = CALL SP_MODI_THOLIDAYDATE(?, ?, ?, ?, ?)}";
		Object[] params = new Object[5];
		params[0] = localVO.getSerial_no();
		params[1] = localVO.getHoliday_id();
		params[2] = localVO.getYearInt();
		params[3] = localVO.getMmddInt();
		params[4] = localVO.getInput_man();
		CrmDBManager.cudProc(modiSql, params);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.system.HolidayLocal#delHolidayDate(enfo.crm.vo.HolidayVO)
	 */
	@Override
	public void delHolidayDate(HolidayVO localVO) throws Exception{
		String delSql = "{? = CALL SP_DEL_THOLIDAYDATE(?, ?)}";
		Object[] params = new Object[2];
		params[0] = localVO.getSerial_no();
		params[1] = localVO.getInput_man();
		CrmDBManager.cudProc(delSql, params);
	}
}