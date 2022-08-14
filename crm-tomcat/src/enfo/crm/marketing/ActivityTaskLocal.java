package enfo.crm.marketing;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.ActivityTaskVO;

public interface ActivityTaskLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local" 增加活动任务明细
	 * @param vo
	 * @throws BusiException
	 */
	void append(ActivityTaskVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 修改客户费用信息
	 * @param vo
	 * @throws BusiException
	 */
	void modi(ActivityTaskVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 删除客户费用信息
	 * @param vo
	 * @throws BusiException
	 */
	void delete(ActivityTaskVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 分页查询活动任务信息
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pageList_query(ActivityTaskVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 查询活动任务信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query(ActivityTaskVO vo) throws BusiException;

}