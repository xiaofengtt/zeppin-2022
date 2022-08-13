package com.whaty.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.util.BasicData;

/**
 * Servlet implementation class InitBasicDataServlet
 */
public class InitBasicDataServlet<T extends AbstractBean> extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GeneralService<T> generalService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitBasicDataServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	@SuppressWarnings({ "unchecked" })
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
//		System.out.println("111111111111111111111111111111111111111111111111111");
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("../config/applicationContext.xml");
		this.generalService = (GeneralService<T>)ac.getBean("generalService");
		String sql1 = "select unitattrib0_.ID as col_0_0_, unitattrib0_.NAME as col_1_0_ from PE_PROVINCE unitattrib0_ order by unitattrib0_.NAME";
		String sql2 = "select unitattrib0_.ID as col_0_0_, unitattrib0_.NAME as col_1_0_ from CITY unitattrib0_ order by unitattrib0_.NAME";
		String sql3 = "select unitattrib0_.ID as col_0_0_, unitattrib0_.NAME as col_1_0_ from COUNTY unitattrib0_ order by unitattrib0_.NAME";
		String sql4 = "select unitattrib0_.ID as col_0_0_, unitattrib0_.NAME as col_1_0_ from PE_SUBJECT unitattrib0_ order by unitattrib0_.NAME";
		String sql5 = "select unitattrib0_.ID as col_0_0_, unitattrib0_.NAME as col_1_0_ from FOLK unitattrib0_ order by unitattrib0_.NAME";
		String sql6 = "select unitattrib0_.ID as col_0_0_, unitattrib0_.NAME as col_1_0_ from UNIT_ATTRIBUTE unitattrib0_ order by unitattrib0_.NAME";
		String sql7 = "select unitattrib0_.ID as col_0_0_, unitattrib0_.NAME as col_1_0_ from EDUCATION unitattrib0_ order by unitattrib0_.NAME";
		String sql8 = "select unitattrib0_.ID as col_0_0_, unitattrib0_.NAME as col_1_0_ from JOB_TITLE unitattrib0_ order by unitattrib0_.NAME";
		String sql9 = "select unitattrib0_.ID as col_0_0_, unitattrib0_.NAME as col_1_0_ from MAIN_TEACHING_SUBJECT unitattrib0_ order by unitattrib0_.NAME";
		String sql10 = "select unitattrib0_.ID as col_0_0_, unitattrib0_.NAME as col_1_0_ from MAIN_TEACHING_GRADE unitattrib0_ order by unitattrib0_.NAME";
		String sql11 = "select unitattrib0_.ID as col_0_0_, unitattrib0_.NAME as col_1_0_ from UNIT_TYPE unitattrib0_ order by unitattrib0_.NAME";
		String sql12 = "select peunit0_.ID as col_0_0_, peunit0_.NAME as col_1_0_ from PE_UNIT peunit0_ order by peunit0_.NAME";
		try {
			BasicData.peProvinces = this.generalService.getBySQL(sql1);
			BasicData.cities = this.generalService.getBySQL(sql2);
			BasicData.counties = this.generalService.getBySQL(sql3);
			BasicData.peSubjects = this.generalService.getBySQL(sql4);
			BasicData.folks = this.generalService.getBySQL(sql5);
			BasicData.unitAttributes = this.generalService.getBySQL(sql6);
			BasicData.educations = this.generalService.getBySQL(sql7);
			BasicData.jobTitles = this.generalService.getBySQL(sql8);
			BasicData.mainTeachingSubject = this.generalService.getBySQL(sql9);
			BasicData.mainTeachingGrade = this.generalService.getBySQL(sql10);
			BasicData.unitTypes = this.generalService.getBySQL(sql11);
			BasicData.units = this.generalService.getBySQL(sql12);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		System.out.println("2222222222222222222222222222222222222222222222222222222222");
		String sql1 = "select unitattrib0_.ID as col_0_0_, unitattrib0_.NAME as col_1_0_ from PE_PROVINCE unitattrib0_ order by unitattrib0_.NAME";
		String sql2 = "select unitattrib0_.ID as col_0_0_, unitattrib0_.NAME as col_1_0_ from CITY unitattrib0_ order by unitattrib0_.NAME";
		String sql3 = "select unitattrib0_.ID as col_0_0_, unitattrib0_.NAME as col_1_0_ from COUNTY unitattrib0_ order by unitattrib0_.NAME";
		String sql4 = "select unitattrib0_.ID as col_0_0_, unitattrib0_.NAME as col_1_0_ from PE_SUBJECT unitattrib0_ order by unitattrib0_.NAME";
		String sql5 = "select unitattrib0_.ID as col_0_0_, unitattrib0_.NAME as col_1_0_ from FOLK unitattrib0_ order by unitattrib0_.NAME";
		String sql6 = "select unitattrib0_.ID as col_0_0_, unitattrib0_.NAME as col_1_0_ from UNIT_ATTRIBUTE unitattrib0_ order by unitattrib0_.NAME";
		String sql7 = "select unitattrib0_.ID as col_0_0_, unitattrib0_.NAME as col_1_0_ from EDUCATION unitattrib0_ order by unitattrib0_.NAME";
		String sql8 = "select unitattrib0_.ID as col_0_0_, unitattrib0_.NAME as col_1_0_ from JOB_TITLE unitattrib0_ order by unitattrib0_.NAME";
		String sql9 = "select unitattrib0_.ID as col_0_0_, unitattrib0_.NAME as col_1_0_ from MAIN_TEACHING_SUBJECT unitattrib0_ order by unitattrib0_.NAME";
		String sql10 = "select unitattrib0_.ID as col_0_0_, unitattrib0_.NAME as col_1_0_ from MAIN_TEACHING_GRADE unitattrib0_ order by unitattrib0_.NAME";
		String sql11 = "select unitattrib0_.ID as col_0_0_, unitattrib0_.NAME as col_1_0_ from UNIT_TYPE unitattrib0_ order by unitattrib0_.NAME";
		String sql12 = "select peunit0_.ID as col_0_0_, peunit0_.NAME as col_1_0_ from PE_UNIT peunit0_ order by peunit0_.NAME";
		
		try {
			BasicData.peProvinces = this.generalService.getBySQL(sql1);
			BasicData.cities = this.generalService.getBySQL(sql2);
			BasicData.counties = this.generalService.getBySQL(sql3);
			BasicData.peSubjects = this.generalService.getBySQL(sql4);
			BasicData.folks = this.generalService.getBySQL(sql5);
			BasicData.unitAttributes = this.generalService.getBySQL(sql6);
			BasicData.educations = this.generalService.getBySQL(sql7);
			BasicData.jobTitles = this.generalService.getBySQL(sql8);
			BasicData.mainTeachingSubject = this.generalService.getBySQL(sql9);
			BasicData.mainTeachingGrade = this.generalService.getBySQL(sql10);
			BasicData.unitTypes = this.generalService.getBySQL(sql11);
			BasicData.units = this.generalService.getBySQL(sql12);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");  
        PrintWriter out = response.getWriter();
        out.println("更新成功");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

//	public GeneralService<T> getGeneralService() {
//		return generalService;
//	}
//
//	public void setGeneralService(GeneralService<T> generalService) {
//		this.generalService = generalService;
//	}

	
}
