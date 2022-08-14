 
package enfo.crm.intrust;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.tools.Utility;
import enfo.crm.vo.AttachmentVO;

@Component(value="attachment")
public class AttachmentBean extends enfo.crm.dao.IntrustBusiExBean implements AttachmentLocal {
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.AttachmentLocal#append(enfo.crm.vo.AttachmentVO)
	 */
	@Override
	public void append(AttachmentVO vo) throws BusiException{
		String sqlStr = "{?=call SP_ADD_TATTACHMENTS(?,?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[10];
		
        params[0] = vo.getDf_talbe_id();
        params[1] = vo.getDf_serial_no();
        params[2] = vo.getSave_name();
        params[3] = vo.getOrigin_name();
        params[4] = vo.getFile_size();
        params[5] = vo.getDescription();
        params[6] = vo.getTemp_df_id();
        params[7] = "";
        params[8] = "";
        params[9] = vo.getInput_man();
        super.cudProc(sqlStr, params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.AttachmentLocal#delete(enfo.crm.vo.AttachmentVO)
	 */
	@Override
	public void delete(AttachmentVO vo) throws BusiException{
		String sqlStr = "{?=call SP_DEL_TATTACHMENTS(?,?)}";
		Object[] params = new Object[2];
		
        params[0] = vo.getAttachment_id();
        params[1] = vo.getInput_man();
		
        super.cudProc(sqlStr, params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.AttachmentLocal#load(enfo.crm.vo.AttachmentVO)
	 */
	@Override
	public List load(AttachmentVO vo) throws BusiException{
		String sqlStr = "{call SP_QUERY_TATTACHMENTS(?,?,?,?,?)}";
		Object[] params = new Object[5];
		
        params[0] = Utility.parseInt(vo.getAttachment_id(), 0);
        params[1] = Utility.parseInt(vo.getDf_talbe_id(), 0);
        params[2] = Utility.parseInt(vo.getDf_serial_no(), 0);
        params[3] = Utility.trimValue(vo.getSave_name(), "");
        params[4] = Utility.trimValue(vo.getOrigin_name(), "");
        
        List rsList = super.listProcAll(sqlStr, params);
        return rsList;
	}
}