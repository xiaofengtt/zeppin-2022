package com.zeppin.action;

import java.io.OutputStream;
import java.sql.Clob;
import java.util.ArrayList;
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

@Controller("expertAction")
@Scope("prototype")
public class expertAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	@Autowired
	private peProApplyNoServiceImpl peProApplyNoService;

	@Autowired
	private proChildApplyNoServiceImpl proChildApplyNoService;

	@Autowired
	private proChildApplyServiceImpl proChildApplyService;

	private List judgmentStands = new ArrayList();
	private String[] id;

	private List yijian;
	private List alreadyList;

	public String[] getId() {
		return id;
	}

	public void setId(String[] id) {
		this.id = id;
	}

	private Double[] score;

	public Double[] getScore() {
		return score;
	}

	public void setScore(Double[] score) {
		this.score = score;
	}

	public List getJudgmentStands() {
		return judgmentStands;
	}

	public void setJudgmentStands(List judgmentStands) {
		this.judgmentStands = judgmentStands;
	}

	private String expid;

	public String getExpid() {
		return expid;
	}

	public void setExpid(String expid) {
		this.expid = expid;
	}

	private String advice = "";
	private Double total = 0.0;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	public expertAction() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	public void expertForward() {
		String pids = request.getParameter("pids");
		session.setAttribute("prochildapplylist", pids);
		try {
			String str = "";
			str += "{\"url\":\"../vote/expertList.jsp\"}";
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(str);

		} catch (Exception e) {
			e.printStackTrace();
		}
//		return "forward";
	}

	// 查看意见
	public String chakanyijian() {

		// id
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		yijian = new ArrayList();

		// 根据id可查询出 打分情况与 建议
		String sql = "select t.result,t.note,p.name from PROCHILDEXPERT t,pe_valua_expert p where t.fk_value_expert=p.id and t.fk_progame='" + id + "'";
		List li = peProApplyNoService.executeSQL(sql);

		if (li != null && li.size() > 0) {
			for (int i = 0; i < li.size(); i++) {
				Object[] obj = (Object[]) li.get(i);
				Object[] ob = new Object[3];
				ob[0] = obj[0];
				ob[2] = obj[2];
				if (type != null) {
					ob[2] = "***专家";
				}
				Clob clb = (Clob) obj[1];
				try {
					String detil = clb != null ? clb.getSubString(1, (int) clb.length()) : "";
					ob[1] = detil;
					yijian.add(ob);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		return "forwardEx";
	}

	public String expertAready() {

		// id
		String id = request.getParameter("id");
		alreadyList = new ArrayList();
		String sql = "select p.name,p.telephone,p.email,t.id,t.fk_progame,p.login_id from PROCHILDEXPERT t,pe_valua_expert p where t.fk_value_expert=p.id and t.fk_progame='" + id + "'";
		List li = peProApplyNoService.executeSQL(sql);

		if (li != null && li.size() > 0) {
			for (int i = 0; i < li.size(); i++) {
				Object[] obj = (Object[]) li.get(i);
				Object[] ob = new Object[6];
				ob[0] = obj[0];
				ob[1] = obj[1];
				ob[2] = obj[2];
				ob[3] = obj[3];
				ob[4] = obj[4];
				ob[5] = obj[5];
				alreadyList.add(ob);
			}
		}

		return "already";
	}

	public void expertQXZhuanJ() {
		String id = request.getParameter("id");

		try {
			String sqlcheck = "select count(*) from PE_PRO_JUDGMENT_SCORE_DETAIL t where t.scoreid='" + id + "'";
			List li = peProApplyNoService.executeSQL(sqlcheck);
			int flag = Integer.valueOf(li.get(0).toString());
			if (flag > 0) {
				try {
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					response.setHeader("Cache-Control", "no-cache");
					response.getWriter().write("false");

				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
			}

			String sql = "delete PROCHILDEXPERT t where t.id='" + id + "'";

			peProApplyNoService.executeSQLUpdate(sql);

			try {
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("true");

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("false");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void headSerach() {

		String sql1 = "select t.id,t.name from ENUM_CONST t where t.namespace='FkStatusValuate'";
		List li = peProApplyNoService.executeSQL(sql1);
		String province = "{\"id\":\"all\",\"name\":\"所有\"},";
		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String id = obj[0].toString();
			String name = obj[1].toString();
			province += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			province += ",";
		}
		if (province.length() > 0) {
			province = province.substring(0, province.length() - 1);
		}

		String retJson = "{\"preInitData\":{\"province\":[" + province + "]}}";

		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(retJson);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void expertList() {

		String stuts = request.getParameter("status");
		stuts = stuts == null ? "all" : stuts;
		String tname = request.getParameter("name");
		tname = tname == null ? "" : tname;

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

		String sqlcount = "select count(*) from pe_valua_expert t join enum_const e on t.fk_status = e.id  where 1=1 and t.login_id is not null";

		String sql = "select t.id,t.name as xingming ,e.name as statusname,t.workplace,t.major,t.zhicheng,t.login_id from pe_valua_expert t join enum_const e on t.fk_status = e.id  where 1=1 and t.login_id is not null";

		if (!stuts.equals("all")) {
			sqlcount += " and e.id='" + stuts + "'";
			sql += " and e.id='" + stuts + "'";
		}
		if (!tname.equals("")) {
			sqlcount += " and t.name like'%" + tname + "%'";
			sql += " and t.name like'%" + tname + "%'";
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
			String name = obj[1].toString();
			String status = obj[2] == null ? "" : obj[2].toString();

			String workplace = obj[3] == null ? "" : obj[3].toString();
			String major = obj[4] == null ? "" : obj[4].toString();
			String zhicheng = obj[5] == null ? "" : obj[5].toString();
			String loginId = obj[6] == null ? "" : obj[6].toString();

			String t = "{\"id\":\"" + id + "\",\"cell\":[\"" + loginId + "\",\"" + name + "\",\"" + status + "\",\"" + workplace + "\",\"" + major + "\",\"" + zhicheng + "\"]}";

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

	// 专家分配
	public void expertAllot() {

		String expertId = request.getParameter("pid");
		String method = request.getParameter("method");

		if (method.equals("add")) {
			String pids = session.getAttribute("prochildapplylist").toString();

			String[] listpids = pids.split(",");
			String[] expertPids = expertId.split(",");
			for (String es : expertPids) {
				for (String s : listpids) {
					try {
						UUID uuid = UUID.randomUUID();
						String vid = uuid.toString().replaceAll("-", "");
						String sql = "insert into PROCHILDEXPERT(id,FK_VALUE_EXPERT,FK_PROGAME) values('" + vid + "','" + es + "','" + s + "')";
						peProApplyNoService.executeSQLUpdate(sql);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
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

	// 重置密码
	public void expertPassword() {
		String expertId = request.getParameter("pid");
		String[] expertPids = expertId.split(",");
		for (String es : expertPids) {

			try {
				String sql = "update sso_user s set s.password='000000'where s.id = (select t.fk_sso_user_id from pe_valua_expert t where t.id='" + es + "')";
				peProApplyNoService.executeSQLUpdate(sql);
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

	// 要打分的列表
	public void expertDeclareList() {

		String userid = request.getParameter("userid");
		session.setAttribute("experuserid", userid);
		String sql = "select t.id from PE_VALUA_EXPERT t where t.login_id='" + userid + "'";
		List li = peProApplyNoService.executeSQL(sql);
		if (li.size() > 0) {
			String expertId = li.get(0).toString();
			session.setAttribute("expertId", expertId);
		}

		String provinceId = request.getParameter("provinceName");
		String parentNameId = request.getParameter("parentName");
		String subjuectId = request.getParameter("subjuect");

		String unitname = request.getParameter("unitname");
		String projectname = request.getParameter("projectname");

		unitname = unitname == null ? "" : unitname;
		projectname = projectname == null ? "" : projectname;

		provinceId = provinceId == null ? "all" : provinceId;
		parentNameId = parentNameId == null ? "all" : parentNameId;
		subjuectId = subjuectId == null ? "all" : subjuectId;

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

		String expertId = session.getAttribute("expertId").toString();

		String sqlcount = "select count(*) " + "from prochildapply pro join prochildexpert pre on pro.id = pre.fk_progame " + " join pe_unit u on pro.fk_unit = u.id " + " join pe_subject s on s.id = pro.fk_subject " + " join prochildapplyno pao on pao.id=pro.fk_applyno  "
				+ " join pe_province pr on pao.provinceid=pr.id" + " where  pre.fk_value_expert='" + expertId + "'";
		sql = "select pre.id,u.name as unitname ,pao.name as projectname ,s.name as subjectname ,pro.declaration,pro.scheme,pr.name as provinceName,pao.parentname,pre.result " + "from prochildapply pro join prochildexpert pre on pro.id = pre.fk_progame " + " join pe_unit u on pro.fk_unit = u.id "
				+ " join pe_subject s on s.id = pro.fk_subject " + " join prochildapplyno pao on pao.id=pro.fk_applyno  " + " join pe_province pr on pao.provinceid=pr.id" + " where  pre.fk_value_expert='" + expertId + "'";

		sqlcount += " and (pro.selectflag!=4 and pinflag=1) ";
		sql += " and (pro.selectflag!=4 and pinflag=1) ";

		if (!provinceId.equals("all")) {
			sqlcount += " and pr.id='" + provinceId + "' ";
			sql += " and pr.id='" + provinceId + "' ";
		}
		if (!parentNameId.equals("all")) {
			sqlcount += " and pao.parentid='" + parentNameId + "' ";
			sql += " and pao.parentid='" + parentNameId + "' ";
		}
		if (!subjuectId.equals("all")) {
			sqlcount += " and s.id='" + subjuectId + "' ";
			sql += " and s.id='" + subjuectId + "' ";
		}

		if (!unitname.equals("")) {
			sqlcount += " and u.name like '%" + unitname + "%' ";
			sql += " and u.name like '%" + unitname + "%' ";
		}

		if (!projectname.equals("")) {
			sqlcount += " and pao.name like'%" + projectname + "%' ";
			sql += " and pao.name like'%" + projectname + "%' ";
		}

		int totalCount = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
		int tol = totalCount / limit;
		if ((totalCount % limit) > 0) {
			tol += 1;
		}

		sql += " order by pao.id,pre.id";

		session.setAttribute("importexpertDeclareList", sql);

		li = peProApplyNoService.getListPage(sql, start, limit);

		String cellString = "";

		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String id = obj[0].toString();
			String tunitname = obj[1].toString();
			String tprojectname = obj[2] == null ? "" : obj[2].toString();
			
			tprojectname = tprojectname.replaceAll("\"", "");
			tprojectname = tprojectname.replaceAll("\t", "");
			
			String subjectname = obj[3] == null ? "" : obj[3].toString();
			String declare = obj[4] == null ? "" : obj[4].toString();
			String scheme = obj[5] == null ? "" : obj[5].toString();
			String provinceName = obj[6] == null ? "" : obj[6].toString();
			String parentName = obj[7] == null ? "" : obj[7].toString();
			String result = obj[8] == null ? "0" : obj[8].toString();
			String sresult = "";
			int fresult = Integer.valueOf(result);
			switch (fresult) {
			case 0:
				break;
			case 1:
				sresult = "差";
				break;
			case 2:
				sresult = "中";
				break;
			case 3:
				sresult = "良";
				break;
			case 4:
				sresult = "优";
				break;
			}

			String t = "{\"id\":\"" + id + "\",\"cell\":[\"" + provinceName + "\",\"" + parentName + "\",\"" + tunitname + "\",\"" + tprojectname + "\",\"" + subjectname + "\",\"" + declare + "\",\"" + scheme + "\",\"" + sresult + "\"]}";

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

	public void importexpertDeclareList() {

		String sql = (String) session.getAttribute("importexpertDeclareList");
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
		rowheader.createCell((short) 5).setCellValue("得分");

		if (li != null && li.size()>0) {
			for (int ti = 0; ti < li.size(); ti++) {
				Object[] obj = (Object[]) li.get(ti);
	
				HSSFRow row = sheet1.createRow((short) i);
				i++;
	
				String tunitname = obj[1] == null ? "" : obj[1].toString();
				String tprojectname = obj[2] == null ? "" : obj[2].toString();
				String subjectname = obj[3] == null ? "" : obj[3].toString();
	
				String provinceName = obj[6] == null ? "" : obj[6].toString();
				String parentName = obj[7] == null ? "" : obj[7].toString();
				String result = obj[8] == null ? "0" : obj[8].toString();
				String sresult = "";
				int fresult = Integer.valueOf(result);
				switch (fresult) {
				case 0:
					break;
				case 1:
					sresult = "差";
					break;
				case 2:
					sresult = "中";
					break;
				case 3:
					sresult = "良";
					break;
				case 4:
					sresult = "优";
					break;
				}
	
				row.createCell((short) 0).setCellValue(provinceName);
				row.createCell((short) 1).setCellValue(parentName);
				row.createCell((short) 2).setCellValue(tprojectname);
				row.createCell((short) 3).setCellValue(tunitname);
				row.createCell((short) 4).setCellValue(subjectname);
				row.createCell((short) 5).setCellValue(sresult);
	
			}
		}
		try {
			String filename = "评分列表导出";
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

	// 打分
	public String expertGrade() {
		int pageId = 0;
		int pageSize = 200;
		String expid = request.getParameter("expId");
		this.expid = expid;

		String sql = "select t.id,t.fk_value_expert,t.fk_progame from PROCHILDEXPERT t where t.id = '" + expid + "'";

		List li = peProApplyNoService.executeSQL(sql);

		String proapply = "";

		if (li.size() > 0) {

			Object[] obj = (Object[]) li.get(0);
			proapply = obj[2].toString();

			proChildApply papply = proChildApplyService.get(proapply);

			if (papply != null) {
				String proid = papply.getFkApplyNo();

				proChildApplyNo papplyno = proChildApplyNoService.get(proid);

				String parentid = papplyno.getParentid();

				String t1 = "select t.id,t.result,t.note from PROCHILDEXPERT t where t.id='" + this.expid + "'";

				List t1list = peProApplyNoService.executeSQL(t1);
				if (t1list != null && t1list.size() > 0) {
					Object[] tobj = (Object[]) t1list.get(0);
					this.total = tobj[1] == null ? 0.0 : Float.parseFloat(tobj[1].toString());
					Clob clb = (Clob) tobj[2];
					try {
						String detil = clb != null ? clb.getSubString(1, (int) clb.length()) : "";
						this.advice = detil;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

				sql = "select t.id,t.type,t.name,t.detail_bak,t.paixu,t.note,t.proid,t.score,t.detail from PE_PRO_APPLY_JUDGMENT_STAND t where t.proid='" + parentid + "' order by t.paixu asc";

				List judList = peProApplyNoService.executeSQL(sql);

				String tsql = "select t.sid,t.score from PE_PRO_JUDGMENT_SCORE_DETAIL t where t.scoreid='" + this.expid + "'";
				List jsd = peProApplyNoService.executeSQL(tsql);

				if (jsd.size() > 0) {
					this.message = "edit";
				} else {
					this.message = "add";
				}

				// this.total = this.total / judList.size();

				for (int i = 0; i < judList.size(); i++) {
					Object[] obji = (Object[]) judList.get(i);
					Clob clb = (Clob) obji[8];
					try {
						String detil = clb != null ? clb.getSubString(1, (int) clb.length()) : "";
						Object[] data = { obji[0], obji[1], obji[2], obji[3], obji[4], obji[5], obji[6], obji[7], detil, 0 };
						judgmentStands.add(data);

						if (jsd != null && jsd.size() > 0) {
							for (int j = 0; j < jsd.size(); j++) {
								Object[] objj = (Object[]) jsd.get(j);
								if (obji[0].equals(objj[0])) {
									data[9] = objj[1];
								}
							}
						}

					} catch (Exception e) {
					}
				}

			}

		}

		return "ok";

	}

	// 保存
	public void expertSave() {

		String sql1 = "select p.pinflag from PROCHILDEXPERT t ,Prochildapply p where 1=1 and t.fk_progame=p.id and t.id='" + this.expid + "'";
		List li = peProApplyNoService.executeSQL(sql1);
		if (li != null) {
			int flag = Integer.parseInt(li.get(0).toString());
			if (flag == 0) {
				try {
					response.setCharacterEncoding("UTF-8");
					response.setHeader("Cache-Control", "no-cache");
					response.setContentType("text/html;charset=utf-8");
					response.getWriter().write("管理员已经统计，不可在打分");

				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
			}
		}

		Double total = 0.0;
		try {
			for (Double i : this.score) {
				total += i;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("打分:" + total);

		String updateSql = "update PROCHILDEXPERT set result=" + total + " , note='" + advice + "' , selectflag=2 where id='" + this.expid + "'";

		System.out.println(updateSql);

		peProApplyNoService.executeSQLUpdate(updateSql);

		// 删除原来的数据
		String delSql = "delete PE_PRO_JUDGMENT_SCORE_DETAIL t where t.scoreid='" + this.expid + "'";

		peProApplyNoService.executeSQLUpdate(delSql);

		int i = 0;
		for (Double s : this.score) {
			UUID uuid = UUID.randomUUID();
			String vid = uuid.toString().replaceAll("-", "");
			String insql = "insert into PE_PRO_JUDGMENT_SCORE_DETAIL(id,sid,scoreid,score) values('" + vid + "','" + this.id[i] + "','" + this.expid + "'," + s + ")";
			peProApplyNoService.executeSQLUpdate(insql);
			i++;
		}

		try {

			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write("打分成功");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List getYijian() {
		return yijian;
	}

	public void setYijian(List yijian) {
		this.yijian = yijian;
	}

	public List getAlreadyList() {
		return alreadyList;
	}

	public void setAlreadyList(List alreadyList) {
		this.alreadyList = alreadyList;
	}

}
