package com.whaty.platform.entity.web.action.study;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeAnswers;
import com.whaty.platform.entity.bean.PeQuestions;
import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.PeTeacher;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.PrTchTraineeCourse;
import com.whaty.platform.entity.bean.PrTraingCourse;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.JsonUtil;

public class PeQuestionsAction extends MyBaseAction {


	private int totalPage; 
	private int curPage = 1;
	private int totalSize;
	private static int SIZE_PER_PAGE = 10;
	
	private String detail;//用户留言或回复的内容 
	private String title;//用户留言标题
	private String questionId;//留言ID，保存回复留言时用得到
	
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle("课程答疑管理");
		this.getGridConfig().addColumn(this.getText(""),"id",false);
		ColumnConfig cc=new ColumnConfig(this.getText("提问者"),"combobox_d.q_name");
		cc.setComboSQL("select id,true_name from pe_trainee");
		cc.setSearch(true);
		this.getGridConfig().addColumn(cc);
		this.getGridConfig().addColumn(this.getText("标题"),"title");
//		this.getGridConfig().addColumn(this.getText("内容"),"detail",false,false,false,null);
		this.getGridConfig().addColumn(this.getText("提问时间"),"q_date",false,false,true,"date",false,10);
		this.getGridConfig().addColumn(this.getText("课程"),"c_name");
		ColumnConfig ccfg=new ColumnConfig(this.getText("是否解决"),"combobox_s.flag_solve");
		ccfg.setComboSQL("select id,name from enum_const where namespace='FlagSolve'");
		ccfg.setSearch(true);
		this.getGridConfig().addColumn(ccfg);
		ColumnConfig ccf=new ColumnConfig(this.getText("回复人"),"combobox_sa.a_name");
		ccf.setComboSQL("select id,true_name from pe_manager union select id,true_name from pe_teacher");
		ccf.setSearch(true);
		this.getGridConfig().addColumn(ccf);
//		this.getGridConfig().addColumn(this.getText("回复内容"),"a_detail",false,false,false,null);
		this.getGridConfig().addColumn(this.getText("回复时间"),"a_date",false,false,true,"date",false,10);
		
		this.getGridConfig().addMenuFunction(this.getText("删除"), "/entity/basic/messageBoardAction_delQuesById.action", false, true);
		
		this.getGridConfig().addRenderFunction(this.getText("查看回答"), "<a href='#' onclick=\"window.open('/entity/basic/messageBoardAction_getMsgDetail.action?ids=${value}','','height=300,width=600,left=350,top=200')\">查看</a>", "id");
		
