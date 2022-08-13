package com.whaty.platform.entity.web.action.information.vote;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeProApplyno;
import com.whaty.platform.entity.bean.PeVotePaper;
import com.whaty.platform.entity.bean.PrVoteQuestion;
import com.whaty.platform.entity.bean.PrVoteRecord;
import com.whaty.platform.entity.bean.PrVoteSuggest;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.information.PeVotePaperService;
import com.whaty.platform.entity.util.AnalyseClassType;
import com.whaty.platform.entity.util.ExpressionParse;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

public class PeVotePaperInitAction extends MyBaseAction {
	
	private PeVotePaperService peVotePaperService;
	private String applyno;
	private String classIdentifierSuffix;
	
	@Override
	public void initGrid() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String applyNo1 = request.getParameter("search1_peProApplyno.id");
		String peUnit1 = request.getParameter("peUnit");
		String peSubject1 = request.getParameter("peSubject");
		String peProvince1 = request.getParameter("peProvince");
			session.setAttribute("peProvince",peProvince1 );
			session.setAttribute("applyNo", applyNo1);
			session.setAttribute("peUnit", peUnit1);
			session.setAttribute("peSubject", peSubject1);
		String peProvince =(String)session.getAttribute("peProvince");
		String applyNo = (String)session.getAttribute("applyNo");
		String peUnit = (String)session.getAttribute("peUnit");
		String peSubject = (String)session.getAttribute("peSubject");
		UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
//		if(peProvince1 .equals("")){
//			String classIdentifierSuffix = this.getYearById(applyNo).substring(2)+getCodeById(applyNo, "pe_pro_applyno") + getCodeById(peUnit, "pe_unit") + getCodeById(peSubject, "pe_subject");
//		}else{
			String classIdentifierSuffix = this.getYearById(applyNo).substring(2)+getCodeById(applyNo, "pe_pro_applyno")+ getCodeById(peUnit, "pe_unit")+ getCodeById(peSubject, "pe_subject") +getCodeById(peProvince, "pe_province");
//			System.out.println(classIdentifierSuffix+"===========");
			
//		}
	this.getGridConfig().setTitle(this.getText("调查问卷列表"));
		this.getGridConfig().setCapability(false, false, false,false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("名称"), "title");
		this.getGridConfig().addColumn(this.getText("培训项目"), "peProApplyno.name");
		this.getGridConfig().addColumn(this.getText("创建日期"), "foundDate");
		this.getGridConfig().addColumn(this.getText("开始日期"), "startDate");
		this.getGridConfig().addColumn(this.getText("结束日期"), "endDate");
		this.getGridConfig().addColumn(this.getText("是否发布"), "enumConstByFlagIsvalid.name");
		this.getGridConfig().addRenderFunction(this.getText("查看结果"),
				"<a href=\"peVotePaper_viewDetail.action?bean.id=${value}&suffix="+classIdentifierSuffix+"\" target=\"_blank\">查看详细信息</a>",
				"id");
		if(us.getRoleType().equals("4")){
			this.getGridConfig().setCapability(false, true, false);
			this.getGridConfig().addRenderFunction(this.getText("管理题目"),
					"<a href=\"peVotePaper_toVoteQuestion.action?bean.id=${value}&applyNo="+applyNo+"&peUnit="+peUnit+"&peSubject="+peSubject+"\" >管理题目</a>",
					"id");
			this.getGridConfig().addRenderFunction(this.getText("修改"),
					"<a href=\"peVotePaper_toEditVotePaper.action?bean.id=${value}\" >修改</a>","id");
			this.getGridConfig().addRenderFunction(this.getText("查看建议"),
					"<a href=\"prVoteSuggest.action?bean.peVotePaper.id=${value}\" >查看建议</a>","id");		
			this.getGridConfig().addMenuFunction(this.getText("发布"),"enumConstByFlagIsvalid.id" ,
					this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "1").getId());
			this.getGridConfig().addMenuFunction(this.getText("取消发布"),"enumConstByFlagIsvalid.id" , 
					this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "0").getId());
			initCopyButton();
		}
		this.getGridConfig().addMenuScript(this.getText("返回"), "{history.go(-1);}");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeVotePaper.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/information/peVotePaperInit";
	}
	//初始化单位列表
	public void initUnit(){
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter pWriter = null;
		try {
			pWriter = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String queryUnitInfo = "select u.id,u.name from pe_unit u where u.id in (select FK_UNIT from pr_program_unit where FK_PROGRAM_ID=:theProApplyNo)";
		Map<String, String> params = new HashMap<String, String>();
		params.put("theProApplyNo", ServletActionContext.getRequest().getParameter("proApplyNo"));
		List<Object[]> resultList = null;
		try {
			resultList = getGeneralService().getBySQL(queryUnitInfo, params);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		StringBuffer jsonData = new StringBuffer("");
//		StringBuffer queryString = new StringBuffer();
		if(resultList != null && !resultList.isEmpty()) {
			Object[] objects;
			for (Iterator<Object[]> iterator = resultList.iterator(); iterator.hasNext();) {
				objects = iterator.next();
				jsonData.append(objects[0]).append(",").append(objects[1]).append(";");
//				queryString.append("'"+objects[0]+"'"+",");
			}
//			queryString.deleteCharAt(queryString.length()-1);
		}
		pWriter.write(jsonData.length() == 0 ? "" : jsonData.toString().substring(0, jsonData.length() - 1));
		pWriter.close();
//		String initProvincesql = "   select p.id ,p.name from pe_province p inner join pe_unit u on u.fk_province = p.id where u.id in ("+queryString+")";
	}
	
	//初始化学科列表
	public void initSubject(){
		String proApplyNo = ServletActionContext.getRequest().getParameter("proApplyNo");
		String unitId = ServletActionContext.getRequest().getParameter("unitValue");
		String queryApplyInfo = "select id,name from pe_subject where id in (select fk_subject from pe_pro_apply where fk_unit=:theFKToUnit and fk_applyno=:theFKToApplyNo)";
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("theFKToApplyNo", proApplyNo);
		paramsMap.put("theFKToUnit", unitId);
		List<Object[]> resultList = null;
		try {
			resultList = getGeneralService().getBySQL(queryApplyInfo,paramsMap);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		StringBuffer writeData = new StringBuffer("");
		if(resultList != null && !resultList.isEmpty()) {
			Object[] tempObjects;
			for (int i = 0; i < resultList.size(); i++) {
				tempObjects = resultList.get(i);
				writeData.append(tempObjects[0]).append(",").append(tempObjects[1]).append(";");
			}
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter outWriter = null;
		try {
			outWriter = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		outWriter.write(writeData.length() == 0 ? "" : writeData.substring(0, writeData.length() - 1));
		outWriter.close();
	}
	//初始化省份列表
	public void initProvince(){
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter pWriter = null;
		try {
			pWriter = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String queryUnitInfo = "select distinct u.id,u.name from PE_PROVINCE u inner join pe_unit unit on unit.fk_province = u.id where unit.id in (select FK_UNIT from pr_program_unit where FK_PROGRAM_ID=:theProApplyNo)";
		Map<String, String> params = new HashMap<String, String>();
		params.put("theProApplyNo", ServletActionContext.getRequest().getParameter("proApplyNo"));
		List<Object[]> resultList = null;
		try {
			resultList = getGeneralService().getBySQL(queryUnitInfo, params);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		StringBuffer queryString = new StringBuffer("");
		if(resultList != null && !resultList.isEmpty()) {
			Object[] objects;
			for (Iterator<Object[]> iterator = resultList.iterator(); iterator.hasNext();) {
				objects = iterator.next();
				queryString.append(objects[0]).append(",").append(objects[1]).append(";");
			}
		}
		pWriter.write(queryString.length() == 0 ? "" : queryString.toString().substring(0, queryString.length() - 1));
		pWriter.close();
	}
	/**
	 * @description 根据id查询对应的编码code(针对培训单位和学科)
	 * @param id 条件id号
	 * @param tableName 操作的表名
	 * @return 查询所得的年度
	 */
	private String getYearById(String id) {
		String getCodeSQL = "select year from pe_pro_applyno where id=:theId";
		Map<String, String> params = new HashMap<String, String>();
		params.put("theId", id);
		List<String> resultList = null;
		try {
			resultList = getGeneralService().getBySQL(getCodeSQL, params);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		String queryId = "";
		if(resultList != null && resultList.size() != 0) {
			queryId = resultList.get(0);
		}
		return queryId;
	}
	/**
	 * @description 根据id查询对应的编码code(针对培训单位和学科)
	 * @param id 条件id号
	 * @param tableName 操作的表名
	 * @return 查询所得的code编码
	 */
	private String getCodeById(String id,String tableName) {
		String getCodeSQL = "select code from " + tableName + " where id=:theId";
		Map<String, String> params = new HashMap<String, String>();
		params.put("theId", id);
		List<String> resultList = null;
		try {
			resultList = getGeneralService().getBySQL(getCodeSQL, params);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		String queryId = "";
		if(resultList != null && resultList.size() != 0) {
			queryId = resultList.get(0);
		}
		return queryId;
	}
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeVotePaper.class);
		dc.createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		dc.createCriteria("peProApplyno", "peProApplyno");
		return dc;
	}
	/**
	 * 判断sort，dir输入，置入DetachedCriteria 递归遍历bean，取得用户输入的参数，置入DetachedCriteria
	 */
	public DetachedCriteria setDetachedCriteria(
			DetachedCriteria detachedCriteria, Object bean) {
		ActionContext context = ActionContext.getContext(); 
		Map params = context.getParameters();
		Iterator iterator = params.entrySet().iterator();
		String str_="search1_";
		while(iterator.hasNext()){
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            String name = entry.getKey().toString();
            if(name.startsWith("search__")){
	            if("".equals(((String[])entry.getValue())[0]))
	            	continue;
            	str_="search__";
            }//System.out.println(name+"====="+((String[])entry.getValue())[0]+"==================");
		}
		detachedCriteria = setDC1(detachedCriteria, params,str_);
		return detachedCriteria;
	}

	private DetachedCriteria setDC1(DetachedCriteria detachedCriteria, Map params,String str_) {
		Iterator iterator = params.entrySet().iterator();
		do {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            String name = entry.getKey().toString();
            if(!name.startsWith(str_))
            	continue;
            if("".equals(((String[])entry.getValue())[0]))
            	continue;
            if(name.indexOf(".") > 1 && name.indexOf(".") != name.lastIndexOf(".")){
            	name=name.substring(name.lastIndexOf(".", name.lastIndexOf(".")-1)+1);
			}else{
				name=name.substring(8);
			}
            CriteriaImpl imp = (CriteriaImpl)detachedCriteria.getExecutableCriteria(null);
            Class beanClass = null;
			try {
				beanClass = Class.forName(imp.getEntityOrClassName());
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			Class clazz = AnalyseClassType.getClassType(beanClass, entry.getKey().toString().substring(8));
			
            detachedCriteria = ExpressionParse.parseExpression(detachedCriteria, name, ((String[])entry.getValue())[0], clazz);
        }while(true);
		return detachedCriteria;
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
				DetachedCriteria criteria = DetachedCriteria.forClass(PrVoteQuestion.class);
				criteria.createCriteria("peVotePaper", "peVotePaper");
				criteria.add(Restrictions.in("peVotePaper.id", ids));
				DetachedCriteria criteria1 = DetachedCriteria.forClass(PrVoteSuggest.class);
				criteria1.createCriteria("peVotePaper", "peVotePaper");
				criteria1.add(Restrictions.in("peVotePaper.id", ids));
				DetachedCriteria criteria2 = DetachedCriteria.forClass(PrVoteRecord.class);
				criteria2.createCriteria("peVotePaper", "peVotePaper");
				criteria2.add(Restrictions.in("peVotePaper.id", ids));
				try {
					List<PrVoteQuestion> plist = this.getGeneralService().getList(criteria);
					List<PrVoteSuggest> vslist = this.getGeneralService().getList(criteria1);
					List<PrVoteRecord> vrlist = this.getGeneralService().getList(criteria2);
					if(plist.size()>0||vslist.size()>0 ||vrlist.size()>0){
						map.clear();
						map.put("success", "false");
						map.put("info", "该问卷下已经添加题目或者已经存在建议或调查结果,无法删除!");
						return map;
					}
				} catch (EntityException e) {
					e.printStackTrace();
				}
				try{
					this.getGeneralService().getGeneralDao().setEntityClass(PeVotePaper.class);
					this.getGeneralService().deleteByIds(idList);
					map.put("success", "true");
					map.put("info", "删除成功");
				}catch(RuntimeException e){
					e.printStackTrace();
					return this.checkForeignKey(e);
				}catch(Exception e1){
					map.clear();
					map.put("success", "false");
					map.put("info", "删除失败");
				}
			} else {
				map.put("success", "false");
				map.put("info", "请选择操作项");
			}
		}
		return map;
	}
	public String copyPrograms(){
		Map map=new HashMap();
		map.put("success", "false");
		map.put("info", "修改失败");
		if(this.getApplyno()==null||this.getApplyno().length()==0){
			this.setJsonString(JsonUtil.toJSONString(map));
			return this.json();
		}
		DetachedCriteria dcApplyno = DetachedCriteria.forClass(PeProApplyno.class);
		dcApplyno.add(Restrictions.eq("name", this.getApplyno()));
		PeProApplyno peProApplyno = null;
		try {
			peProApplyno = (PeProApplyno)this.getGeneralService().getList(dcApplyno).get(0);
		} catch (EntityException e) {
			e.printStackTrace();
			this.setJsonString(JsonUtil.toJSONString(map));
			return this.json();
		}
		PeVotePaper votePaper = null;
		try {
			votePaper = (PeVotePaper) this.getGeneralService().getById(PeVotePaper.class, this.getIds());
		} catch (EntityException e) {
			e.printStackTrace();
			this.setJsonString(JsonUtil.toJSONString(map));
			return this.json();
		}
		if(votePaper.getPeProApplyno().getId().equals(peProApplyno.getId())){
			map.put("info", "培训项目选择错误！");
			this.setJsonString(JsonUtil.toJSONString(map));
			return this.json();
		}
		
		this.getPeVotePaperService().copyVotePaper(votePaper, peProApplyno);
		
		map.put("info", "操作成功，该培训项目已被复制到 "+this.getApplyno());
		map.put("success","true");
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}																				
	public void initCopyButton(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String applyNo = (String)session.getAttribute("applyNo");
		List list=null;
		try {
			list = this.getMyListService().queryBySQL("select id,name from pe_pro_applyno where /*year='"+Const.getYear()+"' and*/ id !='"+applyNo+"' ");
			if(list.size()==0){
				throw new EntityException("error");
			}
		} catch (Exception e) {
		}
		String comboboxitem = " var comboitem = new Ext.form.ComboBox({ store: new Ext.data.SimpleStore({fields: ['id', 'name'],data : [";
		for(int i = 0; i < list.size(); i++){
			Object[] s = (Object[])list.get(i);
			if(i == 0){
				comboboxitem += " ['" + s[0].toString() + "', '" + s[1].toString() + "']";
			}else{
				comboboxitem += ", ['" + s[0].toString() + "', '" + s[1].toString() + "']";
			}
		}
		comboboxitem += " ] }), valueField: 'id', displayField:'name', selectOnFocus:true, allowBlank: false, typeAhead:false," +
		"	fieldLabel: '培训项目*'," +
		"	name:'applyno'," +
		"	id:'applyno'," +
		"	width:300," +
		"	triggerAction: 'all'," +
		"	editable: true," +
		"	mode:'local'," +
		"	emptyText:''," +
		"	blankText:''" +
		"});\n";
		StringBuilder script1 = new StringBuilder();
		script1.append(" {");
		script1.append(" 	 var m = grid.getSelections();");
		script1.append("    	 var ids = '';");
		script1.append("    	 if(m.length == 1){");
		script1.append("    	  ids = m[0].get('id'); ");
		script1.append("    	 } else if(m.length == 0){");
		script1.append("    	 	Ext.MessageBox.alert('错误','请选择要复制的调查问卷'); return;");
		script1.append("    	 }else{");
		script1.append("    	 	Ext.MessageBox.alert('错误','最多只能选择一个调查问卷进行复制'); return;");
		script1.append("    	 }");
		script1.append(comboboxitem);
		script1.append("        var fids = new Ext.form.Hidden({name:'ids',value:ids});");
		script1.append("    	 var formPanel = new Ext.form.FormPanel({");
		script1.append(" 	    frame:true,");
		script1.append("         labelWidth: 80,");
		script1.append("        	defaultType: 'textfield',");
		script1.append(" 				autoScroll:true,");
		script1.append("         items: [ comboitem ,fids]");
		script1.append("       });");
		script1.append("        var addModelWin = new Ext.Window({");
		script1.append("        title: '复制调查问卷',");
		script1.append("        width: 450,");
		script1.append("        height: 225,");
		script1.append("        minWidth: 300,");
		script1.append("        minHeight: 250,");
		script1.append("        layout: 'fit',");
		script1.append("        plain:true,");
		script1.append("        bodyStyle:'padding:5px;',");
		script1.append("        buttonAlign:'center',");
		script1.append("        items: formPanel,");
		script1.append("        buttons: [{");
		script1.append(" 	            text: '保存',");
		script1.append(" 	            handler: function() {");
		script1.append(" 	                if (formPanel.form.isValid()) {");
		script1.append(" 		 		        formPanel.form.submit({");
		script1.append(" 		 		        	url:'"+this.getServletPath()+"_copyPrograms.action?' ,");
		script1.append(" 				            waitMsg:'处理中，请稍候...',");
		script1.append(" 							success: function(form, action) {");
		script1.append(" 							    var responseArray = action.result;");
		script1.append(" 							    if(responseArray.success=='true'){");
		script1.append(" 							    	Ext.MessageBox.alert('提示', responseArray.info);");
		script1.append(" 							    	store.load({params:{start:g_start,limit:g_limit"+this.getGridConfig().getFieldsFilter()+"}});                      ");//+this.getGridConfig().getFieldsFilter()+"
		script1.append(" 								    addModelWin.close();");
		script1.append(" 							    } else {");
		script1.append(" 							    	Ext.MessageBox.alert('错误', responseArray.info );");
		script1.append(" 							    }");
		script1.append(" 							}");
		script1.append(" 				        });");
		script1.append(" 	                } else{");
		script1.append(" 						Ext.MessageBox.alert('错误', '输入错误，请先修正提示的错误');");
		script1.append(" 					}");
		script1.append(" 		        }");
		script1.append(" 	        },{");
		script1.append(" 	            text: '取消',");
		script1.append(" 	            handler: function(){addModelWin.close();}");
		script1.append(" 	        }]");
		script1.append("        });");
		script1.append("        addModelWin.show();");
		script1.append("  }");
		this.getGridConfig().addMenuScript(this.getText("复制调查问卷"), script1.toString());
		this.getGridConfig().setPrepared(false);
	}
	public void setBean(PeVotePaper instance) {
		super.superSetBean(instance);
	}

	public PeVotePaper getBean() {
		return (PeVotePaper) super.superGetBean();
	}

//	@Override
//	public String gridjs() {
//		initCopyButton();
//		return super.gridjs();
//	}
	public String getApplyno() {
		return applyno;
	}

	public void setApplyno(String applyno) {
		this.applyno = applyno;
	}

	public PeVotePaperService getPeVotePaperService() {
		return peVotePaperService;
	}

	public void setPeVotePaperService(PeVotePaperService peVotePaperService) {
		this.peVotePaperService = peVotePaperService;
	}
}
