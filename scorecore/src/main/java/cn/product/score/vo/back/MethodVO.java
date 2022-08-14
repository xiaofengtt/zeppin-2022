package cn.product.score.vo.back;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.product.score.entity.Method;

public class MethodVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1307642330847921969L;
	
	private String uuid;
	private String name;
	private String url;
	private Integer level;
	private String parent;
	private Integer sort;
	
	private List<MethodVO> children = new ArrayList<>();
	
	public MethodVO(){
		
	}
	
	public MethodVO(Method method){
		this.uuid = method.getUuid();
		this.name = method.getName();
		this.url = method.getUrl();
		this.level = method.getLevel();
		this.parent = method.getParent();
		this.sort = method.getSort();
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public List<MethodVO> getChildren() {
		return children;
	}

	public void setChildren(List<MethodVO> children) {
		this.children = children;
	}
}
