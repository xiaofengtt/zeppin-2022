package com.whaty.platform.entity.service.imp.basic;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.PeEnterpriseBacthService;
import com.whaty.platform.util.Const;
import com.whaty.util.RandomString;
import com.whaty.util.string.AttributeManage;
import com.whaty.util.string.WhatyAttributeManage;

public class PeEnterpriseBacthServiceImpl implements PeEnterpriseBacthService {

	private GeneralDao<PeEnterprise> generalDao;

	public int Bacthsave(File filetest)throws EntityException {
		StringBuffer msg = new StringBuffer();
		int count = 0;

		Workbook work = null;
		try {
			work = Workbook.getWorkbook(filetest);
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
		if (rows < 2) {
			msg.append("表格为空！<br/>");
		}
		String temp = "";
		Set<PeEnterprise> enterpriseSet = new HashSet();
		Set<String> cards = new HashSet();
		for (int i = 1; i < rows; i++) {
			int n= 0;
			boolean flag = false;
			PeEnterprise enterprise = new PeEnterprise();
			// 判断是否是企业编号
			temp = sheet.getCell(0, i).getContents().trim();
			if(temp==""||temp.equals("")){
				msg.append("第" + (i + 1) + "行数据，第1列数据为空！<br/>");
				continue;
			}else{
				flag = this.CheckTemp(temp);
				if(!flag){
					DetachedCriteria testdc = DetachedCriteria.forClass(PeEnterprise.class);
					testdc.add(Restrictions.eq("code", temp));
					List<PeEnterprise> list = this.generalDao.getList(testdc);
					if(list.size()==0||list.get(0)==null){
						n = 1;
						enterprise.setCode(temp);
					}else{
						msg.append("第" + (i + 1) + "行数据，企业编号已有重复数据！<br/>");
						continue;
					}
				}
			}

			// *企业名称
			temp = sheet.getCell(n, i).getContents().trim();
//			System.out.println("n// *企业名称========"+temp+"======="+n);
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，企业名称为空！<br/>");
				continue;
			}else{
				if(temp.length()>100)
				{
					msg.append("第" + (i + 1) + "行数据，企业名称长度不能超过100！<br/>");
					continue;
				}
				DetachedCriteria testdc = DetachedCriteria.forClass(PeEnterprise.class);
				testdc.add(Restrictions.eq("name", temp));
				List<PeEnterprise> list = this.generalDao.getList(testdc);
				if(list.size()==0||list.get(0)==null){
					enterprise.setName(temp);
				}else{
					msg.append("第" + (i + 1) + "行数据，企业名称已有重复数据！<br/>");
					continue;
				}
			}
			
			
			// 所属行业
			temp = sheet.getCell(++n, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				temp = "--";
				//msg.append("第" + (i + 1) + "行数据，所属行业为空！<br/>");
				//continue;
			}
			else if(temp.length()>100)
			{
				msg.append("第" + (i + 1) + "行数据，行业长度不能超过100！<br/>");
				continue;
			}
			enterprise.setIndustry(temp);
		
			// *通讯地址
			temp = sheet.getCell(++n, i).getContents().trim();
//			System.out.println("n通讯地址========"+temp+"======="+n);
			if (temp == null || "".equals(temp)) {
				temp = "--";
				//continue;
			}
			else if(temp.length()>100)
			{
				msg.append("第" + (i + 1) + "行数据，通讯地址长度不能超过150！<br/>");
				continue;
			}
			
			enterprise.setAddress(temp);
			
			// 邮编
			temp = sheet.getCell(++n, i).getContents().trim();
			if ((temp == null || "".equals(temp))) {
				temp = "--";
				//continue;
			}
			else if(temp.length()>10)
			{
				msg.append("第" + (i + 1) + "行数据，邮编长度不能超过10！<br/>");
				continue;
			}
				
			enterprise.setZipcode(temp);
			
			// 传真
			temp = sheet.getCell(++n, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				temp = "--";
				//continue;
			}
			else if(temp.length()>20)
			{
				msg.append("第" + (i + 1) + "行数据，传真长度不能超过20！<br/>");
				continue;
			}
			enterprise.setFax(temp);
		
			// *上级企业
			if(flag){
				
			temp = sheet.getCell(++n, i).getContents().trim();
			if (!(temp == null || "".equals(temp))) {
				DetachedCriteria criteria = DetachedCriteria
						.forClass(PeEnterprise.class);
				criteria.add(Restrictions.eq("name", temp));
				List<PeEnterprise> list = this.generalDao.getList(criteria);
				if (list.size() == 0) {
					msg.append("第" + (i + 1) + "行数据，当前企业信息下无此企业！<br/>");
					continue;
				}else{
					DetachedCriteria temdc =DetachedCriteria.forClass(PeEnterprise.class);
					temdc.add(Restrictions.eq("name", temp));
					List<PeEnterprise> pelist = this.generalDao.getList(temdc);
					if(pelist.size()==0||pelist.get(0)==null){
						msg.append("第" + (i + 1) + "行数据,上级企业信息不存在！<br/>");
						continue;
					}else{
						PeEnterprise peEnterprise = (PeEnterprise) pelist.get(0);
						String subcode = codenum(peEnterprise.getCode(),i);	
						enterprise.setCode(subcode);
					}
				}
				enterprise.setPeEnterprise(list.get(0));
			}
			}

			

			// 负责人姓名
			temp = sheet.getCell(++n, i).getContents().trim();
//			System.out.println("n负责人姓名======"+temp+"========="+n);
			/*if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，负责人姓名为空！<br/>");
				continue;
			}*/
			if(temp!=null && temp.length()>25)
			{
				msg.append("第" + (i + 1) + "行数据，负责人姓名长度不能超过25！<br/>");
				continue;
			}
			enterprise.setFzrName(temp);
			
			// 负责人性别
			temp = sheet.getCell(++n, i).getContents().trim();
//			System.out.println("n负责人性别======="+temp+"========"+n);
			/*if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，性别为空！<br/>");
				continue;
			}
			if (temp.equals("男") || temp.equals("女")) {
				enterprise.setFzrXb(temp);
			} else {
				msg.append("第" + (i + 1) + "行数据，负责人性别只能为男/女！<br/>");
				continue;
			}*/
			if(temp!=null && temp.length()>5)
			{
				msg.append("第" + (i + 1) + "行数据，负责人性别长度不能超过5！<br/>");
				continue;
			}
			enterprise.setFzrXb(temp);
			
			//负责人部门
			
			temp = sheet.getCell(++n, i).getContents().trim();
//			System.out.println("n负责人部门======="+temp+"========"+n);
			if (temp == null || "".equals(temp)) {
				temp = "--";
				//continue;
			}
			if(temp!=null && temp.length()>100)
			{
				msg.append("第" + (i + 1) + "行数据，负责人部门长度不能超过100！<br/>");
				continue;
			}
			enterprise.setFzrDepart(temp);
			
			// 负责人职务
			temp = sheet.getCell(++n, i).getContents().trim();
//			System.out.println("n负责人职务========"+temp+"======="+n);
			if (temp == null || "".equals(temp)) {
				temp = "--";
				//continue;
			}
			if(temp!=null && temp.length()>25)
			{
				msg.append("第" + (i + 1) + "行数据，负责人职务长度不能超过25！<br/>");
				continue;
			}
			enterprise.setFzrPosition(temp);
			
			// 负责人办公电话
			temp = sheet.getCell(++n, i).getContents().trim();
			if(temp!=null && temp.length()>25)
			{
				msg.append("第" + (i + 1) + "行数据，负责人办公电话长度不能超过25！<br/>");
				continue;
			}
//			System.out.println("n负责人办公电话======="+temp+"========"+n);
			enterprise.setFzrPhone(temp);
			
			// 负责人移动电话
			temp = sheet.getCell(++n, i).getContents().trim();
//			System.out.println("n负责人移动电话========"+temp+"======="+n);
			/*if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，手机为空！<br/>");
				continue;
			}
			if (!this.isMobile(temp)) {
				msg.append("第" + (i + 1) + "行数据，手机格式错误！<br/>");
				continue;
			}*/
			if(temp!=null && temp.length()>25)
			{
				msg.append("第" + (i + 1) + "行数据，负责人移动电话长度不能超过25！<br/>");
				continue;
			}
			enterprise.setFzrMobile(temp);
			
			// 负责人电子邮件
			temp = sheet.getCell(++n, i).getContents().trim();
//			System.out.println("n负责人电子邮件========="+temp+"======"+n);
			/*if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，E-mail为空！<br/>");
				continue;
			}
			if (!isEmail(temp)) {
				msg.append("第" + (i + 1) + "行数据，E-mail格式错误！<br/>");
				continue;
			}*/
			if(temp!=null && temp.length()>25)
			{
				msg.append("第" + (i + 1) + "行数据，负责人电子邮件长度不能超过25！<br/>");
				continue;
			}
			enterprise.setFzrEmail(temp);
			
			// 负责人通讯地址
			temp = sheet.getCell(++n, i).getContents().trim();
//			System.out.println("n负责人通讯地址========"+temp+"======="+n);
			if (temp == null || "".equals(temp)) {
				temp = "--";
				//continue;
			}
			if(temp!=null && temp.length()>150)
			{
				msg.append("第" + (i + 1) + "行数据，负责人通讯地址长度不能超过150！<br/>");
				continue;
			}
			enterprise.setFzrAddress(temp);
			
			// 联系人姓名
			temp = sheet.getCell(++n, i).getContents().trim();
//			System.out.println("n 联系人姓名========"+temp+"======"+n);
			int age = 0;
			/*if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据， 联系人姓名为空！<br/>");
				continue;
			}*/
			if(temp!=null && temp.length()>25)
			{
				msg.append("第" + (i + 1) + "行数据，联系人姓名长度不能超过25！<br/>");
				continue;
			}
			enterprise.setLxrName(temp);
			
			// 联系人性别
			temp = sheet.getCell(++n, i).getContents().trim();
//			System.out.println("n// 联系人性别======="+temp+"========"+n);
			//System.out.println("temp========>" + temp);
			/*if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，性别为空！<br/>");
				continue;
			}
			if (temp.equals("男") || temp.equals("女")) {
				//enterprise.setLxrXb(temp);
			} else {
				msg.append("第" + (i + 1) + "行数据，负责人性别只能为男/女！<br/>");
				continue;
			}*/
			if(temp!=null && temp.length()>5)
			{
				msg.append("第" + (i + 1) + "行数据，联系人性别长度不能超过5！<br/>");
				continue;
			}
			enterprise.setLxrXb(temp);
			
			//联系人部门
			
			temp = sheet.getCell(++n, i).getContents().trim();
//			System.out.println("n联系人部门========="+temp+"======"+n);
			if (temp == null || "".equals(temp)) {
				temp = "--";
				//continue;
			}
			if(temp!=null && temp.length()>100)
			{
				msg.append("第" + (i + 1) + "行数据，联系人部门长度不能超过100！<br/>");
				continue;
			}
			
			// 联系人职务
			temp = sheet.getCell(++n, i).getContents().trim();
//			System.out.println("n联系人职务======="+temp+"========"+n);
			if (temp == null || "".equals(temp)) {
				temp = "--";
				//continue;
			}
			if(temp!=null && temp.length()>25)
			{
				msg.append("第" + (i + 1) + "行数据，联系人职务长度不能超过25！<br/>");
				continue;
			}
			enterprise.setLxrPosition(temp);
			
			// 联系人办公电话
			temp = sheet.getCell(++n, i).getContents().trim();
//			System.out.println("n联系人办公电话====="+temp+"=========="+n);
//			if (!(temp == null || "".equals(temp))) {
//				if (!isTelephone(temp)) {
//					msg.append("第" + (i + 1) + "行数据，办公电话格式错误！<br/>");
//					continue;
//				}
//			}
			if(temp!=null && temp.length()>25)
			{
				msg.append("第" + (i + 1) + "行数据，联系人办公电话长度不能超过25！<br/>");
				continue;
			}
			enterprise.setLxrPhone(temp);
			
			// 联系人移动电话
			temp = sheet.getCell(++n, i).getContents().trim();
//			System.out.println("n 联系人移动电话====== "+temp+"========="+n);
			/*if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，手机为空！<br/>");
				continue;
			}
			if (!this.isMobile(temp)) {
				msg.append("第" + (i + 1) + "行数据，手机格式错误！<br/>");
				continue;
			}*/
			if(temp!=null && temp.length()>25)
			{
				msg.append("第" + (i + 1) + "行数据，联系人移动电话长度不能超过25！<br/>");
				continue;
			}
			enterprise.setLxrMobile(temp);
			
			// 联系人电子邮件
			temp = sheet.getCell(++n, i).getContents().trim();
//			System.out.println("n联系人电子邮件======="+temp+"========"+n);
			/*if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，E-mail为空！<br/>");
				continue;
			}
			if (!isEmail(temp)) {
				msg.append("第" + (i + 1) + "行数据，E-mail格式错误！<br/>");
				continue;
			}*/
			if(temp!=null && temp.length()>25)
			{
				msg.append("第" + (i + 1) + "行数据，联系人电子邮件长度不能超过25！<br/>");
				continue;
			}
			enterprise.setLxrEmail(temp);
			
			// 联系人通讯地址
			temp = sheet.getCell(++n, i).getContents().trim();
//			System.out.println("n联系人通讯地址======="+temp+"========"+n);
			if (temp == null || "".equals(temp)) {
				temp = "--";
				//continue;
			}
			if(temp!=null && temp.length()>150)
			{
				msg.append("第" + (i + 1) + "行数据，联系人通讯地址长度不能超过150！<br/>");
				continue;
			}
			enterprise.setLxrAddress(temp);
			
			if (!enterpriseSet.add(enterprise)) {
				msg.append("第" + (i + 1) + "行数据与文件中前面的数据重复！<br/>");
				//continue;
			}
		}

		if (msg.length() > 0) {
			msg.append("信息批量上传失败，请修改以上错误之后重新上传！<br/>");
				throw new EntityException(msg.toString());
		}

		for (PeEnterprise enterprise : enterpriseSet) {
			try {
				this.getGeneralDao().save(enterprise);
				count++;
			} catch (Exception e) {
				e.printStackTrace();
				try {
					throw new EntityException("批量上传信息失败");
				} catch (EntityException e1) {
					e1.printStackTrace();
				}
			}
		}
		return count;
	}

