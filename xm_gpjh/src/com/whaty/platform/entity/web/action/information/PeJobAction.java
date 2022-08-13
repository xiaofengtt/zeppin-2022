package com.whaty.platform.entity.web.action.information;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeJob;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PrJobUnit;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.EmailSendUtil;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PeJobAction extends MyBaseAction {
	private String jobName;
	private Date startDate;
	private Date finishDate;
	private String jobNote;
	private String enum1;
    private String person;
    private String cb_id;//承办单位
    private String st_id;//中西部省厅师范处
    private String st2_id;//其他省厅师范处
    private String zxb_id;
    private String other_id;
    private String jiaoshi_id;
	private PeJob peJob;
	private String scopeString;
	private String send;
	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("任务列表"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("任务名称"), "name");
		this.getGridConfig().addColumn(this.getText("任务开始时间"), "startDate", false);
		this.getGridConfig().addColumn(this.getText("任务截止时间"), "finishDate", false);
		this.getGridConfig().addColumn(this.getText("发布单位"), "peManager.peUnit.name");
		this.getGridConfig().addColumn(this.getText("任务优先级"), "enumConstByFkJobPriority.name");
		this.getGridConfig().addRenderFunction(this.getText("查看详细信息"), "<a href='/entity/information/peJob_viewDetail.action?ids=${value}' target='_blank'>查看</a>", "id");
		if(this.getGridConfig().checkBeforeAddMenu("/entity/programApply/flagPEO_flagPEO.action")){
			this.getGridConfig().setCapability(false, true, false, true);
			this.getGridConfig().addRenderFunction(this.getText("操作"), "<a href='peJob_editJob.action?ids=${value}'>修改</a>", "id");
			this.getGridConfig().addRenderFunction(this.getText("查看任务完成状况"), "<a href=\"prJobUnitInit.action?bean.peJob.id=${value}\" target=\"_self\">进入</a>","id");
		}else{
			this.getGridConfig().setCapability(false, false, false, true);
			this.getGridConfig().addRenderFunction(this.getText("提交任务资料"), "<a href=\"prJobUnitInit.action?bean.peJob.id=${value}\" target=\"_self\">进入</a>","id");
		}
		
	}
	public String showAddJob() {
		return "show_add_job";
	}

	public String addJob() {
		PeJob job = new PeJob();
		if(this.getBean()!=null && this.getBean().getId()!=null && !"".equals(this.getBean().getId().trim())){
			try {
//				this.getGeneralService().getGeneralDao().setEntityClass(PeJob.class);
				job = (PeJob) this.getGeneralService().getById(PeJob.class,this.getBean().getId());
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		job.setName(this.getJobName());
		job.setNote(this.getJobNote());
		job.setFinishDate(this.getFinishDate());
		job.setStartDate(this.getStartDate());
		job.setPeManager(getCurrentUser());
//		this.getGeneralService().getGeneralDao().setEntityClass();
		EnumConst enu = (EnumConst) this.getMyListService().getById(EnumConst.class,this.getEnum1());
		job.setEnumConstByFkJobPriority(enu);
		
		StringBuffer scopeString = new StringBuffer();
		if (this.getPerson().indexOf("cbunit_")>=0 && this.getCb_id() != null && !"".equals(this.getCb_id().trim())) {
		    scopeString.append(this.getCb_id()+",");
		}
		if (this.getPerson().indexOf("zxbunit_")>=0 && this.getZxb_id() != null && !"".equals(this.getZxb_id().trim())) {
		    scopeString.append(this.getZxb_id()+",");
		}
		if (this.getPerson().indexOf("zxbst_")>=0 && this.getSt_id() != null && !"".equals(this.getSt_id().trim())) {
			scopeString.append(this.getSt_id()+",");
		 }
		if (this.getPerson().indexOf("qtst_")>=0 && this.getSt2_id() != null && !"".equals(this.getSt2_id().trim())) {
			scopeString.append(this.getSt2_id()+",");
		}
		if (this.getPerson().indexOf("other_")>=0 && this.getOther_id() != null && !"".equals(this.getOther_id().trim())) {
			scopeString.append(this.getOther_id()+",");
		}
		if (this.getPerson().indexOf("jspx__")>=0 && this.getJiaoshi_id() != null && !"".equals(this.getJiaoshi_id().trim())) {
			scopeString.append(this.getJiaoshi_id()+",");
		}
		if (this.getPerson().indexOf("xmzxb_")>=0){
			String sql="select pu.id from pe_unit pu where pu.fk_unit_type='afdsfqeradfasdf123413'";
			List<String> idList = null;
			try {
				idList = this.getGeneralService().getBySQL(sql);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			if(idList!=null && !idList.isEmpty()){
				for(int i=0;i<idList.size();i++){
					scopeString.append(idList.get(i)+",");
				}
			}
		}
		if (this.getPerson().indexOf("sfsxmb_")>=0){
			String sql="select pu.id from pe_unit pu where pu.fk_unit_type='afdsfqeradfasdf123412'";
			List<String> idList = null;
			try {
				idList = this.getGeneralService().getBySQL(sql);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			if(idList!=null && !idList.isEmpty()){
				for(int i=0;i<idList.size();i++){
					scopeString.append(idList.get(i)+",");
				}
			}
		}
		job.setScopeString(this.getPerson()+","+scopeString.toString());
		
		
		this.getGeneralService().getGeneralDao().setEntityClass(PeJob.class);
		try {
			this.getGeneralService().save(job);
			if(this.getSend().equals("1")){
				String[]ids_ = scopeString.toString().trim().split(",");
				DetachedCriteria dcEmail = DetachedCriteria.forClass(PeManager.class);
				dcEmail.createAlias("peUnit", "peUnit");
				dcEmail.add(Restrictions.in("peUnit.id", ids_));
				dcEmail.add(Restrictions.isNotNull("email"));
				dcEmail.setProjection(Projections.distinct(Property.forName("email")));
				List<String> emailList = null;
				try {
					emailList = this.getGeneralService().getList(dcEmail);
				} catch (EntityException e1) {
					e1.printStackTrace();
				}
				List<InternetAddress> adreesList = new ArrayList<InternetAddress>(); 
				if(emailList!=null && !emailList.isEmpty()){
					for(int i=0;i<emailList.size();i++){
						adreesList.add(new InternetAddress(emailList.get(i)));
					}
				}
	        	StringBuffer content = new StringBuffer();
	        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        	content.append("任务开始时间"+sdf.format(this.getStartDate())+"<br>");
	        	content.append("任务开始时间："+sdf.format(this.getFinishDate())+"<br>");
	        	content.append("任务内容:"+this.getJobNote()+"<br>");
	        	content.append("<br>");
	        	content.append("<br>");
	        	content.append("<br>");
	        	content.append("发送单位："+job.getPeManager().getPeUnit().getName()+"<br>");
	        	content.append(sdf.format(new Date()));
				EmailSendUtil.sendMail(adreesList, this.getJobName(),content);
				this.setMsg("任务分配成功，并已以邮件的形式发送给相关单位！");
			}else{
				this.setMsg("任务分配成功！");
			}
//			String sql = "delete from ( select t.* from pr_job_unit t join pe_unit p on t.fk_unit=p.id where t.fk_job = :jobId and p.code not in ("+idString+"''))";
//			Map map1 = new HashMap();
//			map1.put("jobId", job.getId());
//			this.getGeneralService().executeBySQL(sql, map1);
//			List<PeUnit>  unitList = this.getUnitById(job,codeString);
//			for (int i = 0; i < unitList.size(); i++) {
//				PeUnit unit = unitList.get(i);
//				PrJobUnit jobUnit = new PrJobUnit();
//				jobUnit.setPeJob(job);
//				jobUnit.setPeUnit(unit);
//				jobUnit.setEnumConstByFkJobCheck(this.getMyListService()
//						.getEnumConstByNamespaceCode("FkJobCheck", "1805"));
//				jobUnit.setEnumConstByFkJobStatus(this.getMyListService()
//						.getEnumConstByNamespaceCode("FkJobStatus", "1806"));
//				try {
//					this.getGeneralService().save(jobUnit);
//				} catch (Exception e) {
//					e.printStackTrace();
//					this.setMsg("存储失败！");
//				}
			
			this.setTogo("/entity/information/peJob.action");
		} catch (Exception e) {
			this.setMsg("任务分配失败！");
			e.printStackTrace();
			this.setTogo("/entity/information/peJob_showAddJob.action");
		}
		return "msg";
	}
	public List getJobPriority() {
		String sql = "select id,name from enum_const where namespace = 'FkJobPriority'";
		List jobPriorityList = new ArrayList();
		try {
			jobPriorityList = this.getGeneralService().getBySQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobPriorityList;
	}
	
	// 修改任务
	public String editJob() {
		this.getGeneralService().getGeneralDao().setEntityClass(PeJob.class);
		try {
			PeJob job = (PeJob) this.getGeneralService().getById(this.getIds());
			this.setPeJob(job);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "show_add_job";
	}
//	public void checkBeforeDelete(List idList){
//		String ids[] = new String[idList.size()];
//		for(int i=0;i<idList.size();i++){
//			ids[i] = idList.get(i).toString();
//		}
//		DetachedCriteria dc = DetachedCriteria.forClass(PrJobUnit.class);
//		dc.createAlias("peJob", "peJob");
//		dc.add(Restrictions.in("peJob.id", ids));
//		dc.setProjection(Projections.distinct(Property.forName("id")));
//		try {
//			List list=this.getGeneralService().getList(dc);
//			this.getGeneralService().getGeneralDao().setEntityClass(PrJobUnit.class);
//			this.getGeneralService().deleteByIds(list);
//		} catch (EntityException e) {
//			e.printStackTrace();
//		}
//	}
		public PeJob getBean() {
			return (PeJob) super.superGetBean();
		}
		public void setEntityClass() {
			 this.entityClass = PeJob.class;
		     }
		
		 public void setServletPath() {
			 this.servletPath = "/entity/information/peJob";
		 }
			
		 public void setBean(PeJob instance) {
				super.superSetBean(instance);
		}
		@Override
		public DetachedCriteria initDetachedCriteria() {
	    	DetachedCriteria dc=DetachedCriteria.forClass(PeJob.class);
	    	dc.createAlias("enumConstByFkJobPriority", "enumConstByFkJobPriority",1);
//	    	if(this.getPeUnit().getEnumConstByFkUnitType().getCode().equals("1526")){
//	    		DetachedCriteria expcetdc = DetachedCriteria.forClass(PrJobUnit.class);
//				expcetdc.createCriteria("peUnit", "peUnit");
//				expcetdc.createCriteria("peJob", "peJob");
//				expcetdc.add(Restrictions.eq("peUnit", getPeUnit()));
//				expcetdc.setProjection(Projections.distinct(Property.forName("peJob.id")));
//		    	dc.add(Subqueries.propertyIn("id", expcetdc));
//	    	}
	    	dc.createCriteria("peManager", "peManager")
	    		.createAlias("peUnit", "peUnit");
	    	UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
	    	if(!us.getRoleType().equals("4")){
        		dc.add(Restrictions.like("scopeString", getCurrentUser().getPeUnit().getId(),MatchMode.ANYWHERE));
        	}
	    	dc.addOrder(Order.desc("startDate"));
	    	return dc;
		}
		 //得到当前用户的名字
        public PeManager getCurrentUser(){
        	UserSession usersession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
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
		public String viewDetail(){
//			List list = null;
			try {
				PeJob job = (PeJob)this.getGeneralService().getById(this.getIds());
//				 list = this.getGeneralService().getBySQL("select to_char(t.note) from pe_job t where t.id = '"+this.getIds()+"'");
				this.setBean(job);
			} catch (EntityException e) {
				e.printStackTrace();
			}
//			if(list!=null&&!list.isEmpty()){
//				String msg = (String)list.get(0);
//				this.setNoteTitle("任务详细信息");
//				this.setNote(msg);
//			}
			return "viewJob";
		}
		 //设置承办单位范围
	    public List getCbScop(){
	    	List cbScopList = new ArrayList();
	    	try{
	    		String sql = "select  p.id,p.name from pe_unit p join enum_const e  on e.id=p.fk_unit_type join enum_const ec on p.flag_isvalid=ec.id where e.code='1526' and ec.code='1' order by p.name";
	    		cbScopList=this.getGeneralService().getBySQL(sql);
	    	}catch(EntityException e){
	    		e.printStackTrace();
	    	}
	    	return cbScopList;
	    	
	    }
	  //设置中西部
	    public List getZxbScop(){
	    	List cbScopList = new ArrayList();
	    	try{
	    		String sql = "select  p.id,p.name from pe_unit p join enum_const e  on e.id=p.fk_unit_type join enum_const ec on p.flag_isvalid=ec.id where e.code='1527' and ec.code='1' order by p.name";
	    		cbScopList=this.getGeneralService().getBySQL(sql);
	    	}catch(EntityException e){
	    		e.printStackTrace();
	    	}
	    	return cbScopList;
	    	
	    }
	    //设置中西部省厅师范处范围
	    public List getStScop(){
	    	List stScopList = new ArrayList();
	    	try{
	    		String sql ="select  p.id,p.name from pe_unit p join enum_const e  on e.id=p.fk_unit_type join enum_const ec on p.flag_isvalid=ec.id where e.code='1525' and ec.code='1' and p.fk_province not in('402880962a28b056012a28b062e40001',"
	              +"'402880962a2c1b88012a2c1b96670001','402880962a2c18d8012a2c18e5700001','402880962a2c1a81012a2c1a8eda0001','402880962a3d36bd012a3d36cdee0003','402880962a2c1d52012a2c1d5f7f0001',"
	        +"'402880962a28b132012a28b13f0b0001','402880142a10492e012a104a1d7b0001','402880962a28b199012a28b1a6220001') order by p.name";
	    		stScopList = this.getGeneralService().getBySQL(sql);
	    	}catch(EntityException e){
	    		e.printStackTrace();
	    	}
	    	return stScopList;
	    }
	    //设置其他省厅师范处范围
	    public List getQtstScop(){
	    	List qtstScopList= null;
	    	String sql = "select  p.id,p.name from pe_unit p join enum_const e  on e.id=p.fk_unit_type join enum_const ec on p.flag_isvalid=ec.id where e.code='1525' and ec.code='1' and p.fk_province in('402880962a28b056012a28b062e40001',"
	            +"'402880962a2c1b88012a2c1b96670001','402880962a2c18d8012a2c18e5700001','402880962a2c1a81012a2c1a8eda0001','402880962a3d36bd012a3d36cdee0003','402880962a2c1d52012a2c1d5f7f0001',"
	            +"'402880962a28b132012a28b13f0b0001','402880142a10492e012a104a1d7b0001','402880962a28b199012a28b1a6220001')order by p.name ";
	    	try {
				qtstScopList = this.getGeneralService().getBySQL(sql);
			} catch (EntityException e) {
				e.printStackTrace();
			}
	    	return qtstScopList;
	    }
	    
	    public List getOtherScop(){
	    	List otherScopList= null;
	    	String sql = "select  p.id,p.name from pe_unit p join enum_const e  on e.id=p.fk_unit_type join enum_const ec on p.flag_isvalid=ec.id where e.code='1528' and ec.code='1' order by p.name";
	    	try {
	    		otherScopList = this.getGeneralService().getBySQL(sql);
			} catch (EntityException e) {
				e.printStackTrace();
			}
	    	return otherScopList;
	    }
	    public List getJiaoshiScop(){
	    	List otherScopList= null;
	    	String sql = "select  p.id,p.name from pe_unit p join enum_const e  on e.id=p.fk_unit_type join enum_const ec on p.flag_isvalid=ec.id where e.code='1529' and ec.code='1' order by p.name";
	    	try {
	    		otherScopList = this.getGeneralService().getBySQL(sql);
			} catch (EntityException e) {
				e.printStackTrace();
			}
	    	return otherScopList;
	    }
		public String getJobName() {
			return jobName;
		}
		public void setJobName(String jobName) {
			this.jobName = jobName;
		}
		public Date getStartDate() {
			return startDate;
		}
		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}
		public Date getFinishDate() {
			return finishDate;
		}
		public void setFinishDate(Date finishDate) {
			this.finishDate = finishDate;
		}
		public String getJobNote() {
			return jobNote;
		}
		public void setJobNote(String jobNote) {
			this.jobNote = jobNote;
		}
		public String getEnum1() {
			return enum1;
		}
		public void setEnum1(String enum1) {
			this.enum1 = enum1;
		}
		public String getPerson() {
			return person;
		}
		public void setPerson(String person) {
			this.person = person;
		}
		public String getCb_id() {
			return cb_id;
		}
		public void setCb_id(String cb_id) {
			this.cb_id = cb_id;
		}
		public String getSt_id() {
			return st_id;
		}
		public void setSt_id(String st_id) {
			this.st_id = st_id;
		}
		public String getSt2_id() {
			return st2_id;
		}
		public void setSt2_id(String st2_id) {
			this.st2_id = st2_id;
		}
		public String getZxb_id() {
			return zxb_id;
		}
		public void setZxb_id(String zxb_id) {
			this.zxb_id = zxb_id;
		}
		public String getOther_id() {
			return other_id;
		}
		public void setOther_id(String other_id) {
			this.other_id = other_id;
		}
		public PeJob getPeJob() {
			return peJob;
		}
		public void setPeJob(PeJob peJob) {
			this.peJob = peJob;
		}
		public String getScopeString() {
			return scopeString;
		}
		public void setScopeString(String scopeString) {
			this.scopeString = scopeString;
		}
		public String getSend() {
			return send;
		}
		public void setSend(String send) {
			this.send = send;
		}
		public String getJiaoshi_id() {
			return jiaoshi_id;
		}
		public void setJiaoshi_id(String jiaoshi_id) {
			this.jiaoshi_id = jiaoshi_id;
		}
}
