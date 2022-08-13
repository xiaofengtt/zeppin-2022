package com.whaty.platform.entity.web.action.studentStatus.stuInfo;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.studentStatas.PeStudentSerive;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.DateConvertor;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 学生信息管理，包括修改查看学生信息，打印学籍表，学生证等
 * @author Administrator
 *
 */
public class PeStudentInfoAction extends MyBaseAction<PeStudent> {

	private static final long serialVersionUID = -5246671259226801541L;
	
	 PeStudentSerive peStudentService;
	
		private String sitename;//学习中心
		private String gradename;//年级
		private String edutypename;//层次
		private String majorname;//专业
		private String cardId;//学号
		private Date startDate;//发证日期
		private Date faDate; //补发日期
		private List<PeStudent> students;//学生信息
		
	private String readonly;
	
	@Override
	public void initGrid() {
		
//		this.getGridConfig().addMenuFunction("打印毕业证", "/entity/studentStatus/peStudentInfo_printBYZS.action", false, false);
		//this.getGridConfig().addMenuFunction("打印学生证", "/entity/studentStatus/peStudentInfo_printxszs.action", false, false);
		if(this.getGridConfig().checkBeforeAddMenu("/entity/studentStatus/peStudentInfo_printxjs.action")){
		this.getGridConfig().addMenuScript(this.getText("打印学籍表"), 
				"{var m = grid.getSelections();  "+
					"if(m.length > 0){	         "+
					"	var jsonData = '';       "+
					"	for(var i = 0, len = m.length; i < len; i++){"+
					"		var ss =  m[i].get('id');"+
					"		if(i==0)	jsonData = jsonData + ss ;"+
					"		else	jsonData = jsonData + ',' + ss;"+
					"	}                        "+
					"	document.getElementById('user-defined-content').style.display='none'; "+
					"	document.getElementById('user-defined-content').innerHTML=\"<form target='_blank' action='/entity/studentStatus/peStudentInfo_printxjs.action' method='post' name='formx1' style='display:none'><input type=hidden name=ids value='\"+jsonData+\"' ></form>\";"+
					"	document.formx1.submit();"+
					"	document.getElementById('user-defined-content').innerHTML=\"\";"+
					"} else {                    "+
					"Ext.MessageBox.alert('" + this.getText("test.error") + "', '" + this.getText("test.pleaseSelectAtLeastOneItem") + "');  "  +                    
		"}}                         ");
		}
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("学生信息列表（打印学籍表的时候请在页面打印设置中设置为横向打印）"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "regNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
		//this.getGridConfig().addColumn(this.getText("出身日期"), "prStudentInfo.birthday");
		this.getGridConfig().addColumn(this.getText("证件号码"), "prStudentInfo.cardNo");
		this.getGridConfig().addColumn(this.getText("性别"), "prStudentInfo.gender");
		this.getGridConfig().addColumn(this.getText("学习中心"), "peSite.name");
		this.getGridConfig().addColumn(this.getText("年级"), "peGrade.name");
		this.getGridConfig().addColumn(this.getText("专业"), "peMajor.name");
		this.getGridConfig().addColumn(this.getText("层次"), "peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业备注"), "enumConstByFlagMajorType.name");
		ColumnConfig column = new ColumnConfig(this.getText("状态"),"enumConstByFlagStudentStatus.name");
		column.setComboSQL("select id,name from enum_const where namespace='FlagStudentStatus' and code>='4'");
		this.getGridConfig().addColumn(column);
		//this.getGridConfig().addColumn(this.getText("状态"), "enumConstByFlagStudentStatus.name");
		this.getGridConfig().addColumn(this.getText(""), "enumConstByFlagStudentStatus.code",false,false,false,"");

		this.getGridConfig().addRenderScript(this.getText("学生基本信息"), "{if(record.data['enumConstByFlagStudentStatus.code']=='4') return '<a href=/entity/studentStatus/peStudentInfo_edit.action?readonly=false&ids=' + record.data['id'] + ' target=_blank><u><font color=blue>查看修改</font></u></a>'; else return '<a href=/entity/studentStatus/peStudentInfo_edit.action?readonly=true&ids=' + record.data['id'] + ' target=_blank><u><font color=blue>查看</font></u></a>';}","");
		//this.getGridConfig().addRenderFunction(this.getText("修改学生信息"), "<a href=\"/entity/studentStatus/peStudentInfo_edit.action?ids=${value}\" target=\"_blank\"><u><font color=blue>修改学生信息</font></u></a>", "id");
		//this.getGridConfig().addRenderFunction(this.getText("模拟登陆"), "<a href=\"/entity/student/main_frame.jsp\" target=\"_blank\"><u><font color=\"#FF0000\">模拟登录</font></u></a>", "regNo");

	}
	@Override
	public void setEntityClass() {
		this.entityClass = PeStudent.class;
		
	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/studentStatus/peStudentInfo";
	}

