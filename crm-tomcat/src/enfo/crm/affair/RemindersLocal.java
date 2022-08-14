package enfo.crm.affair;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.RemindersVO;

public interface RemindersLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 新增
	 * @param vo
		@IN_SCHEDULE_DATE	int
		@IN_CONTENT	nvarchar
		@IN_INPUT_MAN	int
	 */
	void append(RemindersVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改
	 * @param vo
		@IN_SERIAL_NO	int
		@IN_SCHEDULE_DATE	int
		@IN_CONTENT	nvarchar
		@IN_CHECK_FLAG	int
		@IN_INPUT_MAN	int
	 */
	void modi(RemindersVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除
	 * @param vo
		@IN_SERIAL_NO	int
		@IN_INPUT_MAN	int
	 */
	void delete(RemindersVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页查询
	 * @param vo
		@IN_SERIAL_NO	int
		@IN_INPUT_MAN	int
		@IN_BEGIN_DATE	int
		@IN_END_DATE	int
		@IN_CHECK_FLAG	int
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pagelist_query(RemindersVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询
		@IN_SERIAL_NO	int
		@IN_INPUT_MAN	int
		@IN_BEGIN_DATE	int
		@IN_END_DATE	int
		@IN_CHECK_FLAG	int
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_query(RemindersVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页查询 员工日程
	 * @param vo
		 @IN_OP_CODE             INT,              --员工编号
	     @IN_BEGIN_DATE          INT = 0,          --起始时间
	     @IN_END_DATE            INT = 0,          --结束时间
	     @IN_SCHEDULE_TYPE       INT,              --日程类型 --约会/活动/计划         
	     @IN_INPUT_MAN           INT               --操作员
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pagelistStaffSchedule(RemindersVO vo, int pageIndex, int pageSize) throws BusiException;

}