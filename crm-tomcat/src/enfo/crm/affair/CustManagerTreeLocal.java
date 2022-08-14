package enfo.crm.affair;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.CustManagerTreeMembersVO;
import enfo.crm.vo.TcustmanagertreeVO;

public interface CustManagerTreeLocal extends IBusiExLocal{

	/**
	 * @ejb.interface-method view-type = "local"
	 * 添加经理级别成员
	 * @param vo
	 * @throws BusiException
	 */
	void appendCustManagerTreeMember(CustManagerTreeMembersVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除经理级别成员
	 * @param vo
	 * @throws BusiException
	 */
	void delCustManagerTreeMember(CustManagerTreeMembersVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询某级别客户经理列表
	 * @param vo(GROUPID,INPUTMAN)
	 * @return list
	 * @throws BusiException
	 */
	IPageList queryAll(CustManagerTreeMembersVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 新增树级 hesl 20110516 新增了字段
	 * @param dept
	 * @param TcustmanagertreeVO
	 * @throws Exception
	 */
	void level_tree_append(TcustmanagertreeVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改客户群组
	 * @param vo
	 * @throws BusiException
	 */
	void modiCustGroup(TcustmanagertreeVO vo) throws BusiException;

}