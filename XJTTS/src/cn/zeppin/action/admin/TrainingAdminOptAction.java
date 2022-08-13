package cn.zeppin.action.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Area;
import cn.zeppin.entity.Ethnic;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectAdminRight;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.TrainingAdmin;
import cn.zeppin.entity.TrainingAdminAuthority;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IEthnicService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.ITrainingAdminAuthorityService;
import cn.zeppin.service.ITrainingAdminService;
import cn.zeppin.service.ITrainingCollegeService;
import cn.zeppin.service.ITrainingSubjectService;
import cn.zeppin.utility.DictionyMap.ROLEENUM;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

public class TrainingAdminOptAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	static Logger logger = LogManager.getLogger(TrainingAdminOptAction.class);

	private ITrainingCollegeService iTrainingCollegeService;
	private IAreaService iArea; // 地区信息
	private ITrainingAdminService iTrainingAdmin;
	private IEthnicService iEthnicService;
	private IProjectService iProjectService; // 项目信息
	private ITrainingSubjectService iTrainingSubjectService; // 培训科目
	private ITrainingAdminAuthorityService iTrainingAdminAuthorityService; //承训单位管理员权限

	// from 表单
	private String id;
	private String area;
	private String trainingCollege;
	private String ethnic;
	private String name;
	private String idcard;
	private String mobile;
	private String email;
	private String sex;
	private String department;
	private String createuser;
	private String phone;
	private String fax;
	private String jobDuty;
	private String address;
	private String postcode;
	private String status;
	private String remark;
	private String trainingCollegeEnable = "true";
	private String[] restrictRightId;
	private List<String[]> restrictRightList;
	private String restrictRight; // 是否开启 项目类型权限

	// 初始化 地區列表与民族列表
	private List<Object[]> provinceIds;
	private List<Ethnic> listEthnic;
	private List<TrainingCollege> listCollege;

	// 状态与错误消息
	private String pageStatus;
	private String message;

	public TrainingAdminOptAction() {
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	public String initPage() {

		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		ROLEENUM ROLE = ROLEENUM.valueof(us.getRole());

		String id = request.getParameter("id");
		this.id = id;

		this.pageStatus = "";
		this.message = "";
		trainingCollegeEnable = "true";

		// 设置地区 与 名族
		// 地区第一级
		if (provinceIds == null) {
			provinceIds = new ArrayList<Object[]>();

			List<Area> li = iArea.getParentCodeArea("0");
			for (Area ae : li) {
				Object[] obj = { ae.getId(), ae.getName() };
				provinceIds.add(obj);
			}
		}

		// 获取民族信息
		if (this.listEthnic == null) {
			this.listEthnic = this.iEthnicService.getEthnicByWight();
		}

		this.listCollege = this.iTrainingCollegeService.findByStatus((short) 1);

		if (id != null && !id.equals("0") && !id.equals("")) {
			// 编辑

			TrainingAdmin ta = this.iTrainingAdmin.get(Integer.parseInt(id));

			if (ta != null) {

				this.area = ta.getArea().getId().toString();
				this.trainingCollege = ta.getTrainingCollege().getId().toString();
				this.ethnic = ta.getEthnic().getId().toString();
				this.name = ta.getName();
				this.idcard = ta.getIdcard();
				this.mobile = ta.getMobile();
				this.email = ta.getEmail();
				this.sex = ta.getSex() == null ? "1" : ta.getSex().toString();
				this.department = ta.getDepartment();
				this.createuser = ta.getCreateuser() ? "1" : "0";
				this.phone = ta.getPhone();
				this.fax = ta.getFax();
				this.jobDuty = ta.getJobDuty();
				this.address = ta.getAddress();
				this.postcode = ta.getPostcode();
				this.status = ta.getStatus().toString();
				this.remark = ta.getRemark();
				this.restrictRight = ta.getRestrictRight() ? "1" : "0";
				
				//加载权限
				Set<TrainingAdminAuthority> setTrainAdminAuthority = ta.getTrainingAdminAuthoritys();
				this.restrictRightId = new String[setTrainAdminAuthority.size()];
				this.restrictRightList = new ArrayList<String[]>();
				int index = 0;
				for(TrainingAdminAuthority taa: setTrainAdminAuthority){
					Project project = taa.getProject();
					String ids = project.getId().toString();
					
					if(taa.getTrainingSubject() != null){
						ids += "_"+taa.getTrainingSubject().getId();
					}
					if(taa.getClasses() != null){
						ids += "_" + taa.getClasses();
					}
					
					this.restrictRightId[index] = ids;
					
					String ts = getNaviString(taa);
					if(ts.length()>0){
						String[] spts = ts.split(">");
						String[] rerights = new String[spts.length + 1];
						for(int i = 0; i < spts.length; i++){
							rerights[i] = spts[i];
						}
						rerights[spts.length] = ids;
						this.restrictRightList.add(rerights);
					}
					index++;
					
				}
//				// 加载权限
//				Set<ProjectAdminRight> setProjectRights = pa.getProjectAdminRights();
//				this.restrictRightId = new String[setProjectRights.size()];
//				this.restrictRightList = new ArrayList<String[]>();
//				int index = 0;
//				for (ProjectAdminRight tpar : setProjectRights) {
//					ProjectType tpt = tpar.getProjectType();
//					this.restrictRightId[index] = tpt.getId().toString();
//					String ts = getNaviString(tpt);
//					if (ts.length() > 0) {
//						String[] spts = ts.split("&");
//						String[] rerights = new String[spts.length + 1];
//						for (int i = 0; i < spts.length; i++) {
//							rerights[i] = spts[i];
//						}
//						rerights[spts.length] = tpt.getId().toString();
//						this.restrictRightList.add(rerights);
//					}
//					index++;
//				}

			}

		} else {
			// 添加
			this.area = "";
			this.trainingCollege = "";
			this.ethnic = "";
			this.name = "";
			this.idcard = "";
			this.mobile = "";
			this.email = "";
			this.sex = "";
			this.department = "";
			this.createuser = "";
			this.phone = "";
			this.fax = "";
			this.jobDuty = "";
			this.address = "";
			this.postcode = "";
			this.status = "";
			this.remark = "";
			this.restrictRight = "0";
		}
		if (ROLE == ROLEENUM.TRAINING) {
			this.trainingCollege = iTrainingAdmin.get(us.getId()).getTrainingCollege().getId().toString();
			trainingCollegeEnable = "false";
			session.setAttribute("trainingCollege", trainingCollege);
		}

		return "init";
	}
	
	String getNaviString(TrainingAdminAuthority taa){
		String naviString = taa.getProject().getName();
		if(taa.getTrainingSubject()!=null){
			naviString = naviString + ">" +taa.getTrainingSubject().getName();
			if(taa.getClasses() != null){
				
				if(taa.getClasses() == 0){
					naviString = naviString + ">全部";
				}else{
					naviString = naviString + ">" + taa.getClasses() + "班";
				}
			}
		}
				
		return naviString;
	}

	/**
	 * @category 承训单位管理员管理 编辑与添加
	 */
	public void optTrainingAdmin() {

		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		ROLEENUM ROLE = ROLEENUM.valueof(us.getRole());

		try {
			Map<String, String[]> parMap = request.getParameterMap();

			String id = parMap.get("id")[0];
			String area = parMap.get("area")[0];
			String trainingCollege;
			if (ROLE == ROLEENUM.TRAINING) {

				trainingCollege = session.getAttribute("trainingCollege").toString();
			} else {
				trainingCollege = parMap.get("trainingCollege")[0];
			}

			String ethnic = parMap.get("ethnic")[0];
			String name = parMap.get("name")[0];
			String idcard = parMap.get("idcard")[0];
			String mobile = parMap.get("mobile")[0];
			String email = parMap.get("email")[0];
			String sex = parMap.get("sex")[0];
			String department = parMap.get("department")[0];
			String createuser = parMap.containsKey("createuser") ? parMap.get("createuser")[0] : "";
			String phone = parMap.get("phone")[0];
			String fax = parMap.get("fax")[0];
			String jobDuty = parMap.get("jobDuty")[0];
			String address = parMap.get("address")[0];
			String postcode = parMap.containsKey("postcode") ? parMap.get("")[0] : "";
			String status = parMap.get("status")[0];
			String remark = parMap.get("remark")[0];
			String restrictRight = parMap.containsKey("restrictRight") ? parMap.get("restrictRight")[0] : ""; // 是否开启项目类型权限
			String[] restrictRightId = parMap.get("restrictRightId");

			if (name == null || name.equals("")) {
				sendResponse("ERROR", "用户名为空");
				return;
			}
			if (idcard == null || idcard.equals("")) {
				sendResponse("ERROR", "身份证为空");
				return;
			}
			if (mobile == null || mobile.equals("")) {
				sendResponse("ERROR", "手机为空");
				return;
			}
			if (email == null || email.equals("")) {
				sendResponse("ERROR", "邮箱为空");
				return;
			}

			if (!Utlity.checkIdCard(idcard)) {
				sendResponse("ERROR", "身份证不合法");
				return;
			}
			if (!Utlity.isMobileNO(mobile)) {
				sendResponse("ERROR", "手机不合法");
				return;
			}
			if (!Utlity.isEmail(email)) {
				sendResponse("ERROR", "邮箱不合法");
				return;
			}

			if (id == null || id.equals("0") || id.equals("")) {
				// 添加
				Object[] pars = { idcard, mobile, email };
				int flag = iTrainingAdmin.checkUserInfo(pars);
				if (flag > 0) {
					sendResponse("ERROR", "添加失败，已经存在用户信息");
					return;
				}

				TrainingAdmin ta = new TrainingAdmin();
				ta.setName(name);
				ta.setIdcard(idcard);
				ta.setSex(Short.parseShort(sex));
				ta.setMobile(mobile);
				ta.setPassword(mobile.substring(mobile.length() - 6, mobile.length()));
				ta.setDepartment(department);
				ta.setJobDuty(jobDuty);
				ta.setEmail(email);
				ta.setAddress(address);
				ta.setPhone(phone);
				ta.setFax(fax);
				ta.setRestrictRight(false);

				if (createuser != null && createuser.equals("on")) {
					ta.setCreateuser(true);
				} else {
					ta.setCreateuser(false);
				}
				
				if (restrictRight != null && restrictRight.equals("on")) {
					ta.setRestrictRight(true);
				} else {
					ta.setRestrictRight(false);
				}

				ta.setStatus(Short.parseShort(status));
				ta.setRemark(remark);

				Area a = iArea.get(Integer.parseInt(area));
				ta.setArea(a);

				Ethnic e = iEthnicService.get(Short.parseShort(ethnic));
				ta.setEthnic(e);

				ta.setCreator(us.getCreator());

				TrainingCollege tc = iTrainingCollegeService.get(Integer.parseInt(trainingCollege));
				ta.setTrainingCollege(tc);

				iTrainingAdmin.add(ta);
				
				try {
					if(restrictRightId != null){
						
						for (String s : restrictRightId) {
							if (s != null && !s.equals("0") && !s.equals("")) {
								TrainingAdminAuthority taa = new TrainingAdminAuthority();
								String[] ids = s.split("_");
								if(ids != null && ids.length == 1){
									Project project = this.iProjectService.get(Integer.parseInt(ids[0]));
									taa.setProject(project);
								}else if(ids != null && ids.length == 2){
									Project project = this.iProjectService.get(Integer.parseInt(ids[0]));
									TrainingSubject ts = this.iTrainingSubjectService.get(Short.parseShort(ids[1]));
									taa.setProject(project);
									taa.setTrainingSubject(ts);
								}else if(ids != null && ids.length == 3){
									Project project = this.iProjectService.get(Integer.parseInt(ids[0]));
									TrainingSubject ts = this.iTrainingSubjectService.get(Short.parseShort(ids[1]));
									taa.setProject(project);
									taa.setTrainingSubject(ts);
									taa.setClasses(Integer.parseInt(ids[3]));
								}
								taa.setTrainingAdmin(ta);
								this.iTrainingAdminAuthorityService.add(taa);
							}
						}
						
					}
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}

				sendResponse("OK", "添加成功");
				return;

			} else {
				// 编辑
				int eid = Integer.parseInt(id);
				TrainingAdmin pa = iTrainingAdmin.get(eid);

				// 省份证
				if (pa.getIdcard() == null || !pa.getIdcard().equals(idcard)) {
					Object[] pars = { idcard, null, null };
					int flag = iTrainingAdmin.checkUserInfo(pars);
					if (flag > 0) {
						sendResponse("ERROR", "添加失败，已经存在用户 身份证 信息");
						return;
					}
				}

				// 手机
				if (pa.getMobile() == null || !pa.getMobile().equals(mobile)) {
					Object[] pars = { null, mobile, null };
					int flag = iTrainingAdmin.checkUserInfo(pars);
					if (flag > 0) {
						sendResponse("ERROR", "添加失败，已经存在用户 手机 信息");
						return;
					}
				}

				if (pa.getEmail() == null || !pa.getEmail().equals(email)) {
					Object[] pars = { null, null, email };
					int flag = iTrainingAdmin.checkUserInfo(pars);
					if (flag > 0) {
						sendResponse("ERROR", "添加失败，已经存在用户 邮箱 信息");
						return;
					}
				}

				pa.setName(name);
				pa.setIdcard(idcard);
				pa.setSex(Short.parseShort(sex));
				pa.setMobile(mobile);
				pa.setDepartment(department);
				pa.setJobDuty(jobDuty);
				pa.setEmail(email);
				pa.setAddress(address);
				pa.setPhone(phone);
				pa.setFax(fax);
				pa.setRemark(remark);
				pa.setStatus(Short.parseShort(status));
				pa.setRestrictRight(false);

				if (createuser != null && createuser.equals("on")) {
					pa.setCreateuser(true);
				} else {
					pa.setCreateuser(false);
				}
				if (restrictRight != null && restrictRight.equals("on")) {
					pa.setRestrictRight(true);
				} else {
					pa.setRestrictRight(false);
				}
				

				TrainingCollege tc = iTrainingCollegeService.get(Integer.parseInt(trainingCollege));
				pa.setTrainingCollege(tc);

				Area a = iArea.get(Integer.parseInt(area));
				pa.setArea(a);

				Ethnic e = iEthnicService.get(Short.parseShort(ethnic));
				pa.setEthnic(e);

				iTrainingAdmin.update(pa);
				
				
				//编辑权限
				try {
					
					// 先删除以前所有
					StringBuilder sb = new StringBuilder();
					sb.append("delete TrainingAdminAuthority t where 1=1 ");
					sb.append(" and  t.trainingAdmin=");
					sb.append(pa.getId());
					this.iTrainingAdminAuthorityService.executeHSQL(sb.toString());
					if(pa.getRestrictRight()){
						if(restrictRightId != null){
							
							for (String s : restrictRightId) {
								if (s != null && !s.equals("0") && !s.equals("")) {
									TrainingAdminAuthority taa = new TrainingAdminAuthority();
									String[] ids = s.split("_");
									if(ids != null && ids.length == 1){
										Project project = this.iProjectService.get(Integer.parseInt(ids[0]));
										taa.setProject(project);
									}else if(ids != null && ids.length == 2){
										Project project = this.iProjectService.get(Integer.parseInt(ids[0]));
										TrainingSubject ts = this.iTrainingSubjectService.get(Short.parseShort(ids[1]));
										taa.setProject(project);
										taa.setTrainingSubject(ts);
									}else if(ids != null && ids.length == 3){
										Project project = this.iProjectService.get(Integer.parseInt(ids[0]));
										TrainingSubject ts = this.iTrainingSubjectService.get(Short.parseShort(ids[1]));
										taa.setProject(project);
										taa.setTrainingSubject(ts);
										taa.setClasses(Integer.parseInt(ids[2]));
									}
									taa.setTrainingAdmin(pa);
									this.iTrainingAdminAuthorityService.add(taa);
								}
							}
							
						}
					}
					
					
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}

				sendResponse("OK", "编辑成功");
				return;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			sendResponse("ERROR", "操作异常失败");
			return;
		}

	}

	// 发送信息
	public void sendResponse(String status, String value) {
		StringBuilder checkSB = new StringBuilder();
		checkSB.append("{");
		checkSB.append("\"Result\":\"" + status + "\"");
		checkSB.append(",");
		checkSB.append("\"Message\":\"" + value + "\"");
		checkSB.append("}");
		Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
	}

	// 属性

	public ITrainingCollegeService getiTrainingCollegeService() {
		return iTrainingCollegeService;
	}

	public void setiTrainingCollegeService(ITrainingCollegeService iTrainingCollegeService) {
		this.iTrainingCollegeService = iTrainingCollegeService;
	}

	public IAreaService getiArea() {
		return iArea;
	}

	public void setiArea(IAreaService iArea) {
		this.iArea = iArea;
	}

	public ITrainingAdminService getiTrainingAdmin() {
		return iTrainingAdmin;
	}

	public void setiTrainingAdmin(ITrainingAdminService iTrainingAdmin) {
		this.iTrainingAdmin = iTrainingAdmin;
	}

	public IEthnicService getiEthnicService() {
		return iEthnicService;
	}

	public void setiEthnicService(IEthnicService iEthnicService) {
		this.iEthnicService = iEthnicService;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getTrainingCollege() {
		return trainingCollege;
	}

	public void setTrainingCollege(String trainingCollege) {
		this.trainingCollege = trainingCollege;
	}

	public String getEthnic() {
		return ethnic;
	}

	public void setEthnic(String ethnic) {
		this.ethnic = ethnic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getJobDuty() {
		return jobDuty;
	}

	public void setJobDuty(String jobDuty) {
		this.jobDuty = jobDuty;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Object[]> getProvinceIds() {
		return provinceIds;
	}

	public void setProvinceIds(List<Object[]> provinceIds) {
		this.provinceIds = provinceIds;
	}

	public List<Ethnic> getListEthnic() {
		return listEthnic;
	}

	public void setListEthnic(List<Ethnic> listEthnic) {
		this.listEthnic = listEthnic;
	}

	public List<TrainingCollege> getListCollege() {
		return listCollege;
	}

	public void setListCollege(List<TrainingCollege> listCollege) {
		this.listCollege = listCollege;
	}

	public String getPageStatus() {
		return pageStatus;
	}

	public void setPageStatus(String pageStatus) {
		this.pageStatus = pageStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the trainingCollegeEnable
	 */
	public String isTrainingCollegeEnable() {
		return trainingCollegeEnable;
	}

	/**
	 * @param trainingCollegeEnable
	 *            the trainingCollegeEnable to set
	 */
	public void setTrainingCollegeEnable(String trainingCollegeEnable) {
		this.trainingCollegeEnable = trainingCollegeEnable;
	}

	public String[] getRestrictRightId() {
		return restrictRightId;
	}

	public void setRestrictRightId(String[] restrictRightId) {
		this.restrictRightId = restrictRightId;
	}

	public List<String[]> getRestrictRightList() {
		return restrictRightList;
	}

	public void setRestrictRightList(List<String[]> restrictRightList) {
		this.restrictRightList = restrictRightList;
	}

	public String getRestrictRight() {
		return restrictRight;
	}

	public void setRestrictRight(String restrictRight) {
		this.restrictRight = restrictRight;
	}

	public IProjectService getiProjectService() {
		return iProjectService;
	}

	public void setiProjectService(IProjectService iProjectService) {
		this.iProjectService = iProjectService;
	}

	public ITrainingSubjectService getiTrainingSubjectService() {
		return iTrainingSubjectService;
	}

	public void setiTrainingSubjectService(
			ITrainingSubjectService iTrainingSubjectService) {
		this.iTrainingSubjectService = iTrainingSubjectService;
	}

	public ITrainingAdminAuthorityService getiTrainingAdminAuthorityService() {
		return iTrainingAdminAuthorityService;
	}

	public void setiTrainingAdminAuthorityService(
			ITrainingAdminAuthorityService iTrainingAdminAuthorityService) {
		this.iTrainingAdminAuthorityService = iTrainingAdminAuthorityService;
	}
	
	

}
