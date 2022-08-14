package enfo.crm.contractManage;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.TcoContractPointsVO;

public interface TcoContractPointsLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local" 
	 * ��ͬ����-���Ӻ�ͬҪ��
	 * @param vo
	 * @throws BusiException
	 */
	void append(TcoContractPointsVO vo) throws BusiException;

	/**
	 * ��ѯ---�޷�ҳ����ͬҪ���б�
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	List queryByList(TcoContractPointsVO vo) throws BusiException;

	/**
	 * �޸�--��ͬҪ��
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	void modi(TcoContractPointsVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * ɾ��--��ͬҪ��
	 * @param vo
	 * @throws BusiException
	 */
	void delete(TcoContractPointsVO vo) throws BusiException;

}