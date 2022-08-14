package enfo.crm.cont;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.ContractManagementVO;

public interface ContractManagementLocal extends IBusiExLocal {

	/**
	 * ��ѯ--��ҳ����ͬ�Ǽ��б�
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	IPageList queryByPageList(ContractManagementVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * ��ѯ---�޷�ҳ����ͬ�Ǽ��б�
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	List queryByList(ContractManagementVO vo) throws BusiException;

	/**
	 * ���--��ͬ�Ǽ�
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	Integer addContManagement(ContractManagementVO vo) throws BusiException;

	/**
	 * ������ͬͳ��
	 * @ejb.interface-method view-type="local"
	 * @param product_id : ��ƷID
	 * @param service_man : �ͻ�����
	 * @param flag : ��ͬ״̬
	 * @param input_man : ����Ա
	 * @param summary : ��ע
	 * @throws BusiException
	 */
	void addContractStat(Integer product_id, Integer service_man, Integer flag, Integer input_man, String summary,
			Integer serial_no) throws BusiException;

	/**
	 * ��ѯ��ͬͳ��
	 * @ejb.interface-method view-type="local"
	 * @param product_id : ��ƷID
	 * @return
	 * @throws BusiException
	 */
	List queryContractStat(Integer product_id) throws BusiException;

	/**
	 * ��ѯ�ͻ��������µĺ�ͬ״̬������
	 * @ejb.interface-method view-type="local"
	 * @param service_man
	 * @return
	 * @throws BusiException
	 */
	List queryContractStatResult(Integer service_man) throws BusiException;

	/**
	 * �޸�--��ͬ�Ǽ�
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	void modiContManagement(ContractManagementVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * ɾ��--��ͬ�Ǽ�
	 * @param vo
	 * @throws BusiException
	 */
	void deleteContManagement(ContractManagementVO vo) throws BusiException;

}