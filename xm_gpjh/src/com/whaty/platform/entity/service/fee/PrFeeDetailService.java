package com.whaty.platform.entity.service.fee;

import java.io.File;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.exception.EntityException;

public interface PrFeeDetailService {
	
	/**
	 * 导入交费信息
	 * @param file
	 * @param feeType
	 * @return
	 * @throws EntityException
	 */
	public int save_upload(File file,EnumConst feeType) throws EntityException;
	
	/**
	 * 导入交费信息
	 * @param file
	 * @param feeType
	 * @return
	 * @throws EntityException
	 */
	public int save_uploadInvoiceNo(File file) throws EntityException;
}
