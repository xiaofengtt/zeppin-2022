/**
 * 
 */
package cn.zeppin.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.FunCategory;
import cn.zeppin.entity.OrgaCateMap;
import cn.zeppin.entity.OrganizationLevel;
import cn.zeppin.entity.TrainingAdmin;
import cn.zeppin.service.IFunCategoryService;
import cn.zeppin.service.IOrgaCateMapService;
import cn.zeppin.service.IOrganizationLevelService;
import cn.zeppin.service.ITrainingAdminService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.DictionyMap.ROLEENUM;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 左侧导航列表
 * 
 * @author sj
 * @category 根据数据库中的对应表进行查找关联
 */
public class LeftAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(LeftAction.class);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private IOrgaCateMapService iOrgaCateMapService;
	private IFunCategoryService iFunCategoryService;
	private IOrganizationLevelService iOrganizationLevel; // organizationlevel
	private ITrainingAdminService iTrainingAdminService;
	private LinkedHashMap<FunCategory, List<FunCategory>> leftHash;
	
	private LinkedHashMap<OrgaCateMap, List<OrgaCateMap>> leftHashNew;

	private String leftName;
	
	private String role;
	private String level;
	private String manager;
	
	private List<FunCategory> lstfc;

	public LeftAction() {

	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	/**
	 * @return
	 * @category 初始化左侧导航菜单
	 */
	public String init() {

		initServlert();

		UserSession us = (UserSession) session.getAttribute("usersession");

		short roleid = us.getRole();

//		leftHash = new LinkedHashMap<FunCategory, List<FunCategory>>();
		leftHashNew = new LinkedHashMap<OrgaCateMap, List<OrgaCateMap>>();

		ROLEENUM ROLE = ROLEENUM.valueof(roleid);

		if (ROLE == ROLEENUM.SUPERADMIN || ROLE == ROLEENUM.ADMIN
				|| ROLE == ROLEENUM.TRAINING || ROLE == ROLEENUM.PROJECTEXPERT) {

			List<OrgaCateMap> li = this.iOrgaCateMapService.findByRoleId(
					roleid, us.getOrganizationLevel());

			for(OrgaCateMap ocm : li){

				FunCategory fc = ocm.getFunCategory(); // 功能类型
				FunCategory pfc = fc.getFunCategory(); // 父功能类型
				
				if(ROLE == ROLEENUM.TRAINING && (fc.getId() == 33 || fc.getId() == 36)){
					TrainingAdmin ta = this.iTrainingAdminService.get(us.getId());
					if(ta.getTrainingCollege().getProjectLevel().getLevel()>2){//非国培区培项目承训机构 过滤功能
						continue;
					}
				}
				if (pfc != null) {
					
					/*
					 * 判断一级菜单是否存在
					 * 不存在则 新增一条记录
					 */
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("status", 1);
					if(us.getOrganizationLevel() > 0){
						params.put("level", us.getOrganizationLevel());
					}
					params.put("role", roleid);
					params.put("funCategory", pfc.getId());
					List<OrgaCateMap> record = this.iOrgaCateMapService.getListByParams(params, null, -1, -1);
					OrgaCateMap ocmp = new OrgaCateMap();
					if(record != null && record.size() > 0){
						ocmp = record.get(0);
					}else{
						ocmp.setFunCategory(pfc);
						ocmp.setName(pfc.getName());
						ocmp.setOrganizationLevel(ocm.getOrganizationLevel());
						ocmp.setRoleid(ocm.getRoleid());
						ocmp.setStatus((short)1);
						ocmp = this.iOrgaCateMapService.add(ocmp);
					}
					// 如果父不为空，则以父为key
					if (leftHashNew.containsKey(ocmp)) {
						List<OrgaCateMap> t = leftHashNew.get(ocmp);
						t.add(ocm);
					} else {
						List<OrgaCateMap> t = new ArrayList<OrgaCateMap>();
						t.add(ocm);
						leftHashNew.put(ocmp, t);
					}
				} else {
					// 如果父为空为一级菜单
					
					if (!leftHashNew.containsKey(ocm)) {
						List<OrgaCateMap> t = new ArrayList<OrgaCateMap>();
						leftHashNew.put(ocm, t);

					}
				}
			}
			
		}
		return "init";
	}
	
	/**
	 * 初始化菜单配置页面
	 * @return
	 */
	public String funCategoryInit(){
		initServlert();
		return "funCategoryInit";
		
	}
	
	/**
	 * 根据不用登录角色获取角色权限列表
	 * 角色：role:1-项目管理员 2-承训单位 3-评审专家 5-超级管理员
	 * 		level 1-自治区教育厅 2-地市 3-县区 4-学校
	 * 共7中角色，分别获取各自的功能列表
	 * 
	 */
	public void getList(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		UserSession us = (UserSession)session.getAttribute("usersession");
		LinkedHashMap<OrgaCateMap, List<OrgaCateMap>> leftHashMap = new LinkedHashMap<OrgaCateMap, List<OrgaCateMap>>();
		if(DictionyMap.ROLEENUM.SUPERADMIN != ROLEENUM.valueof(us.getRole())){
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"没有操作权限\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		
		String role = request.getParameter("role") == null ? "0" : request.getParameter("role");
		if("".equals(role)){
			role = "0";
		}
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		if("1".equals(role)){//项目管理功能员列表 1- 2- 3- 4-（各级管理员权限分开设置）
			
			String level = request.getParameter("level") == null ? "0" : request.getParameter("level");;
			if("".equals(level)){
				level = "0";
			}
			Map<String, Object> paramss = new HashMap<String, Object>();
//			paramss.put("status", 1);
			paramss.put("level", level);
			paramss.put("role", role);
			List<OrgaCateMap> li = this.iOrgaCateMapService.getListByParams(paramss, null, -1, -1);
			if(li != null && li.size() > 0){
				for(OrgaCateMap ocm : li){
//					OrgaCateMap ocm = li.get(i);

					FunCategory fc = ocm.getFunCategory(); // 功能类型
					FunCategory pfc = fc.getFunCategory(); // 父功能类型

					if (pfc != null) {
						
						/*
						 * 判断一级菜单是否存在
						 * 不存在则 新增一条记录
						 */
						Map<String, Object> params = new HashMap<String, Object>();
//						params.put("status", 1);
						params.put("level", level);
						params.put("role", role);
						params.put("funCategory", pfc.getId());
						List<OrgaCateMap> record = this.iOrgaCateMapService.getListByParams(params, null, -1, -1);
						OrgaCateMap ocmp = new OrgaCateMap();
						if(record != null && record.size() > 0){
							ocmp = record.get(0);
						}else{
							ocmp.setFunCategory(pfc);
							ocmp.setName(pfc.getName());
							ocmp.setOrganizationLevel(ocm.getOrganizationLevel());
							ocmp.setRoleid(ocm.getRoleid());
							ocmp.setStatus((short)1);
							ocmp = this.iOrgaCateMapService.add(ocmp);
						}
						// 如果父不为空，则以父为key
						if (leftHashMap.containsKey(ocmp)) {
							List<OrgaCateMap> t = leftHashMap.get(ocmp);
							t.add(ocm);
						} else {
							List<OrgaCateMap> t = new ArrayList<OrgaCateMap>();
							t.add(ocm);
							leftHashMap.put(ocmp, t);
						}
					} else {
						// 如果父为空为一级菜单
						
						if (!leftHashMap.containsKey(ocm)) {
							List<OrgaCateMap> t = new ArrayList<OrgaCateMap>();
							leftHashMap.put(ocm, t);

						}
					}
				}
				
				sb.append("\"TotalRecordCount\":" + li.size());
				sb.append(",");
				sb.append("\"Records\":[");
				for (Iterator<OrgaCateMap> it =  leftHashMap.keySet().iterator();it.hasNext();){
					Object key = it.next();
					OrgaCateMap ocm = (OrgaCateMap)key;
					List<OrgaCateMap> lst = leftHashMap.get(key);
					
					if(lst != null && lst.size() > 0){
						sb.append("{");
						sb.append("\"id\":"+ocm.getId());
						sb.append(",");
						sb.append("\"name\":\""+ocm.getName()+"\"");
						String status = ocm.getStatus() + "";
//						if(ocm.getStatus() == 1){
//							status = "正常";
//						}else{
//							status = "停用";
//						}
						sb.append(",");
						sb.append("\"status\":\""+status+"\"");
						sb.append(",");
						sb.append("\"childrens\":[");
						boolean flag = false;
						for(OrgaCateMap ocmc : lst){
							sb.append("{");
							sb.append("\"id\":"+ocmc.getId());
							sb.append(",");
							sb.append("\"name\":\""+ocmc.getName()+"\"");
							String status1 = ocmc.getStatus() + "";
							sb.append(",");
							sb.append("\"status\":\""+status1+"\"");
							sb.append("},");
							if(ocmc.getStatus() != 0){
								flag = true;
							}
						}
						if(!flag){
							ocm.setStatus((short)0);
							this.iOrgaCateMapService.update(ocm);
						}
						sb.delete(sb.length() - 1, sb.length());
						sb.append("]");
					}else{
						ocm.setStatus((short)-1);
						this.iOrgaCateMapService.update(ocm);
					}
					sb.append("},");
				}
				
				sb.delete(sb.length() - 1, sb.length());
				sb.append("]");
				
			}else{
				sb.append("\"TotalRecordCount\":0");
				sb.append(",");
				sb.append("\"Records\":[]");
			}
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			
		}else{
			Map<String, Object> paramss = new HashMap<String, Object>();
//			paramss.put("status", 1);
			paramss.put("role", role);
			List<OrgaCateMap> li = this.iOrgaCateMapService.getListByParams(paramss, null, -1, -1);
			if(li != null && li.size() > 0){
				for(OrgaCateMap ocm : li){
//					OrgaCateMap ocm = li.get(i);

					FunCategory fc = ocm.getFunCategory(); // 功能类型
					FunCategory pfc = fc.getFunCategory(); // 父功能类型

					if (pfc != null) {
						
						/*
						 * 判断一级菜单是否存在
						 * 不存在则 新增一条记录
						 */
						Map<String, Object> params = new HashMap<String, Object>();
//						params.put("status", 1);
						params.put("role", role);
						params.put("funCategory", pfc.getId());
						List<OrgaCateMap> record = this.iOrgaCateMapService.getListByParams(params, null, -1, -1);
						OrgaCateMap ocmp = new OrgaCateMap();
						if(record != null && record.size() > 0){
							ocmp = record.get(0);
//							if(ocmp.getStatus() != 1){
//								ocmp.setStatus((short)1);
//								this.iOrgaCateMapService.update(ocmp);
//							}
							
						}else{
							ocmp.setFunCategory(pfc);
							ocmp.setName(pfc.getName());
							ocmp.setOrganizationLevel(ocm.getOrganizationLevel());
							ocmp.setRoleid(ocm.getRoleid());
							ocmp.setStatus((short)1);
							ocmp = this.iOrgaCateMapService.add(ocmp);
						}
						// 如果父不为空，则以父为key
						if (leftHashMap.containsKey(ocmp)) {
							List<OrgaCateMap> t = leftHashMap.get(ocmp);
							t.add(ocm);
						} else {
							List<OrgaCateMap> t = new ArrayList<OrgaCateMap>();
							t.add(ocm);
							leftHashMap.put(ocmp, t);
						}
					} else {
						// 如果父为空为一级菜单
						
						if (!leftHashMap.containsKey(ocm)) {
							List<OrgaCateMap> t = new ArrayList<OrgaCateMap>();
							leftHashMap.put(ocm, t);

						}
					}
				}
				
				sb.append("\"TotalRecordCount\":" + li.size());
				sb.append(",");
				sb.append("\"Records\":[");
				for (Iterator<OrgaCateMap> it =  leftHashMap.keySet().iterator();it.hasNext();){
					Object key = it.next();
					OrgaCateMap ocm = (OrgaCateMap)key;
					List<OrgaCateMap> lst = leftHashMap.get(key);
					
					if(lst != null && lst.size() > 0){
						sb.append("{");
						sb.append("\"id\":"+ocm.getId());
						sb.append(",");
						sb.append("\"name\":\""+ocm.getName()+"\"");
						String status = ocm.getStatus() + "";
//						if(ocm.getStatus() == 1){
//							status = "正常";
//						}else{
//							status = "停用";
//						}
						sb.append(",");
						sb.append("\"status\":\""+status+"\"");
						sb.append(",");
						sb.append("\"childrens\":[");
						boolean flag = false;
						for(OrgaCateMap ocmc : lst){
							sb.append("{");
							sb.append("\"id\":"+ocmc.getId());
							sb.append(",");
							sb.append("\"name\":\""+ocmc.getName()+"\"");
							String status1 = ocmc.getStatus() + "";
							sb.append(",");
							sb.append("\"status\":\""+status1+"\"");
							sb.append("},");
							if(ocmc.getStatus() != 0){
								flag = true;
							}
						}
						if(!flag){
							ocm.setStatus((short)0);
							this.iOrgaCateMapService.update(ocm);
						}
						sb.delete(sb.length() - 1, sb.length());
						sb.append("]");
					}else{
						ocm.setStatus((short)-1);
						this.iOrgaCateMapService.update(ocm);
					}
					sb.append("},");
				}
				
				sb.delete(sb.length() - 1, sb.length());
				sb.append("]");
				
			}else{
				sb.append("\"TotalRecordCount\":0");
				sb.append(",");
				sb.append("\"Records\":[]");
			}
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
		
	}
	
//	public String getListStr(LinkedHashMap<FunCategory, List<FunCategory>> leftHashMap,StringBuilder sb){
//		for (Iterator<FunCategory> it =  leftHashMap.keySet().iterator();it.hasNext();){
//			
//		}
//		return sb.toString();
//	}
	
	
	/**
	 * 初始化设置权限页面
	 * 获取所有一级功能列表
	 * @return
	 */
	public String initAdd(){
		initServlert();
		String role = request.getParameter("role") == null ? "" : request.getParameter("role");
		String level = request.getParameter("level") == null ? "0" : request.getParameter("level");
		if("".equals(level)){
			level = "0";
		}
		if("1".equals(role)){
			this.role = "1";
			this.level = level;
			
			switch (level) {
			case "1":
				this.manager = "自治区教育厅管理员";
				break;
			case "2":
				this.manager = "地市州教育局管理员";
				break;
			case "3":
				this.manager = "县市区教育局管理员";
				break;
			case "4":
				this.manager = "普通派出学校管理员";
				break;

			default:
				break;
			}
			
			
		}else{
			this.role = role;
			this.level = "0";
			
			switch (role) {
			case "2":
				this.manager = "承训单位管理员";
				break;
			case "4":
				this.manager = "评审专家";
				break;
			case "5":
				this.manager = "超级管理员";
				break;
			default:
				break;
			}
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("level", 1);
		List<FunCategory> list =  this.iFunCategoryService.getListByParams(params, null, -1, -1);
		this.lstfc = new ArrayList<FunCategory>();
		FunCategory fc = new FunCategory();
		fc.setId(0);
		fc.setName("请选择");
		this.lstfc.add(fc);
		this.lstfc.addAll(list);
		
		return "initAdd";
	}
	
	/**
	 * 通过角色获取对应一级菜单下，未设置的二级菜单
	 * @serialData 2016-09-19
	 */
	public void getOtherChildFuncategory(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		
		String funcategoryId = request.getParameter("funcategory")  == null ? "0" : request.getParameter("funcategory");
		if("".equals(funcategoryId)){
			funcategoryId = "0";
		}
		
		if("0".equals(funcategoryId)){
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"请选择一级功能菜单\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		String role = request.getParameter("role") == null ? "" : request.getParameter("role");
		String level = request.getParameter("level") == null ? "0" : request.getParameter("level");
		if("".equals(level)){
			level = "0";
		}
		
		Map<String, Object> paramss = new HashMap<String, Object>();
//		paramss.put("status", 1);
		paramss.put("level", level);
		paramss.put("role", role);
		List<OrgaCateMap> li = this.iOrgaCateMapService.getListByParams(paramss, null, -1, -1);
		paramss.clear();
		paramss.put("funCategory", funcategoryId);
		List<FunCategory> lst = this.iFunCategoryService.getListByParams(paramss, null, -1, -1);
		
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		sb.append("\"Records\":[");
		
		if(lst != null && lst.size() > 0){
			List<FunCategory> list = new ArrayList<FunCategory>();
			if(li != null && li.size() > 0){
				for(FunCategory fc : lst){
					boolean flag = false;
					for(OrgaCateMap ocm : li){
						if(ocm.getFunCategory().getId() == fc.getId()){
							flag = true;
						}
					}
					if(!flag){
						list.add(fc);
					}
				}
				
			}else{
				list.addAll(lst);
			}
			if(list.size() > 0){
				for(FunCategory fc : list){
					sb.append("{");
					sb.append("\"id\":"+fc.getId());
					sb.append(",");
					sb.append("\"name\":\""+fc.getName()+"\"");
					sb.append("},");
				}
				sb.delete(sb.length() - 1, sb.length());
			}
		}
		
		sb.append("]}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
		
	}
	
	/**
	 * 新增或编辑角色权限列表
	 */
	public void setup(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		UserSession us = (UserSession)session.getAttribute("usersession");
		if(DictionyMap.ROLEENUM.SUPERADMIN != ROLEENUM.valueof(us.getRole())){
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"没有操作权限\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		String method = request.getParameter("method");
		if (method == null || "".equals(method)) {
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"不存在的操作项\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;

		} else {
			if (method.equals("edit")) {
				String id = request.getParameter("id") == null ? "0" : request.getParameter("id");
				if("".equals(id)){
					id = "0";
				}
				if("0".equals(id)){
					sb.append("{");
					sb.append("\"Result\":\"ERROR\"");
					sb.append(",");
					sb.append("\"Message\":\"无效的操作记录\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				
				String name = request.getParameter("name") == null ? "" : request.getParameter("name");
				if("".equals(name)){
					sb.append("{");
					sb.append("\"Result\":\"ERROR\"");
					sb.append(",");
					sb.append("\"Message\":\"请填写菜单名称\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				
				String status = request.getParameter("status") == null ? "1" : request.getParameter("status");
				if("".equals(status)){
					sb.append("{");
					sb.append("\"Result\":\"ERROR\"");
					sb.append(",");
					sb.append("\"Message\":\"菜单状态异常\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				
				OrgaCateMap ocm = this.iOrgaCateMapService.get(Integer.parseInt(id));
				if(ocm != null){
					ocm.setName(name);
					ocm.setStatus(Short.parseShort(status));
					
					try {
						if(ocm.getFunCategory().getFunCategory() != null){
							this.iOrgaCateMapService.update(ocm);
						}else{
							this.iOrgaCateMapService.updateMore(ocm);
						}
					} catch (Exception e) {
						e.printStackTrace();
						sb.append("{");
						sb.append("\"Result\":\"ERROR\"");
						sb.append(",");
						sb.append("\"Message\":\"操作异常\"");
						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json", response);
						return;
						// TODO: handle exception
					}
					
					
					sb.append("{");
					sb.append("\"Result\":\"OK\"");
					sb.append(",");
					sb.append("\"Message\":\"操作成功\"");
					sb.append("}");
				}else{
					sb.append("{");
					sb.append("\"Result\":\"ERROR\"");
					sb.append(",");
					sb.append("\"Message\":\"不存在的菜单项\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			}else if(method.equals("add")){
				
				Map<String, Object> params = new HashMap<String, Object>();
				
				String role = request.getParameter("role") == null ? "" : request.getParameter("role");
				params.put("role", role);
				
				String level = request.getParameter("level") == null ? "0" : request.getParameter("level");
				if("".equals(level)){
					level = "0";
				}
				OrganizationLevel ol = null;
				if(!"0".equals(level)){
					ol = this.iOrganizationLevel.get(Short.parseShort(level));
				}
				params.put("level", ol);
				
				String children = request.getParameter("children") == null ? "" : request.getParameter("children");
				List<FunCategory> lst = new ArrayList<FunCategory>();
				if(!"".equals(children)){
					String[] childrenArr = children.split(",");
					for(String str: childrenArr){
						FunCategory fc = this.iFunCategoryService.get(Integer.parseInt(str));
						if(fc != null){
							lst.add(fc);
						}
					}
				}
				if(lst.size() > 0){
					params.put("children", lst);
				}
			
				try {
					this.iOrgaCateMapService.addMore(params);
					sb.append("{");
					sb.append("\"Result\":\"OK\"");
					sb.append(",");
					sb.append("\"Message\":\"操作成功\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					sb.append("{");
					sb.append("\"Result\":\"ERROR\"");
					sb.append(",");
					sb.append("\"Message\":\"操作过程异常\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				
			}else{
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\"不存在的操作项\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
		}
		
	}
	
	/**
	 * 删除
	 */
	public void delete(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		UserSession us = (UserSession)session.getAttribute("usersession");
		if(DictionyMap.ROLEENUM.SUPERADMIN != ROLEENUM.valueof(us.getRole())){
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"没有操作权限\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		String id = request.getParameter("id") == null ? "0" : request.getParameter("id");
		if("".equals(id)){
			id = "0";
		}
		if("0".equals(id)){
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"无效的操作记录\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		OrgaCateMap ocm = this.iOrgaCateMapService.get(Integer.parseInt(id));
		if(ocm != null){
			ocm.setStatus((short)-1);
			try {
				if(ocm.getFunCategory().getFunCategory() != null){
					this.iOrgaCateMapService.update(ocm);
				}else{
					this.iOrgaCateMapService.updateMore(ocm);
				}
			} catch (Exception e) {
				e.printStackTrace();
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\"操作异常\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
				// TODO: handle exception
			}
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"Message\":\"操作成功\"");
			sb.append("}");
		}else{
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"不存在的菜单项\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}

	public IOrgaCateMapService getiOrgaCateMapService() {
		return iOrgaCateMapService;
	}

	public void setiOrgaCateMapService(IOrgaCateMapService iOrgaCateMapService) {
		this.iOrgaCateMapService = iOrgaCateMapService;
	}

	public LinkedHashMap<FunCategory, List<FunCategory>> getLeftHash() {
		return leftHash;
	}

	public void setLeftHash(
			LinkedHashMap<FunCategory, List<FunCategory>> leftHash) {
		this.leftHash = leftHash;
	}

	public String getLeftName() {
		return leftName;
	}

	public void setLeftName(String leftName) {
		this.leftName = leftName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	
	public LinkedHashMap<OrgaCateMap, List<OrgaCateMap>> getLeftHashNew() {
		return leftHashNew;
	}
	

	public void setLeftHashNew(
			LinkedHashMap<OrgaCateMap, List<OrgaCateMap>> leftHashNew) {
		this.leftHashNew = leftHashNew;
	}

	
	public String getLevel() {
		return level;
	}
	

	public void setLevel(String level) {
		this.level = level;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public List<FunCategory> getLstfc() {
		return lstfc;
	}

	public void setLstfc(List<FunCategory> lstfc) {
		this.lstfc = lstfc;
	}

	public IFunCategoryService getiFunCategoryService() {
		return iFunCategoryService;
	}

	public void setiFunCategoryService(IFunCategoryService iFunCategoryService) {
		this.iFunCategoryService = iFunCategoryService;
	}

	public IOrganizationLevelService getiOrganizationLevel() {
		return iOrganizationLevel;
	}

	public void setiOrganizationLevel(IOrganizationLevelService iOrganizationLevel) {
		this.iOrganizationLevel = iOrganizationLevel;
	}

	
	public ITrainingAdminService getiTrainingAdminService() {
		return iTrainingAdminService;
	}
	

	public void setiTrainingAdminService(ITrainingAdminService iTrainingAdminService) {
		this.iTrainingAdminService = iTrainingAdminService;
	}
	
}
