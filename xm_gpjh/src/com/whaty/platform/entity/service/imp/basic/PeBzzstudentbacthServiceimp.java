package com.whaty.platform.entity.service.imp.basic;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Sheet;
import jxl.Workbook;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.PeBzzstudentbacthService;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.util.Const;
import com.whaty.util.RandomString;

public class PeBzzstudentbacthServiceimp implements PeBzzstudentbacthService {

	private GeneralDao<PeBzzStudent> generalDao;
	private Set checkcomnum = new HashSet();
	private Vector vector = new Vector();

	public int Bacthsave(File file,String id) throws EntityException {
		StringBuffer msg = new StringBuffer();
		
		int count = 0;
		int jsnum=0;
		Workbook work = null;
		boolean check = false;
		try {
			work = Workbook.getWorkbook(file);
		} catch (Exception e) {
			e.printStackTrace();
			msg.append("Excel表格读取异常！批量添加失败！<br/>");
			try {
				throw new EntityException(msg.toString());
			} catch (EntityException e1) {
				e1.printStackTrace();
			}
		}
		Sheet sheet = work.getSheet(0);
		int rows = sheet.getRows();
		if (rows < 4) {
			msg.append("表格为空！<br/>");
		}
		String temp = "";
		String twoname = "";
		String onename ="";
		Set<PeBzzStudent> studentSet = new HashSet<PeBzzStudent>();
		checkcomnum.clear();
		vector.removeAllElements();
		
		for (int i = 3; i < rows; i++) {
			PeBzzStudent student = new PeBzzStudent();
			SsoUser suser = new SsoUser();
			int n = i-2;
			// *姓名
			temp = sheet.getCell(1, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，姓名为空！<br/>");
				continue;
			}
			else if(temp.length()>50)
			{
				msg.append("第" + (i + 1) + "行数据，姓名长度不能超过50！<br/>");
				continue;
			}
			student.setTrueName(temp);

			// 性别
			temp = sheet.getCell(2, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，性别为空！<br/>");
				continue;
			}
			else
			{
				if (temp.equals("男") || temp.equals("女")) {
					student.setGender(temp);
				} else {
					msg.append("第" + (i + 1) + "行数据，性别只能为男/女！<br/>");
					continue;
				}
			}
			
			// 民族
			temp = sheet.getCell(3, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				//temp="--";
				msg.append("第" + (i + 1) + "行数据，民族为空！<br/>");
				continue;
			}
			else if(temp.length()>50)
			{
				msg.append("第" + (i + 1) + "行数据，民族长度不能超过50！<br/>");
				continue;
			}
			student.setFolk(temp);
			
			// 学历
			temp = sheet.getCell(4, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				//temp="--";
				msg.append("第" + (i + 1) + "行数据，学历为空！<br/>");
				continue;
			}
			else
			{
			//String test = "[初中]、[高中]、[职高]、[中专]、[技校]、[大专]、[本科]、[硕士]、[博士]";
				if(temp.equals("初中") ||temp.equals("高中")||temp.equals("职高")||temp.equals("中专")||temp.equals("技校")||temp.equals("大专")||temp.equals("大本") ||temp.equals("硕士")||temp.equals("博士")){
					//student.setEducation(temp);
				}else{
					msg.append("第" + (i + 1) + "行数据，学历不在范围之内空！<br/>");
				}
			}
			student.setEducation(temp);
			
			// 出生日期
			temp = sheet.getCell(5, i).getContents().trim();
			if(temp.length()!=8){
				msg.append("第" + (i + 1) + "行数据，出生日期格式错误！<br/>");
				continue;
			}
			String year = temp.substring(0,4);
			String month = temp.substring(4,6);
			String day = temp.substring(6,8);
			if(!isNumeric(year) || !isNumeric(month) ||!isNumeric(day) )
			{
				msg.append("第" + (i + 1) + "行数据，出生日期不为整数！<br/>");
				continue;
			}
			int imonth = Integer.parseInt("1"+month);
			int iday = Integer.parseInt("1"+day);
			if(imonth>112||iday>132){
				msg.append("第" + (i + 1) + "行数据，出生日期错误！<br/>");
				continue;
			}
			
			temp = year+"-"+month+"-"+day;
			Date birthday = this.StringtoDate(temp, "yyyy-MM-dd");
			student.setBirthdayDate(birthday);
			
			

			// *所在批次
			DetachedCriteria tempbatchdc = DetachedCriteria.forClass(PeBzzBatch.class);
			tempbatchdc.add(Restrictions.eq("id", id));
			PeBzzBatch bzzBatch = this.getGeneralDao().getPeBzzBatch(tempbatchdc);
			student.setPeBzzBatch(bzzBatch);

			// *所在集团
			onename = sheet.getCell(6, i).getContents().trim();
			twoname = sheet.getCell(7, i).getContents().trim();
			if((onename.length()>1&&twoname.length()>1)){
				temp = twoname;
			}else if((onename.length()>1&&twoname.length()<1)){
				temp = onename;
			}
			
			DetachedCriteria criteria = DetachedCriteria
					.forClass(PeEnterprise.class);
			criteria.add(Restrictions.eq("name", temp));
			List<PeEnterprise> list = this.generalDao.getList(criteria);
			if (list.size() == 0) {
				msg.append("第" + (i + 1) + "行数据，当前工作单位信息不存在！<br/>");
				continue;
			}
			student.setPeEnterprise(list.get(0));
			String comnum="";
			
			DetachedCriteria teria = DetachedCriteria
			.forClass(PeEnterprise.class);
			teria.add(Restrictions.eq("name", temp));
			List<PeEnterprise> enlist = this.generalDao.getList(teria);	
			String tempcom="";
			int start =0;
			int end =0;
			List<PeEnterprise> blist=null;
			//如果是2级企业
			if(enlist.get(0).getPeEnterprise()!=null){
				DetachedCriteria adc = DetachedCriteria.forClass(PeEnterprise.class);
				adc.createCriteria("peEnterprise", "peEnterprise");
				adc.add(Restrictions.eq("id",list.get(0).getId()));
				blist= this.generalDao.getList(adc);
				tempcom = blist.get(0).getPeEnterprise().getCode();
				start = tempcom .length()-3;
				end = tempcom.length();
				comnum =tempcom.substring(start, end);
			}else{
				start = enlist.get(0).getCode().length()-3;
				end = enlist.get(0).getCode().length();
				comnum =enlist.get(0).getCode().substring(start, end);
			}
			
			String Loginid = this.getCardsNo(comnum,n,true);
			
			
			student.setRegNo(Loginid);
			SsoUser sso = new SsoUser();
			sso.setLoginId(Loginid);
			String pwsd = RandomString.getString(8);
			sso.setPassword(pwsd);
			sso.setEnumConstByFlagIsvalid(this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsvalid", "1"));
			DetachedCriteria dc = DetachedCriteria.forClass(PePriRole.class);
			dc.add(Restrictions.eq("name", SsoConstant.SSO_DEFAULT_STUDENT_ROLE_NAME));
			List rl = this.getGeneralDao().getList(dc);
			if(rl != null ){
				PePriRole role = (PePriRole)rl.get(0);
				sso.setPePriRole(role);
			}
			
			student.setName(Loginid+"/"+student.getTrueName());
			student.setSsoUser(sso);
		
			// 移动电话
			
			
			int k=0;
			temp = sheet.getCell(8, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				//msg.append("第" + (i + 1) + "行数据，手机为空！<br/>");
				//check = true;
				k+=1;
				//continue;
			}
			else if(temp.length()>20)
			{
				msg.append("第" + (i + 1) + "行数据， 移动电话长度不能超过20！<br/>");
				continue;
			}
			//if (temp!=null && !temp.equals("")&& !this.isMobile(temp)) {
				//msg.append("第" + (i + 1) + "行数据，手机格式错误！<br/>");
				//continue;
			//}
			student.setMobilePhone(temp);
			// 办公电话
			
			temp = sheet.getCell(9, i).getContents().trim();
			/*if (temp!=null && !temp.equals("")&&!isTelephone(temp)) {
				msg.append("第" + (i + 1) + "行数据，办公电话格式错误！."+temp+".<br/>");
				continue;
			}*/
			if (temp == null || "".equals(temp)) {
				k+=1;
			}
			else if(temp.length()>50)
			{
				msg.append("第" + (i + 1) + "行数据， 办公电话长度不能超过50！<br/>");
				continue;
			}
			if(k==2){
				msg.append("第" + (i + 1) + "行数据，联系电话至少填一项！<br/>");
				continue;
			}
			
			student.setPhone(temp);
			
			// 电子邮件
			temp = sheet.getCell(10, i).getContents().trim();
			/*if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，E-mail为空！<br/>");
				continue;
			}*/
			/*if (temp!=null && !temp.equals("")&& !isEmail(temp)) {
				msg.append("第" + (i + 1) + "行数据，E-mail格式错误！<br/>");
				continue;
			}*/
			if(temp.length()>50)
			{
				msg.append("第" + (i + 1) + "行数据， 电子邮件长度不能超过50！<br/>");
				continue;
			}
			
			student.setEmail(temp);
			
			// 年龄
			Date now = new Date();
			int tage = this.getDaysOfTowDiffDate(birthday, now);
			if(tage>18&&tage<100){
				student.setAge((tage+"").trim());
			}
			// 具体职业
		
			String  tempc = "---";

			
			student.setPosition(tempc);

			student.setTitle(tempc);

		
			student.setDepartment(tempc);

			// 地址

			student.setAddress(tempc);

			// 邮编

			student.setZipcode(tempc);
			
			if (!studentSet.add(student)) {
				msg.append("第" + (i + 1) + "行数据与文件中前面的数据重复！<br/>");
				continue;
			}
		}

		if (msg.length() > 0) {
			msg.append("学生报名信息批量上传失败，请修改以上错误之后重新上传！<br/>");
			throw new EntityException(msg.toString());
		}

		for (PeBzzStudent student : studentSet) {
			try {
				this.getGeneralDao().save(student);
				count++;
			} catch (Exception e) {
				e.printStackTrace();

					throw new EntityException("批量上传学生报名信息失败");

			}
		}
		checkcomnum.clear();
		vector.removeAllElements();
		return count;
	}
	
	/**
	 * 自动计算年龄
	 * 
	 */
	
	private Date StringtoDate(String temp,String format){
		DateFormat  df  =  new SimpleDateFormat(format);
		Date tempDate = null;
		try {
			tempDate = df.parse(temp);
		} catch (ParseException e) {
			System.out.println("格式错误");
			e.printStackTrace();
		}
		return tempDate;
	}
	
	private  int getDaysOfTowDiffDate(Date sdate,Date edate){
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdate);
		Long begintime =calendar.getTimeInMillis();
		calendar.setTime(edate);
		Long endtime =calendar.getTimeInMillis();
		int betweenDays = (int) ( ( endtime- begintime ) / ( 1000 * 60 * 60 * 24*365 ) );
	   return betweenDays;
	}
	
	
	/***
	 * 自动生成学号
	 * 根据企业重新生成
	 * @param comnum 
	 * @param i 
	 */
		private String getCardsNo(String comnum,int i,boolean flag){
			GregorianCalendar gc = new GregorianCalendar();
			int nowYear = gc.get(Calendar.YEAR);
			
			String newRrgNO = "";
			String testno =nowYear+comnum;
			DetachedCriteria chdc = DetachedCriteria.forClass(PeBzzStudent.class);
			chdc.setProjection(Projections.max("regNo"));
			chdc.add(Restrictions.like("regNo",testno,MatchMode.START));
			List mlist = this.generalDao.getList(chdc);
			
			String tempno="";
			if(flag){
			    i = this.getcount(comnum);
			}
			//如果学号为空
			if(mlist.size()==0||mlist.get(0)==null||mlist.get(0).equals("null")||mlist.get(0).equals("")){
			    if(i<10){
				newRrgNO=testno+"0000"+i;
			    }
			    if(10 <= i&&i <100){
				newRrgNO=testno+"000"+i;
			    }
			    if(100 <= i&&i <1000){
				newRrgNO=testno+"00"+i;
			    }
			    if(1000 <= i&&i <10000){
				newRrgNO=testno+"0"+i;
			    }
			    if(10000 <= i&&i <100000){
				newRrgNO=testno+i;
			    }
			}else{
				/*
				 * 如果学号不为空
				 * 则先取得匹配当前年份当前企业的最大学号
				 */
				String stem ="1"+mlist.get(0).toString().substring(7, 12);
				int tem = Integer.parseInt(stem)+i;
				String testRrgNO = (tem+"");
				newRrgNO = (testno+testRrgNO.trim().substring(1, testRrgNO.length())).trim();
				
			}
			return newRrgNO;
		}
		
