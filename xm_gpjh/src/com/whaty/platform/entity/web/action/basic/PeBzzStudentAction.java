package com.whaty.platform.entity.web.action.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.service.basic.PeBzzstudentbacthService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeRecruitplan;
import com.whaty.platform.entity.bean.PeTeacher;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.util.RandomString;

/**
 * @param
 * @version 创建时间：2009-6-22 下午03:29:27
 * @return
 * @throws PlatformException
 * 类说明
 */
public class PeBzzStudentAction extends MyBaseAction<PeBzzStudent> {
	
	// 上传所用字段
	private File upload; // 文件
	private String uploadFileName; // 文件名属性
	private String uploadContentType; // 文件类型属性
	private String savePath; // 文件存储位置
	private String batchid ;
	private List<PeBzzBatch> peBzzBatch;
	private List<PeBzzStudent> plist = new ArrayList<PeBzzStudent>();

	
	private PeBzzstudentbacthService peBzzstudentbacthService;



	public PeBzzstudentbacthService getPeBzzstudentbacthService() {
		return peBzzstudentbacthService;
	}

	public void setPeBzzstudentbacthService(
			PeBzzstudentbacthService peBzzstudentbacthService) {
		this.peBzzstudentbacthService = peBzzstudentbacthService;
	}

	public File getUpload() {
		return upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public String getSavePath() {
		return ServletActionContext.getServletContext().getRealPath(savePath);
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		
		this.getGridConfig().setTitle(this.getText("学员列表"));
		
		this.getGridConfig().addColumn(this.getText("ID"),"id",false);
		
		this.getGridConfig().addColumn(this.getText("学号"),"regNo",false,false,true,"textField");
		if(us.getRoleId().equals("3") && !us.getLoginId().equals("gzw"))
		{
			this.getGridConfig().addColumn(this.getText("密码"),"ssoUser.password",false,false,true,"textField");
		}
		this.getGridConfig().addColumn(this.getText("姓名"),"trueName");
		this.getGridConfig().addColumn(this.getText("性别"),"gender",false,true,true,Const.sex_for_extjs);
		this.getGridConfig().addColumn(this.getText("民族"),"folk",false);
		this.getGridConfig().addColumn(this.getText("学历"),"education",false,true,true,Const.edu_for_extjs);
		this.getGridConfig().addColumn(this.getText("出生日期"),"birthdayDate",false);
		this.getGridConfig().addColumn(this.getText("所在学期"),"peBzzBatch.name");
		
		if (us.getRoleId().equals("2") ||us.getRoleId().equals("402880a92137be1c012137db62100006")) {
			ColumnConfig c_name1 = new ColumnConfig(this.getText("所在企业"),
					"peEnterprise.name");
			c_name1
					.setComboSQL("select t.id,t.name as p_name from pe_enterprise t where t.id='"
							+ us.getPriEnterprises().get(0).getId() + "' or t.fk_parent_id='"+us.getPriEnterprises().get(0).getId()+"'");
			this.getGridConfig().addColumn(c_name1);
		}
		else
			this.getGridConfig().addColumn(this.getText("所在企业"),"peEnterprise.name");
		
		//this.getGridConfig().addColumn(this.getText("具体职务"),"position",false,false,false,"TextField",true,25);
		//this.getGridConfig().addColumn(this.getText("职称"),"title",false,true,false,"TextField",true,25);
		//this.getGridConfig().addColumn(this.getText("工作部门"),"department",false,false,false,"TextField",true,100);
		//this.getGridConfig().addColumn(this.getText("通讯地址"),"address",false,false,false,"TextField",true,150);
		
		//this.getGridConfig().addColumn(this.getText("邮编"),"zipcode",false,false,false,Const.zip_for_extjs);
		this.getGridConfig().addColumn(this.getText("办公电话"),"phone",false,true,false,"TextField",true,50);
		this.getGridConfig().addColumn(this.getText("移动电话"),"mobilePhone",false,true,false,"TextField",true,20);
		this.getGridConfig().addColumn(this.getText("电子邮件"),"email",false,true,false,"TextField",true,50);
		
		if (us.getRoleId().equals("3")) {                   
			this.getGridConfig().addMenuFunction("重置密码","pwsd");   //只给总站管理员提供此功能
		}
		this.getGridConfig().addRenderFunction(this.getText("详细信息"), "<a href=\"peDetail_stuviewDetail.action?id=${value}\">查看详细信息</a>", "id");
	}
	
	@SuppressWarnings({ "unchecked", "unchecked", "unchecked" })
	public Map updateColumn() {
		Map map = new HashMap();
		String action = this.getColumn();
		
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			try {
				DetachedCriteria tempdc = DetachedCriteria.forClass(PeBzzStudent.class);
			if(action.equals("pwsd")){
				tempdc.setProjection(Projections.property("ssoUser"));
				tempdc.add(Restrictions.in("id", ids));
				List<SsoUser> sslist = new ArrayList<SsoUser>();
				sslist =this.getGeneralService().getList(tempdc);
				Iterator<SsoUser> iterator = sslist.iterator();
				while(iterator.hasNext()){
					SsoUser ssoUser = iterator.next();
					String pswd = RandomString.getString(8);
					ssoUser.setPassword(pswd);
					this.peBzzstudentbacthService.updateSsoUser(ssoUser);
				}
			}
			
			if(action.equals("info")){
				tempdc.createCriteria("ssoUser", "ssoUser");
				tempdc.add(Restrictions.in("id", ids));
				this.setPlist(this.getGeneralService().getList(tempdc));
			}
				map.put("success", "true");
				map.put("info", ids.length + "条记录操作成功");
			} catch (Exception e) {
				map.clear();
				map.put("success", "false");
				map.put("info", "操作失败");
				return map;
			}
		}else{
			map.clear();
			map.put("success", "false");
			map.put("info", "至少一条数据被选择");
			return map;
		}
		return map;
	}

