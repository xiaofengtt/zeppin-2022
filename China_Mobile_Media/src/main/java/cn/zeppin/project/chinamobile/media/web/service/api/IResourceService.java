package cn.zeppin.project.chinamobile.media.web.service.api;

import cn.zeppin.project.chinamobile.media.core.entity.Resource;
import cn.zeppin.project.chinamobile.media.web.service.base.IBaseService;


public interface IResourceService extends IBaseService<Resource, String> {

	public Resource insert(Resource resource);
	
	public Resource delete(Resource resource);
	
	public Resource update(Resource resource);
	
	public Resource get(String id);
}
