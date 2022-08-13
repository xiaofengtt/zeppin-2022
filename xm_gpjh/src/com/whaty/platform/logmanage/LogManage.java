package com.whaty.platform.logmanage;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Page;

/**
 * @author wq
 * 
 */
public abstract class LogManage {
	/**
	 * @param page
	 * @param id
	 * @param userid
	 * @param operateStartTime
	 * @param operateEndTime
	 * @param behavior
	 * @param status
	 * @param notes
	 * @param logtype
	 * @param priority
	 * @return
	 */
	public abstract List getLogEntities(Page page, String id, String userid,
			String operateStartTime, String operateEndTime, String behavior,
			String status, String notes, String logtype, String priority, boolean history);

	/**
	 * @param id
	 * @param userid
	 * @param operateStartTime
	 * @param operateEndTime
	 * @param behavior
	 * @param status
	 * @param notes
	 * @param logtype
	 * @param priority
	 * @return
	 */
	public abstract int getLogEntitiesNum(String id, String userid,
			String operateStartTime, String operateEndTime, String behavior,
			String status, String notes, String logtype, String priority, boolean history);

	/**
	 * @param id
	 * @param userid
	 * @param operateStartTime
	 * @param operateEndTime
	 * @param behavior
	 * @param status
	 * @param notes
	 * @param logtype
	 * @param priority
	 */
	public abstract int backUpLogEntities(String id, String userid,
			String operateStartTime, String operateEndTime, String behavior,
			String status, String notes, String logtype, String priority);
	
	/**
	 * 插入日志 
	 */
	public abstract int insertLogEntities(String id, String userid,
			String operateTime, String behavior,
			String status, String notes, String logtype, String priority)throws PlatformException;
	/**
	 * 插入日志，action调用
	 * @param behavior
	 * @return
	 * @throws PlatformException
	 */
	public abstract int insertLog(String behavior)throws PlatformException;
	/**
	 * 插入日志，供action调用
	 * @param behavior
	 * @param note
	 * @return
	 * @throws PlatformException
	 */
	public abstract int insertLog(String behavior,String note)throws PlatformException;
	/**
	 * 插入日志，用于jsp页面调用
	 * @param behavior
	 * @param userSession
	 * @return
	 * @throws PlatformException
	 */
	public abstract int insertLog(String behavior,UserSession userSession)throws PlatformException;
	
	public abstract int insertLog(String user_id, String operate_time, String behavior, String status, String notes, String logtype, String priority) throws PlatformException ;
}