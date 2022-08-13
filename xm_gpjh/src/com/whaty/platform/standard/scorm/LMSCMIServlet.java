package com.whaty.platform.standard.scorm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.whaty.platform.standard.scorm.datamodels.SCODataManager;
import com.whaty.platform.standard.scorm.datamodels.cmi.CMICore;
import com.whaty.platform.standard.scorm.operation.ScormFactory;
import com.whaty.platform.standard.scorm.operation.ScormManage;
import com.whaty.platform.standard.scorm.operation.UserScoData;
import com.whaty.platform.standard.scorm.util.ScormLog;

public class LMSCMIServlet extends HttpServlet {
	private String userID;

	private String courseID;

	private String scoID;
	
	private String openId;

	private boolean logoutFlag;
	
	private Date date;

	/***************************************************************************
	 * * * Method: doPost * Input: HttpServletRequest request,
	 * HttpServletResponse response * Output: none * * Description: * This
	 * method handles post messages to the servlet. This servlet will respond *
	 * to the following commands: * cmigetcat * cmiputcat * * A real LMS would
	 * probably want to handle each request as a seperate servlet, * but for the
	 * purpose of demonstrating a sample LMS it was easier to have a * single
	 * servlet. *
	 **************************************************************************/
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		try {
			ScormLog.setDebug("requested session: "
					+ request.getRequestedSessionId());
			if (request.isRequestedSessionIdFromCookie() == true) {
				ScormLog.setDebug("requested session id from cookie");
			}

			ScormLog.setDebug("query string: " + request.getQueryString()
					+ "\nheader string: " + request.getContextPath()
					+ "\ncookie header: " + request.getHeader("Cookie"));

			for (Enumeration e = request.getHeaderNames(); e.hasMoreElements();)
				ScormLog.setDebug(e.nextElement().toString());

			ScormLog.setDebug("POST received by LMSCMIServlet");
			HttpSession session = request.getSession(false);
			if (session == null) {
				ScormLog.setDebug("this is bad - no session in cmi servlet");
			} else {
				ScormLog.setDebug("session id is: " + session.getId());
			}

			ScormLog.setDebug("about to check attributes");

			for (Enumeration e = request.getHeaderNames(); e.hasMoreElements();)
				ScormLog.setDebug(e.nextElement().toString());

			scoID = (String) session.getAttribute("SCOID");
			courseID = (String) session.getAttribute("COURSEID"); //课件ID
			userID = (String) session.getAttribute("USERID");
			
			//add lwx open_course_id;
			this.openId = (String)session.getAttribute("openId"); //开课ID
			

			ScormLog.setDebug("session scoID: " + scoID); 

			// Open the input stream and pull off the incomming command
			ObjectInputStream in = new ObjectInputStream(request.getInputStream());
			ScormLog.setDebug("Created REQUEST object INPUT stream successfully");
			ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());
			ScormLog.setDebug("Created RESPONSE object OUTPUT stream successfully");
			String command = (String) in.readObject();
			ScormLog.setDebug("Command to LMSCMIServlet is: " + command);
			// Process the incomming command accordingly
			ScormFactory scormFactory = ScormFactory.getInstance();
			ScormManage scormManage = scormFactory.creatScormManage();

			// putParam
			if (command.equalsIgnoreCase("cmiputcat")) {
				logoutFlag = false;
				SCODataManager inSCOData = (SCODataManager) in.readObject();
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
					CMICore lmsCore = inSCOData.getCore();

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

					
					//在此加入lesson_location的位置信息;
				//	if(lmsCore.getLessonLocation().getValue() ==null || "".equals(lmsCore.getLessonLocation().getValue()))
				//		lmsCore.setLessonLocation(this.scoID);
					
					lessonStatus = lmsCore.getLessonStatus().getValue(); 
					lessonExit = lmsCore.getExit().getValue();
					lessonEntry = lmsCore.getEntry().getValue();
					lessonTime = lmsCore.getSessionTime().getValue();
					inSCOData.setCore(lmsCore);
					ScormLog.setDebug("The SCO Data Manager for the current SCO contains the following:");
					inSCOData.getCore().showData();
					scormManage.putIntoDB(inSCOData, userID, courseID, scoID,openId);
                   
					List userScoData = scormManage.getUserScoDatas(userID,
							courseID, scoID);
					for (int i = 0; i < userScoData.size(); i++) {
						String newStatus = ((UserScoData) userScoData.get(i)).getCore().getLessonStatus().getValue();
						ScormLog.setDebug("the status in sequencingEngine after set is: "
										+ newStatus);
					}
				} catch (Exception e) {
					e.printStackTrace();

				}

				if (logoutFlag == true) {
					session.setAttribute("EXITFLAG", "true");
				} else {
					session.removeAttribute("EXITFLAG");
				}
			} else if (command.equalsIgnoreCase("cmigetcat")) {

				SCODataManager scoData = scormManage.getFromDB(userID,courseID, scoID);
				scoData.getCore().setSessionTime("00:00:00.0");
				scoData.getCore().showData();
				out.writeObject(scoData);
				ScormLog.setDebug("LMSCMIServlet processed get for SCO Data\n");

			} else // invalid command sent, real LMS would handle this more
					// gracefully
			{
				String err_msg = "invalid command";
				out.writeObject(err_msg);
			}

			// Close the input and output streams
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // end doPost

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
