package cn.zeppin.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.CountTeacherYear;

public interface ICountTeacherYearService extends IBaseService<CountTeacherYear, Integer> {
	
	public List<List<Object[]>> getPoorCountList(Map<String, Object> params);
	
	public List<List<Object[]>> getVillageCountList(Map<String, Object> params);
	
	public List<Object[]> getYearCountList(UserSession us ,String years);
	
	public List<Object[]> getLevelCountList(UserSession us ,String years,String endYear);
	
	public List<Object[]> getSubjectCountList(UserSession us ,String years,String endYear);
	
	public List<Object[]> getGradeCountList(UserSession us ,String years,String endYear);
	
	public List<Object[]> getOrganizationCountList(Map<String, Object> params);
}
