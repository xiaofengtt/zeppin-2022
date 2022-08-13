package com.whaty.platform.entity.web.action.teaching.paper;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeTchRejoinRoom;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PeTchRejoinRoomAction extends MyBaseAction {
	
	
	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("答辩教室设置。请为当前学期设置6个答辩教室，教室编号为1-6的整数，自动分配答辩时间的同时会根据教室编号分配答辩教室"));

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学期"), "peSemester.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("答辩教室"), "name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("教室地址"),"trueName");
		this.getGridConfig().addColumn(this.getText("教室编号"),"code",true,true,true,"regex:new RegExp(/^([1-6])$/),regexText:'只能输入1-6的整数',");	
	}
	
	public void checkBeforeUpdate() throws EntityException{
		if(!this.getBean().getPeSemester().getName().equals(this.semester().getName())){
			throw new EntityException("只能操作当前学期的数据");
		}
		List<PeTchRejoinRoom> list = this.checkCode();
		if(list!=null&&list.size()>0){
			if(!list.get(0).getId().equals(this.getBean().getId())){
				throw new EntityException("编号必须在当前学期唯一，所输入的编序号已经存在");
			}
		}
		this.theRejoinName();
		list = this.checkName();
		if(list!=null&&list.size()>0){
			if(!list.get(0).getId().equals(this.getBean().getId())){
				throw new EntityException("答辩教室在当前学期唯一，所输入的教室已经存在");
			}
		}
	}
	public void checkBeforeAdd() throws EntityException{
		List<PeTchRejoinRoom> list = this.checkCode();
		if(list!=null&&list.size()>0){
				throw new EntityException("编序号必须在当前学期唯一，所输入的编号已经存在");
		}
		this.getBean().setPeSemester(this.semester());
		this.theRejoinName();
		list = this.checkName();
		if(list!=null&&list.size()>0){
				throw new EntityException("答辩教室在当前学期唯一，所输入的教室已经存在");
		}
	}
	
	/**
	 * 检查所生成的name是否已经存在，即时间段是否已经存在
	 * @return
	 */
	private List<PeTchRejoinRoom> checkName(){
		DetachedCriteria dc = DetachedCriteria.forClass(PeTchRejoinRoom.class);
		dc.createCriteria("peSemester", "peSemester").add(Restrictions.eq("flagActive", "1"));
		dc.add(Restrictions.eq("name", this.getBean().getName()));
		List<PeTchRejoinRoom> peTchRejoinRoomList = null;
		try {
			peTchRejoinRoomList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return peTchRejoinRoomList;
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
	       String name = this.semester().getName()+this.getBean().getTrueName();
	       this.getBean().setName(name);
	}
	/**
	 * 查看当前学期这个编号是否被使用
	 * @return
	 */
	private List<PeTchRejoinRoom> checkCode(){
		DetachedCriteria dc = DetachedCriteria.forClass(PeTchRejoinRoom.class);
		dc.createCriteria("peSemester", "peSemester").add(Restrictions.eq("flagActive", "1"));
		dc.add(Restrictions.eq("code", this.getBean().getCode()));
		List<PeTchRejoinRoom> peTchRejoinRoomList = null;
		try {
			peTchRejoinRoomList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return peTchRejoinRoomList;
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeTchRejoinRoom.class);
		dc.createCriteria("peSemester","peSemester");
		return dc;
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PeTchRejoinRoom.class;

	}

	@Override
	public void setServletPath() {
	this.servletPath = "/entity/teaching/peTchRejoinRoom";

	}
	
	public void setBean(PeTchRejoinRoom instance) {
		super.superSetBean(instance);
		
	}
	
	public PeTchRejoinRoom getBean(){
		return (PeTchRejoinRoom) super.superGetBean();
	}
}
