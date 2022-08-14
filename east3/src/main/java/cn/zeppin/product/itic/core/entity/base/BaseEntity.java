package cn.zeppin.product.itic.core.entity.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * Entity基础类，可序列化
 * 
 * @author Clark.R 2016-03-28
 * 
 */
public class BaseEntity implements Entity, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3163347966181765533L;

	public interface StatusType {

	}

	public class GerneralStatusType implements StatusType {
		public final static String DISABLE = "disable";
		public final static String NORMAL = "normal";
		public final static String DELETED = "deleted";
	}

	/**
	 * 将Model对象值进行打印，同一个id的实体打印的值相同，可作为Cache的key键
	 */
	@Override
	public String toString() {
		String classname = this.getClass().getName();
		StringBuilder result = new StringBuilder(classname);
		result.append(JSON.toJSONString(this, true));
		return result.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!obj.getClass().equals(BaseEntity.class)) {
			return false;
		}
        return (this.toString().equals(obj.toString()));
    }
	
	 @Override
	 public int hashCode(){
		 return this.toString().hashCode();
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
	
	/**
	 * 浅度克隆，新对象还是同一个内存对象
	 */
	public Entity clone() {
		Entity cloneEntity = null;
		try {
			cloneEntity = (Entity) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return cloneEntity;
	}
	
	/**
	 * 深度克隆，新对象已经是新的内存对象
	 */
	public Object deepClone() throws IOException, ClassNotFoundException {
		// 将对象写到流里
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(this);
		// 从流里读回来
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bis);
		return ois.readObject();
	}
}
