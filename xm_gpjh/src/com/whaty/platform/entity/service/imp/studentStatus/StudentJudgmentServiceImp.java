package com.whaty.platform.entity.service.imp.studentStatus;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PeTchProgram;
import com.whaty.platform.entity.bean.PrExamStuMaincourse;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.studentStatas.StudentJudgmentService;
import com.whaty.platform.util.Const;

public class StudentJudgmentServiceImp implements StudentJudgmentService{
	
	private  GeneralDao generalDao;
	
	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.imp.studentStatus.StudentJudgmentService#matchpublicrequired(java.lang.String)
	 */
	public  boolean matchpublicrequired(String id) throws EntityException{
		String typesql = "select name from pe_tch_coursegroup where id='"+Const.publicrequired_id+"'";
		String name = "";
		try{
			name = getGeneralDao().getBySQL(typesql).remove(0).toString();
		}catch(Throwable e){
			throw new EntityException("系统课程分组出错,请联系管理员...");
		}
		boolean b = true;
		String sql ="    select tpg.min_credit              " +
					"   from pe_student           s,                " +
					"        pe_tch_program       tp,               " +
					"        pe_tch_program_group tpg              " +
					//"        pe_tch_coursegroup   tcg               " +
					"  where tp.fk_major_id = s.fk_major_id         " +
					"    and tp.fk_grade_id = s.fk_grade_id         " +
					"    and tp.fk_edutype_id = s.fk_edutype_id     " +
					"    and tp.flag_major_type = s.flag_major_type     " +
					"    and tp.id = tpg.fk_program_id              " +
					//"    and tcg.id = tpg.fk_coursegroup_id         " +
					"    and tpg.fk_coursegroup_id = '"+ Const.publicrequired_id +"'" +
					"    and s.id = '"+ id+"'                   " ;
		try{
		java.util.List list =  getGeneralDao().getBySQL(sql);
		if(list!=null&&list.size()>0){
			Object os = (Object)list.remove(0);
			if(os!=null&&os.toString().length()>0){
			Double min_credit = Double.parseDouble(os.toString());
			if(min_credit>0){
				String sq10="  select nvl(sum(credit),0) sum_credit        " + 
						"   from (select tpc.credit,                                    " +
						"                tpc.fk_course_id,                              " +
						"                tse.score_total,                               " +
						"                tse.fk_stu_id,                                 " +
						"                tpg.fk_coursegroup_id,                                        " +
						//"                tcg.name,                                      " +
						"                tpg.fk_program_id,                             " +
						"                tpg.min_credit                                 " +
						"           from pr_tch_program_course tpc,                     " +
						"                pr_tch_stu_elective   tse,                     " +
						"                pe_tch_program_group  tpg                     " +
						//"                pe_tch_coursegroup    tcg                      " +
						"          where tse.fk_tch_program_course = tpc.id             " +
						//"            and tpg.fk_coursegroup_id = tcg.id                 " +
						"            and tpc.fk_programgroup_id = tpg.id                " +
						"            and tse.score_total >= "+Const.mustscore+") x1,    " +
						"        (select s.id s_id, tp.id tp_id, tp.graduate_min_credit " +
						"           from pe_student s, pe_tch_program tp                " +
						"          where tp.fk_major_id = s.fk_major_id                 " +
						"            and tp.fk_grade_id = s.fk_grade_id                 " +
						"            and tp.flag_major_type = s.flag_major_type         " +
						"            and s.id='"+id+"'                                  " +
						"            and tp.fk_edutype_id = s.fk_edutype_id) x2         " +
						"  where x1.fk_stu_id = x2.s_id                                 " +
						"    and x1.fk_program_id = tp_id                               " +
						"    and x1.fk_coursegroup_id = '"+ Const.publicrequired_id +"'                 " +
						"        ";
				java.util.List list2 =  getGeneralDao().getBySQL(sq10);
				if(list2!=null&&list2.size()>0){
					Double sum_credit = Double.parseDouble(list2.remove(0).toString());
					if(sum_credit<min_credit){
						throw new EntityException(name +"的一共获得了"+sum_credit+"学分,没有达到教学计划的"+min_credit+"学分的要求!");
					}
				}else{
					throw new EntityException(name +"的没有获得学分,没有达到教学计划的"+min_credit+"学分的要求!");
				}
			}
			}else{
				throw new EntityException(name +"的毕业所要获得的最少学分没有设置...");
			}
		}
		}catch(Throwable e){
			e.printStackTrace();
			if(e.getClass().getName().endsWith("EntityException"))
				throw new EntityException(e.getMessage());
			else
				throw new EntityException("毕业验证失败,请联系管理员...");
		}
		return b;
	}
	
	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.imp.studentStatus.StudentJudgmentService#matchmajorrequired(java.lang.String)
	 */
	public  boolean matchmajorrequired(String id) throws EntityException{
		String typesql = "select name from pe_tch_coursegroup where id='"+Const.majorrequired_id+"'";
		String name = "";
		try{
			name = getGeneralDao().getBySQL(typesql).remove(0).toString();
		}catch(Throwable e){
			throw new EntityException("系统课程分组出错,请联系管理员...");
		}
		boolean b = true;
		String sql ="    select tpg.min_credit              " +
					"   from pe_student           s,                " +
					"        pe_tch_program       tp,               " +
					"        pe_tch_program_group tpg              " +
					//"        pe_tch_coursegroup   tcg               " +
					"  where tp.fk_major_id = s.fk_major_id         " +
					"    and tp.fk_grade_id = s.fk_grade_id         " +
					"    and tp.fk_edutype_id = s.fk_edutype_id     " +
					"    and tp.flag_major_type = s.flag_major_type     " +
					"    and tp.id = tpg.fk_program_id              " +
					//"    and tcg.id = tpg.fk_coursegroup_id         " +
					"    and tpg.fk_coursegroup_id = '"+ Const.majorrequired_id +"'" +
					"    and s.id = '"+ id+"'                   " ;
		try{
		java.util.List list =  getGeneralDao().getBySQL(sql);
		if(list!=null&&list.size()>0){
			Object os = (Object)list.remove(0);
			if(os!=null&&os.toString().length()>0){
			Double min_credit = Double.parseDouble(os.toString());
			if(min_credit>0){
				String sq10="  select nvl(sum(credit),0) sum_credit        " + 
						"   from (select tpc.credit,                                    " +
						"                tpc.fk_course_id,                              " +
						"                tse.score_total,                               " +
						"                tse.fk_stu_id,                                 " +
						"                tpg.fk_coursegroup_id,                                        " +
						//"                tcg.name,                                      " +
						"                tpg.fk_program_id,                             " +
						"                tpg.min_credit                                 " +
						"           from pr_tch_program_course tpc,                     " +
						"                pr_tch_stu_elective   tse,                     " +
						"                pe_tch_program_group  tpg                     " +
						//"                pe_tch_coursegroup    tcg                      " +
						"          where tse.fk_tch_program_course = tpc.id             " +
						//"            and tpg.fk_coursegroup_id = tcg.id                 " +
						"            and tpc.fk_programgroup_id = tpg.id                " +
						"            and tse.score_total >= "+Const.mustscore+") x1,    " +
						"        (select s.id s_id, tp.id tp_id, tp.graduate_min_credit " +
						"           from pe_student s, pe_tch_program tp                " +
						"          where tp.fk_major_id = s.fk_major_id                 " +
						"            and tp.fk_grade_id = s.fk_grade_id                 " +
						"            and tp.flag_major_type = s.flag_major_type     " +
						"            and s.id='"+id+"'                                  " +
						"            and tp.fk_edutype_id = s.fk_edutype_id) x2         " +
						"  where x1.fk_stu_id = x2.s_id                                 " +
						"    and x1.fk_program_id = tp_id                               " +
						"    and x1.fk_coursegroup_id = '"+ Const.majorrequired_id +"'                 " +
						"    ";
				java.util.List list2 =  getGeneralDao().getBySQL(sq10);
				if(list2!=null&&list2.size()>0){
					Double sum_credit = Double.parseDouble(list2.remove(0).toString());
					if(sum_credit<min_credit){
						throw new EntityException(name +"的一共获得了"+sum_credit+"学分,没有达到教学计划的"+min_credit+"学分的要求!");
					}
				}else{
					throw new EntityException(name +"的没有获得学分,没有达到教学计划的"+min_credit+"学分的要求!");
				}
			}
			}else{
				throw new EntityException(name +"的毕业所要获得的最少学分没有设置..");
			}
		}
		}catch(Throwable e){
			e.printStackTrace();
			if(e.getClass().getName().endsWith("EntityException"))
				throw new EntityException(e.getMessage());
			else
				throw new EntityException(name+"毕业验证失败,请联系管理员...");
		}
		return b;
	}
	
	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.imp.studentStatus.StudentJudgmentService#matchmajoralternation(java.lang.String)
	 */
	public  boolean matchmajoralternation(String id) throws EntityException{
		String typesql = "select name from pe_tch_coursegroup where id='"+Const.majoralternation_id+"'";
		String name = "";
		try{
			name = getGeneralDao().getBySQL(typesql).remove(0).toString();
		}catch(Throwable e){
			throw new EntityException("系统课程分组出错,请联系管理员...");
		}
		boolean b = true;
		String sql ="    select tpg.min_credit              " +
					"   from pe_student           s,                " +
					"        pe_tch_program       tp,               " +
					"        pe_tch_program_group tpg              " +
					//"        pe_tch_coursegroup   tcg               " +
					"  where tp.fk_major_id = s.fk_major_id         " +
					"    and tp.fk_grade_id = s.fk_grade_id         " +
					"    and tp.fk_edutype_id = s.fk_edutype_id     " +
					"    and tp.flag_major_type = s.flag_major_type     " +
					"    and tp.id = tpg.fk_program_id              " +
					//"    and tcg.id = tpg.fk_coursegroup_id         " +
					"    and tpg.fk_coursegroup_id = '"+ Const.majoralternation_id +"'" +
					"    and s.id = '"+ id+"'                   " ;
		try{
		java.util.List list =  getGeneralDao().getBySQL(sql);
		if(list!=null&&list.size()>0){
			Object os = (Object)list.remove(0);
			if(os!=null&&os.toString().length()>0){
			Double min_credit = Double.parseDouble(os.toString());
			if(min_credit>0){
				String sq10="  select nvl(sum(credit),0) sum_credit        " + 
						"   from (select tpc.credit,                                    " +
						"                tpc.fk_course_id,                              " +
						"                tse.score_total,                               " +
						"                tse.fk_stu_id,                                 " +
						"                tpg.fk_coursegroup_id,                                        " +
						//"                tcg.name,                                      " +
						"                tpg.fk_program_id,                             " +
						"                tpg.min_credit                                 " +
						"           from pr_tch_program_course tpc,                     " +
						"                pr_tch_stu_elective   tse,                     " +
						"                pe_tch_program_group  tpg                     " +
						//"                pe_tch_coursegroup    tcg                      " +
						"          where tse.fk_tch_program_course = tpc.id             " +
						//"            and tpg.fk_coursegroup_id = tcg.id                 " +
						"            and tpc.fk_programgroup_id = tpg.id                " +
						"            and tse.score_total >= "+Const.mustscore+") x1,    " +
						"        (select s.id s_id, tp.id tp_id, tp.graduate_min_credit " +
						"           from pe_student s, pe_tch_program tp                " +
						"          where tp.fk_major_id = s.fk_major_id                 " +
						"            and tp.fk_grade_id = s.fk_grade_id                 " +
						"            and tp.flag_major_type = s.flag_major_type     " +
						"            and s.id='"+id+"'                                  " +
						"            and tp.fk_edutype_id = s.fk_edutype_id) x2         " +
						"  where x1.fk_stu_id = x2.s_id                                 " +
						"    and x1.fk_program_id = tp_id                               " +
						"    and x1.fk_coursegroup_id = '"+ Const.majoralternation_id +"'                 " +
						"               ";
				java.util.List list2 =  getGeneralDao().getBySQL(sq10);
				if(list2!=null&&list2.size()>0){
					Double sum_credit = Double.parseDouble(list2.remove(0).toString());
					if(sum_credit<min_credit){
						throw new EntityException(name +"的一共获得了"+sum_credit+"学分,没有达到教学计划的"+min_credit+"学分的要求!");
					}
				}else{
					throw new EntityException(name +"的没有获得学分,没有达到教学计划的"+min_credit+"学分的要求!");
				}
			}
			}else{
				throw new EntityException(name +"的毕业所要获得的最少学分没有设置..");
			}
		}
		}catch(Throwable e){
			e.printStackTrace();
			if(e.getClass().getName().endsWith("EntityException"))
				throw new EntityException(e.getMessage());
			else
				throw new EntityException(name +"毕业验证失败,请联系管理员...");
		}
		return b;
	}
	
	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.imp.studentStatus.StudentJudgmentService#matchpublicalternation(java.lang.String)
	 */
	public  boolean matchpublicalternation(String id) throws EntityException{
		String typesql = "select name from pe_tch_coursegroup where id='"+Const.publicalternation_id+"'";
		String name = "";
		try{
			name = getGeneralDao().getBySQL(typesql).remove(0).toString();
		}catch(Throwable e){
			throw new EntityException("系统课程分组出错,请联系管理员...");
		}
		boolean b = true;
		String sql ="    select tpg.min_credit              " +
					"   from pe_student           s,                  " +
					"        pe_tch_program       tp,                 " +
					"        pe_tch_program_group tpg                " +
					//"        pe_tch_coursegroup   tcg                 " +
					"  where tp.fk_major_id = s.fk_major_id           " +
					"    and tp.fk_grade_id = s.fk_grade_id           " +
					"    and tp.fk_edutype_id = s.fk_edutype_id       " +
					"    and tp.flag_major_type = s.flag_major_type     " +
					"    and tp.id = tpg.fk_program_id                " +
					//"    and tcg.id = tpg.fk_coursegroup_id            " +
					"    and tpg.fk_coursegroup_id = '"+ Const.publicalternation_id +"'" +
					"    and s.id = '"+ id+"'                   " ;
		try{
			java.util.List list =  getGeneralDao().getBySQL(sql);
			if(list!=null&&list.size()>0){
				Object os = (Object)list.remove(0);
				if(os!=null&&os.toString().length()>0){
				Double min_credit = Double.parseDouble(os.toString());
				if(min_credit>0){
					String sq10=" select  nvl(sum(credit),0) sum_credit  from       " + 
					"  (select tpc.credit,                                          " +
					"                tpc.fk_course_id,                              " +
					"                tse.score_total,                               " +
					"                tse.fk_stu_id,                                 " +
					"                tpg.fk_coursegroup_id,                                        " +
					//"                tcg.name,                                      " +
					"                tpg.fk_program_id,                             " +
					"                tpg.min_credit                                 " +
					"           from pr_tch_program_course tpc,                     " +
					"                pr_tch_stu_elective   tse,                     " +
					"                pe_tch_program_group  tpg                     " +
					//"                pe_tch_coursegroup    tcg                      " +
					"          where tse.fk_tch_program_course = tpc.id             " +
					//"            and tpg.fk_coursegroup_id = tcg.id                 " +
					"            and tpc.fk_programgroup_id = tpg.id                " +
					"            and tse.score_total >= " + Const.mustscore +
					"            and tse.fk_stu_id = '"+id+"'                       "+
					"            minus                                              " +
					"            select x1.*                                        " +
					"   from (select tpc.credit,                                    " +
					"                tpc.fk_course_id,                              " +
					"                tse.score_total,                               " +
					"                tse.fk_stu_id,                                 " +
					"                tpg.fk_coursegroup_id,                                        " +
					//"                tcg.name,                                      " +
					"                tpg.fk_program_id,                             " +
					"                tpg.min_credit                                 " +
					"           from pr_tch_program_course tpc,                     " +
					"                pr_tch_stu_elective   tse,                     " +
					"                pe_tch_program_group  tpg                     " +
					//"                pe_tch_coursegroup    tcg                      " +
					"          where tse.fk_tch_program_course = tpc.id             " +
					//"            and tpg.fk_coursegroup_id = tcg.id                 " +
					"            and tpc.fk_programgroup_id = tpg.id                " +
					"            and tse.fk_stu_id = '"+id+"'                       "+
					"            and tse.score_total >= " + Const.mustscore +") x1, " +
					"        (select s.id s_id, tp.id tp_id, tp.graduate_min_credit " +
					"           from pe_student s, pe_tch_program tp                " +
					"          where tp.fk_major_id = s.fk_major_id                 " +
					"            and tp.fk_grade_id = s.fk_grade_id                 " +
					"            and tp.flag_major_type = s.flag_major_type     " +
					"            and s.id='"+id+"'                                  " +
					"            and tp.fk_edutype_id = s.fk_edutype_id) x2         " +
					"  where x1.fk_stu_id = x2.s_id                                 " +
					"    and x1.fk_coursegroup_id in( '"+ Const.majoralternation_id +"','"+ Const.majorrequired_id +"','"+ Const.publicrequired_id +"')" +					
					"    and x1.fk_program_id = tp_id)                              " +
					"    group by fk_stu_id                                         ";
					java.util.List list2 =  getGeneralDao().getBySQL(sq10);
					if(list2!=null&&list2.size()>0){
						Double sum_credit = Double.parseDouble(list2.remove(0).toString());
						if(sum_credit<min_credit){
							throw new EntityException(name +"的一共获得了"+sum_credit+"学分,没有达到教学计划的"+min_credit+"学分的要求!");
						}
					}else{
						throw new EntityException(name +"的没有获得学分,没有达到教学计划的"+min_credit+"学分的要求!");					
					}
				}
				}else{
					throw new EntityException(name +"的毕业所要获得的最少学分没有设置..");
				}
			}
			}catch(Throwable e){
				e.printStackTrace();
				if(e.getClass().getName().endsWith("EntityException"))
					throw new EntityException(e.getMessage());
				else
					throw new EntityException(name +"毕业验证失败,请联系管理员...");
			}
			return b;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.imp.studentStatus.StudentJudgmentService#matchTotalCredit(java.lang.String)
	 */
	public  boolean matchTotalCredit(String id) throws EntityException{
		boolean b = true;
		String sql ="    select tp.graduate_min_credit   " +
		"   from pe_student           s,                 " +
		"        pe_tch_program       tp                 " +
		"  where tp.fk_major_id = s.fk_major_id          " +
		"    and tp.fk_grade_id = s.fk_grade_id          " +
		"    and tp.flag_major_type = s.flag_major_type     " +
		"    and tp.fk_edutype_id = s.fk_edutype_id      " +
		"    and s.id = '"+ id+"'                   " ;
		try{
			java.util.List list =  getGeneralDao().getBySQL(sql);
			if(list!=null&&list.size()>0){
				Object os = (Object)list.remove(0);
				if(os!=null&&os.toString().length()>0){
				Double min_credit = Double.parseDouble(os.toString());
				if(min_credit>0){					
					Double sum_credit = getTotalCredit(id);
					if(sum_credit<min_credit){
						throw new EntityException("一共获得了"+sum_credit+"学分,没有达到教学计划的"+min_credit+"学分的要求!");
					}
				}
			}else{
				throw new EntityException("毕业所要获得的最少学分没有设置..");
			}
		}
		}catch(Throwable e){
			e.printStackTrace();
			if(e.getClass().getName().endsWith("EntityException"))
				throw new EntityException(e.getMessage());
			else
				throw new EntityException("总学分毕业验证失败,请联系管理员...");
		}
		return b;
	}
	
	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.imp.studentStatus.StudentJudgmentService#matchGraduation(java.lang.String)
	 */
	public  boolean matchGraduation(String id) throws EntityException{
		String exception = "";
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.add(Restrictions.eq("id", id));
		java.util.List list = getGeneralDao().getList(dc);
		if(list==null||list.size()<=0){
			throw new EntityException("没有查到学生...");
		}
		boolean b = true;
		try{
			b = b && matchpublicrequired(id);
		}catch(EntityException e){
			exception += e.getMessage()+"<br>";
		}
		try{
			b = b && matchmajorrequired(id);
		}catch(EntityException e){
			exception += e.getMessage()+"<br>";
		}
		try{	
			b = b && matchmajoralternation(id);
		}catch(EntityException e){
			exception += e.getMessage()+"<br>";
		}
		try{
			b = b && matchpublicalternation(id);
		}catch(EntityException e){
			exception += e.getMessage()+"<br>";
		}
		try{
			b = b && matchTotalCredit(id);
		}catch(EntityException e){
			exception += e.getMessage()+"<br>";
		}
		PeStudent student = (PeStudent)list.remove(0);
		if(student.getPeEdutype().getName().indexOf("本")>=0){
			double paperscore = 0.0;
			String papersql = "select nvl(score_total,0) from pr_tch_stu_paper where fk_stu_id ='"+id+"'";
			java.util.List paperlist = getGeneralDao().getBySQL(papersql);
			if(paperlist==null||paperlist.size()<=0){
				exception += "学生还没有做毕业论文...<br>";
				//throw new EntityException("学生还没有做毕业论文...");
			}else{
				try{
					for(Object s:paperlist){
						if(s!=null&&Double.parseDouble(s.toString())>paperscore)
							paperscore = Double.parseDouble(s.toString());
					}
				}catch(Throwable e){
					e.printStackTrace();
				}
				if(paperscore<Const.paperscore){
					exception += "学生的论文得分"+paperscore+"未达到毕业要求...<br>";
					//throw new EntityException("学生的论文得分"+paperscore+"达到毕业要求...");
				}
			}
			EnumConst English = student.getEnumConstByScoreUniteEnglishA();
			if(English==null)
					English = student.getEnumConstByScoreUniteEnglishB();
			if(English==null){
				exception += "学生的统考英语还没有成绩...<br>";
				//throw new EntityException("学生的统考英语还没有成绩...");
			}else if(English.getCode()==null||!English.getCode().equals("0")){
				exception += "学生的统考英语没有通过...<br>";
				//throw new EntityException("学生的统考英语没有通过...");
			}
			if(student.getEnumConstByScoreUniteComputer()==null||!student.getEnumConstByScoreUniteComputer().equals(getGeneralDao().getEnumConstByNamespaceCode("ScoreUniteComputer", "0"))){
				exception += "学生的统考计算机没有通过...<br>";
				//throw new EntityException("学生的统考计算机没有通过...");
			}
		}
		if(exception.length()>0)
			throw new EntityException(exception);
		return b;
	}
	
	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.imp.studentStatus.StudentJudgmentService#matchDegree(java.lang.String)
	 */
	public  boolean matchDegree(String id) throws EntityException{
		String exception = "";
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.add(Restrictions.eq("id", id));
		java.util.List list = getGeneralDao().getList(dc);
		if(list==null||list.size()<=0){
			throw new EntityException("没有查到学生...");
		}
		PeStudent student = (PeStudent)list.remove(0);
		DetachedCriteria dc0 =DetachedCriteria.forClass(PeTchProgram.class);
		dc0.createAlias("peGrade", "peGrade")
			.add(Restrictions.eq("peGrade.id", student.getPeGrade().getId()))
			.createAlias("peMajor", "peMajor")
			.add(Restrictions.eq("peMajor.id", student.getPeMajor().getId()))
			.createAlias("peEdutype", "peEdutype")
			.add(Restrictions.eq("peEdutype.id", student.getPeEdutype().getId()))
			.createAlias("enumConstByFlagMajorType", "enumConstByFlagMajorType")
			.add(Restrictions.eq("enumConstByFlagMajorType", student.getEnumConstByFlagMajorType()));
		list = getGeneralDao().getList(dc0);
		if(list==null||list.size()<=0){
			throw new EntityException("没有查到学生的教学计划...");
		}
		PeTchProgram peTchProgram =  (PeTchProgram)list.remove(0);		

		boolean b = true;
		try{
			b = b && matchpublicrequired(id);
		}catch(EntityException e){
			exception += e.getMessage()+"<br>";
			b = false;
		}
		try{
			b = b && matchmajorrequired(id);
		}catch(EntityException e){
			exception += e.getMessage()+"<br>";
			b = false;
		}
		try{	
			b = b && matchmajoralternation(id);
		}catch(EntityException e){
			exception += e.getMessage()+"<br>";
			b = false;
		}
		try{
			b = b && matchpublicalternation(id);
		}catch(EntityException e){
			exception += e.getMessage()+"<br>";
			b = false;
		}
		try{
			b = b && matchTotalCredit(id);
		}catch(EntityException e){
			exception += e.getMessage()+"<br>";
			b = false;
		}
		
		double paperscore = 0.0;
		String papersql = "select nvl(score_total,0) from pr_tch_stu_paper where fk_stu_id ='"+id+"'";
		list = getGeneralDao().getBySQL(papersql);
		if(list==null||list.size()<=0){
			exception += "学生还没有做毕业论文...<br>";
			//throw new EntityException("学生还没有做毕业论文...");
		}else{
			try{
				for(Object s:list){
					if(s!=null&&Double.parseDouble(s.toString())>paperscore)
						paperscore = Double.parseDouble(s.toString());
				}
			}catch(Throwable e){
				e.printStackTrace();
			}
			if(paperscore<peTchProgram.getDegreePaperScore()){
				exception += "学生的毕业论文得分"+paperscore+"未达到要求获得学位的最低论文得分"+peTchProgram.getDegreePaperScore()+"的要求...<br>";
				//throw new EntityException("学生的论文得分"+paperscore+"达到要求获得学位的最低论文得分"+peTchProgram.getDegreePaperScore()+"的要求...");			
			}
		}
		if(b){
			double agscore = getAverageScore(id);
			if(peTchProgram.getDegreeAvgScore()>agscore){
				exception += "学生的平均分数"+agscore+"未达到要求获得学位的最低平均分"+peTchProgram.getDegreeAvgScore()+"的要求...<br>";
				//throw new EntityException("学生的平均分数"+agscore+"达到要求获得学位的最低平均分"+peTchProgram.getDegreeAvgScore()+"的要求...");			
			}
		}
		EnumConst English = student.getEnumConstByScoreUniteEnglishA();
		if(English==null)
				English = student.getEnumConstByScoreUniteEnglishB();
		if(English==null){
			exception += "学生的统考英语还没有成绩...<br>";
			//throw new EntityException("学生的统考英语还没有成绩...");
		}else if(English.getCode()==null||!English.getCode().equals("0")){
			exception += "学生的统考英语没有通过...<br>";
			//throw new EntityException("学生的统考英语没有通过...");
		}
		if(!student.getEnumConstByScoreUniteComputer().equals(getGeneralDao().getEnumConstByNamespaceCode("ScoreUniteComputer", "0"))){
			exception += "学生的统考计算机没有通过...<br>";
			throw new EntityException("学生的统考计算机没有通过...");
		}

		if(student.getScoreDegreeEnglish()==null||student.getScoreDegreeEnglish()<Const.mustEnglishDegreeScore){
			exception += "学位英语没有达到要求...<br>";
			//throw new EntityException("学位英语没有达到要求...");
		}
		//主干课程判断
		String mcSql = " select name,code,score from (select kc.name, kc.code, nvl(score,-1) score " + 
				"   from (select tpc.id, tc.name, tc.code, tpc.fk_course_id                 " +
				"           from pr_tch_program_course tpc, enum_const ec, pe_tch_course tc " +
				"          where tpc.flag_is_main_course = ec.id                            " +
				"            and ec.code = '1'                                              " +
				"            and tc.id = tpc.fk_course_id                                   " +
				"            and tpc.fk_programgroup_id in                                  " +
				"                (select tpg.id                                             " +
				"                   from pe_student           s,                            " +
				"                        pe_tch_program       tp,                           " +
				"                        pe_tch_program_group tpg                           " +
				"                  where s.id = '"+id+"'             " +
				"                    and s.fk_edutype_id = tp.fk_edutype_id                 " +
				"                    and s.fk_grade_id = tp.fk_grade_id                     " +
				"                    and s.fk_major_id = tp.fk_major_id                     " +
				"                    and tp.flag_major_type = s.flag_major_type     " +
				"                    and tpg.fk_program_id = tp.id)) kc,                    " +
				"        (select esmc.score, eomc.fk_course_id, esmc.fk_student_id        " +
				"           from pr_exam_stu_maincourse esmc,pr_exam_open_maincourse eomc   " +
				"          where esmc.fk_student_id = '"+id+"'" +
				"            and esmc.fk_exam_open_maincourse_id=eomc.id) se          " +
				"  where kc.fk_course_id = se.fk_course_id(+) ) where score<"+ Const.mustscore;
		list = this.getGeneralDao().getBySQL(mcSql);
		if(list!=null&&list.size()>0){
			while(list.size()>0){
				Object[] mcso =  (Object[]) list.remove(0);
				String cname = mcso[0].toString();
				String ccode = mcso[1].toString();
				double score = Double.parseDouble(mcso[2].toString());
				if(score<0){
					exception += "学生的主干课程"+cname+"("+ccode+")还没有成绩...<br>";
				}else{
					exception += "学生的主干课程"+cname+"("+ccode+")的成绩还没有通过...<br>";
				}
			}
		}
		DetachedCriteria disobeydc = DetachedCriteria.forClass(PrTchStuElective.class);
		disobeydc.createAlias("enumConstByFlagScoreStatus", "enumConstByFlagScoreStatus")
			.add(Restrictions.or(Restrictions.eq("enumConstByFlagScoreStatus.code", "3"), Restrictions.eq("enumConstByFlagScoreStatus.code", "4")))
			.createAlias("peStudent", "peStudent")
			.add(Restrictions.eq("peStudent.id", id));
		list = getGeneralDao().getList(disobeydc);
		if(list!=null&&list.size()>0){
			exception += "有考试违纪，不能申请学位...<br>";
		}
		//增加主干课是否违纪的判断
		DetachedCriteria maincourse = DetachedCriteria.forClass(PrExamStuMaincourse.class);
		maincourse.createCriteria("enumConstByFlagScoreStatus", "enumConstByFlagScoreStatus")
			.add(Restrictions.or(Restrictions.eq("code", "3"), Restrictions.eq("code", "4")));
		maincourse.createAlias("peStudent", "peStudent").add(Restrictions.eq("peStudent.id", id));
		list = getGeneralDao().getList(maincourse);
		if(list!=null&&list.size()>0){
			exception += "有主干课考试违纪，不能申请学位...<br>";
		}
//		if(!student.getEnumConstByFlagDisobey().getCode().equals("0")){
//			
//			exception += "违纪了，现在是"+student.getEnumConstByFlagDisobey().getName()+"状态,无法获得学位!<br>";
//			//throw new EntityException("违纪过，现在是"+student.getEnumConstByFlagDisobey().getName()+"状态,不能获得学位...");
//		}
		if(exception.length()>0){
			throw new EntityException(exception);
		}
		return b;
	}
	
	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.imp.studentStatus.StudentJudgmentService#getTotalCredit(java.lang.String)
	 */
	public  double getTotalCredit(String id){
		double totalCredit = 0.0;
		try{
			String sql ="select nvl(sum(pc.credit),0) total_credit   " + 
			"            from pr_tch_stu_elective   se,        " + 
			"                 pr_tch_program_course pc         " + 
			"           where se.fk_tch_program_course = pc.id " +  
			"             and se.score_total >=" + Const.mustscore +
			"             and se.fk_stu_id = '"+id+"'          ";
			java.util.List list2 =  getGeneralDao().getBySQL(sql);
			if(list2!=null&&list2.size()>0){
				totalCredit = Double.parseDouble(list2.remove(0).toString());
			}
		}catch(Throwable e){
			e.printStackTrace();			
		}
		return totalCredit;
	}
	
	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.imp.studentStatus.StudentJudgmentService#getAverageCredit(java.lang.String)
	 */
	public  double getAverageScore(String id){
		double totalScore = 0.0;
		int coursenum = 0;
		try{
			String sql ="  (select tse.id                                " +
				"           from pr_tch_program_course tpc,           " +
				"                pr_tch_stu_elective   tse,              " +
				"                pe_tch_program_group  tpg,               " +
				"                pe_tch_coursegroup    tcg              " +
				"          where tse.fk_tch_program_course = tpc.id     " +
				"            and tpg.fk_coursegroup_id = tcg.id         " +
				"            and tpc.fk_programgroup_id = tpg.id          " +
				"            and tse.score_total >= " + Const.mustscore +
				"            and tse.fk_stu_id = '"+id+"'                       "+
				"            minus                                              " +
				"            select x1.id                                        " +
				"   from (select tse.id ,tse.fk_stu_id,  tpg.fk_program_id       " +
				"           from pr_tch_program_course tpc,                     " +
				"                pr_tch_stu_elective   tse,                     " +
				"                pe_tch_program_group  tpg,                     " +
				"                pe_tch_coursegroup    tcg                      " +
				"          where tse.fk_tch_program_course = tpc.id             " +
				"            and tpg.fk_coursegroup_id = tcg.id                 " +
				"            and tpc.fk_programgroup_id = tpg.id                " +
				"            and tse.fk_stu_id = '"+id+"'                       "+
				"            and tse.score_total >= " + Const.mustscore +") x1, " +
				"        (select s.id s_id, tp.id tp_id, tp.graduate_min_credit " +
				"           from pe_student s, pe_tch_program tp                " +
				"          where tp.fk_major_id = s.fk_major_id                 " +
				"            and tp.fk_grade_id = s.fk_grade_id                 " +
				"            and tp.flag_major_type = s.flag_major_type     " +
				"            and s.id='"+id+"'                                  " +
				"            and tp.fk_edutype_id = s.fk_edutype_id) x2         " +
				"  where x1.fk_stu_id = x2.s_id                                 " +
				"    and x1.fk_program_id = tp_id)   union                      "+
				"  select x1.id        " + 
				"   from (select tpc.credit,                                    " +
				"                tpc.fk_course_id,                              " +
				"                tse.score_total,                               " +
				"                tse.fk_stu_id,                                 " +
				"                tse.id,                                        " +
				"                tcg.name,                                      " +
				"                tpg.fk_program_id,                             " +
				"                tpg.min_credit                                 " +
				"           from pr_tch_program_course tpc,                     " +
				"                pr_tch_stu_elective   tse,                     " +
				"                pe_tch_program_group  tpg,                     " +
				"                pe_tch_coursegroup    tcg                      " +
				"          where tse.fk_tch_program_course = tpc.id             " +
				"            and tpg.fk_coursegroup_id = tcg.id                 " +
				"            and tpc.fk_programgroup_id = tpg.id       ) x1,    " +
				"        (select s.id s_id, tp.id tp_id, tp.graduate_min_credit " +
				"           from pe_student s, pe_tch_program tp                " +
				"          where tp.fk_major_id = s.fk_major_id                 " +
				"            and tp.fk_grade_id = s.fk_grade_id                 " +
				"            and tp.flag_major_type = s.flag_major_type     " +
				"            and s.id='"+id+"'                                  " +
				"            and tp.fk_edutype_id = s.fk_edutype_id) x2         " +
				"  where x1.fk_stu_id = x2.s_id                                 " +
				"    and x1.fk_program_id = tp_id                               ";
			java.util.List list = getGeneralDao().getBySQL(sql);
			if(list!=null&&list.size()<=0){
				coursenum = 1;
			}else
				coursenum = list.size();
			String sql0 ="select nvl(sum(score_total),0) from (" +
			"select nvl(se.score_total,0) score_total  " + 
			"            from pr_tch_stu_elective   se,        " + 
			"                 pr_tch_program_course pc         " + 
			"           where se.fk_tch_program_course = pc.id " +  
			"            and se.fk_stu_id = '"+id+"'         " +
			"            and se.id in(" +
			"  (select tse.id                                " +
			"           from pr_tch_program_course tpc,           " +
			"                pr_tch_stu_elective   tse,              " +
			"                pe_tch_program_group  tpg,               " +
			"                pe_tch_coursegroup    tcg              " +
			"          where tse.fk_tch_program_course = tpc.id     " +
			"            and tpg.fk_coursegroup_id = tcg.id         " +
			"            and tpc.fk_programgroup_id = tpg.id          " +
			"            and tse.score_total >= " + Const.mustscore +
			"            and tse.fk_stu_id = '"+id+"'                       "+
			"            minus                                              " +
			"            select x1.id                                        " +
			"   from (select tse.id ,tse.fk_stu_id,  tpg.fk_program_id       " +
			"           from pr_tch_program_course tpc,                     " +
			"                pr_tch_stu_elective   tse,                     " +
			"                pe_tch_program_group  tpg,                     " +
			"                pe_tch_coursegroup    tcg                      " +
			"          where tse.fk_tch_program_course = tpc.id             " +
			"            and tpg.fk_coursegroup_id = tcg.id                 " +
			"            and tpc.fk_programgroup_id = tpg.id                " +
			"            and tse.fk_stu_id = '"+id+"'                       "+
			"            and tse.score_total >= " + Const.mustscore +") x1, " +
			"        (select s.id s_id, tp.id tp_id, tp.graduate_min_credit " +
			"           from pe_student s, pe_tch_program tp                " +
			"          where tp.fk_major_id = s.fk_major_id                 " +
			"            and tp.fk_grade_id = s.fk_grade_id                 " +
			"            and tp.flag_major_type = s.flag_major_type     " +
			"            and s.id='"+id+"'                                  " +
			"            and tp.fk_edutype_id = s.fk_edutype_id) x2         " +
			"  where x1.fk_stu_id = x2.s_id                                 " +
			"    and x1.fk_program_id = tp_id)   union                      "+
			"  select x1.id        " + 
			"   from (select tpc.credit,                                    " +
			"                tpc.fk_course_id,                              " +
			"                tse.score_total,                               " +
			"                tse.fk_stu_id,                                 " +
			"                tse.id,                                        " +
			"                tcg.name,                                      " +
			"                tpg.fk_program_id,                             " +
			"                tpg.min_credit                                 " +
			"           from pr_tch_program_course tpc,                     " +
			"                pr_tch_stu_elective   tse,                     " +
			"                pe_tch_program_group  tpg,                     " +
			"                pe_tch_coursegroup    tcg                      " +
			"          where tse.fk_tch_program_course = tpc.id             " +
			"            and tpg.fk_coursegroup_id = tcg.id                 " +
			"            and tpc.fk_programgroup_id = tpg.id       ) x1,    " +
			"        (select s.id s_id, tp.id tp_id, tp.graduate_min_credit " +
			"           from pe_student s, pe_tch_program tp                " +
			"          where tp.fk_major_id = s.fk_major_id                 " +
			"            and tp.fk_grade_id = s.fk_grade_id                 " +
			"            and tp.flag_major_type = s.flag_major_type         " +
			"            and s.id='"+id+"'                                  " +
			"            and tp.fk_edutype_id = s.fk_edutype_id) x2         " +
			"  where x1.fk_stu_id = x2.s_id                                 " +
			"    and x1.fk_program_id = tp_id                               " +
			"))";
			java.util.List list2 =  getGeneralDao().getBySQL(sql0);
			if(list2!=null&&list2.size()>0){
				totalScore = Double.parseDouble(list2.remove(0).toString());
			}
		}catch(Throwable e){
			e.printStackTrace();			
		}
		double result = totalScore/coursenum;
		double theScore = new   java.math.BigDecimal(Double.toString(result)).setScale(2,java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
		return theScore;
	}
	
	private  GeneralDao getGeneralDao() {
		return generalDao;
	}
	
	public  void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}
	
}
