package com.whaty.platform.standard.aicc.operation;

import com.whaty.platform.standard.aicc.file.CRSData;

public abstract class AiccCourse extends CRSData{

	public abstract int add();
	public abstract int update();
	public abstract int delete();
	
	public abstract boolean isExist();
	
}
