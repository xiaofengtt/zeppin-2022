package enfo.crm.contractManage;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.TcoContractPayPlanVO;

public interface TcoContractPayPlanLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local" 
	 * ��ͬ����-�����տ�ƻ�
	 * @param vo
	 * @throws BusiException
	 */
	void append(TcoContractPayPlanVO vo) throws BusiException;

	/**
	 * ��ѯ---�޷�ҳ���տ�ƻ��б�
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	List queryByList(TcoContractPayPlanVO vo) throws BusiException;

	/**
	 * ��ѯ---��ҳ���տ���٣�
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	IPageList queryUnReceive(TcoContractPayPlanVO vo, String[] totalNumber, int pageIndex, int pageSize)
			throws BusiException;
	
	IPageList queryUnReceive(TcoContractPayPlanVO vo, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * �޸�--�տ�ƻ�
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */

	void modi(TcoContractPayPlanVO vo) throws BusiException;

	/**
	 * �޸�--��Ʊ
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	void setInvoice(TcoContractPayPlanVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * ɾ��--�տ�ƻ�
	 * @param vo
	 * @throws BusiException
	 */
	void delete(TcoContractPayPlanVO vo) throws BusiException;

}