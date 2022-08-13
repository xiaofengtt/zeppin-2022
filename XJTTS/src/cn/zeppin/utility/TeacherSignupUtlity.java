package cn.zeppin.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherSignupActionScope;
import cn.zeppin.entity.TeacherSignupCondition;
import cn.zeppin.entity.TeacherSignupReActionScope;
import cn.zeppin.entity.TeacherSignupSizer;
import cn.zeppin.entity.TeachingGrade;
import cn.zeppin.entity.TeachingLanguage;
import cn.zeppin.entity.TeachingSubject;

/**
 * 教师培训报送筛查器
 * 两部分：一、作用域筛查
 * 二、条件筛查
 * @author Administrator
 *
 */
@SuppressWarnings({"unchecked"})
public class TeacherSignupUtlity {

	static Logger logger = LogManager.getLogger(TeacherSignupUtlity.class);

	// 页面回传
	public static void ResponseWrite(String msg, String type, HttpServletResponse response) {

		try {
			if (type != null && type.length() > 0) {
				response.setContentType(type);
			}
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(msg);

		} catch (Exception e) {
			logger.error(e);
		}

	}

	// 发送信息
	public static void sendResponse(String status, String value, HttpServletResponse response) {
		StringBuilder checkSB = new StringBuilder();
		checkSB.append("{");
		checkSB.append("\"Result\":\"" + status + "\"");
		checkSB.append(",");
		checkSB.append("\"Message\":\"" + value + "\"");
		checkSB.append("}");
		ResponseWrite(checkSB.toString(), "application/json", response);
	}

	/**
	 * 校验--是否符合作用域范围
	 * @param params
	 * @return
	 */
	public static boolean checkSizer(Map<String, Object> params){
		if(params != null){
			if(!params.containsKey("teacherSignupSizer")){
				return false;
			}
			List<TeacherSignupSizer> tsslist = (List<TeacherSignupSizer>) params.get("teacherSignupSizer");
			if(tsslist != null && tsslist.size() > 0){
				params.remove("teacherSignupSizer");
				JSONObject obj = null;
				boolean flag = false;
				boolean flag2 = false;
				boolean flag3 = false;
				boolean flag4 = false;
				boolean flag5 = false;
				boolean flag6 = false;
				List<TeacherSignupCondition> tscList = new ArrayList<TeacherSignupCondition>();
				List<TeacherSignupReActionScope> tsrsList = new ArrayList<TeacherSignupReActionScope>();
				for(TeacherSignupSizer tss : tsslist){
					String actionScope = tss.getActionScope();
					String condition = tss.getCondition();
					String reactionScope = tss.getReactionScope();
					TeacherSignupActionScope tsas = null;
					TeacherSignupCondition tsc = null;
					TeacherSignupReActionScope tsrs = null;
					if(actionScope != null && !"".equals(actionScope)){
						obj = JSONObject.fromObject(actionScope);
						tsas = (TeacherSignupActionScope) JSONObject.toBean(obj,TeacherSignupActionScope.class);
					}else{
						continue;
					}
					if(condition != null && !"".equals(condition)){
						obj = JSONObject.fromObject(condition);
						tsc = (TeacherSignupCondition) JSONObject.toBean(obj,TeacherSignupCondition.class);
					}else{
						continue;
					}
					if(reactionScope != null && !"".equals(reactionScope)){
						obj = JSONObject.fromObject(reactionScope);
						tsrs = (TeacherSignupReActionScope) JSONObject.toBean(obj,TeacherSignupReActionScope.class);
					}else{
						continue;
					}
					
					if(tsas.getYears() != null && tsas.getYears().size() > 0){
						params.put("years", tsas.getYears());
						flag = checkYear(params);
					}
					if(tsas.getProjectLevel() != null && tsas.getProjectLevel().size() > 0){
						params.put("levels", tsas.getProjectLevel());
						flag2 = checkProjectlevel(params);
					}
					if(tsas.getProjectType() != null && tsas.getProjectType().size() > 0){
						params.put("types", tsas.getProjectType());
						flag3 = checkProjecttype(params);
					}
					if(tsas.getProject() != null && tsas.getProject().size() > 0){
						params.put("projects", tsas.getProject());
						flag4 = checkProject(params);
					}
					if(tsas.getSubject() != null && tsas.getSubject().size() > 0){
						params.put("subjects", tsas.getSubject());
						flag5 = checkSubject(params);
					}
					if(tsas.getHours() != null && tsas.getHours().size() > 0){
						params.put("hours", tsas.getHours());
						flag6 = checkHours(params);
					} else {
						flag6 = true;
					}
					
					if(flag && flag2 && flag3 && flag4 && flag5 && flag6){
						tscList.add(tsc);
						tsrsList.add(tsrs);
					}
				}
				if(tscList.size() > 0){
					params.put("teacherSignupCondition", tscList);
					if(tsrsList.size() > 0){
						params.put("teacherSignupReActionScope", tsrsList);
					}
					return true;
				}else{
					if(tsrsList.size() > 0){
						params.put("teacherSignupReActionScope", tsrsList);
						return true;
					}
					return false;
				}
			}else{
				return false;
			}
			
		}else{
			return false;
		}
//		return true;
	}
	
