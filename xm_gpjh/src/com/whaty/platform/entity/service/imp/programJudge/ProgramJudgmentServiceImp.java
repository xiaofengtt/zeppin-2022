package com.whaty.platform.entity.service.imp.programJudge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.PeProApply;
import com.whaty.platform.entity.bean.PeProImplemt;
import com.whaty.platform.entity.bean.PrProSummary;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.imp.GeneralServiceImp;
import com.whaty.platform.entity.service.programJudge.ProgramJudgmentService;
import com.whaty.platform.util.Const;

@SuppressWarnings("unchecked")
public class ProgramJudgmentServiceImp extends GeneralServiceImp<PeProApply> implements ProgramJudgmentService{

	public PeProApply saveMangerFirstCheck(PeProApply bean,boolean isForce) throws EntityException {
		PeProApply peProApply = (PeProApply) this.getGeneralDao().getById(bean.getId());
		if(isForce || peProApply.getEnumConstByFkCheckFinal()==null||"1000".equals(peProApply.getEnumConstByFkCheckFinal().getCode())){
			peProApply.setEnumConstByFkCheckFirst(this.getGeneralDao().getEnumConstByNamespaceCode("FkCheckFirst", bean.getEnumConstByFkCheckFirst().getCode()));
			peProApply.setNoteFirst(bean.getNoteFirst());
			this.getGeneralDao().save(peProApply);
		}else{
			throw new EntityException("保存失败，该项目已经完成终审，无法修改！");
		}
		return peProApply;
	}

	public PeProApply saveMangerFinalCheck(PeProApply bean)
			throws EntityException {
		PeProApply peProApply = (PeProApply) this.getGeneralDao().getById(PeProApply.class,bean.getId());
		peProApply.setNoteFinal(bean.getNoteFinal());
		String classIdentifierString = null;
		
		classIdentifierString = generateClassIdentifier(bean);
		
		if(bean.getEnumConstByFkCheckFinal().getCode().equals("1001")){
			//如果已经生成 则不再保存 替换
			if(peProApply.getClassIdentifier()==null||"".equals(peProApply.getClassIdentifier())){
				peProApply.setClassIdentifier(classIdentifierString);
				associateSsoUser(classIdentifierString);
			}
			//保存项目实施信息，使承担单位可以提交相关材料一
			try {
				DetachedCriteria dcImplemt = DetachedCriteria.forClass(PeProImplemt.class);
				dcImplemt.createAlias("peProApply", "peProApply");
				dcImplemt.add(Restrictions.eq("peProApply", peProApply));
				List list = this.getList(dcImplemt);
				if((list==null)||(list.isEmpty())){
					PeProImplemt peProImplemt = new PeProImplemt();
					peProImplemt.setPeProApply(peProApply);
					this.getGeneralDao().save(peProImplemt);
				}

				//保存项目实施信息，使承担单位可以提交相关材料二
				DetachedCriteria dcSummary = DetachedCriteria.forClass(PrProSummary.class);
				dcSummary.createAlias("peProApplyno", "peProApplyno");
				dcSummary.createAlias("peUnit", "peUnit");
				dcSummary.add(Restrictions.eq("peProApplyno", peProApply.getPeProApplyno()));
				dcSummary.add(Restrictions.eq("peUnit", peProApply.getPeUnit()));
				list = this.getList(dcSummary);
				if((list==null)||(list.isEmpty())){
					PrProSummary prProSummary = new PrProSummary();
					prProSummary.setPeProApplyno(peProApply.getPeProApplyno());
					prProSummary.setPeUnit(peProApply.getPeUnit());
					this.getGeneralDao().save(prProSummary);
				}
			} catch (EntityException e) {
				e.printStackTrace();
				throw new EntityException("保存项目实施信息出错");
			}
		}else if(bean.getEnumConstByFkCheckFinal().getCode().equals("1002")){
			try {
				peProApply.setClassIdentifier(null);
//				paramsMap.put("theIdentifier", "");
//				getGeneralService().executeBySQL(setClassIdentifierSQL, paramsMap);
				String sql = "delete from sso_user u where u.login_id like '"+classIdentifierString.substring(0,11)+"_'";
				this.executeBySQL(sql);
				DetachedCriteria dcImplemt = DetachedCriteria.forClass(PeProImplemt.class);
				dcImplemt.createAlias("peProApply", "peProApply");
				dcImplemt.add(Restrictions.eq("peProApply", peProApply));
				List list = this.getList(dcImplemt);//getByHQL("from PeProImplemt t where t.peProApply='"+peProApply.getId()+"'");
				if((list!=null)&&(!list.isEmpty())){
					PeProImplemt peProImplemt = (PeProImplemt)list.get(0);
					this.getGeneralDao().delete(peProImplemt);
				}
				
//				DetachedCriteria dcSummary = DetachedCriteria.forClass(PrProSummary.class);
//				dcSummary.createAlias("peProApplyno", "peProApplyno");
//				dcSummary.createAlias("peUnit", "peUnit");
//				dcSummary.add(Restrictions.eq("peProApplyno", peProApply.getPeProApplyno()));
//				dcSummary.add(Restrictions.eq("peUnit", peProApply.getPeUnit()));
//				list = this.getList(dcSummary);
//				if((list!=null)&&(!list.isEmpty())){
//					PrProSummary prProSummary = (PrProSummary)list.get(0);
//					this.getGeneralDao().delete(prProSummary);
//				}
			} catch (EntityException e) {
				e.printStackTrace();
				throw new EntityException("操作失败");
			}
			
		}
		peProApply.setEnumConstByFkCheckFinal(this.getGeneralDao().getEnumConstByNamespaceCode("FkCheckFinal", bean.getEnumConstByFkCheckFinal().getCode()));
		this.getGeneralDao().save(peProApply);
		return peProApply;
	}

