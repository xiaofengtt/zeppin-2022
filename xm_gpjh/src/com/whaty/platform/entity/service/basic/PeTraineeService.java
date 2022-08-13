package com.whaty.platform.entity.service.basic;

import java.io.File;
import java.util.List;

import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.exception.EntityException;

public interface PeTraineeService {
	
	public AbstractBean save(AbstractBean instance)throws EntityException;

	public List saveList(List list) throws EntityException;
	
	public int saveCertificateNo(File file) throws EntityException;
}
