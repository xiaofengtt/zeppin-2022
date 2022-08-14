package enfo.crm.system;

import java.util.List;
import java.util.Map;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.MenuInfoVO;

public interface MenuInfoLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询单条记录
	 * @param menu_id,parent_id
	 * @return
	 * @throws BusiException
	 */
	List listBySig(String menu_id, String parent_id) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页查询记录
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listByMul(MenuInfoVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改
	 * @param vo
	 * @param input_operatorCode
	 * @throws BusiException
	 */
	void modi(MenuInfoVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询菜单树
	 */
	List listMenuRight(Integer input_opCode, String menu_id, String parent_id, Integer languageType)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param menu_id
	 * @return
	 * @throws BusiException
	 */
	List listMenuInfo(String menu_id, Integer languageType) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param menu_id
	 * @param viewStr
	 * @return
	 * @throws BusiException
	 */
	List listMenuViews(String menu_id, String viewStr) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param menu_id
	 * @param viewStr
	 * @return
	 * @throws BusiException
	 */
	Map listMenuView(String menu_id, String viewStr) throws BusiException;

}