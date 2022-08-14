 
package enfo.crm.callcenter;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.CCVO;

@Component(value="callCenter")
public class CallCenterBean extends enfo.crm.dao.CrmBusiExBean implements CallCenterLocal {

	private String statCCCheckRecordsSql =
		"{call SP_STAT_CC_CheckRecords(?,?,?,?,?)}";
	//private String statCCSeatActivitySql = "{call SP_STAT_CC_SeatActivity()}";
	private String statCCSeatCallDetailSql =
		"{call SP_STAT_CC_SeatCallDetail(?,?,?,?,?)}";
	private String addCallRecordsSql =
		"{?=call SP_ADD_TCALLRECORDS(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private String modiCallRecordsSql =
		"{?=call SP_MODI_TCALLRECORDS(?,?,?,?)}";
	private String listCallRecordsSql = 
		"{call SP_QUERY_TCallRecords(?,?,?,?,?,?,?,?,?,?)}";
	private String listCustByPhoneSql = "{call SP_QUERY_TCUSTOMER_BYPHONE2(?,?)}"; 
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.CallCenterLocal#statCCCheckRecords(enfo.crm.vo.CCVO, int, int)
	 */
	@Override
	public IPageList statCCCheckRecords(CCVO vo, int pageIndex, int pageSize)
		throws BusiException {

		Object[] params = new Object[5];

		params[0] = vo.getManagerID();
		params[1] = vo.getExtension();
		params[2] = vo.getStart_date();
		params[3] = vo.getEnd_date();
		params[4] = vo.getInput_man();

		try {
			return super.listProcPage(
				statCCCheckRecordsSql,
				params,
				pageIndex,
				pageSize);
		} catch (BusiException e) {
			throw new BusiException("查询话务录音记录失败：" + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.CallCenterLocal#query_cc_detail(enfo.crm.vo.CCVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList query_cc_detail(
		CCVO vo,
		String[] totalColumn,
		int pageIndex,
		int pageSize)
		throws BusiException {
		IPageList rsList = null;
		Object[] params = new Object[5];
		String sql = "{call SP_STAT_CC_CheckRecords(?,?,?,?,?)}";

		params[0] = vo.getManagerID();
		params[1] = vo.getExtension();
		params[2] = vo.getStartDate();
		params[3] = vo.getEndDate();
		params[4] = vo.getInputMan();
		try {
			rsList =
					super.listProcPage(sql, params, totalColumn, pageIndex, pageSize);
			
		}
		catch(Exception e )
		{
			throw new BusiException("查询话务录音记录失败：" + e.getMessage());
		}
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.CallCenterLocal#l_query_cc_detail(enfo.crm.vo.CCVO)
	 */
	@Override
	public List l_query_cc_detail(CCVO vo) throws BusiException {
		List list = null;
		Object[] params = new Object[5];
		String sql = "{call SP_STAT_CC_CheckRecords(?,?,?,?,?)}";

		params[0] = vo.getManagerID();
		params[1] = vo.getExtension();
		params[2] = vo.getStartDate();
		params[3] = vo.getEndDate();
		params[4] = vo.getInputMan();

		list = super.listProcAll(sql, params);
		return list;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.CallCenterLocal#statCCSeatCallDetail(enfo.crm.vo.CCVO, int, int, int)
	 */
	@Override
	public IPageList statCCSeatCallDetail(
		CCVO vo,
		int page,
		int pageIndex,
		int pageSize)
		throws BusiException {

		Object[] params = new Object[5];

		params[0] = vo.getManagerID();
		params[1] = vo.getExtension();
		params[2] = vo.getStart_date();
		params[3] = vo.getEnd_date();
		params[4] = vo.getInput_man();

		try {
			return super.listProcPage(
				statCCSeatCallDetailSql,
				params,
				pageIndex,
				pageSize);
		} catch (BusiException e) {
			throw new BusiException("查询坐席电话呼叫明细记录失败：" + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.CallCenterLocal#addCallRecords(enfo.crm.vo.CCVO)
	 */
	@Override
	public Integer addCallRecords(CCVO vo) throws BusiException {
		Integer new_cc_id = new Integer(0);
		Object[] params = new Object[13];

		params[0] = vo.getDirection();
		params[1] = vo.getCallTime();
		params[2] = vo.getCallLength();
		params[3] = vo.getManagerID();
		params[4] = Utility.parseInt(vo.getExtension(), new Integer(0));
		params[5] = vo.getCust_id();
		params[6] = vo.getContactID();
		params[7] = vo.getPhoneNumStr();
		params[8] = vo.getBusinessID();
		params[9] = vo.getContent();
		params[10] = vo.getStatus();
		params[11] = vo.getCallCenterID();
		params[12] = vo.getInput_man();

		try {
			new_cc_id = (Integer) super.cudProc(addCallRecordsSql, params,15,java.sql.Types.INTEGER);
		} catch (BusiException e) {
			throw new BusiException("新增CC通话记录失败：" + e.getMessage());
		}
		
		return new_cc_id;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.CallCenterLocal#modiCallRecords(enfo.crm.vo.CCVO)
	 */
	@Override
	public void modiCallRecords(CCVO vo) throws BusiException {
		Object[] params = new Object[4];

		params[0] = vo.getSerial_no();
		params[1] = vo.getContent();
		params[2] = vo.getStatus();
		params[3] = vo.getInput_man();

		try {
			super.cudProc(modiCallRecordsSql, params);
		} catch (BusiException e) {
			throw new BusiException("修改CC通话记录失败：" + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.CallCenterLocal#listCallRecords(enfo.crm.vo.CCVO, int, int)
	 */
	@Override
	public IPageList listCallRecords(CCVO vo, int pageIndex, int pageSize)
		throws BusiException {
		Object[] params = new Object[10];
		params[0] = vo.getSerial_no();
		params[1] = vo.getDirection();
		params[2] = vo.getCallTime();
		params[3] = vo.getManagerID();
		params[4] = vo.getExtension();
		params[5] = vo.getCust_id();
		params[6] = vo.getPhoneNumStr();
		params[7] = vo.getContent();
		params[8] = vo.getStatus();
		params[9] = vo.getInput_man();
		return super.listProcPage(listCallRecordsSql,params,pageIndex,pageSize);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.CallCenterLocal#listCustByPhone(enfo.crm.vo.CCVO, int, int)
	 */
	@Override
	public IPageList listCustByPhone(CCVO vo,int pageIndex,int pageSize) throws BusiException{
		Object[] params = new Object[2];
		params[0] = vo.getPhoneNumStr();
		params[1] = vo.getInputMan();
		return super.listProcPage(listCustByPhoneSql,params,pageIndex,pageSize);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.CallCenterLocal#listCustByPhone2(enfo.crm.vo.CCVO, int, int)
	 */
	@Override
	public IPageList listCustByPhone2(CCVO vo,int pageIndex,int pageSize) throws BusiException{
		String sqlStr =  "{call SP_QUERY_TCustomer_ByPhone2(?,?)}"; 
		
		Object[] params = new Object[2];
		params[0] = vo.getPhoneNumStr();
		params[1] = vo.getInputMan();
		return super.listProcPage(sqlStr,params,pageIndex,pageSize);
	}
}