package com.whaty.platform.entity.web.action.recruit.baoming;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.PeTrainingClass;
import com.whaty.platform.entity.bean.PeTrainingPlan;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;
import com.whaty.util.string.AttributeManage;
import com.whaty.util.string.WhatyAttributeManage;

public class PeTraineeRegAction extends MyBaseAction {
	
	private static int MAX_REGIST_COUNT	= 100000;
	private String uploadMsg;
	private File photoZip;
	private String photoZipFileName;
	private String photoZipContentType;
	private String photoLink;

	@Override
	public void initGrid() {

		this.getGridConfig().setCapability(true, true, true, true, true);
		this.getGridConfig().setTitle(this.getText("学员管理"));
		
		this.getGridConfig().addMenuFunction(this.getText("设为有效"), "truevalid");
		this.getGridConfig().addMenuFunction(this.getText("设为无效"), "falsevalid");

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学员姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("用户名"), "loginId", true, true, true, Const.userName_for_extjs);
		
		ColumnConfig column = new ColumnConfig(this.getText("性别"), "enumConstByGender.name");
		column.setAllowBlank(true);
		this.getGridConfig().addColumn(column);
		
		this.getGridConfig().addColumn(this.getText("身份证号"), "cardNo", true, true, true, Const.cardId_for_extjs);
		
		column = new ColumnConfig(this.getText("是否有效"), "ssoUser.enumConstByFlagIsvalid.name");
		column.setAllowBlank(true);
		column.setAdd(false);
		this.getGridConfig().addColumn(column);
		
		column = new ColumnConfig(this.getText("学员状态"), "enumConstByStatus.name");
//		column.setAllowBlank(true);
		this.getGridConfig().addColumn(column);
		
		column = new ColumnConfig(this.getText("所属培训班"), "peTrainingClass.name");
		column.setAllowBlank(true);
		this.getGridConfig().addColumn(column);
		
		column = new ColumnConfig(this.getText("培训级别"), "enumConstByTrainingType.name");
		column.setAllowBlank(false);
		this.getGridConfig().addColumn(column);
		
		column = new ColumnConfig(this.getText("是否在职"), "enumConstByFlagInJob.name");
		column.setAllowBlank(true);
		this.getGridConfig().addColumn(column);
		
