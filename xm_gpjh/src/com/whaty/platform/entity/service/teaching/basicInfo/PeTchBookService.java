package com.whaty.platform.entity.service.teaching.basicInfo;

import java.io.File;
import java.util.List;

import com.whaty.platform.entity.exception.EntityException;

public interface PeTchBookService {
	
	public int save_uploadBook(File file) throws EntityException;
	
}
