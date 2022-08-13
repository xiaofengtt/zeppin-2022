package cn.zeppin.service.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Version;

public interface IVersionService {

	/**
	 * 添加
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午4:57:31 <br/>
	 * @param Version
	 */
	public void addVersion(Version version);

	/**
	 * 更新
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 上午11:27:43 <br/>
	 * @param Version
	 */
	public void updateVersion(Version version);

	/**
	 * 删除
	 * 
	 * @author Administrator
	 * @date: 2014年7月22日 下午2:02:09 <br/>
	 * @param Version
	 */
	public void deleteVersion(Version version);
	
	/**
	 * 根据 id获取
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:12:39 <br/>
	 * @param id
	 * @return
	 */
	public Version getVersionById(Integer id);
	
	/**
	 * 获取个数
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:30:41 <br/>
	 * @param searchMap
	 * @return
	 */
	public int getVersionCountByParams(HashMap<String, Object> searchMap);

	/**
	 * 获取分页列表
	 * 
	 * @author Administrator
	 * @date: 2014年7月15日 下午3:19:03 <br/>
	 * @param params
	 * @param sorts
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Version> getVersionListByParams(HashMap<String, Object> params, String sorts, int offset, int length);
	
	/**
	 * 查询版本
	 * @param version
	 */
	public Version getVersionByParams(Map<String, Object> map);
	
	/**
	 * 查询新版本
	 * @param version
	 */
	public List<Version> getNewVersions(Map<String, Object> map);
	
}
