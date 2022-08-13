package com.whaty.platform.standard.scorm;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.standard.scorm.Exception.ScormException;
import com.whaty.platform.standard.scorm.datamodels.SCODataManager;
import com.whaty.platform.standard.scorm.datamodels.cmi.CMICore;
import com.whaty.platform.standard.scorm.operation.ScormFactory;
import com.whaty.platform.standard.scorm.operation.ScormManage;
import com.whaty.platform.standard.scorm.operation.UserScoData;
import com.whaty.platform.standard.scorm.util.ScormLog;

public class LMSCMIServletJS extends HttpServlet {
	
	private String userID;

	private String courseID;

	private String scoID;
	
	private boolean logoutFlag;
	
	private String openCourseId;
	
	private String jsonString = "";
	public String[] dataModel = {"cmi.core.student_id","cmi.core.student_name","cmi.core.lesson_location",
			"cmi.core.credit","cmi.core.lesson_status","cmi.core.entry","cmi.core.total_time","cmi.core.lesson_mode",
			"cmi.core.exit","cmi.core.session_time","cmi.core.score.raw","cmi.core.score.min","cmi.core.score.max",
			"cmi.core.uncommit_time","cmi.comments","cmi.launch_data","cmi.suspend_data"
	};
	int numjd =0;
	private final static String SERVLET_PATH = "servlet/lmscmi";
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String sessionid = req.getParameter("JSESSIONID");
		String command = req.getParameter("command");
		String SCOID = req.getParameter("SCOID");
		
		PrintWriter out = resp.getWriter();
		resp.setCharacterEncoding("UTF-8");
		
		HttpSession session = req.getSession(false);
		
		if (session == null) {
			ScormLog.setDebug("this is bad - no session in cmi servlet");
			out.write("FAILED");
			out.close();
			return;
		} else {
			ScormLog.setDebug("session id is: " + session.getId());
		}
		
		Date startdate = (Date)session.getAttribute("now");
		
