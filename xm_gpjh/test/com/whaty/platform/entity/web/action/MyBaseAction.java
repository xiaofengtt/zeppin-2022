package com.whaty.platform.entity.web.action;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.util.JSONUtils;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Criteria;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.mapping.PersistentClass;
import org.springframework.web.util.JavaScriptUtils;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.config.AlternateKey;
import com.whaty.platform.config.ForeignKey;
import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.County;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeProApplyno;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.PeUnit;
import com.whaty.platform.entity.bean.WhatyuserLog4j;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.service.MyListService;
import com.whaty.platform.entity.service.sms.PeSmsInfoService;
import com.whaty.platform.entity.util.AnalyseClassType;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.ExpressionParse;
import com.whaty.platform.entity.util.GridConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

public abstract class MyBaseAction<T extends AbstractBean> extends EntityBaseAction {
	private PeSmsInfoService peSmsInfoService;//发送短信
	
	private boolean isAddPri = true;//是否横向权限拦截
	
	private GeneralService<T> generalService;
	
	private T bean;		//父类默认bean的变量名称为"bean"
	
	private File _upload;
	
	private String _uploadField;
	
	private String _uploadFileName;

	private GridConfig gridConfig;

	//servletPath放到子类中设置
	public String servletPath;

	private String ids; // Id 的列表，供delete、update方法调用

	private String column; // 列名，供update方法调用

	private String value; // 列值，供update方法调用

	private String jsonString;
	
	private MyListService myListService;
	
	private boolean search;
	
	private final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    
    private static String dateformat = null;
    
    private String format;			//导出Excel时的选择项
    
    //返回的信息
    private String msg = null;
    
    //返回的url，不设置就是history.back（）
    private String togo = null;
    
    private boolean canProjections = true;//是否加投影, true设置，false 不设置
    
    //交流园地返回页面
    private String interactionMsg = null;
    //交流园地返回url，不设置为history.back()
    private String interactionTogo = null;
    
    //entityClass赋值放在子类里是否更好一些？
    protected Class entityClass;
    
    private String filePath;

	public GeneralService<T> getGeneralService() {
		return generalService;
	}


	public void setGeneralService(GeneralService<T> generalService) {
		this.generalService = generalService;
		this.generalService.getGeneralDao().setEntityClass(entityClass);
	}

	public File get_upload() {
		return _upload;
	}

	public void set_upload(File _upload) {
		this._upload = _upload;
	}

	public String get_uploadField() {
		return _uploadField;
	}

	public void set_uploadField(String field) {
		_uploadField = field;
	}

	public String get_uploadFileName() {
		return _uploadFileName;
	}

	public void set_uploadFileName(String fileName) {
		_uploadFileName = fileName;
	}

	public Class getEntityClass() {
		return entityClass;
	}

	//FIXME: 设置为abstract是否更合理一些
	public abstract void setEntityClass();

	/**
	 * 默认构造函数
	 */
	public MyBaseAction() {
		gridConfig = new GridConfig(this);
		gridConfig.setCapability(true, true, true, true, false);
		
		this.init();
		logger.info(this.getClass().getName() + ".init()");
	}

	// getters and setters
	public GridConfig getGridConfig() {
		return gridConfig;
	}

	public void setGridConfig(GridConfig gridConfig) {
		this.gridConfig = gridConfig;
	}

	public String getEntityMemberName() {
		return gridConfig.getEntityMemberName();
	}

	public void setEntityMemberName(String entityMemberName) {
		gridConfig.setEntityMemberName(entityMemberName);
	}

	public String getServletPath() {
		return servletPath;
	}

	//FIXME: 设置为abstract是否更合理一些
	public abstract void setServletPath();

	public String getLimit() {
		return gridConfig.getLimit();
	}

	public void setLimit(String limit) {
		this.gridConfig.setLimit(limit);
	}

	public String getStart() {
		return gridConfig.getStart();
	}

	public void setStart(String start) {
		this.gridConfig.setStart(start);
	}

	// handle multiple table name before getSort()
	public String getSort() {
		return handleSort(gridConfig.getSort());
	}

	public void setSort(String sort) {
		this.gridConfig.setSort(sort);
	}

	public String getDir() {
		return this.gridConfig.getDir();
	}

