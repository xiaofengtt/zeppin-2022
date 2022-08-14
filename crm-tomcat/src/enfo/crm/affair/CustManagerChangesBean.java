 
package enfo.crm.affair;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.CustomerVO;
import enfo.crm.vo.TcustmanagerchangesVO;

@Component(value="custManagerChanges")
public class CustManagerChangesBean extends enfo.crm.dao.CrmBusiExBean implements CustManagerChangesLocal {
	
	private static final String sql_add_tcustmanagerchanges = "{?=call SP_ADD_TCUSTMANAGERCHANGES(?,?,?,?)}";

	private static final String sql_modi_tcustmanagerchanges = "{?=call SP_MODI_TCUSTMANAGERCHANGES(?,?,?,?)}";

	private static final String sql_del_tcustmanagerchanges = "{?=call SP_DEL_TCUSTMANAGERCHANGES(?,?)}";

	private static final String sql_get_tcustmanagerchanges = "{call SP_QUERY_TCUSTMANAGERCHANGES(?,?,?,?,?,?)}";

	private static final String sql_check_tcustmanagerchanges = "{?=call SP_CHECK_TCUSTMANAGERCHANGES(?,?,?)}";
	
	private static final String sql_modi_cust_inputman = "{?=call SP_MODI_CUST_INPUTMAN_MODI(?,?)}";
	/* (non-Javadoc)
	 * @see enfo.crm.affair.CustManagerChangesLocal#append(enfo.crm.vo.TcustmanagerchangesVO)
	 */
	@Override
	public void append(TcustmanagerchangesVO vo) throws BusiException{
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(vo.getManagerid_before(),null);
		params[1] = Utility.parseInt(vo.getManagerid_now(),null);
		params[2] = Utility.parseInt(vo.getInput_man(),null);
		params[3] = vo.getCust_id();
		super.cudProc(sql_add_tcustmanagerchanges,params);	
	}

	/* (non-Javadoc)
	 * @see enfo.crm.affair.CustManagerChangesLocal#modi(enfo.crm.vo.TcustmanagerchangesVO)
	 */
	@Override
	public void modi(TcustmanagerchangesVO vo) throws BusiException{
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(vo.getSerial_no(),null);
		params[1] = Utility.parseInt(vo.getManagerid_before(),null);
		params[2] = Utility.parseInt(vo.getManagerid_now(),null);
		params[3] = Utility.parseInt(vo.getInput_man(),null);
		super.cudProc(sql_modi_tcustmanagerchanges,params);
	}


	/* (non-Javadoc)
	 * @see enfo.crm.affair.CustManagerChangesLocal#delete(enfo.crm.vo.TcustmanagerchangesVO)
	 */
	@Override
	public void delete(TcustmanagerchangesVO vo) throws BusiException{
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getSerial_no(),null);
		params[1] = Utility.parseInt(vo.getInput_man(),null);
		super.cudProc(sql_del_tcustmanagerchanges,params);	
	}

	/* (non-Javadoc)
	 * @see enfo.crm.affair.CustManagerChangesLocal#pagelist_query(enfo.crm.vo.TcustmanagerchangesVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pagelist_query(TcustmanagerchangesVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		Object[] params = new Object[6];
		params[0] = Utility.parseInt(vo.getSerial_no(),new Integer(0));
		params[1] = Utility.trimNull(vo.getManagername_before());
		params[2] = Utility.trimNull(vo.getManagername_now());
		params[3] = Utility.parseInt(vo.getCheck_flag(),new Integer(0));
		params[4] = Utility.parseInt(vo.getInput_man(),null);
		params[5] = Utility.parseInt(vo.getFlag1(),new Integer(1));//标志: 0查全部 1仅操作员有审核权限时才返回待审核记录
		return super.listProcPage(sql_get_tcustmanagerchanges,params,totalColumn,pageIndex,pageSize);		
	}

	/* (non-Javadoc)
	 * @see enfo.crm.affair.CustManagerChangesLocal#list_query(enfo.crm.vo.TcustmanagerchangesVO)
	 */
	@Override
	public List list_query(TcustmanagerchangesVO vo) throws BusiException{
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(vo.getSerial_no(),null);
		params[1] = Utility.trimNull(vo.getManagername_before());
		params[2] = Utility.trimNull(vo.getManagername_now());
		params[3] = Utility.parseInt(vo.getCheck_flag(),null);
		return super.listProcAll(sql_get_tcustmanagerchanges,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.CustManagerChangesLocal#check(enfo.crm.vo.TcustmanagerchangesVO)
	 */
	@Override
	public void check(TcustmanagerchangesVO vo) throws BusiException{
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(vo.getSerial_no(),null);
		params[1] = Utility.parseInt(vo.getCheck_flag(),null);
		params[2] = Utility.parseInt(vo.getInput_man(),null);
		super.cudProc(sql_check_tcustmanagerchanges,params);	
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.CustManagerChangesLocal#modiCustInputMan(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public void modiCustInputMan(CustomerVO vo) throws BusiException{
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getCust_id(),null);
		params[1] = Utility.parseInt(vo.getInput_man(),null);
		super.cudProc(sql_modi_cust_inputman,params);
	}
}