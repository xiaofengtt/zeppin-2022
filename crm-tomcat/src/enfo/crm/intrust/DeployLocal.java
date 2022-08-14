package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.DeployVO;

public interface DeployLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ������˻���ϸ
	 * @param vo
	 * @throws BusiException
	 * @return list
	 */
	List querycust(DeployVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ�����Ʒ��������ϸ
	 * @param vo
	 * 				@IN_CUST_ID INT,                 --�ͻ�ID
	 *           	@IN_PRODUCT_ID INT,              --��ƷID
	 * 				@IN_SUB_PRODUCT_ID INT ,         --�Ӳ�ƷID
	 *  			@IN_CONTRACT_SUB_BH VARCHAR(80), --��ͬʵ�ʱ��
	 *  			@IN_INPUT_MAN INT                --����Ա
	 * @throws BusiException
	 * @return list
	 */
	List queryForCust(DeployVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ������˻���ϸ
	 * @param vo
	 * @throws BusiException
	 * @return list
	 */
	List queryDeployByCustId(DeployVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TDEPLOY_OUTPUT @IN_BOOK_CODE INT, @IN_PRODUCT_ID INT,
	 * @IN_BANK_ID VARCHAR(10), @IN_SY_TYPE VARCHAR(10), @IN_JK_TYPE VARCHAR(10) =
	 * '', @IN_SY_DATE INT = NULL, @IN_PROV_LEVEL VARCHAR(10) = NULL,
	 * @IN_INPUT_MAN INT = NULL
	 */
	IPageList queryOutputList(DeployVO vo, int page, int pagesize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	IPageList queryDetail(DeployVO vo, int page, int pagesize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TDEPLOY_LOAD @IN_SERIAL_NO INT = 0
	 */
	List load(Integer serial_no) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 *  SP_QUERY_TDEPLOY_OUTPUT �������������@IN_INPUT_MAN ��Ȼ��2005-7-26
	 * 
	 * @return
	 * @throws Exception
	 */
	sun.jdbc.rowset.CachedRowSet getOutputListResult(DeployVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TDEPLOY_OUTPUT_ALL�������������@IN_SY_TYPE��@IN_SY_DATE��@IN_PROV_LEVEL��@IN_INPUT_MAN
	 * 
	 * @return
	 * @throws Exception
	 */
	sun.jdbc.rowset.CachedRowSet getDeployOutputResult(DeployVO vo, String date) throws Exception;

}