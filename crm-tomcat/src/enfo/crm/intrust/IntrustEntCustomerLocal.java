package enfo.crm.intrust;

import java.math.BigDecimal;
import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiFullLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.EntRelationVO;

public interface IntrustEntCustomerLocal extends IBusiFullLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_DEL_TENTCUSTINFO
	 * 
	 * @IN_CUST_ID INT,
	 * @IN_INPUT_MAN INT
	 */
	void deleteEntCustomer() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * update by tsg 2007-11-02
	 * 
	 * �޸���ҵ�ͻ���Ϣ
	 * 
	 * SP_MODI_TENTCUSTINFO
	 * 
	 * @IN_CUST_ID INT,
	 * @IN_CUST_NAME VARCHAR(60),
	 * @IN_CUST_CODE VARCHAR(20),
	 * @IN_CARD_ID VARCHAR(20),
	 * @IN_BANK_ID VARCHAR(10),
	 * @IN_BANK_SUB_NAME VARCHAR(30),
	 * @IN_BANK_ACCT VARCHAR(30),
	 * @IN_REG_ADDRESS VARCHAR(60),
	 * @IN_LINK_MAN VARCHAR(10),
	 * @IN_ADDRESS VARCHAR(60),
	 * @IN_TELPHONE VARCHAR(20),
	 * @IN_FAX VARCHAR(20),
	 * @IN_EMAIL VARCHAR(30),
	 * @IN_ENT_TYPE VARCHAR(10),
	 * @IN_SUMMARY VARCHAR(200),
	 * @IN_REG_POSTCODE VARCHAR(6),
	 * @IN_POSTCODE VARCHAR(6),
	 * @IN_SEX INT,
	 * @IN_CUST_TYPE INT,
	 * @IN_CARD_CODE VARCHAR(60),
	 * @IN_CARD_TYPE VARCHAR(10),
	 * @IN_CREDIT_LEVEL VARCHAR(10),
	 * @IN_JT_FLAG INT,
	 * @IN_IS_LINK INT, -- 1 ������ 2 �ǹ�����
	 * @IN_LINK_TYPE INT, -- ��������
	 * @IN_LINK_GD_MONEY DECIMAL(16,3), -- �ɶ�Ͷ��������й�˾���
	 * @IN_INPUT_MAN INT
	 * @IN_VOC_TYPE VARCHAR(10) = '' -- ����ְҵ/������ҵ���(1142/2142)
	 * 
	 * @IN_WORKADDRESS VARCHAR(60), --Ŀǰ�칫�ص�
	 * @IN_LEGAL_PERSON VARCHAR(30), --����������
	 * @IN_CONTRI_CAPITAL DEC(16,3), --ʵ���ʱ�
	 * @IN_REGIST_CAPITAL DEC(16,3), --ע���ʱ�
	 * @IN_TOTAL_SESET DEC(16,3), --�ʲ��ܶ�
	 * @IN_INCORPORATION_DATE VARCHAR(30), --����ʱ��
	 * @IN_TAX_REGISTRATION_NO VARCHAR(30), --˰��ǼǺ�
	 * @IN_NET_ASSET DEC(16,3), --���ʲ�
	 * @IN_ANNUAL_INSPECTION INT, --�Ƿ����:1��,2��
	 * @IN_FORMS_TYPE INT, --���񱨱�����:1�����,2�������
	 * @IN_SALES_INCOME DEC(16,3), --������������
	 * @IN_FUNDING_SOURCE VARCHAR(50), --��Ŀ��Դ
	 * @IN_STRONG_STOCKHOLDER VARCHAR(30), --��ҵʵ�ʿ�����
	 * @IN_RATIO DEC(5,2), --ռ�ɱ���
	 * @IN_REQUIRE INT, --��ҵ���� SELECT TYPE_VALUE FROM TDICTPARAM WHERE TYPE_ID =
	 *             5116
	 * @IN_ITEM_LOCATION VARCHAR(60), --��Ŀ��������
	 * @IN_SCALE_TERM VARCHAR(60), --��Ŀ��ģ������
	 * @IN_FINANCIAL_COST DEC(16,3), --����ɱ�
	 * @IN_BUSINESS_SCOPE VARCHAR(100),--��Ӫҵ��
	 * @IN_GUARANTEE_MODE VARCHAR(100) --��ҵ���ṩ�ĵ�����ʽ
	 */
	void updateEntCustomer() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * update by tsg 2007-11-02 modify by zhangmy 20100304 ���Ӳ�����ҵ��Ϣ�ֶ�
	 * 
	 * ���һ���µ���ҵ�ͻ� SP_ADD_TENTCUSTINFO
	 * 
	 * @IN_BOOK_CODE INT,
	 * @IN_CUST_NAME VARCHAR(60), -- �ͻ�����
	 * @IN_CUST_CODE VARCHAR(20), -- �ͻ�����
	 * @IN_CARD_ID VARCHAR(20), -- �����
	 * @IN_BANK_ID VARCHAR(10), -- ���б��
	 * @IN_BANK_SUB_NAME VARCHAR(30), -- ֧������
	 * @IN_BANK_ACCT VARCHAR(30), -- �����ʺ�
	 * @IN_REG_ADDRESS VARCHAR(60), -- ע���ַ
	 * @IN_LINK_MAN VARCHAR(10), -- ��ϵ��
	 * @IN_ADDRESS VARCHAR(60), -- ͨѶ��ַ
	 * @IN_TELPHONE VARCHAR(20), -- ��ϵ�绰
	 * @IN_FAX VARCHAR(20), -- ����
	 * @IN_EMAIL VARCHAR(30), -- EMAIL
	 * @IN_ENT_TYPE VARCHAR(10), -- ��ҵ����
	 * @IN_SUMMARY VARCHAR(200), -- ��ע
	 * @IN_REG_POSTCODE VARCHAR(6), -- ע�����������
	 * @IN_POSTCODE VARCHAR(6), -- ͨѶ����������
	 * @IN_SEX INT, -- �Ա�
	 * @IN_CUST_TYPE INT, -- �ͻ����
	 * @IN_CARD_CODE VARCHAR(60), -- ֤������
	 * @IN_CARD_TYPE VARCHAR(10), -- ֤������
	 * @IN_CREDIT_LEVEL VARCHAR(10), -- ���ü���
	 * @IN_JT_FLAG INT, -- ���ű�־
	 * @IN_IS_LINK INT, -- 1 ������ 2 �ǹ�����
	 * @IN_LINK_TYPE INT, -- ��������
	 * @IN_LINK_GD_MONEY DECIMAL(16,3), -- �ɶ�Ͷ��������й�˾���
	 * @IN_INPUT_MAN INT, -- ����Ա
	 * @IN_VOC_TYPE VARCHAR(10) = '', -- ����ְҵ/������ҵ���(1142/2142)
	 * 
	 * @IN_WORKADDRESS VARCHAR(60), --Ŀǰ�칫�ص�
	 * @IN_LEGAL_PERSON VARCHAR(30), --����������
	 * @IN_CONTRI_CAPITAL DEC(16,3), --ʵ���ʱ�
	 * @IN_REGIST_CAPITAL DEC(16,3), --ע���ʱ�
	 * @IN_TOTAL_SESET DEC(16,3), --�ʲ��ܶ�
	 * @IN_INCORPORATION_DATE VARCHAR(30), --����ʱ��
	 * @IN_TAX_REGISTRATION_NO INT, --˰��ǼǺ�
	 * @IN_NET_ASSET DEC(16,3), --���ʲ�
	 * @IN_ANNUAL_INSPECTION INT, --�Ƿ����:1��,2��
	 * @IN_FORMS_TYPE INT, --���񱨱�����:1�����,2�������
	 * @IN_SALES_INCOME DEC(16,3), --������������
	 * @IN_FUNDING_SOURCE VARCHAR(50), --��Ŀ��Դ
	 * @IN_STRONG_STOCKHOLDER VARCHAR(30), --��ҵʵ�ʿ�����
	 * @IN_RATIO DEC(5,2), --ռ�ɱ���
	 * @IN_REQUIRE INT, --��ҵ���� SELECT TYPE_VALUE FROM TDICTPARAM WHERE TYPE_ID =
	 *             5116
	 * @IN_ITEM_LOCATION VARCHAR(60), --��Ŀ��������
	 * @IN_SCALE_TERM VARCHAR(60), --��Ŀ��ģ������
	 * @IN_FINANCIAL_COST DEC(16,3), --����ɱ�
	 * @IN_BUSINESS_SCOPE VARCHAR(100),--��Ӫҵ��
	 * @OUT_CUST_ID INT OUTPUT,-- �ͻ�ID
	 * @IN_GUARANTEE_MODE VARCHAR(100) --��ҵ���ṩ�ĵ�����ʽ
	 * @IN_GOV_PROV_REGIONAL VARCHAR(10) = '',           --ʡ����������(9999)    20100714  luohh
	 * @IN_GOV_REGIONAL      VARCHAR(10) = '',            --��������(9999)        20100714  luohh 
	 * @IN_TYPE_CODE         VARCHAR(5)  =''              --��ҵ�����ϸ����      20100714  luohh 
	 */
	void addNewEntCustomer() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * update by tsg 2007-09-30
	 * 
	 * ��ѯ��ҵ��Ϣ�б�
	 * 
	 * SP_QUERY_TENTCUSTINFO
	 * 
	 * @IN_BOOK_CODE INT,
	 * @IN_CUST_ID INT,
	 * @IN_CUST_TYPE VARCHAR(10),
	 * @IN_CUST_NAME VARCHAR(60),
	 * @IN_CUST_CODE VARCHAR(20)
	 * @IN_IS_LINK INT = 0
	 * @IN_JT_FLAG = NULL,
	 * @IN_CARD_CODE = NULL
	 */
	void list() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	void load() throws Exception;

	/** 
	 * @ejb.interface-method view-type = "local"
	 */
	boolean getNext() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ������Ϣ��ѯ���Զ��е���ʾ
	 * @return
	 * @throws Exception
	 */
	List getNextAllist() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void fixLogic() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ͬ���ͻ���Ϣ
	 * 
	 * @throws Exception
	 */
	void syncCrmEntCust() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 2008-09-25 YZJ ��ҵ�ͻ���Ϣ�ϲ� �洢�������ƣ�SP_HB_TENTCUSTINFO
	 * 
	 * @IN_FROM_CUST_ID INT Դ�ͻ�ID
	 * @IN_TO_CUST_ID INT Ŀ�Ŀͻ�ID
	 * @IN_INPUT_MAN INT ����Ա
	 * 
	 * @throws Exception
	 */
	void unite() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void queryTentCustInfoDetail() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextTentCustInfoDetail() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * add by jinxr 2007/4/26
	 * 
	 * @throws Exception
	 */
	void queryentcustht() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * add by jinxr 2007/4/26
	 * 
	 * @return
	 * @throws Exception
	 */
	boolean getNextentcustht() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param request
	 * @param prefix
	 * @throws Exception
	 */
	void setProperties(javax.servlet.ServletRequest request, String prefix) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param value
	 * @return
	 * @throws Exception
	 */
	String listTreeBySql(String type_code, String value) throws Exception;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 *�����ͻ��ɶ���ϵ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void addEntRelation(EntRelationVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ɾ���ͻ��ɶ���ϵ
	 * @param vo
	 * @throws BusiException
	 */
	void delEntRelation(EntRelationVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ��ѯ�ͻ��ɶ���ϵ
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList queryEntRelation(EntRelationVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� address��
	 */
	java.lang.String getAddress();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param address
	 *            Ҫ���õ� address��
	 */
	void setAddress(java.lang.String address);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� annual_inspection��
	 */
	Integer getAnnual_inspection();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param annual_inspection
	 *            Ҫ���õ� annual_inspection��
	 */
	void setAnnual_inspection(Integer annual_inspection);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� bank_acct��
	 */
	java.lang.String getBank_acct();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param bank_acct
	 *            Ҫ���õ� bank_acct��
	 */
	void setBank_acct(java.lang.String bank_acct);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� bank_id��
	 */
	java.lang.String getBank_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param bank_id
	 *            Ҫ���õ� bank_id��
	 */
	void setBank_id(java.lang.String bank_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� bank_name��
	 */
	java.lang.String getBank_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param bank_name
	 *            Ҫ���õ� bank_name��
	 */
	void setBank_name(java.lang.String bank_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� bank_sub_name��
	 */
	java.lang.String getBank_sub_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param bank_sub_name
	 *            Ҫ���õ� bank_sub_name��
	 */
	void setBank_sub_name(java.lang.String bank_sub_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� book_code��
	 */
	java.lang.Integer getBook_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param book_code
	 *            Ҫ���õ� book_code��
	 */
	void setBook_code(java.lang.Integer book_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� business_scope��
	 */
	String getBusiness_scope();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param business_scope
	 *            Ҫ���õ� business_scope��
	 */
	void setBusiness_scope(String business_scope);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� card_code��
	 */
	java.lang.String getCard_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param card_code
	 *            Ҫ���õ� card_code��
	 */
	void setCard_code(java.lang.String card_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� card_id��
	 */
	java.lang.String getCard_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param card_id
	 *            Ҫ���õ� card_id��
	 */
	void setCard_id(java.lang.String card_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� card_type��
	 */
	java.lang.String getCard_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param card_type
	 *            Ҫ���õ� card_type��
	 */
	void setCard_type(java.lang.String card_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� card_type_name��
	 */
	java.lang.String getCard_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param card_type_name
	 *            Ҫ���õ� card_type_name��
	 */
	void setCard_type_name(java.lang.String card_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� contri_capital��
	 */
	BigDecimal getContri_capital();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param contri_capital
	 *            Ҫ���õ� contri_capital��
	 */
	void setContri_capital(BigDecimal contri_capital);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� credit_level��
	 */
	java.lang.String getCredit_level();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param credit_level
	 *            Ҫ���õ� credit_level��
	 */
	void setCredit_level(java.lang.String credit_level);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� credit_level_name��
	 */
	java.lang.String getCredit_level_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param credit_level_name
	 *            Ҫ���õ� credit_level_name��
	 */
	void setCredit_level_name(java.lang.String credit_level_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_code��
	 */
	java.lang.String getCust_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_code
	 *            Ҫ���õ� cust_code��
	 */
	void setCust_code(java.lang.String cust_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_id��
	 */
	java.lang.Integer getCust_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_id
	 *            Ҫ���õ� cust_id��
	 */
	void setCust_id(java.lang.Integer cust_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_name��
	 */
	java.lang.String getCust_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_name
	 *            Ҫ���õ� cust_name��
	 */
	void setCust_name(java.lang.String cust_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_type��
	 */
	Integer getCust_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_type
	 *            Ҫ���õ� cust_type��
	 */
	void setCust_type(Integer cust_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� email��
	 */
	java.lang.String getEmail();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param email
	 *            Ҫ���õ� email��
	 */
	void setEmail(java.lang.String email);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ent_type��
	 */
	java.lang.String getEnt_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ent_type
	 *            Ҫ���õ� ent_type��
	 */
	void setEnt_type(java.lang.String ent_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ent_type_name��
	 */
	java.lang.String getEnt_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ent_type_name
	 *            Ҫ���õ� ent_type_name��
	 */
	void setEnt_type_name(java.lang.String ent_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� fax��
	 */
	java.lang.String getFax();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param fax
	 *            Ҫ���õ� fax��
	 */
	void setFax(java.lang.String fax);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� field_cn_name��
	 */
	java.lang.String getField_cn_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param field_cn_name
	 *            Ҫ���õ� field_cn_name��
	 */
	void setField_cn_name(java.lang.String field_cn_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� field_name��
	 */
	java.lang.String getField_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param field_name
	 *            Ҫ���õ� field_name��
	 */
	void setField_name(java.lang.String field_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� financial_cost��
	 */
	BigDecimal getFinancial_cost();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param financial_cost
	 *            Ҫ���õ� financial_cost��
	 */
	void setFinancial_cost(BigDecimal financial_cost);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� forms_type��
	 */
	Integer getForms_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param forms_type
	 *            Ҫ���õ� forms_type��
	 */
	void setForms_type(Integer forms_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� from_cust_id��
	 */
	Integer getFrom_cust_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param from_cust_id
	 *            Ҫ���õ� from_cust_id��
	 */
	void setFrom_cust_id(Integer from_cust_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� funding_source��
	 */
	String getFunding_source();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param funding_source
	 *            Ҫ���õ� funding_source��
	 */
	void setFunding_source(String funding_source);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� guarantee_mode��
	 */
	String getGuarantee_mode();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param guarantee_mode
	 *            Ҫ���õ� guarantee_mode��
	 */
	void setGuarantee_mode(String guarantee_mode);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� incorporation_date��
	 */
	String getIncorporation_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param incorporation_date
	 *            Ҫ���õ� incorporation_date��
	 */
	void setIncorporation_date(String incorporation_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� input_man��
	 */
	java.lang.Integer getInput_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param input_man
	 *            Ҫ���õ� input_man��
	 */
	void setInput_man(java.lang.Integer input_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� input_time��
	 */
	java.sql.Timestamp getInput_time();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param input_time
	 *            Ҫ���õ� input_time��
	 */
	void setInput_time(java.sql.Timestamp input_time);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� is_link��
	 */
	Integer getIs_link();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param is_link
	 *            Ҫ���õ� is_link��
	 */
	void setIs_link(Integer is_link);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� item_location��
	 */
	String getItem_location();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param item_location
	 *            Ҫ���õ� item_location��
	 */
	void setItem_location(String item_location);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� jt_cust_id��
	 */
	java.lang.Integer getJt_cust_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param jt_cust_id
	 *            Ҫ���õ� jt_cust_id��
	 */
	void setJt_cust_id(java.lang.Integer jt_cust_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� jt_flag��
	 */
	Integer getJt_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param jt_flag
	 *            Ҫ���õ� jt_flag��
	 */
	void setJt_flag(Integer jt_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� last_modi_time��
	 */
	java.sql.Timestamp getLast_modi_time();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param last_modi_time
	 *            Ҫ���õ� last_modi_time��
	 */
	void setLast_modi_time(java.sql.Timestamp last_modi_time);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� legal_person��
	 */
	String getLegal_person();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param legal_person
	 *            Ҫ���õ� legal_person��
	 */
	void setLegal_person(String legal_person);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� link_gd_money��
	 */
	BigDecimal getLink_gd_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param link_gd_money
	 *            Ҫ���õ� link_gd_money��
	 */
	void setLink_gd_money(BigDecimal link_gd_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� link_man��
	 */
	java.lang.String getLink_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param link_man
	 *            Ҫ���õ� link_man��
	 */
	void setLink_man(java.lang.String link_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� link_type��
	 */
	java.lang.Integer getLink_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param link_type
	 *            Ҫ���õ� link_type��
	 */
	void setLink_type(java.lang.Integer link_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� modi_man��
	 */
	java.lang.Integer getModi_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param modi_man
	 *            Ҫ���õ� modi_man��
	 */
	void setModi_man(java.lang.Integer modi_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� net_asset��
	 */
	BigDecimal getNet_asset();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param net_asset
	 *            Ҫ���õ� net_asset��
	 */
	void setNet_asset(BigDecimal net_asset);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� new_field_info��
	 */
	java.lang.String getNew_field_info();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param new_field_info
	 *            Ҫ���õ� new_field_info��
	 */
	void setNew_field_info(java.lang.String new_field_info);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� old_field_info��
	 */
	java.lang.String getOld_field_info();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param old_field_info
	 *            Ҫ���õ� old_field_info��
	 */
	void setOld_field_info(java.lang.String old_field_info);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� postcode��
	 */
	java.lang.String getPostcode();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param postcode
	 *            Ҫ���õ� postcode��
	 */
	void setPostcode(java.lang.String postcode);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� product_id��
	 */
	Integer getProduct_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_id
	 *            Ҫ���õ� product_id��
	 */
	void setProduct_id(Integer product_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� product_name��
	 */
	String getProduct_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_name
	 *            Ҫ���õ� product_name��
	 */
	void setProduct_name(String product_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ratio��
	 */
	BigDecimal getRatio();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ratio
	 *            Ҫ���õ� ratio��
	 */
	void setRatio(BigDecimal ratio);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� reg_address��
	 */
	java.lang.String getReg_address();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param reg_address
	 *            Ҫ���õ� reg_address��
	 */
	void setReg_address(java.lang.String reg_address);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� reg_postcode��
	 */
	java.lang.String getReg_postcode();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param reg_postcode
	 *            Ҫ���õ� reg_postcode��
	 */
	void setReg_postcode(java.lang.String reg_postcode);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� regist_capital��
	 */
	BigDecimal getRegist_capital();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param regist_capital
	 *            Ҫ���õ� regist_capital��
	 */
	void setRegist_capital(BigDecimal regist_capital);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� require��
	 */
	Integer getRequire();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param require
	 *            Ҫ���õ� require��
	 */
	void setRequire(Integer require);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sales_income��
	 */
	BigDecimal getSales_income();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sales_income
	 *            Ҫ���õ� sales_income��
	 */
	void setSales_income(BigDecimal sales_income);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� scale_term��
	 */
	String getScale_term();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param scale_term
	 *            Ҫ���õ� scale_term��
	 */
	void setScale_term(String scale_term);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� serial_no��
	 */
	java.lang.Integer getSerial_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param serial_no
	 *            Ҫ���õ� serial_no��
	 */
	void setSerial_no(java.lang.Integer serial_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sex��
	 */
	Integer getSex();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sex
	 *            Ҫ���õ� sex��
	 */
	void setSex(Integer sex);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� strong_stockholder��
	 */
	String getStrong_stockholder();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param strong_stockholder
	 *            Ҫ���õ� strong_stockholder��
	 */
	void setStrong_stockholder(String strong_stockholder);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� summary��
	 */
	java.lang.String getSummary();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param summary
	 *            Ҫ���õ� summary��
	 */
	void setSummary(java.lang.String summary);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� tax_registration_no��
	 */
	String getTax_registration_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param tax_registration_no
	 *            Ҫ���õ� tax_registration_no��
	 */
	void setTax_registration_no(String tax_registration_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� telphone��
	 */
	java.lang.String getTelphone();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param telphone
	 *            Ҫ���õ� telphone��
	 */
	void setTelphone(java.lang.String telphone);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� to_cust_id��
	 */
	Integer getTo_cust_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param to_cust_id
	 *            Ҫ���õ� to_cust_id��
	 */
	void setTo_cust_id(Integer to_cust_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� total_seset��
	 */
	BigDecimal getTotal_seset();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param total_seset
	 *            Ҫ���õ� total_seset��
	 */
	void setTotal_seset(BigDecimal total_seset);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� voc_type��
	 */
	java.lang.String getVoc_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param voc_type
	 *            Ҫ���õ� voc_type��
	 */
	void setVoc_type(java.lang.String voc_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� voc_type_name��
	 */
	java.lang.String getVoc_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param voc_type_name
	 *            Ҫ���õ� voc_type_name��
	 */
	void setVoc_type_name(java.lang.String voc_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� workaddress��
	 */
	String getWorkaddress();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param workaddress
	 *            Ҫ���õ� workaddress��
	 */
	void setWorkaddress(String workaddress);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� busi_name��
	 */
	String getBusi_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� contract_id��
	 */
	Integer getContract_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� contract_sub_bh��
	 */
	String getContract_sub_bh();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cw_money��
	 */
	java.math.BigDecimal getCw_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� end_date��
	 */
	Integer getEnd_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ht_money��
	 */
	java.math.BigDecimal getHt_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� r_type_name��
	 */
	String getR_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� start_date��
	 */
	Integer getStart_date();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @return ���� gov_prov_regioal��
	 */
	String getGov_prov_regioal();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param gov_prov_regioal Ҫ���õ� gov_prov_regioal��
	 */
	void setGov_prov_regioal(String gov_prov_regioal);

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @return ���� gov_regioal��
	 */
	String getGov_regioal();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param gov_regioal Ҫ���õ� gov_regioal��
	 */
	void setGov_regioal(String gov_regioal);

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @return ���� type_code��
	 */
	String getType_code();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param type_code Ҫ���õ� type_code��
	 */
	void setType_code(String type_code);

	/**
	 *   @ejb.interface-method view-type = "local"
	 * @return ���� type_name��
	 */
	String getType_name();

	/**
	 *   @ejb.interface-method view-type = "local"
	 * @param type_name Ҫ���õ� type_name��
	 */
	void setType_name(String type_name);

	/**
	 *    @ejb.interface-method view-type = "local"
	 * @return ���� gov_prov_regioal_name��
	 */
	String getGov_prov_regioal_name();

	/**
	 *    @ejb.interface-method view-type = "local"
	 * @param gov_prov_regioal_name Ҫ���õ� gov_prov_regioal_name��
	 */
	void setGov_prov_regioal_name(String gov_prov_regioal_name);

	/**
	 *    @ejb.interface-method view-type = "local"
	 * @return ���� gov_regioal_name��
	 */
	String getGov_regioal_name();

	/**
	 *    @ejb.interface-method view-type = "local"
	 * @param gov_regioal_name Ҫ���õ� gov_regioal_name��
	 */
	void setGov_regioal_name(String gov_regioal_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� country��
	 */
	String getCountry();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param country Ҫ���õ� country��
	 */
	void setCountry(String country);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� country_name��
	 */
	String getCountry_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param country_name Ҫ���õ� country_name��
	 */
	void setCountry_name(String country_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� jkr_type��
	 */
	String getJkr_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param jkr_type Ҫ���õ� jkr_type��
	 */
	void setJkr_type(String jkr_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� jkr_type_name��
	 */
	String getJkr_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param jkr_type_name Ҫ���õ� jkr_type_name��
	 */
	void setJkr_type_name(String jkr_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� jkr_type2��
	 */
	String getJkr_type2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param jkr_type2 Ҫ���õ� jkr_type2��
	 */
	void setJkr_type2(String jkr_type2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� jkr_type2_name��
	 */
	String getJkr_type2_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param jkr_type2_name Ҫ���õ� jkr_type2_name��
	 */
	void setJkr_type2_name(String jkr_type2_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� country_tax_no��
	 */
	String getCountry_tax_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param country_tax_no Ҫ���õ� country_tax_no��
	 */
	void setCountry_tax_no(String country_tax_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� land_tax_no��
	 */
	String getLand_tax_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param land_tax_no Ҫ���õ� land_tax_no��
	 */
	void setLand_tax_no(String land_tax_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� license_end_date��
	 */
	Integer getLicense_end_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param license_end_date Ҫ���õ� license_end_date��
	 */
	void setLicense_end_date(Integer license_end_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� reg_date��
	 */
	Integer getReg_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param reg_date Ҫ���õ� reg_date��
	 */
	void setReg_date(Integer reg_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� reg_no��
	 */
	String getReg_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param reg_no Ҫ���õ� reg_no��
	 */
	void setReg_no(String reg_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� valid_period��
	 */
	Integer getValid_period();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param valid_period Ҫ���õ� valid_period��
	 */
	void setValid_period(Integer valid_period);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� period_unit��
	 */
	Integer getPeriod_unit();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param period_unit Ҫ���õ� period_unit��
	 */
	void setPeriod_unit(Integer period_unit);

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @return Returns the public_flag.
	 */
	Integer getPublic_flag();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param public_flag The public_flag to set.
	 */
	void setPublic_flag(Integer public_flag);

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @return Returns the seid_id.
	 */
	String getSeid_id();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param seid_id The seid_id to set.
	 */
	void setSeid_id(String seid_id);

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @return Returns the stock_code.
	 */
	String getStock_code();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param stock_code The stock_code to set.
	 */
	void setStock_code(String stock_code);

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @return Returns the stock_name.
	 */
	String getStock_name();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param stock_name The stock_name to set.
	 */
	void setStock_name(String stock_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� complete_flag��
	 */
	Integer getComplete_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param complete_flag Ҫ���õ� complete_flag��
	 */
	void setComplete_flag(Integer complete_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� fz_total��
	 */
	String getFz_total();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param fz_total Ҫ���õ� fz_total��
	 */
	void setFz_total(String fz_total);

}