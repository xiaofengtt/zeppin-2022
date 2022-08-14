package enfo.crm.contractManage;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.TcoContractRecordVO;

public interface TcoContractRecordLocal extends IBusiExLocal {

	/**
		 * @ejb.interface-method view-type = "local"
		 * �ͻ�ά������-���ӿͻ�ά����Ϣ
		 * @param vo
		 * @throws BusiException
		 * 
	 */
	void append(TcoContractRecordVO vo) throws BusiException;

	/**
	 * ��ѯ--��ҳ���ͻ�ά����¼�б�
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	IPageList queryByPageList(TcoContractRecordVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * ��ѯ---�޷�ҳ���ͻ�ά����¼�б�
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	List queryByList(TcoContractRecordVO vo) throws BusiException;

	/**
	 * �޸�--�ͻ�ά����¼
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	void modi(TcoContractRecordVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * ɾ��--�ͻ�ά����¼
	 * @param vo
	 * @throws BusiException
	 */
	void delete(TcoContractRecordVO vo) throws BusiException;

}