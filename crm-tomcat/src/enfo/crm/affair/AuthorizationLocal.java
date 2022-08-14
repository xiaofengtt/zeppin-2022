package enfo.crm.affair;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.AuthorizationCustsVO;
import enfo.crm.vo.AuthorizationVO;

public interface AuthorizationLocal extends IBusiExLocal{

	/**
		 * @ejb.interface-method view-type = "local"
		 * 新增客户授权集
		 * @param vo
		 */
	Integer append(AuthorizationVO vo) throws BusiException;
	//改成无返回值的
	//	public void append(AuthorizationVO vo) throws BusiException{
	//		Object[] params = new Object[4];
	//
	//		params[0] = Utility.trimNull(vo.getCa_name());
	//		params[1] = Utility.trimNull(vo.getCa_description());
	//		params[2] = Utility.parseInt(vo.getManagerID(),null);
	//		params[3] = Utility.parseInt(vo.getInput_man(),null);
	//		
	//		super.cudProc(sql_add_TAuthorization,params);
	//	}

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改客户授权集
	 * @param vo
	 * 
	 */
	void modi(AuthorizationVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除客户授权集
	 * @param vo
	*/
	void delete(AuthorizationVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页查询客户授权集
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pagelist_query(AuthorizationVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询客户授权集
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_query(AuthorizationVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 新增客户授权集-成员管理
	 * @param vo
		@IN_TEAM_ID	nvarchar
		@IN_MANAGERID	nvarchar
		@IN_DESCRIPTION	nvarchar
		@IN_INPUT_MAN	int
	 */
	void append(AuthorizationCustsVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除客户授权集-成员管理
	 * @param vo
		@IN_SERIAL_NO	int
		@IN_INPUT_MAN	int
	 */
	void delete(AuthorizationCustsVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页查询客户授权集-成员管理
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pagelist_query(AuthorizationCustsVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

}