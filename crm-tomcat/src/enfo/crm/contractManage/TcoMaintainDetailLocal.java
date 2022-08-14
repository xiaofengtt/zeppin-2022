package enfo.crm.contractManage;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.TcoMaintainDetailVO;

public interface TcoMaintainDetailLocal extends IBusiExLocal {

	/**
		 * @ejb.interface-method view-type = "local"
		 * ��ͬ����-���Ӻ�ͬ��Ϣ
		 * @param vo
		 * @throws BusiException
		 * 
	 * 
		 */
	void append(TcoMaintainDetailVO vo) throws BusiException;

	/**
	 * ��ѯ---�޷�ҳ��������ϸ�б�
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	List queryByList(TcoMaintainDetailVO vo) throws BusiException;

	/**
	 * �޸�--������ϸ
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	void modi(TcoMaintainDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * ɾ��--������ϸ
	 * @param vo
	 * @throws BusiException
	 */
	void delete(TcoMaintainDetailVO vo) throws BusiException;

}