package cn.zeppin.action.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.TrainingAdmin;
import cn.zeppin.entity.TrainingAdminAuthority;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IEthnicService;
import cn.zeppin.service.ITrainingAdminService;
import cn.zeppin.service.ITrainingCollegeService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.DictionyMap.ROLEENUM;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

public class TrainingAdminAction extends ActionSupport
{

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    static Logger logger = LogManager.getLogger(TrainingAdminAction.class);

    private HttpServletRequest request;
    private HttpSession session;
    private HttpServletResponse response;

    private ITrainingCollegeService iTrainingCollegeService;
    private IAreaService iArea; // 地区信息
    private ITrainingAdminService iTrainingAdmin;
    private IEthnicService iEthnicService;

    private int showAddButton;

    public TrainingAdminAction()
    {
    }

    public void initServlert()
    {
	request = ServletActionContext.getRequest();
	session = request.getSession();
	response = ServletActionContext.getResponse();
    }

    public String initPage()
    {
	initServlert();
	UserSession us = (UserSession) session.getAttribute("usersession");
	showAddButton = 0;
	if (us.getRole() == ROLEENUM.SUPERADMIN.getValue() || 
			us.getRole() == ROLEENUM.ADMIN.getValue() && us.getOrganizationLevel() == 1 ||   
			(us.getRole() == ROLEENUM.TRAINING.getValue() && us.getCreateuser() == 1))
	{
		showAddButton = 1;
	}
	
	return "init";
    }

