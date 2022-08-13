package cn.zeppin.action.admin;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.service.ICountTeacherService;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 教师基本信息大数据统计
 * 
 * @author geng
 *
 */
@SuppressWarnings("unchecked")
public class CountTeacherAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	// service
	private ICountTeacherService iCountTeacherService;

	private void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	/**
	 * 总教师人数
	 */
	public void initCount() {
		initServlert();
		String id =getParameId();
		StringBuilder sb = new StringBuilder();
		sb.append(getCountByYear(id));
		responseJsonToHtml(sb);
	}

	/**
	 * 教师基本情况统计
	 */
	public void initBaseInfo() {
		initServlert();
		String id =getParameId();
		// 获取当前年的数据统计
		Calendar a = Calendar.getInstance();
		String currentYear = a.get(Calendar.YEAR) + "";

		StringBuilder sb = new StringBuilder();
		sb.append(getCountBySex(id, currentYear));
		sb.append(",");
		sb.append(getCountByAge(id, currentYear));
		sb.append(",");
		sb.append(getCountByTeachingAge(id, currentYear));
		sb.append(",");
		sb.append(getCountBySchoolType(id, currentYear));
		sb.append(",");
		sb.append(getCountByJopTitle(id, currentYear));
		sb.append(",");
		sb.append(getCountByPolitice(id, currentYear));
		responseJsonToHtml(sb);
	}

	/**
	 * 教学信息统计
	 */
	public void initTeachingInfo() {
		initServlert();
		String id =getParameId();
		// 获取当前年的数据统计
		Calendar a = Calendar.getInstance();
		String currentYear = a.get(Calendar.YEAR) + "";
		StringBuilder sb = new StringBuilder();

		sb.append(getCountByMutiLanguage(id, currentYear));
		sb.append(",");
		sb.append(getCountByTeachingLanguage(id, currentYear));
		sb.append(",");
		sb.append(getCountByTeachingGrade(id, currentYear));
		sb.append(",");
		sb.append(getCountByTeachingSubject(id, currentYear));
		responseJsonToHtml(sb);
	}

	/**
	 * 所在地区统计
	 */
	public void initAddress() {
		initServlert();
		String id =getParameId();
		// 获取当前年的数据统计
		Calendar a = Calendar.getInstance();
		String currentYear = a.get(Calendar.YEAR) + "";
		StringBuilder sb = new StringBuilder();
		sb.append(getCountByAttribute(id, currentYear));
		sb.append(",");
		sb.append(getCountByTeacherAddress(id, currentYear));
		responseJsonToHtml(sb);
	}

	private String getParameId() {
		String id;
		UserSession us = (UserSession) session.getAttribute("usersession");
		try {
			id = request.getParameter("id") == null ? "" : request.getParameter("id");
			if (id.equals("")) {
				id = us.getOrganizationScode();
			}
		} catch (Exception e) {
			id = us.getOrganizationScode();
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * 提交json到html中
	 * 
	 * @param builder
	 *            不同情况 拼接的json
	 */
	private void responseJsonToHtml(StringBuilder builder) {
		StringBuilder countBuilder = new StringBuilder();
		countBuilder.append("{");
		countBuilder.append("\"status\":\"OK\"");
		countBuilder.append(",");
		countBuilder.append("\"records\":{");
		countBuilder.append(builder);
		countBuilder.append("}");
		countBuilder.append("}");

		Utlity.ResponseWrite(countBuilder.toString(), "application/json",
				response);
	}

	/**
	 * 根据年计算在岗教师数
	 * 
	 * @param year
	 */
	private StringBuilder getCountByYear(String id) {
		Calendar a = Calendar.getInstance();
		int year = a.get(Calendar.YEAR);
		int currentYearCount = 0;
		int lastYearCount = 0;
		StringBuilder sb = new StringBuilder();
		List<Object[]> list = iCountTeacherService.countByCurrentYear(id, year);
		List<Object[]> lastYearList = iCountTeacherService.countByLastYear(id,
				year - 1);
		sb.append("\"teacherCount\":{");
		if (list != null && list.size() > 0) {
			currentYearCount = Integer.parseInt(list.get(0) + "");
			sb.append("\"currentYearCount\":\"" + currentYearCount + "\",");
		}
		if (lastYearList != null && lastYearList.size() > 0) {
			lastYearCount = Integer.parseInt(lastYearList.get(0) + "");
			sb.append("\"lastYearCount\":\"" + lastYearCount + "\",");
		}
		// 创建一个数值格式化对象
		NumberFormat numberFormat = NumberFormat.getInstance();
		// 设置精确到小数点后2位
		numberFormat.setMaximumFractionDigits(2);
		String result;
		if (currentYearCount != 0) {
			float count = (1 - (float) lastYearCount / (float) currentYearCount) * 100;
			if (count >= 0) {
				result = "+" + numberFormat.format(count);
			} else {
				String format = numberFormat.format(count);
				if (format.equals("-0")) {
					result = numberFormat.format(count);
				} else {
					result = "-" + numberFormat.format(count);
				}
			}
		} else {
			result = "0";
		}
		sb.append("\"percentage\":\"" + result + "\"");
		sb.append("}");
		return sb;
	}

	/**
	 * 性别统计
	 */
	private StringBuilder getCountBySex(String id, String currentYear) {
		StringBuilder sb = new StringBuilder();
		List<Object[]> list = iCountTeacherService.countBySex(id, currentYear);
		sb.append("\"sex\":[");
		if (list != null && list.size() > 0) {
			for (Object[] objects : list) {
				String sex = objects[0].toString();
				sb.append("{");
				sb.append("\"name\":\"" + (sex.equals("2") ? "女" : "男") + "\",");
				sb.append("\"count\":\"" + objects[1] + "\"");
				sb.append("},");
			}
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append("]");
		return sb;
	}

	/**
	 * 年龄统计
	 */
	private StringBuilder getCountByAge(String id, String currentYear) {
		StringBuilder sb = new StringBuilder();
		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		int count4 = 0;
		int countOther = 0;
		int age = 0;
		int ageYear;
		int count;

		List<Object[]> list = iCountTeacherService.countByAge(id, currentYear);
		sb.append("\"age\":[");
		if (list != null && list.size() > 0) {
			int cYear = Integer.parseInt(currentYear);
			for (Object[] objects : list) {
				ageYear = Integer.parseInt((String) objects[0]);
				count = Integer.parseInt(objects[1].toString());
				age = cYear - ageYear;
				if (age >= 21 && age <= 30) {
					count1 += count;
				} else if (age >= 31 && age <= 40) {
					count2 += count;
				} else if (age >= 41 && age <= 50) {
					count3 += count;
				} else if (age >= 51 && age <= 60) {
					count4 += count;
				} else {
					countOther += count;
				}
			}
			sb.append("{");
			sb.append("\"name\":\"21-30岁 \",");
			sb.append("\"count\":\"" + count1 + "\",");
			sb.append("},");
			sb.append("{");
			sb.append("\"name\":\"31-40岁 \",");
			sb.append("\"count\":\"" + count2 + "\"");
			sb.append("},");
			sb.append("{");
			sb.append("\"name\":\"41-50岁 \",");
			sb.append("\"count\":\"" + count3 + "\"");
			sb.append("},");
			sb.append("{");
			sb.append("\"name\":\"51-60岁 \",");
			sb.append("\"count\":\"" + count4 + "\"");
			sb.append("},");
			sb.append("{");
			sb.append("\"name\":\"其他 \",");
			sb.append("\"count\":\"" + countOther + "\"");
			sb.append("}");
		}

		sb.append("]");
		return sb;
	}

	/**
	 * 教龄统计
	 */
	private StringBuilder getCountByTeachingAge(String id, String currentYear) {
		StringBuilder sb = new StringBuilder();
		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		int count4 = 0;
		int count;
		int countOther = 0;
		int teachingAge = 0;
		int teachingAgeYear;
		List<Object[]> list = iCountTeacherService.countByTeachingAge(id,
				currentYear);
		sb.append("\"teachingAge\":[");
		if (list != null && list.size() > 0) {
			int cYear = Integer.parseInt(currentYear);
			for (Object[] objects : list) {
				teachingAgeYear = Integer.parseInt((String) objects[0]);
				count = Integer.parseInt(objects[1].toString());
				teachingAge = cYear - teachingAgeYear;
				if (teachingAge >= 0 && teachingAge <= 1) {
					count1 += count;
				} else if (teachingAge >= 2 && teachingAge <= 5) {
					count2 += count;
				} else if (teachingAge >= 6 && teachingAge <= 10) {
					count3 += count;
				} else if (teachingAge >= 11 && teachingAge <= 20) {
					count4 += count;
				} else {
					countOther += count;
				}
			}
			sb.append("{");
			sb.append("\"name\":\"0-1年 \",");
			sb.append("\"count\":\"" + count1 + "\",");
			sb.append("},");
			sb.append("{");
			sb.append("\"name\":\"2-5年 \",");
			sb.append("\"count\":\"" + count2 + "\"");
			sb.append("},");
			sb.append("{");
			sb.append("\"name\":\"6-10年 \",");
			sb.append("\"count\":\"" + count3 + "\"");
			sb.append("},");
			sb.append("{");
			sb.append("\"name\":\"11-20年 \",");
			sb.append("\"count\":\"" + count4 + "\"");
			sb.append("},");
			sb.append("{");
			sb.append("\"name\":\"20年以上 \",");
			sb.append("\"count\":\"" + countOther + "\"");
			sb.append("}");
		}

		sb.append("]");
		return sb;
	}

	/**
	 * 在岗教师学校举办者类型统计
	 */
	private StringBuilder getCountBySchoolType(String id, String currentYear) {
		StringBuilder sb = new StringBuilder();
		List<Object[]> list = iCountTeacherService.countBySchoolType(id,
				currentYear);
		sb.append("\"schoolType\":[");
		if (list != null && list.size() > 0) {
			for (Object[] objects : list) {
				sb.append("{");
				sb.append("\"name\":\""
						+ (objects[0].equals("") ? "其他" : objects[0]) + "\",");
				sb.append("\"schoolCount\":\"" + objects[1] + "\",");
				sb.append("\"teacherCount\":\"" + objects[2] + "\"");
				sb.append("},");
			}
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append("]");
		return sb;
	}

	/**
	 * 职称统计
	 */
	private StringBuilder getCountByJopTitle(String id, String currentYear) {
		StringBuilder sb = new StringBuilder();
		List<Object[]> list = iCountTeacherService.countByJopTitle(id,
				currentYear);
		sb.append("\"jobTitle\":[");
		if (list != null && list.size() > 0) {
			for (Object[] objects : list) {
				sb.append("{");
				sb.append("\"name\":\"" + objects[0] + "\",");
				sb.append("\"count\":\"" + objects[1] + "\"");
				sb.append("},");
			}
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append("]");
		return sb;

	}

	/**
	 * 政治面貌
	 */
	private StringBuilder getCountByPolitice(String id, String currentYear) {
		StringBuilder sb = new StringBuilder();
		List<Object[]> list = iCountTeacherService.countByPolitice(id,
				currentYear);
		sb.append("\"politice\":[");
		if (list != null && list.size() > 0) {
			for (Object[] objects : list) {
				sb.append("{");
				sb.append("\"name\":\"" + objects[0] + "\",");
				sb.append("\"count\":\"" + objects[1] + "\"");
				sb.append("},");
			}
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append("]");
		return sb;
	}

	// /////////////////////////////////////////

	/**
	 * 双语教师
	 */
	private StringBuilder getCountByMutiLanguage(String id, String currentYear) {
		StringBuilder sb = new StringBuilder();
		List<String> list = iCountTeacherService.countByIsMutiLanguage(id,
				currentYear);
		sb.append("\"mutiLanguage\":[");
		if (list != null && list.size() > 0) {
			sb.append("{");
			sb.append("\"name\":\"双语 \",");
			sb.append("\"count\":\"" + list.get(0) + "\"");
			sb.append("},");
			sb.append("{");
			sb.append("\"name\":\"汉语 \",");
			sb.append("\"count\":\"" + list.get(1) + "\"");
			sb.append("},");
			sb.append("{");
			sb.append("\"name\":\"非汉语 \",");
			sb.append("\"count\":\"" + list.get(2) + "\"");
			sb.append("}");
		}
		sb.append("]");
		return sb;
	}

	/**
	 * 主要教学语言
	 */
	private StringBuilder getCountByTeachingLanguage(String id,
			String currentYear) {
		int count = 0;
		StringBuilder sb = new StringBuilder();
		List<Object[]> list = iCountTeacherService.countByTeachingLanguage(id,
				currentYear);
		sb.append("\"teachingLanguage\":[");
		if (list != null && list.size() > 0) {
			for (Object[] objects : list) {
				sb.append("{");
				if (objects[0] == null) {
					sb.append("\"name\":\"其他\",");
				} else {
					sb.append("\"name\":\"" + objects[0] + "\",");
				}
				sb.append("\"count\":\"" + objects[1] + "\"");
				sb.append("},");
				count += Integer.parseInt(objects[1].toString());
			}
		}
		sb.append("]");
		return sb;
	}

	/**
	 * 主要教学学段
	 */
	private StringBuilder getCountByTeachingGrade(String id, String currentYear) {
		int count = 0;
		StringBuilder sb = new StringBuilder();
		List<Object[]> list = iCountTeacherService.countByTeachingGrade(id,
				currentYear);
		sb.append("\"teachingGrade\":[");
		if (list != null && list.size() > 0) {
			for (Object[] objects : list) {
				sb.append("{");
				if (objects[0] == null) {
					sb.append("\"name\":\"其他\",");
				} else {
					sb.append("\"name\":\"" + objects[0] + "\",");
				}
				sb.append("\"count\":\"" + objects[1] + "\"");
				sb.append("},");
				count += Integer.parseInt(objects[1].toString());
			}
		}
		sb.append("]");
		return sb;
	}

	/**
	 * 主要教学学科
	 */
	private StringBuilder getCountByTeachingSubject(String id,
			String currentYear) {
		int count = 0;
		StringBuilder sb = new StringBuilder();
		List<Object[]> list = iCountTeacherService.countByTeachingSubject(id,
				currentYear);
		sb.append("\"teachingSubject\":[");
		if (list != null && list.size() > 0) {
			for (Object[] objects : list) {
				sb.append("{");
				if (objects[0] == null) {
					sb.append("\"name\":\"其他\",");
				} else {
					sb.append("\"name\":\"" + objects[0] + "\",");
				}
				sb.append("\"count\":\"" + objects[1] + "\"");
				sb.append("},");
				count += Integer.parseInt(objects[1].toString());
			}
		}
		sb.append("]");
		return sb;
	}

	/**
	 * 教师所在地区下属单位
	 * 
	 * @param id
	 * @return
	 */
	private StringBuilder getCountByTeacherAddress(String id, String currentYear) {
		StringBuilder sb = new StringBuilder();
		List<Object[]> list = iCountTeacherService.countByTeacherAddress(id,
				currentYear + "");
		sb.append("\"teacherAddress\":[");
		if (list != null && list.size() > 0) {
			for (Object[] objects : list) {
				sb.append("{");
				sb.append("\"name\":\"" + objects[0] + "\",");
				sb.append("\"count\":\"" + objects[1] + "\"");
				sb.append("},");
			}
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append("]");
		return sb;
	}

	/**
	 * 教师所在地区类型下属单位
	 * 
	 * @return
	 */
	public StringBuilder getCountByAttribute(String id, String currentYear) {
		String name = null;
		StringBuilder sb = new StringBuilder();
		List<Object[]> list = iCountTeacherService.countByAttribute(id,
				currentYear + "");
		sb.append("\"teacherAddressType\":[");
		if (list != null && list.size() > 0) {
			for (Object[] objects : list) {
				sb.append("{");
				if (objects[0] == null || objects[0].equals("")) {
					name = "无";
				} else {
					String value = objects[0].toString();
//					if (value == null || value.equals("") || value.equals("0")) {
//						name = "无";
//					} else if (value.equals("1")) {
//						name = "城市";
//					} else if (value.equals("2")) {
//						name = "县城";
//					} else if (value.equals("3")) {
//						name = "镇区";
//					} else if (value.equals("4")) {
//						name = "乡";
//					} else if (value.equals("5")) {
//						name = "村";
//					} else if (value.equals("6")) {
//						name = "教学点";
//					}
					name=value;
				}
				sb.append("\"name\":\"" + name + "\",");
				sb.append("\"count\":\"" + objects[1] + "\"");
				sb.append("},");
			}
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append("]");
		return sb;
	}

	public ICountTeacherService getiCountTeacherService() {
		return iCountTeacherService;
	}

	public void setiCountTeacherService(
			ICountTeacherService iCountTeacherService) {
		this.iCountTeacherService = iCountTeacherService;
	}

}
