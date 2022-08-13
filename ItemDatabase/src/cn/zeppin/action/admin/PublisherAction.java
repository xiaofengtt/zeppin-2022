/**  
 * This class is used for 出版社操作
 * @author suijing
 * @version  
 *       1.0, 2014年7月29日 上午11:49:57  
 */
package cn.zeppin.action.admin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.Publisher;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IPublisherService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

/**
 * @author sj
 *
 */
public class PublisherAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -559509175482230849L;
	private IPublisherService publisherService;

	public IPublisherService getPublisherService() {
		return publisherService;
	}

	public void setPublisherService(IPublisherService publisherService) {
		this.publisherService = publisherService;
	}


	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT", errMsg = "无权添加出版社")
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "fcode", type = ValueType.STRING, nullable = false, emptyable = false)
	public void Add() {
		ActionResult result = new ActionResult();
		String name = request.getParameter("name");
		String fcode = request.getParameter("fcode");
		Short status = Short.valueOf(request.getParameter("status"));
		
		if (this.publisherService.isExist(name, fcode , null)) {
			result.init(FAIL_STATUS, "出版社已经存在，不能重复添加", null);
		} else {
			SysUser currentUser = (SysUser) session.getAttribute("usersession");
			
			Publisher publisher = new Publisher();
			publisher.setName(name);
			publisher.setFcode(fcode);
			publisher.setStatus(status);
			publisher.setSysUser(currentUser);
			publisher.setCreatetime(new Timestamp((new Date()).getTime()));
			this.publisherService.add(publisher);
			
			HashMap<String, Object> data = (HashMap<String, Object>) SerializeEntity.publisher2Map(publisher);
			result.init(SUCCESS, "添加教材成功", data);
		}

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT", errMsg = "无权编辑出版社")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "fcode", type = ValueType.STRING, nullable = false, emptyable = false)
	public void Update() {
		ActionResult result = new ActionResult();
		Integer id = Integer.valueOf(request.getParameter("id"));
		Publisher publisher = this.publisherService.getById(id);
		if (publisher == null) {
			result.init(FAIL_STATUS, "出版社不存在", null);
		} else {
			String name = request.getParameter("name");
			String fcode = request.getParameter("fcode");
			Short status = Short.valueOf(request.getParameter("status"));
			if (this.publisherService.isExist(name , fcode, id)) {
				result.init(FAIL_STATUS, "出版社已经存在，不能重复添加", null);
			} else {
				publisher.setFcode(fcode);
				publisher.setName(name);
				publisher.setStatus(status);
				this.publisherService.update(publisher);
				
				HashMap<String, Object> data = (HashMap<String, Object>) SerializeEntity.publisher2Map(publisher);
				result.init(SUCCESS, "编辑教材成功", data);
			}
		}
		Utlity.ResponseWrite(result, dataType, response);
	}

	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT", errMsg = "无权删除出版社")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Delete() {
		ActionResult result = new ActionResult();
		Integer id = Integer.parseInt(request.getParameter("id"));
		Publisher publisher = publisherService.getById(id);
		if (publisher == null) {
			result.init(FAIL_STATUS, "教材不存在", null);
		} else {
			this.publisherService.delete(publisher);
			result.init(SUCCESS, "删除教材成功", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);

	}
	
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "fcode", type = ValueType.STRING)
	public void Search() {
		ActionResult result = new ActionResult();
		
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("id", request.getParameter("id"));
		searchMap.put("name", request.getParameter("name"));
		searchMap.put("fcode", request.getParameter("fcode"));
		
		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;
		
		int recordCount = this.getPublisherService().getPublisherCount(searchMap);
		int pageCount = (int) Math.ceil((double) recordCount/pagesize);
		
		List<Publisher> publishers = this.getPublisherService().getPublisherList(searchMap, sorts, offset, pagesize);
		
		List<Map<String, Object>> dataList = new ArrayList<>();
		if (publishers != null && publishers.size() > 0){
			for (Publisher publisher : publishers){
				Map<String, Object> data = SerializeEntity.publisher2Map(publisher);
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
	
	
	@ActionParam(key = "name", type = ValueType.STRING, nullable = true, emptyable = true)
	@ActionParam(key = "fcode", type = ValueType.STRING, nullable = true, emptyable = true)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = true, emptyable = true)
	public void SearchAllPublisher() {
		String dataType = request.getParameter("datatype");
		ActionResult result = new ActionResult();

		String status = request.getParameter("status");
		String name = request.getParameter("name");
		String fcode = request.getParameter("fcode");
		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("name", name);
		searchMap.put("fcode", fcode);
		searchMap.put("status", status);

		int recordCount = this.getPublisherService().getPublisherCount(searchMap);
		List<Publisher> publishers = this.getPublisherService().getPublisherList(searchMap, sorts, -1, -1);

		List<Map<String, Object>> dataList = new ArrayList<>();
		for (Publisher p : publishers) {
			dataList.add(SerializeEntity.publisher2Map(p));
		}

		result.init(SUCCESS_STATUS, null, dataList);
		result.setTotalCount(recordCount);

		Utlity.ResponseWrite(result, dataType, response);
	}
}
