package com.whaty.platform.entity.web.action.teaching.paper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeTchRejoinSection;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.Container;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.JsonUtil;
/**
 * 答辩时间设置
 * @author 李冰
 *
 */
public class PeTchRejoinSectionAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("答辩时间设置。请按时间的先后顺序设置顺序号(1-12),自动安排答辩时间会以此为依据"));

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学期"), "peSemester.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("答辩时间段"), "name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("答辩时间"),"rejoinDatetime");
		this.getGridConfig().addColumn(this.getText("顺序号"),"sequence",true,true,true,"regex:new RegExp(/^([1-9]|1[0-2])$/),regexText:'只能输入1-12的整数',");	
	}
	
	public void checkBeforeUpdate() throws EntityException{
		if(!this.getBean().getPeSemester().getName().equals(this.semester().getName())){
			throw new EntityException("只能操作当前学期的数据");
		}
		List<PeTchRejoinSection> list = this.checkSequence();
		if(list!=null&&list.size()>0){
			if(!list.get(0).getId().equals(this.getBean().getId())){
				throw new EntityException("顺序号必须在当前学期唯一，所输入的顺序号已经存在");
			}
		}
		this.theRejoinName();
		list = this.checkName();
		if(list!=null&&list.size()>0){
			if(!list.get(0).getId().equals(this.getBean().getId())){
				throw new EntityException("答辩时间段在当前学期唯一，所输入的时间段已经存在");
			}
		}
	}


	public void checkBeforeAdd() throws EntityException{
		List<PeTchRejoinSection> list = this.checkSequence();
		if(list!=null&&list.size()>0){
				throw new EntityException("顺序号必须在当前学期唯一，所输入的顺序号已经存在");
		}
		this.getBean().setPeSemester(this.semester());
		this.theRejoinName();
		list = this.checkName();
		if(list!=null&&list.size()>0){
				throw new EntityException("答辩时间段在当前学期唯一，所输入的时间段已经存在");
		}
	}
	
	/**
	 * 检查所生成的name是否已经存在，即时间段是否已经存在
	 * @return
	 */
	private List<PeTchRejoinSection> checkName(){
		DetachedCriteria dc = DetachedCriteria.forClass(PeTchRejoinSection.class);
		dc.createCriteria("peSemester", "peSemester").add(Restrictions.eq("flagActive", "1"));
		dc.add(Restrictions.like("name", this.getBean().getName().substring(0, 12),MatchMode.START));
		List<PeTchRejoinSection> peTchRejoinSectionList = null;
		try {
			peTchRejoinSectionList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return peTchRejoinSectionList;
	}
	/**
	 * 取得当前学期
	 * @return
	 */
	private PeSemester semester(){
		DetachedCriteria dc = DetachedCriteria.forClass(PeSemester.class);
		dc.add(Restrictions.eq("flagActive", "1"));
		List<PeSemester> list = null;
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else {
			return null;
		}
	}
	/**
	 * 生成name字段
	 */
	private void theRejoinName(){

	       SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
	       String time = bartDateFormat.format(this.getBean().getRejoinDatetime());
	       String name = time.substring(0, 10);
	       int num = Integer.parseInt(time.substring(11, 13));
	       if(num<12){
	    	   name +="上午"+time.substring(11, time.length());
	       } else if(num<18){
	    	   name +="下午"+time.substring(11, time.length());
	       } else {
	    	   name +="晚上"+time.substring(11, time.length());
	       }
	       this.getBean().setName(name);
	}
	/**
	 * 查看当前学期这个顺序号是否被使用
	 * @return
	 */
	private List<PeTchRejoinSection> checkSequence(){
		DetachedCriteria dc = DetachedCriteria.forClass(PeTchRejoinSection.class);
		dc.createCriteria("peSemester", "peSemester").add(Restrictions.eq("flagActive", "1"));
		dc.add(Restrictions.eq("sequence", this.getBean().getSequence()));
		List<PeTchRejoinSection> peTchRejoinSectionList = null;
		try {
			peTchRejoinSectionList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return peTchRejoinSectionList;
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		JsonUtil.setDateformat("yyyy-MM-dd HH:mm:ss");
		DetachedCriteria dc = DetachedCriteria.forClass(PeTchRejoinSection.class);
		dc.createCriteria("peSemester","peSemester");
		return dc;
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PeTchRejoinSection.class;

	}

	@Override
	public void setServletPath() {
	this.servletPath = "/entity/teaching/peTchRejoinSection";

	}
	
	public void setBean(PeTchRejoinSection instance) {
		super.superSetBean(instance);
		
	}
	
	public PeTchRejoinSection getBean(){
		return (PeTchRejoinSection) super.superGetBean();
	}
}
