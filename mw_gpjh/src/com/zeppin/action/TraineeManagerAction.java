package com.zeppin.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.zeppin.service.impl.peProApplyNoServiceImpl;
import com.zeppin.service.impl.peProApplyServiceImpl;
import com.zeppin.util.IdCardUtil;

@SuppressWarnings("rawtypes")
@Controller("traineeManagerAction")
@Scope("prototype")
public class TraineeManagerAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private peProApplyNoServiceImpl peProApplyNoService;

	@Autowired
	private peProApplyServiceImpl peProApplyService;

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private String provinceId;
	private String provinceName;

	private String impProvince;
	private String impProjectParent;
	private String impProject;
	private String impUnit;
	private String impSubject;

	private String subprojectId;
	private String projectId;
	private String unitId;
	private String subjectId;
	private String role;

	private File fileupload;
	private String fileuploadFileName;

	public TraineeManagerAction() {
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

	public String forwardjh() {
		if (session.getAttribute("userid") == null) {
			String userId = request.getParameter("userid");
			session.setAttribute("projectUserId", userId);
		}
		return "forwardjh";
	}

	public String forwardUpload() {
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

		return "forwardUpload";
	}
	
	public String forwardUploadOld() {
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

		return "forwardUploadOld";
	}

	public String forwardJHUpload() {
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

		return "forwardJHUpload";
	}

	public String historyUpload() {
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

		return "historyUpload";
	}

	public void headsearch() {

		String role = request.getParameter("role");
		String userid = request.getParameter("userid");
		String unitId = "";

		if (role != null && role.equals("1")) {
			String sql = "select u.fk_province from sso_user s join pe_manager p on s.login_id = p.login_id join pe_unit u on p.fk_unit=u.id " + "where s.login_id='" + userid + "'";
			List li = peProApplyNoService.executeSQL(sql);
			if (li.size() > 0) {
				unitId = li.get(0).toString();
			}
		}

		String sql1 = "select distinct t.provinceid,p.name from PROCHILDAPPLYNO t join PE_PROVINCE p on t.provinceid = p.id where 1=1  ";
		String sql2 = "select distinct t.id,t.name from prochildapplyno t where 1=1 ";
		String sql3 = "select distinct t.parentid,t.parentname from prochildapplyno t where 1=1 ";
		String sql4 = "select t.id,t.name from PE_UNIT t where t.fk_unit_type='ff8080812b493236012b50bfcb6e02cd' ";
		String sql5 = "select t.id,t.name from PE_SUBJECT t ";
		
		//新增地区市、地区县筛选
		String sql6 = "select distinct c.id, c.name from CITY c where 1=1 ";
		String sql7 = "select distinct co.id, co.name from COUNTY co join CITY c on co.fk_city=c.id where 1=1 ";

		String sql8 = "select ua.id,ua.name from UNIT_ATTRIBUTE ua where 1=1 ";
		String sql9 = "select f.id,f.name from FOLK f where 1=1 ";
		String sql10 = "select jb.id,jb.name from JOB_TITLE jb where 1=1 ";
		String sql11 = "select mtg.id,mtg.name from MAIN_TEACHING_GRADE mtg where 1=1 ";
		String sql12 = "select mts.id,mts.name from MAIN_TEACHING_SUBJECT mts where 1=1 ";
		String sql13 = "select edu.id,edu.name from EDUCATION edu where 1=1 ";
		
		String sql14 = "select ua.id,ua.name from UNIT_TYPE ua where 1=1 ";
		
		
		String province0 = request.getParameter("province") == null?"":request.getParameter("province");
		String city0 = request.getParameter("city") == null?"":request.getParameter("city");
		

		if (unitId.length() > 0) {
			sql1 += " and p.id='" + unitId + "'";
			sql2 += " and t.provinceid='" + unitId + "'";
			sql3 += " and t.provinceid='" + unitId + "'";
			sql4 += " and t.fk_province='" + unitId + "'";
			sql6 += " and c.fk_province='" + unitId +"'";
			sql7 += " and c.fk_province='" + unitId +"'";
			if(city0.length() > 0 &&  !city0.equals("all")){
				sql7 += " and co.fk_city='" + city0 +"'";
			}
		}
		
		if(role != null && role.equals("2") && !province0.equals("all") && province0.length() > 0){
			sql6 += " and c.fk_province='"+ province0 + "'";
			sql7 += " and c.fk_province='" + province0 +"'";
			if(city0.length() > 0 &&  !city0.equals("all")){
				sql7 += " and co.fk_city='" + city0 +"'";
			}
		}
		sql2 += " order by t.datetime desc";
		sql3 += " order by t.parentname desc";
		sql4 += " order by t.code desc";
		sql5 += " order by t.code desc";

		
		//新增地区市、县筛选
		List list = peProApplyNoService.executeSQL(sql6);
		String cities = "{\"id\":\"all\",\"name\":\"所有\"},";
		List list2 = peProApplyNoService.executeSQL(sql7);
		String counties = "{\"id\":\"all\",\"name\":\"所有\"},";
		
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				Object[] obj = (Object[]) list.get(i);
				String id = obj[0] == null ? "" : obj[0].toString();
				String name = obj[1] == null ? "" : obj[1].toString();
				cities += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
				cities += ",";
			}
		}
		if (cities.length() > 0) {
			cities = cities.substring(0, cities.length() - 1);
		}
		
		if(list != null && list2.size() > 0){
			for(int i = 0; i < list2.size(); i++){
				Object[] obj = (Object[]) list2.get(i);
				String id = obj[0] == null ? "" : obj[0].toString();
				String name = obj[1] == null ? "" : obj[1].toString();
				counties += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
				counties += ",";
			}
		}
		if (counties.length() > 0) {
			counties = counties.substring(0, counties.length() - 1);
		}
		
		List list3 = peProApplyNoService.executeSQL(sql8);
		String unitAttribute = "{\"id\":\"all\",\"name\":\"所有\"},";
		List list4 = peProApplyNoService.executeSQL(sql9);
		String folk = "{\"id\":\"all\",\"name\":\"所有\"},";
		List list5 = peProApplyNoService.executeSQL(sql10);
		String jobTitle = "{\"id\":\"all\",\"name\":\"所有\"},";
		List list6 = peProApplyNoService.executeSQL(sql11);
		String mainGrade = "{\"id\":\"all\",\"name\":\"所有\"},";
		List list7 = peProApplyNoService.executeSQL(sql12);
		String mainSubject = "{\"id\":\"all\",\"name\":\"所有\"},";
		List list8 = peProApplyNoService.executeSQL(sql13);
		String education = "{\"id\":\"all\",\"name\":\"所有\"},";
		
		List list9 = peProApplyNoService.executeSQL(sql14);
		String unitType = "{\"id\":\"all\",\"name\":\"所有\"},";
		
		
		for (int i = 0; i < list3.size(); i++) {
			Object[] obj = (Object[]) list3.get(i);
			String id = obj[0] == null ? "" : obj[0].toString();
			String name = obj[1] == null ? "" : obj[1].toString();
			unitAttribute += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			unitAttribute += ",";
		}
		if (unitAttribute.length() > 0) {
			unitAttribute = unitAttribute.substring(0, unitAttribute.length() - 1);
		}
		
		for (int i = 0; i < list9.size(); i++) {
			Object[] obj = (Object[]) list9.get(i);
			String id = obj[0] == null ? "" : obj[0].toString();
			String name = obj[1] == null ? "" : obj[1].toString();
			unitType += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			unitType += ",";
		}
		if (unitType.length() > 0) {
			unitType = unitType.substring(0, unitType.length() - 1);
		}
		
		for (int i = 0; i < list4.size(); i++) {
			Object[] obj = (Object[]) list4.get(i);
			String id = obj[0] == null ? "" : obj[0].toString();
			String name = obj[1] == null ? "" : obj[1].toString();
			folk += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			folk += ",";
		}
		if (folk.length() > 0) {
			folk = folk.substring(0, folk.length() - 1);
		}
		
		for (int i = 0; i < list5.size(); i++) {
			Object[] obj = (Object[]) list5.get(i);
			String id = obj[0] == null ? "" : obj[0].toString();
			String name = obj[1] == null ? "" : obj[1].toString();
			jobTitle += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			jobTitle += ",";
		}
		if (jobTitle.length() > 0) {
			jobTitle = jobTitle.substring(0, jobTitle.length() - 1);
		}
		
		for (int i = 0; i < list6.size(); i++) {
			Object[] obj = (Object[]) list6.get(i);
			String id = obj[0] == null ? "" : obj[0].toString();
			String name = obj[1] == null ? "" : obj[1].toString();
			mainGrade += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			mainGrade += ",";
		}
		if (mainGrade.length() > 0) {
			mainGrade = mainGrade.substring(0, mainGrade.length() - 1);
		}
		
		for (int i = 0; i < list7.size(); i++) {
			Object[] obj = (Object[]) list7.get(i);
			String id = obj[0] == null ? "" : obj[0].toString();
			String name = obj[1] == null ? "" : obj[1].toString();
			mainSubject += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			mainSubject += ",";
		}
		if (mainSubject.length() > 0) {
			mainSubject = mainSubject.substring(0, mainSubject.length() - 1);
		}
		
		for (int i = 0; i < list8.size(); i++) {
			Object[] obj = (Object[]) list8.get(i);
			String id = obj[0] == null ? "" : obj[0].toString();
			String name = obj[1] == null ? "" : obj[1].toString();
			education += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			education += ",";
		}
		if (education.length() > 0) {
			education = education.substring(0, education.length() - 1);
		}
		
		
		
		List li = peProApplyNoService.executeSQL(sql1);
		String province = "{\"id\":\"all\",\"name\":\"所有\"},";
		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String id = obj[0] == null ? "" : obj[0].toString();
			String name = obj[1] == null ? "" : obj[1].toString();
			province += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			province += ",";
		}

		List si = peProApplyNoService.executeSQL(sql2);
		String project = "{\"id\":\"all\",\"name\":\"所有\"},";
		for (int i = 0; i < si.size(); i++) {
			Object[] obj = (Object[]) si.get(i);
			String id = obj[0] == null ? "" : obj[0].toString();
			String name = obj[1] == null ? "" : obj[1].toString();
			name = name.replaceAll("\"", "");
			name = name.replaceAll("\t", "");
			project += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			project += ",";
		}

		List pi = peProApplyNoService.executeSQL(sql3);
		String parents = "{\"id\":\"all\",\"name\":\"所有\"},";
		for (int i = 0; i < pi.size(); i++) {
			Object[] obj = (Object[]) pi.get(i);
			String id = obj[0] == null ? "" : obj[0].toString();
			String name = obj[1] == null ? "" : obj[1].toString();
			name = name.replaceAll("\"", "");
			name = name.replaceAll("\t", "");
			parents += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			parents += ",";
		}

		pi = peProApplyNoService.executeSQL(sql4);
		String units = "{\"id\":\"all\",\"name\":\"所有\"},";
		for (int i = 0; i < pi.size(); i++) {
			Object[] obj = (Object[]) pi.get(i);
			String id = obj[0] == null ? "" : obj[0].toString();
			String name = obj[1] == null ? "" : obj[1].toString();
			units += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			units += ",";
		}

		pi = peProApplyNoService.executeSQL(sql5);
		String subjects = "{\"id\":\"all\",\"name\":\"所有\"},";
		for (int i = 0; i < pi.size(); i++) {
			Object[] obj = (Object[]) pi.get(i);
			String id = obj[0] == null ? "" : obj[0].toString();
			String name = obj[1] == null ? "" : obj[1].toString();
			subjects += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			subjects += ",";
		}

		if (province.length() > 0) {
			province = province.substring(0, province.length() - 1);
		}
		if (project.length() > 0) {
			project = project.substring(0, project.length() - 1);
		}
		if (parents.length() > 0) {
			parents = parents.substring(0, parents.length() - 1);
		}

		if (units.length() > 0) {
			units = units.substring(0, units.length() - 1);
		}

		if (subjects.length() > 0) {
			subjects = subjects.substring(0, subjects.length() - 1);
		}

