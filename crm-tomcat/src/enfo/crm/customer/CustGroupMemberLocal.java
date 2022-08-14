package enfo.crm.customer;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.CustGroupMemberVO;

public interface CustGroupMemberLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ӿͻ�Ⱥ���Ա
	 * @param vo
	 * @throws BusiException
	 */
	void appendCustGroupMember(CustGroupMemberVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ���ͻ�Ⱥ���Ա
	 * @param vo
	 * @throws BusiException
	 */
	void delCustGroupMember(CustGroupMemberVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ���пͻ���Ա
	 * @param vo(GROUPID,INPUTMAN)
	 * @return list
	 * @throws BusiException
	 */
	IPageList queryAll(CustGroupMemberVO vo, int pageIndex, int pageSize) throws BusiException;

}