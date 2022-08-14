package enfo.crm.intrust;

import java.math.BigDecimal;
import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;

public interface ValidateprintLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ȷ�ϵ���ӡ----����ҳ����ѯ
	 * 
	 * SP_QUERY_TBENAMOUNTDETAIL_CRM IN_SERIAL_NO INT ��� IN_PRODUCT_ID INT ��ƷID
	 * IN_CONTRACT_BH VARCHAR(16) ��ͬ��� IN_CUST_NAME VARCHAR(100) �ͻ�����
	 * IN_CHG_TYPE INT �ݶ�䶯ҵ����� 1�Ϲ� 2�깺 3��� 4�ݶ�ת�� IN_START_DATE INT ��ʼ����
	 * IN_END_DATE INT ��ֹ���� IN_INPUT_MAN INT ����Ա
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listAll(int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ����ȷ�ϵ���ӡ----����ҳ����ѯ
	 * 
	 * SP_QUERY_TMONEYDETAIL_4CUST
	 *   @IN_PRODUCT_ID           INT,                            --��ƷID
	 * 	 @IN_SUB_PRODUCT_ID       INT ,                           --�Ӳ�ƷID   20111112  LUOHH
	 * 	 @IN_PRODUCT_NAME         NVARCHAR(100) ,                 --��Ʒ����
	 *   @IN_CUST_ID              INTEGER ,                       --�ͻ�ID   
	 *   @IN_CUST_NAME            NVARCHAR(100) ,                 --�ͻ�����
	 *   @IN_CONTRACT_SUB_BH      NVARCHAR(50) ,                  --ʵ�ʺ�ͬ���
	 *   @IN_DZ_DATE              INTEGER         = NULL,         --�ɿ�ʱ����������
	 *   @IN_END_DATE             INTEGER         = 0,            --�ɿ�ʱ����������
	 *   @IN_CHECK_FLAG           INTEGER,                        --��˱�־
	 *   @IN_INPUT_MAN            INTEGER                         --����Ա
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listAllFz(int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ����ȷ�ϵ���ӡ----����ҳ����ѯ
	 * 
	 * SP_QUERY_HCUSTZJBD IN_SERIAL_NO INT ��� IN_PRODUCT_ID INT ��ƷID
	 * IN_CONTRACT_SUB_BH VARCHAR(16) ��ͬ��� IN_CUST_NAME VARCHAR(100) �ͻ�����
	 * IN_CHG_TYPE INT �ݶ�䶯ҵ����� 1�Ϲ� 2�깺 IN_START_DATE INT ��ʼ����
	 * IN_END_DATE INT ��ֹ���� IN_INPUT_MAN INT ����Ա
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listAll1(int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ȷ�ϵ���ӡ----����һ����ѯ
	 * 
	 * SP_QUERY_TBENAMOUNTDETAIL_CRM IN_SERIAL_NO INT ��� IN_PRODUCT_ID INT ��ƷID
	 * IN_CONTRACT_BH VARCHAR(16) ��ͬ��� IN_CUST_NAME VARCHAR(100) �ͻ�����
	 * IN_CHG_TYPE INT �ݶ�䶯ҵ����� 1�Ϲ� 2�깺 3��� 4�ݶ�ת�� IN_START_DATE INT ��ʼ����
	 * IN_END_DATE INT ��ֹ���� IN_INPUT_MAN INT ����Ա
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	List listBySql() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ȷ�ϵ���ӡ----����һ����ѯ
	 * 
	 * SP_QUERY_HCUSTZJBD IN_SERIAL_NO INT ��� IN_PRODUCT_ID INT ��ƷID
	 * IN_CONTRACT_SUB_BH VARCHAR(16) ��ͬ��� IN_CUST_NAME VARCHAR(100) �ͻ�����
	 * IN_CHG_TYPE INT �ݶ�䶯ҵ����� 1�Ϲ� 2�깺 IN_START_DATE INT ��ʼ����
	 * IN_END_DATE INT ��ֹ���� IN_INPUT_MAN INT ����Ա
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	List listBySql1() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �ݶ�䶯��¼---����
	 * 
	 * SP_ADD_TBENAMOUNTDETAIL IN_PRODUCT_ID INT ��ƷID IN_CONTRACT_BH VARCHAR(16)
	 * ��ͬ��� IN_CUST_ID VARCHAR(100) �ͻ�ID IN_CHG_TYPE INT �ݶ�䶯ҵ����� 1�Ϲ� 2�깺 3���
	 * 4�ݶ�ת�� IN_APPL_AMOUNT DECIMAL(16,3) ������ IN_FEE_MONEY DECIMAL(16,3) ����
	 * IN_PRICE DECIMAL(16,3) �۸񣨾�ֵ�� IN_CHG_MONEY DECIMAL(16,3) �䶯���
	 * IN_CHG_AMOUNT DECIMAL(16,3) �䶯�ݶ� IN_AFTER_MONEY DECIMAL(16,3) �䶯����
	 * IN_AFTER_AMOUNT DECIMAL(16,3) �䶯��ݶ� IN_SQ_DATE INT �������ڣ��������ڣ� IN_DZ_DATE
	 * INT �ʽ������� IN_HK_DATE INT �ʽ𻮿����� IN_JK_TYPE VARCHAR(10) �ɿʽ��1114��
	 * IN_INPUT_MAN INT ����Ա
	 * 
	 * @throws BusiException
	 */
	void append() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ȷ�ϵ��޸Ĵ�ӡ��־
	 * @param print_flag
	 * @param serial_no
	 * @throws BusiException
	 */
	void updatePrintFlag(Integer print_flag, Integer serial_no) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ȷ�ϵ��޸Ĵ�ӡ��־
	 * @param print_flag
	 * @param serial_no
	 * @throws BusiException
	 */
	void updatePrintFlag1(Integer print_flag, Integer serial_no) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� after_amount��
	 */
	BigDecimal getAfter_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            after_amount��
	 */
	void setAfter_amount(BigDecimal after_amount);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� after_money��
	 */
	BigDecimal getAfter_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            after_money��
	 */
	void setAfter_money(BigDecimal after_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� appl_amout��
	 */
	BigDecimal getAppl_amout();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            appl_amout��
	 */
	void setAppl_amout(BigDecimal appl_amout);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ben_start_date��
	 */
	Integer getBen_start_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            ben_start_date��
	 */
	void setBen_start_date(Integer ben_start_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� chg_amount��
	 */
	BigDecimal getChg_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            chg_amount��
	 */
	void setChg_amount(BigDecimal chg_amount);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� chg_money��
	 */
	BigDecimal getChg_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            chg_money��
	 */
	void setChg_money(BigDecimal chg_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� chg_type��
	 */
	Integer getChg_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            chg_type��
	 */
	void setChg_type(Integer chg_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� chg_type_name��
	 */
	String getChg_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            chg_type_name��
	 */
	void setChg_type_name(String chg_type_name);

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
	 * @return ���� dz_date��
	 */
	Integer getDz_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            dz_date��
	 */
	void setDz_date(Integer dz_date);

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
	 * @return ���� fee_money��
	 */
	BigDecimal getFee_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            fee_money��
	 */
	void setFee_money(BigDecimal fee_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� fee_rate��
	 */
	BigDecimal getFee_rate();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            fee_rate��
	 */
	void setFee_rate(BigDecimal fee_rate);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� hk_date��
	 */
	Integer getHk_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            hk_date��
	 */
	void setHk_date(Integer hk_date);

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
	 * @return ���� jk_type��
	 */
	String getJk_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            jk_type��
	 */
	void setJk_type(String jk_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� jk_type_name��
	 */
	String getJk_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            jk_type_name��
	 */
	void setJk_type_name(String jk_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� price��
	 */
	BigDecimal getPrice();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            price��
	 */
	void setPrice(BigDecimal price);

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
	 * @return ���� sq_date��
	 */
	Integer getSq_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            sq_date��
	 */
	void setSq_date(Integer sq_date);

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
	 * @return
	 */
	Integer getPrint_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param printFlag
	 */
	void setPrint_flag(Integer printFlag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 */
	String getProduct_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param productName
	 */
	void setProduct_name(String productName);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 */
	Integer getSub_product_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param subProductId
	 */
	void setSub_product_id(Integer subProductId);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� contract_sub_bh��
	 */
	String getContract_sub_bh();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param contract_sub_bh Ҫ���õ� contract_sub_bh��
	 */
	void setContract_sub_bh(String contract_sub_bh);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� check_flag��
	 */
	Integer getCheck_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param check_flag Ҫ���õ� check_flag��
	 */
	void setCheck_flag(Integer check_flag);

}