/**
 * 
 */
package cn.zeppin.action.admin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.ItemType;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IItemService;
import cn.zeppin.service.api.IItemTypeService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

/**
 * @author Clark
 *
 */
public class ItemTypeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2074220274672443041L;
	
	private IItemTypeService itemTypeService;
	private IItemService itemService;

	public IItemTypeService getItemTypeService() {
		return itemTypeService;
	}

	public void setItemTypeService(IItemTypeService itemTypeService) {
		this.itemTypeService = itemTypeService;
	}
	
	public IItemService getItemService() {
		return itemService;
	}

	public void setItemService(IItemService itemService) {
		this.itemService = itemService;
	}

	/**
	 * 添加题型
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "isStandard", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "modelType", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Add(){
		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		ActionResult result = new ActionResult();
		String name = request.getParameter("name");
		Boolean isStandard = this.getBoolValue(request.getParameter("isStandard"));
		Short modelType = Short.parseShort(request.getParameter("modelType"));
//		Boolean isGroup=modelType==4?true:false;

		Short status = Short.parseShort(request.getParameter("status"));
		//不存在重名的题型
		ItemType itemTypes = this.getItemTypeService().getItemTypeByName(name);
		if (itemTypes == null){
			ItemType itemType = new ItemType();
			itemType.setName(name);
			itemType.setIsStandard(isStandard);
//			itemType.setIsGroup(isGroup);
			itemType.setSysUser(currentUser);
			itemType.setModelType(modelType);
			itemType.setStatus(status);
			itemType.setCreatetime(new Timestamp((new Date()).getTime()));
			this.getItemTypeService().addItemType(itemType);
			Map<String, Object> data = SerializeEntity.itemType2Map(itemType);
			result.init(SUCCESS_STATUS, "添加题型：" + name + "成功！", data);
		}
		else{
			result.init(FAIL_STATUS, "已经存在同名题型！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);	
	}
	
	/**
	 * 修改题型
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "isStandard", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "modelType", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Update() {
		ActionResult result = new ActionResult();
		Integer typeID = this.getIntValue(request.getParameter("id"));
		String name = request.getParameter("name");
		Boolean isStandard = this.getBoolValue(request.getParameter("isStandard"));
		Short modelType = Short.parseShort(request.getParameter("modelType"));
		Short status = Short.parseShort(request.getParameter("status"));
//		Boolean isGroup=modelType==4?true:false;
		//不会把名字改为重名
		ItemType itemType = this.getItemTypeService().getItemTypeById(typeID);
		ItemType duplicateItemType = this.getItemTypeService().getItemTypeByName(name);
		if (itemType == null){
			result.init(FAIL_STATUS, "无效的题型ID！", null);
		}
		
		else if (duplicateItemType != null && ! duplicateItemType.getId().equals(typeID) && 
				duplicateItemType.getName().equals(name)){
			result.init(FAIL_STATUS, "已经存在同名题型！", null);
		}
		
		else {
			itemType.setName(name);
//			itemType.setIsGroup(isGroup);
			itemType.setIsStandard(isStandard);
			itemType.setModelType(modelType);
			itemType.setStatus(status);
			this.getItemTypeService().updateItemType(itemType);
			Map<String, Object> data = SerializeEntity.itemType2Map(itemType);
			result.init(SUCCESS_STATUS, "修改题型成功！", data);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);	
		
	}
	
	/**
	 * 删除题型
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Delete() {
		ActionResult result = new ActionResult();
		Integer typeID = this.getIntValue(request.getParameter("id"));
		ItemType itemType = this.getItemTypeService().getItemTypeById(typeID);		
		if (itemType == null){
			result.init(FAIL_STATUS, "无效的题型ID！", null);
		}
		else {			
			this.getItemTypeService().deleteItemType(itemType);
			Map<String, Object> data = SerializeEntity.itemType2Map(itemType);
			result.init(SUCCESS_STATUS, "删除题型成功！", data);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);	
	}
	
	/**
	 * 题型列表
	 */
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "name", type = ValueType.STRING)
//	@ActionParam(key = "isGroup", type = ValueType.NUMBER)
	@ActionParam(key = "isStandard", type = ValueType.NUMBER)
	@ActionParam(key = "sysUser.id", type = ValueType.NUMBER)
	@ActionParam(key = "status", type = ValueType.NUMBER)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	public void Search() {
		ActionResult result = new ActionResult();
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("id", request.getParameter("id"));
		searchMap.put("name", request.getParameter("name"));
//		searchMap.put("isGroup", getBoolValue(request.getParameter("isGroup"), null));
		searchMap.put("isStandard", getBoolValue(request.getParameter("isStandard"), null));
		searchMap.put("sysUser.id", request.getParameter("sysUser.id"));
		searchMap.put("status", request.getParameter("status"));
		
		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;
		
		int recordCount = this.getItemTypeService().searchItemTypeCount(searchMap);
		int pageCount = (int) Math.ceil((double) recordCount/pagesize);
		
		List<ItemType> itemTypes = this.getItemTypeService().searchItemType(searchMap, sorts, offset, pagesize);
		
		List<Map<String, Object>> dataList = new ArrayList<>();
		if (itemTypes != null && itemTypes.size() > 0){
			for (ItemType itemType : itemTypes){
				Map<String, Object> data = SerializeEntity.itemType2Map(itemType);
				dataList.add(data);
			}
		}
		
		result.init(SUCCESS_STATUS, "搜索完成！", dataList);
		result.setPageCount(pageCount);
		result.setPageNum(pagenum);
		result.setPageSize(pagesize);
		result.setTotalCount(recordCount);
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);	
	}
}
