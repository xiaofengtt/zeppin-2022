/**
 * This class is used for 资源表操作
 * 
 * @author suijing
 * @version 1.0, 2014年7月24日 下午5:05:00
 */
package cn.zeppin.action.admin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.entity.Resource;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IResourceService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

/**
 * @author sj
 * 
 */
public class ResourceAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 369393031897616109L;

	private IResourceService resourceService;

	public IResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(IResourceService resourceService) {
		this.resourceService = resourceService;
	}

	/**
	 * 添加
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "type", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Add() {
		
		
		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		ActionResult actionResult = new ActionResult();
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

						String[] dir = UUID.randomUUID().toString().split("-");
						String basePath = "upload/";

						for (String sPath : dir) {
							basePath += sPath + "/";

							File tfFile = new File(serverPath + "/" + basePath);
							if (!tfFile.exists()) {
								tfFile.mkdirs();
							}
						}
						// 存储文件

						resource.setName(item.getName().substring(0, item.getName().lastIndexOf(".")));
						resource.setTitle(resource.getName());
						resource.setFilesize((int) item.getSize());
						resource.setSuffix(item.getName().substring(item.getName().lastIndexOf(".")));
						resource.setSourcePath(basePath + resource.getName() + resource.getSuffix());
						resource.setPath(resource.getSourcePath());
						resource.setType(Short.parseShort(request.getParameter("type")));
						resource.setStatus((short) 1);
						resource.setCreatetime(Utlity.getCurrentTime(""));
						resource.setSysUser(currentUser);
						File file2 = new File(serverPath + "/" + resource.getSourcePath());
						try {

							item.write(file2);
							resource = resourceService.add(resource);
							Map<String, Object> data = SerializeEntity.resource2Map(resource);
							actionResult.init(SUCCESS, "资源上传成功", data);

						} catch (Exception e) {
							e.getStackTrace().toString();
							actionResult.init(FAIL_STATUS, "资源上传失败", null);
						}

					}
				}
			}
		}
		Utlity.ResponseWrite(actionResult, dataType, response);

	}

	/**
	 * 修改
	 */
	public void Update() {

		ActionResult actionResult = new ActionResult();
		// check paras
		Resource resource = new Resource();
		resourceService.update(resource);
		actionResult.init(SUCCESS, "资源更新成功", null);
		Utlity.ResponseWrite(actionResult, dataType, response);
	}


	/**
	 * 删除
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Delete() {
		// TODO Auto-generated method stub
		ActionResult actionResult = new ActionResult();
		int id = Integer.parseInt(request.getParameter("id"));
		Resource resource = resourceService.getById(id);
		if (resource != null) {
			resource.setStatus((short) 0);
			resourceService.update(resource);

			actionResult.init(SUCCESS, "删除更新成功", null);
		} else {
			actionResult.init(FAIL_STATUS, "此资源不存在", null);
		}
		Utlity.ResponseWrite(actionResult, dataType, response);

	}

	/**
	 * 列表
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void List() {
		
		int id = -1;
		ActionResult actionResult = new ActionResult();
		id = Integer.parseInt(request.getParameter("id"));
		Resource resource = resourceService.getById(id);
		if (resource != null) {

			Map<String, Object> data = null;
			try {
				data = SerializeEntity.resource2Map(resource);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			actionResult.init(SUCCESS_STATUS, "获取资源成功", data);

		} else {
			actionResult.init(FAIL_STATUS, "资源不存在", null);
		}
		Utlity.ResponseWrite(actionResult, dataType, response);

	}

}
