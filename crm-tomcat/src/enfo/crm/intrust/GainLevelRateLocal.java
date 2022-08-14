package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.GainLevelRateVO;

public interface GainLevelRateLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 新增收益率信息 SP_ADD_TGAINLEVELRATE
	 * 
	*IN_DF_SERIAL_NO
	*IN_GAIN_RATE
	*IN_START_DATE
	*IN_END_DATE
	*IN_INPUT_MAN
	* @param param
	* @throws Exception
	*/
	void add(GainLevelRateVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 修改收益率信息 SP_MODI_TGAINLEVELRATE
	 * 
	*IN_SERIAL_NO
	*IN_GAIN_RATE
	*IN_START_DATE
	*IN_END_DATE
	*IN_INPUT_MAN
	* @param param
	* @throws Exception
	*/
	void update(GainLevelRateVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 删除收益率信息 SP_DEL_TGAINLEVELRATE
	 * 
	*IN_SERIAL_NO
	*IN_INPUT_MAN
	* @param param
	* @throws Exception
	*/
	void del(GainLevelRateVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 查询收益率信息 SP_QUERY_TGAINLEVELRATE
	 * 
	*IN_DF_SERIAL_NO
	*IN_SERIAL_NO	
	* @param param
	* @throws Exception
	*/
	List query(GainLevelRateVO vo) throws Exception;

	/**
	 * 注释：CRM系统引用信托系统中的BONDINFOBean.java
	 * @ejb.interface-method view-type = "local"
	 * 
	 * add by tangshg 2009-11-24 开放日信息查询 SP_QUERY_TOPENDATE
	 * 
	 * @IN_SERIAL_NO INT, --序号
	 * @IN_PRODUCT_ID INT, --产品ID
	 * @IN_INPUT_MAN INT, --操作员
	 * @IN_OPEN_STATUS INT = 0 -- 确认标志 -- 1 预设、2 正式
	 *  
	 */
	List listOpenDate(GainLevelRateVO vo) throws Exception;

}