package enfo.crm.affair;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.ScheDulesVO;

public interface ScheDulesLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 新增
	 * @param vo
	@IN_SCHEDULE_TYPE	int
	@IN_SCHEDULE_DATE	int
	@IN_CONTENT	nvarchar
	@IN_INPUT_MAN	int
	 */
	void append(ScheDulesVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改
	 * @param vo
		@IN_SERIAL_NO	int
		@IN_SCHEDULE_TYPE	int
		@IN_SCHEDULE_DATE	int
		@IN_CONTENT	nvarchar
		@IN_CHECK_FLAG	int
		@IN_INPUT_MAN	int
	 */
	void modi(ScheDulesVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除
	 * @param vo
	@IN_SERIAL_NO	int
	@IN_INPUT_MAN	int
	 */
	void delete(ScheDulesVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页查询
	 * @param vo
	@IN_SERIAL_NO	int
	@IN_INPUT_MAN	int
	@IN_BEGIN_DATE	int
	@IN_END_DATE	int
	@IN_SCHEDULE_TYPE	int
	@IN_CHECK_FLAG	int
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pagelist_query(ScheDulesVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询
		@IN_SERIAL_NO	int
		@IN_INPUT_MAN	int
		@IN_BEGIN_DATE	int
		@IN_END_DATE	int
		@IN_SCHEDULE_TYPE	int
		@IN_CHECK_FLAG	int
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_query(ScheDulesVO vo) throws BusiException;

}