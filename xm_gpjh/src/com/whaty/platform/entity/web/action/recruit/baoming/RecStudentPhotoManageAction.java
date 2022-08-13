package com.whaty.platform.entity.web.action.recruit.baoming;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

//import com.whaty.platform.entity.bean.PeEdutype;
//import com.whaty.platform.entity.bean.PeGrade;
//import com.whaty.platform.entity.bean.PeMajor;
//import com.whaty.platform.entity.bean.PeRecStudent;
//import com.whaty.platform.entity.bean.PeSite;
//import com.whaty.platform.entity.service.recruit.PeRecStudentService;
import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.EntityBaseAction;
import com.whaty.platform.entity.web.action.MyBaseAction; //import com.whaty.platform.sso.web.action.SsoConstant;
//import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.util.Exception.FileManageException;
import com.whaty.util.file.FileManage;
import com.whaty.util.file.FileManageFactory;

public class RecStudentPhotoManageAction extends MyBaseAction {

	/**
	 * 学生照片管理的ACTION 包括单个上传和批量上传
	 * 
	 * @author 李冰
	 */

	private String contentType;
	private File photoUpload;
	private File photos;
	private String fileName;
	// 照片存储位置，在配置文件中配置
	private String savePath;

	public String getSavePath() {
		return ServletActionContext.getRequest().getRealPath(savePath);
	}