	public void setBean(PeStudent instance) {
		super.superSetBean(instance);
	}
	
	public PeStudent getBean(){
		return super.superGetBean();
	}
	
	public String getReadonly() {
		return readonly;
	}
	
	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.createAlias("prStudentInfo", "prStudentInfo", DetachedCriteria.LEFT_JOIN)
			.createAlias("peSite", "peSite")
			.createAlias("peMajor", "peMajor", DetachedCriteria.LEFT_JOIN)
			.createAlias("peEdutype","peEdutype", DetachedCriteria.LEFT_JOIN)
			.createAlias("peGrade", "peGrade", DetachedCriteria.LEFT_JOIN)
			.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus", DetachedCriteria.LEFT_JOIN)
			.add(Restrictions.ge("enumConstByFlagStudentStatus.code", "4"))
			.createAlias("enumConstByFlagMajorType", "enumConstByFlagMajorType", DetachedCriteria.LEFT_JOIN);
		return dc;
	}
	
	/**
	 * 弹出学生信息修改页面
	 * @return
	 */
	public String edit(){
			
			DetachedCriteria dcStudentInfo = DetachedCriteria.forClass(PeStudent.class);
			
			dcStudentInfo.createAlias("prStudentInfo", "prStudentInfo")
				.createAlias("peSite", "peSite")
				.createAlias("peEdutype", "peEdutype")
				.createAlias("peMajor", "peMajor")
				.createAlias("peGrade", "peGrade")
				.add(Restrictions.eq("id", this.getIds()));
			
			java.util.List<PeStudent> prStudentInfoList = null;
			try {
				prStudentInfoList = this.getGeneralService().getList(dcStudentInfo);				
				this.setBean(prStudentInfoList.get(0));
			} catch (Throwable e) {
				e.printStackTrace();
			}
			
			return "edit";
		}
	/**
	 * 学生信息修改
	 * @return
	 */
	public String editexe(){

		String msg="";//存储操作结果，用于页面信息提示		
		try{
			DetachedCriteria dcEditStudent = DetachedCriteria.forClass(PeStudent.class);		
			dcEditStudent.createAlias("prStudentInfo", "prStudentInfo")
				.add(Restrictions.eq("id", this.getBean().getId()));
			
			java.util.List<PeStudent> studentInfoList = this.getGeneralService().getList(dcEditStudent);			
			
			if(this.getBean().getRegNo() != null && studentInfoList.get(0).getRegNo() != null && !this.getBean().getRegNo().equals(studentInfoList.remove(0).getRegNo())){
				
				DetachedCriteria searchStudent = DetachedCriteria.forClass(PeStudent.class);
				searchStudent.createAlias("prStudentInfo", "prStudentInfo").add(Restrictions.eq("regNo", this.getBean().getRegNo()));
				java.util.List<PeStudent> searchStudentList = this.getGeneralService().getList(searchStudent);
				
				if(searchStudentList.size()>0){
					msg += "修改失败！所修改成的学号已存在，请首先确认所修改为的学号是否正确！\n";
					this.setMsg(msg);
					return "edit";
				}
			}
			
			DetachedCriteria student = DetachedCriteria.forClass(PeStudent.class);
			student.createAlias("prStudentInfo", "prStudentInfo")
				.add(Restrictions.and(Restrictions.ne("id", this.getBean().getId()), Restrictions.eq("prStudentInfo.cardNo", this.getBean().getPrStudentInfo().getCardNo())))
				.createAlias("peGrade","peGrade")
				.add(Restrictions.eq("peGrade.name", this.getBean().getPeGrade().getName()));
				;
			
			java.util.List<PeStudent> existstudent = this.getGeneralService().getList(student);
			
			//如果修改了证件号码，要进行唯一性判断，如果修改为的证件号已存在，提示错误信息
			if(existstudent!=null&&existstudent.size() > 0 && !existstudent.remove(0).getId().equals(this.getBean().getId())){
				msg +=("修改失败！所修改为的证件号码已存在，请确认所修改的证件号码是否正确！");
				this.setMsg(msg);
			}else{
				PeStudent stu = this.getPeStudentService().save(this.getBean());
				if(stu!=null){
					this.setBean(stu);
					msg +=("学生信息修改成功！");
					this.setMsg(msg);	
				}else{
					msg +=("学生信息修改失败！");
					this.setMsg(msg);			
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			msg +=("学生信息修改失败！");
			this.setMsg(msg);
		}
		return "edit";
		
	}
	
	/**
	 * 获得ids的学生信息
	 * 
	 * @return
	 */
	public List getStuList(){
		try{
			String[] printIds = this.getIds().split(",");
			DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
			dc.add(Restrictions.in("id", printIds));
			return this.getGeneralService().getList(dc);
		}catch(Throwable e){
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 打印学籍
	 * @return
	 */
	public String printxjs(){
		return "printxjs";
	}
	public PeStudentSerive getPeStudentService() {
		return peStudentService;
	}
	public void setPeStudentService(PeStudentSerive peStudentService) {
		this.peStudentService = peStudentService;
	}
	
	/**
	 * 打印学生证
	 * @return
	 */
	public String printStuCard(){
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		DetachedCriteria dcSite = dc.createCriteria("peSite", "peSite");
		DetachedCriteria dcGrade = dc.createCriteria("peGrade", "peGrade");
		DetachedCriteria dcEdutype = dc.createCriteria("peEdutype", "peEdutype");
		DetachedCriteria dcMajor = dc.createCriteria("peMajor", "peMajor");
		dc.createCriteria("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus").add(Restrictions.eq("code", "4"));
		dc.createCriteria("prStudentInfo", "prStudentInfo");
		if(!this.getSitename().equals("所有学习中心")){
			dcSite.add(Restrictions.eq("name", this.getSitename()));
		}
		if(!this.getGradename().equals("所有年级")){
			dcGrade.add(Restrictions.eq("name", this.getGradename()));
		}
		if(!this.getEdutypename().equals("所有层次")){
			dcEdutype.add(Restrictions.eq("name", this.getEdutypename()));
		}
		if(!this.getMajorname().equals("所有专业")){
			dcMajor.add(Restrictions.eq("name", this.getMajorname()));
		}
		if(this.getCardId()!=null&&this.getCardId().length()>0){
			dc.add(Restrictions.eq("regNo", this.getCardId().trim()));
		}
		
		List<PeStudent> list = null;
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		if(list==null||list.isEmpty()){
			this.setMsg("没有符合条件的数据");
			return "msg";
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		HttpServletResponse response=ServletActionContext.getResponse();
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session= request.getSession();
		
		//学生证pdf 排版
		Rectangle pSize=new Rectangle(422,298);//A4纸张595*842 mm*72/25.4  149/105 共10列
		Document document = new Document(pSize,0, 0, 0, 0);// 建立一个Document对象
		final int DEFAULT_BORDER_WIDTH = 0;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			PdfWriter pdfWriter = PdfWriter.getInstance(document,baos);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		try {
			document.open();
			BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);// 设置中文字体
			Font headFont10 = new Font(bfChinese, 10, Font.NORMAL);// 设置字体大小
			Font headFont8 = new Font(bfChinese, 8, Font.BOLD);// 设置字体大小
			Font headFont9 = new Font(bfChinese, 9, Font.NORMAL);// 设置字体大小
			Font headFont11 = new Font(bfChinese, 11, Font.NORMAL);// 设置字体大小
			Font headFont12 = new Font(bfChinese, 12, Font.NORMAL);// 设置字体大小
			//float[] widths = { 16f,45.355f, 181.418f, 53.858f, 68.032f,40.252f};// 设置表格的列宽
			//27, 36,33,6,18,20,9   行高：22,5,15,6,12,6,10,10,19 换算后（62.4,14.2,42.5,16,36,17,28.3,28.3,53.9）
			float[] widths = { 18.12f,24.16f, 22.15f, 4f,12.08f,13.42f,6.04f};// 设置表格的列宽
			//23.5mm  18mm  72mm   21mm  28mm  
			//float[] widths = { 66.614f,51.024f,204.094f,59.527f,79.370f,77.953f};// 设置表格的列宽
			
			for (PeStudent peStudent : list) {
			PdfPTable table = new PdfPTable(widths);// 建立一个pdf表格			
			//table.setSpacingBefore(130f);// 设置表格上面空白宽度
			table.setTotalWidth(422);// 设置表格的宽度
			table.setLockedWidth(true);// 设置表格的宽度固定
			
			// table.getDefaultCell().setBorder(0);//设置表格默认为无边框
			PdfPCell cell = new PdfPCell();//空白行
			//第一行
			cell.setFixedHeight(62.4f);
			cell.setColspan(7);// 设置合并单元格的列数
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			//第二行
			cell = new PdfPCell();//左空白
			cell.setFixedHeight(14.2f);
			cell.setColspan(3);// 设置合并单元格的列数
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(" "+peStudent.getPrStudentInfo().getGender(),headFont10));// 建立一个单元格
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居左显示
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setFixedHeight(14.2f);
			cell.setColspan(2);// 设置合并单元格的列数
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			if(peStudent.getPrStudentInfo().getBirthday()!=null){
				String birthday = sdf.format(peStudent.getPrStudentInfo().getBirthday());
				String[] bir = birthday.split("-");
				cell = new PdfPCell(new Paragraph(" "+bir[0]+"年"+bir[1]+"月",headFont10));// 建立一个单元格
			}else{
				cell = new PdfPCell(new Paragraph("",headFont10));// 建立一个单元格
			}
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居左显示
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setColspan(2);// 设置合并单元格的列数
			cell.setFixedHeight(14.2f);
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
//			cell = new PdfPCell();//右空白
//			cell.setFixedHeight(14.2f);
//			cell.setBorder(DEFAULT_BORDER_WIDTH);
//			table.addCell(cell);
			
			//第三行
			cell = new PdfPCell();//空白行
			cell.setFixedHeight(42.5f);
			cell.setColspan(7);// 设置合并单元格的列数
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			//第四行
			cell = new PdfPCell();//左空白
			cell.setFixedHeight(16f);
			cell.setColspan(3);// 设置合并单元格的列数
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			String province = peStudent.getPrStudentInfo().getProvince();
			if(province==null){
				province="";
			}
			cell = new PdfPCell(new Paragraph("  "+province,headFont10));// 建立一个单元格
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居左显示
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setFixedHeight(16f);
			cell.setColspan(2);// 设置合并单元格的列数
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			String city = peStudent.getPrStudentInfo().getCity();
			if(city==null){
				city="";
			}
			cell = new PdfPCell(new Paragraph("  "+city,headFont10));// 建立一个单元格
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居左显示
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setColspan(2);// 设置合并单元格的列数
			cell.setFixedHeight(16f);
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			
//			cell = new PdfPCell();//右空白
//			cell.setFixedHeight(16f);
//			cell.setBorder(DEFAULT_BORDER_WIDTH);
//			table.addCell(cell);
			
			//第五行
			cell = new PdfPCell();//空白行
			cell.setFixedHeight(36f);
			cell.setColspan(7);// 设置合并单元格的列数
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			//第六行
			cell = new PdfPCell();//左空白
			cell.setFixedHeight(17f);
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
//			cell = new PdfPCell(new Paragraph("_______________",headFont10));// 建立一个单元格W10012009030020010022
			cell = new PdfPCell(new Paragraph(" "+peStudent.getRegNo(),headFont10));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居左显示
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setColspan(2);// 设置合并单元格的列数
			cell.setFixedHeight(17f);
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			cell = new PdfPCell();//左空白
			cell.setFixedHeight(17f);
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			if(this.getStartDate()!=null){
				String birthday = sdf.format(this.getStartDate());
				String[] bir = birthday.split("-");
				cell = new PdfPCell(new Paragraph("  "+bir[0]+"年"+bir[1]+"月"+bir[2]+"日",headFont10));// 建立一个单元格
			}else{
				cell = new PdfPCell(new Paragraph("",headFont10));// 建立一个单元格
			}
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居左显示
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setFixedHeight(17f);
			cell.setColspan(2);// 设置合并单元格的列数
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			cell = new PdfPCell();//右空白
			cell.setFixedHeight(17f);
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			//第7行
			cell = new PdfPCell();//左空白
			cell.setFixedHeight(28.3f);
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
//			cell = new PdfPCell(new Paragraph("_______________",headFont10));// 建立一个单元格
			cell = new PdfPCell(new Paragraph(" "+peStudent.getTrueName(),headFont12));// 建立一个单元格
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居左显示
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setColspan(2);// 设置合并单元格的列数
			cell.setFixedHeight(28.3f);
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			cell = new PdfPCell();//右空白
			cell.setFixedHeight(28.3f);
			cell.setColspan(4);// 设置合并单元格的列数
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			//第8行
			cell = new PdfPCell();//左空白
			cell.setFixedHeight(28.3f);
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
//			cell = new PdfPCell(new Paragraph("_______________",headFont10));// 建立一个单元格
			cell = new PdfPCell(new Paragraph(" 网路教育学院",headFont12));// 建立一个单元格
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居左显示
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setFixedHeight(28.3f);
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			cell = new PdfPCell();//左空白
			cell.setFixedHeight(28.3f);
			cell.setColspan(2);// 设置合并单元格的列数
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			if(this.getFaDate()!=null){
				String birthday = sdf.format(this.getFaDate());
				String[] bir = birthday.split("-");
				cell = new PdfPCell(new Paragraph("   "+bir[0]+"      "+bir[1]+"     "+bir[2],headFont10));// 建立一个单元格
			}else{
				cell = new PdfPCell(new Paragraph("",headFont10));// 建立一个单元格
			}
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居左显示
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setFixedHeight(28.3f);
			cell.setColspan(2);// 设置合并单元格的列数
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			cell = new PdfPCell();//右空白
			cell.setFixedHeight(28.3f);
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			//第9行
			cell = new PdfPCell();//空白行
			cell.setFixedHeight(52.9f);
			cell.setColspan(7);// 设置合并单元格的列数
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			document.add(table);
			document.newPage();
			}
			
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
		document.close();
		
		DataOutput output;
		try {
			output = new DataOutputStream(response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
		byte[] bytes = baos.toByteArray();
		response.setContentType("application/pdf");
		response.reset();
		response.setContentLength(bytes.length);
		session.setAttribute("bytes", bytes);
		
		return "pdfStuCard";
	}
	
	/**
	 * 根据长度和字号取得空格个数
	 * @param length  长度
	 * @param font   字号
	 * @return
	 */
	private double blankNum(double length, int font){
		double a = length/(font*0.075);
		return a;
	}
	
	private String blank(double num){
		String str = "";
		for(int i = 0; i < (num-1) ; i++){
			str += " ";
		}
		return str;
	}
	
	public String printBYZS(){
		String[] ids = this.getIds().split(",");
		List idList = new ArrayList();
		
		for (int i = 0; i < ids.length; i++) {
			idList.add(ids[i]);
		}
		
		List<PeStudent> stuList = this.getMyListService().getByIds(PeStudent.class, idList);
		for (PeStudent peStudent : stuList) {
			
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//生成pdf文件
		
		HttpServletResponse response=ServletActionContext.getResponse();
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session= request.getSession();
		
		int a = 0;//上边距系数 （-8 < a < 62）
		int b = 0;//右边距系数 (-8 < b < 82)
		float width = 215 + b;//8, 24,43,10,23,21,21,41,16,8 
		float tableWidth = (float)(width*72/25.4);
		float hight = 148f; 
		float hight1 = (float)((8+a)*72/25.4);
		float hight2 = (float)(55*72/25.4);
		float hight3 = (float)(9*72/25.4);
		float hight4 = (float)(12*72/25.4);
		float hight5 = (float)(12*72/25.4);
		float hight6 = (float)(20*72/25.4);
		float hight7 = (float)(12*72/25.4);
		float hight8 = (float)(7*72/25.4);
		float hight9 = (float)(5*72/25.4);
		float hight10 = (float)(7*72/25.4);
		Rectangle pSize=new Rectangle(842,595f);//A4纸张595*842 mm*72/25.4  148/215 共10列 610,420f
		Document document = new Document(pSize,0, 0, 0, 0);// 建立一个Document对象
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			PdfWriter pdfWriter = PdfWriter.getInstance(document,baos);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		final int DEFAULT_BORDER_WIDTH = 0;
		try {
			document.open();
			BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);// 设置中文字体
			Font headFont10 = new Font(bfChinese, 10, Font.NORMAL);// 设置字体大小
			Font headFont8 = new Font(bfChinese, 8, Font.BOLD);// 设置字体大小
			Font headFont9 = new Font(bfChinese, 9, Font.NORMAL);// 设置字体大小
			Font headFont11 = new Font(bfChinese, 11, Font.NORMAL);// 设置字体大小
			Font headFont12 = new Font(bfChinese, 12, Font.NORMAL);// 设置字体大小
			Font headFont12B = new Font(bfChinese, 12, Font.BOLD);// 设置字体大小
			Font headFont14 = new Font(bfChinese, 14, Font.NORMAL);// 设置字体大小
			Font headFont14B = new Font(bfChinese, 14, Font.BOLD);// 设置字体大小
			//float[] widths = { 16f,45.355f, 181.418f, 53.858f, 68.032f,40.252f};// 设置表格的列宽
			//8, 24,43,10,23,21,21,41,16,8   行高：8,55,9,11,12,20,13,7,4,8 换算后（22.68,155.9,25.5,31.18,34,56.7,36.85,19.84,11.34,22.68）
			float[] widths = { 8/width, 24/width, 43/width, 10/width, 23/width, 21/width, 21/width, 41/width, 16/width, (8+b)/width};// 设置表格的列宽
			
			for (PeStudent peStudent : stuList) {
				
			PdfPTable table = new PdfPTable(widths);// 建立一个pdf表格			
			//table.setSpacingBefore(130f);// 设置表格上面空白宽度
			table.setTotalWidth(tableWidth);// 设置表格的宽度
			table.setLockedWidth(true);// 设置表格的宽度固定
			
//			table.getDefaultCell().setBorder(1);//设置表格默认为无边框
			PdfPCell cell = new PdfPCell();//空白行
			// cell.setBorder(0);
//			if(i%3==0){
//				cell.setFixedHeight(70);
//			}else{
//				cell.setFixedHeight(73);
//			}
			//第一行
			cell.setFixedHeight(hight1);
			cell.setColspan(10);// 设置合并单元格的列数
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			//第二行
			cell.setFixedHeight(hight2);
			cell.setColspan(10);// 设置合并单元格的列数
			cell.setBorder(1);
			table.addCell(cell);
			
			//第三行
			cell = new PdfPCell();//左空白
			cell.setFixedHeight(hight3);
			cell.setColspan(2);// 设置合并单元格的列数
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("_" + peStudent.getTrueName(),headFont12));// 建立一个单元格
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居左显示
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(peStudent.getPrStudentInfo().getGender(),headFont12));// 建立一个单元格
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居左显示
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			String birthday = sdf.format(peStudent.getPrStudentInfo().getBirthday());
			birthday = DateConvertor.formatStr(birthday);
			birthday = birthday.replace("年", "-");
			birthday = birthday.replace("月", "-");
			birthday = birthday.replace("日", "-");
			String[] bir = birthday.split("-");
			cell = new PdfPCell(new Paragraph(bir[0] + "_",headFont12));// 建立一个单元格
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);// 设置内容水平居左显示
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph( bir[1] + "_",headFont12));// 建立一个单元格
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);// 设置内容水平居左显示
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph( bir[2] + "_",headFont12));// 建立一个单元格
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);// 设置内容水平居左显示
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			String start = sdf.format(peStudent.getPeGrade().getBeginDate());
			start = DateConvertor.formatStr(start);
			start = start.substring(0, start.indexOf("月"));
			start = start.replace("年", "-");
			String[] startD = start.split("-");
			
			cell = new PdfPCell(new Paragraph(startD[0] + "_",headFont12));// 建立一个单元格
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);// 设置内容水平居左显示
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			cell = new PdfPCell();//右空白
			cell.setColspan(2);// 设置合并单元格的列数
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			//第四行
			cell = new PdfPCell();//左空白
			cell.setFixedHeight(hight4);
			cell.setBorder(1);
			table.addCell(cell);
			
			if(peStudent.getDegreeDate()!=null){
				String end = sdf.format(peStudent.getDegreeDate());
				end = DateConvertor.formatStr(end);
				end = end.substring(0, end.indexOf("月"));
				end = end.replace("年", "-");
				String[] endD = end.split("-");
				cell = new PdfPCell(new Paragraph("________" + startD[1] + "________" + endD[0] + "______" + endD[1] + "__________" + peStudent.getPeMajor().getName(),headFont12));//内容
			}else {
				cell = new PdfPCell(new Paragraph(this.blank(this.blankNum(25, 12)-5) + startD[1] + this.blank(this.blankNum(31, 12)-20) +"二零零八                七                                   " + peStudent.getPeMajor().getName(),headFont12));//内容
			}
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居左显示
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setColspan(8);// 设置合并单元格的列数
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			cell = new PdfPCell();//右空白
			cell.setBorder(1);
			table.addCell(cell);
			
			//第五行
			cell = new PdfPCell();//左空白
			cell.setFixedHeight(hight5);
			cell.setBorder(1);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(this.blank(this.blankNum(25, 12)-5) + "四",headFont12));//内容
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居左显示
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setColspan(8);// 设置合并单元格的列数
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			cell = new PdfPCell();//右空白
			cell.setBorder(1);
			table.addCell(cell);
			
			
		
			
			//第六行
			cell = new PdfPCell();//左空白
			cell.setFixedHeight(hight6);
			cell.setBorder(1);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph( this.blank(this.blankNum(51, 14)) + "华南师范大学",headFont14B));//内容
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居左显示
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setColspan(8);// 设置合并单元格的列数
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			cell = new PdfPCell();//右空白
			cell.setBorder(1);
			table.addCell(cell);
			
			//第7行
			cell = new PdfPCell();//空白行
			cell.setFixedHeight(hight7);
			cell.setColspan(10);// 设置合并单元格的列数
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			//第8行
			cell = new PdfPCell();//左空白
			cell.setFixedHeight(hight8);
			cell.setBorder(1);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(this.blank(this.blankNum(33, 12))+"120233655545678",headFont12B));//内容
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居左显示
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setColspan(5);// 设置合并单元格的列数
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			//时间暂且用当前日期
			String day = sdf.format(new Date());
			day = DateConvertor.formatStr(day);
			day = day.replace("年", "-");
			day = day.replace("月", "-");
			day = day.replace("日", "-");
			bir = day.split("-");
			
			cell = new PdfPCell(new Paragraph(this.blank(this.blankNum(22, 12)-20) + bir[0] + this.blank(this.blankNum(21, 12)-5*bir[1].length())+bir[1] + this.blank(this.blankNum(22, 12)-5*bir[2].length()) + bir[2],headFont12B));//内容
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居左显示
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setColspan(3);// 设置合并单元格的列数
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			cell = new PdfPCell();//右空白
			cell.setBorder(1);
			table.addCell(cell);
			
			//第9行
			cell = new PdfPCell();//空白行
			cell.setFixedHeight(hight9);
			cell.setColspan(10);// 设置合并单元格的列数
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			
			
			//第10行
			cell = new PdfPCell();//空白行
			cell.setFixedHeight(hight10);
			cell.setColspan(10);// 设置合并单元格的列数
			cell.setBorder(DEFAULT_BORDER_WIDTH);
			table.addCell(cell);
			table.setHorizontalAlignment(Element.ALIGN_RIGHT);
			
			document.add(table);
			document.newPage();
			}
			
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
		document.close();
		
		DataOutput output;
		try {
			output = new DataOutputStream(response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
		byte[] bytes = baos.toByteArray();
		response.setContentType("application/pdf");
		response.reset();
		response.setContentLength(bytes.length);
		session.setAttribute("bytes", bytes);
		
		return "pdfbyzs";
	}
	
	public String getSitename() {
		return sitename;
	}
	public void setSitename(String sitename) {
		this.sitename = sitename;
	}
	public String getGradename() {
		return gradename;
	}
	public void setGradename(String gradename) {
		this.gradename = gradename;
	}
	public String getEdutypename() {
		return edutypename;
	}
	public void setEdutypename(String edutypename) {
		this.edutypename = edutypename;
	}
	public String getMajorname() {
		return majorname;
	}
	public void setMajorname(String majorname) {
		this.majorname = majorname;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public List<PeStudent> getStudents() {
		return students;
	}
	public void setStudents(List<PeStudent> students) {
		this.students = students;
	}
	public Date getFaDate() {
		return faDate;
	}
	public void setFaDate(Date faDate) {
		this.faDate = faDate;
	}
}
