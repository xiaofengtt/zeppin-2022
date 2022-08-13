package com.whaty.platform.entity.service.imp.exam.finalExam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeExamScoreInputUser;
import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.PrExamBooking;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.dao.MyListDAO;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.exam.finalExam.CreatInputUserService;

public class CreatInputUserServiceImp implements CreatInputUserService {
	private GeneralDao generalDao;
	private MyListDAO myListDao;	

	public String save_CreatInputUser() throws EntityException {
		int sum = 0;//帐户数量
		int minsum = 0;//小帐户数量
		if(clearOld()){
			String minSumOfElectiveStu = this.getMyListDao().getSysValueByName("minSumOfElectiveStu");
			String maxSumOfTotal = this.getMyListDao().getSysValueByName("maxSumOfTotal");
			int maxS = 0;
			try{
				Integer.parseInt(minSumOfElectiveStu);
			}catch(Throwable e){
				minSumOfElectiveStu = "100";
			}
			try{
				maxS = Integer.parseInt(maxSumOfTotal);
			}catch(Throwable e){
				maxS = 500;
			}
			List list1 = getCourse_SumofStu(true,minSumOfElectiveStu);
			if(list1!=null&&list1.size()>0){
				for(int i = 0 ; i <list1.size();i++){
					Object[] os = (Object[])list1.get(i);
					PeTchCourse course = (PeTchCourse)this.getMyListDao().getById(PeTchCourse.class, os[0].toString());
					PeExamScoreInputUser userA = new PeExamScoreInputUser();
					userA.setName(course.getCode()+"a");
					userA.setPassword(getPassword());
					userA = (PeExamScoreInputUser)this.getGeneralDao().save(userA);
					course.setPeExamScoreInputUserByFkExamScoreInputUseraId(userA);
					PeExamScoreInputUser userB = new PeExamScoreInputUser();
					userB.setName(course.getCode()+"b");
					userB.setPassword(getPassword());
					userB = (PeExamScoreInputUser)this.getGeneralDao().save(userB);
					course.setPeExamScoreInputUserByFkExamScoreInputUserbId(userB);
					course = (PeTchCourse)this.getGeneralDao().save(course);
					sum++;
				}
			}
			List list2 = getCourse_SumofStu(false,minSumOfElectiveStu);
			if(list2!=null&&list2.size()>0){
				String name = ".";
				int times = 1;
				int sumofstu = 0;
				Map<String, List<Object[]>> map = new HashMap<String, List<Object[]>>();
				List<Object[]> list = new ArrayList<Object[]>();
				for(int i = 0 ; i <list2.size();i++){
					Object[] os = (Object[])list2.get(i);
					int snum = Integer.parseInt(os[1].toString());
					if( sumofstu + snum > maxS){
						map.put(times+"", list);
						times ++;
						list = new ArrayList<Object[]>();
						sumofstu = snum;
					}else{
						sumofstu += snum;
					}						
					list.add(os);
				}
				if(list.size()>0) map.put(times+"", list);					
				if(map!=null){
					java.util.Set<String> timeslist = map.keySet();
					for(String t:timeslist){
						PeExamScoreInputUser userA = new PeExamScoreInputUser();
						userA.setName(t+name+"a");
						userA.setPassword(getPassword());
						userA = (PeExamScoreInputUser)this.getGeneralDao().save(userA);
						PeExamScoreInputUser userB = new PeExamScoreInputUser();
						userB.setName(t+name+"b");
						userB.setPassword(getPassword());
						userB = (PeExamScoreInputUser)this.getGeneralDao().save(userB);
						List<Object[]> l = map.get(t);
						for(Object[] os:l){
							PeTchCourse course = (PeTchCourse)this.getMyListDao().getById(PeTchCourse.class, os[0].toString());
							course.setPeExamScoreInputUserByFkExamScoreInputUseraId(userA);
							course.setPeExamScoreInputUserByFkExamScoreInputUserbId(userB);
							course = (PeTchCourse)this.getGeneralDao().save(course);
						}
						sum++;
						minsum++;
					}
				}
				
			}
		}
		// TODO Auto-generated method stub
		return "共创建"+sum+"个账户,其中考试人数较少的课程使用的等分帐户有"+minsum+"个";
	}
	/**
	 * 将考试成绩同步到学生选课表
	 * @return
	 * @throws EntityException
	 */
	public String saveExamScore() throws EntityException{
		try {
			DetachedCriteria dcBooking = DetachedCriteria.forClass(PrExamBooking.class);
			dcBooking.createCriteria("peSemester", "peSemester").add(Restrictions.eq("flagActive", "1"));
			dcBooking.add(Restrictions.isNull("scoreExam"));
			List<PrExamBooking> list = null;
			list = this.getGeneralDao().getList(dcBooking);
			if(list!=null&&list.size()>0){
				throw new EntityException("有"+list.size()+"个考试记录还没有录入成绩，请成绩录入完成以后再将考试成绩同步到学生选课表。");
			}
			DetachedCriteria dcBooking2 = DetachedCriteria.forClass(PrExamBooking.class);
			dcBooking2.createCriteria("peSemester", "peSemester").add(Restrictions.eq("flagActive", "1"));
			list = this.getGeneralDao().getList(dcBooking2);
			if(list==null||list.isEmpty()){
				throw new EntityException("当前学期没有学生参加考试。");
			}
			int i = 0;
			for (PrExamBooking prExamBooking : list) {
				PrTchStuElective elective = prExamBooking.getPrTchStuElective();
				if(prExamBooking.getScoreExam()!=null&&prExamBooking.getScoreExam()>=0){
					if(elective.getScoreExam()==null||elective.getScoreExam()<prExamBooking.getScoreExam()){
						elective.setScoreExam(prExamBooking.getScoreExam());
						elective.setEnumConstByFlagScoreStatus(prExamBooking.getEnumConstByFlagScoreStatus());
						this.getGeneralDao().save(elective);
						i++;
					}
				}
				
			}
			return "共同步了"+i+"条数据。";
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new EntityException("同步学生成绩失败。");
		}
		
	}
	/**
	 * 清空旧数据
	 * @return
	 */
	private boolean clearOld(){
		try{
			String sql1 = "update pe_tch_course set fk_exam_score_input_usera_id=null,fk_exam_score_input_userb_id=null where fk_exam_score_input_usera_id is not null or fk_exam_score_input_userb_id is not null";
			this.getGeneralDao().executeBySQL(sql1);
			String sql2 = "delete from pe_exam_score_input_user";
			this.getGeneralDao().executeBySQL(sql2);
			return true;
		}catch(Throwable e){
			return false;
		}
	}
	
