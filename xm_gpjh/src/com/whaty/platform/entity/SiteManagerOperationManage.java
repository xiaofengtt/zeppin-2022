package com.whaty.platform.entity;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;

public abstract class SiteManagerOperationManage {

	public abstract int getStudentsNum(String id,String reg_no,String name,String id_card,String phone,String major_id,String edu_type_id,String grade_id) throws PlatformException;
	
	public abstract List getStudents(Page page,String id,String reg_no,String name,String id_card,String phone,String major_id,String edu_type_id,String grade_id)throws PlatformException;
}