//		String retJson = "{\"preInitData\":{\"project\":[" + project + "],\"province\":[" + province + "],\"parent\":[" + parents + "],\"unit\":[" + units + "],\"subject\":[" + subjects + "]}}";
		
		String retJson = "{\"preInitData\":{\"project\":[" + project + "],\"province\":[" + province + "],\"city\":[" + cities + "],\"county\":[" + counties 
				+ "],\"unitAttribute\":[" + unitAttribute+ "],\"unitType\":[" + unitType+ "],\"folk\":[" + folk + "],\"jobTitle\":[" + jobTitle + "],\"mainGrade\":[" + mainGrade 
				+ "],\"mainSubject\":[" + mainSubject + "],\"education\":[" + education + "],\"parent\":[" + parents + "],\"unit\":[" + units + "],\"subject\":[" + subjects + "]}}";
			
		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(retJson);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void headsearchLv() {

		String role = request.getParameter("role");
		String userid = request.getParameter("userid");
		String unitId = "";

		if (role != null && role.equals("1")) {
			String sql = "select u.fk_province from sso_user s join pe_manager p on s.login_id = p.login_id join pe_unit u on p.fk_unit=u.id " + "where s.login_id='" + userid + "'";
			List li = peProApplyNoService.executeSQL(sql);
			if (li.size() > 0) {
				unitId = li.get(0).toString();
			}
		}

		String sql3 = "select distinct t.parentid,t.parentname from prochildapplyno t where 1=1 ";
		String sql5 = "select t.id,t.name from PE_SUBJECT t";

		if (unitId.length() > 0) {
			sql3 += " and t.provinceid='" + unitId + "'";
		}
		
		sql3 += " order by t.parentname desc";
		sql5 += " order by t.code desc";


		List pi = peProApplyNoService.executeSQL(sql3);
		String parents = "{\"id\":\"all\",\"name\":\"所有\"},";
		for (int i = 0; i < pi.size(); i++) {
			Object[] obj = (Object[]) pi.get(i);
			String id = obj[0] == null ? "" : obj[0].toString();
			String name = obj[1] == null ? "" : obj[1].toString();
			name = name.replaceAll("\"", "");
			name = name.replaceAll("\t", "");
			parents += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			parents += ",";
		}


		pi = peProApplyNoService.executeSQL(sql5);
		String subjects = "";
		for (int i = 0; i < pi.size(); i++) {
			Object[] obj = (Object[]) pi.get(i);
			String id = obj[0] == null ? "" : obj[0].toString();
			String name = obj[1] == null ? "" : obj[1].toString();
			subjects += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			subjects += ",";
		}

		if (parents.length() > 0) {
			parents = parents.substring(0, parents.length() - 1);
		}


		if (subjects.length() > 0) {
			subjects = subjects.substring(0, subjects.length() - 1);
		}

		String retJson = "{\"preInitData\":{\"parent\":[" + parents + "],\"subject\":[" + subjects + "]}}";

		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(retJson);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 示范性筛选内容
	 */
	public void headsearchLvforsf() {

		String role = request.getParameter("role");
		String userid = request.getParameter("userid");
		String unitId = "";

		if (role != null && role.equals("1")) {
			String sql = "select u.fk_province from sso_user s join pe_manager p on s.login_id = p.login_id join pe_unit u on p.fk_unit=u.id " + "where s.login_id='" + userid + "'";
			List li = peProApplyNoService.executeSQL(sql);
			if (li.size() > 0) {
				unitId = li.get(0).toString();
			}
		}

		String sql3 = "select distinct t.id,t.name from pe_pro_applyno t where 1=1 ";
		String sql5 = "select t.id,t.name from PE_SUBJECT t";

		if (unitId.length() > 0) {
//			sql3 += " and t.provinceid='" + unitId + "'";
		}
		
		sql3 += " order by t.name desc";
		sql5 += " order by t.code desc";

		List pi = peProApplyNoService.executeSQL(sql3);
		String parents = "{\"id\":\"all\",\"name\":\"所有\"},";
		for (int i = 0; i < pi.size(); i++) {
			Object[] obj = (Object[]) pi.get(i);
			String id = obj[0] == null ? "" : obj[0].toString();
			String name = obj[1] == null ? "" : obj[1].toString();
			name = name.replaceAll("\"", "");
			name = name.replaceAll("\t", "");
			parents += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			parents += ",";
		}


		pi = peProApplyNoService.executeSQL(sql5);
		String subjects = "";
		for (int i = 0; i < pi.size(); i++) {
			Object[] obj = (Object[]) pi.get(i);
			String id = obj[0] == null ? "" : obj[0].toString();
			String name = obj[1] == null ? "" : obj[1].toString();
			subjects += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			subjects += ",";
		}

		if (parents.length() > 0) {
			parents = parents.substring(0, parents.length() - 1);
		}


		if (subjects.length() > 0) {
			subjects = subjects.substring(0, subjects.length() - 1);
		}

		String retJson = "{\"preInitData\":{\"parent\":[" + parents + "],\"subject\":[" + subjects + "]}}";

		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(retJson);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void areasearch() {
		
		String role = request.getParameter("role");
		String userid = request.getParameter("userid");
		String unitId = "";

		if (role != null && role.equals("1")) {
			String sql = "select u.fk_province from sso_user s join pe_manager p on s.login_id = p.login_id join pe_unit u on p.fk_unit=u.id " + "where s.login_id='" + userid + "'";
			List li = peProApplyNoService.executeSQL(sql);
			if (li.size() > 0) {
				unitId = li.get(0).toString();
			}
		}

		String type = request.getParameter("type") == null ?"":request.getParameter("type");
		String ide = request.getParameter("id") == null ?"":request.getParameter("id");
		
		String sql = "";
		if(!"".equals(type) && !"".equals(ide)){
			if(type.equals("province")){
				sql += "select distinct c.id,c.name from CITY c where 1=1 ";
				if(!"all".equals(ide)){
					sql += " and c.fk_province='"+ide+"'";
				}else{
					if(unitId.length()>0){
						sql += " and c.fk_province='"+unitId+"'";
					}
				}
			}else{
				sql += "select distinct co.id, co.name from COUNTY co join CITY c on co.fk_city=c.id where 1=1 ";
				if(!"all".equals(ide)){
					sql += " and co.fk_city='"+ide+"'";
				}else{
					if(unitId.length()>0){
						sql += " and c.fk_province='"+unitId+"'";
					}
				}
			}
		}
		
		List listArea = peProApplyNoService.executeSQL(sql);
		String area = "{\"id\":\"all\",\"name\":\"所有\"},";
		if(listArea != null && listArea.size()>0){
			for(int i = 0; i < listArea.size(); i++){
				Object[] obj = (Object[]) listArea.get(i);
				String id = obj[0] == null ? "" : obj[0].toString();
				String name = obj[1] == null ? "" : obj[1].toString();
				area += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
				area += ",";
			}
		}
		if (area.length() > 0) {
			area = area.substring(0, area.length() - 1);
		}
		

		String retJson = "{\"preInitData\":{\"area\":[" + area +  "]}}";
			
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
		String role = request.getParameter("role");
		String userid = request.getParameter("userid");
		String unitId = "";

		if (role != null && role.equals("1")) {
			String sql = "select u.fk_province from sso_user s join pe_manager p on s.login_id = p.login_id join pe_unit u on p.fk_unit=u.id " + "where s.login_id='" + userid + "'";
			List li = peProApplyNoService.executeSQL(sql);
			if (li.size() > 0) {
				unitId = li.get(0).toString();
			}
		}

		String provinceId = request.getParameter("provinceName");
		
		String cityId = request.getParameter("cityName") == null ? "all" : request.getParameter("cityName");
		String countyId = request.getParameter("countyName") == null ? "all" :  request.getParameter("countyName");
		
		String unitAttributeId = request.getParameter("unitAttributeName") == null ? "all" : request.getParameter("unitAttributeName");
		
		String unitTypeId = request.getParameter("unitTypeName") == null ? "all" : request.getParameter("unitTypeName");
		
		String folkId = request.getParameter("folkName") == null ? "all" :  request.getParameter("folkName");
		
		String jobTitleId = request.getParameter("jobTitleName") == null ? "all" : request.getParameter("jobTitleName");
		String mainGradeId = request.getParameter("mainGradeName") == null ? "all" :  request.getParameter("mainGradeName");
		
		String mainSubjectId = request.getParameter("mainSubjectName") == null ? "all" : request.getParameter("mainSubjectName");
		String educationId = request.getParameter("educationName") == null ? "all" :  request.getParameter("educationName");
		
		String idcards = request.getParameter("idcard") == null ? "" : request.getParameter("idcard");
		
		String projectId = request.getParameter("projectName");
		String parentId = request.getParameter("parentName");

		String unitName = request.getParameter("unitName");
		unitName = unitName == null ? "" : unitName;

		String subjectId = request.getParameter("subjectName");
		String tame = request.getParameter("name");

		String status = request.getParameter("status");
		status = status == null ? "0" : status;

		if (provinceId == null || provinceId.equals("all")) {
			if (unitId.length() > 0) {
				provinceId = unitId;
			} else {
				provinceId = "all";
			}
		}

		if (projectId == null) {
			projectId = "all";
		}
		if (parentId == null) {
			parentId = "all";
		}
		subjectId = subjectId == null ? "all" : subjectId;
		tame = tame == null ? "" : tame;

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

//		String sqlcount = "select count(*) " + " from TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id " + " join PE_UNIT u on t.fk_training_unit = u.id " + " join PE_SUBJECT s on t.fk_subject = s.id " + " where 1=1 ";
//
//		String sql = "select t.id,p.name as provinceName ,t.name as traineename,po.name as projectName,u.name as unitName,s.name as subjectName ,t.dipcode,po.parentname,t.telephone,t.email,t.work_place,t.status "
//				+ " from TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id " + " join PE_UNIT u on t.fk_training_unit = u.id " + " join PE_SUBJECT s on t.fk_subject = s.id " + " where 1=1 ";
		
//		String sql = "select t.id,p.name as provinceName ,t.name as traineename,po.name as projectName,u.name as unitName,s.name as subjectName ,t.dipcode,po.parentname,t.telephone,t.email,t.work_place,t.volk,t.idcard,t.property,t.main_teaching_grade,t.main_teaching_subject,"
//				+ "t.education,t.graduation,t.major,t.hiredate "
//				+ " from TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id " + " join PE_UNIT u on t.fk_training_unit = u.id " + " join PE_SUBJECT s on t.fk_subject = s.id " + " where 1=1 ";
		String sqlcount = "select count(*) " + " from TRAINEE t join PE_PROVINCE p on t.fk_province = p.id left join city c on t.fk_city=c.id left join county co on t.fk_county=co.id join prochildapplyno po on t.fk_applyno = po.id " + " join PE_UNIT u on t.fk_training_unit = u.id " + " join PE_SUBJECT s on t.fk_subject = s.id " 
				+ " left join UNIT_ATTRIBUTE ua on t.fk_unitattribute=ua.id left join FOLK f on t.fk_folk=f.id left join JOB_TITLE jt on t.fk_jobtitle=jt.id"
				+ " left join MAIN_TEACHING_GRADE mtg on t.fk_main_teaching_grade=mtg.id left join MAIN_TEACHING_SUBJECT mts on t.fk_main_teaching_subject=mts.id "
				+ " left join EDUCATION edu on t.fk_education = edu.id left join UNIT_TYPE ut on t.fk_unit_type=ut.id "
				+ " where 1=1 ";
		String sql = "select t.id,p.name as provinceName ,t.name as traineename,po.name as projectName,u.name as unitName,s.name as subjectName ,po.parentname,t.telephone,t.email,t.work_place,t.status,c.name as cityName,co.name as countyName, "
				+ " ua.name as unitAttributeName,f.name as folkName,jt.name as jobTitleName,mtg.name as mainGradeName,mts.name as mainSubjectName,edu.name as educationName, "
				+ " t.idcard,t.graduation,t.major,t.dipcode,ut.name as unitType "
				+ " from TRAINEE t join PE_PROVINCE p on t.fk_province = p.id left join city c on t.fk_city=c.id left join county co on t.fk_county=co.id join prochildapplyno po on t.fk_applyno = po.id " + " join PE_UNIT u on t.fk_training_unit = u.id " + " join PE_SUBJECT s on t.fk_subject = s.id " 
				+ " left join UNIT_ATTRIBUTE ua on t.fk_unitattribute=ua.id left join FOLK f on t.fk_folk=f.id left join JOB_TITLE jt on t.fk_jobtitle=jt.id"
				+ " left join MAIN_TEACHING_GRADE mtg on t.fk_main_teaching_grade=mtg.id left join MAIN_TEACHING_SUBJECT mts on t.fk_main_teaching_subject=mts.id "
				+ " left join EDUCATION edu on t.fk_education = edu.id left join UNIT_TYPE ut on t.fk_unit_type=ut.id "
				+ " where 1=1 ";
		
		if (!provinceId.equals("all")) {
			sql += " and p.id='" + provinceId + "' ";
			sqlcount += " and p.id='" + provinceId + "' ";
			if (!cityId.equals("all")) {
				sql += " and c.id='" + cityId + "' ";
				sqlcount += " and c.id='" + cityId + "' ";
				if (!countyId.equals("all")) {
					sql += " and co.id='" + countyId + "' ";
					sqlcount += " and co.id='" + countyId + "' ";
				}
			}
		}
		
		if (!unitAttributeId.equals("all")) {
			sql += " and ua.id='" + unitAttributeId + "' ";
			sqlcount += " and ua.id='" + unitAttributeId + "' ";
		}
		if (!unitTypeId.equals("all")) {
			sql += " and ut.id='" + unitTypeId + "' ";
			sqlcount += " and ut.id='" + unitTypeId + "' ";
		}
		
		if (!folkId.equals("all")) {
			sql += " and f.id='" + folkId + "' ";
			sqlcount += " and f.id='" + folkId + "' ";
		}
		if (!jobTitleId.equals("all")) {
			sql += " and jt.id='" + jobTitleId + "' ";
			sqlcount += " and jt.id='" + jobTitleId + "' ";
		}
		if (!mainGradeId.equals("all")) {
			sql += " and mtg.id='" + mainGradeId + "' ";
			sqlcount += " and mtg.id='" + mainGradeId + "' ";
		}
		if (!mainSubjectId.equals("all")) {
			sql += " and mts.id='" + mainSubjectId + "' ";
			sqlcount += " and mts.id='" + mainSubjectId + "' ";
		}
		if (!educationId.equals("all")) {
			sql += " and edu.id='" + educationId + "' ";
			sqlcount += " and edu.id='" + educationId + "' ";
		}
		
		if (!projectId.equals("all")) {
			sql += " and po.id='" + projectId + "' ";
			sqlcount += " and po.id='" + projectId + "' ";
		}
		if (!parentId.equals("all")) {
			sql += " and po.parentid='" + parentId + "' ";
			sqlcount += " and po.parentid='" + parentId + "' ";
		}
		if (!subjectId.equals("all")) {
			sql += " and t.fk_subject ='" + subjectId + "' ";
			sqlcount += " and t.fk_subject ='" + subjectId + "' ";
		}
		if (!tame.equals("")) {
			sql += " and t.name like'%" + tame + "%' ";
			sqlcount += " and t.name like'%" + tame + "%' ";
		}
		
		if (!idcards.equals("")) {
			sql += " and t.idcard like'%" + idcards + "%' ";
			sqlcount += " and t.idcard like'%" + idcards + "%' ";
		}
		
		if (!unitName.equals("")) {
			sql += " and u.name like'%" + unitName + "%' ";
			sqlcount += " and u.name like'%" + unitName + "%' ";
		}
		if (!status.equals("0")) {
			sql += " and t.status=" + status + " ";
			sqlcount += " and t.status=" + status + " ";
		}

		int totalCount = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
		int tol = totalCount / limit;
		if ((totalCount % limit) > 0) {
			tol += 1;
		}


		List li = peProApplyNoService.getListPage(sql, start, limit);

		session.setAttribute("traineeCXsql", sql);

		String cellString = "";

		for (int i = 0; i < li.size(); i++) {
//			Object[] obj = (Object[]) li.get(i);
//			String id = obj[0].toString();
//			String provinceName = obj[1] == null ? "" : obj[1].toString();
//			String name = obj[2] == null ? "" : obj[2].toString();
//			String projectName = obj[3] == null ? "" : obj[3].toString();
//			String tunitName = obj[4] == null ? "" : obj[4].toString();
//			String subjectName = obj[5] == null ? "" : obj[5].toString();
//			String dipCode = obj[6] == null ? "" : obj[6].toString();
//			String parentname = obj[7] == null ? "" : obj[7].toString();
//			String tel = obj[8] == null ? "" : obj[8].toString();
//			String mail = obj[9] == null ? "" : obj[9].toString();
//			String workpalce = obj[10] == null ? "" : obj[10].toString();
//
//			String tstatus = obj[11].toString().equals("2") ? "已结业" : "未结业";
			
//			/*
//			 * 新增属性
//			 */
//			String volk = obj[12] == null ? "" : obj[12].toString();//民族
//			String idcard = obj[13] == null ? "" : obj[13].toString();//身份证号
//			String property = obj[14] == null ? "" : obj[14].toString();//单位属性
//			String mainTeachingGrade = obj[15] == null ? "" : obj[15].toString();//主要教学学段
//			String mainTeachingSubject = obj[16] == null ? "" : obj[16].toString();//主要教学学科
//			String education = obj[17] == null ? "" : obj[17].toString();//学历
//			String graduation = obj[18] == null ? "" : obj[18].toString();//毕业院校
//			String major = obj[19] == null ? "" : obj[19].toString();//专业
//			String hiredate = obj[20] == null ? "" : obj[20].toString();//入职时间

//			String t = "{\"id\":\"" + id + "\",\"cell\":[\"" + provinceName + "\",\"" + parentname + "\",\"" + projectName + "\",\""
//			+ tunitName + "\",\"" + subjectName + "\",\"" + name + "\",\"" + tel + "\",\"" + mail + "\",\"" + workpalce + "\",\""
//			+ dipCode + "\",\"" + tstatus + "\",\"" + volk + "\",\"" + idcard + "\",\"" + property + "\",\"" + mainTeachingGrade + "\",\""
//			+ mainTeachingSubject + "\",\"" + education + "\",\"" + graduation + "\",\"" + major + "\",\"" + hiredate + "\"]}";
			Object[] obj = (Object[]) li.get(i);
			String id = obj[0].toString();
			String provinceName = obj[1] == null ? "" : obj[1].toString();
			String name = obj[2] == null ? "" : obj[2].toString();
			String projectName = obj[3] == null ? "" : obj[3].toString();
			String tunitName = obj[4] == null ? "" : obj[4].toString();
			String subjectName = obj[5] == null ? "" : obj[5].toString();
			String parentname = obj[6] == null ? "" : obj[6].toString();
			String tel = obj[7] == null ? "" : obj[7].toString();
			String mail = obj[8] == null ? "" : obj[8].toString();
			String workpalce = obj[9] == null ? "" : obj[9].toString();
			String tstatus = obj[10] == null ? "1" : obj[10].toString();
			if (tstatus.equals("1")) {
				tstatus = "未结业";
			} else if (tstatus.equals("2")) {
				tstatus = "已结业";
			}

			String city = obj[11] == null ? "" : obj[11].toString();//市
			String county = obj[12] == null ? "" : obj[12].toString();//县
			
			String unitAttribute = obj[13] == null ? "" : obj[13].toString();//单位属性
			String folk = obj[14] == null ? "" : obj[14].toString();//民族
			String jobTitle = obj[15] == null ? "" : obj[15].toString();//职称
			String mainGrade = obj[16] == null ? "" : obj[16].toString();//主要教学学段
			String mainSubject = obj[17] == null ? "" : obj[17].toString();//主要教学学科
			String education = obj[18] == null ? "" : obj[18].toString();//学历
			
			String idcard = obj[19] == null ? "" : obj[19].toString();
			String graduation = obj[20] == null ? "" : obj[20].toString();
			String major = obj[21] == null ? "" : obj[21].toString();
//			String hiredate = obj[22] == null ? "" : obj[22].toString();
			String dipCode = obj[22] == null ? "" : obj[22].toString();
			String unitType = obj[23] == null ? "" : obj[23].toString();
			
			String t = "{\"id\":\"" + id + "\",\"cell\":[\"" + provinceName + "\",\"" + city + "\",\"" + county + "\",\"" + parentname + "\",\"" + projectName + "\",\"" + tunitName + "\",\"" + subjectName + "\",\""
					+ name + "\",\"" + idcard + "\",\"" + tel + "\",\"" + mail + "\",\"" + workpalce + "\",\"" + unitAttribute + "\",\"" + unitType + "\",\"" + folk + "\",\""+ jobTitle + "\",\""+ mainGrade + "\",\""+ mainSubject + "\",\""+ education + "\",\""
					+ graduation + "\",\"" + major + "\",\"" + dipCode + "\",\""
					+ tstatus + "\"]}";

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

	public void importList() {
		try {
			String sql = session.getAttribute("traineeCXsql").toString();
			List li = peProApplyNoService.executeSQL(sql);

//			HSSFWorkbook wb = new HSSFWorkbook();
//			HSSFSheet sheet1 = wb.createSheet("sheet1");
//			int i = 0;
//			HSSFRow rowheader = sheet1.createRow(i);
//			i++;
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet1 = wb.createSheet();
			wb.setSheetName(0, "Sheet1");
			int i = 0;
			XSSFRow rowheader = sheet1.createRow(i);
			i++;

			rowheader.createCell(0).setCellValue("所属地区（省）");
			rowheader.createCell(1).setCellValue("所属地区（市）");
			rowheader.createCell(2).setCellValue("所属地区（县）");
			rowheader.createCell(3).setCellValue("所属项目");
			rowheader.createCell(4).setCellValue("项目名称");
			rowheader.createCell(5).setCellValue("培训单位");
			rowheader.createCell(6).setCellValue("培训学科");

			rowheader.createCell(7).setCellValue("姓名");
			rowheader.createCell(8).setCellValue("身份证号");
			rowheader.createCell(9).setCellValue("手机");
			rowheader.createCell(10).setCellValue("电子邮件");
			rowheader.createCell(11).setCellValue("工作单位");
			rowheader.createCell(12).setCellValue("学校所在地区");
			rowheader.createCell(13).setCellValue("学校类别");
			rowheader.createCell(14).setCellValue("民族");
			rowheader.createCell(15).setCellValue("职称");
			rowheader.createCell(16).setCellValue("主要任教学段");
			rowheader.createCell(17).setCellValue("主要任教学科");
			rowheader.createCell(18).setCellValue("最高学历");
			rowheader.createCell(19).setCellValue("毕业院校");
			rowheader.createCell(20).setCellValue("所学专业");
//			rowheader.createCell(21).setCellValue("入职时间");
			rowheader.createCell(21).setCellValue("证书编号");
			rowheader.createCell(22).setCellValue("状态");

			for (int ti = 0; ti < li.size(); ti++) {
				Object[] obj = (Object[]) li.get(ti);
				XSSFRow row = sheet1.createRow(i);
				i++;

//				String provinceName = obj[1] == null ? "" : obj[1].toString();
//				String name = obj[2] == null ? "" : obj[2].toString();
//				String projectName = obj[3] == null ? "" : obj[3].toString();
//				String tunitName = obj[4] == null ? "" : obj[4].toString();
//				String subjectName = obj[5] == null ? "" : obj[5].toString();
//				String dipCode = obj[6] == null ? "" : obj[6].toString();
//				String parentname = obj[7] == null ? "" : obj[7].toString();
//
//				String tel = obj[8] == null ? "" : obj[8].toString();
//				String mail = obj[9] == null ? "" : obj[9].toString();
//				String workpalce = obj[10] == null ? "" : obj[10].toString();
//
//				String tstatus = obj[11].toString().equals("2") ? "已结业" : "未结业";
				
				String provinceName = obj[1] == null ? "" : obj[1].toString();
				String name = obj[2] == null ? "" : obj[2].toString();
				String projectName = obj[3] == null ? "" : obj[3].toString();
				String tunitName = obj[4] == null ? "" : obj[4].toString();
				String subjectName = obj[5] == null ? "" : obj[5].toString();
				String parentname = obj[6] == null ? "" : obj[6].toString();
				String tel = obj[7] == null ? "" : obj[7].toString();
				String mail = obj[8] == null ? "" : obj[8].toString();
				String workpalce = obj[9] == null ? "" : obj[9].toString();
				String tstatus = obj[10] == null ? "1" : obj[10].toString();
				if (tstatus.equals("1")) {
					tstatus = "未结业";
				} else if (tstatus.equals("2")) {
					tstatus = "已结业";
				}

				String city = obj[11] == null ? "" : obj[11].toString();//市
				String county = obj[12] == null ? "" : obj[12].toString();//县
				
				String unitAttribute = obj[13] == null ? "" : obj[13].toString();//单位属性
				String folk = obj[14] == null ? "" : obj[14].toString();//民族
				String jobTitle = obj[15] == null ? "" : obj[15].toString();//职称
				String mainGrade = obj[16] == null ? "" : obj[16].toString();//主要教学学段
				String mainSubject = obj[17] == null ? "" : obj[17].toString();//主要教学学科
				String education = obj[18] == null ? "" : obj[18].toString();//学历
				
				String idcard = obj[19] == null ? "" : obj[19].toString();
				String graduation = obj[20] == null ? "" : obj[20].toString();
				String major = obj[21] == null ? "" : obj[21].toString();
//				String hiredate = obj[22] == null ? "" : obj[22].toString();
				String dipCode = obj[22] == null ? "" : obj[22].toString();
				String unitType = obj[23] == null ? "" : obj[23].toString();
				

//				row.createCell(0).setCellValue(provinceName);
//				row.createCell(1).setCellValue(parentname);
//				row.createCell(2).setCellValue(projectName);
//				row.createCell(3).setCellValue(tunitName);
//				row.createCell(4).setCellValue(subjectName);
//
//				row.createCell(5).setCellValue(name);
//				row.createCell(6).setCellValue(tel);
//				row.createCell(7).setCellValue(mail);
//				row.createCell(8).setCellValue(workpalce);
//				row.createCell(9).setCellValue(dipCode);
//				row.createCell(10).setCellValue(tstatus);
				row.createCell(0).setCellValue(provinceName);
				row.createCell(1).setCellValue(city);
				row.createCell(2).setCellValue(county);
				row.createCell(3).setCellValue(parentname);
				row.createCell(4).setCellValue(projectName);
				row.createCell(5).setCellValue(tunitName);
				row.createCell(6).setCellValue(subjectName);

				row.createCell(7).setCellValue(name);
				row.createCell(8).setCellValue(idcard);
				row.createCell(9).setCellValue(tel);
				row.createCell(10).setCellValue(mail);
				row.createCell(11).setCellValue(workpalce);
				row.createCell(12).setCellValue(unitAttribute);
				row.createCell(13).setCellValue(unitType);
				row.createCell(14).setCellValue(folk);
				row.createCell(15).setCellValue(jobTitle);
				row.createCell(16).setCellValue(mainGrade);
				row.createCell(17).setCellValue(mainSubject);
				row.createCell(18).setCellValue(education);
				row.createCell(19).setCellValue(graduation);
				row.createCell(20).setCellValue(major);
//				row.createCell(20).setCellValue(hiredate);
				row.createCell(21).setCellValue(dipCode);
				row.createCell(22).setCellValue(tstatus);

			}

			String filename = "参训学员导出";
			filename = new String(filename.getBytes("GBK"), "ISO8859-1");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xlsx");
			response.setCharacterEncoding("UTF-8");

			OutputStream os = response.getOutputStream();
			os.flush();
			wb.write(os);
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void listjh() {

		String role = request.getParameter("role");
		String userid = request.getParameter("userid");
		String unitId = "";

		if (role != null && role.equals("1")) {
			String sql = "select u.fk_province from sso_user s join pe_manager p on s.login_id = p.login_id join pe_unit u on p.fk_unit=u.id " + "where s.login_id='" + userid + "'";
			List li = peProApplyNoService.executeSQL(sql);
			if (li.size() > 0) {
				unitId = li.get(0).toString();
			}
		}

		String provinceId = request.getParameter("provinceName");
		
		String cityId = request.getParameter("cityName") == null ? "all" : request.getParameter("cityName");
		String countyId = request.getParameter("countyName") == null ? "all" :  request.getParameter("countyName");
		
		String unitAttributeId = request.getParameter("unitAttributeName") == null ? "all" : request.getParameter("unitAttributeName");
		
		String unitTypeId = request.getParameter("unitTypeName") == null ? "all" : request.getParameter("unitTypeName");
		
		String folkId = request.getParameter("folkName") == null ? "all" :  request.getParameter("folkName");
		
		String jobTitleId = request.getParameter("jobTitleName") == null ? "all" : request.getParameter("jobTitleName");
		String mainGradeId = request.getParameter("mainGradeName") == null ? "all" :  request.getParameter("mainGradeName");
		
		String mainSubjectId = request.getParameter("mainSubjectName") == null ? "all" : request.getParameter("mainSubjectName");
		String educationId = request.getParameter("educationName") == null ? "all" :  request.getParameter("educationName");
		
		String idcards = request.getParameter("idcard") == null ? "" : request.getParameter("idcard");
		
		String projectId = request.getParameter("projectName");
		String parentId = request.getParameter("parentName");

		String unitName = request.getParameter("unitName");
		unitName = unitName == null ? "" : unitName;

		String subjectId = request.getParameter("subjectName");
		String tame = request.getParameter("name");

		String status = request.getParameter("status");
		status = status == null ? "0" : status;

		if (provinceId == null || provinceId.equals("all")) {
			if (unitId.length() > 0) {
				provinceId = unitId;
			} else {
				provinceId = "all";
			}
		}

		if (projectId == null) {
			projectId = "all";
		}
		if (parentId == null) {
			parentId = "all";
		}
		subjectId = subjectId == null ? "all" : subjectId;
		tame = tame == null ? "" : tame;

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

//		String sqlcount = "select count(*) " + " from JHTRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id " + " join PE_UNIT u on t.fk_training_unit = u.id " + " join PE_SUBJECT s on t.fk_subject = s.id " + " where 1=1 ";
//
//		String sql = "select t.id,p.name as provinceName ,t.name as traineename,po.name as projectName,u.name as unitName,s.name as subjectName ,po.parentname,t.telephone,t.email,t.work_place,t.status "
//				+ " from JHTRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id " + " join PE_UNIT u on t.fk_training_unit = u.id " + " join PE_SUBJECT s on t.fk_subject = s.id " + " where 1=1 ";

//		String sql = "select t.id,p.name as provinceName ,t.name as traineename,po.name as projectName,u.name as unitName,s.name as subjectName ,po.parentname,t.telephone,t.email,t.work_place,t.status,t.volk,t.idcard,t.property,t.mainteachinggrade"
//				+ ",t.mainteachingsubject,t.education,t.graduation,t.major,t.hiredate "
//				+ " from JHTRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id " + " join PE_UNIT u on t.fk_training_unit = u.id " + " join PE_SUBJECT s on t.fk_subject = s.id " + " where 1=1 ";
		
		String sqlcount = "select count(*) " + " from JHTRAINEE t join PE_PROVINCE p on t.fk_province = p.id left join city c on t.fk_city=c.id left join county co on t.fk_county=co.id join prochildapplyno po on t.fk_applyno = po.id " + " join PE_UNIT u on t.fk_training_unit = u.id " + " join PE_SUBJECT s on t.fk_subject = s.id " 
				+ " left join UNIT_ATTRIBUTE ua on t.fk_unitattribute=ua.id left join FOLK f on t.fk_folk=f.id left join JOB_TITLE jt on t.fk_jobtitle=jt.id"
				+ " left join MAIN_TEACHING_GRADE mtg on t.fk_main_teaching_grade=mtg.id left join MAIN_TEACHING_SUBJECT mts on t.fk_main_teaching_subject=mts.id "
				+ " left join EDUCATION edu on t.fk_education = edu.id left join UNIT_TYPE ut on t.fk_unit_type=ut.id "
				+ " where 1=1 ";

		String sql = "select t.id,p.name as provinceName ,t.name as traineename,po.name as projectName,u.name as unitName,s.name as subjectName ,po.parentname,t.telephone,t.email,t.work_place,t.status,c.name as cityName,co.name as countyName, "
				+ " ua.name as unitAttributeName,f.name as folkName,jt.name as jobTitleName,mtg.name as mainGradeName,mts.name as mainSubjectName,edu.name as educationName, "
				+ " t.idcard,t.graduation,t.major,ut.name as unitType "
				+ " from JHTRAINEE t join PE_PROVINCE p on t.fk_province = p.id left join city c on t.fk_city=c.id left join county co on t.fk_county=co.id join prochildapplyno po on t.fk_applyno = po.id " + " join PE_UNIT u on t.fk_training_unit = u.id " + " join PE_SUBJECT s on t.fk_subject = s.id " 
				+ " left join UNIT_ATTRIBUTE ua on t.fk_unitattribute=ua.id left join FOLK f on t.fk_folk=f.id left join JOB_TITLE jt on t.fk_jobtitle=jt.id"
				+ " left join MAIN_TEACHING_GRADE mtg on t.fk_main_teaching_grade=mtg.id left join MAIN_TEACHING_SUBJECT mts on t.fk_main_teaching_subject=mts.id "
				+ " left join EDUCATION edu on t.fk_education = edu.id left join UNIT_TYPE ut on t.fk_unit_type=ut.id "
				+ " where 1=1 ";

		
		
		if (!provinceId.equals("all")) {
			sql += " and p.id='" + provinceId + "' ";
			sqlcount += " and p.id='" + provinceId + "' ";
			if (!cityId.equals("all")) {
				sql += " and c.id='" + cityId + "' ";
				sqlcount += " and c.id='" + cityId + "' ";
				if (!countyId.equals("all")) {
					sql += " and co.id='" + countyId + "' ";
					sqlcount += " and co.id='" + countyId + "' ";
				}
			}
		}
		
		if (!unitAttributeId.equals("all")) {
			sql += " and ua.id='" + unitAttributeId + "' ";
			sqlcount += " and ua.id='" + unitAttributeId + "' ";
		}
		if (!unitTypeId.equals("all")) {
			sql += " and ut.id='" + unitTypeId + "' ";
			sqlcount += " and ut.id='" + unitTypeId + "' ";
		}
		if (!folkId.equals("all")) {
			sql += " and f.id='" + folkId + "' ";
			sqlcount += " and f.id='" + folkId + "' ";
		}
		if (!jobTitleId.equals("all")) {
			sql += " and jt.id='" + jobTitleId + "' ";
			sqlcount += " and jt.id='" + jobTitleId + "' ";
		}
		if (!mainGradeId.equals("all")) {
			sql += " and mtg.id='" + mainGradeId + "' ";
			sqlcount += " and mtg.id='" + mainGradeId + "' ";
		}
		if (!mainSubjectId.equals("all")) {
			sql += " and mts.id='" + mainSubjectId + "' ";
			sqlcount += " and mts.id='" + mainSubjectId + "' ";
		}
		if (!educationId.equals("all")) {
			sql += " and edu.id='" + educationId + "' ";
			sqlcount += " and edu.id='" + educationId + "' ";
		}
		
		if (!projectId.equals("all")) {
			sql += " and po.id='" + projectId + "' ";
			sqlcount += " and po.id='" + projectId + "' ";
		}
		if (!parentId.equals("all")) {
			sql += " and po.parentid='" + parentId + "' ";
			sqlcount += " and po.parentid='" + parentId + "' ";
		}
		if (!subjectId.equals("all")) {
			sql += " and t.fk_subject ='" + subjectId + "' ";
			sqlcount += " and t.fk_subject ='" + subjectId + "' ";
		}
		if (!tame.equals("")) {
			sql += " and t.name like'%" + tame + "%' ";
			sqlcount += " and t.name like'%" + tame + "%' ";
		}
		
		if (!idcards.equals("")) {
			sql += " and t.idcard like'%" + idcards + "%' ";
			sqlcount += " and t.idcard like'%" + idcards + "%' ";
		}
		
		if (!unitName.equals("")) {
			sql += " and u.name like'%" + unitName + "%' ";
			sqlcount += " and u.name like'%" + unitName + "%' ";
		}
		if (!status.equals("0")) {
			sql += " and t.status=" + status + " ";
			sqlcount += " and t.status=" + status + " ";
		}

		int totalCount = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
		int tol = totalCount / limit;
		if ((totalCount % limit) > 0) {
			tol += 1;
		}

		session.setAttribute("traineeJHSql", sql);

		List li = peProApplyNoService.getListPage(sql, start, limit);

		String cellString = "";

		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String id = obj[0].toString();
			String provinceName = obj[1] == null ? "" : obj[1].toString();
			String name = obj[2] == null ? "" : obj[2].toString();
			String projectName = obj[3] == null ? "" : obj[3].toString();
			String tunitName = obj[4] == null ? "" : obj[4].toString();
			String subjectName = obj[5] == null ? "" : obj[5].toString();
			String parentname = obj[6] == null ? "" : obj[6].toString();
			String tel = obj[7] == null ? "" : obj[7].toString();
			String mail = obj[8] == null ? "" : obj[8].toString();
			String workpalce = obj[9] == null ? "" : obj[9].toString();
			String tstatus = obj[10] == null ? "1" : obj[10].toString();
			if (tstatus.equals("1")) {
				tstatus = "未审核";
			} else if (tstatus.equals("2")) {
				tstatus = "通过";
			} else if (tstatus.equals("3")) {
				tstatus = "不通过";
			}

			String city = obj[11] == null ? "" : obj[11].toString();//市
			String county = obj[12] == null ? "" : obj[12].toString();//县
			
			String unitAttribute = obj[13] == null ? "" : obj[13].toString();//单位属性
			String folk = obj[14] == null ? "" : obj[14].toString();//民族
			String jobTitle = obj[15] == null ? "" : obj[15].toString();//职称
			String mainGrade = obj[16] == null ? "" : obj[16].toString();//主要教学学段
			String mainSubject = obj[17] == null ? "" : obj[17].toString();//主要教学学科
			String education = obj[18] == null ? "" : obj[18].toString();//学历
			
			String idcard = obj[19] == null ? "" : obj[19].toString();
			String graduation = obj[20] == null ? "" : obj[20].toString();
			String major = obj[21] == null ? "" : obj[21].toString();
			String unitType = obj[22] == null ? "" : obj[22].toString();
			
//			/*
//			 * 新增属性
//			 */
//			String volk = obj[11] == null ? "" : obj[11].toString();//民族
//			String idcard = obj[12] == null ? "" : obj[12].toString();//身份证号
//			String property = obj[13] == null ? "" : obj[13].toString();//单位属性
//			String mainTeachingGrade = obj[14] == null ? "" : obj[14].toString();//主要教学学段
//			String mainTeachingSubject = obj[15] == null ? "" : obj[15].toString();//主要教学学科
//			String education = obj[16] == null ? "" : obj[16].toString();//学历
//			String graduation = obj[17] == null ? "" : obj[17].toString();//毕业院校
//			String major = obj[18] == null ? "" : obj[18].toString();//专业
//			String hiredate = obj[19] == null ? "" : obj[19].toString();//入职时间
			
//			String t = "{\"id\":\"" + id + "\",\"cell\":[\"" + provinceName + "\",\"" + parentname + "\",\"" + projectName + "\",\"" + tunitName + "\",\"" + subjectName + "\",\""
//			+ name + "\",\"" + tel + "\",\"" + mail + "\",\"" + workpalce + "\",\"" + tstatus + "\"]}";
			String t = "{\"id\":\"" + id + "\",\"cell\":[\"" + provinceName + "\",\"" + city + "\",\"" + county + "\",\"" + parentname + "\",\"" + projectName + "\",\"" + tunitName + "\",\"" + subjectName + "\",\""
					+ name + "\",\"" + idcard + "\",\"" + tel + "\",\"" + mail + "\",\"" + workpalce + "\",\"" + unitAttribute + "\",\"" + unitType + "\",\"" + folk + "\",\""+ jobTitle + "\",\""+ mainGrade + "\",\""+ mainSubject + "\",\""+ education + "\",\""
					+ graduation + "\",\"" + major + "\",\""
					+ tstatus + "\"]}";
			
//			String t = "{\"id\":\"" + id + "\",\"cell\":[\"" + provinceName + "\",\"" + parentname + "\",\"" + projectName + "\",\"" + tunitName + "\",\"" + subjectName + "\",\""
//					+ name + "\",\"" + tel + "\",\"" + mail + "\",\"" + workpalce + "\",\"" + tstatus + "\",\"" + volk + "\",\"" + idcard + "\",\"" + property + "\",\"" + mainTeachingGrade + "\",\""
//					+ mainTeachingSubject + "\",\"" + education + "\",\"" + graduation + "\",\"" + major + "\",\"" + hiredate + "\"]}";

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

	public void importListJH() {

		String sql = session.getAttribute("traineeJHSql").toString();
		List li = peProApplyNoService.executeSQL(sql);

//		HSSFWorkbook wb = new HSSFWorkbook();
//		HSSFSheet sheet1 = wb.createSheet("sheet1");
//		int i = 0;
//		HSSFRow rowheader = sheet1.createRow((short) i);
//		i++;
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet();
		wb.setSheetName(0, "Sheet1");
		int i = 0;
		XSSFRow rowheader = sheet1.createRow(i);
		i++;
		
//		rowheader.createCell(0).setCellValue("省份");
//		rowheader.createCell(1).setCellValue("所属项目");
//		rowheader.createCell(2).setCellValue("项目名称");
//		rowheader.createCell(3).setCellValue("培训单位");
//		rowheader.createCell(4).setCellValue("培训学科");
//
//		rowheader.createCell(5).setCellValue("姓名");
//		rowheader.createCell(6).setCellValue("手机");
//		rowheader.createCell(7).setCellValue("电子邮件");
//		rowheader.createCell(8).setCellValue("工作单位");
//		rowheader.createCell(9).setCellValue("状态");
		rowheader.createCell(0).setCellValue("所属地区（省）");
		rowheader.createCell(1).setCellValue("所属地区（市）");
		rowheader.createCell(2).setCellValue("所属地区（县）");
		rowheader.createCell(3).setCellValue("所属项目");
		rowheader.createCell(4).setCellValue("项目名称");
		rowheader.createCell(5).setCellValue("培训单位");
		rowheader.createCell(6).setCellValue("培训学科");

		rowheader.createCell(7).setCellValue("姓名");
		rowheader.createCell(8).setCellValue("身份证号");
		rowheader.createCell(9).setCellValue("手机");
		rowheader.createCell(10).setCellValue("电子邮件");
		rowheader.createCell(11).setCellValue("工作单位");
		rowheader.createCell(12).setCellValue("学校所在区域");
		rowheader.createCell(13).setCellValue("学校类别");
		rowheader.createCell(14).setCellValue("民族");
		rowheader.createCell(15).setCellValue("职称");
		rowheader.createCell(16).setCellValue("主要教学学段");
		rowheader.createCell(17).setCellValue("主要教学学科");
		rowheader.createCell(18).setCellValue("学历");
		rowheader.createCell(19).setCellValue("毕业院校");
		rowheader.createCell(20).setCellValue("所学专业");
//		rowheader.createCell(20).setCellValue("入职时间");
		rowheader.createCell(21).setCellValue("状态");

		for (int ti = 0; ti < li.size(); ti++) {
			Object[] obj = (Object[]) li.get(ti);

			XSSFRow row = sheet1.createRow(i);
			i++;
//			String provinceName = obj[1].toString();
//			String name = obj[2].toString();
//			String projectName = obj[3].toString();
//			String tunitName = obj[4].toString();
//			String subjectName = obj[5].toString();
//			String parentname = obj[6].toString();
//			String tel = obj[7] == null ? "" : obj[7].toString();
//			String mail = obj[8] == null ? "" : obj[8].toString();
//			String workpalce = obj[9] == null ? "" : obj[9].toString();
//			String tstatus = obj[10] == null ? "1" : obj[10].toString();
//			if (tstatus.equals("1")) {
//				tstatus = "未审核";
//			} else if (tstatus.equals("2")) {
//				tstatus = "通过";
//			} else if (tstatus.equals("3")) {
//				tstatus = "不通过";
//			}

//			row.createCell(0).setCellValue(provinceName);
//			row.createCell(1).setCellValue(parentname);
//			row.createCell(2).setCellValue(projectName);
//			row.createCell(3).setCellValue(tunitName);
//			row.createCell(4).setCellValue(subjectName);
//
//			row.createCell(5).setCellValue(name);
//			row.createCell(6).setCellValue(tel);
//			row.createCell(7).setCellValue(mail);
//			row.createCell(8).setCellValue(workpalce);
//			row.createCell(9).setCellValue(tstatus);
			
			String provinceName = obj[1] == null ? "" : obj[1].toString();
			String name = obj[2] == null ? "" : obj[2].toString();
			String projectName = obj[3] == null ? "" : obj[3].toString();
			String tunitName = obj[4] == null ? "" : obj[4].toString();
			String subjectName = obj[5] == null ? "" : obj[5].toString();
			String parentname = obj[6] == null ? "" : obj[6].toString();
			String tel = obj[7] == null ? "" : obj[7].toString();
			String mail = obj[8] == null ? "" : obj[8].toString();
			String workpalce = obj[9] == null ? "" : obj[9].toString();
			String tstatus = obj[10] == null ? "1" : obj[10].toString();
			if (tstatus.equals("1")) {
				tstatus = "未审核";
			} else if (tstatus.equals("2")) {
				tstatus = "通过";
			} else if (tstatus.equals("3")) {
				tstatus = "不通过";
			}

			String city = obj[11] == null ? "" : obj[11].toString();//市
			String county = obj[12] == null ? "" : obj[12].toString();//县
			
			String unitAttribute = obj[13] == null ? "" : obj[13].toString();//单位属性
			String folk = obj[14] == null ? "" : obj[14].toString();//民族
			String jobTitle = obj[15] == null ? "" : obj[15].toString();//职称
			String mainGrade = obj[16] == null ? "" : obj[16].toString();//主要教学学段
			String mainSubject = obj[17] == null ? "" : obj[17].toString();//主要教学学科
			String education = obj[18] == null ? "" : obj[18].toString();//学历
			
			String idcard = obj[19] == null ? "" : obj[19].toString();
			String graduation = obj[20] == null ? "" : obj[20].toString();
			String major = obj[21] == null ? "" : obj[21].toString();
			String unitType = obj[22] == null ? "" : obj[22].toString();
			
			row.createCell(0).setCellValue(provinceName);
			row.createCell(1).setCellValue(city);
			row.createCell(2).setCellValue(county);
			row.createCell(3).setCellValue(parentname);
			row.createCell(4).setCellValue(projectName);
			row.createCell(5).setCellValue(tunitName);
			row.createCell(6).setCellValue(subjectName);

			row.createCell(7).setCellValue(name);
			row.createCell(8).setCellValue(idcard);
			row.createCell(9).setCellValue(tel);
			row.createCell(10).setCellValue(mail);
			row.createCell(11).setCellValue(workpalce);
			row.createCell(12).setCellValue(unitAttribute);
			row.createCell(13).setCellValue(unitType);
			row.createCell(14).setCellValue(folk);
			row.createCell(15).setCellValue(jobTitle);
			row.createCell(16).setCellValue(mainGrade);
			row.createCell(17).setCellValue(mainSubject);
			row.createCell(18).setCellValue(education);
			row.createCell(19).setCellValue(graduation);
			row.createCell(20).setCellValue(major);
//			row.createCell(20).setCellValue(hiredate);
			row.createCell(21).setCellValue(tstatus);

		}

		try {
			String filename = "计划学员导出";
			filename = new String(filename.getBytes("GBK"), "ISO8859-1");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xlsx");
			response.setCharacterEncoding("UTF-8");

			OutputStream os = response.getOutputStream();
			os.flush();
			wb.write(os);
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void adminTraineelv() {
		String provinceId = request.getParameter("provinceName");
		String parentId = request.getParameter("parentName");

		if (provinceId == null) {
			provinceId = "all";
		}
		if (parentId == null) {
			parentId = "all";
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

		String sqlcount = "select count(*) ";
		String sql = "select p.id ,po.parentid,p.name as provinceName,po.parentname,count(*) ";

		sql += "from TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id ";
		sqlcount += "from TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id ";

		sql += "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id ";
		sqlcount += "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id ";

		sql += "  where 1=1";
		sqlcount += "  where 1=1";

		if (!provinceId.equals("all")) {
			sql += " and p.id='" + provinceId + "' ";
			sqlcount += " and p.id='" + provinceId + "' ";
		}
		if (!parentId.equals("all")) {
			sql += " and po.parentid='" + parentId + "' ";
			sqlcount += " and po.parentid='" + parentId + "' ";
		}

		sql += " group by p.id,p.name,po.parentid,po.parentname";
		sqlcount += " group by p.id,p.name,po.parentid,po.parentname";

		List li = peProApplyNoService.executeSQL(sqlcount);
		String strCount = "0";
		if (li != null && li.size() > 0) {
			strCount = li.size()+"";
		}

		int totalCount = Integer.parseInt(strCount);
		int tol = totalCount / limit;
		if ((totalCount % limit) > 0) {
			tol += 1;
		}

		session.setAttribute("adminTraineelvSql", sql);

		li = peProApplyNoService.getListPage(sql, start, limit);

		String cellString = "";
		DecimalFormat df = new DecimalFormat();
		String style = "0.00%";
		df.applyPattern(style);

		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String tmprovinceId = obj[0].toString();
			String tmparentId = obj[1].toString();
			String provinceName = obj[2].toString();
			String parentName = obj[3].toString();
			int cxcount = Integer.valueOf(obj[4].toString());

			String tsql = "select count(*) " + "from JHTRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id " + "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id "
					+ "join prochildapply papp on po.id = papp.fk_applyno where 1=1 and p.id='" + tmprovinceId + "' and po.parentid='" + tmparentId + "'";

			int jhcount = Integer.parseInt(peProApplyNoService.executeSQL(tsql).get(0).toString());
//			if (jhcount == 0) {
//				jhcount = cxcount;
//			}

			float f = (float) cxcount / jhcount;

			String lv = df.format(f);

			String t = "{\"id\":\"" + tmprovinceId + "_" + tmparentId + "\",\"cell\":[\"" + provinceName + "\",\"" + parentName + "\",\"" + jhcount + "\",\"" + cxcount + "\",\"" + lv + "\"]}";

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
	
	/**
	 * 中西部项目维度人数统计
	 */
	public void adminTraineeNumForMW() {
		String parentId = request.getParameter("parent");
		String subjectId = request.getParameter("subject");

		if (parentId == null) {
			parentId = "all";
		}
		
		if (subjectId == null) {
			subjectId = "all";
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
		/*
		 * select count(*)  from TRAINEE t 
		 * join PE_PROVINCE p on t.fk_province = p.id 
		 * left join city c on t.fk_city=c.id 
		 * left join county co on t.fk_county=co.id 
		 * join prochildapplyno po on t.fk_applyno = po.id  
		 * join PE_UNIT u on t.fk_training_unit = u.id  
		 * join PE_SUBJECT s on t.fk_subject = s.id  
		 * left join UNIT_ATTRIBUTE ua on t.fk_unitattribute=ua.id 
		 * left join FOLK f on t.fk_folk=f.id 
		 * left join JOB_TITLE jt on t.fk_jobtitle=jt.id 
		 * left join MAIN_TEACHING_GRADE mtg on t.fk_main_teaching_grade=mtg.id 
		 * left join MAIN_TEACHING_SUBJECT mts on t.fk_main_teaching_subject=mts.id  
		 * left join EDUCATION edu on t.fk_education = edu.id 
		 * left join UNIT_TYPE ut on t.fk_unit_type=ut.id  
		 * where 1=1  and po.parentid='ff80808145d506840145efbb03560022' 
		 */
		String sqlcount = "select count(*) ";
		String sql = "select p.id ,p.name as provinceName,count(*) ";
		
		sql += "from TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id ";
		sqlcount += "from TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id ";
		
		sql += "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id ";
		sqlcount += "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id ";
		
		sql += " where 1=1";
		sqlcount += " where 1=1";
		
		if (!parentId.equals("all")) {
			String[] parentIds = parentId.split(",");
			if(!parentIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< parentIds.length; i++){
					parentID.append("'"+parentIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and po.parentid in " + parentID.toString() + " ";
				sqlcount += " and po.parentid in " + parentID.toString() + " ";
			}
			
		}
		
		if (!subjectId.equals("all")) {
			String[] subjectIds = subjectId.split(",");
			if(!subjectIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< subjectIds.length; i++){
					parentID.append("'"+subjectIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and t.fk_subject in " + parentID.toString() + " ";
				sqlcount += " and t.fk_subject in " + parentID.toString() + " ";
			}
			
		}
		
		sql += " group by p.id,p.name,p.code";
		sqlcount += " group by p.id,p.name,p.code";
		
		sql += " order by p.code ";
		sqlcount += " order by p.code ";
		
		List li = peProApplyNoService.executeSQL(sqlcount);
		
		String strCount = "0";
		if (li != null && li.size() > 0) {
			strCount = li.size()+"";
		}
		
		int totalCount = Integer.parseInt(strCount);
		int tol = totalCount / limit;
		if ((totalCount % limit) > 0) {
			tol += 1;
		}
		
		
		li = peProApplyNoService.getListPage(sql, start, limit);
		
		String cellString = "";
		
		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String tmprovinceId = obj[0].toString();
			String provinceName = obj[1].toString();
			int cxcount = Integer.valueOf(obj[2].toString());
	
			String tsql = "select count(*) " + "from JHTRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id " + "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id "
					+ " where 1=1 and p.id='" + tmprovinceId  + "'";
			
			if (!parentId.equals("all")) {
				String[] parentIds = parentId.split(",");
				if(!parentIds[0].equals("all")){
					StringBuilder parentID = new StringBuilder();
					parentID.append("(");
					for(int j = 0; j< parentIds.length; j++){
						parentID.append("'"+parentIds[j]+"',");
					}
					parentID.delete(parentID.length()-1, parentID.length());
					parentID.append(")");
					
					tsql += " and po.parentid in " + parentID.toString() + " ";
				}
				
			}
			
			if (!subjectId.equals("all")) {
				String[] subjectIds = subjectId.split(",");
				if(!subjectIds[0].equals("all")){
					StringBuilder parentID = new StringBuilder();
					parentID.append("(");
					for(int j = 0; j< subjectIds.length; j++){
						parentID.append("'"+subjectIds[j]+"',");
					}
					parentID.delete(parentID.length()-1, parentID.length());
					parentID.append(")");
					
					tsql += " and t.fk_subject in " + parentID.toString() + " ";
				}
				
			}
			
	
			int jhcount = Integer.parseInt(peProApplyNoService.executeSQL(tsql).get(0).toString());
//			if (jhcount == 0) {
//				jhcount = cxcount;
//			}
			
			String tsql1 = "select count(*) " + "from TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id " + "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id "
					+ " where 1=1 and p.id='" + tmprovinceId + "' and t.status=2";
			
			
			if (!parentId.equals("all")) {
				String[] parentIds = parentId.split(",");
				if(!parentIds[0].equals("all")){
					StringBuilder parentID = new StringBuilder();
					parentID.append("(");
					for(int j = 0; j< parentIds.length; j++){
						parentID.append("'"+parentIds[j]+"',");
					}
					parentID.delete(parentID.length()-1, parentID.length());
					parentID.append(")");
					
					tsql1 += " and po.parentid in " + parentID.toString() + " ";
				}
				
			}
			
			if (!subjectId.equals("all")) {
				String[] subjectIds = subjectId.split(",");
				if(!subjectIds[0].equals("all")){
					StringBuilder parentID = new StringBuilder();
					parentID.append("(");
					for(int j = 0; j< subjectIds.length; j++){
						parentID.append("'"+subjectIds[j]+"',");
					}
					parentID.delete(parentID.length()-1, parentID.length());
					parentID.append(")");
					
					tsql1 += " and t.fk_subject in " + parentID.toString() + " ";
				}
				
			}
			
			
			int jycount = Integer.parseInt(peProApplyNoService.executeSQL(tsql1).get(0).toString());
	
			String t = "{\"id\":\"" + tmprovinceId  + "\",\"cell\":[\"" + provinceName + "\",\"" + jhcount + "\",\"" + cxcount + "\",\"" + jycount + "\"]}";
	
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
	
	/**
	 * 中西部项目维度人数统计导出
	 */
	public void importTraineeNumForMW() {
		

		String parentId = request.getParameter("parent");
		String subjectId = request.getParameter("subject");

		if (parentId == null) {
			parentId = "all";
		}
		
		if (subjectId == null) {
			subjectId = "all";
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
		
		String sql = "select p.id ,p.name as provinceName,count(*) ";
		
		sql += "from TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id ";
		
		sql += "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id ";
		
		sql += " where 1=1";
		
		if (!parentId.equals("all")) {
			String[] parentIds = parentId.split(",");
			if(!parentIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< parentIds.length; i++){
					parentID.append("'"+parentIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and po.parentid in " + parentID.toString() + " ";
			}
			
		}
		
		if (!subjectId.equals("all")) {
			String[] subjectIds = subjectId.split(",");
			if(!subjectIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< subjectIds.length; i++){
					parentID.append("'"+subjectIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and t.fk_subject in " + parentID.toString() + " ";
			}
			
		}
		
		sql += " group by p.id,p.name,p.code";
		
		sql += " order by p.code ";
		
		
		List li = peProApplyNoService.getListPage(sql, start, limit);
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet("sheet1");
		int k = 0;
		HSSFRow rowheader = sheet1.createRow((short) k);
		k++;
		
		rowheader.createCell(0).setCellValue("省份");
		rowheader.createCell(1).setCellValue("计划人数");
		rowheader.createCell(2).setCellValue("参训人数");
		rowheader.createCell(3).setCellValue("结业人数");

		int countjh = 0;
		int countcx = 0;
		int countjy = 0;
		
		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String tmprovinceId = obj[0].toString();
			String provinceName = obj[1].toString();
			int cxcount = Integer.valueOf(obj[2].toString());
	
			String tsql = "select count(*) " + "from JHTRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id " + "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id "
					+ " where 1=1 and p.id='" + tmprovinceId  + "'";
			
			if (!parentId.equals("all")) {
				String[] parentIds = parentId.split(",");
				if(!parentIds[0].equals("all")){
					StringBuilder parentID = new StringBuilder();
					parentID.append("(");
					for(int j = 0; j< parentIds.length; j++){
						parentID.append("'"+parentIds[j]+"',");
					}
					parentID.delete(parentID.length()-1, parentID.length());
					parentID.append(")");
					
					tsql += " and po.parentid in " + parentID.toString() + " ";
				}
				
			}
			
			if (!subjectId.equals("all")) {
				String[] subjectIds = subjectId.split(",");
				if(!subjectIds[0].equals("all")){
					StringBuilder parentID = new StringBuilder();
					parentID.append("(");
					for(int j = 0; j< subjectIds.length; j++){
						parentID.append("'"+subjectIds[j]+"',");
					}
					parentID.delete(parentID.length()-1, parentID.length());
					parentID.append(")");
					
					tsql += " and t.fk_subject in " + parentID.toString() + " ";
				}
				
			}
			
	
			int jhcount = Integer.parseInt(peProApplyNoService.executeSQL(tsql).get(0).toString());
//			if (jhcount == 0) {
//				jhcount = cxcount;
//			}
			
			String tsql1 = "select count(*) " + "from TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id " + "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id "
					+ " where 1=1 and p.id='" + tmprovinceId + "' and t.status=2";
			
			
			if (!parentId.equals("all")) {
				String[] parentIds = parentId.split(",");
				if(!parentIds[0].equals("all")){
					StringBuilder parentID = new StringBuilder();
					parentID.append("(");
					for(int j = 0; j< parentIds.length; j++){
						parentID.append("'"+parentIds[j]+"',");
					}
					parentID.delete(parentID.length()-1, parentID.length());
					parentID.append(")");
					
					tsql1 += " and po.parentid in " + parentID.toString() + " ";
				}
				
			}
			
			if (!subjectId.equals("all")) {
				String[] subjectIds = subjectId.split(",");
				if(!subjectIds[0].equals("all")){
					StringBuilder parentID = new StringBuilder();
					parentID.append("(");
					for(int j = 0; j< subjectIds.length; j++){
						parentID.append("'"+subjectIds[j]+"',");
					}
					parentID.delete(parentID.length()-1, parentID.length());
					parentID.append(")");
					
					tsql1 += " and t.fk_subject in " + parentID.toString() + " ";
				}
				
			}
			
			
			int jycount = Integer.parseInt(peProApplyNoService.executeSQL(tsql1).get(0).toString());
	
			HSSFRow row = sheet1.createRow((short) k);
			k++;
			
			row.createCell(0).setCellValue(provinceName);
			row.createCell(1).setCellValue(jhcount);
			row.createCell(2).setCellValue(cxcount);
			row.createCell(3).setCellValue(jycount);
			
			countjh += jhcount;
			countcx += cxcount;
			countjy += jycount;

		}
		HSSFRow row = sheet1.createRow((short) k);
		
		row.createCell(0).setCellValue("合计");
		row.createCell(1).setCellValue(countjh);
		row.createCell(2).setCellValue(countcx);
		row.createCell(3).setCellValue(countjy);
		
		try {
			String filename = "中西部和幼师国培项目维度人数统计导出";
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
	
	/**
	 * 中西部贫困县维度统计
	 */
	public void adminTraineeNumForMWPKX() {
		String parentId = request.getParameter("parent");
		String subjectId = request.getParameter("subject");

		if (parentId == null) {
			parentId = "all";
		}
		
		if (subjectId == null) {
			subjectId = "all";
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
		
		
		String sqlcount = "select count(*) ";
		String sql = "select p.id ,p.name as provinceName,count(*) ";
		
		sql += "from TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id ";
		sqlcount += "from TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id ";
		
		sql += "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id ";
		sqlcount += "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id ";
		
		sql += " left join COUNTY co on t.fk_county = co.id ";
		sqlcount += " left join COUNTY co on t.fk_county = co.id ";
		
		sql += "  where 1=1 and co.is_poor is not null and co.is_countrypoor is not null";
		sqlcount += "  where 1=1 and co.is_poor is not null and co.is_countrypoor is not null";
		
		if (!parentId.equals("all")) {
			String[] parentIds = parentId.split(",");
			if(!parentIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< parentIds.length; i++){
					parentID.append("'"+parentIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and po.parentid in " + parentID.toString() + " ";
				sqlcount += " and po.parentid in " + parentID.toString() + " ";
			}
			
		}
		
		if (!subjectId.equals("all")) {
			String[] subjectIds = subjectId.split(",");
			if(!subjectIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< subjectIds.length; i++){
					parentID.append("'"+subjectIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and t.fk_subject in " + parentID.toString() + " ";
				sqlcount += " and t.fk_subject in " + parentID.toString() + " ";
			}
			
		}
		
		sql += " group by p.id,p.name,p.code";
		sqlcount += " group by p.id,p.name,p.code";
		
		sql += " order by p.code ";
		sqlcount += " order by p.code ";
		
		List li = peProApplyNoService.executeSQL(sqlcount);
		
		String strCount = "0";
		if (li != null && li.size() > 0) {
			strCount = li.size()+"";
		}
		
		int totalCount = Integer.parseInt(strCount);
		int tol = totalCount / limit;
		if ((totalCount % limit) > 0) {
			tol += 1;
		}
		
		
		li = peProApplyNoService.getListPage(sql, start, limit);
		
		String cellString = "";
		
		DecimalFormat df = new DecimalFormat();
		String style = "0.00%";
		df.applyPattern(style);
		
		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String tmprovinceId = obj[0].toString();
			String provinceName = obj[1].toString();
			int cxcount = Integer.valueOf(obj[2].toString());
	
			
			String tsql1 = "select co.is_poor as poor,co.is_countrypoor as countrypoor " 
					+ " from TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id "
					+ " left join COUNTY co on t.fk_county = co.id " 
					+ " left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id "
					+ "  where 1=1 and p.id='" + tmprovinceId + "' and co.is_poor is not null and co.is_countrypoor is not null";
			
			if (!parentId.equals("all")) {
				String[] parentIds = parentId.split(",");
				if(!parentIds[0].equals("all")){
					StringBuilder parentID = new StringBuilder();
					parentID.append("(");
					for(int j = 0; j< parentIds.length; j++){
						parentID.append("'"+parentIds[j]+"',");
					}
					parentID.delete(parentID.length()-1, parentID.length());
					parentID.append(")");
					
					tsql1 += " and po.parentid in " + parentID.toString() + " ";
				}
				
			}
			
			if (!subjectId.equals("all")) {
				String[] subjectIds = subjectId.split(",");
				if(!subjectIds[0].equals("all")){
					StringBuilder parentID = new StringBuilder();
					parentID.append("(");
					for(int j = 0; j< subjectIds.length; j++){
						parentID.append("'"+subjectIds[j]+"',");
					}
					parentID.delete(parentID.length()-1, parentID.length());
					parentID.append(")");
					
					tsql1 += " and t.fk_subject in " + parentID.toString() + " ";
				}
				
			}
			String jzsql = "select count(*) from ("+tsql1+")  newt where newt.poor='是'";
			String jzsql1 = "select count(*) from ("+tsql1+") newt where newt.countrypoor='是'";
			String jzsql2 = "select count(*) from ("+tsql1+") newt where (newt.poor='是' or newt.countrypoor='是')";
			int poorcount = Integer.parseInt(peProApplyNoService.executeSQL(jzsql).get(0).toString());
			
			int cpoorcount = Integer.parseInt(peProApplyNoService.executeSQL(jzsql1).get(0).toString());
			
			int totals = Integer.parseInt(peProApplyNoService.executeSQL(jzsql2).get(0).toString());
			
	
			float f1 = (float) poorcount / cxcount;
			float f2 = (float) cpoorcount / cxcount;
			float f3 = (float) totals / cxcount;
	
			String lv1 = df.format(f1);
			String lv2 = df.format(f2);
			String lv3 = df.format(f3);
	
			String t = "{\"id\":\"" + tmprovinceId  + "\",\"cell\":[\"" + provinceName + "\",\"" + cxcount + "\",\"" + poorcount + "\",\"" + lv1 + "\",\"" + cpoorcount + "\",\"" + lv2 + "\",\"" + totals + "\",\"" + lv3 + "\"]}";
	
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
	
	/**
	 * 中西部贫困县维度统计导出
	 */
	public void importTraineeNumForPKX() {
		

		String parentId = request.getParameter("parent");
		String subjectId = request.getParameter("subject");

		if (parentId == null) {
			parentId = "all";
		}
		
		if (subjectId == null) {
			subjectId = "all";
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
		
		String sql = "select p.id ,p.name as provinceName,count(*) ";
		
		sql += "from TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id ";
		
		sql += "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id ";
		
		sql += " left join COUNTY co on t.fk_county = co.id ";
		
		sql += "  where 1=1 and co.is_poor is not null and co.is_countrypoor is not null";
		
		if (!parentId.equals("all")) {
			String[] parentIds = parentId.split(",");
			if(!parentIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< parentIds.length; i++){
					parentID.append("'"+parentIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and po.parentid in " + parentID.toString() + " ";
			}
			
		}
		
		if (!subjectId.equals("all")) {
			String[] subjectIds = subjectId.split(",");
			if(!subjectIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< subjectIds.length; i++){
					parentID.append("'"+subjectIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and t.fk_subject in " + parentID.toString() + " ";
			}
			
		}
		
		sql += " group by p.id,p.name,p.code";
		
		sql += " order by p.code ";
		
		
		List li = peProApplyNoService.getListPage(sql, start, limit);
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet("sheet1");
		int k = 0;
		HSSFRow rowheader = sheet1.createRow((short) k);
		k++;
		
		rowheader.createCell(0).setCellValue("省份");
		rowheader.createCell(1).setCellValue("总人数");
		rowheader.createCell(2).setCellValue("集中连片特困地区县人数");
		rowheader.createCell(3).setCellValue("占比");
		rowheader.createCell(4).setCellValue("国家级贫困县人数");
		rowheader.createCell(5).setCellValue("占比");
		rowheader.createCell(6).setCellValue("特困地区/贫困县合计人数");
		rowheader.createCell(7).setCellValue("占比");

		int countz = 0;
		int countpkx = 0;
		int countpkgj = 0;
		int countpkhj = 0;
		
		
		DecimalFormat df = new DecimalFormat();
		String style = "0.00%";
		df.applyPattern(style);
		
		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String tmprovinceId = obj[0].toString();
			String provinceName = obj[1].toString();
			int cxcount = Integer.valueOf(obj[2].toString());
	
			
			String tsql1 = "select co.is_poor as poor,co.is_countrypoor as countrypoor " 
					+ " from TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id "
					+ " left join COUNTY co on t.fk_county = co.id " 
					+ " left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id "
					+ "  where 1=1 and p.id='" + tmprovinceId + "' and co.is_poor is not null and co.is_countrypoor is not null";
			
			if (!parentId.equals("all")) {
				String[] parentIds = parentId.split(",");
				if(!parentIds[0].equals("all")){
					StringBuilder parentID = new StringBuilder();
					parentID.append("(");
					for(int j = 0; j< parentIds.length; j++){
						parentID.append("'"+parentIds[j]+"',");
					}
					parentID.delete(parentID.length()-1, parentID.length());
					parentID.append(")");
					
					tsql1 += " and po.parentid in " + parentID.toString() + " ";
				}
				
			}
			
			if (!subjectId.equals("all")) {
				String[] subjectIds = subjectId.split(",");
				if(!subjectIds[0].equals("all")){
					StringBuilder parentID = new StringBuilder();
					parentID.append("(");
					for(int j = 0; j< subjectIds.length; j++){
						parentID.append("'"+subjectIds[j]+"',");
					}
					parentID.delete(parentID.length()-1, parentID.length());
					parentID.append(")");
					
					tsql1 += " and t.fk_subject in " + parentID.toString() + " ";
				}
				
			}
			String jzsql = "select count(*) from ("+tsql1+")  newt where newt.poor='是'";
			String jzsql1 = "select count(*) from ("+tsql1+") newt where newt.countrypoor='是'";
			String jzsql2 = "select count(*) from ("+tsql1+") newt where (newt.poor='是' or newt.countrypoor='是')";
			int poorcount = Integer.parseInt(peProApplyNoService.executeSQL(jzsql).get(0).toString());
			
			int cpoorcount = Integer.parseInt(peProApplyNoService.executeSQL(jzsql1).get(0).toString());
			
			int totals = Integer.parseInt(peProApplyNoService.executeSQL(jzsql2).get(0).toString());
			
	
			float f1 = (float) poorcount / cxcount;
			float f2 = (float) cpoorcount / cxcount;
			float f3 = (float) totals / cxcount;
	
			String lv1 = df.format(f1);
			String lv2 = df.format(f2);
			String lv3 = df.format(f3);
	
//			String t = "{\"id\":\"" + tmprovinceId  + "\",\"cell\":[\"" + provinceName + "\",\"" + cxcount + "\",\"" + poorcount + "\",\"" + lv1 + "\",\"" + cpoorcount + "\",\"" + lv2 + "\",\"" + totals + "\",\"" + lv3 + "\"]}";
//	
//			cellString += t + ",";
	
			HSSFRow row = sheet1.createRow((short) k);
			k++;
			
			row.createCell(0).setCellValue(provinceName);
			row.createCell(1).setCellValue(cxcount);
			row.createCell(2).setCellValue(poorcount);
			row.createCell(3).setCellValue(lv1);
			row.createCell(4).setCellValue(cpoorcount);
			row.createCell(5).setCellValue(lv2);
			row.createCell(6).setCellValue(totals);
			row.createCell(7).setCellValue(lv3);
			
			countz += cxcount;
			countpkx += poorcount;
			countpkgj += cpoorcount;
			countpkhj += totals;

		}
		

		
		HSSFRow row = sheet1.createRow((short) k);
		
		row.createCell(0).setCellValue("合计");
		row.createCell(1).setCellValue(countz);
		row.createCell(2).setCellValue(countpkx);
		row.createCell(4).setCellValue(countpkgj);
		row.createCell(6).setCellValue(countpkhj);
		
		float f4 = (float) countpkx / countz;
		float f5 = (float) countpkgj / countz;
		float f6 = (float) countpkhj / countz;
		
		String lv4 = df.format(f4);
		String lv5 = df.format(f5);
		String lv6 = df.format(f6);
		
		row.createCell(3).setCellValue(lv4);
		row.createCell(5).setCellValue(lv5);
		row.createCell(7).setCellValue(lv6);
		
		try {
			String filename = "中西部和幼师国培贫困县维度人数统计导出";
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
	
	/**
	 * 中西部学校所在区域维度人数统计
	 */
	public void adminTraineeNumForMWQY() {
		String parentId = request.getParameter("parent");
		String subjectId = request.getParameter("subject");

		if (parentId == null) {
			parentId = "all";
		}
		
		if (subjectId == null) {
			subjectId = "all";
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
		
		
		String sqlcount = "select count(*) ";
		String sql = "select p.id ,p.name as provinceName,count(*) ";
		
		sql += "from TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id ";
		sqlcount += "from TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id ";
		
		sql += "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id ";
		sqlcount += "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id ";
		
		sql += " left join UNIT_ATTRIBUTE ua on t.fk_unitattribute = ua.id ";
		sqlcount += " left join UNIT_ATTRIBUTE ua on t.fk_unitattribute = ua.id ";
		
		sql += "  where 1=1 and t.fk_unitattribute is not null";
		sqlcount += "  where 1=1 and t.fk_unitattribute is not null";
		
		if (!parentId.equals("all")) {
			String[] parentIds = parentId.split(",");
			if(!parentIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< parentIds.length; i++){
					parentID.append("'"+parentIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and po.parentid in " + parentID.toString() + " ";
				sqlcount += " and po.parentid in " + parentID.toString() + " ";
			}
			
		}
		
		if (!subjectId.equals("all")) {
			String[] subjectIds = subjectId.split(",");
			if(!subjectIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< subjectIds.length; i++){
					parentID.append("'"+subjectIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and t.fk_subject in " + parentID.toString() + " ";
				sqlcount += " and t.fk_subject in " + parentID.toString() + " ";
			}
			
		}
		
		sql += " group by p.id,p.name,p.code";
		sqlcount += " group by p.id,p.name,p.code";
		
		sql += " order by p.code ";
		sqlcount += " order by p.code ";
		
		List li = peProApplyNoService.executeSQL(sqlcount);
		
		String strCount = "0";
		if (li != null && li.size() > 0) {
			strCount = li.size()+"";
		}
		
		int totalCount = Integer.parseInt(strCount);
		int tol = totalCount / limit;
		if ((totalCount % limit) > 0) {
			tol += 1;
		}
		
		
		li = peProApplyNoService.getListPage(sql, start, limit);
		
		String cellString = "";
		
		DecimalFormat df = new DecimalFormat();
		String style = "0.00%";
		df.applyPattern(style);
		
		String nsql = "select t.id from UNIT_ATTRIBUTE t where 1=1 order by t.NAME";
		
		List listn = peProApplyNoService.getListPage(nsql, -1, -1);
		
		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String tmprovinceId = obj[0].toString();
			String provinceName = obj[1].toString();
			int cxcount = Integer.valueOf(obj[2].toString());
	
			
			
			int count1 = 0;
			int count2 = 0;
			int count3 = 0;
			int count4 = 0;
			int count5 = 0;
			int count6 = 0;
			if(listn != null && listn.size() >0){
				String nid = listn.get(0).toString();
				String nid2 = listn.get(1).toString();
				String nid3 = listn.get(2).toString();
				String nid4 = listn.get(3).toString();
				String nid5 = listn.get(4).toString();
				String nid6 = listn.get(5).toString();
				
				String tsql1 = getSql(tmprovinceId,nid);
				
				String tsql2 = getSql(tmprovinceId,nid2);
				String tsql3 = getSql(tmprovinceId,nid3);
				String tsql4 = getSql(tmprovinceId,nid4);
				String tsql5 = getSql(tmprovinceId,nid5);
				String tsql6 = getSql(tmprovinceId,nid6);
				
				if (!parentId.equals("all")) {
					String[] parentIds = parentId.split(",");
					if(!parentIds[0].equals("all")){
						StringBuilder parentID = new StringBuilder();
						parentID.append("(");
						for(int j = 0; j< parentIds.length; j++){
							parentID.append("'"+parentIds[j]+"',");
						}
						parentID.delete(parentID.length()-1, parentID.length());
						parentID.append(")");
						
						tsql1 += " and po.parentid in " + parentID.toString() + " ";
						tsql2 += " and po.parentid in " + parentID.toString() + " ";
						tsql3 += " and po.parentid in " + parentID.toString() + " ";
						tsql4 += " and po.parentid in " + parentID.toString() + " ";
						tsql5 += " and po.parentid in " + parentID.toString() + " ";
						tsql6 += " and po.parentid in " + parentID.toString() + " ";
					}
					
				}
				
				if (!subjectId.equals("all")) {
					String[] subjectIds = subjectId.split(",");
					if(!subjectIds[0].equals("all")){
						StringBuilder parentID = new StringBuilder();
						parentID.append("(");
						for(int j = 0; j< subjectIds.length; j++){
							parentID.append("'"+subjectIds[j]+"',");
						}
						parentID.delete(parentID.length()-1, parentID.length());
						parentID.append(")");
						
						tsql1 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql2 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql3 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql4 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql5 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql6 += " and t.fk_subject in " + parentID.toString() + " ";
					}
					
				}
				
				count1 = Integer.parseInt(peProApplyNoService.executeSQL(tsql1).get(0).toString());
				count2 = Integer.parseInt(peProApplyNoService.executeSQL(tsql2).get(0).toString());
				count3 = Integer.parseInt(peProApplyNoService.executeSQL(tsql3).get(0).toString());
				count4 = Integer.parseInt(peProApplyNoService.executeSQL(tsql4).get(0).toString());
				count5 = Integer.parseInt(peProApplyNoService.executeSQL(tsql5).get(0).toString());
				count6 = Integer.parseInt(peProApplyNoService.executeSQL(tsql6).get(0).toString());
				
				
			}
			
			
	
			float f1 = (float) count1 / cxcount;
			float f2 = (float) count2 / cxcount;
			float f3 = (float) count3 / cxcount;
			float f4 = (float) count4 / cxcount;
			float f5 = (float) count5 / cxcount;
			float f6 = (float) count6 / cxcount;
	
			String lv1 = df.format(f1);
			String lv2 = df.format(f2);
			String lv3 = df.format(f3);
			String lv4 = df.format(f4);
			String lv5 = df.format(f5);
			String lv6 = df.format(f6);
	
			String t = "{\"id\":\"" + tmprovinceId  + "\",\"cell\":[\"" + provinceName + "\",\"" + cxcount + "\",\"" + count1 + "\",\"" + lv1 + "\",\"" + count2 + "\",\"" + lv2 + "\",\"" + count3 + "\",\"" + lv3 + "\",\"" + count4 + "\",\"" + lv4 + "\",\"" + count5 + "\",\"" + lv5 + "\",\""+count6+"\",\""+lv6+"\"]}";
	
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
	
	/**
	 * 中西部学校所在区域维度人数统计导出
	 */
	public void importTraineeNumForMWQY() {
		

		String parentId = request.getParameter("parent");
		String subjectId = request.getParameter("subject");

		if (parentId == null) {
			parentId = "all";
		}
		
		if (subjectId == null) {
			subjectId = "all";
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
		
		String sql = "select p.id ,p.name as provinceName,count(*) ";
		
		sql += "from TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id ";
		
		sql += "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id ";
		
		sql += " left join UNIT_ATTRIBUTE ua on t.fk_unitattribute = ua.id ";
		
		sql += "  where 1=1 and t.fk_unitattribute is not null";
		
		if (!parentId.equals("all")) {
			String[] parentIds = parentId.split(",");
			if(!parentIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< parentIds.length; i++){
					parentID.append("'"+parentIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and po.parentid in " + parentID.toString() + " ";
			}
			
		}
		
		if (!subjectId.equals("all")) {
			String[] subjectIds = subjectId.split(",");
			if(!subjectIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< subjectIds.length; i++){
					parentID.append("'"+subjectIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and t.fk_subject in " + parentID.toString() + " ";
			}
			
		}
		
		sql += " group by p.id,p.name,p.code";
		
		sql += " order by p.code ";
		
		
		List li = peProApplyNoService.getListPage(sql, start, limit);
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet("sheet1");
		int k = 0;
		HSSFRow rowheader = sheet1.createRow((short) k);
		k++;
		
		rowheader.createCell(0).setCellValue("省份");
		rowheader.createCell(1).setCellValue("总人数");
		rowheader.createCell(2).setCellValue("城市人数");
		rowheader.createCell(3).setCellValue("占比");
		rowheader.createCell(4).setCellValue("县城人数");
		rowheader.createCell(5).setCellValue("占比");
		rowheader.createCell(6).setCellValue("镇区（不含县城）人数");
		rowheader.createCell(7).setCellValue("占比");
		rowheader.createCell(8).setCellValue("乡人数");
		rowheader.createCell(9).setCellValue("占比");
		rowheader.createCell(10).setCellValue("村人数");
		rowheader.createCell(11).setCellValue("占比");
		rowheader.createCell(12).setCellValue("团场人数");
		rowheader.createCell(13).setCellValue("占比");

		int countz = 0;
		int countcs = 0;
		int countpxc = 0;
		int countpzq = 0;
		int countpx = 0;
		int countpc = 0;
		int countptc = 0;
		
		DecimalFormat df = new DecimalFormat();
		String style = "0.00%";
		df.applyPattern(style);
		
		String nsql = "select t.id from UNIT_ATTRIBUTE t where 1=1 order by t.NAME";
		
		List listn = peProApplyNoService.getListPage(nsql, -1, -1);
		
		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String tmprovinceId = obj[0].toString();
			String provinceName = obj[1].toString();
			int cxcount = Integer.valueOf(obj[2].toString());
	
			
			
			int count1 = 0;
			int count2 = 0;
			int count3 = 0;
			int count4 = 0;
			int count5 = 0;
			int count6 = 0;
			if(listn != null && listn.size() >0){
				String nid = listn.get(0).toString();
				String nid2 = listn.get(1).toString();
				String nid3 = listn.get(2).toString();
				String nid4 = listn.get(3).toString();
				String nid5 = listn.get(4).toString();
				String nid6 = listn.get(5).toString();
				
				String tsql1 = getSql(tmprovinceId,nid);
				
				String tsql2 = getSql(tmprovinceId,nid2);
				String tsql3 = getSql(tmprovinceId,nid3);
				String tsql4 = getSql(tmprovinceId,nid4);
				String tsql5 = getSql(tmprovinceId,nid5);
				String tsql6 = getSql(tmprovinceId,nid6);
				
				if (!parentId.equals("all")) {
					String[] parentIds = parentId.split(",");
					if(!parentIds[0].equals("all")){
						StringBuilder parentID = new StringBuilder();
						parentID.append("(");
						for(int j = 0; j< parentIds.length; j++){
							parentID.append("'"+parentIds[j]+"',");
						}
						parentID.delete(parentID.length()-1, parentID.length());
						parentID.append(")");
						
						tsql1 += " and po.parentid in " + parentID.toString() + " ";
						tsql2 += " and po.parentid in " + parentID.toString() + " ";
						tsql3 += " and po.parentid in " + parentID.toString() + " ";
						tsql4 += " and po.parentid in " + parentID.toString() + " ";
						tsql5 += " and po.parentid in " + parentID.toString() + " ";
						tsql6 += " and po.parentid in " + parentID.toString() + " ";
					}
					
				}
				
				if (!subjectId.equals("all")) {
					String[] subjectIds = subjectId.split(",");
					if(!subjectIds[0].equals("all")){
						StringBuilder parentID = new StringBuilder();
						parentID.append("(");
						for(int j = 0; j< subjectIds.length; j++){
							parentID.append("'"+subjectIds[j]+"',");
						}
						parentID.delete(parentID.length()-1, parentID.length());
						parentID.append(")");
						
						tsql1 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql2 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql3 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql4 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql5 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql6 += " and t.fk_subject in " + parentID.toString() + " ";
					}
					
				}
				
				count1 = Integer.parseInt(peProApplyNoService.executeSQL(tsql1).get(0).toString());
				count2 = Integer.parseInt(peProApplyNoService.executeSQL(tsql2).get(0).toString());
				count3 = Integer.parseInt(peProApplyNoService.executeSQL(tsql3).get(0).toString());
				count4 = Integer.parseInt(peProApplyNoService.executeSQL(tsql4).get(0).toString());
				count5 = Integer.parseInt(peProApplyNoService.executeSQL(tsql5).get(0).toString());
				count6 = Integer.parseInt(peProApplyNoService.executeSQL(tsql6).get(0).toString());
				
			}
			
			float f1 = (float) count1 / cxcount;
			float f2 = (float) count2 / cxcount;
			float f3 = (float) count3 / cxcount;
			float f4 = (float) count4 / cxcount;
			float f5 = (float) count5 / cxcount;
			float f6 = (float) count6 / cxcount;
	
			String lv1 = df.format(f1);
			String lv2 = df.format(f2);
			String lv3 = df.format(f3);
			String lv4 = df.format(f4);
			String lv5 = df.format(f5);
			String lv6 = df.format(f6);
	
			HSSFRow row = sheet1.createRow((short) k);
			k++;
			
			row.createCell(0).setCellValue(provinceName);
			row.createCell(1).setCellValue(cxcount);
			row.createCell(2).setCellValue(count1);
			row.createCell(3).setCellValue(lv1);
			row.createCell(4).setCellValue(count2);
			row.createCell(5).setCellValue(lv2);
			row.createCell(6).setCellValue(count3);
			row.createCell(7).setCellValue(lv3);
			row.createCell(8).setCellValue(count4);
			row.createCell(9).setCellValue(lv4);
			row.createCell(10).setCellValue(count5);
			row.createCell(11).setCellValue(lv5);
			row.createCell(12).setCellValue(count6);
			row.createCell(13).setCellValue(lv6);
			
			countz += cxcount;
			countcs += count1;
			countpxc += count2;
			countpzq += count3;
			countpx += count4;
			countpc += count5;
			countptc += count6;

		}
		

		
		HSSFRow row = sheet1.createRow((short) k);
		
		row.createCell(0).setCellValue("合计");
		row.createCell(1).setCellValue(countz);
		row.createCell(2).setCellValue(countcs);
		row.createCell(4).setCellValue(countpxc);
		row.createCell(6).setCellValue(countpzq);
		row.createCell(8).setCellValue(countpx);
		row.createCell(10).setCellValue(countpc);
		row.createCell(12).setCellValue(countptc);
		
		float f6 = (float) countcs / countz;
		float f7 = (float) countpxc / countz;
		float f8 = (float) countpzq / countz;
		float f9 = (float) countpx / countz;
		float f10 = (float) countpc / countz;
		float f11 = (float) countptc / countz;
		
		String lv6 = df.format(f6);
		String lv7 = df.format(f7);
		String lv8 = df.format(f8);
		String lv9 = df.format(f9);
		String lv10 = df.format(f10);
		String lv11 = df.format(f11);
		
		row.createCell(3).setCellValue(lv6);
		row.createCell(5).setCellValue(lv7);
		row.createCell(7).setCellValue(lv8);
		row.createCell(9).setCellValue(lv9);
		row.createCell(11).setCellValue(lv10);
		row.createCell(13).setCellValue(lv11);
		
		try {
			String filename = "中西部和幼师国培学校所在区域维度人数统计导出";
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
	
	public String  getSql(String tempSql,String key){
		String tsql1 = "select count(*) " 
				+ " from TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id "
				+ " left join UNIT_ATTRIBUTE ua on t.fk_unitattribute = ua.id "
				+ " left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id "
				+ "  where 1=1 and p.id='" + tempSql + "' and ua.id='"+key+"'";
		
		return tsql1;
	}
	
	/**
	 * 中西部学校类别维度人数统计
	 */
	public void adminTraineeNumForMWLB() {
		String parentId = request.getParameter("parent");
		String subjectId = request.getParameter("subject");

		if (parentId == null) {
			parentId = "all";
		}
		
		if (subjectId == null) {
			subjectId = "all";
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
		
		
		String sqlcount = "select count(*) ";
		String sql = "select p.id ,p.name as provinceName,count(*) ";
		
		sql += "from TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id ";
		sqlcount += "from TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id ";
		
		sql += "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id ";
		sqlcount += "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id ";
		
		sql += " left join UNIT_TYPE ua on t.fk_unit_type = ua.id ";
		sqlcount += " left join UNIT_TYPE ua on t.fk_unit_type = ua.id ";
		
		sql += "  where 1=1 and t.fk_unit_type is not null ";
		sqlcount += "  where 1=1 and t.fk_unit_type is not null ";
		
		if (!parentId.equals("all")) {
			String[] parentIds = parentId.split(",");
			if(!parentIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< parentIds.length; i++){
					parentID.append("'"+parentIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and po.parentid in " + parentID.toString() + " ";
				sqlcount += " and po.parentid in " + parentID.toString() + " ";
			}
			
		}
		
		if (!subjectId.equals("all")) {
			String[] subjectIds = subjectId.split(",");
			if(!subjectIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< subjectIds.length; i++){
					parentID.append("'"+subjectIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and t.fk_subject in " + parentID.toString() + " ";
				sqlcount += " and t.fk_subject in " + parentID.toString() + " ";
			}
			
		}
		
		sql += " group by p.id,p.name,p.code";
		sqlcount += " group by p.id,p.name,p.code";
		
		sql += " order by p.code ";
		sqlcount += " order by p.code ";
		
		List li = peProApplyNoService.executeSQL(sqlcount);
		
		String strCount = "0";
		if (li != null && li.size() > 0) {
			strCount = li.size()+"";
		}
		
		int totalCount = Integer.parseInt(strCount);
		int tol = totalCount / limit;
		if ((totalCount % limit) > 0) {
			tol += 1;
		}
		
		
		li = peProApplyNoService.getListPage(sql, start, limit);
		
		String cellString = "";
		
		DecimalFormat df = new DecimalFormat();
		String style = "0.00%";
		df.applyPattern(style);
		
		String nsql = "select t.id,t.name from UNIT_TYPE t where 1=1 order by t.NAME";
		
		List listn = peProApplyNoService.getListPage(nsql, -1, -1);
		String cells = "{\"id\":\"0\",\"cell\":[\"省份\",\"总人数\",\"";
		for(int i = 0; i < listn.size(); i++){
			String name = ((Object[])listn.get(i))[1].toString();
			cells+=(name + "人数\",\"占比\",\"");
		}
		if (cells.length() > 0) {
			cells = cells.substring(0, cells.length() - 2);
		}
		cells+="]},";
		
		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String tmprovinceId = obj[0].toString();
			String provinceName = obj[1].toString();
			int cxcount = Integer.valueOf(obj[2].toString());
	
			String t = "{\"id\":\"" + tmprovinceId  + "\",\"cell\":[\"" + provinceName + "\",\"" + cxcount + "\",\"";
			if(listn != null && listn.size() >0){
				int count1 = 0;
				for(int m = 0; m < listn.size(); m++){
					String nid = ((Object[])listn.get(m))[0].toString();
					String tsql1 = getSql2(tmprovinceId,nid);
					if (!parentId.equals("all")) {
						String[] parentIds = parentId.split(",");
						if(!parentIds[0].equals("all")){
							StringBuilder parentID = new StringBuilder();
							parentID.append("(");
							for(int j = 0; j< parentIds.length; j++){
								parentID.append("'"+parentIds[j]+"',");
							}
							parentID.delete(parentID.length()-1, parentID.length());
							parentID.append(")");
							
							tsql1 += " and po.parentid in " + parentID.toString() + " ";
						}
					}
					
					if (!subjectId.equals("all")) {
						String[] subjectIds = subjectId.split(",");
						if(!subjectIds[0].equals("all")){
							StringBuilder parentID = new StringBuilder();
							parentID.append("(");
							for(int j = 0; j< subjectIds.length; j++){
								parentID.append("'"+subjectIds[j]+"',");
							}
							parentID.delete(parentID.length()-1, parentID.length());
							parentID.append(")");
							
							tsql1 += " and t.fk_subject in " + parentID.toString() + " ";
						}
						
					}
					count1 = Integer.parseInt(peProApplyNoService.executeSQL(tsql1).get(0).toString());
					float f1 = (float) count1 / cxcount;
					String lv1 = df.format(f1);
//					cells+=(name + "人数\",\"占比\",\"");
					t += (count1 + "\",\"" + lv1 + "\",\"");
				}
				if (t.length() > 0) {
					t = t.substring(0, t.length() - 2);
				}
				t+="]}";
			}
	
			cellString += t + ",";
			
		}
		cells += cellString;
		if (cells.length() > 0) {
			cells = cells.substring(0, cells.length() - 1);
		}
	
		String retJson = "{\"page\":\"" + start + "\",\"total\":\"" + tol + "\",\"records\":\"" + totalCount + "\",\"rows\":[" + cells + "]}";
	
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
	
	/**
	 * 中西部学校类别维度人数统计导出
	 */
	public void importTraineeNumForMWLB() {
		

		String parentId = request.getParameter("parent");
		String subjectId = request.getParameter("subject");

		if (parentId == null) {
			parentId = "all";
		}
		
		if (subjectId == null) {
			subjectId = "all";
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
		
		String sql = "select p.id ,p.name as provinceName,count(*) ";
		
		sql += "from TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id ";
		
		sql += "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id ";
		
		sql += " left join UNIT_TYPE ua on t.fk_unit_type = ua.id ";
		
		sql += "  where 1=1 and t.fk_unit_type is not null ";
		
		if (!parentId.equals("all")) {
			String[] parentIds = parentId.split(",");
			if(!parentIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< parentIds.length; i++){
					parentID.append("'"+parentIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and po.parentid in " + parentID.toString() + " ";
			}
			
		}
		
		if (!subjectId.equals("all")) {
			String[] subjectIds = subjectId.split(",");
			if(!subjectIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< subjectIds.length; i++){
					parentID.append("'"+subjectIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and t.fk_subject in " + parentID.toString() + " ";
			}
			
		}
		
		sql += " group by p.id,p.name,p.code";
		
		sql += " order by p.code ";
		
		
		List li = peProApplyNoService.getListPage(sql, start, limit);
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet("sheet1");
		int k = 0;
		HSSFRow rowheader = sheet1.createRow((short) k);
		k++;
		
		rowheader.createCell(0).setCellValue("省份");
		rowheader.createCell(1).setCellValue("总人数");
		rowheader.createCell(2).setCellValue("初中人数");
		rowheader.createCell(3).setCellValue("占比");
		rowheader.createCell(4).setCellValue("高中人数");
		rowheader.createCell(5).setCellValue("占比");
		rowheader.createCell(6).setCellValue("教学点人数");
		rowheader.createCell(7).setCellValue("占比");
		rowheader.createCell(8).setCellValue("九年一贯制学校人数");
		rowheader.createCell(9).setCellValue("占比");
		rowheader.createCell(10).setCellValue("其他人数");
		rowheader.createCell(11).setCellValue("占比");
		rowheader.createCell(12).setCellValue("十二年一贯制学校人数");
		rowheader.createCell(13).setCellValue("占比");
		rowheader.createCell(14).setCellValue("完全中学人数");
		rowheader.createCell(15).setCellValue("占比");
		rowheader.createCell(16).setCellValue("小学人数");
		rowheader.createCell(17).setCellValue("占比");
		rowheader.createCell(18).setCellValue("幼儿园人数");
		rowheader.createCell(19).setCellValue("占比");

		int countz = 0;
		int countyey = 0;
		int countpxx = 0;
		int countpcz = 0;
		int countgz = 0;
		int countwq = 0;
		int countjn = 0;
		int countsen = 0;
		int countjxd = 0;
		int countqt = 0;
		
		DecimalFormat df = new DecimalFormat();
		String style = "0.00%";
		df.applyPattern(style);
		
		String nsql = "select t.id from UNIT_TYPE t where 1=1 order by t.NAME";
		
		List listn = peProApplyNoService.getListPage(nsql, -1, -1);
		
		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String tmprovinceId = obj[0].toString();
			String provinceName = obj[1].toString();
			int cxcount = Integer.valueOf(obj[2].toString());
	

			
			int count1 = 0;
			int count2 = 0;
			int count3 = 0;
			int count4 = 0;
			int count5 = 0;
			int count6 = 0;
			int count7 = 0;
			int count8 = 0;
			int count9 = 0;
			if(listn != null && listn.size() >0){
				String nid = listn.get(0).toString();
				String nid2 = listn.get(1).toString();
				String nid3 = listn.get(2).toString();
				String nid4 = listn.get(3).toString();
				String nid5 = listn.get(4).toString();
				String nid6 = listn.get(5).toString();
				String nid7 = listn.get(6).toString();
				String nid8 = listn.get(7).toString();
				String nid9 = listn.get(8).toString();
				
				String tsql1 = getSql2(tmprovinceId,nid);
				
				String tsql2 = getSql2(tmprovinceId,nid2);
				String tsql3 = getSql2(tmprovinceId,nid3);
				String tsql4 = getSql2(tmprovinceId,nid4);
				String tsql5 = getSql2(tmprovinceId,nid5);
				String tsql6 = getSql2(tmprovinceId,nid6);
				String tsql7 = getSql2(tmprovinceId,nid7);
				String tsql8 = getSql2(tmprovinceId,nid8);
				String tsql9 = getSql2(tmprovinceId,nid9);
				
				if (!parentId.equals("all")) {
					String[] parentIds = parentId.split(",");
					if(!parentIds[0].equals("all")){
						StringBuilder parentID = new StringBuilder();
						parentID.append("(");
						for(int j = 0; j< parentIds.length; j++){
							parentID.append("'"+parentIds[j]+"',");
						}
						parentID.delete(parentID.length()-1, parentID.length());
						parentID.append(")");
						
						tsql1 += " and po.parentid in " + parentID.toString() + " ";
						tsql2 += " and po.parentid in " + parentID.toString() + " ";
						tsql3 += " and po.parentid in " + parentID.toString() + " ";
						tsql4 += " and po.parentid in " + parentID.toString() + " ";
						tsql5 += " and po.parentid in " + parentID.toString() + " ";
						tsql6 += " and po.parentid in " + parentID.toString() + " ";
						tsql7 += " and po.parentid in " + parentID.toString() + " ";
						tsql8 += " and po.parentid in " + parentID.toString() + " ";
						tsql9 += " and po.parentid in " + parentID.toString() + " ";
					}
					
				}
				
				if (!subjectId.equals("all")) {
					String[] subjectIds = subjectId.split(",");
					if(!subjectIds[0].equals("all")){
						StringBuilder parentID = new StringBuilder();
						parentID.append("(");
						for(int j = 0; j< subjectIds.length; j++){
							parentID.append("'"+subjectIds[j]+"',");
						}
						parentID.delete(parentID.length()-1, parentID.length());
						parentID.append(")");
						
						tsql1 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql2 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql3 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql4 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql5 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql6 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql7 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql8 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql9 += " and t.fk_subject in " + parentID.toString() + " ";
					}
					
				}
				
				count1 = Integer.parseInt(peProApplyNoService.executeSQL(tsql1).get(0).toString());
				count2 = Integer.parseInt(peProApplyNoService.executeSQL(tsql2).get(0).toString());
				count3 = Integer.parseInt(peProApplyNoService.executeSQL(tsql3).get(0).toString());
				count4 = Integer.parseInt(peProApplyNoService.executeSQL(tsql4).get(0).toString());
				count5 = Integer.parseInt(peProApplyNoService.executeSQL(tsql5).get(0).toString());
				count6 = Integer.parseInt(peProApplyNoService.executeSQL(tsql6).get(0).toString());
				count7 = Integer.parseInt(peProApplyNoService.executeSQL(tsql7).get(0).toString());
				count8 = Integer.parseInt(peProApplyNoService.executeSQL(tsql8).get(0).toString());
				count9 = Integer.parseInt(peProApplyNoService.executeSQL(tsql9).get(0).toString());
				
				
			}
			
			
	
			float f1 = (float) count1 / cxcount;
			float f2 = (float) count2 / cxcount;
			float f3 = (float) count3 / cxcount;
			float f4 = (float) count4 / cxcount;
			float f5 = (float) count5 / cxcount;
			float f6 = (float) count6 / cxcount;
			float f7 = (float) count7 / cxcount;
			float f8 = (float) count8 / cxcount;
			float f9 = (float) count9 / cxcount;
	
			String lv1 = df.format(f1);
			String lv2 = df.format(f2);
			String lv3 = df.format(f3);
			String lv4 = df.format(f4);
			String lv5 = df.format(f5);
			String lv6 = df.format(f6);
			String lv7 = df.format(f7);
			String lv8 = df.format(f8);
			String lv9 = df.format(f9);
	
			HSSFRow row = sheet1.createRow((short) k);
			k++;
			
			row.createCell(0).setCellValue(provinceName);
			row.createCell(1).setCellValue(cxcount);
			row.createCell(2).setCellValue(count1);
			row.createCell(3).setCellValue(lv1);
			row.createCell(4).setCellValue(count2);
			row.createCell(5).setCellValue(lv2);
			row.createCell(6).setCellValue(count3);
			row.createCell(7).setCellValue(lv3);
			row.createCell(8).setCellValue(count4);
			row.createCell(9).setCellValue(lv4);
			row.createCell(10).setCellValue(count5);
			row.createCell(11).setCellValue(lv5);
			row.createCell(12).setCellValue(count6);
			row.createCell(13).setCellValue(lv6);
			row.createCell(14).setCellValue(count7);
			row.createCell(15).setCellValue(lv7);
			row.createCell(16).setCellValue(count8);
			row.createCell(17).setCellValue(lv8);
			row.createCell(18).setCellValue(count9);
			row.createCell(19).setCellValue(lv9);
			
			countz += cxcount;
			countyey += count1;
			countpxx += count2;
			countpcz += count3;
			countgz += count4;
			countwq += count5;
			countjn += count6;
			countsen += count7;
			countjxd += count8;
			countqt += count9;

		}
		

		
		HSSFRow row = sheet1.createRow((short) k);
		
		row.createCell(0).setCellValue("合计");
		row.createCell(1).setCellValue(countz);
		row.createCell(2).setCellValue(countyey);
		row.createCell(4).setCellValue(countpxx);
		row.createCell(6).setCellValue(countpcz);
		row.createCell(8).setCellValue(countgz);
		row.createCell(10).setCellValue(countwq);
		row.createCell(12).setCellValue(countjn);
		row.createCell(14).setCellValue(countsen);
		row.createCell(16).setCellValue(countjxd);
		row.createCell(18).setCellValue(countqt);
		
		float f6 = (float) countyey / countz;
		float f7 = (float) countpxx / countz;
		float f8 = (float) countpcz / countz;
		float f9 = (float) countgz / countz;
		float f10 = (float) countwq / countz;
		float f11 = (float) countjn / countz;
		float f12 = (float) countsen / countz;
		float f13 = (float) countjxd / countz;
		float f14 = (float) countqt / countz;
		
		
		String lv6 = df.format(f6);
		String lv7 = df.format(f7);
		String lv8 = df.format(f8);
		String lv9 = df.format(f9);
		String lv10 = df.format(f10);
		String lv11 = df.format(f11);
		String lv12 = df.format(f12);
		String lv13 = df.format(f13);
		String lv14 = df.format(f14);
		
		row.createCell(3).setCellValue(lv6);
		row.createCell(5).setCellValue(lv7);
		row.createCell(7).setCellValue(lv8);
		row.createCell(9).setCellValue(lv9);
		row.createCell(11).setCellValue(lv10);
		row.createCell(13).setCellValue(lv11);
		row.createCell(15).setCellValue(lv12);
		row.createCell(17).setCellValue(lv13);
		row.createCell(19).setCellValue(lv14);
		
		try {
			String filename = "中西部和幼师国培学校类别维度人数统计导出";
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
	
	public String  getSql2(String tempSql,String key){
		String tsql1 = "select count(*) " 
				+ " from TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id "
				+ " left join UNIT_TYPE ua on t.fk_unit_type = ua.id "
				+ " left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id "
				+ "  where 1=1 and p.id='" + tempSql + "' and ua.id='"+key+"'";
		
		return tsql1;
	}
	
	
	/**
	 * 示范性项目维度人数统计
	 * 报送人数--推荐
	 * 审核人数--计划
	 * 报到人数--参训
	 * 结业人数--参训
	 * select  from TCHR_TRAINING1.PE_TRAINEE this_ 
	 * left outer join TCHR_TRAINING1.PE_PRO_APPLYNO peproapply5_ on this_.FK_PRO_APPLYNO=peproapply5_.ID 
	 * left outer join TCHR_TRAINING1.ENUM_CONST enumconstb1_ on this_.FK_GENDER=enumconstb1_.ID 
	 * left outer join TCHR_TRAINING1.PE_SUBJECT pesubject6_ on this_.FK_SUBJECT=pesubject6_.ID 
	 * left outer join TCHR_TRAINING1.ENUM_CONST enumconstb19_ on this_.FK_STATUS_TRAINING=enumconstb19_.ID 
	 * left outer join TCHR_TRAINING1.ENUM_CONST enumconstb3_ on this_.FK_GRADUTED=enumconstb3_.ID 
	 * left outer join TCHR_TRAINING1.ENUM_CONST enumconstb4_ on this_.FK_MODIFY_CHECKED=enumconstb4_.ID 
	 * left outer join TCHR_TRAINING1.ENUM_CONST enumconstb2_ on this_.FK_CHECKED_TRAINEE=enumconstb2_.ID 
	 * left outer join TCHR_TRAINING1.PE_UNIT peunitbyfk17_ on this_.FK_TRAINING_UNIT=peunitbyfk17_.ID 
	 * left outer join TCHR_TRAINING1.PE_UNIT peunitbyfk18_ on this_.FK_UNIT_FROM=peunitbyfk18_.ID 
	 * left outer join TCHR_TRAINING1.PE_PROVINCE peprovince7_ on this_.FK_PROVINCE=peprovince7_.ID 
	 * left outer join TCHR_TRAINING1.COUNTY county9_ on this_.FK_COUNTY=county9_.ID 
	 * left outer join TCHR_TRAINING1.CITY city10_ on county9_.FK_CITY=city10_.ID 
	 * left outer join TCHR_TRAINING1.FOLK folk8_ on this_.FK_FOLK=folk8_.ID 
	 * left outer join TCHR_TRAINING1.EDUCATION education11_ on this_.FK_EDUCATION=education11_.ID 
	 * left outer join TCHR_TRAINING1.JOB_TITLE jobtitle12_ on this_.FK_JOBTITLE=jobtitle12_.ID 
	 * left outer join TCHR_TRAINING1.UNIT_ATTRIBUTE unitattrib13_ on this_.FK_UNIT_ATTRIBUTE=unitattrib13_.ID 
	 * left outer join TCHR_TRAINING1.UNIT_TYPE unittype14_ on this_.FK_UNIT_Type=unittype14_.ID 
	 * left outer join TCHR_TRAINING1.MAIN_TEACHING_GRADE mainteachi15_ on this_.FK_MAIN_TEACHING_GRADE=mainteachi15_.ID 
	 * left outer join TCHR_TRAINING1.MAIN_TEACHING_SUBJECT mainteachi16_ on this_.FK_MAIN_TEACHING_SUBJECT=mainteachi16_.ID
	 *  where (enumconstb19_.NAME like '正常' or enumconstb19_.NAME='新增') and enumconstb2_.NAME='审核已通过' 
	 * order by this_.ID desc
	 */
	public void adminTraineeNumForSF() {
		String parentId = request.getParameter("parent");
		String subjectId = request.getParameter("subject");

		if (parentId == null) {
			parentId = "all";
		}
		
		if (subjectId == null) {
			subjectId = "all";
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
		
		String sqlcount = "select count(*) ";
		String sql = "select p.id ,p.name as provinceName,count(*) ";
		
		
		sql += "from PE_TRAINEE t join PE_PROVINCE p on t.fk_province = p.id left join pe_pro_applyno po on t.fk_pro_applyno = po.id ";
		sqlcount += "from PE_TRAINEE t left join PE_PROVINCE p on t.fk_province = p.id left join pe_pro_applyno po on t.fk_pro_applyno = po.id ";
		
		sql += "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id ";
		sqlcount += "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id ";
		
		sql += " left outer join ENUM_CONST enumconstb2_ on t.FK_CHECKED_TRAINEE=enumconstb2_.ID ";
		sqlcount += " left outer join ENUM_CONST enumconstb2_ on t.FK_CHECKED_TRAINEE=enumconstb2_.ID ";
		
		sql += " left outer join ENUM_CONST enumconstb19_ on t.FK_STATUS_TRAINING=enumconstb19_.ID";
		sqlcount += " left outer join ENUM_CONST enumconstb19_ on t.FK_STATUS_TRAINING=enumconstb19_.ID";
		// and (enumconstb19_.NAME like '新增' or enumconstb19_.NAME='正常') and enumconstb2_.NAME='审核已通过'
		sql += " where 1=1 ";
		sqlcount += " where 1=1 ";
		
		if (!parentId.equals("all")) {
			String[] parentIds = parentId.split(",");
			if(!parentIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< parentIds.length; i++){
					parentID.append("'"+parentIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and po.id in " + parentID.toString() + " ";
				sqlcount += " and po.id in " + parentID.toString() + " ";
			}
			
		}
		
		if (!subjectId.equals("all")) {
			String[] subjectIds = subjectId.split(",");
			if(!subjectIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< subjectIds.length; i++){
					parentID.append("'"+subjectIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and t.fk_subject in " + parentID.toString() + " ";
				sqlcount += " and t.fk_subject in " + parentID.toString() + " ";
			}
			
		}
		
		sql += " group by p.id,p.name,p.code order by p.code";
		sqlcount += " group by p.id,p.name,p.code order by p.code";
		
		List li = peProApplyNoService.executeSQL(sqlcount);
		
		String strCount = "0";
		if (li != null && li.size() > 0) {
			strCount = li.size()+"";
		}
		
		int totalCount = Integer.parseInt(strCount);
		int tol = totalCount / limit;
		if ((totalCount % limit) > 0) {
			tol += 1;
		}
		
		
		li = peProApplyNoService.getListPage(sql, start, limit);
		
		String cellString = "";
		
		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String tmprovinceId = obj[0].toString();
			String provinceName = obj[1].toString();
			int cxcount = Integer.valueOf(obj[2].toString());//报送人数
	
			//报到人数
			String tsql = "select count(*) " 
			+ "from PE_TRAINEE t join PE_PROVINCE p on t.fk_province = p.id left join pe_pro_applyno po on t.fk_pro_applyno = po.id " 
					+ "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id "
					+ " left outer join ENUM_CONST enumconstb19_ on t.FK_STATUS_TRAINING=enumconstb19_.ID"
					+ " left outer join ENUM_CONST enumconstb2_ on t.FK_CHECKED_TRAINEE=enumconstb2_.ID "
					+ " where 1=1 and p.id='" + tmprovinceId  + "' and (enumconstb19_.NAME like '新增' or enumconstb19_.NAME='正常') and enumconstb2_.NAME='审核已通过'";
			
			if (!parentId.equals("all")) {
				String[] parentIds = parentId.split(",");
				if(!parentIds[0].equals("all")){
					StringBuilder parentID = new StringBuilder();
					parentID.append("(");
					for(int j = 0; j< parentIds.length; j++){
						parentID.append("'"+parentIds[j]+"',");
					}
					parentID.delete(parentID.length()-1, parentID.length());
					parentID.append(")");
					
					tsql += " and po.id in " + parentID.toString() + " ";
				}
				
			}
			
			if (!subjectId.equals("all")) {
				String[] subjectIds = subjectId.split(",");
				if(!subjectIds[0].equals("all")){
					StringBuilder parentID = new StringBuilder();
					parentID.append("(");
					for(int j = 0; j< subjectIds.length; j++){
						parentID.append("'"+subjectIds[j]+"',");
					}
					parentID.delete(parentID.length()-1, parentID.length());
					parentID.append(")");
					
					tsql += " and t.fk_subject in " + parentID.toString() + " ";
				}
				
			}
			
			//报到人数
			int jhcount = Integer.parseInt(peProApplyNoService.executeSQL(tsql).get(0).toString());
			
//			if (jhcount == 0) {
//				jhcount = cxcount;
//			}
			
			//审核人数 正常和未报到 20170613修改
			String tsql1 = "select count(*) " + "from PE_TRAINEE t join PE_PROVINCE p on t.fk_province = p.id left join pe_pro_applyno po on t.fk_pro_applyno = po.id " 
					+ " left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id "
					+ " left outer join ENUM_CONST enumconstb2_ on t.FK_CHECKED_TRAINEE=enumconstb2_.ID"
					+ " where 1=1 and p.id='" + tmprovinceId  + "' and enumconstb2_.CODE=65230 ";
			
			
			if (!parentId.equals("all")) {
				String[] parentIds = parentId.split(",");
				if(!parentIds[0].equals("all")){
					StringBuilder parentID = new StringBuilder();
					parentID.append("(");
					for(int j = 0; j< parentIds.length; j++){
						parentID.append("'"+parentIds[j]+"',");
					}
					parentID.delete(parentID.length()-1, parentID.length());
					parentID.append(")");
					
					tsql1 += " and po.id in " + parentID.toString() + " ";
				}
				
			}
			
			if (!subjectId.equals("all")) {
				String[] subjectIds = subjectId.split(",");
				if(!subjectIds[0].equals("all")){
					StringBuilder parentID = new StringBuilder();
					parentID.append("(");
					for(int j = 0; j< subjectIds.length; j++){
						parentID.append("'"+subjectIds[j]+"',");
					}
					parentID.delete(parentID.length()-1, parentID.length());
					parentID.append(")");
					
					tsql1 += " and t.fk_subject in " + parentID.toString() + " ";
				}
				
			}
			
			//审核人数
			int shcount = Integer.parseInt(peProApplyNoService.executeSQL(tsql1).get(0).toString());
			
			//结业人数
			String tsql2 = "select count(*) " + "from PE_TRAINEE t join PE_PROVINCE p on t.fk_province = p.id left join pe_pro_applyno po on t.fk_pro_applyno = po.id " 
					+ " left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id "
					+ " left outer join ENUM_CONST enumconstb21_ on t.FK_CHECKED_TRAINEE=enumconstb21_.ID "
					+ " left outer join ENUM_CONST enumconstb2_ on t.FK_GRADUTED=enumconstb2_.ID"
					+ " left outer join ENUM_CONST enumconstb19_ on t.FK_STATUS_TRAINING=enumconstb19_.ID"
					+ " where 1=1 "
					+ " and p.id='" + tmprovinceId  + "' and enumconstb2_.NAME='已结业' "
					+ " and (enumconstb19_.NAME like '新增' or enumconstb19_.NAME='正常') and enumconstb21_.NAME='审核已通过' ";
			
			
			if (!parentId.equals("all")) {
				String[] parentIds = parentId.split(",");
				if(!parentIds[0].equals("all")){
					StringBuilder parentID = new StringBuilder();
					parentID.append("(");
					for(int j = 0; j< parentIds.length; j++){
						parentID.append("'"+parentIds[j]+"',");
					}
					parentID.delete(parentID.length()-1, parentID.length());
					parentID.append(")");
					
					tsql2 += " and po.id in " + parentID.toString() + " ";
				}
				
			}
			
			if (!subjectId.equals("all")) {
				String[] subjectIds = subjectId.split(",");
				if(!subjectIds[0].equals("all")){
					StringBuilder parentID = new StringBuilder();
					parentID.append("(");
					for(int j = 0; j< subjectIds.length; j++){
						parentID.append("'"+subjectIds[j]+"',");
					}
					parentID.delete(parentID.length()-1, parentID.length());
					parentID.append(")");
					
					tsql2 += " and t.fk_subject in " + parentID.toString() + " ";
				}
				
			}
			//结业人数
			int jycount = Integer.parseInt(peProApplyNoService.executeSQL(tsql2).get(0).toString());
	
			String t = "{\"id\":\"" + tmprovinceId  + "\",\"cell\":[\"" + provinceName + "\",\"" + cxcount + "\",\"" + shcount + "\",\"" + jhcount + "\",\"" + jycount + "\"]}";
	
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
	
	/**
	 * 示范性项目维度人数统计导出
	 */
	public void importTraineeNumForSF() {
		

		String parentId = request.getParameter("parent");
		String subjectId = request.getParameter("subject");

		if (parentId == null) {
			parentId = "all";
		}
		
		if (subjectId == null) {
			subjectId = "all";
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
		
		String sql = "select p.id ,p.name as provinceName,count(*) ";
		
		sql += "from PE_TRAINEE t join PE_PROVINCE p on t.fk_province = p.id left join pe_pro_applyno po on t.fk_pro_applyno = po.id ";
		
		sql += " left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id ";
		
		sql += " left outer join ENUM_CONST enumconstb2_ on t.FK_CHECKED_TRAINEE=enumconstb2_.ID ";
		
		sql += " left outer join ENUM_CONST enumconstb19_ on t.FK_STATUS_TRAINING=enumconstb19_.ID";
		//and (enumconstb19_.NAME like '新增' or enumconstb19_.NAME='正常') and enumconstb2_.NAME='审核已通过'
		sql += " where 1=1 ";
		
		if (!parentId.equals("all")) {
			String[] parentIds = parentId.split(",");
			if(!parentIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< parentIds.length; i++){
					parentID.append("'"+parentIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and po.id in " + parentID.toString() + " ";
			}
			
		}
		
		if (!subjectId.equals("all")) {
			String[] subjectIds = subjectId.split(",");
			if(!subjectIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< subjectIds.length; i++){
					parentID.append("'"+subjectIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and t.fk_subject in " + parentID.toString() + " ";
			}
			
		}
		
		sql += " group by p.id,p.name,p.code order by p.code";
		
		List li = peProApplyNoService.getListPage(sql, start, limit);
		
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet("sheet1");
		int k = 0;
		HSSFRow rowheader = sheet1.createRow((short) k);
		k++;
		
		rowheader.createCell(0).setCellValue("省份");
		rowheader.createCell(1).setCellValue("报送人数");
		rowheader.createCell(2).setCellValue("审核人数");
		rowheader.createCell(3).setCellValue("报到人数");
		rowheader.createCell(4).setCellValue("结业人数");

		int countbs = 0;
		int countsh = 0;
		int countbd = 0;
		int countjy = 0;
		
		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String tmprovinceId = obj[0].toString();
			String provinceName = obj[1].toString();
			int cxcount = Integer.valueOf(obj[2].toString());
	
			//报送人数
//			String tsql = "select count(*) " + "from PE_TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join pe_pro_applyno po on t.fk_pro_applyno = po.id " 
//			+ "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id "
//					+ " where 1=1 and p.id='" + tmprovinceId  + "'";
//			//报送人数
//			String tsql = "select count(*) " + "from PE_TRAINEE t join PE_PROVINCE p on t.fk_province = p.id left join pe_pro_applyno po on t.fk_pro_applyno = po.id " + "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id "
//					+ " where 1=1 and p.id='" + tmprovinceId  + "'";
			//报到人数
			String tsql = "select count(*) " 
			+ "from PE_TRAINEE t join PE_PROVINCE p on t.fk_province = p.id left join pe_pro_applyno po on t.fk_pro_applyno = po.id " 
					+ "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id "
					+ " left outer join ENUM_CONST enumconstb19_ on t.FK_STATUS_TRAINING=enumconstb19_.ID"
					+ " left outer join ENUM_CONST enumconstb2_ on t.FK_CHECKED_TRAINEE=enumconstb2_.ID "
					+ " where 1=1 and p.id='" + tmprovinceId  + "' and (enumconstb19_.NAME like '新增' or enumconstb19_.NAME='正常') and enumconstb2_.NAME='审核已通过'";
			
			if (!parentId.equals("all")) {
				String[] parentIds = parentId.split(",");
				if(!parentIds[0].equals("all")){
					StringBuilder parentID = new StringBuilder();
					parentID.append("(");
					for(int j = 0; j< parentIds.length; j++){
						parentID.append("'"+parentIds[j]+"',");
					}
					parentID.delete(parentID.length()-1, parentID.length());
					parentID.append(")");
					
					tsql += " and po.id in " + parentID.toString() + " ";
				}
				
			}
			
			if (!subjectId.equals("all")) {
				String[] subjectIds = subjectId.split(",");
				if(!subjectIds[0].equals("all")){
					StringBuilder parentID = new StringBuilder();
					parentID.append("(");
					for(int j = 0; j< subjectIds.length; j++){
						parentID.append("'"+subjectIds[j]+"',");
					}
					parentID.delete(parentID.length()-1, parentID.length());
					parentID.append(")");
					
					tsql += " and t.fk_subject in " + parentID.toString() + " ";
				}
				
			}
			
	
			int jhcount = Integer.parseInt(peProApplyNoService.executeSQL(tsql).get(0).toString());
			
//			if (jhcount == 0) {
//				jhcount = cxcount;
//			}
			
			//审核人数
			String tsql1 = "select count(*) " + "from PE_TRAINEE t join PE_PROVINCE p on t.fk_province = p.id left join pe_pro_applyno po on t.fk_pro_applyno = po.id " 
					+ "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id "
					+ " left outer join ENUM_CONST enumconstb2_ on t.FK_CHECKED_TRAINEE=enumconstb2_.ID"
					+ " where 1=1 and p.id='" + tmprovinceId  + "' and enumconstb2_.CODE=65230 ";
			
			
			if (!parentId.equals("all")) {
				String[] parentIds = parentId.split(",");
				if(!parentIds[0].equals("all")){
					StringBuilder parentID = new StringBuilder();
					parentID.append("(");
					for(int j = 0; j< parentIds.length; j++){
						parentID.append("'"+parentIds[j]+"',");
					}
					parentID.delete(parentID.length()-1, parentID.length());
					parentID.append(")");
					
					tsql1 += " and po.id in " + parentID.toString() + " ";
				}
				
			}
			
			if (!subjectId.equals("all")) {
				String[] subjectIds = subjectId.split(",");
				if(!subjectIds[0].equals("all")){
					StringBuilder parentID = new StringBuilder();
					parentID.append("(");
					for(int j = 0; j< subjectIds.length; j++){
						parentID.append("'"+subjectIds[j]+"',");
					}
					parentID.delete(parentID.length()-1, parentID.length());
					parentID.append(")");
					
					tsql1 += " and t.fk_subject in " + parentID.toString() + " ";
				}
				
			}
			
			//审核人数
			int shcount = Integer.parseInt(peProApplyNoService.executeSQL(tsql1).get(0).toString());
			
			//结业人数
			String tsql2 = "select count(*) " + "from PE_TRAINEE t join PE_PROVINCE p on t.fk_province = p.id left join pe_pro_applyno po on t.fk_pro_applyno = po.id " 
					+ " left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id "
					+ " left outer join ENUM_CONST enumconstb21_ on t.FK_CHECKED_TRAINEE=enumconstb21_.ID "
					+ " left outer join ENUM_CONST enumconstb2_ on t.FK_GRADUTED=enumconstb2_.ID"
					+ " left outer join ENUM_CONST enumconstb19_ on t.FK_STATUS_TRAINING=enumconstb19_.ID"
					+ " where 1=1 "
					+ " and p.id='" + tmprovinceId  + "' and enumconstb2_.NAME='已结业' "
					+ " and (enumconstb19_.NAME like '新增' or enumconstb19_.NAME='正常') and enumconstb21_.NAME='审核已通过' ";
			
			
			if (!parentId.equals("all")) {
				String[] parentIds = parentId.split(",");
				if(!parentIds[0].equals("all")){
					StringBuilder parentID = new StringBuilder();
					parentID.append("(");
					for(int j = 0; j< parentIds.length; j++){
						parentID.append("'"+parentIds[j]+"',");
					}
					parentID.delete(parentID.length()-1, parentID.length());
					parentID.append(")");
					
					tsql2 += " and po.id in " + parentID.toString() + " ";
				}
				
			}
			
			if (!subjectId.equals("all")) {
				String[] subjectIds = subjectId.split(",");
				if(!subjectIds[0].equals("all")){
					StringBuilder parentID = new StringBuilder();
					parentID.append("(");
					for(int j = 0; j< subjectIds.length; j++){
						parentID.append("'"+subjectIds[j]+"',");
					}
					parentID.delete(parentID.length()-1, parentID.length());
					parentID.append(")");
					
					tsql2 += " and t.fk_subject in " + parentID.toString() + " ";
				}
				
			}
			//结业人数
			int jycount = Integer.parseInt(peProApplyNoService.executeSQL(tsql2).get(0).toString());
	
	
			HSSFRow row = sheet1.createRow((short) k);
			k++;
			
			row.createCell(0).setCellValue(provinceName);
			row.createCell(1).setCellValue(cxcount);
			row.createCell(2).setCellValue(shcount);
			row.createCell(3).setCellValue(jhcount);
			row.createCell(4).setCellValue(jycount);
			
			countbs += cxcount;
			countsh += shcount;
			countbd += jhcount;
			countjy += jycount;

		}
		HSSFRow row = sheet1.createRow((short) k);
		
		row.createCell(0).setCellValue("合计");
		row.createCell(1).setCellValue(countbs);
		row.createCell(2).setCellValue(countsh);
		row.createCell(3).setCellValue(countbd);
		row.createCell(4).setCellValue(countjy);
		
		try {
			String filename = "示范性项目维度人数统计导出";
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
	
	
	/**
	 * 示范性贫困县维度人数统计
	 * 集中连片特困地区县人数
	 * 国家级贫困县人数
	 * 特困地区/贫困县合计人数
	 */
	public void adminTraineeNumForSFPKX() {
		String parentId = request.getParameter("parent");
		String subjectId = request.getParameter("subject");

		if (parentId == null) {
			parentId = "all";
		}
		
		if (subjectId == null) {
			subjectId = "all";
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
		
		String sqlcount = "select count(*) ";
		String sql = "select p.id ,p.name as provinceName,count(*) ";
		
		sql += "from PE_TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join pe_pro_applyno po on t.fk_pro_applyno = po.id ";
		sqlcount += "from PE_TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join pe_pro_applyno po on t.fk_pro_applyno = po.id ";
		
		sql += "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id ";
		sqlcount += "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id ";
		
		sql += " left outer join ENUM_CONST enumconstb19_ on t.FK_STATUS_TRAINING=enumconstb19_.ID";
		sqlcount += " left outer join ENUM_CONST enumconstb19_ on t.FK_STATUS_TRAINING=enumconstb19_.ID";
		
		sql += " left outer join ENUM_CONST enumconstb2_ on t.FK_CHECKED_TRAINEE=enumconstb2_.ID ";
		sqlcount += " left outer join ENUM_CONST enumconstb2_ on t.FK_CHECKED_TRAINEE=enumconstb2_.ID ";
		
		sql += " left join COUNTY co on t.fk_county = co.id " ;
		sqlcount += " left join COUNTY co on t.fk_county = co.id " ;
		
		sql += " where 1=1 and (enumconstb19_.NAME like '新增' or enumconstb19_.NAME='正常') and enumconstb2_.NAME='审核已通过'";
		sqlcount += " where 1=1 and (enumconstb19_.NAME like '新增' or enumconstb19_.NAME='正常') and enumconstb2_.NAME='审核已通过'";
		
		sql += " and co.is_poor is not null and co.is_countrypoor is not null ";
		sqlcount += " and co.is_poor is not null and co.is_countrypoor is not null ";
		
		if (!parentId.equals("all")) {
			String[] parentIds = parentId.split(",");
			if(!parentIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< parentIds.length; i++){
					parentID.append("'"+parentIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and po.id in " + parentID.toString() + " ";
				sqlcount += " and po.id in " + parentID.toString() + " ";
			}
			
		}
		
		if (!subjectId.equals("all")) {
			String[] subjectIds = subjectId.split(",");
			if(!subjectIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< subjectIds.length; i++){
					parentID.append("'"+subjectIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and t.fk_subject in " + parentID.toString() + " ";
				sqlcount += " and t.fk_subject in " + parentID.toString() + " ";
			}
			
		}
		
		sql += " group by p.id,p.name,p.code order by p.code";
		sqlcount += " group by p.id,p.name,p.code order by p.code";
		
		List li = peProApplyNoService.executeSQL(sqlcount);
		
		String strCount = "0";
		if (li != null && li.size() > 0) {
			strCount = li.size()+"";
		}
		
		int totalCount = Integer.parseInt(strCount);
		int tol = totalCount / limit;
		if ((totalCount % limit) > 0) {
			tol += 1;
		}
		
		
		li = peProApplyNoService.getListPage(sql, start, limit);
		
		String cellString = "";
		
		DecimalFormat df = new DecimalFormat();
		String style = "0.00%";
		df.applyPattern(style);
		
		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String tmprovinceId = obj[0].toString();
			String provinceName = obj[1].toString();
			int cxcount = Integer.valueOf(obj[2].toString());
	
			//报送人数
			String tsql = "select co.is_poor as poor,co.is_countrypoor as countrypoor " 
					+ "from PE_TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join pe_pro_applyno po on t.fk_pro_applyno = po.id " 
					+ " left join COUNTY co on t.fk_county = co.id " 
					+ " left outer join ENUM_CONST enumconstb19_ on t.FK_STATUS_TRAINING=enumconstb19_.ID"
					+ " left outer join ENUM_CONST enumconstb2_ on t.FK_CHECKED_TRAINEE=enumconstb2_.ID "
					+ " left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id "
					+ " where 1=1 and p.id='" + tmprovinceId  + "' and co.is_poor is not null and co.is_countrypoor is not null"
					+ " and (enumconstb19_.NAME like '新增' or enumconstb19_.NAME='正常') and enumconstb2_.NAME='审核已通过'";
			
			if (!parentId.equals("all")) {
				String[] parentIds = parentId.split(",");
				if(!parentIds[0].equals("all")){
					StringBuilder parentID = new StringBuilder();
					parentID.append("(");
					for(int j = 0; j< parentIds.length; j++){
						parentID.append("'"+parentIds[j]+"',");
					}
					parentID.delete(parentID.length()-1, parentID.length());
					parentID.append(")");
					
					tsql += " and po.id in " + parentID.toString() + " ";
				}
				
			}
			
			if (!subjectId.equals("all")) {
				String[] subjectIds = subjectId.split(",");
				if(!subjectIds[0].equals("all")){
					StringBuilder parentID = new StringBuilder();
					parentID.append("(");
					for(int j = 0; j< subjectIds.length; j++){
						parentID.append("'"+subjectIds[j]+"',");
					}
					parentID.delete(parentID.length()-1, parentID.length());
					parentID.append(")");
					
					tsql += " and t.fk_subject in " + parentID.toString() + " ";
				}
				
			}
			

			
			String jzsql = "select count(*) from ("+tsql+")  newt where newt.poor='是'";
			String jzsql1 = "select count(*) from ("+tsql+") newt where newt.countrypoor='是'";
			String jzsql2 = "select count(*) from ("+tsql+") newt where (newt.poor='是' or newt.countrypoor='是')";
			int poorcount = Integer.parseInt(peProApplyNoService.executeSQL(jzsql).get(0).toString());
			
			int cpoorcount = Integer.parseInt(peProApplyNoService.executeSQL(jzsql1).get(0).toString());
			
			int totals = Integer.parseInt(peProApplyNoService.executeSQL(jzsql2).get(0).toString());
			
	
			float f1 = (float) poorcount / cxcount;
			float f2 = (float) cpoorcount / cxcount;
			float f3 = (float) totals / cxcount;
	
			String lv1 = df.format(f1);
			String lv2 = df.format(f2);
			String lv3 = df.format(f3);
	
			String t = "{\"id\":\"" + tmprovinceId  + "\",\"cell\":[\"" + provinceName + "\",\"" + cxcount + "\",\"" + poorcount + "\",\"" + lv1 + "\",\"" + cpoorcount + "\",\"" + lv2 + "\",\"" + totals + "\",\"" + lv3 + "\"]}";
	
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
	
	/**
	 * 示范性贫困县维度统计导出
	 */
	public void importTraineeNumForSFPKX() {
		

		String parentId = request.getParameter("parent");
		String subjectId = request.getParameter("subject");

		if (parentId == null) {
			parentId = "all";
		}
		
		if (subjectId == null) {
			subjectId = "all";
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
		
		String sql = "select p.id ,p.name as provinceName,count(*) ";
		
		sql += "from PE_TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join pe_pro_applyno po on t.fk_pro_applyno = po.id ";
		
		sql += "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id ";
		
		sql += "left outer join ENUM_CONST enumconstb19_ on t.FK_STATUS_TRAINING=enumconstb19_.ID";
		
		sql += " left outer join ENUM_CONST enumconstb2_ on t.FK_CHECKED_TRAINEE=enumconstb2_.ID ";
		
		sql += " left join COUNTY co on t.fk_county = co.id " ;
		
		sql += " where 1=1 and (enumconstb19_.NAME like '新增' or enumconstb19_.NAME='正常') and enumconstb2_.NAME='审核已通过'";
		
		sql += " and co.is_poor is not null and co.is_countrypoor is not null ";
		
		if (!parentId.equals("all")) {
			String[] parentIds = parentId.split(",");
			if(!parentIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< parentIds.length; i++){
					parentID.append("'"+parentIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and po.id in " + parentID.toString() + " ";
			}
			
		}
		
		if (!subjectId.equals("all")) {
			String[] subjectIds = subjectId.split(",");
			if(!subjectIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< subjectIds.length; i++){
					parentID.append("'"+subjectIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and t.fk_subject in " + parentID.toString() + " ";
			}
			
		}
		
		sql += " group by p.id,p.name,p.code order by p.code";
		
		
		List li = peProApplyNoService.getListPage(sql, start, limit);
		
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet("sheet1");
		int k = 0;
		HSSFRow rowheader = sheet1.createRow((short) k);
		k++;
		
		rowheader.createCell(0).setCellValue("省份");
		rowheader.createCell(1).setCellValue("总人数");
		rowheader.createCell(2).setCellValue("集中连片特困地区县人数");
		rowheader.createCell(3).setCellValue("占比");
		rowheader.createCell(4).setCellValue("国家级贫困县人数");
		rowheader.createCell(5).setCellValue("占比");
		rowheader.createCell(6).setCellValue("特困地区/贫困县合计人数");
		rowheader.createCell(7).setCellValue("占比");

		int countz = 0;
		int countpkx = 0;
		int countpkgj = 0;
		int countpkhj = 0;
		
		
		DecimalFormat df = new DecimalFormat();
		String style = "0.00%";
		df.applyPattern(style);
		
		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String tmprovinceId = obj[0].toString();
			String provinceName = obj[1].toString();
			int cxcount = Integer.valueOf(obj[2].toString());
	
			//报送人数
			String tsql = "select co.is_poor as poor,co.is_countrypoor as countrypoor " 
					+ "from PE_TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join pe_pro_applyno po on t.fk_pro_applyno = po.id " 
					+ " left join COUNTY co on t.fk_county = co.id " 
					+ " left outer join ENUM_CONST enumconstb19_ on t.FK_STATUS_TRAINING=enumconstb19_.ID"
					+ " left outer join ENUM_CONST enumconstb2_ on t.FK_CHECKED_TRAINEE=enumconstb2_.ID "
					+ " left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id "
					+ " where 1=1 and p.id='" + tmprovinceId  + "' and co.is_poor is not null and co.is_countrypoor is not null"
					+ " and (enumconstb19_.NAME like '新增' or enumconstb19_.NAME='正常')  and enumconstb2_.NAME='审核已通过'";
			
			if (!parentId.equals("all")) {
				String[] parentIds = parentId.split(",");
				if(!parentIds[0].equals("all")){
					StringBuilder parentID = new StringBuilder();
					parentID.append("(");
					for(int j = 0; j< parentIds.length; j++){
						parentID.append("'"+parentIds[j]+"',");
					}
					parentID.delete(parentID.length()-1, parentID.length());
					parentID.append(")");
					
					tsql += " and po.id in " + parentID.toString() + " ";
				}
				
			}
			
			if (!subjectId.equals("all")) {
				String[] subjectIds = subjectId.split(",");
				if(!subjectIds[0].equals("all")){
					StringBuilder parentID = new StringBuilder();
					parentID.append("(");
					for(int j = 0; j< subjectIds.length; j++){
						parentID.append("'"+subjectIds[j]+"',");
					}
					parentID.delete(parentID.length()-1, parentID.length());
					parentID.append(")");
					
					tsql += " and t.fk_subject in " + parentID.toString() + " ";
				}
				
			}
			

			
			String jzsql = "select count(*) from ("+tsql+")  newt where newt.poor='是'";
			String jzsql1 = "select count(*) from ("+tsql+") newt where newt.countrypoor='是'";
			String jzsql2 = "select count(*) from ("+tsql+") newt where (newt.poor='是' or newt.countrypoor='是')";
			int poorcount = Integer.parseInt(peProApplyNoService.executeSQL(jzsql).get(0).toString());
			
			int cpoorcount = Integer.parseInt(peProApplyNoService.executeSQL(jzsql1).get(0).toString());
			
			int totals = Integer.parseInt(peProApplyNoService.executeSQL(jzsql2).get(0).toString());
			
	
			float f1 = (float) poorcount / cxcount;
			float f2 = (float) cpoorcount / cxcount;
			float f3 = (float) totals / cxcount;
	
			String lv1 = df.format(f1);
			String lv2 = df.format(f2);
			String lv3 = df.format(f3);
	
			HSSFRow row = sheet1.createRow((short) k);
			k++;
			
			row.createCell(0).setCellValue(provinceName);
			row.createCell(1).setCellValue(cxcount);
			row.createCell(2).setCellValue(poorcount);
			row.createCell(3).setCellValue(lv1);
			row.createCell(4).setCellValue(cpoorcount);
			row.createCell(5).setCellValue(lv2);
			row.createCell(6).setCellValue(totals);
			row.createCell(7).setCellValue(lv3);
			
			countz += cxcount;
			countpkx += poorcount;
			countpkgj += cpoorcount;
			countpkhj += totals;

		}
		

		
		HSSFRow row = sheet1.createRow((short) k);
		
		row.createCell(0).setCellValue("合计");
		row.createCell(1).setCellValue(countz);
		row.createCell(2).setCellValue(countpkx);
		row.createCell(4).setCellValue(countpkgj);
		row.createCell(6).setCellValue(countpkhj);
		
		float f4 = (float) countpkx / countz;
		float f5 = (float) countpkgj / countz;
		float f6 = (float) countpkhj / countz;
		
		String lv4 = df.format(f4);
		String lv5 = df.format(f5);
		String lv6 = df.format(f6);
		
		row.createCell(3).setCellValue(lv4);
		row.createCell(5).setCellValue(lv5);
		row.createCell(7).setCellValue(lv6);
		
		try {
			String filename = "示范性贫困县维度人数统计导出";
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
	
	
	/**
	 * 示范性学校所在区域维度人数统计
	 * 城市人数
	 * 县城人数
	 * 镇区（不含县城）人数
	 * 乡人数
	 * 村人数
	 */
	public void adminTraineeNumForSFQY() {
		String parentId = request.getParameter("parent");
		String subjectId = request.getParameter("subject");

		if (parentId == null) {
			parentId = "all";
		}
		
		if (subjectId == null) {
			subjectId = "all";
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
		
		String sqlcount = "select count(*) ";
		String sql = "select p.id ,p.name as provinceName,count(*) ";
		
		sql += "from PE_TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join pe_pro_applyno po on t.fk_pro_applyno = po.id ";
		sqlcount += "from PE_TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join pe_pro_applyno po on t.fk_pro_applyno = po.id ";
		
		sql += "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id ";
		sqlcount += "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id ";
		
		sql += " left outer join ENUM_CONST enumconstb19_ on t.FK_STATUS_TRAINING=enumconstb19_.ID";
		sqlcount += " left outer join ENUM_CONST enumconstb19_ on t.FK_STATUS_TRAINING=enumconstb19_.ID";
		
		sql += " left outer join ENUM_CONST enumconstb2_ on t.FK_CHECKED_TRAINEE=enumconstb2_.ID ";
		sqlcount += " left outer join ENUM_CONST enumconstb2_ on t.FK_CHECKED_TRAINEE=enumconstb2_.ID ";
		
		sql += " left join UNIT_ATTRIBUTE ua on t.fk_unit_attribute = ua.id ";
		sqlcount += " left join UNIT_ATTRIBUTE ua on t.fk_unit_attribute = ua.id ";
		
		sql += " where 1=1 and (enumconstb19_.NAME like '新增' or enumconstb19_.NAME='正常') and enumconstb2_.NAME='审核已通过'";
		sqlcount += " where 1=1 and (enumconstb19_.NAME like '新增' or enumconstb19_.NAME='正常') and enumconstb2_.NAME='审核已通过'";
		
		sql += " and ua.id is not null ";
		sqlcount += " and ua.id is not null ";
		
		if (!parentId.equals("all")) {
			String[] parentIds = parentId.split(",");
			if(!parentIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< parentIds.length; i++){
					parentID.append("'"+parentIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and po.id in " + parentID.toString() + " ";
				sqlcount += " and po.id in " + parentID.toString() + " ";
			}
			
		}
		
		if (!subjectId.equals("all")) {
			String[] subjectIds = subjectId.split(",");
			if(!subjectIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< subjectIds.length; i++){
					parentID.append("'"+subjectIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and t.fk_subject in " + parentID.toString() + " ";
				sqlcount += " and t.fk_subject in " + parentID.toString() + " ";
			}
			
		}
		
		sql += " group by p.id,p.name,p.code order by p.code";
		sqlcount += " group by p.id,p.name,p.code order by p.code";
		
		List li = peProApplyNoService.executeSQL(sqlcount);
		
		String strCount = "0";
		if (li != null && li.size() > 0) {
			strCount = li.size()+"";
		}
		
		int totalCount = Integer.parseInt(strCount);
		int tol = totalCount / limit;
		if ((totalCount % limit) > 0) {
			tol += 1;
		}
		
		
		li = peProApplyNoService.getListPage(sql, start, limit);
		
		String cellString = "";
		
		DecimalFormat df = new DecimalFormat();
		String style = "0.00%";
		df.applyPattern(style);
		
		String nsql = "select t.id from UNIT_ATTRIBUTE t where 1=1 order by t.NAME";
		
		List listn = peProApplyNoService.getListPage(nsql, -1, -1);
//		StringBuilder qyStr = new StringBuilder();
//		if(listn != null && listn.size() >0){
//			for(int i = 0; i < listn.size(); i++){
//				Object[] obj = (Object[]) listn.get(i);
//				String s = obj[1].toString();
//				qyStr.append("\""+ s+"\",\""+"\"占比\",");
//			}
//			qyStr.delete(qyStr.length() -1, qyStr.length());
//		}
//		cellString += "{\"id\":\"0\",\"cell\":[\"省份\",\"总人数\","+qyStr.toString()+"]},";
		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String tmprovinceId = obj[0].toString();
			String provinceName = obj[1].toString();
			int cxcount = Integer.valueOf(obj[2].toString());
	
			int count1 = 0;
			int count2 = 0;
			int count3 = 0;
			int count4 = 0;
			int count5 = 0;
			int count6 = 0;
			if(listn != null && listn.size() >0){
				String nid = listn.get(0).toString();
				String nid2 = listn.get(1).toString();
				String nid3 = listn.get(2).toString();
				String nid4 = listn.get(3).toString();
				String nid5 = listn.get(4).toString();
				String nid6 = listn.get(5).toString();
				
				String tsql1 = getSql3(tmprovinceId,nid);
				
				String tsql2 = getSql3(tmprovinceId,nid2);
				String tsql3 = getSql3(tmprovinceId,nid3);
				String tsql4 = getSql3(tmprovinceId,nid4);
				String tsql5 = getSql3(tmprovinceId,nid5);
				String tsql6 = getSql3(tmprovinceId,nid6);
				
				if (!parentId.equals("all")) {
					String[] parentIds = parentId.split(",");
					if(!parentIds[0].equals("all")){
						StringBuilder parentID = new StringBuilder();
						parentID.append("(");
						for(int j = 0; j< parentIds.length; j++){
							parentID.append("'"+parentIds[j]+"',");
						}
						parentID.delete(parentID.length()-1, parentID.length());
						parentID.append(")");
						
						tsql1 += " and po.id in " + parentID.toString() + " ";
						tsql2 += " and po.id in " + parentID.toString() + " ";
						tsql3 += " and po.id in " + parentID.toString() + " ";
						tsql4 += " and po.id in " + parentID.toString() + " ";
						tsql5 += " and po.id in " + parentID.toString() + " ";
						tsql6 += " and po.id in " + parentID.toString() + " ";
					}
					
				}
				
				if (!subjectId.equals("all")) {
					String[] subjectIds = subjectId.split(",");
					if(!subjectIds[0].equals("all")){
						StringBuilder parentID = new StringBuilder();
						parentID.append("(");
						for(int j = 0; j< subjectIds.length; j++){
							parentID.append("'"+subjectIds[j]+"',");
						}
						parentID.delete(parentID.length()-1, parentID.length());
						parentID.append(")");
						
						tsql1 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql2 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql3 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql4 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql5 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql6 += " and t.fk_subject in " + parentID.toString() + " ";
					}
					
				}
				
				count1 = Integer.parseInt(peProApplyNoService.executeSQL(tsql1).get(0).toString());
				count2 = Integer.parseInt(peProApplyNoService.executeSQL(tsql2).get(0).toString());
				count3 = Integer.parseInt(peProApplyNoService.executeSQL(tsql3).get(0).toString());
				count4 = Integer.parseInt(peProApplyNoService.executeSQL(tsql4).get(0).toString());
				count5 = Integer.parseInt(peProApplyNoService.executeSQL(tsql5).get(0).toString());
				count6 = Integer.parseInt(peProApplyNoService.executeSQL(tsql6).get(0).toString());
				
				
			}
			
			
	
			float f1 = (float) count1 / cxcount;
			float f2 = (float) count2 / cxcount;
			float f3 = (float) count3 / cxcount;
			float f4 = (float) count4 / cxcount;
			float f5 = (float) count5 / cxcount;
			float f6 = (float) count6 / cxcount;
	
			String lv1 = df.format(f1);
			String lv2 = df.format(f2);
			String lv3 = df.format(f3);
			String lv4 = df.format(f4);
			String lv5 = df.format(f5);
			String lv6 = df.format(f6);
	
			String t = "{\"id\":\"" + tmprovinceId  + "\",\"cell\":[\"" + provinceName + "\",\"" + cxcount + "\",\"" + count1 + "\",\"" + lv1 + "\",\"" + count2 + "\",\"" + lv2 + "\",\"" + count3 + "\",\"" + lv3 + "\",\"" + count4 + "\",\"" + lv4 + "\",\"" + count5 + "\",\"" + lv5 + "\",\"" + count6 + "\",\""+lv6+"\"]}";
	
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
	
	/**
	 * 示范性学校所在区域维度人数统计导出
	 */
	public void importTraineeNumForSFQY() {
		

		String parentId = request.getParameter("parent");
		String subjectId = request.getParameter("subject");

		if (parentId == null) {
			parentId = "all";
		}
		
		if (subjectId == null) {
			subjectId = "all";
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
		
		String sql = "select p.id ,p.name as provinceName,count(*) ";
		
		sql += "from PE_TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join pe_pro_applyno po on t.fk_pro_applyno = po.id ";
		
		sql += "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id ";
		
		sql += " left outer join ENUM_CONST enumconstb19_ on t.FK_STATUS_TRAINING=enumconstb19_.ID";
		
		sql += " left outer join ENUM_CONST enumconstb2_ on t.FK_CHECKED_TRAINEE=enumconstb2_.ID ";
		
		sql += " left join UNIT_ATTRIBUTE ua on t.fk_unit_attribute = ua.id ";
		
		sql += " where 1=1 and (enumconstb19_.NAME like '新增' or enumconstb19_.NAME='正常') and enumconstb2_.NAME='审核已通过'";
		
		sql += " and ua.id is not null ";
		
		if (!parentId.equals("all")) {
			String[] parentIds = parentId.split(",");
			if(!parentIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< parentIds.length; i++){
					parentID.append("'"+parentIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and po.id in " + parentID.toString() + " ";
			}
			
		}
		
		if (!subjectId.equals("all")) {
			String[] subjectIds = subjectId.split(",");
			if(!subjectIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< subjectIds.length; i++){
					parentID.append("'"+subjectIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and t.fk_subject in " + parentID.toString() + " ";
			}
			
		}
		
		sql += " group by p.id,p.name,p.code order by p.code";
		
		List li = peProApplyNoService.getListPage(sql, start, limit);
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet("sheet1");
		int k = 0;
		HSSFRow rowheader = sheet1.createRow((short) k);
		k++;
		
		rowheader.createCell(0).setCellValue("省份");
		rowheader.createCell(1).setCellValue("总人数");
		rowheader.createCell(2).setCellValue("城市人数");
		rowheader.createCell(3).setCellValue("占比");
		rowheader.createCell(4).setCellValue("县城人数");
		rowheader.createCell(5).setCellValue("占比");
		rowheader.createCell(6).setCellValue("镇区（不含县城）人数");
		rowheader.createCell(7).setCellValue("占比");
		rowheader.createCell(8).setCellValue("乡人数");
		rowheader.createCell(9).setCellValue("占比");
		rowheader.createCell(10).setCellValue("村人数");
		rowheader.createCell(11).setCellValue("占比");
		rowheader.createCell(12).setCellValue("团场人数");
		rowheader.createCell(13).setCellValue("占比");

		int countz = 0;
		int countcs = 0;
		int countpxc = 0;
		int countpzq = 0;
		int countpx = 0;
		int countpc = 0;
		int countptc = 0;
		
		DecimalFormat df = new DecimalFormat();
		String style = "0.00%";
		df.applyPattern(style);
		
		String nsql = "select t.id from UNIT_ATTRIBUTE t where 1=1 order by t.NAME";
		
		List listn = peProApplyNoService.getListPage(nsql, -1, -1);
		
		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String tmprovinceId = obj[0].toString();
			String provinceName = obj[1].toString();
			int cxcount = Integer.valueOf(obj[2].toString());
	
			int count1 = 0;
			int count2 = 0;
			int count3 = 0;
			int count4 = 0;
			int count5 = 0;
			int count6 = 0;
			if(listn != null && listn.size() >0){
				String nid = listn.get(0).toString();
				String nid2 = listn.get(1).toString();
				String nid3 = listn.get(2).toString();
				String nid4 = listn.get(3).toString();
				String nid5 = listn.get(4).toString();
				String nid6 = listn.get(5).toString();
				
				String tsql1 = getSql3(tmprovinceId,nid);
				
				String tsql2 = getSql3(tmprovinceId,nid2);
				String tsql3 = getSql3(tmprovinceId,nid3);
				String tsql4 = getSql3(tmprovinceId,nid4);
				String tsql5 = getSql3(tmprovinceId,nid5);
				String tsql6 = getSql3(tmprovinceId,nid6);
				
				if (!parentId.equals("all")) {
					String[] parentIds = parentId.split(",");
					if(!parentIds[0].equals("all")){
						StringBuilder parentID = new StringBuilder();
						parentID.append("(");
						for(int j = 0; j< parentIds.length; j++){
							parentID.append("'"+parentIds[j]+"',");
						}
						parentID.delete(parentID.length()-1, parentID.length());
						parentID.append(")");
						
						tsql1 += " and po.id in " + parentID.toString() + " ";
						tsql2 += " and po.id in " + parentID.toString() + " ";
						tsql3 += " and po.id in " + parentID.toString() + " ";
						tsql4 += " and po.id in " + parentID.toString() + " ";
						tsql5 += " and po.id in " + parentID.toString() + " ";
						tsql6 += " and po.id in " + parentID.toString() + " ";
					}
					
				}
				
				if (!subjectId.equals("all")) {
					String[] subjectIds = subjectId.split(",");
					if(!subjectIds[0].equals("all")){
						StringBuilder parentID = new StringBuilder();
						parentID.append("(");
						for(int j = 0; j< subjectIds.length; j++){
							parentID.append("'"+subjectIds[j]+"',");
						}
						parentID.delete(parentID.length()-1, parentID.length());
						parentID.append(")");
						
						tsql1 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql2 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql3 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql4 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql5 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql6 += " and t.fk_subject in " + parentID.toString() + " ";
					}
					
				}
				
				count1 = Integer.parseInt(peProApplyNoService.executeSQL(tsql1).get(0).toString());
				count2 = Integer.parseInt(peProApplyNoService.executeSQL(tsql2).get(0).toString());
				count3 = Integer.parseInt(peProApplyNoService.executeSQL(tsql3).get(0).toString());
				count4 = Integer.parseInt(peProApplyNoService.executeSQL(tsql4).get(0).toString());
				count5 = Integer.parseInt(peProApplyNoService.executeSQL(tsql5).get(0).toString());
				count6 = Integer.parseInt(peProApplyNoService.executeSQL(tsql6).get(0).toString());
				
				
			}
			
			
	
			float f1 = (float) count1 / cxcount;
			float f2 = (float) count2 / cxcount;
			float f3 = (float) count3 / cxcount;
			float f4 = (float) count4 / cxcount;
			float f5 = (float) count5 / cxcount;
			float f6 = (float) count6 / cxcount;
	
			String lv1 = df.format(f1);
			String lv2 = df.format(f2);
			String lv3 = df.format(f3);
			String lv4 = df.format(f4);
			String lv5 = df.format(f5);
			String lv6 = df.format(f6);
	
	
			HSSFRow row = sheet1.createRow((short) k);
			k++;
			
			row.createCell(0).setCellValue(provinceName);
			row.createCell(1).setCellValue(cxcount);
			row.createCell(2).setCellValue(count1);
			row.createCell(3).setCellValue(lv1);
			row.createCell(4).setCellValue(count2);
			row.createCell(5).setCellValue(lv2);
			row.createCell(6).setCellValue(count3);
			row.createCell(7).setCellValue(lv3);
			row.createCell(8).setCellValue(count4);
			row.createCell(9).setCellValue(lv4);
			row.createCell(10).setCellValue(count5);
			row.createCell(11).setCellValue(lv5);
			row.createCell(12).setCellValue(count6);
			row.createCell(13).setCellValue(lv6);
			
			countz += cxcount;
			countcs += count1;
			countpxc += count2;
			countpzq += count3;
			countpx += count4;
			countpc += count5;
			countptc += count6;

		}
		

		
		HSSFRow row = sheet1.createRow((short) k);
		
		row.createCell(0).setCellValue("合计");
		row.createCell(1).setCellValue(countz);
		row.createCell(2).setCellValue(countcs);
		row.createCell(4).setCellValue(countpxc);
		row.createCell(6).setCellValue(countpzq);
		row.createCell(8).setCellValue(countpx);
		row.createCell(10).setCellValue(countpc);
		row.createCell(12).setCellValue(countptc);
		
		float f6 = (float) countcs / countz;
		float f7 = (float) countpxc / countz;
		float f8 = (float) countpzq / countz;
		float f9 = (float) countpx / countz;
		float f10 = (float) countpc / countz;
		float f12 = (float) countptc / countz;
		
		String lv6 = df.format(f6);
		String lv7 = df.format(f7);
		String lv8 = df.format(f8);
		String lv9 = df.format(f9);
		String lv10 = df.format(f10);
		String lv12 = df.format(f12);
		
		row.createCell(3).setCellValue(lv6);
		row.createCell(5).setCellValue(lv7);
		row.createCell(7).setCellValue(lv8);
		row.createCell(9).setCellValue(lv9);
		row.createCell(11).setCellValue(lv10);
		row.createCell(13).setCellValue(lv12);
		
		try {
			String filename = "示范性学校所在区域维度人数统计导出";
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
	
	public String  getSql3(String tempSql,String key){
		String tsql1 = "select count(*) " 
				+ " from PE_TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join pe_pro_applyno po on t.fk_pro_applyno = po.id "
				+ " left outer join ENUM_CONST enumconstb19_ on t.FK_STATUS_TRAINING=enumconstb19_.ID"
				+ " left outer join ENUM_CONST enumconstb2_ on t.FK_CHECKED_TRAINEE=enumconstb2_.ID "
				+ " left join UNIT_ATTRIBUTE ua on t.fk_unit_attribute = ua.id "
				+ " left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id "
				+ " where 1=1 and p.id='" + tempSql + "' and ua.id='"+key+"'" 
				+ " and t.FK_TRAINING_UNIT is not null and (enumconstb19_.NAME like '新增' or enumconstb19_.NAME='正常') and enumconstb2_.NAME='审核已通过'";
		
		return tsql1;
	}
	
	
	/**
	 * 示范性学校类别维度人数统计
	 */
	public void adminTraineeNumForSFLB() {
		String parentId = request.getParameter("parent");
		String subjectId = request.getParameter("subject");

		if (parentId == null) {
			parentId = "all";
		}
		
		if (subjectId == null) {
			subjectId = "all";
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
		
		String sqlcount = "select count(*) ";
		String sql = "select p.id ,p.name as provinceName,count(*) ";
		
		sql += "from PE_TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join pe_pro_applyno po on t.fk_pro_applyno = po.id ";
		sqlcount += "from PE_TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join pe_pro_applyno po on t.fk_pro_applyno = po.id ";
		
		sql += "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id ";
		sqlcount += "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id ";
		
		sql += " left outer join ENUM_CONST enumconstb19_ on t.FK_STATUS_TRAINING=enumconstb19_.ID";
		sqlcount += " left outer join ENUM_CONST enumconstb19_ on t.FK_STATUS_TRAINING=enumconstb19_.ID";
		
		sql += " left outer join ENUM_CONST enumconstb2_ on t.FK_CHECKED_TRAINEE=enumconstb2_.ID ";
		sqlcount += " left outer join ENUM_CONST enumconstb2_ on t.FK_CHECKED_TRAINEE=enumconstb2_.ID ";
		
		sql += " left join UNIT_TYPE ua on t.fk_unit_type = ua.id ";
		sqlcount += " left join UNIT_TYPE ua on t.fk_unit_type = ua.id ";
		
		sql += " where 1=1 and (enumconstb19_.NAME like '新增' or enumconstb19_.NAME='正常') and enumconstb2_.NAME='审核已通过'";
		sqlcount += " where 1=1 and (enumconstb19_.NAME like '新增' or enumconstb19_.NAME='正常') and enumconstb2_.NAME='审核已通过'";
		
		sql += " and ua.id is not null";
		sqlcount += " and ua.id is not null";
		
		if (!parentId.equals("all")) {
			String[] parentIds = parentId.split(",");
			if(!parentIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< parentIds.length; i++){
					parentID.append("'"+parentIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and po.id in " + parentID.toString() + " ";
				sqlcount += " and po.id in " + parentID.toString() + " ";
			}
			
		}
		
		if (!subjectId.equals("all")) {
			String[] subjectIds = subjectId.split(",");
			if(!subjectIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< subjectIds.length; i++){
					parentID.append("'"+subjectIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and t.fk_subject in " + parentID.toString() + " ";
				sqlcount += " and t.fk_subject in " + parentID.toString() + " ";
			}
			
		}
		
		sql += " group by p.id,p.name,p.code order by p.code";
		sqlcount += " group by p.id,p.name,p.code order by p.code";
		
		List li = peProApplyNoService.executeSQL(sqlcount);
		
		String strCount = "0";
		if (li != null && li.size() > 0) {
			strCount = li.size()+"";
		}
		
		int totalCount = Integer.parseInt(strCount);
		int tol = totalCount / limit;
		if ((totalCount % limit) > 0) {
			tol += 1;
		}
		
		
		li = peProApplyNoService.getListPage(sql, start, limit);
		
		String cellString = "";
		
		DecimalFormat df = new DecimalFormat();
		String style = "0.00%";
		df.applyPattern(style);
		
		String nsql = "select t.id,t.name from UNIT_TYPE t where 1=1 order by t.NAME";
		
		List listn = peProApplyNoService.getListPage(nsql, -1, -1);
		String cells = "{\"id\":\"0\",\"cell\":[\"省份\",\"总人数\",\"";
		for(int i = 0; i < listn.size(); i++){
			String name = ((Object[])listn.get(i))[1].toString();
			cells+=(name + "人数\",\"占比\",\"");
		}
		if (cells.length() > 0) {
			cells = cells.substring(0, cells.length() - 2);
		}
		cells+="]},";
		
		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String tmprovinceId = obj[0].toString();
			String provinceName = obj[1].toString();
			int cxcount = Integer.valueOf(obj[2].toString());
	
			String t = "{\"id\":\"" + tmprovinceId  + "\",\"cell\":[\"" + provinceName + "\",\"" + cxcount + "\",\"";
			if(listn != null && listn.size() >0){
				int count1 = 0;
				for(int m = 0; m < listn.size(); m++){
					String nid = ((Object[])listn.get(m))[0].toString();
					String tsql1 = getSql4(tmprovinceId,nid);
					if (!parentId.equals("all")) {
						String[] parentIds = parentId.split(",");
						if(!parentIds[0].equals("all")){
							StringBuilder parentID = new StringBuilder();
							parentID.append("(");
							for(int j = 0; j< parentIds.length; j++){
								parentID.append("'"+parentIds[j]+"',");
							}
							parentID.delete(parentID.length()-1, parentID.length());
							parentID.append(")");
							
							tsql1 += " and po.id in " + parentID.toString() + " ";
						}
					}
					
					if (!subjectId.equals("all")) {
						String[] subjectIds = subjectId.split(",");
						if(!subjectIds[0].equals("all")){
							StringBuilder parentID = new StringBuilder();
							parentID.append("(");
							for(int j = 0; j< subjectIds.length; j++){
								parentID.append("'"+subjectIds[j]+"',");
							}
							parentID.delete(parentID.length()-1, parentID.length());
							parentID.append(")");
							
							tsql1 += " and t.fk_subject in " + parentID.toString() + " ";
						}
						
					}
					count1 = Integer.parseInt(peProApplyNoService.executeSQL(tsql1).get(0).toString());
					float f1 = (float) count1 / cxcount;
					String lv1 = df.format(f1);
//					cells+=(name + "人数\",\"占比\",\"");
					t += (count1 + "\",\"" + lv1 + "\",\"");
				}
				if (t.length() > 0) {
					t = t.substring(0, t.length() - 2);
				}
				t+="]}";
			}
	
			cellString += t + ",";



		}
		cells+=cellString;
		if (cells.length() > 0) {
			cells = cells.substring(0, cells.length() - 1);
		}
	
		String retJson = "{\"page\":\"" + start + "\",\"total\":\"" + tol + "\",\"records\":\"" + totalCount + "\",\"rows\":[" + cells + "]}";
	
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
	
	/**
	 * 示范性学校类别维度人数统计导出
	 */
	public void importTraineeNumForSFLB() {
		

		String parentId = request.getParameter("parent");
		String subjectId = request.getParameter("subject");

		if (parentId == null) {
			parentId = "all";
		}
		
		if (subjectId == null) {
			subjectId = "all";
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
		
		String sql = "select p.id ,p.name as provinceName,count(*) ";
		
		sql += "from PE_TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join pe_pro_applyno po on t.fk_pro_applyno = po.id ";
		
		sql += "left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id ";
		
		sql += " left outer join ENUM_CONST enumconstb19_ on t.FK_STATUS_TRAINING=enumconstb19_.ID";
		
		sql += " left outer join ENUM_CONST enumconstb2_ on t.FK_CHECKED_TRAINEE=enumconstb2_.ID ";
		
		sql += " left join UNIT_TYPE ua on t.fk_unit_type = ua.id ";
		
		sql += " where 1=1 and (enumconstb19_.NAME like '新增' or enumconstb19_.NAME='正常') and enumconstb2_.NAME='审核已通过'";
		
		sql += " and ua.id is not null";
		
		if (!parentId.equals("all")) {
			String[] parentIds = parentId.split(",");
			if(!parentIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< parentIds.length; i++){
					parentID.append("'"+parentIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and po.id in " + parentID.toString() + " ";
			}
			
		}
		
		if (!subjectId.equals("all")) {
			String[] subjectIds = subjectId.split(",");
			if(!subjectIds[0].equals("all")){
				StringBuilder parentID = new StringBuilder();
				parentID.append("(");
				for(int i = 0; i< subjectIds.length; i++){
					parentID.append("'"+subjectIds[i]+"',");
				}
				parentID.delete(parentID.length()-1, parentID.length());
				parentID.append(")");
				
				sql += " and t.fk_subject in " + parentID.toString() + " ";
			}
			
		}
		
		sql += " group by p.id,p.name,p.code order by p.code";
		
		List li = peProApplyNoService.getListPage(sql, start, limit);
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet("sheet1");
		int k = 0;
		HSSFRow rowheader = sheet1.createRow((short) k);
		k++;
		
		rowheader.createCell(0).setCellValue("省份");
		rowheader.createCell(1).setCellValue("总人数");
		rowheader.createCell(2).setCellValue("初中人数");
		rowheader.createCell(3).setCellValue("占比");
		rowheader.createCell(4).setCellValue("高中人数");
		rowheader.createCell(5).setCellValue("占比");
		rowheader.createCell(6).setCellValue("教学点人数");
		rowheader.createCell(7).setCellValue("占比");
		rowheader.createCell(8).setCellValue("九年一贯制学校人数");
		rowheader.createCell(9).setCellValue("占比");
		rowheader.createCell(10).setCellValue("其他人数");
		rowheader.createCell(11).setCellValue("占比");
		rowheader.createCell(12).setCellValue("十二年一贯制学校人数");
		rowheader.createCell(13).setCellValue("占比");
		rowheader.createCell(14).setCellValue("完全中学人数");
		rowheader.createCell(15).setCellValue("占比");
		rowheader.createCell(16).setCellValue("小学人数");
		rowheader.createCell(17).setCellValue("占比");
		rowheader.createCell(18).setCellValue("幼儿园人数");
		rowheader.createCell(19).setCellValue("占比");

		int countz = 0;
		int countyey = 0;
		int countpxx = 0;
		int countpcz = 0;
		int countgz = 0;
		int countwq = 0;
		int countjn = 0;
		int countsen = 0;
		int countjxd = 0;
		int countqt = 0;
		
		DecimalFormat df = new DecimalFormat();
		String style = "0.00%";
		df.applyPattern(style);
		
		String nsql = "select t.id,t.name from UNIT_TYPE t where 1=1 order by t.NAME";
		
		List listn = peProApplyNoService.getListPage(nsql, -1, -1);
		
		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String tmprovinceId = obj[0].toString();
			String provinceName = obj[1].toString();
			int cxcount = Integer.valueOf(obj[2].toString());
	
			int count1 = 0;
			int count2 = 0;
			int count3 = 0;
			int count4 = 0;
			int count5 = 0;
			int count6 = 0;
			int count7 = 0;
			int count8 = 0;
			int count9 = 0;
			if(listn != null && listn.size() >0){
				String nid = ((Object[])listn.get(0))[0].toString();
				String nid2 = ((Object[])listn.get(1))[0].toString();
				String nid3 = ((Object[])listn.get(2))[0].toString();
				String nid4 = ((Object[])listn.get(3))[0].toString();
				String nid5 = ((Object[])listn.get(4))[0].toString();
				String nid6 = ((Object[])listn.get(5))[0].toString();
				String nid7 = ((Object[])listn.get(6))[0].toString();
				String nid8 = ((Object[])listn.get(7))[0].toString();
				String nid9 = ((Object[])listn.get(8))[0].toString();
				
				String tsql1 = getSql4(tmprovinceId,nid);
				
				String tsql2 = getSql4(tmprovinceId,nid2);
				String tsql3 = getSql4(tmprovinceId,nid3);
				String tsql4 = getSql4(tmprovinceId,nid4);
				String tsql5 = getSql4(tmprovinceId,nid5);
				String tsql6 = getSql4(tmprovinceId,nid6);
				String tsql7 = getSql4(tmprovinceId,nid7);
				String tsql8 = getSql4(tmprovinceId,nid8);
				String tsql9 = getSql4(tmprovinceId,nid9);
				
				if (!parentId.equals("all")) {
					String[] parentIds = parentId.split(",");
					if(!parentIds[0].equals("all")){
						StringBuilder parentID = new StringBuilder();
						parentID.append("(");
						for(int j = 0; j< parentIds.length; j++){
							parentID.append("'"+parentIds[j]+"',");
						}
						parentID.delete(parentID.length()-1, parentID.length());
						parentID.append(")");
						
						tsql1 += " and po.id in " + parentID.toString() + " ";
						tsql2 += " and po.id in " + parentID.toString() + " ";
						tsql3 += " and po.id in " + parentID.toString() + " ";
						tsql4 += " and po.id in " + parentID.toString() + " ";
						tsql5 += " and po.id in " + parentID.toString() + " ";
						tsql6 += " and po.id in " + parentID.toString() + " ";
						tsql7 += " and po.id in " + parentID.toString() + " ";
						tsql8 += " and po.id in " + parentID.toString() + " ";
						tsql9 += " and po.id in " + parentID.toString() + " ";
					}
					
				}
				
				if (!subjectId.equals("all")) {
					String[] subjectIds = subjectId.split(",");
					if(!subjectIds[0].equals("all")){
						StringBuilder parentID = new StringBuilder();
						parentID.append("(");
						for(int j = 0; j< subjectIds.length; j++){
							parentID.append("'"+subjectIds[j]+"',");
						}
						parentID.delete(parentID.length()-1, parentID.length());
						parentID.append(")");
						
						tsql1 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql2 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql3 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql4 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql5 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql6 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql7 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql8 += " and t.fk_subject in " + parentID.toString() + " ";
						tsql9 += " and t.fk_subject in " + parentID.toString() + " ";
					}
					
				}
				
				count1 = Integer.parseInt(peProApplyNoService.executeSQL(tsql1).get(0).toString());
				count2 = Integer.parseInt(peProApplyNoService.executeSQL(tsql2).get(0).toString());
				count3 = Integer.parseInt(peProApplyNoService.executeSQL(tsql3).get(0).toString());
				count4 = Integer.parseInt(peProApplyNoService.executeSQL(tsql4).get(0).toString());
				count5 = Integer.parseInt(peProApplyNoService.executeSQL(tsql5).get(0).toString());
				count6 = Integer.parseInt(peProApplyNoService.executeSQL(tsql6).get(0).toString());
				count7 = Integer.parseInt(peProApplyNoService.executeSQL(tsql7).get(0).toString());
				count8 = Integer.parseInt(peProApplyNoService.executeSQL(tsql8).get(0).toString());
				count9 = Integer.parseInt(peProApplyNoService.executeSQL(tsql9).get(0).toString());
				
				
			}
			
			
	
			float f1 = (float) count1 / cxcount;
			float f2 = (float) count2 / cxcount;
			float f3 = (float) count3 / cxcount;
			float f4 = (float) count4 / cxcount;
			float f5 = (float) count5 / cxcount;
			float f6 = (float) count6 / cxcount;
			float f7 = (float) count7 / cxcount;
			float f8 = (float) count8 / cxcount;
			float f9 = (float) count9 / cxcount;
	
			String lv1 = df.format(f1);
			String lv2 = df.format(f2);
			String lv3 = df.format(f3);
			String lv4 = df.format(f4);
			String lv5 = df.format(f5);
			String lv6 = df.format(f6);
			String lv7 = df.format(f7);
			String lv8 = df.format(f8);
			String lv9 = df.format(f9);
	
			HSSFRow row = sheet1.createRow((short) k);
			k++;
			
			row.createCell(0).setCellValue(provinceName);
			row.createCell(1).setCellValue(cxcount);
			row.createCell(2).setCellValue(count1);
			row.createCell(3).setCellValue(lv1);
			row.createCell(4).setCellValue(count2);
			row.createCell(5).setCellValue(lv2);
			row.createCell(6).setCellValue(count3);
			row.createCell(7).setCellValue(lv3);
			row.createCell(8).setCellValue(count4);
			row.createCell(9).setCellValue(lv4);
			row.createCell(10).setCellValue(count5);
			row.createCell(11).setCellValue(lv5);
			row.createCell(12).setCellValue(count6);
			row.createCell(13).setCellValue(lv6);
			row.createCell(14).setCellValue(count7);
			row.createCell(15).setCellValue(lv7);
			row.createCell(16).setCellValue(count8);
			row.createCell(17).setCellValue(lv8);
			row.createCell(18).setCellValue(count9);
			row.createCell(19).setCellValue(lv9);
			
			countz += cxcount;
			countyey += count1;
			countpxx += count2;
			countpcz += count3;
			countgz += count4;
			countwq += count5;
			countjn += count6;
			countsen += count7;
			countjxd += count8;
			countqt += count9;

		}
		

		
		HSSFRow row = sheet1.createRow((short) k);
		
		row.createCell(0).setCellValue("合计");
		row.createCell(1).setCellValue(countz);
		row.createCell(2).setCellValue(countyey);
		row.createCell(4).setCellValue(countpxx);
		row.createCell(6).setCellValue(countpcz);
		row.createCell(8).setCellValue(countgz);
		row.createCell(10).setCellValue(countwq);
		row.createCell(12).setCellValue(countjn);
		row.createCell(14).setCellValue(countsen);
		row.createCell(16).setCellValue(countjxd);
		row.createCell(18).setCellValue(countqt);
		
		float f6 = (float) countyey / countz;
		float f7 = (float) countpxx / countz;
		float f8 = (float) countpcz / countz;
		float f9 = (float) countgz / countz;
		float f10 = (float) countwq / countz;
		float f11 = (float) countjn / countz;
		float f12 = (float) countsen / countz;
		float f13 = (float) countjxd / countz;
		float f14 = (float) countqt / countz;
		
		
		String lv6 = df.format(f6);
		String lv7 = df.format(f7);
		String lv8 = df.format(f8);
		String lv9 = df.format(f9);
		String lv10 = df.format(f10);
		String lv11 = df.format(f11);
		String lv12 = df.format(f12);
		String lv13 = df.format(f13);
		String lv14 = df.format(f14);
		
		row.createCell(3).setCellValue(lv6);
		row.createCell(5).setCellValue(lv7);
		row.createCell(7).setCellValue(lv8);
		row.createCell(9).setCellValue(lv9);
		row.createCell(11).setCellValue(lv10);
		row.createCell(13).setCellValue(lv11);
		row.createCell(15).setCellValue(lv12);
		row.createCell(17).setCellValue(lv13);
		row.createCell(19).setCellValue(lv14);
		
		try {
			String filename = "示范性学校类别维度人数统计导出";
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
	
	
	public String  getSql4(String tempSql,String key){
		String tsql1 = "select count(*) " 
				+ " from PE_TRAINEE t join PE_PROVINCE p on t.fk_province = p.id join pe_pro_applyno po on t.fk_pro_applyno = po.id "
				+ " left outer join ENUM_CONST enumconstb19_ on t.FK_STATUS_TRAINING=enumconstb19_.ID"
				+ " left outer join ENUM_CONST enumconstb2_ on t.FK_CHECKED_TRAINEE=enumconstb2_.ID "
				+ " left join UNIT_TYPE ua on t.fk_unit_type = ua.id "
				+ " left join PE_UNIT u on t.fk_training_unit = u.id left join PE_SUBJECT s on t.fk_subject = s.id "
				+ " where 1=1 and p.id='" + tempSql + "' and ua.id='"+key+"'" 
				+ " and t.FK_TRAINING_UNIT is not null and (enumconstb19_.NAME like '新增' or enumconstb19_.NAME='正常') and enumconstb2_.NAME='审核已通过'";
		
		return tsql1;
	}
	

	public void importtrainHistory() {
		String sql = session.getAttribute("traineeJHSql").toString();
		List li = peProApplyNoService.executeSQL(sql);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet("sheet1");
		int i = 0;
		HSSFRow rowheader = sheet1.createRow((short) i);
		i++;

		rowheader.createCell((short) 0).setCellValue("省份");
		rowheader.createCell((short) 1).setCellValue("所属项目");
		rowheader.createCell((short) 2).setCellValue("子项目");
		rowheader.createCell((short) 3).setCellValue("培训单位");
		rowheader.createCell((short) 4).setCellValue("学科");
		rowheader.createCell((short) 5).setCellValue("姓名");
		rowheader.createCell((short) 6).setCellValue("手机");
		rowheader.createCell((short) 7).setCellValue("电子邮件");
		rowheader.createCell((short) 8).setCellValue("工作单位");
		rowheader.createCell((short) 9).setCellValue("办公室电话");
		rowheader.createCell((short) 10).setCellValue("职务");
		rowheader.createCell((short) 11).setCellValue("职称");
		rowheader.createCell((short) 12).setCellValue("证书编号");
		rowheader.createCell((short) 13).setCellValue("备注");
		rowheader.createCell((short) 14).setCellValue("状态");

		for (int ti = 0; ti < li.size(); ti++) {
			Object[] obj = (Object[]) li.get(ti);

			HSSFRow row = sheet1.createRow((short) i);
			i++;

			String provinceName = "";
			if (obj[1] != null) {
				provinceName = obj[1].toString();
			}
			String traineename = "";
			if (obj[2] != null) {
				traineename = obj[2].toString();
			}
			String child_project = "";
			if (obj[3] != null) {
				child_project = obj[3].toString();
			}
			String training_unit = "";
			if (obj[4] != null) {
				training_unit = obj[4].toString();
			}
			String subject = "";
			if (obj[5] != null) {
				subject = obj[5].toString();
			}
			String fk_applyno = "";
			if (obj[6] != null) {
				fk_applyno = obj[6].toString();
			}
			String telephone = "";
			if (obj[7] != null) {
				telephone = obj[7].toString();
			}
			String email = "";
			if (obj[8] != null) {
				email = obj[8].toString();
			}
			String work_place = "";
			if (obj[9] != null) {
				work_place = obj[9].toString();
			}
			String office_phone = "";
			if (obj[10] != null) {
				office_phone = obj[10].toString();
			}
			String zhiwu = "";
			if (obj[11] != null) {
				zhiwu = obj[11].toString();
			}
			String zhicheng = "";
			if (obj[12] != null) {
				zhicheng = obj[12].toString();
			}
			String dipcode = "";
			if (obj[13] != null) {
				dipcode = obj[13].toString();
			}
			String beizhu1 = "";
			if (obj[14] != null) {
				beizhu1 = obj[14].toString();
			}
			String status = obj[15].toString().equals("1") ? "通过" : "未通过";

			row.createCell((short) 0).setCellValue(provinceName);
			row.createCell((short) 1).setCellValue(fk_applyno);
			row.createCell((short) 2).setCellValue(child_project);
			row.createCell((short) 3).setCellValue(training_unit);
			row.createCell((short) 4).setCellValue(subject);
			row.createCell((short) 5).setCellValue(traineename);
			row.createCell((short) 6).setCellValue(telephone);
			row.createCell((short) 7).setCellValue(email);
			row.createCell((short) 8).setCellValue(work_place);
			row.createCell((short) 9).setCellValue(office_phone);
			row.createCell((short) 10).setCellValue(zhiwu);
			row.createCell((short) 11).setCellValue(zhicheng);
			row.createCell((short) 12).setCellValue(dipcode);
			row.createCell((short) 13).setCellValue(beizhu1);
			row.createCell((short) 14).setCellValue(status);

		}

		try {
			String filename = "学员管理导出";
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

	public void importadminTraineelv() {

		String sql = session.getAttribute("adminTraineelvSql").toString();
		List li = peProApplyNoService.executeSQL(sql);

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet("sheet1");
		int i = 0;
		HSSFRow rowheader = sheet1.createRow((short) i);
		i++;

		rowheader.createCell((short) 0).setCellValue("省份");
		rowheader.createCell((short) 1).setCellValue("所属项目");

		rowheader.createCell((short) 2).setCellValue("计划学员人数");
		rowheader.createCell((short) 3).setCellValue("参训学员人数");
		rowheader.createCell((short) 4).setCellValue("参训率");

		DecimalFormat df = new DecimalFormat();
		String style = "0.00%";
		df.applyPattern(style);

		for (int ti = 0; ti < li.size(); ti++) {
			Object[] obj = (Object[]) li.get(ti);

			HSSFRow row = sheet1.createRow((short) i);
			i++;

			String tmprovinceId = obj[0].toString();
			String tmparentId = obj[1].toString();

			String provinceName = obj[2].toString();
			String parentName = obj[3].toString();
			int cxcount = Integer.valueOf(obj[4].toString());

			String tsql = "select count(*) " + "from JHTRAINEE t join PE_PROVINCE p on t.fk_province = p.id join prochildapplyno po on t.fk_applyno = po.id " + "join PE_UNIT u on t.fk_training_unit = u.id join PE_SUBJECT s on t.fk_subject = s.id "
					+ " where 1=1 and p.id='" + tmprovinceId + "' and po.parentid='" + tmparentId + "'";

			int jhcount = Integer.parseInt(peProApplyNoService.executeSQL(tsql).get(0).toString());
			if (jhcount == 0) {
				jhcount = cxcount;
			}

			float f = (float) cxcount / jhcount;

			String lv = df.format(f);

			row.createCell((short) 0).setCellValue(provinceName);
			row.createCell((short) 1).setCellValue(parentName);
			row.createCell((short) 2).setCellValue(jhcount);
			row.createCell((short) 3).setCellValue(cxcount);
			row.createCell((short) 4).setCellValue(lv);

		}

		try {
			String filename = "学员统计导出";
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

	// 参训学员编辑
	public void trainEdit() {

		String tid = request.getParameter("tid");
		String provinceId = request.getParameter("provinceId");
		String parentId = request.getParameter("parentId");
		String projectId = request.getParameter("projectId");
		String unitId = request.getParameter("unitId");
		String subjectId = request.getParameter("subjectId");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone"); 
		String mail = request.getParameter("mail");
		String tel = request.getParameter("tel");
		String workplace = request.getParameter("workplace");
		String dipcode = request.getParameter("dipcode");

		tid = tid == null ? "" : tid;
		provinceId = provinceId == null ? "" : provinceId;
		parentId = parentId == null ? "" : parentId;
		projectId = projectId == null ? "" : projectId;
		unitId = unitId == null ? "" : unitId;
		subjectId = subjectId == null ? "" : subjectId;
		name = name == null ? "" : name;
		phone = phone == null ? "" : phone;
		mail = mail == null ? "" : mail;
		workplace = workplace == null ? "" : workplace;
		dipcode = dipcode == null ? "" : dipcode;
		tel = tel == null ? "" : tel;

		/*
		 * 新增属性
		 */
		String city = request.getParameter("cityId") == null ? "" : request.getParameter("cityId");
		String county = request.getParameter("countyId") == null ? "" : request.getParameter("countyId");
		
		String duty = request.getParameter("jobTitleId") == null ? "":request.getParameter("jobTitleId");//职称
		String volk = request.getParameter("folkId") == null ? "":request.getParameter("folkId");//民族
		String idcard = request.getParameter("idcard") == null ? "":request.getParameter("idcard");//身份证号 需验证
		String property = request.getParameter("unitAttributeId") == null ? "":request.getParameter("unitAttributeId");//单位属性、
		String unittype = request.getParameter("unitTypeId") == null ? "":request.getParameter("unitTypeId");//单位属性、
		String mainTeachingGrade = request.getParameter("mainGradeId") == null ? "":request.getParameter("mainGradeId");//主要教学学段
		String mainTeachingSubject = request.getParameter("mainSubjectId") == null ? "":request.getParameter("mainSubjectId");//主要教学学科
		String education = request.getParameter("educationId") == null ? "":request.getParameter("educationId");//教师学历
		String graduation = request.getParameter("graduation") == null ? "":request.getParameter("graduation");//毕业院校
		String major = request.getParameter("major") == null ? "":request.getParameter("major");//所学专业
//		String hiredate = request.getParameter("hiredate") == null ? "":request.getParameter("hiredate");//任教（入职）时间
		mail = replaceAll(mail);
		graduation = replaceAll(graduation);
		major = replaceAll(major);
		phone = replaceAll(phone);
		/*
		 * 规范化数据必填，校验非空及对应格式
		 */
		if("".equals(city) || "".equals(county) || "".equals(graduation) || "".equals(major) || "".equals(duty) || "".equals(volk) || "".equals(idcard) || "".equals(property) || "".equals(mainTeachingGrade) || "".equals(mainTeachingSubject) || "".equals(education) || "".equals(unittype)){
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
		
		if("all".equals(provinceId) || "all".equals(parentId) || "all".equals(projectId) || "all".equals(unitId) || "all".equals(subjectId) || "all".equals(city) || "all".equals(county) || "".equals(graduation) || "".equals(major) || "all".equals(duty) || "all".equals(volk) || "".equals(idcard) || "all".equals(property) || "all".equals(mainTeachingGrade) || "all".equals(mainTeachingSubject) || "all".equals(education) || "all".equals(unittype)){
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
		/*
		 * 校验格式
		 */
		//身份证号
		if(!"".equals(IdCardUtil.IDCardValidate(idcard))){
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
		
		
		String method = request.getParameter("method");

		if (method.equals("add")) {
			UUID uuid = UUID.randomUUID();
			String vid = uuid.toString().replaceAll("-", "");

			// 插入前先判断有没有错误
			if (name.equals("") || phone.equals("") || dipcode.equals("")) {
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

			String sqlcount = "select count(*) from TRAINEE t where 1=1 ";
			sqlcount += " and t.FK_PROVINCE='" + provinceId + "'";
			sqlcount += " and t.FK_APPLYNO='" + projectId + "'";

			sqlcount += " and t.FK_TRAINING_UNIT='" + unitId + "'";
			sqlcount += " and t.FK_SUBJECT='" + subjectId + "'";

			sqlcount += " and t.name='" + name + "'";
			sqlcount += " and t.telephone='" + phone + "'";
			
			sqlcount += " and t.idcard='" + idcard + "'";

			int count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
			if (count > 0) {
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

			sqlcount = "select count(*) from TRAINEE t where 1=1 and t.DIPCODE='" + dipcode + "'";
			count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
			if (count > 0) {
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

			// 插入 pe_trainee
//			String sql = "insert into TRAINEE(id,name,fk_unit_from,telephone,email,office_phone,FK_GRADUTED," + "FK_SUBJECT,FK_PROVINCE,WORK_PLACE,ZHIWU,FK_TRAINING_UNIT," + "FK_APPLYNO,ZHICHENG,DIPCODE,beizhu1) values(";
//			sql += "'" + vid + "','" + name + "','402880142a103b95012a103edcdd0001','" + phone + "','" + mail + "','" + tel + "','isgraduateidflag2','" + subjectId + "','" + provinceId + "','" + workplace + "','" + "" + "','" + unitId + "','" + projectId + "','" + "" + "','" + dipcode + "','" + ""
//					+ "')";
			
//			String sql = "insert into TRAINEE(id,name,fk_unit_from,telephone,email,office_phone,FK_GRADUTED," + "FK_SUBJECT,FK_PROVINCE,WORK_PLACE,ZHIWU,FK_TRAINING_UNIT," + "FK_APPLYNO,ZHICHENG,DIPCODE,beizhu1,volk,idcard,property,main_teaching_grade,main_teaching_subject,education,"
//			+ "graduation,major,hiredate) values(";
//			sql += "'" + vid + "','" + name + "','402880142a103b95012a103edcdd0001','" + phone + "','" + mail + "','" + tel + "','isgraduateidflag2','" + subjectId + "','" + provinceId + "','" + workplace + "','" + "" + "','" + unitId + "','" + projectId + "','" + duty + "','" + dipcode + "','" + ""
//			+ "','" + volk + "','" + idcard + "','" + property + "','" + mainTeachingGrade +  "','" + mainTeachingSubject + "','" + education + "','" + graduation + "','" + major + "','" + hiredate + "')";
			String sql = "insert into TRAINEE(id,name,fk_unit_from,telephone,email,office_phone,FK_GRADUTED," + "FK_SUBJECT,FK_PROVINCE,WORK_PLACE,ZHIWU,FK_TRAINING_UNIT," + "FK_APPLYNO,ZHICHENG,DIPCODE,beizhu1,"
					+ "fk_city,fk_county,fk_jobtitle,fk_folk,fk_main_teaching_grade,fk_main_teaching_subject,"
					+ "fk_unitattribute,fk_education,idcard,"
					+ "graduation,major,fk_unit_type,status) values(";
					sql += "'" + vid + "','" + name + "','402880142a103b95012a103edcdd0001','" + phone + "','" + mail + "','" + tel + "','isgraduateidflag2','" + subjectId + "','" + provinceId + "','" + workplace + "','" + "" + "','" + unitId + "','" + projectId + "','" + "" + "','" + dipcode + "','" + ""
					+ "','" + city + "','" + county + "','" + duty + "','" + volk + "','" + mainTeachingGrade + "','" + mainTeachingSubject +  "','" + property + "','" + education + "','" + idcard + "','" + graduation + "','" + major + "','" + unittype + "',1)";


			peProApplyService.executeSQLUpdate(sql);

			String retJson = "{\"id\":\"" + vid + "\"}";
			try {

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write(retJson);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			StringBuilder sb = new StringBuilder();
//			sb.append("update TRAINEE set ").append("name='" + name).append("',telephone='" + phone).append("',email='" + mail).append("',FK_SUBJECT='" + subjectId).append("',FK_PROVINCE='" + provinceId).append("',WORK_PLACE='" + workplace).append("',FK_TRAINING_UNIT='" + unitId)
//					.append("',FK_APPLYNO='" + projectId).append("',DIPCODE='" + dipcode).append("' where id='" + tid + "'");
			
			sb.append("update TRAINEE set ").append("name='" + name).append("',telephone='" + phone).append("',email='" + mail)
//			.append("',FK_SUBJECT='" + subjectId)
			.append("',FK_PROVINCE='" + provinceId).append("',WORK_PLACE='" + workplace)
//			.append("',FK_TRAINING_UNIT='" + unitId)
//			.append("',FK_APPLYNO='" + projectId)
			.append("',DIPCODE='" + dipcode)
			.append("',fk_folk='" + volk).
			append("',fk_city='" + city).
			append("',fk_county='" + county).
			append("',fk_jobtitle='" + duty).
			append("',fk_unitattribute='" + property).
			append("',fk_main_teaching_grade='" + mainTeachingGrade).
			append("',fk_main_teaching_subject='" + mainTeachingSubject).
			append("',fk_education='" + education).
			append("',idcard='" + idcard).
			append("',graduation='" + graduation).
			append("',major='" + major).
			append("',fk_unit_type='" + unittype).
			append("' where id='" + tid + "'");
			
			peProApplyService.executeSQLUpdate(sb.toString());

			String retJson = "{\"id\":\"" + tid + "\"}";
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

	public void trainDelete() {
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		for (String t : ids) {
			String sql = "delete from TRAINEE  where id='" + t + "'";
			peProApplyService.executeSQLUpdate(sql);
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

	// 计划学员编辑
	public void trainJHEdit() {

		String tid = request.getParameter("tid");
		String provinceId = request.getParameter("provinceId");
		String parentId = request.getParameter("parentId");
		String projectId = request.getParameter("projectId");
		String unitId = request.getParameter("unitId");
		String subjectId = request.getParameter("subjectId");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String mail = request.getParameter("mail");
		String tel = request.getParameter("tel");
		String workplace = request.getParameter("workplace");
		String dipcode = request.getParameter("dipcode");

		tid = tid == null ? "" : tid;
		provinceId = provinceId == null ? "" : provinceId;
		parentId = parentId == null ? "" : parentId;
		projectId = projectId == null ? "" : projectId;
		unitId = unitId == null ? "" : unitId;
		subjectId = subjectId == null ? "" : subjectId;
		name = name == null ? "" : name;
		phone = phone == null ? "" : phone;
		mail = mail == null ? "" : mail;
		workplace = workplace == null ? "" : workplace;
		dipcode = dipcode == null ? "" : dipcode;
		tel = tel == null ? "" : tel;

		/*
		 * 新增属性
		 */
		String city = request.getParameter("cityId") == null ? "" : request.getParameter("cityId");
		String county = request.getParameter("countyId") == null ? "" : request.getParameter("countyId");
		
		String duty = request.getParameter("jobTitleId") == null ? "":request.getParameter("jobTitleId");//职称
		String volk = request.getParameter("folkId") == null ? "":request.getParameter("folkId");//民族
		String idcard = request.getParameter("idcard") == null ? "":request.getParameter("idcard");//身份证号 需验证
		String property = request.getParameter("unitAttributeId") == null ? "":request.getParameter("unitAttributeId");//单位属性、
		String unittype = request.getParameter("unitTypeId") == null ? "":request.getParameter("unitTypeId");//单位属性、
		String mainTeachingGrade = request.getParameter("mainGradeId") == null ? "":request.getParameter("mainGradeId");//主要教学学段
		String mainTeachingSubject = request.getParameter("mainSubjectId") == null ? "":request.getParameter("mainSubjectId");//主要教学学科
		String education = request.getParameter("educationId") == null ? "":request.getParameter("educationId");//教师学历
		String graduation = request.getParameter("graduation") == null ? "":request.getParameter("graduation");//毕业院校
		String major = request.getParameter("major") == null ? "":request.getParameter("major");//所学专业
//		String hiredate = request.getParameter("hiredate") == null ? "":request.getParameter("hiredate");//任教（入职）时间
		mail = replaceAll(mail);
		graduation = replaceAll(graduation);
		major = replaceAll(major);
		phone = replaceAll(phone);
		/*
		 * 规范化数据必填，校验非空及对应格式
		 */
		if("".equals(city) || "".equals(county) || "".equals(graduation) || "".equals(major) || "".equals(duty) || "".equals(volk) || "".equals(idcard) || "".equals(property) || "".equals(mainTeachingGrade) || "".equals(mainTeachingSubject) || "".equals(education) ||  "".equals(unittype)){
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
		
		if("all".equals(provinceId) || "all".equals(parentId) || "all".equals(projectId) || "all".equals(unitId) || "all".equals(subjectId) || "all".equals(city) || "all".equals(county) || "".equals(graduation) || "".equals(major) || "all".equals(duty) || "all".equals(volk) || "".equals(idcard) || "all".equals(property) || "all".equals(mainTeachingGrade) || "all".equals(mainTeachingSubject) || "all".equals(education) || "all".equals(unittype)){
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
		
		/*
		 * 校验格式
		 */
		//身份证号
		if(!"".equals(IdCardUtil.IDCardValidate(idcard))){
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
		
		String method = request.getParameter("method");

		if (method.equals("add")) {

			// 插入前先判断有没有错误
			if (name.equals("") || phone.equals("")) {
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

			String sqlcount = "select count(*) from JHTRAINEE t where 1=1 ";
			sqlcount += " and t.FK_PROVINCE='" + provinceId + "'";
			sqlcount += " and t.FK_APPLYNO='" + projectId + "'";

			sqlcount += " and t.FK_TRAINING_UNIT='" + unitId + "'";
			sqlcount += " and t.FK_SUBJECT='" + subjectId + "'";

			sqlcount += " and t.name='" + name + "'";
			sqlcount += " and t.telephone='" + phone + "'";
			
			sqlcount += " and t.idcard='" + idcard + "'";

			int count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
			if (count > 0) {
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

			UUID uuid = UUID.randomUUID();
			String vid = uuid.toString().replaceAll("-", "");

			// 插入 pe_trainee
//			String sql = "insert into JHTRAINEE(id,name,fk_unit_from,telephone,email,office_phone,FK_GRADUTED," + "FK_SUBJECT,FK_PROVINCE,WORK_PLACE,ZHIWU,FK_TRAINING_UNIT," + "FK_APPLYNO,ZHICHENG,DIPCODE,beizhu1) values(";
//			sql += "'" + vid + "','" + name + "','402880142a103b95012a103edcdd0001','" + phone + "','" + mail + "','" + tel + "','isgraduateidflag2','" + subjectId + "','" + provinceId + "','" + workplace + "','" + "" + "','" + unitId + "','" + projectId + "','" + duty + "','" + dipcode + "','" + ""
//					+ "')";
			String sql = "insert into JHTRAINEE(id,name,fk_unit_from,telephone,email,office_phone,FK_GRADUTED," + "FK_SUBJECT,FK_PROVINCE,WORK_PLACE,ZHIWU,FK_TRAINING_UNIT," + "FK_APPLYNO,ZHICHENG,DIPCODE,beizhu1,"
					+ "fk_city,fk_county,fk_jobtitle,fk_folk,fk_main_teaching_grade,fk_main_teaching_subject,"
					+ "fk_unitattribute,fk_education,idcard,"
					+ "graduation,major,fk_unit_type) values(";
					sql += "'" + vid + "','" + name + "','402880142a103b95012a103edcdd0001','" + phone + "','" + mail + "','" + tel + "','isgraduateidflag2','" + subjectId + "','" + provinceId + "','" + workplace + "','" + "" + "','" + unitId + "','" + projectId + "','" + "" + "','" + dipcode + "','" + ""
					+ "','" + city + "','" + county + "','" + duty + "','" + volk + "','" + mainTeachingGrade + "','" + mainTeachingSubject +  "','" + property + "','" + education + "','" + idcard + "','" + graduation + "','" + major + "','" + unittype + "')";

			peProApplyService.executeSQLUpdate(sql);

			String retJson = "{\"id\":\"" + vid + "\"}";
			try {

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write(retJson);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			StringBuilder sb = new StringBuilder();
//			sb.append("update JHTRAINEE set ").append("name='" + name).append("',telephone='" + phone).append("',email='" + mail).append("',FK_SUBJECT='" + subjectId).append("',FK_PROVINCE='" + provinceId).append("',WORK_PLACE='" + workplace).append("',FK_TRAINING_UNIT='" + unitId)
//					.append("',FK_APPLYNO='" + projectId).append("',DIPCODE='" + dipcode).append("' where id='" + tid + "'");
			
			sb.append("update JHTRAINEE set ").append("name='" + name).append("',telephone='" + phone).append("',email='" + mail)
//			.append("',FK_SUBJECT='" + subjectId)
			.append("',FK_PROVINCE='" + provinceId).append("',WORK_PLACE='" + workplace)
//			.append("',FK_TRAINING_UNIT='" + unitId)
//			.append("',FK_APPLYNO='" + projectId)
			.append("',DIPCODE='" + dipcode)
			.append("',fk_folk='" + volk).
			append("',fk_city='" + city).
			append("',fk_county='" + county).
			append("',fk_jobtitle='" + duty).
			append("',fk_unitattribute='" + property).
			append("',fk_main_teaching_grade='" + mainTeachingGrade).
			append("',fk_main_teaching_subject='" + mainTeachingSubject).
			append("',fk_education='" + education).
			append("',idcard='" + idcard).
			append("',graduation='" + graduation).
			append("',major='" + major).
//			append("',hiredate='" + hiredate).
			append("',fk_unit_type='" + unittype).
			append("' where id='" + tid + "'");
			
			peProApplyService.executeSQLUpdate(sb.toString());

			String retJson = "{\"id\":\"" + tid + "\"}";
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

	public void trainJHDelete() {
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		for (String t : ids) {
			String sql = "delete from JHTRAINEE  where id='" + t + "'";
			peProApplyService.executeSQLUpdate(sql);
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

	/**
	 * 参训学员设置结业未结业
	 */
	public void trainJY() {
		String method = request.getParameter("method");
		String id = request.getParameter("id");

		String[] ids = id.split(",");

		if (method.equals("jieye")) {
			for (String t : ids) {
				String sql = "update TRAINEE set status=2 where id='" + t + "'";
				peProApplyService.executeSQLUpdate(sql);
			}
		} else if (method.equals("nojieye")) {
			for (String t : ids) {
				String sql = "update TRAINEE set status=1 where id='" + t + "'";
				peProApplyService.executeSQLUpdate(sql);
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

	/**
	 * 计划学员设置通过未通过
	 */
	public void trainTG() {
		String method = request.getParameter("method");
		String id = request.getParameter("id");

		String[] ids = id.split(",");

		if (method.equals("tg")) {
			for (String t : ids) {
				String sql = "update JHTRAINEE set status=2 where id='" + t + "'";
				peProApplyService.executeSQLUpdate(sql);
			}
		} else if (method.equals("notg")) {
			for (String t : ids) {
				String sql = "update JHTRAINEE set status=3 where id='" + t + "'";
				peProApplyService.executeSQLUpdate(sql);
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

	public void listHistory() {

		String role = request.getParameter("role");
		String userid = request.getParameter("userid");
		String unitId = "";

		if (role != null && role.equals("1")) {
			String sql = "select u.fk_province from sso_user s join pe_manager p on s.login_id = p.login_id join pe_unit u on p.fk_unit=u.id " + "where s.login_id='" + userid + "'";
			List li = peProApplyNoService.executeSQL(sql);
			if (li.size() > 0) {
				unitId = li.get(0).toString();
			}
		}

		String provinceId = request.getParameter("provinceName");
		String projectId = request.getParameter("projectName");
		String parentId = request.getParameter("parentName");
		String dpcode = request.getParameter("dpcode");
		dpcode = dpcode == null ? "" : dpcode;

		String phone = request.getParameter("phone");
		phone = phone == null ? "" : phone;

		String unitName = request.getParameter("unitName");
		unitName = unitName == null ? "" : unitName;

		String subjectId = request.getParameter("subjectName");
		String tame = request.getParameter("name");

		String status = request.getParameter("status");
		status = status == null ? "-1" : status;

		if (provinceId == null || provinceId.equals("all")) {
			if (unitId.length() > 0) {
				provinceId = unitId;
			} else {
				provinceId = "all";
			}
		}

		if (projectId == null || projectId.equals("所有")) {
			projectId = "all";
		}
		if (parentId == null || parentId.equals("所有")) {
			parentId = "all";
		}
		subjectId = subjectId == null ? "all" : subjectId;
		tame = tame == null ? "" : tame;

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

		String sqlcount = "select count(*) " + " from TRAINHISTORY t join PE_PROVINCE p on t.fk_province = p.id " + " where 1=1 ";

		String sql = "select t.id,p.name as provinceName ,t.name as traineename,t.child_project,t.training_unit,t.subject,t.fk_applyno,t.telephone,t.email,t.work_place,t.office_phone,t.zhiwu,t.zhicheng,t.dipcode,t.beizhu1,t.status "
				+ " from TRAINHISTORY t join PE_PROVINCE p on t.fk_province = p.id  where 1=1 ";

		if (!provinceId.equals("all")) {
			sql += " and p.id='" + provinceId + "' ";
			sqlcount += " and p.id='" + provinceId + "' ";
		}
		if (!projectId.equals("all")) {
			sql += " and t.child_project like'%" + projectId + "%' ";
			sqlcount += " and t.child_project like'%" + projectId + "%' ";
		}
		if (!parentId.equals("all")) {
			sql += " and t.fk_applyno like'%" + parentId + "%' ";
			sqlcount += " and t.fk_applyno like'%" + parentId + "%' ";
		}
		if (!subjectId.equals("all")) {
			sql += " and t.subject like'%" + subjectId + "%' ";
			sqlcount += " and t.subject like'%" + subjectId + "%' ";
		}
		if (!tame.equals("")) {
			sql += " and t.name like'%" + tame + "%' ";
			sqlcount += " and t.name like'%" + tame + "%' ";
		}

		if (!phone.equals("")) {
			sql += " and t.telephone like'%" + phone + "%' ";
			sqlcount += " and t.telephone like'%" + phone + "%' ";
		}

		if (!dpcode.equals("")) {
			sql += " and t.dipcode like'%" + dpcode + "%' ";
			sqlcount += " and t.dipcode like'%" + dpcode + "%' ";
		}

		if (!unitName.equals("")) {
			sql += " and t.training_unit like'%" + unitName + "%' ";
			sqlcount += " and t.training_unit like'%" + unitName + "%' ";
		}
		if (!status.equals("-1")) {
			sql += " and t.status=" + status + " ";
			sqlcount += " and t.status=" + status + " ";
		}

		sql += " order by t.inputime desc ";

		int totalCount = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
		int tol = totalCount / limit;
		if ((totalCount % limit) > 0) {
			tol += 1;
		}

		session.setAttribute("traineeJHSql", sql);

		List li = peProApplyNoService.getListPage(sql, start, limit);

		String cellString = "";

		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String id = obj[0].toString();
			String provinceName = obj[1] == null ? "" : obj[1].toString();
			String name = obj[2] == null ? "" : obj[2].toString();

			String projectName = obj[3] == null ? "" : obj[3].toString();
			projectName = projectName.replaceAll("\"", "”");

			String tunitName = obj[4] == null ? "" : obj[4].toString();
			String subjectName = obj[5] == null ? "" : obj[5].toString();
			String parentname = obj[6] == null ? "" : obj[6].toString();

			String tel = obj[7] == null ? "" : obj[7].toString();
			tel = tel.replaceAll("\"", "");
			tel = tel.replaceAll("\\\\", "");

			String mail = obj[8] == null ? "" : obj[8].toString();
			mail = mail.replaceAll("\\\\", "");

			String workpalce = obj[9] == null ? "" : obj[9].toString();
			workpalce = workpalce.replaceAll("\\\\", "");

			String officephone = obj[10] == null ? "" : obj[10].toString();
			officephone = officephone.replaceAll("\\\\", "");

			String zhiwu = obj[11] == null ? "" : obj[11].toString();
			zhiwu = zhiwu.replaceAll("\\\\", "");

			String zhicheng = obj[12] == null ? "" : obj[12].toString();
			zhicheng = zhicheng.replaceAll("\\\\", "");

			String dipcode = obj[13] == null ? "" : obj[13].toString();
			dipcode = dipcode.replaceAll("\\\\", "");

			String beizhu = obj[14] == null ? "" : obj[14].toString();
			beizhu = beizhu.replaceAll("\\\\", "");

			String tstatus = obj[15] == null ? "1" : obj[15].toString();
			if (tstatus.equals("0")) {
				tstatus = "未审核";
			} else if (tstatus.equals("1")) {
				tstatus = "通过";
			} else if (tstatus.equals("2")) {
				tstatus = "不通过";
			}

			String t = "{\"id\":\"" + id + "\",\"cell\":[\"" + provinceName + "\",\"" + parentname + "\",\"" + projectName + "\",\"" + tunitName + "\",\"" + subjectName + "\",\"" + name + "\",\"" + tel + "\",\"" + mail + "\",\"" + workpalce + "\",\"" + officephone + "\",\"" + zhiwu + "\",\""
					+ zhicheng + "\",\"" + dipcode + "\",\"" + beizhu + "\",\"" + tstatus + "\"]}";

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

	public void trainHistoryEdit() {

		String tid = request.getParameter("tid");
		String provinceId = request.getParameter("provinceId");
		String parentId = request.getParameter("parentId");
		String projectId = request.getParameter("projectId");
		String unitId = request.getParameter("unitId");
		String subjectId = request.getParameter("subjectId");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String mail = request.getParameter("mail");
		String workplace = request.getParameter("workplace");

		String officephone = request.getParameter("officephone");
		String zhiwu = request.getParameter("zhiwu");
		String zhicheng = request.getParameter("zhicheng");
		String dipcode = request.getParameter("dipcode");
		String beizhu = request.getParameter("beizhu");

		tid = tid == null ? "" : tid;
		provinceId = provinceId == null ? "" : provinceId;
		parentId = parentId == null ? "" : parentId;
		projectId = projectId == null ? "" : projectId;
		unitId = unitId == null ? "" : unitId;
		subjectId = subjectId == null ? "" : subjectId;
		name = name == null ? "" : name;
		phone = phone == null ? "" : phone;
		mail = mail == null ? "" : mail;
		workplace = workplace == null ? "" : workplace;
		dipcode = dipcode == null ? "" : dipcode;

		officephone = officephone == null ? "" : officephone;
		zhiwu = zhiwu == null ? "" : zhiwu;
		zhicheng = zhicheng == null ? "" : zhicheng;
		beizhu = beizhu == null ? "" : beizhu;
//		mail = mail.replaceAll("\\\\", ".");
		mail = replaceAll(mail);
		phone = replaceAll(phone);
		String method = request.getParameter("method");

		if (method.equals("add")) {
			UUID uuid = UUID.randomUUID();
			String vid = uuid.toString().replaceAll("-", "");

			// 插入 pe_trainee
			String sql = "insert into TRAINHISTORY(id,name,WORK_PLACE,telephone,email,office_phone,ZHIWU," + "ZHICHENG,FK_APPLYNO,CHILD_PROJECT,TRAINING_UNIT,SUBJECT," + "DIPCODE,BEIZHU1,FK_PROVINCE) values(";
			sql += "'" + vid + "','" + name + "','" + workplace + "','" + phone + "','" + mail + "','" + officephone + "','" + zhiwu + "','" + zhicheng + "','" + parentId + "','" + projectId + "','" + unitId + "','" + subjectId + "','" + dipcode + "','" + beizhu + "','" + provinceId + "')";

			peProApplyService.executeSQLUpdate(sql);

			String retJson = "{\"id\":\"" + vid + "\"}";
			try {

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write(retJson);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			StringBuilder sb = new StringBuilder();
			sb.append("update TRAINHISTORY set ").append("name='" + name).append("',telephone='" + phone).append("',email='" + mail).append("',SUBJECT='" + subjectId).append("',FK_PROVINCE='" + provinceId).append("',WORK_PLACE='" + workplace).append("',TRAINING_UNIT='" + unitId)
					.append("',FK_APPLYNO='" + parentId).append("',CHILD_PROJECT='" + projectId).append("',office_phone='" + officephone).append("',ZHIWU='" + zhiwu).append("',ZHICHENG='" + zhicheng).append("',DIPCODE='" + dipcode).append("',BEIZHU1='" + beizhu).append("' where id='" + tid + "'");

			peProApplyService.executeSQLUpdate(sb.toString());

			String retJson = "{\"id\":\"" + tid + "\"}";
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

	public void trainHistoryDelete() {
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		for (String t : ids) {
			String sql = "delete from TRAINHISTORY  where id='" + t + "'";
			peProApplyService.executeSQLUpdate(sql);
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

	public void trainHistoryTG() {
		String method = request.getParameter("method");
		String id = request.getParameter("id");

		String[] ids = id.split(",");

		if (method.equals("tg")) {
			for (String t : ids) {
				String sql = "update TRAINHISTORY set status=1 where id='" + t + "'";
				peProApplyService.executeSQLUpdate(sql);
			}
		} else if (method.equals("notg")) {
			for (String t : ids) {
				String sql = "update TRAINHISTORY set status=2 where id='" + t + "'";
				peProApplyService.executeSQLUpdate(sql);
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

	// 上传文件

	public void trainUploadFile() {

		try {
			if(getFileupload() != null){
				String names = getFileuploadFileName();
				if(names != null){
					String types = names.substring(names.lastIndexOf(".")+1);
					if(!"xls".equals(types)){
						try {
							response.setCharacterEncoding("UTF-8");
							response.setHeader("Cache-Control", "no-cache");
							response.setContentType("text/html;charset=utf-8");
							response.getWriter().write("上传文件格式错误，只允许上传Excel(.xls)文件。");
						} catch (Exception e) {
							e.printStackTrace();
						}
						return;
					}
				}
			}
			FileInputStream fs = new FileInputStream(getFileupload());
			HSSFWorkbook wb = new HSSFWorkbook(fs);

			HSSFSheet sheet = wb.getSheetAt(0); // sheet工作目录
			DecimalFormat df = new DecimalFormat("#");

			int rows = sheet.getPhysicalNumberOfRows();// 总条数

			String proviceId = session.getAttribute("provinceId").toString();

			StringBuilder sb = new StringBuilder();
			boolean errorFlag = false;

			for (int r = 1; r < rows; r++) {
				HSSFRow row = sheet.getRow(r);
				if (row == null) {
					continue;
				}
				// 每个学员的信
				String dipCode = "";
				try {
					HSSFCell cell = row.getCell(0);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							dipCode = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							dipCode = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(e);
					System.out.println(r);
				}

				String stuName = "";
				try {
					HSSFCell cell = row.getCell(1);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							stuName = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							stuName = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(r);
					e.printStackTrace();
				}
				
				String graduation = "";
				try {
					HSSFCell cell = row.getCell(18);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							graduation = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							graduation = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(r);
					e.printStackTrace();
				}
				
				String major = "";
				try {
					HSSFCell cell = row.getCell(19);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							major = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							major = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(r);
					e.printStackTrace();
				}
				
//				String hiredate = "";
//				try {
//					HSSFCell cell = row.getCell(20);
//					if (cell != null) {
//						System.out.println(cell.getDateCellValue());
//						SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd");
//						hiredate = formats.format(cell.getDateCellValue());
////						String format = "((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]) ";
////						Pattern pattern = Pattern.compile(format);
////						Matcher matcher = pattern.matcher();
////						matcher.matches();
//					}
//				} catch (Exception e) {
//					System.out.println(r);
//					sb.append("文件第").append(r+1).append("行入职时间格式错误，").append(hiredate).append("<br />");
//					errorFlag = true;
//					e.printStackTrace();
//					continue;
//				}

				String phone = "";
				try {
					HSSFCell cell = row.getCell(13);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							phone = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							phone = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(r);
					e.printStackTrace();
				}
				
				String idcard = "";
				try {
					HSSFCell cell = row.getCell(2);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							idcard = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							idcard = cell.getStringCellValue();
						}
						if(!"".equals(IdCardUtil.IDCardValidate(idcard))){
							sb.append("文件第").append(r+1).append("行数据身份证信息有误，").append(idcard).append("<br />");
							errorFlag = true;
							continue;
						}else{
							idcard = cell.getStringCellValue();
							String sqlcount = "select count(*) from TRAINEE t where 1=1 ";
							sqlcount += " and t.FK_PROVINCE='" + proviceId + "'";
							sqlcount += " and t.FK_APPLYNO='" + subprojectId + "'";

							sqlcount += " and t.FK_TRAINING_UNIT='" + unitId + "'";
							sqlcount += " and t.FK_SUBJECT='" + subjectId + "'";
							sqlcount += " and t.idcard='"+idcard+"'";
							int count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
							if(count >0){
								sb.append("文件第").append(r+1).append("行数据身份证信息重复，").append(idcard).append("<br />");
								errorFlag = true;
								continue;
							}
						}
					}
				} catch (Exception e) {
					System.out.println(r);
					e.printStackTrace();
				}
				
				String province = "";
				try {
					HSSFCell cell = row.getCell(3);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							province = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							province = cell.getStringCellValue();
						}
						String sqlcount = "select count(*) from PE_PROVINCE t where 1=1 ";
						sqlcount += " and t.name='"+province+"'";
						
						int count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
						if(count <= 0){
							sb.append("文件第").append(r+1).append("行数据,所属地区（省）信息错误，").append(province).append("<br />");
							errorFlag = true;
							continue;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String city = "";
				try {
					HSSFCell cell = row.getCell(4);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							city = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							city = cell.getStringCellValue();
						}
						String sqlcount = "select count(*) from CITY t where 1=1 ";
						sqlcount += " and t.fk_province='"+proviceId+"'";
						sqlcount += " and t.name='"+city+"'";
						int count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
						if (count <= 0) {
							sb.append("文件第").append(r + 1).append("行，所属地区（市）信息错误，").append(city).append("<br />");
							errorFlag = true;
							continue;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				String county = "";
				try {
					HSSFCell cell = row.getCell(5);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							county = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							county = cell.getStringCellValue();
						}
						String sqlcount = "select count(*) from COUNTY t left join CITY c on t.fk_city=c.id where 1=1 ";
						sqlcount += " and c.fk_province='"+proviceId+"'";
						sqlcount += " and t.name='"+county+"'";
						int count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
						if (count <= 0) {
							sb.append("文件第").append(r + 1).append("行，所属地区（县）信息错误，").append(county).append("<br />");
							errorFlag = true;
							continue;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String education = "";
				try {
					HSSFCell cell = row.getCell(17);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							education = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							education = cell.getStringCellValue();
						}
						String sqlcount = "select count(*) from EDUCATION t where 1=1 ";
						sqlcount += " and t.name='"+education+"'";
						int count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
						if (count <= 0) {
							sb.append("文件第").append(r + 1).append("行，学历信息错误，").append(education).append("<br />");
							errorFlag = true;
							continue;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String unitAttribute = "";
				try {
					HSSFCell cell = row.getCell(7);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							unitAttribute = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							unitAttribute = cell.getStringCellValue();
						}
						String sqlcount = "select count(*) from UNIT_ATTRIBUTE t where 1=1 ";
						sqlcount += " and t.name='"+unitAttribute+"'";
						int count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
						if (count <= 0) {
							sb.append("文件第").append(r + 1).append("行，学校所在区域信息错误，").append(unitAttribute).append("<br />");
							errorFlag = true;
							continue;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String unitType = "";
				try {
					HSSFCell cell = row.getCell(8);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							unitType = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							unitType = cell.getStringCellValue();
						}
						String sqlcount = "select count(*) from UNIT_TYPE t where 1=1 ";
						sqlcount += " and t.name='"+unitType+"'";
						int count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
						if (count <= 0) {
							sb.append("文件第").append(r + 1).append("行，学校类别信息错误，").append(unitType).append("<br />");
							errorFlag = true;
							continue;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String folk = "";
				try {
					HSSFCell cell = row.getCell(9);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							folk = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							folk = cell.getStringCellValue();
						}
						String sqlcount = "select count(*) from FOLK t where 1=1 ";
						sqlcount += " and t.name='"+folk+"'";
						int count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
						if (count <= 0) {
							sb.append("文件第").append(r + 1).append("行，民族信息错误，").append(folk).append("<br />");
							errorFlag = true;
							continue;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				String jobTitle = "";
				try {
					HSSFCell cell = row.getCell(10);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							jobTitle = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							jobTitle = cell.getStringCellValue();
						}
						String sqlcount = "select count(*) from JOB_TITLE t where 1=1 ";
						sqlcount += " and t.name='"+jobTitle+"'";
						int count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
						if (count <= 0) {
							sb.append("文件第").append(r + 1).append("行，职称信息错误，").append(jobTitle).append("<br />");
							errorFlag = true;
							continue;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				String mainGrade = "";
				try {
					HSSFCell cell = row.getCell(15);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							mainGrade = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							mainGrade = cell.getStringCellValue();
						}
						String sqlcount = "select count(*) from MAIN_TEACHING_GRADE t where 1=1 ";
						sqlcount += " and t.name='"+mainGrade+"'";
						int count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
						if (count <= 0) {
							sb.append("文件第").append(r + 1).append("行，主要教学学段信息错误，").append(mainGrade).append("<br />");
							errorFlag = true;
							continue;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				String mainSubject = "";
				try {
					HSSFCell cell = row.getCell(16);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							mainSubject = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							mainSubject = cell.getStringCellValue();
						}
						String sqlcount = "select count(*) from MAIN_TEACHING_SUBJECT t where 1=1 ";
						sqlcount += " and t.name='"+mainSubject+"'";
						int count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
						if (count <= 0) {
							sb.append("文件第").append(r + 1).append("行，主要教学学科信息错误，").append(mainSubject).append("<br />");
							errorFlag = true;
							continue;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}

				// 插入前先判断有没有错误
				if (dipCode.equals("") || stuName.equals("") || phone.equals("") || idcard.equals("") || graduation.equals("") || major.equals("")
						 || city.equals("") || county.equals("") || jobTitle.equals("") || unitAttribute.equals("") || unitType.equals("") || mainGrade.equals("") || mainSubject.equals("") || education.equals("")) {
					sb.append("文件第").append(r + 1).append("行，数据不完整").append("<br />");
					errorFlag = true;
					continue;
				}

				String sqlcount = "select count(*) from TRAINEE t where 1=1 ";
				sqlcount += " and t.FK_PROVINCE='" + proviceId + "'";
				sqlcount += " and t.FK_APPLYNO='" + subprojectId + "'";

				sqlcount += " and t.FK_TRAINING_UNIT='" + unitId + "'";
				sqlcount += " and t.FK_SUBJECT='" + subjectId + "'";

				sqlcount += " and t.name='" + stuName + "'";
				sqlcount += " and t.telephone='" + phone + "'";
				
				sqlcount += " and t.idcard='"+idcard+"'";

				int count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
				if (count > 0) {
					sb.append("文件第").append(r + 1).append("行，数据重复，").append(stuName).append("<br />");
					errorFlag = true;
					continue;
				}
				
				sqlcount = "select count(*) from TRAINEE t where 1=1 and t.DIPCODE='" + dipCode + "'";
				count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
				if (count > 0) {
					sb.append("文件第").append(r + 1).append("行，证书编号重复，").append(stuName).append("  ").append(dipCode).append("<br />");
					errorFlag = true;
					continue;
				}
			}

			if (errorFlag) {
				try {
					response.setCharacterEncoding("UTF-8");
					response.setHeader("Cache-Control", "no-cache");
					response.setContentType("text/html;charset=utf-8");
					response.getWriter().write(sb.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
			}

			for (int r = 1; r < rows; r++) {
				HSSFRow row = sheet.getRow(r);
				if (row == null) {
					continue;
				}
				// 每个学员的信息

				String dipCode = "";
				try {
					HSSFCell cell = row.getCell(0);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							dipCode = df.format(cell.getNumericCellValue()).trim();
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							dipCode = cell.getStringCellValue().trim();;
						}
					}
				} catch (Exception e) {
					System.out.println(r);
					e.printStackTrace();
				}

				String stuName = "";
				try {
					HSSFCell cell = row.getCell(1);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							stuName = df.format(cell.getNumericCellValue()).trim();;
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							stuName = cell.getStringCellValue().trim();;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String idcard = "";
				try {
					HSSFCell cell = row.getCell(2);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							idcard = df.format(cell.getNumericCellValue()).trim();;
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							idcard = cell.getStringCellValue().trim();;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String province = "";
				try {
					HSSFCell cell = row.getCell(3);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							province = df.format(cell.getNumericCellValue()).trim();;
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							province = cell.getStringCellValue().trim();;
						}
						String sqlcount = "select t.id from PE_PROVINCE t where 1=1 ";
						sqlcount += " and t.name='"+province+"'";
						
						String count =peProApplyNoService.executeSQL(sqlcount).get(0).toString();
						province = count;
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String city = "";
				try {
					HSSFCell cell = row.getCell(4);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							city = df.format(cell.getNumericCellValue()).trim();;
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							city = cell.getStringCellValue().trim();;
						}
						String sqlcount = "select t.id from CITY t where 1=1 ";
						sqlcount += " and t.name='"+city+"'";
						
						String count =peProApplyNoService.executeSQL(sqlcount).get(0).toString();
						city = count;
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String county = "";
				try {
					HSSFCell cell = row.getCell(5);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							county = df.format(cell.getNumericCellValue()).trim();;
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							county = cell.getStringCellValue().trim();;
						}
						String sqlcount = "select t.id from COUNTY t where 1=1 ";
						sqlcount += " and t.name='"+county+"'";
						
						String count =peProApplyNoService.executeSQL(sqlcount).get(0).toString();
						county = count;
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String workPlace = "";

				try {
					HSSFCell cell = row.getCell(6);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							workPlace = df.format(cell.getNumericCellValue()).trim();;
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							workPlace = cell.getStringCellValue().trim();;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String unitAttribute = "";
				try {
					HSSFCell cell = row.getCell(7);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							unitAttribute = df.format(cell.getNumericCellValue()).trim();;
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							unitAttribute = cell.getStringCellValue().trim();;
						}
						String sqlcount = "select t.id from UNIT_ATTRIBUTE t where 1=1 ";
						sqlcount += " and t.name='"+unitAttribute+"'";
						
						String count =peProApplyNoService.executeSQL(sqlcount).get(0).toString();
						unitAttribute = count;
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String unitType = "";
				try {
					HSSFCell cell = row.getCell(8);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							unitType = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							unitType = cell.getStringCellValue();
						}
						String sqlcount = "select t.id from UNIT_TYPE t where 1=1 ";
						sqlcount += " and t.name='"+unitType+"'";

						String count =peProApplyNoService.executeSQL(sqlcount).get(0).toString();
						unitType = count;
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String folk = "";
				try {
					HSSFCell cell = row.getCell(9);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							folk = df.format(cell.getNumericCellValue()).trim();;
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							folk = cell.getStringCellValue().trim();;
						}
						String sqlcount = "select t.id from FOLK t where 1=1 ";
						sqlcount += " and t.name='"+folk+"'";
						
						String count =peProApplyNoService.executeSQL(sqlcount).get(0).toString();
						folk = count;
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String zhicheng = "";

				try {
					HSSFCell cell = row.getCell(10);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							zhicheng = df.format(cell.getNumericCellValue()).trim();;
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							zhicheng = cell.getStringCellValue().trim();;
						}
						String sqlcount = "select t.id from JOB_TITLE t where 1=1 ";
						sqlcount += " and t.name='"+zhicheng+"'";
						
						String count =peProApplyNoService.executeSQL(sqlcount).get(0).toString();
						zhicheng = count;
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String zhiwu = "";

				try {
					HSSFCell cell = row.getCell(11);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							zhiwu = df.format(cell.getNumericCellValue()).trim();;
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							zhiwu = cell.getStringCellValue().trim();;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}

				String dianhua = "";
				try {
					HSSFCell cell = row.getCell(12);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							dianhua = df.format(cell.getNumericCellValue()).trim();;
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							dianhua = cell.getStringCellValue().trim();;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				String phone = "";
				try {
					HSSFCell cell = row.getCell(13);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							phone = df.format(cell.getNumericCellValue()).trim();;
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							phone = cell.getStringCellValue().trim();;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				phone = replaceAll(phone);
				
				String mail = "";

				try {
					HSSFCell cell = row.getCell(14);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							mail = df.format(cell.getNumericCellValue()).trim();;
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							mail = cell.getStringCellValue().trim();;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
//				mail = mail.replaceAll("\\\\", ".");
				mail = replaceAll(mail);
				String mainGrade = "";

				try {
					HSSFCell cell = row.getCell(15);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							mainGrade = df.format(cell.getNumericCellValue()).trim();;
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							mainGrade = cell.getStringCellValue().trim();;
						}
						String sqlcount = "select t.id from MAIN_TEACHING_GRADE t where 1=1 ";
						sqlcount += " and t.name='"+mainGrade+"'";
						
						String count =peProApplyNoService.executeSQL(sqlcount).get(0).toString();
						mainGrade = count;
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String mainSubject = "";

				try {
					HSSFCell cell = row.getCell(16);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							mainSubject = df.format(cell.getNumericCellValue()).trim();;
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							mainSubject = cell.getStringCellValue().trim();;
						}
						String sqlcount = "select t.id from MAIN_TEACHING_SUBJECT t where 1=1 ";
						sqlcount += " and t.name='"+mainSubject+"'";
						
						String count =peProApplyNoService.executeSQL(sqlcount).get(0).toString();
						mainSubject = count;
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String education = "";

				try {
					HSSFCell cell = row.getCell(17);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							education = df.format(cell.getNumericCellValue()).trim();;
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							education = cell.getStringCellValue().trim();;
						}
						String sqlcount = "select t.id from EDUCATION t where 1=1 ";
						sqlcount += " and t.name='"+education+"'";
						
						String count =peProApplyNoService.executeSQL(sqlcount).get(0).toString();
						education = count;
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String graduation = "";
				try {
					HSSFCell cell = row.getCell(18);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							graduation = df.format(cell.getNumericCellValue()).trim();;
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							graduation = cell.getStringCellValue().trim();;
						}
					}
				} catch (Exception e) {
					System.out.println(r);
					e.printStackTrace();
				}
//				graduation = graduation.replaceAll("\\\\", "");
				graduation = replaceAll(graduation);
				String major = "";
				try {
					HSSFCell cell = row.getCell(19);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							major = df.format(cell.getNumericCellValue()).trim();;
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							major = cell.getStringCellValue().trim();;
						}
					}
				} catch (Exception e) {
					System.out.println(r);
					e.printStackTrace();
				}
//				major = major.replaceAll("\\\\", "");
				major = replaceAll(major);
//				String hiredate = "";
//				try {
//					HSSFCell cell = row.getCell(19);
//					if (cell != null) {
//						SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd");
//						hiredate = formats.format(cell.getDateCellValue()).trim();;
//						System.out.println(hiredate);
////						String format = "((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]) ";
////						Pattern pattern = Pattern.compile(format);
////						Matcher matcher = pattern.matcher();
////						matcher.matches();
//					}
//				} catch (Exception e) {
//					System.out.println(r);
//					e.printStackTrace();
//				}
				
				String beizhu1 = "";
				try {
					HSSFCell cell = row.getCell(20);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							beizhu1 = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							beizhu1 = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
//				beizhu1 = beizhu1.replaceAll("\\\\", "");
				beizhu1 = replaceAll(beizhu1);
				
				UUID uuid = UUID.randomUUID();
				String vid = uuid.toString().replaceAll("-", "");

				String sql = "insert into TRAINEE(id,name,fk_unit_from,telephone,email,office_phone," + "FK_SUBJECT,FK_PROVINCE,WORK_PLACE,ZHIWU,FK_TRAINING_UNIT," + "FK_APPLYNO,ZHICHENG,DIPCODE,beizhu1,status,idcard,FK_CITY,FK_COUNTY,FK_UNITATTRIBUTE,FK_FOLK,"
						+ "FK_JOBTITLE,FK_MAIN_TEACHING_GRADE,FK_MAIN_TEACHING_SUBJECT,FK_EDUCATION,GRADUATION,MAJOR,FK_UNIT_TYPE) values(";
				sql += "'" + vid + "','" + stuName + "','402880142a103b95012a103edcdd0001','" + phone + "','" + mail + "','" + dianhua + "','" + subjectId + "','" + proviceId + "','" + workPlace + "','" + zhiwu + "','" + unitId + "','" + subprojectId + "','" + "" + "','" + dipCode + "','" + beizhu1 + "',1,"
						+ "'"+idcard+"','"+city+"','"+county+"','"+unitAttribute+"','"+folk+"','"+zhicheng+"','"+mainGrade+"','"+mainSubject+"','"+education+"','"+graduation+"','"+major+"'"
								+ ",'"+unitType+"')";

				peProApplyService.executeSQLUpdate(sql);

			}
			try {
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().write("插入成功");

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().write("插入失败。文件类型错误，请选择模版文件上传。");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
	}
	
	public void trainUploadFileOld() {

		try {

			FileInputStream fs = new FileInputStream(getFileupload());
			HSSFWorkbook wb = new HSSFWorkbook(fs);

			HSSFSheet sheet = wb.getSheetAt(0); // sheet工作目录
			DecimalFormat df = new DecimalFormat("#");

			int rows = sheet.getPhysicalNumberOfRows();// 总条数

			String proviceId = session.getAttribute("provinceId").toString();

			StringBuilder sb = new StringBuilder();
			boolean errorFlag = false;

			for (int r = 1; r < rows; r++) {
				HSSFRow row = sheet.getRow(r);
				if (row == null) {
					continue;
				}
				// 每个学员的信息

				String dipCode = "";
				try {
					HSSFCell cell = row.getCell(0);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							dipCode = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							dipCode = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(e);
					System.out.println(r);
				}

				String stuName = "";
				try {
					HSSFCell cell = row.getCell(1);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							stuName = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							stuName = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(e);
					System.out.println(r);
				}

				String phone = "";
				try {
					HSSFCell cell = row.getCell(6);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							phone = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							phone = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(e);
					System.out.println(r);
				}

				// 插入前先判断有没有错误
				if (stuName.equals("") || phone.equals("") || dipCode.equals("")) {
					sb.append("文件第").append(r + 1).append("行，数据不完整").append("<br />");
					errorFlag = true;
					continue;
				}

				String sqlcount = "select count(*) from TRAINEE t where 1=1 ";
				sqlcount += " and t.FK_PROVINCE='" + proviceId + "'";
				sqlcount += " and t.FK_APPLYNO='" + subprojectId + "'";

				sqlcount += " and t.FK_TRAINING_UNIT='" + unitId + "'";
				sqlcount += " and t.FK_SUBJECT='" + subjectId + "'";

				sqlcount += " and t.name='" + stuName + "'";
				sqlcount += " and t.telephone='" + phone + "'";

				int count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
				if (count > 0) {
					sb.append("文件第").append(r + 1).append("行，数据重复，").append(stuName).append("<br />");
					errorFlag = true;
					continue;
				}

				sqlcount = "select count(*) from TRAINEE t where 1=1 and t.DIPCODE='" + dipCode + "'";
				count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
				if (count > 0) {
					sb.append("文件第").append(r + 1).append("行，证书编号重复，").append(stuName).append("  ").append(dipCode).append("<br />");
					errorFlag = true;
					continue;
				}

			}

			if (errorFlag) {
				try {
					response.setCharacterEncoding("UTF-8");
					response.setHeader("Cache-Control", "no-cache");
					response.setContentType("text/html;charset=utf-8");
					response.getWriter().write(sb.toString());
				} catch (Exception e) {
					System.out.println(e);
				}
				return;
			}

			for (int r = 1; r < rows; r++) {
				HSSFRow row = sheet.getRow(r);
				if (row == null) {
					continue;
				}
				// 每个学员的信息

				String dipCode = "";
				try {
					HSSFCell cell = row.getCell(0);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							dipCode = df.format(cell.getNumericCellValue()).trim();
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							dipCode = cell.getStringCellValue().trim();
						}
					}
				} catch (Exception e) {
					System.out.println(e);
					System.out.println(r);
				}

				String stuName = "";
				try {
					HSSFCell cell = row.getCell(1);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							stuName = df.format(cell.getNumericCellValue()).trim();
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							stuName = cell.getStringCellValue().trim();
						}
					}
				} catch (Exception e) {
					System.out.println(e);
					System.out.println(r);
				}
				String workPlace = "";

				try {
					HSSFCell cell = row.getCell(2);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							workPlace = df.format(cell.getNumericCellValue()).trim();
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							workPlace = cell.getStringCellValue().trim();
						}
					}
				} catch (Exception e) {
					System.out.println(e);
					System.out.println(r);
				}
				String zhiwu = "";

				try {
					HSSFCell cell = row.getCell(3);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							zhiwu = df.format(cell.getNumericCellValue()).trim();
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							zhiwu = cell.getStringCellValue().trim();
						}
					}
				} catch (Exception e) {
					System.out.println(e);
					System.out.println(r);
				}
				String zhicheng = "";

				try {
					HSSFCell cell = row.getCell(4);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							zhicheng = df.format(cell.getNumericCellValue()).trim();
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							zhicheng = cell.getStringCellValue().trim();
						}
					}
				} catch (Exception e) {
					System.out.println(e);
					System.out.println(r);
				}

				String dianhua = "";
				try {
					HSSFCell cell = row.getCell(5);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							dianhua = df.format(cell.getNumericCellValue()).trim();
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							dianhua = cell.getStringCellValue().trim();
						}
					}
				} catch (Exception e) {
					System.out.println(e);
					System.out.println(r);
				}
				String phone = "";
				try {
					HSSFCell cell = row.getCell(6);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							phone = df.format(cell.getNumericCellValue()).trim();
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							phone = cell.getStringCellValue().trim();
						}
					}
				} catch (Exception e) {
					System.out.println(e);
					System.out.println(r);
				}
				String mail = "";

				try {
					HSSFCell cell = row.getCell(7);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							mail = df.format(cell.getNumericCellValue()).trim();
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							mail = cell.getStringCellValue().trim();
						}
					}
				} catch (Exception e) {
					System.out.println(e);
					System.out.println(r);
				}
//				mail = mail.replaceAll("\\\\", ".");
				mail = replaceAll(mail);
				phone = replaceAll(phone);
				String beizhu1 = "";
				try {
					HSSFCell cell = row.getCell(8);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							beizhu1 = df.format(cell.getNumericCellValue()).trim();
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							beizhu1 = cell.getStringCellValue().trim();
						}
					}
				} catch (Exception e) {
					System.out.println(e);
					System.out.println(r);
				}

				//
				UUID uuid = UUID.randomUUID();
				String vid = uuid.toString().replaceAll("-", "");

				// 插入 pe_trainee
				String sql = "insert into TRAINEE(id,name,fk_unit_from,telephone,email,office_phone,FK_GRADUTED," + "FK_SUBJECT,FK_PROVINCE,WORK_PLACE,ZHIWU,FK_TRAINING_UNIT," + "FK_APPLYNO,ZHICHENG,DIPCODE,beizhu1,status) values(";
				sql += "'" + vid + "','" + stuName + "','402880142a103b95012a103edcdd0001','" + phone + "','" + mail + "','" + dianhua + "','isgraduateidflag2','" + subjectId + "','" + proviceId + "','" + workPlace + "','" + zhiwu + "','" + unitId + "','" + subprojectId + "','" + zhicheng + "','"
						+ dipCode + "','" + beizhu1 + "',1)";

				peProApplyService.executeSQLUpdate(sql);

			}
			try {
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().write("插入成功");

			} catch (Exception e) {
				System.out.println(e);
			}

		} catch (Exception ex) {
			System.out.println(ex);
		}
	}


	public void trainJHUploadFile() {

		try {
			if(getFileupload() != null){
				String names = getFileuploadFileName();
				if(names != null){
					String types = names.substring(names.lastIndexOf(".")+1);
					if(!"xls".equals(types)){
						try {
							response.setCharacterEncoding("UTF-8");
							response.setHeader("Cache-Control", "no-cache");
							response.setContentType("text/html;charset=utf-8");
							response.getWriter().write("上传文件格式错误，只允许上传Excel(.xls)文件。");
						} catch (Exception e) {
							e.printStackTrace();
						}
						return;
					}
				}
			}
			
			FileInputStream fs = new FileInputStream(getFileupload());
			HSSFWorkbook wb = new HSSFWorkbook(fs);

			HSSFSheet sheet = wb.getSheetAt(0); // sheet工作目录
			DecimalFormat df = new DecimalFormat("#");

			int rows = sheet.getPhysicalNumberOfRows();// 总条数

			String proviceId = session.getAttribute("provinceId").toString();

			StringBuilder sb = new StringBuilder();
			boolean errorFlag = false;

			for (int r = 1; r < rows; r++) {
				HSSFRow row = sheet.getRow(r);
				if (row == null) {
					continue;
				}
				// 每个学员的信
				String stuName = "";
				try {
					HSSFCell cell = row.getCell(1);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							stuName = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							stuName = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(r);
					e.printStackTrace();
				}
				
				String graduation = "";
				try {
					HSSFCell cell = row.getCell(18);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							graduation = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							graduation = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(r);
					e.printStackTrace();
				}
				
				String major = "";
				try {
					HSSFCell cell = row.getCell(19);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							major = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							major = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(r);
					e.printStackTrace();
				}
				
//				String hiredate = "";
//				try {
//					HSSFCell cell = row.getCell(19);
//					if (cell != null) {
//						System.out.println(cell.getDateCellValue());
//						SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd");
//						hiredate = formats.format(cell.getDateCellValue());
////						String format = "((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]) ";
////						Pattern pattern = Pattern.compile(format);
////						Matcher matcher = pattern.matcher();
////						matcher.matches();
//					}
//				} catch (Exception e) {
//					System.out.println(r);
//					sb.append("文件第").append(r+1).append("行入职时间格式错误，").append(hiredate).append("<br />");
//					errorFlag = true;
//					e.printStackTrace();
//					continue;
//				}

				String phone = "";
				try {
					HSSFCell cell = row.getCell(13);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							phone = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							phone = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(r);
					e.printStackTrace();
				}
				
				String idcard = "";
				try {
					HSSFCell cell = row.getCell(2);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							idcard = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							idcard = cell.getStringCellValue();
						}
						if(!"".equals(IdCardUtil.IDCardValidate(idcard))){
							sb.append("文件第").append(r+1).append("行数据身份证信息有误，").append(idcard).append("<br />");
							errorFlag = true;
							continue;
						}else{
							idcard = cell.getStringCellValue();
							String sqlcount = "select count(*) from JHTRAINEE t where 1=1 ";
							sqlcount += " and t.FK_PROVINCE='" + proviceId + "'";
							sqlcount += " and t.FK_APPLYNO='" + subprojectId + "'";

							sqlcount += " and t.FK_TRAINING_UNIT='" + unitId + "'";
							sqlcount += " and t.FK_SUBJECT='" + subjectId + "'";
							sqlcount += " and t.idcard='"+idcard+"'";
							int count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
							if(count >0){
								sb.append("文件第").append(r+1).append("行数据身份证信息重复，").append(idcard).append("<br />");
								errorFlag = true;
								continue;
							}
						}
					}
				} catch (Exception e) {
					System.out.println(r);
					e.printStackTrace();
				}
				
				String province = "";
				try {
					HSSFCell cell = row.getCell(3);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							province = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							province = cell.getStringCellValue();
						}
						String sqlcount = "select count(*) from PE_PROVINCE t where 1=1 ";
						sqlcount += " and t.name='"+province+"'";
						
						int count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
						if(count <= 0){
							sb.append("文件第").append(r+1).append("行数据,所属地区（省）信息错误，").append(province).append("<br />");
							errorFlag = true;
							continue;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String city = "";
				try {
					HSSFCell cell = row.getCell(4);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							city = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							city = cell.getStringCellValue();
						}
						String sqlcount = "select count(*) from CITY t where 1=1 ";
						sqlcount += " and t.fk_province='"+proviceId+"'";
						sqlcount += " and t.name='"+city+"'";
						int count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
						if (count <= 0) {
							sb.append("文件第").append(r + 1).append("行，所属地区（市）信息错误，").append(city).append("<br />");
							errorFlag = true;
							continue;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				String county = "";
				try {
					HSSFCell cell = row.getCell(5);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							county = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							county = cell.getStringCellValue();
						}
						String sqlcount = "select count(*) from COUNTY t left join CITY c on t.fk_city=c.id where 1=1 ";
						sqlcount += " and c.fk_province='"+proviceId+"'";
						sqlcount += " and t.name='"+county+"'";
						int count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
						if (count <= 0) {
							sb.append("文件第").append(r + 1).append("行，所属地区（县）信息错误，").append(county).append("<br />");
							errorFlag = true;
							continue;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String education = "";
				try {
					HSSFCell cell = row.getCell(17);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							education = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							education = cell.getStringCellValue();
						}
						String sqlcount = "select count(*) from EDUCATION t where 1=1 ";
						sqlcount += " and t.name='"+education+"'";
						int count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
						if (count <= 0) {
							sb.append("文件第").append(r + 1).append("行，学历信息错误，").append(education).append("<br />");
							errorFlag = true;
							continue;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String unitAttribute = "";
				try {
					HSSFCell cell = row.getCell(7);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							unitAttribute = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							unitAttribute = cell.getStringCellValue();
						}
						String sqlcount = "select count(*) from UNIT_ATTRIBUTE t where 1=1 ";
						sqlcount += " and t.name='"+unitAttribute+"'";
						int count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
						if (count <= 0) {
							sb.append("文件第").append(r + 1).append("行，学校所在区域信息错误，").append(unitAttribute).append("<br />");
							errorFlag = true;
							continue;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String unitType = "";
				try {
					HSSFCell cell = row.getCell(8);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							unitType = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							unitType = cell.getStringCellValue();
						}
						String sqlcount = "select count(*) from UNIT_TYPE t where 1=1 ";
						sqlcount += " and t.name='"+unitType+"'";
						int count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
						if (count <= 0) {
							sb.append("文件第").append(r + 1).append("行，学校类别信息错误，").append(unitType).append("<br />");
							errorFlag = true;
							continue;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String folk = "";
				try {
					HSSFCell cell = row.getCell(9);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							folk = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							folk = cell.getStringCellValue();
						}
						String sqlcount = "select count(*) from FOLK t where 1=1 ";
						sqlcount += " and t.name='"+folk+"'";
						int count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
						if (count <= 0) {
							sb.append("文件第").append(r + 1).append("行，民族信息错误，").append(folk).append("<br />");
							errorFlag = true;
							continue;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				String jobTitle = "";
				try {
					HSSFCell cell = row.getCell(10);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							jobTitle = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							jobTitle = cell.getStringCellValue();
						}
						String sqlcount = "select count(*) from JOB_TITLE t where 1=1 ";
						sqlcount += " and t.name='"+jobTitle+"'";
						int count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
						if (count <= 0) {
							sb.append("文件第").append(r + 1).append("行，职称信息错误，").append(jobTitle).append("<br />");
							errorFlag = true;
							continue;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				String mainGrade = "";
				try {
					HSSFCell cell = row.getCell(15);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							mainGrade = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							mainGrade = cell.getStringCellValue();
						}
						String sqlcount = "select count(*) from MAIN_TEACHING_GRADE t where 1=1 ";
						sqlcount += " and t.name='"+mainGrade+"'";
						int count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
						if (count <= 0) {
							sb.append("文件第").append(r + 1).append("行，主要教学学段信息错误，").append(mainGrade).append("<br />");
							errorFlag = true;
							continue;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				String mainSubject = "";
				try {
					HSSFCell cell = row.getCell(16);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							mainSubject = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							mainSubject = cell.getStringCellValue();
						}
						String sqlcount = "select count(*) from MAIN_TEACHING_SUBJECT t where 1=1 ";
						sqlcount += " and t.name='"+mainSubject+"'";
						int count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
						if (count <= 0) {
							sb.append("文件第").append(r + 1).append("行，主要教学学科信息错误，").append(mainSubject).append("<br />");
							errorFlag = true;
							continue;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}

				// 插入前先判断有没有错误
				if (stuName.equals("") || phone.equals("") || idcard.equals("") || graduation.equals("") || major.equals("") || unitType.equals("")
						 || city.equals("") || county.equals("") || jobTitle.equals("") || unitAttribute.equals("") || mainGrade.equals("") || mainSubject.equals("") || education.equals("")) {
					sb.append("文件第").append(r + 1).append("行，数据不完整").append("<br />");
					errorFlag = true;
					continue;
				}

				String sqlcount = "select count(*) from JHTRAINEE t where 1=1 ";
				sqlcount += " and t.FK_PROVINCE='" + proviceId + "'";
				sqlcount += " and t.FK_APPLYNO='" + subprojectId + "'";

				sqlcount += " and t.FK_TRAINING_UNIT='" + unitId + "'";
				sqlcount += " and t.FK_SUBJECT='" + subjectId + "'";

				sqlcount += " and t.name='" + stuName + "'";
				sqlcount += " and t.telephone='" + phone + "'";
				
				sqlcount += " and t.idcard='"+idcard+"'";

				int count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
				if (count > 0) {
					sb.append("文件第").append(r + 1).append("行，数据重复，").append(stuName).append("<br />");
					errorFlag = true;
					continue;
				}
			}

			if (errorFlag) {
				try {
					response.setCharacterEncoding("UTF-8");
					response.setHeader("Cache-Control", "no-cache");
					response.setContentType("text/html;charset=utf-8");
					response.getWriter().write(sb.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
			}

			for (int r = 1; r < rows; r++) {
				HSSFRow row = sheet.getRow(r);
				if (row == null) {
					continue;
				}
				// 每个学员的信息

				String dipCode = "";
				try {
					HSSFCell cell = row.getCell(0);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							dipCode = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							dipCode = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(r);
					e.printStackTrace();
				}

				String stuName = "";
				try {
					HSSFCell cell = row.getCell(1);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							stuName = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							stuName = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String idcard = "";
				try {
					HSSFCell cell = row.getCell(2);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							idcard = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							idcard = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String province = "";
				try {
					HSSFCell cell = row.getCell(3);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							province = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							province = cell.getStringCellValue();
						}
						String sqlcount = "select t.id from PE_PROVINCE t where 1=1 ";
						sqlcount += " and t.name='"+province+"'";
						
						String count =peProApplyNoService.executeSQL(sqlcount).get(0).toString();
						province = count;
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String city = "";
				try {
					HSSFCell cell = row.getCell(4);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							city = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							city = cell.getStringCellValue();
						}
						String sqlcount = "select t.id from CITY t where 1=1 ";
						sqlcount += " and t.name='"+city+"'";
						
						String count =peProApplyNoService.executeSQL(sqlcount).get(0).toString();
						city = count;
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String county = "";
				try {
					HSSFCell cell = row.getCell(5);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							county = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							county = cell.getStringCellValue();
						}
						String sqlcount = "select t.id from COUNTY t where 1=1 ";
						sqlcount += " and t.name='"+county+"'";
						
						String count =peProApplyNoService.executeSQL(sqlcount).get(0).toString();
						county = count;
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String workPlace = "";

				try {
					HSSFCell cell = row.getCell(6);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							workPlace = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							workPlace = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String unitAttribute = "";
				try {
					HSSFCell cell = row.getCell(7);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							unitAttribute = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							unitAttribute = cell.getStringCellValue();
						}
						String sqlcount = "select t.id from UNIT_ATTRIBUTE t where 1=1 ";
						sqlcount += " and t.name='"+unitAttribute+"'";
						
						String count =peProApplyNoService.executeSQL(sqlcount).get(0).toString();
						unitAttribute = count;
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String unitType = "";
				try {
					HSSFCell cell = row.getCell(8);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							unitType = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							unitType = cell.getStringCellValue();
						}
						String sqlcount = "select t.id from UNIT_TYPE t where 1=1 ";
						sqlcount += " and t.name='"+unitType+"'";
						
						String count =peProApplyNoService.executeSQL(sqlcount).get(0).toString();
						unitType = count;
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String folk = "";
				try {
					HSSFCell cell = row.getCell(9);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							folk = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							folk = cell.getStringCellValue();
						}
						String sqlcount = "select t.id from FOLK t where 1=1 ";
						sqlcount += " and t.name='"+folk+"'";
						
						String count =peProApplyNoService.executeSQL(sqlcount).get(0).toString();
						folk = count;
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String zhicheng = "";

				try {
					HSSFCell cell = row.getCell(10);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							zhicheng = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							zhicheng = cell.getStringCellValue();
						}
						String sqlcount = "select t.id from JOB_TITLE t where 1=1 ";
						sqlcount += " and t.name='"+zhicheng+"'";
						
						String count =peProApplyNoService.executeSQL(sqlcount).get(0).toString();
						zhicheng = count;
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String zhiwu = "";

				try {
					HSSFCell cell = row.getCell(11);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							zhiwu = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							zhiwu = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}

				String dianhua = "";
				try {
					HSSFCell cell = row.getCell(12);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							dianhua = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							dianhua = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				String phone = "";
				try {
					HSSFCell cell = row.getCell(13);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							phone = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							phone = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				String mail = "";

				try {
					HSSFCell cell = row.getCell(14);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							mail = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							mail = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
//				mail = mail.replaceAll("\\\\", ".");
				mail = replaceAll(mail);
				phone = replaceAll(phone);
				String mainGrade = "";

				try {
					HSSFCell cell = row.getCell(15);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							mainGrade = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							mainGrade = cell.getStringCellValue();
						}
						String sqlcount = "select t.id from MAIN_TEACHING_GRADE t where 1=1 ";
						sqlcount += " and t.name='"+mainGrade+"'";
						
						String count =peProApplyNoService.executeSQL(sqlcount).get(0).toString();
						mainGrade = count;
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String mainSubject = "";

				try {
					HSSFCell cell = row.getCell(16);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							mainSubject = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							mainSubject = cell.getStringCellValue();
						}
						String sqlcount = "select t.id from MAIN_TEACHING_SUBJECT t where 1=1 ";
						sqlcount += " and t.name='"+mainSubject+"'";
						
						String count =peProApplyNoService.executeSQL(sqlcount).get(0).toString();
						mainSubject = count;
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String education = "";

				try {
					HSSFCell cell = row.getCell(17);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							education = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							education = cell.getStringCellValue();
						}
						String sqlcount = "select t.id from EDUCATION t where 1=1 ";
						sqlcount += " and t.name='"+education+"'";
						
						String count =peProApplyNoService.executeSQL(sqlcount).get(0).toString();
						education = count;
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
				
				String graduation = "";
				try {
					HSSFCell cell = row.getCell(18);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							graduation = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							graduation = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(r);
					e.printStackTrace();
				}
//				graduation = graduation.replaceAll("\\\\", "");
				graduation = replaceAll(graduation);
				String major = "";
				try {
					HSSFCell cell = row.getCell(19);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							major = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							major = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(r);
					e.printStackTrace();
				}
				major = major.replaceAll("\\\\", "");
				major = replaceAll(major);
//				String hiredate = "";
//				try {
//					HSSFCell cell = row.getCell(19);
//					if (cell != null) {
//						SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd");
//						hiredate = formats.format(cell.getDateCellValue());
////						String format = "((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]) ";
////						Pattern pattern = Pattern.compile(format);
////						Matcher matcher = pattern.matcher();
////						matcher.matches();
//					}
//				} catch (Exception e) {
//					System.out.println(r);
//					e.printStackTrace();
//				}
				
				String beizhu1 = "";
				try {
					HSSFCell cell = row.getCell(20);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							beizhu1 = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							beizhu1 = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(r);
				}
//				beizhu1 = beizhu1.replaceAll("\\\\", "");
				beizhu1 = replaceAll(beizhu1);

				UUID uuid = UUID.randomUUID();
				String vid = uuid.toString().replaceAll("-", "");

				String sql = "insert into JHTRAINEE(id,name,fk_unit_from,telephone,email,office_phone," + "FK_SUBJECT,FK_PROVINCE,WORK_PLACE,ZHIWU,FK_TRAINING_UNIT," + "FK_APPLYNO,ZHICHENG,beizhu1,status,idcard,FK_CITY,FK_COUNTY,FK_UNITATTRIBUTE,FK_FOLK,"
						+ "FK_JOBTITLE,FK_MAIN_TEACHING_GRADE,FK_MAIN_TEACHING_SUBJECT,FK_EDUCATION,GRADUATION,MAJOR,FK_UNIT_TYPE) values(";
				sql += "'" + vid + "','" + stuName + "','402880142a103b95012a103edcdd0001','" + phone + "','" + mail + "','" + dianhua + "','" + subjectId + "','" + proviceId + "','" + workPlace + "','" + zhiwu + "','" + unitId + "','" + subprojectId + "','" + "" + "','" + beizhu1 + "',1,"
						+ "'"+idcard+"','"+city+"','"+county+"','"+unitAttribute+"','"+folk+"','"+zhicheng+"','"+mainGrade+"','"+mainSubject+"','"+education+"','"+graduation+"','"+major+"'"
								+ ",'"+unitType+"')";

				peProApplyService.executeSQLUpdate(sql);

			}
			try {
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().write("插入成功");

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().write("插入失败。文件类型错误，请选择模版文件上传。");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
	}

	public void historyUploadFile() {

		try {

			StringBuilder sb = new StringBuilder();

			FileInputStream fs = new FileInputStream(getFileupload());
			HSSFWorkbook wb = new HSSFWorkbook(fs);

			HSSFSheet sheet = wb.getSheetAt(0); // sheet工作目录
			DecimalFormat df = new DecimalFormat("#");

			int rows = sheet.getPhysicalNumberOfRows();// 总条数

			String proviceId = session.getAttribute("provinceId").toString();

			boolean errorFlag = false;

			for (int r = 1; r < rows; r++) {
				HSSFRow row = sheet.getRow(r);
				if (row == null) {
					continue;
				}
				// 每个学员的信息

				String childProject = "";
				try {
					HSSFCell cell = row.getCell(0);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							childProject = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							childProject = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(e);
					System.out.println("row:" + r + "; cell:" + row.getCell(0));
				}

				String trainingUnit = "";
				try {
					HSSFCell cell = row.getCell(1);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							trainingUnit = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							trainingUnit = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(e);
					System.out.println("row:" + r + "; cell:" + row.getCell(1));
				}

				String subject = "";
				try {
					HSSFCell cell = row.getCell(2);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							subject = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							subject = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(e);
					System.out.println("row:" + r + "; cell:" + row.getCell(2));
				}
				String dipcode = "";

				try {
					HSSFCell cell = row.getCell(3);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							dipcode = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							dipcode = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(e);
					System.out.println("row:" + r + "; cell:" + row.getCell(3));
				}
				String name = "";

				try {
					HSSFCell cell = row.getCell(4);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							name = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							name = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(e);
					System.out.println("row:" + r + "; cell:" + row.getCell(4));
				}

				String phone = "";
				try {
					HSSFCell cell = row.getCell(6);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							phone = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							phone = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(e);
					System.out.println("row:" + r + "; cell:" + row.getCell(6));
				}

				// 插入前先判断有没有错误
				if (name.equals("") || phone.equals("") || childProject.equals("") || trainingUnit.equals("") || subject.equals("") || dipcode.equals("")) {
					sb.append("文件第").append(r + 1).append("行，数据不完整").append("<br />");
					errorFlag = true;
					continue;
				}

				String sqlcount = "select count(*) from TRAINHISTORY t where 1=1 ";
				sqlcount += " and t.FK_PROVINCE='" + proviceId + "'";
				sqlcount += " and t.FK_APPLYNO='" + projectId + "'";
				sqlcount += " and t.CHILD_PROJECT='" + childProject + "'";

				sqlcount += " and t.TRAINING_UNIT='" + trainingUnit + "'";
				sqlcount += " and t.SUBJECT='" + subject + "'";

				sqlcount += " and t.name='" + name + "'";
				sqlcount += " and t.telephone='" + phone + "'";

				int count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
				if (count > 0) {
					sb.append("文件第").append(r + 1).append("行，数据重复，").append(name).append("<br />");
					errorFlag = true;
					continue;
				}

				sqlcount = "select count(*) from TRAINHISTORY t where 1=1 and t.DIPCODE='" + dipcode + "'";
				count = Integer.parseInt(peProApplyNoService.executeSQL(sqlcount).get(0).toString());
				if (count > 0) {
					sb.append("文件第").append(r + 1).append("行，证书编号重复，").append(name).append("  ").append(dipcode).append("<br />");
					errorFlag = true;
					continue;
				}

			}

			if (errorFlag) {
				try {
					response.setCharacterEncoding("UTF-8");
					response.setHeader("Cache-Control", "no-cache");
					response.setContentType("text/html;charset=utf-8");
					response.getWriter().write(sb.toString());
				} catch (Exception e) {
					System.out.println(e);
				}
			} else {

				for (int r = 1; r < rows; r++) {
					HSSFRow row = sheet.getRow(r);
					if (row == null) {
						continue;
					}
					// 每个学员的信息
					String childProject = "";
					try {
						HSSFCell cell = row.getCell(0);
						if (cell != null) {
							if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
								childProject = df.format(cell.getNumericCellValue()).trim();
							} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
								childProject = cell.getStringCellValue().trim();
							}
						}
					} catch (Exception e) {
						System.out.println(e);
						System.out.println("row:" + r + "; cell:" + row.getCell(0));
					}

					String trainingUnit = "";
					try {
						HSSFCell cell = row.getCell(1);
						if (cell != null) {
							if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
								trainingUnit = df.format(cell.getNumericCellValue()).trim();
							} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
								trainingUnit = cell.getStringCellValue().trim();
							}
						}
					} catch (Exception e) {
						System.out.println(e);
						System.out.println("row:" + r + "; cell:" + row.getCell(1));
					}

					String subject = "";
					try {
						HSSFCell cell = row.getCell(2);
						if (cell != null) {
							if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
								subject = df.format(cell.getNumericCellValue()).trim();
							} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
								subject = cell.getStringCellValue().trim();
							}
						}
					} catch (Exception e) {
						System.out.println(e);
						System.out.println("row:" + r + "; cell:" + row.getCell(2));
					}
					String dipcode = "";

					try {
						HSSFCell cell = row.getCell(3);
						if (cell != null) {
							if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
								dipcode = df.format(cell.getNumericCellValue()).trim();
							} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
								dipcode = cell.getStringCellValue().trim();
							}
						}
					} catch (Exception e) {
						System.out.println(e);
						System.out.println("row:" + r + "; cell:" + row.getCell(3));
					}
					String name = "";

					try {
						HSSFCell cell = row.getCell(4);
						if (cell != null) {
							if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
								name = df.format(cell.getNumericCellValue()).trim();
							} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
								name = cell.getStringCellValue().trim();
							}
						}
					} catch (Exception e) {
						System.out.println(e);
						System.out.println("row:" + r + "; cell:" + row.getCell(4));
					}

					String workplace = "";
					try {
						HSSFCell cell = row.getCell(5);
						if (cell != null) {
							if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
								workplace = df.format(cell.getNumericCellValue()).trim();
							} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
								workplace = cell.getStringCellValue().trim();
							}
						}
					} catch (Exception e) {
						System.out.println(e);
						System.out.println("row:" + r + "; cell:" + row.getCell(5));
					}
					String phone = "";
					try {
						HSSFCell cell = row.getCell(6);
						if (cell != null) {
							if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
								phone = df.format(cell.getNumericCellValue()).trim();
							} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
								phone = cell.getStringCellValue().trim();
							}
						}
					} catch (Exception e) {
						System.out.println(e);
						System.out.println("row:" + r + "; cell:" + row.getCell(6));
					}
					String zhicheng = "";

					try {
						HSSFCell cell = row.getCell(7);
						if (cell != null) {
							if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
								zhicheng = df.format(cell.getNumericCellValue()).trim();
							} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
								zhicheng = cell.getStringCellValue().trim();
							}
						}
					} catch (Exception e) {
						System.out.println(e);
						System.out.println("row:" + r + "; cell:" + row.getCell(7));
					}
					String zhiwu = "";
					try {
						HSSFCell cell = row.getCell(8);
						if (cell != null) {
							if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
								zhiwu = df.format(cell.getNumericCellValue()).trim();
							} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
								zhiwu = cell.getStringCellValue().trim();
							}
						}
					} catch (Exception e) {
						System.out.println(e);
						System.out.println("row:" + r + "; cell:" + row.getCell(8));
					}
					String officePhone = "";
					try {
						HSSFCell cell = row.getCell(9);
						if (cell != null) {
							if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
								officePhone = df.format(cell.getNumericCellValue()).trim();
							} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
								officePhone = cell.getStringCellValue().trim();
							}
						}
					} catch (Exception e) {
						System.out.println(e);
						System.out.println("row:" + r + "; cell:" + row.getCell(9));
					}
					String mail = "";
					try {
						HSSFCell cell = row.getCell(10);
						if (cell != null) {
							if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
								mail = df.format(cell.getNumericCellValue()).trim();
							} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
								mail = cell.getStringCellValue().trim();
							}
						}
					} catch (Exception e) {
						System.out.println(e);
						System.out.println("row:" + r + "; cell:" + row.getCell(10));
					}
//					mail = mail.replaceAll("\\\\", ".");
					mail = replaceAll(mail);
					phone = replaceAll(phone);
					String beizhu1 = "";
					try {
						HSSFCell cell = row.getCell(11);
						if (cell != null) {
							if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
								beizhu1 = df.format(cell.getNumericCellValue()).trim();
							} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
								beizhu1 = cell.getStringCellValue().trim();
							}
						}
					} catch (Exception e) {
						System.out.println(e);
						System.out.println("row:" + r + "; cell:" + row.getCell(11));
					}

					UUID uuid = UUID.randomUUID();
					String vid = uuid.toString().replaceAll("-", "");

					// 插入 pe_trainee
					String sql = "insert into TRAINHISTORY(id,name,WORK_PLACE,telephone,email,office_phone,ZHIWU," + "ZHICHENG,FK_APPLYNO,CHILD_PROJECT,TRAINING_UNIT,SUBJECT," + "DIPCODE,BEIZHU1,FK_PROVINCE) values(";
					sql += "'" + vid + "','" + name + "','" + workplace + "','" + phone + "','" + mail + "','" + officePhone + "','" + zhiwu + "','" + zhicheng + "','" + projectId + "','" + childProject + "','" + trainingUnit + "','" + subject + "','" + dipcode + "','" + beizhu1 + "','" + proviceId
							+ "')";

					peProApplyService.executeSQLUpdate(sql);

				}
				try {
					response.setCharacterEncoding("UTF-8");
					response.setHeader("Cache-Control", "no-cache");
					response.setContentType("text/html;charset=utf-8");

					response.getWriter().write("插入成功!");

				} catch (Exception e) {
					System.out.println(e);
				}
			}

		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
	
	
	/*
	 * 20171016
	 * 新增按县区查询培训人数的功能
	 * 数据分为示范性和中西部
	 * 表格显示
	 * 重名的县区需要区分县区的所属上级单位
	 */
	public void headsearch4county() {

		String sql1 = "select t.id,t.name as name,t.code as code,p.name as provinceName,c.name as cityName from county t left join city c on t.fk_city=c.id left join pe_province p on c.fk_province=p.id order by t.code";

		List li = peProApplyNoService.getListPage(sql1, 0, -1);
//		List li = peProApplyNoService.getListPage(sql1, 0, 100);
//		String province = "{\"id\":\"all\",\"name\":\"请选择\",\"search\":\"所有\"},";
		String countyStr = "{\"id\":\"all\",\"name\":\"请选择\",\"code\":\"0000\",\"cityName\":\"无\",\"provinceName\":\"无\",\"search\":\"请选择\"},";
		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String id = obj[0].toString();
			String name = obj[1].toString();
			String search = obj[3].toString()+obj[4].toString()+obj[1].toString()+obj[2].toString();
			countyStr += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\",\"code\":\"" + obj[2].toString() + "\",\"cityName\":\"" + obj[4].toString() + "\",\"provinceName\":\"" + obj[3].toString() + "\",\"search\":\""+search+"\"}";
			countyStr += ",";
		}
//		this.countySearch = list;
//		System.out.println(this.countySearch.size());
//		return "countySearch";
		if (countyStr.length() > 0) {
			countyStr = countyStr.substring(0, countyStr.length() - 1);
		}
		
		String retJson = "{\"preInitData\":{\"county\":[" + countyStr + "]}}";

		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(retJson);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void adminTraineeNumForAllPKX(){
		String countyId = request.getParameter("county");

		String status = "OK";
		String message = "操作成功";
		String retJson = "{\"status\":\""+status+"\",\"message\":\""+message+"\"}";
		if (countyId == null || "".equals(countyId) || "all".equals(countyId)) {
//			countyId = "all";
			status = "FAIL";
			message = "请选择县区";
			retJson = "{\"status\":\""+status+"\",\"message\":\""+message+"\"}";
			try {
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write(retJson);
				return;
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		String sqlCounty = "select t.id,t.name as name,t.code as code,p.name as provinceName,c.name as cityName from county t left join city c on t.fk_city=c.id left join pe_province p on c.fk_province=p.id where t.id='"+countyId+"'";
		List li = peProApplyNoService.executeSQL(sqlCounty);
		Object[] obj = (Object[])li.get(0);
		String name = obj[1].toString();
		String cityName = obj[4].toString();
		String provinceName = obj[3].toString();
		String nameStr = provinceName+"-"+cityName+"-"+name;
		
		String sql = "select count(*) from trainee t where t.fk_county ='"+countyId+"'";
		String sql2 = "select count(*) from pe_trainee t "
				+ "left outer join ENUM_CONST enumconstb19_ on t.FK_STATUS_TRAINING=enumconstb19_.ID "
				+ "left outer join ENUM_CONST enumconstb2_ on t.FK_CHECKED_TRAINEE=enumconstb2_.ID where t.fk_county ='"+countyId+"' "
						+ " and (enumconstb19_.NAME like '新增' or enumconstb19_.NAME='正常') and enumconstb2_.NAME='审核已通过'";
		
		
		int mwcount = Integer.parseInt(peProApplyNoService.executeSQL(sql).get(0).toString());
		int sfcount = Integer.parseInt(peProApplyNoService.executeSQL(sql2).get(0).toString());
		
		retJson = "{\"status\":\""+status+"\",\"message\":\""+message
				+"\",\"nameStr\":\""+nameStr
				+"\",\"mwcount\":"+mwcount+",\"sfcount\":"+sfcount+"}";
		try {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(retJson);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	public static String replaceAll(String str) {
		str = str.replaceAll("\"", "")
				.replaceAll("\\\\", "")
				.replaceAll("\t", "")
				.replaceAll("	", "");
		return str;
	}
	
	public String getImpProvince() {
		return impProvince;
	}

	public void setImpProvince(String impProvince) {
		this.impProvince = impProvince;
	}

	public String getImpProjectParent() {
		return impProjectParent;
	}

	public void setImpProjectParent(String impProjectParent) {
		this.impProjectParent = impProjectParent;
	}

	public String getImpProject() {
		return impProject;
	}

	public void setImpProject(String impProject) {
		this.impProject = impProject;
	}

	public String getImpUnit() {
		return impUnit;
	}

	public void setImpUnit(String impUnit) {
		this.impUnit = impUnit;
	}

	public String getImpSubject() {
		return impSubject;
	}

	public void setImpSubject(String impSubject) {
		this.impSubject = impSubject;
	}

	public File getFileupload() {
		return fileupload;
	}

	public void setFileupload(File fileupload) {
		this.fileupload = fileupload;
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

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}


	public String getRole() {
		return role;
	}

	
	public void setRole(String role) {
		this.role = role;
	}
}
