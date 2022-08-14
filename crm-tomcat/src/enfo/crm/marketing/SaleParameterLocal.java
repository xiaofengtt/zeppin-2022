package enfo.crm.marketing;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.CommissionRateVO;
import enfo.crm.vo.SaleParameterVO;

public interface SaleParameterLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * @param vo
	 * @IN_PRODUCT_ID
	 * @IN_TEAM_ID
	 * @IN_QUOTAMONEY
	 * @throws BusiException TZ_QUALIFIED_NUM
	 */
	void modi(SaleParameterVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @IN_TEAM_ID INT
	 * @IN_PRODUNT_ID INT
	 * @IN_INPUT_MAN INT
	 * @throws BusiException
	 */
	IPageList query(SaleParameterVO vo, String[] totalColumn, Integer input_operatorCode, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @IN_TEAM_ID INT
	 * @IN_PRODUNT_ID INT
	 * @IN_INPUT_MAN INT
	 * @throws BusiException
	 */
	List queryQuota(SaleParameterVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @IN_TEAM_ID INT
	 * @IN_PRODUNT_ID INT
	 * @IN_INPUT_MAN INT
	 * @throws BusiException
	 */
	List getQualified_num(SaleParameterVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * SP_MODI_TTEAMQUOTA_SUBSCRIBE	@IN_SERIAL_NO                       
									@IN_PRODUCT_ID                      
									@IN_TEAM_ID                         
									@IN_ALREADYSALE                     
									@IN_SERIAL_NO_CONTRACT              
									@IN_ALREADY_QUALIFIED_NUM           
									@IN_INPUT_MAN                       
									@IN_PREPRODUCT_ID                   
									@IN_SUB_PRODUCT_ID
	 * @param vo
	 * @IN_PRODUCT_ID
	 * @IN_TEAM_ID
	 * @IN_QUOTAMONEY
	 * @throws BusiException
	 */
	void modiAlreadysale(SaleParameterVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * @param vo
	 * @return
	 * @IN_TEAM_ID INT
	 * @IN_PRODUNT_ID INT
	 * @IN_INPUT_MAN INT
	 * @throws BusiException
	 */
	List queryTeam(SaleParameterVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @IN_PRODUCT_ID
	 * @IN_TEAM_ID
	 * @IN_QUOTAMONEY
	 * @throws BusiException
	 */
	void adjustQuota(SaleParameterVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryTeamHis(SaleParameterVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryTeamHisDetail(SaleParameterVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_id
	 * @return
	 * @throws BusiException
	 */
	List queryTTeamValue(Integer product_id) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param input_man
	 * @return
	 * @throws BusiException
	 */
	List queryTTeamValue1(Integer product_id, Integer leader, Integer sub_productId) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryPersonValue(SaleParameterVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void modiPersonValue(SaleParameterVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * @param vo
	 *   @IN_PRODUCT_ID         INTEGER       ,       --��ƷID
	 *   @IN_SUBPRODUCT_ID      INTEGER       ,       --�Ӳ�ƷID
	 *   @IN_PERIOD             INTEGER       ,       --����ֵ
	 *   @IN_PERIOD_UNIT        INTEGER       ,       -- 0������ 1�� 2�� 3��
	 *   @IN_TRADE_START_MONEY  INTEGER       ,       --��ͬ�ݶ�����
	 *   @IN_TRADE_END_MONEY    INTEGER       ,       --��ͬ�ݶ�����
	 *   @IN_FEE_RATE           DECIMAL(5,4),         --��ɱ���
	 *   @IN_FEE_AMOUNT         DECIMAL(16,2),        --��ɹ̶����
	 *   @IN_SUMMARY            NVARCHAR(400),        --��ע
	 *   @IN_INPUT_MAN          INTEGER               --¼����
	 * @throws BusiException
	 */
	void addCommissionRate(CommissionRateVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * @param vo
	 *   @IN_PRODUCT_ID         INTEGER       ,       --��ƷID
	 *   @IN_SUBPRODUCT_ID      INTEGER       ,       --�Ӳ�ƷID
	 *   @IN_INPUT_MAN          INTEGER               --¼����
	 * @throws BusiException
	 */
	void delCommissionRate(CommissionRateVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * @param vo
	 *   @IN_PRODUCT_ID         INTEGER       ,       --��ƷID
	 *   @IN_SUBPRODUCT_ID      INTEGER       ,       --�Ӳ�ƷID
	 *   @IN_INPUT_MAN          INTEGER               --¼����
	 * @throws BusiException
	 */
	List queryCommissionRate(CommissionRateVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * @param vo
	 *   @IN_PRODUCT_ID         INTEGER       ,       --��ƷID
	 *   @IN_SUBPRODUCT_ID      INTEGER       ,       --�Ӳ�ƷID
	 *   @IN_OP_CODE           INT ,       --������Ա(TOPERATOR.OP_CODE)
	 *   @IN_CUST_ID           INT ,       --�ͻ�ID
	 *   @IN_CUST_NAME         VARCHAR(100),
	 *   @IN_INPUT_MAN          INTEGER               --¼����
	 * @throws BusiException
	 */
	IPageList queryManagerCommission(SaleParameterVO vo, int page, int pagesize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * @param vo
	 *   @IN_PRODUCT_ID         INTEGER       ,       --��ƷID
	 *   @IN_SUBPRODUCT_ID      INTEGER       ,       --�Ӳ�ƷID
	 *   @IN_INPUT_MAN          INTEGER               --¼����
	 * @throws BusiException
	 */
	void calManagerCommission(SaleParameterVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * @param vo
	 *   @IN_PRODUCT_ID         INTEGER       ,       --��ƷID
	 *   @IN_SUBPRODUCT_ID      INTEGER       ,       --�Ӳ�ƷID
	 * 	 @IN_PREPRODUCT_ID		INTEGER		  ,		  --
	 * 	 @IN_TEAM_TYPE 			INTEGER		  ,		  --����1���Ŷ�2
	 * 	 @IN_SELECT_ID			INTEGER		  ,		  --CUST_ID �� TEAM_ID
	 * @throws BusiException
	 */
	IPageList queryTeamOrPersonQuota(SaleParameterVO vo, String[] totalColumn, int page, int pagesize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryTTeamValue2(SaleParameterVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryPersonValueNew(SaleParameterVO vo) throws BusiException;

}