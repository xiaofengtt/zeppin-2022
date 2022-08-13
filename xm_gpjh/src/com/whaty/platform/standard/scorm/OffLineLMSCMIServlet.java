package com.whaty.platform.standard.scorm;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.lang.StringUtils;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.listener.session.MySessionContext;
import com.whaty.platform.sso.SsoFactory;
import com.whaty.platform.sso.SsoManage;
import com.whaty.platform.sso.SsoUser;
import com.whaty.platform.sso.SsoUserOperation;
import com.whaty.platform.sso.SsoUserPriv;
import com.whaty.platform.standard.scorm.Exception.ScormException;
import com.whaty.platform.standard.scorm.offline.OffLineRecord;
import com.whaty.platform.standard.scorm.offline.OffLineRecordManage;
import com.whaty.util.JsonUtil;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.sso.web.action.SsoConstant;

public class OffLineLMSCMIServlet extends HttpServlet{

	private String lmsUrl;
	
	/**
	 * Constructor of the object.
	 */
	public OffLineLMSCMIServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		//response.setContentType("application/x-shockwave-flash");
		response.setHeader("Pragma","No-cache"); 
		response.setHeader("Cache-Control","no-cache"); 
		response.setDateHeader("Expires", 0); 
		
		String command = request.getParameter("command");
		String returnJson = "";
		PrintWriter out =  response.getWriter();
		
