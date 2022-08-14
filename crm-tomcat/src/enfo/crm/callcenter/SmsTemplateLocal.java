package enfo.crm.callcenter;

import java.util.Map;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.SmsTemplateVO;

public interface SmsTemplateLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��Ӷ���ģ����Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void append(SmsTemplateVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��Ӷ���ģ����Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void addSms(SmsTemplateVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��Ӷ���ģ����Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void modiSms(SmsTemplateVO vo) throws BusiException;

	/**
	 *  @ejb.interface-method view-type = "local"
	 * �༭����ģ����Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void modi(SmsTemplateVO vo) throws BusiException;

	/**
	 *  @ejb.interface-method view-type = "local"
	 *  ɾ������ģ����Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void delete(SmsTemplateVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��õ������󷽷�
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Map load(SmsTemplateVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ����ģ���б�
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listAll(SmsTemplateVO vo, int pageIndex, int pageSize) throws BusiException;

}