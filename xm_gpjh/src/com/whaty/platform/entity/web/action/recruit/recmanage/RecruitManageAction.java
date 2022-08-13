package com.whaty.platform.entity.web.action.recruit.recmanage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.recruit.recmanage.RecruitManageMatriculatService;
import com.whaty.platform.entity.util.DateConvertor;
import com.whaty.platform.entity.web.action.first.PeRecStudentBaseAction;
import com.whaty.platform.util.Const;

/**
 * 录取状态修改action
 * 
 * @author 李冰
 * 
 */
public class RecruitManageAction extends PeRecStudentBaseAction {
	RecruitManageMatriculatService recruitManageMatriculatService;
	private String sitename;//学习中心
	private String edutypename;//层次
	private String majorname;//专业
	private String cardId;//证件号码
	private String startDate;//发证日期
	private List<PeRecStudent> students;//录取学生信息

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, true);

		this.getGridConfig().setTitle(this.getText("录取状态修改"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("性别"), "gender",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("证件类型"), "cardType",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("证件号码"), "cardNo",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("学历验证状态"),
				"enumConstByFlagXueliCheck.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("学生状态"),
				"enumConstByFlagRecStatus.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("是否免试生"),
				"enumConstByFlagNoexam.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("免试审核状态"),
				"enumConstByFlagNoexamStatus.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("录取状态"),
				"enumConstByFlagMatriculate.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("录取号"),
				"matriculateNum",true,true,true,Const.matriculateNum_for_extjs);
		this.getGridConfig().addColumn(this.getText("学习中心"),
				"prRecPlanMajorSite.peSite.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("层次"),
				"prRecPlanMajorSite.prRecPlanMajorEdutype.peEdutype.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("专业"),
				"prRecPlanMajorSite.prRecPlanMajorEdutype.peMajor.name",true,false,true,"");
		this.getGridConfig().addMenuFunction(
				"修改为录取",
				"matriculate",
				this.getMyListService().getEnumConstByNamespaceCode(
						"FlagMatriculate", "1").getId());
		this.getGridConfig().addMenuFunction(
				"修改为不录取",
				"NoMatriculate",
				this.getMyListService().getEnumConstByNamespaceCode(
						"FlagMatriculate", "2").getId());
		this.getGridConfig().addMenuFunction(
				"修改为等待",
				"wait",
				this.getMyListService().getEnumConstByNamespaceCode(
						"FlagMatriculate", "0").getId());
	}
	
	public void checkBeforeUpdate() throws EntityException{
		if(this.getBean().getMatriculateNum()!=null){
			if(!this.getBean().getEnumConstByFlagMatriculate().getCode().equals("1")){
				throw new EntityException("只能为已录取的学生设置录取号");
			}
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PeRecStudent.class);
		dc.add(Restrictions.eq("matriculateNum", this.getBean().getMatriculateNum()));
		dc.add(Restrictions.ne("id", this.getBean().getId()));
		List list =this.getGeneralService().getList(dc);
		if(list!=null&&list.size()>0){
			throw new EntityException("录取号已经存在！");
		}
	}
	public void checkBeforeUpdateColumn(List idList) throws EntityException {
		if (idList != null && idList.size() > 0) {
			if (!this.checkNoExamDate()) {
				throw new EntityException("当前时间不在录取操作日期范围内!");
			}
			if (!this.isActive(idList)) {
				throw new EntityException("只能操作当前活动招生批次下的数据!");
			}
			if (this.getColumn().equals("matriculate")) {
				List<PeRecStudent> list = this.getMyListService().getByIds(
						PeRecStudent.class, idList);
				for (PeRecStudent peRecStudent : list) {
					if (peRecStudent.getEnumConstByFlagMatriculate() != null) {
						String code = peRecStudent
								.getEnumConstByFlagMatriculate().getCode();
						if (code != null && code.equals("1"))
							throw new EntityException("包含已录取的学生，无法完成操作，请重新选择！");
					}
					if(!peRecStudent.getEnumConstByFlagRecStatus().getCode().equals("3")){
						throw new EntityException("学生状态是已审核状态的学生才能录取，请重新选择！");
					}
					if(peRecStudent.getEnumConstByFlagXueliCheck()==null||
							(!peRecStudent.getEnumConstByFlagXueliCheck().getCode().equals("2")&&
							!peRecStudent.getEnumConstByFlagXueliCheck().getCode().equals("3"))){
						throw new EntityException("学历验证暂通过或者通过的学生才能录取！");
							}
				}
			} else {
				for (int i = 0; i < idList.size(); i++) {
					DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
					dc.createCriteria("peRecStudent", "peRecStudent").add(Restrictions.eq("id", idList.get(i)));
					List<PeStudent> peStudentList = this.getGeneralService().getList(dc);
					if (peStudentList.size()>0) {
						if (!peStudentList.get(0).getEnumConstByFlagStudentStatus().getCode().equals("0")) {
							throw new EntityException("包含已在学籍的学生，无法完成操作，请重新选择！");
						}
					}
				}
			}
		} else {
			throw new EntityException("请选择至少一条记录");
		}
	}

	public Map updateColumn() {
		String msg = "";
		Map map = new HashMap();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			List idList = new ArrayList();

			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}

			try {
				checkBeforeUpdateColumn(idList);
			} catch (EntityException e) {
				map.clear();
				map.put("success", "false");
				map.put("info", e.getMessage());
				return map;
			}
			try {
				List<PeRecStudent> peRecStudentList = this.getMyListService()
						.getByIds(PeRecStudent.class, idList);
				if (this.getColumn().equals("matriculate")) {
					msg = this.getRecruitManageMatriculatService()
							.saveMatriculat(peRecStudentList, this.getValue());
					map.clear();
					map.put("success", "true");
					map.put("info", msg);
					return map;
				} else {
					msg = this.getRecruitManageMatriculatService()
							.deleMatriculat(idList,
									"enumConstByFlagMatriculate.id",
									this.getValue());

				}
			} catch (Exception e) {
				e.printStackTrace();
				map.clear();
				map.put("success", "false");
				map.put("info", this.getText("操作失败"));
				return map;
			}
			map.clear();
			map.put("success", "true");
			map.put("info", "操作成功");

		} else {
			map.put("success", "false");
			map.put("info", "parameter value error");
		}
		return map;
	}

	/**
	 * 打印录取通知书
	 * @return
	 */
	public String printExe(){
		DetachedCriteria dc = DetachedCriteria.forClass(PeRecStudent.class);
		DetachedCriteria dcRecPlanMajorSite = dc.createCriteria("prRecPlanMajorSite", "prRecPlanMajorSite");
		DetachedCriteria dcPrRecPlanMajorEdutype = dcRecPlanMajorSite.createCriteria("prRecPlanMajorEdutype", "prRecPlanMajorEdutype");
		dcPrRecPlanMajorEdutype.createCriteria("peRecruitplan", "peRecruitplan").add(Restrictions.eq("flagActive", "1"));
		DetachedCriteria dcSite = dcRecPlanMajorSite.createCriteria("peSite", "peSite");
		DetachedCriteria dcEdutype = dcPrRecPlanMajorEdutype.createCriteria("peEdutype", "peEdutype");
		DetachedCriteria dcMajor = dcPrRecPlanMajorEdutype.createCriteria("peMajor", "peMajor");
		dc.createCriteria("enumConstByFlagMatriculate", "enumConstByFlagMatriculate").add(Restrictions.eq("code", "1"));
		if(!this.getSitename().equals("所有学习中心")){
			dcSite.add(Restrictions.eq("name", this.getSitename()));
		}
		if(!this.getEdutypename().equals("所有层次")){
			dcEdutype.add(Restrictions.eq("name", this.getEdutypename()));
		}
		if(!this.getMajorname().equals("所有专业")){
			dcMajor.add(Restrictions.eq("name", this.getMajorname()));
		}
		if(this.getCardId()!=null&&this.getCardId().length()>0){
			dc.add(Restrictions.eq("cardNo", this.getCardId().trim()));
		}
		List<PeRecStudent> list = null;
		try {
			list = this.getGeneralService().getList(dc);
			if(list!=null&&list.size()>0){
				this.setStartDate(DateConvertor.formatStr(this.getStartDate()));
				this.setStudents(list);
				return "print";
			} 
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.setMsg("没有符合条件的数据");
		return "msg";

	}

	
	
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeRecStudent.class);
		DetachedCriteria dcMajorEdutype = dc.createCriteria(
				"prRecPlanMajorSite", "prRecPlanMajorSite",
				DetachedCriteria.LEFT_JOIN).createAlias("peSite", "peSite")
				.createCriteria("prRecPlanMajorEdutype",
						"prRecPlanMajorEdutype");
		dcMajorEdutype.createAlias("peEdutype", "peEdutype").createAlias(
				"peMajor", "peMajor");
		dcMajorEdutype.createCriteria("peRecruitplan", "peRecruitplan").add(
				Restrictions.eq("flagActive", "1"));
		dc.createCriteria("enumConstByFlagRecStatus",
				"enumConstByFlagRecStatus", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria dcEnumConstByFlagNoexam = dc.createCriteria(
				"enumConstByFlagNoexam", "enumConstByFlagNoexam");
		dc.createCriteria("enumConstByFlagPublish", "enumConstByFlagPublish",
				DetachedCriteria.LEFT_JOIN);
		DetachedCriteria dcEnumConstByFlagNoexamStatus = dc.createCriteria(
				"enumConstByFlagNoexamStatus", "enumConstByFlagNoexamStatus",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagMatriculate",
				"enumConstByFlagMatriculate", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagXueliCheck",
				"enumConstByFlagXueliCheck", DetachedCriteria.LEFT_JOIN);
		return dc;
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeRecStudent.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/recruitManage";
	}

	public void setBean(PeRecStudent instance) {
		super.superSetBean(instance);
	}

	public PeRecStudent getBean() {
		return (PeRecStudent) super.superGetBean();
	}

	public RecruitManageMatriculatService getRecruitManageMatriculatService() {
		return recruitManageMatriculatService;
	}

	public void setRecruitManageMatriculatService(
			RecruitManageMatriculatService recruitManageMatriculatService) {
		this.recruitManageMatriculatService = recruitManageMatriculatService;
	}

	public String getSitename() {
		return sitename;
	}

	public void setSitename(String sitename) {
		this.sitename = sitename;
	}

	public String getEdutypename() {
		return edutypename;
	}

	public void setEdutypename(String edutypename) {
		this.edutypename = edutypename;
	}

	public String getMajorname() {
		return majorname;
	}

	public void setMajorname(String majorname) {
		this.majorname = majorname;
	}
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public List<PeRecStudent> getStudents() {
		return students;
	}

	public void setStudents(List<PeRecStudent> students) {
		this.students = students;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
}
