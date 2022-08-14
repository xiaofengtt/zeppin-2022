package enfo.crm.contractManage;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.TcoContractMaintainVO;

public interface TcoContractMaintainLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ά����ͬ����-����ά����ͬ��Ϣ
	 * @param vo
	 * @throws BusiException
	 * 
	 */
	Integer append(TcoContractMaintainVO vo) throws BusiException;

	/**
	 * ��ѯ--��ҳ��ά����ͬ�Ǽ��б�
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	IPageList queryByPageList(TcoContractMaintainVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * ��ѯ---�޷�ҳ��ά����ͬ�Ǽ��б�
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	List queryByList(TcoContractMaintainVO vo) throws BusiException;

	/**
	 * �޸�--ά����ͬ�Ǽ�
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */

	void modi(TcoContractMaintainVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * ɾ��--ά����ͬ�Ǽ�
	 * @param vo
	 * @throws BusiException
	 */
	void delete(TcoContractMaintainVO vo) throws BusiException;

	/**
	 * ���--ά����ͬ�Ǽ�
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	void check(TcoContractMaintainVO vo) throws BusiException;

	/**
	 * ��ѯ---��ҳ��ά�����տ���٣�
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	IPageList queryUnReceive(TcoContractMaintainVO vo, String[] totalNum, int pageIndex, int pageSize)
			throws BusiException;
	
	IPageList queryUnReceive(TcoContractMaintainVO vo, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * �޸�--��Ʊ
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	void setInvoice(TcoContractMaintainVO vo) throws BusiException;

}