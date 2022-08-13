package cn.zeppin.dao.api;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.entity.Resource;

public interface IResourceDAO extends IBaseDAO<Resource, Integer> {
	/**
	 * 通过参数取count
	 */
	public Integer getCountByParams(HashMap<String,String> searchMap);
	
	/**
	 * 通过参数取列表
	 */
	public List<Resource> getListByParams(HashMap<String,String> searchMap, String sorts, Integer offset, Integer length);
	
	/**
	 * 通过参数取用户收藏count
	 */
	public Integer getUserLoveCountByParams(HashMap<String,String> searchMap);
	
	/**
	 * 获取用户收藏页面信息
	 */
	public List<Resource> getUserLoveListByParams(HashMap<String,String> searchMap, String sorts, Integer offset, Integer length);
	
	/**
	 * 通过参数取用户下载count
	 */
	public Integer getUserDownloadCountByParams(HashMap<String,String> searchMap);
	
	/**
	 * 获取用户下载页面信息
	 */
	public List<Resource> getUserDownloadListByParams(HashMap<String,String> searchMap, String sorts, Integer offset, Integer length);
}
