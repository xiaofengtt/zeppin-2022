package cn.zeppin.action.admin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.Category;
import cn.zeppin.entity.National;
import cn.zeppin.entity.Resource;
import cn.zeppin.entity.User;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.ICategoryService;
import cn.zeppin.service.api.INationalService;
import cn.zeppin.service.api.IResourceCustomTagService;
import cn.zeppin.service.api.IResourceService;
import cn.zeppin.service.api.IResourceTagService;
import cn.zeppin.utility.DataTimeConvert;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

public class ResourceAction extends BaseAction {

	private static final long serialVersionUID = 369393031897616109L;
	private IResourceService resourceService;
	private IResourceTagService resourceTagService;
	private IResourceCustomTagService resourceCustomTagService;
	private INationalService nationalService;
	private ICategoryService categoryService;

	public IResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(IResourceService resourceService) {
		this.resourceService = resourceService;
	}
	
	public IResourceTagService getResourceTagService() {
		return resourceTagService;
	}

	public void setResourceTagService(IResourceTagService resourceTagService) {
		this.resourceTagService = resourceTagService;
	}
	
	public IResourceCustomTagService getResourceCustomTagService() {
		return resourceCustomTagService;
	}

	public void setResourceCustomTagService(IResourceCustomTagService resourceCustomTagService) {
		this.resourceCustomTagService = resourceCustomTagService;
	}
	
	public INationalService getNationalService() {
		return nationalService;
	}

	public void setNationalService(INationalService nationalService) {
		this.nationalService = nationalService;
	}
	
