package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.AmCustInfoVO;
import enfo.crm.vo.CustomerInfoVO;

public interface AmCustInfoLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 根据cust_id读取客户附加信息
	 * @throws Exception
	 *             SP_AML_QUERY_TAMCUSTINFO
	 * @IN_CUST_ID INT,
	 * @IN_INPUT_MAN INT
	 */
	List load(AmCustInfoVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * update by guifeng 2008-08-21 新增客户附加信息 SP_AML_SET_TAMCUSTINFO
	 * @IN_CUST_ID INT,
	 * @IN_CBSC NVARCHAR(256),
	 * @IN_CRFT VARCHAR(10),
	 * @IN_CRFD DECIMAL(20,3),
	 * @IN_CTRN NVARCHAR(128),
	 * @IN_CRNM NVARCHAR(128),
	 * @IN_CRIT VARCHAR(10),
	 * @IN_CRID VARCHAR(30),
	 * @IN_CRVT INT,
	 * @IN_PCNM NVARCHAR(128),
	 * @IN_PITP VARCHAR(10),
	 * @IN_PICD VARCHAR(30),
	 * @IN_PIVT INT,
	 * @IN_INPUT_MAN INT,
	 */
	void update(AmCustInfoVO amvo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * update by guifeng 2008-08-25 新增客户信息 SP_AML_MODI_CUST
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

	/**
	 * @ejb.interface-method view-type = "local"
	 * // 币种
	 * @throws Exception
	 */
	String getCurrencyOptions(String value) throws Exception;

}