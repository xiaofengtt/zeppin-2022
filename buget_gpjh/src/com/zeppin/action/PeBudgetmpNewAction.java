/**  
 * @Title: PeBudgetmpNewAction.java
 * @Package com.zeppin.action
 * @author jiangfei  
 */
package com.zeppin.action;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.opensymphony.xwork2.ActionSupport;
import com.zeppin.model.PeBudgetmpNew;
import com.zeppin.model.PeBudgetmpNewDetail;
import com.zeppin.model.peProApplyNo;
import com.zeppin.service.impl.PeBudgetmpNewDetailServiceImpl;
import com.zeppin.service.impl.PeBudgetmpNewServiceImpl;
import com.zeppin.service.impl.peProApplyNoServiceImpl;
import com.zeppin.service.impl.peProApplyServiceImpl;

/**
 * @author Administrator
 * 
 */

@Controller("peBudgetmpNewAction")
@Scope("prototype")
public class PeBudgetmpNewAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2710749723093568603L;

	private HttpServletRequest request;

	private HttpSession session;
	private HttpServletResponse response;

	@Autowired
	private peProApplyServiceImpl peProApplyService;

	@Autowired
	private peProApplyNoServiceImpl peProApplyNoService;

	@Autowired
	private PeBudgetmpNewServiceImpl peBudgetmpNewService;

	@Autowired
	private PeBudgetmpNewDetailServiceImpl peBudgetmpNewDetailService;

	private String msg;
	private String filePath;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * 项目信息
	 */
	private peProApplyNo peProApplyno;
	private PeBudgetmpNew peFeeBudget;

	@SuppressWarnings("rawtypes")
	private List subjectList;

	private String praid;
	private String pbid;
	private String classes;

	private String[] timearray;

	private Double total_item_1;
	private Double total_item_2;
	private Double total_item_3;
	private Double total_item_4;
	private Double total_item_5;
	private Double total_item_6;
	private Double total_item_7;
	private Double total_item_8;
	private Double total_item_9;
	private Double total_item_10;
	private Double total_item_11;
	private Double total_item_12;
	private Double total_item_13;
	private Double total_item_14;
	private Double total_item_15;
	private Double total_item_16;
	

	private String price_item_1;
	private String price_item_2;
	private String price_item_3;
	private String price_item_4;
	private String price_item_5;
	private String price_item_6;
	private String price_item_7;
	private String price_item_8;
	private String price_item_9;
	private String price_item_10;
	private String price_item_11;
	private String price_item_12;
	private String price_item_13;
	private String price_item_14;
	private String price_item_15;
	private String price_item_16;

	private String qty_item_1;
	private String qty_item_2;
	private String qty_item_3;
	private String qty_item_4;
	private String qty_item_5;
	private String qty_item_6;
	private String qty_item_7;
	private String qty_item_8;
	private String qty_item_9;
	private String qty_item_10;
	private String qty_item_11;
	private String qty_item_12;
	private String qty_item_13;
	private String qty_item_14;
	private String qty_item_15;
	private String qty_item_16;

	private String time_item_1;
	private String time_item_2;
	private String time_item_3;
	private String time_item_4;
	private String time_item_5;
	private String time_item_6;
	private String time_item_7;
	private String time_item_8;
	private String time_item_9;
	private String time_item_10;
	private String time_item_11;
	private String time_item_12;
	private String time_item_13;
	private String time_item_14;
	private String time_item_15;
	private String time_item_16;

	private String total_person_1;

	public PeBudgetmpNewAction() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	/**
	 * 跳转
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public void yusuanList() {

		String role = request.getParameter("role");
		String userid = request.getParameter("userid");
		String unitId = "";
		String unitName1 = "";

		if (role != null && role.equals("1")) {
			String sql = "select u.id,u.name from sso_user s join pe_manager p on s.login_id = p.login_id join pe_unit u on p.fk_unit=u.id " + "where s.login_id='" + userid + "'";
			List li = peProApplyNoService.executeSQL(sql);
			if (li.size() > 0) {
				Object[] obj = (Object[]) li.get(0);
				unitId = obj[0].toString();
				unitName1 = obj[1].toString();
			}
		}

		String searchName = request.getParameter("name");
		searchName = searchName == null ? "" : searchName;

		session.setAttribute("unitId", unitId);
		session.setAttribute("unitName", unitName1);

		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		String ien = (String) request.getParameter("rows");
		if (ien == null || ien.equals("")) {
			ien = "50";
		}

		int start = Integer.parseInt(ist);
		int limit = Integer.parseInt(ien);

		StringBuilder sqlCount = new StringBuilder();
		StringBuilder sql_a = new StringBuilder();

		sql_a.append("SELECT DISTINCT APPLYNO.ID AS ID,");
		sqlCount.append("SELECT COUNT(DISTINCT APPLYNO.ID)");

		sql_a.append("APPLYNO.NAME AS APPLYNONAME,");
		sql_a.append("APPLYNO.CODE AS APPLYNOCODE,");
		sql_a.append("APPLYNO.YEAR AS APPLYNOYEAR,");
		sql_a.append("EC1.NAME AS PROGRAMTYPE,");
		sql_a.append("APPLYNO.DEADLINE AS APPLYNODEADLINE,");
		sql_a.append("APPLYNO.FEE_STANDARD AS FEESTANDARD,");
		sql_a.append("APPLYNO.LIMIT AS APPLYNOLIMIT,");
		sql_a.append("UNIT.NAME AS UNITNAME,");
		sql_a.append("BUGET.INPUT_DATE AS INPUTTIME,");
//		sql_a.append("BUGET.ID_DATE AS BUIID,");
		sql_a.append("BUGET.AUD_DATE AS AUDTIME,");
		sql_a.append("BUGET.FLAG AS AUDFLAG");

		sql_a.append("  FROM PE_PRO_APPLY APPLY  ");
		sqlCount.append("  FROM PE_PRO_APPLY APPLY  ");

		sql_a.append(" INNER JOIN PE_PRO_APPLYNO APPLYNO ON APPLY.FK_APPLYNO = APPLYNO.ID    ");
		sqlCount.append(" INNER JOIN PE_PRO_APPLYNO APPLYNO ON APPLY.FK_APPLYNO = APPLYNO.ID    ");

		sql_a.append(" INNER JOIN PE_UNIT UNIT ON APPLY.FK_UNIT = UNIT.ID    ");
		sqlCount.append(" INNER JOIN PE_UNIT UNIT ON APPLY.FK_UNIT = UNIT.ID    ");

		sql_a.append(" LEFT OUTER JOIN PEBUDGETMPNEW BUGET ON BUGET.FK_PRO_APPLYNO = APPLYNO.ID AND BUGET.FK_UNIT= UNIT.ID");
		sqlCount.append(" LEFT OUTER JOIN PEBUDGETMPNEW BUGET ON BUGET.FK_PRO_APPLYNO = APPLYNO.ID AND BUGET.FK_UNIT= UNIT.ID");

		sql_a.append(" INNER JOIN ENUM_CONST EC1 ON APPLYNO.FK_PROGRAM_TYPE = EC1.ID         ");
		sqlCount.append(" INNER JOIN ENUM_CONST EC1 ON APPLYNO.FK_PROGRAM_TYPE = EC1.ID         ");

		sql_a.append(" INNER JOIN ENUM_CONST EC2 ON APPLY.FK_CHECK_FINAL = EC2.ID            ");
		sqlCount.append(" INNER JOIN ENUM_CONST EC2 ON APPLY.FK_CHECK_FINAL = EC2.ID            ");

		sql_a.append(" WHERE  EC2.CODE='1001'      ");
		sqlCount.append(" WHERE  EC2.CODE='1001'      ");
		
		sql_a.append(" and APPLYNO.YEAR in('2015','2016')      ");
		sqlCount.append(" and APPLYNO.YEAR in('2015','2016')       ");
		
//		sql_a.append(" AND BUGET.FK_UNIT= UNIT.ID     ");
//		sqlCount.append(" AND  BUGET.FK_UNIT= UNIT.ID      ");

		if (!unitId.equals("")) {
			sql_a.append("   AND    APPLY.FK_UNIT = '" + unitId + "' ");
			sqlCount.append("   AND    APPLY.FK_UNIT = '" + unitId + "' ");
		}

		if (!searchName.equals("")) {
			sql_a.append("   AND    APPLYNO.NAME like '%" + searchName + "%' ");
			sqlCount.append("   AND    APPLYNO.NAME like '%" + searchName + "%' ");
		}

		sql_a.append(" ORDER BY APPLYNO.DEADLINE DESC ");

		int totalCount = Integer.parseInt(peProApplyNoService.executeSQL(sqlCount.toString()).get(0).toString());
		int tol = totalCount / limit;
		if ((totalCount % limit) > 0) {
			tol += 1;
		}

		List li = peProApplyNoService.getListPage(sql_a.toString(), start, limit);

		String cellString = "";

		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);

			String id = obj[0].toString();
			String name = obj[1] == null ? "" : obj[1].toString();
//			String number = obj[2] == null ? "" : obj[2].toString();

			String year = obj[3] == null ? "" : obj[3].toString();
//			String type = obj[4] == null ? "" : obj[4].toString();
//			String time = obj[5] == null ? "" : obj[5].toString();

			String standand = obj[6] == null ? "" : obj[6].toString();
//			String limitStr = obj[7] == null ? "" : obj[7].toString();
			String unitName = obj[8] == null ? "" : obj[8].toString();
			
			String inputTime = obj[9] == null ? "" : obj[9].toString();
			String audTime = obj[10] == null ? "" : obj[10].toString();
			String flag = obj[11] == null ? "" : obj[11].toString();
			
//			String inputTime = obj[8] == null ? "" : obj[8].toString();
//			String audTime = obj[8] == null ? "" : obj[8].toString();
//			String flag = obj[8] == null ? "" : obj[8].toString();
			String statusName = "";
			if (flag.equals("0")) {
				statusName = "未审核";
			} else if (flag.equals("1")) {
				statusName = "通过";
			} else if (flag.equals("2")) {
				statusName = "未通过";
			}
			this.peProApplyno = new peProApplyNo();
			this.peProApplyno.setId(id);
			initSubjectApplyNo();
			
			List lst = this.subjectList;
			int total_persons = 0;
			if(lst !=null && lst.size()>0){
				for(int in = 0; in < lst.size(); in++){
					Object[] sub = (Object[])lst.get(in);
					int persons = Integer.parseInt(sub[2].toString().trim());
					total_persons += persons;
				}
			}
//			System.out.println(total_persons);
			
			
			
			String t = "{\"id\":\"" + id + "\",\"cell\":[\"" + unitName + "\",\"" + name + "\",\"" + year + "\",\"" + standand + "\",\"" + total_persons + "\",\"" + inputTime + "\",\"" + audTime + "\",\"" + statusName + "\"]}";

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
		}
	}

	/**
	 * 设置subject
	 */
	protected void initSubjectApplyNo() {
		String unitId = session.getAttribute("unitId") != null ? session.getAttribute("unitId").toString() : "";

		StringBuffer sql = new StringBuffer();
		sql.append("select sub.id, sub.name, count(stu.id)						");
		sql.append("  from pe_trainee stu                                       ");
		sql.append("  full outer join pe_pro_apply program                      ");
		sql.append("    on stu.fk_pro_applyno = program.fk_applyno              ");
		sql.append("   and stu.fk_subject = program.fk_subject                  ");
		sql.append("   and stu.fk_training_unit = program.fk_unit               ");
		sql.append(" inner join pe_pro_applyno appno                            ");
		sql.append("    on appno.id = program.fk_applyno                        ");
		sql.append(" inner join pe_subject sub                                  ");
		sql.append("    on program.fk_subject = sub.id                          ");
		sql.append("  left outer join enum_const enu                            ");
		sql.append("    on stu.fk_status_training = enu.id                      ");
		sql.append(" inner join enum_const ec                                   ");
		sql.append("    on program.fk_check_final = ec.id                       ");
		sql.append(" where ec.code = '1001'                                     ");
		sql.append("   and (enu.code is null or enu.code in ('001', '003'))     ");
		if (!unitId.equals("")) {
			sql.append("   and program.fk_unit = '" + unitId + "' ");
		}
		sql.append("   and appno.id = '" + this.peProApplyno.getId() + "'        ");
		sql.append(" group by sub.id, sub.name                                  ");

		this.subjectList = this.getPeProApplyNoService().executeSQL(sql.toString());
	}

	public void initPeBudgetmp() {

		String unitId = session.getAttribute("unitId") != null ? session.getAttribute("unitId").toString() : "";

		String hql = "from PeBudgetmpNew pb where 1=1 and pb.fk_pro_applyno='" + this.peProApplyno.getId() + "'";

		if (!unitId.equals("")) {
			hql += " and pb.fk_unit='" + unitId + "'";
		}

		hql += " order by pb.input_date desc ";

		List<PeBudgetmpNew> lstmp = this.getPeBudgetmpNewService().getListByHSQL(hql);
		if (lstmp != null && lstmp.size() > 0) {
			this.peFeeBudget = lstmp.get(0);
		} else {
			String unitName = session.getAttribute("unitName") != null ? session.getAttribute("unitName").toString() : "";
			this.peFeeBudget = new PeBudgetmpNew();
			this.peFeeBudget.setUnitName(unitName);
		}
	}

	/**
	 * 提交预算
	 * 
	 * @return
	 */
	public String addForward() {

		String projectId = request.getParameter("proId");
		this.peProApplyno = this.getPeProApplyNoService().get(projectId);

		initSubjectApplyNo();
		
//		String unitId = session.getAttribute("unitId") != null ? session.getAttribute("unitId").toString() : "";
//
//		String hql = "from PeBudgetmpNew pb where 1=1 and pb.fk_pro_applyno='" + this.peProApplyno.getId() + "'";
//
//		if (!unitId.equals("")) {
//			hql += " and pb.fk_unit='" + unitId + "'";
//		}
//
//		hql += " order by pb.input_date desc ";
//
//		List<PeBudgetmpNew> lstmp = this.getPeBudgetmpNewService().getListByHSQL(hql);
//		if (lstmp != null && lstmp.size() > 0) {
////			this.peFeeBudget = lstmp.get(0);
////			PeBudgetmpNew tmp = new PeBudgetmpNew();
//			for(PeBudgetmpNew tmp:lstmp){
//				try {
//					this.peBudgetmpNewService.delete(tmp);
//					this.peBudgetmpNewDetailService.delete(tmp.getPeFeeBudgetNewDetail());
//					
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
//			}
//		} 
////		else {
////			String unitName = session.getAttribute("unitName") != null ? session.getAttribute("unitName").toString() : "";
////			this.peFeeBudget = new PeBudgetmpNew();
////			this.peFeeBudget.setUnitName(unitName);
////		}
		

		initPeBudgetmp();

//		String[] strs = this.peFeeBudget.getPeFeeBudgetNewDetail().getNoteMeal().split("\\*");
////		String[] strs = this.peFeeBudget.getPeFeeBudgetNewDetail().getNoteOfficeSupplies().split("\\*");
//		for(String str: strs){
//			System.out.println(str+"+");
//		}
//		
		
		return "yusuan";
	}

	/**
	 * save
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String savePeBudgetmp() {

		this.peProApplyno = this.getPeProApplyNoService().get(this.praid);
		initSubjectApplyNo();

		PeBudgetmpNewDetail peFeeBudgetNewDetail = new PeBudgetmpNewDetail();

		boolean isadd = false;
		
		String unitId2 = session.getAttribute("unitId") != null ? session.getAttribute("unitId").toString() : "";

		String hql = "from PeBudgetmpNew pb where 1=1 and pb.fk_pro_applyno='" + this.peProApplyno.getId() + "'";

		if (!unitId2.equals("")) {
			hql += " and pb.fk_unit='" + unitId2 + "'";
		}

		hql += " order by pb.input_date desc ";

		List<PeBudgetmpNew> lstmp = this.getPeBudgetmpNewService().getListByHSQL(hql);
		PeBudgetmpNew pb = new PeBudgetmpNew();
		if (lstmp != null && lstmp.size() > 0) {
			pb = lstmp.get(0);
		} 

//		if (this.getPbid() != null && !this.getPbid().equals("")) {
		if (pb.getId() != null && !pb.getId().equals("")) {
			// 跟新
			PeBudgetmpNew tmp = pb;

			if (tmp.getFlag() == 1) {
				msg = "项目办管理员已经审核通过，不能修改！";
				return "ok";
			}

			peFeeBudgetNewDetail = tmp.getPeFeeBudgetNewDetail();
			String unitId = session.getAttribute("unitId") != null ? session.getAttribute("unitId").toString() : "";

			this.peFeeBudget.setId(tmp.getId());
			this.peFeeBudget.setFk_unit(unitId);
			this.peFeeBudget.setFk_pro_applyno(peProApplyno);
			this.peFeeBudget.setFlag(0);//编辑过后状态变为未审核状态
			
			this.peFeeBudget.setInput_date(new Date(System.currentTimeMillis()));

			this.peFeeBudget.setPersonCount(tmp.getPersonCount());
			this.peFeeBudget.setPeFeeBudgetNewDetail(peFeeBudgetNewDetail);

		} else {
			isadd = true;
			// 添加
			String unitId = session.getAttribute("unitId") != null ? session.getAttribute("unitId").toString() : "";

			UUID uuid = UUID.randomUUID();
			String vid = uuid.toString().replaceAll("-", "");
			this.peFeeBudget.setId(vid);

			UUID uuidt = UUID.randomUUID();
			String vidt = uuidt.toString().replaceAll("-", "");
			peFeeBudgetNewDetail.setId(vidt);

			this.peFeeBudget.setFk_unit(unitId);
			this.peFeeBudget.setFk_pro_applyno(this.peProApplyno);
		}

		List subList = this.getSubjectList();
		String personNote = "";
		for (int i = 0; i < subList.size(); i++) {
			Object obj = subList.get(i);
			Object[] o = (Object[]) obj;
			String person = request.getParameter("person_" + i);
			personNote += o[1] + ":" + person + "人；";
		}
		personNote += "总计：" + this.getTotal_person_1() + "人、" + this.getClasses() + "个培训班。";
		
		peFeeBudget.setPersonCount(personNote);

		// 设置配置培训周期
		if (timearray != null) {
			StringBuilder sb = new StringBuilder();
			for (String st : timearray) {
				sb.append(st).append(";");
			}
			peFeeBudget.setTraining_data(sb.toString());
		}

		peFeeBudgetNewDetail.setFeeMeal(this.getTotal_item_1());
		peFeeBudgetNewDetail.setFeeAccommodation(this.getTotal_item_2());
		peFeeBudgetNewDetail.setFeeTeach(this.getTotal_item_3());
		peFeeBudgetNewDetail.setFeeTrafficExpert(this.getTotal_item_4());
		peFeeBudgetNewDetail.setFeeMealAccExpert(this.getTotal_item_5());
		peFeeBudgetNewDetail.setFeeMaterials(this.getTotal_item_6());
		peFeeBudgetNewDetail.setFeeAreaRent(this.getTotal_item_7());
		peFeeBudgetNewDetail.setFeeEquipmentRent(this.getTotal_item_8());
		// peFeeBudgetDetail.setFeeLabourService(this.getTotal_item_9());
		peFeeBudgetNewDetail.setFeeSurvey(this.getTotal_item_9());
		peFeeBudgetNewDetail.setFeeResearch(this.getTotal_item_10());
		peFeeBudgetNewDetail.setFeeArgument(this.getTotal_item_11());
		peFeeBudgetNewDetail.setFeeTrafficStu(this.getTotal_item_12());
//		peFeeBudgetNewDetail.setFeeElectronCourse(this.getTotal_item_13());
		peFeeBudgetNewDetail.setFeeElectronCourse(this.getTotal_item_14());
		peFeeBudgetNewDetail.setFeeMealExpert(this.getTotal_item_15());
		peFeeBudgetNewDetail.setFeeOfficeSupplies(this.getTotal_item_16());

		peFeeBudgetNewDetail.setNoteMeal(this.getPrice_item_1() + "*" + this.getQty_item_1() + "*" + this.getTime_item_1());
		peFeeBudgetNewDetail.setNoteAccommodation(this.getPrice_item_2() + "*" + this.getQty_item_2() + "*" + this.getTime_item_2());
		peFeeBudgetNewDetail.setNoteTeach(this.getPrice_item_3() + "*" + this.getQty_item_3());
		peFeeBudgetNewDetail.setNoteTrafficExpert(this.getPrice_item_4() + "*" + this.getQty_item_4());
		peFeeBudgetNewDetail.setNoteMealAccExpert(this.getPrice_item_5() + "*" + this.getQty_item_5());
		peFeeBudgetNewDetail.setNoteMaterials(this.getPrice_item_6() + "*" + this.getQty_item_6());
		peFeeBudgetNewDetail.setNoteAreaRent(this.getPrice_item_7() + "*" + this.getQty_item_7());
		peFeeBudgetNewDetail.setNoteEquipmentRent(this.getPrice_item_8() + "*" + this.getQty_item_8());

		// peFeeBudgetDetail.setNoteLabourService(this.getPrice_item_9()+"*"+this.getQty_item_9());

		peFeeBudgetNewDetail.setNoteSurvey(this.getPrice_item_9() + "*" + this.getQty_item_9());
		peFeeBudgetNewDetail.setNoteResearch(this.getPrice_item_10() + "*" + this.getQty_item_10() + "*" + this.getTime_item_10());
		peFeeBudgetNewDetail.setNoteArgument(this.getPrice_item_11() + "*" + this.getQty_item_11());
		peFeeBudgetNewDetail.setNoteTrafficStu(this.getPrice_item_12() + "*" + this.getQty_item_12() + "*" + this.getTime_item_12());
//		peFeeBudgetNewDetail.setNoteElectronCourse(this.getPrice_item_13() + "*" + this.getQty_item_13());
		peFeeBudgetNewDetail.setNoteElectronCourse(this.getPrice_item_14() + "*" + this.getQty_item_14() + "*" + this.getTime_item_14());
		peFeeBudgetNewDetail.setNoteMealExpert(this.getPrice_item_15() + "*" + this.getQty_item_15());
		peFeeBudgetNewDetail.setNoteOfficeSupplies(this.getPrice_item_16() + "*" + this.getQty_item_16());
		
		
//		if (isadd) {
//			this.getPeBudgetmpNewDetailService().add(peFeeBudgetNewDetail);
//			this.peFeeBudget.setPeFeeBudgetNewDetail(peFeeBudgetNewDetail);
//			this.getPeBudgetmpNewService().add(peFeeBudget);
//			msg = "添加成功";
//		} else {
//			this.getPeBudgetmpNewDetailService().update(peFeeBudgetNewDetail);
//			this.getPeBudgetmpNewService().update(peFeeBudget);
//			msg = "编辑成功";
//		}

		if(isadd){
			int status = this.getPeBudgetmpNewService().addAndUpdate(isadd, peFeeBudgetNewDetail, this.peFeeBudget);
			if(status == 1){
				this.msg = "添加成功";
			}else{
				this.msg = "添加失败";
			}
		}else{
			int status = this.getPeBudgetmpNewService().addAndUpdate(isadd, peFeeBudgetNewDetail, this.peFeeBudget);
			if(status == 1){
				this.msg = "编辑成功";
			}else{
				this.msg = "编辑失败";
			}
		}
		return "ok";
	}

	private Double total;

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String toDetail() {

		String projectId = request.getParameter("proId");
		this.peProApplyno = this.getPeProApplyNoService().get(projectId);

		initSubjectApplyNo();

		initPeBudgetmp();

		if (this.peFeeBudget != null) {
			PeBudgetmpNewDetail peFB = this.peFeeBudget.getPeFeeBudgetNewDetail();
			if (peFB != null) {
				DecimalFormat df1 = new DecimalFormat("#.00");
				double sum=peFB.getFeeMeal() + peFB.getFeeAccommodation() + peFB.getFeeTeach() + peFB.getFeeTrafficExpert() + peFB.getFeeMealAccExpert() + peFB.getFeeMaterials() + peFB.getFeeAreaRent() + peFB.getFeeEquipmentRent() + peFB.getFeeSurvey() + peFB.getFeeResearch()
						+ peFB.getFeeArgument() + peFB.getFeeTrafficStu() + peFB.getFeeElectronCourse() + peFB.getFeeMealExpert() + peFB.getFeeOfficeSupplies();
				
				this.setTotal(Double.parseDouble(df1.format(sum)));
//				this.setTotal(peFB.getFeeMeal() + peFB.getFeeAccommodation() + peFB.getFeeTeach() + peFB.getFeeTrafficExpert() + peFB.getFeeMealAccExpert() + peFB.getFeeMaterials() + peFB.getFeeAreaRent() + peFB.getFeeEquipmentRent() + peFB.getFeeSurvey() + peFB.getFeeResearch()
//						+ peFB.getFeeArgument() + peFB.getFeeTrafficStu() + peFB.getFeeElectronCourse() + peFB.getFeeMealExpert() + peFB.getFeeOfficeSupplies());
			}
		}
		return "yusuan_print";
	}

	public String toPdf() throws DocumentException, IOException {

		String projectId = request.getParameter("proId");
		this.peProApplyno = this.getPeProApplyNoService().get(projectId);

		initPeBudgetmp();

		String path = ServletActionContext.getServletContext().getRealPath("/").replace("\\", "/");

		if (this.peFeeBudget != null && this.peFeeBudget.getId() != null && !this.peFeeBudget.getId().equals("")) {

			PeBudgetmpNewDetail budgetDetal = this.peFeeBudget.getPeFeeBudgetNewDetail();
			String returnValue = "export/" + this.peFeeBudget.getId() + ".pdf";
			String TemplatePDF = path + "export/budgetNew.pdf";

			FileOutputStream fos = new FileOutputStream(path + returnValue);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);

			PdfReader reader = new PdfReader(TemplatePDF);
			PdfStamper stamp = new PdfStamper(reader, baos);

			AcroFields form = stamp.getAcroFields();
			form.addSubstitutionFont(bfChinese);

			form.setField("projectName", this.peProApplyno.getName());
			form.setField("unitName", this.peFeeBudget.getUnitName() + "（公章）");
			form.setField("personCount", this.peFeeBudget.getPersonCount());
			String[] strTime = this.peFeeBudget.getTraining_data() == null ? new String[0] : this.peFeeBudget.getTraining_data().split(";");
			StringBuilder sb = new StringBuilder();
			String[] ast = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
			for (int i = 0; i < strTime.length; i++) {
				sb.append("第").append(ast[i + 1]).append("期：").append(strTime[i]).append("\r\n");
			}
			form.setField("trainingDate", sb.toString());

			if (budgetDetal != null) {
				form.setField("fee1", budgetDetal.getFeeMeal().toString());
				form.setField("fee2", budgetDetal.getFeeAccommodation().toString());
				form.setField("fee3", budgetDetal.getFeeTeach().toString());
				form.setField("fee4", budgetDetal.getFeeTrafficExpert().toString());
				form.setField("fee5", budgetDetal.getFeeMealAccExpert().toString());
				form.setField("fee6", budgetDetal.getFeeMaterials().toString());
				form.setField("fee7", budgetDetal.getFeeAreaRent().toString());
				form.setField("fee8", budgetDetal.getFeeEquipmentRent().toString());

				form.setField("fee10", budgetDetal.getFeeSurvey().toString());
				form.setField("fee11", budgetDetal.getFeeResearch().toString());
				form.setField("fee12", budgetDetal.getFeeArgument().toString());
				form.setField("fee13", budgetDetal.getFeeTrafficStu().toString());
				form.setField("fee14", budgetDetal.getFeeElectronCourse().toString());
				
				form.setField("fee15", budgetDetal.getFeeMealExpert().toString());
				form.setField("fee16", budgetDetal.getFeeOfficeSupplies().toString());

				String temp[] = budgetDetal.getNoteMeal().split("\\*");
				form.setField("note1", temp[0] + "元/人/天×" + temp[1] + "人×" + temp[2] + "天");
				temp = budgetDetal.getNoteAccommodation().split("\\*");
				form.setField("note2", temp[0] + "元/人/天×" + temp[1] + "人×" + temp[2] + "天");
				temp = budgetDetal.getNoteTeach().split("\\*");
				form.setField("note3", temp[0] + "元/次×" + temp[1] + "次");
				temp = budgetDetal.getNoteTrafficExpert().split("\\*");
				form.setField("note4", temp[0] + "元/人次×" + temp[1] + "人次");
				temp = budgetDetal.getNoteMealAccExpert().split("\\*");
				form.setField("note5", temp[0] + "元/（人•半天）×" + temp[1] + "人次");
				temp = budgetDetal.getNoteMaterials().split("\\*");
				form.setField("note6", temp[0] + "元/人次×" + temp[1] + "人次");
				temp = budgetDetal.getNoteAreaRent().split("\\*");
				form.setField("note7", temp[0] + "元/人次×" + temp[1] + "人次");
				temp = budgetDetal.getNoteEquipmentRent().split("\\*");
				form.setField("note8", temp[0] + "元/人×" + temp[1] + "人");

				temp = budgetDetal.getNoteSurvey().split("\\*");
				form.setField("note10", temp[0] + "元/件×" + temp[1] + "件");
				temp = budgetDetal.getNoteResearch().split("\\*");
				form.setField("note11", temp[0] + "元/班次/天×" + temp[1] + "班次×" + temp[2] + "天");
				temp = budgetDetal.getNoteArgument().split("\\*");
				form.setField("note12", temp[0] + "元/人×" + temp[1] + "人");
				temp = budgetDetal.getNoteTrafficStu().split("\\*");
				form.setField("note13", temp[0] + "元/班次/天×" + temp[1] + "班次×" + temp[2] + "天");

				temp = budgetDetal.getNoteElectronCourse().split("\\*");
				form.setField("note14", temp[0] + "元/人/天×" + temp[1] + "人×" + temp[2] + "天");
				temp = budgetDetal.getNoteMealExpert().split("\\*");
				form.setField("note15", temp[0] + "元/人次×" + temp[1] + "人次");
				temp = budgetDetal.getNoteOfficeSupplies().split("\\*");
				form.setField("note16",temp[0] + "元/人×" + temp[1] + "人");

				
				Double total = budgetDetal.getFeeMeal() + budgetDetal.getFeeAccommodation() + budgetDetal.getFeeTeach() + budgetDetal.getFeeTrafficExpert() + budgetDetal.getFeeMealAccExpert() + budgetDetal.getFeeMaterials() + budgetDetal.getFeeAreaRent() + budgetDetal.getFeeEquipmentRent()
						+ budgetDetal.getFeeSurvey() + budgetDetal.getFeeResearch() + budgetDetal.getFeeArgument() + budgetDetal.getFeeTrafficStu() + budgetDetal.getFeeElectronCourse() + budgetDetal.getFeeMealExpert() + budgetDetal.getFeeOfficeSupplies();
//				form.setField("total", total.toString());
				DecimalFormat df1 = new DecimalFormat("#.00");
				form.setField("total", df1.format(total));
			}

			form.setField("payeeName", this.peFeeBudget.getPayeeName());
			form.setField("openingBank", this.peFeeBudget.getOpeningBank());
			form.setField("accountNumber", this.peFeeBudget.getAccountNumber());
			form.setField("contactInfo", this.peFeeBudget.getContactInfo());

			stamp.setFormFlattening(true); // 千万不漏了这句啊, */
			stamp.close();

			Document doc = new Document();
			PdfCopy pdfCopy = new PdfCopy(doc, fos);
			doc.open();

			PdfImportedPage impPage = pdfCopy.getImportedPage(new PdfReader(baos.toByteArray()), 1);
			pdfCopy.addPage(impPage);

			doc.close();// 当文件拷贝 记得关闭doc

			this.filePath = returnValue;

			return "actual_pdf";
		} else {
			msg = "还没有提交预算信息！";
			return "ok";
		}

	}

	public String toPdf2() throws DocumentException, IOException {
		String peId = request.getParameter("peId");

		this.peFeeBudget = this.getPeBudgetmpNewService().get(peId);

		String path = ServletActionContext.getServletContext().getRealPath("/").replace("\\", "/");

		if (this.peFeeBudget != null) {

			PeBudgetmpNewDetail budgetDetal = this.peFeeBudget.getPeFeeBudgetNewDetail();
			String returnValue = "export/" + this.peFeeBudget.getId() + ".pdf";
			String TemplatePDF = path + "export/budgetNew.pdf";

			FileOutputStream fos = new FileOutputStream(path + returnValue);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);

			PdfReader reader = new PdfReader(TemplatePDF);
			PdfStamper stamp = new PdfStamper(reader, baos);

			AcroFields form = stamp.getAcroFields();
			form.addSubstitutionFont(bfChinese);

			form.setField("projectName", this.peFeeBudget.getFk_pro_applyno().getName());
			form.setField("unitName", this.peFeeBudget.getUnitName() + "（公章）");
			form.setField("personCount", this.peFeeBudget.getPersonCount());
			String[] strTime = this.peFeeBudget.getTraining_data() == null ? new String[0] : this.peFeeBudget.getTraining_data().split(";");
			StringBuilder sb = new StringBuilder();
			String[] ast = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
			for (int i = 0; i < strTime.length; i++) {
				sb.append("第").append(ast[i + 1]).append("期：").append(strTime[i]).append("\r\n");
			}
			form.setField("trainingDate", sb.toString());

			if (budgetDetal != null) {
				form.setField("fee1", budgetDetal.getFeeMeal().toString());
				form.setField("fee2", budgetDetal.getFeeAccommodation().toString());
				form.setField("fee3", budgetDetal.getFeeTeach().toString());
				form.setField("fee4", budgetDetal.getFeeTrafficExpert().toString());
				form.setField("fee5", budgetDetal.getFeeMealAccExpert().toString());
				form.setField("fee6", budgetDetal.getFeeMaterials().toString());
				form.setField("fee7", budgetDetal.getFeeAreaRent().toString());
				form.setField("fee8", budgetDetal.getFeeEquipmentRent().toString());

				form.setField("fee10", budgetDetal.getFeeSurvey().toString());
				form.setField("fee11", budgetDetal.getFeeResearch().toString());
				form.setField("fee12", budgetDetal.getFeeArgument().toString());
				form.setField("fee13", budgetDetal.getFeeTrafficStu().toString());
				form.setField("fee14", budgetDetal.getFeeElectronCourse().toString());
				
				form.setField("fee15", budgetDetal.getFeeMealExpert().toString());
				form.setField("fee16", budgetDetal.getFeeOfficeSupplies().toString());

				String temp[] = budgetDetal.getNoteMeal().split("\\*");
				form.setField("note1", temp[0] + "元/人/天×" + temp[1] + "人×" + temp[2] + "天");
				temp = budgetDetal.getNoteAccommodation().split("\\*");
				form.setField("note2", temp[0] + "元/人/天×" + temp[1] + "人×" + temp[2] + "天");
				temp = budgetDetal.getNoteTeach().split("\\*");
				form.setField("note3", temp[0] + "元/次×" + temp[1] + "次");
				temp = budgetDetal.getNoteTrafficExpert().split("\\*");
				form.setField("note4", temp[0] + "元/人次×" + temp[1] + "人次");
				temp = budgetDetal.getNoteMealAccExpert().split("\\*");
				form.setField("note5", temp[0] + "元/（人•半天）×" + temp[1] + "人次");
				temp = budgetDetal.getNoteMaterials().split("\\*");
				form.setField("note6", temp[0] + "元/人次×" + temp[1] + "人次");
				temp = budgetDetal.getNoteAreaRent().split("\\*");
				form.setField("note7", temp[0] + "元/人次×" + temp[1] + "人次");
				temp = budgetDetal.getNoteEquipmentRent().split("\\*");
				form.setField("note8", temp[0] + "元/人×" + temp[1] + "人");

				temp = budgetDetal.getNoteSurvey().split("\\*");
				form.setField("note10", temp[0] + "元/件×" + temp[1] + "件");
				temp = budgetDetal.getNoteResearch().split("\\*");
				form.setField("note11", temp[0] + "元/班次/天×" + temp[1] + "班次×" + temp[2] + "天");
				temp = budgetDetal.getNoteArgument().split("\\*");
				form.setField("note12", temp[0] + "元/人×" + temp[1] + "人");
				temp = budgetDetal.getNoteTrafficStu().split("\\*");
				form.setField("note13", temp[0] + "元/班次/天×" + temp[1] + "班次×" + temp[2] + "天");

				temp = budgetDetal.getNoteElectronCourse().split("\\*");
				form.setField("note14", temp[0] + "元/人/天×" + temp[1] + "人×" + temp[2] + "天");
				temp = budgetDetal.getNoteMealExpert().split("\\*");
				form.setField("note15", temp[0] + "元/人次×" + temp[1] + "人次");
				temp = budgetDetal.getNoteOfficeSupplies().split("\\*");
				form.setField("note16",temp[0] + "元/人×" + temp[1] + "人");

				Double total = budgetDetal.getFeeMeal() + budgetDetal.getFeeAccommodation() + budgetDetal.getFeeTeach() + budgetDetal.getFeeTrafficExpert() + budgetDetal.getFeeMealAccExpert() + budgetDetal.getFeeMaterials() + budgetDetal.getFeeAreaRent() + budgetDetal.getFeeEquipmentRent()
						+ budgetDetal.getFeeSurvey() + budgetDetal.getFeeResearch() + budgetDetal.getFeeArgument() + budgetDetal.getFeeTrafficStu() + budgetDetal.getFeeElectronCourse() + budgetDetal.getFeeMealExpert() + budgetDetal.getFeeOfficeSupplies();
//				form.setField("total", total.toString());
				DecimalFormat df1 = new DecimalFormat("#.00");
				form.setField("total", df1.format(total));
			}

			form.setField("payeeName", this.peFeeBudget.getPayeeName());
			form.setField("openingBank", this.peFeeBudget.getOpeningBank());
			form.setField("accountNumber", this.peFeeBudget.getAccountNumber());
			form.setField("contactInfo", this.peFeeBudget.getContactInfo());

			stamp.setFormFlattening(true); // 千万不漏了这句啊, */
			stamp.close();

			Document doc = new Document();
			PdfCopy pdfCopy = new PdfCopy(doc, fos);
			doc.open();

			PdfImportedPage impPage = pdfCopy.getImportedPage(new PdfReader(baos.toByteArray()), 1);
			pdfCopy.addPage(impPage);

			doc.close();// 当文件拷贝 记得关闭doc

			this.filePath = returnValue;

		}

		return "actual_pdf";
	}

	@SuppressWarnings("rawtypes")
	public void yusuanList2() {
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		String ien = (String) request.getParameter("rows");
		if (ien == null || ien.equals("")) {
			ien = "50";
		}

		int start = Integer.parseInt(ist);
		int limit = Integer.parseInt(ien);

		StringBuilder sqlCount = new StringBuilder();
		StringBuilder sql_a = new StringBuilder();

		sql_a.append("SELECT DISTINCT APPLYNO.ID AS ID,");
		sqlCount.append("SELECT COUNT(*)");

		sql_a.append("PB.ID AS PBID,");
		sql_a.append("APPLYNO.NAME AS APPLYNONAME,");
		sql_a.append("APPLYNO.CODE AS APPLYNOCODE,");
		sql_a.append("APPLYNO.YEAR AS APPLYNOYEAR,");
		sql_a.append("EC.NAME AS PROGRAMTYPE,");
		sql_a.append("APPLYNO.DEADLINE AS APPLYNODEADLINE,");
		sql_a.append("APPLYNO.FEE_STANDARD AS FEESTANDARD,");
		sql_a.append("APPLYNO.LIMIT AS APPLYNOLIMIT,");
		sql_a.append("UNIT.NAME AS UNITNAME,");
		sql_a.append("PB.INPUT_DATE AS INDATE,");
		sql_a.append("PB.FLAG AS STATUS,");
		sql_a.append("PB.AUD_DATE AS AUDTIME,");
		sql_a.append("PB.PERSON_COUNT AS PSCOUNT,");
		sql_a.append("PB.FK_UNIT AS PBUNIT");

		sql_a.append(" FROM PEBUDGETMPNEW PB,PE_PRO_APPLYNO APPLYNO,PE_UNIT UNIT ,ENUM_CONST EC ");
		sqlCount.append(" FROM PEBUDGETMPNEW PB,PE_PRO_APPLYNO APPLYNO,PE_UNIT UNIT,ENUM_CONST EC ");

		sql_a.append(" where PB.FK_PRO_APPLYNO=APPLYNO.ID ");
		sqlCount.append(" where PB.FK_PRO_APPLYNO=APPLYNO.ID ");

		sql_a.append(" and PB.FK_UNIT = UNIT.ID ");
		sqlCount.append(" and PB.FK_UNIT = UNIT.ID ");

		sql_a.append(" and APPLYNO.FK_PROGRAM_TYPE = EC.ID");
		sqlCount.append(" and APPLYNO.FK_PROGRAM_TYPE = EC.ID");
		
		sql_a.append(" and APPLYNO.YEAR in('2015','2016')      ");
		sqlCount.append(" and APPLYNO.YEAR in('2015','2016')       ");

		String searchName = request.getParameter("name");
		searchName = searchName == null ? "" : searchName;
		if (!searchName.equals("")) {
			sql_a.append("   AND    APPLYNO.NAME like '%" + searchName + "%' ");
			sqlCount.append("   AND    APPLYNO.NAME like '%" + searchName + "%' ");
		}

		String searchUnitName = request.getParameter("unitName");
		searchUnitName = searchUnitName == null ? "" : searchUnitName;
		if (!searchUnitName.equals("")) {
			sql_a.append("   AND    UNIT.NAME like '%" + searchUnitName + "%' ");
			sqlCount.append("   AND    UNIT.NAME like '%" + searchUnitName + "%' ");
		}

		String status = request.getParameter("status");
		status = status == null ? "-1" : status;
		if (!status.equals("-1")) {
			sql_a.append("   AND    PB.FLAG =" + status + " ");
			sqlCount.append("   AND    PB.FLAG =" + status + " ");
		}

		sql_a.append(" ORDER BY INDATE DESC ");

		int totalCount = Integer.parseInt(peProApplyNoService.executeSQL(sqlCount.toString()).get(0).toString());
		int tol = totalCount / limit;
		if ((totalCount % limit) > 0) {
			tol += 1;
		}

		List li = peProApplyNoService.getListPage(sql_a.toString(), start, limit);

		String cellString = "";

		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);

			String id = obj[0].toString();
			String peId = obj[1].toString();
			String name = obj[2] == null ? "" : obj[2].toString();
