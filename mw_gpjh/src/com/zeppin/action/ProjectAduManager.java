package com.zeppin.action;

import java.io.*;
import java.util.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.zeppin.model.proChildApply;
import com.zeppin.service.impl.peProApplyNoServiceImpl;
import com.zeppin.service.impl.proChildApplyNoServiceImpl;
import com.zeppin.service.impl.proChildApplyServiceImpl;

@Controller("projectAduManagerAction")
@Scope("prototype")
public class ProjectAduManager extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private peProApplyNoServiceImpl peProApplyNoService;

	@Autowired
	private proChildApplyNoServiceImpl proChildApplyNoService;

	@Autowired
	private proChildApplyServiceImpl proChildApplyService;

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private String provinceId;
	private String provinceName;

	public ProjectAduManager() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	private String subprojectId;
	private String unitId;
	private String subjectId;
	private File fileupload;
	private String fileuploadFileName;

	// 跳转 获取 身份id和身份名称
	public String forward() {

		String userId = request.getParameter("userid");

		session.setAttribute("provinceUserId", userId);
		String sql = " select u.fk_province,pr.name from sso_user s join pe_manager p on s.login_id = p.login_id join pe_unit u on p.fk_unit=u.id join pe_province pr on u.fk_province=pr.id " + "where s.login_id='" + userId + "'";
		List li = peProApplyNoService.executeSQL(sql);
		if (li.size() > 0) {

			Object[] obj = (Object[]) li.get(0);
			String proid = obj[0].toString();
			String proname = obj[1].toString();

			this.provinceId = proid;
			this.provinceName = proname;

			session.setAttribute("provinceId", proid);
			session.setAttribute("provinceName", proname);
		}

		return "forward";
	}

	// 获取 省份所 参与的 父项目
	public void getProjects() {

		String unitId = session.getAttribute("provinceId").toString();

		String sql2 = "select distinct t.parentid,t.parentname from prochildapplyno t where t.provinceid='" + unitId + "'";
		sql2 += " order by t.parentname desc";
		List si = peProApplyNoService.executeSQL(sql2);
		String project = "{\"id\":\"all\",\"name\":\"所有\"},";

		for (int i = 0; i < si.size(); i++) {
			Object[] obj = (Object[]) si.get(i);
			String id = obj[0].toString();
			String name = obj[1].toString();
			project += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			project += ",";
		}

		if (project.length() > 0) {
			project = project.substring(0, project.length() - 1);
		}

		String retJson = "[" + project + "]";

		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(retJson);

		} catch (Exception e) {
		}

	}
	
	/**
	 * 获取16年以前的项目列表
	 */
	public void getOProjects() {

		String unitId = session.getAttribute("provinceId").toString();

		String sql2 = "select distinct t.parentid,t.parentname from prochildapplyno t left join PE_PRO_APPLYNO tt on t.parentid=tt.id where t.provinceid='" + unitId + "'";
		sql2 += " and tt.year in('2008','2009','2010','2011','2012','2013','2014','2015') ";
		sql2 += " order by t.parentname desc";
		List si = peProApplyNoService.executeSQL(sql2);
		String project = "{\"id\":\"all\",\"name\":\"所有\"},";

		for (int i = 0; i < si.size(); i++) {
			Object[] obj = (Object[]) si.get(i);
			String id = obj[0].toString();
			String name = obj[1].toString();
			project += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			project += ",";
		}

		if (project.length() > 0) {
			project = project.substring(0, project.length() - 1);
		}

		String retJson = "[" + project + "]";

		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(retJson);

		} catch (Exception e) {
		}

	}

	// 搜索子项目
	// userid 和大项目id
	public void getChildProjects() {

		String unitId = session.getAttribute("provinceId").toString();

		String projectId = request.getParameter("projectId");

		String sql1 = "select t.id,t.name from prochildapplyno t where t.selectflag=2 and t.provinceid='" + unitId + "' and t.parentid='" + projectId + "'";
		sql1 += " order by t.datetime desc";
		List si = peProApplyNoService.executeSQL(sql1);

		String project = "{\"id\":\"all\",\"name\":\"所有\"},";

		for (int i = 0; i < si.size(); i++) {
			Object[] obj = (Object[]) si.get(i);
			String id = obj[0].toString();
			String name = obj[1].toString();
			project += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			project += ",";
		}

		if (project.length() > 0) {
			project = project.substring(0, project.length() - 1);
		}

		String retJson = "[" + project + "]";

		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(retJson);

		} catch (Exception e) {
		}
	}

	// 获取学科
	public void getSubjects() {

		String sql = "select pe.id,pe.name from pe_subject pe";
		sql += " order by pe.code desc";

		List si = peProApplyNoService.executeSQL(sql);

		String project = "{\"id\":\"all\",\"name\":\"所有\"},";

		for (int i = 0; i < si.size(); i++) {
			Object[] obj = (Object[]) si.get(i);
			String id = obj[0].toString();
			String name = obj[1].toString();
			project += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			project += ",";
		}

		if (project.length() > 0) {
			project = project.substring(0, project.length() - 1);
		}

		String retJson = "[" + project + "]";

		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(retJson);

		} catch (Exception e) {
		}

	}

	// 获取中西部培训单位
	public void getUnits() {

		String unitId = session.getAttribute("provinceId").toString();

		String sql1 = "select t.id,t.name from PE_UNIT t where t.fk_unit_type='ff8080812b493236012b50bfcb6e02cd' and t.Flag_Isvalid=2 and t.fk_province='" + unitId + "'";
		sql1 += " order by t.code desc";
		List si = peProApplyNoService.executeSQL(sql1);

		String project = "{\"id\":\"all\",\"name\":\"所有\"},";

		for (int i = 0; i < si.size(); i++) {
			Object[] obj = (Object[]) si.get(i);
			String id = obj[0].toString();
			String name = obj[1].toString();
			project += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			project += ",";
		}

		if (project.length() > 0) {
			project = project.substring(0, project.length() - 1);
		}

		String retJson = "[" + project + "]";

		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(retJson);

		} catch (Exception e) {
		}
	}

	public void aduUploadFile() {

		try {

			Object isAdu = session.getAttribute("isAdu");

			if (isAdu != null) {
				boolean b = (Boolean) isAdu;
				if (b) {
					// 当前正在提交
					return;
				}
			}
			
			if(this.unitId.equals("all") || this.subjectId.equals("all") || this.subprojectId.equals("all")){
				try {
					response.setCharacterEncoding("UTF-8");
					response.setHeader("Cache-Control", "no-cache");
					response.setContentType("text/html;charset=utf-8");
					response.getWriter().write("子项目、培训单位、培训学科必须要选择！");

				} catch (Exception e) {
				}
				return;
			}

			String hql = "select count(*) from PROCHILDAPPLY t where t.fk_unit='" + this.unitId + "' and t.fk_subject='" + this.subjectId + "' and t.fk_applyno='" + this.subprojectId + "'";
			System.out.println(hql);
			List li = peProApplyNoService.executeSQL(hql);
			if (li != null && li.size() > 0) {
				int flag = Integer.valueOf(li.get(0).toString());
				if (flag > 0) {
					try {
						response.setCharacterEncoding("UTF-8");
						response.setHeader("Cache-Control", "no-cache");
						response.setContentType("text/html;charset=utf-8");
						response.getWriter().write("已经存在提交的申报材料");

					} catch (Exception e) {
					}
					return;
				}
			}
			session.setAttribute("isAdu", true);
			String rootPath = ServletActionContext.getServletContext().getRealPath("");
			String dirPath = request.getContextPath();
			String uploadDir = "/upload/" + subprojectId + "/" + subjectId + "/" + unitId + "/";

			String fileName = rootPath + uploadDir;
			fileName = fileName.replace('/', '\\');
			fileName = fileName.replace('\\', '/');

			File tmpfile = new File(fileName);

			if (!tmpfile.exists()) {
				tmpfile.mkdirs();
			}

			System.out.println("dir:" + fileName);

			FileInputStream fis = new FileInputStream(fileupload);
			FileOutputStream fos = new FileOutputStream(fileName + this.fileuploadFileName);

			byte[] buffer = new byte[1024];
			int flag = fis.read(buffer, 0, buffer.length);

			while (flag > 0) {
				fos.write(buffer, 0, flag);
				flag = fis.read(buffer, 0, buffer.length);
			}

			fis.close();
			fos.close();
//			select * from prochildapply po,prochildapplyno pao where po.fk_applyno=pao.id and pao.provinceid='402880962a2c1d01012a2c1d0f0d0001' and pao.parentname like'%国培计划（2016）中西部乡村教师访名校培训项目%';
			UUID uuid = UUID.randomUUID();
			String vid = uuid.toString().replaceAll("-", "");
			proChildApply peapply = new proChildApply();
			peapply.setId(vid);
			peapply.setUnitId(unitId);
			peapply.setSubjectId(subjectId);
			peapply.setFkApplyNo(subprojectId);
			peapply.setDeclaration(dirPath + uploadDir + this.fileuploadFileName);
			peapply.setSelectFlag(1);
			peapply.setPinfenflag(1);

			proChildApplyService.add(peapply);

			session.setAttribute("isAdu", false);

			try {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=utf-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("申报数据提交成功");
				
				return;

			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("isAdu", false);
				try {
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/html;charset=utf-8");
					response.setHeader("Cache-Control", "no-cache");
					response.getWriter().write("上传材料失败，请稍后再上传!");

				} catch (Exception e1) {
				}
				return;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			session.setAttribute("isAdu", false);
			try {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=utf-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("上传材料失败，请稍后再上传!");

			} catch (Exception e) {
			}
			return;
		}

	}

	public String getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return this.provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getFileuploadFileName() {
		return fileuploadFileName;
	}

	public void setFileuploadFileName(String fileuploadFileName) {
		this.fileuploadFileName = fileuploadFileName;
	}

	public String getSubprojectId() {
		return subprojectId;
	}

	public void setSubprojectId(String subprojectId) {
		this.subprojectId = subprojectId;
	}

	public File getFileupload() {
		return fileupload;
	}

	public void setFileupload(File fileupload) {
		this.fileupload = fileupload;
	}

}