	/**
	 * 校验--是否符合报名筛选条件(基础信息筛查)
	 * @param params
	 * @return
	 */
	public static Map<String, String> checkCondition(Map<String, Object> params){
		Map<String, String> info = new HashMap<String, String>();
		if(params != null){
			if(!params.containsKey("teacher")){
				info.put("status", "error");
				info.put("message", "没有符合条件的老师");
				return info;
			}
			Teacher teacher = (Teacher) params.get("teacher");
			boolean flag = true;
			StringBuilder message = new StringBuilder();
			if(params.containsKey("teacherSignupCondition")){
				/*
				 * 如果没有条件限制，那么所有条件都符合
				 * 关于权重
				 * 权重高的通过，权重底的不通过---算通过还是不通过？
				 */
				List<TeacherSignupCondition> tscList = (List<TeacherSignupCondition>) params.get("teacherSignupCondition");
				if(tscList != null && tscList.size() > 0){
					for(TeacherSignupCondition tsc : tscList){
						/*
						 * 培训次数限制（待定）
						 */
//						if(tsc.getTrainingCount() != null && tsc.getTrainingCount().size() > 0){
//							
//						}
						//验证学段
						if(tsc.getTeachingGrade() != null && tsc.getTeachingGrade().size() > 0){
							TeachingGrade tg = null;
							if(teacher.getTeachingGrades() != null && teacher.getTeachingGrades().size() > 0){
								for(TeachingGrade g : teacher.getTeachingGrades()){
									if(g.getIsprime()){
										tg = g;
									}
								}
							}
							params.put("teachingGrade", tg.getGrade().getId().toString());
							params.put("grades", tsc.getTeachingGrade());
							if(!"ok".equals(checkTeachingGrade(params).get("status"))){
								flag = false;
								message.append(checkTeachingGrade(params).get("message"));
								message.append(",");
							}
						}
						
						//验证学科
						if(tsc.getTeachingSubject() != null && tsc.getTeachingSubject().size() > 0){
							TeachingSubject ts = null;
							if(teacher.getTeachingSubjects() != null && teacher.getTeachingSubjects().size() > 0){
								for(TeachingSubject s : teacher.getTeachingSubjects()){
									if(s.getIsprime()){
										ts = s;
									}
								}
							}
							params.put("teachingSubject", ts.getSubject().getId().toString());
							params.put("teachingSubjects", tsc.getTeachingSubject());
							if(!"ok".equals(checkTeachingSubject(params).get("status"))){
								flag = false;
								message.append(checkTeachingSubject(params).get("message"));
								message.append(",");
							}
						}
						
						//验证教学语言
						if(tsc.getTeachingLanguage() != null && tsc.getTeachingLanguage().size() > 0){
							TeachingLanguage tl = null;
							if(teacher.getTeachingLanguages() != null && teacher.getTeachingLanguages().size() > 0){
								for(TeachingLanguage l : teacher.getTeachingLanguages()){
									if(l.getIsprime()){
										tl = l;
									}
								}
							}
							params.put("teachingLanguage", tl.getLanguage().getId().toString());
							params.put("languages", tsc.getTeachingLanguage());
							if(!"ok".equals(checkTeachingLanguage(params).get("status"))){
								flag = false;
								message.append(checkTeachingLanguage(params).get("message"));
								message.append(",");
							}
						}
						
						//验证是否双语
						if(tsc.getMultiLanguage() != null && !"".equals(tsc.getMultiLanguage())){
							Short multiLanguage = (short) (teacher.getMultiLanguage() == true ? 1:0);
							params.put("multiLanguage", multiLanguage);
							params.put("multi", tsc.getMultiLanguage());
							if(!"ok".equals(checkMultiLanguage(params).get("status"))){
								flag = false;
								message.append(checkMultiLanguage(params).get("message"));
								message.append(",");
							}
						}
						
						//验证教师职称
						if(tsc.getJobTitle() != null && tsc.getJobTitle().size() > 0){
							String jobTitle = teacher.getJobTitle().getId()+"";
							params.put("jobTitle", jobTitle);
							params.put("titles", tsc.getJobTitle());
							if(!"ok".equals(checkJobTitle(params).get("status"))){
								flag = false;
								message.append(checkJobTitle(params).get("message"));
								message.append(",");
							}
						}
						
						//验证教师民族
						if(tsc.getEthnic() != null && tsc.getEthnic().size() > 0){
							String ethnic = teacher.getEthnic().getId()+"";
							params.put("teacherEthnic", ethnic);
							params.put("ethnics", tsc.getEthnic());
							if(!"ok".equals(checkTeacherEthnic(params).get("status"))){
								flag = false;
								message.append(checkTeacherEthnic(params).get("message"));
								message.append(",");
							}
						}
						
						//验证教师教龄
						if(tsc.getTeachingAge()!= null && tsc.getTeachingAge().size() > 0){
							String teachingAge = Utlity.getAge(teacher.getTeachingAge())+"";
							params.put("teachingAge", teachingAge);
							params.put("teachingAges", tsc.getTeachingAge());
							if(!"ok".equals(checkTeachingAge(params).get("status"))){
								flag = false;
								message.append(checkTeachingAge(params).get("message"));
								message.append(",");
							}
						}
						
						//验证教师年龄
						if(tsc.getTeacherAge()!= null && tsc.getTeacherAge().size() > 0){
							String teacherAge = Utlity.getAge(teacher.getBirthday())+"";
							params.put("teacherAge", teacherAge);
							params.put("ages", tsc.getTeacherAge());
							if(!"ok".equals(checkTeacherAge(params).get("status"))){
								flag = false;
								message.append(checkTeacherAge(params).get("message"));
								message.append(",");
							}
						}
						
						//验证教师政治面貌
						if(tsc.getPolice()!= null && tsc.getPolice().size() > 0){
							String teacherPolitic = teacher.getPolitics().getId()+"";
							params.put("teacherPolitic", teacherPolitic);
							params.put("politics", tsc.getPolice());
							if(!"ok".equals(checkTeacherPolitic(params).get("status"))){
								flag = false;
								message.append(checkTeacherPolitic(params).get("message"));
								message.append(",");
							}
						}
						
//						if(!flag){
//							break;
//						}
					}
				}
			}
			String status = "ok";
			if(!flag){
				status = "error";
				message.delete(message.length() - 1, message.length());
			}
			info.put("status", status);
			info.put("message", message.toString());
		}else{
			info.put("status", "error");
			info.put("message", "教师不符合报名条件");
		}
		
		
		return info;
	}