	public void setEntityClass() {
		this.entityClass=PeBzzStudent.class;

	}

	public void setServletPath() {
		this.servletPath="/entity/basic/peBzzStudent";
	}
	
	public void setBean(PeBzzStudent instance){
		super.superSetBean(instance);
	}
	
	public PeBzzStudent getBean(){
		return super.superGetBean();
	}
	
	public String batch(){
		/**
		 * 取得管理员类型.总站管理员不限制
		 */
		UserSession us = (UserSession)ServletActionContext
			.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String userLoginType = "";//保存管理员类型
		if (us!=null) {
			userLoginType = us.getUserLoginType();
		}
		if(userLoginType.equals("3")||userLoginType.equals("2")){
			DetachedCriteria bathdc = DetachedCriteria.forClass(PeBzzBatch.class);
			bathdc.addOrder(Order.asc("startDate"));
			try {
			this.setPeBzzBatch(this.getGeneralService().getList(bathdc));
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "batch";
		}else{
			this.setMsg("您的权限不够，无法进行此项操作!!!!");
		}
		return "msg";
	}
	
	public String uploadExcel(){
		int count =0;
		try {
			FileInputStream fis = new FileInputStream(getUpload());
			File file = new File(getSavePath().replaceAll("\\\\", "/"));
			 if (!file.exists()) {
				    file.mkdirs();
				   }
			 	String filepath = getSavePath().replaceAll("\\\\", "/") + "/" + getUploadFileName();
			 FileOutputStream fos = new FileOutputStream(getSavePath().replaceAll("\\\\", "/") + "/" + getUploadFileName());
			  int i = 0;
			   byte[] buf = new byte[1024];
			   while ((i = fis.read(buf)) != -1) {
			    fos.write(buf, 0, i);
			   }
			   File filetest = new File(filepath);
			   count = this.peBzzstudentbacthService.Bacthsave(filetest,batchid);
		} catch (Exception e) {
			this.setMsg(e.getMessage());
			return "uploadStudent_result";
		}
		this.setMsg("共" + count + "条数据上传成功！");
		this.setTogo("back");
		return "uploadStudent_result";
	}
	
	
	public DetachedCriteria initDetachedCriteria() {
		
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		
		//获取一级企业下属二级单位
		DetachedCriteria criteria = DetachedCriteria
				.forClass(PeEnterprise.class);
		criteria.setProjection(Projections.property("id"));
		criteria.createCriteria("peEnterprise", "peEnterprise");
		if (!us.getRoleId().equals("3"))
			criteria.add(Expression.eq("peEnterprise.id", us
					.getPriEnterprises().get(0).getId()));
		criteria.add(Expression.neProperty("id", "peEnterprise.id"));
		List idList = new ArrayList();
		try {
			idList = this.getGeneralService().getList(criteria);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzStudent.class);
		dc.createCriteria("ssoUser","ssoUser");
		dc.createCriteria("peBzzBatch", "peBzzBatch");
		if (us.getRoleId().equals("2") ||us.getRoleId().equals("402880a92137be1c012137db62100006")) {
			//添加一级企业id
			idList.add(us.getPriEnterprises().get(0).getId());
			
			dc.createAlias("peEnterprise", "peEnterprise",
					DetachedCriteria.LEFT_JOIN).add(
					Restrictions.in("peEnterprise.id", idList));
//			dc.createAlias("peEnterprise", "peEnterprise",
//					DetachedCriteria.LEFT_JOIN).add(
//					Restrictions.or(Restrictions.in("peEnterprise.id", idList),
//							Restrictions.eq("peEnterprise.id", us.getPriEnterprises().get(0).getId())));
		} 
		else if(us.getRoleId().equals("3"))
			dc.createCriteria("peEnterprise", "peEnterprise");
		else
			dc.createCriteria("peEnterprise", "peEnterprise").add(Restrictions.eq("peEnterprise.id", us.getPriEnterprises().get(0).getId()));
		return dc;
	}
	
	@Override
	public Map add() {
		Map map = new HashMap();
		this.setBean((PeBzzStudent)super.setSubIds(this.getBean()));
		PeBzzStudent instance = null;
		boolean flag = false;
		try {
			String mobile=this.getBean().getMobilePhone();
			String phone=this.getBean().getPhone();
			String email=this.getBean().getEmail();
			/*if((mobile==null||mobile.equals("")) && (phone==null||phone.equals("")))
			{
				map.clear();
				map.put("success", "false");
				map.put("info", "固定电话和移动电话不能都为空，请至少填写一项！");
				return map;
			}
			else
			{
				if(phone!=null && !phone.equals(""))
				{
					if(!phone.matches(Const.telephone))
					{
						map.clear();
						map.put("success", "false");
						map.put("info", "固定电话格式不正确！输入格式：3至4位区号-7至8位直播号码-1至4位分机号。");
						return map;
					}
				}
				if(mobile!=null && !mobile.equals(""))
				{
					if(!mobile.matches(Const.mobile))
					{
						map.clear();
						map.put("success", "false");
						map.put("info", "移动电话格式不正确！请输入正确的移动电话。");
						return map;
					}
				}
			}
			if(email!=null && !email.equals(""))
			{
				if(!email.matches(Const.email))
				{
					map.clear();
					map.put("success", "false");
					map.put("info", "电子邮箱格式不正确！请输入正确的电子邮箱。");
					return map;
				}
			}*/
			instance = this.peBzzstudentbacthService.save(this.getBean());
			map.put("success", "true");
			map.put("info", "添加成功");
			logger.info("添加成功! id= " + instance.getId());
		} catch (Exception e) {
			return super.checkAlternateKey(e, "添加");
		}
		return map;
	}

	public Map delete() {
		Map map = new HashMap();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String str = this.getIds();
			if (str != null && str.length() > 0) {
				String[] ids = str.split(",");
				List idList = new ArrayList();
				for (int i = 0; i < ids.length; i++) {
					idList.add(ids[i]);
				}
				
				DetachedCriteria criteria = DetachedCriteria.forClass(PrBzzTchStuElective.class);
				criteria.createCriteria("peBzzStudent", "peBzzStudent");
				criteria.add(Restrictions.in("peBzzStudent.id", ids));
				
				try {
					List<PrBzzTchStuElective> plist = this.getGeneralService().getList(criteria);
					if(plist.size()>0){
						map.clear();
						map.put("success", "false");
						map.put("info", "所选中学员已经选课,无法删除!");
						return map;
					}
				} catch (EntityException e) {
					e.printStackTrace();
				}
				map.put("success", "true");
				map.put("info", "删除成功");
				try{
					this.peBzzstudentbacthService.deleteByIds(idList);
				}catch(Exception e){
					return super.checkForeignKey(e);
				}

			} else {
				map.put("success", "false");
				map.put("info", "请选择操作项");
			}
		}
		return map;
	}

	public List<PeBzzStudent> getPlist() {
		return plist;
	}

	public void setPlist(List<PeBzzStudent> plist) {
		this.plist = plist;
	}

	public List<PeBzzBatch> getPeBzzBatch() {
		return peBzzBatch;
	}

	public void setPeBzzBatch(List<PeBzzBatch> peBzzBatch) {
		this.peBzzBatch = peBzzBatch;
	}

	public String getBatchid() {
		return batchid;
	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

}
