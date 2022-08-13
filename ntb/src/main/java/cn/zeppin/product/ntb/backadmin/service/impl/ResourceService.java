/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IResourceDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 *
 */
@Service
public class ResourceService extends BaseService implements IResourceService {

	@Autowired
	private IResourceDAO resourceDAO;
	
	@Override
	public Resource insert(Resource resource) {
		return resourceDAO.insert(resource);
	}

	@Override
	public Resource delete(Resource resource) {
		return resourceDAO.delete(resource);
	}

	@Override
	public Resource update(Resource resource) {
		return resourceDAO.update(resource);
	}

	@Override
	public Resource get(String uuid) {
		return resourceDAO.get(uuid);
	}

	@Override
	public Resource updateName(Resource resource) {
		String beanPath = Resource.class.getResource("").getPath();
		String serverPath = beanPath.substring(0,beanPath.indexOf("WEB-INF"));
		File file1 = new File(serverPath + resource.getUrl());
		String baseUrl = resource.getUrl().substring(0, resource.getUrl().lastIndexOf("/") + 1);
		String newUrl = baseUrl+resource.getFilename() + "." + resource.getFiletype();
		File file2 = new File(serverPath + newUrl);
		Boolean result = file1.renameTo(file2);
		if (result){
			resource.setUrl(newUrl);
			return resourceDAO.update(resource);
		}else{
			return null;
		}
	}
}
