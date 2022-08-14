package enfo.crm.intrust;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmBusiExBean;
import enfo.crm.tools.Utility;
import enfo.crm.vo.AttachmentVO;

@Component(value="attachmentToCrm")
public class AttachmentToCrmBean extends CrmBusiExBean implements AttachmentToCrmLocal {

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.AttachmentToCrmLocal#append(enfo.crm.vo.AttachmentVO)
	 */
	@Override
	public void append(AttachmentVO vo) throws BusiException{
		String sqlStr = "{?=call SP_ADD_TATTACHMENTS(?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[8];
		
        params[0] = vo.getDf_talbe_id();
        params[1] = vo.getDf_table_name();
        params[2] = vo.getDf_serial_no();
        params[3] = vo.getSave_name();
        params[4] = vo.getOrigin_name();
        params[5] = vo.getFile_size();
        params[6] = vo.getDescription();
        params[7] = vo.getInput_man();
        super.cudProc(sqlStr, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.AttachmentToCrmLocal#delete(enfo.crm.vo.AttachmentVO)
	 */
	@Override
	public void delete(AttachmentVO vo) throws BusiException{
		String sqlStr = "{?=call SP_DEL_TATTACHMENTS(?,?,?)}";
		Object[] params = new Object[3];
		
        params[0] = vo.getDf_serial_no();
        params[1] = vo.getDf_talbe_id();
        params[2] = vo.getInput_man();
        super.cudProc(sqlStr, params);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.AttachmentToCrmLocal#deleteById(enfo.crm.vo.AttachmentVO)
	 */
	@Override
	public void deleteById(AttachmentVO vo) throws BusiException{
		String sqlStr = "{?=call SP_DEL_TATTACHMENTS_BY_ID(?,?)}";
		Object[] params = new Object[2];
		
        params[0] = vo.getAttachment_id();
        params[1] = vo.getInput_man();
        super.cudProc(sqlStr, params);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.AttachmentToCrmLocal#deleteFile(enfo.crm.vo.AttachmentVO)
	 */
	@Override
	public void deleteFile(AttachmentVO vo) throws BusiException{
		String fileName=vo.getSave_name();// fileName为文件的绝对路径
		File file = new File(fileName);
		if (file.exists()) {
			boolean d = file.delete();

			if (d) {
				System.out.print("删除成功！");
			} else {
				System.out.print("删除失败！");
			}
		}else{
			System.out.println("没有此文件");
		}
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.AttachmentToCrmLocal#load(enfo.crm.vo.AttachmentVO)
	 */
	@Override
	public List load(AttachmentVO vo) throws BusiException{
		String sqlStr = "{call SP_QUERY_TATTACHMENTS(?,?,?)}";
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(vo.getDf_serial_no(), 0);
        params[1] = Utility.parseInt(vo.getDf_talbe_id(), 0);
        params[2] = Utility.parseInt(vo.getInput_man(), 0);
        List rsList = super.listProcAll(sqlStr, params);
        return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.AttachmentToCrmLocal#loadById(enfo.crm.vo.AttachmentVO)
	 */
	@Override
	public List loadById(AttachmentVO vo) throws BusiException{
		String sqlStr = "{call SP_QUERY_TATTACHMENTS_BY_ID(?,?)}";
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getAttachment_id(), 0);
        params[1] = Utility.parseInt(vo.getInput_man(), 0);
        List rsList = super.listProcAll(sqlStr, params);
        return rsList;
	}
}
