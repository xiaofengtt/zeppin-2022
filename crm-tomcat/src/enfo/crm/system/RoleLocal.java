package enfo.crm.system;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.RoleVO;

public interface RoleLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询全部
	 * @param role
	 * @return
	 * @throws Exception
	 * 
	 * @IN_ROLE_ID INT =0,              --角色编号(0为全部) 
	 * @IN_ROLE_NAME  VARCHAR(20) =NULL --角色名称
	 */
	List listBySql(RoleVO role) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 新增
	 * @param role
	 * @throws Exception
	 * 
	 * @IN_ROLE_ID	INT	            --角色编号
	 * @IN_ROLE_NAME	VARCHAR(16)	--角色名称
	 * @IN_SUMMARY	VARCHAR(200)	--说明
	 * @IN_INPUT_MAN	INT	        --操作员
	 */
	void append(RoleVO role, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改
	 * @param role
	 * @throws Exception
	 * 
	 * @IN_ROLE_ID	INT	            --角色编号
	 * @IN_ROLE_NAME	VARCHAR(16)	--角色名称
	 * @IN_SUMMARY	VARCHAR(200)	--说明
	 * @IN_INPUT_MAN	INT	        --操作员
	 */
	void modi(RoleVO role, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除
	 * @param role
	 * @throws Exception
	 * 
	 * @IN_ROLE_ID	INT	            --角色编号
	 * @IN_INPUT_MAN	INT	        --操作员
	 */
	void delete(RoleVO role, Integer input_operatorCode) throws BusiException;

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
	 * @IN_ROLE_ID INT =0,              --角色编号(0为全部) 
	 * @IN_ROLE_NAME  VARCHAR(20) =NULL --角色名称
	 * 
	 */
	IPageList listByMul(RoleVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 添加所有权限
	 * SP_ADD_TROLERIGHT_ALL  @IN_ROLE_ID INT,          --角色编号
							  @IN_MENU_ID VARCHAR (10),    --
							  @IN_MENU_NAME VARCHAR(60),   --
							  @IN_FLAG INT,                --
							  @IN_INPUT_MAN INT,           --
							  @OUT_RET_CODE INT OUTPUT     --
	 */
	void updateRights(RoleVO vo, String menu_id, int flag, String menu_name) throws Exception;

}