		private int getcount(String comnum){
			String tempnum = "";
			int result =0;
			if(checkcomnum.add(comnum)){
				vector.addElement(comnum+"1");
				result = 1;
			}else{
			for(int m = 0 ; m<vector.size();m++){
				if((vector.get(m).toString()).contains(comnum)){
					tempnum =	vector.get(m).toString().substring(3);
					int k =Integer.parseInt(tempnum)+1;
						vector.setElementAt(comnum+k, m);
						result = k;
					}
				}
			}
			
			return result;
		}

	/**
	 * 验证日期
	 * 
	 * @param date
	 * @return
	 */
	
	private static boolean isDate(String brithday) {
		if (brithday == null)
			return false;
		else
			return brithday.matches(Const.checkdate);
	}

	/**
	 * 校验手机号码
	 * 
	 * @param mobile
	 * @return
	 */
	public boolean isMobile(String mobile) {
		if (mobile == null)
			return false;
		else
			return mobile.matches(Const.mobile);
	}

	/**
	 * 校验email格式
	 * 
	 * @param email
	 *            email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (email == null)
			return false;
		else
			return email.matches(Const.email);
	}

	/**
	 * 校验邮编格式
	 * 
	 * @param zip
	 *            zip
	 * @return
	 */
	public static boolean isZIP(String zip) {
		if (zip == null)
			return false;
		else
			return zip.matches(Const.zip);
	}

