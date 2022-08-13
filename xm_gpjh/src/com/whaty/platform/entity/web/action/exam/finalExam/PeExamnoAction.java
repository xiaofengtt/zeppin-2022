package com.whaty.platform.entity.web.action.exam.finalExam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeExamNo;
import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.Container;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.JsonUtil;

public class PeExamnoAction extends MyBaseAction {

	@Override
	public Map updateColumn() {
		return null;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("考试场次管理"));
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		this.getGridConfig().addColumn(this.getText("考试学期"),"peSemester.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("场次名称"),"name");
		//添加场次编号交验为1位整数（20090423 龚妮娜）
		this.getGridConfig().addColumn(this.getText("场次编号"),"sequence",true, true, true,"regex:new RegExp(/^\\d{1}?$/),regexText:'场次编号格式：1位整数',");
		this.getGridConfig().addColumn(this.getText("试卷类型"), "paperType", true, true, true, "TextField", false, 1);
		this.getGridConfig().addColumn(this.getText("场次开始时间"),"startDatetime");
		this.getGridConfig().addColumn(this.getText("场次结束时间"),"endDatetime");
//		this.getGridConfig().addColumn(this.getText("是否活动考试学期"),"peSemester.flagActive",false,false,false,"");
//		this.getGridConfig().addRenderScript(this.getText("为考试场次指定考试课程"), "{if (record.data['peSemester.flagActive']=='1') return '<a href=/entity/recruit/recruitmanage_showRecNote.action?id='+record.data['id']+' target=_blank><font color=blue>设置考试课程</font></a>'; else return ''; }", "");
	}

	public void checkBeforeDelete(List idList) throws EntityException{
		StringBuffer ExamNoNames = new StringBuffer();
		if(!isCanDelete(idList,ExamNoNames)){
			throw new EntityException("删除失败！<br/>“" + ExamNoNames.toString() + "”不在当前活动考试学期之内，不能删除");
		}
	}


	public void checkBeforeAdd() throws EntityException {
		this.beforeOperate();
		this.checkTime();
	}

	public void checkBeforeUpdate() throws EntityException {
		
		if(!isCanOperate()){
			throw new EntityException("只能修改当前考试学期之内的考试场次信息");
		}
		this.beforeOperate();
		this.checkTime();
	}

	private void checkTime() throws EntityException {
		if(this.getBean() != null && this.getBean().getStartDatetime() != null && this.getBean().getEndDatetime() != null){
			if(this.getBean().getStartDatetime().after(this.getBean().getEndDatetime())){
				throw new EntityException("考试场次开始时间不能晚于结束时间");
			}
		}
	}
	@Override
	public void setEntityClass() {
		this.entityClass = PeExamNo.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/exam/peExamno";
	}

	public void setBean(PeExamNo instance){
		this.superSetBean(instance);
	}
	
	public PeExamNo getBean(){
		return (PeExamNo)this.superGetBean();
	}
	
	public DetachedCriteria initDetachedCriteria() {
		JsonUtil.setDateformat("yyyy-MM-dd HH:mm:ss");
		DetachedCriteria dc = DetachedCriteria.forClass(PeExamNo.class);
		dc.createCriteria("peSemester", "peSemester");
		return dc;
	}
	
	private boolean isCanOperate(){
		boolean result = false;
		
		String  sql = "";
		List list = new ArrayList();
		
		try {
			sql = " select semester.flag_active                          " + 
			"   from pe_exam_no examno, pe_semester semester       " +
			"  where examno.fk_semester_id = semester.id           " +
			"    and examno.id = '"+this.getBean().getId()+"'      " ;
			
			list = this.getGeneralService().getBySQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(list.size() > 0){
			if(list.get(0) != null && "1".equals(list.get(0).toString())){
				result = true;
			}
		}
		
		return result;
	}
	/**
	 * 对于添加、修改，所属考试学期默认都为当前活动学期
	 * 同时添加校验，同一个考试学期之下，场次号是唯一的
	 * @throws EntityException
	 */
	private void beforeOperate()throws EntityException{
		DetachedCriteria dcPeSemester = DetachedCriteria.forClass(PeSemester.class);
		dcPeSemester.add(Restrictions.eq("flagActive", "1"));
		List<PeSemester> list = new ArrayList();
		
		try {
			list = this.getGeneralService().getList(dcPeSemester);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EntityException("操作失败");
		}
		if(list.size() < 1){
			throw new EntityException("当前没有设置活动考试学期，操作失败");
		}else if(list.size() > 1){
			throw new EntityException("当前活动学期个数多于一个，操作失败");
		}
		this.getBean().setPeSemester(list.get(0));
		
		
		//以下用于判定所添加或是修改的场次号是否在数据库中已经存在
		String sql = "";
		
		try {
			sql = " select t.id,t.sequence from pe_exam_no t where t.sequence = '"+this.getBean().getSequence()+"' ";
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		List tempList = new ArrayList();
		
		try {
			tempList = this.getGeneralService().getBySQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(tempList.size() > 0){
			Object[] obj = (Object[])tempList.get(0);
			if(this.getBean() != null){
				if(this.getBean().getId() == null){
					DetachedCriteria dcPeExamNo = DetachedCriteria.forClass(PeExamNo.class);
					dcPeExamNo.add(Restrictions.eq("peSemester", this.getBean().getPeSemester()));
					dcPeExamNo.add(Restrictions.eq("sequence", this.getBean().getSequence()));
					List dclist = this.getGeneralService().getList(dcPeExamNo);
					if(dclist!=null&&dclist.size()>0){
					throw new EntityException("操作失败，当前考试学期内已经存在“"+this.getBean().getSequence()+"”这一场次编号");
					}
				}
//				if(this.getBean().getId() != null && !this.getBean().getId().equals(obj[0].toString())){
//					throw new EntityException("操作失败，当前考试学期内已经存在“"+this.getBean().getSequence()+"”这一场次编号");
//				}
			}
		}
	}
	/**
	 * 判断所删除的是否都是当前活动考试学期内的考试场次
	 * @param ids
	 * @param ExamNoNames
	 * @return
	 */
	private boolean isCanDelete(List ids,StringBuffer ExamNoNames){
		boolean result = false;
		
		String sql = " select examno.name from pe_exam_no examno,pe_semester semester where examno.fk_semester_id = semester.id and semester.flag_active != '1' and examno.id in(' ' ";
		
		for(int i = 0; i < ids.size(); i++){
			sql += ",'"+ids.get(i)+"'";
		}
		sql += ")";
		
		List list = new ArrayList();
		try {
			list = this.getGeneralService().getBySQL(sql);
			if(list.size() < 1){
				result = true;
			}else{
				ExamNoNames.append(list.get(0).toString());
				for(int j = 1; j < list.size(); j++){
					ExamNoNames.append("、" + list.get(j).toString());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