	public void setDir(String dir) {
		this.gridConfig.setDir(dir);
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isSearch() {
		return search;
	}

	public void setSearch(boolean search) {
		this.search = search;
	}

	// replace null before getJsonString()
	public String getJsonString() {
		//TODO 存在用户输入文本字段命中匹配的风险，待解决
		/**
		 * 将 ":null 替换为 ":""
		 */
		String s = jsonString.replaceAll("\":null", "\":\"\"");
		if (!s.equals(jsonString)) {
//			System.out.println("ZLB DEBUG::MyBaseAction.getJsonString(): replaced null");
//			System.out.println(jsonString);
		}
		return s;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public MyListService getMyListService() {
		return myListService;
	}

	public void setMyListService(MyListService myListService) {
		this.myListService = myListService;
	}

	// functions
	private String handleSort(String sort) {
		String s = sort;
		if (sort == null || sort.equals("")) {
			return s;
		}
		while (sort.indexOf(".") >= 0) {
			s = sort;
			sort = sort.substring(sort.indexOf(".") + 1);
		}
		return s;
	}

	public String getJsAction() {
		String queryString = "";
		if (ServletActionContext.getRequest().getQueryString() != null)
			queryString = ServletActionContext.getRequest().getQueryString();
		return this.getServletPath() + "_gridjs.action?" + queryString;
	}
	
	public String getListAction() {
		String queryString = "";
		if (ServletActionContext.getRequest().getQueryString() != null)
			queryString = ServletActionContext.getRequest().getQueryString();
		return this.getServletPath() + "_abstractList.action?" + queryString;
	}

	public String getAddAction() {
		String queryString = "";
		if (ServletActionContext.getRequest().getQueryString() != null)
			queryString = ServletActionContext.getRequest().getQueryString();
		return this.getServletPath() + "_abstractAdd.action?" + queryString;
	}

	public String getDeleteAction() {
		String queryString = "";
		if (ServletActionContext.getRequest().getQueryString() != null)
			queryString = ServletActionContext.getRequest().getQueryString();
		return this.getServletPath() + "_abstractDelete.action?" + queryString;
	}

	public String getUpdateAction() {
		return this.getServletPath() + "_abstractUpdate.action?";
	}

	public String getUpdateColumnAction() {
		return this.getServletPath() + "_abstractUpdateColumn.action";
	}
	
	public String getDetailAction() {
		String queryString = "";
		if (ServletActionContext.getRequest().getQueryString() != null)
			queryString = ServletActionContext.getRequest().getQueryString();
		return this.getServletPath() + "_abstractDetail.action?" + (queryString == null ? "1=1" : "".equals(queryString) ? "1=1" : queryString);
	}
	
	public String getExcelAction() {
		String queryString = "";
		if (ServletActionContext.getRequest().getQueryString() != null)
			queryString = ServletActionContext.getRequest().getQueryString();
		return this.getServletPath() + "_abstractExcel.action?" + queryString;
	}

	public String getAddExcelUploadAction(){
		return this.getServletPath() + "_batchAddExcelUpload.action";
	}
	
	public String getAddExcelDownloadAction(){
		return this.getServletPath() + "_batchAddExcel.action";
	}
	
	public String getUpdateExcelUploadAction(){
		return this.getServletPath() + "_update_excel.action";
	}
	
	public String getUpdateExcelDownloadAction(){
		return this.getServletPath() + "_excelUpdate.action";
	}
	/**
	 * 判断sort，dir输入，置入DetachedCriteria 递归遍历bean，取得用户输入的参数，置入DetachedCriteria
	 */
	public DetachedCriteria setDetachedCriteria(
			DetachedCriteria detachedCriteria, Object bean) {
		if (this.getSort() != null) {
			if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc"))
				detachedCriteria.addOrder(Order.desc(this.getSort()));
			else
				detachedCriteria.addOrder(Order.asc(this.getSort()));
			if(!this.getSort().equals("id")){
				detachedCriteria.addOrder(Order.desc("id"));
			}
		}
		
//		detachedCriteria = setProjections(detachedCriteria);
		detachedCriteria = setDetachedCriteriaPriority(detachedCriteria);

		//set bean's properties
		detachedCriteria = this.setDetachedCriteria(detachedCriteria, bean, true, null);
		
		//set search expressions
		ActionContext context = ActionContext.getContext(); 
		Map params = context.getParameters(); 
//		System.out.println("context"+params.size());
		detachedCriteria = setDC(detachedCriteria, params);
		
		return detachedCriteria;
	}

	private DetachedCriteria setDC(DetachedCriteria detachedCriteria, Map params) {
		Iterator iterator = params.entrySet().iterator();
		do {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            String name = entry.getKey().toString();
            if(!name.startsWith("search__"))
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
			
			//对于日期类型的搜索 默认搜索当天的 且仅限于yyyy-MM-dd格式，modified by houxuelong
			if(clazz.getName().equals("java.util.Date")){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String datetemp=((String[])entry.getValue())[0];
				if(datetemp.indexOf("<")>=0||datetemp.indexOf("<")>=0){
					detachedCriteria = ExpressionParse.parseExpression(detachedCriteria, name, ((String[])entry.getValue())[0], clazz);
				}else{
					java.util.Date date_ = null;
					try {
						date_ = sdf.parse(datetemp);
					} catch (ParseException e) {
						detachedCriteria = ExpressionParse.parseExpression(detachedCriteria, name, ((String[])entry.getValue())[0], clazz);
					}
					String datetemp2 = sdf.format(Const.getNextDate(date_));//取得选择日期的下一天
					detachedCriteria = ExpressionParse.parseExpression(detachedCriteria, name, ">"+datetemp+" and <"+datetemp2, clazz);
				}
			}else if(name.indexOf(".")>0){//如果有外键 默认精确查找 modified by houxuelong
				detachedCriteria = ExpressionParse.parseExpression(detachedCriteria, name, "="+((String[])entry.getValue())[0], clazz);
			}else
            detachedCriteria = ExpressionParse.parseExpression(detachedCriteria, name, ((String[])entry.getValue())[0], clazz);
	    }while(true);
		return detachedCriteria;
	}

	public DetachedCriteria setProjections(DetachedCriteria detachedCriteria) {
		ProjectionList projectionList = Projections.projectionList();
		if(this.getGridConfig().getListColumnConfig().size() != 0){
			for (ColumnConfig columnConfig : this.getGridConfig().getListColumnConfig()) {
				String column = columnConfig.getDataIndex();
				if(column.indexOf(".") > 1 && column.indexOf(".") != column.lastIndexOf(".")){
					column=column.substring(column.lastIndexOf(".", column.lastIndexOf(".")-1)+1);
				}
				projectionList.add(Projections.property(column));
			}
			detachedCriteria.setProjection(projectionList);
		}
		return detachedCriteria;
	}

	/**
	 * 递归遍历bean，取得用户输入的参数，置入DetachedCriteria
	 * 
	 * @param detachedCriteria
	 * @param bean
	 * @param isBase
	 * @return
	 */
	private DetachedCriteria setDetachedCriteria(
			DetachedCriteria detachedCriteria, Object bean, boolean isBase, String parentBeanName) {
		if (bean == null)
			return detachedCriteria;

		Class klass = bean.getClass();
		Method method;
		Object obj = null;
		String name;
		String key;

		Method[] methods = klass.getMethods();
		for (int i = 0; i < methods.length; i += 1) {
			method = methods[i];
			name = method.getName();
			key = "";
			if (name.startsWith("get")) {
				key = name.substring(3);
			} else if (name.startsWith("is")) {
				key = name.substring(2);
			} else {
				continue;
			}

			if (key.length() > 0 && Character.isUpperCase(key.charAt(0))
					&& method.getParameterTypes().length == 0) {
				if (key.length() == 1) {
					key = key.toLowerCase();
				} else if (!Character.isUpperCase(key.charAt(1))) {
					key = key.substring(0, 1).toLowerCase() + key.substring(1);
				}
			}

			if (key.equals("class") || key.equals("integer"))
				continue;
			try {
				obj = method.invoke(bean, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (obj != null) {
				String dcKey = null;
				if (!isBase) {
					dcKey = parentBeanName + "." + key;
				} else {
					dcKey = key;
				}

//				if (obj.getClass().getName().equals("java.lang.String") && key.endsWith("name")) {
				if (obj.getClass().getName().equals("java.lang.String")) {
					if (!obj.toString().equals("")) {
						detachedCriteria.add(Restrictions.eq(dcKey, obj
								.toString()));
						logger.info("setDetachedCriteria: " + dcKey + "~"
								+ obj.toString());
					}
				} else if (obj.getClass().getName().equals("java.lang.String")
						|| obj.getClass().getName().equals("java.lang.Double")
						|| obj.getClass().getName().equals("java.lang.Long")
						|| obj.getClass().getName().equals("java.util.Date")) {
					if (!obj.toString().equals("")) {
						detachedCriteria.add(Restrictions.eq(dcKey, obj));
						logger.info("setDetachedCriteria: " + dcKey + "="
								+ obj.toString());
					}
				} else if (obj.getClass().getName().equals("java.util.HashSet")){
				}else {
					detachedCriteria = setDetachedCriteria(detachedCriteria,
							obj, false, key);
				}
			}
		}
		return detachedCriteria;
	}
	
	/*
	 * zhangyingying 
	 * 设置横向权限 登陆人可以浏览的专业年级层次站点由session中读出，如果登陆人没有配置横向权限 则认为不加限制
	 */
	private DetachedCriteria setDetachedCriteriaPriority(DetachedCriteria detachedCriteria) {
		CriteriaImpl impl = (CriteriaImpl) detachedCriteria.getExecutableCriteria(null);
		Iterator it = impl.iterateSubcriteria();
		String entityFullName = impl.getEntityOrClassName();
		String entityName = entityFullName.substring(entityFullName.lastIndexOf(".") + 1);
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (us != null) {
			if (entityName != null
					&& entityName.equals("PeProApplyno")) {    //|| entityName.equals("PeEnterprise")
				List<String> objIds = new ArrayList<String>();
				if (entityName.equals("PeProApplyno")) {
					for (PeProApplyno obj : us.getPriPros()) {
						objIds.add(obj.getId());
					}
				}
				
				if (objIds.size()!=0) {
					detachedCriteria.add(Restrictions.in("id", objIds));
				}
				return detachedCriteria;
			}
			while ( it.hasNext() ) {
				Criteria subcriteria = (Criteria) it.next();
				if ( subcriteria.getAlias()!=null ) {
					List<String> objIds=new ArrayList<String>();
					if(subcriteria.getAlias().equals("peProApplyno")){
						for (PeProApplyno obj : us.getPriPros()) {
							objIds.add(obj.getId());
						}
					}
					if(objIds.size()!=0){
						detachedCriteria.add(Restrictions.in(subcriteria.getAlias()+".id", objIds));
					}
				}
			}
		}
		return detachedCriteria;
	}
	
	public static String addPri(String sql){
		String sql_tmp = null;
		ActionContext ctx= ActionContext.getContext();
		if (ctx == null || ctx.getSession() == null) {
			return sql;
		}
		UserSession us = null;
		us = (UserSession) ctx.getSession().get(SsoConstant.SSO_USER_SESSION_KEY_BAK);
		us = us==null?(UserSession) ctx.getSession().get(SsoConstant.SSO_USER_SESSION_KEY):us;
		if(ctx.getSession()!=null&&us!=null){
			sql_tmp = sql.toUpperCase();
			int n_pro = sql_tmp.indexOf("PE_PRO_APPLYNO ");
			if(n_pro >= 0){
				List<PeProApplyno> tmp_list = us.getPriPros();
				List<String> replace_tmp = new ArrayList<String>();
				StringBuffer beanIds = new StringBuffer();
				if(tmp_list!=null&&tmp_list.size()>0) {
					for(PeProApplyno bean : tmp_list ) {
						beanIds.append("'"+bean.getId()+"',");
					}
					while(n_pro>=0){
						String replaceStr = sql.substring(n_pro,n_pro+15);
						replace_tmp.add(replaceStr);
						sql_tmp = sql_tmp.replaceFirst("PE_PRO_APPLYNO ", "pe_pro_applyno ");
						n_pro = sql_tmp.indexOf("PE_PRO_APPLYNO ");
					}
					if(beanIds.length()>0)
						beanIds.deleteCharAt(beanIds.length()-1);
					for(String str_tmp:replace_tmp){
						sql = sql.replace(str_tmp, "(SELECT * FROM PE_PRO_APPLYNO WHERE ID IN ("+beanIds+"))");
					}
				}
			}
			

		}
		return sql;
	}
	
	/**
	 * 此方法已废弃，不推荐使用
	 * 存在FK字段的bean在add方法之前需要调用此方法
	 * 用于将ComboBox的name转换为Id
	 * 依赖于假设条件：数据库中所有表的name已经存在唯一性约束
	 * 遍历子bean的name，如果有值，则访问数据库取出Id，赋予子bean
	 */
	public Object setSubIds(Object bean) {
		// 不调用数据库中实例
		return setSubIds(bean, bean);
	}
	
	/**
	 * 存在FK字段的bean在update方法之前需要调用此方法
	 * 把数据库中的bean实例与Action接收的参数合并为一个bean返回
	 * 调用方法（先调用）：
	 * Bean dbInstance = beanSerivce.getById(this.getBean().getId());
	 */
	public Object setSubIds(Object dbInstance, Object bean) {
		Class klass = bean.getClass();
		Method method, setId, getName;
		Object obj = null;
		String name;
		String key;

		// 遍历bean类的方法
		Method[] methods = klass.getMethods();
		for (int i = 0; i < methods.length; i++) {
			method = methods[i];
			name = method.getName();
			key = "";

			// 通过getXxx, isXxx找到bean的成员属性
			if (name.startsWith("get")) {
				if(name.contains("EnumConstBy")||name.contains("PeUnitBy")){
					key = name.substring(3);
				}else{
					key = method.getReturnType().toString();
					key = key.substring(key.lastIndexOf(".")+1, key.length());
				}
			} else if (name.startsWith("is")) {
				key = name.substring(2);
			} else {
				continue;
			}
			// 获取此成员属性的值，保存为obj
			try {
				obj = method.invoke(bean, null);
			} catch (Exception e) {
			}
			// 如果obj不为空，并且不是HashSet类型，则尝试从bean中获取值，赋值给dbInstance
			if (obj != null && !obj.getClass().getName().equals("java.util.HashSet")) {
				try {
					getName = obj.getClass().getMethod("getName", null);
					Class[] parameterTypes = new Class[1];
					parameterTypes[0] = String.class;
					setId = obj.getClass().getMethod("setId", parameterTypes);
					if (getName != null && setId != null) {
						String nameValue = getName.invoke(obj, null).toString();
						if (nameValue != null) {
							if (nameValue.equals("") && !key.startsWith("EnumConstBy")&&!key.startsWith("PeUnitBy")) {
								obj = null;
							} else {
								/**
								 * 如果name有值，则取id
								 * 如果name为空并且key是以EnumConstBy开头，则取EnumConst该NameSpace下的默认值
								 */
								String idValue = myListService.getIdByName(key, nameValue);
								if("getCounty".equals(name)){
									County county = (County)obj;
									if(county.getId() == null){
										PeTrainee pe = (PeTrainee)bean;
										String sqls = "select c.id from county c left join city ci on c.fk_city=ci.id where c.name='"+nameValue+"' and ci.name='"+pe.getCity().getName()+"'";
										List listcounty = this.getGeneralService().getBySQL(sqls);
										if(listcounty != null && listcounty.size()>0){
											idValue = listcounty.get(0).toString();
										}
									}else{
										idValue = county.getId();
									}
									
								}
								// 如果取到了id，则将id赋值给obj，否则obj赋值为null
								if (idValue != null && !idValue.equals("")) {
									String[] parameters = {idValue};
									setId.invoke(obj, parameters);
								} else {
									obj = null;
								}
							}
							// 查找该obj的set方法，将obj赋值给父bean
							for (int j = 0; j < methods.length; j++) {
								if (methods[j].getName().equals("set" + key)) {
									Object[] objs = new Object[1];
									objs[0] = obj;
									methods[j].invoke(dbInstance, objs);
									break;
								}
							}
						}
					}
				} catch (Exception e) {
					key = name.substring(3);
					// 查找该obj的set方法，将obj赋值给父bean
					for (int j = 0; j < methods.length; j++) {
						if (methods[j].getName().equals("set" + key)) {
							Object[] objs = new Object[1];
							objs[0] = obj;
							try {
								methods[j].invoke(dbInstance, objs);
							} catch (Exception e1) {
							}
							break;
						}
					}
				}
			}
		}
		return dbInstance;
	}
	
	/**
	 * results: "grid", "gridjs", "json"
	 */
	/**
	 * *.action: 定向默认result到 "grid"
	 */
	public String execute() {
		return "grid";
	}
	
	/**
	 * *_gridjs.action: addColumn() and return "gridjs"
	 */
	public String gridjs() {

		initGrid();
		this.setEntityMemberName("bean");
		setPriorityCapability();
		return "gridjs";
	}
	/*
	 * zhangyingying 配置是否有添加删除更新功能 
	 * 说明 如果权限为* 则按子类默认值 否则，在子类有此能力而登陆人没有权限时需要更改配置true为false
	 */
	public void setPriorityCapability() {
		UserSession us = (UserSession)ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if(us!=null){
			Set capabilitySet=us.getUserPriority().keySet();
			if(!capabilitySet.contains(this.servletPath+"_*.action")){
				boolean canAdd=this.gridConfig.isCanAdd();
				boolean canDelete=this.gridConfig.isCanDelete();
				boolean canUpdate=this.gridConfig.isCanUpdate();
				if(canAdd&&!capabilitySet.contains(this.servletPath+"_abstractAdd.action")){
					canAdd=false;
			    }
				if(canDelete&&!capabilitySet.contains(this.servletPath+"_abstractDelete.action")){
					canDelete=false;
			    }
				if(canUpdate&&!capabilitySet.contains(this.servletPath+"_abstractUpdate.action")){
					canUpdate=false;
			    }
				this.gridConfig.setCapability(canAdd, canDelete, canUpdate);
			}
		}
	}
	//FIXME: 为什么不是abstract?
	public abstract void initGrid();

	/**
	 * *_json.action: construct jsonString and return "json"
	 */
	public String json() {
		return "json";
	}
	
	/**
	 * Abstract methods for actions
	 * @return
	 */
	public String abstractList() {
		initGrid();
		Page page = list();
		List jsonObjects = JsonUtil.ArrayToJsonObjects(page.getItems(), this.getGridConfig().getListColumnConfig());
		Map map = new HashMap();
		if (page != null) {
			map.put("totalCount", page.getTotalCount());
			map.put("models", jsonObjects);
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		JsonUtil.setDateformat(DEFAULT_DATE_FORMAT);
		return json();
	}
	
	public String abstractDetail() {
		Page page = list();
		Map map = new HashMap();
		if (page != null) {
			page.setItems(JsonUtil.ArrayToJsonObjects(page.getItems(), this.getGridConfig().getListColumnConfig()));
			Container o = new Container();
			o.setBean(page.getItems().get(0));
			List list = new ArrayList();
			list.add(o);
			map.put("totalCount", 1);
			map.put("models", list);		
		}		
		this.setJsonString(JsonUtil.toJSONString(map));
		JsonUtil.setDateformat(DEFAULT_DATE_FORMAT);
		return json();
	}

	public String abstractAdd() {
		this.modifyEndDates();
		Map map = add();
		if (map == null) {
			map = new HashMap();
			map.put("success", "false");
			map.put("info", this.getText(("Add method is not implemented in Action")));
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}

	public String abstractDelete() {
		Map map = delete();
		if (map == null) {
			map = new HashMap();
			map.put("success", "false");
			map.put("info", this.getText(("Delete method is not implemented in Action")));
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}

	public String abstractUpdate() {
		this.modifyEndDates();
		Map map = update();
		if (map == null) {
			map = new HashMap();
			map.put("success", "false");
			map.put("info", this.getText(("Update method is not implemented in Action")));
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		JsonUtil.setDateformat(DEFAULT_DATE_FORMAT);
		return json();
	}

	public String abstractUpdateColumn() {
		Map map = updateColumn();
		if (map == null) {
			map = new HashMap();
			map.put("success", "false");
			map.put("info",this.getText(("UpdateColumn method is not implemented in Action")));
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}
	
	public String abstractExcel() {
		initGrid();
		excel();
		JsonUtil.setDateformat(DEFAULT_DATE_FORMAT);
		return "excel";
	}

	/**
	 * Excel导入处理功能
	 * @return
	 */
	public String batchAddExcelUpload(){
		this.setTogo("back");
		init();
		initGrid4BatchAddExcel();
		int count;
		Map map = new HashMap();
		try {
			count = excelAdd();
		} catch (EntityException e) {
			this.operateLog("Excel上传" + this.getExcelInfo() + "失败");
			map.put("success", "false");
			map.put("info", e.getMessage());
			this.setJsonString(JsonUtil.toJSONString(map));
			return json();
		} catch (Exception e) {
			this.operateLog("Excel上传" + this.getExcelInfo() + "失败");
			map.put("success", "false");
			map.put("info", "文件上传失败");
			this.setJsonString(JsonUtil.toJSONString(map));
			return json();
		}
		
		map.put("success", "true");
		map.put("info", "导入成功<br/> 共上传"+count+"条记录");
		this.setJsonString(JsonUtil.toJSONString(map));
		
		this.operateLog("Excel上传" + this.getExcelInfo() + "成功");
		return json();
	}
	
	/**
	 * Excel导入更新处理功能
	 * @return
	 */
	public String update_excel(){
		this.setTogo("back");
		init();
		initGrid();
		int count;
		Map map = new HashMap();
		try {
			count = excel_update_exe();
			map.put("success", "true");
			map.put("info", "导入成功<br/> 共更新"+count+"条记录");
			this.operateLog("Excel更新" + this.getExcelInfo() + "成功");
		} catch (EntityException e) {
			this.operateLog("Excel更新" + this.getExcelInfo() + "失败");
			e.printStackTrace();
			try {
				this.getGeneralService().saveError();
			} catch (EntityException e2) {
			}
			map.put("success", "false");
			map.put("info", e.getMessage());
		} catch (Exception e) {
			this.operateLog("Excel更新" + this.getExcelInfo() + "失败");
			e.printStackTrace();
			try {
				this.getGeneralService().saveError();
			} catch (EntityException e2) {
			}
			map.put("success", "false");
			map.put("info", "文件上传失败");
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}
	
	/**
	 * 检查Excel文件是否正确
	 * @throws EntityException
	 */
	protected int excelAdd()throws EntityException {
		Workbook work = null;
		File file = this.get_upload();
		try {
			work = Workbook.getWorkbook(file);
		} catch (Exception e){
			e.printStackTrace();
			throw new EntityException("Excel表格读取异常！导入失败！<br/>");
		}
		Sheet sheet = work.getSheet(0);
		int rows = sheet.getRows();			//获取Excel表格的行数
		if(rows<2){
			throw new EntityException("表格为空！<br/>");
		}
		String message="";
		String temp = "";
		int j= 0;
		List<ColumnConfig> columns = this.gridConfig.getListColumnConfig();
		/**
		 * i从1开始，跳过第一列id
		 */
		for (int i=1; i<columns.size(); i++) {
			ColumnConfig columnConfig = columns.get(i);
			if(!columnConfig.isAdd()){
				continue;
			}
			try {
				temp = sheet.getCell(j, 0).getContents().trim();
				j++;
				if(temp!=null&&temp.length()>0&&temp.indexOf(columnConfig.getName())>=0){
				} else {
					message+="第1行第"+(j)+"列单元格内容错误<br/>";
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new EntityException("Excel表格读取异常！导入失败！<br/>");
			}
		}
		if(message.length()>0){
			throw new EntityException(message+"<br/>请检查Excel表格正确后重新上传。");
		}
		List list = excelToBean(sheet);
/*		for (int i = 0; i < list.size(); i++) {
			list.set(i, setSubIds(list.get(i)));
		}*/
		list = this.checkBeforeBatchAdd(list);
		try {
			this.getGeneralService().saveList(list);
		} catch (Exception e) {
			throw new EntityException(this.checkAlternateKey(e, "保存").get("info").toString());
		}
		work.close();
		return list.size();
	}
	
	/**
	 * 更新处理功能，首先检查excel是否正确
	 * @throws EntityException
	 */
	private int excel_update_exe()throws EntityException {
		Workbook work = null;
		File file = this.get_upload();
		try {
			work = Workbook.getWorkbook(file);
		} catch (Exception e){
			e.printStackTrace();
			throw new EntityException("Excel表格读取异常！导入失败！<br/>");
		}
		Sheet sheet = work.getSheet(0);
		int rows = sheet.getRows();			//获取Excel表格的行数
		if(rows<2){
			throw new EntityException("表格为空！<br/>");
		}
		String message="";
		String temp = "";
		int j= 0;
		List<ColumnConfig> columns = this.gridConfig.getListColumnConfig();
		
		//检查excel表格第一行格列是否正确
		for (int i=0; i<columns.size(); i++) {
			ColumnConfig columnConfig = columns.get(i);
			if(columnConfig.isAdd()||columnConfig.isList()){
				try {
					temp = sheet.getCell(j, 0).getContents().trim();
					j++;
					if(temp!=null&&temp.length()>0&&temp.indexOf(columnConfig.getName())>=0){
					} else {
						message+="第1行第"+(j)+"列单元格内容错误<br/>";
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new EntityException("Excel表格读取异常！导入失败！<br/>");
				}
			}
		}
		if(message.length()>0){
			throw new EntityException(message+"<br/>请检查Excel表格正确后重新上传。");
		}
		List list = excel_update_setBean(sheet);
/*		for (int i = 0; i < list.size(); i++) {
			list.set(i, setSubIds(list.get(i)));
		}*/
		list = this.checkBeforeExcelUpdate(list);
		try {
			this.getGeneralService().saveList(list);
		} catch (Exception e) {
			throw new EntityException(this.checkAlternateKey(e, "保存").get("info").toString());
		}
		work.close();
		return list.size();
	}
	
	/**
	 * 调用checkBeforeUpdate方法 检查所有的bean是否合法
	 * @param list
	 * @return
	 * @throws EntityException
	 */
	private List checkBeforeExcelUpdate(List list ) throws EntityException {
		String message="";
		for (int i = 0; i < list.size(); i++) {
			this.superSetBean((T)list.get(i));
			try {
				this.checkBeforeUpdate();
				list.set(i, this.superGetBean());
			} catch (EntityException e) {
				e.printStackTrace();
				message+="第"+(i+2)+"行数据,"+e.getMessage()+"<br/>";
			}
		}
		if(message.length()>0){
			throw new EntityException(message);
		}
		return list;
	}
	
	/**
	 * 调用checkBeforeAdd方法 检查所有的bean是否合法
	 * @param list
	 * @return
	 */
	protected List checkBeforeBatchAdd(List list ) throws EntityException {
		String message="";
		for (int i = 0; i < list.size(); i++) {
			this.superSetBean((T)list.get(i));
			try {
				this.checkBeforeAdd();
				list.set(i, this.superGetBean());
			} catch (EntityException e) {
				message+="第"+(i+2)+"行数据,"+e.getMessage()+"<br/>";
			}
		}
		if(message.length()>0){
			throw new EntityException(message);
		}
		return list;
	}
	
	/**
	 * 读取excel文件，设置bean
	 * @param sheet
	 * @return
	 * @throws EntityException
	 */
	private List excel_update_setBean(Sheet sheet)throws EntityException {
		int rows = sheet.getRows();			//获取Excel表格的行数
		StringBuffer message=new StringBuffer();
		String temp = "";
		Method method;
		List<ColumnConfig> columns = this.gridConfig.getListColumnConfig();
		List list = new ArrayList(); //保存excel 生成的bean
		Set<Object> beans = new HashSet<Object>(); //用于看是否有重复的数据
		Set<String> ids = new HashSet<String>(); //用于看是否有重复的id
		for(int i=1;i<rows;i++){
			try{
				int j= 0; //记录列数
				Class obj = this.getEntityClass();
				T  ob=null; 
				for (int n=0; n<columns.size(); n++) {
					ColumnConfig columnConfig=columns.get(n);
					if(columnConfig.isAdd()||columnConfig.isList()){

						String columnName=columnConfig.getName();
						temp = sheet.getCell(j, i).getContents().trim();
						j++;
						if(n==0){
							if(temp==null||temp.length()==0){
								message.append("第"+(i+1)+"行"+columnName+"不能为空！<br/>");
								break;
							}
							try {
								ob = this.getGeneralService().getById(temp);
							} catch (EntityException e1) {
								message.append("第"+(i+1)+"行"+columnName+"不存在！<br/>");
								break;
							}
							if(ob==null){
								message.append("第"+(i+1)+"行"+columnName+"不存在！<br/>");
								break;
							}
							if(!ids.add(temp)){
								message.append("第"+(i+1)+"行"+columnName+"与文件中前面的数据重复！<br/>");
								break;
							}
						}
						if(columnConfig.isAdd()){
						if(temp==null||temp.length()==0){
							if(!columnConfig.isAllowBlank()){
								message.append("第"+(i+1)+"行"+columnName+"不能为空！<br/>");
							}
							continue;
						}
						String columnDataIndex=columnConfig.getDataIndex();
						//判断是否需要正则验证
						String test = columnConfig.getTextFieldParameters();
						if(test!=null&&test.length()>0){
							String test1=test.substring(test.indexOf("(/")+2, test.indexOf("/)"));
							String test2 =test.substring(test.indexOf(":'")+2, test.indexOf("',"));
							if(!temp.matches(test1)){
								message.append("第"+(i+1)+"行"+columnName+"格式错误！"+test2+"<br/>");
								continue;
							}
						}
						try {
							//根据是否有.判断是否是外键
							if(columnDataIndex.indexOf(".")>0){
								columnDataIndex = columnDataIndex.substring(0, columnDataIndex.indexOf("."));
								columnDataIndex = columnDataIndex.substring(0, 1).toUpperCase() + columnDataIndex.substring(1);
								//取出id
								String id = this.getMyListService().getIdByName(columnDataIndex, temp);
								if(id==null||id.equals("")){
									message.append("第"+(i+1)+"行"+columnName+"不存在<br/>");
									continue;
								}
								//判断是否是enumConst
								if(columnDataIndex.startsWith("EnumConstBy")){
									EnumConst enumConst = new EnumConst();
									enumConst.setNamespace(columnDataIndex.substring(11));
									enumConst.setName(temp);
									enumConst.setId(id);
									
									method = this.getEntityClass().getMethod("set" + columnDataIndex, EnumConst.class);
									method.invoke(ob, enumConst);
								}else{
									//生成外键关联的bean
									Class fkBean = Class.forName("com.whaty.platform.entity.bean."+columnDataIndex);
									Object bean=fkBean.newInstance(); 
									method = bean.getClass().getMethod("setName", String.class);
									method.invoke(bean, temp);
									method = bean.getClass().getMethod("setId", String.class);
									method.invoke(bean, id);
									
									method = this.getEntityClass().getMethod("set" + columnDataIndex, bean.getClass());
									method.invoke(ob, bean);
								}
							} else {
								columnDataIndex = columnDataIndex.substring(0, 1).toUpperCase() + columnDataIndex.substring(1);
							Field field =	obj.getDeclaredField(columnConfig.getDataIndex());
							String type = field.getType().getName();
							if(type.indexOf("String")>=0){
								method = this.getEntityClass().getMethod("set" + columnDataIndex, String.class);
								method.invoke(ob, temp);
							}else if (type.indexOf("Double")>=0){
								method = this.getEntityClass().getMethod("set" + columnDataIndex, Double.class);
								method.invoke(ob, Double.parseDouble(temp));
							}else if(type.indexOf("Long")>=0){
								method = this.getEntityClass().getMethod("set" + columnDataIndex, Long.class);
								method.invoke(ob, Long.parseLong(temp));
							}else if(type.indexOf("Date")>=0){
								method = this.getEntityClass().getMethod("set" + columnDataIndex, Date.class);
							       SimpleDateFormat bartDateFormat =  
							           new SimpleDateFormat(DEFAULT_DATE_FORMAT);  
								method.invoke(ob, bartDateFormat.parse(temp));
							}else {
								message.append("不支持的类型："+type+"<br/>");
							}
							}
						} catch (Exception e) {
							e.printStackTrace();
							message.append("第"+(i+1)+"行数据"+columnName+"格式错误，更新失败！<br/>");
							continue;
						}
						}
					}
				}
				if(beans.add(ob)){
					list.add(ob);
				} else{
					message.append("第"+(i+1)+"行数据与文件中前面的数据重复！<br/>");
					continue;
				}
			}catch(Exception e){
				e.printStackTrace();
				message.append("第"+(i+1)+"行数据更新失败！<br/>");
				continue;
			}
		}
		if(message.toString().length()>0){
			throw new EntityException(message.toString()+"请修改完以上错误之后重新上传！");
		}
		return list;
	}
	/**
	 * 读取excel内容
	 * @return
	 */
	protected List excelToBean(Sheet sheet)throws EntityException {
		int rows = sheet.getRows();			//获取Excel表格的行数
		StringBuffer message=new StringBuffer();
		String temp = "";
		Method method;
		List<ColumnConfig> columns = this.gridConfig.getListColumnConfig();
		List list = new ArrayList(); //保存excel 生成的bean
		Set<Object> beans = new HashSet<Object>(); //用于看是否有重复的数据
		for(int i=1;i<rows;i++){
			try{
				int j= 0; //记录列数
				Class obj = this.getEntityClass();
				Object ob=obj.newInstance(); 
				for (int n=1; n<columns.size(); n++) {
					ColumnConfig columnConfig=columns.get(n);
					if(!columnConfig.isAdd()){
						continue;
					}
					String columnName=columnConfig.getName();
					temp = sheet.getCell(j, i).getContents().trim();
					
					j++;
					if(temp==null||temp.length()==0){
						if(!columnConfig.isAllowBlank()){
							message.append("第"+(i+1)+"行"+columnName+"不能为空！<br/>");
						}
						continue;
					}
					if(temp!=null&&!"".equals(temp.trim())){
						if(temp.length()>columnConfig.getMaxLength()){
							message.append("第"+(i+1)+"行"+columnName+"长度不能超过"+columnConfig.getMaxLength()+"！<br/>");
						}
					}
					String columnDataIndex=columnConfig.getDataIndex();
					//判断是否需要正则验证
					String test = columnConfig.getTextFieldParameters();
					if(test!=null&&test.length()>0){
						String test1=test.substring(test.indexOf("(/")+2, test.indexOf("/)"));
						String test2 =test.substring(test.indexOf(":'")+2, test.indexOf("',"));
						if(!temp.matches(test1)){
							message.append("第"+(i+1)+"行"+columnName+"格式错误！"+test2+"<br/>");
							continue;
						}
					}
					try {
						//根据是否有.判断是否是外键
						if(columnDataIndex.indexOf(".")>0){
							columnDataIndex = columnDataIndex.substring(0, columnDataIndex.indexOf("."));
							columnDataIndex = columnDataIndex.substring(0, 1).toUpperCase() + columnDataIndex.substring(1);
							//取出id
							
							String id = this.getMyListService().getIdByName(columnDataIndex, temp);
							if("County".equals(columnDataIndex)){
								String temp1 = sheet.getCell((j-2), i).getContents().trim();
//								String sql = "select t.name from pe_pri_role t where t.id = '" + userType + "'";
//								List list = this.getGeneralService().getBySQL(sql);
								String sqls = "select c.id from county c left join city ci on c.fk_city=ci.id where c.name='"+temp+"' and ci.name='"+temp1+"'";
								List listcounty = this.getGeneralService().getBySQL(sqls);
								if(listcounty != null && listcounty.size()>0){
									id = listcounty.get(0).toString();
								}
							}
							if(id==null||id.equals("")){
								message.append("第"+(i+1)+"行"+columnName+"不存在<br/>");
								continue;
							}
							//判断是否是enumConst
							if(columnDataIndex.startsWith("EnumConstBy")){
								EnumConst enumConst = new EnumConst();
								enumConst.setNamespace(columnDataIndex.substring(11));
								enumConst.setName(temp);
								enumConst.setId(id);
								
								method = this.getEntityClass().getMethod("set" + columnDataIndex, EnumConst.class);
								method.invoke(ob, enumConst);
							}else if(columnDataIndex.startsWith("PeUnitBy")){
								PeUnit unit=new PeUnit();
								unit.setId(id);
								method = this.getEntityClass().getMethod("set" + columnDataIndex, PeUnit.class);
								try{
								method.invoke(ob, unit);
								}catch(Exception e){
									e.printStackTrace();
								}
							}else{
								//生成外键关联的bean
								Class fkBean = Class.forName("com.whaty.platform.entity.bean."+columnDataIndex);
								Object bean=fkBean.newInstance(); 
								method = bean.getClass().getMethod("setName", String.class);
								method.invoke(bean, temp);
								method = bean.getClass().getMethod("setId", String.class);
								method.invoke(bean, id);
								
								method = this.getEntityClass().getMethod("set" + columnDataIndex, bean.getClass());
								method.invoke(ob, bean);
							}
						} else {
							columnDataIndex = columnDataIndex.substring(0, 1).toUpperCase() + columnDataIndex.substring(1);
						Field field =	obj.getDeclaredField(columnConfig.getDataIndex());
						String type = field.getType().getName();
						if(type.indexOf("String")>=0){
							method = this.getEntityClass().getMethod("set" + columnDataIndex, String.class);
							method.invoke(ob, temp);
						}else if (type.indexOf("Double")>=0){
							method = this.getEntityClass().getMethod("set" + columnDataIndex, Double.class);
							method.invoke(ob, Double.parseDouble(temp));
						}else if(type.indexOf("Long")>=0){
							method = this.getEntityClass().getMethod("set" + columnDataIndex, Long.class);
							method.invoke(ob, Long.parseLong(temp));
						}else if(type.indexOf("Date")>=0){
							method = this.getEntityClass().getMethod("set" + columnDataIndex, Date.class);
						       SimpleDateFormat bartDateFormat =  
						           new SimpleDateFormat(DEFAULT_DATE_FORMAT);  
							method.invoke(ob, bartDateFormat.parse(temp));
						}else {
							message.append("不支持的类型："+type+"<br/>");
						}
						}
					} catch (Exception e) {
						e.printStackTrace();
						message.append("第"+(i+1)+"行数据"+columnName+"格式错误，添加失败！<br/>");
						continue;
					}
				}
				if(beans.add(ob)){
					list.add(ob);
				} else{
					message.append("第"+(i+1)+"行数据与文件中前面的数据重复！<br/>");
					continue;
				}
			}catch(Exception e){
				e.printStackTrace();
				message.append("第"+(i+1)+"行数据添加失败！<br/>");
				continue;
			}
		}
		if(message.toString().length()>0){
			throw new EntityException(message.toString()+"请修改完以上错误之后重新上传！");
		}
		return list;
	}
	/**
	 * Excel导入,生成模板
	 */
	protected void initGrid4BatchAddExcel() {
		initGrid();
	}
	
	/**
	 * Excel导入,生成模板
	 * @return
	 */
	public String batchAddExcel(){
		initGrid4BatchAddExcel();
		batchExcel();
		return "excel";
	}
	
	/**
	 * Excel更新,生成模板
	 * @return
	 */
	public String excelUpdate(){
		initGrid();
		excel_updateOutput();
		return "excel";
	}
	
	private void batchExcel(){
		try {
			// 打开文件
			WritableWorkbook book = Workbook.createWorkbook(new File(
					ServletActionContext.getServletContext().getRealPath(
							"/test/export.xls")));
			WritableSheet sheet = book.createSheet("ExcelReport", 0);
			sheet = this.addExcelTitle(sheet, 0);
			
			book.write();
			book.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 批量更新，生成excel
	 */
	private void excel_updateOutput(){
		String[] ids = getIds().split(",");
		List<String> idList = new ArrayList();
		for (int i = 0; i < ids.length; i++) {
			idList.add(ids[i]);
		}
		try {
			// 打开文件
			WritableWorkbook book = Workbook.createWorkbook(new File(
					ServletActionContext.getServletContext().getRealPath(
							"/test/export.xls")));
			WritableSheet sheet = book.createSheet("ExcelReport", 0);

			sheet = this.addExcelTitle_update(sheet, 0);
			for (int i = 0; i < idList.size(); i++) {
				String string = idList.get(i);
				Object bean = this.getGeneralService().getById(string);
				sheet = this.excel_update_addRow(sheet, bean, i+1);
			}
			book.write();
			book.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新操作 向 Excel 工作表中添加一行
	 * @param sheet 已有工作表
	 * @param bean 引用的bean实例
	 * @param rowIndex 行索引序列号
	 * @return 添加了当前行的工作表
	 */
	private WritableSheet excel_update_addRow(WritableSheet sheet, Object bean, int rowIndex) {
		List<ColumnConfig> columns = this.gridConfig.getListColumnConfig();
		
		/**
		 * i从1开始，跳过第一列id
		 */
		if(!bean.getClass().isArray()){
			int j = 0;
			//对象类型
			for (int i=0; i<columns.size(); i++) {
				ColumnConfig columnConfig = columns.get(i);
				if(columnConfig.isAdd()||columnConfig.isList()){
					sheet = this.addCell(sheet, bean, columnConfig.getDataIndex(), j, rowIndex);
					j++;
				}
			}
		} else {
			// 数组类型
			Object[] beanArray = (Object[]) bean;
			for (int i = 0; i < columns.size(); i++) {
				String valueString = "";
				if (beanArray[i] instanceof String) {
					valueString = (String) beanArray[i];
				} else if (beanArray[i] instanceof Number) {
					valueString = ((Number) beanArray[i]).toString();
				} else if (beanArray[i] instanceof Date
						|| beanArray[i] instanceof Timestamp
						|| beanArray[i] instanceof java.sql.Date) {
					SimpleDateFormat sf = null;
					if (dateformat == null) {
						sf = new SimpleDateFormat(this.DEFAULT_DATE_FORMAT);
					} else {
						sf = new SimpleDateFormat(dateformat);
					}
					valueString = sf.format(beanArray[i]);
				}
			      WritableCellFormat   contentFromart   =   new   WritableCellFormat(NumberFormats.TEXT); 
//			        jxl.write.Label   labelCFC2   =   new   jxl.write.Label(0,15, 01234567890123456789 ,   contentFromart); 
//			        sheet.addCell(labelCFC2);    
				Label label = new Label(i - 1, rowIndex, valueString, contentFromart);
				try {
					sheet.addCell(label);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return sheet;
	}
	
	/**
	 * 向 Excel 工作表中添加标题行
	 * @param sheet 已有工作表
	 * @param rowIndex 行索引序列号，通常可输入0
	 * @return 添加了标题行的工作表
	 */
	private WritableSheet addExcelTitle(WritableSheet sheet, int rowIndex) {
		List<ColumnConfig> columns = this.gridConfig.getListColumnConfig();
		int j = 1;
		/**
		 * i从1开始，跳过第一列id
		 */
		for (int i=1; i<columns.size(); i++) {
			ColumnConfig columnConfig = columns.get(i);
			if(!columnConfig.isAdd()){
				continue;
			}
			Label label = null;
			if(columnConfig.isAllowBlank()){
				label = new Label(j-1, rowIndex, columnConfig.getName());
			} else{
				label = new Label(j-1, rowIndex, columnConfig.getName()+"*");
			}
			try {
				sheet.addCell(label);
				j++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sheet;
	}

	/**
	 * 批量更新，生成excel
	 * 向 Excel 工作表中添加标题行
	 * @param sheet 已有工作表
	 * @param rowIndex 行索引序列号，通常可输入0
	 * @return 添加了标题行的工作表
	 */
	private WritableSheet addExcelTitle_update(WritableSheet sheet, int rowIndex) {
		List<ColumnConfig> columns = this.gridConfig.getListColumnConfig();
		int j = 1;
		// i从0开始，显示第一列id
		for (int i=0; i<columns.size(); i++) {
			ColumnConfig columnConfig = columns.get(i);
			Label label = null;
			if(i==0){
				label = new Label(j-1, rowIndex, columnConfig.getName() + "(不可编辑)");
			} else {
				if(columnConfig.isAdd()){
					String data = columnConfig.getDataIndex();
					if(data.indexOf(".")<0||((data.indexOf(".")==data.lastIndexOf("."))&&data.endsWith(".name"))){
						if(columnConfig.isAllowBlank()){
							label = new Label(j-1, rowIndex, columnConfig.getName() + "(可编辑)");
						} else{
							label = new Label(j-1, rowIndex, columnConfig.getName() + "(可编辑)" + "*");
						}
					}else {
						label = new Label(j-1, rowIndex, columnConfig.getName());
					}
				} else if(columnConfig.isList()){
					label = new Label(j-1, rowIndex, columnConfig.getName());
				} else {
					continue;
				}
			}
			try {
				sheet.addCell(label);
				j++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sheet;
	}

	/**
	 * 初始化Action的所有参数：
	 * 		setServletPath()
	 * 		Action对应servlet的路径（不含.action），例如	 * /entity/basic/site(.action)
	 * 		setTitle() Action显示表格的标题，例如 * this.getText("site")+this.getText("manage") （站点管理）
	 * 		addColumn() 数据中含有的数据列
	 * 		addMenuFunction() 表格菜单功能
	 * 		addRenderFunction() 列功能
	 */
	public void init() {
		this.setEntityClass();
		this.setServletPath();
	}

	/**
	 * list方法： 接收页面的搜索参数（自动存储到bean实例成员中） 调用service层的getByPage方法
	 * 查询结果使用this.setJsonString()方法存储到jsonString
	 * @return Page
	 */
//	public abstract Page list();
	
	/**
	 * 子类写明类型  再调用supersetBean();
	 * @param instance
	 */
//	public abstract void setBean(T instance);
	
	/**
	 * 根据list()方法返回的结果生成Excel文件，并保存至磁盘指定位置
	 */
	private void excel() {
		// 在输出新的Excel之前把历史文件删除掉，避免临时目录中产生大量垃圾文件
		File testDirectory = new File(ServletActionContext.getServletContext().getRealPath("/test"));
		File[] readyDeleteFiles = testDirectory.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				String fileName = pathname.getName();
				String deleteFileNameReg = "^export_\\w*_\\d{8}\\.xls$";
				if(fileName.matches(deleteFileNameReg)) {
					return true;
				}
				return false;
			}
		});
		for (int i = 0; i < readyDeleteFiles.length; i++) {
			readyDeleteFiles[i].delete();
		}
		
		if(this.getStart() == null || "".equals(this.getStart())
				|| this.getLimit() == null || "".equals(this.getLimit())){
			this.setStart("0");
			this.setLimit("1000000");
		}
		if(this.getFormat() != null && "XLSALL".equals(this.getFormat())){
			this.setStart("0");
			this.setLimit("1000000");
		}
		List list = list().getItems();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		
		String suffix = getServletPath().substring(getServletPath().lastIndexOf("/")+1,getServletPath().length()-6)+ "_" + dateFormat.format(new Date());
		try {
			// 打开文件
			WritableWorkbook book = Workbook.createWorkbook(new File(
					ServletActionContext.getServletContext().getRealPath("/test/export_" + suffix + ".xls")));
			if(list.size()<65536){
				WritableSheet sheet = book.createSheet("data", 0);

				sheet = this.addTitle(sheet, 0);
				
				for (int i = 0; i < list.size(); i++) {
					Object bean = list.get(i);
					sheet = this.addRow(sheet, bean, i+1);
				}
			}else{
				for(int s=0 ;s<=list.size()/50000;s++){
					WritableSheet sheet = book.createSheet("data"+(1+s), s);

					sheet = this.addTitle(sheet, 0);
					
					for (int i = s*50000; i < list.size()&& i<(s+1)*50000 ; i++) {
						Object bean = list.get(i);
						sheet = this.addRow(sheet, bean, i+1-s*50000);
					}
				}
			}
			ServletActionContext.getRequest().setAttribute("exportFileName", "export_" + suffix + ".xls");
			book.write();
			book.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
	
	/**
	 * 向 Excel 工作表中添加标题行
	 * @param sheet 已有工作表
	 * @param rowIndex 行索引序列号，通常可输入0
	 * @return 添加了标题行的工作表
	 */
	private WritableSheet addTitle(WritableSheet sheet, int rowIndex) {
		List<ColumnConfig> columns = this.gridConfig.getListColumnConfig();
		
		//i从1开始，跳过第一列id
		int j=0;
		for (int i=1; i<columns.size(); i++) {
			ColumnConfig columnConfig = columns.get(i);
			if(columnConfig.isList()){
				Label label = new Label(j++, rowIndex, columnConfig.getName());
				try {
					sheet.addCell(label);
				} catch (Exception e) {
				}
			}
		}
		return sheet;
	}

	/**
	 * 向 Excel 工作表中添加一行
	 * @param sheet 已有工作表
	 * @param bean 引用的bean实例
	 * @param rowIndex 行索引序列号
	 * @return 添加了当前行的工作表
	 */
	private WritableSheet addRow(WritableSheet sheet, Object bean, int rowIndex) {
		List<ColumnConfig> columns = this.gridConfig.getListColumnConfig();
		/**
		 * i从1开始，跳过第一列id
		 */
		if(!bean.getClass().isArray()){
			
			//对象类型
			int j=0;
			for (int i=1; i<columns.size(); i++) {
				ColumnConfig columnConfig = columns.get(i);
				if(columnConfig.isList()){
					sheet = this.addCell(sheet, bean, columnConfig.getDataIndex(), j++, rowIndex);
				}
			}
		} else {
			// 数组类型
			Object[] beanArray = (Object[]) bean;
			int j=0;
			for (int i = 1; i < columns.size(); i++) {
				ColumnConfig columnConfig = columns.get(i);
				if(columnConfig.isList()){
					String valueString = "";
					if (beanArray[i] instanceof String) {
						valueString = (String) beanArray[i];
					} else if (beanArray[i] instanceof Number) {
						valueString = ((Number) beanArray[i]).toString();
					} else if (beanArray[i] instanceof Date
							|| beanArray[i] instanceof Timestamp
							|| beanArray[i] instanceof java.sql.Date) {
						SimpleDateFormat sf = null;
						if (JsonUtil.getDateformat() == null) {
							sf = new SimpleDateFormat(this.DEFAULT_DATE_FORMAT);
						} else {
							sf = new SimpleDateFormat(JsonUtil.getDateformat());
						}
						valueString = sf.format(beanArray[i]);
					}
		        //单元格格式设置为纯文本
		        WritableCellFormat   contentFromart   =   new   WritableCellFormat(NumberFormats.TEXT); 
					
					Label label = new Label(j++, rowIndex, valueString, contentFromart);
					try {
						sheet.addCell(label);
					} catch (Exception e) {
					}
				}
			}
		}
		return sheet;
	}
	
	/**
	 * 向 Excel 工作表中添加一个单元格
	 * @param sheet 已有工作表
	 * @param bean 引用的bean实例
	 * @param dataIndex 列索引，包含bean的层次结构，例如：mainBean1.subBean2.name
	 * @param columnIndex 列索引序列号
	 * @param rowIndex 行索引序列号
	 * @return 添加了当前单元格的工作表
	 */
	private WritableSheet addCell(WritableSheet sheet, Object bean, String dataIndex, int columnIndex, int rowIndex) {
		Method method;
		String subBeanName;
		Object value;

		/**
		 * 一层一层递进 dataIndex 到最内层，获取最内层的 bean 及属性名
		 */
		while (dataIndex.indexOf(".") >= 0) {
			subBeanName = dataIndex.substring(0, dataIndex.indexOf("."));
			subBeanName = subBeanName.substring(0, 1).toUpperCase() + subBeanName.substring(1);
			dataIndex = dataIndex.substring(dataIndex.indexOf(".") + 1);
			try {
				method = bean.getClass().getMethod("get" + subBeanName, null);
				bean = method.invoke(bean, null);
			} catch (Exception e) {
				e.printStackTrace();
				return sheet;
			}
		}
		/**
		 * 已获取最内层的 bean，及属性名，调用 get 方法，得到真实属性
		 */
		subBeanName = dataIndex;
		try {
			if (bean.getClass().getName().equals("java.util.HashMap")) {
				/**
				 * 如果是HashMap(SQL查询方法经JsonUtil转换返回)，直接获取名为subBean的键值
				 */
				Class[] parameterTypes = new Class[1];
				parameterTypes[0] = Object.class;
				method = bean.getClass().getMethod("get", parameterTypes);
				value = method.invoke(bean, subBeanName);
			} else {
				/**
				 * 否则为bean对象，获取subBean子成员
				 */
				subBeanName = subBeanName.substring(0, 1).toUpperCase() + subBeanName.substring(1);
				method = bean.getClass().getMethod("get" + subBeanName, null);
				value = method.invoke(bean, null);
			}
		} catch (Exception e) {
			return sheet;
		}
		
		/**
		 * 将得到的属性写入单元格
		 */
		if (value != null) {
			if (value instanceof Number)  {
	            value = JSONUtils.numberToString((Number) value);
	        }
	        if(value instanceof Date || value instanceof Timestamp || value instanceof java.sql.Date){
	        	SimpleDateFormat sf = null;
	        	if(JsonUtil.getDateformat() == null){
	        		sf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
	        	}else{
	        		sf = new SimpleDateFormat(JsonUtil.getDateformat());
	        	}
	        	value = JavaScriptUtils.javaScriptEscape(sf.format(value));
	        }			
	        //单元格格式设置为纯文本
	        WritableCellFormat   contentFromart   =   new   WritableCellFormat(NumberFormats.TEXT); 
			Label label = new Label(columnIndex, rowIndex, value.toString(),contentFromart);
			try {
				sheet.addCell(label);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
	        //单元格格式设置为纯文本
	        WritableCellFormat   contentFromart   =   new   WritableCellFormat(NumberFormats.TEXT); 
			Label label = new Label(columnIndex, rowIndex, "",contentFromart);
			try {
				sheet.addCell(label);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return sheet;
	}
	
	/**
	 * 使用sql查询时调用此方法返回登陆人可操作的站点ID 作为查询条件
	 * 当登陆人没有站点限制时返回 " 1=1 " ,可操作所有站点
	 * 当登陆人有可操作站点限制时返回 " site.id in ('id1','id2','id3') "形式，其中"site.id"为入参
	 * @param siteAlias 
	 * @return
	 */

	public T superGetBean() {
		return bean;
	}

	public void superSetBean(T bean) {
		this.bean = bean;
	}

	public Map add() {
		Map map = new HashMap();
		String linkUrl = null;
		if(this._upload != null) {
			linkUrl = saveUpload();
		}
		if(this._upload != null && linkUrl == null) {
			map.put("success", "false");
			map.put("info", "文件上传失败");
			return map;
		}
		this.superSetBean((T)setSubIds(this.bean));
		try {
			checkBeforeAdd();
		} catch (EntityException e1) {
			if(linkUrl != null)
				new File(ServletActionContext.getRequest().getRealPath(linkUrl)).delete();
			map.put("success", "false");
			map.put("info", e1.getMessage());
			return map;
		}
		T instance = null;
		try {
			instance = this.getGeneralService().save(this.bean);
			map.put("success", "true");
			map.put("info", "添加成功");
			this.operateLog("添加" + this.getDataInfo() + "成功");
		} catch (Exception e) {
			this.operateLog("添加" + this.getDataInfo() + "失败");
			if(linkUrl != null)
				new File(ServletActionContext.getRequest().getRealPath(linkUrl)).delete();
			return this.checkAlternateKey(e, "添加");
//			map.clear();
//			map.put("success", "false");
//			map.put("info", "添加失败");
		}
		return map;
	}

	private String saveUpload() {
		try{
			String savePath = "/incoming/photo/";
			//改为通过struts配置文件 动态注入
			//<pram name="filePath">/incoming/filePath</pram> 
			//modified by houxuelong
			if(this.getFilePath()!=null){
				savePath = this.getFilePath();
			}
			String link = savePath+this._uploadFileName;
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
			//FIXME: 转存文件，用java流读写文件，效率待验证。张利斌 2008-11-18
			FileOutputStream fos=new FileOutputStream(ServletActionContext.getRequest().getRealPath(linkTemp));
			FileInputStream fis=new FileInputStream(this._upload);
			byte[] buffer=new byte[1024];
			int len=0;
			while((len=fis.read(buffer))>0){
				fos.write(buffer, 0, len);
			}
			fos.close();
			fis.close();
			String[] fields = this._uploadField.split("\\.");
			Class clazz = this.bean.getClass();
			Object fieldObj = this.bean;
			for (int i = 0; i < fields.length; i++) {
				if(i == fields.length-1){
					Method method = clazz.getMethod("set"+fields[i].substring(0, 1).toUpperCase()+fields[i].substring(1), String.class);
					method.invoke(fieldObj, linkTemp);
				}else{
					Method method = clazz.getMethod("get"+fields[i].substring(0, 1).toUpperCase()+fields[i].substring(1), null);
					method.invoke(fieldObj, null);
				}
			}
			return linkTemp;
		}catch(FileNotFoundException e1){
			e1.printStackTrace();
			return null;
		}catch(IOException e2){
			e2.printStackTrace();
			return null;
		} catch (SecurityException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
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
				try{
					checkBeforeDelete(idList);
				}catch(EntityException e){
					map.put("success", "false");
					map.put("info", e.getMessage());
					return map;
				}
				map.put("success", "true");
				map.put("info", "删除成功");
				try{
					this.operateLog("删除" + this.getDataInfo(idList) + "成功");
					this.getGeneralService().deleteByIds(idList);
					afterDelete();
				}catch (EntityException e) {
					e.printStackTrace();
					this.operateLog("删除失败");
					map.clear();
					map.put("success", "false");
					map.put("info", "删除失败！");
					return map;
				}catch(RuntimeException e){
					this.operateLog("删除" + this.getDataInfo(idList) + "失败");
					return this.checkForeignKey(e);
				}catch(Exception e1){
					this.operateLog("删除" + this.getDataInfo(idList) + "失败");
					map.clear();
					map.put("success", "false");
					map.put("info", "删除失败:记录被其他数据引用！");
					return map;
				}
			} else {
				map.put("success", "false");
				map.put("info", "请选择操作项");
			}
		}
		return map;
	}
	
	//更新方法
	public Map update() {
		Map map = new HashMap();
		String linkUrl = null;
		if(this._upload != null) {
			linkUrl = saveUpload();
		}
		if(this._upload != null && linkUrl == null) {
			map.put("success", "false");
			map.put("info", "文件上传失败");
			return map;
		}
		T dbInstance = null;
		//此处修改过，原来放在this.superSetBean((T)setSubIds(dbInstance,this.bean));下方 gaoyuan 09.11.23
		try {
			checkBeforeUpdate();
		} catch (EntityException e1) {
			try {
				this.getGeneralService().saveError();
			} catch (EntityException e2) {
			}
			map.put("success", "false");
			map.put("info", e1.getMessage());
			return map;
		}
		try {
			dbInstance = this.getGeneralService().getById(this.bean.getId());
		} catch (EntityException e1) {
			map.put("success", "false");
			map.put("info", "更新失败");
			return map;
		}
		String updateInfo = "";
		updateInfo = getUpdateInfo(dbInstance, this.bean);
		this.superSetBean((T)setSubIds(dbInstance,this.bean));
		T instance = null;
		try {
			instance = this.getGeneralService().save(this.bean);
			map.put("success", "true");
			map.put("info", "更新成功");
			this.operateLog("更新" + updateInfo + "成功");
		} catch (Exception e) {
			this.operateLog("更新" + updateInfo + "失败");
			return this.checkAlternateKey(e, "更新");
		}
		return map;
	}

	public Map updateColumn() {
		Map map = new HashMap();
		int count = 0;
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			List idList = new ArrayList();
			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}
			try {
				checkBeforeUpdateColumn(idList);
			} catch (EntityException e) {
				map.clear();
				map.put("success", "false");
				map.put("info", e.getMessage());
				return map;
			}
			try {
				count = this.getGeneralService().updateColumnByIds(idList, this.getColumn(), this.getValue());
			} catch (EntityException e) {
				e.printStackTrace();
				map.clear();
				map.put("success", "false");
				map.put("info", "操作失败");
				this.operateLog("更新列" + this.getDataInfo(idList) + "失败");
				return map;
			}
			map.clear();
			map.put("success", "true");
			map.put("info", this.getText(String.valueOf(count)+"条记录操作成功"));
			this.operateLog("更新列" + this.getDataInfo(idList) + "成功");
		} else {
			map.put("success", "false");
			map.put("info", "parameter value error");
		}
		return map;
	}
	
	public PeSmsInfoService getPeSmsInfoService() {
		return peSmsInfoService;
	}

	public void setPeSmsInfoService(PeSmsInfoService peSmsInfoService) {
		this.peSmsInfoService = peSmsInfoService;
	}

	/**
	 * 选择学生发送短信。
	 * action 的initGrid() 方法中添加按钮 
	 * 形式如下，msg=003 为这个短信所对应的系统短信点的code。
	 *  this.getGridConfig().addMenuFunction("发送短信", "/entity/fee/listStudentForFeeSet_sendSms.action?msg=003", false, true);
	 * @return
	 */
	public String sendSms(){
		String str = this.getIds();
		String[] ids = str.split(",");
		List idList = new ArrayList();
		for (int i = 0; i < ids.length; i++) {
			idList.add(ids[i]);
		}
		Map map = new HashMap();
		try{
			String string = this.getPeSmsInfoService().saveSendStuSms(idList, this.getMsg());
			map.put("success", true);
			map.put("info", string);
		}catch(EntityException e){
			map.put("success", false);
			map.put("info", e.getMessage());
		}
		catch(Exception e){
			e.printStackTrace();
			map.put("success", false);
			map.put("info", "发送短信失败");
		}
		
		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}
	/**
	 * 对Exception处理，检查是否违反完整性约束条件
	 * @param e
	 * @return 返回map
	 */
	public Map checkForeignKey(Exception e){
		Map map = new HashMap();
		String error = e.getCause().getCause().getMessage();
		if(error.startsWith("ORA-02292: 违反完整约束条件")) {
			error = error.split("[()]")[1];
			Map dbForeign = (Map)ServletActionContext.getServletContext().getAttribute(SsoConstant.DB_FOREIGN_KEY);
			ForeignKey key = (ForeignKey)dbForeign.get(error);
			if(key != null){
				map.put("success", "false");
//				map.put("info", "删除失败：记录被 "+this.getText(key.getChildTable())+" 引用,无法删除");
				map.put("info", "删除失败：记录被 "+this.getText(key.getChildTableNote())+" 引用,无法删除");
				return map;
			}
		}
		map.put("success", "false");
		map.put("info", "删除失败:记录被其他数据引用，无法删除。");
		return map;
	}
	
	/**
	 * 对Exception处理，检查是否违反唯一性约束条件
	 * @param e
	 * @param s 所进行的操作
	 * @return 返回map
	 */
	public Map checkAlternateKey(Exception e , String s){
		Map map = new HashMap();
		String error = e.getCause().getCause().getMessage();
		if(error.startsWith("ORA-00001: 违反唯一约束条件")||error.startsWith("ORA-00001: unique constraint")) {
			error = error.split("[()]")[1];
			Map dbAlternate = (Map)ServletActionContext.getServletContext().getAttribute(SsoConstant.DB_ALTERNATE_KEY);
			AlternateKey key = (AlternateKey)dbAlternate.get(error);
			if(key != null){
				map.put("success", "false");
				String str = s + "失败：已经存在相同 ";
				List<String> list = key.getColumns();
				for (String object : list) {
					str += this.getText(object) + " ";
				}
				str += "的"+this.getText(key.getTable())+"";
				map.put("info", str);
				return map;
			}
		}
		map.put("success", "false");
		map.put("info", s + "失败");
		return map;
	}
	
	public Page list(){
		DetachedCriteria dc = initDetachedCriteria();
		dc = setDetachedCriteria(dc, this.superGetBean());
		if(this.canProjections){
			dc = this.setProjections(dc);
		}
		Page page = null;
		try {
			page = getGeneralService().getByPage(dc, Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}

	public Page iniSqllist(StringBuffer sql){
		
		this.setSqlCondition(sql);
		Page page = null;
		try {
			page = getGeneralService().getByPageSQL(isAddPri?addPri(sql.toString()):sql.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return page;
	}
	
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
		return dc;
	}
	
	public void checkBeforeAdd() throws EntityException{
		
	}
	
	public void checkBeforeUpdate() throws EntityException{
		
	}
	
	public void checkBeforeUpdateColumn(List idList) throws EntityException{
		
	}
	
	public void checkBeforeDelete(List idList) throws EntityException{
		
	}
	
	public void afterDelete() throws EntityException{
		
	}
	
	public String convertCharset(String str) {
		try {
			return new String(str.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTogo() {
		return togo;
	}

	public void setTogo(String togo) {
		this.togo = togo;
	}
	
	private void modifyEndDates() {
		//modify bean's endDate property
		try {
			//get bean from action
			Method getBeanMethod = this.getClass().getMethod("getBean", null);
			Object tempBean = getBeanMethod.invoke(this, null);
			//modify tempBean's property
			List<ColumnConfig> list = this.getGridConfig().getListColumnConfig();
			for(int i = 0; i < list.size(); i++){
				ColumnConfig column = list.get(i);
				if(column.isAdd()
						&& column.getDataIndex().toLowerCase().endsWith("enddate")
						&& column.getDataIndex().indexOf(".") < 0){
					modifyEndDate(tempBean, column.getDataIndex());
				}
			}
			//set bean back to Action
			Class[] parameterTypes = new Class[1];
			parameterTypes[0] = tempBean.getClass();
			Method setBeanMethod = this.getClass().getMethod("setBean", parameterTypes);
			setBeanMethod.invoke(this, tempBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void modifyEndDate(Object bean, String fieldName){
		Method getMethod;
		Method setMethod;
		String getMethodName;
		String setMethodName;
		Date value;

		getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		try {
			getMethod = bean.getClass().getMethod(getMethodName, null);
			value = (Date) getMethod.invoke(bean, null);
			value.setTime(value.getTime() + 24*60*60*1000 -1);
			Class[] parameterTypes = new Class[1];
			parameterTypes[0] = Date.class;
			setMethod = bean.getClass().getMethod(setMethodName, parameterTypes);
			setMethod.invoke(bean, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 给SQL语句查询添加 搜索、排序 参数
	 * 在所有getByPageSQL()之前调用
	 * 
	 * 注意：使用sql方法查询的结果如果需要显示ComboBox，
	 * addColumn时对sql的别名字段名添加前缀"Combobox_PeXxxxx."
	 * 
	 * 对任意可执行的完整的sql语句都可以使用，已不存在sql写法的特殊要求。
	 * 张群方 2009-1-13
	 */
	public void setSqlCondition(StringBuffer sql){
		this.setSqlCondition(sql, "");
	}
	
	/**
	 * 此方法已废弃，由setSqlCondition(StringBuffer)代替
	 * 张群方 2009-01-13
	 */
	public void setSqlCondition(StringBuffer sql,String groupBy){
		ActionContext context = ActionContext.getContext(); 
		Map params = context.getParameters();
		//为支持2009-01-13之前所写的sql，特加上如下处理
		this.arrangeSQL(sql, params);
		setCondition(sql,params);
		if(groupBy != null && !"".equals(groupBy)){
			sql.append(groupBy);
		}
		/**
		 * 对于表中含有下划线"_"的字段暂不支持排序
		 * (如果需要对含下划线的字段也支持排序，则命名时要求命名为与数据库字段名相同)
		 */
		String temp = this.getSort();
		//截掉前缀 Combobox_PeXxxxx.
		if(temp.indexOf(".") > 1){
			if(temp.toLowerCase().startsWith("combobox_")){
				temp = temp.substring(temp.indexOf(".") + 1);
			}
		}
		if (this.getSort() != null && temp != null) {
			if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")){
				sql.append(" order by " +temp+ " desc ");
			}else{
				sql.append(" order by " +temp+ " asc ");
			}
			if(!temp.equals("id")){
				sql.append(" , id desc");
			}
		}
	}
	
	/**
	 * 为SQL查询方法添加搜索条件，遍历servlet的参数中的search__开头的变量，设置到sql中
	 * 参考setDC(DetachedCriteria, Map)方法
	 * @param sql 
	 * @param params
	 */
	private void setCondition(StringBuffer sql,Map params){
		Iterator iterator = params.entrySet().iterator();
		do {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            String name = entry.getKey().toString();
            String value = ((String[])entry.getValue())[0].toString();
            if(!name.startsWith("search__"))
            	continue;
            if("".equals(value))
            	continue;
            if(name.indexOf(".") > 1 && name.indexOf(".") != name.lastIndexOf(".")){
            	name=name.substring(name.lastIndexOf(".", name.lastIndexOf(".")-1)+1);
			}else{
				name=name.substring(8);
			}
            //截掉前缀 Combobox_PeXxxxx.
            if(name.toLowerCase().startsWith("combobox_")){
            	name = name.substring(name.indexOf(".") + 1);
            	 sql.append(" and " + name + " = '"+value+"'");
            }else{
            	if(name.endsWith("Date")){//紧限于 yyyy-MM-dd格式的日期
            		sql.append(" and " + name + "- to_date('"+ value +"','yyyy-MM-dd')<1");
            		sql.append(" and " + name + "- to_date('"+ value +"','yyyy-MM-dd')>0");
            	}else{
            		sql.append(" and " + name + " like '%"+value+"%'");
            	}
            }
	       
	   }while(true);
	}
	/**
	 * 为支持2009-01-13之前所写的sql，特加上如下处理
	 */
	private void arrangeSQL(StringBuffer sql,Map params){
		Iterator iterator = params.entrySet().iterator();
		do {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            String name = entry.getKey().toString();
            String value = ((String[])entry.getValue())[0].toString();
            if(!name.startsWith("search__"))
            	continue;
            if(name.indexOf(".") > 1 && name.indexOf(".") != name.lastIndexOf(".")){
            	name=name.substring(name.lastIndexOf(".", name.lastIndexOf(".")-1)+1);
			}else{
				name=name.substring(8);
			}
            if(name.toLowerCase().startsWith("combobox_")){
            	sql.insert(0, "select * from (");
        		sql.append(") t where 1 = 1 ");
        		return;
            }
        }while(true);
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getInteractionMsg() {
		return interactionMsg;
	}

	public void setInteractionMsg(String interactionMsg) {
		this.interactionMsg = interactionMsg;
	}

	public String getInteractionTogo() {
		return interactionTogo;
	}

	public void setInteractionTogo(String interactionTogo) {
		this.interactionTogo = interactionTogo;
	}
	
	private static Configuration hibernateConf;
	private String excelInfo = "";
	
	/**
	 * 插入数据库操作日志
	 * @param msg
	 */
	public void insertLog(String msg){
		UserSession us=(UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		
		int logLength = Const.LOG_LENGTH;
		String userId = "";
		String userType ="";
		if(us != null){
			 userId = us.getLoginId();
//			 userType = us.getUserLoginType();
			 userType = us.getRoleId();
		}
		String userIp = ServletActionContext.getRequest().getRemoteAddr();
		
		HttpServletRequest req = ServletActionContext.getRequest();
		String uri = req.getRequestURI();
		String action = uri.substring(uri.lastIndexOf("/") + 1, uri.indexOf("_"));
		String method = uri.substring(uri.indexOf("_") + 1, uri.indexOf(".")) + "()";
		String behavior = action + "_" + method;
		
		for(int i = 0; i < (msg.length()-1)/logLength + 1; i ++) {
			String message = "";
			if(msg.length() < i*logLength + logLength) {
				message = msg.substring(i*logLength);
			} else {
				message = msg.substring(i*logLength, i*logLength + logLength);
			}
			try {
//				String sql = "select t.name from enum_const t where t.namespace = 'FlagRoleType' and t.code = '" + userType + "'";
				String sql = "select t.name from pe_pri_role t where t.id = '" + userType + "'";
				List list = this.getGeneralService().getBySQL(sql);
				if(list != null && list.size() > 0) {
					userType = (String)list.get(0);
				}
				WhatyuserLog4j log = new WhatyuserLog4j();
				log.setUserid(userId);
				log.setOperateTime(new Date());
				log.setBehavior(behavior);
				log.setNotes(message);
				log.setLogtype(userType);
				log.setIp(userIp);
				
				this.getGeneralService().saveLog(log);
			} catch (EntityException e) {
				e.printStackTrace();
			}	
		}
	}
	
	/**
	 * 不同的操作执行日志记录
	 * 
	 * @param operate
	 */
	public void operateLog(String operation) {
		UserSession us=(UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String loginId = "";
		String userName = "";
		if(us != null){
			loginId = us.getLoginId();
		}
		HttpServletRequest req = ServletActionContext.getRequest();
		String uri = req.getRequestURI();
		String action = uri.substring(uri.lastIndexOf("/") + 1, uri.indexOf("_"));
		String method = uri.substring(uri.indexOf("_") + 1, uri.indexOf("."));
		String sql = "select p.name from pe_priority p where p.action = '" + action + "' and (p.method = '" + method + "' or p.method = '*')";
		String s = "select name from pe_manager where login_id = '" + loginId + "'";
		try {
			List list = this.getGeneralService().getBySQL(sql);
			List l = this.getGeneralService().getBySQL(s);
			if(l != null && l.size() > 0) {
				userName = (String)l.get(0);
			}
			if(list == null || list.size() == 0) {
				this.insertLog("用户 " + userName + ": 执行<未知>操作: " + operation);
			} else {
				String sr = (String)list.get(0);
				if(sr.indexOf("_") >= 0) {
					sr = sr.substring(0, sr.indexOf("_"));
				}
				this.insertLog("用户 " + userName + ": 执行<<" + sr + ">>操作: " + operation);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取添加记录的信息
	 * 
	 * @return
	 */
	protected String getDataInfo() {
		Method[] methods = this.bean.getClass().getMethods();
		String mName = "";
		try {
			for(int i = 0; i < methods.length; i++) {
				mName = methods[i].getName();
				if(mName != null && (mName.equals("getName") || mName.equals("getTitle"))) {
					return "\"" + (String)methods[i].invoke(this.bean, null) + "\"";
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取更新信息
	 * 
	 * @param beforeBean
	 * @param afterBean
	 * @return
	 */
	protected String getUpdateInfo(T beforeBean, T afterBean) {
		Method[] methods = beforeBean.getClass().getMethods();
		String mName = "";
		Class rType = null;
		String dataInfo = "";
		try {
			for(int i = 0; i < methods.length; i++) {
				mName = methods[i].getName();
				if(mName != null && (mName.equals("getName") || mName.equals("getTitle"))) {
					dataInfo += "\"" + (String)methods[i].invoke(beforeBean, null) + "\" ";
					break;
				}
			}
		for(int i = 0; i < methods.length; i++) {
			mName = methods[i].getName();
			rType = methods[i].getReturnType();
				if( mName!= null && mName.startsWith("get") && !rType.equals(Set.class)) {
					if(rType != null && rType.equals(String.class)) {
						String before = (String)methods[i].invoke(beforeBean, null);
						String after = (String)methods[i].invoke(afterBean, null);
						if(after != null && !after.equals(before)){
							dataInfo += ", 修改\"" + before + "\"为\"" + after + "\"";
						}
					}else if(rType != null && rType.equals(Long.class) || rType.equals(long.class)) {
						Long before = ((Long)methods[i].invoke(beforeBean, null));
						Long after = ((Long)methods[i].invoke(afterBean, null));
						if(after != null && before != null && after.longValue() != before.longValue()){
							dataInfo += ", 修改\"" + before.longValue() + "\"为\"" + after.longValue() + "\"";
						} else if(after != null && before == null) {
							dataInfo += ", 修改\"null\"为\"" + after.longValue() + "\"";
						}
					}else if(rType != null && rType.equals(Date.class)) {
						Calendar before = Calendar.getInstance();
						Calendar after = Calendar.getInstance();
						Date beforeTime = (Date)methods[i].invoke(beforeBean, null);
						Date afterTime = (Date)methods[i].invoke(afterBean, null);
						if(beforeTime != null && afterTime != null) {
							before.setTime(beforeTime);
							after.setTime(afterTime);
							if(before.compareTo(after) != 0) {
								dataInfo += ", 修改\"" + before.get(Calendar.YEAR) + "-" + (before.get(Calendar.MONTH) + 1) + "-" + before.get(Calendar.DATE) + "\"为\"" + after.get(Calendar.YEAR) + "-" + (after.get(Calendar.MONTH) + 1) + "-" + after.get(Calendar.DATE) + "\"";
							}
						} else if(afterTime != null) {
							after.setTime(afterTime);
							dataInfo += ", 修改\"null\"为\"" + after.get(Calendar.YEAR) + "-" + (after.get(Calendar.MONTH) + 1) + "-" + after.get(Calendar.DATE) + "\"";
						}
					}else if(rType != null && rType.equals(byte.class) || rType.equals(Byte.class)) {
						Byte before = (Byte)methods[i].invoke(beforeBean, null);
						Byte after = (Byte)methods[i].invoke(afterBean, null);
						if(after != null && before != null && before.byteValue() != after.byteValue()){
							dataInfo += ", 修改\"" + before + "\"为\"" + after + "\"";
						}else if(after != null && before == null) {
							dataInfo += ", 修改\"null\"为\"" + after.byteValue() + "\"";
						}
					}else if(rType != null && rType.equals(short.class) || rType.equals(Short.class)) {
						Short before = (Short)methods[i].invoke(beforeBean, null);
						Short after = (Short)methods[i].invoke(afterBean, null);
						if(after != null && before != null && before.shortValue() != after.shortValue()){
							dataInfo += ", 修改\"" + before + "\"为\"" + after + "\"";
						}else if(after != null && before == null) {
							dataInfo += ", 修改\"null\"为\"" + after.shortValue() + "\"";
						}
					}else if(rType != null && rType.equals(int.class) || rType.equals(Integer.class)) {
						Integer before = (Integer)methods[i].invoke(beforeBean, null);
						Integer after = (Integer)methods[i].invoke(afterBean, null);
						if(after != null && before != null && before.intValue() != after.intValue()){
							dataInfo += ", 修改\"" + before + "\"为\"" + after + "\"";
						}else if(after != null && before == null) {
							dataInfo += ", 修改\"null\"为\"" + after.intValue() + "\"";
						}
					}else if(rType != null && rType.equals(double.class) || rType.equals(Double.class)) {
						Double before = (Double)methods[i].invoke(beforeBean, null);
						Double after = (Double)methods[i].invoke(afterBean, null);
						if(after != null && before != null && before.doubleValue() != after.doubleValue()){
							dataInfo += ", 修改\"" + before + "\"为\"" + after + "\"";
						}else if(after != null && before == null) {
							dataInfo += ", 修改\"null\"为\"" + after.doubleValue() + "\"";
						}
					}else if(rType != null && rType.equals(boolean.class) || rType.equals(Boolean.class)) {
						Boolean before = (Boolean)methods[i].invoke(beforeBean, null);
						Boolean after = (Boolean)methods[i].invoke(afterBean, null);
						if(after != null && before != null && before.booleanValue() != after.booleanValue()){
							dataInfo += ", 修改\"" + before + "\"为\"" + after + "\"";
						}else if(after != null && before == null) {
							dataInfo += ", 修改\"null\"为\"" + after.booleanValue() + "\"";
						}
					}else if(rType != null && rType.equals(float.class) || rType.equals(Float.class)) {
						Float before = (Float)methods[i].invoke(beforeBean, null);
						Float after = (Float)methods[i].invoke(afterBean, null);
						if(after != null && before != null && before.floatValue() != after.floatValue()){
							dataInfo += ", 修改\"" + before + "\"为\"" + after + "\"";
						}else if(after != null && before == null) {
							dataInfo += ", 修改\"null\"为\"" + after.floatValue() + "\"";
						}
					}else if(rType != null && rType.equals(char.class) || rType.equals(Character.class)) {
						Character before = (Character)methods[i].invoke(beforeBean, null);
						Character after = (Character)methods[i].invoke(afterBean, null);
						if(after != null && before != null && before.charValue() != after.charValue()){
							dataInfo += ", 修改\"" + before + "\"为\"" + after + "\"";
						}else if(after != null && before == null) {
							dataInfo += ", 修改\"null\"为\"" + after.charValue() + "\"";
						}
					}else{
						Object b = methods[i].invoke(beforeBean, null);
						Object a = methods[i].invoke(afterBean, null);
						if(b != null && a != null) {
							Method[] bMethods = b.getClass().getMethods();
							Method[] aMethods = a.getClass().getMethods();
							String inName = "";
							String aName = "";
							for(int j = 0; j < bMethods.length; j++) {
								inName = bMethods[j].getName();
								if(inName != null && (inName.equals("getName") || inName.equals("getTitle"))) {
									String before = (String)bMethods[j].invoke(b, null);
									String after = "";
									for(int t = 0; t < aMethods.length; t++) {
										aName = aMethods[t].getName();
										if(inName.equals(aName)) {
											after = (String)aMethods[t].invoke(a, null);
											break;
										}
									}
									if(after != null && !after.equals(before)){
										dataInfo += ", 修改\"" + before + "\"为\"" + after + "\"";
									}
								}
								break;
							}
						}
					} 
				}
			}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			if(dataInfo != null && dataInfo.indexOf(", 修改\"null\"为\"\"") != -1) {
				dataInfo = dataInfo.replaceAll(", 修改\"null\"为\"\"", "");
			}
		return dataInfo;
	}
	
	/**
	 * 获取excel导入消息
	 * 
	 * @return
	 */
	protected String getExcelInfo() {
		if(excelInfo != null && excelInfo.length() > 2) {
			return excelInfo.substring(2);
		} 
		return excelInfo;
	}
	
	/**
	 * 设置Excel导入消息
	 * 
	 * @param list
	 */
	protected void setExcelInfo(List list) {
		
		try {
		for(int k = 0; k < list.size(); k++) {
			Method[] methods = list.get(k).getClass().getMethods();
			String mName = "";
			for(int i = 0; i < methods.length; i++) {
				mName = methods[i].getName();
				if(mName != null && (mName.equals("getName") || mName.equals("getTitle"))) {
					excelInfo += ", \"" + (String)methods[i].invoke(list.get(k), null) + "\"";
				}
			}
		}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取删除记录的信息
	 * 
	 * @param tableName
	 * @param ids
	 * @return
	 */
	protected String getDataInfo(List idList) {
		String tableName = "";
		Class clazz = this.getEntityClass();
		tableName = getPersistentClass(clazz).getTable().getName();
		
		String ids = "";
		for(int i = 0; i < idList.size(); i++) {
			if(i == 0) {
				ids = "'" + (String)idList.get(i) + "'";
			} else {
				ids += ", '" + (String)idList.get(i) + "'";
			}
		}
		String dataInfo = "" ;
		String sql = "select name from " + tableName + " where id in (" + ids + ")";
		String sql0 = "select title from " + tableName + " where id in (" + ids + ")";
		List list = null;
		try {
			list = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			try {
				list = this.getGeneralService().getBySQL(sql0);
			} catch (EntityException e1) {
//				e1.printStackTrace();
			}
//			e.printStackTrace();
		}
		if(list != null && list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				if(i == 0) {
					dataInfo += "\"" + (String)list.get(i) + "\"";
				} else {
					dataInfo += ", \"" + (String)list.get(i) + "\"";
				}
			}
		}
		return dataInfo;
	}
	
	private PersistentClass getPersistentClass(Class clazz) {
		synchronized (MyBaseAction.class) {
			PersistentClass pc = getHibernateConf().getClassMapping(
					clazz.getName());
			if (pc == null) {
				hibernateConf = getHibernateConf().addClass(clazz);
				pc = getHibernateConf().getClassMapping(clazz.getName());
			}
			return pc;
		}
	}
	
	public Configuration getHibernateConf() {
		if (hibernateConf == null) {
			return new Configuration();
		}
		return hibernateConf;
	}

	public static String getDateformat() {
		return dateformat;
	}

	public static void setDateformat(String dateformat) {
		MyBaseAction.dateformat = dateformat;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
