package enfo.crm.contractManage;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.TcoContractMoneyDetailVO;

public interface TcoContractMoneyDetailLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ͬ����-���Ӻ�ͬ��Ϣ
	 * @param vo
	 * @throws BusiException
	 * 
	 */
	void append(TcoContractMoneyDetailVO vo) throws BusiException;

	/**
	 * ��ѯ---�޷�ҳ��������ϸ�б�
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	List queryByList(TcoContractMoneyDetailVO vo) throws BusiException;

	/**
	 * �޸�--������ϸ
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	void modi(TcoContractMoneyDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * ɾ��--������ϸ
	 * @param vo
	 * @throws BusiException
	 */
	void delete(TcoContractMoneyDetailVO vo) throws BusiException;

}