	/**
	 * 获取当前学期下课程ID及其对应的考试人数列表
	 * 	b为true是可以独立设置账户密码的课程
	 * 	b为false是不可以独立设置账户密码的小课程
	 * @param b
	 * @param minSumOfElectiveStu
	 * @return
	 */
	private List getCourse_SumofStu(boolean b, String minSumOfElectiveStu){
		StringBuffer sql = new StringBuffer();
		sql.append(" select cid,cnum from (");
		sql.append(getCourse_SumofStu_SQL());
		if(b) sql.append(") where cnum>="+minSumOfElectiveStu);
		else sql.append(") where cnum<"+minSumOfElectiveStu);
		sql.append(" order by cnum desc ");
		return this.getGeneralDao().getBySQL(sql.toString());
	}
	
	/**
	 * 获取当前学期下课程ID及其对应的考试人数的SQL
	 * @return
	 */
	private StringBuffer getCourse_SumofStu_SQL(){
		StringBuffer sql = new StringBuffer();
		sql.append("select prTchOpencourse.Fk_Course_Id cid,                            ");
		sql.append("       count(prTchOpencourse.Fk_Course_Id) cnum                     ");
		sql.append("from  pe_exam_no peExamNo,                                          ");
		sql.append("      pe_exam_room peExamRoom,                                      ");
		sql.append("      pr_exam_booking prExamBooking,                                ");
		sql.append("      pe_semester peSemester,                                       ");
		sql.append("      pr_tch_stu_elective prTchStuElective,                         ");
		sql.append("      pr_tch_opencourse prTchOpencourse                             ");
		sql.append("where peSemester.Flag_Active='1' and                                ");
	//	sql.append("      --考试场次表与学期表                                          ");
		sql.append("      peExamNo.Fk_Semester_Id=peSemester.Id and                     ");
	//	sql.append("      --考试预约表与学生选课表                                      ");
		sql.append("      prExamBooking.Fk_Tch_Stu_Elective_Id=prTchStuElective.Id and  ");
	//	sql.append("        --学生选课表和开课表                                        ");
		sql.append("        prTchStuElective.Fk_Tch_Opencourse_Id=prTchOpencourse.Id and");
	//	sql.append("      --考试预约表和考场表                                          ");
		sql.append("      prExamBooking.Fk_Exam_Room_Id=peExamRoom.Id and               ");
	//	sql.append("        --考场表和考试场次表                                        ");
		sql.append("        peExamRoom.Fk_Exam_No=peExamNo.Id                           ");
		sql.append("group by prTchOpencourse.Fk_Course_Id                               ");
		return sql;
	}
	
	private String getPassword(){
		Random r = new Random();
		String p = ""+r.nextInt(10000);
		while(p.length()<4){
			p = "0"+p;
		}
		return p;
	}
	
	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}
	
	public MyListDAO getMyListDao() {
		return myListDao;
	}

	public void setMyListDao(MyListDAO myListDao) {
		this.myListDao = myListDao;
	}
}
