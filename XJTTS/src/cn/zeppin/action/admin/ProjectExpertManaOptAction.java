package cn.zeppin.action.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Area;
import cn.zeppin.entity.EductionBackground;
import cn.zeppin.entity.Ethnic;
import cn.zeppin.entity.Politics;
import cn.zeppin.entity.ProjectExpert;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IEductionBackgroundService;
import cn.zeppin.service.IEthnicService;
import cn.zeppin.service.IPoliticsService;
import cn.zeppin.service.IProjectExpertService;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

public class ProjectExpertManaOptAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(ProjectExpertManaOptAction.class);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private IAreaService iAreaService;
	private IEthnicService iEthnicService;
	private IProjectExpertService iProjectExpertService;
	private IPoliticsService iPoliticsService;
	private IEductionBackgroundService iEductionBackgroundService;

	// 初始化 地區列表
	private List<Object[]> provinceIds;
	private List<Object[]> cityIds;
	private List<Object[]> countyIds;
	private List<Ethnic> listEthnic;
	private List<Politics> listPoliticsId;
	private List<EductionBackground> listEduction;

	// from 表单

	private String id;
	private String name;
	private String sex;
	private String idcard;
	private String mobile;
	private String[] area;
	private String ethnic;
	private String organization;
	private String department;
	private String jobDuty;
	private String jobTitle;
	private String politicsId;
	private String eduction;
	private String email;
	private String address;
	private String phone;
	private String fax;
	private String status;
	private String research;
	private String teach;
	private String resume;
	private String achievement;
	private String experience;
	private String remark;


	private String pageStatus;
	private String message;

	public ProjectExpertManaOptAction() {
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	// 业务逻辑方法

	// 导航跳转初始化
	public String initPage() {
		initServlert();
		String id = request.getParameter("id");
		this.id = id;

		// 设置地区 与 名族
		// 地区第一级
		provinceIds = new ArrayList<Object[]>();
		this.provinceIds.add(new Object[] { 0, "请选择..." });
		List<Area> sli = iAreaService.getParentCodeArea("0");
		for (Area ae : sli) {
			Object[] obj = { ae.getId(), ae.getName() };
			provinceIds.add(obj);	
		}

		// 获取民族信息
		if (this.listEthnic == null) {
			this.listEthnic = this.iEthnicService.getEthnicByWight();
		}
		
		if (this.listPoliticsId == null) {
			this.listPoliticsId = this.iPoliticsService.getPoliticsByWight();
		}
		
		if (this.listEduction == null) {
			this.listEduction = this.iEductionBackgroundService.getEductionBackgroundByWight(); 
		}


		if (id != null && !id.equals("0") && !id.equals("")) {
			// 编辑
			ProjectExpert pa = this.iProjectExpertService.get(Integer.parseInt(id));

			this.pageStatus = "";
			this.message = "";

			if (pa != null) {
				this.name = pa.getName();
				this.sex = pa.getSex() == null ? "1" : pa.getSex().toString();

				this.idcard = pa.getIdcard();
				this.mobile = pa.getMobile();
				this.ethnic = pa.getEthnic() == null ? "1" : pa.getEthnic().getId().toString();
				this.organization = pa.getOrganization();
				this.department = pa.getDepartment();
				this.jobDuty = pa.getJobDuty();
				this.jobTitle=pa.getJobTitle();
				this.politicsId=pa.getPolitics()==null?"1":pa.getPolitics().getId().toString();
				this.eduction=pa.getEductionBackground()==null?"1":pa.getEductionBackground().getId().toString();
				this.research=pa.getResearch();
				this.resume=pa.getResume();
				this.teach=pa.getTeach();
				this.achievement=pa.getAchievement();
				this.experience=pa.getExperience();
				this.email = pa.getEmail();
				this.address = pa.getAddress();
				this.phone = pa.getPhone();
				this.fax = pa.getFax();
				this.status = pa.getStatus().toString();
				this.remark = pa.getRemark();

				if(pa.getArea()!=null){
					Area a = pa.getArea();
					String code1 = "0";
					String code2 = "0";
					int areaid = a.getId();
					int areaid1 = 0;
					int areaid2 = 0;
					String areacode = a.getParentcode();
					if (areacode != null && !areacode.equals("0") && !areacode.equals("")) {
						// 当前的地区的父地区存在
						Area pae = this.iAreaService.getAreaByCode(areacode);
						if (pae != null) {
							areaid1 = pae.getId();
							code1 = pae.getCode();
							areacode = pae.getParentcode();
							// 父父地区
							if (areacode != null && !areacode.equals("0") && !areacode.equals("")) {
								pae = this.iAreaService.getAreaByCode(areacode);
								if (pae != null) {
									areaid2 = pae.getId();
									code2 = pae.getCode();
								}
							}
						}
					}
					int a1 = 0, a2 = 0, a3 = 0;
					String c1 = "0", c2 = "0";
					if (areaid1 > 0) {
						if (areaid2 > 0) {
							a1 = areaid2;
							a2 = areaid1;
							a3 = areaid;
							c1 = code2;
							c2 = code1;
						} else {
							a1 = areaid1;
							a2 = areaid;
							a3 = 0;
							c1 = code1;
						}
					} else {
						a1 = areaid;
						a2 = 0;
						a3 = 0;
					}
					this.setArea(new String[] { a1 + "", a2 + "", a3 + "" });
	
					if (!c1.equals("0")) {
						List<Area> li = iAreaService.getParentCodeArea(c1);
						cityIds = new ArrayList<Object[]>();
						for (Area ae : li) {
							Object[] obj = { ae.getId(), ae.getName() };
							cityIds.add(obj);
						}
					} else {
						this.cityIds = new ArrayList<Object[]>();
						this.cityIds.add(new Object[] { 0, "请选择..." });
	
						List<Area> tli = iAreaService.getParentCodeArea(a.getCode());
						for (Area ae : tli) {
							Object[] obj = { ae.getId(), ae.getName() };
							cityIds.add(obj);
						}
					}
					if (!c2.equals("0")) {
						List<Area> li = iAreaService.getParentCodeArea(c2);
						this.countyIds = new ArrayList<Object[]>();
						for (Area ae : li) {
							Object[] obj = { ae.getId(), ae.getName() };
							countyIds.add(obj);
						}
					} else {
						this.countyIds = new ArrayList<Object[]>();
						this.countyIds.add(new Object[] { 0, "请选择..." });
					}
				}else{
					this.cityIds = new ArrayList<Object[]>();
					this.countyIds = new ArrayList<Object[]>();
					this.cityIds.add((new Object[] { 0, "请选择..." }));
					this.countyIds.add((new Object[] { 0, "请选择..." }));
				}

			}

		} else {
			// 添加

			this.pageStatus = "";
			this.message = "";

			this.name = "";
			this.sex = "";
			this.idcard = "";
			this.mobile = "";
			this.ethnic = "";
			this.organization = "";
			this.department = "";
			this.jobDuty = "";
			this.jobTitle="";
			this.politicsId="";
			this.eduction="";
			this.research="";
			this.resume="";
			this.teach="";
			this.achievement="";
			this.experience="";
			this.email = "";
			this.address = "";
			this.phone = "";
			this.fax = "";
			this.status = "";
			this.remark = "";
			this.area = new String[3];

			this.cityIds = new ArrayList<Object[]>();
			this.cityIds.add(new Object[] { 0, "请选择..." });
			
			this.countyIds = new ArrayList<Object[]>();
			this.countyIds.add(new Object[] { 0, "请选择..." });
		}

		return "init";
	}

	public void opProjectExpert() {

		initServlert();
		try {

			Map<String, String[]> parMap = request.getParameterMap();
			String[] area = parMap.get("area");
 			String id = parMap.get("id")[0];
			String name = parMap.get("name")[0];
			String sex = parMap.get("sex")[0];
			String idcard = parMap.get("idcard")[0];
			String mobile = parMap.get("mobile")[0];
			String ethnic = parMap.get("ethnic")[0];
			String organization = parMap.get("organization")[0];
			String department = parMap.get("department")[0];
			String jobDuty = parMap.get("jobDuty")[0];
			String jobTitle=parMap.get("jobTitle")[0];
			String politicsId=parMap.get("politicsId")[0];
			String eduction=parMap.get("eduction")[0];
			String research=parMap.get("research")[0];
			String resume=parMap.get("resume")[0];
			String teach=parMap.get("teach")[0];
			String achievement=parMap.get("achievement")[0];
			String experience=parMap.get("experience")[0];
			String email = parMap.get("email")[0];
			String address = parMap.get("address")[0];
			String phone = parMap.get("phone")[0];
			String fax = parMap.get("fax")[0];
			String status = parMap.get("status")[0];
			String remark = parMap.get("remark")[0];


			// 获取地区id
			String area0 = area[0];
			String area1 = area[1];
			String area2 = area[2];
			String tmpArea = area0;
			if (area2 != null && !area2.equals("0") && !area2.equals("")) {
				tmpArea = area2;
			} else {
				if (area1 != null && !area1.equals("0") && !area1.equals("")) {
					tmpArea = area1;
				}
			}
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
			if (organization == null || organization.equals("")) {
				sendResponse("ERROR", "工作单位为空");
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
				Object[] pars = { idcard, mobile, email };
				int flag = iProjectExpertService.checkUserInfo(pars);
				if (flag > 0) {
					sendResponse("ERROR", "添加失败，已经存在用户信息");
					return;
				}

				ProjectExpert pa = new ProjectExpert();
				pa.setName(name);
				pa.setIdcard(idcard);
				pa.setSex(Short.parseShort(sex));
				pa.setMobile(mobile);
				pa.setPassword(mobile.substring(mobile.length() - 6, mobile.length()));
				pa.setDepartment(department);
				pa.setJobDuty(jobDuty);
				pa.setJobTitle(jobTitle);
				pa.setResearch(research);
				pa.setResume(resume);
				pa.setTeach(teach);
				pa.setAchievement(achievement);
				pa.setExperience(experience);
				pa.setEmail(email);
				pa.setAddress(address);
				pa.setPhone(phone);
				pa.setFax(fax);
				
				Ethnic e = this.iEthnicService.get(Short.parseShort(ethnic));
				pa.setEthnic(e);
				
				EductionBackground edu=this.iEductionBackgroundService.get(Integer.valueOf(eduction));
				pa.setEductionBackground(edu);
				
				Politics p=this.iPoliticsService.getPoliticsById(politicsId);
				pa.setPolitics(p);

				pa.setStatus(Short.parseShort(status));
				pa.setRemark(remark);

				Area a = this.iAreaService.get(Integer.parseInt(tmpArea));
				pa.setArea(a);

				pa.setOrganization(organization);

				UserSession us = (UserSession) session.getAttribute("usersession");
				pa.setCreator(us.getId());

				this.iProjectExpertService.add(pa);
				sendResponse("OK", "添加成功");
				return;

			} else {
				// 修改

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
				if (organization == null || organization.equals("")) {
					sendResponse("ERROR", "工作单位为空");
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
				// TODO 判断 是否存在相同属性数据
				int eid = Integer.parseInt(id);
				ProjectExpert pa = this.iProjectExpertService.get(eid);

				// 身份证
				if (pa.getIdcard() == null || !pa.getIdcard().equals(idcard)) {
					Object[] pars = { idcard, null, null };
					int flag = iProjectExpertService.checkUserInfo(pars);
					if (flag > 0) {
						sendResponse("ERROR", "添加失败，已经存在用户 身份证 信息");
						return;
					}
				}

				// 手机
				if (pa.getMobile() == null || !pa.getMobile().equals(mobile)) {
					Object[] pars = { null, mobile, null };
					int flag = iProjectExpertService.checkUserInfo(pars);
					if (flag > 0) {
						sendResponse("ERROR", "添加失败，已经存在用户 手机 信息");
						return;
					}
				}

				if (pa.getEmail() == null || !pa.getEmail().equals(email)) {
					Object[] pars = { null, null, email };
					int flag = iProjectExpertService.checkUserInfo(pars);
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
				pa.setJobTitle(jobTitle);
				pa.setResearch(research);
				pa.setResume(resume);
				pa.setTeach(teach);
				pa.setAchievement(achievement);
				pa.setExperience(experience);
				pa.setEmail(email);
				pa.setAddress(address);
				pa.setPhone(phone);
				pa.setFax(fax);
				pa.setRemark(remark);


				Politics p=this.iPoliticsService.getPoliticsById(politicsId);
				pa.setPolitics(p);
				
				EductionBackground edu=this.iEductionBackgroundService.get(Integer.valueOf(eduction));
				pa.setEductionBackground(edu);
				
				pa.setStatus(Short.parseShort(status));

				Area a = this.iAreaService.get(Integer.parseInt(tmpArea));
				pa.setArea(a);

				Ethnic e = this.iEthnicService.get(Short.parseShort(ethnic));
				pa.setEthnic(e);

				pa.setOrganization(organization);

				this.iProjectExpertService.update(pa);

				sendResponse("OK", "编辑成功");
				return;

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			sendResponse("ERROR", "操作出现异常");
			return;
		}
	}


	public void sendResponse(String status, String value) {
		StringBuilder checkSB = new StringBuilder();
		checkSB.append("{");
		checkSB.append("\"Result\":\"" + status + "\"");
		checkSB.append(",");
		checkSB.append("\"Message\":\"" + value + "\"");
		checkSB.append("}");
		Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
	}

	public String getNaviString(ProjectType pt) {
		if (pt.getProjectType() == null) {
			return pt.getName();
		} else {
			return getNaviString(pt.getProjectType()) + "&" + pt.getName();
		}
	}

	// 属性

	public IAreaService getiAreaService() {
		return iAreaService;
	}

	public void setiAreaService(IAreaService iAreaService) {
		this.iAreaService = iAreaService;
	}
	
	public IPoliticsService getiPoliticsService() {
		return iPoliticsService;
	}

	public void setiPoliticsService(IPoliticsService iPoliticsService) {
		this.iPoliticsService = iPoliticsService;
	}
	
	public IEductionBackgroundService getiEductionBackgroundService() {
		return iEductionBackgroundService;
	}

	public void setiEductionBackgroundService(IEductionBackgroundService iEductionBackgroundService) {
		this.iEductionBackgroundService = iEductionBackgroundService;
	}

	public IEthnicService getiEthnicService() {
		return iEthnicService;
	}

	public void setiEthnicService(IEthnicService iEthnicService) {
		this.iEthnicService = iEthnicService;
	}


	public IProjectExpertService getiProjectExpertService() {
		return iProjectExpertService;
	}

	public void setiProjectExpertService(IProjectExpertService iProjectExpertService) {
		this.iProjectExpertService = iProjectExpertService;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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

	public String[] getArea() {
		return area;
	}

	public void setArea(String[] area) {
		this.area = area;
	}

	public String getEthnic() {
		return ethnic;
	}

	public void setEthnic(String ethnic) {
		this.ethnic = ethnic;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getJobDuty() {
		return jobDuty;
	}

	public void setJobDuty(String jobDuty) {
		this.jobDuty = jobDuty;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPoliticsId() {
		return politicsId;
	}

	public void setPoliticsId(String politicsId) {
		this.politicsId = politicsId;
	}
	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getEduction() {
		return eduction;
	}

	public void setEduction(String eduction) {
		this.eduction = eduction;
	}
	public String getResearch() {
		return research;
	}

	public void setResearch(String research) {
		this.research = research;
	}
	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}
	public String getTeach() {
		return teach;
	}

	public void setTeach(String teach) {
		this.teach = teach;
	}
	public String getAchievement() {
		return achievement;
	}

	public void setAchievement(String achievement) {
		this.achievement = achievement;
	}
	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
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

	public List<Object[]> getCityIds() {
		return cityIds;
	}

	public void setCityIds(List<Object[]> cityIds) {
		this.cityIds = cityIds;
	}

	public List<Object[]> getCountyIds() {
		return countyIds;
	}

	public void setCountyIds(List<Object[]> countyIds) {
		this.countyIds = countyIds;
	}

	public List<Ethnic> getListEthnic() {
		return listEthnic;
	}

	public void setListEthnic(List<Ethnic> listEthnic) {
		this.listEthnic = listEthnic;
	}
	
	public List<Politics> getListPoliticsId() {
		return listPoliticsId;
	}

	public void setListPoliticsId(List<Politics> listPoliticsId) {
		this.listPoliticsId = listPoliticsId;
	}
	
	public List<EductionBackground> getListEduction() {
		return listEduction;
	}

	public void setListEduction(List<EductionBackground> listEduction) {
		this.listEduction = listEduction;
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



}
