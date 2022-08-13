package com.zixueku.entity;

import java.util.Map;

import android.content.Context;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-3 下午3:35:08
 */
public class Request {

	private int requestUrl; // 请求地址资源ID
	private Map<String, Object> requestData; // 请求参数,可为空
	private Context context; // 调用的上下文
	private Class<?> type; // 需要转化成指定类型bean对象的,需要指定具体的Class Type
	private ParserType parserType; // 解析类型
	private Object objType;

	/**
	 * 包含请求参数的构造方法 结果集直接转化成List或Map对象的构造函数 需指明ParserType 为LIST 或MAP
	 */
	public Request(int requestUrl, Map<String, Object> requestData, Context context, ParserType parserType) {
		super();
		this.requestUrl = requestUrl;
		this.requestData = requestData;
		this.context = context;
		this.parserType = parserType;
	}

	/**
	 * 包含请求参数的构造方法 需要转化成指定类型的bean对象的,需要指定具体的Class Type
	 */
	public Request(int requestUrl, Map<String, Object> requestData, Context context, ParserType parserType, Class<?> type) {
		super();
		this.requestUrl = requestUrl;
		this.requestData = requestData;
		this.context = context;
		this.type = type;
		this.parserType = parserType;
	}

	/**
	 * 包含请求参数的构造方法 需要转化成指定类型的bean对象的,需要指定具体的Class Type
	 */
	public Request(int requestUrl, Map<String, Object> requestData, Context context, Object objType) {
		super();
		this.requestUrl = requestUrl;
		this.requestData = requestData;
		this.context = context;
		this.objType = objType;
		this.parserType = ParserType.USER_DEFINED2;
	}
	
	/**
	 * 包含请求参数的构造方法 需要转化成指定类型的bean对象的,需要指定具体的Class Type
	 */
	public Request(int requestUrl, Context context, Object objType) {
		super();
		this.requestUrl = requestUrl;
		this.context = context;
		this.objType = objType;
		this.parserType = ParserType.USER_DEFINED2;
	}

	/**
	 * 不包含请求参数的构造方法 结果集直接转化成List或Map对象的构造函数 需指明ParserType 为LIST 或MAP
	 */
	public Request(int requestUrl, Context context, ParserType parserType) {
		super();
		this.requestUrl = requestUrl;
		this.context = context;
		this.parserType = parserType;
	}

	/**
	 * 不包含请求参数的构造方法 需要转化成指定类型bean对象的,需要指定具体的Class Type
	 */
	public Request(int requestUrl, Context context, ParserType parserType, Class<?> type) {
		super();
		this.requestUrl = requestUrl;
		this.context = context;
		this.type = type;
		this.parserType = parserType;
	}

	public int getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(int requestUrl) {
		this.requestUrl = requestUrl;
	}

	public Map<String, Object> getRequestData() {
		return requestData;
	}

	public void setRequestData(Map<String, Object> requestData) {
		this.requestData = requestData;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public ParserType getParserType() {
		return parserType;
	}

	public void setParserType(ParserType parserType) {
		this.parserType = parserType;
	}

	public Object getObjType() {
		return objType;
	}

	public void setObjType(Object objType) {
		this.objType = objType;
	}

	@Override
	public String toString() {
		return "GetRequest [requestUrl=" + requestUrl + ", requestData=" + requestData + ", context=" + context + ", type=" + type + ", parserType="
				+ parserType + "]";
	}
}
