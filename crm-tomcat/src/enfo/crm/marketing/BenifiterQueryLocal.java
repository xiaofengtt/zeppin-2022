package enfo.crm.marketing;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.BenifitorVO;

public interface BenifiterQueryLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 受益人查询 SP_QUERY_TBENIFITOR
	 * @IN_BOOK_CODE INT,
	 * @IN_SERIAL_NO INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_CONTRACT_SUB_BH VARCHAR(50),
	 * @IN_SY_CUST_NO VARCHAR(8),
	 * @IN_SY_CARD_ID VARCHAR(40),
	 * @IN_SY_CUST_NAME VARCHAR(100),
	 * @IN_CUST_TYPE INT,
	 * @IN_PROV_LEVEL VARCHAR(10),
	 * @IN_BEN_STATUS VARCHAR(10),
	 * @IN_INPUT_MAN INT
	 * @throws Exception  
	 */
	IPageList listbySqlSYRAll(BenifitorVO vo, String[] totalColumn, int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 受益人查询——列表显示
	 * @param vo
	 * @throws BusiException
	 * @return list
	 */
	List listbySqlAll(BenifitorVO vo) throws Exception;

}