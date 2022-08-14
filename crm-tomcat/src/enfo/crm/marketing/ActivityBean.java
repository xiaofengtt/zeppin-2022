 
package enfo.crm.marketing;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.ActivityVO;

@Component(value="activity")
public class ActivityBean extends enfo.crm.dao.CrmBusiExBean implements ActivityLocal {

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.ActivityLocal#append(enfo.crm.vo.ActivityVO)
	 */
	@Override
	public Integer append(ActivityVO vo) throws BusiException{
		String sqlStr = "{?=call SP_ADD_TACTIVITIES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[13];
		
		params[0] = vo.getActive_type();
		params[1] = vo.getActive_theme();
		params[2] = vo.getActive_start_date();
		params[3] = vo.getActive_end_date();
		params[4] = vo.getManage_code();
		params[5] = vo.getCustomer_type();
		params[6] = vo.getActive_plan();
		params[7] = vo.getActive_trace();
		params[8] = vo.getActive_result();
		params[9] = vo.getActive_flag();
		params[10] = vo.getCompleteTime();
		params[11] = vo.getCreator();
		params[12] = vo.getInput_man();		
		return (Integer)super.cudProc(sqlStr, params, 15, Types.INTEGER);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.ActivityLocal#modi(enfo.crm.vo.ActivityVO)
	 */
	@Override
	public void modi(ActivityVO vo) throws BusiException{
		String sqlStr = "{?=call SP_MODI_TACTIVITIES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[14];
		
		params[0] = vo.getSerial_no();
		params[1] = vo.getActive_type();
		params[2] = vo.getActive_theme();
		params[3] = vo.getActive_start_date();
		params[4] = vo.getActive_end_date();
		params[5] = vo.getManage_code();
		params[6] = vo.getCustomer_type();
		params[7] = vo.getActive_plan();
		params[8] = vo.getActive_trace();
		params[9] = vo.getActive_result();
		params[10] = vo.getActive_flag();
		params[11] = vo.getCompleteTime();
		params[12] = vo.getCreator();
		params[13] = vo.getInput_man();
		
		super.cudProc(sqlStr,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.ActivityLocal#delete(enfo.crm.vo.ActivityVO)
	 */
	@Override
	public void delete(ActivityVO vo) throws BusiException{
		String sqlStr = "{?=call SP_DEL_TACTIVITIES(?,?)}";
		Object[] params = new Object[2];
		
		params[0] = vo.getSerial_no();
		params[1] = vo.getInput_man();
		
		super.cudProc(sqlStr,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.ActivityLocal#pageList_query(enfo.crm.vo.ActivityVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pageList_query(ActivityVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		String sqlStr = "{call SP_QUERY_TACTIVITIES(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		IPageList rsList = null;		
		Object[] params = new Object[13];
		
		params[0] = Utility.parseInt(Utility.trimNull(vo.getSerial_no()),new Integer(0));
		params[1] = vo.getActive_type();
		params[2] = vo.getActive_theme();
		params[3] = vo.getStartDate();
		params[4] = vo.getEndDate();
		params[5] = vo.getManage_code();
		params[6] = vo.getActive_flag();
		params[7] = vo.getCompleteTimeUp();
		params[8] = vo.getCompleteTimeDown();
		params[9] = vo.getActive_fee_up();
		params[10] = vo.getActive_fee_down();
		params[11] = vo.getCreator();
		params[12] = vo.getActive_code();
			
		rsList = super.listProcPage(sqlStr,params,totalColumn,pageIndex,pageSize);		
		return rsList;				
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.ActivityLocal#query(enfo.crm.vo.ActivityVO)
	 */
	@Override
	public List query(ActivityVO vo) throws BusiException{
		String sqlStr = "{call SP_QUERY_TACTIVITIES(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		List rsList = null;
		Object[] params = new Object[13];
		
		params[0] = Utility.parseInt(Utility.trimNull(vo.getSerial_no()),new Integer(0));
		params[1] = vo.getActive_type();
		params[2] = vo.getActive_theme();
		params[3] = vo.getStartDate();
		params[4] = vo.getEndDate();
		params[5] = vo.getManage_code();
		params[6] = vo.getActive_flag();
		params[7] = vo.getCompleteTimeUp();
		params[8] = vo.getCompleteTimeDown();
		params[9] = vo.getActive_fee_up();
		params[10] = vo.getActive_fee_down();
		params[11] = vo.getCreator();
		params[12] = vo.getActive_code();
		
		rsList = super.listProcAll(sqlStr,params);
		return rsList;
	}	
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.ActivityLocal#load(java.lang.Integer)
	 */
	@Override
	public List load(Integer serial_no) throws BusiException{
		String sqlStr = "{call SP_QUERY_TActivitiesLoad(?)}";
		List rsList = null;
		Object[] params = new Object[1];
		
		params[0] = serial_no;
		rsList = super.listProcAll(sqlStr,params);
		return rsList;	
	}		
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.ActivityLocal#appendCustomerInfo(enfo.crm.vo.ActivityVO)
	 */
	@Override
	public void appendCustomerInfo(ActivityVO vo)throws BusiException, SQLException{
		String sqlAdd = "INSERT INTO TACTIVITYCUSTS(ACTIVITY_ID,CUST_ID) " +
				"VALUES("+vo.getSerial_no()+","+vo.getCust_id()+")";
		Connection conn = null;
		PreparedStatement prestmt = null;
		try {
			conn = CrmDBManager.getConnection();
			prestmt = conn.prepareStatement(sqlAdd);	
			prestmt.executeUpdate();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			if(conn!=null)
				conn.close();		
			if(prestmt!=null)
				prestmt.close();
		}
		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.ActivityLocal#queryByListCustomerInfo(java.lang.Integer)
	 */
	@Override
	public List queryByListCustomerInfo(Integer serial_no)throws BusiException{
		List list = new ArrayList();
		String sqlQuery = "SELECT * FROM TACTIVITYCUSTS WHERE ACTIVITY_ID = ?";
		Object[]params = new Object[1];
		params[0] = serial_no;
		list = super.listBySql(sqlQuery, params);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.ActivityLocal#deleteCustomerInfo(java.lang.Integer)
	 */
	@Override
	public void deleteCustomerInfo(Integer serial_no)throws BusiException, SQLException{
		String sqlDelete = "DELETE TACTIVITYCUSTS WHERE ACTIVITY_ID = "+serial_no;
		Connection conn = null;
		PreparedStatement prestmt = null;
		try {
			conn = CrmDBManager.getConnection();
			prestmt = conn.prepareStatement(sqlDelete);	
			prestmt.executeUpdate();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			if(conn!=null)
				conn.close();		
			if(prestmt!=null)
				prestmt.close();
		}
	}
}