	/**
	 * 校验年份--是否符合范围
	 * @param params
	 * @return
	 */
	public static boolean checkYear(Map<String, Object> params){
		if(params != null){
			String projectYear = "";
			if(params.containsKey("projectYear")){
				projectYear = params.get("projectYear").toString();
			}else{
				return false;
			}
			String startyear = "";
			String endyear = "";
			if(params.containsKey("years")){
				
				Map<String, Object> years = (Map<String, Object>) params.get("years");
				if(years != null){
					startyear = years.get("startyear").toString();
					endyear = years.get("endyear").toString();
				}else{
					return false;
				}
			}else{
				return false;
			}
			if(Integer.parseInt(projectYear)>= Integer.parseInt(startyear) && Integer.parseInt(projectYear)<=Integer.parseInt(endyear)){//符合条件 返回true
				return true;
			}
		}else{
			return false;
		}
		return false;
	}
	
	/**
	 * 校验项目级别--是否符合范围
	 * @param params
	 * @return
	 */
	public static boolean checkProjectlevel(Map<String, Object> params){
		if(params != null){
			String projectLevel = "";
			if(params.containsKey("projectLevel")){
				projectLevel = params.get("projectLevel").toString();
			}else{
				return false;
			}
			if(params.containsKey("levels")){
				
				List<String> levels = (List<String>) params.get("levels");
				if(levels != null){
					if(levels.get(0).equals("all")){
						return true;
					}
					for(String level:levels){
						if(projectLevel.equals(level)){
							return true;
						}
					}
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
		return false;
	}
	
	/**
	 * 校验项目类型--是否符合范围
	 * @param params
	 * @return
	 */
	public static boolean checkProjecttype(Map<String, Object> params){
		if(params != null){
			String projectType = "";
			if(params.containsKey("projectType")){
				projectType = params.get("projectType").toString();
			}else{
				return false;
			}
			if(params.containsKey("types")){
				
				List<String> types = (List<String>) params.get("types");
				if(types != null){
					if(types.get(0).equals("all")){
						return true;
					}
					for(String type:types){
						if(projectType.equals(type)){
							return true;
						}
					}
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
		return false;
	}
	
	/**
	 * 校验项目--是否符合范围
	 * @param params
	 * @return
	 */
	public static boolean checkProject(Map<String, Object> params){
		if(params != null){
			String project = "";
			if(params.containsKey("project")){
				project = params.get("project").toString();
			}else{
				return false;
			}
			if(params.containsKey("projects")){
				
				List<String> projects = (List<String>) params.get("projects");
				if(projects != null){
					if(projects.get(0).equals("all")){
						return true;
					}
					for(String pro:projects){
						if(project.equals(pro)){
							return true;
						}
					}
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
		return false;
	}
	
	/**
	 * 校验培训学科--是否符合范围
	 * @param params
	 * @return
	 */
	public static boolean checkSubject(Map<String, Object> params){
		if(params != null){
			String subject = "";
			if(params.containsKey("subject")){
				subject = params.get("subject").toString();
			}else{
				return false;
			}
			if(params.containsKey("subjects")){
				
				List<String> subjects = (List<String>) params.get("subjects");
				if(subjects != null){
					if(subjects.get(0).equals("all")){
						return true;
					}
					for(String su:subjects){
						if(subject.equals(su)){
							return true;
						}
					}
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
		return false;
	}
	
	/**
	 * 校验培训学时--是否符合范围（待定）
	 * @param params
	 * @return
	 */
	public static boolean checkHours(Map<String, Object> params){
		if(params != null){
			String projectHours = "";
			if(params.containsKey("projectHours")){
				projectHours = params.get("projectHours").toString();
			}else{
				return false;
			}
			String starthours = "";
			String endhours = "";
			if(params.containsKey("hours")){
				
				Map<String, Object> years = (Map<String, Object>) params.get("hours");
				if(years != null){
					starthours = years.get("starthours").toString();
					endhours = years.get("endhours").toString();
				}else{
					return false;
				}
			}else{
				return false;
			}
			if(Integer.parseInt(projectHours)>= Integer.parseInt(starthours) && Integer.parseInt(projectHours)<=Integer.parseInt(endhours)){//符合条件 返回true
				return true;
			}
		}else{
			return false;
		}
		return false;
	}

	/**
	 * 筛查培训次数 -- 是否符合条件
	 * @param params
	 * @return
	 * 返回筛查结果：status、message
	 */
	public static Map<String, String> checkTrainingCount(Map<String, Object> params){
		Map<String, String> info = new HashMap<String, String>();
		return info;
	}
	
	/**
	 * 筛查教师主要教学学段 -- 是否符合条件
	 * @param params
	 * @return
	 * 返回筛查结果：status、message
	 */
	public static Map<String, String> checkTeachingGrade(Map<String, Object> params){
		Map<String, String> info = new HashMap<String, String>();
		info.put("status", "error");
		info.put("message", "教师主要教学学段不符合要求");
		if(params != null){
			if(params.containsKey("teachingGrade")){
				String teachingGrade = params.get("teachingGrade").toString();
				if(params.containsKey("grades")){
					List<String> grades = (List<String>) params.get("grades");
					for(String grade : grades){
						if(teachingGrade.equals(grade)){
							info.put("status", "ok");
							info.put("message", "教师主要教学学段符合要求");
						}
					}
				}
			}
		}
		return info;
	}
	
	/**
	 * 筛查教师主要教学学科 -- 是否符合条件
	 * @param params
	 * @return
	 * 返回筛查结果：status、message
	 */
	public static Map<String, String> checkTeachingSubject(Map<String, Object> params){
		Map<String, String> info = new HashMap<String, String>();
		info.put("status", "error");
		info.put("message", "教师主要教学学科不符合要求");
		if(params != null){
			if(params.containsKey("teachingSubject")){
				String teachingSubject = params.get("teachingSubject").toString();
				if(params.containsKey("teachingSubjects")){
					List<String> teachingSubjects = (List<String>) params.get("teachingSubjects");
					for(String subject : teachingSubjects){
						if(teachingSubject.equals(subject)){
							info.put("status", "ok");
							info.put("message", "教师主要教学学科符合要求");
						}
					}
				}
			}
		}
		return info;
	}
	
	/**
	 * 筛查教师主要教学语言 -- 是否符合条件
	 * @param params
	 * @return
	 * 返回筛查结果：status、message
	 */
	public static Map<String, String> checkTeachingLanguage(Map<String, Object> params){
		Map<String, String> info = new HashMap<String, String>();
		info.put("status", "error");
		info.put("message", "教师主要教学语言不符合要求");
		if(params != null){
			if(params.containsKey("teachingLanguage")){
				String teachingLanguage = params.get("teachingLanguage").toString();
				if(params.containsKey("languages")){
					List<String> languages = (List<String>) params.get("languages");
					for(String language : languages){
						if(teachingLanguage.equals(language)){
							info.put("status", "ok");
							info.put("message", "教师主要教学语言符合要求");
						}
					}
				}
			}
		}
		return info;
	}
	
	/**
	 * 筛查是否为双语教师 -- 是否符合条件
	 * @param params
	 * @return
	 * 返回筛查结果：status、message
	 */
	public static Map<String, String> checkMultiLanguage(Map<String, Object> params){
		Map<String, String> info = new HashMap<String, String>();
		info.put("status", "error");
		info.put("message", "教师主教师是否双语教学条件不符合要求");
		if(params != null){
			if(params.containsKey("multiLanguage")){
				short multiLanguage = (Short) params.get("multiLanguage");
				if(params.containsKey("multi")){
					short multi =  Short.parseShort(params.get("multi").toString());
					if(multiLanguage == multi){
						info.put("status", "ok");
						info.put("message", "教师是否双语教学条件符合要求");
					}
				}
			}
		}
		return info;
	}
	
	/**
	 * 筛查教师职称 -- 是否符合条件
	 * @param params
	 * @return
	 * 返回筛查结果：status、message
	 */
	public static Map<String, String> checkJobTitle(Map<String, Object> params){
		Map<String, String> info = new HashMap<String, String>();
		info.put("status", "error");
		info.put("message", "教师职称不符合要求");
		if(params != null){
			if(params.containsKey("jobTitle")){
				String jobTitle = params.get("jobTitle").toString();
				if(params.containsKey("titles")){
					List<String> titles = (List<String>) params.get("titles");
					for(String title : titles){
						if(jobTitle.equals(title)){
							info.put("status", "ok");
							info.put("message", "教师职称符合要求");
						}
					}
				}
			}
		}
		return info;
	}
	
	/**
	 * 筛查教师民族 -- 是否符合条件
	 * @param params
	 * @return
	 * 返回筛查结果：status、message
	 */
	public static Map<String, String> checkTeacherEthnic(Map<String, Object> params){
		Map<String, String> info = new HashMap<String, String>();
		info.put("status", "error");
		info.put("message", "教师民族不符合要求");
		if(params != null){
			if(params.containsKey("teacherEthnic")){
				String teacherEthnic = params.get("teacherEthnic").toString();
				if(params.containsKey("ethnics")){
					List<String> ethnics = (List<String>) params.get("ethnics");
					for(String ethnic : ethnics){
						if(teacherEthnic.equals(ethnic)){
							info.put("status", "ok");
							info.put("message", "教师民族符合要求");
						}
					}
				}
			}
		}
		return info;
	}
	
	/**
	 * 筛查教师教龄 -- 是否符合条件
	 * @param params
	 * @return
	 * 返回筛查结果：status、message
	 */
	public static Map<String, String> checkTeachingAge(Map<String, Object> params){
		Map<String, String> info = new HashMap<String, String>();
		info.put("status", "error");
		info.put("message", "教师教龄不符合要求");
		if(params != null){
			if(params.containsKey("teachingAge")){
				Integer teachingAge = Integer.parseInt(params.get("teachingAge").toString());
				if(params.containsKey("teachingAges")){
					Map<String, Object> teachingAges = (Map<String, Object>) params.get("teachingAges");
					Integer startTimeAge = Integer.parseInt(teachingAges.get("startTimeAge").toString());
					Integer endTimeAge = Integer.parseInt(teachingAges.get("endTimeAge").toString());
					if(teachingAge >= startTimeAge && teachingAge <= endTimeAge){
						info.put("status", "ok");
						info.put("message", "教师教龄符合要求");
					}
				}
			}
		}
		return info;
	}
	
	/**
	 * 筛查教师年龄 -- 是否符合条件
	 * @param params
	 * @return
	 * 返回筛查结果：status、message
	 */
	public static Map<String, String> checkTeacherAge(Map<String, Object> params){
		Map<String, String> info = new HashMap<String, String>();
		info.put("status", "error");
		info.put("message", "教师年龄不符合要求");
		if(params != null){
			if(params.containsKey("teacherAge")){
				Integer teacherAge = Integer.parseInt(params.get("teacherAge").toString());
				if(params.containsKey("ages")){
					Map<String, Object> ages = (Map<String, Object>) params.get("ages");
					Integer startAge = Integer.parseInt(ages.get("startAge").toString());
					Integer endAge = Integer.parseInt(ages.get("endAge").toString());
					if(teacherAge >= startAge && teacherAge <= endAge){
						info.put("status", "ok");
						info.put("message", "教师年龄符合要求");
					}
				}
			}
		}
		return info;
	}
	
	/**
	 * 筛查教师政治面貌 -- 是否符合条件
	 * @param params
	 * @return
	 * 返回筛查结果：status、message
	 */
	public static Map<String, String> checkTeacherPolitic(Map<String, Object> params){
		Map<String, String> info = new HashMap<String, String>();
		info.put("status", "error");
		info.put("message", "教师政治面貌不符合要求");
		if(params != null){
			if(params.containsKey("teacherPolitic")){
				String teacherPolitic = params.get("teacherPolitic").toString();
				if(params.containsKey("politics")){
					List<String> politics = (List<String>) params.get("politics");
					for(String politic : politics){
						if(teacherPolitic.equals(politic)){
							info.put("status", "ok");
							info.put("message", "教师政治面貌符合要求");
						}
					}
				}
			}
		}
		return info;
	}
}
