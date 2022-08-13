package com.zixueku.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 类说明 试题选项信息---试题选项---正确答案---试题题干
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-4-8 下午12:11:48
 */
public class Data implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7964557650268129839L;
	private String stem;    //题干
	private List<Option> options;   //选项
	private List<Result> results;    //正确答案

	public String getStem() {
		return stem;
	}

	public void setStem(String stem) {
		this.stem = stem;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "Data [stem=" + stem + ", options=" + options + ", results=" + results + "]";
	}
	
}
