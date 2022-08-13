package cn.zeppin.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import cn.zeppin.dao.api.IResourceDAO;
import cn.zeppin.entity.Category;
import cn.zeppin.entity.National;
import cn.zeppin.entity.Resource;
import cn.zeppin.service.api.IResourceService;

public class ResourceService implements IResourceService
{
	IResourceDAO resourceDAO;

	public IResourceDAO getResourceDAO()
	{
		return resourceDAO;
	}

	public void setResourceDAO(IResourceDAO resourceDAO)
	{
		this.resourceDAO = resourceDAO;
	}
	
	/**
	 * 获取
	 */
	public Resource getResource(int id)
	{
		return this.getResourceDAO().get(id);
	}
	
	/**
	 * 添加
	 */
	public Resource addResource(Resource resource)
	{
		return this.getResourceDAO().save(resource);
	}
	
	/**
	 * 修改
	 */
	public void updateResource(Resource resource)
	{
		this.getResourceDAO().update(resource);
		Category cag = resource.getCategory();
		National nat = resource.getNational();
		Integer status = resource.getStatus();
		
		Set<Resource> childResource = resource.getChildResource();
		if(childResource != null && !childResource.isEmpty()){
			for(Resource res : childResource){
				res.setCategory(cag);
				res.setNational(nat);
				res.setStatus(status);
				updateResource(res);
			}
		}
	}
	
	/**
	 * 停用
	 */
	public void deleteResource(int id)
	{
		Resource resource = this.getResourceDAO().get(id);
		resource.setStatus(0);
		this.getResourceDAO().update(resource);
	}
	
	/**
	 * 通过参数取count
	 */
	public Integer getCountByParams(HashMap<String,String> searchMap){
		return this.getResourceDAO().getCountByParams(searchMap);
	}
	
	/**
	 * 获取页面信息
	 */
	public List<Resource> getListForPage(HashMap<String,String> searchMap, String sort,  Integer offset, Integer pagesize ){
		return this.getResourceDAO().getListByParams(searchMap, sort, offset, pagesize);
	}
	
	/**
	 * 通过参数取列表
	 */
	public List<Resource> getListByParams(HashMap<String,String> searchMap){
		return this.getResourceDAO().getListByParams(searchMap, null, null, null);
	}
	
	/**
	 * 通过参数取用户下载count
	 */
	public Integer getUserDownloadCountByParams(HashMap<String,String> searchMap){
		return this.getResourceDAO().getUserDownloadCountByParams(searchMap);
	}
	
	/**
	 * 获取用户下载页面信息
	 */
	public List<Resource> getUserDownloadListForPage(HashMap<String,String> searchMap, String sort,  Integer offset, Integer pagesize ){
		return this.getResourceDAO().getUserDownloadListByParams(searchMap, sort, offset, pagesize);
	}
	
	/**
	 * 通过参数取用户收藏count
	 */
	public Integer getUserLoveCountByParams(HashMap<String,String> searchMap){
		return this.getResourceDAO().getUserLoveCountByParams(searchMap);
	}
	
	/**
	 * 获取用户收藏页面信息
	 */
	public List<Resource> getUserLoveListForPage(HashMap<String,String> searchMap, String sort,  Integer offset, Integer pagesize ){
		return this.getResourceDAO().getUserLoveListByParams(searchMap, sort, offset, pagesize);
	}
}
