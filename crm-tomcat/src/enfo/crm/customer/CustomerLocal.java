package enfo.crm.customer;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.fileupload.DestinationTableVo;
import enfo.crm.vo.AmCustInfoVO;
import enfo.crm.vo.CustLevelVO;
import enfo.crm.vo.CustomerInfoVO;
import enfo.crm.vo.CustomerVO;

public interface CustomerLocal extends IBusiExLocal{

	/**
	 * @ejb.interface-method view-type = "local"
	 * �ͻ�������Ϣ����
	 * @param vo 
	 * @throws BusiException          
	 */
	Integer append(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �ͻ�������Ϣ���� ��ί���� �޷ֲ�ʽͬ��
	 * @param vo 
	 * @throws BusiException          
	 */
	Integer append2(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �ͻ�������Ϣ���� ��ί���� �޷ֲ�ʽͬ��
	 * @param vo 
	 * @throws BusiException          
	 */
	Integer append_jh(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �޸Ŀͻ�������Ϣ SP_MODI_TCUSTOMERINFO
	 * 
	 * @IN_CUST_ID INT,
	 * @IN_CUST_NAME VARCHAR(100),
	 * @IN_CUST_TEL VARCHAR(20),
	 * @IN_POST_ADDRESS VARCHAR(60),
	 * @IN_POST_CODE VARCHAR(6),
	 * @IN_CARD_TYPE VARCHAR(10),
	 * @IN_CARD_ID VARCHAR(40),
	 * @IN_AGE INT,
	 * @IN_SEX INT,
	 * @IN_O_TEL VARCHAR(20),
	 * @IN_H_TEL VARCHAR(20),
	 * @IN_MOBILE VARCHAR(100),
	 * @IN_BP VARCHAR(20),
	 * @IN_FAX VARCHAR(20),
	 * @IN_E_MAIL VARCHAR(30),
	 * @IN_CUST_TYPE INT,
	 * @IN_TOUCH_TYPE VARCHAR(10),
	 * @IN_CUST_SOURCE VARCHAR(10),
	 * @IN_SUMMARY VARCHAR(200),
	 * @IN_INPUT_MAN INT,
	 * @IN_CUST_NO VARCHAR(8),
	 * @IN_LEGAL_MAN VARCHAR(20),
	 * @IN_LEGAL_ADDRESS VARCHAR(60),
	 * @IN_BIRTHDAY INT,
	 * @IN_POST_ADDRESS2 VARCHAR(60),
	 * @IN_POST_CODE2 VARCHAR(6),
	 * @IN_PRINT_DEPLOY_BILL INT,
	 * @IN_PRINT_POST_INFO INT,
	 * @IN_IS_LINK INT,
	 * @IN_SERVICE_MAN INT = NULL,
	 * @IN_VIP_CARD_ID VARCHAR(20) = NULL, -- VIP�����
	 * @IN_VIP_DATE INT = NULL, -- VIP��������
	 * @IN_HGTZR_BH VARCHAR(20) = NULL,
	 * @IN_VOC_TYPE VARCHAR(10) = '' -- ����ְҵ/������ҵ���(1142/2142)
	 * @IN_CARD_VALID_DATE INT �ͻ����֤����Ч����8λ���ڱ�ʾ
	 * @IN_COUNTRY VARCHAR(10) �ͻ�����(9901)
	 * @IN_JG_CUST_TYPE VARCHAR(10) �����ͻ����(9921)������CUST_TYPE=2����ʱ��Ч
	 * @IN_CONTACT_MAN VARCHAR(30) = NULL --�����ͻ���ϵ��
	 * @IN_LINK_TYPE INT, -- ��������(1302)
	 * @IN_LINK_GD_MONEY DECIMAL(16,3), -- �ɶ�Ͷ��������й�˾���
	 */
	void save_jh(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ӿͻ�������Ϣ
	 * @param procSql
	 * @param params
	 * @param outParamPos
	 * @param outParamType
	 * @return ret
	 */
	Object cudProcAdd(String procSql, Object[] params, int outParamPos, int outParamType, InputStream inputStream)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸Ŀͻ�������Ϣ
	 * @param procSql
	 * @param params
	 * @param outParamPos
	 * @param outParamType
	 * @return ret
	 */
	Object cudProcEdit(String procSql, Object[] params, InputStream inputStream) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸Ŀͻ�������Ϣ
	 * @param procSql
	 * @param params
	 * @param outParamPos
	 * @param outParamType
	 * @return ret
	 */
	Object cudProcEdit1(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �ͻ���Ϣ�ϲ�����
	 * @param vo
	 * @throws BusiException
	 */
	void unite(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �����Ϲ�,�깺�Ⱥ�ͬ¼��ʱ�� ѡ��ͻ��Ĳ�ѯ��
	 * SP_QUERY_TCustomers_LOAD_2   @IN_CUST_ID 		INT,
								  @IN_CUST_NO    	NVARCHAR(8),
								  @IN_CUST_NAME  	NVARCHAR(80),
								  @IN_VIP_CARD_ID	NVARCHAR(20)   = NULL,  --VIP�����
								  @IN_CARD_ID       NVARCHAR(20),
								  @IN_HGTZR_BH      NVARCHAR(20)   = NULL,  --�ϸ�Ͷ���˱��
								  @IN_IS_DEAL    	INT 		   =0,
								  @IN_IS_LINK       INT            = NULL,
								  @IN_INPUT_MAN 	INT 		   = NULL
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryAllCustomer(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ֤���쵽�ڵĿͻ���Ϣ����ҳ��ʾ��
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @throws BusiException
	 * @return rsList
	 */
	IPageList queryCardValidDate(CustomerVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ���ͻ�������Ϣ
	 * @param vo 
	 * @throws BusiException
	 */
	void delete(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸Ŀͻ�������Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void modify(CustomerVO vo) throws BusiException;
	void modify_m(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸Ŀͻ�������Ϣ �޷ֲ�ʽͬ��
	 * @param vo
	 * @throws BusiException
	 */
	void modify2(CustomerVO vo) throws BusiException;
	void modify2_m(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ����֤�����롢�ͻ����������ѯ��
	 * @param vo
	 * @throws BusiException
	 * @return list
	 */
	List queryByCustNo(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ����ϵĻ�����Ϣ������ҳ��ʾ
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @param sQuery
	 * @throws BusiException
	 * @return rsList
	*/
	IPageList listProcAll(CustomerVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�����ͻ����ϵĻ�����Ϣ������ҳ��ʾ
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @throws BusiException
	 * @return rsList
	 */
	IPageList listNewCustAll(CustomerVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ����ϵĻ�����Ϣ�����б���ʾ
	 * @param vo
	 * @throws BusiException
	 * @return list
	 */
	List listProcAll(CustomerVO vo) throws BusiException;
	List listProcAll_m(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ÿͻ�ͼƬ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	InputStream getInputStream(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ����ϵĻ�����Ϣ������ҳ��ʾ(�����)
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @param sQuery
	 * @throws BusiException
	 * @return rsList
	 */
	IPageList listProcAllExt(CustomerVO vo, int pageIndex, int pageSize) throws BusiException;
	
	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ����ϵĻ�����Ϣ������ҳ��ʾ(�����)
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @param sQuery
	 * @throws BusiException
	 * @return rsList
	 */
	IPageList listProcAllExt_C(CustomerVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param totalValue
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listProcAllExt(CustomerVO vo, String[] totalValue, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ����ϵĻ�����Ϣ�����б���ʾ(�����)
	 * @param vo
	 * @return list
	 * @throws BusiException
	 */
	List listProcAllExt(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ö������ѯ����
	 * @param vo
	 * @return
	 */
	Object[] getParmas(CustomerVO vo);

	/**
	 * @ejb.interface-method view-type = "local"
	 * �ͻ���Ϣ��ѯ������¼
	 * @param vo(cust_id, input_man)
	 * @throws BusiException
	 * @return list
	 */
	List listByControl(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ���Ϣ���ض���ֵ  
	 * @param vo
	 * @throws BusiException
	 * @return list
	 */
	List listCustomerLoad(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �ͻ��ϲ���ѯ
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @throws BusiException
	 * @return list
	 */
	IPageList queryRepeatCustomers(CustomerVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws Exception
	 */
	void addCustLevel(CustLevelVO vo) throws Exception;


	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws Exception
	 */
	void delCustLevel(CustLevelVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listCustLevel(CustLevelVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ���빦��
	 * @param vo
	 * @throws BusiException
	 */
	void importCustomer(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList getCustomerMaintenanceRecord(CustomerVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	List getCustomerMaintenanceRecord(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * update by guifeng 2008-08-25 �޸Ŀͻ���Ϣ SP_AML_MODI_CUST
	 * @IN_CUST_ID
	 * @IN_POST_ADDRESS
	 * @IN_CUST_TEL
	 * @IN_POST_ADDRESS2
	 * @IN_CARD_TYPE
	 * @IN_CARD_ID
	 * @IN_VOC_TYPE
	 * @IN_CARD_VALID_DATE
	 * @IN_COUNTRY
	 * @IN_JG_CUST_TYPE
	 * @IN_INPUT_MAN,
	 */
	void modi2(AmCustInfoVO amvo, CustomerInfoVO vo) throws Exception;

	//��������ͳ�� 1.������
	/**
	 * @ejb.interface-method view-type = "local"
	 */
	List queryChart(Integer product_id, String cust_id, int flag, String arrayField) throws Exception;

	//��ϵͳ���Զ�����
	/**
	 * @ejb.interface-method view-type = "local"
	 */
	List queryChart2(Integer product_id, String cust_id, int x_flag, int y_flag, int cell_flag, Integer cell_id)
			throws BusiException;

	//����������ͳ�� ����
	/**
	 * @ejb.interface-method view-type = "local"
	 */
	List queryChart3(Integer product_id, String cust_id, int flag, String arrayField) throws Exception;

	//��ϵͳ���Զ�����--����
	/**
	 * @ejb.interface-method view-type = "local"
	 */
	List queryChart4(Integer product_id, String cust_id, int query_flag, int table_flag, Integer start_date,
			Integer end_date) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	IPageList queryImportData(DestinationTableVo vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	void prepareImportData(DestinationTableVo vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	void confirmImportData(DestinationTableVo vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	void deleteImportData() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸Ŀͻ�����
	 * @param vo
	 * @throws BusiException
	 */
	void modiManager(CustomerVO vo) throws BusiException;

	/**
	 * ���ټ���--��ҳ
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList quickSearch(CustomerVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * ���ټ���
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	List quickSearchByAll(CustomerVO vo) throws BusiException;

	/**
	 * ��ѯ�޸ļ�¼
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listCustUpdateProcPage(CustomerVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * ��ѯ�ͻ��޸ļ�¼
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listCustUpdateProcPage(CustomerVO vo, Integer check_flag, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * ������֤�ͻ�
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws BusiException
	 */
	String listByAll(int cust_type) throws BusiException;

	/**
	 * ������֤�ͻ�
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws BusiException
	 */
	List listSameNameCustomers(String custName) throws BusiException;

	/**
	 * ͨ���ͻ�ID��ͻ�֤���ĺ˲���
	 * @ejb.interface-method view-type = "local"
	 * @param cust_id
	 * @return
	 * @throws BusiException
	 */
	List listCustCardInfo(Integer cust_id) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸Ŀͻ�������Ϣ-��������޸�
	 * @param vo
	 * @throws BusiException
	 */
	void modify3(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * * ����֤�������Ϣ
	 * @param name ֤���� ��id ֤������,state֤�����״̬���� ,summary,֤���������
	 * @throws BusiException
	 */
	void updateCheckStatus(String name, String id, String state, String summary) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ�Ͷ����Ϣ
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList getCustomerComplaint(Object[] params, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �˲�ͻ�Ͷ����Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void updateCustomerComplaint(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ���ӿͻ�Ͷ����Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void addCustomerComplaint(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �����ʼ�Ⱥ����Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void addEmail(Integer cust_id, String Email, String title, String content, Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ʼ�Ⱥ����Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	List queryEmail(Integer cust_id, Integer send_flag) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸��ʼ�Ⱥ������״̬
	 * @param vo
	 * @throws BusiException
	 */
	void modiEmail(Integer cust_id, Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��˿ͻ���Ϣ�޸�
	 * @param vo
	 * @throws BusiException
	 */
	void checkCustMsg(Integer cust_id, Integer check_flag, Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ��ע���ͻ���Ϣ
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList getDelCustomer(Object[] params, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �ָ���ע���ͻ�
	 * @param 
	 * @throws BusiException
	 */
	boolean modiDelCustomer(Integer cust_id, Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ����������Ϣ��flag: 0������Ϣ1�ͻ����к�ͬ2�ͻ��ѽ�����ͬ3�ͻ�������ͬ4�ͻ�������Ϣ
	 * @throws BusiException
	 */
	List queryCustAllInfo(Integer cust_id, Integer flag, Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ����һ���ͻ��ϲ���¼���ȴ����
	 * @throws BusiException
	 */
	void merge(Integer from_cust_id, Integer to_cust_id, Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ���һ���ͻ��ϲ���¼��check_flag: 2���ͨ�� 3��˷��
	 * @throws BusiException
	 */
	void checkMerge(Integer serial_no, Integer check_flag, Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��Ϊ�ͻ��ϲ�ʧ�󣬻ָ��ϲ��漰������Ϊ�ϲ�ǰ��״̬ 
	 * @throws BusiException
	 */
	void recoverMerge(Integer serial_no, Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ��ע���ͻ���Ϣ
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listMerge(CustomerVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ���»����ͻ������֤����Ϣ
	 * @throws BusiException
	 */
	void updateEntCustInfo(CustomerVO vo) throws BusiException;
	void updateEntCustInfo_m(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�����ͻ������֤����Ϣ
	 * @throws BusiException
	 */
	List loadEntCustInfo(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ���Ŀͻ�����ʵ�Ա��
	 * @throws BusiException
	 */
	void modiCustTrueFlag(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �����޸Ŀͻ���Ϣ
	 * @throws BusiException
	 */
	void batchModiCustInfo(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸Ŀͻ���ϵ��Ϣ
	 * @throws BusiException
	 */
	void maintainCustContactInfo(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * @throws BusiException
	 */
	List getCustContactInfo(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸ı���ͻ����вƲ������
	 * @param vo
	 * @throws BusiException
	 */
	void modiPropertySurvey(enfo.crm.vo.PropertySurveyVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ���ݿͻ�id��ѯʧ��
	 * @param vo
	 * @throws BusiException
	 * @return list
	 */
	List querySurveyByCustId(Integer cust_id) throws BusiException;

	/**
	 * �޸����غ���
	 * @param vo
	 * @throws BusiException
	 */
	void modiCustYc_Mobile(Integer cust_id) throws BusiException;
	
	/**
	 * ��ѯ�ͻ���Ϣ
	 * @param cust_id
	 * @return
	 * @throws BusiException
	 */
	Map queryCustomersByCustId(Integer cust_id) throws BusiException;
	
	/**
	 * ����ֻ�
	 * @param vo
	 * @throws BusiException
	 */
	void modiCustomerMobile(CustomerVO vo) throws BusiException;
	
	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ����ϵĻ�����Ϣ������ҳ��ʾ(�����)
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @param sQuery
	 * @throws BusiException
	 * @return rsList
	 */
	public IPageList SearchByCustomers(CustomerVO vo,int pageIndex,int pageSize) throws BusiException;	
	
	
	/** 
	 */
	public void addGifi(enfo.crm.vo.CustomerVO vo) throws java.lang.Exception;

	/** 
	 */
	public java.lang.Integer addGifiMove(enfo.crm.vo.CustomerVO vo)
			throws java.lang.Exception;

	/** 
	 */
	public void addGifitoCust(enfo.crm.vo.CustomerVO vo)
			throws java.lang.Exception;
	
	/** 
	 */
	public void modiCustLevel(enfo.crm.vo.CustLevelVO vo)
			throws java.lang.Exception;

	/** 
	 */
	public void updateGifi(enfo.crm.vo.CustomerVO vo)
			throws java.lang.Exception;

	/** 
	 */
	public void modiGiftMove(enfo.crm.vo.CustomerVO vo)
			throws java.lang.Exception;
	
	/** 
	 */
	public enfo.crm.dao.IPageList listGifiAll(enfo.crm.vo.CustomerVO vo,
			int pageIndex, int pageSize) throws enfo.crm.dao.BusiException;

	/** 
	 */
	public java.util.List listGifiAll(enfo.crm.vo.CustomerVO vo)
			throws enfo.crm.dao.BusiException;

	/** 
	 */
	public java.util.List listGifimoveoutAll(enfo.crm.vo.CustomerVO vo)
			throws enfo.crm.dao.BusiException;

	/** 
	 */
	public java.util.List listGifiToCust(enfo.crm.vo.CustomerVO vo)
			throws enfo.crm.dao.BusiException;

	/** 
	 */
	public void checkGiftMoveout(enfo.crm.vo.CustomerVO vo)
			throws java.lang.Exception;

	/** 
	 */
	public enfo.crm.dao.IPageList listGifimoveoutAll(enfo.crm.vo.CustomerVO vo,
			int pageIndex, int pageSize) throws enfo.crm.dao.BusiException;

	/** 
	 */
	public enfo.crm.dao.IPageList listGifiPutin(enfo.crm.vo.CustomerVO vo,
			int pageIndex, int pageSize) throws enfo.crm.dao.BusiException;
	
	/** 
	 */
	public enfo.crm.dao.IPageList getlistGainCust(enfo.crm.vo.CustomerVO vo,
			java.lang.String[] totalValue, int pageIndex, int pageSize)
			throws enfo.crm.dao.BusiException;
	/** 
	 */
	public void deleteGifi(enfo.crm.vo.CustomerVO vo)
			throws java.lang.Exception;

	/** 
	 */
	public void deleteGifimove(enfo.crm.vo.CustomerVO vo)
			throws java.lang.Exception;

	/** 
	 */
	public java.util.List queryCustByMobile(enfo.crm.vo.CustomerVO vo)
			throws enfo.crm.dao.BusiException;
	
}