	public static boolean isTelephone(String telephone) {
		if (telephone == null)
			return false;
		else
			return telephone.matches(Const.telephone);
	}

	public GeneralDao<PeBzzStudent> getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao<PeBzzStudent> generalDao) {
		this.generalDao = generalDao;
	}


	public PeBzzStudent save(PeBzzStudent transientInstance) throws EntityException {
		PeBzzStudent instance = null;
		boolean flag =false;
	 String comnum ="";
	 comnum= transientInstance.getPeEnterprise().getName();
	 DetachedCriteria comdc = DetachedCriteria.forClass(PeEnterprise.class);
	 comdc.add(Restrictions.eq("name", comnum));
	 List<PeEnterprise> list = this.getGeneralDao().getList(comdc);
	 String tempcom="";
		int start =0;
		int end =0;
	List<PeEnterprise> blist=null; 
	 if(list.get(0).getPeEnterprise()!=null){
			DetachedCriteria adc = DetachedCriteria.forClass(PeEnterprise.class);
			adc.createCriteria("peEnterprise", "peEnterprise");
			adc.add(Restrictions.eq("id",list.get(0).getId()));
			blist= this.generalDao.getList(adc);
			tempcom = blist.get(0).getPeEnterprise().getCode();
			start = tempcom .length()-3;
			end = tempcom.length();
			comnum =tempcom.substring(start, end);
		}else{
			start = list.get(0).getCode().length()-3;
			end = list.get(0).getCode().length();
			comnum =list.get(0).getCode().substring(start, end);
		}
	 String loginid = this.getCardsNo(comnum, 1,false);
	 SsoUser ssoUser = new SsoUser();
	 ssoUser.setLoginId(loginid);
	 String pwsd = RandomString.getString(8);
	 ssoUser.setPassword(pwsd);
	 ssoUser.setEnumConstByFlagIsvalid(this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsvalid", "1"));
	 try{
		DetachedCriteria dc = DetachedCriteria.forClass(PePriRole.class);
		dc.add(Restrictions.eq("name", SsoConstant.SSO_DEFAULT_STUDENT_ROLE_NAME));
		List rl = this.getGeneralDao().getList(dc);
		if(rl != null ){
			PePriRole role = (PePriRole)rl.get(0);
			ssoUser.setPePriRole(role);
		}
		this.getGeneralDao().saveSsoUser(ssoUser);
		transientInstance.setRegNo(loginid);
		transientInstance.setName(loginid+"/"+transientInstance.getTrueName());
		transientInstance.setSsoUser(ssoUser);
		instance = (PeBzzStudent)this.generalDao.save(transientInstance);
		if(instance.getId()!=null){
			flag =true;
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	checkcomnum.clear();
	vector.removeAllElements();
	
	return instance;
	}
	
	public int deleteByIds(List idList) throws EntityException {
		for (Iterator iter = idList.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			PeBzzStudent peBzzStudent = (PeBzzStudent) this.getGeneralDao().getById(id);
			SsoUser ssoUser = peBzzStudent.getSsoUser();
			try {
				this.getGeneralDao().delete(peBzzStudent);
				this.getGeneralDao().delete(ssoUser);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return idList.size();
	}
	
	public void updateSsoUser(SsoUser ssoUser) {
		this.getGeneralDao().updateSsoUser(ssoUser);
	}
	
	public boolean isNumeric(String str)
	{
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if(!isNum.matches())
		{
			return false;
		}
			return true;
	} 
}
