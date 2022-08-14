 
package enfo.crm.affair;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.vo.ServiceManageVO;

@Component(value="serviceManage")
public class ServiceManageBean extends enfo.crm.dao.CrmBusiExBean implements ServiceManageLocal {

	/* (non-Javadoc)
	 * @see enfo.crm.affair.ServiceManageLocal#query_TServiceDefine(enfo.crm.vo.ServiceManageVO)
	 */
	@Override
	public List query_TServiceDefine(ServiceManageVO vo) throws BusiException{
		List rsList = null;
		Object[] params= new Object[4];
		String sql = "{call SP_QUERY_TSERVICEDEFINE(?,?,?,?)}";
		
		params[0] = vo.getSerial_no();
		params[1] = vo.getServiceType();
		params[2] = vo.getIsValid();
		params[3] = vo.getInputMan();
		
		rsList = super.listProcAll(sql,params);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.ServiceManageLocal#edit_TServiceDefine(enfo.crm.vo.ServiceManageVO)
	 */
	@Override
	public void edit_TServiceDefine(ServiceManageVO vo) throws BusiException{		
		Object[] params= new Object[10];
		String sql = "{?= call SP_EDIT_TSERVICEDEFINE(?,?,?,?,?,?,?,?,?,?)}";
		
		params[0] = vo.getServiceType();
		params[1] = vo.getOffsetDays();
		params[2] = vo.getServiceWay();
		params[3] = vo.getDescription();
		params[4] = vo.getNoticeCaption();
		params[5] = vo.getIsValid();
		params[6] = vo.getExecutor();
		params[7] = vo.getAutoFlag();
		params[8] = vo.getTempID();
		params[9] = vo.getInputMan();
		
		super.cudProc(sql,params);		
	}	
}