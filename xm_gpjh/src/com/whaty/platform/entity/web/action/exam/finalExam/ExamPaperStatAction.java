package com.whaty.platform.entity.web.action.exam.finalExam;

import com.whaty.platform.entity.bean.PeExamRoom;
import com.whaty.platform.entity.bean.PrExamStuMaincourse;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 期末考试试卷统计列表
 * @author 李冰
 *
 */
public class ExamPaperStatAction extends MyBaseAction {


	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("试卷统计列表"));
		this.getGridConfig().addColumn(this.getText("id"),"id",false,false,false,"");
		this.getGridConfig().addColumn(this.getText("考场ID"),"roomId",false,false,false,"");
        this.getGridConfig().addColumn(this.getText("考试场次"),"combobox_peExamNo.examName");
        this.getGridConfig().addColumn(this.getText("学习中心"),"combobox_peSite.siteName");
        this.getGridConfig().addColumn(this.getText("教室"),"roomName");
        this.getGridConfig().addColumn(this.getText("考试课程"),"combobox_peTchCourse.courseName");
        this.getGridConfig().addColumn(this.getText("试卷类型"),"paperType");
        this.getGridConfig().addColumn(this.getText("人数"),"num",false);

	}
    
    public Page list() {
        StringBuffer sql_temp = new StringBuffer();
        sql_temp.append("    select rownum as id, one.*    									");			                                                              
        sql_temp.append("      from (select room.id as roomId,                                ");                           
        sql_temp.append("                   examNo.Name examName,                             ");                           
        sql_temp.append("                   site.name siteName,                               ");                           
        sql_temp.append("                   room.classroom roomName,                          ");                           
        sql_temp.append("                   course.name courseName,                           ");                           
        sql_temp.append("                   examNo.Paper_Type paperType,                      ");                           
        sql_temp.append("                   count(booking.id) as num                          ");                           
        sql_temp.append("              from pe_exam_room        room,                         ");                           
        sql_temp.append("                   pe_site             site,                         ");                           
        sql_temp.append("                   pe_exam_no          examNo,                       ");                           
        sql_temp.append("                   pr_exam_booking     booking,                      ");                           
        sql_temp.append("                   pr_tch_stu_elective elective,                     ");                           
        sql_temp.append("                   pr_tch_opencourse   opencourse,                   ");                           
        sql_temp.append("                   pe_tch_course       course,                       ");                           
        sql_temp.append("                   pe_semester         semester                      ");                           
        sql_temp.append("             where room.fk_site_id = site.id                         ");                           
        sql_temp.append("               and room.fk_exam_no = examNo.Id                       ");                           
        sql_temp.append("               and booking.fk_exam_room_id = room.id                 ");                           
        sql_temp.append("               and booking.fk_tch_stu_elective_id = elective.id      ");                           
        sql_temp.append("               and booking.fk_semester_id = semester.id              ");                           
        sql_temp.append("               and semester.flag_active = '1'                        ");                           
        sql_temp.append("               and elective.fk_tch_opencourse_id = opencourse.id     ");                           
        sql_temp.append("               and opencourse.fk_course_id = course.id               ");                           
        sql_temp.append("             group by room.id,                                       ");                           
        sql_temp.append("                      examNo.Name,                                   ");                           
        sql_temp.append("                      site.name,                                     ");                           
        sql_temp.append("                      room.classroom,                                ");                           
        sql_temp.append("                      examNo.Paper_Type,                             ");                           
        sql_temp.append("                      course.name) one          					");	                                                                                                	
        this.setSqlCondition(sql_temp);
        Page page = null;
        try {
                page = getGeneralService().getByPageSQL(sql_temp.toString(),
                                Integer.parseInt(this.getLimit()),
                                Integer.parseInt(this.getStart()));
        } catch (NumberFormatException e) {
                e.printStackTrace();
        } catch (Exception e) {
                e.printStackTrace();
        }
        return page;
}
  

	@Override
	public void setEntityClass() {
		this.entityClass =PeExamRoom.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/exam/examPaperStat";
	}

}
