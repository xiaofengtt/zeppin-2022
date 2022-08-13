package cn.zeppin.dao;

import java.util.List;

import cn.zeppin.entity.CountTeacherYear;

public interface ICountTeacherYearDao extends IBaseDao<CountTeacherYear, Integer> {
	
	public List<List<Object[]>> getPoorCountList(String organizationScode,Integer organizationLevel,String years ,String endYear ,String projectType);
	
	public List<List<Object[]>> getVillageCountList(String organizationScode,Integer organizationLevel,String years ,String endYear ,String projectType);

	public List<Object[]> getYearCountList(String organizationScode,String years);
	
	public List<Object[]> getLevelCountList(String organizationScode,String years,String endYear);
	
	public List<Object[]> getSubjectCountList(String organizationScode,String years,String endYear);
	
	public List<Object[]> getGradeCountList(String organizationScode,String years,String endYear);
	
	public List<Object[]> getOrganizationCountList(String organizationScode, Integer organizationLevel,String years,String endYear);
}
