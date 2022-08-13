package cn.zeppin.action.sso;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.entity.Organization;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectAdminRight;
import cn.zeppin.entity.ProjectExpert;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.Submit;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherTrainingRecords;
import cn.zeppin.entity.TrainingAdmin;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.ISubmitService;
import cn.zeppin.service.IProjectExpertService;
import cn.zeppin.service.ISysUserService;
import cn.zeppin.service.ITeacherTrainingRecordsService;
import cn.zeppin.service.ITrainingAdminService;
//import cn.zeppin.utility.DictionyMap.ROLEENUM;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author Administrator
 * @category 登陆认证接口
 */
public class LoginAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 设置图形验证码中的字符串的字体的大小
	private Font mFont = new Font("Times New Roman", Font.PLAIN, 18);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private ISysUserService iSysUserService;
	private IOrganizationService iOrganization;
	private String message;
	private String status;
	private IProjectAdminService iProjectAdminService;
	private IProjectExpertService iProjectExpertService;
	private ITrainingAdminService iTrainingAdminService;
	private ITeacherTrainingRecordsService iTeacherTrainingRecordsService;
	private ISubmitService iSubmitService;

	private int valuator;
	private int projectid;
	private short subjectid;
	private int trainingCollege;
	private int type;
	private int psq;

	private List<TeacherTrainingRecords> onSubmitList;

	public LoginAction() {
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	/**
	 * 登陆接口
	 * 
	 * @author Administrator
	 * @category 登陆接口可以用身份证、邮件和手机登陆
	 * @param username
	 * @param passwd
	 * @param code
	 */
	@SuppressWarnings("rawtypes")
	public String login() {

		initServlert();
		String username = request.getParameter("username");
		String passwd = request.getParameter("passwd");
		String code = request.getParameter("authCode");
		String roleId = request.getParameter("roleid");

		if (username == null || username.equals("")) {
			// 验证用户名为空
			this.message = "没有输入用户名";
			this.status = "fail";
			return "fail";
		}

		if (passwd == null || passwd.equals("")) {
			// 验证密码为空
			this.message = "没有输入密码";
			this.status = "fail";
			return "fail";
		}

		if (code == null || code.equals("")) {
			// 验证验证码为空
			this.message = "没有输入验证码";
			this.status = "fail";
			return "fail";
		}

		String authCode = session.getAttribute("authCode") == null ? "" : session.getAttribute("authCode").toString();
		if (!authCode.equals(code)) {
			// 验证码输入不一致
			this.message = "验证码输入不一致";
			this.status = "fail";
			session.setAttribute("authCode", "");
			return "fail";
		} else {
			try {

				session.setAttribute("authCode", "");

				Object[] pars = { username, passwd, roleId };
				List li = this.iSysUserService.findByAll(pars);
				if (li != null && li.size() > 0) {
					UserSession us = new UserSession();
					Object[] obj = (Object[]) li.get(0);
					// 添加到 session中
					us.setId(obj[0] != null ? Integer.valueOf(obj[0].toString()) : 0);
					us.setIdcard(obj[1] != null ? obj[1].toString() : null);
					us.setMobile(obj[2] != null ? obj[2].toString() : null);
					us.setEmail(obj[3] != null ? obj[3].toString() : null);
					us.setRole(obj[5] != null ? Short.parseShort(obj[5].toString()) : 0);
					us.setName(obj[6] != null ? obj[6].toString() : null);
					us.setOrganization(obj[7] != null ? Integer.parseInt(obj[7].toString()) : 0);
					
					if(Integer.valueOf(roleId)!=4){
						Organization oz = this.iOrganization.get(us.getOrganization());
						if (oz != null) {
							us.setOrganizationLevel(oz.getOrganizationLevel().getLevel());
							us.setSchool(oz.getIsschool());
							us.setOrganizationScode(oz.getScode());
						}
					}
					us.setCreateuser(obj[8] != null ? Integer.parseInt(obj[8].toString()) : 0);
					us.setLevel(obj[9] != null ? Integer.parseInt(obj[9].toString()) : 0);
					us.setStatus(obj[10] != null ? Short.parseShort(obj[10].toString()) : 0);

					ProjectAdmin user = iProjectAdminService.get(us.getId());
					if (user != null) {
						List<ProjectType> lsProjectType = new ArrayList<>();

						for (ProjectAdminRight par : user.getProjectAdminRights()) {
							lsProjectType.add(par.getProjectType());
						}

						us.setLstProjectType(lsProjectType);
						session.setAttribute("lstProjectType", lsProjectType);
					}

					if (us.getStatus() == 2) {
						this.message = "登陆失败,用户名状态已停用";
						this.status = "fail";
						return "fail";
					} else {
						this.message = "";
						this.status = "ok";
						session.setAttribute("usersession", us);
						return "admin";
					}

				} else {
					this.message = "登陆失败,用户名密码错误";
					this.status = "fail";
					return "fail";
				}

				// }

			} catch (Exception ex) {
				ex.printStackTrace();
				this.message = "登陆失败";
				this.status = "fail";
				return "fail";
			}

		}

	}

	/**
	 * 登陆 问卷系统
	 * 
	 * @return
	 */
	public String loginPaper() {
		initServlert();

		// =========================*************===========================
		// 1.首先判断是手机登陆还是登陆码登陆
		// 2.如果是手机登陆徐列出所有需要回答的问卷列表
		// 3.如果是登陆码登陆直接进入
		// =========================*************===========================

		// 教师培训记录的UUID
		String uuid = request.getParameter("uuid");

		if (Utlity.isMobileNO(uuid)) {
			// 是手机登陆

			List<TeacherTrainingRecords> liT = this.getiTeacherTrainingRecordsService().getTeacherTrainingRecordsByMobile(uuid);

			if (liT != null && liT.size() > 0) {

				this.onSubmitList = new ArrayList<>();

				for (TeacherTrainingRecords ttr : liT) {
					int tid = ttr.getTeacher().getId();
					int pid = ttr.getProject().getId();
					short sid = ttr.getTrainingSubject().getId();
					int tcid = ttr.getTrainingCollege().getId();
					int paperid = 0;
					if (ttr.getProject().getPsqByEvaluationTrainingPsq() != null) {

						paperid = ttr.getProject().getPsqByEvaluationTrainingPsq().getId();

					} else {
						paperid = 0;
					}

					Submit sub = this.getiSubmitService().getSubmitByAll(tid, paperid, pid, sid, tcid, tid);
					if (sub == null) {
						onSubmitList.add(ttr);
					}

				}
			}

			return "paperlist";
		} else {

			TeacherTrainingRecords ttr = this.getiTeacherTrainingRecordsService().getTeacherTrainingRecordsByUuid(uuid);

			if (ttr != null) {
				// 获取到教师培训记录
				Teacher t = ttr.getTeacher();

				// 获取教师登陆信息
				UserSession us = new UserSession();
				// 添加到 session中
				us.setId(t.getId());
				us.setIdcard(t.getIdcard());
				us.setMobile(t.getMobile());
				us.setEmail(t.getEmail());
				us.setRole((short) 3);
				us.setName(t.getName());
				us.setOrganization(t.getOrganization().getId());

				Organization oz = t.getOrganization();
				if (oz != null) {
					us.setOrganizationLevel(oz.getOrganizationLevel().getLevel());
					us.setSchool(oz.getIsschool());
				}

				us.setCreateuser(0);
				us.setLevel(0);
				us.setStatus(t.getStatus());
				session.setAttribute("papersession", us);
				session.setAttribute("paperUuid", uuid);

				valuator = t.getId();
				projectid = ttr.getProject().getId();
				subjectid = ttr.getTrainingSubject().getId();
				this.trainingCollege = ttr.getTrainingCollege().getId();
				this.type = 3;
				if (ttr.getProject().getPsqByEvaluationTrainingPsq() != null) {
					this.psq = ttr.getProject().getPsqByEvaluationTrainingPsq().getId();
				} else {
					this.psq = 0;
				}

				// 判断是否已经提交了问卷
				return "paper";

			} else {
				// 不存在培訓記錄
				if (uuid != null) {
					this.message = "登陆失败,学员培训登陆码填写错误！";
					this.status = "fail";
				} else {
					this.message = "";
					this.status = "";
				}
				return "paperfail";
			}
		}
	}

	public String modifyPassword() {
		return "modifyPassword";
	}

	public String logout() {
		initServlert();
		session.removeAttribute("usersession");

		this.message = "";
		this.status = "ok";
		return "logout";
	}

	public void errorMessage(String message) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"Result\":\"ERROR\"");
		sb.append(",");
		sb.append("\"Message\":\"" + message + "\"");
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}

	/**
	 * @category 生成验证码
	 */
	public void authImg() {

		initServlert();
		try {
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType("image/jpeg");
			response.addHeader("expires", "0");

			OutputStream os = response.getOutputStream();

			// 指定图形验证码图片的大小
			int width = 60, height = 20;

			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			// 准备在图片中绘制内容
			Graphics g = image.getGraphics();
			Random random = new Random();

			g.setColor(getRandColor(200, 250));
			g.fillRect(0, 0, width, height);

			g.setFont(mFont);

			g.setColor(getRandColor(160, 200));
			// 生成随机线条
			for (int i = 0; i < 155; i++) {
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int xl = random.nextInt(12);
				int yl = random.nextInt(12);
				g.drawLine(x, y, x + xl, y + yl);
			}
			String rand = RandomStringUtils.randomNumeric(4);
			char c;
			// 生成随机的数字并加入到图片中
			for (int i = 0; i < 4; i++) {
				c = rand.charAt(i);
				g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110))); // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
				g.drawString(String.valueOf(c), 13 * i + 6, 16);
			}
			// 将验证码保存到seesion中
			HttpSession seesion = request.getSession();
			seesion.setAttribute("authCode", rand);

			ImageIO.write(image, "jpeg", os);
			os.close();
		} catch (Exception ex) {

		}

	}

	public void sendResponse(String status, String value) {
		StringBuilder checkSB = new StringBuilder();
		checkSB.append("{");
		checkSB.append("\"Result\":\"" + status + "\"");
		checkSB.append(",");
		checkSB.append("\"Message\":\"" + value + "\"");
		checkSB.append("}");
		Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
	}

	// 修改密码
	@SuppressWarnings("rawtypes")
	public void modifyPasswd() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		String Idcard = us.getIdcard();
		String passwd = request.getParameter("passwd");
		String newpasswd = request.getParameter("newpasswd");
		String confimpasswd = request.getParameter("confimpasswd");
		if (passwd == null || passwd.equals("")) {
			sendResponse("ERROR", "密码为空");
			return;
		}
		if (newpasswd == null || newpasswd.equals("")) {
			sendResponse("ERROR", "新密码为空");
			return;
		}
		if (!newpasswd.equals(confimpasswd)) {
			sendResponse("ERROR", "两次输入的新密码不同");
			return;
		}
		Object[] pars = { Idcard, passwd, us.getRole() };
		List li = this.iSysUserService.findByAll(pars);
		if (li != null && li.size() > 0) {
			if (Idcard.equals("admin")) {
				String updateinfo = "CREATE OR REPLACE VIEW SYS_USER AS SELECT ID,IDCARD,MOBILE,EMAIL,PASSWORD,1 AS ROLE,NAME,ORGANIZATION,CREATEUSER,LEVEL,STATUS,CREATOR,CREATTIME FROM PROJECT_ADMIN UNION SELECT ID,IDCARD,MOBILE,EMAIL,PASSWORD,2 AS ROLE,NAME,ORGANIZATION,CREATEUSER,1 AS LEVEL,STATUS,CREATOR,CREATTIME FROM TRAINING_ADMIN UNION SELECT ID,IDCARD,MOBILE,EMAIL,PASSWORD,3 AS ROLE,NAME,ORGANIZATION,FALSE AS CREATEUSER,1 AS LEVEL,STATUS,CREATOR,CREATTIME FROM TEACHER UNION SELECT ID,IDCARD,MOBILE,EMAIL,PASSWORD,4 AS ROLE,NAME,0 AS ORGANIZATION,FALSE AS CREATEUSER,1 AS LEVEL,STATUS,CREATOR,CREATTIME FROM PROJECT_EXPERT UNION SELECT 0 AS ID,'admin' AS IDCARD,'admin' AS MOBILE,'admin' AS EMAIL,'"
						+ newpasswd + "' AS PASSWORD,5 AS ROLE,'超级管理员' AS  NAME,0 AS ORGANIZATION,TRUE AS CREATEUSER,1 AS LEVEL,1 AS STATUS,0 AS CREATOR,'' AS  CREATTIME ; ";
				this.iProjectAdminService.executeSQLUpdate(updateinfo, null);
			} else {
				if (us.getRole() == 1) {
					ProjectAdmin pa = this.iProjectAdminService.get(us.getId());
					pa.setPassword(newpasswd);
					this.iProjectAdminService.update(pa);
				}else if(us.getRole()==4){ 
					ProjectExpert pe =this.iProjectExpertService.get(us.getId());
					pe.setPassword(newpasswd);
					this.iProjectExpertService.update(pe);
				}else {
					TrainingAdmin pa = this.iTrainingAdminService.get(us.getId());
					pa.setPassword(newpasswd);
					this.iTrainingAdminService.update(pa);
				}
			}
			sendResponse("OK", "添加成功");
			session.removeAttribute("usersession");
			return;
		} else {
			sendResponse("ERROR", "密码错误");
			return;
		}
	}

	/**
	 * 生成随机颜色
	 * 
	 * @param fc
	 * @param bc
	 * @return
	 */
	private Color getRandColor(int fc, int bc) { // 给定范围获得随机颜色
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	public ISysUserService getiSysUserService() {
		return iSysUserService;
	}

	public void setiSysUserService(ISysUserService iSysUserService) {
		this.iSysUserService = iSysUserService;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public IOrganizationService getiOrganization() {
		return iOrganization;
	}

	public void setiOrganization(IOrganizationService iOrganization) {
		this.iOrganization = iOrganization;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public IProjectAdminService getiProjectAdminService() {
		return iProjectAdminService;
	}

	public void setiProjectAdminService(IProjectAdminService iProjectAdminService) {
		this.iProjectAdminService = iProjectAdminService;
	}
	
	public IProjectExpertService getiProjectExpertService() {
		return iProjectExpertService;
	}

	public void setiProjectExpertService(IProjectExpertService iProjectExpertService) {
		this.iProjectExpertService = iProjectExpertService;
	}

	public ITrainingAdminService getiTrainingAdminService() {
		return iTrainingAdminService;
	}

	public void setiTrainingAdminService(ITrainingAdminService iTrainingAdminService) {
		this.iTrainingAdminService = iTrainingAdminService;
	}

	public ITeacherTrainingRecordsService getiTeacherTrainingRecordsService() {
		return iTeacherTrainingRecordsService;
	}

	public void setiTeacherTrainingRecordsService(ITeacherTrainingRecordsService iTeacherTrainingRecordsService) {
		this.iTeacherTrainingRecordsService = iTeacherTrainingRecordsService;
	}

	public int getValuator() {
		return valuator;
	}

	public void setValuator(int valuator) {
		this.valuator = valuator;
	}

	public int getProjectid() {
		return projectid;
	}

	public void setProjectid(int projectid) {
		this.projectid = projectid;
	}

	public short getSubjectid() {
		return subjectid;
	}

	public void setSubjectid(short subjectid) {
		this.subjectid = subjectid;
	}

	public int getTrainingCollege() {
		return trainingCollege;
	}

	public void setTrainingCollege(int trainingCollege) {
		this.trainingCollege = trainingCollege;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getPsq() {
		return psq;
	}

	public void setPsq(int psq) {
		this.psq = psq;
	}

	public ISubmitService getiSubmitService() {
		return iSubmitService;
	}

	public void setiSubmitService(ISubmitService iSubmitService) {
		this.iSubmitService = iSubmitService;
	}

	public List<TeacherTrainingRecords> getOnSubmitList() {
		return onSubmitList;
	}

	public void setOnSubmitList(List<TeacherTrainingRecords> onSubmitList) {
		this.onSubmitList = onSubmitList;
	}

}
