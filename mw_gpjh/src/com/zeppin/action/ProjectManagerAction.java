package com.zeppin.action;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.zeppin.model.proChildApply;
import com.zeppin.model.proChildApplyNo;
import com.zeppin.service.impl.peProApplyNoServiceImpl;
import com.zeppin.service.impl.proChildApplyNoServiceImpl;
import com.zeppin.service.impl.proChildApplyServiceImpl;

@Controller("projectManagerAction")
@Scope("prototype")
public class ProjectManagerAction extends ActionSupport {

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

	public ProjectManagerAction() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	public String forward() {
		if (session.getAttribute("userid") == null) {
			String userId = request.getParameter("userid");
			session.setAttribute("projectUserId", userId);
		}
		return "forward";
	}

	// 获取省份和 父项目
	public void headsearch() {

		String sql1 = "select distinct t.provinceid,p.name from PROCHILDAPPLYNO t join PE_PROVINCE p on t.provinceid = p.id";
		String sql2 = "select distinct p.id,p.name from prochildapplyno t join PE_PRO_APPLYNO p on t.parentid=p.id ";
		String sql3 = "select distinct t.id,t.name from PE_SUBJECT t";

		sql2+=" order by p.name desc ";
		sql3+=" order by t.code desc";
		List li = peProApplyNoService.executeSQL(sql1);
		String province = "{\"id\":\"all\",\"name\":\"所有\"},";
		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String id = obj[0].toString();
			String name = obj[1].toString();
			province += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			province += ",";
		}

		List si = peProApplyNoService.executeSQL(sql2);
		String project = "{\"id\":\"all\",\"name\":\"所有\"},";
		for (int i = 0; i < si.size(); i++) {
			Object[] obj = (Object[]) si.get(i);
			String id = obj[0].toString();
			String name = obj[1].toString();
			project += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			project += ",";
		}

		List subli = peProApplyNoService.executeSQL(sql3);
		String unit = "{\"id\":\"all\",\"name\":\"所有\"},";
		for (int i = 0; i < subli.size(); i++) {
			Object[] obj = (Object[]) subli.get(i);
			String id = obj[0].toString();
			String name = obj[1].toString();
			unit += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			unit += ",";
		}

		if (province.length() > 0) {
			province = province.substring(0, province.length() - 1);
		}
		if (project.length() > 0) {
			project = project.substring(0, project.length() - 1);
		}
		if (unit.length() > 0) {
			unit = unit.substring(0, unit.length() - 1);
		}

		String retJson = "{\"preInitData\":{\"project\":[" + project + "],\"province\":[" + province + "],\"unit\":[" + unit + "]}}";

		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(retJson);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void list() {

		String provinceId = request.getParameter("provinceName");
		String projectId = request.getParameter("projectName");

		if (provinceId == null) {
			provinceId = "all";
		}
		if (projectId == null) {
			projectId = "all";
		}

		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		String ien = (String) request.getParameter("rows");
		if (ien == null || ien.equals("")) {
			ien = "50";
		}

		int start = Integer.parseInt(ist);
		int limit = 50;
		limit = Integer.parseInt(ien);

		String sqlcount = "select count(*) " + " from prochildapply pa join prochildapplyno po on pa.fk_applyno = po.id " + " join pe_unit u on pa.fk_unit = u.id " + " join pe_subject s on pa.fk_subject = s.id " + " join pe_province p on po.provinceid = p.id " + " where 1=1 ";

		String sql = "select pa.id,p.name as provinceName,po.parentname,po.name as projectName,u.name as unitName,s.name as subjectName " + " from prochildapply pa join prochildapplyno po on pa.fk_applyno = po.id " + " join pe_unit u on pa.fk_unit = u.id "
				+ " join pe_subject s on pa.fk_subject = s.id " + " join pe_province p on po.provinceid = p.id " + " where 1=1 ";

		if (!provinceId.equals("all")) {

			sql += " and p.id='" + provinceId + "' ";
			sqlcount += " and p.id='" + provinceId + "' ";
		}
		if (!projectId.equals("all")) {
			sql += " and po.parentid='" + projectId + "' ";
			sqlcount += " and po.parentid='" + projectId + "' ";
		}

		int totalCount = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
		int tol = totalCount / limit;
		if ((totalCount % limit) > 0) {
			tol += 1;
		}

		List li = peProApplyNoService.getListPage(sql, start, limit);

		String cellString = "";

		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String id = obj[0].toString();
			String provinceName = obj[1].toString();
			String parentName = obj[2].toString();

			String projectName = obj[3].toString();
			String unitName = obj[4].toString();
			String subjectName = obj[5].toString();

			String t = "{\"id\":\"" + id + "\",\"cell\":[\"" + provinceName + "\",\"" + parentName + "\",\"" + projectName + "\",\"" + unitName + "\",\"" + subjectName + "\"]}";

			cellString += t + ",";

		}
		if (cellString.length() > 0) {
			cellString = cellString.substring(0, cellString.length() - 1);
		}

		String retJson = "{\"page\":\"" + start + "\",\"total\":\"" + tol + "\",\"records\":\"" + totalCount + "\",\"rows\":[" + cellString + "]}";

		retJson = retJson.replace("\r", "");
		retJson = retJson.replace("\n", "");
		retJson = retJson.replace("\r\n", "");

		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(retJson);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 审核列表
	public void declareList() {

		String provinceId = request.getParameter("provinceName");
		String projectId = request.getParameter("projectName");
		String subjectName = request.getParameter("subjectname");

		String subProjectName = request.getParameter("subProjectName");
		String unitname = request.getParameter("unitname");

		subProjectName = subProjectName == null ? "" : subProjectName;
		unitname = unitname == null ? "" : unitname;

		if (provinceId == null) {
			provinceId = "all";
		}
		if (projectId == null) {
			projectId = "all";
		}
		if (subjectName == null) {
			subjectName = "all";
		}

		String selectFalg = request.getParameter("selectFlag");
		selectFalg = selectFalg == null ? "2" : selectFalg;

		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		String ien = (String) request.getParameter("rows");
		if (ien == null || ien.equals("")) {
			ien = "50";
		}

		int start = Integer.parseInt(ist);
		int limit = 50;
		limit = Integer.parseInt(ien);

		String sqlcount = "select count(*)  " + "from prochildapply po join prochildapplyno pao on po.fk_applyno=pao.id " + " join pe_subject pe on po.fk_subject=pe.id " + " join pe_unit u on u.id=po.fk_unit " + " join pe_province pr on pao.provinceid=pr.id " + " where 1=1 ";

		String sql = "select po.id,pao.parentname,pao.name as projectname,u.name as unitname,pe.name as subjectname,po.selectflag,po.declaration,po.scheme,pr.name as provinceName  " + "from prochildapply po join prochildapplyno pao on po.fk_applyno=pao.id "
				+ " join pe_subject pe on po.fk_subject=pe.id " + " join pe_unit u on u.id=po.fk_unit " + " join pe_province pr on pao.provinceid=pr.id " + " where 1=1 ";

		if (!provinceId.equals("all")) {

			sql += " and pr.id='" + provinceId + "' ";
			sqlcount += " and pr.id='" + provinceId + "' ";
		}
		if (!projectId.equals("all")) {
			sql += " and pao.parentid='" + projectId + "' ";
			sqlcount += " and pao.parentid='" + projectId + "' ";
		}
		if (!subjectName.equals("all")) {
			sql += " and pe.id='" + subjectName + "' ";
			sqlcount += " and pe.id='" + subjectName + "' ";
		}

		if (!subProjectName.equals("")) {
			sql += " and pao.name like'%" + subProjectName + "%' ";
			sqlcount += " and pao.name like'%" + subProjectName + "%' ";
		}
		if (!unitname.equals("")) {
			sql += " and u.name like'%" + unitname + "%' ";
			sqlcount += " and u.name like'%" + unitname + "%' ";
		}

		if (!selectFalg.equals("0")) {
			sql += " and po.selectflag=" + selectFalg;
			sqlcount += " and po.selectflag=" + selectFalg;
		}

		int totalCount = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
		int tol = totalCount / limit;
		if ((totalCount % limit) > 0) {
			tol += 1;
		}

		List li = peProApplyNoService.getListPage(sql, start, limit);

		String cellString = "";

		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);

