package enfo.crm.system;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;

public interface PortalLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type="local"
	 * 修改 Protal 用户状态信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void updatePortalUserState(String state, Integer opCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local" 
	 * 修改 Portal信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void updatePortalAdd(String portalCodes, Integer opCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 修改Portal CLOSE信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void updatePortalClose(String portalCode, Integer opCode, String visiable) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 修改Portal Collapse信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void updatePortalCollapse(String portalCode, Integer opCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 修改Portal Collapse信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void updateProtalExpand(String portalCode, Integer opCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 查询Portal信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryMyPortal(Integer bookCode, Integer opCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 查询首页portal内功能操作
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryPortalMenu3(Integer op_code, String language) throws BusiException;

}