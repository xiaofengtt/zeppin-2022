package enfo.crm.contractManage;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.TcoTeamMemberVO;

public interface TcoTeamMemberLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 合同管理-增加项目组成员信息
	 * @param vo
	 * @throws BusiException
	 */
	void append(TcoTeamMemberVO vo) throws BusiException;

	/**
	 * 查询--分页（项目组成员列表）
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	IPageList queryByPageList(TcoTeamMemberVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * 查询---无分页（项目组成员列表）
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	List queryByList(TcoTeamMemberVO vo) throws BusiException;

	/**
	 * 修改--项目组成员
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	void modi(TcoTeamMemberVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 删除--项目组成员
	 * @param vo
	 * @throws BusiException
	 */
	void delete(TcoTeamMemberVO vo) throws BusiException;

}