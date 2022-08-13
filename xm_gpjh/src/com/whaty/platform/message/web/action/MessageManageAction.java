package com.whaty.platform.message.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.logmanage.LogManage;
import com.whaty.platform.message.bean.MsgInfo;
import com.whaty.platform.message.exception.MessageException;
import com.whaty.platform.message.service.MessageService;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.JsonUtil;
import com.whaty.util.Exception.WhatyUtilException;
import com.whaty.util.string.StrManage;
import com.whaty.util.string.StrManageFactory;

public class MessageManageAction extends MessageBaseAction {
	
	private String curStatus; //用户是否有新短消息
	
	//list返回给extjs的json字符串
	private String jsonString;

	// ----extjs 分页程序提交的request参数
	private String start; // 列表开始的位置
	private String limit; // 每页要显示的条目
	private String sort; // 排序的对应列
	private String dir; // 升序asc，降序desc
	private String messageIds;  //删除对应的消息id的json表达式
	// ------------------------------
	
	
	//搜索条件相关变量
	String startDate;  //开始时间
 	String endDate; 	//结束时间
 	String status;  //状态，00代表未阅读，01代表已阅读
 	String canReply; //是否允许回复，0代表不允许,1代表允许
 	String userId; //当前用户ID
	//搜索条件相关变量end
	
	private MsgInfo message; //页面显示时用的消息类。
 	
	private MessageService messageService;

	
	
	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		String loginId = "";
		UserSession us = (UserSession)ServletActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		try {
			loginId = us.getLoginId();
		} catch (Exception e) {
		}
		return loginId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the canReply
	 */
	public String getCanReply() {
		return canReply;
	}

	/**
	 * @param canReply the canReply to set
	 */
	public void setCanReply(String canReply) {
		this.canReply = canReply;
	}

	
	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the message
	 */
	public MsgInfo getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(MsgInfo message) {
		this.message = message;
	}

	/**
	 * @return the jsonString
	 */
	public String getJsonString() {
		return jsonString;
	}

	/**
	 * @param jsonString the jsonString to set
	 */
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	/**
	 * @return the start
	 */
	public String getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * @return the limit
	 */
	public String getLimit() {
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(String limit) {
		this.limit = limit;
	}

	/**
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * @return the dir
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * @param dir the dir to set
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}

	/**
	 * @return the messageIds
	 */
	public String getMessageIds() {
		return messageIds;
	}

	/**
	 * @param messageIds the messageIds to set
	 */
	public void setMessageIds(String messageIds) {
		this.messageIds = messageIds;
	}

	/**
	 * @return the messageService
	 */
	public MessageService getMessageService() {
		return messageService;
	}

	/**
	 * @param messageService the messageService to set
	 */
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	/**
	 * @return the curStatus
	 */
	public String getCurStatus() {
		return curStatus;
	}

	/**
	 * @param curStatus the curStatus to set
	 */
	public void setCurStatus(String curStatus) {
		this.curStatus = curStatus;
	}
	
	public String status()
	{
		if(this.getMessageService().getCurStatus(this.getUserId()))
			this.setCurStatus("1");
		else
			this.setCurStatus("0");
		return "curStatus";
	}
	
	/**
	 * 获得消息列表
	 */
	public String listByPage()throws MessageException{
		
		if(this.getUserId()==null || this.getUserId().length()<1)
			throw new MessageException();
		
		StrManage strManage=StrManageFactory.creat();
		
		//增加查询条件
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MsgInfo.class);
		
		if (this.getSort() != null) {
			if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc"))
				detachedCriteria.addOrder(Order.desc(this.getSort()));
			else
				detachedCriteria.addOrder(Order.asc(this.getSort()));
		}
		if(this.getStartDate()!=null && this.getStartDate().length()>0)
		{
			try {
				detachedCriteria.add(Restrictions.ge("sendTime",strManage.strToDate(this.getStartDate(),null)));
			} catch (WhatyUtilException e) {
				// TODO Auto-generated catch block
				
			}
		}
		detachedCriteria.add(Restrictions.eq("userId",this.getUserId()));
		
		if(this.getEndDate()!=null && this.getEndDate().length()>0)
		{
			try {
				detachedCriteria.add(Restrictions.le("sendTime", strManage.strToDate(this.getEndDate(),null)));
			} catch (WhatyUtilException e) {
				// TODO Auto-generated catch block
				
			}
		}
		
		if(this.getCanReply()!=null && !"".equals(this.getCanReply())){
			detachedCriteria.add(Restrictions.eq("canReply",this.getCanReply()));
		}
		if(this.getStatus() !=null && !"".equals(this.getStatus())){
			detachedCriteria.add(Restrictions.eq("status",this.getStatus()));
		}
			
		Page page = this.getMessageService().getMsgInfosByPage(detachedCriteria,Integer.parseInt(this.getLimit()),Integer.parseInt(this.getStart()));
	
//		获得总条数与列表;
		String strCount = "";
		int totalCount = page.getTotalCount();
		if(totalCount>0)
			strCount = (new Integer(totalCount)).toString();
		
		//json处理
		Map map = new HashMap();
		map.put("totalCount",strCount);
		map.put("messages",page.getItems());
		jsonString = JsonUtil.toJSONString(map);
		
		logger.info("json string: "+jsonString);
		
		return "listjson";
	}
	
