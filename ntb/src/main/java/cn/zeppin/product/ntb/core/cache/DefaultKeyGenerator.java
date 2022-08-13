package cn.zeppin.product.ntb.core.cache;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.SimpleKeyGenerator;

import com.alibaba.fastjson.JSON;

public class DefaultKeyGenerator extends SimpleKeyGenerator {

	/**
	 * 通过Class、MethodName、params生成缓存靠谱的key值
	 */
	@Override
	public Object generate(Object target, Method method, Object... params) {
		Object key = JSON.toJSONString(new DefaultKeyObject(target, method, params), false);
		return key;
	}
	
	public class DefaultKeyObject {
		private String className;
		private String methodName;
		private Object[] params;
		
		DefaultKeyObject(Object target, Method method, Object... params){
			this.setClassName(target.getClass().getName());
			this.setMethodName(method.getName());
			this.setParams(params);
		}

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		public String getMethodName() {
			return methodName;
		}

		public void setMethodName(String methodName) {
			this.methodName = methodName;
		}

		public Object[] getParams() {
			return params;
		}

		public void setParams(Object[] params) {
			this.params = params;
		}
		
	}
}
