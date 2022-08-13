package com.whaty.platform.entity.service.imp.recruit.recmanage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrEduMajorSiteFeeLevel;
import com.whaty.platform.entity.bean.PrStudentInfo;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.recruit.recmanage.RecruitManageMatriculatService;

public class RecruitManageMatriculatServiceImp implements
		RecruitManageMatriculatService {
	private GeneralDao generalDao;
	public String saveMatriculat(List<PeRecStudent> peRecStudentList,
			String value) throws EntityException {
		 Map<String,Integer> matNo = new HashMap<String, Integer>();
		String msg = "";
		int i = 0;
		this.getGeneralDao().setEntityClass(EnumConst.class);
		EnumConst enumConstByFlagMatriculate = (EnumConst) this.getGeneralDao()
				.getById(value);
		for (PeRecStudent peRecStudent : peRecStudentList) {
			PrStudentInfo prStudentInfo = new PrStudentInfo();
			prStudentInfo.setAddress(peRecStudent.getAddress());
			prStudentInfo.setBirthday(peRecStudent.getBirthday());
			prStudentInfo.setCardNo(peRecStudent.getCardNo());
			prStudentInfo.setCardType(peRecStudent.getCardType());
			prStudentInfo.setCity(peRecStudent.getCity());
			prStudentInfo.setEmail(peRecStudent.getEmail());
			prStudentInfo.setFork(peRecStudent.getFolk());
			prStudentInfo.setGender(peRecStudent.getGender());
			prStudentInfo.setGraduateCode(peRecStudent.getGraduateCode());
			prStudentInfo.setGraduateSchool(peRecStudent.getGraduateSchool());
			prStudentInfo.setGraduateYear(peRecStudent.getGraduateDate());
			prStudentInfo.setMarriage(peRecStudent.getMarriage());
			prStudentInfo.setMobilephone(peRecStudent.getMobilephone());
			prStudentInfo.setOccupation(peRecStudent.getOccupation());
			prStudentInfo.setPhone(peRecStudent.getPhone());
			prStudentInfo.setProvince(peRecStudent.getProvince());
			prStudentInfo.setWorkplace(peRecStudent.getWorkplace());
			prStudentInfo.setXueli(peRecStudent.getXueli());
			prStudentInfo.setZip(peRecStudent.getZip());
			prStudentInfo.setZzmm(peRecStudent.getZzmm());
			this.getGeneralDao().setEntityClass(PrStudentInfo.class);
			prStudentInfo = (PrStudentInfo) this.getGeneralDao().save(
					prStudentInfo);

			PeStudent peStudent = new PeStudent();
			peStudent.setName(peRecStudent.getCardNo()+"/"+peRecStudent.getName());
			peStudent.setPrStudentInfo(prStudentInfo);
			peStudent.setPeRecStudent(peRecStudent);
			peStudent.setTrueName(peRecStudent.getName());
			peStudent
					.setPeGrade(peRecStudent.getPrRecPlanMajorSite()
							.getPrRecPlanMajorEdutype().getPeRecruitplan()
							.getPeGrade());
			peStudent.setPeEdutype(peRecStudent.getPrRecPlanMajorSite()
					.getPrRecPlanMajorEdutype().getPeEdutype());
			peStudent.setPeSite(peRecStudent.getPrRecPlanMajorSite()
					.getPeSite());
			peStudent.setPeMajor(peRecStudent.getPrRecPlanMajorSite()
					.getPrRecPlanMajorEdutype().getPeMajor());
			peStudent.setEnumConstByFlagMajorType(peRecStudent.getEnumConstByFlagMajorType());
			peStudent.setFeeBalance(0.00);
			peStudent.setFeeInactive(0.00);

			DetachedCriteria dcFlagStudentStatus = DetachedCriteria
					.forClass(EnumConst.class);
			dcFlagStudentStatus.add(Restrictions.eq("namespace",
					"FlagStudentStatus"));
			dcFlagStudentStatus.add(Restrictions.eq("code", "0"));
			List enumList = this.getGeneralDao().getList(dcFlagStudentStatus);
			peStudent.setEnumConstByFlagStudentStatus((EnumConst) enumList
					.get(0));
			peStudent.setEnumConstByFlagDisobey(
					this.getGeneralDao().getEnumConstByNamespaceCode("FlagDisobey", "0"));
			DetachedCriteria dcPrFee = DetachedCriteria
					.forClass(PrEduMajorSiteFeeLevel.class);
			DetachedCriteria dcSite = dcPrFee
					.createCriteria("peSite", "peSite").add(
							Restrictions
									.eq("id", peStudent.getPeSite().getId()));
			DetachedCriteria dcEdutype = dcPrFee.createCriteria("peEdutype",
					"peEdutype").add(
					Restrictions.eq("id", peStudent.getPeEdutype().getId()));
			DetachedCriteria dcMajor = dcPrFee.createCriteria("peMajor",
					"peMajor").add(
					Restrictions.eq("id", peStudent.getPeMajor().getId()));
			this.getGeneralDao().setEntityClass(PrEduMajorSiteFeeLevel.class);
			List<PrEduMajorSiteFeeLevel> list = this.getGeneralDao().getList(
					dcPrFee);
			if (list.size() > 0) {
				PrEduMajorSiteFeeLevel prEduMajorSiteFeeLevel = list.get(0);

				peStudent.setPeFeeLevel(prEduMajorSiteFeeLevel.getPeFeeLevel());
			} else {
				msg += "学号为" + peRecStudent.getCardNo()
						+ "的学生，未找到相关的学费标准!无法录取!</br>";
			}

			this.getGeneralDao().save(peStudent);
			peRecStudent
					.setEnumConstByFlagMatriculate(enumConstByFlagMatriculate);
			
			Integer a = matNo.get(peRecStudent.getPrRecPlanMajorSite().getPeSite().getName());
			if(a==null){
			//设置录取号
				int max = this.getMatriculatNum(peRecStudent);
				matNo.put(peRecStudent.getPrRecPlanMajorSite().getPeSite().getName(), max);
				peRecStudent.setMatriculateNum(peRecStudent.getPrRecPlanMajorSite().getPrRecPlanMajorEdutype().getPeRecruitplan().getCode()+peRecStudent.getPrRecPlanMajorSite().getPeSite().getCode()+max);
			}else{
				int max = a+1;
//				matNo.remove(peRecStudent.getPrRecPlanMajorSite().getPeSite().getName());
				matNo.put(peRecStudent.getPrRecPlanMajorSite().getPeSite().getName(), max);
				peRecStudent.setMatriculateNum(peRecStudent.getPrRecPlanMajorSite().getPrRecPlanMajorEdutype().getPeRecruitplan().getCode()+peRecStudent.getPrRecPlanMajorSite().getPeSite().getCode()+max);
			}
			this.getGeneralDao().save(peRecStudent);
			i++;
		}
		msg += "共成功录取" + i + "个学生。</br>";
		return msg;
	}

	/**
	 * 根据这个学习中心的最大顺序号，生成录取号  批次号+学习中心编号+四位顺序号
	 * @param peRecStudent
	 * @return
	 */
	
	private int getMatriculatNum(PeRecStudent peRecStudent){
		String matriculatNum = "";
		StringBuffer sql_temp = new StringBuffer();
		sql_temp.append("  select peRecStudent.Matriculate_Num							");	
		sql_temp.append("    from pe_rec_student            peRecStudent,               ");
		sql_temp.append("         PR_REC_PLAN_MAJOR_SITE    recSite,                    ");
		sql_temp.append("         pe_site                   peSite,                     ");
		sql_temp.append("         PR_REC_PLAN_MAJOR_EDUTYPE recEdutype,                 ");
		sql_temp.append("         PE_RECRUITPLAN            recruitplan,                ");
		sql_temp.append("         enum_const                enumConst                   ");
		sql_temp.append("   where peRecStudent.Fk_Rec_Major_Site_Id = recSite.Id        ");
		sql_temp.append("     and recSite.Fk_Site_Id = peSite.Id                        ");
		sql_temp.append("     and recSite.Fk_Rec_Plan_Major_Edutype_Id = recEdutype.Id  ");
		sql_temp.append("     and recEdutype.Fk_Recruitplan_Id = recruitplan.id         ");
		sql_temp.append("     and recruitplan.flag_active = '1'                         ");
		sql_temp.append("     and peRecStudent.Flag_Matriculate = enumConst.Id          ");
		sql_temp.append("     and enumConst.Code = '1'                                  ");
		sql_temp.append("     and peSite.name = '"+peRecStudent.getPrRecPlanMajorSite().getPeSite().getName()+"'                ");
		sql_temp.append("     and peRecStudent.Matriculate_Num is not null				");		
		List<String>  numList = this.getGeneralDao().getBySQL(sql_temp.toString());
		int max = 1000;
		if(numList==null||numList.size()==0){
//			matriculatNum = peRecStudent.getPrRecPlanMajorSite().getPrRecPlanMajorEdutype().getPeRecruitplan().getCode()+peRecStudent.getPrRecPlanMajorSite().getPeSite().getCode()+"1001";
		} else{
			//求最大顺序号
			for (String string : numList) {
				String num = string.substring(string.length()-4, string.length());
				int n;
				try {
					n = Integer.parseInt(num);
				} catch (Exception e) {
					e.printStackTrace();
					n = 1000;
				}
				if(n>max){
					max = n;
				}
			}
			
		}
		max++;
//		matriculatNum = peRecStudent.getPrRecPlanMajorSite().getPrRecPlanMajorEdutype().getPeRecruitplan().getCode()+peRecStudent.getPrRecPlanMajorSite().getPeSite().getCode()+(max+1);
		return max;
	}
	public String deleMatriculat(List<String> idList, String column,
			String value) throws EntityException {
		DetachedCriteria dcPeStudent = DetachedCriteria
				.forClass(PeStudent.class);
		dcPeStudent.createCriteria("peRecStudent", "peRecStudent").add(
				Restrictions.in("id", idList));
		this.getGeneralDao().setEntityClass(PeStudent.class);
		List<PeStudent> peStudentList = this.getGeneralDao().getList(
				dcPeStudent);

		int i = 0;
		this.getGeneralDao().setEntityClass(PeRecStudent.class);
		this.getGeneralDao().updateColumnByIds(idList, column, value);
		//将录取号清空
		this.getGeneralDao().updateColumnByIds(idList, "matriculateNum", "");
		this.getGeneralDao().setEntityClass(PeStudent.class);
		for (PeStudent peStudent : peStudentList) {
			this.getGeneralDao().delete(peStudent);
			i++;
		}
		return "共成功操作" + i + "条记录";
	}

	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

}
