package com.whaty.platform.entity.web.action.information;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeMeeting;
import com.whaty.platform.entity.bean.PeMeetingResource;
import com.whaty.platform.entity.bean.PeUnit;
import com.whaty.platform.entity.bean.PrMeetPerson;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.EmailSendUtil;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public  class PeMeetingAction extends MyBaseAction{
	   
	private PeMeeting peMeeting;
	private String fk_unit;
	private String meeting_date;
	private String person;
	private String person2;
	private String cb_id;//承办单位
    private String st_id;//中西部省厅师范处
    private String st2_id;//其他省厅师范处
    private String zxb_id;
    private String other_id;
	private String cb2_id;
	private String zxb2_id;
	private String zxst2_id;
	private String qtst2_id;
	private String other2_id;
	private String jiaoshi_id;
	private String jiaoshi2_id;
	//判读是否发送邮件
	private String send;

	@Override
	public void initGrid() {
		
		this.getGridConfig().setTitle(this.getText("会议通知"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("会议名称"), "name");
		this.getGridConfig().addColumn(this.getText("会议时间"), "meetingDate", false);
		this.getGridConfig().addColumn(this.getText("创建时间"), "createDate", false);
//		this.getGridConfig().addColumn(this.getText("发布人"), "peManager.name", false,false,true,"");
		this.getGridConfig().addColumn(this.getText("ad"), "receiprUnit",false,false,false,"");
		this.getGridConfig().addColumn(this.getText("ad"), "resourceUnit",false,false,false,"");
		this.getGridConfig().addColumn(this.getText("发布人所在单位"), "peManager.peUnit.name", false,false,true,"");
		this.getGridConfig().addRenderFunction(this.getText("查看会议内容"), 
				"<a href=\"peMeeting_viewDetail.action?bean.id=${value}\" target=\"_blank\">查看详细信息</a>","id");
		
		if(this.getGridConfig().checkBeforeAddMenu("/entity/programApply/flagPEO_flagPEO.action")){
			this.getGridConfig().setCapability(false, true, false, true);
			this.getGridConfig().addRenderFunction(this.getText("会议资源"), 
					"<a href=\"peMeetingResource.action?bean.peMeeting.id=${value}\" target=\"_self\">资源列表</a>","id");
			this.getGridConfig().addRenderFunction(this.getText("参会人员"), 
					"<a href=\"prMeetPerson.action?bean.peMeeting.id=${value}\" target=\"_self\">人员列表</a>","id");
//			this.getGridConfig().addRenderFunction(this.getText("查看指定填报回执单位"), 
//						"<a href=\"peMeeting_viewUnitReceipt.action?bean.id=${value}\" target=\"_self\">单位列表</a>","id");
//			this.getGridConfig().addRenderFunction(this.getText("查看指定上传资源单位"), 
//						"<a href=\"peMeeting_viewUnitUpload.action?bean.id=${value}\" target=\"_self\">单位列表</a>","id");
			
			this.getGridConfig().addRenderFunction(this.getText("操作"), "<a href=peMeeting_editMeeting.action?bean.id=${value}>修改</a>", "id");
		}else{
			this.getGridConfig().setCapability(false, false, false, true);
			this.getGridConfig().addRenderScript("会议资源", 
					"{if((record.data['resourceUnit']).indexOf('"+getCurrentUser().getPeUnit().getId()+"')>=0)" +
					"{return '<a href=\"peMeetingResource.action?bean.peMeeting.id='+record.data['id'] +'\" target=\"_self\">资源列表</a>';}else{ return '无权操作'}}", "");
			this.getGridConfig().addRenderScript("参会人员", 
					"{if((record.data['receiprUnit']).indexOf('"+getCurrentUser().getPeUnit().getId()+"')>=0)" +
					"{return '<a href=\"prMeetPerson.action?bean.peMeeting.id='+record.data['id'] +'\" target=\"_self\">人员列表</a>';}else{return '无权操作'}}", "");

		}
	}
	public void setEntityClass() {
	    this.entityClass = PeMeeting.class;
	}

        public void setServletPath() {
        	this.servletPath = "/entity/information/peMeeting";
        }

        public String showAddMeeting() {
        	return "show_add_meeting";
        }
       
        //添加会议
        public String toAddMeeting() throws AddressException{
        	PeMeeting meet = new PeMeeting();
        	if(this.getBean()!=null && this.getBean().getId()!=null && !"".equals(this.getBean().getId().trim())){
//        		this.getGeneralService().getGeneralDao().setEntityClass(PeMeeting.class);
        		try {
					meet = (PeMeeting)this.getGeneralService().getById(PeMeeting.class,this.getBean().getId().trim());
				} catch (EntityException e) {
					e.printStackTrace();
				}
        	}
        	PeManager manager=this.getCurrentUser();
        	meet.setCreateDate(new Date());
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        	try {
				meet.setMeetingDate(sdf.parse(this.getMeeting_date()));
			} catch (ParseException e2) {
				e2.printStackTrace();
			}
        	meet.setName(this.getPeMeeting().getName());
        	meet.setNote(this.getPeMeeting().getNote());
        	meet.setPlace(this.getPeMeeting().getPlace());
        	meet.setPeManager(manager);
//        	this.getGeneralService().getGeneralDao().setEntityClass(PeMeeting.class);
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
    		if (this.getPerson().indexOf("other_")>=0 && this.getOther_id() != null && !"".equals(this.getOther_id().trim())) {
    			scopeString.append(this.getOther_id()+",");
    		}
    		if (this.getPerson().indexOf("jspx_")>=0 && this.getJiaoshi_id() != null && !"".equals(this.getJiaoshi_id().trim())) {
    			scopeString.append(this.getJiaoshi_id()+",");
    		}
    		if (this.getPerson().indexOf("xmzxb_")>=0){
    			String sql="select pu.id from pe_unit pu where pu.fk_unit_type ='afdsfqeradfasdf123413'";
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
    		meet.setReceiprUnit(this.getPerson()+","+scopeString.toString());
        	
    		StringBuffer scopeString2 = new StringBuffer();
    		if (this.getPerson2().indexOf("cbunit_")>=0 && this.getCb2_id() != null && !"".equals(this.getCb2_id().trim())) {
    		    scopeString2.append(this.getCb2_id()+",");
    		}
    		if (this.getPerson2().indexOf("zxbunit_")>=0 && this.getZxb2_id() != null && !"".equals(this.getZxb2_id().trim())) {
    		    scopeString2.append(this.getZxb2_id()+",");
    		}
    		if (this.getPerson2().indexOf("zxbst_")>=0 && this.getZxst2_id() != null && !"".equals(this.getZxst2_id().trim())) {
    			scopeString2.append(this.getZxst2_id()+",");
    		 }
    		if (this.getPerson2().indexOf("qtst_")>=0 && this.getQtst2_id() != null && !"".equals(this.getQtst2_id().trim())) {
    			scopeString2.append(this.getQtst2_id()+",");
    		}
    		if (this.getPerson2().indexOf("other_")>=0 && this.getOther2_id() != null && !"".equals(this.getOther2_id().trim())) {
    			scopeString2.append(this.getOther2_id()+",");
    		}
    		if (this.getPerson2().indexOf("jspx_")>=0 && this.getJiaoshi2_id() != null && !"".equals(this.getJiaoshi2_id().trim())) {
    			scopeString2.append(this.getJiaoshi2_id()+",");
    		}
    		if (this.getPerson2().indexOf("xmzxb_")>=0){
    			String sql="select pu.id from pe_unit pu where pu.fk_unit_type='afdsfqeradfasdf123413'";
    			List<String> idList = null;
    			try {
    				idList = this.getGeneralService().getBySQL(sql);
    			} catch (EntityException e) {
    				e.printStackTrace();
    			}
    			if(idList!=null && !idList.isEmpty()){
    				for(int i=0;i<idList.size();i++){
    					scopeString2.append(idList.get(i)+",");
    				}
    			}
    		}
    		if (this.getPerson2().indexOf("sfsxmb_")>=0){
    			String sql="select pu.id from pe_unit pu where pu.fk_unit_type='afdsfqeradfasdf123412'";
    			List<String> idList = null;
    			try {
    				idList = this.getGeneralService().getBySQL(sql);
    			} catch (EntityException e) {
    				e.printStackTrace();
    			}
    			if(idList!=null && !idList.isEmpty()){
    				for(int i=0;i<idList.size();i++){
    					scopeString2.append(idList.get(i)+",");
    				}
    			}
    		}
    		meet.setResourceUnit(this.getPerson2()+","+scopeString2.toString());
        	
        	if(this.getSend().equals("1")){
	    		List<String>  emailList = null;
	    		StringBuffer emailString = new StringBuffer();
	    		List<InternetAddress> adreesList = new ArrayList<InternetAddress>(); 
	        	if(scopeString.indexOf("valueExpert")>=0||scopeString2.indexOf("valueExpert")>=0){
		        	String sql = "SELECT DISTINCT PE.EMAIL FROM PE_VALUA_EXPERT PE where pe.email is not null";
		        	try {
						emailList = this.getGeneralService().getBySQL(sql);
					} catch (EntityException e) {
						e.printStackTrace();
					}
					if(emailList!=null && !emailList.isEmpty()){
						for(int i=0;i<emailList.size();i++){
							adreesList.add(new InternetAddress(emailList.get(i)));
							emailString.append(emailList.get(i)+";");
						}
					}
	        	}
	        	String[]ids_ = scopeString.append(scopeString2).toString().trim().split(",");
				DetachedCriteria dcEmail = DetachedCriteria.forClass(PeManager.class);
				dcEmail.createAlias("peUnit", "peUnit");
				dcEmail.add(Restrictions.in("peUnit.id", ids_));
				dcEmail.add(Restrictions.isNotNull("email"));
				dcEmail.setProjection(Projections.distinct(Property.forName("email")));
				try {
					emailList = this.getGeneralService().getList(dcEmail);
				} catch (EntityException e1) {
					e1.printStackTrace();
				}
				if(emailList!=null && !emailList.isEmpty()){
					for(int i=0;i<emailList.size();i++){
						adreesList.add(new InternetAddress(emailList.get(i)));
					}
				}
	        	StringBuffer content = new StringBuffer();
	        	content.append("会议时间"+this.getMeeting_date()+"<br>");
	        	content.append("会议地点："+this.getPeMeeting().getPlace()+"<br>");
	        	content.append("会议内容"+this.getPeMeeting().getNote());
	        	
	        	if(this.getBean()!=null && this.getBean().getId()!=null && !"".equals(this.getBean().getId().trim())){
	        		EmailSendUtil.sendMail(adreesList,"修改："+this.getPeMeeting().getName(), content);
	        		this.setMsg("您已经成功修改一个会议，并已经以邮件形式将会议内容发到单位联系人的邮箱！");//：\n"+emailString.toString());
	        	}else{
	        		EmailSendUtil.sendMail(adreesList,this.getPeMeeting().getName(), content);
		        	this.setMsg("您已经成功添加一个会议，并已经以邮件形式将会议内容发到单位联系人的邮箱！");//：\n"+emailString.toString());
	        	}
        	}else{
        		this.setMsg("会议添加成功！");
        		if(this.getBean()!=null && this.getBean().getId()!=null && !"".equals(this.getBean().getId().trim())){
        			this.setMsg("会议修改成功！");
        		}
        	}
        	try{
        		this.getGeneralService().save(meet);
        		
        	}catch(Exception e){
        		this.setMsg("操作失败！");
        		e.printStackTrace();
        	}
        	
        	this.setTogo("/entity/information/peMeeting.action");
        	return "msg";
        	
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
        @Override
		public DetachedCriteria initDetachedCriteria() {
        	DetachedCriteria dc = DetachedCriteria.forClass(PeMeeting.class);
        	DetachedCriteria dcManager = dc.createCriteria("peManager", "peManager");
        	dcManager.createAlias("peUnit", "peUnit");
        	UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
        	if(!us.getRoleType().equals("4")){
        		dc.add(Restrictions.or(Restrictions.like("receiprUnit", getCurrentUser().getPeUnit().getId(),MatchMode.ANYWHERE), 
        				Restrictions.like("resourceUnit", getCurrentUser().getPeUnit().getId(),MatchMode.ANYWHERE)));
        	}
        	return dc;
		}
		//查看会议详细内容
        public String viewDetail(){
    		DetachedCriteria dcPeMeeting = DetachedCriteria.forClass(PeMeeting.class);
    		dcPeMeeting.add(Restrictions.eq("id", this.getBean().getId()));
    		try {
    			List list = this.getGeneralService().getList(dcPeMeeting);
    			if(list!=null&&list.size()>0){
	    			PeMeeting meeting=(PeMeeting) list.get(0);
	    			this.setPeMeeting(meeting);
    			}
    		} catch (EntityException e) {
    			e.printStackTrace();
    		}	
        
        return "view_meeting_detail";
        }
        //修改会议
        public String editMeeting(){
//        	this.getGeneralService().getGeneralDao().setEntityClass(PeMeeting.class);
        	PeMeeting meet = null;
        	try{
        		meet = (PeMeeting)this.getGeneralService().getById(PeMeeting.class,this.getBean().getId());
                this.setPeMeeting(meet); 
        		
        	}catch (EntityException e){
        		e.printStackTrace();
        	}	
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        	this.setMeeting_date(sdf.format(meet.getMeetingDate()));
        	return "show_add_meeting";
        }
        //删除
//        public void checkBeforeDelete(List idList){
//        	this.getGeneralService().getGeneralDao().setEntityClass(PeMeeting.class);
//            PeMeeting meet=null;
//        	try{
//            	for(int i=0;i<idList.size();i++){
//            		meet=(PeMeeting)this.getGeneralService().getById(idList.get(i).toString());
//            	}
//            }catch(Exception e1){
//            	e1.printStackTrace();
//            }
//            DetachedCriteria dc1 = DetachedCriteria.forClass(PeMeetingResource.class); 
//            dc1.createAlias("peMeeting","peMeeting");
//            dc1.add(Restrictions.eq("peMeeting",meet));
//            try{
//            	List list = this.getGeneralService().getList(dc1);
//            	if(list!=null&&list.size()>0)
//            	for(int i=0;i<list.size();i++){
//            		PeMeetingResource meetingresource = (PeMeetingResource)list.get(i);
//            		this.getGeneralService().delete(meetingresource);
//            	}
//            }catch(Exception e2){
//            	e2.printStackTrace();
//            }
//            DetachedCriteria dc2 = DetachedCriteria.forClass(PrMeetPerson.class);
//            dc2.createAlias("peMeeting", "peMeeting");
//            dc2.add(Restrictions.eq("peMeeting", meet));
//            try{
//            	List list2 = this.getGeneralService().getList(dc2);
//            	if(list2!=null&&list2.size()>0)
//            		for(int j=0;j<list2.size();j++){
//            		PrMeetPerson person = (PrMeetPerson)list2.get(j);
//            		this.getGeneralService().delete(person);
//            	}
//            }catch(Exception e3){
//            	e3.printStackTrace();
//            }
//        }
        //查看指定的填报回执单位
        public String viewUnitReceipt(){
        	return "view_assinedUnit_toReceipt";
        }
        public List getReceiptUnit() {
        	PeMeeting meet = null;
        	List idList = new ArrayList(); 
        	try {
				 meet= (PeMeeting) this.getGeneralService().getById(PeMeeting.class,this.getBean().getId());
			} catch (EntityException e) {
				e.printStackTrace();
			}
            try {
            	
            	StringBuilder strb=new StringBuilder(meet.getReceiprUnit());
            	if(strb.toString().endsWith(",")){
            		strb.deleteCharAt(strb.length()-1);
            	}
            	String[] ids=strb.toString().split(",");
            	StringBuilder units=new StringBuilder();
			    for(int i=0;i<ids.length;i++){
			    	units.append("'"+ids[i].trim()+"',");
			    }
			    String sql="select t.id,t.name from pe_unit t where t.id in("+units+"'')";
			    idList=this.getGeneralService().getBySQL(sql);
			} catch (EntityException e) {
				e.printStackTrace();
			}		
			return idList;
        }
        //查看上传会议资源的单位
        public String viewUnitUpload(){
        	return "view_assinedUnit_toUpload";
        }
        public List getResourcUnit() {
        	PeMeeting meet = null;
        	List idList = new ArrayList(); 
        	try {
				 meet= (PeMeeting) this.getGeneralService().getById(PeMeeting.class,this.getBean().getId());
			} catch (EntityException e) {
				e.printStackTrace();
			}
            try {
            	
            	StringBuilder strb=new StringBuilder(meet.getResourceUnit());
            	if(strb.toString().endsWith(",")){
            		strb.deleteCharAt(strb.length()-1);
            	}
            	String[] ids=strb.toString().split(",|\\||:");
            	StringBuilder units=new StringBuilder();
			    for(int i=0;i<ids.length;i++){
			    	units.append("'"+ids[i].trim()+"',");
			    }
//			    units.delete(units.length());
			    String sql="select t.id,t.name from pe_unit t where t.code in("+units+"'')";
			    idList=this.getGeneralService().getBySQL(sql);
			} catch (EntityException e) {
				e.printStackTrace();
			}		
			return idList;
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
        public void setBean(PeMeeting instance) {
    		super.superSetBean(instance);

    	}

    	public PeMeeting getBean() {
    		return (PeMeeting) super.superGetBean();
    	}
		public String getCb_id() {
			return cb_id;
		}
		public void setCb_id(String cb_id) {
			this.cb_id = cb_id;
		}
		public String getCb2_id() {
			return cb2_id;
		}
		public void setCb2_id(String cb2_id) {
			this.cb2_id = cb2_id;
		}
		public String getPerson() {
			return person;
		}
		public void setPerson(String person) {
			this.person = person;
		}
		public String getPerson2() {
			return person2;
		}
		public void setPerson2(String person2) {
			this.person2 = person2;
		}

		public String getZxst2_id() {
			return zxst2_id;
		}
		public void setZxst2_id(String zxst2_id) {
			this.zxst2_id = zxst2_id;
		}
		public String getQtst2_id() {
			return qtst2_id;
		}
		public void setQtst2_id(String qtst2_id) {
			this.qtst2_id = qtst2_id;
		}
		public String getSend() {
			return send;
		}
		public void setSend(String send) {
			this.send = send;
		}
		public String getZxb_id() {
			return zxb_id;
		}
		public void setZxb_id(String zxb_id) {
			this.zxb_id = zxb_id;
		}
		public String getZxb2_id() {
			return zxb2_id;
		}
		public void setZxb2_id(String zxb2_id) {
			this.zxb2_id = zxb2_id;
		}
		public String getFk_unit() {
			return fk_unit;
		}
		public void setFk_unit(String fk_unit) {
			this.fk_unit = fk_unit;
		}

		public String getMeeting_date() {
			return meeting_date;
		}
		public void setMeeting_date(String meeting_date) {
			this.meeting_date = meeting_date;
		}
		public PeMeeting getPeMeeting() {
			return peMeeting;
		}
		public void setPeMeeting(PeMeeting peMeeting) {
			this.peMeeting = peMeeting;
		}
		public String getOther_id() {
			return other_id;
		}
		public void setOther_id(String other_id) {
			this.other_id = other_id;
		}
		public String getOther2_id() {
			return other2_id;
		}
		public void setOther2_id(String other2_id) {
			this.other2_id = other2_id;
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
		public String getJiaoshi_id() {
			return jiaoshi_id;
		}
		public void setJiaoshi_id(String jiaoshi_id) {
			this.jiaoshi_id = jiaoshi_id;
		}
		public String getJiaoshi2_id() {
			return jiaoshi2_id;
		}
		public void setJiaoshi2_id(String jiaoshi2_id) {
			this.jiaoshi2_id = jiaoshi2_id;
		}
	
}