	public String saveMangerForceCheck(String ids,String value) throws EntityException {
		int count = 0;
		StringBuffer error = new StringBuffer();
		EnumConst firstEnumConst = null;
		EnumConst finalEnumConst = null;
		if (value.equals("1")) {
			firstEnumConst = this.getGeneralDao().getEnumConstByNamespaceCode("FkCheckFirst", "1011");
			finalEnumConst = this.getGeneralDao().getEnumConstByNamespaceCode("FkCheckFinal", "1001");
		}else {
			firstEnumConst = this.getGeneralDao().getEnumConstByNamespaceCode("FkCheckFirst", "1012");
			finalEnumConst = this.getGeneralDao().getEnumConstByNamespaceCode("FkCheckFinal", "1002");
		}
		if (ids != null && ids.length() > 0) {
			for (String id : ids.split(",")) {
				PeProApply bean = (PeProApply) this.getGeneralDao().getById(PeProApply.class,id);
				bean.setEnumConstByFkCheckFirst(firstEnumConst);
				bean.setEnumConstByFkCheckFinal(finalEnumConst);
				try {
					this.saveMangerFirstCheck(bean, true);
					this.saveMangerFinalCheck(bean);
					count ++;
				} catch (EntityException e) {
					error.append("项目"+bean.getPeProApplyno().getName()+" : "+e.getMessage());
				}
			}
		}else {
			throw new EntityException("请至少选择一条记录");
		}
		return ( count > 0 ? "成功设置"+count + "条记录" : "") + (error.length() == 0 ? "" : "以下项目操作失败:"+error);
	}
	
	/**
	 * @throws EntityException 单位、学科及项目标示码不正确时会抛出，评审无法通过
	 * @description 生成登录名（班级标示码）
	 * @return 生成的班级标识符，由年份、单位码、学科友、项目码及一位随机数字组成
	 */
	private String generateClassIdentifier(PeProApply bean) throws EntityException {
		String yearCode = "";

		String getFKsInPeProApplySQL = "select FK_SUBJECT,FK_UNIT,FK_APPLYNO from pe_pro_apply where id=:theId";
		Map<String, String> params = new HashMap<String, String>();
		params.put("theId", bean.getId());
		List FKsList = null;
		try {
			 FKsList = this.getBySQL(getFKsInPeProApplySQL, params);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		String FKToUnit = "";
		String FKToSuject = "";
		String FKToProject = "";
		if(FKsList != null && !FKsList.isEmpty()) {
			Object[] tempObjects = (Object[]) FKsList.get(0);
			FKToUnit = (String) tempObjects[1];
			FKToProject = (String) tempObjects[2];
			FKToSuject = (String) tempObjects[0];
		}
		params.clear();
		String getUnitCodeSQL = "select code from pe_unit where id=:theId";
		String getSujectCodeSQL = "select code from pe_subject where id=:theId";
		String getProjectCodeSQL = "select code,year from pe_pro_applyno where id=:theId";
		String unitCode = "";
		String subjectCode = "";
		String projectCode = "";
		try {
			params.put("theId", FKToUnit);
			unitCode = (String) this.getBySQL(getUnitCodeSQL,params).get(0);
			params.put("theId", FKToSuject);
			subjectCode = (String) this.getBySQL(getSujectCodeSQL,params).get(0);
			params.put("theId", FKToProject);
			Object object =  this.getBySQL(getProjectCodeSQL,params).get(0);
			Object [] obj = (Object[])object;
			projectCode = obj[0].toString();
			yearCode = obj[1].toString().substring(2);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(unitCode.length() != 5) {
			throw new EntityException("单位代码有误,无法生成班级标示码,评审不通过！");
		}
		if(subjectCode.length() < 2) {
			throw new EntityException("学科代码有误,无法生成班级标示码,评审不通过！");
		}
		if(projectCode.length() != 2) {
			throw new EntityException("培训项目代码有误,无法生成班级标示码,评审不通过！");
		}
		String randomCode = Math.round(Math.random()*9)+"";
		String classIdentifier = yearCode + unitCode + subjectCode + projectCode + randomCode;
		return classIdentifier;
	}
	
	/**
	 * @description 保存与项目评审结果相关的SsoUser，方便学员登录
	 * @param classIdentifier 项目终审通过后生成的班级标示码，即SsoUser中的login_id
	 */
	private void associateSsoUser(String classIdentifier) {
		SsoUser ssoUser = new SsoUser();
		ssoUser.setLoginId(classIdentifier);
		ssoUser.setPassword(Const.FIRST_PASSWORD);
		ssoUser.setLoginNum(new Long(1));
		//学员不能修改密码，也不必去修改个人信息
		ssoUser.setCheckedInfo("1");
		ssoUser.setCheckedPw("1");
		
		EnumConst enumConst = this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsvalid", "1");
		ssoUser.setEnumConstByFlagIsvalid(enumConst);
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PePriRole.class);
		detachedCriteria.add(Restrictions.eq("name", "学员"));
		List<PePriRole> roleList = null;
		try {
			roleList = this.getList(detachedCriteria);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		if(roleList != null && !roleList.isEmpty()) {
			ssoUser.setPePriRole(roleList.get(0));
		}
//		this.getGeneralDao().setEntityClass(SsoUser.class);
		this.getGeneralDao().save(ssoUser);
	}
	
	public GeneralDao getGeneralDao() {
		return super.getGeneralDao();
	}

	public void setGeneralDao(GeneralDao generalDao) {
		super.setGeneralDao(generalDao);
	}

}
