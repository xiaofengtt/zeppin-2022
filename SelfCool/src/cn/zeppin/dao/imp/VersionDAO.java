/** 
 * Project Name:Self_Cool 
 * File Name:RoleDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.imp;

import java.util.HashMap;
import java.util.List;
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
	
	/**
	 * 获取个数
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:30:41 <br/>
	 * @param params
	 * @return
	 */
	public int getVersionCountByParams(HashMap<String, Object> params) {
		StringBuilder hql = new StringBuilder();
		hql.append("select count(*) from Version v where 1=1 ");
		if (params.get("id") != null  && !params.get("id").equals("")){
			hql.append(" and v.id=").append(params.get("id"));
		}
		if (params.get("version") != null && !params.get("version").equals("")){
			hql.append(" and v.version like '%").append(params.get("version")).append("%' ");
		}
		if (params.get("device") != null  && !params.get("device").equals("")){
			hql.append(" and v.device=").append(params.get("device"));
		}
		if (params.get("forcedUpdate") != null  && !params.get("forcedUpdate").equals("")){
			hql.append(" and v.forcedUpdate=").append(params.get("forcedUpdate"));
		}
		if (params.get("status") != null && !params.get("status").equals("")){
			hql.append(" and v.status=").append(params.get("status"));
		}
		Object result = this.getResultByHQL(hql.toString());
		if (result != null) {
			return Integer.valueOf(result.toString());
		}else{
			return 0;
		}
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
	public List<Version> getVersionListByParams(HashMap<String, Object> params, String sorts, int offset, int length) {

		StringBuilder hql = new StringBuilder();
		hql.append(" from Version v where 1=1 ");
		if (params.get("id") != null  && !params.get("id").equals("")){
			hql.append(" and v.id=").append(params.get("id"));
		}
		if (params.get("version") != null && !params.get("version").equals("")){
			hql.append(" and v.version like '%").append(params.get("version")).append("%' ");
		}
		if (params.get("device") != null  && !params.get("device").equals("")){
			hql.append(" and v.device=").append(params.get("device"));
		}
		if (params.get("forcedUpdate") != null  && !params.get("forcedUpdate").equals("")){
			hql.append(" and v.forcedUpdate=").append(params.get("forcedUpdate"));
		}
		if (params.get("status") != null && !params.get("status").equals("")){
			hql.append(" and v.status=").append(params.get("status"));
		}
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			hql.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				hql.append(comma);
				hql.append("v.").append(sort);
				comma = ",";
			}
		}
		return this.getByHQL(hql.toString(), offset, length);
	}
	
	
	public Version getVersionByParams(Map<String,Object> map){
		StringBuilder sb = new StringBuilder();
		sb.append("from Version v where 1=1 ");
		
		if (map.containsKey("version") && map.get("version") != null) {
			sb.append(" and v.version='").append(map.get("version")).append("'");
		}
		if (map.containsKey("device") && map.get("device") != null) {
			sb.append(" and v.device='").append(map.get("device")).append("'");
		}
		return (Version) this.getResultByHQL(sb.toString());
	}

	
	public List<Version> getNewVersions(Map<String,Object> map){
		StringBuilder sb = new StringBuilder();
		sb.append("from Version v where v.status = 1");

		if (map.containsKey("device") && map.get("device") != null) {
			sb.append(" and v.device='").append(map.get("device")).append("'");
		}
		if (map.containsKey("id") && map.get("id") != null) {
			sb.append(" and v.id >'").append(map.get("id")).append("'");
		}
		sb.append(" order by v.id desc");
		
		return this.getByHQL(sb.toString());
	}
}
