package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.Map;

import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class CourseOrderStatAction extends MyBaseAction {

	@Override
	public void initGrid() {

		this.getGridConfig().setTitle(this.getText("教材征订统计"));
		this.getGridConfig().setCapability(false, false, false);
//		this.getGridConfig().addColumn(this.getText("row_num"),"row_num",false,false,false,"");
		this.getGridConfig().addColumn(this.getText("id"),"id",false,false,false,"");
		this.getGridConfig().addColumn(this.getText("学期"),"combobox_PeSemester.semesterName");
		this.getGridConfig().addColumn(this.getText("学习中心"),"combobox_PeSite.siteName");
		this.getGridConfig().addColumn(this.getText("课程名称"),"combobox_PeTchCourse.courseName");
		this.getGridConfig().addColumn(this.getText("教材名称"),"combobox_PeTchBook.bookName");
		this.getGridConfig().addColumn(this.getText("征订数量"),"num",false);
		
		this.getGridConfig().addColumn(this.getText("ISBN"), "ISBN", true);
		this.getGridConfig().addColumn(this.getText("价格"),  "price",false);
		this.getGridConfig().addColumn(this.getText("作者"),  "author", true);
		this.getGridConfig().addColumn(this.getText("出版社"), "publisher", true);
		this.getGridConfig().addColumn(this.getText("备注"),  "note", false);
	}
    public Page list() {
        StringBuffer sql_temp = new StringBuffer();
        sql_temp.append("select rownum as id, one.*                                                     ");
        sql_temp.append("  from (select semester.name as semesterName,                                  ");
        sql_temp.append("               site.name as siteName,                                          ");
        sql_temp.append("               course.name as courseName,                                      ");
        sql_temp.append("               book.name as bookName,                                          ");
        sql_temp.append("               count(elective.id) as num,                                      ");
        sql_temp.append("               book.isbn as ISBN,                                              ");
        sql_temp.append("               book.price as price,                                            ");
        sql_temp.append("               book.author as author,                                          ");
        sql_temp.append("               book.publisher as publisher,                                    ");
        sql_temp.append("               book.note as note                                               ");
        sql_temp.append("          from pr_tch_stu_elective    elective,                                ");
        sql_temp.append("               pr_tch_opencourse      opencourse,                              ");
        sql_temp.append("               pe_tch_course          course,                                  ");
        sql_temp.append("               pe_semester            semester,                                ");
        sql_temp.append("               PR_TCH_OPENCOURSE_BOOK opencourseBook,                          ");
        sql_temp.append("               PE_TCH_BOOK            book,                                    ");
        sql_temp.append("               pe_student             student,                                 ");
        sql_temp.append("               pe_site                site,                                    ");
        sql_temp.append("               enum_const             const                                    ");
        sql_temp.append("         where elective.fk_tch_opencourse_id = opencourse.id                   ");
        sql_temp.append("           and opencourse.fk_course_id = course.id                             ");
        sql_temp.append("           and opencourse.fk_semester_id = semester.id                         ");
        sql_temp.append("           and opencourseBook.Fk_Opencourse_Id = opencourse.id                 ");
        sql_temp.append("           and opencourseBook.Fk_Book_Id = book.id                             ");
        sql_temp.append("           and elective.fk_stu_id = student.id                                 ");
        sql_temp.append("           and student.fk_site_id = site.id                                    ");
        sql_temp.append("           and book.flag_isvalid = const.id                                    ");
        sql_temp.append("           and const.code = '1'                                                ");
        sql_temp.append("         group by semester.name, site.name, course.name, book.name ,book.isbn  ");
        sql_temp.append("         ,book.price ,book.author,book.publisher ,book.note ) one              ");		
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
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/courseOrderStat";
		
	}

}
