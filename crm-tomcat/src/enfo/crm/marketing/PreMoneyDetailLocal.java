package enfo.crm.marketing;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.PreMoneyDetailVO;

public interface PreMoneyDetailLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ����ԤԼ������Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Integer addPreMoneyDetail(PreMoneyDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ɾ��ԤԼ������Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void delPreMoneyDetail(PreMoneyDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * �޸�ԤԼ������Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void modiPreMoneyDetail(PreMoneyDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * �˿�ԤԼ������Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void refundPreMoneyDetail(PreMoneyDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ��ѯԤԼ������Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryPreMoneyDetail(PreMoneyDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ��ѯԤԼ������Ϣ
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList queryPreMoneyDetail(PreMoneyDetailVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 *
	 * �޸�ԤԼ���˵ķ��Ͷ�����Ϣ
	 * @param vo
	 */
	void updateSMS(PreMoneyDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �����Ϲ���ͬ��¼
	 * @param vo
	 * @throws Exception
	 */
	void relateContract(Integer serial_no, Integer contract_serial_no) throws Exception;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ��ѯ�ʽ�֤ʵ����Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryMoneyConfirmation(PreMoneyDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * ��ѯ�ʽ�֤ʵ����Ϣ:��TMONEYDATA���в�
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryMoneyDataSerial(PreMoneyDetailVO vo) throws BusiException;

}