			String id = obj[0].toString();
			String parentName = obj[1].toString();
			String name = obj[2].toString();

			String tunitname = obj[3].toString();
			String subjectname = obj[4].toString();
			String st = obj[5].toString();
			if (st.equals("1")) {
				st = "未审核";
			} else if (st.equals("2")) {
				st = "通过";
			} else if (st.equals("3")) {
				st = "不通过";
			} else if (st.equals("4")) {
				st = "正在评分";
			}
			String declare = obj[6] == null ? "" : obj[6].toString();
			String scheme = obj[7] == null ? "" : obj[7].toString();

			String provinceName = obj[8].toString();

			String t = "{\"id\":\"" + id + "\",\"cell\":[\"" + provinceName + "\",\"" + parentName + "\",\"" + name + "\",\"" + tunitname + "\",\"" + subjectname + "\",\"" + st + "\",\"" + declare + "\",\"" + scheme + "\"]}";

			cellString += t + ",";

		}
		if (cellString.length() > 0) {
			cellString = cellString.substring(0, cellString.length() - 1);
		}

		String retJson = "{\"page\":\"" + start + "\",\"total\":\"" + tol + "\",\"records\":\"" + totalCount + "\",\"rows\":[" + cellString + "]}";

		retJson = retJson.replace("\r", "");
		retJson = retJson.replace("\n", "");
		retJson = retJson.replace("\r\n", "");

		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(retJson);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 专拣审核之后，管理员审核
	public void aduDeclareFirstList() {

		String provinceId = request.getParameter("provinceName");
		String projectId = request.getParameter("projectName");
		String subjectId = request.getParameter("subjectname");

		String subProjectName = request.getParameter("subProjectName");
		String unitname = request.getParameter("unitname");

		subProjectName = subProjectName == null ? "" : subProjectName;
		unitname = unitname == null ? "" : unitname;

		if (provinceId == null) {
			provinceId = "all";
		}
		if (projectId == null) {
			projectId = "all";
		}
		if (subjectId == null) {
			subjectId = "all";
		}

		String selectFalg = request.getParameter("adminSelectFlag");
		selectFalg = selectFalg == null ? "0" : selectFalg;

		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		String ien = (String) request.getParameter("rows");
		if (ien == null || ien.equals("")) {
			ien = "50";
		}

		int start = Integer.parseInt(ist);
		int limit = 50;
		limit = Integer.parseInt(ien);

		String sqlcount = "select count(*) " + "from prochildapply po join prochildapplyno pao on po.fk_applyno=pao.id " + " join pe_subject pe on po.fk_subject=pe.id " + " join pe_unit u on u.id=po.fk_unit " + " join pe_province pr on pao.provinceid=pr.id " + " where 1=1 ";

		String sql = "select po.id,pao.parentname,pao.name as projectname,u.name as unitname,pe.name as subjectname" + ",po.declaration,po.scheme,pr.name as provinceName,po.selectflag as adminstatus,to_char(po.datetime,'yyyy-MM-dd HH24:mi:ss') as createtime  "
				+ "from prochildapply po join prochildapplyno pao on po.fk_applyno=pao.id " + " join pe_subject pe on po.fk_subject=pe.id " + " join pe_unit u on u.id=po.fk_unit " + " join pe_province pr on pao.provinceid=pr.id " + " where 1=1 ";

		if (!provinceId.equals("all")) {
			sql += " and pr.id='" + provinceId + "' ";
			sqlcount += " and pr.id='" + provinceId + "' ";
		}
		if (!projectId.equals("all")) {
			sql += " and pao.parentid='" + projectId + "' ";
			sqlcount += " and pao.parentid='" + projectId + "' ";
		}
		if (!subjectId.equals("all")) {
			sql += " and po.fk_subject='" + subjectId + "' ";
			sqlcount += " and po.fk_subject='" + subjectId + "' ";
		}
		if (!subProjectName.equals("")) {
			sql += " and pao.name like'%" + subProjectName + "%' ";
			sqlcount += " and pao.name like'%" + subProjectName + "%' ";
		}
		if (!unitname.equals("")) {
			sql += " and u.name like'%" + unitname + "%' ";
			sqlcount += " and u.name like'%" + unitname + "%' ";
		}

		if (!selectFalg.equals("0")) {
			sql += " and po.selectflag=" + selectFalg;
			sqlcount += " and po.selectflag=" + selectFalg;
		} else {
			sql += " and po.selectflag in (1,2,3)";
			sqlcount += " and po.selectflag in (1,2,3)";
		}

		int totalCount = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
		int tol = totalCount / limit;
		if ((totalCount % limit) > 0) {
			tol += 1;
		}

		session.setAttribute("aduDeclareFirstList", sql);

		List li = peProApplyNoService.getListPage(sql, start, limit);

		String cellString = "";

		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);

			String id = obj[0].toString();
			String parentName = obj[1].toString();
			String name = obj[2].toString();

			String tunitname = obj[3].toString();
			String subjectname = obj[4].toString();

			String declare = obj[5] == null ? "" : obj[5].toString();
			String scheme = obj[6] == null ? "" : obj[6].toString();

			String provinceName = obj[7].toString();

			String adminst = obj[8].toString();
			if (adminst.equals("1")) {
				adminst = "未审核";
			} else if (adminst.equals("2")) {
				adminst = "通过";
			} else if (adminst.equals("3")) {
				adminst = "不通过";
			}

			String time = obj[9] == null ? "" : obj[9].toString();

			String t = "{\"id\":\"" + id + "\",\"cell\":[\"" + provinceName + "\",\"" + parentName + "\",\"" + name + "\",\"" + tunitname + "\",\"" + subjectname + "\",\"" + declare + "\",\"" + scheme + "\",\"" + adminst + "\",\"" + time + "\"]}";

			cellString += t + ",";

		}
		if (cellString.length() > 0) {
			cellString = cellString.substring(0, cellString.length() - 1);
		}

		String retJson = "{\"page\":\"" + start + "\",\"total\":\"" + tol + "\",\"records\":\"" + totalCount + "\",\"rows\":[" + cellString + "]}";

		retJson = retJson.replace("\r", "");
		retJson = retJson.replace("\n", "");
		retJson = retJson.replace("\r\n", "");

		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(retJson);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void importaduDeclareFirstList() {

		String sql = session.getAttribute("aduDeclareFirstList").toString();
		List li = peProApplyNoService.executeSQL(sql);

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet("sheet1");
		int i = 0;
		HSSFRow rowheader = sheet1.createRow((short) i);
		i++;

		rowheader.createCell((short) 0).setCellValue("省份");
		rowheader.createCell((short) 1).setCellValue("所属项目");
		rowheader.createCell((short) 2).setCellValue("项目名称");
		rowheader.createCell((short) 3).setCellValue("培训单位");
		rowheader.createCell((short) 4).setCellValue("培训学科");
		rowheader.createCell((short) 5).setCellValue("状态");
		rowheader.createCell((short) 6).setCellValue("时间");
		for (int ti = 0; ti < li.size(); ti++) {
			Object[] obj = (Object[]) li.get(ti);

			HSSFRow row = sheet1.createRow((short) i);
			i++;

			String parentName = obj[1].toString();
			String name = obj[2].toString();
			String tunitname = obj[3].toString();
			String subjectname = obj[4].toString();
			String declare = obj[5] == null ? "" : obj[5].toString();
			String scheme = obj[6] == null ? "" : obj[6].toString();

			String provinceName = obj[7].toString();
			String adminst = obj[8].toString();
			if (adminst.equals("1")) {
				adminst = "未审核";
			} else if (adminst.equals("2")) {
				adminst = "通过";
			} else if (adminst.equals("3")) {
				adminst = "不通过";
			}

			String time = obj[9] == null ? "" : obj[9].toString();

			row.createCell((short) 0).setCellValue(provinceName);
			row.createCell((short) 1).setCellValue(parentName);
			row.createCell((short) 2).setCellValue(name);
			row.createCell((short) 3).setCellValue(tunitname);
			row.createCell((short) 4).setCellValue(subjectname);
			row.createCell((short) 5).setCellValue(adminst);
			row.createCell((short) 6).setCellValue(time);

		}

		try {
			String filename = "申报材料管理导出";
			filename = new String(filename.getBytes("GBK"), "ISO8859-1");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xls");
			response.setCharacterEncoding("UTF-8");

			OutputStream os = response.getOutputStream();
			os.flush();
			wb.write(os);
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void aduDeclareFirstMethod() {
		String method = request.getParameter("method");
		String projectIds = request.getParameter("pId");
		String[] listpids = projectIds.split(",");
		if (method.equals("adu")) {

			for (String s : listpids) {
				proChildApply pcapp = proChildApplyService.get(s);
				if (pcapp != null) {
					pcapp.setSelectFlag(2);
					proChildApplyService.update(pcapp);
				}
			}

			try {

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("true");

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (method.equals("noadu")) {
			for (String s : listpids) {
				proChildApply pcapp = proChildApplyService.get(s);
				if (pcapp != null) {
					pcapp.setSelectFlag(3);
					proChildApplyService.update(pcapp);
				}
			}

			try {

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("true");

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	// 专拣审核之后，管理员审核
	public void aduDeclareList() {

		String provinceId = request.getParameter("provinceName");
		String projectId = request.getParameter("projectName");
		String subjectId = request.getParameter("subjectname");

		subjectId = subjectId == null ? "all" : subjectId;

		String subProjectName = request.getParameter("subProjectName");

		String unitname = request.getParameter("unitname");

		subProjectName = subProjectName == null ? "" : subProjectName;
		unitname = unitname == null ? "" : unitname;

		String pinfenzhuangtai = request.getParameter("pinfenzhuangtai");
		pinfenzhuangtai = pinfenzhuangtai == null ? "3" : pinfenzhuangtai;

		String pinshengzhuangtai = request.getParameter("pinshengzhuangtai");
		pinshengzhuangtai = pinshengzhuangtai == null ? "0" : pinshengzhuangtai;

		if (provinceId == null) {
			provinceId = "all";
		}
		if (projectId == null) {
			projectId = "all";
		}

		String selectFalg = request.getParameter("adminSelectFlag");
		selectFalg = selectFalg == null ? "0" : selectFalg;

		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		String ien = (String) request.getParameter("rows");
		if (ien == null || ien.equals("")) {
			ien = "50";
		}

		int start = Integer.parseInt(ist);
		int limit = 50;
		limit = Integer.parseInt(ien);

		String sqlcount = "select count(distinct po.id) " + " from PROCHILDEXPERT ex ,PROCHILDAPPLY po,PROCHILDAPPLYNO pao,PE_SUBJECT pe,PE_UNIT u,pe_province pr " + " where ex.fk_progame=po.id and po.fk_subject=pe.id and po.fk_unit=u.id and po.fk_applyno=pao.id and pao.provinceid=pr.id  ";

		String sql = "select po.id,pr.name as provinceName,pao.parentname,pao.name as subName,pe.name as subjectName,u.name as unitName,po.declaration,po.scheme,po.selectflag,po.pinflag,po.pinsheng,avg(ex.result) "
				+ " from PROCHILDEXPERT ex ,PROCHILDAPPLY po,PROCHILDAPPLYNO pao,PE_SUBJECT pe,PE_UNIT u,pe_province pr " + " where ex.fk_progame=po.id and po.fk_subject=pe.id and po.fk_unit=u.id and po.fk_applyno=pao.id and pao.provinceid=pr.id  ";

		String infoSql = "select pr.name as provinceName,pao.parentname,pao.name as subName ," + " u.name as unitName ,pe.name as subjectName,p.name as usname,ex.result,to_char(ex.note) ";
		infoSql += "from pe_valua_expert p, PROCHILDEXPERT ex ,PROCHILDAPPLY po,PROCHILDAPPLYNO pao,PE_SUBJECT pe,PE_UNIT u,pe_province pr ";

		infoSql += "where ex.fk_value_expert=p.id and ex.fk_progame=po.id " + "and po.fk_subject=pe.id and po.fk_unit=u.id and po.fk_applyno=pao.id and pao.provinceid=pr.id ";

		if (!provinceId.equals("all")) {
			sql += " and pr.id='" + provinceId + "' ";
			sqlcount += " and pr.id='" + provinceId + "' ";
			infoSql += " and pr.id='" + provinceId + "' ";
		}
		if (!projectId.equals("all")) {
			sql += " and pao.parentid='" + projectId + "' ";
			sqlcount += " and pao.parentid='" + projectId + "' ";
			infoSql += " and pao.parentid='" + projectId + "' ";
		}
		if (!subjectId.equals("all")) {
			sql += " and po.fk_subject='" + subjectId + "' ";
			sqlcount += " and po.fk_subject='" + subjectId + "' ";
			infoSql += " and po.fk_subject='" + subjectId + "' ";
		}

		if (!subProjectName.equals("")) {
			sql += " and pao.name like'%" + subProjectName + "%' ";
			sqlcount += " and pao.name like'%" + subProjectName + "%' ";
			infoSql += " and pao.name like'%" + subProjectName + "%' ";
		}
		if (!unitname.equals("")) {
			sql += " and u.name like'%" + unitname + "%' ";
			sqlcount += " and u.name like'%" + unitname + "%' ";
			infoSql += " and u.name like'%" + unitname + "%' ";
		}

		if (!selectFalg.equals("0")) {
			sql += " and po.selectflag=" + selectFalg;
			sqlcount += " and po.selectflag=" + selectFalg;
			infoSql += " and po.selectflag=" + selectFalg;
		}

		if (!pinfenzhuangtai.equals("3")) {
			sql += " and po.pinflag=" + pinfenzhuangtai;
			sqlcount += " and po.pinflag=" + pinfenzhuangtai;
			infoSql += " and po.pinflag=" + pinfenzhuangtai;
		}

		if (!pinshengzhuangtai.equals("0")) {
			sql += " and po.pinsheng=" + pinshengzhuangtai;
			sqlcount += " and po.pinsheng=" + pinshengzhuangtai;
			infoSql += " and po.pinsheng=" + pinshengzhuangtai;
		}

		sql += " group by po.id,pr.name,pao.parentname,pao.name,pe.name,u.name,po.scheme,po.declaration,po.selectflag,po.pinflag,po.pinsheng";

		int totalCount = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
		int tol = totalCount / limit;
		if ((totalCount % limit) > 0) {
			tol += 1;
		}

		session.setAttribute("aduDeclareList", sql);
		session.setAttribute("aduDeclareListInfo", infoSql);

		List li = peProApplyNoService.getListPage(sql, start, limit);

		String cellString = "";

		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);

			String id = obj[0].toString();
			String provinceName = obj[1].toString();
			String parentName = obj[2].toString();
			parentName = parentName.replaceAll("\"", "");
			parentName = parentName.replaceAll("\t", "");
			String name = obj[3].toString();
			name = name.replaceAll("\"", "");
			name = name.replaceAll("\t", "");

			String subjectname = obj[4].toString();
			String tunitname = obj[5].toString();

			String declare = obj[6] == null ? "" : obj[6].toString();
			String scheme = obj[7] == null ? "" : obj[7].toString();

			String adminst = obj[8].toString();
			if (adminst.equals("2")) {
				adminst = "未审核";
			} else if (adminst.equals("4")) {
				adminst = "通过";
			} else if (adminst.equals("5")) {
				adminst = "不通过";
			}

			String dafenzhuangtai = obj[9].toString().equals("1") ? "可评分" : "不可评分";
			String pinszhuangtai = obj[10].toString().equals("2") ? "发布" : "停止";
			String dafen = obj[11] == null ? "" : obj[11].toString();

			String t = "{\"id\":\"" + id + "\",\"cell\":[\"" + provinceName + "\",\"" + parentName + "\",\"" + name + "\",\"" + tunitname + "\",\"" + subjectname + "\",\"" + declare + "\",\"" + scheme + "\",\"" + dafenzhuangtai + "\",\"" + pinszhuangtai + "\",\"" + adminst + "\",\"" + dafen
					+ "\"]}";

			cellString += t + ",";

		}
		if (cellString.length() > 0) {
			cellString = cellString.substring(0, cellString.length() - 1);
		}

		String retJson = "{\"page\":\"" + start + "\",\"total\":\"" + tol + "\",\"records\":\"" + totalCount + "\",\"rows\":[" + cellString + "]}";

		retJson = retJson.replace("\r", "");
		retJson = retJson.replace("\n", "");
		retJson = retJson.replace("\r\n", "");

		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(retJson);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void importaduDeclareList() {

		String sql = session.getAttribute("aduDeclareList").toString();
		List li = peProApplyNoService.executeSQL(sql);

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet("sheet1");
		int i = 0;
		HSSFRow rowheader = sheet1.createRow((short) i);
		i++;

		rowheader.createCell((short) 0).setCellValue("省份");
		rowheader.createCell((short) 1).setCellValue("所属项目");
		rowheader.createCell((short) 2).setCellValue("项目名称");
		rowheader.createCell((short) 3).setCellValue("培训单位");
		rowheader.createCell((short) 4).setCellValue("培训学科");

		rowheader.createCell((short) 5).setCellValue("评分状态");
		rowheader.createCell((short) 6).setCellValue("管理员最终审核");
		rowheader.createCell((short) 7).setCellValue("专家打分");
		rowheader.createCell((short) 8).setCellValue("评审状态");

		for (int ti = 0; ti < li.size(); ti++) {
			Object[] obj = (Object[]) li.get(ti);

			HSSFRow row = sheet1.createRow((short) i);
			i++;

			String provinceName = obj[1].toString();
			String parentName = obj[2].toString();
			String name = obj[3].toString();
			String subjectname = obj[4].toString();
			String tunitname = obj[5].toString();

			String adminst = obj[8].toString();
			if (adminst.equals("2")) {
				adminst = "未审核";
			} else if (adminst.equals("4")) {
				adminst = "通过";
			} else if (adminst.equals("5")) {
				adminst = "不通过";
			}

			String dafenzhuangtai = obj[9].toString().equals("1") ? "可评分" : "不可评分";
			String pinszhuangtai = obj[10].toString().equals("2") ? "发布" : "停止";

			String dafen = obj[11] == null ? "" : obj[11].toString();

			System.out.println("dd:"+dafen);

			row.createCell((short) 0).setCellValue(provinceName);
			row.createCell((short) 1).setCellValue(parentName);
			row.createCell((short) 2).setCellValue(name);
			row.createCell((short) 3).setCellValue(tunitname);
			row.createCell((short) 4).setCellValue(subjectname);
			row.createCell((short) 5).setCellValue(dafenzhuangtai);
			row.createCell((short) 6).setCellValue(adminst);
			row.createCell((short) 7).setCellValue(dafen);
			row.createCell((short) 8).setCellValue(pinszhuangtai);

		}

		try {
			String filename = "管理员审核";
			filename = new String(filename.getBytes("GBK"), "ISO8859-1");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xls");
			response.setCharacterEncoding("UTF-8");

			OutputStream os = response.getOutputStream();
			os.flush();
			wb.write(os);
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void importaduDeclareListInfo() {

		String sql = session.getAttribute("aduDeclareListInfo").toString();
		List li = peProApplyNoService.executeSQL(sql);

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet("sheet1");
		int i = 0;
		HSSFRow rowheader = sheet1.createRow((short) i);
		i++;

		rowheader.createCell((short) 0).setCellValue("省份");
		rowheader.createCell((short) 1).setCellValue("所属项目");
		rowheader.createCell((short) 2).setCellValue("项目名称");
		rowheader.createCell((short) 3).setCellValue("培训单位");
		rowheader.createCell((short) 4).setCellValue("培训学科");

		rowheader.createCell((short) 5).setCellValue("专家姓名");
		rowheader.createCell((short) 6).setCellValue("专家打分结果");
		rowheader.createCell((short) 7).setCellValue("专家打分意见");

		for (int ti = 0; ti < li.size(); ti++) {
			Object[] obj = (Object[]) li.get(ti);

			HSSFRow row = sheet1.createRow((short) i);
			i++;

			row.createCell((short) 0).setCellValue(obj[0] == null ? "" : obj[0].toString());
			row.createCell((short) 1).setCellValue(obj[1] == null ? "" : obj[1].toString());
			row.createCell((short) 2).setCellValue(obj[2] == null ? "" : obj[2].toString());
			row.createCell((short) 3).setCellValue(obj[3] == null ? "" : obj[3].toString());
			row.createCell((short) 4).setCellValue(obj[4] == null ? "" : obj[4].toString());
			row.createCell((short) 5).setCellValue(obj[5] == null ? "" : obj[5].toString());
			row.createCell((short) 6).setCellValue(obj[6] == null ? "" : obj[6].toString());
			row.createCell((short) 7).setCellValue(obj[7] == null ? "" : obj[7].toString());

		}

		try {
			String filename = "评审详细信息";
			filename = new String(filename.getBytes("GBK"), "ISO8859-1");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xls");
			response.setCharacterEncoding("UTF-8");

			OutputStream os = response.getOutputStream();
			os.flush();
			wb.write(os);
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void aduDeclareMethod() {
		String method = request.getParameter("method");
		String projectIds = request.getParameter("pId");
		String[] listpids = projectIds.split(",");
		if (method.equals("adu")) {

			for (String s : listpids) {
				proChildApply pcapp = proChildApplyService.get(s);
				if (pcapp != null) {
					pcapp.setSelectFlag(4);
					proChildApplyService.update(pcapp);
				}
			}

			try {

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("true");

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (method.equals("noadu")) {
			for (String s : listpids) {
				proChildApply pcapp = proChildApplyService.get(s);
				if (pcapp != null) {
					pcapp.setSelectFlag(5);
					proChildApplyService.update(pcapp);
				}
			}

			try {

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("true");

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void pinfenDeclareMetod() {
		String method = request.getParameter("method");
		String projectIds = request.getParameter("pId");
		String[] listpids = projectIds.split(",");
		if (method.equals("pinfen")) {

			for (String s : listpids) {
				proChildApply pcapp = proChildApplyService.get(s);
				if (pcapp != null) {
					pcapp.setPinfenflag(1);
					proChildApplyService.update(pcapp);
				}
			}

			try {

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("true");

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (method.equals("nopinfen")) {
			for (String s : listpids) {
				proChildApply pcapp = proChildApplyService.get(s);
				if (pcapp != null) {
					pcapp.setPinfenflag(0);
					proChildApplyService.update(pcapp);
				}
			}

			try {

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("true");

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void pinshengDeclareMetod() {
		String method = request.getParameter("method");
		String projectIds = request.getParameter("pId");
		String[] listpids = projectIds.split(",");
		if (method.equals("pinsheng")) {

			for (String s : listpids) {
				proChildApply pcapp = proChildApplyService.get(s);
				if (pcapp != null) {
					pcapp.setPinsheng(2);
					proChildApplyService.update(pcapp);
				}
			}

			try {

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("true");

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (method.equals("nopinsheng")) {
			for (String s : listpids) {
				proChildApply pcapp = proChildApplyService.get(s);
				if (pcapp != null) {
					pcapp.setPinsheng(1);
					proChildApplyService.update(pcapp);
				}
			}

			try {

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("true");

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	// 子项目审核列表
	public void aduList() {

		String projectName = request.getParameter("projectName");
		projectName = projectName == null ? "all" : projectName;

		String provinceName = request.getParameter("provinceName");
		provinceName = provinceName == null ? "all" : provinceName;

		String subprojectName = request.getParameter("subProjectName");
		subprojectName = subprojectName == null ? "" : subprojectName;

		String selectFalg = request.getParameter("selectFlag");
		selectFalg = selectFalg == null ? "0" : selectFalg;

		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		String ien = (String) request.getParameter("rows");
		if (ien == null || ien.equals("")) {
			ien = "50";
		}

		int start = Integer.parseInt(ist);
		int limit = 50;
		limit = Integer.parseInt(ien);

		String sqlcount = "select count(*) " + " from prochildapplyno po join pe_province pr on po.provinceid = pr.id" + " join enum_const e on po.fk_program_status=e.id where 1=1 ";

		String sql = "select po.id,pr.name as provinename, p.name,po.code,po.year,po.name as projectname ,e.name as statusname,po.selectflag,to_char(po.datetime,'yyyy-MM-dd HH24:mi:ss') as createtime " + " from prochildapplyno po join pe_province pr on po.provinceid = pr.id"
				+ "  join PE_PRO_APPLYNO p on po.parentid=p.id  "
				+ " join enum_const e on po.fk_program_status=e.id where 1=1 ";

		if (!subprojectName.equals("")) {
			sql += " and po.name like'%" + subprojectName + "%' ";
			sqlcount += " and po.name like'%" + subprojectName + "%' ";
		}

		if (!provinceName.equals("all")) {

			sql += " and pr.id='" + provinceName + "' ";
			sqlcount += " and pr.id='" + provinceName + "' ";
		}
		if (!projectName.equals("all")) {
			sql += " and po.parentid='" + projectName + "' ";
			sqlcount += " and po.parentid='" + projectName + "' ";
		}

		if (!selectFalg.equals("0")) {
			sql += " and po.selectflag=" + selectFalg;
			sqlcount += " and po.selectflag=" + selectFalg;
		}
//		sql += " order by createtime desc";

		int totalCount = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
		int tol = totalCount / limit;
		if ((totalCount % limit) > 0) {
			tol += 1;
		}

		// 导出数据用
		session.setAttribute("aduList", sql);

		List li = peProApplyNoService.getListPage(sql, start, limit);

		String cellString = "";

		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String id = obj[0].toString();
			String provname = obj[1].toString();
			String parentName = obj[2].toString();

			String code = obj[3] == null ? "" : obj[3].toString();
			// String year = obj[4].toString();
			String projname = obj[5] == null ? "" : obj[5].toString();
			projname = projname.replaceAll("\"", "");
			projname = projname.replaceAll("\t", "");
			String statusname = obj[6] == null ? "" : obj[6].toString();
			String st = obj[7] == null ? "" : obj[7].toString();
			if (st.equals("1")) {
				st = "未审核";
			} else if (st.equals("2")) {
				st = "通过";
			} else if (st.equals("3")) {
				st = "不通过";
			}
			String time = obj[8] == null ? "" : obj[8].toString();

			String t = "{\"id\":\"" + id + "\",\"cell\":[\"" + provname + "\",\"" + parentName + "\",\"" + code + "\",\"" + projname + "\",\"" + statusname + "\",\"" + time + "\",\"" + st + "\"]}";

			cellString += t + ",";

		}
		if (cellString.length() > 0) {
			cellString = cellString.substring(0, cellString.length() - 1);
		}

		String retJson = "{\"page\":\"" + start + "\",\"total\":\"" + tol + "\",\"records\":\"" + totalCount + "\",\"rows\":[" + cellString + "]}";

		retJson = retJson.replace("\r", "");
		retJson = retJson.replace("\n", "");
		retJson = retJson.replace("\r\n", "");

		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(retJson);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void ImportExcelAduList() {

		String sql = session.getAttribute("aduList").toString();
		List li = peProApplyNoService.executeSQL(sql);

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet("sheet1");
		int i = 0;
		HSSFRow rowheader = sheet1.createRow((short) i);
		i++;

		rowheader.createCell((short) 0).setCellValue("省份");
		rowheader.createCell((short) 1).setCellValue("所属项目");
		rowheader.createCell((short) 2).setCellValue("项目编号");
		rowheader.createCell((short) 3).setCellValue("项目名称");
		rowheader.createCell((short) 4).setCellValue("活动状态");
		rowheader.createCell((short) 5).setCellValue("时间");
		rowheader.createCell((short) 6).setCellValue("审核状态");
		for (int ti = 0; ti < li.size(); ti++) {
			Object[] obj = (Object[]) li.get(ti);

			HSSFRow row = sheet1.createRow((short) i);
			i++;

			String provname = obj[1].toString();
			String parentName = obj[2].toString();
			String code = obj[3] == null ? "" : obj[3].toString();
			String projname = obj[5] == null ? "" : obj[5].toString();
			String statusname = obj[6] == null ? "" : obj[6].toString();
			String st = obj[7] == null ? "" : obj[7].toString();
			if (st.equals("1")) {
				st = "未审核";
			} else if (st.equals("2")) {
				st = "通过";
			} else if (st.equals("3")) {
				st = "不通过";
			}
			String time = obj[8] == null ? "" : obj[8].toString();

			row.createCell((short) 0).setCellValue(provname);
			row.createCell((short) 1).setCellValue(parentName);
			row.createCell((short) 2).setCellValue(code);
			row.createCell((short) 3).setCellValue(projname);
			row.createCell((short) 4).setCellValue(statusname);
			row.createCell((short) 5).setCellValue(time);
			row.createCell((short) 6).setCellValue(st);

		}

		try {
			String filename = "子项目管理导出";
			filename = new String(filename.getBytes("GBK"), "ISO8859-1");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xls");
			response.setCharacterEncoding("UTF-8");

			OutputStream os = response.getOutputStream();
			os.flush();
			wb.write(os);
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 子项目审核接口
	public void proChildAdu() {

		String method = request.getParameter("method");
		String projectIds = request.getParameter("pId");
		String[] listpids = projectIds.split(",");
		if (method.equals("adu")) {

			for (String s : listpids) {
				proChildApplyNo pcapp = proChildApplyNoService.get(s);
				// if (pcapp.getSelectFlag() == 1) {
				if (pcapp != null) {
					pcapp.setSelectFlag(2);
					proChildApplyNoService.update(pcapp);
				}
				// }
			}

			try {

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("true");

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (method.equals("noadu")) {
			for (String s : listpids) {
				proChildApplyNo pcapp = proChildApplyNoService.get(s);
				// if (pcapp.getSelectFlag() == 1) {
				if (pcapp != null) {
					pcapp.setSelectFlag(3);
					proChildApplyNoService.update(pcapp);
				}
				// }
			}

			try {

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("true");

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	// 获取父项目 4028088145630dab0145631145200001
	public void getParents() {
		String sql2 = "select distinct t.id,t.name from PE_PRO_APPLYNO t ,ENUM_CONST e where 1=1 and t.fk_program_type = e.id and e.id='ff808081458798ad0145879c93380001'";
		List si = peProApplyNoService.executeSQL(sql2);
		String project = "";
		;
		for (int i = 0; i < si.size(); i++) {
			try {
				Object[] obj = (Object[]) si.get(i);
				String id = obj[0].toString();
				String name = obj[1].toString();
				project += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
				project += ",";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (project.length() > 0) {
			project = project.substring(0, project.length() - 1);
		}
		project = "[" + project + "]";

		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(project);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 项目管理头
	public void proChildHeader() {

		String userId = request.getParameter("userid");

		session.setAttribute("unitUserId", userId);
		String sql = "select u.fk_province from sso_user s join pe_manager p on s.login_id = p.login_id join pe_unit u on p.fk_unit=u.id " + "where s.login_id='" + userId + "'";
		List li = peProApplyNoService.executeSQL(sql);
		String unitId = "";
		if (li.size() > 0) {
			unitId = li.get(0).toString();
			session.setAttribute("unitNormalId", unitId);
		}

		String sql2 = "select distinct t.parentid,t.parentname from prochildapplyno t where t.provinceid='" + unitId + "'";

		List si = peProApplyNoService.executeSQL(sql2);
		String project = "{\"id\":\"all\",\"name\":\"所有\"},";
		for (int i = 0; i < si.size(); i++) {
			Object[] obj = (Object[]) si.get(i);
			String id = obj[0] == null ? "" : obj[0].toString();
			String name = obj[1] == null ? "" : obj[1].toString();
			project += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			project += ",";
		}

		if (project.length() > 0) {
			project = project.substring(0, project.length() - 1);
		}

		String sql3 = "select distinct t.id,t.name from PE_SUBJECT t";
		List subli = peProApplyNoService.executeSQL(sql3);
		String unit = "{\"id\":\"all\",\"name\":\"所有\"},";
		for (int i = 0; i < subli.size(); i++) {
			Object[] obj = (Object[]) subli.get(i);
			String id = obj[0].toString();
			String name = obj[1].toString();
			unit += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			unit += ",";
		}

		if (unit.length() > 0) {
			unit = unit.substring(0, unit.length() - 1);
		}

		String retJson = "{\"preInitData\":{\"project\":[" + project + "],\"unit\":[" + unit + "]}}";

		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(retJson);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 子项目列表
	public void proChildList() {

		String userId = request.getParameter("userid");

		String projectName = request.getParameter("projectName");
		projectName = projectName == null ? "all" : projectName;

		String selectFalg = request.getParameter("selectFlag");
		selectFalg = selectFalg == null ? "0" : selectFalg;

		String subProjectName = request.getParameter("subProjectName");
		subProjectName = subProjectName == null ? "" : subProjectName;

		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		String ien = (String) request.getParameter("rows");
		if (ien == null || ien.equals("")) {
			ien = "50";
		}

		int start = Integer.parseInt(ist);
		int limit = 50;
		limit = Integer.parseInt(ien);

		session.setAttribute("unitUserId", userId);
		String sql = "select u.fk_province from sso_user s join pe_manager p on s.login_id = p.login_id join pe_unit u on p.fk_unit=u.id " + "where s.login_id='" + userId + "'";
		List li = peProApplyNoService.executeSQL(sql);
		String unitId = "";
		if (li.size() > 0) {
			unitId = li.get(0).toString();
			session.setAttribute("unitNormalId", unitId);
		}

		String searchSql = "select po.id,po.parentname,po.name as projectname,po.code,po.year,e.name as statusname ,po.selectflag,pr.name as provincename,to_char(po.datetime,'yyyy-MM-dd HH24:mi:ss') as createtime" + " from prochildapplyno po join enum_const e on po.fk_program_status=e.id "
				+ " join pe_province pr on po.provinceid=pr.id " + " where po.provinceid='" + unitId + "'";

		String searchSqlCount = "select count(*) " + " from prochildapplyno po join enum_const e on po.fk_program_status=e.id " + " join pe_province pr on po.provinceid=pr.id " + " where po.provinceid='" + unitId + "'";

		if (!projectName.equals("all")) {
			searchSql += " and po.parentid='" + projectName + "'";
			searchSqlCount += " and po.parentid='" + projectName + "'";
		}
		if (!selectFalg.equals("0")) {
			searchSql += " and po.selectflag=" + selectFalg;
			searchSqlCount += " and po.selectflag=" + selectFalg;
		}

		if (!subProjectName.equals("")) {
			searchSql += " and po.name like '%" + subProjectName + "%' ";
			searchSqlCount += " and po.name like '%" + subProjectName + "%' ";
		}

		int totalCount = Integer.parseInt(peProApplyNoService.executeSQL(searchSqlCount).get(0).toString());
		int tol = totalCount / limit;
		if ((totalCount % limit) > 0) {
			tol += 1;
		}

		session.setAttribute("proChildList", searchSql);

		li = peProApplyNoService.getListPage(searchSql, start, limit);

		String cellString = "";

		for (int i = 0; i < li.size(); i++) {
			try {
				Object[] obj = (Object[]) li.get(i);
				String id = obj[0] == null ? "" : obj[0].toString();
				String parentName = obj[1] == null ? "" : obj[1].toString();
				String name = obj[2] == null ? "" : obj[2].toString();
				if(name.length()>0){
					name = name.replaceAll("\"", "\\\\\"");
				}
				String code = obj[3] == null ? "" : obj[3].toString();
				// String year = obj[4].toString();
				String statusname = obj[5] == null ? "" : obj[5].toString();
				String st = obj[6] == null ? "" : obj[6].toString();
				String provinceName = obj[7] == null ? "" : obj[7].toString();
				if (st.equals("1")) {
					st = "未审核";
				} else if (st.equals("2")) {
					st = "通过";
				} else if (st.equals("3")) {
					st = "不通过";
				}
				String time = obj[8] == null ? "" : obj[8].toString();

				String t = "{\"id\":\"" + id + "\",\"cell\":[\"" + provinceName + "\",\"" + parentName + "\",\"" + code + "\",\"" + name + "\",\"" + statusname + "\",\"" + time + "\",\"" + st + "\"]}";

				cellString += t + ",";
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
		if (cellString.length() > 0) {
			cellString = cellString.substring(0, cellString.length() - 1);
		}

		String retJson = "{\"page\":\"" + start + "\",\"total\":\"" + tol + "\",\"records\":\"" + totalCount + "\",\"rows\":[" + cellString + "]}";

		if (retJson != null) {
			retJson = retJson.replace("\r", "");
			retJson = retJson.replace("\n", "");
			retJson = retJson.replace("\r\n", "");
		}
		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(retJson);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void importproChildList() {
		String sql = session.getAttribute("proChildList").toString();
		List li = peProApplyNoService.executeSQL(sql);

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet("sheet1");
		int i = 0;
		HSSFRow rowheader = sheet1.createRow((short) i);
		i++;

		rowheader.createCell((short) 0).setCellValue("省份");
		rowheader.createCell((short) 1).setCellValue("所属项目");
		rowheader.createCell((short) 2).setCellValue("项目编号");
		rowheader.createCell((short) 3).setCellValue("项目名称");
		rowheader.createCell((short) 4).setCellValue("活动状态");
		rowheader.createCell((short) 5).setCellValue("时间");
		rowheader.createCell((short) 6).setCellValue("审核状态");

		for (int ti = 0; ti < li.size(); ti++) {
			Object[] obj = (Object[]) li.get(ti);

			HSSFRow row = sheet1.createRow((short) i);
			i++;

			String parentName = obj[1] == null ? "" : obj[1].toString();
			String name = obj[2] == null ? "" : obj[2].toString();
			String code = obj[3] == null ? "" : obj[3].toString();
			String statusname = obj[5] == null ? "" : obj[5].toString();
			String st = obj[6] == null ? "" : obj[6].toString();
			String provinceName = obj[7] == null ? "" : obj[7].toString();
			if (st.equals("1")) {
				st = "未审核";
			} else if (st.equals("2")) {
				st = "通过";
			} else if (st.equals("3")) {
				st = "不通过";
			}
			String time = obj[8] == null ? "" : obj[8].toString();

			row.createCell((short) 0).setCellValue(provinceName);
			row.createCell((short) 1).setCellValue(parentName);
			row.createCell((short) 2).setCellValue(code);
			row.createCell((short) 3).setCellValue(name);
			row.createCell((short) 4).setCellValue(statusname);
			row.createCell((short) 5).setCellValue(time);
			row.createCell((short) 6).setCellValue(st);

		}

		try {
			String filename = "中西部子项目管理导出";
			filename = new String(filename.getBytes("GBK"), "ISO8859-1");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xls");
			response.setCharacterEncoding("UTF-8");

			OutputStream os = response.getOutputStream();
			os.flush();
			wb.write(os);
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 添加子项目
	public void proChildAdd() {
		String userid = request.getParameter("userid");
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String code = request.getParameter("code");
		// String year = request.getParameter("year");
		String parentid = request.getParameter("parentid");
		String parentname = request.getParameter("parentname");
		String method = request.getParameter("method");

		if (method.equals("add")) {

			if (name == null || name.trim().length() == 0) {
				try {

					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					response.setHeader("Cache-Control", "no-cache");
					response.getWriter().write("false");
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (code == null || code.trim().length() == 0) {
				try {

					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					response.setHeader("Cache-Control", "no-cache");
					response.getWriter().write("false");
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			String unitId = "";
			if (userid != null) {
				session.setAttribute("unitUserId", userid);
				String sql = "select u.fk_province from sso_user s join pe_manager p on s.login_id = p.login_id join pe_unit u on p.fk_unit=u.id " + "where s.login_id='" + userid + "'";
				List li = peProApplyNoService.executeSQL(sql);

				if (li.size() > 0) {
					unitId = li.get(0).toString();
					session.setAttribute("unitNormalId", unitId);
				}
			} else {
				unitId = request.getParameter("proviceid");
			}

			UUID uuid = UUID.randomUUID();
			String vid = uuid.toString().replaceAll("-", "");

			proChildApplyNo pcapp = new proChildApplyNo();
			pcapp.setId(vid);
			pcapp.setName(name);
			pcapp.setCode(code + "");
			// pcapp.setYear(year);
			pcapp.setFkProgramType("402880962a63e21f012a6402c5000001");
			pcapp.setSelectFlag(1);
			pcapp.setPlevel(1);
			pcapp.setFkProgramStatus("402880962a9da820012a9dd88b710001");
			pcapp.setFkProvinceCheck("402880962a607c3a012a615ad5a50019");
			pcapp.setParentid(parentid);
			pcapp.setParentName(parentname);
			pcapp.setProvice(unitId);

			proChildApplyNoService.add(pcapp);

			String prosql = "select name from pe_province t where t.id='" + unitId + "'";
			String proname = "";
			List li = peProApplyNoService.executeSQL(prosql);
			if (li.size() > 0) {
				proname = li.get(0).toString();
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String ttime = sdf.format(pcapp.getDateTime());

			String retJson = "{\"id\":\"" + vid + "\",\"name\":\"" + proname + "\",\"time\":\"" + ttime + "\"}";
			try {

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write(retJson);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (method.equals("edit")) {

			proChildApplyNo pcapp = proChildApplyNoService.get(id);
			if (pcapp != null) {
				pcapp.setName(name);
				pcapp.setCode(code + "");
				// pcapp.setYear(year);
				pcapp.setParentid(parentid);
				pcapp.setParentName(parentname);

				proChildApplyNoService.update(pcapp);
			}

			try {

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("true");

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (method.equals("del")) {

			String[] sids = id.split(",");

			for (String si : sids) {
				try {
					proChildApplyNo pcapp = proChildApplyNoService.get(si);
					if (pcapp != null) {
						proChildApplyNoService.delete(pcapp);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			try {

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("true");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// 项目 申报列表
	public void proChildDeclareList() {

		String userId = request.getParameter("userid");

		String projectName = request.getParameter("projectName");
		projectName = projectName == null ? "all" : projectName;

		String selectFalg = request.getParameter("selectFlag");
		selectFalg = selectFalg == null ? "0" : selectFalg;

		String subjectId = request.getParameter("subjectname");
		subjectId = subjectId == null ? "all" : subjectId;

		String subProjectName = request.getParameter("subProjectName");
		subProjectName = subProjectName == null ? "" : subProjectName;

		String unitname = request.getParameter("unitname");
		unitname = unitname == null ? "" : unitname;

		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		String ien = (String) request.getParameter("rows");
		if (ien == null || ien.equals("")) {
			ien = "50";
		}

		int start = Integer.parseInt(ist);
		int limit = 50;
		limit = Integer.parseInt(ien);

		session.setAttribute("unitUserId", userId);
		String sql = "select u.fk_province from sso_user s join pe_manager p on s.login_id = p.login_id join pe_unit u on p.fk_unit=u.id " + "where s.login_id='" + userId + "'";
		List li = peProApplyNoService.executeSQL(sql);
		String unitId = "";
		if (li.size() > 0) {
			unitId = li.get(0).toString();
			session.setAttribute("unitNormalId", unitId);
		}

		String searchSql = "select po.id,pao.parentname,pao.name as projectname,u.name as unitname,pe.name as subjectname,po.selectflag,po.declaration,po.scheme,to_char(po.datetime,'yyyy-MM-dd HH24:mi:ss') as createtime " + " from prochildapply po join prochildapplyno pao on po.fk_applyno=pao.id "
				+ " join pe_subject pe on po.fk_subject=pe.id " + " join pe_unit u on u.id=po.fk_unit " + " where pao.provinceid='" + unitId + "'";

		String searchSqlCount = "select count(*) " + " from prochildapply po join prochildapplyno pao on po.fk_applyno=pao.id " + " join pe_subject pe on po.fk_subject=pe.id " + " join pe_unit u on u.id=po.fk_unit " + " where pao.provinceid='" + unitId + "'";

		if (!projectName.equals("all")) {
			searchSql += " and pao.parentid='" + projectName + "'";
			searchSqlCount += " and pao.parentid='" + projectName + "'";
		}
		if (!selectFalg.equals("0")) {
			searchSql += " and po.selectflag=" + selectFalg;
			searchSqlCount += " and po.selectflag=" + selectFalg;
		}
		if (!subjectId.equals("all")) {
			searchSql += " and pe.id='" + subjectId + "'";
			searchSqlCount += " and pe.id='" + subjectId + "'";
		}

		if (!subProjectName.equals("")) {
			searchSql += " and pao.name like '%" + subProjectName + "%' ";
			searchSqlCount += " and pao.name like '%" + subProjectName + "%' ";
		}

		if (!unitname.equals("")) {
			searchSql += " and u.name like '%" + unitname + "%' ";
			searchSqlCount += " and u.name like '%" + unitname + "%' ";
		}

		int totalCount = Integer.parseInt(peProApplyNoService.executeSQL(searchSqlCount).get(0).toString());
		int tol = totalCount / limit;
		if ((totalCount % limit) > 0) {
			tol += 1;
		}

		session.setAttribute("proChildDeclareList", searchSql);

		li = peProApplyNoService.getListPage(searchSql, start, limit);

		String cellString = "";

		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String id = obj[0].toString();
			String parentName = obj[1].toString();
			String name = obj[2].toString();

			String tunitname = obj[3].toString();
			String subjectname = obj[4].toString();
			String st = obj[5].toString();
			if (st.equals("1")) {
				st = "未审核";
			} else if (st.equals("2")) {
				st = "通过";
			} else if (st.equals("3")) {
				st = "不通过";
			} else if (st.equals("4")) {
				st = "已中标";
			} else if (st.equals("5")) {
				st = "未中标";
			}
			String declare = obj[6] == null ? "" : obj[6].toString();
			String scheme = obj[7] == null ? "" : obj[7].toString();
			String time = obj[8] == null ? "" : obj[8].toString();

			String t = "{\"id\":\"" + id + "\",\"cell\":[\"" + parentName + "\",\"" + name + "\",\"" + tunitname + "\",\"" + subjectname + "\",\"" + st + "\",\"" + declare + "\",\"" + scheme + "\",\"" + time + "\"]}";

			cellString += t + ",";

		}
		if (cellString.length() > 0) {
			cellString = cellString.substring(0, cellString.length() - 1);
		}

		String retJson = "{\"page\":\"" + start + "\",\"total\":\"" + tol + "\",\"records\":\"" + totalCount + "\",\"rows\":[" + cellString + "]}";

		retJson = retJson.replace("\r", "");
		retJson = retJson.replace("\n", "");
		retJson = retJson.replace("\r\n", "");

		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(retJson);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void importproChildDeclareList() {

		String sql = session.getAttribute("proChildDeclareList").toString();
		List li = peProApplyNoService.executeSQL(sql);

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet("sheet1");
		int i = 0;
		HSSFRow rowheader = sheet1.createRow((short) i);
		i++;

		rowheader.createCell((short) 0).setCellValue("所属项目");
		rowheader.createCell((short) 1).setCellValue("项目名称");
		rowheader.createCell((short) 2).setCellValue("培训单位");
		rowheader.createCell((short) 3).setCellValue("培训学科");

		rowheader.createCell((short) 4).setCellValue("审核状态");
		rowheader.createCell((short) 5).setCellValue("时间");

		for (int ti = 0; ti < li.size(); ti++) {
			Object[] obj = (Object[]) li.get(ti);

			HSSFRow row = sheet1.createRow((short) i);
			i++;

			String parentName = obj[1].toString();
			String name = obj[2].toString();

			String unitname = obj[3].toString();
			String subjectname = obj[4].toString();
			String st = obj[5].toString();
			if (st.equals("1")) {
				st = "未审核";
			} else if (st.equals("2")) {
				st = "通过";
			} else if (st.equals("3")) {
				st = "不通过";
			} else if (st.equals("4")) {
				st = "已中标";
			} else if (st.equals("5")) {
				st = "未中标";
			}

			String time = obj[8] == null ? "" : obj[8].toString();

			row.createCell((short) 0).setCellValue(parentName);
			row.createCell((short) 1).setCellValue(name);
			row.createCell((short) 2).setCellValue(unitname);
			row.createCell((short) 3).setCellValue(subjectname);
			row.createCell((short) 4).setCellValue(st);
			row.createCell((short) 5).setCellValue(time);
		}

		try {
			String filename = "管理员审核";
			filename = new String(filename.getBytes("GBK"), "ISO8859-1");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xls");
			response.setCharacterEncoding("UTF-8");

			OutputStream os = response.getOutputStream();
			os.flush();
			wb.write(os);
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void proChildDeclareOpreate() {

		String method = request.getParameter("method");
		String id = request.getParameter("id");

		if (method.equals("del")) {

			String[] sids = id.split(",");
			for (String s : sids) {
				try {
					proChildApply pcapp = proChildApplyService.get(s);
					if (pcapp != null) {
						proChildApplyService.delete(pcapp);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			try {

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("true");

			} catch (Exception e) {
				e.printStackTrace();
				try {
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					response.setHeader("Cache-Control", "no-cache");
					response.getWriter().write("false");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		}
	}

	public void proChildAduYijian() {

		String userId = request.getParameter("userid");

		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		String ien = (String) request.getParameter("rows");
		if (ien == null || ien.equals("")) {
			ien = "50";
		}

		int start = Integer.parseInt(ist);
		int limit = 50;
		limit = Integer.parseInt(ien);

		session.setAttribute("unitUserId", userId);
		String sql = "select u.fk_province from sso_user s join pe_manager p on s.login_id = p.login_id join pe_unit u on p.fk_unit=u.id " + "where s.login_id='" + userId + "'";
		List li = peProApplyNoService.executeSQL(sql);
		String unitId = "";
		if (li.size() > 0) {
			unitId = li.get(0).toString();
			session.setAttribute("unitNormalId", unitId);
		}

		String provinceId = unitId;
		String projectId = request.getParameter("projectName");
		String subjectId = request.getParameter("subjectname");

		subjectId = subjectId == null ? "all" : subjectId;

		String subProjectName = request.getParameter("subProjectName");

		String unitname = request.getParameter("unitname");

		subProjectName = subProjectName == null ? "" : subProjectName;
		unitname = unitname == null ? "" : unitname;

		if (provinceId == null) {
			provinceId = "all";
		}
		if (projectId == null) {
			projectId = "all";
		}

		String sqlcount = "select count(distinct po.id) " + " from PROCHILDEXPERT ex ,PROCHILDAPPLY po,PROCHILDAPPLYNO pao,PE_SUBJECT pe,PE_UNIT u,pe_province pr " + " where ex.fk_progame=po.id and po.fk_subject=pe.id and po.fk_unit=u.id and po.fk_applyno=pao.id and pao.provinceid=pr.id  ";

		sql = "select po.id,pr.name as provinceName,pao.parentname,pao.name as subName,pe.name as subjectName,u.name as unitName,po.declaration,po.scheme,po.selectflag,po.pinflag,po.pinsheng,avg(ex.result) "
				+ " from PROCHILDEXPERT ex ,PROCHILDAPPLY po,PROCHILDAPPLYNO pao,PE_SUBJECT pe,PE_UNIT u,pe_province pr " + " where ex.fk_progame=po.id and po.fk_subject=pe.id and po.fk_unit=u.id and po.fk_applyno=pao.id and pao.provinceid=pr.id  ";

		String infoSql = "select pr.name as provinceName,pao.parentname,pao.name as subName ," + " u.name as unitName ,pe.name as subjectName,p.name as usname,ex.result,to_char(ex.note) ";
		infoSql += "from pe_valua_expert p, PROCHILDEXPERT ex ,PROCHILDAPPLY po,PROCHILDAPPLYNO pao,PE_SUBJECT pe,PE_UNIT u,pe_province pr ";

		infoSql += "where ex.fk_value_expert=p.id and ex.fk_progame=po.id " + "and po.fk_subject=pe.id and po.fk_unit=u.id and po.fk_applyno=pao.id and pao.provinceid=pr.id ";

		if (!provinceId.equals("all")) {
			sql += " and pr.id='" + provinceId + "' ";
			sqlcount += " and pr.id='" + provinceId + "' ";
			infoSql += " and pr.id='" + provinceId + "' ";
		}
		if (!projectId.equals("all")) {
			sql += " and pao.parentid='" + projectId + "' ";
			sqlcount += " and pao.parentid='" + projectId + "' ";
			infoSql += " and pao.parentid='" + projectId + "' ";
		}
		if (!subjectId.equals("all")) {
			sql += " and po.fk_subject='" + subjectId + "' ";
			sqlcount += " and po.fk_subject='" + subjectId + "' ";
			infoSql += " and po.fk_subject='" + subjectId + "' ";
		}

		if (!subProjectName.equals("")) {
			sql += " and pao.name like'%" + subProjectName + "%' ";
			sqlcount += " and pao.name like'%" + subProjectName + "%' ";
			infoSql += " and pao.name like'%" + subProjectName + "%' ";
		}
		if (!unitname.equals("")) {
			sql += " and u.name like'%" + unitname + "%' ";
			sqlcount += " and u.name like'%" + unitname + "%' ";
			infoSql += " and u.name like'%" + unitname + "%' ";
		}

		sql += " and po.pinsheng=2";
		sqlcount += " and po.pinsheng=2";
		infoSql += " and po.pinsheng=2";

		sql += " group by po.id,pr.name,pao.parentname,pao.name,pe.name,u.name,po.scheme,po.declaration,po.selectflag,po.pinflag,po.pinsheng";

		int totalCount = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
		int tol = totalCount / limit;
		if ((totalCount % limit) > 0) {
			tol += 1;
		}

		session.setAttribute("aduDeclareList", sql);
		session.setAttribute("aduDeclareListInfo", infoSql);

		li = peProApplyNoService.getListPage(sql, start, limit);

		String cellString = "";

		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);

			String id = obj[0].toString();

			String parentName = obj[2].toString();
			parentName = parentName.replaceAll("\"", "");
			parentName = parentName.replaceAll("\t", "");

			String name = obj[3].toString();
			name = name.replaceAll("\"", "");
			name = name.replaceAll("\t", "");

			String subjectname = obj[4].toString();
			String tunitname = obj[5].toString();

			String declare = obj[6] == null ? "" : obj[6].toString();
			String scheme = obj[7] == null ? "" : obj[7].toString();

			String dafen = obj[11] == null ? "" : obj[11].toString();

			String t = "{\"id\":\"" + id + "\",\"cell\":[\"" + parentName + "\",\"" + name + "\",\"" + tunitname + "\",\"" + subjectname + "\",\"" + declare + "\",\"" + scheme + "\",\"" + dafen + "\"]}";

			cellString += t + ",";

		}
		if (cellString.length() > 0) {
			cellString = cellString.substring(0, cellString.length() - 1);
		}

		String retJson = "{\"page\":\"" + start + "\",\"total\":\"" + tol + "\",\"records\":\"" + totalCount + "\",\"rows\":[" + cellString + "]}";

		retJson = retJson.replace("\r", "");
		retJson = retJson.replace("\n", "");
		retJson = retJson.replace("\r\n", "");

		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(retJson);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
