package cn.zeppin.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherAdjust;
@SuppressWarnings("rawtypes")
public interface ITeacherAdjustService extends
		IBaseService<TeacherAdjust, Integer> {
	
	public List getRecordsListByParams(Map<String, Object> params, Map<String, Object> sortMap, int offset, int length);
	
	public int getRecordsListByParams(Map<String, Object> params, Map<String, Object> sortMap);

	public void saveMergeSchool(Organization oorg,Organization norg,UserSession us);
	
	public void saveAdjuestTeacher(Teacher teacher, TeacherAdjust ta);
	
	public void updateAdjuestTeacher(Teacher teacher, TeacherAdjust ta);
}
