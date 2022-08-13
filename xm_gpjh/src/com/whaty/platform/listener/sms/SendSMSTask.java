/**
 * 
 */
package com.whaty.platform.listener.sms;

import java.sql.SQLException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleRegister;
import com.whaty.platform.database.oracle.standard.entity.user.OracleManagerPriv;
import com.whaty.platform.database.oracle.standard.sms.OracleSmsManagerPriv;
import com.whaty.platform.entity.BasicEntityManage;
import com.whaty.platform.entity.PlatformFactory;
import com.whaty.platform.entity.basic.Semester;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.sms.SmsFactory;
import com.whaty.platform.sms.SmsManage;
import com.whaty.platform.sms.basic.SmsSystemPoint;
import com.whaty.platform.util.SearchProperty;
import com.whaty.util.TimeUtil;

public class SendSMSTask extends TimerTask {

	private static boolean isRunning = false;

	public String processPhone(String phone) {
		String[] phones = null;
		if (phone != null)
			phones = phone.split(",");
		else
			return "";
		String tmpPhone = "";
		for (int i = 0; i < phones.length; i++) {
			if (phones[i] != null
					&& !phones[i].equals("")
					&& !phones[i].equals("null")
					&& (tmpPhone.length() == 0 || (tmpPhone.length() > 0 && tmpPhone
							.indexOf("," + phones[i]) < 0)))
				tmpPhone += "," + phones[i];
		}
		if (tmpPhone.length() > 0)
			tmpPhone = tmpPhone.substring(1);
		return tmpPhone;
	}

