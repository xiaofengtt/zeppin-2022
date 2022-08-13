package cn.zeppin.service.imp;

import java.util.Map;

import cn.zeppin.dao.api.IVersionDAO;
import cn.zeppin.entity.Version;
import cn.zeppin.service.api.IVersionService;

public class VersionService implements IVersionService {
	
	private IVersionDAO versionDAO;

	public IVersionDAO getVersionDAO() {
		return versionDAO;
	}

	public void setVersionDAO(IVersionDAO versionDAO) {
		this.versionDAO = versionDAO;
	}
	
	public Version getVersionByParams(Map<String, Object> map){
		return this.getVersionDAO().getVersionByParams(map);
	}
	
}
