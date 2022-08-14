 
package enfo.crm.webcall;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.tools.Utility;
import enfo.crm.vo.TCrmWebCallVO;

@Component(value="tCrmWebCall")
public class TCrmWebCallBean extends enfo.crm.dao.CrmBusiExBean implements TCrmWebCallLocal{
	/* (non-Javadoc)
	 * @see enfo.crm.webcall.TCrmWebCallLocal#appendOperatorUUID(enfo.crm.vo.TCrmWebCallVO)
	 */
	@Override
	public void appendOperatorUUID(TCrmWebCallVO vo) throws BusiException{
		String appendSql = "{?=call SP_ADD_TOPERATOR_UUID(?,?,?)}";
		Object[] params = new Object[3];

		params[0] = Utility.parseInt(vo.getOp_code(),null);
		params[1] = vo.getUuid();
		params[2] = Utility.parseInt(vo.getInput_man(),null);

		super.cudProc(appendSql,params);	
	}
	/* (non-Javadoc)
	 * @see enfo.crm.webcall.TCrmWebCallLocal#listOperatorUUID(enfo.crm.vo.TCrmWebCallVO)
	 */
	@Override
	public List listOperatorUUID(TCrmWebCallVO vo) throws BusiException{
		List list = null;		
		String listSql = "{call SP_QUERY_TOPERATOR_UUID(?,?)}";
		Object[] params = new Object[2];
		
		params[0] = Utility.parseInt(vo.getOp_code(),null);
		params[1] = vo.getUuid();
		
		list = super.listProcAll(listSql,params);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.webcall.TCrmWebCallLocal#appendCrmWebcall(enfo.crm.vo.TCrmWebCallVO)
	 */
	@Override
	public void appendCrmWebcall(TCrmWebCallVO vo) throws BusiException{
		String appendSql = "{?=call SP_ADD_TCustomers_WEBCALL(?,?,?)}";
		Object[] params = new Object[3];

		params[0] = Utility.parseInt(vo.getCust_id(),null);
		params[1] = vo.getWebcallId();
		params[2] = Utility.parseInt(vo.getInput_man(),null);

		super.cudProc(appendSql,params);	
	}
	/* (non-Javadoc)
	 * @see enfo.crm.webcall.TCrmWebCallLocal#listCrmWebcall(enfo.crm.vo.TCrmWebCallVO)
	 */
	@Override
	public List listCrmWebcall(TCrmWebCallVO vo) throws BusiException{
		List list = null;		
		String listSql = "{call SP_QUERY_TCustomers_WEBCALL(?,?)}";
		Object[] params = new Object[2];
		
		params[0] = Utility.parseInt(vo.getCust_id(),null);
		params[1] = vo.getWebcallId();
		
		list = super.listProcAll(listSql,params);
		return list;
	}
}