package cn.zeppin.action.web;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import cn.zeppin.entity.Category;
import cn.zeppin.entity.Keyword;
import cn.zeppin.entity.National;
import cn.zeppin.entity.Resource;
import cn.zeppin.entity.User;
import cn.zeppin.entity.UserDownloadResource;
import cn.zeppin.entity.UserLoveResource;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.ICategoryService;
import cn.zeppin.service.api.IKeywordService;
import cn.zeppin.service.api.INationalService;
import cn.zeppin.service.api.IResourceService;
import cn.zeppin.service.api.IUserDownloadResourceService;
import cn.zeppin.service.api.IUserLoveResourceService;
import cn.zeppin.utility.DataTimeConvert;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

public class WebAction extends BaseAction {

	private static final long serialVersionUID = 369393031897616109L;
	private IResourceService resourceService;
	private INationalService nationalService;
	private ICategoryService categoryService;
	private IKeywordService keywordService;
	private IUserLoveResourceService userLoveResourceService;
	private IUserDownloadResourceService userDownloadResourceService;

	public IResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(IResourceService resourceService) {
		this.resourceService = resourceService;
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

	public IKeywordService getKeywordService() {
		return keywordService;
	}

	public void setKeywordService(IKeywordService keywordService) {
		this.keywordService = keywordService;
	}

	public IUserLoveResourceService getUserLoveResourceService() {
		return userLoveResourceService;
	}

	public void setUserLoveResourceService(
			IUserLoveResourceService userLoveResourceService) {
		this.userLoveResourceService = userLoveResourceService;
	}

	public IUserDownloadResourceService getUserDownloadResourceService() {
		return userDownloadResourceService;
	}

	public void setUserDownloadResourceService(
			IUserDownloadResourceService userDownloadResourceService) {
		this.userDownloadResourceService = userDownloadResourceService;
	}

	@ActionParam(key = "search", type = ValueType.STRING)
	@ActionParam(key = "category", type = ValueType.NUMBER)
	@ActionParam(key = "national", type = ValueType.NUMBER)
	@ActionParam(key = "parent", type = ValueType.NUMBER)
	@ActionParam(key = "level", type = ValueType.NUMBER)
	@ActionParam(key = "status", type = ValueType.NUMBER)
	@ActionParam(key = "type", type = ValueType.STRING)
	@ActionParam(key = "user", type = ValueType.NUMBER)
	@ActionParam(key = "getCount", type = ValueType.NUMBER)
	@ActionParam(key = "except", type = ValueType.NUMBER)
	public void ResourceList() {
		ActionResult result = new ActionResult();

		User user = (User) session.getAttribute("usersession");
		HashMap<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("search", request.getParameter("search"));
		if(request.getParameter("category") !=null && !request.getParameter("category").equals("") 
				&& !request.getParameter("category").equals("0")){
			Category category = this.categoryService.getCategory(Integer.valueOf(request.getParameter("category")));
			if(category != null){
				searchMap.put("categoryScode", category.getScode());
			}
		}
		searchMap.put("national", request.getParameter("national"));
		searchMap.put("parent", request.getParameter("parent"));
		searchMap.put("resourceScode", request.getParameter("resourceScode"));
		searchMap.put("level", request.getParameter("level"));
		searchMap.put("status", request.getParameter("status"));
		searchMap.put("type", request.getParameter("type"));
		searchMap.put("user", request.getParameter("user"));
		searchMap.put("except", request.getParameter("except"));
		
		String sorts = "eminent desc , id desc";
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"),
				Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;

		int recordCount = this.resourceService.getCountByParams(searchMap);
		int pageCount = (int) Math.ceil((double) recordCount / pagesize);

		List<Resource> resourceList = this.resourceService.getListForPage(
				searchMap, sorts, offset, pagesize);

		List<Map<String, Object>> dataList = new ArrayList<>();
		if (resourceList != null && resourceList.size() > 0) {
			for (Resource resource : resourceList) {
				Map<String, Object> data = SerializeEntity
						.resource2Map(resource);
				if (request.getParameter("getCount") != null
						&& request.getParameter("getCount").equals("1")) {
					HashMap<String, String> countSearchMap = new HashMap<String, String>();
					countSearchMap.put("resource", resource.getId() + "");
					Integer userLoveCount = this.userLoveResourceService
							.getCountByParams(countSearchMap);
					Integer userDownloadCount = this.userDownloadResourceService
							.getCountByParams(countSearchMap);
					data.put("userLoveCount", userLoveCount);
					data.put("userDownloadCount", userDownloadCount);
				}
				dataList.add(data);
			}
		}

		if (request.getParameter("search") != null
				&& !request.getParameter("search").equals("")) {
			Keyword keyword = new Keyword();
			keyword.setWord(request.getParameter("search"));
			keyword.setUser(user);
			keyword.setCreatetime(DataTimeConvert.getCurrentTime(""));
			this.keywordService.addKeyword(keyword);
		}
		result.init(SUCCESS_STATUS, "搜索完成！", dataList);
		result.setPageCount(pageCount);
		result.setPageNum(pagenum);
		result.setPageSize(pagesize);
		result.setTotalCount(recordCount);
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void ResourceLoad() {
		ActionResult result = new ActionResult();
		Integer id = Integer.valueOf(request.getParameter("id"));
		User user = (User) session.getAttribute("usersession");
		Resource resource = this.resourceService.getResource(id);
		if (resource != null) {
			Map<String, Object> data = SerializeEntity.resource2Map(resource);
			HashMap<String, String> searchMap = new HashMap<String, String>();
			searchMap.put("resource", resource.getId() + "");
			Integer userLoveCount = this.userLoveResourceService
					.getCountByParams(searchMap);
			Integer userDownloadCount = this.userDownloadResourceService
					.getCountByParams(searchMap);
			data.put("userLoveCount", userLoveCount);
			data.put("userDownloadCount", userDownloadCount);
			if (user != null) {
				searchMap.put("user", user.getId() + "");
				Integer isLove = this.userLoveResourceService
						.getCountByParams(searchMap);
				data.put("isLove", isLove);
				data.put("isLogin", 1);
			} else {
				data.put("isLove", 0);
				data.put("isLogin", 0);
			}
			result.init(SUCCESS_STATUS, "加载编辑信息成功！", data);
		} else {
			result.init(FAIL_STATUS, "无效的编辑ID！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	@SuppressWarnings("unchecked")
	public void ResourceAdd() {
		ActionResult result = new ActionResult();

		User currentUser = (User) session.getAttribute("usersession");
		if (currentUser != null) {
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(Dictionary.UPLOAD_FILE_MAX_SIZE);
				String serverPath = ServletActionContext.getServletContext()
						.getRealPath("/").replace("\\", "/");
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
					FileItem imgFile = null;
					HashMap<String, String> dataMap = new HashMap<String, String>();
					while (iter.hasNext()) {
						FileItem item = iter.next();
						if (!item.isFormField() && item.getSize() > 0) {
							imgFile = item;
						} else {
							try {
								dataMap.put(item.getFieldName(),
										item.getString("UTF-8"));
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}
						}
					}
					if (imgFile == null) {
						result.init(FAIL_STATUS, "未上传图片", null);
						String dataType = request.getParameter("datatype");
						Utlity.ResponseWrite(result, dataType, response);
						return;
					}
					if (imgFile.getSize() > 20 * 1024 * 1024) {
						result.init(FAIL_STATUS, "图片不可大于20M", null);
						String dataType = request.getParameter("datatype");
						Utlity.ResponseWrite(result, dataType, response);
						return;
					}
					String imgType = imgFile.getName().substring(
							imgFile.getName().indexOf("."));
					if (!imgType.equals(".jpg") && !imgType.equals(".jpeg")
							&& !imgType.equals(".JPEG")
							&& !imgType.equals(".jpg")
							&& !imgType.equals(".png")
							&& !imgType.equals("bmp")
							&& !imgType.equals(".tiff")) {
						result.init(FAIL_STATUS, "文件格式不正确", null);
						String dataType = request.getParameter("datatype");
						Utlity.ResponseWrite(result, dataType, response);
						return;
					}
					Resource resource = new Resource();
					if (dataMap.get("title") != null
							&& !dataMap.get("title").equals("")) {
						resource.setTitle(dataMap.get("title"));
					} else {
						result.init(FAIL_STATUS, "图片标题未填", null);
						String dataType = request.getParameter("datatype");
						Utlity.ResponseWrite(result, dataType, response);
						return;
					}
					resource.setComment(dataMap.get("comment"));

					if (dataMap.get("source") != null
							&& !dataMap.get("source").equals("")) {
						Integer source = Integer.valueOf(dataMap.get("source"));
						resource.setSource(source);

						if (source > 1) {
							resource.setSourcePath(dataMap.get("sourcePath"));
						} else {
							resource.setSourcePath(null);
						}
						if (source == 3) {
							resource.setSourceTime(dataMap.get("sourceTime"));
						} else {
							resource.setSourceTime(null);
						}
					} else {
						result.init(FAIL_STATUS, "图片来源未选择", null);
						String dataType = request.getParameter("datatype");
						Utlity.ResponseWrite(result, dataType, response);
						return;
					}

					if (dataMap.get("isObject") != null
							&& !dataMap.get("isObject").equals("")) {
						Integer isObject = Integer.valueOf(dataMap
								.get("isObject"));
						resource.setIsObject(isObject);
					} else {
						result.init(FAIL_STATUS, "图片是否是实物未选择", null);
						String dataType = request.getParameter("datatype");
						Utlity.ResponseWrite(result, dataType, response);
						return;
					}

					if (dataMap.get("national") != null
							&& !dataMap.get("national").equals("")) {
						Integer nationalId = Integer.valueOf(dataMap
								.get("national"));
						National national = this.nationalService
								.getNational(nationalId);
						if (national != null) {
							resource.setNational(national);
						}
					} else {
						result.init(FAIL_STATUS, "图片民族未选择", null);
						String dataType = request.getParameter("datatype");
						Utlity.ResponseWrite(result, dataType, response);
						return;
					}

					if (dataMap.get("category") != null
							&& !dataMap.get("category").equals("")) {
						Integer categoryId = Integer.valueOf(dataMap.get("category"));
						Category category = this.categoryService.getCategory(categoryId);
						if (category != null) {
							resource.setCategory(category);
						}
					} else {
						result.init(FAIL_STATUS, "图片分类未选择", null);
						String dataType = request.getParameter("datatype");
						Utlity.ResponseWrite(result, dataType, response);
						return;
					}

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
					String name = dir[dir.length - 1];
					resource.setEminent(0);
					resource.setCreatetime(DataTimeConvert.getCurrentTime(""));
					resource.setOwner(currentUser);
					resource.setType(imgFile.getName().substring(
							imgFile.getName().indexOf(".")));
					resource.setSize(imgFile.getSize());
					resource.setUrl(basePath + name + resource.getType());
					resource.setLevel(1);
					resource.setStatus(1);
					File file2 = new File(serverPath + "/" + resource.getUrl());
					try {

						imgFile.write(file2);
						BufferedImage src = ImageIO.read(file2);
						resource.setRatio(src.getWidth(null) + "x"
								+ src.getHeight(null));
						resource = resourceService.addResource(resource);
						Map<String, Object> data = SerializeEntity
								.resource2Map(resource);
						result.init(SUCCESS, "资源上传成功", data);

					} catch (Exception e) {
						e.printStackTrace();
						result.init(FAIL_STATUS, "资源上传失败", null);
					}
				} else {
					result.init(FAIL_STATUS, "上传的图片资源异常", null);
				}
			} else {
				result.init(FAIL_STATUS, "未选择上传图片", null);
			}
		} else {
			result.init(FAIL_STATUS, "请重新登录", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	public void UploadCount() {
		ActionResult result = new ActionResult();
		User user = (User) session.getAttribute("usersession");
		if (user != null) {
			HashMap<String, String> searchMap = new HashMap<String, String>();
			searchMap.put("user", user.getId() + "");
			List<Resource> resourceList = this.resourceService
					.getListByParams(searchMap);
			Integer uncheck = 0, pass = 0, fail = 0;
			for (Resource resource : resourceList) {
				if (resource.getStatus() == 1) {
					uncheck++;
				} else if (resource.getStatus() == 2) {
					pass++;
				} else if (resource.getStatus() == 3) {
					fail++;
				}
			}
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("uncheck", uncheck);
			data.put("pass", pass);
			data.put("fail", fail);
			result.init(SUCCESS_STATUS, "搜索完成！", data);
		} else {
			result.init(FAIL_STATUS, "请重新登录", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void LoveAdd() {
		ActionResult result = new ActionResult();
		User user = (User) session.getAttribute("usersession");
		Integer id = Integer.valueOf(request.getParameter("id"));
		if (user != null) {
			Resource resource = this.resourceService.getResource(id);
			if (resource != null) {
				UserLoveResource userLoveResource = new UserLoveResource();
				userLoveResource.setResource(resource);
				userLoveResource.setUser(user);
				userLoveResource.setCreatetime(DataTimeConvert
						.getCurrentTime(""));
				this.userLoveResourceService
						.addUserLoveResource(userLoveResource);
				result.init(SUCCESS_STATUS, "添加成功！", null);
			} else {
				result.init(FAIL_STATUS, "素材不存在", null);
			}
		} else {
			result.init(FAIL_STATUS, "请重新登录", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void DownloadAdd() {
		ActionResult result = new ActionResult();
		User user = (User) session.getAttribute("usersession");
		Integer id = Integer.valueOf(request.getParameter("id"));
		if (user != null) {
			Resource resource = this.resourceService.getResource(id);
			if (resource != null) {
				UserDownloadResource userDownloadResource = new UserDownloadResource();
				userDownloadResource.setResource(resource);
				userDownloadResource.setUser(user);
				userDownloadResource.setCreatetime(DataTimeConvert
						.getCurrentTime(""));
				this.userDownloadResourceService
						.addUserDownloadResource(userDownloadResource);
				result.init(SUCCESS_STATUS, "添加成功！", null);
			} else {
				result.init(FAIL_STATUS, "素材不存在", null);
			}
		} else {
			result.init(FAIL_STATUS, "请重新登录", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	@ActionParam(key = "category", type = ValueType.NUMBER)
	public void LoveList() {
		ActionResult result = new ActionResult();
		User user = (User) session.getAttribute("usersession");
		if (user != null) {
			HashMap<String, String> searchMap = new HashMap<String, String>();
			if(request.getParameter("category") !=null && !request.getParameter("category").equals("") 
					&& !request.getParameter("category").equals("0")){
				Category category = this.categoryService.getCategory(Integer.valueOf(request.getParameter("category")));
				if(category != null){
					searchMap.put("categoryScode", category.getScode());
				}
			}
			searchMap.put("user", user.getId() + "");

			String sorts = "createtime desc";
			int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
			int pagesize = this.getIntValue(request.getParameter("pagesize"),
					Dictionary.PAGESIZE_DEFAULT);
			int offset = (pagenum - 1) * pagesize;

			int recordCount = this.resourceService
					.getUserLoveCountByParams(searchMap);
			int pageCount = (int) Math.ceil((double) recordCount / pagesize);

			List<Resource> resourceList = this.resourceService
					.getUserLoveListForPage(searchMap, sorts, offset, pagesize);
			List<Map<String, Object>> dataList = new ArrayList<>();
			if (resourceList != null && resourceList.size() > 0) {
				for (Resource resource : resourceList) {
					Map<String, Object> data = SerializeEntity
							.resource2Map(resource);
					HashMap<String, String> countSearchMap = new HashMap<String, String>();
					countSearchMap.put("resource", resource.getId() + "");
					Integer userLoveCount = this.userLoveResourceService
							.getCountByParams(countSearchMap);
					Integer userDownloadCount = this.userDownloadResourceService
							.getCountByParams(countSearchMap);
					data.put("userLoveCount", userLoveCount);
					data.put("userDownloadCount", userDownloadCount);
					dataList.add(data);
				}
			}
			result.init(SUCCESS_STATUS, "搜索完成！", dataList);
			result.setPageCount(pageCount);
			result.setPageNum(pagenum);
			result.setPageSize(pagesize);
			result.setTotalCount(recordCount);
		} else {
			result.init(FAIL_STATUS, "请重新登录", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	@ActionParam(key = "category", type = ValueType.NUMBER)
	public void DownloadList() {
		ActionResult result = new ActionResult();
		User user = (User) session.getAttribute("usersession");

		if (user != null) {
			HashMap<String, String> searchMap = new HashMap<String, String>();
			if(request.getParameter("category") !=null && !request.getParameter("category").equals("") 
					&& !request.getParameter("category").equals("0")){
				Category category = this.categoryService.getCategory(Integer.valueOf(request.getParameter("category")));
				if(category != null){
					searchMap.put("categoryScode", category.getScode());
				}
			}
			searchMap.put("user", user.getId() + "");

			String sorts = "createtime desc";
			int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
			int pagesize = this.getIntValue(request.getParameter("pagesize"),
					Dictionary.PAGESIZE_DEFAULT);
			int offset = (pagenum - 1) * pagesize;

			int recordCount = this.resourceService
					.getUserDownloadCountByParams(searchMap);
			int pageCount = (int) Math.ceil((double) recordCount / pagesize);

			List<Resource> resourceList = this.resourceService
					.getUserDownloadListForPage(searchMap, sorts, offset,
							pagesize);
			List<Map<String, Object>> dataList = new ArrayList<>();
			if (resourceList != null && resourceList.size() > 0) {
				for (Resource resource : resourceList) {
					Map<String, Object> data = SerializeEntity
							.resource2Map(resource);
					HashMap<String, String> countSearchMap = new HashMap<String, String>();
					countSearchMap.put("resource", resource.getId() + "");
					Integer userLoveCount = this.userLoveResourceService
							.getCountByParams(countSearchMap);
					Integer userDownloadCount = this.userDownloadResourceService
							.getCountByParams(countSearchMap);
					data.put("userLoveCount", userLoveCount);
					data.put("userDownloadCount", userDownloadCount);
					dataList.add(data);
				}
			}
			result.init(SUCCESS_STATUS, "搜索完成！", dataList);
			result.setPageCount(pageCount);
			result.setPageNum(pagenum);
			result.setPageSize(pagesize);
			result.setTotalCount(recordCount);
		} else {
			result.init(FAIL_STATUS, "请重新登录", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	@ActionParam(key = "number", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void GetKeyword() {
		ActionResult result = new ActionResult();
		int number = Integer.valueOf(request.getParameter("number"));
		List<Keyword> keywordList = this.keywordService.getKeyWordList(number);
		List<Object> dataList = new ArrayList<Object>();
		for (Keyword keyword : keywordList) {
			dataList.add(keyword.getWord());
		}
		result.init(SUCCESS_STATUS, "搜索完成！", dataList);

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void BuildImg() {
		ActionResult result = new ActionResult();
		Integer id = Integer.valueOf(request.getParameter("id"));
		String serverPath = this.getClass().getResource("/").getPath();
		serverPath = serverPath.substring(0,serverPath.indexOf("WEB-INF"));
		
		Resource resource = this.resourceService.getResource(id);
		if(resource!=null){
//			HashMap<String,List<String>> resultMap = ImageProcessing.imageBuild(resource, 500, serverPath);
//			if(resultMap.get("status").get(0).equals("success")){
//				result.init(SUCCESS_STATUS, "生成图片成功！", resultMap.get("result"));
//			}else{
//				result.init(FAIL_STATUS, "生成图片失败！", null);
//			}
			List<String> urlList = new ArrayList<String>();
			urlList.add("img/child0.jpg");
			urlList.add("img/child1.jpg");
			urlList.add("img/child2.jpg");
			urlList.add("img/child3.jpg");
			urlList.add("img/child4.jpg");
			result.init(SUCCESS_STATUS, "生成图片成功！", urlList);
		}else{
			result.init(FAIL_STATUS, "资源不存在！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}
	
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void DownloadImg() {
		ActionResult result = new ActionResult();
		User user = (User) session.getAttribute("usersession");
		String serverPath = ServletActionContext.getServletContext()
				.getRealPath("/").replace("\\", "/");
		String dataType = "";
		Integer id = Integer.valueOf(request.getParameter("id"));
		if (user != null) {
			Resource resource = this.resourceService.getResource(id);
			if (resource != null) {
				dataType = ServletActionContext.getServletContext().getMimeType(resource.getType());
				String filename = Utlity.getUUID()+resource.getType();
				response.setContentType(dataType);
				response.setHeader("Content-disposition", "attachment; filename=" + filename);

				BufferedInputStream bis = null;
				BufferedOutputStream bos = null;

				try {
					bis = new BufferedInputStream(new FileInputStream(
							serverPath + "/" + resource.getUrl()));
					bos = new BufferedOutputStream(response.getOutputStream());

					byte[] buff = new byte[2048000];
					@SuppressWarnings("unused")
					int bytesRead = 0;

					while (-1 != (bytesRead = (bis.read(buff, 0, buff.length)))) {
						bos.write(buff, 0, buff.length);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (bis != null) {
						try {
							bis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (bos != null) {
						try {
							bos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				return;

			} else {
				result.init(FAIL_STATUS, "素材不存在", null);
			}
		} else {
			result.init(FAIL_STATUS, "请重新登录", null);
		}

		Utlity.ResponseWrite(result, dataType, response);
	}
	
	@ActionParam(key = "url", type = ValueType.STRING, nullable = false, emptyable = false)
	public void DownloadBuildImg() {
		ActionResult result = new ActionResult();
		User user = (User) session.getAttribute("usersession");
		String serverPath = ServletActionContext.getServletContext()
				.getRealPath("/").replace("\\", "/");
		String dataType = "";
		String url = request.getParameter("url");
		String type = url.substring(url.lastIndexOf("."));
		if (user != null) {
			dataType = ServletActionContext.getServletContext().getMimeType(type);
			String filename = Utlity.getUUID()+type;
			response.setContentType(dataType);
			response.setHeader("Content-disposition", "attachment; filename=" + filename);

			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;

			try {
				bis = new BufferedInputStream(new FileInputStream(
						serverPath + "/" + url));
				bos = new BufferedOutputStream(response.getOutputStream());

				byte[] buff = new byte[2048000];
				@SuppressWarnings("unused")
				int bytesRead = 0;

				while (-1 != (bytesRead = (bis.read(buff, 0, buff.length)))) {
					bos.write(buff, 0, buff.length);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (bos != null) {
					try {
						bos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			return;

		} else {
			result.init(FAIL_STATUS, "请重新登录", null);
		}

		Utlity.ResponseWrite(result, dataType, response);
	}
}