		StringBuilder script = new StringBuilder();
		script.append(" {");
		script.append(" 	 var m = grid.getSelections();");
		script.append("    	 var ids = '';");
		script.append("    	 if(m.length == 1){");
		script.append("    	 		for(var i=0,len =m.length;i<len;i++ ){");
		script.append("    	 			var ss = m[i].get('id');");
		script.append("    	 			if(i==0)ids = ids+ ss ;");
		script.append("    	 			else ids = ids +','+ss;");
		script.append("    	 		}");
		script.append("    	 }else{");
		script.append("    	 	Ext.MessageBox.alert('错误','只能选择一个问题进行回复'); return;");
		script.append("    	 }");
		script.append("        var planname = new Ext.form.TextArea({fieldLabel: '回复内容*',name: 'bean.detail',allowBlank:false,anchor: '90%',regex:new RegExp(/^([^\\s\\'\\\"\\&]|[^\\s\\'\\\"\\&][^\\'\\\"\\&]*[^\\s\\\'\\\"\\&])$/),regexText:'输入格式：不能以空格开头和结尾、不能包含单引号、双引号及&符号'});\n ");
		script.append("        var fids = new Ext.form.Hidden({name:'bean.id',value:ids});");
		script.append("    	 var roomformPanel = new Ext.form.FormPanel({");
		script.append(" 	    frame:true,");
		script.append("         labelWidth: 100,");
		script.append("        	defaultType: 'textfield',");
		script.append(" 				autoScroll:true,reader: new Ext.data.JsonReader({root: 'models'},['bean.id','bean.name']),");
		script.append("         items: [ planname ,fids]");
		script.append("       });  ");
//		script.append("        roomformPanel.form.load({url:'"+this.getDetailAction()+"?bean.id='+ids,waitMsg:'Loading'});  ");
		script.append("        var saveroomModelWin = new Ext.Window({");
		script.append("        title: '对问题进行回复',");
		script.append("        width: 450,");
		script.append("        height: 225,");
		script.append("        minWidth: 300,");
		script.append("        minHeight: 250,");
		script.append("        layout: 'fit',");
		script.append("        plain:true,");
		script.append("        bodyStyle:'padding:5px;',");
		script.append("        buttonAlign:'center',");
		script.append("        items: roomformPanel,buttons: [{");
		script.append(" 	            text: '保存',");
		script.append(" 	            handler: function() {");
		script.append(" 	                if (roomformPanel.form.isValid()) {");
		script.append(" 		 		        roomformPanel.form.submit({");
		script.append(" 		 		        	url:'/entity/basic/messageBoardAction_reply.action?' ,");
		script.append(" 				            waitMsg:'处理中，请稍候...',");
		script.append(" 							success: function(form, action) {");
		script.append(" 							    var responseArray = action.result;");
		script.append(" 							    if(responseArray.success=='true'){");
		script.append(" 							    	Ext.MessageBox.alert('提示', responseArray.info);");
		script.append(" 							    	store.load({params:{start:g_start,limit:g_limit"+this.getGridConfig().getFieldsFilter()+"}});                      ");//+this.getGridConfig().getFieldsFilter()+"
		script.append(" 								    saveroomModelWin.close();");
		script.append(" 							    } else {");
		script.append(" 							    	Ext.MessageBox.alert('错误', responseArray.info );");
		script.append(" 							    }");
		script.append(" 							}");
		script.append(" 				        });");
		script.append(" 	                } else{");
		script.append(" 						Ext.MessageBox.alert('错误', '输入错误，请先修正提示的错误');");
		script.append(" 					}");
		script.append(" 		        }");
		script.append(" 	        },{");
		script.append(" 	            text: '取消',");
		script.append(" 	            handler: function(){saveroomModelWin.close();}");
		script.append(" 	        }]");
		script.append("        });");
		script.append("        saveroomModelWin.show();");
		script.append("  }");

