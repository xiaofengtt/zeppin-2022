/** 
 * Project Name:Self_Cool 
 * File Name:RoleDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.imp;

import java.util.Map;

import cn.zeppin.dao.api.IVersionDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Version;

/** 
 * ClassName: VersionDAO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2015年6月10日 下午9:47:47 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public class VersionDAO extends HibernateTemplateDAO<Version, Integer> implements IVersionDAO{
	
	public Version getVersionByParams(Map<String,Object> map){
		StringBuilder sb = new StringBuilder();
		sb.append("from Version v where 1=1 ");
		
		if (map.containsKey("version") && map.get("version") != null) {
			sb.append(" and v.version='").append(map.get("version")).append("'");
		}
		return (Version) this.getResultByHQL(sb.toString());
	}

}