//			String number = obj[3] == null ? "" : obj[3].toString();

			String year = obj[4] == null ? "" : obj[4].toString();
//			String type = obj[5] == null ? "" : obj[5].toString();
//			String time = obj[6] == null ? "" : obj[6].toString();

			String standand = obj[7] == null ? "" : obj[7].toString();
//			String limitStr = obj[8] == null ? "" : obj[8].toString();
			String unitName = obj[9] == null ? "" : obj[9].toString();
			String intime = obj[10] == null ? "" : obj[10].toString();
			String flag = obj[11] == null ? "" : obj[11].toString();
			String statusName = "未知";
			if (flag.equals("0")) {
				statusName = "未审核";
			} else if (flag.equals("1")) {
				statusName = "通过";
			} else if (flag.equals("2")) {
				statusName = "未通过";
			}

			String audTime = obj[12] == null ? "" : obj[12].toString();
//			
//			String personCount = obj[13] == null ? "" : obj[13].toString();
//			int index1 = personCount.lastIndexOf("、");
//			int index2 = personCount.lastIndexOf("：");
//			String total_persons = personCount.substring(index2+1, index1-1);
			
			String unitId = obj[14] == null ? "" : obj[14].toString();;
			this.peProApplyno = new peProApplyNo();
			this.peProApplyno.setId(id);