		this.getGridConfig().addMenuScript(this.getText("回复问题"), script.toString());
		this.getGridConfig().setPrepared(false);
	}

	@Override
	public void setEntityClass() {
		this.entityClass=PeQuestions.class;
		
	}

	public String delQuesById(){
		Map map=new HashMap();
		map.put("success", "false");
		map.put("info", "删除失败");
		boolean flag=true;
		int succ=0;
		String[] ids=this.getIds().split(",");
		for(int i=0;i<ids.length;i++){
			String id=ids[i];
			String sql_check=new String("select id from pe_answers where fk_ques_id=:ids");
			Map map_check=new HashMap();
			map_check.put("ids", id);
			List list=new ArrayList();
			try {
				list = this.getGeneralService().getBySQL(sql_check,map_check);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(list!=null&&list.size()>0){
				continue;
			}else{
				String sql_del=new String("delete from pe_questions where id=:ids");
				Map map_del=new HashMap();
				map_del.put("ids", id);
				try {
					int j=this.getGeneralService().executeBySQL(sql_del, map_del);
					if(j>0){
						succ++;
					}
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if(succ==ids.length){
			map.put("success", "true");
			map.put("info", "删除成功");
		}else if(succ>0){
			map.put("success", "true");
			map.put("info", succ+"个问题删除成功,"+(ids.length-succ)+"个失败");
		}else{
			map.put("success", "false");
			map.put("info", "删除失败");
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc=DetachedCriteria.forClass(PeQuestions.class);
		dc.createCriteria("enumConstByFlagSolve","enumConstByFlagSolve");
		dc.createCriteria("peTrainee","peTrainee");
		dc.createCriteria("peTchCourse","peTchCourse").add(Restrictions.eq("name", "留言板"));

		return dc;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/study/peQuestionsAction";
	}
	
	
	
	@Override
	public void checkBeforeDelete(List idList) throws EntityException {
		for(int i=0;i<idList.size();i++){
			String ids=(String) idList.get(0);
			String sql="select ques.title from pe_answers ans join pe_questions ques on ans.fk_ques_id=ques.id where ques.id=:ids";
			Map map=new HashMap();
			map.put("ids", ids);
			List list=new LinkedList();
			list=this.getGeneralService().getBySQL(sql,map);
			if(list!=null&&list.size()>0){
				String title=(String) list.get(0);
				throw new EntityException(title+" 已被回复，不能删除");
			}
		}
		
	}

	/**
	 * 在后台对问题进行回复
	 * @return
	 */
	public String reply(){//先查询是否回复，然后保存
		Map map=new HashMap();
		map.put("success", "false");
		String id=this.getBean().getId();
		String detail=this.getBean().getDetail();
		String sql="select id from pe_answers where fk_ques_id=:ids";
		Map map_sql=new HashMap();
		map_sql.put("ids", id);
		List list=new LinkedList();
		try {
			list=this.getGeneralService().getBySQL(sql, map_sql);
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(list!=null&&list.size()>0){
			map.put("info", "该问题已经回答过，不能再回答");
			this.setJsonString(JsonUtil.toJSONString(map));
			return this.json();
		}
			
		boolean flag=true;
		PeQuestions ques=new PeQuestions();
		this.getGeneralService().getGeneralDao().setEntityClass(PeQuestions.class);
		try {
			ques=(PeQuestions) this.getGeneralService().getById(id);
			if(ques!=null){
				ques.setEnumConstByFlagSolve(this.getMyListService().getEnumConstByNamespaceCode("FlagSolve", "1"));
			}
			ques=(PeQuestions) this.getGeneralService().save(ques);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			flag=false;
			e.printStackTrace();
		}
		PeAnswers ans=new PeAnswers();
		ans.setCreateDate(new Date());
		ans.setDetail(detail);
		ans.setPeQuestions(ques);
		ans.setSsoUser(this.getSsoUserOnline());
		ans.setTitle(ques.getTitle());
		this.getGeneralService().getGeneralDao().setEntityClass(PeAnswers.class);
		try {
			this.getGeneralService().save(ans);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			flag=false;
			e.printStackTrace();
		}
		if(flag){
			map.put("success", "true");
			map.put("info", "回复成功");
		}else{
			map.put("info", "false");
			map.put("info", "回复失败");
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}

	public void setBean(PeQuestions instance) {
		super.superSetBean(instance);
		
	}
	
	public PeQuestions getBean(){
		return  (PeQuestions)super.superGetBean();
	}
	/**
	 * 获取学员的已学习课程关系列表
	 * 
	 * @return
	 */
	private List getMyPrTraingCoursePage() {
		
		UserSession userSession = (UserSession) ActionContext.getContext()
				.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		DetachedCriteria dc = DetachedCriteria.forClass(PeTrainee.class);
		dc.createCriteria("peTrainingPlan", "peTrainingPlan",
				DetachedCriteria.LEFT_JOIN);
		dc.add(Restrictions.eq("ssoUser", ssoUser));
		List peTraineeList = new ArrayList();
		try {
			peTraineeList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
//		this.setPeTrainee((PeTrainee) peTraineeList.get(0));

		DetachedCriteria dcp = DetachedCriteria.forClass(PrTraingCourse.class);//用于查询培训计划的全部课程
		dcp.createCriteria("peTchCourse", "peTchCourse");
		dcp.createCriteria("peTeacher", "peTeacher",
				DetachedCriteria.LEFT_JOIN);
		dcp.createCriteria("enumConstByFlagCourseType", "enumConstByFlagCourseType",
						DetachedCriteria.LEFT_JOIN);
//		dcp.add(Restrictions.eq("peTrainingPlan", this.getPeTrainee()
//				.getPeTrainingPlan()));
		
		DetachedCriteria dcq=DetachedCriteria.forClass(PrTchTraineeCourse.class);
		dcq.createCriteria("peTchCourse","peTchCourse");
//		dcq.add(Restrictions.eq("peTrainee", this.getPeTrainee()));

		List ptcList = new ArrayList();//培训计划中的全部课程
		List deqList = new ArrayList();//学员已学的所有课程
		try {
			this.getGeneralService().getGeneralDao().setEntityClass(
					PrTraingCourse.class);
			ptcList = this.getGeneralService().getList(dcp);
			
			this.getGeneralService().getGeneralDao().setEntityClass(PrTchTraineeCourse.class);
			deqList=this.getGeneralService().getList(dcq);
			
//			for(int i=0;i<ptcList.size();i++){
//				PrTraingCourse ptc=(PrTraingCourse) ptcList.get(i);
//				PeTchCourse course=ptc.getPeTchCourse();
//				for(int j=0;j<deqList.size();j++){
//					PrTraineeCourse ptrc=(PrTraineeCourse) deqList.get(j);
//					PeTchCourse course1=ptrc.getPeTchCourse();
//					if(course.getId().equals(course1.getId())){
//						ptcList.remove(i);
//						break;
//					}
//				}
//			}
			List idList=new LinkedList();//学员已学课程ID列表
			for(int i=0;i<deqList.size();i++){
				PrTchTraineeCourse ptc=(PrTchTraineeCourse) deqList.get(i);
				idList.add(ptc.getPeTchCourse().getId());
			}
			
			int size = deqList.size();//学员的已学课程数
			this.setTotalSize(size);
			if(size == 0) {
				this.setTotalPage(1);
			} else if(size % this.SIZE_PER_PAGE == 0) {
				this.setTotalPage(size/this.SIZE_PER_PAGE);
			} else {
				this.setTotalPage(size/this.SIZE_PER_PAGE + 1);
			}
			
			dcp.add(Restrictions.in("peTchCourse.id", idList));
			Page p = this.getGeneralService().getByPage(dcp, this.SIZE_PER_PAGE, (curPage - 1) * this.SIZE_PER_PAGE);
			ptcList = p.getItems();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return ptcList;
	}
	//zhaoyuxiao begin
	
	private List messageList;//所有留言列表
	/**
	 * 返回所有留言列表
	 */
	public String getMessages(){
//		DetachedCriteria dc=DetachedCriteria.forClass(PeQuestions.class);
//		dc.createCriteria("peTchCourse","peTchCourse");
//		dc.createCriteria("peTrainee","peTrainee");
//		dc.add(Restrictions.eq("peTchCourse.name","留言板"));
//		dc.addOrder(Order.desc("createDate"));
//		List list=new LinkedList();
//		try {
//			list=this.getGeneralService().getList(dc);
//		} catch (EntityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		StringBuffer strb=new StringBuffer();
		
		String sql_count=new String("select ques.id id from pe_questions ques join pe_tch_course course on ques.fk_course_id=course.id where fk_trainee_id=:userId and course.name='留言板' union select ques.id from pe_questions ques join pe_answers ans on ans.fk_ques_id=ques.id join pe_tch_course course on ques.fk_course_id=course.id where course.name='留言板'");
		
		strb.append(" select a.id id,a.title title,ee.true_name eeName,a.c_date c_date,if(tcr.true_name is null && mgr.true_name is null,'未回答','已回答') ifAnsr,ifnull(tcr.true_name,mgr.true_name) tName,ans.create_date r_date from (                                                    ");
		 strb.append(" select ques.id id,ques.title title,ques.fk_trainee_id ee_id,ques.create_date c_date from pe_questions ques join pe_tch_course course on ques.fk_course_id=course.id where fk_trainee_id=:userId and course.name='留言板'   ");
		 strb.append(" union                                                                                                                                                                                                                    ");
		 strb.append(" select ques.id id,ques.title title,ques.fk_trainee_id ee_id,ques.create_date c_date from pe_questions ques join pe_answers ans on ans.fk_ques_id=ques.id) a                                                              ");
		 strb.append(" left outer join pe_answers ans on a.id=ans.fk_ques_id                                                                                                                                                                    ");
		 strb.append(" left outer join sso_user sso on ans.fk_sso_user=sso.id                                                                                                                                                                   ");
		 strb.append(" left outer join pe_teacher tcr on tcr.fk_sso_user_id=sso.id                                                                                                                                                              ");
		 strb.append(" left outer join pe_manager mgr on mgr.fk_sso_user_id=sso.id                                                                                                                                                              ");
		 strb.append(" left outer join pe_trainee ee on a.ee_id=ee.id                                                                                                                                                                           ");
		 strb.append(" order by a.c_date desc                                                                                                                                                                                                  ");
		
		Map map=new HashMap();
		map.put("userId", this.getSsoTrainee().getId());
		
		List list=new LinkedList();
		try {
			list = this.getGeneralService().getBySQL(sql_count,map);
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int size=list.size();
		this.setTotalSize(size);

		if(size == 0) {
			this.setTotalPage(1);
		} else if(size % this.SIZE_PER_PAGE == 0) {
			this.setTotalPage(size/this.SIZE_PER_PAGE);
		} else {
			this.setTotalPage(size/this.SIZE_PER_PAGE + 1);
		}
		Page page=null;
		try {
//			page = this.getGeneralService().getByPage(dc, this.getSIZE_PER_PAGE(), this.getSIZE_PER_PAGE()*(this.getCurPage()-1));
			page=this.getGeneralService().getByPageSQL(strb.toString(), this.getSIZE_PER_PAGE(), this.getSIZE_PER_PAGE()*(this.getCurPage()-1),map);
			
			list=page.getItems();
			this.setMessageList(list);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "messageList";
	}

	@Override
	public Page list() {
		StringBuffer strb=new StringBuffer();
		strb.append(" select ques.id id,ee.true_name q_name,ques.title title,                                                        ");
		strb.append(" ques.create_date q_date,course.name c_name,enu.name flag_solve,nvl(tcr.true_name,mgr.true_name) a_name,                                        ");
//		strb.append(" concat('ques_detail','') q_detail,ques.create_date q_date,enu.name flag_solve,ifnull(tcr.true_name,mgr.true_name) a_name,concat('','and_detail') a_detail,                                                        ");
		strb.append(" ans.create_date a_date   from                                                                                   ");
		strb.append(" (select id,title,create_date,fk_trainee_id,flag_solve,fk_course_id from   pe_questions)ques                           ");
		strb.append(" left outer join ( select id,fk_sso_user,create_date,fk_ques_id from pe_answers )ans on ans.fk_ques_id=ques.id         ");                                                                                                                             
		strb.append(" left outer join pe_trainee ee on ee.id=ques.fk_trainee_id                                                                                      ");                                                                      
		strb.append(" left outer join sso_user sso on sso.id=ans.fk_sso_user                                                                                         ");                                                                      
		strb.append(" left outer join pe_manager mgr on mgr.fk_sso_user_id=sso.id                                                                                    ");                                                                      
		strb.append(" left outer join pe_teacher tcr on tcr.fk_sso_user_id=sso.id                                                                                    ");                                                                      
		strb.append(" left outer join enum_const enu on enu.id=ques.flag_solve                                                                                       ");                                                                      
		strb.append(" join pe_tch_course course on course.id=ques.fk_course_id                                                                                       ");                                                                      
		strb.append(" where course.name!='留言板'                                                                                                                     ");                                        
		strb.append(" order by q_date desc                                                                                                                           ");
		  
		this.setSqlCondition(strb);
		Page page=null;
		try {
			page=this.getGeneralService().getByPageSQL(strb.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return page;
	}
	/**
	 * 插入新的留言，刷新页面
	 * @return
	 */
	public String insertMessages(){
		PeTrainee trainee=this.getSsoTrainee();
		PeQuestions ques=new PeQuestions();
		ques.setCreateDate(new Date());
		ques.setDetail(this.getDetail());
		ques.setTitle(this.getTitle());
		ques.setPeTchCourse(this.getMsgCourse());
		ques.setPeTrainee(this.getSsoTrainee());
		ques.setEnumConstByFlagSolve(this.getMyListService().getEnumConstByNamespaceCode("FlagSolve", "0"));
		ques.setReplyNum("0");
		ques.setResolveDate(null);
		ques.setEnumConstByFlagCryptonym(null);
		ques.setOppose((long)0);
		ques.setScore((long)0);
		ques.setSupport((long)0);
		
		
		this.getGeneralService().getGeneralDao().setEntityClass(PeQuestions.class);
		try {
			ques=(PeQuestions) this.getGeneralService().save(ques);
			this.setMsg("提问成功");
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.setMsg("提问失败");
		}
		return "quesSucc";
	}
	public String sub(String str,int len){
		String strr=new String();
		if(str.length()>len){
			strr=str.substring(0,len)+"...";
			
		}else{
			strr=str;
		}
		return strr;
	}
	/**
	 * 回复留言
	 * @return
	 */
	public String replyMessages(){
		PeAnswers ans=new PeAnswers();
		ans.setCreateDate(new Date());
		ans.setTitle(this.getTitle());
		ans.setDetail(this.getDetail());
		ans.setPeQuestions(this.getQuesById(this.getQuestionId()));
		
//		ans.setPeTeacher(this.getSsoTeacher());
		ans.setSsoUser(this.getSsoUserOnline());
		this.getGeneralService().getGeneralDao().setEntityClass(PeAnswers.class);
		try {
			ans=(PeAnswers) this.getGeneralService().save(ans);
			this.setMsg("提问成功");
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.setMsg("提问失败");
		}
		return this.getMessages();
	}
	private PeQuestions getQuesById(String id){
		this.getGeneralService().getGeneralDao().setEntityClass(PeQuestions.class);
		try {
			PeQuestions ques=(PeQuestions) this.getGeneralService().getById(id);
			if(ques!=null){
				return ques;
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 返回当前的在线学员的PeTrainee
	 * @return
	 */
	private PeTrainee getSsoTrainee(){
		UserSession us=(UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if(us==null){
			return null;
		}
		SsoUser user=us.getSsoUser();
		DetachedCriteria dc=DetachedCriteria.forClass(PeTrainee.class);
		dc.add(Restrictions.eq("ssoUser", user));
		List list=new LinkedList();
		try {
			list=this.getGeneralService().getList(dc);
			PeTrainee trainee=null;
			if(list!=null&&list.size()>0){
				trainee=(PeTrainee) list.get(0);
			}
			return trainee;
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private boolean answered;//是否被回答
	private boolean canEdit;//是否可以编辑，提问者可以编辑，其他人不行
	private boolean succ;//是否编辑成功
	
	/**
	 * 修改問題
	 * @return
	 */
	public String modifyQues(){
		StringBuilder strb=new StringBuilder();
		strb.append(" select ques.id id,'' e,ques.title,concat('',ques.detail) q_detail from pe_questions ques ");
//		strb.append("  left outer join pe_answers ans on ans.fk_ques_id=ques.id                                                                         ");
//		strb.append(" left outer join pe_teacher tcr on ans.fk_teacher_id=tcr.id                                                                        ");
//		strb.append(" left outer join pe_trainee ee on ques.fk_trainee_id=ee.id                                                                         ");
		strb.append(" where ques.id=:ids                                                                                 ");
		Map map=new HashMap();
		List list=new LinkedList();
		try {
			map.put("ids", this.getIds());
			list=this.getGeneralService().getBySQL(strb.toString(), map);

		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(list!=null&&list.size()>0){
			this.setMessageList(list);//提问和回答列表，实际只有一对（一问一答）
		}
		return "modifyPage";
	}
	
	/**
	 * 保存用戶修改的問題
	 * @return
	 */
	public String saveMod(){
		boolean flag=true;
		PeQuestions ques=new PeQuestions();
		this.getGeneralService().getGeneralDao().setEntityClass(PeQuestions.class);
		try {
			ques=(PeQuestions) this.getGeneralService().getById(this.getIds());
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			flag=false;
			e.printStackTrace();
		}
		if(ques!=null){
			ques.setTitle(this.getTitle());
			ques.setDetail(this.getDetail());
			ques.setCreateDate(new Date());
			try {
				ques=(PeQuestions) this.getGeneralService().save(ques);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				flag=false;
				e.printStackTrace();
			}
		}
		if(flag){
			this.setMsg("修改成功");
		}else{
			this.setMsg("修改失败");
		}
		this.setSucc(flag);
		return "editResu";
	}
	
	/**
	 * 删除问题
	 * @return
	 */
	public String delQues(){
		boolean flag=true;
		String sql="delete from pe_questions where id=:ids";
		Map map=new HashMap();
		map.put("ids", this.getIds());
		try {
			int i=this.getGeneralService().executeBySQL(sql, map);
			if(i>0){
				flag=true;
			}else{
				flag=false;
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setSucc(flag);
		if(flag){
			this.setMsg("删除成功");
		}else{
			this.setMsg("删除失败");
		}
		
		return "editResu";
	
	}
	
	public String delQuesOnPage(){
		this.delQues();
		if(this.isSucc()){
			this.setMsg("删除成功");
		}else{
			this.setMsg("删除失败");
		}
		return this.getMessages();
	}
	
	//查看问题详细信息
	public String getMsgDetail(){
		String id=this.getIds();
		String sql="select id from pe_answers where fk_ques_id =:ids";
		Map map=new HashMap();
		map.put("ids", id);
		List list=new LinkedList();
		try {
			list=this.getGeneralService().getBySQL(sql, map);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(list!=null&&list.size()>0){//该问题已被回答
			this.answered=true;
			StringBuilder strb=new StringBuilder();
			strb.append(" select ques.id id,ee.true_name q_name,ques.title,concat('',ques.detail) q_detail,ques.create_date q_date,ifnull(tcr.true_name,mgr.true_name) a_name,ans.create_date a_date,concat('',ans.detail) a_detail from pe_questions ques ");
			strb.append("  left outer join pe_answers ans on ans.fk_ques_id=ques.id                                                                         ");
			strb.append(" left outer join sso_user sso on ans.fk_sso_user=sso.id ");
			strb.append(" left outer join pe_teacher tcr on tcr.fk_sso_user_id=sso.id                                                                        ");
			strb.append(" left outer join pe_manager mgr on mgr.fk_sso_user_id=sso.id                                                                        ");
			strb.append(" left outer join pe_trainee ee on ques.fk_trainee_id=ee.id                                                                         ");
			strb.append(" where ques.id=:ids                                                                                 ");
			try {
				map.put("ids", id);
				list=this.getGeneralService().getBySQL(strb.toString(), map);

			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(list!=null&&list.size()>0){
				this.setMessageList(list);//提问和回答列表，实际只有一对（一问一答）
			}
		}else{
			this.answered=false;
//			SsoUser sso=this.getSsoUserOnline();
			PeTrainee trainee=this.getSsoTrainee();
			String sql_sso="select id from pe_questions where fk_trainee_id=:ids";
			List list_sso=new LinkedList();
			this.setCanEdit(false);
			if(trainee!=null){
				Map map_sso=new HashMap();
				map_sso.put("ids", trainee.getId());
				try {
					list_sso=this.getGeneralService().getBySQL(sql_sso, map_sso);
					if(list_sso!=null&&list_sso.size()>0){
						this.setCanEdit(true);//如果当前查看者是提问者，就可以编辑（修改和删除）
					}
				} catch (EntityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			StringBuilder strb=new StringBuilder();
			strb.append(" select ques.id id,ee.true_name q_name,ques.title,concat('',ques.detail) q_detail,ques.create_date q_date from pe_questions ques ");
//			strb.append("  left outer join pe_answers ans on ans.fk_ques_id=ques.id                                                                         ");
//			strb.append(" left outer join pe_teacher tcr on ans.fk_teacher_id=tcr.id                                                                        ");
			strb.append(" left outer join pe_trainee ee on ques.fk_trainee_id=ee.id                                                                         ");
			strb.append(" where ques.id=:ids                                                                                 ");
			try {
				map.put("ids", id);
				list=this.getGeneralService().getBySQL(strb.toString(), map);

			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(list!=null&&list.size()>0){
				this.setMessageList(list);//提问和回答列表，实际只有一对（一问一答）
			}
		}
		
		return "quesDetail";
	}
	/**
	 * 返回当前的在线教师的PeTeacher
	 * @return
	 */
	private PeTeacher getSsoTeacher(){
		UserSession us=(UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if(us==null){
			return null;
		}
		SsoUser user=us.getSsoUser();
		DetachedCriteria dc=DetachedCriteria.forClass(PeTeacher.class);
		dc.add(Restrictions.eq("ssoUser", user));
		List list=new LinkedList();
		try {
			list=this.getGeneralService().getList(dc);
			PeTeacher teacher=null;
			if(list!=null&&list.size()>0){
				teacher=(PeTeacher) list.get(0);
			}
			return teacher;
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 返回当前的在线教师的SsoUser
	 * @return
	 */
	private SsoUser getSsoUserOnline(){
		UserSession us=(UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if(us==null){
			return null;
		}
		SsoUser user=us.getSsoUser();
		return user;
	}
	
	/**
	 * 返回留言板类别的课程对象，用于设置留言板对象的类别属性
	 * @return
	 */
	private PeTchCourse getMsgCourse(){
		DetachedCriteria dc=DetachedCriteria.forClass(PeTchCourse.class);
		dc.add(Restrictions.eq("name", "留言板"));
		List list=new LinkedList();
		PeTchCourse course=null;
		try {
			list=this.getGeneralService().getList(dc);
			if(list!=null&&list.size()>0){
				course=(PeTchCourse) list.get(0);
				return course;
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
System.out.println("没有找到类型为 留言板 的类型，提问会出错");		
		return null;
	}

	public List getMessageList() {
		return messageList;
	}

	public void setMessageList(List messageList) {
		this.messageList = messageList;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public static int getSIZE_PER_PAGE() {
		return SIZE_PER_PAGE;
	}

	public static void setSIZE_PER_PAGE(int size_per_page) {
		SIZE_PER_PAGE = size_per_page;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isAnswered() {
		return answered;
	}

	public void setAnswered(boolean answered) {
		this.answered = answered;
	}

	public boolean isSucc() {
		return succ;
	}

	public void setSucc(boolean succ) {
		this.succ = succ;
	}

	public boolean isCanEdit() {
		return canEdit;
	}

	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
	
	//zhaoyuxiao end



}
