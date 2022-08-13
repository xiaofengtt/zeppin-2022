package cn.zeppin.dao.imp;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import cn.zeppin.dao.api.IResourceDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Resource;

public class ResourceDAO extends HibernateTemplateDAO<Resource, Integer> implements IResourceDAO{
	
	/**
	 * 添加
	 */
	@Override
	public Resource save(Resource resource) {
		resource.setScode("");
		Resource result = super.save(resource);
		String format = "0000";
		DecimalFormat df = new DecimalFormat(format);
		String scode = df.format(result.getId());
		if (result.getParent() != null) {
			scode = result.getParent().getScode() + scode;
		}
		result.setScode(scode);
		result = this.update(result);
		return result;
	}
	
	/**
	 * 通过参数取count
	 */
	public Integer getCountByParams(HashMap<String,String> searchMap){
		StringBuilder sb = new StringBuilder();
		sb.append("select count(distinct r.id) from Resource r");
		sb.append(" left outer join r.tagList as rt left outer join r.customTagList as rct");
		sb.append(" left outer join r.national as n left outer join r.category as c");
		sb.append(" where r.status>0");
		if (searchMap.get("search") != null && !searchMap.get("search").equals("")){
			String[] searchs = searchMap.get("search").split(" ");
			if (searchs.length > 0){
				sb.append(" and (");
				for(String s : searchs){
					s=s.trim();
					sb.append(" (r.title like'%").append(s).append("%'");
					sb.append(" or rt.tag like'%").append(s).append("%'");
					sb.append(" or rct.value like'%").append(s).append("%'");
					sb.append(" or n.name like'%").append(s).append("%'");
					sb.append(" or c.name like'%").append(s).append("%'");
					sb.append(" or r.meaning like'%").append(s).append("%') or");
				}
				sb.setLength(sb.length() - 2);
				sb.append(")");
			}
		}
		if (searchMap.get("category") != null && !searchMap.get("category").equals("")){
			sb.append(" and r.category=").append(searchMap.get("category"));
		}
		if (searchMap.get("categoryScode") != null && !searchMap.get("categoryScode").equals("")){
			sb.append(" and r.category.scode like '").append(searchMap.get("categoryScode")).append("%'");
		}
		if (searchMap.get("except") != null && !searchMap.get("except").equals("")){
			sb.append(" and r.id<>").append(searchMap.get("except"));
		}
		if (searchMap.get("national") != null && !searchMap.get("national").equals("")){
			sb.append(" and r.national=").append(searchMap.get("national"));
		}
		if (searchMap.get("parent") != null && !searchMap.get("parent").equals("")){
			sb.append(" and r.parent=").append(searchMap.get("parent"));
		}
		if (searchMap.get("resourceScode") != null && !searchMap.get("resourceScode").equals("")){
			sb.append(" and r.scode like '").append(searchMap.get("resourceScode")).append("%'");
		}
		if (searchMap.get("level") != null && !searchMap.get("level").equals("")){
			sb.append(" and r.level=").append(searchMap.get("level"));
		}
		if (searchMap.get("type") != null && !searchMap.get("type").equals("")){
			sb.append(" and r.type='.").append(searchMap.get("type")).append("'");
		}
		if (searchMap.get("url") != null && !searchMap.get("url").equals("")){
			sb.append(" and r.url='").append(searchMap.get("url")).append("'");
		}
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")){
			sb.append(" and r.status=").append(searchMap.get("status"));
		}
		if (searchMap.get("user") != null && !searchMap.get("user").equals("")){
			sb.append(" and r.owner=").append(searchMap.get("user"));
		}
		if (searchMap.get("title") != null && !searchMap.get("title").equals("")){
			sb.append(" and r.title like'%").append(searchMap.get("title")).append("%'");
		}
		if (searchMap.get("tag") != null && !searchMap.get("tag").equals("")){
			sb.append(" and rt.tag like'%").append(searchMap.get("tag")).append("%'");
		}
		return Integer.valueOf(this.getResultByHQL(sb.toString()).toString());
	}
	
	/**
	 * 通过参数取列表
	 */
	public List<Resource> getListByParams(HashMap<String,String> searchMap, String sorts, Integer offset, Integer length){
		StringBuilder sb = new StringBuilder();
		sb.append("select distinct r from Resource r");
		sb.append(" left outer join r.tagList as rt left outer join r.customTagList as rct");
		sb.append(" left outer join r.national as n left outer join r.category as c");
		sb.append(" where r.status>0");
		if (searchMap.get("search") != null && !searchMap.get("search").equals("")){
			String[] searchs = searchMap.get("search").split(" ");
			if (searchs.length > 0){
				sb.append(" and (");
				for(String s : searchs){
					s=s.trim();
					sb.append(" (r.title like'%").append(s).append("%'");
					sb.append(" or rt.tag like'%").append(s).append("%'");
					sb.append(" or rct.value like'%").append(s).append("%'");
					sb.append(" or n.name like'%").append(s).append("%'");
					sb.append(" or c.name like'%").append(s).append("%'");
					sb.append(" or r.meaning like'%").append(s).append("%') or");
				}
				sb.setLength(sb.length() - 2);
				sb.append(")");
			}
		}
		if (searchMap.get("category") != null && !searchMap.get("category").equals("")){
			sb.append(" and r.category=").append(searchMap.get("category"));
		}
		if (searchMap.get("categoryScode") != null && !searchMap.get("categoryScode").equals("")){
			sb.append(" and r.category.scode like '").append(searchMap.get("categoryScode")).append("%'");
		}
		if (searchMap.get("except") != null && !searchMap.get("except").equals("")){
			sb.append(" and r.id<>").append(searchMap.get("except"));
		}
		if (searchMap.get("national") != null && !searchMap.get("national").equals("")){
			sb.append(" and r.national=").append(searchMap.get("national"));
		}
		if (searchMap.get("level") != null && !searchMap.get("level").equals("")){
			sb.append(" and r.level=").append(searchMap.get("level"));
		}
		if (searchMap.get("type") != null && !searchMap.get("type").equals("")){
			sb.append(" and r.type='.").append(searchMap.get("type")).append("'");
		}
		if (searchMap.get("url") != null && !searchMap.get("url").equals("")){
			sb.append(" and r.url='").append(searchMap.get("url")).append("'");
		}
		if (searchMap.get("parent") != null && !searchMap.get("parent").equals("")){
			sb.append(" and r.parent=").append(searchMap.get("parent"));
		}
		if (searchMap.get("resourceScode") != null && !searchMap.get("resourceScode").equals("")){
			sb.append(" and r.scode like '").append(searchMap.get("resourceScode")).append("%'");
		}
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")){
			sb.append(" and r.status=").append(searchMap.get("status"));
		}
		if (searchMap.get("user") != null && !searchMap.get("user").equals("")){
			sb.append(" and r.owner=").append(searchMap.get("user"));
		}
		if (searchMap.get("title") != null && !searchMap.get("title").equals("")){
			sb.append(" and r.title like'%").append(searchMap.get("title")).append("%'");
		}
		if (searchMap.get("tag") != null && !searchMap.get("tag").equals("")){
			sb.append(" and rt.tag like'%").append(searchMap.get("tag")).append("%'");
		}
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			sb.append(" order by ");
			String comma = "";
			for (String sort : sortArray){
				sb.append(comma);
				sb.append(" r.").append(sort);
				comma = ",";
			}
		}
		if(offset!=null && length!=null){
			return this.getByHQL(sb.toString(), offset, length);
		}else{
			return this.getByHQL(sb.toString());
		}
	}
	
	/**
	 * 通过参数取用户收藏count
	 */
	public Integer getUserLoveCountByParams(HashMap<String,String> searchMap){
		StringBuilder sb = new StringBuilder();
		sb.append("select count(distinct r.id) from UserLoveResource ulr left outer join ulr.resource as r where 1=1");
		if (searchMap.get("user") != null && !searchMap.get("user").equals("")){
			sb.append(" and ulr.user=").append(searchMap.get("user"));
		}
		if (searchMap.get("category") != null && !searchMap.get("category").equals("")){
			sb.append(" and r.category=").append(searchMap.get("category"));
		}
		return Integer.valueOf(this.getResultByHQL(sb.toString()).toString());
	}
	
	/**
	 * 获取用户收藏页面信息
	 */
	public List<Resource> getUserLoveListByParams(HashMap<String,String> searchMap, String sorts, Integer offset, Integer length){
		StringBuilder sb = new StringBuilder();
		sb.append(" select distinct r from UserLoveResource ulr left outer join ulr.resource as r where 1=1");
		if (searchMap.get("user") != null && !searchMap.get("user").equals("")){
			sb.append(" and ulr.user=").append(searchMap.get("user"));
		}
		if (searchMap.get("category") != null && !searchMap.get("category").equals("")){
			sb.append(" and r.category=").append(searchMap.get("category"));
		}
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			sb.append(" order by ");
			String comma = "";
			for (String sort : sortArray){
				sb.append(comma);
				sb.append(" ulr.").append(sort);
				comma = ",";
			}
		}
		if(offset!=null && length!=null){
			return this.getByHQL(sb.toString(), offset, length);
		}else{
			return this.getByHQL(sb.toString());
		}
	}
	
	/**
	 * 通过参数取用户下载count
	 */
	public Integer getUserDownloadCountByParams(HashMap<String,String> searchMap){
		StringBuilder sb = new StringBuilder();
		sb.append("select count(distinct r.id) from UserDownloadResource udr left outer join udr.resource as r where 1=1");
		if (searchMap.get("user") != null && !searchMap.get("user").equals("")){
			sb.append(" and udr.user=").append(searchMap.get("user"));
		}
		if (searchMap.get("category") != null && !searchMap.get("category").equals("")){
			sb.append(" and r.category=").append(searchMap.get("category"));
		}
		return Integer.valueOf(this.getResultByHQL(sb.toString()).toString());
	}
	
	/**
	 * 获取用户下载页面信息
	 */
	public List<Resource> getUserDownloadListByParams(HashMap<String,String> searchMap, String sorts, Integer offset, Integer length){
		StringBuilder sb = new StringBuilder();
		sb.append(" select distinct r from UserDownloadResource udr left outer join udr.resource as r where 1=1");
		if (searchMap.get("user") != null && !searchMap.get("user").equals("")){
			sb.append(" and udr.user=").append(searchMap.get("user"));
		}
		if (searchMap.get("category") != null && !searchMap.get("category").equals("")){
			sb.append(" and r.category=").append(searchMap.get("category"));
		}
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			sb.append(" order by ");
			String comma = "";
			for (String sort : sortArray){
				sb.append(comma);
				sb.append(" udr.").append(sort);
				comma = ",";
			}
		}
		if(offset!=null && length!=null){
			return this.getByHQL(sb.toString(), offset, length);
		}else{
			return this.getByHQL(sb.toString());
		}
	}
}
