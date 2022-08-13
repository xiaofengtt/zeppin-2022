package cn.zeppin.dao.imp;

import cn.zeppin.dao.api.IResourceCustomTagDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.ResourceCustomTag;

public class ResourceCustomTagDAO  extends HibernateTemplateDAO<ResourceCustomTag, Integer> implements IResourceCustomTagDAO{
	
	/**
	 * 删除资源的所有自定义标签
	 */
	public void deleteResourceCustomTagByResource(int id){
		StringBuilder sb = new StringBuilder();
		sb.append("delete ResourceCustomTag where resource=").append(id);
		this.executeHQL(sb.toString());
	}
	
}