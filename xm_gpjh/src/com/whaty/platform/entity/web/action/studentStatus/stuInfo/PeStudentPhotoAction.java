package com.whaty.platform.entity.web.action.studentStatus.stuInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrStudentInfo;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.util.Exception.FileManageException;
import com.whaty.util.file.FileManage;
import com.whaty.util.file.FileManageFactory;

public class PeStudentPhotoAction extends PeStudentInfoAction {

	private static final long serialVersionUID = -6692802340260474729L;
	
	private List<PeStudent> studentlist;
	
	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("电子图像校对"));
		this.getGridConfig().setCapability(false, false, false,true);
		if(this.getGridConfig().checkBeforeAddMenu("/entity/studentStatus/peStudentPhoto_photosUpload.action")){
		this.getGridConfig().addMenuScript(this.getText("导入毕业学生照片"), "{window.navigate('/entity/studentStatus/peStudentPhoto_photosUpload.action')}");
		}
		//this.getGridConfig().addMenuFunction(this.getText("比对选中学生照片"), "/entity/studentStatus/peStudentPhoto_showphotos.action", false, false);
		if(this.getGridConfig().checkBeforeAddMenu("/entity/studentStatus/peStudentPhoto_showphotos.action")){
		this.getGridConfig().addMenuScript(this.getText("校对选中学生照片"), 
				"{var m = grid.getSelections();  "+
					"if(m.length > 0){	         "+
					"	var jsonData = '';       "+
					"	for(var i = 0, len = m.length; i < len; i++){"+
					"		var ss =  m[i].get('id');"+
					"		if(i==0)	jsonData = jsonData + ss ;"+
					"		else	jsonData = jsonData + ',' + ss;"+
					"	}                        "+
					"	document.getElementById('user-defined-content').style.display='none'; "+
					"	document.getElementById('user-defined-content').innerHTML=\"<form target='_blank' action='/entity/studentStatus/peStudentPhoto_showphotos.action' method='post' name='formx1' style='display:none'><input type=hidden name=ids value='\"+jsonData+\"' ></form>\";"+
					"	document.formx1.submit();"+
					"	document.getElementById('user-defined-content').innerHTML=\"\";"+
					"} else {                    "+
//					"	Ext.MessageBox.alert('<s:text name=\"test.error\"/>','<s:text name=\"test.pleaseSelectAtLeastOneItem\"/>');"+
					"Ext.MessageBox.alert('" + this.getText("test.error") + "', '" + this.getText("test.pleaseSelectAtLeastOneItem") + "');  "  +                    
		"}}                         ");
		}
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "regNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("学习中心"), "peSite.name");
		this.getGridConfig().addColumn(this.getText("年级"), "peGrade.name");
		this.getGridConfig().addColumn(this.getText("层次"), "peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"), "peMajor.name");
		ColumnConfig column = new ColumnConfig(this.getText("状态"),"enumConstByFlagStudentStatus.name");
		column.setComboSQL("select id,name from enum_const where namespace='FlagStudentStatus' and code>='4'");
		this.getGridConfig().addColumn(column);		
		this.getGridConfig().addRenderFunction(this.getText("入学毕业照片"),
				"<a href=\"/entity/studentStatus/peStudentPhoto_showphotos.action?ids=${value}\" target=\"_blank\">校对照片</a>", "id");
	}


	@Override
	public void setServletPath() {
		this.servletPath = "/entity/studentStatus/peStudentPhoto";
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.createAlias("prStudentInfo", "prStudentInfo")
			.createAlias("peSite", "peSite")
			.createAlias("peMajor", "peMajor")
			.createAlias("peEdutype","peEdutype")
			.createAlias("peGrade", "peGrade")
			.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")
			.add(Restrictions.ge("enumConstByFlagStudentStatus.code", "4"))
			.createAlias("peRecStudent", "peRecStudent",DetachedCriteria.LEFT_JOIN);
		return dc;
	}
	
	public String showphotos(){
		DetachedCriteria dcStudentInfo = DetachedCriteria.forClass(PeStudent.class);
		String[] ids = this.getIds().split(",");
		dcStudentInfo.createAlias("prStudentInfo", "prStudentInfo")
			.createAlias("peSite", "peSite")
			.createAlias("peEdutype", "peEdutype")
			.createAlias("peMajor", "peMajor")
			.createAlias("peGrade", "peGrade")
			.createAlias("peRecStudent", "peRecStudent",DetachedCriteria.LEFT_JOIN)
			.add(Restrictions.in("id", ids));
		
		java.util.List<PeStudent> prStudentInfoList = null;
		try {
			prStudentInfoList = this.getGeneralService().getList(dcStudentInfo);				
			this.setStudentlist(prStudentInfoList);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "showphotos";
	}
	
	public String photosUpload(){
		return "photosupload";
	}
	
	public String photosUploadexe() throws EntityException {
		File photos = this.get_upload();
		String savePath = "/incoming/photo/graduated/";
		this.setMsg("");
		if (photos != null) {
			FileManage filemanage = FileManageFactory.creat();
			List<String> filenamelist = new ArrayList<String>();
			try {
				filenamelist = filemanage.unzip(photos.getCanonicalPath(), ServletActionContext.getRequest().getRealPath(savePath),true);
				String photosave_temp = "";
				int n = filenamelist.size();
				int faile = 0;
				for (int i = 0; i < n; i++) {
					String msg_temp;
					String fileName = filenamelist.get(i);
					msg_temp = this.doPhoto(savePath,fileName);
					if(msg_temp.length()<=0)
						faile++;
					else{
						new File(ServletActionContext.getRequest().getRealPath(savePath),fileName).delete();
						this.setMsg(this.getMsg()+msg_temp+"<br>");
					}
				}
				this.setMsg(this.getMsg()+photosave_temp + "共上传" + n + "个照片文件，其中上传成功了"
						+ faile + "张");

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
	
	/**
	 * @param savePath 保存路径
	 * @param fileName 文件名字
	 * @return
	 */
	private String doPhoto(String savePath,String fileName){
		String msg="";
		if (fileName != null) {
			if (fileName.toLowerCase().endsWith(".bmp")
					|| fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".gif")) {
				int start = fileName.indexOf('\\')>=0?fileName.lastIndexOf('\\')+1:0;
				if(start==0)
					start = fileName.indexOf('/')>=0?fileName.lastIndexOf('/')+1:0;
				String str = fileName.substring(start,fileName.length() - 4);
				
				DetachedCriteria dc = DetachedCriteria.forClass(PrStudentInfo.class);
				dc.add(Restrictions.eq("cardNo", str));
				try{
					List<PrStudentInfo> list = this.getGeneralService().getList(dc);
					if (list==null||list.size() == 0) {
							msg = "学生中查询不到身份证号为"+str+"(照片"+fileName+")对应的学生。";
					}else{
						//List ids = new ArrayList();
						for (PrStudentInfo prStudentInfo : list) {
						//	ids.add(peStudent.getPrStudentInfo().getId());
							this.getGeneralService().executeBySQL("update pr_student_info set photo_link='"+savePath + "/" + fileName+"',photo_date=sysdate where id ='"+prStudentInfo.getId()+"'");
						}
						//this.getGeneralService().updateColumnByIds(ids, "photoLink",savePath + "/" + fileName);
					}
				}catch (EntityException e) {
					msg = "照片"+fileName+"插入数据库失败。";
					e.printStackTrace();
				}
			} else {
				msg = "文件格式不正确";
			}
		} else {
			msg = "无文件";
		}
		return msg;
	}


	public List getStudentlist() {
		return studentlist;
	}


	public void setStudentlist(List studentlist) {
		this.studentlist = studentlist;
	}
}
