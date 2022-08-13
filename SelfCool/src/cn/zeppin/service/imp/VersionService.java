package cn.zeppin.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IVersionDAO;
import cn.zeppin.entity.Version;
import cn.zeppin.service.api.IVersionService;
import cn.zeppin.utility.Dictionary;

public class VersionService implements IVersionService {
	
	private IVersionDAO versionDAO;

	public IVersionDAO getVersionDAO() {
		return versionDAO;
	}

	public void setVersionDAO(IVersionDAO versionDAO) {
		this.versionDAO = versionDAO;
	}
	
	/**
	 * 添加
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午4:57:31 <br/>
	 * @param Version
	 */
	@Override
	public void addVersion(Version version) {
		this.getVersionDAO().save(version);
	}
	
	/**
	 * 修改
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午4:57:31 <br/>
	 * @param Version
	 */
	@Override
	public void updateVersion(Version version) {
		this.getVersionDAO().update(version);
	}

	/**
	 * 删除
	 * @author Administrator
	 * @date: 2014年7月22日 下午2:02:09 <br/> 
	 * @param Version
	 */
	@Override
	public void deleteVersion(Version version) {
		version.setStatus(Dictionary.VERSION_TEST);
		this.getVersionDAO().update(version);
	}
	
	/**
	 * 根据id获取
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:12:39 <br/>
	 * @param id
	 * @return
	 */
	@Override
	public Version getVersionById(Integer id) {
		return this.getVersionDAO().get(id);
	}

	/**
	 * 获取个数
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:30:41 <br/>
	 * @param params
	 * @return
	 */
	@Override
	public int getVersionCountByParams(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return this.getVersionDAO().getVersionCountByParams(params);
	}

	/**
	 * 获取分页列表
	 * 
	 * @author Administrator
	 * @date: 2014年7月15日 下午3:19:03 <br/>
	 * @param params
	 * @param offset
	 * @param length
	 * @return
	 */
	@Override
	public List<Version> getVersionListByParams(HashMap<String, Object> params, String sorts, int offset, int length) {
		// TODO Auto-generated method stub
		return this.getVersionDAO().getVersionListByParams(params, sorts, offset, length);
	}
	
	public Version getVersionByParams(Map<String, Object> map){
		return this.getVersionDAO().getVersionByParams(map);
	}
	
	public List<Version> getNewVersions(Map<String, Object> map){
		return this.getVersionDAO().getNewVersions(map);
	}
}
