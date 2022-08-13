package com.cmos.chinamobile.media.convertor;

import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;
import com.cmos.core.bean.xml.Output;
import com.cmos.core.bean.xml.Parameter;
import com.cmos.core.util.ControlConstants;
import com.cmos.core.util.StringUtil;

/**
 * 转换基类
 * 
 * 
 */
public class BaseConvertor extends com.cmos.core.service.BaseConvertor {
	/**
	 * 获取此次请求的Output对象
	 */
	public Output getOutput(){
		return (Output) ServletActionContext.getContext().get(ControlConstants.OUTPUT);
	}
	
	/**
	 * 将OutputObject按照Output中的key和toKey转化，然后转成Json
	 */
	public String convertor(InputObject inputObject, OutputObject outputObject) {
		this.convertBeanKey(outputObject.getBean());// 转化bean中的key
		this.convertBeansKey(outputObject.getBeans());// 转换beans中的key
		return super.convertor(inputObject, outputObject);
	}
	
	/**
	 * 将OutputObject中bean中的key值，按照Output中的配置转换
	 * 
	 * @param bean 待转换的Map
	 * @return
	 */
	protected void convertBeanKey(Map<String, Object> bean) {
		Output output = this.getOutput();
		if (output == null) {
			return;
		}
		List<Parameter> parameters = output.getParameters();

		for (Parameter p : parameters) {// 遍历列表
			String key = p.getKey();
			String toKey = p.getToKey();
			if (StringUtil.isNotEmpty((String)bean.get(key)) && StringUtil.isNotEmpty(key)
					&& StringUtil.isNotEmpty(toKey)) {// key和toKey和value均不为空
				bean.put(toKey, bean.get(key));
				bean.remove(key);
			}
		}
	}
	
	/**
	 * 将OutputObject中beans中的key值，按照Output中的配置转换
	 * 
	 * @param beans 待转换的List< Map>
	 * @return
	 */
	protected void convertBeansKey(List<Map<String, Object>> beans){
		for (Map<String, Object> bean : beans) {
			this.convertBeanKey(bean);
		}
	}
}
