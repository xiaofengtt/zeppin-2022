package enfo.crm.system;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.AttachmentTypeVO;

public interface AttachmentTypeLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询全部
	 * @param attachment_type
	 * @return
	 * @throws Exception
	 * @IN_ATTACHMENT_ID  	INT				 	--附件类别id
	 * @IN_ATTACHMENT_NAME  VARCHAR(20) =NULL 	--附件类别名称
	 * @IN_DF_TABLE_NAME	VARCHAR(60)			--对应表名
	 */
	List listBySql(AttachmentTypeVO attachment_type) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 新增
	 * @param attachment_type
	 * @throws Exception
	 * 
	 * @IN_ATTACHMENT_TYPE_ID		INT	            --附件类别编号
	 * @IN_ATTACHMENT_TYPE_NAME		VARCHAR(30)		--附件类别名称
	 * @IN_ATTACHMENT_TYPE_SUMMARY	VARCHAR(200)	--说明
	 */
	void append(AttachmentTypeVO attachment_type, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改
	 * @param attachment_type
	 * @throws Exception
	 * 
	 * @IN_ATTACHMENT_TYPE_ID		INT	         	--附件类别编号
	 * @IN_ATTACHMENT_TYPE_NAME		VARCHAR(16)		--附件类别名称
	 * @IN_ATTACHMENT_TYPE_SUMMARY	VARCHAR(200)	--说明
	 * @IN_INPUT_MAN				INT	        	--操作员
	 */
	void modi(AttachmentTypeVO attachment_type, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除
	 * @param attachment_type
	 * @throws Exception
	 * 
	 * @IN_ATTACHMENT_TYPE_ID	INT	    --附件类别编号
	 * @IN_INPUT_MAN			INT	    --操作员
	 */
	void delete(AttachmentTypeVO attachment_type, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页查询
	 * @param vo
	 * @param input_bookCode
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 * 
	 * @IN_ATTACHMENT_TYPE_NAME	VARCHAR(20)	--附件类别名称
	 * @IN_DF_TABLE_NAME		VARCHAR(60)	--对应表名
	 */
	IPageList listByMul(AttachmentTypeVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

}