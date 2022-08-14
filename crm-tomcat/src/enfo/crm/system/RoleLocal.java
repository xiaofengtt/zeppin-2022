package enfo.crm.system;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.RoleVO;

public interface RoleLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯȫ��
	 * @param role
	 * @return
	 * @throws Exception
	 * 
	 * @IN_ROLE_ID INT =0,              --��ɫ���(0Ϊȫ��) 
	 * @IN_ROLE_NAME  VARCHAR(20) =NULL --��ɫ����
	 */
	List listBySql(RoleVO role) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ����
	 * @param role
	 * @throws Exception
	 * 
	 * @IN_ROLE_ID	INT	            --��ɫ���
	 * @IN_ROLE_NAME	VARCHAR(16)	--��ɫ����
	 * @IN_SUMMARY	VARCHAR(200)	--˵��
	 * @IN_INPUT_MAN	INT	        --����Ա
	 */
	void append(RoleVO role, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸�
	 * @param role
	 * @throws Exception
	 * 
	 * @IN_ROLE_ID	INT	            --��ɫ���
	 * @IN_ROLE_NAME	VARCHAR(16)	--��ɫ����
	 * @IN_SUMMARY	VARCHAR(200)	--˵��
	 * @IN_INPUT_MAN	INT	        --����Ա
	 */
	void modi(RoleVO role, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ��
	 * @param role
	 * @throws Exception
	 * 
	 * @IN_ROLE_ID	INT	            --��ɫ���
	 * @IN_INPUT_MAN	INT	        --����Ա
	 */
	void delete(RoleVO role, Integer input_operatorCode) throws BusiException;

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
	 * @IN_ROLE_ID INT =0,              --��ɫ���(0Ϊȫ��) 
	 * @IN_ROLE_NAME  VARCHAR(20) =NULL --��ɫ����
	 * 
	 */
	IPageList listByMul(RoleVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �������Ȩ��
	 * SP_ADD_TROLERIGHT_ALL  @IN_ROLE_ID INT,          --��ɫ���
							  @IN_MENU_ID VARCHAR (10),    --
							  @IN_MENU_NAME VARCHAR(60),   --
							  @IN_FLAG INT,                --
							  @IN_INPUT_MAN INT,           --
							  @OUT_RET_CODE INT OUTPUT     --
	 */
	void updateRights(RoleVO vo, String menu_id, int flag, String menu_name) throws Exception;

}