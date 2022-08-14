package enfo.crm.marketing;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.ActivityFeeVO;

public interface ActivityFeeLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 增加活动费用明细
	 * @param vo
	 * @throws BusiException
	 */
	void append(ActivityFeeVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改活动费用明细
	 * @param vo
	 * @throws BusiException
	 */

	void modi(ActivityFeeVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除活动费用明细
	 * @param vo
	 * @throws BusiException
	 */
	void delete(ActivityFeeVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页查询活动费用信息
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pageList_query(ActivityFeeVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询活动费用信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query(ActivityFeeVO vo) throws BusiException;

}