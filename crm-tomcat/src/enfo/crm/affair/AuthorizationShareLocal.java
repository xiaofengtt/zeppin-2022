package enfo.crm.affair;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.AuthorizationShareVO;

public interface AuthorizationShareLocal extends IBusiExLocal{

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询授权信息单条记录  
	 * @param vo
	 * @throws BusiException
	 * @return list
	 */
	List load(Integer serial_no) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 新增授权集分配记录
	 * @param vo
	 */
	Integer append(AuthorizationShareVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页查询授权集授权记录信息
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pagelist_query_authorize(AuthorizationShareVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页查询客户信息共享记录
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pagelist_query_share(AuthorizationShareVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除客户授权集
	 * @param vo
	*/
	void delete(AuthorizationShareVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改授权记录启用状态（启用、禁用）
	 * @param vo
	*/
	void modiStatus(AuthorizationShareVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改客户授权集授权记录
	 * @param vo
	 * 
	 */
	void modi(AuthorizationShareVO vo) throws BusiException;

}