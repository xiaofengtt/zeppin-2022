/** 
 * Project Name:ItemDatabase 
 * File Name:SubjectAction.java 
 * Package Name:cn.zeppin.action.admin 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.action.admin;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.entity.Item;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.TextbookCapter;
import cn.zeppin.service.api.IOrganizationService;
import cn.zeppin.service.api.IItemService;
import cn.zeppin.service.api.ITextbookCapterService;
import cn.zeppin.utility.JSONUtils;
import cn.zeppin.utility.Utlity;

/**
 * ClassName: OrganizationAction <br/>
 * date: 2014年7月20日 下午5:23:35 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class OrganizationItemReceiveAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1381225742757194727L;

	private IOrganizationService organizationService;
	private IItemService  itemService;
	private ITextbookCapterService  textbookCapterService;
	
	public IOrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(IOrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	public IItemService getItemService() {
		return itemService;
	}

	public void setItemService(IItemService itemService) {
		this.itemService = itemService;
	}
	
	public ITextbookCapterService getTextbookCapterService() {
		return textbookCapterService;
	}

	public void setTextbookCapterService(ITextbookCapterService textbookCapterService) {
		this.textbookCapterService = textbookCapterService;
	}
	
	@SuppressWarnings("unchecked")
	@ActionParam(key = "token", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "publisher", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "subject", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "schoolYear", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "term", type = ValueType.STRING, nullable = false, emptyable = false)
	public void UpdateCapter(){
		ActionResult result = new ActionResult();
		String token = request.getParameter("token");
		Organization org =this.organizationService.getByToken(token);
		if(org != null){
			Map<String,Object> searchMap = new HashMap<String,Object>();
			searchMap.put("publisherFcode", request.getParameter("publisher"));
			searchMap.put("subjectFcode", request.getParameter("subject"));
			String gradeFcode = request.getParameter("schoolYear") + "_" + request.getParameter("term");
			searchMap.put("gradeFcode", gradeFcode);
			
			List<TextbookCapter> capterList = this.textbookCapterService.searchTextbookCapter(searchMap);
			List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
			for(TextbookCapter tc : capterList){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("id", tc.getId());
				dataMap.put("level", tc.getLevel());
				dataMap.put("inx", tc.getNumber());
				dataMap.put("name", tc.getName());
				dataMap.put("code", tc.getFcode());
				dataMap.put("children", new ArrayList<Map<String,Object>>());
				if(tc.getLevel() == 1){
					dataList.add(dataMap);
				}else if (tc.getLevel() == 2){
					TextbookCapter parent = tc.getTextbookCapter();
					for(Map<String,Object> map : dataList){
						if(map.get("id").toString().equals(parent.getId().toString())){
							((List<Map<String,Object>>)(map.get("children"))).add(dataMap);
						}
					}
				}else{
					TextbookCapter parent = tc.getTextbookCapter();
					TextbookCapter grandParent = parent.getTextbookCapter();
					for(Map<String,Object> map : dataList){
						if(map.get("id").toString().equals(grandParent.getId().toString())){
							List<Map<String,Object>> childrenList = (List<Map<String,Object>>) map.get("children");
							for(Map<String,Object> childMap : childrenList){
								if(childMap.get("id").toString().equals(parent.getId().toString())){
									((List<Map<String,Object>>)(childMap.get("children"))).add(dataMap);
								}
							}
						}
					}
				}
			}
			result.init(SUCCESS_STATUS, "搜索完成！", dataList);
		}else{
			result.init(FAIL_STATUS, "您必须经过授权！", null);
		}
		Utlity.ResponseWrite(result, dataType, response);
	}
	
	@ActionParam(key = "token", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "startTime", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "startPage", type = ValueType.NUMBER)
	@ActionParam(key = "publisher", type = ValueType.STRING)
	@ActionParam(key = "subject", type = ValueType.STRING)
	@ActionParam(key = "schoolYear", type = ValueType.STRING)
	@ActionParam(key = "term", type = ValueType.STRING)
	public void UpdateItems() throws IOException{
		ActionResult result = new ActionResult();
		int pagesize = 1000;
		String token = request.getParameter("token");
		String time = request.getParameter("startTime");
		int startPage = this.getIntValue(request.getParameter("startPage"), 1);
		int offset = (startPage - 1) * pagesize;
		
		try{
			Timestamp startTime = Timestamp.valueOf(time);
			Organization org =this.organizationService.getByToken(token);
			if(org != null){
				Map<String,Object> searchMap = new HashMap<String,Object>();
				searchMap.put("startTime", startTime);
				searchMap.put("statusRange", "(0,1,3)");
				searchMap.put("isgroup", 0);
				searchMap.put("isdelete", 1);
				
				if(request.getParameter("publisher")!=null && !request.getParameter("publisher").equals("")){
					searchMap.put("publisherFcode", request.getParameter("publisher"));
				}
				
				if(request.getParameter("subject")!=null && !request.getParameter("subject").equals("")){
					searchMap.put("subjectFcode", request.getParameter("subject"));
				}
				
				if(request.getParameter("schoolYear")!=null && !request.getParameter("schoolYear").equals("") && request.getParameter("term")!=null && !request.getParameter("term").equals("")){
					String gradeFcode = request.getParameter("schoolYear") + "_" + request.getParameter("term");
					searchMap.put("gradeFcode", gradeFcode);
				}else if(request.getParameter("schoolYear")!=null && !request.getParameter("schoolYear").equals("")){
					String gradeFcode = request.getParameter("schoolYear") + "_";
					searchMap.put("gradeFcode", gradeFcode);
				}
				
				Integer itemCount = this.itemService.searchItemCount(searchMap);
				List<Item> itemList = this.itemService.searchItem(searchMap, null, offset , pagesize);
				Map<String,Object> dataMap = new HashMap<String , Object>();
				List<Map<String, Object>> dataList = new ArrayList<>();
				if (itemList != null && itemList.size() > 0) {
					for (Item item : itemList) {
						if((short)item.getStatus() == (short)0 && item.getCreatetime().after(startTime)){}
						else{
							Map<String, Object> data = item2Map(item);
							if ((short)item.getStatus() == (short)0) {
								data.put("type", "delete");
							}else if (item.getCreatetime().before(startTime)){
								data.put("type", "update");
							}else{
								data.put("type", "insert");
							}
							dataList.add(data);
						}
					}
				}
				dataMap.put("itemList", dataList);
				dataMap.put("itemCount", itemCount);
				dataMap.put("startPage", startPage);
				dataMap.put("pageNumber", itemCount / pagesize + 1);
				result.init(SUCCESS_STATUS, "搜索完成！", dataMap);
			}else{
				result.init(FAIL_STATUS, "您必须经过授权！", null);
			}
		}catch(Exception e){
			result.init(FAIL_STATUS, "传入参数检验有误", null);
		}
		Utlity.ResponseWrite(result, dataType, response);
	}
	
	Map<String, Object> item2Map(Item item) throws IOException {

		Map<String, Object> result = new LinkedHashMap<String, Object>();

		result.put("id", item.getId());
		result.put("itemTypeId", item.getItemType().getId());
		result.put("itemTypeName", item.getItemType().getName());
		result.put("diffcultyLevel", item.getDiffcultyLevel());
		result.put("diffcultyLevelCN", Utlity.getDiffcultyLevelType(item.getDiffcultyLevel()));
		result.put("defaultScore", item.getDefaultScore());
		result.put("source", item.getSource());
		
		if (item.getAnalysis() != null) {
			result.put("analysis", item.getAnalysis());
		} else {
			result.put("analysis", "");
		}
		
		if (item.getSubject() != null) {
			result.put("subject", item.getSubject().getFcode());
		} else {
			result.put("subject", "");
		}
		
		if (item.getGrade() != null) {
			String[] gradeFcode =item.getGrade().getFcode().split("_");
			if(gradeFcode.length >= 2){
				result.put("school_year", gradeFcode[0]);
				result.put("term",  gradeFcode[1]);
			}else if (gradeFcode.length >= 1){
				result.put("school_year", gradeFcode[0]);
				result.put("term",  "0");
			}else{
				result.put("school_year", "00");
				result.put("term", "0");
			}
		} else {
			result.put("school_year", "00");
			result.put("term", "0");
		}

		if (item.getTextbookCapter() != null) {
			result.put("textbookCapter", item.getTextbookCapter().getFcode());
			result.put("textbookCapterName", getTextbookCapterNaviName(item.getTextbookCapter()));
			result.put("publisher", item.getTextbookCapter().getTextbook().getPublisher().getFcode());
		} else {
			result.put("textbookCapter", "00_00_00");
			result.put("textbookCapterName", "");
			result.put("publisher", "00");
		}
		if (!item.getIsGroup()) {
			result.put("material", "");
			result.put("data", JSONUtils.json2map(item.getContentBackup()));
		} else {
			// 组合题
			result.put("material", item.getContentBackup());
			
		}
		result.put("createtime", Utlity.timeSpanToString(item.getCreatetime()));
		result.put("updatetime", Utlity.timeSpanToString(item.getUpdatetime()));
		
		return result;
	}
	
	String getTextbookCapterNaviName(TextbookCapter capter) {
		if (capter.getTextbookCapter() == null) {
			return capter.getTextbook().getName() + "_" + capter.getName();
		} else {
			return getTextbookCapterNaviName(capter.getTextbookCapter()) + "_" + capter.getName();
		}
	}
}
