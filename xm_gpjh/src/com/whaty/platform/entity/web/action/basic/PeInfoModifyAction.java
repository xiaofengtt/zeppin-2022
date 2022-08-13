package com.whaty.platform.entity.web.action.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeInfoModify;
import com.whaty.platform.entity.bean.PeInfoNews;
import com.whaty.platform.entity.bean.PeInfoNewsType;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class PeInfoModifyAction extends MyBaseAction {
    private PeInfoModify info;

	private String id;
	private String logo_photo;
	private String comu_photo;
	private String copyright;
	private String pingtaiName;
	private String schoolName;
	
	
	private File upload1;
	private File upload2;

	private String uploadFileName;

//查询平台信息
	public String updateInfoNews(){
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		try {
			DetachedCriteria infodc = DetachedCriteria.forClass(PeInfoModify.class);
			PeInfoModify infoModify=(PeInfoModify)this.getGeneralService().getList(infodc).get(0);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "info_modify";
	}
	
//修改平台信息
	public String infoModify(){
		String logoPhoto ="";
		String compuPhoto ="";
		try {
			PeInfoModify peInfoModify =(PeInfoModify)this.getGeneralService().getById(id);
			DetachedCriteria dc = DetachedCriteria.forClass(PeInfoModify.class);
			
			if(upload1!=null&&upload2!=null){
				logoPhoto =this.uploadexe(upload1, uploadFileName);
				peInfoModify.setLogoPhoto(logoPhoto);
				compuPhoto =this.uploadexe(upload2,uploadFileName);
				peInfoModify.setCompuPhoto(compuPhoto);
			}
			
			peInfoModify.setCopright(copyright);
			peInfoModify.setPingtaiName(pingtaiName);
			peInfoModify.setSchoolName(schoolName);
			this.getGeneralService().save(peInfoModify);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "info_modify";
	}
	
//上传照片
	protected String uploadexe(File file,String filename){
		String link ="";
		try{
		String savePath = "/incoming/photo/";
		link = savePath+filename;
		String linkTemp = link;
		int afterFileName = 0;
		
		while(true){
			if(new File(ServletActionContext.getRequest().getRealPath(linkTemp)).isFile()){
				int point = (link.lastIndexOf(".")>0 ? link.lastIndexOf("."):link.length());
				linkTemp = link.substring(0, point)+"["+String.valueOf(afterFileName)+"]"+link.substring(point);
				afterFileName++;
				continue;
			}
			break;
		}
		
		FileOutputStream fos=new FileOutputStream(ServletActionContext.getRequest().getRealPath(linkTemp));
		FileInputStream fis=new FileInputStream(file);
		byte[] buffer=new byte[1024];
		int len=0;
		while((len=fis.read(buffer))>0){
			fos.write(buffer, 0, len);
		}
		fos.close();
		fis.close();
		}catch (Exception e) {
			
		}
		return link;
		
	}
	
	public String getLogo_photo() {
		return logo_photo;
	}

	public void setLogo_photo(String logo_photo) {
		this.logo_photo = logo_photo;
	}

	public String getComu_photo() {
		return comu_photo;
	}

	public void setComu_photo(String comu_photo) {
		this.comu_photo = comu_photo;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public String getPingtaiName() {
		return pingtaiName;
	}

	public void setPingtaiName(String pingtaiName) {
		this.pingtaiName = pingtaiName;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeInfoModify.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/peInfoModify";

	}

	public PeInfoModify getInfo() {
		return info;
	}

	public void setInfo(PeInfoModify info) {
		this.info = info;
	}


	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public File getUpload1() {
		return upload1;
	}

	public void setUpload1(File upload1) {
		this.upload1 = upload1;
	}

	public File getUpload2() {
		return upload2;
	}

	public void setUpload2(File upload2) {
		this.upload2 = upload2;
	}



}