	/**
	 * 添加
	 */
	public String add()throws MessageException{
		com.whaty.platform.logmanage.LogFactory factory = com.whaty.platform.logmanage.LogFactory.getInstance();
		LogManage logManage = factory.creatLogManage();
		HttpServletRequest request = ServletActionContext.getRequest();
		MsgInfo msgInfo=new MsgInfo();
		msgInfo.setUserId(request.getParameter("msgUserId"));
		msgInfo.setFromUserId(request.getParameter("msgFromUserId"));
		msgInfo.setFromUserName(request.getParameter("msgFromUserName"));
		msgInfo.setContent(request.getParameter("msgContent"));
		msgInfo.setCanReply(request.getParameter("msgCanReply"));
		msgInfo.setStatus("00");
		msgInfo.setSendTime(new Date());
		//msgInfo.setExpireTime(request.getParameter("expireTime"));
		
		MsgInfo  n = this.getMessageService().save(msgInfo);
		
		Map map = new HashMap();
		if(n != null && n.getId() !=null){
			map.put("success","true");
			map.put("info", this.getText("msg.successinfo"));
			try {
				logManage.insertLog("添加消息成功");
			} catch (PlatformException e) {
				
			}
			logger.info("添加消息成功! id= "+n.getId());
		}else{
			map.put("failure","true");
			map.put("errorInfo", "添加消息失败");
			try {
				logManage.insertLog("添加消息失败");
			} catch (PlatformException e) {
				
			}
			logger.error("添加消息失败!");
		}
		
		//json处理
		JSONObject obj = JSONObject.fromObject(map);
		jsonString = obj.toString();
		
		return "listjson";
	}
	
	/**
	 * 删除信息
	 */
	public String delete()  throws EntityException{
		com.whaty.platform.logmanage.LogFactory factory = com.whaty.platform.logmanage.LogFactory.getInstance();
		LogManage logManage = factory.creatLogManage();
		HttpServletRequest request = ServletActionContext.getRequest();
		Map map = new HashMap();
		if (request.getParameter("msgIds") != null && request.getParameter("msgIds").length()>0) {
			String str =request.getParameter("msgIds");	
			if(str!=null && str.length()>0)
			{
				String[] ids=str.split(",");
				List idList=new ArrayList();
				for(int i=0;i<ids.length;i++)
				{
					idList.add(ids[i]);
					this.getMessageService().deleteMsgInfosByIds(idList);
					map.put("success","true");
					map.put("info",str);
					try {
						logManage.insertLog("删除短信成功");
					} catch (PlatformException e) {
						
					}
				}
			}
			else
			{
				map.put("failure","true");
				map.put("errorInfo", "delete error");
				try {
					logManage.insertLog("删除短信失败");
				} catch (PlatformException e) {
					
				}
			}
		}
		
		JSONObject jsonObject = JSONObject.fromObject(map);
		jsonString = jsonObject.toString();
		return "listjson";
	}
	
	/**
	 * 标记信息已读
	 */
	public String markReaded()  throws EntityException{
		HttpServletRequest request = ServletActionContext.getRequest();
		Map map = new HashMap();
		if (request.getParameter("msgIds") != null && request.getParameter("msgIds").length()>0) {
			String str =request.getParameter("msgIds");	
			if(str!=null && str.length()>0)
			{
				String[] ids=str.split(",");
				List idList=new ArrayList();
				for(int i=0;i<ids.length;i++)
				{
					idList.add(ids[i]);
					this.getMessageService().markReadedMsgInfosByIds(idList);
					map.put("success","true");
					map.put("info",str);
				}
			}
			else
			{
				map.put("failure","true");
				map.put("errorInfo", "delete error");
			}
		}
		
		JSONObject jsonObject = JSONObject.fromObject(map);
		jsonString = jsonObject.toString();
		return "listjson";
	}
	
}
