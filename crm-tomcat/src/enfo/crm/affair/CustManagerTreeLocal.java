package enfo.crm.affair;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.CustManagerTreeMembersVO;
import enfo.crm.vo.TcustmanagertreeVO;

public interface CustManagerTreeLocal extends IBusiExLocal{

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��Ӿ������Ա
	 * @param vo
	 * @throws BusiException
	 */
	void appendCustManagerTreeMember(CustManagerTreeMembersVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ���������Ա
	 * @param vo
	 * @throws BusiException
	 */
	void delCustManagerTreeMember(CustManagerTreeMembersVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯĳ����ͻ������б�
	 * @param vo(GROUPID,INPUTMAN)
	 * @return list
	 * @throws BusiException
	 */
	IPageList queryAll(CustManagerTreeMembersVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �������� hesl 20110516 �������ֶ�
	 * @param dept
	 * @param TcustmanagertreeVO
	 * @throws Exception
	 */
	void level_tree_append(TcustmanagertreeVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸Ŀͻ�Ⱥ��
	 * @param vo
	 * @throws BusiException
	 */
	void modiCustGroup(TcustmanagertreeVO vo) throws BusiException;

}