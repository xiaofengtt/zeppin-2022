package enfo.crm.marketing;

import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.MeetingVO;

@Component(value="meeting")
public class MeetingBean extends enfo.crm.dao.CrmBusiExBean implements MeetingLocal {

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.MeetingLocal#append(enfo.crm.vo.MeetingVO)
	 */
	@Override
	public Integer append(MeetingVO vo) throws BusiException {
		Integer serial_no = new Integer(0);
		Object[] params = new Object[10];
		params[0] = vo.getMeeting_date();
		params[1] = vo.getMeeting_type();
		params[2] = vo.getStart_date();
		params[3] = vo.getEnd_date();
		params[4] = vo.getMeeting_address();
		params[5] = vo.getAttend_man();
		params[6] = vo.getAttend_man_code();
		params[7] = vo.getMeeting_theme();
		params[8] = vo.getRemark();
		params[9] = vo.getInput_man();

		serial_no = (Integer) super.cudProc("{?=call SP_ADD_TMEETINGS(?,?,?,?,?,?,?,?,?,?,?)}", params, 12,
				java.sql.Types.INTEGER);
		return serial_no;
	}
	

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.MeetingLocal#modi(enfo.crm.vo.MeetingVO)
	 */
	@Override
	public void modi(MeetingVO vo) throws BusiException {
		Object[] params = new Object[11];

		params[0] = vo.getSerial_no();
		params[1] = vo.getMeeting_date();
		params[2] = vo.getMeeting_type();
		params[3] = vo.getStart_date();
		params[4] = vo.getEnd_date();
		params[5] = vo.getMeeting_address();
		params[6] = vo.getAttend_man();
		params[7] = vo.getAttend_man_code();
		params[8] = vo.getMeeting_theme();
		params[9] = vo.getRemark();
		params[10] = vo.getInput_man();

		super.cudProc("{?= call SP_MODI_TMEETINGS(?,?,?,?,?,?,?,?,?,?,?)}",
				params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.MeetingLocal#delete(enfo.crm.vo.MeetingVO)
	 */
	@Override
	public void delete(MeetingVO vo) throws BusiException {
		Object[] params = new Object[2];

		params[0] = vo.getSerial_no();
		params[1] = vo.getInput_man();

		super.cudProc("{?=call SP_DEL_TMEETINGS(?,?)}", params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.MeetingLocal#pageList_query(enfo.crm.vo.MeetingVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pageList_query(MeetingVO vo, String[] totalColumn,
			int pageIndex, int pageSize) throws BusiException {
		IPageList rsList = null;
		Object[] params = new Object[8];

		params[0] = Utility.parseInt(Utility.trimNull(vo.getSerial_no()),
				new Integer(0));
		params[1] = vo.getMeeting_date();
		params[2] = vo.getQ_start_date();
		params[3] = vo.getQ_end_date();
		params[4] = vo.getMeeting_type();
		params[5] = vo.getAttend_man();
		params[6] = vo.getMeeting_address();
		params[7] = vo.getMeeting_theme();

		rsList = super.listProcPage(
				"{call SP_QUERY_TMEETINGS(?,?,?,?,?,?,?,?)}", params,
				totalColumn, pageIndex, pageSize);
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.MeetingLocal#query(enfo.crm.vo.MeetingVO)
	 */
	@Override
	public List query(MeetingVO vo) throws BusiException {
		List rsList = null;
		Object[] params = new Object[8];

		params[0] = Utility.parseInt(Utility.trimNull(vo.getSerial_no()),
				new Integer(0));
		params[1] = vo.getMeeting_date();
		params[2] = vo.getQ_start_date();
		params[3] = vo.getQ_end_date();
		params[4] = vo.getMeeting_type();
		params[5] = vo.getAttend_man();
		params[6] = vo.getMeeting_address();
		params[7] = vo.getMeeting_theme();

		rsList = super.listProcAll(
				"{call SP_QUERY_TMEETINGS(?,?,?,?,?,?,?,?)}", params);
		return rsList;
	}
}