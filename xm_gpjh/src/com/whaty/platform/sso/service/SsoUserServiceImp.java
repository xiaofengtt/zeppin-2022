package com.whaty.platform.sso.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PePriority;
import com.whaty.platform.entity.bean.PeProApplyno;
import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PeValuaExpert;
import com.whaty.platform.entity.bean.PrManProno;
import com.whaty.platform.entity.bean.PrPriRole;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.imp.GeneralServiceImp;
import com.whaty.platform.sso.exception.SsoException;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class SsoUserServiceImp extends GeneralServiceImp<SsoUser> implements SsoUserService{

	public boolean CheckSsoUserByType(SsoUser ssoUser, String loginType) throws SsoException {
		boolean pass = false;
		try{
			if(SsoConstant.SSO_MANAGER.equals(loginType)){//验证管理员身份
				PeManager peManager = null;
				DetachedCriteria dc = DetachedCriteria.forClass(PeManager.class);
				dc.createCriteria("ssoUser", "ssoUser");
				dc.add(Restrictions.eq("ssoUser.loginId", ssoUser.getLoginId()));
				List managers = this.getGeneralDao().getList(dc);
				if(managers !=null && !managers.isEmpty()) {
					peManager = (PeManager)managers.get(0);
					if(SsoConstant.SSO_USER_IS_VALID.equals(peManager.getEnumConstByFlagIsvalid().getCode())){
						pass = true;
					}else{
						throw new SsoException("您的帐号处于无效，不能登陆");
					}
				}
			}
			if(SsoConstant.SSO_VALUEEXPERT.equals(loginType)){//验证评审专家身份
				PeValuaExpert peTeacher = null;
				DetachedCriteria dc = DetachedCriteria.forClass(PeValuaExpert.class);
				dc.createCriteria("ssoUser", "ssoUser");
				dc.add(Restrictions.eq("ssoUser.loginId", ssoUser.getLoginId()));
				List teachers = this.getGeneralDao().getList(dc);
				if(teachers !=null && !teachers.isEmpty()) {
					peTeacher = (PeValuaExpert)teachers.get(0);
					pass = true;
				}
			}
			if(SsoConstant.SSO_STUDENT.equals(ssoUser.getPePriRole().getEnumConstByFlagRoleType().getCode())){//验证学生身份
				pass = false;
				String classIdentifier = ssoUser.getLoginId();
				System.out.println(classIdentifier);
				if(classIdentifier.length()<10){
					throw new SsoException("标识码无效！");
				}
				Map<String, String> paramsMap = new HashMap<String, String>();
				
				String year = classIdentifier.substring(0,2);
				String unitCode = classIdentifier.substring(2,7);
				String subjectCode = classIdentifier.substring(7,9);
				String projectCode = classIdentifier.substring(9,11);
				
				String getClassStudentNumberSQL = "select count(*) from pe_trainee t where t.fk_training_unit=(select max(id) from pe_unit where code=:theUnitCode)" +
						"and t.fk_subject=(select max(id) from pe_subject where code=:theSubjectCode)" +
						"and t.fk_pro_applyno=(select max(id) from pe_pro_applyno where code=:theProjectCode and year=:year )" +
						" and t.FK_GRADUTED=(select max(id) from enum_const where namespace='FkGraduted' and code ='1')";
				paramsMap.clear();
				paramsMap.put("theUnitCode", unitCode);
				paramsMap.put("theSubjectCode", subjectCode);
				paramsMap.put("theProjectCode", projectCode);
				paramsMap.put("year", "20"+year);
				
				List<BigDecimal> decimaList = getGeneralDao().getBySQL(getClassStudentNumberSQL, paramsMap);
				long classStudentNumber = decimaList.get(0).longValue();
				
				String getVotePaperSubmitSuccessNumSQL = "select count(*) from pr_vote_record where class_identifier=:theClassIdentifier";
				paramsMap.clear();
				paramsMap.put("theClassIdentifier", ssoUser.getLoginId());
				decimaList = getGeneralDao().getBySQL(getVotePaperSubmitSuccessNumSQL, paramsMap);
				long votePaperSubmitSucNumber = decimaList.get(0).longValue();
				
				String maxCountSql = "select sv.value from system_variables sv where sv.name= :name";
				paramsMap.clear();
				paramsMap.put("name", "max_count");
				List maxCountlist = this.getGeneralDao().getBySQL(maxCountSql, paramsMap);
				String maxCount = "10";
				if(maxCountlist!=null && !maxCountlist.isEmpty()){
					maxCount = maxCountlist.get(0).toString();
				}
				int maxCountInt = 10;
				try{
					maxCountInt = Integer.parseInt(maxCount);
				}catch(Exception e){
					throw new SsoException("系统变量设置错误，请联系管理员！");
				}
				if((votePaperSubmitSucNumber-maxCountInt) >= classStudentNumber) {
					throw new SsoException("该帐号处投票次数已达上限，您已不能登陆！");
				}
				String countSql = "select count(paper.id) from pe_vote_paper paper inner join enum_const ec on paper.flag_isvalid=ec.id "+
									"inner join pe_pro_applyno applyno on paper.flag_type=applyno.id                                      "+
									"where ec.code='1' and applyno.year=:year and applyno.code=:code                                       ";
				paramsMap.clear();
				paramsMap.put("code", projectCode);
				paramsMap.put("year", "20"+year);
				decimaList = getGeneralDao().getBySQL(countSql, paramsMap);
				long voteCount = decimaList.get(0).longValue();
				if(voteCount==0){
					throw new SsoException("尚未发布调查问卷，请与管理员联系！");
				}
				
				pass = true;
			}
		}catch(RuntimeException e){
//			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new SsoException(e.getMessage());
			
		}
		return pass;
	}

	public boolean checkTrue(SsoUser ssoUser, String loginType ,String trueName ,String cardId){
		boolean pass = false;
			if(SsoConstant.SSO_MANAGER.equals(loginType)){//验证管理员身份
				PeManager peManager = null;
				DetachedCriteria dc = DetachedCriteria.forClass(PeManager.class);
				dc.createCriteria("ssoUser", "ssoUser");
				dc.add(Restrictions.eq("ssoUser.loginId", ssoUser.getLoginId()));
				dc.add(Restrictions.eq("trueName", trueName));
				dc.add(Restrictions.eq("idCard", cardId));
				List managers = this.getGeneralDao().getList(dc);
				if(managers !=null && !managers.isEmpty()) {
					pass = true;
				}
			}
			if(SsoConstant.SSO_VALUEEXPERT.equals(loginType)){//验证评审专家身份
				PeValuaExpert peValuaExpert = null;
				DetachedCriteria dc = DetachedCriteria.forClass(PeValuaExpert.class);
				dc.createCriteria("ssoUser", "ssoUser");
				dc.add(Restrictions.eq("ssoUser.loginId", ssoUser.getLoginId()));
				dc.add(Restrictions.eq("trueName", trueName));
				dc.add(Restrictions.eq("idCard", cardId));
				List managers = this.getGeneralDao().getList(dc);
				if(managers !=null && !managers.isEmpty()) {
					pass = true;
				}
			}if(SsoConstant.SSO_STUDENT.equals(loginType)){//验证学生身份
				PeStudent peStudent = null;
				DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
				dc.createCriteria("ssoUser", "ssoUser");
				dc.add(Restrictions.eq("ssoUser.loginId", ssoUser.getLoginId()));
				DetachedCriteria dcInfo = dc.createCriteria("prStudentInfo", "prStudentInfo");
				dc.add(Restrictions.eq("trueName", trueName));
				dcInfo.add(Restrictions.eq("cardNo", cardId));
				List managers = this.getGeneralDao().getList(dc);
				if(managers !=null && !managers.isEmpty()) {
					pass = true;
				}
			}
		return pass;
	}
	
	public void saveNewPassword(SsoUser ssoUser,String passwd){
		ssoUser.setPassword(passwd);
		this.getGeneralDao().save(ssoUser);
	}
	
	public SsoUser getByLoginId(String loginId) throws EntityException{
		final String getByLoginId = "from SsoUser u where u.loginId = '"+loginId+"'";
		List<SsoUser> ssoUsers = this.getGeneralDao().getByHQL(getByLoginId);
		if(ssoUsers !=null && !ssoUsers.isEmpty()){
			return ssoUsers.get(0);
		}else{
			return null;
		}
	}

	public UserSession getUserSession(SsoUser user, String loginType) throws SsoException{
		UserSession us = new UserSession();
		us.setSsoUser(user);
		us.setId(user.getId());
		us.setLoginId(user.getLoginId());
		us.setRoleId(user.getPePriRole() != null ? user.getPePriRole().getId() : null);

		us.setUserLoginType(loginType);
		String getUserNameSQL = null;
		if(loginType.equals(SsoConstant.SSO_STUDENT)) {
			getUserNameSQL = "select name from pe_trainee where login_id=:theLoginId";
		}else if(loginType.equals(SsoConstant.SSO_MANAGER)){
			getUserNameSQL = "select name from pe_manager where login_id=:theLoginId";
			us.setRoleType(getCurrentUserRoleEnumCode(user));
			if(us.getRoleId()!=null && "4".equals(us.getRoleType()) && !us.getRoleId().equals("402880a92137be1c012137db62100007") && !us.getRoleId().equals("402880a92137be1c012137db62100008")){ //班主任项目
				us.setRoleType("5");
			}
		}else if(loginType.equals(SsoConstant.SSO_VALUEEXPERT)) {
			getUserNameSQL = "select name from pe_valua_expert where login_id=:theLoginId";
		}
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("theLoginId", user.getLoginId());
		List<String> result = null;
		result = getGeneralDao().getBySQL(getUserNameSQL, paramsMap);
		try {
			us.setUserName(result.get(0));
		}catch (Exception e) {
		}
		
		//设置权限 
		Map userPriorityMap = new HashMap();
		if(user.getPePriRole()!= null){
			userPriorityMap = getUserPriority(user);
		}else{
			throw new SsoException("没有为用户设置角色!");
		}
		us.setUserPriority(userPriorityMap);
		
		//设置权限范围;
		DetachedCriteria dcPro = DetachedCriteria.forClass(PrManProno.class);
		dcPro.createCriteria("peManager", "peManager")
				.createAlias("ssoUser", "ssoUser")
				.add(Restrictions.eq("ssoUser.id", user.getId()));
		dcPro.createAlias("peProApplyno", "peProApplyno");
		List<PrManProno> prManPronoList = getGeneralDao().getList(dcPro);
		List<PeProApplyno> proList = new ArrayList<PeProApplyno>();
		if(prManPronoList != null && !prManPronoList.isEmpty()){
			Iterator<PrManProno> it = prManPronoList.iterator();
			while(it.hasNext()){
				PrManProno prPro = (PrManProno)it.next();
				proList.add(prPro.getPeProApplyno());
			}
			us.setPriPros(proList); 
		}

//		//	设置企业管理员权限范围;
//		DetachedCriteria dcEnterprise = DetachedCriteria.forClass(PrBzzPriManagerEnterprise.class)
//										.createAlias("ssoUser", "ssoUser")
//										.createAlias("peEnterprise", "peEnterprise");
//		dcEnterprise.add(Restrictions.eq("ssoUser.id", user.getId()));
//		List priMananerEnterpriseList = getGeneralDao().getList(dcEnterprise);
//		List enterpriseList = new ArrayList();
//		if(priMananerEnterpriseList != null && !priMananerEnterpriseList.isEmpty()){
//			Iterator it = priMananerEnterpriseList.iterator();
//			while(it.hasNext()){
//				PrBzzPriManagerEnterprise prenterprise = (PrBzzPriManagerEnterprise)it.next();
//				enterpriseList.add(prenterprise.getPeEnterprise());
//			}
//			us.setPriEnterprises(enterpriseList); 
//		}
		
		return us;
	}
	
	// 获取当前登录用户对应角色在常量表中的特征码
	public String getCurrentUserRoleEnumCode(SsoUser currentUser) {
		String getCurrentUserRoleEnumCodeSQL = "select e.code from sso_user s,pe_pri_role r,enum_const e where s.fk_role_id=r.id and r.flag_role_type=e.id and s.id=:theCurrentUserId";
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("theCurrentUserId",currentUser.getId());
		List<String> codeList = getGeneralDao().getBySQL(getCurrentUserRoleEnumCodeSQL, paramsMap);
		String code = "";
		if(codeList != null && codeList.size() != 0) {
			code = codeList.get(0);
		}
		return code;
	}
	
	/**
	 *  获得用户权限
	 */
	public Map<String, PePriority> getUserPriority(SsoUser user){
		return getUserPriority(user.getPePriRole().getId());
	}
	
	/**
	 *  根据角色id获得用户权限
	 */
	public Map<String, PePriority> getUserPriority(String roleId){
		Map<String, PePriority> userPriority = new HashMap<String, PePriority>();
		
		DetachedCriteria dc = DetachedCriteria.forClass(PrPriRole.class);
		dc.createAlias("pePriRole", "pePriRole");
		dc.createAlias("pePriority", "pePriority");
		dc.add(Restrictions.eq("pePriRole.id", roleId)).add(Restrictions.eq("flagIsvalid", "1"));
		List<PrPriRole> list = this.getGeneralDao().getList(dc);
		
		if(list != null && !list.isEmpty()){ //权限不为空则取出权限放入map中;
			Iterator<PrPriRole> it = list.iterator();
			while(it.hasNext()){
				PrPriRole prRole = it.next();
				PePriority priority = prRole.getPePriority();
				userPriority.put(priority.getNamespace()+"/"+priority.getAction()+"_"+priority.getMethod()+".action", priority);
				userPriority.put(priority.getNamespace()+"/"+priority.getAction()+"_execute.action", priority);
				if("abstractList".equals(priority.getMethod())) {
					userPriority.put(priority.getNamespace()+"/"+priority.getAction()+"_abstractExcel.action", priority);
				}
			}
		}
		return userPriority;
	}

	public PeSitemanager getPeSitemanager(String loginId) {
		return this.getGeneralDao().getPeSitemanager(loginId);
	}

	public PeEnterpriseManager getEnterprisemanager(String loginId) {
		return this.getGeneralDao().getEnterprisemanager(loginId);
	}
}
