package cn.zeppin.service.imp;

import cn.zeppin.dao.api.IResourceDAO;
import cn.zeppin.entity.Resource;
import cn.zeppin.service.api.IResourceService;

/**
 * @author sj
 * 
 */
public class ResourceService implements IResourceService {

	IResourceDAO iResourceDAO;

	/**
	 * @return the iResourceDAO
	 */
	public IResourceDAO getiResourceDAO() {
		return iResourceDAO;
	}

	/**
	 * @param iResourceDAO
	 *            the iResourceDAO to set
	 */
	public void setiResourceDAO(IResourceDAO iResourceDAO) {
		this.iResourceDAO = iResourceDAO;
	}

	@Override
	public Resource getById(int id) {

		return this.getiResourceDAO().get(id);
	}

	@Override
	public Resource add(Resource resource) {

		return this.getiResourceDAO().save(resource);
	}

	@Override
	public void update(Resource resource) {

		this.getiResourceDAO().update(resource);
	}

	@Override
	public void delById(int id) {

		Resource resource = this.getiResourceDAO().get(id);
		resource.setStatus((short) 0);
		this.getiResourceDAO().update(resource);
	}

}
