package cn.zeppin.entity.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import cn.zeppin.access.Navigation;
import cn.zeppin.entity.Category;
import cn.zeppin.entity.Funcation;
import cn.zeppin.entity.National;
import cn.zeppin.entity.Resource;
import cn.zeppin.entity.ResourceCustomTag;
import cn.zeppin.entity.ResourceTag;
import cn.zeppin.entity.User;
import cn.zeppin.utility.Utlity;

public class SerializeEntity {

	/**
	 * 用户
	 */
	public static Map<String, Object> user2Map(User user) {
		return user2Map(user, ".");
	}
	
	public static Map<String, Object> user2Map(User user, String split) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", user.getId());
		result.put("role" + split + "id", user.getRole().getId());
		result.put("role" + split + "name", user.getRole().getName());
		result.put("email", user.getEmail());
		result.put("phone", user.getPhone());
		result.put("name", user.getName());
		result.put("status", user.getStatus());
		result.put("job", user.getJob());
		result.put("company", user.getCompany());
		return result;
	}

	public static Map<String, Object> national2Map(National national) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", national.getId());
		result.put("name", national.getName());
		return result;
	}
	
	/**
	 * 分类
	 */
	public static Map<String, Object> category2Map(Category category) {
		return category2Map(category, ".");
	}
	
	public static Map<String, Object> category2Map(Category category, String split) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", category.getId());
		result.put("name", category.getName());
		result.put("level", category.getLevel());
		if(category.getParent()!=null){
			result.put("parent" + split + "id", category.getParent().getId());
			result.put("parent" + split + "name", category.getParent().getName());
		}else{
			result.put("parent" + split + "id", "");
			result.put("parent" + split + "name", "");
		}
		result.put("scode", category.getScode());
		result.put("status", category.getStatus());
		return result;
	}
	
	/**
	 * 功能
	 */
	public static Map<String, Object> funcation2Map(Funcation func) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", func.getId());
		result.put("name", func.getName());
		result.put("path", func.getPath());
		return result;
	}

	public static Navigation funcation2Navigation(Funcation func) {
		Navigation nv = new Navigation();
		nv.setId(func.getId());
		nv.setName(func.getName());
		nv.setLevel(func.getLevel());
		nv.setPath(func.getPath());

		return nv;
	}

	/**
	 * 资源
	 */
	public static Map<String, Object> resource2Map(Resource resource) {
		String path = ServletActionContext.getRequest().getScheme() + "://" + ServletActionContext.getRequest().getServerName() + ":" + ServletActionContext.getRequest().getServerPort() + ServletActionContext.getRequest().getContextPath();
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", resource.getId());
		result.put("title", resource.getTitle());
		result.put("level", resource.getLevel());
		if(resource.getParent() != null){
			result.put("parentId", resource.getParent().getId());
			result.put("parentName", resource.getParent().getTitle());
			result.put("parentUrl", path +"/"+ resource.getParent().getUrl());
		}else{
			result.put("parentId","");
			result.put("parentName", "");
			result.put("parentSource", "");
		}
		if(resource.getCategory() != null){
			result.put("categoryId", resource.getCategory().getId());
			result.put("categoryName", resource.getCategory().getName());
		}else{
			result.put("categoryId", "");
			result.put("categoryName", "");
		}
		result.put("ownerId", resource.getOwner().getId());
		result.put("ownerName", resource.getOwner().getName());
		result.put("createtime", Utlity.timeSpanToString(resource.getCreatetime()));
		result.put("scode", resource.getScode());
		result.put("comment", resource.getComment() == null ? "" : resource.getComment());
		if(resource.getNational() != null){
			result.put("nationalId", resource.getNational().getId());
			result.put("nationalName", resource.getNational().getName());
		}else{
			result.put("nationalId", "");
			result.put("nationalName", "");
		}
		result.put("meaning", resource.getMeaning() == null ? "" : resource.getMeaning());
		result.put("type", resource.getType());
		result.put("size", resource.getSize());
		result.put("sizeKB", Integer.valueOf((resource.getSize()/1024)+""));
		result.put("ratio", resource.getRatio() == null ? "" : resource.getRatio());
		String[] length = resource.getRatio().split("x");
		if(length.length == 2){
			result.put("width", length[0]);
			result.put("height", length[1]);
		}else{
			result.put("width", 0);
			result.put("height", 0);
		}
		result.put("source", resource.getSource());
		if(resource.getSource() == 1){
			result.put("sourceCN","其他");
		}else if(resource.getSource() == 2){
			result.put("sourceCN","来自互联网");
		}else if(resource.getSource() == 3){
			result.put("sourceCN","现场拍照");
		}else if(resource.getSource() == 4){
			result.put("sourceCN","出版物扫描");
		}
		result.put("sourcePath", resource.getSourcePath() == null ? "" : resource.getSourcePath());
		result.put("sourceTime", resource.getSourceTime() == null ? "" : resource.getSourceTime());
		result.put("url", path +"/"+ resource.getUrl());
		result.put("eminent", resource.getEminent());
		result.put("isObject", resource.getIsObject());
		result.put("status", resource.getStatus());
		if(resource.getStatus() == 1){
			result.put("statusCN","未审核");
		}else if(resource.getStatus() == 2){
			result.put("statusCN","审核通过");
		}else if(resource.getStatus() == 3){
			result.put("statusCN","审核未通过");
		}else if(resource.getStatus() > 3){
			result.put("statusCN","图片处理中");
		}
		List<Map<String, Object>> tagList = new ArrayList<Map<String, Object>>();
		if(resource.getTagList().size()>0){
			for(ResourceTag tag : resource.getTagList()){
				Map<String, Object> tagMap = resourceTag2Map(tag);
				tagList.add(tagMap);
			}
		}
		result.put("tags",tagList);
		List<Map<String, Object>> customTagList = new ArrayList<Map<String, Object>>();
		if(resource.getCustomTagList().size()>0){
			for(ResourceCustomTag customTag : resource.getCustomTagList()){
				Map<String, Object> customTagMap = resourceCustomTag2Map(customTag);
				customTagList.add(customTagMap);
			}
		}
		result.put("customTags",customTagList);
		List<Map<String, Object>> childrenList = new ArrayList<Map<String, Object>>();
		if(resource.getChildResource().size()>0){
			for(Resource childResource : resource.getChildResource()){
				Map<String, Object> childMap = new HashMap<String, Object>();
				childMap.put("id", childResource.getId());
				childMap.put("title", childResource.getTitle());
				childMap.put("url", childResource.getUrl());
				childrenList.add(childMap);
			}
		}
		result.put("children",childrenList);
		return result;
	}

	/**
	 * 资源标签
	 */
	public static Map<String, Object> resourceTag2Map(ResourceTag resourceTag) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", resourceTag.getId());
		result.put("tag", resourceTag.getTag());
		result.put("userId", resourceTag.getUser().getId());
		result.put("userName", resourceTag.getUser().getName());
		result.put("resourceId", resourceTag.getResource().getId());
		result.put("resourceTitle", resourceTag.getResource().getTitle());
		return result;
	}
	
	/**
	 * 自定义属性
	 */
	public static Map<String, Object> resourceCustomTag2Map(ResourceCustomTag resourceCustomTag) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", resourceCustomTag.getId());
		result.put("name", resourceCustomTag.getName());
		result.put("value", resourceCustomTag.getValue());
		result.put("userId", resourceCustomTag.getUser().getId());
		result.put("userName", resourceCustomTag.getUser().getName());
		result.put("resourceId", resourceCustomTag.getResource().getId());
		result.put("resourceTitle", resourceCustomTag.getResource().getTitle());
		return result;
	}
}
