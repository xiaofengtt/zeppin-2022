package cn.zeppin.service.api;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.entity.Resource;

public interface IResourceService
{
	
	/**
	 * 获取
	 */
	public Resource getResource(int id);
	
	/**
	 * 添加
	 */
	public Resource addResource(Resource resource);
	
	/**
	 * 更新
	 */
	public void updateResource(Resource resource);
	
	/**
	 * 删除
	 */
	public void deleteResource(int id);
	
	/**
	 * 通过参数取count
	 */
	public Integer getCountByParams(HashMap<String,String> searchMap);
	
	/**
	 * 获取页面信息
	 */
	public List<Resource> getListForPage(HashMap<String,String> searchMap, String sort,  Integer offset, Integer pagesize );
	
	/**
	 * 通过参数取列表
	 */
	public List<Resource> getListByParams(HashMap<String,String> searchMap);
	
	/**
	 * 通过参数取用户收藏count
	 */
	public Integer getUserLoveCountByParams(HashMap<String,String> searchMap);
	
	/**
	 * 获取用户收藏页面信息
	 */
	public List<Resource> getUserLoveListForPage(HashMap<String,String> searchMap, String sort,  Integer offset, Integer pagesize );
	
	/**
	 * 通过参数取用户下载count
	 */
	public Integer getUserDownloadCountByParams(HashMap<String,String> searchMap);
	
	/**
	 * 获取用户下载页面信息
	 */
	public List<Resource> getUserDownloadListForPage(HashMap<String,String> searchMap, String sort,  Integer offset, Integer pagesize );
}
