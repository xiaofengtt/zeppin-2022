package com.whaty.platform.entity.web.action.programApply;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeProApply;
import com.whaty.platform.entity.bean.PeProApplyno;
import com.whaty.platform.entity.bean.PeSubject;
import com.whaty.platform.entity.bean.PeUnit;
import com.whaty.platform.entity.bean.PrProgramUnit;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.service.programApply.ProgramApplyService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

/**
 * 承办单位 提交申报材料
 * 
 * @author 侯学龙
 *
 */
public class PeProApplyBookAction extends MyBaseAction {
	
	private GeneralService generalService;
	
	private ProgramApplyService programApplyService;
	
	private PeUnit peUnit;
	
	private PeSubject peSubject;
	
	private PeProApplyno peProApplyno;
	
	private File upload;
	
	private File upload2;
	
	private String uploadFileName; // 文件名属性
	
	private String upload2FileName; // 文件名属性
	
	private String savePath; // 文件存储位置
	
	private List proApplyList;
	
	/**
	 * 初始化所属单位
	 */
	private void initPeUnit(){
		ActionContext ctx = ActionContext.getContext();
		UserSession userSession = (UserSession)ctx.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria dc = DetachedCriteria.forClass(PeManager.class);
		dc.createAlias("ssoUser", "ssoUser").add(Restrictions.eq("ssoUser.id", userSession.getSsoUser().getId()));
		dc.createAlias("peUnit", "peUnit");
		try {
			List managerList = this.getGeneralService().getDetachList(dc);
			PeManager manager = (PeManager)managerList.get(0);
			this.setPeUnit(manager.getPeUnit());
		} catch (EntityException e) {
			e.printStackTrace();
		}
//		this.setPeUnit(userSession.getManager().getPeUnit());
	}
	
