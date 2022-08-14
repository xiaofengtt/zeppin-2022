package enfo.crm.contractManage;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.TcoTeamInfoVO;

public interface TcoTeamInfoLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 合同管理-增加项目组信息
	 * @param vo
	 * @throws BusiException
	 * 
	 */
	Integer append(TcoTeamInfoVO vo) throws BusiException;

	/**
	 * 查询--分页（项目组信息列表）
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	IPageList queryByPageList(TcoTeamInfoVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * 查询---无分页（项目组信息列表）
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	List queryByList(TcoTeamInfoVO vo) throws BusiException;

	/**
	 * 修改--项目组信息
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	void modi(TcoTeamInfoVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 删除--项目组信息
	 * @param vo
	 * @throws BusiException
	 */
	void delete(TcoTeamInfoVO vo) throws BusiException;

}