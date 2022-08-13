package com.whaty.platform.entity.web.action.information;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PePolicy;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.EmailSendUtil;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.JsonUtil;

public class PePolicyAction extends MyBaseAction {
    private String person;
    private String cb_id;//承办单位
    private String st_id;//中西部省厅师范处
    private String st2_id;//其他省厅师范处
    private String zxb_id;
    private String jiaoshi_id;
    private String other_id;
    private String major_id;
    private String send;

	@Override
    public void initGrid() {
	    this.getGridConfig().setCapability(false, false, false, true);
		this.getGridConfig().setTitle(this.getText("政策文件"));
		this.getGridConfig().addMenuFunction(this.getText("设为置顶"), "enumConstByFlagIstop",this.getMyListService().getEnumConstByNamespaceCode("FlagIstop", "1").getId());
		this.getGridConfig().addMenuFunction(this.getText("取消置顶"), "enumConstByFlagIstop",this.getMyListService().getEnumConstByNamespaceCode("FlagIstop", "0").getId());
		this.getGridConfig().addMenuFunction(this.getText("设为有效"), "enumConstByFlagIsvalid",this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "1").getId());
		this.getGridConfig().addMenuFunction(this.getText("设为无效"), "enumConstByFlagIsvalid",this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "0").getId());
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("政策文件题目"), "title");
		this.getGridConfig().addColumn(this.getText("是否置顶"), "enumConstByFlagIstop.name", false);
		this.getGridConfig().addColumn(this.getText("是否有效"), "enumConstByFlagIsvalid.name", false);
		this.getGridConfig().addColumn(this.getText("sda"), "enumConstByFlagIsvalid.code", false,false,false,"");
		this.getGridConfig().addColumn(this.getText("发布日期"), "publishDate", false);
		this.getGridConfig().addColumn(this.getText("修改日期"), "updateDate", false);