    /**
     * @category 获取承训单位管理员信息列表
     */
    public void getTrainingAdminList()
    {
    	initServlert();
    	UserSession us = (UserSession) session.getAttribute("usersession");
    	try
    	{
    		//超级管理员、省厅管理员、承训单位总管理员可以对承训单位管理员进行管理
    		if (us.getRole() == ROLEENUM.SUPERADMIN.getValue() || 
    				us.getRole() == ROLEENUM.ADMIN.getValue() && us.getOrganizationLevel() == 1 ||   
    				(us.getRole() == ROLEENUM.TRAINING.getValue() && us.getCreateuser() == 1))
    		{
    		
    			ROLEENUM ROLE = ROLEENUM.valueof(us.getRole());
    			// 起始页
    			String ist = (String) request.getParameter("jtStartIndex");
    			if (ist == null || ist.equals("") || ist.equals("NaN"))
    			{
    				ist = "0";
    			}

    			// 显示的条数
    			String ien = (String) request.getParameter("jtPageSize");
    			if (ien == null || ien.equals(""))
    			{
    				ien = DictionyMap.maxPageSize + "";
    			}

    			int start = Integer.parseInt(ist);
    			int limit = DictionyMap.maxPageSize;
    			limit = Integer.parseInt(ien);

    			// 排序
    			String sort = request.getParameter("jtSorting");

    			String hql = " from TrainingAdmin t,TrainingCollege tc where 1=1 and t.trainingCollege=tc.id ";
    			if (ROLE == ROLEENUM.TRAINING)
    			{
    				hql += " and t.trainingCollege="
    						+ iTrainingAdmin.get(us.getId())
    						.getTrainingCollege().getId();
    			}
    			// 以后其他搜索参数
    			String q = request.getParameter("q");
    			q = q == null ? "" : q;
    			String stype = request.getParameter("stype");
    			stype = stype == null ? "" : stype;

    			if (q.length() > 0)
    			{
    				hql += " and (t." + stype + " like '%" + q
    						+ "%' or tc.name like '%" + q + "%' )";
    			}

    			// TODO 加上权限搜索

    			// 排序 参数
    			if (sort != null && !sort.equals(""))
    			{
    				String[] sortarray = sort.split(" ");
    				String sortname = sortarray[0];
    				String sorttype = sortarray[1];
    				hql += " order by t." + sortname + " " + sorttype;
    			}

    			StringBuilder sb = new StringBuilder();
    			sb.append("{");
    			sb.append("\"Result\":\"OK\"");
    			sb.append(",");

    			List<Object> licount = this.iTrainingAdmin
    					.findByHSQL("select count(*) " + hql);

    			if (licount != null && licount.size() > 0)
    			{
    				int records = Integer.parseInt(licount.get(0).toString());
    				sb.append("\"TotalRecordCount\":" + records);
    				sb.append(",");

    			}
    			else
    			{
    				sb.append("\"TotalRecordCount\":0");
    				sb.append(",");
    			}

    			List li = this.iTrainingAdmin.getListForPage(hql, start, limit);

    			sb.append("\"Records\":[");

    			for (int i = 0; i < li.size(); i++)
    			{
    				Object[] ot = (Object[]) li.get(i);
    				TrainingAdmin ta = (TrainingAdmin) ot[0];
    				String id = ta.getId().toString();
    				String politics = ta.getPolitics() == null ? "" : ta
    						.getPolitics().getName();
    				String area = ta.getArea() == null ? "" : ta.getArea()
    						.getName();
    				String trainingCollege = ta.getTrainingCollege().getName();
    				String ethnic = ta.getEthnic() == null ? "" : ta
    						.getEthnic().getName();
    				String name = ta.getName();
    				String idcard = ta.getIdcard();
    				String mobile = ta.getMobile();
    				String email = ta.getEmail();
    				Short sex = ta.getSex() == null ? 0 : ta.getSex();
    				String department = ta.getDepartment();
    				Boolean createuser = ta.getCreateuser();
    				String phone = ta.getPhone();
    				String fax = ta.getFax();
    				String jobDuty = ta.getJobDuty();
    				String address = ta.getAddress();
    				String postcode = ta.getPostcode();
    				Short status = ta.getStatus();
    				String remark = ta.getRemark();

    				StringBuilder sbstr = new StringBuilder();
    				sbstr.append("{");
    				sbstr.append("\"id\":" + id);
    				sbstr.append(",");
    				sbstr.append("\"name\":\"" + name + "\"");
    				sbstr.append(",");
    				sbstr.append("\"idcard\":\"" + idcard + "\"");
    				sbstr.append(",");
    				sbstr.append("\"mobile\":\"" + mobile + "\"");
    				sbstr.append(",");
    				sbstr.append("\"email\":\"" + email + "\"");
    				sbstr.append(",");
    				sbstr.append("\"sex\":" + sex);
    				sbstr.append(",");
    				sbstr.append("\"ethnic\":\"" + ethnic + "\"");
    				sbstr.append(",");
    				sbstr.append("\"trainingCollege\":\"" + trainingCollege
    						+ "\"");
    				sbstr.append(",");
    				sbstr.append("\"department\":\"" + department + "\"");
    				sbstr.append(",");
    				sbstr.append("\"politics\":\"" + politics + "\"");
    				sbstr.append(",");
    				sbstr.append("\"createuser\":" + createuser);
    				sbstr.append(",");
    				sbstr.append("\"restrictRight\":" + 0);
    				sbstr.append(",");
    				sbstr.append("\"phone\":\"" + phone + "\"");
    				sbstr.append(",");
    				sbstr.append("\"fax\":\"" + fax + "\"");
    				sbstr.append(",");
    				sbstr.append("\"jobDuty\":\"" + jobDuty + "\"");
    				sbstr.append(",");
    				sbstr.append("\"area\":\"" + area + "\"");
    				sbstr.append(",");
    				sbstr.append("\"address\":\"" + address + "\"");
    				sbstr.append(",");
    				sbstr.append("\"postcode\":\"" + postcode + "\"");
    				sbstr.append(",");
    				sbstr.append("\"status\":" + status);
    				sbstr.append(",");
    				sbstr.append("\"remark\":\"" + remark + "\"");
    				sbstr.append("},");
    				sb.append(sbstr.toString());
    			}

    			if (li.size() > 0)
    			{
    				sb.delete(sb.length() - 1, sb.length());
    			}
    			sb.append("]}");

    			Utlity.ResponseWrite(sb.toString(), "application/json",
    					response);
    		}
    		else
    		{
    			Utlity.sendResponse("ERROR", "无权查看承训单位管理员信息！", response);
    		}

    	}
    	catch (Exception ex)
    	{
    		logger.error(ex);
    		StringBuilder sb = new StringBuilder();
    		sb.append("{");
    		sb.append("\"Result\":\"ERROR\"");
    		sb.append("}");
    		Utlity.ResponseWrite(sb.toString(), "application/json", response);
    	}
    }

//    /**
//     * 初始化添加/编辑承训单位管理员权限页面
//     */
//    public void addAuthorityInit(){
//    	initServlert();
//    	StringBuilder sb = new StringBuilder();
//    	
//    	String trainAdmin = request.getParameter("id");
//    	if(trainAdmin != null && !"".equals(trainAdmin)){
//    		TrainingAdmin ta = this.iTrainingAdmin.get(Integer.parseInt(trainAdmin));
//    		
//    		if(ta.getRestrictRight()){//获取权限列表
//    			Set<TrainingAdminAuthority> trainAdminAuthoritySet = ta.getTrainingAdminAuthoritys();
//    			
////    			TrainingAdminAuthority taa = this.
//    			
//    		}else{//获取项目列表
//    			
//    		}
//    	}
//    	
//    }
//    
//    public void addAuthority(){
//    	initServlert();
//    	StringBuilder sb = new StringBuilder();
//    	
//    	String trainAdmin = request.getParameter("id");
//    	if(trainAdmin != null && !"".equals(trainAdmin)){
//    		TrainingAdmin ta = this.iTrainingAdmin.get(Integer.parseInt(trainAdmin));
//    		
//    		if(ta.getRestrictRight()){//编辑
//    			//先清除原权限，再新增
////    			TrainingAdminAuthority taa = this.
//    			
//    		}else{//新增
//    			
//    		}
//    	}
//    	
//    }
    
