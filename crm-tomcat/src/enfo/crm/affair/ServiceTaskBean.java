 
package enfo.crm.affair;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.ServiceTaskVO;

@Component(value="serviceTask")
public class ServiceTaskBean extends enfo.crm.dao.CrmBusiExBean implements ServiceTaskLocal {
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.ServiceTaskLocal#query_dealTask_page(enfo.crm.vo.ServiceTaskVO, java.lang.String[], int, int)
	 */	
    @Override
	public IPageList query_dealTask_page(ServiceTaskVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		IPageList rsList = null;		
		Object[] params = new Object[1];
		String sql ="{call SP_QUERY_InsideServiceTasks(?)}";
		
		params[0]= vo.getSerial_no();
		
		rsList = super.listProcPage(sql,params,totalColumn,pageIndex,pageSize);			
		return rsList;		
	}	
		
	/* (non-Javadoc)
	 * @see enfo.crm.affair.ServiceTaskLocal#query_page(enfo.crm.vo.ServiceTaskVO, java.lang.String[], int, int)
	 */
    @Override
	public IPageList query_page(ServiceTaskVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		IPageList rsList = null;		
		Object[] params = new Object[12];
		String sql ="{call SP_QUERY_TServiceTasks(?,?,?,?,?,?,?,?,?,?,?,?)}";
		
		params[0]= vo.getSerial_no();
		params[1]= vo.getManagerID();
		params[2]= vo.getExecutor();
		params[3]= vo.getInsertMan();
		params[4]= vo.getServiceType();
		params[5]= vo.getServiceWay();
		params[6]= vo.getStatus();
		params[7]= vo.getQues_id();
		params[8]= vo.getProductId();
		params[9]= vo.getInputMan();
		params[10]= vo.getStartDateTime();
		params[11]= vo.getEndDateTime();
		
		rsList = super.listProcPage(sql,params,totalColumn,pageIndex,pageSize);			
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.ServiceTaskLocal#append(enfo.crm.vo.ServiceTaskVO)
	 */
    @Override
	public Object append(ServiceTaskVO vo) throws BusiException{
		Object[] params = new Object[13];
		Object serial_no;
		String sql ="{?=call SP_ADD_TServiceTasks(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		int outParamPos = 14;
		int outParamType = 4;		
		
		params[0]= vo.getManagerID();
		params[1]= vo.getServiceType();
		params[2]= vo.getServiceTitle();
		params[3]= vo.getServiceWay();
		params[4]= vo.getStartDate();
		params[5]= vo.getEndDate();
		params[6]= vo.getServiceRemark();
		params[7]= vo.getQues_id();
		params[8]= vo.getProductId();
		params[9]= vo.getAutoFlag();
		params[10]= vo.getTempID();
		params[11]= vo.getInputMan();
		params[12] = vo.getExecutor();
		
		return super.cudProc(sql,params,outParamPos,outParamType);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.ServiceTaskLocal#modi(enfo.crm.vo.ServiceTaskVO)
	 */
	@Override
	public void modi(ServiceTaskVO vo) throws BusiException{
		Object[] params = new Object[14];
		String sql ="{?=call SP_MODI_TServiceTasks(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		
		params[0]= vo.getSerial_no();
		params[1]= vo.getManagerID();
		params[2]= vo.getServiceType();
		params[3]= vo.getServiceTitle();
		params[4]= vo.getServiceWay();
		params[5]= vo.getStartDate();
		params[6]= vo.getEndDate();
		params[7]= vo.getServiceRemark();
		params[8]= vo.getQues_id();
		params[9]= vo.getProductId();
		params[10]= vo.getAutoFlag();
		params[11]= vo.getTempID();
		params[12]= vo.getInputMan();
		params[13]= vo.getExecutor();

		super.cudProc(sql,params);		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.ServiceTaskLocal#delete(enfo.crm.vo.ServiceTaskVO)
	 */
	@Override
	public void delete(ServiceTaskVO vo) throws BusiException{
		Object[] params = new Object[2];
		String sql ="{?=call SP_DEL_TServiceTasks(?,?)}";
		
		params[0]= vo.getSerial_no();
		params[1]= vo.getInputMan();
		
		super.cudProc(sql,params);		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.ServiceTaskLocal#query(enfo.crm.vo.ServiceTaskVO)
	 */
	@Override
	public List query(ServiceTaskVO vo) throws BusiException{
		List rsList = null;
		Object[] params = new Object[12];
		String sql ="{call SP_QUERY_TServiceTasks(?,?,?,?,?,?,?,?,?,?,?,?)}";
		
		params[0]= vo.getSerial_no();
		params[1]= vo.getManagerID();
		params[2]= vo.getExecutor();
		params[3]= vo.getInsertMan();
		params[4]= vo.getServiceType();
		params[5]= vo.getServiceWay();
		params[6]= vo.getStatus();
		params[7]= vo.getQues_id();
		params[8]= vo.getProductId();
		params[9]= vo.getInputMan();
		params[10]= vo.getStartDateTime();
		params[11]= vo.getEndDateTime();
		
		rsList = super.listBySql(sql,params);			
		return rsList;	
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.ServiceTaskLocal#append_details(enfo.crm.vo.ServiceTaskVO)
	 */
	@Override
	public void append_details(ServiceTaskVO vo) throws BusiException{
		Object[] params = new Object[4];
		String sql ="{?=call SP_ADD_TServiceTaskDetail(?,?,?,?)}";
		
		params[0]= vo.getTaskSerialNO();
		params[1]= vo.getTargetCustID();
		params[2]= vo.getNeedFeedBack();
		params[3]= vo.getInputMan();
		
		super.cudProc(sql,params);		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.ServiceTaskLocal#delete_details(enfo.crm.vo.ServiceTaskVO)
	 */
	@Override
	public void delete_details(ServiceTaskVO vo) throws BusiException{
		Object[] params = new Object[2];
		String sql ="{?=call SP_DEL_TServiceTaskDetail(?,?)}";
		
		params[0]= vo.getSerial_no();
		params[1]= vo.getInputMan();
		
		super.cudProc(sql,params);	
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.ServiceTaskLocal#query_details(enfo.crm.vo.ServiceTaskVO)
	 */
	@Override
	public List query_details(ServiceTaskVO vo) throws BusiException{
		List rsList = null;		
		Object[] params = new Object[7];
		String sql ="{call SP_QUERY_TServiceTaskDetail(?,?,?,?,?,?,?)}";
		
		params[0]= vo.getSerial_no();
		params[1]= vo.getTaskSerialNO();
		params[2]= vo.getServiceType();
		params[3]= vo.getServiceWay();
		params[4]= vo.getStatus();
		params[5]= vo.getNeedFeedBack();
		params[6]= vo.getInputMan();
		
		rsList = super.listBySql(sql,params);			
		return rsList;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.affair.ServiceTaskLocal#query_detailsa(enfo.crm.vo.ServiceTaskVO, java.lang.String[], int, int)
	 */		
	@Override
	public IPageList query_detailsa(ServiceTaskVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		    IPageList rsList = null;
			Object[] params = new Object[7];
			String sql ="{call SP_QUERY_TServiceTaskDetail(?,?,?,?,?,?,?)}";
		
			params[0]= vo.getSerial_no();
			params[1]= vo.getTaskSerialNO();
			params[2]= vo.getServiceType();
			params[3]= vo.getServiceWay();
			params[4]= vo.getStatus();
			params[5]= vo.getNeedFeedBack();
			params[6]= vo.getInputMan();
		
		rsList = super.listProcPage(sql,params,totalColumn,pageIndex,pageSize);			
	    return rsList;
		}	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.ServiceTaskLocal#allocate(enfo.crm.vo.ServiceTaskVO)
	 */
	@Override
	public void allocate(ServiceTaskVO vo) throws BusiException{
		Object[] params = new Object[3];
		String sql ="{?=call SP_Allocate_TServiceTasks(?,?,?)}";
		
		params[0]= vo.getSerial_no();
		params[1]= vo.getExecutor();
		params[2]= vo.getInputMan();
		
		super.cudProc(sql,params);		
	}	
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.ServiceTaskLocal#treat(enfo.crm.vo.ServiceTaskVO)
	 */
	@Override
	public void treat(ServiceTaskVO vo) throws BusiException{
		Object[] params = new Object[3];
		String sql ="{?=call SP_Treat_TServiceTasks(?,?,?)}";
		
		params[0]= vo.getSerial_no();
		params[1]= vo.getResult();
		params[2]= vo.getInputMan();
		
		super.cudProc(sql,params);			
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.ServiceTaskLocal#treat_details(enfo.crm.vo.ServiceTaskVO)
	 */
	@Override
	public void treat_details(ServiceTaskVO vo) throws BusiException{
		Object[] params = new Object[7];
		String sql ="{?=call SP_Treat_TServiceTaskDetail(?,?,?,?,?,?,?)}";
		
		params[0]= vo.getSerial_no();
		params[1]= vo.getServiceWay();
		params[2]= vo.getExecutorTime();
		params[3]= vo.getStatus();
		params[4]= vo.getResult();
		params[5]= vo.getNeedFeedBack();		
		params[6]= vo.getInputMan();
		
		super.cudProc(sql,params);			
	}
	/* (non-Javadoc)
	 * @see enfo.crm.affair.ServiceTaskLocal#feedback(enfo.crm.vo.ServiceTaskVO)
	 */
	@Override
	public void feedback(ServiceTaskVO vo) throws BusiException{
		Object[] params = new Object[4];
		String sql ="{?=call SP_Feedback_TServiceTaskDetail(?,?,?,?)}";
		
		params[0]= vo.getSerial_no(); 
		params[1]= vo.getFeedbackContent(); 
		params[2]= vo.getSatisfaction(); 
		params[3]= vo.getInputMan();

		super.cudProc(sql,params);		
	}
}