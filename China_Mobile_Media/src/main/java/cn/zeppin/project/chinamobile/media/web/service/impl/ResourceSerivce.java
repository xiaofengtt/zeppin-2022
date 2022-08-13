package cn.zeppin.project.chinamobile.media.web.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.project.chinamobile.media.core.entity.Resource;
import cn.zeppin.project.chinamobile.media.core.entity.base.BaseEntity.GerneralStatusType;
import cn.zeppin.project.chinamobile.media.web.dao.api.IResourceDAO;
import cn.zeppin.project.chinamobile.media.web.service.api.IResourceService;
import cn.zeppin.project.chinamobile.media.web.service.base.BaseService;

/**
 * @author Clark.R 2016年4月30日
 *
 */

@Service
public class ResourceSerivce extends BaseService implements IResourceService {

	@Autowired
	private IResourceDAO resourceDAO;

	@Override
	public Resource insert(Resource resource){
		resource = resourceDAO.insert(resource);
		return resource;
	}
	
	@Override
	public Resource delete(Resource resource) {
		resource.setStatus(GerneralStatusType.DELETED);
		resource = resourceDAO.update(resource);
		return resource;
	}
	
	@Override
	public Resource update(Resource resource) {
		return resourceDAO.update(resource);
	}

	@Override
	public Resource get(String id) {
		return resourceDAO.get(id);
	}
	
}
