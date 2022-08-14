package enfo.crm.contractManage;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.TcoTeamInfoVO;

public interface TcoTeamInfoLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ͬ����-������Ŀ����Ϣ
	 * @param vo
	 * @throws BusiException
	 * 
	 */
	Integer append(TcoTeamInfoVO vo) throws BusiException;

	/**
	 * ��ѯ--��ҳ����Ŀ����Ϣ�б�
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	IPageList queryByPageList(TcoTeamInfoVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * ��ѯ---�޷�ҳ����Ŀ����Ϣ�б�
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	List queryByList(TcoTeamInfoVO vo) throws BusiException;

	/**
	 * �޸�--��Ŀ����Ϣ
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	void modi(TcoTeamInfoVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * ɾ��--��Ŀ����Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void delete(TcoTeamInfoVO vo) throws BusiException;

}