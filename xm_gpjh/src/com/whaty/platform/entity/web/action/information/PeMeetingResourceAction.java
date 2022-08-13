package com.whaty.platform.entity.web.action.information;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeMeeting;
import com.whaty.platform.entity.bean.PeMeetingResource;
import com.whaty.platform.entity.bean.PeUnit;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class PeMeetingResourceAction extends MyBaseAction {
	private PeUnit peUnit;
	private File upload;
	private String note;
	private String name;
	private String uploadFilename;
	
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(true, true, false, true);
		this.getGridConfig().setTitle(this.getText("会议资源"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("文件名称"), "name");
		this.getGridConfig().addColumn(this.getText("上传时间"), "uploadDate", true,false,true,"");
//		this.getGridConfig().addColumn(this.getText("会议名称"), "peMeeting.name",false,false,true,"");
//		this.getGridConfig().addColumn(this.getText("会议时间"), "peMeeting.meetingDate", false,false,true,"Date");
//		this.getGridConfig().addColumn(this.getText("创建时间"), "peMeeting.createDate", false,false,true,"Date");
//		this.getGridConfig().addColumn(this.getText("提交人"), "peManager.name", false,false,true,"");
		ColumnConfig cc=new ColumnConfig(this.getText("提交人"), "peManager.name");
		cc.setComboSQL("select p.id,p.name from pe_manager p where p.name is not null");
		cc.setAdd(false);
		this.getGridConfig().addColumn(cc);
		this.getGridConfig().addColumn(this.getText("提交人所在单位"), "peUnit.name", false,false,true,"");
//		this.getGridConfig().addColumn(this.getText("文件名称"), "name", false,true,true,"");
		this.getGridConfig().addColumn(this.getText("上传会议资料"), "meetingresource",false,true,false,"File",false,100);
		this.getGridConfig().addColumn(this.getText("备注"), "note",false,true,false,"Textarea",true,500);
		this.getGridConfig().addRenderFunction(this.getText("会议资料下载"), "<a href='${value}' target='_blank'>下载</a>", "meetingresource");
		this.getGridConfig().addMenuScript(this.getText("返回"), "{history.go(-1);}");
	}
	
	//上传会议资源
	 public String uploadResource(){
		 return "upload_meeting_resource";
	 }
	public String uploadMeeetingResource(){
		 UserSession usersession = (UserSession) ActionContext.getContext()
		    .getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
//		   PeUnit unit=this.getcurrentUserUnit(usersession);
		    PeManager manager=this.getcurrentUser(usersession);
//			this.getGeneralService().getGeneralDao().setEntityClass(PeMeeting.class);
			PeMeeting meet = null;
			PeMeetingResource meetR=new PeMeetingResource();
			try{	
				meet = (PeMeeting)this.getGeneralService().getById(PeMeeting.class,this.getBean().getId());
			}catch(Exception e){
				e.printStackTrace();
			}
			meetR.setNote(this.getNote());
			meetR.setPeMeeting(meet);
			meetR.setPeUnit(manager.getPeUnit());
			meetR.setPeManager(manager);
			meetR.setName(this.getName());
			meetR.setUploadDate(new Date());
			if(this.getUpload()==null){
		    	this.setMsg("会议资料上传失败！");
			}else
			{
				String filename = this.getUploadFilename();
			    String docpath = ServletActionContext.getRequest().getRealPath(Const.meetingFilePath) + filename;
				try {
					FileOutputStream fos = new FileOutputStream(docpath);
					FileInputStream fis = new FileInputStream(this.getUpload());
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = fis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
					fis.close();
					fos.close();
					this.setMsg("会议资料上传成功！");
				} catch (FileNotFoundException e) {
			    	this.setMsg("会议资料上传失败！");
					e.printStackTrace();
				} catch (IOException e) {
			    	this.setMsg("会议资料上传失败！");
					e.printStackTrace();
				}
				if (this.getMsg().equals("任务资料上传成功！")) {
					meetR.setMeetingresource(Const.meetingFilePath+filename);
				}
			 }
		
		try {
			this.getGeneralService().save(meetR);
			this.setMsg("提交会议材料成功！");
			this.setTogo("/entity/information/peMeetingResource.action");
		} catch (EntityException e) {
			this.setMsg("提交会议材料失败！");
			this.setTogo("back");
			e.printStackTrace();
		}
		return "msg";
	}

//	//得到当前用户所在单位
//	public PeUnit getcurrentUserUnit(UserSession usersession){
//		 PeUnit unit=null;
//		 DetachedCriteria dc=DetachedCriteria.forClass(PeManager.class);
//		 dc.createCriteria("ssoUser","ssoUser");
//		 dc.createCriteria("peUnit","peUnit");
//		 dc.add(Restrictions.eq("ssoUser.id", usersession.getSsoUser().getId()));
//		 List list=new LinkedList();
//		 try {
//			list=this.getGeneralService().getList(dc);
//			 if(list!=null&&list.size()>0){
//				 PeManager mgr=(PeManager) list.get(0);
//				 unit=mgr.getPeUnit();
//			 }
//		} catch (EntityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		 return unit;
//	 }
	  //得到当前用户的名字
    public PeManager getcurrentUser(UserSession usersession){
    	PeManager manager=null;
    	DetachedCriteria dc = DetachedCriteria.forClass(PeManager.class);
    	dc.createAlias("ssoUser", "ssoUser");
    	dc.add(Restrictions.eq("ssoUser.id",usersession.getSsoUser().getId()));
    	List list = new LinkedList();
    	try{
    		list=this.getGeneralService().getList(dc);
    		if(list!=null&&list.size()>0){
    			manager=(PeManager)list.get(0);
    		}
    	}catch (EntityException e) {
       		e.printStackTrace();
       	}
    	return manager;
    }
	
	@Override
	public void setEntityClass() {
		this.entityClass = PeMeetingResource.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/information/peMeetingResource";
	}
	@Override
	public void checkBeforeAdd() throws EntityException {
		UserSession usersession = (UserSession) ActionContext.getContext()
	    .getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
//	    PeUnit unit=this.getcurrentUserUnit(usersession);
	    PeManager manager=this.getcurrentUser(usersession);
		this.getBean().setPeManager(manager);
		this.getBean().setPeUnit(manager.getPeUnit());
		this.getBean().setUploadDate(new Date());
		try{
			this.getGeneralService().save(this.getBean());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
    @Override
	public DetachedCriteria initDetachedCriteria() {
    	DetachedCriteria dc=DetachedCriteria.forClass(PeMeetingResource.class);
    	dc.createAlias("peMeeting", "peMeeting");
    	dc.createAlias("peManager", "peManager");
    	dc.createAlias("peUnit", "peUnit");
    	UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
    	if(!us.getRoleType().equals("4")){
    		dc.add(Restrictions.eq("peUnit", getcurrentUser(us).getPeUnit()));
    	}
    	return dc;
	}
    public void setBean(PeMeetingResource instance) {
		super.superSetBean(instance);
	}

	public PeMeetingResource getBean() {
		return  (PeMeetingResource)super.superGetBean();
	}

	public PeUnit getPeUnit() {
		return peUnit;
	}

	public void setPeUnit(PeUnit peUnit) {
		this.peUnit = peUnit;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFilename() {
		return uploadFilename;
	}

	public void setUploadFilename(String uploadFilename) {
		this.uploadFilename = uploadFilename;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
