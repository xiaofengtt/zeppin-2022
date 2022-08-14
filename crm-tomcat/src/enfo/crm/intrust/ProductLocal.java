package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.GainTotalVO;
import enfo.crm.vo.ProductVO;

public interface ProductLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 *             SP_QUERY_TPRODUCT
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_CODE VARCHAR(6),
	 * @IN_ITEM_ID INT,
	 * @IN_PRODUCT_STATUS VARCHAR(10),
	 * @IN_INTRUST_TYPE VARCHAR(10),
	 * @IN_CURRENCY_ID VARCHAR(2),
	 * @IN_INPUT_MAN INT,
	 * @IN_PRODUCT_NAME VARCHAR(60) = NULL,
	 * @IN_IS_LOCAL INT = NULL,
	 * @IN_INTRUST_FLAG1 INT = NULL,
	 * @IN_INTRUST_FLAG2 INT = NULL,
	 * @IN_INTRUST_TYPE1 VARCHAR(10) = NULL,
	 * @IN_INTRUST_TYPE2 VARCHAR(10) = NULL,
	 * @IN_START_DATE INT = NULL,
	 * @IN_END_DATE INT = NULL,
	 * @IN_AMOUNT_MIN DECIMAL(16,3) = NULL,
	 * @IN_AMOUNT_MAX DECIMAL(16,3) = NULL,
	 * @IN_ADMIN_MANAGER INT = 0 -- ִ�о���
	 * @IN_INTRUST_FLAG3 INT = NULL, --������ʽ��1˽ļ2��ļ
	 * @IN_INTRUST_FLAG4 INT = NULL, --����Ŀ�ģ�1˽��2����
	 * @IN_EXP_RATE DECIMAL(5,4) = NULL, --Ԥ�������ʣ����ظ�ֵ�������������
	 * @IN_FACT_MONEY DECIMAL(16,3) = NULL,--��Ʒ���н����ش��ڵ��ڸ�ֵ��
	 * @IN_FACT_MONEY ɾ�� �� ��Ʒ��ģ �ظ�
	 * @IN_CHECK_FLAG INT, --�Ƿ����
	 * @IN_VALID_PERIOD1 INT, --��Ʒ���ޣ���������Ϊ��λ��0Ϊ�����ޣ�����Ϊ����
	 * @IN_VALID_PERIOD2 INT, --��Ʒ���ޣ���������Ϊ��λ��0Ϊ�����ޣ�����Ϊ����
	 * @IN_FPFS NVARCHAR(10), --�����˱�����ȡ��ʽ1105
	 * @IN_OPEN_FLAG INT --���з�ʽ 1����ʽ2���ʽ
	 */
	IPageList productList(ProductVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" ���ص���ֵ��ȫ��ֵ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List load(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" ��Ϣ��¶����
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryPeriodDate1(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" ��ѯ�������
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	IPageList queryByProductID1(ProductVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TGAINTOTAL
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_SY_TYPE VARCHAR(10) = NULL,
	 * @IN_SY_DATE INT = NULL,
	 * @IN_INPUT_MAN INT = NULL
	 */
	IPageList listGainTotal(GainTotalVO vo, int page, int pagesize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" ��ѯ������ϸ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List querytz1(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" ��ѯ�Ϲ�
	 * @param vo
	 * @return
	 * @throws BusiException 
	 * @throws BusiException
	 * @throws  
	 */
	List rg_list(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" ��ѯ����
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_sy(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" ��ѯ������
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listStatus1(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" ��ѯ��Ʒ��ͬ���
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	IPageList list_cb(ProductVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" ��ѯ�޸���Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_xg(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" ��ѯ�ƽ�������
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_tl(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" �����ƽ���
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Integer adddeletecity1(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" �鿴�ƽ���
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List querycity1(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" ���Ӳ�Ʒʱ������
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void addDeleteDate1(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" �鿴��Ʒʱ����Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryDate1(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" ɾ�������
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void delete(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" �鿴�������ȡ�ֶ�������Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_gfp(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" �½������
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void append_glf(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" �޸Ĺ����
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void save_glf(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��Ʒ�������ȡ�ֶ���Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_manrateConfig(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��Ʒ��������Ϣ��ѯ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_openDate(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �����Ӳ�Ʒ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listSubProduct(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �����Ӳ�Ʒ
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listSubProductForPage(ProductVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �ֲ�ͳ��
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List StatSubProductByProv(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��Ϣ��¶��ѯ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listTask(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��Ϣ��¶��ѯ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	IPageList listTask(ProductVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * �½���Ϣ��¶
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void appendTask(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * �޸���Ϣ��¶
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void modiTask(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * ɾ����Ϣ��¶
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void deleteTask(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * �޸Ĳ�Ʒ�ͻ�����
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void modiManager(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ��Ʒ�������� SP_QUERY_TPRODUCTLIMIT 
	 * @IN_BOOK_CODE        INTEGER,        --����
	 * @IN_PRODUCT_ID       INTEGER        --��ƷID
	 */
	List queryProductLimit(ProductVO vo) throws Exception;

	/**
	 * ��������û�����������²�һ�� �˷�����ԭ��ԭ��
	 * @ejb.interface-method view-type = "local"
	 * �����޸Ĳ�Ʒ�������� SP_ADD_TPRODUCTLIMIT 
	 * @IN_BOOK_CODE        INTEGER,        --����
	 * @IN_PRODUCT_ID       INTEGER,        --��ƷID
	 * @IN_QUALIFIED_FLAG   INTEGER,        --�ϸ�Ͷ���˿��ƣ�1.���ƣ�2.������
	 * @IN_QUALIFIED_NUM    INTEGER,        --�Ǻϸ�Ͷ���˺�ͬ����
	 * @IN_QUALIFIED_MONEY  DECIMAL(16,2),  --�ϸ�Ͷ���˺�ͬ�������
	 * @IN_ASFUND_FLAG      INTEGER,        --�Ƿ�Ϊ������Ʒ��1�ǣ�2��
	 * @IN_INPUT_MAN        INTEGER         --����Ա
	 */
	void updateProductLimit(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * �����޸�CRM�в�Ʒ��
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void modiCRMProduct(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �鿴CRM��Ʒ����ĩ���
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	IPageList listPageCrmProduct(ProductVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �鿴CRM��Ʒ����ĩ���
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	IPageList listPageCrmSubProduct(ProductVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �鿴CRM��Ʒ����ĩ���
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listCrmProduct(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * ���CRM�в�Ʒ��
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void modiCRMProductCheck(ProductVO vo) throws Exception;

	/**
	 * ���������ò�ѯ
	 * @ejb.interface-method view-type = "local" 
	 * @param sQuery
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	IPageList query(String sQuery, ProductVO vo, String[] totalColumn, int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param srcQuery
	 * @return
	 */
	String[] parseQuery(String srcQuery);

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ѯ��Ʒ��Ϣ
	 * 
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List searchList(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * ������Ʒ����������Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void addMarketTrench(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * �޸Ĳ�Ʒ����������Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void modiMarketTrench(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * ɾ����Ʒ����������Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void delMarketTrench(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * ��ѯ��Ʒ����������Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryMarketTrench(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ��ѯ��Ʒ�����Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryProductTable(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ��ѯ��Ʒ�����Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	String queryProductTableJosn(ProductVO vo) throws BusiException;

	/**
	 * �ƽ��
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	Integer adddeletecity(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �Ƽ����޸�
	 * SP_MODI_TPRODUCTCITY @IN_SERIAL_NO         INT,
	 *			             @IN_GOV_PROV_REGIONAL VARCHAR(10) = '',           --ʡ����������(9999)
	 *			             @IN_GOV_REGIONAL      VARCHAR(10) = '',           --��������(9999)
	 *			             @IN_INPUT_MAN         INT
	 * @throws Exception
	 */
	void modiProductCity(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * ��ѯ�ͻ��������۹�����Щ��Ʒ�쵽����
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryDueProduct(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * �޸Ĳ�Ʒ��Ͷ������
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void modiProductInvestment(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * �޸Ĳ�Ʒ�Ƿ��ԤԼ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void modiProductPre(ProductVO vo) throws Exception;

	/**
	 * Ԥ���в�Ʒ�����漶�����ò�ѯ
	 * @ejb.interface-method view-type = "local" 
	 * @param sQuery
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	IPageList queryPreproductProv(ProductVO vo, int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * �޸�Ԥ���в�Ʒ�����漶��
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void modiPreproductProv(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * �޸�Ԥ���в�Ʒ�����漶���������
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void modiPreproductProvRate(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * �½�Ԥ���в�Ʒ�����漶��
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void addPreproductProv(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * ɾ��Ԥ���в�Ʒ�����漶��
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void delPreproductProv(ProductVO vo) throws Exception;

}