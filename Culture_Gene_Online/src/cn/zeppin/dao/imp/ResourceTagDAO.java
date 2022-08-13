package cn.zeppin.dao.imp;


import cn.zeppin.dao.api.IResourceTagDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.ResourceTag;

public class ResourceTagDAO extends HibernateTemplateDAO<ResourceTag, Integer> implements IResourceTagDAO{

	/**
	 * 删除资源的所有标签
	 */
	public void deleteResourceTagByResource(int id){
		StringBuilder sb = new StringBuilder();
		sb.append("delete ResourceTag where resource=").append(id);
		this.executeHQL(sb.toString());
	}

}