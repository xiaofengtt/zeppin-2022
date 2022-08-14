package com.cmos.china.mobile.media.core.bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;

import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.core.util.BeanUtil;

/**
 * Java基类
 */
public class Entity implements Serializable {
	private static final long serialVersionUID = -9131680166367906057L;
	private static final Logger logger = LoggerFactory.getUtilLog(Entity.class);
	
	public String toString() {
		Map<String, Object> map = BeanUtil.convertBean2Map(this);
		return map.toString();
	}

	public interface StatusType {

	}

	public class GerneralStatusType implements StatusType {
		public final static String STOPPED = "stopped";
		public final static String NORMAL = "normal";
		public final static String DELETED = "deleted";
	}
	
	/**
	 * 深度克隆
	 * 
	 * @return 克隆的类
	 */
	@SuppressWarnings("unchecked")
	public <T> T deepClone() {
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(this);
			ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
			ObjectInputStream oi = new ObjectInputStream(bi);
			// Return Target Object
			return (T) oi.readObject();
		} catch (Exception e) {
			logger.error("deepClone", "", e);
			return null;
		}
	}
}
