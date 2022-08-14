package enfo.crm.system;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.RolerightVO;

public interface RolerightLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询TROLERIGHT中信息 
	 * */
	List listRoleRight(Integer role_id) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 获取角色表TROLEIGHT信息
	 * */
	List listRole(Integer role_id) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询菜单表信息MENUINFO中的信息	 * 
	 * */
	IPageList listProcAll(RolerightVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 *查询TFUNCTYPE中的信息 
	 * */
	List listFunc_type(String menu_id) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param menu_id
	 * @return
	 */
	boolean hasMenu(String menu_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param menu_id
	 * @param func_id
	 * @return
	 */
	boolean hasFunc(String menu_id, Integer func_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 *  SP_ADD_TROLERIGHT  @IN_ROLE_ID INT,
						@IN_MENU_ID VARCHAR(10),
						@IN_FUNC_ID INT,
						@IN_INPUT_MAN INT,
						@OUT_RET_CODE INT OUTPUT
	   SP_DEL_TROLERIGHT @IN_ROLE_ID INT,
					   @IN_MENU_ID VARCHAR(10),
					   @IN_FUNC_ID INT,
					   @IN_INPUT_MAN INT,
					   @OUT_RET_CODE INT OUTPUT                                    
	 */
	void update(Integer roleid, String menuid, Integer funcid, Integer input_name, Integer flag) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param menuid
	 * @throws Exception
	 */
	void listFuncType(String menuid) throws Exception;

	/**
	 * 	@ejb.interface-method view-type = "local"
	 *  SP_QUERY_ALLCHILDREN_TMENUINFO @IN_MENU_ID VARCHAR(10) = NULL,
										@IN_MENU_NAME NVARCHAR(60) = NULL
	
	 * @author Administrator
	 *
	 * 更改所生成类型注释的模板为
	 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
	 */
	IPageList listAll2(RolerightVO vo, int pageIndex, int pageSize) throws BusiException;

}