//		this.getGridConfig().addColumn(this.getText("发布人"), "peManager.name");
		this.getGridConfig().addColumn(this.getText("发布范围"), "scopeString", false, false, false, null);
		this.getGridConfig().addRenderScript("发布到评审专家", 
				"{if((record.data['scopeString']).indexOf('valueExpert')>=0){return '是';}else{return '否'}}", "");
		this.getGridConfig().addRenderScript("发布到承办单位", 
				"{if((record.data['scopeString']).indexOf('unit_')>=0||" +
				"(record.data['scopeString']).indexOf('other_')>=0){return '是';}else{return '否'}}", "");
		this.getGridConfig()
		.addRenderScript("发布到省厅师范处", 
				"{if((record.data['scopeString']).indexOf('st_')>=0){return '是';}else{return '否'}}", "");
		this.getGridConfig().addRenderScript("发布到师范司项目办", 
				"{if((record.data['scopeString']).indexOf('sfsxmb_')>=0){return '是';}else{return '否'}}", "");
		this.getGridConfig().addRenderScript("发布到项目执行办", 
				"{if((record.data['scopeString']).indexOf('xmzxb_')>=0){return '是';}else{return '否'}}", "");
		
			this.getGridConfig().addRenderScript(
					this.getText("详情内容"), 
					"{if( record.data['enumConstByFlagIsvalid.code']=='1') return '<a href=\"pePolicyView_viewDetail.action?bean.id=' +record.data['id']+ '\" target=\"_blank\">查看详细信息</a>'; else return '<a href=pePolicy_edit.action?bean.id=' +record.data['id']+ ' >修改</a>';}", "");
		this.getGridConfig().setCapability(false, true, false, true);
	
    }

    public void setEntityClass() {
    	this.entityClass = PePolicy.class;
    }

    public void setServletPath() {
    	this.servletPath = "/entity/information/pePolicy";
    }

    public String showAddNotice() {
    	return "show_add_notice";
    }

    public String addNotice() throws AddressException {
	    PePolicy policy = new PePolicy();
	    	UserSession userSession = (UserSession) ActionContext.getContext()
			.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
	    policy.setPublishDate(new Date());
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
		if (this.getPerson().indexOf("jspx_")>=0 && this.getJiaoshi_id() != null && !"".equals(this.getJiaoshi_id().trim())) {
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
		if(this.getSend().equals("1")){
			List<String>  emailList = null;
			List<InternetAddress> adreesList = new ArrayList<InternetAddress>(); 
			if(this.getPerson().indexOf("valueExpert")!=-1){
				String sql = "select pe.email from pe_valua_expert pe where pe.email is not null";
				try {
					emailList = this.getGeneralService().getBySQL(sql);
				} catch (EntityException e) {
					e.printStackTrace();
				}
				if(emailList!=null && !emailList.isEmpty()){
					for(int i=0;i<emailList.size();i++){
						adreesList.add(new InternetAddress(emailList.get(i)));
					}
				}
			}
			String[]ids_ = scopeString.toString().trim().split(",");
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
			
			EmailSendUtil.sendMail(adreesList,this.getBean().getTitle(),this.getBean().getNote());
			this.setMsg("您已经成功添加政策文件，系统已把政策文件内容发至相关单位联系人的邮箱");
		}else{
			this.setMsg("您已经成功添加政策文件!");
		}
		
		policy.setScopeString(this.getPerson()+","+scopeString.toString());
		DetachedCriteria dc = DetachedCriteria.forClass(PeManager.class);
		dc.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
		List managers = null;
		try {
		    managers = this.getGeneralService().getList(dc);
		} catch (EntityException e1) {
		    e1.printStackTrace();
		}
		if (!managers.isEmpty()) {
			policy.setPeManager((PeManager) managers.get(0));
		}
		policy.setEnumConstByFlagIstop(this.getMyListService().getEnumConstByNamespaceCode("FlagIstop", this.getBean().getEnumConstByFlagIstop().getCode()));
		policy.setEnumConstByFlagIsvalid(this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", this.getBean().getEnumConstByFlagIsvalid().getCode()));
		policy.setTitle(this.getBean().getTitle());
		policy.setNote(this.getBean().getNote());
		try {
			this.getGeneralService().save(policy);
		    this.setTogo("/entity/information/pePolicy.action");
		} catch (EntityException e) {
		    e.printStackTrace();
		    this.setMsg("操作失败");
		    this.setTogo("back");
		}
		return "message";
    }


    public String edit() {
		try {
		    this.setBean((PePolicy) this.getGeneralService().getById(PePolicy.class,
			    this.getBean().getId()));
		   
		} catch (EntityException e) {
		    e.printStackTrace();
		}
		return "show_edit_notice";
    }
    public String editexe() throws AddressException {
	    PePolicy PePolicy = null;
	    try {
//	    	this.getGeneralService().getGeneralDao().setEntityClass(PePolicy.class);
	    	PePolicy = (PePolicy)this.getGeneralService().getById(PePolicy.class,this.getBean().getId());
		} catch (EntityException e) {
			e.printStackTrace();
		}
		StringBuffer scopeString = new StringBuffer();
		if (this.getPerson().indexOf("cbunit_")>=0 && (this.getCb_id() != null) && (!"".equals(this.getCb_id().trim()))) {
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
		if (this.getPerson().indexOf("jspx_")>=0 && this.getJiaoshi_id() != null && !"".equals(this.getJiaoshi_id().trim())) {
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
		if(this.getSend().equals("1")){
			List<String>  emailList = null;
			List<InternetAddress> adreesList = new ArrayList<InternetAddress>(); 
			if(this.getPerson().indexOf("valueExpert")!=-1){
				String sql = "select pe.email from pe_valua_expert pe";
				try {
					emailList = this.getGeneralService().getBySQL(sql);
				} catch (EntityException e) {
					e.printStackTrace();
				}
				if(emailList!=null && !emailList.isEmpty()){
					for(int i=0;i<emailList.size();i++){
						adreesList.add(new InternetAddress(emailList.get(i)));
					}
				}
			}
			String[]ids_ = scopeString.toString().trim().split(",");
			DetachedCriteria dcEmail = DetachedCriteria.forClass(PeManager.class);
			dcEmail.createAlias("peUnit", "peUnit");
			dcEmail.add(Restrictions.in("peUnit.id", ids_));
			dcEmail.setProjection(Projections.distinct(Property.forName("email")));
			try {
				emailList = this.getGeneralService().getList(dcEmail);
			} catch (EntityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(emailList!=null && !emailList.isEmpty()){
				for(int i=0;i<emailList.size();i++){
					adreesList.add(new InternetAddress(emailList.get(i)));
				}
			}
			EmailSendUtil.sendMail(adreesList,"修改:"+this.getBean().getTitle(),this.getBean().getNote());
			this.setMsg("政策文件信息修改成功，系统已把通告内容发至相关单位联系人的邮箱");
			
		}else{
			this.setMsg("政策文件信息修改成功!");
		}
		PePolicy.setScopeString(this.getPerson()+","+scopeString.toString());
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria dc = DetachedCriteria.forClass(PeManager.class);
		dc.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
		List managers = null;
		try {
		    managers = this.getGeneralService().getList(dc);
		} catch (EntityException e1) {
		    e1.printStackTrace();
		}
		if (!managers.isEmpty()) {
			PePolicy.setPeManager((PeManager) managers.get(0));
		}
			PePolicy.setEnumConstByFlagIstop(this.getMyListService().getEnumConstByNamespaceCode("FlagIstop", this.getBean().getEnumConstByFlagIstop().getCode()));
			PePolicy.setEnumConstByFlagIsvalid(this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", this.getBean().getEnumConstByFlagIsvalid().getCode()));
			PePolicy.setTitle(this.getBean().getTitle());
			PePolicy.setNote(this.getBean().getNote());
			PePolicy.setUpdateDate(new Date());
		try {
			this.getGeneralService().save(PePolicy);
		    this.setTogo("/entity/information/pePolicy.action");
		} catch (EntityException e) {
		    e.printStackTrace();
		    this.setMsg("操作失败");
		    this.setTogo("back");
		}
		return "message";
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
    
    public PePolicy getBean() {
    	return (PePolicy) super.superGetBean();
    }

    public void setBean(PePolicy bean) {
    	super.superSetBean(bean);
    }

    public String getPerson() {
    	return person;
    }

    public void setPerson(String person) {
    	this.person = person;
    }


    public String getMajor_id() {
    	return major_id;
    }

    public void setMajor_id(String major_id) {
    	this.major_id = major_id;
    }



    @Override
    public void checkBeforeDelete(List idList) throws EntityException {
		try {
		    for (Object id : idList) {
			if (((PePolicy) this.getGeneralService().getById(PePolicy.class,
				id.toString())).getEnumConstByFlagIsvalid().getCode()
				.equals("1"))
			    throw new EntityException("改通知公告处于有效状态，删除失败！");
		    }
		} catch (RuntimeException e) {
		    throw new EntityException("删除失败");
		}
    }

    public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria criteria = DetachedCriteria.forClass(PePolicy.class);
		criteria.createAlias("enumConstByFlagIstop", "enumConstByFlagIstop");
		criteria.createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		criteria.createAlias("peManager", "peManager");
		criteria.addOrder(Order.desc("publishDate"));
		return criteria;
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

	public String getOther_id() {
		return other_id;
	}

	public void setOther_id(String other_id) {
		this.other_id = other_id;
	}

	public String getJiaoshi_id() {
		return jiaoshi_id;
	}

	public void setJiaoshi_id(String jiaoshi_id) {
		this.jiaoshi_id = jiaoshi_id;
	}
}
