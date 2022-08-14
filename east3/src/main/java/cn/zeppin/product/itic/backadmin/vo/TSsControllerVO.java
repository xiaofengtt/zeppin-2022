package cn.zeppin.product.itic.backadmin.vo;

import java.util.ArrayList;
import java.util.List;

import cn.zeppin.product.itic.core.entity.TSsController;
import cn.zeppin.product.itic.core.entity.TSsControllerMethod;
import cn.zeppin.product.itic.core.entity.base.Entity;

public class TSsControllerVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String id;
	private String name;
	private String description;
	private Integer sort;
	private List<TSsControllerMethod> methodList = new ArrayList<>();
	
	public TSsControllerVO(){
		
	}
	
	public TSsControllerVO(TSsController t){
		this.id = t.getId();
		this.name = t.getName();
		this.description = t.getDescription();
		this.sort = t.getSort();
		this.methodList = new ArrayList<>();
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public List<TSsControllerMethod> getMethodList() {
		return methodList;
	}

	public void setMethodList(List<TSsControllerMethod> methodList) {
		this.methodList = methodList;
	}
}
