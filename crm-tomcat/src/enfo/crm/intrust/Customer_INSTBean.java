 
package enfo.crm.intrust;
import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.tools.Utility;
import enfo.crm.vo.CustomerVO;

@Component(value="customer_INST")
public class Customer_INSTBean extends enfo.crm.dao.IntrustBusiExBean implements Customer_INSTLocal {

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.Customer_INSTLocal#cope_customers(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public void cope_customers(CustomerVO vo) throws BusiException{
		Object[] params = new Object[14];
		
		params[0] = Utility.parseInt(Utility.trimNull(vo.getCust_id()),new Integer(0));
		params[1] = Utility.trimNull(vo.getCust_no());
		params[2] = Utility.trimNull(vo.getCust_name());
		params[3] = Utility.trimNull(vo.getCard_type());
		params[4] = Utility.trimNull(vo.getCard_id());
		params[5] = Utility.trimNull(vo.getCust_type());
		params[6] = Utility.trimNull(vo.getLegal_man());
		params[7] = Utility.trimNull(vo.getContact_man());
		params[8] = Utility.trimNull(vo.getPost_address());
		params[9] = Utility.trimNull(vo.getPost_code());
		params[10] = Utility.trimNull(vo.getE_mail());
		params[11] = Utility.trimNull(vo.getMobile());
		params[12] = Utility.parseInt(vo.getService_man(),new Integer(0));
		params[13] = Utility.parseInt(vo.getInput_man(),new Integer(0));		
	
		super.cudProc("{?= call SP_CRM_CopyCustomerToIntust(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",params);	
	}
	
	@Override
	public void cope_customers_m(CustomerVO vo) throws BusiException{
		Object[] params = new Object[14];
		
		params[0] = Utility.parseInt(Utility.trimNull(vo.getCust_id()),new Integer(0));
		params[1] = Utility.trimNull(vo.getCust_no());
		params[2] = Utility.trimNull(vo.getCust_name());
		params[3] = Utility.trimNull(vo.getCard_type());
		params[4] = Utility.trimNull(vo.getCard_id());
		params[5] = Utility.trimNull(vo.getCust_type());
		params[6] = Utility.trimNull(vo.getLegal_man());
		params[7] = Utility.trimNull(vo.getContact_man());
		params[8] = Utility.trimNull(vo.getPost_address());
		params[9] = Utility.trimNull(vo.getPost_code());
		params[10] = Utility.trimNull(vo.getE_mail());
		params[11] = Utility.trimNull(vo.getMobile());
		params[12] = Utility.parseInt(vo.getService_man(),new Integer(0));
		params[13] = Utility.parseInt(vo.getInput_man(),new Integer(0));		
	
		super.cudProc("{?= call SP_CRM_CopyCustomerToIntust_m(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",params);	
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.Customer_INSTLocal#synchro_customers(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public void synchro_customers(CustomerVO vo) throws BusiException{
		String sqlStr = "{? = call SP_SYNCHRO_TCustomers(?)}";
		Object[] params = new Object[1];
		
		params[0] = Utility.parseInt(Utility.trimNull(vo.getCust_id()),new Integer(0));
		
		CrmDBManager.cudProc(sqlStr, params);
	}
}