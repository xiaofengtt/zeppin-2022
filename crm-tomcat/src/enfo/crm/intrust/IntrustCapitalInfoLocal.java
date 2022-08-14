package enfo.crm.intrust;

import java.math.BigDecimal;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiFullLocal;

public interface IntrustCapitalInfoLocal extends IBusiFullLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_CHECK_TCAPINCOME
	 * @IN_SERIAL_NO INT,
	 * @IN_SUB_CODE VARCHAR(24),
	 * @IN_INPUT_MAN INT
	 */
	void checkCapincome() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_DEL_TCAPINCOME
	 * @IN_SERIAL_NO INT,
	 * @IN_INPUT_MAN INT
	 */
	void delCapincome() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_MODI_TCAPINCOME
	 * @IN_SERIAL_NO INT,
	 * @IN_TRADE_DATE INT,
	 * @IN_TRADE_AMOUNT DECIMAL(16,3),
	 * @IN_IN_FLAG VARCHAR(10),
	 * @IN_INPUT_MAN INT
	 */
	void modiCapincome() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_ADD_TCAPINCOME
	 * @IN_REC_NO INT,
	 * @IN_TRADE_DATE INT,
	 * @IN_TRADE_AMOUNT DECIMAL(16,3),
	 * @IN_IN_FLAG VARCHAR(10),
	 * @IN_INPUT_MAN INT
	 */
	void appendCapincome() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TCAPINCOME
	 * @IN_BOOK_CODE INT,
	 * @IN_SERIAL_NO INT,
	 * @IN_REC_NO INT,
	 * @IN_TRADE_DATE1 INT,
	 * @IN_TRADE_DATE2 INT,
	 * @IN_IN_FLAG VARCHAR(10),
	 * @IN_CHECK_FLAG INT
	 */
	void Querycapincome() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextFixAsset() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNext2() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextDeprecationDetail() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextCapincome() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �����۾ɲ�ѯ SP_QUERY_TDEPRECIATION
	 * @IN_CARD_ID INT,
	 * @IN_CAPITAL_NAME VARCHAR(60),
	 * @IN_PRODUCT_ID INT,
	 * @IN_CHECK_FLAG INT = NULL
	 */
	void Querydeprecation() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TDEPRECIATION_DETAIL
	 * @IN_CARD_ID INT,
	 * @IN_CHECK_FLAG INT
	 */
	void Querydeprecationdetail() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TFIXASSET
	 * @IN_CARD_ID INT,
	 * @IN_START_DATE1 INT,
	 * @IN_START_DATE2 INT,
	 * @IN_DEP_STATUS INT --����״̬��1����2�������
	 */
	void queryFixAsset() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void appendDepreciation() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void checkDepreciation() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void appendFixAsset() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void modiFixAsset() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	void append() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	void save() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	void delete() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void Copy() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TCAPITALINFO
	 * @IN_BOOK_CODE INT,
	 * @IN_SERIAL_NO INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CAPITAL_TYPE_NAME VARCHAR(60),
	 * @IN_CAPITALOPER_TYPE_NAME VARCHAR(60),
	 * @IN_CAPITAL_NAME VARCHAR(128),
	 * @IN_BUSI_ID VARCHAR(10),
	 * @IN_CONTRACT_BH VARCHAR(32),
	 * @IN_CHECK_FLAG INT,
	 * @IN_STATUS VARCHAR(10),
	 * @IN_INPUT_MAN INT,
	 * @IN_CAPITAL_NO VARCHAR(32),
	 * @IN_ZC_FLAG INT = 0
	 */
	void list() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * CREATE PROCEDURE SP_QUERY_TDKDBCUSTINFO_TOTAL
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CUST_NAME NVARCHAR (100), --�ͻ�����
	 * @IN_BUSI_ID VARCHAR(10),
	 * @IN_DBFS_TYPE VARCHAR(10), --������ʽ(1126)
	 * @IN_INPUT_MAN INT ,--����ԱID
	 * @IN_CONTRACT_SUB_BH NVARCHAR(200) ='' --��ͬʵ�ʱ��
	 * @throws Exception
	 */
	void list_bzr() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TCAPITALINFO_FIX
	 * @IN_BOOK_CODE INT,
	 * @IN_SERIAL_NO INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CAPITALOPER_TYPE_NAME VARCHAR(60),
	 * @IN_CAPITAL_NAME VARCHAR(128),
	 * @IN_BUSI_ID VARCHAR(10),
	 * @IN_CONTRACT_BH VARCHAR(32),
	 * @IN_INPUT_MAN INT,
	 * @IN_CAPITAL_NO VARCHAR(32),
	 * @IN_FLAG INT = NULL
	 */
	void list2() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TCAPITALINFODETAIL
	 * @IN_CI_SERIAL_NO INT,
	 * @IN_INPUT_MAN INT
	 */
	void historyData() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_CONTRACT
	 * @IN_BOOK_CODE INT,
	 * @IN_BUSI_FLAG VARCHAR(10),
	 * @IN_PRODUCT_ID INT,
	 * @IN_PRODUCT_NAME VARCHAR(60),
	 * @IN_CONTRACT_BH VARCHAR(32),
	 * @IN_CAPITAL_NO VARCHAR(32),
	 * @IN_CAPITAL_NAME VARCHAR(128),
	 * @IN_INPUT_MAN INT
	 */
	void listContractCapital() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TCAPITALINFODETAIL
	 * @IN_CI_SERIAL_NO INT,
	 * @IN_INPUT_MAN INT
	 */
	void listCapitalModiDetail() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getCapitalModiDetailNext() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void listNoownerCapital() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void listByContractCapital() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getContractCapitalNext() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	boolean getNextBzr() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	boolean getNext() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextCapital2() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void appendChanges() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void saveChanges() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void deleteChanges() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void checkChanges() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void listChanges() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getChangesNext() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getCustDetailNext() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� amount��
	 */
	java.math.BigDecimal getAmount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            amount��
	 */
	void setAmount(java.math.BigDecimal amount);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� base_capital_no��
	 */
	String getBase_capital_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            base_capital_no��
	 */
	void setBase_capital_no(String base_capital_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� book_code��
	 */
	Integer getBook_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            book_code��
	 */
	void setBook_code(Integer book_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� btzr_cust_id��
	 */
	Integer getBtzr_cust_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            btzr_cust_id��
	 */
	void setBtzr_cust_id(Integer btzr_cust_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� busi_id��
	 */
	String getBusi_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            busi_id��
	 */
	void setBusi_id(String busi_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� can_use_year��
	 */
	Integer getCan_use_year();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            can_use_year��
	 */
	void setCan_use_year(Integer can_use_year);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� capital_manager��
	 */
	String getCapital_manager();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            capital_manager��
	 */
	void setCapital_manager(String capital_manager);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� capital_name��
	 */
	String getCapital_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            capital_name��
	 */
	void setCapital_name(String capital_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� capital_no��
	 */
	String getCapital_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            capital_no��
	 */
	void setCapital_no(String capital_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� capital_type��
	 */
	Integer getCapital_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            capital_type��
	 */
	void setCapital_type(Integer capital_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� capital_type_name��
	 */
	String getCapital_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            capital_type_name��
	 */
	void setCapital_type_name(String capital_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� capital_use��
	 */
	String getCapital_use();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            capital_use��
	 */
	void setCapital_use(String capital_use);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� capital_use_name��
	 */
	String getCapital_use_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            capital_use_name��
	 */
	void setCapital_use_name(String capital_use_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� capitaloper_type��
	 */
	Integer getCapitaloper_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            capitaloper_type��
	 */
	void setCapitaloper_type(Integer capitaloper_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� capitaloper_type_name��
	 */
	String getCapitaloper_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            capitaloper_type_name��
	 */
	void setCapitaloper_type_name(String capitaloper_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� card_id��
	 */
	Integer getCard_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            card_id��
	 */
	void setCard_id(Integer card_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� change_date��
	 */
	Integer getChange_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            change_date��
	 */
	void setChange_date(Integer change_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� change_type��
	 */
	String getChange_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            change_type��
	 */
	void setChange_type(String change_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� change_type_name��
	 */
	String getChange_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            change_type_name��
	 */
	void setChange_type_name(String change_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� check_flag��
	 */
	Integer getCheck_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            check_flag��
	 */
	void setCheck_flag(Integer check_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� check_man��
	 */
	Integer getCheck_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            check_man��
	 */
	void setCheck_man(Integer check_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� check_time��
	 */
	java.sql.Timestamp getCheck_time();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            check_time��
	 */
	void setCheck_time(java.sql.Timestamp check_time);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ci_serial_no��
	 */
	Integer getCi_serial_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            ci_serial_no��
	 */
	void setCi_serial_no(Integer ci_serial_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� contract_bh��
	 */
	String getContract_bh();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            contract_bh��
	 */
	void setContract_bh(String contract_bh);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� contract_sub_bh��
	 */
	String getContract_sub_bh();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            contract_sub_bh��
	 */
	void setContract_sub_bh(String contract_sub_bh);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� copynum��
	 */
	Integer getCopynum();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            copynum��
	 */
	void setCopynum(Integer copynum);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� current_balance��
	 */
	BigDecimal getCurrent_balance();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            current_balance��
	 */
	void setCurrent_balance(BigDecimal current_balance);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_id��
	 */
	Integer getCust_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            cust_id��
	 */
	void setCust_id(Integer cust_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_id2��
	 */
	String getCust_id2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            cust_id2��
	 */
	void setCust_id2(String cust_id2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_name��
	 */
	String getCust_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            cust_name��
	 */
	void setCust_name(String cust_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� dep_balance��
	 */
	BigDecimal getDep_balance();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            dep_balance��
	 */
	void setDep_balance(BigDecimal dep_balance);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� dep_month��
	 */
	BigDecimal getDep_month();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            dep_month��
	 */
	void setDep_month(BigDecimal dep_month);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� dep_status��
	 */
	Integer getDep_status();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            dep_status��
	 */
	void setDep_status(Integer dep_status);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� dep_total��
	 */
	BigDecimal getDep_total();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            dep_total��
	 */
	void setDep_total(BigDecimal dep_total);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� dep_type��
	 */
	String getDep_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            dep_type��
	 */
	void setDep_type(String dep_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� dep_type_name��
	 */
	String getDep_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            dep_type_name��
	 */
	void setDep_type_name(String dep_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� end_balance��
	 */
	BigDecimal getEnd_balance();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            end_balance��
	 */
	void setEnd_balance(BigDecimal end_balance);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� end_date��
	 */
	Integer getEnd_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            end_date��
	 */
	void setEnd_date(Integer end_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� end_rate��
	 */
	BigDecimal getEnd_rate();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            end_rate��
	 */
	void setEnd_rate(BigDecimal end_rate);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� field_cn_name��
	 */
	String getField_cn_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            field_cn_name��
	 */
	void setField_cn_name(String field_cn_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� field_name��
	 */
	String getField_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            field_name��
	 */
	void setField_name(String field_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� fx_date��
	 */
	Integer getFx_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            fx_date��
	 */
	void setFx_date(Integer fx_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� fx_days��
	 */
	Integer getFx_days();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            fx_days��
	 */
	void setFx_days(Integer fx_days);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� fx_money��
	 */
	java.math.BigDecimal getFx_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            fx_money��
	 */
	void setFx_money(java.math.BigDecimal fx_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� fx_rate��
	 */
	java.math.BigDecimal getFx_rate();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            fx_rate��
	 */
	void setFx_rate(java.math.BigDecimal fx_rate);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ht_money��
	 */
	java.math.BigDecimal getHt_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            ht_money��
	 */
	void setHt_money(java.math.BigDecimal ht_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� in_flag��
	 */
	String getIn_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            in_flag��
	 */
	void setIn_flag(String in_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� in_flag_name��
	 */
	String getIn_flag_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            in_flag_name��
	 */
	void setIn_flag_name(String in_flag_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� init_balance��
	 */
	BigDecimal getInit_balance();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            init_balance��
	 */
	void setInit_balance(BigDecimal init_balance);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� input_man��
	 */
	Integer getInput_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            input_man��
	 */
	void setInput_man(Integer input_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� input_time��
	 */
	java.sql.Timestamp getInput_time();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            input_time��
	 */
	void setInput_time(java.sql.Timestamp input_time);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� insurance��
	 */
	String getInsurance();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            insurance��
	 */
	void setInsurance(String insurance);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� last_dep_date��
	 */
	Integer getLast_dep_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            last_dep_date��
	 */
	void setLast_dep_date(Integer last_dep_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� market_money��
	 */
	java.math.BigDecimal getMarket_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            market_money��
	 */
	void setMarket_money(java.math.BigDecimal market_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� market_price��
	 */
	java.math.BigDecimal getMarket_price();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            market_price��
	 */
	void setMarket_price(java.math.BigDecimal market_price);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� money��
	 */
	java.math.BigDecimal getMoney();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            money��
	 */
	void setMoney(java.math.BigDecimal money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� new_field_info��
	 */
	String getNew_field_info();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            new_field_info��
	 */
	void setNew_field_info(String new_field_info);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� old_field_info��
	 */
	String getOld_field_info();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            old_field_info��
	 */
	void setOld_field_info(String old_field_info);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� pg_method��
	 */
	String getPg_method();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            pg_method��
	 */
	void setPg_method(String pg_method);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� pg_money��
	 */
	java.math.BigDecimal getPg_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            pg_money��
	 */
	void setPg_money(java.math.BigDecimal pg_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� price��
	 */
	java.math.BigDecimal getPrice();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            price��
	 */
	void setPrice(java.math.BigDecimal price);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� product_id��
	 */
	Integer getProduct_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            product_id��
	 */
	void setProduct_id(Integer product_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� product_name��
	 */
	String getProduct_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            product_name��
	 */
	void setProduct_name(String product_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� rec_no��
	 */
	Integer getRec_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            rec_no��
	 */
	void setRec_no(Integer rec_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� serial_no��
	 */
	Integer getSerial_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            serial_no��
	 */
	void setSerial_no(Integer serial_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� start_dat��
	 */
	Integer getStart_dat();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            start_dat��
	 */
	void setStart_dat(Integer start_dat);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� start_date��
	 */
	Integer getStart_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            start_date��
	 */
	void setStart_date(Integer start_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� status��
	 */
	String getStatus();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            status��
	 */
	void setStatus(String status);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� status_name��
	 */
	String getStatus_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            status_name��
	 */
	void setStatus_name(String status_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sub_code��
	 */
	String getSub_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            sub_code��
	 */
	void setSub_code(String sub_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� summary��
	 */
	String getSummary();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            summary��
	 */
	void setSummary(String summary);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� to_ci_serial_no��
	 */
	Integer getTo_ci_serial_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            to_ci_serial_no��
	 */
	void setTo_ci_serial_no(Integer to_ci_serial_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� trade_amount��
	 */
	java.math.BigDecimal getTrade_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            trade_amount��
	 */
	void setTrade_amount(java.math.BigDecimal trade_amount);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� trade_date��
	 */
	Integer getTrade_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            trade_date��
	 */
	void setTrade_date(Integer trade_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� trade_month��
	 */
	Integer getTrade_month();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            trade_month��
	 */
	void setTrade_month(Integer trade_month);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� unit��
	 */
	String getUnit();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            unit��
	 */
	void setUnit(String unit);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� use_year��
	 */
	Integer getUse_year();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            use_year��
	 */
	void setUse_year(Integer use_year);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� zclb_bh��
	 */
	String getZclb_bh();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            zclb_bh��
	 */
	void setZclb_bh(String zclb_bh);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            start_date1��
	 */
	void setStart_date1(Integer start_date1);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            start_date2��
	 */
	void setStart_date2(Integer start_date2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 */
	Integer getZc_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param integer
	 */
	void setZc_flag(Integer integer);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param string
	 */
	void setDbfs_type(String string);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 */
	String getDbfs_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 */
	Integer getTcapitalinfo_serial_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param integer
	 */
	void setTcapitalinfo_serial_no(Integer integer);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 */
	java.math.BigDecimal getDbzr_rate();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param decimal
	 */
	void setDbzr_rate(java.math.BigDecimal decimal);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� telphone��
	 */
	String getTelphone();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param telphone Ҫ���õ� telphone��
	 */
	void setTelphone(String telphone);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� capital_date��
	 */
	Integer getCapital_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param capital_date Ҫ���õ� capital_date��
	 */
	void setCapital_date(Integer capital_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� capital_jg��
	 */
	String getCapital_jg();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param capital_jg Ҫ���õ� capital_jg��
	 */
	void setCapital_jg(String capital_jg);

}