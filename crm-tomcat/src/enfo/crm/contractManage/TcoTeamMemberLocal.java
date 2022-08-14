package enfo.crm.contractManage;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.TcoTeamMemberVO;

public interface TcoTeamMemberLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ͬ����-������Ŀ���Ա��Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void append(TcoTeamMemberVO vo) throws BusiException;

	/**
	 * ��ѯ--��ҳ����Ŀ���Ա�б�
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	IPageList queryByPageList(TcoTeamMemberVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * ��ѯ---�޷�ҳ����Ŀ���Ա�б�
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	List queryByList(TcoTeamMemberVO vo) throws BusiException;

	/**
	 * �޸�--��Ŀ���Ա
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	void modi(TcoTeamMemberVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * ɾ��--��Ŀ���Ա
	 * @param vo
	 * @throws BusiException
	 */
	void delete(TcoTeamMemberVO vo) throws BusiException;

}