package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.GainLevelRateVO;

public interface GainLevelRateLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ������������Ϣ SP_ADD_TGAINLEVELRATE
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
	 * �޸���������Ϣ SP_MODI_TGAINLEVELRATE
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
	 * ɾ����������Ϣ SP_DEL_TGAINLEVELRATE
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
	 * ��ѯ��������Ϣ SP_QUERY_TGAINLEVELRATE
	 * 
	*IN_DF_SERIAL_NO
	*IN_SERIAL_NO	
	* @param param
	* @throws Exception
	*/
	List query(GainLevelRateVO vo) throws Exception;

	/**
	 * ע�ͣ�CRMϵͳ��������ϵͳ�е�BONDINFOBean.java
	 * @ejb.interface-method view-type = "local"
	 * 
	 * add by tangshg 2009-11-24 ��������Ϣ��ѯ SP_QUERY_TOPENDATE
	 * 
	 * @IN_SERIAL_NO INT, --���
	 * @IN_PRODUCT_ID INT, --��ƷID
	 * @IN_INPUT_MAN INT, --����Ա
	 * @IN_OPEN_STATUS INT = 0 -- ȷ�ϱ�־ -- 1 Ԥ�衢2 ��ʽ
	 *  
	 */
	List listOpenDate(GainLevelRateVO vo) throws Exception;

}