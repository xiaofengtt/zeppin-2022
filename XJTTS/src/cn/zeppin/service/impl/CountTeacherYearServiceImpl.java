package cn.zeppin.service.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.dao.ICountTeacherYearDao;
import cn.zeppin.entity.CountTeacherYear;
import cn.zeppin.entity.Organization;
import cn.zeppin.service.ICountTeacherYearService;

public class CountTeacherYearServiceImpl extends BaseServiceImpl<CountTeacherYear, Integer> implements ICountTeacherYearService {

	private ICountTeacherYearDao countTeacherYearDao;

	public ICountTeacherYearDao getCountTeacherYearDao() {
		return countTeacherYearDao;
	}

	public void setCountTeacherYearDao(ICountTeacherYearDao countTeacherYearDao) {
		this.countTeacherYearDao = countTeacherYearDao;
	}
	
	@Override
	public List<List<Object[]>> getPoorCountList(Map<String, Object> params) {
		//UserSession us ,String years ,String endYear ,String projectType
		UserSession us = (UserSession) params.get("us");
		String years = params.get("years").toString();
		String endYear = params.get("endYear").toString();
		String projectType = params.get("projectType").toString();
		
		String organizationScode = "1000026124";
		int organizationLevel = 1;
		if(us.getRole()==1){
			organizationScode = us.getOrganizationScode();
			organizationLevel = us.getOrganizationLevel();
		}
		
		if(params.containsKey("parent")){
			Organization o = (Organization) params.get("parent");
			if(o != null){
				organizationScode = o.getScode();
				organizationLevel = o.getOrganizationLevel().getLevel();
			}
		}
		
		return this.countTeacherYearDao.getPoorCountList(organizationScode,organizationLevel,years,endYear,projectType);
	}
	
	@Override
	public List<List<Object[]>> getVillageCountList(Map<String, Object> params) {
		//UserSession us ,String years ,String endYear ,String projectType
		UserSession us = (UserSession) params.get("us");
		String years = params.get("years").toString();
		String endYear = params.get("endYear").toString();
		String projectType = params.get("projectType").toString();
		String organizationScode = "1000026124";
		int organizationLevel = 1;
		if(us.getRole()==1){
			organizationScode = us.getOrganizationScode();
			organizationLevel = us.getOrganizationLevel();
		}
		if(params.containsKey("parent")){
			Organization o = (Organization) params.get("parent");
			if(o != null){
				organizationScode = o.getScode();
				organizationLevel = o.getOrganizationLevel().getLevel();
			}
		}
		return this.countTeacherYearDao.getVillageCountList(organizationScode,organizationLevel,years,endYear,projectType);
	}

	@Override
	public List<Object[]> getYearCountList(UserSession us, String years) {
		String organizationScode = "1000026124";
		if(us.getRole()==1){
			organizationScode = us.getOrganizationScode();
		}
		return this.countTeacherYearDao.getYearCountList(organizationScode,years);
	}

	@Override
	public List<Object[]> getLevelCountList(UserSession us, String years, String endYear) {
		String organizationScode = "1000026124";
		if(us.getRole()==1){
			organizationScode = us.getOrganizationScode();
		}
		return this.countTeacherYearDao.getLevelCountList(organizationScode,years,endYear);
	}

	@Override
	public List<Object[]> getSubjectCountList(UserSession us, String years, String endYear) {
		String organizationScode = "1000026124";
		if(us.getRole()==1){
			organizationScode = us.getOrganizationScode();
		}
		return this.countTeacherYearDao.getSubjectCountList(organizationScode,years,endYear);
	}
	
	@Override
	public List<Object[]> getGradeCountList(UserSession us, String years, String endYear) {
		String organizationScode = "1000026124";
		if(us.getRole()==1){
			organizationScode = us.getOrganizationScode();
		}
		return this.countTeacherYearDao.getGradeCountList(organizationScode,years,endYear);
	}
	
	@Override
	public List<Object[]> getOrganizationCountList(Map<String, Object> params) {
		//UserSession us, String years, String endYear
		UserSession us = (UserSession) params.get("us");
		String years = params.get("years").toString();
		String endYear = params.get("endYear").toString();
		String organizationScode = "1000026124";
		int organizationLevel = 1;
		if(us.getRole()==1){
			organizationScode = us.getOrganizationScode();
			organizationLevel = us.getOrganizationLevel();
		}
		if(params.containsKey("parent")){
			String parent = params.get("parent").toString();
			if(parent != null && !"".equals(parent)){
				String[] str = parent.split("_");
				if(str != null && str.length > 1){
					organizationScode = str[0];
					organizationLevel = Integer.parseInt(str[1]);
				}
			}
			
		}
		return this.countTeacherYearDao.getOrganizationCountList(organizationScode,organizationLevel,years,endYear);
	}
}
