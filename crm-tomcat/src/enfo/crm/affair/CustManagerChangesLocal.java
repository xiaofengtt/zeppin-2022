package enfo.crm.affair;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.CustomerVO;
import enfo.crm.vo.TcustmanagerchangesVO;

public interface CustManagerChangesLocal extends IBusiExLocal{

	/**
	 * @ejb.interface-method view-type = "local"
	 * 新增经理移交基本信息
	 * @param vo
	 * 	@IN_MANAGERID_BEFORE	int
		@IN_MANAGERID_NOW	int
		@IN_INPUT_MAN	int
	 */
	void append(TcustmanagerchangesVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改经理移交基本信息
	 * @param vo
		@IN_SERIAL_NO	int
		@IN_MANAGERID_BEFORE	int
		@IN_MANAGERID_NOW	int
		@IN_INPUT_MAN	int
	 */
	void modi(TcustmanagerchangesVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除经理移交基本信息
	 * @param vo
		@IN_SERIAL_NO	int
		@IN_INPUT_MAN	int
	 */
	void delete(TcustmanagerchangesVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页查询经理移交基本信息
	 * @param vo
		@IN_SERIAL_NO	int
		@IN_MANAGERNAME_BEFORE	nvarchar
		@IN_MANAGERNAME_NOW	nvarchar
		@IN_CHECK_FLAG	int
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pagelist_query(TcustmanagerchangesVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询经理移交基本信息
		@IN_SERIAL_NO	int
		@IN_MANAGERNAME_BEFORE	nvarchar
		@IN_MANAGERNAME_NOW	nvarchar
		@IN_CHECK_FLAG	int
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_query(TcustmanagerchangesVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 新增经理移交审核
	 * @param vo
	 * @IN_SERIAL_NO	int
		@IN_CHECK_FLAG	int
		@IN_INPUT_MAN	int
	 */
	void check(TcustmanagerchangesVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改客户录入人
	 * @param vo
	 */
	void modiCustInputMan(CustomerVO vo) throws BusiException;

}