	public String getSavePath(String path) {
		return ServletActionContext.getRequest().getRealPath(path);
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getPhotoUploadFileName() {
		return fileName;
	}

	public void setPhotoUploadFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPhotoUploadContentType() {
		return contentType;
	}

	public void setPhotoUploadContentType(String contentType) {
		this.contentType = contentType;
	}

	public File getPhotoUpload() {
		return photoUpload;
	}

	public void setPhotoUpload(File photoUpload) {
		this.photoUpload = photoUpload;
	}

	public File getPhotos() {
		return photos;
	}

	public void setPhotos(File photos) {
		this.photos = photos;
	}

	public String getPhotosFileName() {
		return fileName;
	}

	public void setPhotosFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPhotosContentType() {
		return contentType;
	}

	public void setPhotosContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * 单个照片上传
	 * 
	 * @return
	 */
	public String photoUpload() {
		try {
			this.doPhoto();
		} catch (EntityException e) {
			this.setMsg("操作失败");
			e.printStackTrace();

		}
		this.setTogo("back");
		return "msg";
	}

	/**
	 * 照片上传处理功能
	 * 
	 * @throws EntityException
	 */
	private void doPhoto() throws EntityException {
		String str = "";
		if (this.fileName != null) {
			if (this.fileName.toLowerCase().endsWith(".bmp")
					|| this.fileName.toLowerCase().endsWith("jpg")
					|| this.fileName.toLowerCase().endsWith(".gif")) {
				str = this.fileName.substring(0, this.fileName.length() - 4);
				this.setMsg("文件格式正确");
			} else {
				this.setMsg("文件格式不正确");
			}
		} else {
			this.setMsg("无文件");
		}
		if (this.getMsg() != null && this.getMsg().equals("文件格式正确")) {
			DetachedCriteria dc = DetachedCriteria.forClass(PeRecStudent.class);
			dc.add(Restrictions.eq("cardNo", str));

			List<PeRecStudent> list = this.getGeneralService().getList(dc);
			String id = null;
			if (list.size() == 0) {
				this.setMsg("没有这个身份证号");
				return;
			}
			try {
				if (!this.photoUpload.getCanonicalPath()
						.endsWith(this.fileName)) {
					String photoLink = this.getSavePath() + "/" + this.fileName;
					FileOutputStream fos = new FileOutputStream(photoLink);
					FileInputStream fis = new FileInputStream(this.photoUpload);
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = fis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
					fos.close();
					fis.close();
				}
				List ids = new ArrayList();
				for (PeRecStudent peRecStudent : list) {
					ids.add(peRecStudent.getId());
				}
				this.getGeneralService().updateColumnByIds(ids, "photoLink",
						savePath + "/" + this.fileName);
				this.setMsg("操作成功");
			} catch (FileNotFoundException e) {
				this.setMsg("找不到指定路径");
				e.printStackTrace();
			} catch (IOException e) {
				this.setMsg("IO错误");
				e.printStackTrace();
			}
		}
		// if(this.photosave!=null&&this.photosave.equals("文件格式正确")){
		// DetachedCriteria dc = DetachedCriteria.forClass(PeRecStudent.class);
		// dc.add(Restrictions.eq("cardNo", str));
		// List list=this.peRecStudentService.getList(dc);
		// String id=null;
		// photosave="没有这个身份证号";
		// if(list.size()!=0){
		// //*******横向权限***********
		// UserSession us =
		// (UserSession)ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		// List<String> siteIds=new ArrayList<String>();
		// List<String> edutypeIds=new ArrayList<String>();
		// List<String> gradeIds=new ArrayList<String>();
		// List<String> majorIds=new ArrayList<String>();
		// if(us!=null){
		// List<PeSite> siteList=us.getPriSites();
		// for (PeSite peSite : siteList) {
		// siteIds.add(peSite.getId());
		// }
		// List<PeEdutype> edutypeList=us.getPriEdutype();
		// for (PeEdutype peEdutype : edutypeList) {
		// edutypeIds.add(peEdutype.getId());
		// }
		// List<PeGrade> gradeList=us.getPriGrade();
		// for (PeGrade peGrade : gradeList) {
		// gradeIds.add(peGrade.getId());
		// }
		// List<PeMajor> majorList=us.getPriMajor();
		// for (PeMajor peMajor : majorList) {
		// majorIds.add(peMajor.getId());
		// }
		//					
		// }
		// DetachedCriteria dc1 = DetachedCriteria.forClass(PeRecStudent.class);
		// DetachedCriteria dc2=dc1.createCriteria("prRecPlanMajorSite",
		// "prRecPlanMajorSite").createAlias("peSite", "peSite");
		// dc2.createCriteria("prRecPlanMajorEdutype",
		// "prRecPlanMajorEdutype").createAlias("peEdutype",
		// "peEdutype").createAlias("peMajor", "peMajor")
		// .createCriteria("peRecruitplan",
		// "peRecruitplan").createAlias("peGrade", "peGrade");
		//				
		// if(siteIds!=null&&siteIds.size()!=0){
		// dc1.add(Restrictions.in("peSite.id", siteIds));
		// }
		// if(edutypeIds!=null&&edutypeIds.size()!=0){
		// dc1.add(Restrictions.in("peEdutype.id", edutypeIds));
		// }
		// if(majorIds!=null&&majorIds.size()!=0){
		// dc1.add(Restrictions.in("peMajor.id", majorIds));
		// }
		// if(gradeIds!=null&&gradeIds.size()!=0){
		// dc1.add(Restrictions.in("peGrade.id", gradeIds));
		// }
		// dc1.add(Restrictions.eq("cardNo", str));
		// List list1=this.peRecStudentService.getList(dc1);
		// photosave="您不能对这个学生进行操作";
		// //*********************************
		// if(list1.size()!=0){
		// PeRecStudent instance=(PeRecStudent)list1.get(0);
		// id=instance.getId();
		// }
		// }
		// if(id!=null){
		// try {
		// if(!this.photoUpload.getCanonicalPath().endsWith(this.fileName)){
		// String photoLink=this.getSavePath()+"/"+this.fileName;
		// FileOutputStream fos=new FileOutputStream(photoLink);
		// FileInputStream fis=new FileInputStream(this.photoUpload);
		// byte[] buffer=new byte[1024];
		// int len=0;
		// while((len=fis.read(buffer))>0){
		// fos.write(buffer, 0, len);
		// }
		// fos.close();
		// fis.close();
		// }
		// List ids=new ArrayList();
		// ids.add(id);
		// this.peRecStudentService.updateColumnByIds(ids, "photoLink",
		// savePath+"/"+this.fileName);
		// this.photosave="操作成功";
		// } catch (FileNotFoundException e) {
		// this.photosave="fail1";
		// e.printStackTrace();
		// }catch (IOException e) {
		// this.photosave="fail2";
		// e.printStackTrace();
		// }
		//				
		// }
		//		
		//			
		// }

	}

	/**
	 * 批量上传照片
	 * 
	 * @return
	 */
	public String photosUpload() {
		if (this.photos != null && this.fileName != null) {
			String photosLink = null;
			try {
				photosLink = this.getSavePath(this.savePath + "/"
						+ this.fileName);
				FileOutputStream fos = new FileOutputStream(photosLink);
				FileInputStream fis = new FileInputStream(this.photos);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				this.setMsg("操作成功");
				fos.close();
				fis.close();
			} catch (FileNotFoundException e) {
				this.setMsg("fail1");
			} catch (IOException e) {
				this.setMsg("fail2");
				e.printStackTrace();
			}
			FileManage filemanage = FileManageFactory.creat();
			List<String> filenamelist = new ArrayList<String>();
			try {
				filenamelist = filemanage.unzip(photosLink, this.getSavePath(),
						true);
				String photosave_temp = "";
				int n = filenamelist.size();
				int faile = 0;
				for (int i = 0; i < n; i++) {
					this.fileName = filenamelist.get(i);
					if(this.fileName.lastIndexOf("\\")>0){
						this.fileName = this.fileName.substring(this.fileName.lastIndexOf("\\")+1);
					}
					this.photoUpload = new File(this.getSavePath(this.savePath
							+ "/" + this.fileName));
//					if (this.photoUpload.exists())
						try {
							this.doPhoto();
						} catch (EntityException e) {
							this.setMsg("文件存储失败");
							e.printStackTrace();
						}

					if (this.getMsg().equals("文件格式不正确")
							|| this.getMsg().equals("没有这个身份证号")
							|| this.getMsg().equals("无文件")
							|| this.getMsg().equals("您不能对这个学生进行操作")) {
						photosave_temp += ("压缩包中(" + this.fileName
								+ ")上传失败，原因是：<font color=red>" + this.getMsg() + "</font><br>");
						faile++;
						new File(this.getSavePath(this.savePath + "/"
								+ this.fileName)).delete();
					}
				}
				this.setMsg(photosave_temp + "共上传" + n + "个照片文件，其中上传成功了"
						+ (n - faile) + "张");

			} catch (IOException e) {
				this.setMsg("文件存储失败");
				e.printStackTrace();
			} catch (FileManageException e) {
				this.setMsg("请使用ZIP格式，并且不要使用中文文件名");
				e.printStackTrace();
			} catch (RuntimeException e) {
				this.setMsg("失败");
				e.printStackTrace();
			}
		} else {
			this.setMsg("无文件");
		}
		this.setTogo("back");
		return "msg";
	}

	public String photo() {
		return "photo";
	}

	public String photos() {
		return "photos";
	}

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeRecStudent.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub

	}

	public void setBean(PeRecStudent instance) {
		super.superSetBean(instance);

	}

	public PeRecStudent getBean() {
		return (PeRecStudent) super.superGetBean();
	}

}