	/**
	 * 初始化已经提交的申报书
	 */
	private void initPeProApply(){
		ActionContext ctx = ActionContext.getContext();
		UserSession userSession = (UserSession)ctx.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
//		PeManager manager = userSession.getManager();
		DetachedCriteria dc = DetachedCriteria.forClass(PeProApply.class);
		dc.createAlias("peProApplyno", "peProApplyno");
		dc.createAlias("peSubject", "peSubject");
		dc.createAlias("peUnit", "peUnit");
		dc.createAlias("enumConstByFkCheckResultProvince", "enumConstByFkCheckResultProvince");
		dc.createAlias("enumConstByFkCheckNational", "enumConstByFkCheckNational");
//		dc.add(Restrictions.eq("peProApplyno.year", Const.getYear()));
		dc.addOrder(Order.desc("declareDate"));
		try {
			List peManagerList = this.getGeneralService().getByHQL("from PeManager p where p.ssoUser = '"+userSession.getSsoUser().getId()+"'");
			PeManager manager = (PeManager)peManagerList.get(0);
			dc.add(Restrictions.eq("peUnit", manager.getPeUnit()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		try {
			List list = this.getGeneralService().getList(dc);
//			PeProApply apply = (PeProApply)list.get(0);
			this.setProApplyList(list);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String writeBook(){
		this.initPeUnit();
		this.initPeProApply();
		return "write_Book";
	}

	public String saveBookInfo(){
//		this.setTogo("back");
		boolean flag = false;
		PeProApply peProApply = new PeProApply();
		try {
			List subjectList = this.getGeneralService().getByHQL("from PeSubject t where t.name = '"+this.getPeSubject().getName()+"'");
			if(subjectList != null && !subjectList.isEmpty()){
				peProApply.setPeSubject((PeSubject)subjectList.get(0));
			}
		} catch (EntityException e1) {
			this.setMsg("您选择的申报学科有误，请重新选择！");
		}
		this.initPeUnit();
		peProApply.setPeUnit(this.getPeUnit());
		
		DetachedCriteria expcetdc = DetachedCriteria.forClass(PrProgramUnit.class);
		expcetdc.createCriteria("peUnit", "peUnit");
		expcetdc.add(Restrictions.eq("peUnit.id", this.getPeUnit().getId()));
		expcetdc.createAlias("peProApplyno", "peProApplyno");
		expcetdc.setProjection(Projections.distinct(Property.forName("peProApplyno.id")));
		
		DetachedCriteria dcPeProApplyno = DetachedCriteria.forClass(PeProApplyno.class);
		dcPeProApplyno.createAlias("enumConstByFkProvinceCheck", "enumConstByFkProvinceCheck");
		dcPeProApplyno.add(Restrictions.eq("name", this.getPeProApplyno().getName()));
		dcPeProApplyno.add(Subqueries.propertyIn("id", expcetdc));
		PeProApplyno peProApplyno = null;
		try {
			List listPeApplyno = this.getGeneralService().getList(dcPeProApplyno);
			if(listPeApplyno != null && !listPeApplyno.isEmpty()){
				peProApplyno = (PeProApplyno)listPeApplyno.get(0);
			}else{
				this.setMsg("您选择的培训项目有误，请重新选择！");
				return this.writeBook();
			}
		} catch (EntityException e1) {
			this.setMsg("您选择的培训项目有误，请重新选择！");
			return this.writeBook();
		}
		peProApply.setPeProApplyno(peProApplyno);
		
		//判断是否已经提交过
		DetachedCriteria dc = DetachedCriteria.forClass(PeProApply.class);
		dc.createAlias("peProApplyno", "peProApplyno");
		dc.createAlias("peSubject", "peSubject");
		dc.createAlias("peUnit", "peUnit");
		dc.add(Restrictions.eq("peProApplyno", peProApply.getPeProApplyno()));
		dc.add(Restrictions.eq("peSubject", peProApply.getPeSubject()));
		dc.add(Restrictions.eq("peUnit", peProApply.getPeUnit()));
		dc.addOrder(Order.desc("declareDate"));
		PeProApply peProApply2 = new PeProApply();
		try {
			List listPeProApply = this.getGeneralService().getList(dc);
			if(listPeProApply!=null&&!listPeProApply.isEmpty()){
				peProApply = (PeProApply)listPeProApply.get(0);
				
				if(peProApply.getEnumConstByFkCheckFirst()!=null&&peProApply.getEnumConstByFkCheckFirst().getCode().equals("1011")){
					this.setMsg("您的项目已经初审通过，无法再次提交！");
					return this.writeBook();
				}
				else if(peProApply.getEnumConstByFkCheckFirst()!=null&&peProApply.getEnumConstByFkCheckFirst().getCode().equals("1012")){
					this.setMsg("您的项目初审未通过，无法再次提交！");
					return this.writeBook();
				}else if((peProApply.getEnumConstByFkCheckFirst()!=null&&peProApply.getEnumConstByFkCheckFirst().getCode().equals("1013"))
						||(peProApply.getEnumConstByFkCheckFirst()!=null&&peProApply.getEnumConstByFkCheckFirst().getCode().equals("1014"))){
					flag = true;
					//peProApply.setId(null);
					peProApply2 = peProApply;
					peProApply = new PeProApply();
					peProApply.setDeclaration(peProApply2.getDeclaration());
					
					//设置省厅审核结果 项目执行办审核结果 和原来的一样
					peProApply.setEnumConstByFkCheckResultProvince(peProApply2.getEnumConstByFkCheckResultProvince());
					peProApply.setEnumConstByFkCheckNational(peProApply2.getEnumConstByFkCheckNational());
//					peProApply.setNoteFirst(peProApply2.getNoteFirst());
					peProApply.setPeProApplyno(peProApply2.getPeProApplyno());
					peProApply.setPeSubject(peProApply2.getPeSubject());
					peProApply.setPeUnit(peProApply2.getPeUnit());
					
					//设置初审结果 未审核，终审结果未审核
					EnumConst enumConstByFkCheckFirst = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FkCheckFirst", "1010");
					peProApply.setEnumConstByFkCheckFirst(enumConstByFkCheckFirst);
					EnumConst enumConstByFkCheckFinal = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FkCheckFinal", "1000");
					peProApply.setEnumConstByFkCheckFinal(enumConstByFkCheckFinal);
					
					this.setMsg("重新提交-"+peProApply.getPeSubject().getName()+"-学科成功！");
				}else{
					if((peProApply.getEnumConstByFkCheckResultProvince().getCode().equals("1")||peProApply.getEnumConstByFkCheckResultProvince().getCode().equals("2"))||
							(!peProApply.getEnumConstByFkCheckNational().getCode().equals("0"))){
							this.setMsg("您的项目不允许再次提交！");
							return this.writeBook();
					}
				}
			}else{
				//设置初始状态0：未审核  3：不需要省级审核
				EnumConst enumConstByFkCheckResultProvince = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FkCheckResultProvince", "0");
				if(peProApplyno.getEnumConstByFkProvinceCheck().getCode().equals("0")){
					enumConstByFkCheckResultProvince = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FkCheckResultProvince", "3");
				}
				peProApply.setEnumConstByFkCheckResultProvince(enumConstByFkCheckResultProvince);
				//设置初始状态0：未审核
				EnumConst enumConstByFkCheckNational = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FkCheckNational", "0");
				peProApply.setEnumConstByFkCheckNational(enumConstByFkCheckNational);
				
				
				EnumConst enumConstByFkCheckFirst = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FkCheckFirst", "1010");
				peProApply.setEnumConstByFkCheckFirst(enumConstByFkCheckFirst);
				
				EnumConst enumConstByFkCheckFinal = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FkCheckFinal", "1000");
				peProApply.setEnumConstByFkCheckFinal(enumConstByFkCheckFinal);
				
				if(!checkPeProApplyBookSize(peProApply)){
					return this.writeBook();
				}
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		peProApply.setDeclareDate(new Date());
		
		
		
		if(this.getUpload()!=null){
			this.setMsg("申报书上传失败！");
			if ((this.getUploadFileName().toLowerCase().endsWith(".doc"))||(this.getUploadFileName().toLowerCase().endsWith(".pdf"))) {
				String fileEndName = this.getUploadFileName().substring(this.getUploadFileName().lastIndexOf("."));
				String shenbaoshu = "申报书"+fileEndName;
				if(flag){
					String fileLink = peProApply.getDeclaration()+"";
					String move = "0";
					if((fileLink.indexOf("[")>0)&&(fileLink.indexOf("]")>0)){
						 move = fileLink.substring(fileLink.lastIndexOf("[")-1, fileLink.lastIndexOf("]")); 
					}
					int moveNo = Integer.parseInt(move)+1;
					shenbaoshu = "申报书["+moveNo+"]"+fileEndName;
				}
				
				String filename = peProApplyno.getCode() + this.getPeSubject().getName()+this.getPeUnit().getName()+peProApplyno.getYear()+"年"+shenbaoshu;
				String photoLink = this.getSavePath() + "/" + filename;
				try {
					FileOutputStream fos = new FileOutputStream(photoLink);
					FileInputStream fis = new FileInputStream(this.getUpload());
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = fis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
					fis.close();
					fos.close();
					this.setMsg("申报书上传成功！");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (this.getMsg().equals("申报书上传成功！")) {
					peProApply.setDeclaration(savePath + "/" + filename);
				}
			}else{
				this.setMsg("申报书格式不正确！");
				return this.writeBook();
			}
		}
		if(this.getUpload2()!=null){
			this.setMsg("实施方案上传失败！");
			if ((this.getUpload2FileName().toLowerCase().endsWith(".doc"))||(this.getUpload2FileName().toLowerCase().endsWith(".pdf"))) {
				String fileEndName = this.getUpload2FileName().substring(this.getUpload2FileName().lastIndexOf("."));
				String fangan = "实施方案"+fileEndName;
				if(flag){
					String fileLink = peProApply.getScheme()+"";
					String move = "0";
					if((fileLink.indexOf("[")>0)&&(fileLink.indexOf("]")>0)){
						 move = fileLink.substring(fileLink.lastIndexOf("[")-1, fileLink.lastIndexOf("]")); 
					}
					int moveNo = Integer.parseInt(move)+1;
					fangan = "实施方案["+moveNo+"]"+fileEndName;
				}
				String filename = peProApplyno.getCode() + this.getPeSubject().getName()+this.getPeUnit().getName()+peProApplyno.getYear()+"年"+fangan;
				String photoLink = this.getSavePath() + "/" + filename;
				try {
					FileOutputStream fos = new FileOutputStream(photoLink);
					FileInputStream fis = new FileInputStream(this.getUpload2());
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = fis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
					fis.close();
					fos.close();
					this.setMsg("申报书和实施方案上传成功！");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (this.getMsg().equals("申报书和实施方案上传成功！")) {
					peProApply.setScheme(savePath + "/" + filename);
				}
			}else{
				this.setMsg("实施方案格式不正确！");
				return this.writeBook();
			}
		}
			if(flag){
				//需修改的项目 上传要保留原来的附件 arg1 peProApply2 原来的，arg2 peProApply:新的
				this.getProgramApplyService().savePrProExpert(peProApply2, peProApply);
			}else{
				try {
					this.getGeneralService().save(peProApply);
				} catch (EntityException e) {
					e.printStackTrace();
				}
			}
		
		return this.writeBook();
	}
	
	/**
	 * 判断是否符合提交的最高限制 
	 * @param peProApply
	 * @return
	 */
	private boolean checkPeProApplyBookSize(PeProApply peProApply){
		DetachedCriteria dc = DetachedCriteria.forClass(PeProApply.class);
		dc.createAlias("peProApplyno", "peProApplyno");
		dc.createAlias("peUnit", "peUnit");
		dc.add(Restrictions.eq("peProApplyno", peProApply.getPeProApplyno()));
		dc.add(Restrictions.eq("peUnit", peProApply.getPeUnit()));
		try {
			List listPeProApply = this.getGeneralService().getList(dc);
			if(listPeProApply==null||listPeProApply.size()<peProApply.getPeProApplyno().getLimit()){
				return true;
			}else{
				this.setMsg("您申报的学科已超过-"+peProApply.getPeProApplyno().getLimit()+"科-的最高限制，无法继续申报！");
				return false;
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String delPeProApplyBook(){
		DetachedCriteria dc = DetachedCriteria.forClass(PeProApply.class);
		dc.createAlias("enumConstByFkCheckResultProvince", "enumConstByFkCheckResultProvince");
		dc.createAlias("enumConstByFkCheckNational", "enumConstByFkCheckNational");
		dc.add(Restrictions.eq("id", this.getIds()));
		List bookList = null;
		try {
			bookList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		PeProApply apply = (PeProApply)bookList.get(0);
		if(apply.getEnumConstByFkCheckResultProvince().getCode().equals("1")
				||apply.getEnumConstByFkCheckResultProvince().getCode().equals("2")){
			this.setMsg("省厅师范处已经审核，无法完成删除操作！");
		}else if(apply.getEnumConstByFkCheckResultProvince().getCode().equals("3")
				&&(apply.getEnumConstByFkCheckNational().getCode().equals("1"))){
			this.setMsg("项目执行办已经审核，无法完成删除操作！");
		}else{
			try {
				File file1 = new File(ServletActionContext.getServletContext().getRealPath("/")+apply.getDeclaration());
				if(file1.exists()){
					file1.delete();
				}
				File file2 = new File(ServletActionContext.getServletContext().getRealPath("/")+apply.getScheme());
				if(file2.exists()){
					file2.delete();
				}
				this.getGeneralService().delete(apply);
				this.setMsg("删除成功！");
			} catch (EntityException e) {
				this.setMsg("删除失败！");
			}
		}
		return this.writeBook();
	}
	@Override
	public void initGrid() {

	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PeProApply.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/programApply/peProApplyBook";
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

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public GeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}

	public PeSubject getPeSubject() {
		return peSubject;
	}

	public void setPeSubject(PeSubject peSubject) {
		this.peSubject = peSubject;
	}

	public PeProApplyno getPeProApplyno() {
		return peProApplyno;
	}

	public void setPeProApplyno(PeProApplyno peProApplyno) {
		this.peProApplyno = peProApplyno;
	}
	public String getSavePath() {
		return ServletActionContext.getRequest().getRealPath(savePath);
	}

	public String getSavePath(String path) {
		return ServletActionContext.getRequest().getRealPath(path);
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public File getUpload2() {
		return upload2;
	}
	public void setUpload2(File upload2) {
		this.upload2 = upload2;
	}
	public String getUpload2FileName() {
		return upload2FileName;
	}
	public void setUpload2FileName(String upload2FileName) {
		this.upload2FileName = upload2FileName;
	}
	public List getProApplyList() {
		return proApplyList;
	}
	public void setProApplyList(List proApplyList) {
		this.proApplyList = proApplyList;
	}

	public ProgramApplyService getProgramApplyService() {
		return programApplyService;
	}

	public void setProgramApplyService(ProgramApplyService programApplyService) {
		this.programApplyService = programApplyService;
	}
}
