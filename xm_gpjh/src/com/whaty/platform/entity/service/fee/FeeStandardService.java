package com.whaty.platform.entity.service.fee;

import com.whaty.platform.entity.exception.EntityException;

public interface FeeStandardService {

	/**
	 * 设置新的默认标准
	 * @param ids
	 * 		所要设置为默认标准的标准
	 * @throws EntityException
	 */
	public abstract void updateSetDefault(java.util.List ids) throws EntityException;
	
}
