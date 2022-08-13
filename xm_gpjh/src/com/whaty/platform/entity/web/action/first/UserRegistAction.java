package com.whaty.platform.entity.web.action.first;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.PeTrainingPlan;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class UserRegistAction extends MyBaseAction {

	private static int MAX_REGIST_COUNT	= 100000;
	
	private List trainingLevel;
	
	private String password;
	private String rePassword;
	private String realName;
	private String cardId;
	private String trainLevel;
	private String trainLevel_name;
	private String logID;
	private String email;
	private String userName; //系统自动生成用户报名序号
	private boolean flag = false;//是否已经注册成功
	
	/**
	 * 用户注册
	 * 
	 * @return
	 */
	public String regist() {
		
		if(this.check()) {
			
			PeTrainee trainee = new PeTrainee();
			
			trainee.setLoginId(logID);
			trainee.setTrueName(realName);
			trainee.setCardNo(cardId);
			trainee.setEmail(email); 
			
			try {
				userName = this.getRegistSequence();
				trainee.setUserName(userName);
				trainee.setName(userName + "/" + realName);
				
				SsoUser user = this.getSsoUser(logID);
				trainee.setSsoUser(user);
				
				EnumConst ec = getRegistStatus();
				trainee.setEnumConstByStatus(ec);
				
				EnumConst tt = getTraingType();
				this.setTrainLevel_name(tt.getName());
				trainee.setEnumConstByTrainingType(tt);
				System.out.println(this.getDefaultPlan().getEnumConstByVersion().getCode());
				trainee.setPeTrainingPlan(this.getDefaultPlan());
				this.entityClass = PeTrainee.class;
				this.getGeneralService().save(trainee);
				
				this.flag = true;
				String message = "您已完成报名，您的报名号为： '" + userName + "' ，为了方便您的学习请将您的注册信息保存，并邮寄培训费用（请在汇款单上注明'生殖健康咨询师培训'及'报名号'），待系统审核之后可以用该用户名密码登陆系统进行学习！";
				this.addActionMessage(message);
			} catch (Exception e) {
				this.flag = false;
				this.addActionMessage("注册失败...");
				e.printStackTrace();
			}
		}
		
		return "regist";
		
	}
	/**
	 * 获取对应培训类型的默认培训计划
	 * @throws EntityException 
	 * 
	 */
	private PeTrainingPlan getDefaultPlan() throws EntityException{
		DetachedCriteria dc = DetachedCriteria.forClass(PeTrainingPlan.class);
		dc.add(Restrictions.eq("enumConstByTrainingType", this.getTraingType()));
		dc.createCriteria("enumConstByVersion","enumConstByVersion");
		dc.addOrder(Order.desc("enumConstByVersion.code"));
		Page page=this.getGeneralService().getByPage(dc,1,0);
		List list=page.getItems();
		return (PeTrainingPlan) list.get(0);
	}
	
	/**
	 * 获取培训类型
	 * 
	 * @return
	 * @throws EntityException
	 */
	private EnumConst getTraingType() throws EntityException {
		DetachedCriteria dc = DetachedCriteria.forClass(EnumConst.class);
		dc.add(Restrictions.eq("id", trainLevel));
		dc.add(Restrictions.eq("namespace", "TrainingType"));
		
		List list = null;
		EnumConst ec = null;
		
		list = this.getGeneralService().getList(dc);
		if(list != null && list.size() > 0) {
			ec = (EnumConst)list.get(0);
		}
		
		return ec;
	}
	
	/**
	 * 获取状态为已报名的EnumConst
	 * 
	 * @return
	 * @throws EntityException 
	 */
	private EnumConst getRegistStatus() throws EntityException {
		
		DetachedCriteria dc = DetachedCriteria.forClass(EnumConst.class);
		dc.add(Restrictions.eq("name", "已报名"));
		dc.add(Restrictions.eq("namespace", "Status"));
		
		List list = null;
		EnumConst ec = null;
		
		list = this.getGeneralService().getList(dc);
		if(list != null && list.size() > 0) {
			ec = (EnumConst)list.get(0);
		}
		
		return ec;
	}

	/**
	 * 获取SsoUser
	 * 
	 * @return
	 * @throws EntityException 
	 */
	private SsoUser getSsoUser(String loginId) throws EntityException {
		
		SsoUser user = new SsoUser();
		user.setLoginId(loginId);
		user.setPassword(password);
		user.setLoginNum(Long.valueOf("0"));
		PePriRole role = getPePriRole();
		user.setPePriRole(role);
		
		EnumConst ec = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "1");
		user.setEnumConstByFlagIsvalid(ec);
		
		this.entityClass = SsoUser.class;
		this.getGeneralService().save(user);
		
		return user;
	}
	
	/**
	 * 获取 学生 角色的PePriRole
	 * 
	 * @return
	 * @throws EntityException 
	 */
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
	 */
	private String getRegistSequence() throws EntityException {
		
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int seq = 0;
		String sequence = "";
		String sql = "select s_login_id.nextval from dual";
		List list = this.getGeneralService().getBySQL(sql);
		seq = Integer.valueOf(((java.math.BigDecimal)(list.get(0))) + "");
		
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
	
	/**
	 * 获取培训级别
	 * 
	 */
	private void getTraingTypeList() {
		trainingLevel = new ArrayList();
		DetachedCriteria dcLevel = DetachedCriteria.forClass(EnumConst.class);
		dcLevel.add(Restrictions.eq("namespace", "TraingType"));
		
		try {
			trainingLevel = this.getGeneralService().getList(dcLevel);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 检查数据的合法性
	 * 
	 * @return
	 */
	private boolean check() {
		
		List nList = null;
		if(logID == null || "".equals(logID) || logID.length() < 4 || logID.length() > 16) {
			this.addActionMessage("用户名：请输入4-16位英文字符、下划线或数字");
			return false;
		}
		
		DetachedCriteria dc = DetachedCriteria.forClass(SsoUser.class);
		dc.add(Restrictions.eq("loginId", logID));
		try {
			nList = this.getGeneralService().getList(dc);
			if(nList != null && nList.size() != 0) {
				this.addActionMessage("用户名已经存在，请重新输入");
				return false;
			}
		} catch (EntityException e) {
			e.printStackTrace();
			return false;
		}
		
		if(password == null || "".equals(password) || password.length() < 4 || password.length() > 10) {
			this.addActionMessage("密码：请输入4-10位字符或者数字");
			return false;
		}
		
		if(rePassword == null || "".equals(rePassword) || !rePassword.equals(password)) {
			this.addActionMessage("确认密码：两次输入的密码不同");
			return false;
		}
		
		if(rePassword == realName || "".equals(realName)) {
			this.addActionMessage("姓名：请输入您的姓名");
			return false;
		}
		
		if(cardId == null || "".equals(cardId)) {
			this.addActionMessage("身份证：身份证号码输入错误");
			return false;
		}
		if(email == null || "".equals(email)) {
			this.addActionMessage("注册邮箱：注册邮箱输入错误");
			return false;
		}
		
		return true;
		
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRePassword() {
		return rePassword;
	}
	
	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}
	
	public String getRealName() {
		return realName;
	}
	
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public String getCardId() {
		return cardId;
	}
	
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	public String getTrainLevel() {
		return trainLevel;
	}
	
	public void setTrainLevel(String trainLevel) {
		this.trainLevel = trainLevel;
	}

	@Override
	public void initGrid() {
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeTrainee.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/first/userRegist";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLogID() {
		return logID;
	}

	public void setLogID(String logID) {
		this.logID = logID;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public List getTrainingLevel() {
		return trainingLevel;
	}

	public void setTrainingLevel(List trainingLevel) {
		this.trainingLevel = trainingLevel;
	}

	public String getTrainLevel_name() {
		return trainLevel_name;
	}

	public void setTrainLevel_name(String trainLevel_name) {
		this.trainLevel_name = trainLevel_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}