	public void run() {
		int C_SCHEDULE_HOUR = 8;
		int C_SCHEDULE_MINUTE = 0;
		int C_SCHEDULE_SECOND = 0;

		Calendar cal = Calendar.getInstance();
		if (!isRunning) {
			if (true) {
				isRunning = true;
				String nowDateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm")
						.format(new Date());
				String sql = "select id,targets,content from sms_info where status='1' and type='1' and settime='"
						+ nowDateStr + "'";
				dbpool db = new dbpool();
				MyResultSet rs = null;
				try {
					rs = db.executeQuery(sql);
				} catch (Exception e) {
					db.close(rs);
				}
				SmsFactory sfactory = SmsFactory.getInstance();
				OracleSmsManagerPriv smsManagerPriv = new OracleSmsManagerPriv(
						"system");
				SmsManage smsManage = sfactory.creatSmsManage(smsManagerPriv);
				try {
					while (rs != null && rs.next()) {
						try {
							smsManage.sendMessage(rs.getString("id"),
									processPhone(rs.getString("targets")), rs
											.getString("content"));
						} catch (Exception e) {
						}
					}
				} catch (Exception e) {
					db.close(rs);
				}
				db.close(rs);

				isRunning = false;
			}

			if (C_SCHEDULE_HOUR == cal.get(Calendar.HOUR_OF_DAY)
					&& C_SCHEDULE_MINUTE == cal.get(Calendar.MINUTE)
					&& C_SCHEDULE_SECOND == cal.get(Calendar.SECOND)) {
				System.out.println("开始扫描发送！时间: "
						+ cal.getTime().toLocaleString());
				isRunning = true;
				// 详细任务
				SmsFactory sfactory = SmsFactory.getInstance();
				SmsManage smsManage = sfactory.creatSmsManage();

				OracleSmsManagerPriv smsManagerPriv = new OracleSmsManagerPriv(
						"system");
				smsManagerPriv.manageSmsSystemPoint = 1;
				SmsManage smsManage1 = sfactory.creatSmsManage(smsManagerPriv);

				PlatformFactory pfactory = PlatformFactory.getInstance();
				OracleManagerPriv managerPriv = new OracleManagerPriv();
				managerPriv.getSemester = 1;
				managerPriv.getRegisterStudent = 1;
				BasicEntityManage basicEntityManage = null;
				List activeSemesters = new ArrayList();
				Semester semester = null;
				try {
				basicEntityManage = pfactory
						.creatBasicEntityManage(managerPriv);
					
					activeSemesters = basicEntityManage.getActiveSemesters(); // 以下循环是取得学期时间段包含当前时间的活动学期
				} catch (PlatformException e2) {
					e2.printStackTrace();
				}
				for (int i = 0; i < activeSemesters.size(); i++) {
					semester = (Semester) activeSemesters.get(i);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date startDate = sdf.parse(semester.getStart_date(),
							new ParsePosition(0));
					Date endDate = sdf.parse(semester.getEnd_date(),
							new ParsePosition(0));
					Date startElective = sdf.parse(
							semester.getStart_elective(), new ParsePosition(0));
					Date endElective = sdf.parse(semester.getEnd_elective(),
							new ParsePosition(0));
					String startElectiveDateStr = semester.getStart_elective();
					String endElectiveDateStr = semester.getEnd_elective();
					System.out.println("startElectiveDateStr: "
							+ startElectiveDateStr + " endElectiveDateStr: "
							+ endElectiveDateStr);

					if (semester != null) {
						// 获得当前时间
						String nowDateStr = new SimpleDateFormat("yyyy-MM-dd")
								.format(new Date());

						// 选课开始对学生进行短信通知
						// 获得时间差
						int diff = TimeUtil.getTimeDiffNoABS(nowDateStr,
								startElectiveDateStr);
						try {
							if (diff == 0) {
								System.out.println(semester.getId() + " 07");
								// 获取活动学期的注册学生的手机号
								List studentList = new ArrayList();
								List searchProperties = new ArrayList();
								searchProperties.add(new SearchProperty(
										"semester_id", semester.getId(), "="));
								OracleRegister register = new OracleRegister();
								// studentList = register.getRegStudents(null,
								// searchProperties, null);
								studentList = register
										.getNoElectiveRegStudents(null,
												searchProperties, null,
												semester.getId());
								String mobilePhones = "";
								for (int j = 0; j < studentList.size(); j++) {
									Student student = (Student) studentList
											.get(j);
									mobilePhones += student.getNormalInfo()
											.getMobilePhones()
											+ ",";
								}
								if (mobilePhones.length() > 0)
									mobilePhones = mobilePhones.substring(0,
											mobilePhones.length() - 1);

								try {
									if (mobilePhones.length() > 0) {
										SmsSystemPoint sp = smsManage1
												.getSmsSystemPoint("07");
										String sms_status = sp.getStatus();
										if (sms_status.equals("1")) {

											String reCode = smsManage
													.sendMessage(
															processPhone(mobilePhones),
															sp.getContent()
																	+ "  选课截止时间:"
																	+ semester
																			.getEnd_elective());
											// 如果获取的短信发送状态号为200则表示网关已经接收到;否则的化表明短信网关并未收到短信
											if (reCode.equals("200")) {
												reCode = "0"; // 发送成功状态
											} else {
												reCode = "1"; // 发送失败状态,需要重新发送
											}

											smsManage1
													.addSystemSmsMessage(
															processPhone(mobilePhones),
															sp.getContent()
																	+ "  选课截止时间:"
																	+ semester
																			.getEnd_elective(),
															"选课开始",
															null,
															"0",
															"2",
															new SimpleDateFormat(
																	"yyyy-MM-dd HH:mm:ss")
																	.format(new Date()),
															reCode);
										}
									}
								} catch (PlatformException e) {
									
								}
							}
						} catch (Exception e) {
						}

						// 选课结束前5天，3天，1天分别对学生进行短信通知
						// 获得时间差
						diff = TimeUtil.getTimeDiffNoABS(nowDateStr,
								endElectiveDateStr);
						try {
							if (diff == -5 || diff == -3 || diff == -1) {
								try {
									List studentList = new ArrayList();
									List searchProperties = new ArrayList();
									searchProperties.add(new SearchProperty(
											"semester_id", semester.getId(),
											"="));
									OracleRegister register = new OracleRegister();
									studentList = register
											.getNoElectiveRegStudents(null,
													searchProperties, null,
													semester.getId());
									String mobilePhones = "";
									for (int j = 0; j < studentList.size(); j++) {
										Student student = (Student) studentList
												.get(j);
										mobilePhones += student.getNormalInfo()
												.getMobilePhones()
												+ ",";
									}
									if (mobilePhones.length() > 0)
										mobilePhones = mobilePhones.substring(
												0, mobilePhones.length() - 1);

									if (mobilePhones.length() > 0) {
										String no = "08";
										if (diff == -3)
											no = "09";
										if (diff == -1)
											no = "10";
										System.out.println(semester.getId()
												+ " " + no);
										SmsSystemPoint sp = smsManage1
												.getSmsSystemPoint(no);
										String sms_status = sp.getStatus();
										if (sms_status.equals("1")) {

											String reCode = smsManage
													.sendMessage(
															processPhone(mobilePhones),
															sp.getContent());
											// 如果获取的短信发送状态号为200则表示网关已经接收到;否则的化表明短信网关并未收到短信
											if (reCode.equals("200")) {
												reCode = "0"; // 发送成功状态
											} else {
												reCode = "1"; // 发送失败状态,需要重新发送
											}

											smsManage1
													.addSystemSmsMessage(
															processPhone(mobilePhones),
															sp.getContent(),
															"选课结束前" + (-diff)
																	+ "天",
															null,
															"0",
															"2",
															new SimpleDateFormat(
																	"yyyy-MM-dd HH:mm:ss")
																	.format(new Date()),
															reCode);
										}
									}
								} catch (PlatformException e) {
									
								}
							}
						} catch (Exception e) {
						}
						// 自测结束前7天对学生进行短信通知
						// 获得时间差
						dbpool db = new dbpool();
						String sql = "select distinct a.id as testcourse_id, a.end_date as end_date,d.name as course_name from onlinetest_course_info a,entity_course_active b,entity_teach_class c,entity_course_info d"
								+ " where a.group_id = c.id and c.open_course_id = b.id and a.status = '1' and b.semester_id = '"
								+ semester.getId() + "' and b.course_id=d.id";
						MyResultSet rs = db.executeQuery(sql);

						try {
							if (rs != null)
								System.out.println(semester.getId() + " 11");
							while (rs != null && rs.next()) {
								try {
									String testcourse_id = rs
											.getString("testcourse_id");
									String testcourse_name = rs
											.getString("course_name");
									String end_date = rs.getString("end_date");
									if (end_date != null
											&& end_date.length() > 9)
										end_date = end_date.substring(0, 10);
									diff = TimeUtil.getTimeDiffNoABS(
											nowDateStr, end_date);
									if (diff == -7) {
										sql = "select s.id, u.mobilephone from entity_student_info s, entity_usernormal_info u where s.id = u.id"
												+ " and '('||s.id||')'||s.name in   (select distinct '('||e.student_id||')'||esi.name as id from entity_elective e, onlinetest_course_info d,entity_student_info esi "
												+ " where e.teachclass_id = d.group_id  and e.student_id = esi.id and d.id = '"
												+ testcourse_id
												+ "' minus select distinct c.user_id as id from test_testpaper_info a,"
												+ " onlinetest_course_paper b, test_testpaper_history c where a.id = b.paper_id and a.id = c.testpaper_id and a.status = '1'"
												+ " and b.testcourse_id = '"
												+ testcourse_id
												+ "') and u.mobilephone is not null";
										MyResultSet rs1 = db.executeQuery(sql);
										String mobilePhones = "";
										while (rs1 != null && rs1.next()) {
											mobilePhones += rs1
													.getString("mobilephone")
													+ ",";
										}
										db.close(rs1);
										if (mobilePhones.length() > 0)
											mobilePhones = mobilePhones
													.substring(0, mobilePhones
															.length() - 1);

										if (mobilePhones.length() > 0) {
											SmsSystemPoint sp = smsManage1
													.getSmsSystemPoint("11");
											String sms_status = sp.getStatus();
											if (sms_status.equals("1")) {

												String reCode = smsManage
														.sendMessage(
																processPhone(mobilePhones),
																testcourse_name
																		+ sp
																				.getContent());

												// 如果获取的短信发送状态号为200则表示网关已经接收到;否则的化表明短信网关并未收到短信
												if (reCode.equals("200")) {
													reCode = "0"; // 发送成功状态
												} else {
													reCode = "1"; // 发送失败状态,需要重新发送
												}
												smsManage1
														.addSystemSmsMessage(
																processPhone(mobilePhones),
																testcourse_name
																		+ sp
																				.getContent(),
																"自测结束前7天",
																null,
																"0",
																"2",
																new SimpleDateFormat(
																		"yyyy-MM-dd HH:mm:ss")
																		.format(new Date()),
																reCode);
											}
										}
									}
								} catch (PlatformException e) {
									
								}

							}
						} catch (SQLException e) {
							
						}
						db.close(rs);

						// 自测开始对学生进行短信通知
						// 获得时间差
						sql = "select distinct a.id as testcourse_id, a.start_date as start_date,a.end_date as end_date,d.name as course_name from onlinetest_course_info a,entity_course_active b,entity_teach_class c,entity_course_info d"
								+ " where a.group_id = c.id and c.open_course_id = b.id and a.status = '1' and b.semester_id = '"
								+ semester.getId() + "' and b.course_id=d.id";
						rs = db.executeQuery(sql);

						try {
							if (rs != null)
								System.out.println(semester.getId() + " 14");
							while (rs != null && rs.next()) {
								try {
									String testcourse_id = rs
											.getString("testcourse_id");
									String testcourse_name = rs
											.getString("course_name");
									String start_date = rs
											.getString("start_date");
									String end_date = rs.getString("end_date");
									if (start_date != null
											&& start_date.length() > 9)
										start_date = start_date
												.substring(0, 10);
									if (end_date != null
											&& end_date.length() > 9)
										end_date = end_date.substring(0, 10);

									diff = TimeUtil.getTimeDiffNoABS(
											nowDateStr, start_date);
									if (diff == 0) {
										sql = "select s.id, u.mobilephone from entity_student_info s, entity_usernormal_info u where s.id = u.id"
												+ " and s.id in (select distinct e.student_id as id from entity_elective e, onlinetest_course_info d"
												+ " where e.teachclass_id = d.group_id and d.id = '"
												+ testcourse_id
												+ "') and u.mobilephone is not null";
										MyResultSet rs1 = db.executeQuery(sql);
										String mobilePhones = "";
										while (rs1 != null && rs1.next()) {
											mobilePhones += rs1
													.getString("mobilephone")
													+ ",";
										}
										db.close(rs1);
										if (mobilePhones.length() > 0)
											mobilePhones = mobilePhones
													.substring(0, mobilePhones
															.length() - 1);

										if (mobilePhones.length() > 0) {
											SmsSystemPoint sp = smsManage1
													.getSmsSystemPoint("14");
											String sms_status = sp.getStatus();
											if (sms_status.equals("1")) {

												String reCode = smsManage
														.sendMessage(
																processPhone(mobilePhones),
																testcourse_name
																		+ sp
																				.getContent()
																		+ "  截止时间:"
																		+ end_date);
												// 如果获取的短信发送状态号为200则表示网关已经接收到;否则的化表明短信网关并未收到短信
												if (reCode.equals("200")) {
													reCode = "0"; // 发送成功状态
												} else {
													reCode = "1"; // 发送失败状态,需要重新发送
												}

												smsManage1
														.addSystemSmsMessage(
																processPhone(mobilePhones),
																testcourse_name
																		+ sp
																				.getContent()
																		+ "  截止时间:"
																		+ end_date,
																"自测开始",
																null,
																"0",
																"2",
																new SimpleDateFormat(
																		"yyyy-MM-dd HH:mm:ss")
																		.format(new Date()),
																reCode);
											}
										}
									}
								} catch (PlatformException e) {
									
								}

							}
						} catch (SQLException e) {
							
						}
						db.close(rs);

					}
				}

				// 生日祝福短信通知
				dbpool db = new dbpool();
				String nowDateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm")
						.format(new Date());
				String nowDateStrs = new SimpleDateFormat("yyyyMMdd").format(
						new Date()).substring(4);
				String nowMonth = nowDateStr.substring(5, 7);
				String nowMonth1 = nowMonth;
				if (nowMonth1.charAt(0) == '0')
					nowMonth1 = nowMonth1.substring(1);
				String nowDay = nowDateStr.substring(8, 10);
				String nowDay1 = nowDay;
				if (nowDay1.charAt(0) == '0')
					nowDay1 = nowDay1.substring(1);
				String sql = "select distinct b.mobilephone from entity_student_info a, entity_usernormal_info b where a.id = b.id and "
						+ "a.isgraduated = '0' and a.status = '0' and b.mobilephone is not null "
						+ "and ((b.idcard like '__________"
						+ nowDateStrs
						+ "____' and length(b.idcard) = 18) or "
						+ "(b.idcard like '________"
						+ nowDateStrs
						+ "___' and length(b.idcard) = 15) or "
						+ "(length(b.idcard) <> 15 and length(b.idcard) <> 18 and "
						+ "(b.birthday like '____"
						+ nowDateStrs
						+ "' or b.birthday like '____-"
						+ nowMonth
						+ "-"
						+ nowDay
						+ "' or b.birthday like '____-"
						+ nowMonth
						+ "-"
						+ nowDay1
						+ "' or b.birthday like '____-"
						+ nowMonth1
						+ "-"
						+ nowDay
						+ "' or b.birthday like '____-"
						+ nowMonth1
						+ "-"
						+ nowDay1
						+ "' or b.birthday like '____."
						+ nowMonth
						+ "."
						+ nowDay
						+ "' or b.birthday like '____."
						+ nowMonth1
						+ "."
						+ nowDay
						+ "' or b.birthday like '____."
						+ nowMonth
						+ "."
						+ nowDay1
						+ "' or b.birthday like '____."
						+ nowMonth1
						+ "."
						+ nowDay1
						+ "'))) union"
						+ " select distinct mobilephone from(select distinct mobilephone,re9 as idcard from entity_sitemanager_info "
						+ "where re9 is not null union "
						+ "select distinct mobilephone,re9 as idcard from entity_manager_info "
						+ "where re9 is not null union "
						+ "select distinct mobilephone,id_card as idcard from entity_siteteacher_info "
						+ "where re9 is not null union "
						+ "select distinct mobilephone,id_card as idcard from entity_teacher_info "
						+ "where re9 is not null) where (idcard like '__________"
						+ nowDateStrs
						+ "____' and length(idcard) = 18) or "
						+ "(idcard like '________"
						+ nowDateStrs
						+ "___' and length(idcard) = 15)";
				MyResultSet rs = db.executeQuery(sql);
				try {
					System.out.println(semester.getId() + " 12");
					String mobilePhones = "";
					try {
						while (rs != null && rs.next()) {
							mobilePhones += rs.getString("mobilephone") + ",";
						}
						db.close(rs);
						if (mobilePhones.length() > 0)
							mobilePhones = mobilePhones.substring(0,
									mobilePhones.length() - 1);
					} catch (SQLException e) {
					}
					if (mobilePhones.length() > 0) {
						SmsSystemPoint sp = smsManage1.getSmsSystemPoint("12");
						String sms_status = sp.getStatus();
						if (sms_status.equals("1")) {

							String reCode = smsManage
									.sendMessage(processPhone(mobilePhones), sp
											.getContent());

							// 如果获取的短信发送状态号为200则表示网关已经接收到;否则的化表明短信网关并未收到短信
							if (reCode.equals("200")) {
								reCode = "0"; // 发送成功状态
							} else {
								reCode = "1"; // 发送失败状态,需要重新发送
							}
							smsManage1.addSystemSmsMessage(
									processPhone(mobilePhones),
									sp.getContent(), "生日祝福", null, "0", "2",
									new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
											.format(new Date()), reCode);
						}
					}
				} catch (Exception e) {
				}
				isRunning = false;
			}
		}
	}
}
