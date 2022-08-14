 
package enfo.crm.customer;
import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.tools.Utility;
import enfo.crm.vo.EmailRecipientsVO;

@Component(value="emailRecipients")
public class EmailRecipientsBean extends enfo.crm.dao.CrmBusiExBean implements EmailRecipientsLocal {
	/**
	 * 添加收件人信息
	 */
	private static final String appendSql =
		"{?=call SP_ADD_TEMAILRECIPIENTS(?,?,?,?,?)}";
		
	/* (non-Javadoc)
	 * @see enfo.crm.customer.EmailRecipientsLocal#appendReci(enfo.crm.vo.EmailRecipientsVO)
	 */
	@Override
	public void appendReci(EmailRecipientsVO vo) throws BusiException {
		Object[] oparams = new Object[4];
		oparams[0] = Utility.parseLong(vo.getMail_serial_no(), new Long(0));
		oparams[1] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		oparams[2] = vo.getRecipients();
		oparams[3] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		Integer temp_serial_no = new Integer(0);
		
		temp_serial_no =
			(Integer) super.cudProc(
				appendSql,
				oparams,
				6,
				java.sql.Types.INTEGER);
	}

}