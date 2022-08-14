package enfo.crm.system;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.AttachmentTypeVO;

public interface AttachmentTypeLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯȫ��
	 * @param attachment_type
	 * @return
	 * @throws Exception
	 * @IN_ATTACHMENT_ID  	INT				 	--�������id
	 * @IN_ATTACHMENT_NAME  VARCHAR(20) =NULL 	--�����������
	 * @IN_DF_TABLE_NAME	VARCHAR(60)			--��Ӧ����
	 */
	List listBySql(AttachmentTypeVO attachment_type) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ����
	 * @param attachment_type
	 * @throws Exception
	 * 
	 * @IN_ATTACHMENT_TYPE_ID		INT	            --���������
	 * @IN_ATTACHMENT_TYPE_NAME		VARCHAR(30)		--�����������
	 * @IN_ATTACHMENT_TYPE_SUMMARY	VARCHAR(200)	--˵��
	 */
	void append(AttachmentTypeVO attachment_type, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸�
	 * @param attachment_type
	 * @throws Exception
	 * 
	 * @IN_ATTACHMENT_TYPE_ID		INT	         	--���������
	 * @IN_ATTACHMENT_TYPE_NAME		VARCHAR(16)		--�����������
	 * @IN_ATTACHMENT_TYPE_SUMMARY	VARCHAR(200)	--˵��
	 * @IN_INPUT_MAN				INT	        	--����Ա
	 */
	void modi(AttachmentTypeVO attachment_type, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ��
	 * @param attachment_type
	 * @throws Exception
	 * 
	 * @IN_ATTACHMENT_TYPE_ID	INT	    --���������
	 * @IN_INPUT_MAN			INT	    --����Ա
	 */
	void delete(AttachmentTypeVO attachment_type, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ҳ��ѯ
	 * @param vo
	 * @param input_bookCode
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 * 
	 * @IN_ATTACHMENT_TYPE_NAME	VARCHAR(20)	--�����������
	 * @IN_DF_TABLE_NAME		VARCHAR(60)	--��Ӧ����
	 */
	IPageList listByMul(AttachmentTypeVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

}