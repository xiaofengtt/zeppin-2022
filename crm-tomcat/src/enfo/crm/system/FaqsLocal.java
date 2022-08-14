package enfo.crm.system;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.FaqsVO;

public interface FaqsLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ѯȫ��
	 * 
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listByMul(FaqsVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 
	 * ����
	 * 
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList searchFaqs(FaqsVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ����
	 * 
	 * @param vo
	 * @throws Exception
	 * 
	 * @IN_FAQ_TITLE	NVARCHAR(60)	����
	 * @IN_FAQ_KEYWORDS	NVARCHAR(200)	�ؼ���
	 * @IN_FAQ_CONTENT	NVARCHAR(MAX)	����
	 * @IN_INPUT_MAN	INT	����Ա
	 * 
	 */
	Integer append(FaqsVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ѯ
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	List listBySql(FaqsVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �༭
	 * 
	 * @param vo
	 * @throws Exception
	 */
	void modi(FaqsVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ɾ��
	 * 
	 * @param vo
	 * @throws Exception
	 */
	void delete(FaqsVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��¼֧�ַ�����
	 * 
	 * @param vo
	 * @param flag
	 */
	void countFaq(FaqsVO vo, Integer flag) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ����
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	List listFaqClass(FaqsVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ���json�Ĳ���
	 * @return
	 * @throws Exception
	 */
	String queryClassJosn(FaqsVO vo, java.util.Locale clientLocale) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ���ӷ���
	 * @param vo
	 * @throws Exception
	 */
	String addClass(FaqsVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �༭����
	 * @param vo
	 * @throws Exception
	 */
	void modiClass(FaqsVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ɾ������
	 * @param vo
	 * @throws Exception
	 */
	void deleteClass(FaqsVO vo) throws Exception;

}