package com.zeppin.action;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.zeppin.model.peProApply;
import com.zeppin.model.peProApplyNo;
import com.zeppin.model.proChildApply;
import com.zeppin.model.proChildApplyNo;
import com.zeppin.service.impl.peProApplyNoServiceImpl;
import com.zeppin.service.impl.peProApplyServiceImpl;
import com.zeppin.service.impl.proChildApplyNoServiceImpl;
import com.zeppin.service.impl.proChildApplyServiceImpl;

@Controller("doExcelAction")
@Scope("prototype")
public class doExcelAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private peProApplyNoServiceImpl peProApplynoService;

	@Autowired
	private peProApplyServiceImpl peProApplyService;

	@Autowired
	private proChildApplyNoServiceImpl proChildApplyNoService;

	@Autowired
	private proChildApplyServiceImpl proChildApplyService;

	private File upfile;
	private String proviceId;

	public String getProviceId() {
		return proviceId;
	}

	public void setProviceId(String proviceId) {
		this.proviceId = proviceId;
	}

	public File getUpfile() {
		return upfile;
	}

	public void setUpfile(File upfile) {
		this.upfile = upfile;
	}

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	public doExcelAction() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	public void getProvince() {
		String sql = "select t.id,t.name from pe_province t ";
		List li = peProApplynoService.executeSQL(sql);

		String tmp = "";

		for (int i = 0; i < li.size(); i++) {

			Object[] obj = (Object[]) li.get(i);
			String id = obj[0].toString();
			String name = obj[1].toString();
			String t = "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			tmp += t + ",";

		}
		if (tmp.length() > 0) {
			tmp = tmp.substring(0, tmp.length() - 1);
		}

		String retJson = "[" + tmp + "]";

		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(retJson);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void checkExcel() {

		try {

			FileInputStream fs = new FileInputStream(getUpfile());
			HSSFWorkbook wb = new HSSFWorkbook(fs);

			String parentId = "";
			String parentCode = "";
			String parentYear = "";

			HSSFSheet sheet = wb.getSheetAt(0); // sheet工作目录

			int rows = sheet.getPhysicalNumberOfRows();

			// 判断父项目是否存在，存在拿出id
			HSSFRow pro = sheet.getRow(0); // 父项目 名称
			String pname = pro.getCell(0).getStringCellValue();
			String sql = "select t.id,t.code,t.year from pe_pro_applyno t where t.name like '" + pname + "'";
			List li = peProApplynoService.executeSQL(sql);
			if (li == null || li.size() == 0) {

				try {
					response.setCharacterEncoding("UTF-8");
					response.setHeader("Cache-Control", "no-cache");
					response.getWriter().write("不存在中西部大项目:" + pname);

				} catch (Exception e) {
					e.printStackTrace();
				}
				return;

			} else {
				Object[] obj = (Object[]) li.get(0);
				parentId = obj[0].toString();
				parentCode = obj[1].toString();
				parentYear = obj[2].toString();
			}

			Hashtable<String, String[]> classroom = new Hashtable<String, String[]>();
			DecimalFormat df = new DecimalFormat("#");

			String errunit = "不存在培训单位:";
			String errsub = "不存在学科:";
			String infoPro = "添加子项目:";

			for (int ro = 2; ro < rows; ro++) {
				HSSFRow row = sheet.getRow(ro);
				if (row == null) {
					continue;
				}
				// 每个学员的信息

				String unitName = row.getCell(0).getStringCellValue().trim();
				String cpro = row.getCell(1).getStringCellValue().trim();
				String subName = "";
				HSSFCell cell = row.getCell(2);
				if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					subName = df.format(cell.getNumericCellValue());
				} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
					subName = cell.getStringCellValue();
				}

				String roomName = cpro + "_" + unitName + "_" + subName;

				if (!classroom.containsKey(roomName)) {
					if (!roomName.equals("__")) {
						String[] r = new String[3];
						r[0] = cpro;
						r[1] = unitName;
						r[2] = subName;
						classroom.put(roomName, r);
					}
				}

			}

			// 检测 学科 培训单位
			Enumeration et = classroom.keys();
			while (et.hasMoreElements()) {

				String key = et.nextElement().toString();
				String[] vl = classroom.get(key);

				String p = vl[0];
				String u = vl[1];
				String s = vl[2];

				// 获取学校id和学科id
				sql = "select t.id from PE_SUBJECT t where t.name like'%" + s + "%'";
				List sli = peProApplynoService.executeSQL(sql);
				if (sli == null || sli.size() == 0) {

					if (errsub.indexOf(s) < 0) {
						errsub += s + "  ";
					}
					continue;
				}

				// 培训单位
				sql = "select t.id from PE_UNIT t where t.fk_unit_type='ff8080812b493236012b50bfcb6e02cd'  and t.fk_province='" + proviceId
						+ "' and t.name like'%" + u + "%'";
				List uli = peProApplynoService.executeSQL(sql);
				if (uli == null || uli.size() == 0) {
					if (errunit.indexOf(u) < 0) {
						errunit += u + "  ";
					}
					continue;
				}
				// 查看子项目的个数
				sql = "select count(*) from PROCHILDAPPLYNO t  where t.parentid='" + parentId + "'";
				List countLi = peProApplynoService.executeSQL(sql);
				int codeFlag = 0;
				if (countLi == null || countLi.size() == 0) {
					codeFlag = 1;
				} else {
					codeFlag = Integer.parseInt(countLi.get(0).toString()) + 1;
				}

				// 判断子项目是否存在。
				sql = "select t.id from PROCHILDAPPLYNO t where t.parentid='" + parentId + "' and t.provinceid ='" + proviceId
						+ "' and t.name like '" + p + "'";

				List cli = peProApplynoService.executeSQL(sql);

				if (cli == null || cli.size() == 0) {
					// 不存在子项目
					// 创建项目
					UUID uuid = UUID.randomUUID();
					String vid = uuid.toString().replaceAll("-", "");

					proChildApplyNo pcapp = new proChildApplyNo();
					pcapp.setId(vid);
					pcapp.setName(p);
					pcapp.setCode(codeFlag + "");
					pcapp.setYear(parentYear);
					pcapp.setFkProgramType("402880962a63e21f012a6402c5000001");
					pcapp.setSelectFlag(2);
					pcapp.setPlevel(1);
					pcapp.setFkProgramStatus("402880962a9da820012a9dd88b710001");
					pcapp.setFkProvinceCheck("402880962a607c3a012a615ad5a50019");
					pcapp.setParentid(parentId);
					pcapp.setParentName(pname);
					pcapp.setProvice(proviceId);

					proChildApplyNoService.add(pcapp);

					infoPro += p + "  ";
				}

			}

			try {
				String s = errunit + "\r\n" + errsub + "\r\n" + infoPro;
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("" + s);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			try {

				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("error");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void importExcel() {

		try {

			// 导入excel
			// 1.先分析数据
			// 判断哪数据存在问题
			// 主要是判断哪些培训单位
			// 2. 分析出学科等信息

			DecimalFormat df = new DecimalFormat("#");

			FileInputStream fs = new FileInputStream(getUpfile());
			HSSFWorkbook wb = new HSSFWorkbook(fs);

			String parentId = "";
			String parentCode = "";
			String parentYear = "";

			HSSFSheet sheet = wb.getSheetAt(0); // sheet工作目录

			int rows = sheet.getPhysicalNumberOfRows();

			// 判断父项目是否存在，存在拿出id
			HSSFRow pro = sheet.getRow(0); // 父项目 名称
			String pname = pro.getCell(0).getStringCellValue();
			String sql = "select t.id,t.code,t.year from pe_pro_applyno t where t.name like '" + pname + "'";
			List li = peProApplynoService.executeSQL(sql);
			if (li == null || li.size() == 0) {
				System.out.println("父项目不存在");
				return;

			} else {
				Object[] obj = (Object[]) li.get(0);
				parentId = obj[0].toString();
				parentCode = obj[1].toString();
				parentYear = obj[2].toString();
			}

			// 从第二行开始 是学员信息

			// 班级信息 ==》 项目名称+"_"+培训单位+"_"+学科
			Hashtable<String, List<String[]>> classroom = new Hashtable<String, List<String[]>>();
			for (int r = 2; r < rows; r++) {
				HSSFRow row = sheet.getRow(r);
				if (row == null) {
					continue;
				}
				// 每个学员的信息

				String unitName = row.getCell(0).getStringCellValue().trim();
				String cpro = row.getCell(1).getStringCellValue().trim();
				String subName = "";
				try {

					HSSFCell cell = row.getCell(2);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							subName = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							subName = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				String roomName = cpro + "_" + unitName + "_" + subName;

				if (roomName.equals("__")) {
					continue;
				}

				String dipCode = "";
				try {
					HSSFCell cell = row.getCell(3);
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
					HSSFCell cell = row.getCell(4);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							stuName = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							stuName = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(e);
				}
				String workPlace = "";

				try {
					HSSFCell cell = row.getCell(5);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							workPlace = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							workPlace = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(e);
					System.out.println(r);
				}
				String zhiwu = "";

				try {
					HSSFCell cell = row.getCell(6);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							zhiwu = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							zhiwu = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(e);
					System.out.println(r);
				}
				String zhicheng = "";

				try {
					HSSFCell cell = row.getCell(8);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							zhicheng = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							zhicheng = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(e);
					System.out.println(r);
				}

				String dianhua = "";
				try {
					HSSFCell cell = row.getCell(8);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							dianhua = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							dianhua = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(r);
					System.out.println(e);
				}
				String phone = "";
				try {
					HSSFCell cell = row.getCell(9);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							phone = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							phone = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(r);
					System.out.println(e);
				}
				String mail = "";

				try {
					HSSFCell cell = row.getCell(10);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							mail = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							mail = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(r);
					System.out.println(e);
				}
				String beizhu1 = "";
				try {
					HSSFCell cell = row.getCell(11);
					if (cell != null) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							beizhu1 = df.format(cell.getNumericCellValue());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							beizhu1 = cell.getStringCellValue();
						}
					}
				} catch (Exception e) {
					System.out.println(r);
					System.out.println(e);
				}

				String[] people = new String[9];
				people[0] = dipCode;
				people[1] = stuName;
				people[2] = workPlace;
				people[3] = zhiwu;
				people[4] = zhicheng;
				people[5] = dianhua;
				people[6] = phone;
				people[7] = mail;
				people[8] = beizhu1;

				if (classroom.containsKey(roomName)) {
					classroom.get(roomName).add(people);
				} else {
					List<String[]> htli = new ArrayList<String[]>();
					htli.add(people);
					classroom.put(roomName, htli);

				}

			}

			// 开始处理班级以及学员等信息
			Enumeration et = classroom.keys();
			int total = 0;
			int fail = 0;
			int success = 0;
			String errinfo = "插入失败数据:";

			while (et.hasMoreElements()) {
				String key = et.nextElement().toString().trim();
				List<String[]> htli = classroom.get(key);
				total += htli.size();

				// 开始分析班级信息
				String[] roomName = key.split("_");
				String childProject = roomName[0];
				String unitName = roomName[1];
				String subjectName = roomName[2];

				String subjectId = "";
				String unitId = "";
				String childProjectId = "";

				// 获取学校id和学科id
				sql = "select t.id from PE_SUBJECT t where t.name like'%" + subjectName + "%'";
				List sli = peProApplynoService.executeSQL(sql);
				if (sli == null || sli.size() == 0) {
					// 没有 这个学科
					continue;
				} else {
					subjectId = sli.get(0).toString();
				}

				// 培训单位
				sql = "select t.id from PE_UNIT t where t.fk_unit_type='ff8080812b493236012b50bfcb6e02cd'  and t.fk_province='" + proviceId
						+ "' and t.name like'%" + unitName + "%'";
				List uli = peProApplynoService.executeSQL(sql);
				if (uli == null || uli.size() == 0) {
					continue;
				} else {
					unitId = uli.get(0).toString();
				}

				// 查看子项目的个数
				sql = "select count(*) from PROCHILDAPPLYNO t  where t.parentid='" + parentId + "'";
				List countLi = peProApplynoService.executeSQL(sql);
				int codeFlag = 0;
				if (countLi == null || countLi.size() == 0) {
					codeFlag = 1;
				} else {
					codeFlag = Integer.parseInt(countLi.get(0).toString()) + 1;
				}

				// 判断子项目是否存在。
				sql = "select t.id from PROCHILDAPPLYNO t where t.parentid='" + parentId + "' and t.provinceid ='" + proviceId
						+ "' and t.name like '" + childProject + "'";

				List cli = peProApplynoService.executeSQL(sql);

				if (cli == null || cli.size() == 0) {
					UUID uuid = UUID.randomUUID();
					String vid = uuid.toString().replaceAll("-", "");

					proChildApplyNo pcapp = new proChildApplyNo();
					pcapp.setId(vid);
					pcapp.setName(childProject);
					pcapp.setCode(codeFlag + "");
					pcapp.setYear(parentYear);
					pcapp.setFkProgramType("402880962a63e21f012a6402c5000001");
					pcapp.setSelectFlag(2);
					pcapp.setPlevel(1);
					pcapp.setFkProgramStatus("402880962a9da820012a9dd88b710001");
					pcapp.setFkProvinceCheck("402880962a607c3a012a615ad5a50019");
					pcapp.setParentid(parentId);
					pcapp.setParentName(pname);
					pcapp.setProvice(proviceId);

					proChildApplyNoService.add(pcapp);

					childProjectId = vid;

				} else {
					// 项目存在
					childProjectId = cli.get(0).toString();
				}

				// 判断 是否有这个班级
				sql = "select count(*) from PROCHILDAPPLY t where t.fk_unit='" + unitId + "' and t.fk_subject='" + subjectId + "' and t.fk_applyno='"
						+ childProjectId + "'";
				List applyList = peProApplynoService.executeSQL(sql);

				// 是否存在
				boolean applyExist = false;

				if (applyList == null || applyList.size() == 0) {
					applyExist = false;
				} else {

					if (applyList.get(0).toString().equals("0")) {
						applyExist = false;
					} else {
						applyExist = true;
					}
				}

				if (!applyExist) {
					// 添加apply
					UUID uuid = UUID.randomUUID();
					String vid = uuid.toString().replaceAll("-", "");
					proChildApply peapply = new proChildApply();
					peapply.setId(vid);
					peapply.setUnitId(unitId);
					peapply.setSubjectId(subjectId);
					peapply.setFkApplyNo(childProjectId);
					proChildApplyService.add(peapply);
				}

				// 添加学员

				for (int i = 0; i < htli.size(); i++) {

					String[] val = htli.get(i);
					String dipCode = val[0].trim();
					String stuName = val[1].trim();
					String workPlace = val[2].trim();
					String zhiwu = val[3];
					String zhicheng = val[4];
					String dianhua = val[5];
					String phone = val[6];
					String mail = val[7];
					String beizhu1 = val[8];

					try {

						UUID uuid = UUID.randomUUID();
						String vid = uuid.toString().replaceAll("-", "");

						// 插入 pe_trainee
						sql = "insert into TRAINEE(id,name,fk_unit_from,telephone,email,office_phone,FK_GRADUTED,"
								+ "FK_SUBJECT,FK_PROVINCE,WORK_PLACE,ZHIWU,FK_TRAINING_UNIT," + "FK_APPLYNO,ZHICHENG,DIPCODE,beizhu1) values(";
						sql += "'" + vid + "','" + stuName + "','402880142a103b95012a103edcdd0001','" + phone + "','" + mail + "','" + dianhua
								+ "','isgraduateidflag2','" + subjectId + "','" + proviceId + "','" + workPlace + "','" + zhiwu + "','" + unitId
								+ "','" + childProjectId + "','" + zhicheng + "','" + dipCode + "','" + beizhu1 + "')";

						peProApplyService.executeSQLUpdate(sql);

						success += 1;

					} catch (Exception e) {
						fail += 1;
						errinfo += stuName + ":" + phone + "  ";
						e.printStackTrace();
					}

				}

			}

			try {
				String s = "总条数:" + total + "\r\n" + "成功条数:" + success + "\r\n" + "失败条数:" + fail + "\r\n" + errinfo;
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("" + s);

			} catch (Exception e) {
				System.out.println(e);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