		this.getGridConfig().addColumn(this.getText("手机"), "mobile",false,true,true,"TextField", true,
				20, Const.mobile_for_extjs);
		this.getGridConfig().addColumn(this.getText("家庭电话"), "phoneHome",false,true,true,"TextField", true,
				20,Const.telephone_for_extjs);
		this.getGridConfig().addColumn(this.getText("电子邮箱"), "email",false,true,true,"TextField", true,
				50,Const.email_for_extjs);
		this.getGridConfig().addColumn(this.getText("照片"), "photoLink", false,false,false,"TextField");
		this.getGridConfig().addColumn(this.getText("家庭住址"), "address", false,true,true,"TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("工作单位"), "workPlace", false,true,true,"TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("邮编"), "zip", false,true,true,"TextField", true, 50);
		this.getGridConfig().addRenderFunction(this.getText("浏览照片"), "<a href=# onclick=window.open('peTraineeReg_viewPhoto.action?photoLink=${value}','','left=500,top=300,toolbars=0,statusbars=no,menubars=no,resizable=no,scrollbars=no,height=400,width=300,location=no')>浏览</a>", "photoLink");
//		this.getGridConfig().addColumn(this.getText("省"),
//				"peAreaByFkProvince.name");
//		this.getGridConfig()
//				.addColumn(this.getText("市"), "peAreaByFkCity.name");
//		this.getGridConfig().addColumn(this.getText("县"),
//				"peAreaByFkPrefecture.name");
//		this.getGridConfig().addRenderFunction(this.getText("管理该学员缴费记录"), "<a href='/entity/recruit/peFeeManageAction.action?bean.peTrainee.id=${value}'>"+this.getText("管理")+"</a>", "id");

	}
	
	public Map<String, String> updateColumn() {
		Map<String, String> map = new HashMap<String, String>();
		int count = 0;
		String action = this.getColumn();
		if (this.getIds() != null && this.getIds().length() > 0) {

		    String[] ids = getIds().split(",");
		    List idList = new ArrayList();
		    for (int i = 0; i < ids.length; i++) {
		    	idList.add(ids[i]);
		    }

		    List<PeTrainee> plist = new ArrayList<PeTrainee>();
		    try {
				DetachedCriteria pubdc = DetachedCriteria.forClass(PeTrainee.class);
				pubdc.createCriteria("peTrainingClass", "peTrainingClass",DetachedCriteria.LEFT_JOIN);
				pubdc.add(Restrictions.in("id", ids));
				plist = this.getGeneralService().getList(pubdc);
				EnumConst enumConst = null;
				if (action.equals("truevalid")) {
				    enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "1");
				}
				if (action.equals("falsevalid")) {
				    enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "0");
				}
	
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
		    	int year = cal.get(Calendar.YEAR);
		    	int month = cal.get(Calendar.MONTH) + 1;
		    	String start = "";
		    	if(month < 10) {
		    		start = year + "-0" + month + "-01";
		    	} else {
		    		start = year + "-" + month + "-01";
		    	}
		    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    	Date startDate = sdf.parse(start);
		    	String className = year + "年" + month + "月" + "培训班";	
		    	PeTrainingClass ptc = this.getTrainingClass(className, startDate);
				
				for (int k = 0; k < plist.size(); k++) {
				    if (action.contains("valid")) {
				    	PeTrainingClass p = plist.get(k).getPeTrainingClass();
//				    	if(p == null) {
//				    		plist.get(k).setPeTrainingClass(ptc);
//				    	}
				    	plist.get(k).setPeTrainingClass(ptc);
				    	plist.get(k).getSsoUser().setEnumConstByFlagIsvalid(enumConst);
				    }
				    PeTrainee bulletin = (PeTrainee) this.getGeneralService().save(plist.get(k));
				    count++;
				}
		    } catch (Exception e) {
			e.printStackTrace();
			map.clear();
			map.put("success", "false");
			map.put("info", "操作失败");
			return map;
		    }
		    map.clear();
		    map.put("success", "true");
		    map.put("info", count + "条记录操作成功");

		} else {
		    map.put("success", "false");
		    map.put("info", "parameter value error");
		}
		return map;
	}

	public String photoUpload() {
		return "upload";
	}
	
	public String viewPhoto() {
		return "view";
	}
	
	/**
	 * 创建培训班
	 * 
	 * @param className
	 */
	private PeTrainingClass getTrainingClass(String className, Date startDate) {
		PeTrainingClass ptc = null;
		this.getGeneralService().getGeneralDao().setEntityClass(PeTrainingClass.class);
		DetachedCriteria dc = DetachedCriteria.forClass(PeTrainingClass.class);
		dc.add(Restrictions.eq("name", className));
		try {
			List l = this.getGeneralService().getList(dc);
			if(l != null && l.size() > 0) {
				ptc = (PeTrainingClass)l.get(0);
			} else {
				PeTrainingClass p = new PeTrainingClass();
				p.setName(className);
				p.setNote(className);
				p.setStartDate(startDate);
				ptc = (PeTrainingClass)this.getGeneralService().save(p);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.getGeneralService().getGeneralDao().setEntityClass(PeTrainee.class);
		
		return ptc;
	}
	
	/**
	 * 上传图片
	 * 
	 * @return
	 */
	public String doUpload() {
		
		String path = this.doUploadFile();
		if(path != null) {
			if(this.unZip(path)) {
				
				this.doDeleteFile(path);
				
			} else {
				this.setUploadMsg("上传文件成功，但解压失败，请通知系统管理员手动解压文件...");
			}
		}
		return "upload";
		
	}
	
	/**
	 * 解压上传的zip文件
	 * 
	 * @param path
	 * @return
	 */
	private boolean unZip(String path) {
		
		path = ServletActionContext.getServletContext().getRealPath(path);
		try {
			//检查是否是ZIP文件
			ZipFile zip = new ZipFile(path);
			zip.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		File zipFile = new File(path);
		String destPath = ServletActionContext.getServletContext().getRealPath(Const.FILE_PATH);
		if(zipFile.isFile() && zipFile.exists()) {
			destPath = zipFile.getParent();
		}
		
		try {
			//建立与目标文件的输入连接
			ZipInputStream in = new ZipInputStream(new FileInputStream(path));
			ZipEntry file = in.getNextEntry();
			
			//输出的目标目录
			File destDir = new File(destPath);
			if(destDir.isDirectory() && !destDir.exists()) {
				destDir.mkdir();
			}
			
			byte[] c = new byte[1024];
			int len;
			int i;
			List<String> idList = new ArrayList<String>();
			
			//循环解压
			while (file != null) {
				String fileName = file.getName();
				idList.add(fileName.substring(0, fileName.indexOf(".")));
				
				FileOutputStream out = new FileOutputStream(destPath + File.separator + fileName);
				while ((len = in.read(c, 0, c.length)) != -1) {
					out.write(c, 0, len);
				}
					
				out.close();
			    
			    file = in.getNextEntry();

			}
			this.updatePhoto(idList);
			in.closeEntry();
			in.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
		
	}
	
	/**
	 * 删除上传的zip文件
	 * 
	 * @param path
	 */
	private void doDeleteFile(String path) {
		
		File file = new File(ServletActionContext.getServletContext().getRealPath(path));
		if(file.isFile() && file.exists()) {
			file.delete();
		}
	}
	
	/**
	 * 上传zip文件
	 * 
	 * @return
	 */
	private String doUploadFile() {
		
		String path = Const.FILE_PATH; 	//文件上传目录
		
		if (photoZip != null && photoZip.length()>0) {
			if(!checkFile(this.getPhotoZipFileName())) {
				this.setUploadMsg("请上传指定类型的压缩包文件");
				return null;
			}
			path += this.getPhotoZipFileName();
			path = this.reName(path);
			java.io.InputStream is;
			try {
				is = new FileInputStream(photoZip);
				java.io.OutputStream os = new FileOutputStream(ServletActionContext.getServletContext().getRealPath(path));
				
				byte buffer[] = new byte[8192];
				int count = 0;
				while ((count = is.read(buffer)) > 0)
				{
				    os.write(buffer, 0, count);
				}
				os.close();
				is.close();
			} catch (Exception e) {
				this.setUploadMsg("上传图片压缩包文件失败！");
				return null;
			}
            
		}
		
		this.setUploadMsg("上传图片压缩包文件成功！");
		return path;
		
	}
	
	//检查上传文件格式
	private boolean checkFile(String fileName) {
		List fileList=Const.fileTypeList;
		int inde=fileName.lastIndexOf(".");
		if(inde>=0){
			String filtyp=fileName.substring(inde+1, fileName.length());
			if(!fileList.contains(filtyp)){
				return false;
			}
		}
		return true;
	}
	
	//对上传的文件进行重命名
	private String reName(String path){
		
		String repath = "";
		
		if(new File(ServletActionContext.getServletContext().getRealPath(path)).isFile()){
			int point = (path.lastIndexOf(".")>0 ? path.lastIndexOf("."):path.length());
			repath = path.substring(0, point)+"["+System.currentTimeMillis()+"]"+path.substring(point);
		}else{
			repath = path;
		}
		
		return repath;
	}
	
	//更新学员图片链接地址
	private void updatePhoto(List<String> idList) {
		
		List list = null;
		try {
			for(String loginId : idList) {
				DetachedCriteria dc = DetachedCriteria.forClass(PeTrainee.class);
				dc.add(Restrictions.eq("loginId", loginId));
				list = this.getGeneralService().getList(dc);
				PeTrainee pt = (PeTrainee)list.get(0);
				pt.setPhotoLink(Const.FILE_PATH + loginId + ".jpg");
				this.getGeneralService().save(pt);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PeTrainee.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/peTraineeReg";
	}

	public PeTrainee getBean() {
		return (PeTrainee)super.superGetBean();
	}

	public void setBean(PeTrainee bean) {
		super.superSetBean(bean);
	}

	@Override
	public void checkBeforeAdd() throws EntityException {
		
		checkIdCard();
		DetachedCriteria dc = DetachedCriteria.forClass(SsoUser.class);
		dc.add(Restrictions.eq("loginId", this.getBean().getLoginId()));
		
		List list = this.getGeneralService().getList(dc);
		if(list !=null && list.size() > 0) {
			throw new EntityException("'" + this.getBean().getLoginId() + "' 用户已经存在！");
		} else {
			SsoUser user = new SsoUser();
			user.setLoginId(this.getBean().getLoginId());
			user.setPassword(Const.FIRST_PASSWORD);
			user.setLoginNum(Long.valueOf("0"));
			user.setPePriRole(this.getPePriRole());
			
			EnumConst ec = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "0");
			user.setEnumConstByFlagIsvalid(ec);
			
			this.entityClass = SsoUser.class;
			this.getGeneralService().save(user);
			
			this.entityClass = PeTrainee.class;
			this.getBean().setSsoUser(user);
			this.getBean().setUserName(this.getRegistSequence());
			this.getBean().setName(this.getBean().getUserName() + "/" + this.getBean().getTrueName());
			
		}
		
	}
	
	@Override
	public void checkBeforeUpdate() throws EntityException {
		
		checkIdCard();
		checkStatus();
		PeTrainee oldBean = (PeTrainee)this.getGeneralService().getById(this.getBean().getId());
		if(!oldBean.getLoginId().equals(this.getBean().getLoginId())) {
			DetachedCriteria dc = DetachedCriteria.forClass(SsoUser.class);
			dc.add(Restrictions.eq("loginId", this.getBean().getLoginId()));
			
			List list = this.getGeneralService().getList(dc);
			if(list !=null && list.size() > 0) {
				throw new EntityException("'" + this.getBean().getLoginId() + "' 用户已经存在！");
			} else {
				oldBean.getSsoUser().setLoginId(this.getBean().getLoginId());
				this.getBean().setSsoUser(oldBean.getSsoUser());
				this.getBean().setName(this.getBean().getLoginId() + "/" + this.getBean().getTrueName());
			}
		}
		
	}
	//添加报名管理的限定条件
	private void checkStatus() throws EntityException{
		
		if(this.getBean() instanceof PeTrainee){
			PeTrainee ptBean = (PeTrainee)this.getBean();
			if(ptBean.getEnumConstByTrainingType().getName().equals("初级培训计划")){
				String name = ptBean.getEnumConstByStatus().getName();
				if(!(("已报名").equals(name)||("初级培训已交费").equals(name)||("初级培训已结业").equals(name))){
					throw new EntityException("请选择正确的学员状态");
				}
			}
			if(ptBean.getEnumConstByTrainingType().getName().equals("中级培训计划")){
				String name = ptBean.getEnumConstByStatus().getName();
				if(("高级培训已交费").equals(name)||("高级培训已结业").equals(name)||("高级培训申请已审核").equals(name)){
					throw new EntityException("请选择正确的学员状态");
				}
			}
		}
	}
	private void checkIdCard() throws EntityException {
		AttributeManage manage=new WhatyAttributeManage();
		try {
			if(!manage.isValidIdcard(this.getBean().getCardNo())){
				throw new EntityException("身份证号码输入错误！");
			}
		} catch (Exception e) {
			throw new EntityException("身份证号码输入错误！");
		}
	}
	
//	public Map delete() {
//		
//		Map map = null;
//		
//		if (this.getIds() != null && this.getIds().length() > 0) {
//			String str = this.getIds();
//			if (str != null && str.length() > 0) {
//				String[] ids = str.split(",");
//				List idList = new ArrayList();
//				try {
//					for (int i = 0; i < ids.length; i++) {
//						PeTrainee pt = (PeTrainee)this.getGeneralService().getById(ids[i]);
//						idList.add(pt.getSsoUser().getId());
//					}
//					
//					map = super.delete();
//					this.getGeneralService().getGeneralDao().setEntityClass(SsoUser.class);
//					this.getGeneralService().deleteByIds(idList);
//					
//					this.getGeneralService().getGeneralDao().setEntityClass(PeTrainee.class);
//				} catch (EntityException e) {
//					e.printStackTrace();
//				}
//				
//			}
//		}
//		
//		return map;
//		
//	}
	
	private PePriRole getPePriRole() throws EntityException {
		
		DetachedCriteria dc = DetachedCriteria.forClass(PePriRole.class);
		dc.add(Restrictions.eq("name", "学生"));
		
		List list = null;
		PePriRole role = null;
		list = this.getGeneralService().getList(dc);
		if(list != null && list.size() > 0) {
			role = (PePriRole)list.get(0);
		}
			
		return role;
	}

	/**
	 * 根据当前时间和序列号生成报名号，报名号为9位（201000001）
	 * 
	 * @return
	 * @throws EntityException 
	 * @throws EntityException 
	 */
	private String getRegistSequence() throws EntityException {
		
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int seq = 0;
		String sequence = "";
		String sql = "select s_login_id.nextval from dual";
		try {
			List list = this.getGeneralService().getBySQL(sql);
			seq = Integer.valueOf(((java.math.BigDecimal)(list.get(0))) + "");
		} catch (Exception e) {
			throw new EntityException("获取报名号失败！");
		}
		
		if((seq % this.MAX_REGIST_COUNT) < 10) {
			sequence = "0000" + seq;
		} else if((seq % this.MAX_REGIST_COUNT) < 100) {
			sequence = "000" + seq;
		} else if((seq % this.MAX_REGIST_COUNT) < 1000) {
			sequence = "00" + seq;
		} else if((seq % this.MAX_REGIST_COUNT) < 10000) {
			sequence = "0" + seq;
		} else if((seq % this.MAX_REGIST_COUNT) < 100000) {
			sequence = "" + seq;
		}
		
		return year + "" + sequence;
		
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeTrainee.class);
		dc.createCriteria("enumConstByGender", "enumConstByGender",DetachedCriteria.LEFT_JOIN);
		DetachedCriteria dcStatus = dc.createCriteria("enumConstByStatus", "enumConstByStatus");
		dcStatus.add(Restrictions.eq("namespace", "Status"));
		dcStatus.add(Restrictions.in("code", Arrays.asList("0","7","8")));
		dc.createCriteria("enumConstByTrainingType", "enumConstByTrainingType",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagInJob", "enumConstByFlagInJob",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peTrainingClass", "peTrainingClass",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peAreaByFkProvince", "peAreaByFkProvince",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peAreaByFkCity", "peAreaByFkCity",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peAreaByFkPrefecture", "peAreaByFkPrefecture",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("ssoUser", "ssoUser").createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		
		return dc;
		
	}
	
	public String getUploadMsg() {
		return uploadMsg;
	}

	public void setUploadMsg(String uploadMsg) {
		this.uploadMsg = uploadMsg;
	}

	public File getPhotoZip() {
		return photoZip;
	}

	public void setPhotoZip(File photoZip) {
		this.photoZip = photoZip;
	}

	public String getPhotoZipFileName() {
		return photoZipFileName;
	}

	public void setPhotoZipFileName(String photoZipFileName) {
		this.photoZipFileName = photoZipFileName;
	}

	public String getPhotoZipContentType() {
		return photoZipContentType;
	}

	public void setPhotoZipContentType(String photoZipContentType) {
		this.photoZipContentType = photoZipContentType;
	}

	public String getPhotoLink() {
		return photoLink;
	}

	public void setPhotoLink(String photoLink) {
		this.photoLink = photoLink;
	}

}