		HttpSession session = request.getSession(false);   
		
//		System.out.println("in->"+command);
		if("updatescorm".equals(command)){
			List list = null;    
			String message = request.getParameter("message");
			
			//加入opencourseId;
			// 在/work/student/courseware_list.jsp页设置了opencourseId的session: openId
			String openCourseId = (String)session.getAttribute("openId");
			
			System.out.println("in->message: "+message);
		//	message = message.replaceAll("\"null\"", "");
			if(StringUtils.isNotBlank(message)){ 
				try{ 
					list = OffLineRecordManage.getListFromJsonString(message, 0); 
					OffLineRecordManage recordManage = new OffLineRecordManage(list,openCourseId);
					recordManage.dealOffLineRecord();
					List<OffLineRecord> newRecordList = recordManage.getTotalRecordList();
					returnJson = JsonUtil.toJSONString(newRecordList);  
					returnJson = JsonUtil.rowJson(returnJson);
				}catch (Exception e) {
					returnJson = JsonUtil.rowErrorJson(e.getMessage());
				}
			}else{ 
				
				//获得课件id;
				String cmwareId = (String)session.getAttribute("cmware_id");
				
				//直接从平台获得数据;
				String stuId = (String)session.getAttribute("stuId");
				
//				System.out.println("in wareId: "+cmwareId);
//				System.out.println("in stuId: "+stuId);
//				System.out.println("in openId: "+openCourseId);
				
				
				if(stuId== null || cmwareId == null){
					returnJson = JsonUtil.rowErrorJson("登陆超时,请重新登陆!");
				}else{
					//判断学生登陆帐号并取得id;
					SsoFactory sso = SsoFactory.getInstance();
					SsoManage ssoManage;
					try {
						ssoManage = sso.creatSsoManage();
					
					SsoUser user = ssoManage.getSsoUserByLogin(stuId);
					
					if(user == null){
							throw new ScormException("服务器中没有您的记录，请确定登陆帐号的正确性!");
					}
					String studnetId =  user.getId();
					
					
					OffLineRecordManage recordManage = new OffLineRecordManage();
					recordManage.getPlatformData(studnetId, cmwareId);
					returnJson = JsonUtil.toJSONString(recordManage.getOfflineData());
					returnJson = returnJson.substring(1,returnJson.length()-1);
					}catch (PlatformException e) {
						returnJson = JsonUtil.rowErrorJson(e.getMessage());
					}catch (ScormException e) {
						returnJson = JsonUtil.rowErrorJson(e.getMessage());
					}
				}
				
				
				
			}
		}else if("checkuser".equals(command)){
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			try{
				SsoFactory factory=SsoFactory.getInstance();
				SsoUserPriv ssoUserPriv=factory.creatSsoUserPriv(username);
				SsoUserOperation ssoUserOperation=factory.creatSsoUserOperation(ssoUserPriv);
				boolean flag = ssoUserOperation.login(username,password);
				if(flag){ 
					returnJson = JsonUtil.rowSuccessJson("登陆成功!");
				}else{
					returnJson = JsonUtil.rowErrorJson("密码错误，登陆失败！");
				}
				
			}catch (PlatformException e) {
				returnJson = JsonUtil.rowErrorJson("对不起,服务器中没有此用户，登陆失败！");
			}
		}else if("checkofflinename".equals(command)){
			
			String username = request.getParameter("username");
			try{
				SsoFactory factory=SsoFactory.getInstance();
				SsoUserPriv ssoUserPriv=factory.creatSsoUserPriv(username);
				if(ssoUserPriv!=null && ssoUserPriv.getId()!=null){ 
					returnJson = JsonUtil.rowSuccessJson("用户名验证通过!");
				}else{
					returnJson = JsonUtil.rowErrorJson("服务器中没有此用户！");
				}
				
			}catch (PlatformException e) {
				returnJson = JsonUtil.rowErrorJson("对不起,服务器中没有此用户！");
			}
			
		}else if("getRegNo".equals(command)){
			String stuId = (String)session.getAttribute("stuId");
			
			SsoFactory sso = SsoFactory.getInstance();
			SsoManage ssoManage = null;
			SsoUser user = null;
			try {
				ssoManage = sso.creatSsoManage();
				user = ssoManage.getSsoUserByLogin(stuId);
				ssoManage = sso.creatSsoManage();
				user = ssoManage.getSsoUserByLogin(stuId);
			} catch (PlatformException e) {
				System.out.println(e.getMessage());
			}
		
			if(user!=null){
				returnJson = stuId+"&password="+user.getPassword();
			}else{
				returnJson = stuId;
			}
			
			
		}
		else if("getZyzc".equals(command)){
			String courseId=(String)request.getParameter("courseId");
			UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			String reg_no="";
			if(us!=null)
				reg_no= us.getLoginId();
			
			dbpool db = new dbpool();
			MyResultSet rs = null;
			String course_id="";
			String opencourse_id="";
			String zuoyelink="/entity/function/onlinehomeworkpaper/homeworkpaper_list.jsp";
			String zicelink="/entity/function/testpaper/onlinetestcourse_list.jsp";
			String sql="select a.id as course_id,b.id as opencourse_id from pe_bzz_tch_course a,pr_bzz_tch_opencourse b,pe_bzz_student s where s.reg_no='"+reg_no+"' and b.fk_batch_id=s.fk_batch_id and  b.fk_course_id=a.id and a.id like '%"+courseId+"%' ";
			try{
				rs=db.executeQuery(sql);
				if(rs!=null&&rs.next()){
					course_id = rs.getString("course_id");
					opencourse_id = rs.getString("opencourse_id");
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				db.close(rs);
			}
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		String zuoyeUrl=basePath+"sso/bzzinteraction_InteractionStuManage.action?course_id="+course_id+"&opencourseId="+opencourse_id+"&firstPage="+zuoyelink;
		String ziceUrl=basePath+"sso/bzzinteraction_InteractionStuManage.action?course_id="+course_id+"&opencourseId="+opencourse_id+"&firstPage="+zicelink;
		returnJson="{\"zyzcUrl\":[{\"zuoyeUrl\":\""+zuoyeUrl+"\",\"ziceUrl\":\""+ziceUrl+"\"}]}";
		}
		
	//	System.out.println("out->"+returnJson);
		out.write(returnJson);
		out.flush();
		out.close();
	}


}
