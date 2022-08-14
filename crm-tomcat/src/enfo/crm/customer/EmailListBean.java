 
package enfo.crm.customer;
import java.sql.Types;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.tools.Utility;
import enfo.crm.vo.EmailListVO;

@Component(value="emailList")
public class EmailListBean extends enfo.crm.dao.CrmBusiExBean implements EmailListLocal {

	/**
	 * 添加邮件信息
	 */
	private static final String appendSql =
		"{?=call SP_ADD_TEMAILLIST(?,?,?,?)}";
		
	/* (non-Javadoc)
	 * @see enfo.crm.customer.EmailListLocal#append(enfo.crm.vo.EmailListVO)
	 */
	@Override
	public Integer append(EmailListVO vo) throws BusiException {
		Object[] params = new Object[3];
		params[0] = Utility.trimNull(vo.getSubject());
		params[1] = Utility.trimNull(vo.getBody());
		params[2] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		Integer temp_serial_no = new Integer(0);

		temp_serial_no =
			(Integer) super.cudProc(appendSql, params, 5, Types.INTEGER);
		return temp_serial_no;
	}
}