	public ICategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(ICategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@AuthorityParas(userGroupName = "ALL")
	@ActionParam(key = "title", type = ValueType.STRING )
	@ActionParam(key = "category", type = ValueType.NUMBER)
	@ActionParam(key = "national", type = ValueType.NUMBER)
	@ActionParam(key = "tag", type = ValueType.STRING)
	@ActionParam(key = "parent", type = ValueType.NUMBER)
	@ActionParam(key = "level", type = ValueType.NUMBER)
	public void List() {
		ActionResult result = new ActionResult();
		HashMap<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("title", request.getParameter("title"));
		searchMap.put("category", request.getParameter("category"));
		searchMap.put("national", request.getParameter("national"));
		searchMap.put("tag", request.getParameter("tag"));
		searchMap.put("parent", request.getParameter("parent"));
		searchMap.put("level", request.getParameter("level"));
		
		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;

		int recordCount = this.resourceService.getCountByParams(searchMap);
		int pageCount = (int) Math.ceil((double) recordCount / pagesize);
		List<Resource> resourceList = this.resourceService.getListForPage(searchMap, sorts, offset, pagesize);

		List<Map<String, Object>> dataList = new ArrayList<>();
		if (resourceList != null && resourceList.size() > 0) {
			for (Resource resource : resourceList) {
				Map<String, Object> data = SerializeEntity.resource2Map(resource);
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
	
	@AuthorityParas(userGroupName = "ALL")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Load() {
		ActionResult result = new ActionResult();
		Integer id = Integer.valueOf(request.getParameter("id"));

		Resource resource = this.resourceService.getResource(id);
		if (resource != null) {
			Map<String, Object> data = SerializeEntity.resource2Map(resource);
			result.init(SUCCESS_STATUS, "加载编辑信息成功！", data);
		} else {
			result.init(FAIL_STATUS, "无效的编辑ID！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}
	
	@SuppressWarnings("unchecked")
	@AuthorityParas(userGroupName = "ALL")
	@ActionParam(key = "type", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Add() {
		User currentUser = (User) session.getAttribute("usersession");
		ActionResult result = new ActionResult();
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(Dictionary.UPLOAD_FILE_MAX_SIZE);
			String serverPath = ServletActionContext.getServletContext().getRealPath("/").replace("\\", "/");
			File file = new File(serverPath + "/temp");
			factory.setRepository(file);
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(Dictionary.UPLOAD_FILE_MAX_SIZE);
			List<FileItem> items = new ArrayList<>();
			try {
				items = upload.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			if (items != null) {
				Iterator<FileItem> iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = iter.next();

					if (!item.isFormField()) {
						Resource resource = new Resource();
						
						// 创建目录
						String[] dir = UUID.randomUUID().toString().split("-");
						String basePath = "upload/";

						for (String sPath : dir) {
							basePath += sPath + "/";

							File tfFile = new File(serverPath + "/" + basePath);
							if (!tfFile.exists()) {
								tfFile.mkdir();
							}
						}
						// 存储文件
						
						String name = dir[dir.length-1];
						resource.setTitle(name);
						resource.setType(item.getName().substring(item.getName().lastIndexOf(".")));
						resource.setSize(item.getSize());
						resource.setUrl(basePath + name + resource.getType());
						resource.setStatus(0);
						resource.setEminent(0);
						resource.setLevel(1);
						resource.setSource(1);
						resource.setIsObject(0);
						resource.setCreatetime(DataTimeConvert.getCurrentTime(""));
						resource.setOwner(currentUser);
						File file2 = new File(serverPath + "/" + resource.getUrl());
						try {

							item.write(file2);
							BufferedImage src = ImageIO.read(file2);
							resource.setRatio(src.getWidth(null) + "x" + src.getHeight(null));
							resource = resourceService.addResource(resource);
//							addChild(resource);
							Map<String, Object> data = SerializeEntity.resource2Map(resource);
							result.init(SUCCESS, "资源上传成功", data);

						} catch (Exception e) {
							e.printStackTrace();
							result.init(FAIL_STATUS, "资源上传失败", null);
						}

					}
				}
			}
		}
		Utlity.ResponseWrite(result, dataType, response);

	}
	
	public void addChild(Resource resource){
		for(int i = 0; i < 5; i++){
			Resource res = new Resource();
			res.setParent(resource);
			res.setCategory(resource.getCategory());
			res.setNational(resource.getNational());
			res.setTitle("样例数据"+i);
			res.setType(resource.getType());
			res.setSize(resource.getSize());
			String url = "img/child"+i+".jpg";
			res.setUrl(url);
			res.setStatus(resource.getStatus());
			res.setEminent(0);
			res.setLevel(resource.getLevel()+1);
			res.setCreatetime(DataTimeConvert.getCurrentTime(""));
			res.setOwner(resource.getOwner());
			res.setSource(1);
			res.setIsObject(0);
			res.setRatio("120x200");
			this.resourceService.addResource(res);
		}
		
	}
	
	@AuthorityParas(userGroupName = "ALL")
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "title", type = ValueType.STRING, nullable = false)
	@ActionParam(key = "national", type = ValueType.NUMBER)
	@ActionParam(key = "category", type = ValueType.NUMBER)
	@ActionParam(key = "comment", type = ValueType.STRING)
	@ActionParam(key = "meaning", type = ValueType.STRING)
	@ActionParam(key = "source", type = ValueType.NUMBER)
	@ActionParam(key = "eminent", type = ValueType.NUMBER)
	@ActionParam(key = "isObject", type = ValueType.NUMBER)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Edit() {
		ActionResult result = new ActionResult();
		User user = (User) session.getAttribute("usersession");
		if(request.getParameter("id")!=null){
			Integer id = Integer.valueOf(request.getParameter("id"));
			String title = request.getParameter("title").trim();
			String[] tags = request.getParameterValues("tag");
			String[] customTags = request.getParameterValues("customTag");
			Integer status = Integer.valueOf(request.getParameter("status"));
			if(!title.equals("")){
				Resource resource = resourceService.getResource(id);
				if (resource != null) {
					if(request.getParameter("parent")!=null && !request.getParameter("parent").equals("")){
						Integer parentId = Integer.valueOf(request.getParameter("parent"));
						Resource parent = this.resourceService.getResource(parentId);
						if(parent!=null){
							resource.setParent(parent);
							resource.setNational(parent.getNational());
							resource.setCategory(parent.getCategory());
							resource.setLevel(parent.getLevel() +1);
							resource.setMeaning(parent.getMeaning());
							resource.setComment(parent.getComment());
							resource.setSource(parent.getSource());
							resource.setSourcePath(parent.getSourcePath());
							resource.setSourceTime(parent.getSourceTime());
							resource.setIsObject(parent.getIsObject());
							resource.setEminent(0);
							resource.setScode(parent.getScode() + resource.getScode());
						}
					}
					if(request.getParameter("national")!=null && !request.getParameter("national").equals("")){
						Integer nationalId = Integer.valueOf(request.getParameter("national"));
						National national = this.nationalService.getNational(nationalId);
						if(national!=null){
							resource.setNational(national);
						}
					}
					if(request.getParameter("category")!=null && !request.getParameter("category").equals("")){
						Integer categoryId = Integer.valueOf(request.getParameter("category"));
						Category category = this.categoryService.getCategory(categoryId);
						if(category!=null){
							resource.setCategory(category);
						}
					}
					if(request.getParameter("eminent")!=null && !request.getParameter("eminent").equals("")){
						Integer eminent = Integer.valueOf(request.getParameter("eminent"));
						resource.setEminent(eminent);
					}
					resource.setTitle(title);
					if(request.getParameter("comment")!=null){
						String comment = request.getParameter("comment");
						resource.setComment(comment);
					}
					if(request.getParameter("meaning")!=null){
						String meaning = request.getParameter("meaning");
						resource.setMeaning(meaning);
					}
					if(request.getParameter("source")!=null && !request.getParameter("source").equals("")){
						Integer source = Integer.valueOf(request.getParameter("source"));
						String sourcePath = request.getParameter("sourcePath");
						String sourceTime = request.getParameter("sourceTime");
						resource.setSource(source);
						if(source > 1){
							resource.setSourcePath(sourcePath);
						}else{
							resource.setSourcePath(null);
						}
						if(source == 3){
							resource.setSourceTime(sourceTime);
						}else{
							resource.setSourceTime(null);
						}
					}
					if(request.getParameter("isObject")!=null && !request.getParameter("isObject").equals("")){
						Integer isObject = Integer.valueOf(request.getParameter("isObject"));
						resource.setIsObject(isObject);
					}
					resource.setStatus(status);
					resourceService.updateResource(resource);
					if(user.getRole().getId() == Dictionary.USER_ROLE_ADMIN || user.getRole().getId() == Dictionary.USER_ROLE_SPECIALIST){
						this.getResourceTagService().updateResourceTag(user, resource, tags);
					}
					if(user.getRole().getId() == Dictionary.USER_ROLE_ADMIN || user.getRole().getId() == Dictionary.USER_ROLE_SPECIALIST){
						this.getResourceCustomTagService().updateResourceCustomTag(user, resource, customTags);
					}
					result.init(SUCCESS, "变更状态成功", null);
				} else {
					result.init(FAIL_STATUS, "素材不存在", null);
				}
			}else{
				result.init(FAIL_STATUS, "标题不能为空", null);
			}
		}else{
			result.init(FAIL_STATUS, "未上传素材", null);
		}
		Utlity.ResponseWrite(result, dataType, response);
	}
	
	@AuthorityParas(userGroupName = "EDITOR")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void ChangeStatus() {
		ActionResult result = new ActionResult();
		Integer id = Integer.valueOf(request.getParameter("id"));
		Integer status = Integer.valueOf(request.getParameter("status"));
		Resource resource = resourceService.getResource(id);
		if (resource != null) {
			resource.setStatus(status);
			resourceService.updateResource(resource);
			Map<String, Object> data = SerializeEntity.resource2Map(resource);
			result.init(SUCCESS, "变更状态成功", data);
		} else {
			result.init(FAIL_STATUS, "素材不存在", null);
		}
		Utlity.ResponseWrite(result, dataType, response);

	}
	
	/**
	 * 导航条
	 */
	@ActionParam(key = "parent", type = ValueType.NUMBER)
	public void LoadResourceNav() {
		ActionResult result = new ActionResult();
		
		Integer id = 0;
		if(request.getParameter("parent")!=null && !request.getParameter("parent").equals("")){
			id = Integer.valueOf(request.getParameter("parent"));
		}
		Resource resource = this.getResourceService().getResource(id);

		if (resource == null) {
			result.init(FAIL_STATUS, null, null);
		} else {
			LinkedList<Resource> resourcelist = new LinkedList<Resource>();
			resourcelist.add(resource);

			while (resource.getParent() != null) {
				resource = resource.getParent();
				resourcelist.add(resource);
			}
			List<Map<String, Object>> dataList = new ArrayList<>();
			int i=resourcelist.size()-1;
			for(;i>=0;i--){
				Resource res = resourcelist.get(i);
				Map<String, Object> data = SerializeEntity.resource2Map(res);
				dataList.add(data);
			}
			result.init(SUCCESS_STATUS, null, dataList);
		}
		Utlity.ResponseWrite(result, dataType, response);
	}
}
