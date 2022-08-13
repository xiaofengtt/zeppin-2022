package com.whaty.platform.entity.service.imp.teaching.basicInfo;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeMajor;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.PeTeacher;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.basicInfo.PeTeacherService;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.util.Const;
import com.whaty.util.Exception.IdcardErrorException;
import com.whaty.util.Exception.WhatyUtilException;
import com.whaty.util.string.AttributeManage;
import com.whaty.util.string.WhatyAttributeManage;

public class PeTeacherServiceImp implements PeTeacherService {

	private GeneralDao generalDao;
	
	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}
	
	/**
	 * 批量添加教师信息
	 * @param file
	 * @return
	 * @throws EntityException
	 */
	public int saveBatch(File file) throws EntityException{
		StringBuffer msg = new StringBuffer();
		AttributeManage manage=new WhatyAttributeManage();
		int count = 0;
		
		Workbook work = null;
		
		try {
			work = Workbook.getWorkbook(file);
		} catch (Exception e) {
			e.printStackTrace();
			msg.append("Ecel表格读取异常！批量添加失败！<br/>");
			throw new EntityException(msg.toString());
		}
		Sheet sheet = work.getSheet(0);
		int rows = sheet.getRows();
		if (rows<2) {
			msg.append("表格为空！<br/>");
		}
		
		String temp = "";
		Set<PeTeacher> peTeacherSet = new HashSet();
		Set<String> loginId = new HashSet();
		for(int i=1; i<rows; i++) {
			try {sheet.getCell(15, i).getContents().trim();
				if(sheet.getCell(0, i).getContents().trim().equals("")&&
						sheet.getCell(1, i).getContents().trim().equals("")&&
						sheet.getCell(2, i).getContents().trim().equals("")&&
						sheet.getCell(3, i).getContents().trim().equals("")&&
						sheet.getCell(4, i).getContents().trim().equals("")&&
						sheet.getCell(5, i).getContents().trim().equals("")&&
						sheet.getCell(6, i).getContents().trim().equals("")&&
						sheet.getCell(7, i).getContents().trim().equals("")&&
						sheet.getCell(8, i).getContents().trim().equals("")&&
						sheet.getCell(9, i).getContents().trim().equals("")&&
						sheet.getCell(10, i).getContents().trim().equals("")&&
						sheet.getCell(11, i).getContents().trim().equals("")&&
						sheet.getCell(12, i).getContents().trim().equals("")&&
						sheet.getCell(13, i).getContents().trim().equals("")&&
						sheet.getCell(14, i).getContents().trim().equals("")&&
						sheet.getCell(15, i).getContents().trim().equals("")){
					msg.append("第"+(i+1)+"行数据添加失败，没有输入任何数据！<br/>");
					continue;
				}
			} catch (Exception e1) {
				msg.append("第"+(i+1)+"行数据添加失败，模板错误！<br/>");
				continue;
			}
			try {
				StringBuffer lineMsg = new StringBuffer(); //保存每一行的错误信息
				PeTeacher peTeacher = new PeTeacher();
				//教师姓名
				temp = sheet.getCell(0, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					lineMsg.append("教师姓名不能为空！");
				} else {
					peTeacher.setTrueName(temp);
				}
				//用户名
				temp = sheet.getCell(1, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					lineMsg.append("用户名不能为空！");
				} else {
					DetachedCriteria dcSsoUser = DetachedCriteria.forClass(SsoUser.class);
					dcSsoUser.add(Restrictions.eq("loginId", temp));
					List list = this.getGeneralDao().getList(dcSsoUser);
					if (list!=null&&list.size()>0){
						lineMsg.append("用户名已存在!");
					} else {
						peTeacher.setLoginId(temp);
						if(!loginId.add(temp)){
							lineMsg.append("用户名与文件中前面的数据重复!");
						}
					}
				}
				//性别
				temp = sheet.getCell(2, i).getContents().trim();
				if(temp == null || "".equals(temp)) {
					lineMsg.append("性别不能为空！ ");
				}else{
					if("男".equals(temp) || "女".equals(temp)) {
						if (temp.equals("男")) {
							peTeacher.setEnumConstByGender(this.getGeneralDao().getEnumConstByNamespaceCode("Gender", "1"));
						} else {
							peTeacher.setEnumConstByGender(this.getGeneralDao().getEnumConstByNamespaceCode("Gender", "0"));
						}
					} else {
						lineMsg.append("性别只能填 “男” 或 “女”！ ");
					}
				}
				//身份证号
				temp = sheet.getCell(3, i).getContents().trim();
				if(temp == null || "".equals(temp)) {
					lineMsg.append("身份证号不能为空！ ");
				}else{
					boolean isvalid=false;
					try {
						isvalid=manage.isValidIdcard(temp);
					} catch (IdcardErrorException e) {
						e.printStackTrace();
					} catch (WhatyUtilException e) {
						e.printStackTrace();
					}
					if(!isvalid){
						lineMsg.append("身份证号不正确！ ");
					}else {
						peTeacher.setIdCard(temp);
					}
				}
				//所属专业
				temp = sheet.getCell(4, i).getContents().trim();
				if(temp == null || "".equals(temp)) {
					lineMsg.append("所属专业不能为空！ ");
				}else{
					DetachedCriteria dcPeMajor = DetachedCriteria.forClass(PeMajor.class);
					dcPeMajor.add(Restrictions.eq("name", temp));
					List<PeMajor> peMajorList = this.getGeneralDao().getList(dcPeMajor);
					if(peMajorList==null||peMajorList.isEmpty()){
						lineMsg.append("所属专业不存在！ ");
					}else{
						peTeacher.setPeMajor(peMajorList.get(0));
					}
				}
				//最高学历
				temp = sheet.getCell(5, i).getContents().trim();
				if(temp == null || "".equals(temp)) {
					lineMsg.append("最高学历不能为空！ ");
				}else{
					DetachedCriteria dcFlagMaxXueli = DetachedCriteria.forClass(EnumConst.class);
					dcFlagMaxXueli.add(Restrictions.eq("name", temp));
					dcFlagMaxXueli.add(Restrictions.eq("namespace", "FlagMaxXueli"));
					List<EnumConst> flagMaxXueliList = this.getGeneralDao().getList(dcFlagMaxXueli);
					if(flagMaxXueliList==null||flagMaxXueliList.isEmpty()){
						lineMsg.append("最高学历不存在！ ");
					}else{
						peTeacher.setEnumConstByFlagMaxXueli(flagMaxXueliList.get(0));
					}
				}
				//最高学位
				temp = sheet.getCell(6, i).getContents().trim();
				if(temp == null || "".equals(temp)) {
					lineMsg.append("最高学位不能为空！ ");
				}else{
					DetachedCriteria dcFlagMaxXuewei = DetachedCriteria.forClass(EnumConst.class);
					dcFlagMaxXuewei.add(Restrictions.eq("name", temp));
					dcFlagMaxXuewei.add(Restrictions.eq("namespace", "FlagMaxXuewei"));
					List<EnumConst> flagMaxXueweiList = this.getGeneralDao().getList(dcFlagMaxXuewei);
					if(flagMaxXueweiList==null||flagMaxXueweiList.isEmpty()){
						lineMsg.append("最高学位不存在！ ");
					}else{
						peTeacher.setEnumConstByFlagMaxXuewei(flagMaxXueweiList.get(0));
					}
				}
				//职称
				temp = sheet.getCell(7, i).getContents().trim();
				if(temp == null || "".equals(temp)) {
					lineMsg.append("职称不能为空！ ");
				}else{
					DetachedCriteria dcFlagZhicheng = DetachedCriteria.forClass(EnumConst.class);
					dcFlagZhicheng.add(Restrictions.eq("name", temp));
					dcFlagZhicheng.add(Restrictions.eq("namespace", "FlagZhicheng"));
					List<EnumConst> flagZhichengList = this.getGeneralDao().getList(dcFlagZhicheng);
					if(flagZhichengList==null||flagZhichengList.isEmpty()){
						lineMsg.append("职称不存在！ ");
					}else{
						peTeacher.setEnumConstByFlagZhicheng(flagZhichengList.get(0));
					}
				}
				//毕业院校
				temp = sheet.getCell(8, i).getContents().trim();
				peTeacher.setGraduateSchool(temp);
				//毕业专业
				temp = sheet.getCell(9, i).getContents().trim();
				peTeacher.setGraduateMajor(temp);
				//单位电话
				temp = sheet.getCell(10, i).getContents().trim();
				if(temp == null || "".equals(temp)) {
					lineMsg.append("单位电话不能为空！ ");
				}else{
					if(!temp.matches(Const.telephone)){
						lineMsg.append("单位电话格式不正确！ ");
					} else {
						peTeacher.setPhoneOffice(temp);
					}
				}
				//移动电话
				temp = sheet.getCell(11, i).getContents().trim();
				if(temp == null || "".equals(temp)) {
					lineMsg.append("移动电话不能为空！ ");
				}else{
					if(!temp.matches(Const.mobile)){
						lineMsg.append("移动电话格式不正确！ ");
					} else {
						peTeacher.setMobilephone(temp);
					}
				}
				//电子邮箱
				temp = sheet.getCell(12, i).getContents().trim();
				if(temp == null || "".equals(temp)) {
					lineMsg.append("电子邮箱不能为空！ ");
				}else{
					if(!temp.matches(Const.email)){
						lineMsg.append("电子邮箱格式不正确！ ");
					} else {
						peTeacher.setEmail(temp);
					}
				}
				//工作单位
				temp = sheet.getCell(13, i).getContents().trim();
				peTeacher.setWorkplace(temp);
			/*	//是否带论文
				temp = sheet.getCell(14, i).getContents().trim();
				if(temp == null || "".equals(temp)) {
					lineMsg.append("是否带论文不能为空！ ");
				}else{
					if("是".equals(temp) || "否".equals(temp)) {
						//最大论文学生数
						String num = sheet.getCell(15, i).getContents().trim();
						if (temp.equals("是")) {
							peTeacher.setEnumConstByFlagPaper(this.getGeneralDao().getEnumConstByNamespaceCode("FlagPaper", "1"));
							if(num == null || "".equals(num)) {
								lineMsg.append("论文教师的最大论文学生数不能为空！ ");
							}
						} else {
							peTeacher.setEnumConstByFlagPaper(this.getGeneralDao().getEnumConstByNamespaceCode("FlagPaper", "0"));
						}
						if(num != null && !"".equals(num)) {
							if(!num.matches(Const.scoreLine)){
								lineMsg.append("最大论文学生数" + Const.scoreLineMessage + "! ");
							} else {
								peTeacher.setStuCountLimit(Long.parseLong(num));
							}
						}
					} else {
						lineMsg.append("是否带论文只能填 “是” 或 “否”！ ");
					}
				}
				*/
				//是否有效
				temp = sheet.getCell(14, i).getContents().trim();
				if(temp == null || "".equals(temp)) {
					lineMsg.append("是否有效不能为空！ ");
				}else{
					if("是".equals(temp) || "否".equals(temp)) {
						if (temp.equals("是")) {
							peTeacher.setEnumConstByFlagActive(this.getGeneralDao().getEnumConstByNamespaceCode("FlagActive", "1"));
						} else {
							peTeacher.setEnumConstByFlagActive(this.getGeneralDao().getEnumConstByNamespaceCode("FlagActive", "0"));
						}
					} else {
						lineMsg.append("是否有效只能填 “是” 或 “否”！ ");
					}
				}
				//简介
				temp = sheet.getCell(15, i).getContents().trim();
				peTeacher.setNote(temp);
				//设置为不带论文
				peTeacher.setEnumConstByFlagPaper(this.getGeneralDao().getEnumConstByNamespaceCode("FlagPaper", "0"));
				
				if (!peTeacherSet.add(peTeacher)) {
					lineMsg.append("本行数据与文件中前面的数据重复！ ");
				}
				if (lineMsg.length()>0){
					msg.append("第"+(i+1)+"行数据添加失败！"+lineMsg.toString()+"<br/>");
					continue;
				}
				count ++ ;
			} catch(Exception e) {
				e.printStackTrace();
				msg.append("第"+(i+1)+"行数据添加失败！<br/>");
				continue;
			}
		}
		
		if (msg.length() > 0) {
			msg.append("批量上传教师资料失败，请修改以上错误之后重新上传！<br/>");
			throw new EntityException(msg.toString());
		}
		
		for (PeTeacher peTeacher :peTeacherSet) {
			save(peTeacher);
		}
		return count;
	}
	
	public PeTeacher save(PeTeacher trasientInstance) throws EntityException {
		PeTeacher instance=null;
		try{
			SsoUser ssoUser=new SsoUser();
			ssoUser.setLoginId(trasientInstance.getLoginId());
			ssoUser.setPassword("1111");
			
			//设置默认 教师角色
			DetachedCriteria dc = DetachedCriteria.forClass(PePriRole.class);
			dc.add(Restrictions.eq("name", SsoConstant.SSO_DEFAULT_TEACHER_ROLE_NAME));
			
			List rl = this.getGeneralDao().getList(dc);
			if(rl != null ){
				PePriRole role = (PePriRole)rl.get(0);
				ssoUser.setPePriRole(role);
			}
			
			ssoUser=(SsoUser) this.getGeneralDao().save(ssoUser);
			
			trasientInstance.setName(trasientInstance.getLoginId()+"/"+trasientInstance.getTrueName());
			trasientInstance.setSsoUser(ssoUser);
			instance=(PeTeacher) this.getGeneralDao().save(trasientInstance);
		}catch(Exception e){
			e.printStackTrace();
			throw new EntityException("添加失败");
		}
		return instance;
	}

	public int deleteByIds(List ids) throws EntityException {
		for (Iterator iter = ids.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			PeTeacher peTeacher = (PeTeacher) this.getGeneralDao().getById(id);
			SsoUser ssoUser = peTeacher.getSsoUser();
			try {
				this.getGeneralDao().delete(peTeacher);
				this.getGeneralDao().delete(ssoUser);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ids.size();
	}

	public PeTeacher update(PeTeacher instance) throws EntityException {

		SsoUser ssoUser = instance.getSsoUser();
		ssoUser.setLoginId(instance.getLoginId());
		try {
			ssoUser = (SsoUser)this.getGeneralDao().save(ssoUser);
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new EntityException("更新ssoUser出错!");
			
		}
		instance.setName(instance.getLoginId() + "/" + instance.getTrueName());
		instance.setSsoUser(ssoUser);
		try {
			this.getGeneralDao().save(instance);
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new EntityException("更新teacher出错!");
			
		}
		
		return instance;
	}

}
