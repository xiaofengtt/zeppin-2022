/**
 * 
 */
package com.whaty.platform.training.util;

import java.util.ArrayList;
import java.util.List;

//import javax.servlet.http.HttpSession;

import com.whaty.platform.Exception.PlatformException;

import com.whaty.platform.training.TrainingFactory;
import com.whaty.platform.training.TrainingStudentOperationManage;

import com.whaty.platform.training.basic.TrainingClass;
import com.whaty.platform.training.user.TrainingStudentPriv;

//import uk.ltd.getahead.dwr.WebContext;
//import uk.ltd.getahead.dwr.WebContextFactory;
///


/**
 * @author chenjian
 *
 */
public class TrainingFunctionForDWR {
	
	public List getClassCourses() {
		List classes=new ArrayList();
		List classid=new ArrayList();
		//WebContext ctx = WebContextFactory.get();
		//HttpSession session = ctx.getSession();
		TrainingFactory factory=TrainingFactory.getInstance();
		//TrainingStudentPriv includePriv=(TrainingStudentPriv)session.getAttribute("trainingstudent_priv");
		TrainingStudentPriv includePriv=factory.creatTrainingStudentPriv("chenjian");
		TrainingStudentOperationManage stuManage=factory.creatTrainingUserOperationManage(includePriv);
		try {
			 classes=stuManage.getClasses();
		} catch (PlatformException e) {
			// TODO Auto-generated catch block
			
		}
		for(int i=0;i<classes.size();i++)
		{
			classid.add(((TrainingClass)classes.get(i)).getId());
		}
		return classid;
	}
	
	public List getChildCourseTypes(String parentTypeId) throws PlatformException
	{
		List courseTypes=new ArrayList();
		//WebContext ctx = WebContextFactory.get();
		//HttpSession session = ctx.getSession();
		TrainingFactory factory=TrainingFactory.getInstance();
		//TrainingStudentPriv includePriv=(TrainingStudentPriv)session.getAttribute("trainingstudent_priv");
		TrainingStudentPriv includePriv=factory.creatTrainingStudentPriv("chenjian");
		TrainingStudentOperationManage stuManage=factory.creatTrainingUserOperationManage(includePriv);
		courseTypes=stuManage.getChildCourseTypes(parentTypeId);
		return courseTypes;
	}
	

}