		if(session!=null)
		{
			scoID = (String) session.getAttribute("SCOID");
			courseID = (String) session.getAttribute("COURSEID");
			try {
				UserSession userSession = (UserSession) session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
				userID = (String) userSession.getSsoUser().getId();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			openCourseId = (String)session.getAttribute("COURSEID");
		}
		if(userID==null || userID.equals(""))
		{
			out.write("FAILED");
			out.close();
			return;
		}
		
		if(courseID==null || courseID.equals(""))
		{
			out.write("FAILED");
			out.close();
			return;
		}
		
		if(openCourseId==null || openCourseId.equals(""))
		{
			out.write("FAILED");
			out.close();
			return;
		}

		ScormFactory scormFactory = ScormFactory.getInstance();
		ScormManage scormManage = scormFactory.creatScormManage();
		if("getdata".equalsIgnoreCase(command)){

			SCODataManager scoData = null;
			try {
				scoData = scormManage.getFromDB(userID,courseID, scoID);
				scoData.getCore().setSessionTime("00:00:00.0");
				scoData.getCore().showData();
			} catch (ScormException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ScormLog.setDebug("LMSCMIServlet processed get for SCO Data\n");
			
			StringBuffer sb = new StringBuffer();
			sb.append(dataModel[0]+"|");sb.append(scoData.getCore().getStudentId().getValue()+";");
			sb.append(dataModel[1]+"|");sb.append(scoData.getCore().getStudentName().getValue()+";");
			sb.append(dataModel[2]+"|");sb.append(scoData.getCore().getLessonLocation().getValue()+";");
			sb.append(dataModel[3]+"|");sb.append(scoData.getCore().getCredit().getValue()+";");
			sb.append(dataModel[4]+"|");sb.append(scoData.getCore().getLessonStatus().getValue()+";");
			sb.append(dataModel[5]+"|");sb.append(scoData.getCore().getEntry().getValue()+";");
			sb.append(dataModel[6]+"|");sb.append(scoData.getCore().getTotalTime().getValue()+";");
			sb.append(dataModel[7]+"|");sb.append(scoData.getCore().getLessonMode().getValue()+";");
			sb.append(dataModel[8]+"|");sb.append(scoData.getCore().getExit().getValue()+";");
			sb.append(dataModel[9]+"|");sb.append(scoData.getCore().getSessionTime().getValue()+";");
			sb.append(dataModel[10]+"|");sb.append(scoData.getCore().getScore().getRaw().getValue()+";");
			sb.append(dataModel[11]+"|");sb.append(scoData.getCore().getScore().getMin().getValue()+";");
			sb.append(dataModel[12]+"|");sb.append(scoData.getCore().getScore().getMax().getValue()+";");
			sb.append(dataModel[13]+"|");sb.append(scoData.getCore().getUncommitTime().getValue()+";");
			sb.append(dataModel[14]+"|");sb.append(scoData.getComments().getComments().getValue()+";");
			sb.append(dataModel[15]+"|");sb.append(scoData.getLaunchData().getLaunchData().getValue()+";"); 
			sb.append(dataModel[16]+"|");sb.append(scoData.getSuspendData().getSuspendData().getValue()+";");
			
			out.write(sb.toString()); 
			
		}else if("putdata".equalsIgnoreCase(command)){
			String[] dmeKey = req.getParameterValues("dmeID"); 
			String[] dmeValue = req.getParameterValues("dmeValue");
			
			SCODataManager scoData = new SCODataManager(); 
			
			for(int i =0 ;i <dmeKey.length;i++){
				if(dataModel[0].equals(dmeKey[i])){
					scoData.getCore().getStudentId().setValue(dmeValue[i]);
				}else if (dataModel[1].equals(dmeKey[i])){
					scoData.getCore().getStudentName().setValue(dmeValue[i]);
				}else if (dataModel[2].equals(dmeKey[i])){
					scoData.getCore().getLessonLocation().setValue(dmeValue[i]);
				}else if (dataModel[3].equals(dmeKey[i])){
					scoData.getCore().getCredit().setValue(dmeValue[i]);
				}else if (dataModel[4].equals(dmeKey[i])){
					scoData.getCore().getLessonStatus().setValue(dmeValue[i]);
				}else if (dataModel[5].equals(dmeKey[i])){
					scoData.getCore().getEntry().setValue(dmeValue[i]);
				}else if (dataModel[6].equals(dmeKey[i])){
					scoData.getCore().getTotalTime().setValue(dmeValue[i]); 
				}else if (dataModel[7].equals(dmeKey[i])){
					scoData.getCore().getLessonMode().setValue(dmeValue[i]);
				}else if (dataModel[8].equals(dmeKey[i])){
					scoData.getCore().getExit().setValue(dmeValue[i]);
				}else if (dataModel[9].equals(dmeKey[i])){
					scoData.getCore().getSessionTime().setValue(dmeValue[i]);
				}else if (dataModel[10].equals(dmeKey[i])){
					scoData.getCore().getScore().getRaw().setValue(dmeValue[i]);
				}else if (dataModel[11].equals(dmeKey[i])){
					scoData.getCore().getScore().getMin().setValue(dmeValue[i]);
				}else if (dataModel[12].equals(dmeKey[i])){
					scoData.getCore().getScore().getMax().setValue(dmeValue[i]);
				}else if (dataModel[13].equals(dmeKey[i])){
					scoData.getCore().getUncommitTime().setValue(dmeValue[i]);
				}else if (dataModel[14].equals(dmeKey[i])){
					scoData.getComments().getComments().setValue(dmeValue[i]);
				}else if (dataModel[15].equals(dmeKey[i])){
					scoData.getLaunchData().getLaunchData().setValue(dmeValue[i]);
				}else if (dataModel[16].equals(dmeKey[i])){
					scoData.getSuspendData().getSuspendData().setValue(dmeValue[i]);
				}
			}
			
			
			logoutFlag = false;
			ScormLog.setDebug("LMSCMIServlet read in the SCODataManager object");
			try {
				String lessonStatus = new String();
				String lessonExit = new String();
				String lessonEntry = new String();
				String lessonTime = new String();

				// Handle some sequencing issues. If the exit of the current
				// SCO is set to "suspend", the user has hit the "Go Back"
				// button
				// on the current SCO. When this happens, the mode of the
				// SCO
				// that would be served up prior to the current SCO is set
				// to
				// "review". This is done so that the LMSLessonServlet will
				// know
				// to display the correct SCO. Also, the current SCO entry
				// will be
				// set to "resume" because at some point the student will
				// resume
				// with the current SCO

				// Need to get the core object off of the SCO Data Manager
				// for the current SCO
				CMICore lmsCore = scoData.getCore();

				// Need to add the SCOs Session Time into the running total
				// time
				// CMITime totalTime =
				// new CMITime( lmsCore.getTotalTime().getValue() );

				// System.out.println("\tTotal time: " +
				// totalTime.toString());
				// CMITime sessionTime =
				// new CMITime( lmsCore.getSessionTime().getValue() );
				// System.out.println("\tSession time: " +
				// sessionTime.toString());
				
				/*
				 *记录学习结束时间 
				 */
				
					/*if(numjd<=5){
						boolean flag = true;
						if(numjd==3){
						dbpool db = new dbpool();
						Date now = new Date();
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String start = dateFormat.format(startdate);
						String end = dateFormat.format(now);
						
						String sql ="insert into STUTTIME (id,START_DATE,FK_SSOUSER_ID,FK_OPEN_COURSE_ID,END_DATE) values(to_char(Seq_stutime_id.Nextval),to_date('"+start+"','yyyy-mm-dd hh24:mi:ss'),'"+userID+"','"+openCourseId+"',to_date('"+end+"','yyyy-mm-dd hh24:mi:ss'))";
						System.out.println("sql:"+sql);
						int k =db.executeUpdate(sql);
						numjd=0;
						flag=false;
						}
						if(flag){
							numjd++;
						}
					}*/
				
				// totalTime.add(sessionTime);
				// lmsCore.setTotalTime(totalTime.toString());

				// System.out.println("\t\tTotal time: " +
				// totalTime.toString());

				// if ( lmsCore.getExit().getValue().equalsIgnoreCase(
				// "suspend" ) )
				// {
				// lmsCore.setEntry( "resume" );
				// if ( currentSCO != 0 )
				// {
				// // Set the previous SCOs Lesson Mode to review
				// CMICore previousCore = lmsData[currentSCO - 1].getCore();
				// previousCore.setLessonMode( "review" );
				// lmsData[currentSCO - 1].setCore(previousCore);
				// }
				// }
				if (lmsCore.getExit().getValue()!=null && lmsCore.getExit().getValue().equalsIgnoreCase("logout")) {
					// Nothing special needs to be done when a logout occurs
					// Sequencing will go to the first SCO with a "review"
					// mode or
					// to the first SCO with an incomplete status.
					// lmsCore.setExit( "" );
					logoutFlag = true;
				}
				// else
				// {
				// User has hit the "continue" button on the current SCO.
				// The
				// mode will be set to "normal" in case the mode was
				// "review".
				// lmsCore.setLessonMode( "normal" );
				// }

				lessonStatus = lmsCore.getLessonStatus().getValue();
				lessonExit = lmsCore.getExit().getValue();
				lessonEntry = lmsCore.getEntry().getValue();
				lessonTime = lmsCore.getSessionTime().getValue();
				scoData.setCore(lmsCore);
				ScormLog.setDebug("The SCO Data Manager for the current SCO contains the following:");
				scoData.getCore().showData();
				scormManage.putIntoDB(scoData, userID, courseID, scoID,openCourseId);
               
				List userScoData = scormManage.getUserScoDatas(userID,
						courseID, scoID);
				for (int i = 0; i < userScoData.size(); i++) {
					String newStatus = ((UserScoData) userScoData.get(i)).getCore().getLessonStatus().getValue();
					ScormLog.setDebug("the status in sequencingEngine after set is: "
									+ newStatus);
				}
			} catch (Exception e) {
				e.printStackTrace();
				out.write("FAILED");
			}

			if (logoutFlag == true) {
				session.setAttribute("EXITFLAG", "true");
			} else {
				session.removeAttribute("EXITFLAG");
			}

			out.write("SUCCESS");
		}else if("setSCOID".equalsIgnoreCase(command)){
			SCOID = scormManage.getScoIdByItem(SCOID,courseID);
			session.setAttribute("SCOID", SCOID);
		}
		
		out.close();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}
}
