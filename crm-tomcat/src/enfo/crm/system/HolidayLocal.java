package enfo.crm.system;

import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.HolidayVO;

public interface HolidayLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ²éÑ¯È«²¿
	 * 
	 * @param localVO
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	IPageList queryHolidays(HolidayVO localVO, int page, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param localVO
	 * @return
	 * @throws Exception
	 */
	Integer addHolidays(HolidayVO localVO) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param localVO
	 * @throws Exception
	 */
	void modiHoliday(HolidayVO localVO) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param localVO
	 * @throws Exception
	 */
	void delHoliday(HolidayVO localVO) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param localVO
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	IPageList queryHolidayDate(HolidayVO localVO, int page, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param localVO
	 * @throws Exception
	 */
	void modiHolidayDate(HolidayVO localVO) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param localVO
	 * @throws Exception
	 */
	void delHolidayDate(HolidayVO localVO) throws Exception;

}