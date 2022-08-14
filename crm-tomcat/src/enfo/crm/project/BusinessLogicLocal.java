package enfo.crm.project;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;

public interface BusinessLogicLocal extends IBusiExLocal {

	/**
	 * 更新业务状态
	 * @param object_id
	 * @param object_type
	 * @param action_flag
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void actionFlow(String object_id, String object_type, String action_flag) throws BusiException;

	/**
	 * 删除对象信息
	 * @param object_id
	 * @param object_type
	 * @param action_flag 
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void delObject(String object_id, String object_type, String action_flag) throws BusiException;

	/**
	 * 更新对象信息
	 * @param object_id
	 * @param object_type
	 * @param action_flag 
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void updateObject(String object_id, String object_type, String action_flag) throws BusiException;

	/**
	 * 获得当前用户下具有所有角色
	 * @param userID
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listUserRole(String userID) throws BusiException;

	/**
	 * 获得当前用户下具有角色的所有的显示内容
	 * @param userID
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listAllWork(String userID, String roleID, String productType) throws BusiException;

	/**
	 * 获得当前用户下具有角色的所有的显示内容汇总合计
	 * @param userID
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listWorkCount(String sql) throws BusiException;

	/**
	 * 获得桌面任务显示条件
	 * @param workNo
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listWorkTask(String workNo) throws BusiException;

	/**
	 * 显示各个员工的未结束或者已结束流程统计
	 * @param flowNodeFlag
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listFlowStat(int flowNodeFlag) throws BusiException;

	/**
	 * 根据用户、月份和结束标志查询
	 * @param opCode 用户ID
	 * @param queryMonth 查询月份
	 * @param flowNodeFlag 1未结束流程，2已结束流程
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listFlowStatByUser(int opCode, String queryMonth, int flowNodeFlag) throws BusiException;

	/**
	 * 生成流程统计数据
	 * @param userID
	 * @param genDate
	 * @param queryType
	 * @throws Exception
	 * @ejb.interface-method view-type = "local"
	 */
	List listFlowTask(String userID, String genDate, String queryType) throws BusiException;

	//@Override
	void remove();

	/**
	 * 从删除中恢复数据
	 * @param object_id
	 * @param object_type
	 * @param action_flag
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void resumeObject(String object_id, String object_type, String action_flag) throws BusiException;

	/**
	 * 根据表名和and条件查询
	 * @ejb.interface-method view-type = "local"
	 */
	List listByTableCondition(String tableName, String andSqlCondition) throws BusiException;

	/**
	 * 获得部门级项目权限列表
	 * @param userID
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listDepartItemQuery(String item_id) throws BusiException;

}