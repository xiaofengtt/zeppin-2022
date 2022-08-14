 
package enfo.crm.service;
import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.tools.Utility;
import enfo.crm.vo.CustServiceLogVO;

@Component(value="custServiceLog")
public class CustServiceLogBean extends enfo.crm.dao.CrmBusiExBean implements CustServiceLogLocal {
	private static final String sql_add_TCustServiceLog = "{?=call SP_ADD_TCUSTSERVICELOG(?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String sql_modi_TCustServiceLog = "{?=call SP_MODI_TCUSTSERVICELOG(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String sql_del_TCustServiceLog = "{?=call SP_DEL_TCUSTSERVICELOG(?,?)}";

	/* (non-Javadoc)
	 * @see enfo.crm.service.CustServiceLogLocal#append(enfo.crm.vo.CustServiceLogVO)
	 */
	@Override
	public void append(CustServiceLogVO vo) throws BusiException{
		Object[] params = new Object[12];

		params[0] = vo.getCustId();
		params[1] = vo.getCustNo();
		params[2] = vo.getServiceTime();
		params[3] = vo.getServiceInfo();
		params[4] = vo.getServiceSummary();
		params[5] = vo.getServiceMan();
		params[6] = vo.getExecutor();
		params[7] = vo.getContent();
		params[8] = vo.getSubject();
		params[9] = vo.getStep_flag();
		params[10] = vo.getInfo_level();
		params[11] = vo.getInputMan();
		
		super.cudProc(sql_add_TCustServiceLog,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.service.CustServiceLogLocal#modi(enfo.crm.vo.CustServiceLogVO)
	 */
	@Override
	public void modi(CustServiceLogVO vo) throws BusiException{
		Object[] params = new Object[13];

		params[0] = Utility.parseInt(vo.getSerialNo(),new Integer(0));
		params[1] = Utility.parseInt(vo.getCustId(),new Integer(0));
		params[2] = Utility.trimNull(vo.getCustNo());
		params[3] = vo.getServiceTime();
		params[4] = Utility.trimNull(vo.getServiceInfo());
		params[5] = Utility.trimNull(vo.getServiceSummary());
		params[6] = vo.getServiceMan();
		params[7] = vo.getExecutor();
		params[8] = vo.getContent();
		params[9] = vo.getSubject();
		params[10] = vo.getStep_flag();
		params[11] = vo.getInfo_level();
		params[12] = Utility.parseInt(vo.getInputMan(),new Integer(0));

		super.cudProc(sql_modi_TCustServiceLog,params);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.service.CustServiceLogLocal#delete(enfo.crm.vo.CustServiceLogVO)
	 */
	@Override
	public void delete(CustServiceLogVO vo) throws BusiException{
		Object[] params = new Object[2];

		params[0] = Utility.parseInt(vo.getSerialNo(),new Integer(0));
		params[1] = Utility.parseInt(vo.getInputMan(),new Integer(0));

		super.cudProc(sql_del_TCustServiceLog,params);	
	}
}