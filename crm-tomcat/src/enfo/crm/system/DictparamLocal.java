package enfo.crm.system;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.DictparamVO;

public interface DictparamLocal extends IBusiExLocal {

	/** 
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ��ӡģ������
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	IPageList getPrintCatalog(DictparamVO vo, int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ��ӡģ��
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	IPageList getPrintTemplate(DictparamVO vo, int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸����ñ�־
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	void modiPrintTemplateEnable(DictparamVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸Ĵ�ӡģ��
	 * @param vo
	 * @return
	 * @throws Exception 
	 */
	void modiPrintTemplate(DictparamVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ��ӡҪ��
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	IPageList getPrintElement(DictparamVO vo, int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ������ӡģ��
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	void addPrintTemplate(DictparamVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯϵͳ������Ϣ
	 */
	List listSysControl(DictparamVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸�ϵͳ������Ϣ
	 */
	void modiSysControl(DictparamVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ����CRM
	 */
	List listDictparamAll(DictparamVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ����INTRUST
	 */
	List listDictparamAllIntrust(DictparamVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ����INTRUSTʡ��
	 */
	List listDictparamAllIntrustProvince(DictparamVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ������
	 */
	void delDictparam(DictparamVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸Ĳ���
	 */
	void modiDictparam(DictparamVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ADD BY TSG 20080606
	 * �����޸�ADDITIVE_VALUE��ֵ
	 *   	@IN_SERIAL_NO
			@IN_TYPE_ID
			@IN_TYPE_NAME
			@IN_TYPE_VALUE
			@IN_TYPE_CONTENT
			@IN_INPUT_MAN
			@IN_ADDITIVE_VALUE
			@IN_AML_VALUE
	 * */
	void modiDictparam1(DictparamVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 *  @ejb.interface-method view-type = "local"
	 *  ��������
	 */
	void addDictparam(DictparamVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯϵͳ����Value
	 */
	List listSysControlValue(DictparamVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ҳ��ѯϵͳ������Ϣ
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listByMul(DictparamVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ҳ��ѯϵͳ������Ϣ
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listDictparamAll(DictparamVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ҳ��ѯ���������Ϣ
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listReportAll(DictparamVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �����������
	 */
	void addReportparam(DictparamVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ���������
	 */
	void delReportparam(DictparamVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ��Ŀ
	 */
	IPageList listSummaryByMul(DictparamVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	IPageList listFzType(DictparamVO vo, String[] totalColumn, int pageIndex, int pageSize);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 */
	List listTotherbusiFlag(DictparamVO vo);

	/**
	 * @ejb.interface-method view-type = "local"
	 * add by tsg 2008-06-27
	 * 
	 * ����ҵ����֧�����Ϣ
	 * 
	 * SP_ADD_TOTHERBUSIFLAG @IN_BOOK_CODE     INT,
							 @IN_BUSI_ID       VARCHAR(10),
							 @IN_SUB_CODE_1_1  VARCHAR(6),
							 @IN_SUB_CODE_2_1  VARCHAR(6),
							 @IN_INPUT_MAN     INT
	 * */
	void updateTotherbusiFlag(DictparamVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ⲿ����
	 */
	IPageList pagelistDictparamAll(DictparamVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 *  @ejb.interface-method view-type = "local"
	 *  �����ⲿ����
	 */
	void addWebLinks(DictparamVO vo, Integer input_operatorCode) throws BusiException;

}