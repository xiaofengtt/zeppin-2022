package com.whaty.platform.entity.web.action.exam.mainCourse;

import com.whaty.platform.entity.bean.PrExamStuMaincourse;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
/**
 * 试卷统计列表
 * @author 李冰
 *
 */
public class MaincourseStatAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("试卷统计列表"));
//		this.getGridConfig().addColumn(this.getText("row_num"),"row_num",false,false,false,"");
		this.getGridConfig().addColumn(this.getText("id"),"id",false,false,false,"");
        this.getGridConfig().addColumn(this.getText("考场场次"),"combobox_peExamMaincourseNo.maincourseNoName");
        this.getGridConfig().addColumn(this.getText("考试课程"),"combobox_peTchCourse.courseName");
        this.getGridConfig().addColumn(this.getText("考场名"),"combobox_peExamMaincourseRoom.roomName");
        this.getGridConfig().addColumn(this.getText("人数"),"num");

	}
    
    public Page list() {
        StringBuffer sql_temp = new StringBuffer();
        sql_temp.append("   select rownum as id, one.*                  ");     
        sql_temp.append("     from (select peExamMaincourseNo.Name as maincourseNoName,           ");
        sql_temp.append("                  peTchCourse.Name as courseName,                        ");
        sql_temp.append("                  peExamMaincourseRoom.Name roomName,                    ");
        sql_temp.append("                  count(prExamStuMaincourse.Id) as num                   ");
        sql_temp.append("             from pr_exam_stu_maincourse  prExamStuMaincourse,           ");
        sql_temp.append("                  PR_EXAM_OPEN_MAINCOURSE prExamOpenMaincourse,          ");
        sql_temp.append("                  PE_EXAM_MAINCOURSE_ROOM peExamMaincourseRoom,          ");
        sql_temp.append("                  pe_tch_course           peTchCourse,                   ");
        sql_temp.append("                  PE_EXAM_MAINCOURSE_NO   peExamMaincourseNo             ");
        sql_temp.append("            where prExamStuMaincourse.Fk_Exam_Open_Maincourse_Id =       ");
        sql_temp.append("                  prExamOpenMaincourse.Id                                ");
        sql_temp.append("              and prExamOpenMaincourse.Fk_Course_Id = peTchCourse.Id     ");
        sql_temp.append("              and prExamStuMaincourse.Fk_Exam_Maincourse_Room_Id =       ");
        sql_temp.append("                  peExamMaincourseRoom.Id                                ");
        sql_temp.append("              and peExamMaincourseRoom.Fk_Exam_Open_Maincourse_Id =      ");
        sql_temp.append("                  prExamOpenMaincourse.Id                                ");
        sql_temp.append("              and prExamOpenMaincourse.Fk_Exam_Maincourse_No_Id =        ");
        sql_temp.append("                  peExamMaincourseNo.Id                                  ");
        sql_temp.append("            group by peTchCourse.Name,                                   ");
        sql_temp.append("                     peExamMaincourseNo.Name,                            ");
        sql_temp.append("                     peExamMaincourseRoom.Name) one              ");     
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
		this.entityClass =PrExamStuMaincourse.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/exam/maincourseStat";
	}

}