	/**
	 * 验证日期
	 * 
	 * @param date
	 * @return
	 */
	private boolean isDate(String date) {
		AttributeManage manage = new WhatyAttributeManage();
		try {
			String[] strings = date.split("-");
			if (strings.length != 3)
				return false;
			return manage.isValidDate(strings[0], strings[1], strings[2]);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
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

	public GeneralDao<PeEnterprise> getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao<PeEnterprise> generalDao) {
		this.generalDao = generalDao;
	}
	
	public PeEnterprise save(PeEnterprise bean,String code) {
		String subcode = this.codenum(code,1);
		bean.setCode(subcode);
		return this.generalDao.save(bean);
	}
	
	private String codenum(String code,int n){
		String tempcode = null;
		DetachedCriteria codedc =DetachedCriteria.forClass(PeEnterprise.class);
		codedc.createCriteria("peEnterprise", "peEnterprise");
		codedc.add(Expression.neProperty("id", "peEnterprise.id")); 
		codedc.setProjection(Projections.max("code"));
		codedc.add(Restrictions.ilike("code", code, MatchMode.START));
		List list = this.generalDao.getList(codedc);
		if(list.size()==0||list.get(0)==null){
			if(n<10){
				tempcode = code+"00"+n;
			}
			if(n<100&&10<=n){
				tempcode = code+"0"+n;
			}
			if(n<1000&&100<=n){
				tempcode = code+n;
			}
		}else{
			String temp = "1"+ list.get(0).toString().substring(3, 6);
			temp = Integer.parseInt(temp)+n+"";
			tempcode = code+temp.trim().substring(1, temp.length());
		}
		return tempcode;
	}
	
	private boolean CheckTemp(String temp){
		
		boolean  flag = false;
		try{
			int k =Integer.parseInt(temp);
			if(!(temp.length()==3&&k<1000)){
				flag = true;
			}
		}catch (Exception e) {
			flag = true;
		}
		return flag;
	}

}