//			initSubjectApplyNo();
			StringBuffer sql = new StringBuffer();
			sql.append("select sub.id, sub.name, count(stu.id)						");
			sql.append("  from pe_trainee stu                                       ");
			sql.append("  full outer join pe_pro_apply program                      ");
			sql.append("    on stu.fk_pro_applyno = program.fk_applyno              ");
			sql.append("   and stu.fk_subject = program.fk_subject                  ");
			sql.append("   and stu.fk_training_unit = program.fk_unit               ");
			sql.append(" inner join pe_pro_applyno appno                            ");
			sql.append("    on appno.id = program.fk_applyno                        ");
			sql.append(" inner join pe_subject sub                                  ");
			sql.append("    on program.fk_subject = sub.id                          ");
			sql.append("  left outer join enum_const enu                            ");
			sql.append("    on stu.fk_status_training = enu.id                      ");
			sql.append(" inner join enum_const ec                                   ");
			sql.append("    on program.fk_check_final = ec.id                       ");
			sql.append(" where ec.code = '1001'                                     ");
			sql.append("   and (enu.code is null or enu.code in ('001', '003'))     ");
			if (!unitId.equals("")) {
				sql.append("   and program.fk_unit = '" + unitId + "' ");
			}
			sql.append("   and appno.id = '" + this.peProApplyno.getId() + "'        ");
			sql.append(" group by sub.id, sub.name                                  ");

			List lst = this.getPeProApplyNoService().executeSQL(sql.toString());
			int total_persons = 0;
			if(lst !=null && lst.size()>0){
				for(int in = 0; in < lst.size(); in++){
					Object[] sub = (Object[])lst.get(in);
					int persons = Integer.parseInt(sub[2].toString().trim());
					total_persons += persons;
				}
			}
			
			String t = "{\"id\":\"" + peId + "\",\"cell\":[\"" + unitName + "\",\"" + name + "\",\"" + year+ "\",\"" + standand + "\",\"" + total_persons + "\",\"" + intime + "\",\"" + audTime + "\",\"" + statusName + "\"]}";

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
		}
	}

	public void changeStatus() {
		String method = request.getParameter("method");
		String id = request.getParameter("id");
		String[] ids = id.split(",");

		
//		Date nowTime = new Date(System.currentTimeMillis());
		for (String str : ids) {
			PeBudgetmpNew pbt = this.peBudgetmpNewService.get(str);
			pbt.setAud_date(new Date(System.currentTimeMillis()));
			if(method.equals("pass")){
				pbt.setFlag(1);
			}else if (method.equals("nopass")) {
				pbt.setFlag(2);;
			}
			this.peBudgetmpNewService.update(pbt);
			
//			if (method.equals("pass")) {
//				String sql = "UPDATE PEBUDGETMPNEW set FLAG = 1,AUD_DATE=to_date(sysdate,'yyyy-MM-dd') where id ='" + str + "' ";
//				this.peBudgetmpNewService.executeSQLUpdate(sql);
//			} else if (method.equals("nopass")) {
//				String sql = "UPDATE PEBUDGETMPNEW set FLAG = 2 where id ='" + str + "' ";
//				this.peBudgetmpNewService.executeSQLUpdate(sql);
//			}
		}

	}

	public String toDetail2() {

		String peId = request.getParameter("peId");

		this.peFeeBudget = this.getPeBudgetmpNewService().get(peId);

		PeBudgetmpNewDetail peFB = this.peFeeBudget.getPeFeeBudgetNewDetail();

//		this.setTotal(peFB.getFeeMeal() + peFB.getFeeAccommodation() + peFB.getFeeTeach() + peFB.getFeeTrafficExpert() + peFB.getFeeMealAccExpert() + peFB.getFeeMaterials() + peFB.getFeeAreaRent() + peFB.getFeeEquipmentRent() + peFB.getFeeSurvey() + peFB.getFeeResearch() + peFB.getFeeArgument()
//				+ peFB.getFeeTrafficStu() + peFB.getFeeElectronCourse() + peFB.getFeeMealExpert() + peFB.getFeeOfficeSupplies());

		DecimalFormat df1 = new DecimalFormat("#.00");
		double sum = peFB.getFeeMeal() + peFB.getFeeAccommodation() + peFB.getFeeTeach() + peFB.getFeeTrafficExpert() + peFB.getFeeMealAccExpert() + peFB.getFeeMaterials() + peFB.getFeeAreaRent() + peFB.getFeeEquipmentRent() + peFB.getFeeSurvey() + peFB.getFeeResearch() + peFB.getFeeArgument()
				+ peFB.getFeeTrafficStu() + peFB.getFeeElectronCourse() + peFB.getFeeMealExpert() + peFB.getFeeOfficeSupplies();
		this.setTotal(Double.parseDouble(df1.format(sum)));
		
		return "yusuan_print";
	}

	public String toMorePdf() throws DocumentException, IOException {

		String id = request.getParameter("id");
		String[] ids = id.split(",");

		String path = ServletActionContext.getServletContext().getRealPath("/").replace("\\", "/");
		String returnValue = "export/" + UUID.randomUUID().toString().replaceAll("-", "") + ".pdf";

		ByteArrayOutputStream[] baos = new ByteArrayOutputStream[ids.length];
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);

		FileOutputStream fos = new FileOutputStream(path + returnValue);

		for (int i = 0; i < ids.length; i++) {

			PeBudgetmpNew peBudgetmp = this.getPeBudgetmpNewService().get(ids[i]);
			PeBudgetmpNewDetail budgetDetal = peBudgetmp.getPeFeeBudgetNewDetail();

			String TemplatePDF = path + "export/budgetNew.pdf";

			baos[i] = new ByteArrayOutputStream();

			PdfReader reader = new PdfReader(TemplatePDF);
			PdfStamper stamp = new PdfStamper(reader, baos[i]);

			AcroFields form = stamp.getAcroFields();
			form.addSubstitutionFont(bfChinese);

			form.setField("projectName", peBudgetmp.getFk_pro_applyno().getName());
			form.setField("unitName", peBudgetmp.getUnitName() + "（公章）");
			form.setField("personCount", peBudgetmp.getPersonCount());
			String[] strTime = peBudgetmp.getTraining_data() == null ? new String[0] : peBudgetmp.getTraining_data().split(";");

			StringBuilder sb = new StringBuilder();
			String[] ast = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
			for (int j = 0; j < strTime.length; j++) {
				sb.append("第").append(ast[j + 1]).append("期：").append(strTime[j]).append("\r\n");
			}
			form.setField("trainingDate", sb.toString());

			if (budgetDetal != null) {
				form.setField("fee1", budgetDetal.getFeeMeal().toString());
				form.setField("fee2", budgetDetal.getFeeAccommodation().toString());
				form.setField("fee3", budgetDetal.getFeeTeach().toString());
				form.setField("fee4", budgetDetal.getFeeTrafficExpert().toString());
				form.setField("fee5", budgetDetal.getFeeMealAccExpert().toString());
				form.setField("fee6", budgetDetal.getFeeMaterials().toString());
				form.setField("fee7", budgetDetal.getFeeAreaRent().toString());
				form.setField("fee8", budgetDetal.getFeeEquipmentRent().toString());

				form.setField("fee10", budgetDetal.getFeeSurvey().toString());
				form.setField("fee11", budgetDetal.getFeeResearch().toString());
				form.setField("fee12", budgetDetal.getFeeArgument().toString());
				form.setField("fee13", budgetDetal.getFeeTrafficStu().toString());
				form.setField("fee14", budgetDetal.getFeeElectronCourse().toString());
				
				form.setField("fee15", budgetDetal.getFeeMealExpert().toString());
				form.setField("fee16", budgetDetal.getFeeOfficeSupplies().toString());

				String temp[] = budgetDetal.getNoteMeal().split("\\*");
				form.setField("note1", temp[0] + "元/人/天×" + temp[1] + "人×" + temp[2] + "天");
				temp = budgetDetal.getNoteAccommodation().split("\\*");
				form.setField("note2", temp[0] + "元/人/天×" + temp[1] + "人×" + temp[2] + "天");
				temp = budgetDetal.getNoteTeach().split("\\*");
				form.setField("note3", temp[0] + "元/次×" + temp[1] + "次");
				temp = budgetDetal.getNoteTrafficExpert().split("\\*");
				form.setField("note4", temp[0] + "元/人次×" + temp[1] + "人次");
				temp = budgetDetal.getNoteMealAccExpert().split("\\*");
				form.setField("note5", temp[0] + "元/（人•半天）×" + temp[1] + "人次");
				temp = budgetDetal.getNoteMaterials().split("\\*");
				form.setField("note6", temp[0] + "元/人次×" + temp[1] + "人次");
				temp = budgetDetal.getNoteAreaRent().split("\\*");
				form.setField("note7", temp[0] + "元/人次×" + temp[1] + "人次");
				temp = budgetDetal.getNoteEquipmentRent().split("\\*");
				form.setField("note8", temp[0] + "元/人×" + temp[1] + "人");

				temp = budgetDetal.getNoteSurvey().split("\\*");
				form.setField("note10", temp[0] + "元/件×" + temp[1] + "件");
				temp = budgetDetal.getNoteResearch().split("\\*");
				form.setField("note11", temp[0] + "元/班次/天×" + temp[1] + "班次×" + temp[2] + "天");
				temp = budgetDetal.getNoteArgument().split("\\*");
				form.setField("note12", temp[0] + "元/人×" + temp[1] + "人");
				temp = budgetDetal.getNoteTrafficStu().split("\\*");
				form.setField("note13", temp[0] + "元/班次/天×" + temp[1] + "班次×" + temp[2] + "天");

				temp = budgetDetal.getNoteElectronCourse().split("\\*");
				form.setField("note14", temp[0] + "元/人/天×" + temp[1] + "人×" + temp[2] + "天");
				temp = budgetDetal.getNoteMealExpert().split("\\*");
				form.setField("note15", temp[0] + "元/人次×" + temp[1] + "人次");
				temp = budgetDetal.getNoteOfficeSupplies().split("\\*");
				form.setField("note16",temp[0] + "元/人×" + temp[1] + "人");

				Double total = budgetDetal.getFeeMeal() + budgetDetal.getFeeAccommodation() + budgetDetal.getFeeTeach() + budgetDetal.getFeeTrafficExpert() + budgetDetal.getFeeMealAccExpert() + budgetDetal.getFeeMaterials() + budgetDetal.getFeeAreaRent() + budgetDetal.getFeeEquipmentRent()
						+ budgetDetal.getFeeSurvey() + budgetDetal.getFeeResearch() + budgetDetal.getFeeArgument() + budgetDetal.getFeeTrafficStu() + budgetDetal.getFeeElectronCourse() + budgetDetal.getFeeMealExpert() + budgetDetal.getFeeOfficeSupplies();
//				form.setField("total", total.toString());
				DecimalFormat df1 = new DecimalFormat("#.00");
				form.setField("total", df1.format(total));
			}

			form.setField("payeeName", peBudgetmp.getPayeeName());
			form.setField("openingBank", peBudgetmp.getOpeningBank());
			form.setField("accountNumber", peBudgetmp.getAccountNumber());
			form.setField("contactInfo", peBudgetmp.getContactInfo());

			stamp.setFormFlattening(true); // 千万不漏了这句啊, */
			stamp.close();

		}

		if (ids.length > 0) {
			Document doc = new Document();
			PdfCopy pdfCopy = new PdfCopy(doc, fos);
			doc.open();
			for (int i = 0; i < ids.length; i++) {
				PdfImportedPage impPage = pdfCopy.getImportedPage(new PdfReader(baos[i].toByteArray()), 1);
				pdfCopy.addPage(impPage);
			}
			doc.close();// 当文件拷贝 记得关闭doc
		}
		
		this.filePath = returnValue;

		return "actual_pdf";
	}

	public peProApplyServiceImpl getPeProApplyService() {
		return peProApplyService;
	}

	public void setPeProApplyService(peProApplyServiceImpl peProApplyService) {
		this.peProApplyService = peProApplyService;
	}

	public peProApplyNoServiceImpl getPeProApplyNoService() {
		return peProApplyNoService;
	}

	public void setPeProApplyNoService(peProApplyNoServiceImpl peProApplyNoService) {
		this.peProApplyNoService = peProApplyNoService;
	}


	public PeBudgetmpNewServiceImpl getPeBudgetmpNewService() {
		return peBudgetmpNewService;
	}

	public void setPeBudgetmpNewService(
			PeBudgetmpNewServiceImpl peBudgetmpNewService) {
		this.peBudgetmpNewService = peBudgetmpNewService;
	}

	public PeBudgetmpNewDetailServiceImpl getPeBudgetmpNewDetailService() {
		return peBudgetmpNewDetailService;
	}

	public void setPeBudgetmpNewDetailService(
			PeBudgetmpNewDetailServiceImpl peBudgetmpNewDetailService) {
		this.peBudgetmpNewDetailService = peBudgetmpNewDetailService;
	}

	public peProApplyNo getPeProApplyno() {
		return peProApplyno;
	}

	public void setPeProApplyno(peProApplyNo peProApplyno) {
		this.peProApplyno = peProApplyno;
	}


	public PeBudgetmpNew getPeFeeBudget() {
		return peFeeBudget;
	}

	public void setPeFeeBudget(PeBudgetmpNew peFeeBudget) {
		this.peFeeBudget = peFeeBudget;
	}

	@SuppressWarnings("rawtypes")
	public List getSubjectList() {
		return subjectList;
	}

	@SuppressWarnings("rawtypes")
	public void setSubjectList(List subjectList) {
		this.subjectList = subjectList;
	}

	public String getPraid() {
		return praid;
	}

	public void setPraid(String praid) {
		this.praid = praid;
	}

	public String getPbid() {
		return pbid;
	}

	public void setPbid(String pbid) {
		this.pbid = pbid;
	}

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public Double getTotal_item_1() {
		return total_item_1;
	}

	public void setTotal_item_1(Double total_item_1) {
		this.total_item_1 = total_item_1;
	}

	public Double getTotal_item_2() {
		return total_item_2;
	}

	public void setTotal_item_2(Double total_item_2) {
		this.total_item_2 = total_item_2;
	}

	public Double getTotal_item_3() {
		return total_item_3;
	}

	public void setTotal_item_3(Double total_item_3) {
		this.total_item_3 = total_item_3;
	}

	public Double getTotal_item_4() {
		return total_item_4;
	}

	public void setTotal_item_4(Double total_item_4) {
		this.total_item_4 = total_item_4;
	}

	public Double getTotal_item_5() {
		return total_item_5;
	}

	public void setTotal_item_5(Double total_item_5) {
		this.total_item_5 = total_item_5;
	}

	public Double getTotal_item_6() {
		return total_item_6;
	}

	public void setTotal_item_6(Double total_item_6) {
		this.total_item_6 = total_item_6;
	}

	public Double getTotal_item_7() {
		return total_item_7;
	}

	public void setTotal_item_7(Double total_item_7) {
		this.total_item_7 = total_item_7;
	}

	public Double getTotal_item_8() {
		return total_item_8;
	}

	public void setTotal_item_8(Double total_item_8) {
		this.total_item_8 = total_item_8;
	}

	public Double getTotal_item_9() {
		return total_item_9;
	}

	public void setTotal_item_9(Double total_item_9) {
		this.total_item_9 = total_item_9;
	}

	public Double getTotal_item_10() {
		return total_item_10;
	}

	public void setTotal_item_10(Double total_item_10) {
		this.total_item_10 = total_item_10;
	}

	public Double getTotal_item_11() {
		return total_item_11;
	}

	public void setTotal_item_11(Double total_item_11) {
		this.total_item_11 = total_item_11;
	}

	public Double getTotal_item_12() {
		return total_item_12;
	}

	public void setTotal_item_12(Double total_item_12) {
		this.total_item_12 = total_item_12;
	}

	public Double getTotal_item_13() {
		return total_item_13;
	}

	public void setTotal_item_13(Double total_item_13) {
		this.total_item_13 = total_item_13;
	}

	public Double getTotal_item_14() {
		return total_item_14;
	}

	public void setTotal_item_14(Double total_item_14) {
		this.total_item_14 = total_item_14;
	}

	public String getPrice_item_1() {
		return price_item_1;
	}

	public void setPrice_item_1(String price_item_1) {
		this.price_item_1 = price_item_1;
	}

	public String getPrice_item_2() {
		return price_item_2;
	}

	public void setPrice_item_2(String price_item_2) {
		this.price_item_2 = price_item_2;
	}

	public String getPrice_item_3() {
		return price_item_3;
	}

	public void setPrice_item_3(String price_item_3) {
		this.price_item_3 = price_item_3;
	}

	public String getPrice_item_4() {
		return price_item_4;
	}

	public void setPrice_item_4(String price_item_4) {
		this.price_item_4 = price_item_4;
	}

	public String getPrice_item_5() {
		return price_item_5;
	}

	public void setPrice_item_5(String price_item_5) {
		this.price_item_5 = price_item_5;
	}

	public String getPrice_item_6() {
		return price_item_6;
	}

	public void setPrice_item_6(String price_item_6) {
		this.price_item_6 = price_item_6;
	}

	public String getPrice_item_7() {
		return price_item_7;
	}

	public void setPrice_item_7(String price_item_7) {
		this.price_item_7 = price_item_7;
	}

	public String getPrice_item_8() {
		return price_item_8;
	}

	public void setPrice_item_8(String price_item_8) {
		this.price_item_8 = price_item_8;
	}

	public String getPrice_item_9() {
		return price_item_9;
	}

	public void setPrice_item_9(String price_item_9) {
		this.price_item_9 = price_item_9;
	}

	public String getPrice_item_10() {
		return price_item_10;
	}

	public void setPrice_item_10(String price_item_10) {
		this.price_item_10 = price_item_10;
	}

	public String getPrice_item_11() {
		return price_item_11;
	}

	public void setPrice_item_11(String price_item_11) {
		this.price_item_11 = price_item_11;
	}

	public String getPrice_item_12() {
		return price_item_12;
	}

	public void setPrice_item_12(String price_item_12) {
		this.price_item_12 = price_item_12;
	}

	public String getPrice_item_13() {
		return price_item_13;
	}

	public void setPrice_item_13(String price_item_13) {
		this.price_item_13 = price_item_13;
	}

	public String getPrice_item_14() {
		return price_item_14;
	}

	public void setPrice_item_14(String price_item_14) {
		this.price_item_14 = price_item_14;
	}

	public String getQty_item_1() {
		return qty_item_1;
	}

	public void setQty_item_1(String qty_item_1) {
		this.qty_item_1 = qty_item_1;
	}

	public String getQty_item_2() {
		return qty_item_2;
	}

	public void setQty_item_2(String qty_item_2) {
		this.qty_item_2 = qty_item_2;
	}

	public String getQty_item_3() {
		return qty_item_3;
	}

	public void setQty_item_3(String qty_item_3) {
		this.qty_item_3 = qty_item_3;
	}

	public String getQty_item_4() {
		return qty_item_4;
	}

	public void setQty_item_4(String qty_item_4) {
		this.qty_item_4 = qty_item_4;
	}

	public String getQty_item_5() {
		return qty_item_5;
	}

	public void setQty_item_5(String qty_item_5) {
		this.qty_item_5 = qty_item_5;
	}

	public String getQty_item_6() {
		return qty_item_6;
	}

	public void setQty_item_6(String qty_item_6) {
		this.qty_item_6 = qty_item_6;
	}

	public String getQty_item_7() {
		return qty_item_7;
	}

	public void setQty_item_7(String qty_item_7) {
		this.qty_item_7 = qty_item_7;
	}

	public String getQty_item_8() {
		return qty_item_8;
	}

	public void setQty_item_8(String qty_item_8) {
		this.qty_item_8 = qty_item_8;
	}

	public String getQty_item_9() {
		return qty_item_9;
	}

	public void setQty_item_9(String qty_item_9) {
		this.qty_item_9 = qty_item_9;
	}

	public String getQty_item_10() {
		return qty_item_10;
	}

	public void setQty_item_10(String qty_item_10) {
		this.qty_item_10 = qty_item_10;
	}

	public String getQty_item_11() {
		return qty_item_11;
	}

	public void setQty_item_11(String qty_item_11) {
		this.qty_item_11 = qty_item_11;
	}

	public String getQty_item_12() {
		return qty_item_12;
	}

	public void setQty_item_12(String qty_item_12) {
		this.qty_item_12 = qty_item_12;
	}

	public String getQty_item_13() {
		return qty_item_13;
	}

	public void setQty_item_13(String qty_item_13) {
		this.qty_item_13 = qty_item_13;
	}

	public String getQty_item_14() {
		return qty_item_14;
	}

	public void setQty_item_14(String qty_item_14) {
		this.qty_item_14 = qty_item_14;
	}

	public String getTime_item_1() {
		return time_item_1;
	}

	public void setTime_item_1(String time_item_1) {
		this.time_item_1 = time_item_1;
	}

	public String getTime_item_2() {
		return time_item_2;
	}

	public void setTime_item_2(String time_item_2) {
		this.time_item_2 = time_item_2;
	}

	public String getTime_item_3() {
		return time_item_3;
	}

	public void setTime_item_3(String time_item_3) {
		this.time_item_3 = time_item_3;
	}

	public String getTime_item_4() {
		return time_item_4;
	}

	public void setTime_item_4(String time_item_4) {
		this.time_item_4 = time_item_4;
	}

	public String getTime_item_5() {
		return time_item_5;
	}

	public void setTime_item_5(String time_item_5) {
		this.time_item_5 = time_item_5;
	}

	public String getTime_item_6() {
		return time_item_6;
	}

	public void setTime_item_6(String time_item_6) {
		this.time_item_6 = time_item_6;
	}

	public String getTime_item_7() {
		return time_item_7;
	}

	public void setTime_item_7(String time_item_7) {
		this.time_item_7 = time_item_7;
	}

	public String getTime_item_8() {
		return time_item_8;
	}

	public void setTime_item_8(String time_item_8) {
		this.time_item_8 = time_item_8;
	}

	public String getTime_item_9() {
		return time_item_9;
	}

	public void setTime_item_9(String time_item_9) {
		this.time_item_9 = time_item_9;
	}

	public String getTime_item_10() {
		return time_item_10;
	}

	public void setTime_item_10(String time_item_10) {
		this.time_item_10 = time_item_10;
	}

	public String getTime_item_11() {
		return time_item_11;
	}

	public void setTime_item_11(String time_item_11) {
		this.time_item_11 = time_item_11;
	}

	public String getTime_item_12() {
		return time_item_12;
	}

	public void setTime_item_12(String time_item_12) {
		this.time_item_12 = time_item_12;
	}

	public String getTime_item_13() {
		return time_item_13;
	}

	public void setTime_item_13(String time_item_13) {
		this.time_item_13 = time_item_13;
	}

	public String getTime_item_14() {
		return time_item_14;
	}

	public void setTime_item_14(String time_item_14) {
		this.time_item_14 = time_item_14;
	}

	public String getTotal_person_1() {
		return total_person_1;
	}

	public void setTotal_person_1(String total_person_1) {
		this.total_person_1 = total_person_1;
	}

	public String[] getTimearray() {
		return timearray;
	}

	public void setTimearray(String[] timearray) {
		this.timearray = timearray;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Double getTotal_item_15() {
		return total_item_15;
	}

	public void setTotal_item_15(Double total_item_15) {
		this.total_item_15 = total_item_15;
	}

	public Double getTotal_item_16() {
		return total_item_16;
	}

	public void setTotal_item_16(Double total_item_16) {
		this.total_item_16 = total_item_16;
	}

	public String getPrice_item_15() {
		return price_item_15;
	}

	public void setPrice_item_15(String price_item_15) {
		this.price_item_15 = price_item_15;
	}

	public String getPrice_item_16() {
		return price_item_16;
	}

	public void setPrice_item_16(String price_item_16) {
		this.price_item_16 = price_item_16;
	}

	public String getQty_item_15() {
		return qty_item_15;
	}

	public void setQty_item_15(String qty_item_15) {
		this.qty_item_15 = qty_item_15;
	}

	public String getQty_item_16() {
		return qty_item_16;
	}

	public void setQty_item_16(String qty_item_16) {
		this.qty_item_16 = qty_item_16;
	}

	public String getTime_item_15() {
		return time_item_15;
	}

	public void setTime_item_15(String time_item_15) {
		this.time_item_15 = time_item_15;
	}

	public String getTime_item_16() {
		return time_item_16;
	}

	public void setTime_item_16(String time_item_16) {
		this.time_item_16 = time_item_16;
	}

}
