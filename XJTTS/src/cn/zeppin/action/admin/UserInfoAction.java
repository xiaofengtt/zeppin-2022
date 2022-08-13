/**
 * 
 */
package cn.zeppin.action.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Area;
import cn.zeppin.entity.EductionBackground;
import cn.zeppin.entity.Ethnic;
import cn.zeppin.entity.Politics;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectAdminRight;
import cn.zeppin.entity.ProjectExpert;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IEductionBackgroundService;
import cn.zeppin.service.IEthnicService;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.IPoliticsService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.IProjectExpertService;
import cn.zeppin.utility.DictionyMap;

import com.opensymphony.xwork2.ActionSupport;


public class UserInfoAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpSession session;
	
	private IProjectAdminService iProjectAdminService;
	private IProjectExpertService iProjectExpertService;
	
	private IAreaService iAreaService;
	private IOrganizationService iOragnizationService;
	private IEthnicService iEthnicService;
	private IPoliticsService iPoliticsService;
	private IEductionBackgroundService iEductionBackgroundService;
	
	private List<Object[]> provinceIds;
	private List<Object[]> cityIds;
	private List<Object[]> countyIds;
	private List<Ethnic> listEthnic;
	private List<Politics> listPoliticsId;
	private List<EductionBackground> listEduction;
	
	private String id;
	private String name;
	private String sex;
	private String idcard;
	private String mobile;
	private String[] area;
	private String ethnic;
	private String organization;
	private String organizationName = "请选择工作单位";
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
	private String restrictRight; // 是否开启 项目类型权限
	private String createuser; // 是否可创建用户
	private String level;
	private String[] restrictRightId;
	private List<String[]> restrictRightList;
	
	private String pageStatus;
	private String message;
	
	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
	}
	
	public String modifyInfo() {
		initServlert();
		
		provinceIds = new ArrayList<Object[]>();
		this.provinceIds.add(new Object[] { 0, "请选择..." });
		List<Area> sli = iAreaService.getParentCodeArea("0");
		for (Area ae : sli) {
			Object[] obj = { ae.getId(), ae.getName() };
			provinceIds.add(obj);	
		}
		
		if (this.listEthnic == null) {
			this.listEthnic = this.iEthnicService.getEthnicByWight();
		}
		
		if (this.listPoliticsId == null) {
			this.listPoliticsId = this.iPoliticsService.getPoliticsByWight();
		}
		
		if (this.listEduction == null) {
			this.listEduction = this.iEductionBackgroundService.getEductionBackgroundByWight(); 
		}
		
		UserSession us = (UserSession) session.getAttribute("usersession");
		
		this.id = us.getId().toString();
		if(us.getRole()==1){
			ProjectAdmin pa = this.iProjectAdminService.get(Integer.parseInt(id));

			this.pageStatus = "";
			this.message = "";

			if (pa != null) {
				this.name = pa.getName();
				this.sex = pa.getSex() == null ? "1" : pa.getSex().toString();

				this.idcard = pa.getIdcard();
				this.mobile = pa.getMobile();
				this.ethnic = pa.getEthnic() == null ? "1" : pa.getEthnic().getId().toString();
				this.organization = pa.getOrganization().getId().toString();
				this.organizationName = pa.getOrganization().getName();
				this.department = pa.getDepartment();
				this.jobDuty = pa.getJobDuty();
				this.email = pa.getEmail();
				this.address = pa.getAddress();
				this.phone = pa.getPhone();
				this.fax = pa.getFax();
				this.status = pa.getStatus().toString();
				this.remark = pa.getRemark();
				this.level = pa.getLevel().toString();
				this.createuser = pa.getCreateuser() ? "1" : "0";
				this.restrictRight = pa.getRestrictRight() ? "1" : "0";

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

					List<Area> tli = iAreaService.getParentCodeArea(DictionyMap.areaCode);
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

				// 加载权限
				Set<ProjectAdminRight> setProjectRights = pa.getProjectAdminRights();
				this.restrictRightId = new String[setProjectRights.size()];
				this.restrictRightList = new ArrayList<String[]>();
				int index = 0;
				for (ProjectAdminRight tpar : setProjectRights) {
					ProjectType tpt = tpar.getProjectType();
					this.restrictRightId[index] = tpt.getId().toString();
					String ts = getNaviString(tpt);
					if (ts.length() > 0) {
						String[] spts = ts.split("&");
						String[] rerights = new String[spts.length + 1];
						for (int i = 0; i < spts.length; i++) {
							rerights[i] = spts[i];
						}
						rerights[spts.length] = tpt.getId().toString();
						this.restrictRightList.add(rerights);
					}
					index++;
				}

			}
			return "modifyProjectAdmin";
		}else{
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
			return "modifyProjectExpert";
		}
	}
	
	String getNaviString(ProjectType pt) {
		if (pt.getProjectType() == null) {
			return pt.getName();
		} else {
			return getNaviString(pt.getProjectType()) + "&" + pt.getName();
		}
	}
	
	public IProjectAdminService getiProjectAdminService() {
		return iProjectAdminService;
	}

	public void setiProjectAdminService(IProjectAdminService iProjectAdminService) {
		this.iProjectAdminService = iProjectAdminService;
	}
	
	public IProjectExpertService getiProjectExpertService() {
		return iProjectExpertService;
	}

	public void setiProjectExpertService(IProjectExpertService iProjectExpertService) {
		this.iProjectExpertService = iProjectExpertService;
	}
	
	public IAreaService getiAreaService() {
		return iAreaService;
	}

	public void setiAreaService(IAreaService iAreaService) {
		this.iAreaService = iAreaService;
	}
	
	public IOrganizationService getiOragnizationService() {
		return iOragnizationService;
	}

	public void setiOragnizationService(IOrganizationService iOragnizationService) {
		this.iOragnizationService = iOragnizationService;
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

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
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
	
	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	public String[] getRestrictRightId() {
		return restrictRightId;
	}

	public String getRestrictRight() {
		return restrictRight;
	}

	public void setRestrictRight(String restrictRight) {
		this.restrictRight = restrictRight;
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
