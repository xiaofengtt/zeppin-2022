/**
 * 
 */
package cn.zeppin.product.ntb.core.cache;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;


/**
 * @author elegantclack
 *
 */
public class DefaultKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9078858603361710781L;

	
	/** 调用目标对象全类名 */
	private String targetClassName;
	/** 调用目标方法名称 */
	private String methodName;
	/** 调用目标参数 */
	private Object[] params;
	
	private final int hashCode;

	public DefaultKey(Object target, Method method, Object[] elements) {
		this.targetClassName = target.getClass().getName();
		this.methodName = generatorMethodName(method);
		if(elements != null && elements.length > 0){
			this.params = new Object[elements.length];
			for(int i =0;i<elements.length;i++){
				this.params[i] = elements[i];
			}
		}
		this.hashCode = generatorHashCode();
	}

	private String generatorMethodName(Method method){
		StringBuilder builder = new StringBuilder(method.getName());
		Class<?>[] types = method.getParameterTypes();
		if(types != null && types.length > 0){
			builder.append("(");
			for(Class<?> type:types){
				String name = type.getName();
				builder.append(name+",");
			}
			builder.append(")");
		}
		return builder.toString();
	}

	//生成hashCode
	private int generatorHashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hashCode;
		result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
		result = prime * result + Arrays.hashCode(params);
		result = prime * result + ((targetClassName == null) ? 0 : targetClassName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		DefaultKey other = (DefaultKey) obj;
		if (hashCode != other.hashCode) {
			return false;
		}
		if (methodName == null) {
			if (other.methodName != null) {
				return false;
			}
		} 
		else if (!methodName.equals(other.methodName)){
			return false;
		}
		if (!Arrays.equals(params, other.params)){
			return false;
		}
		if (targetClassName == null) {
			if (other.targetClassName != null) {
				return false;
			}
		} else if (!targetClassName.equals(other.targetClassName)) {
			return false;
		}
		return true;
	}

	@Override
	public final int hashCode() {
		return hashCode;
	}

}