    public String authority() {
		return "authority";
	}
    
    /**
	 * @category 获取承训单位管理员权限列表
	 * @param jtStartIndex
	 * @param jtPageSize
	 * @param jtSorting
	 */
	@SuppressWarnings("rawtypes")
	public void getTrainingAdminAuthorityList() {

		initServlert();
    	UserSession us = (UserSession) session.getAttribute("usersession");
		try {
			if(us.getCreateuser() == 1){
				String ist = (String) request.getParameter("jtStartIndex");
				if (ist == null || ist.equals("") || ist.equals("NaN"))
				{
					ist = "0";
				}
	
				// 显示的条数
				String ien = (String) request.getParameter("jtPageSize");
				if (ien == null || ien.equals(""))
				{
					ien = DictionyMap.maxPageSize + "";
				}
	
				int start = Integer.parseInt(ist);
				int limit = DictionyMap.maxPageSize;
				limit = Integer.parseInt(ien);
	
				// 排序
				String sort = request.getParameter("jtSorting");
	
				String hql = " from TrainingAdmin t,TrainingCollege tc where 1=1 and t.trainingCollege=tc.id ";
				hql += " and t.trainingCollege=" + iTrainingAdmin.get(us.getId()).getTrainingCollege().getId();
				// 以后其他搜索参数
				String q = request.getParameter("q");
				q = q == null ? "" : q;
				String stype = request.getParameter("stype");
				stype = stype == null ? "" : stype;
	
				if (q.length() > 0)
				{
					hql += " and (t." + stype + " like '%" + q + "%' or tc.name like '%" + q + "%' )";
				}
	
				// TODO 加上权限搜索
	
				// 排序 参数
				if (sort != null && !sort.equals(""))
				{
					String[] sortarray = sort.split(" ");
					String sortname = sortarray[0];
					String sorttype = sortarray[1];
					hql += " order by t." + sortname + " " + sorttype;
				}
	
				List<Object> licount = this.iTrainingAdmin.findByHSQL("select count(*) " + hql);
				int records = Integer.parseInt(licount.get(0).toString());
				List li = this.iTrainingAdmin.getListForPage(hql, start, limit);
				
				StringBuilder sb = new StringBuilder();
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
	
				boolean flag = false;
				sb.append("\"Records\":[");
				for (int i = 0; i < li.size(); i++) {
					Object[] ot = (Object[]) li.get(i);
					TrainingAdmin ta = (TrainingAdmin) ot[0];
					
					int id = ta.getId();
					flag = true;
					String name = ta.getName();
					String mobile = ta.getMobile();
	
					// 工作单位
					String organizationName = ta.getTrainingCollege().getName();
					String department = ta.getDepartment(); // 所在部门
	
					String phone = ta.getPhone();
					String jobDuty = ta.getJobDuty(); // 职务
					String authority = "";
					if(ta.getRestrictRight()){
						for (TrainingAdminAuthority taa : ta.getTrainingAdminAuthoritys()) {
							authority += getNaviString(taa);
							authority += "</br>";
						}
					}else{
						authority = "全部项目权限";
					}
					StringBuilder sbstr = new StringBuilder();
					sbstr.append("{");
					sbstr.append("\"id\":" + id);
					sbstr.append(",");
					sbstr.append("\"name\":\"" + name + "\"");
					sbstr.append(",");
					sbstr.append("\"mobile\":\"" + mobile + "\"");
					sbstr.append(",");
					sbstr.append("\"organization\":\"" + organizationName + "\"");
					sbstr.append(",");
					sbstr.append("\"department\":\"" + department + "\"");
					sbstr.append(",");
					sbstr.append("\"phone\":\"" + phone + "\"");
					sbstr.append(",");
					sbstr.append("\"jobDuty\":\"" + jobDuty + "\"");
					sbstr.append(",");
					sbstr.append("\"authority\":\"" + authority + "\"");
					sbstr.append("},");
					sb.append(sbstr.toString());
				}
	
				if (flag) {
					sb.delete(sb.length() - 1, sb.length());
				}
				sb.append("]");
				sb.append(",");
				sb.append("\"TotalRecordCount\":" + records);
				sb.append("}");
	
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			}else{
				Utlity.sendResponse("ERROR", "无权查看承训单位管理员权限！", response);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
	}
	String getNaviString(TrainingAdminAuthority taa){
		String naviString = taa.getProject().getName();
		if(taa.getTrainingSubject()!=null){
			naviString = naviString + ">" +taa.getTrainingSubject().getName();
			if(taa.getClasses() != null){
				naviString = naviString + ">" + taa.getClasses() + "班";
			}
		}
				
		return naviString;
	}
	
	public String opPassword() {
		initServlert();
		String id = request.getParameter("id");
		if (id != null && !id.equals("")) {
			TrainingAdmin ta = this.iTrainingAdmin.get(Integer.parseInt(id));
			String password=ta.getMobile();
			password=password.substring(password.length()-6);
			ta.setPassword(password);
			this.iTrainingAdmin.update(ta);
		}
		return "init";
	}
    /**
     * @category 删除 管理员信息
     */
    public void OptTrainingAdmin()
    {
	try
	{
	    initServlert();

	    String method = request.getParameter("method");
	    if (method.equals("delete"))
	    {
		// 删除
		String id = request.getParameter("id");
		if (id != null && !id.equals(""))
		{
		    TrainingAdmin pa = this.iTrainingAdmin.get(Integer
			    .parseInt(id));
		    if (pa != null)
		    {
			try
			{

			    // TODO 可能根据当前用户与要删除的用户进行 承训单位，

			    this.iTrainingAdmin.delete(pa);
			    sendResponse("OK", "删除成功");

			}
			catch (Exception ex)
			{
			    sendResponse("ERROR", "删除失败,存在项目类型权限");
			}

		    }
		    else
		    {
			sendResponse("ERROR", "删除失败，不存在项目管理员");
		    }
		}
		else
		{
		    sendResponse("ERROR", "删除失败，不存在项目管理员");
		}

	    }
	    else
	    {
		sendResponse("ERROR", "操作失败，不存在操作类型");
	    }
	}
	catch (Exception ex)
	{
	    logger.error(ex);
	    sendResponse("ERROR", "操作失败");
	}
    }

    public void sendResponse(String status, String value)
    {
	StringBuilder checkSB = new StringBuilder();
	checkSB.append("{");
	checkSB.append("\"Result\":\"" + status + "\"");
	checkSB.append(",");
	checkSB.append("\"Message\":\"" + value + "\"");
	checkSB.append("}");
	Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
    }

    // 属性

    public ITrainingCollegeService getiTrainingCollegeService()
    {
	return iTrainingCollegeService;
    }

    public void setiTrainingCollegeService(
	    ITrainingCollegeService iTrainingCollegeService)
    {
	this.iTrainingCollegeService = iTrainingCollegeService;
    }

    public IAreaService getiArea()
    {
	return iArea;
    }

    public void setiArea(IAreaService iArea)
    {
	this.iArea = iArea;
    }

    public ITrainingAdminService getiTrainingAdmin()
    {
	return iTrainingAdmin;
    }

    public void setiTrainingAdmin(ITrainingAdminService iTrainingAdmin)
    {
	this.iTrainingAdmin = iTrainingAdmin;
    }

    public IEthnicService getiEthnicService()
    {
	return iEthnicService;
    }

    public void setiEthnicService(IEthnicService iEthnicService)
    {
	this.iEthnicService = iEthnicService;
    }

    /**
     * @return the showAddButton
     */
    public int getShowAddButton()
    {
	return showAddButton;
    }

    /**
     * @param showAddButton
     *            the showAddButton to set
     */
    public void setShowAddButton(int showAddButton)
    {
	this.showAddButton = showAddButton;
    }

}
