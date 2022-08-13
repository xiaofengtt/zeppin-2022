/**
 * Copyright (c) 2014, Zeppin All Rights Reserved.
 * cn.zeppin.entity.base
 * BaseEntity
 */
package cn.zeppin.entity.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author Clark
 * 下午6:33:19
 */
public class BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1898657569862129928L;

	public BaseEntity copy() throws IOException, ClassNotFoundException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(this);
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
		return (BaseEntity) ois.readObject();
	}

}
