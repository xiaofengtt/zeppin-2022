 
package enfo.crm.system;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.AttachmentTypeVO;

@Component(value="attachmentType")
public class AttachmentTypeBean extends enfo.crm.dao.CrmBusiExBean implements AttachmentTypeLocal  {
	
	private String listSql = "{call SP_QUERY_TATTACHMENTTYPE(?,?,?)}";//查询附件类别信息表
	private String appendSql = "{?=call SP_ADD_TATTACHMENTTYPE(?,?,?,?,?,?)}";//添加附件类别信息
	private String modiSql = "{?=call SP_MODI_TATTACHMENTTYPE(?,?,?,?,?,?,?)}";//修改附件类别信息表
	private String deleteSql = "{?=call SP_DEL_TATTACHMENTTYPE(?,?)}";//删除附件类别信息表
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.AttachmentTypeLocal#listBySql(enfo.crm.vo.AttachmentTypeVO)
	 */
	@Override
	public List listBySql(AttachmentTypeVO attachment_type)throws BusiException{
		List list = null;
		Object[] params = new Object[3];
		params[0] = Utility.trimNull(attachment_type.getAttachmentType_id());
		params[1] = Utility.trimNull(attachment_type.getAttachmentType_name());
		params[2] = Utility.trimNull(attachment_type.getDF_Table_id());
		list = super.listBySql(listSql,params);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.AttachmentTypeLocal#append(enfo.crm.vo.AttachmentTypeVO, java.lang.Integer)
	 */
	@Override
	public void append(AttachmentTypeVO attachment_type,Integer input_operatorCode)throws BusiException{
		Object[]param = new Object[6];
		param[0] = Utility.trimNull(attachment_type.getAttachmentType_name());
		param[1] = Utility.trimNull(attachment_type.getAttachmentTypeSummary());
		param[2] = Utility.parseInt(attachment_type.getIS_Necessary(),new Integer(0));
		param[3] = Utility.parseInt(attachment_type.getDF_Table_id(), new Integer(0));
		param[4] = Utility.trimNull(attachment_type.getDF_Table_name());
		param[5] = Utility.parseInt(input_operatorCode,new Integer(0));
		super.cudProc(appendSql,param);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.AttachmentTypeLocal#modi(enfo.crm.vo.AttachmentTypeVO, java.lang.Integer)
	 */
	@Override
	public void modi(AttachmentTypeVO attachment_type,Integer input_operatorCode)throws BusiException{
		Object[]param = new Object[7];
		param[0] = Utility.parseInt(attachment_type.getAttachmentType_id(),new Integer(0));
		param[1] = Utility.trimNull(attachment_type.getAttachmentType_name());
		param[2] = Utility.trimNull(attachment_type.getAttachmentTypeSummary());
		param[3] = Utility.parseInt(attachment_type.getIS_Necessary(),new Integer(0));
		param[4] = Utility.parseInt(attachment_type.getDF_Table_id(), new Integer(0));
		param[5] = Utility.trimNull(attachment_type.getDF_Table_name());
		param[6] = Utility.parseInt(input_operatorCode,new Integer(0));
		super.cudProc(modiSql,param);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.AttachmentTypeLocal#delete(enfo.crm.vo.AttachmentTypeVO, java.lang.Integer)
	 */
	@Override
	public void delete(AttachmentTypeVO attachment_type,Integer input_operatorCode)throws BusiException{
		Object[]params = new Object[2];
		params[0] = Utility.parseInt(attachment_type.getAttachmentType_id(),new Integer(0));
		params[1] = Utility.parseInt(input_operatorCode,new Integer(0));
		super.cudProc(deleteSql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.AttachmentTypeLocal#listByMul(enfo.crm.vo.AttachmentTypeVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList listByMul(AttachmentTypeVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
			IPageList rsList = null;
			Object[] params = new Object[3];
			params[0] = Utility.trimNull(vo.getAttachmentType_id());
			params[1] = Utility.trimNull(vo.getAttachmentType_name());
			params[2] = Utility.trimNull(vo.getDF_Table_id());
			rsList = super.listProcPage(listSql,params,totalColumn,pageIndex,pageSize);
			return rsList;
	}
	
}