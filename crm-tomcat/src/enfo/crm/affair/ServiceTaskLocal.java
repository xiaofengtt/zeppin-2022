package enfo.crm.affair;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.ServiceTaskVO;

public interface ServiceTaskLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询待处理任务 By InputMan
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList query_dealTask_page(ServiceTaskVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页查询服务任务
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList query_page(ServiceTaskVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 新增任务信息 返回SerialNo
	 * @param vo
	 * @throws BusiException
	 */
	Object append(ServiceTaskVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改任务信息
	 * @param vo
	 * @throws BusiException
	 */
	void modi(ServiceTaskVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除任务信息
	 * @param vo
	 * @throws BusiException
	 */
	void delete(ServiceTaskVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 一般查询任务信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query(ServiceTaskVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 增加服务任务明细 增加客户
	 * @param vo
	 * @throws BusiException
	 */
	void append_details(ServiceTaskVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除明细
	 * @param vo
	 * @throws BusiException
	 */
	void delete_details(ServiceTaskVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询明细
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query_details(ServiceTaskVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页查询明细
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	IPageList query_detailsa(ServiceTaskVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分配任务
	 * @param vo
	 * @throws BusiException
	 */
	void allocate(ServiceTaskVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 处理任务
	 * @param vo
	 * @throws BusiException
	 */
	void treat(ServiceTaskVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 处理任务明细
	 * @param vo
	 * @throws BusiException
	 */
	void treat_details(ServiceTaskVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 反馈服务
	 * @param vo
	 * @throws BusiException
	 */
	void feedback(ServiceTaskVO vo) throws BusiException;

}