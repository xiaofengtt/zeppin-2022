package cn.zeppin.project.chinamobile.media.core.entity.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Entity基础类，可序列化
 * 
 * @author Clark.R 2016-03-28
 * 
 */
public class BaseEntity implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3163347966181765533L;

	public interface StatusType {

	}

	public class GerneralStatusType implements StatusType {
		public final static String STOPPED = "stopped";
		public final static String NORMAL = "normal";
		public final static String DELETED = "deleted";
	}

	/**
	 * 将Model对象值进行打印，同一个id的实体打印的值相同，可作为Cache的key键
	 */
	@Override
	public String toString() {
		String classname = this.getClass().getName();
		String result = "";
		String methodName = "getId";
		try {
			Method method = this.getClass().getMethod(methodName, new Class[] {});
			if (method != null) {
				Object value = method.invoke(this, new Object[] {});
				if (value != null) {
					result = new StringBuilder().append(classname).append("@id=").append(value.toString()).toString();
				} else {
					result = super.toString();
				}
			} else {
				result = super.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 将Map转换成POJO Bean
	 * @param t
	 * @param params
	 */
	public static <T extends Entity> void flushObject(T t, Map<String, Object> params) {
		if (params == null || t == null) {
			return;
		}
		Class<?> clazz = t.getClass();
		for (; clazz != Entity.class; clazz = clazz.getSuperclass()) {
			try {
				Field[] fields = clazz.getDeclaredFields();

				for (int i = 0; i < fields.length; i++) {
					String name = fields[i].getName(); // 获取属性的名字

					Object value = params.get(name);
					if (value != null && !"".equals(value)) {
						// 注意下面这句，不设置true的话，不能修改private类型变量的值
						fields[i].setAccessible(true);
						fields[i].set(t, value);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
