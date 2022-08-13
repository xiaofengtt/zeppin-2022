package com.whaty.platform.standard.scorm.offline;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.sso.SsoFactory;
import com.whaty.platform.sso.SsoManage;
import com.whaty.platform.sso.SsoUser;
import com.whaty.platform.standard.scorm.Exception.ScormException;
import com.whaty.platform.standard.scorm.datamodels.Element;
import com.whaty.platform.standard.scorm.datamodels.SCODataManager;
import com.whaty.platform.standard.scorm.datamodels.cmi.CMICore;
import com.whaty.platform.standard.scorm.datamodels.cmi.CMIScore;
import com.whaty.platform.standard.scorm.datamodels.cmi.CMITime;
import com.whaty.platform.standard.scorm.operation.ScormCourse;
import com.whaty.platform.standard.scorm.operation.ScormFactory;
import com.whaty.platform.standard.scorm.operation.ScormItem;
import com.whaty.platform.standard.scorm.operation.ScormManage;
import com.whaty.platform.training.basic.StudyProgress;
import com.whaty.util.JsonUtil;

public class OffLineRecordManage {
	
	private List offLineRecordList; //学生离线学习记录
	private List<OffLineRecord> totalRecordList;	//学生整体学习记录;
	private String openCourseId;
	
	public final static String COURSEWARE_NOT_ATTEMPTED = "not attempted";
	
	public OffLineRecordManage(){
		offlineData = new ArrayList<OffLineScoStruts>();
	}
	
	public OffLineRecordManage(List list){
		this.offLineRecordList = list;
		totalRecordList = new ArrayList<OffLineRecord>();
	}
	
	public OffLineRecordManage(List list,String openCourseId){
		this.offLineRecordList = list;
		totalRecordList = new ArrayList<OffLineRecord>();
		this.openCourseId = openCourseId;
	}
	
	
	
	/**
	 * 对离线学习记录进行处理
	 */
	public void dealOffLineRecord()throws ScormException,PlatformException{
		
		if(CollectionUtils.isNotEmpty(offLineRecordList)){
			
			for(int i = 0;i<offLineRecordList.size();i++){
				OffLineRecord studentRecord = (OffLineRecord)offLineRecordList.get(i);
				
				OffLineRecord totalRecord = dealStudyRecord(studentRecord); 
				totalRecordList.add(totalRecord); 
			}
		}
	}
	
	/**
	 * 单个离线学习记录处理,返回处理后的记录;
	 */
	private OffLineRecord dealStudyRecord(OffLineRecord record)throws ScormException,PlatformException{
		
		OffLineRecord totalOffLineRecord = record;
//		this.sid = record.getSid();
		this.studentID = record.getStudentID();
		this.courseID = record.getCourseID();
		this.title = record.getTitle();
		
		
		//判断学生登陆帐号并取得id;
		SsoFactory sso = SsoFactory.getInstance();
		SsoManage ssoManage = sso.creatSsoManage();
		SsoUser user =  ssoManage.getSsoUserByLogin(this.studentID);
		if(user == null){
			throw new ScormException("服务器中没有您的记录，请确定登陆帐号的正确性!");
		}else{
			this.studentID = user.getId();
		}
		
		//判断课件是否存在
		ScormFactory sf = ScormFactory.getInstance();
		ScormManage sm = sf.creatScormManage();
		ScormCourse sCourse = sm.getScormCourse(courseID);
		if(sCourse==null||sCourse.getCourseId()==null){
			throw new ScormException("服务器中没有该课件的记录，请确定课件的正确性!");
		}
		
		
		
		List<OffLineSco> scoList = record.getItems();
		
		List<OffLineSco> totalOffLineScoList = new ArrayList();
		for(int i = 0;i<scoList.size();i++){
			OffLineSco sco = (OffLineSco)scoList.get(i);
			
			sco = dealSco(sco);
			totalOffLineScoList.add(sco);
		}
		totalOffLineRecord.setItems(totalOffLineScoList); 
		return totalOffLineRecord;
	}
	
	/**
	 * 对学习单元进行处理,把离线学习记录与在线学习记录统一起来;
	 */
	private OffLineSco dealSco(OffLineSco sco)throws ScormException{
		
		//获得用户的平台学习sco
		ScormFactory scormFactory = ScormFactory.getInstance();
		ScormManage scormManage = scormFactory.creatScormManage();
		this.sid = sco.getSid();
		SCODataManager platformScoData = scormManage.getFromDB(studentID, courseID, sid);
		
		//获得离线学习sco
		OffLineScoData offLineScoData = sco.getRTData();
		
		OffLineScoData totalScoData = compareScoData(platformScoData, offLineScoData);
		OffLineSco totalSco = sco;
		totalSco.setRTData(totalScoData);
		return totalSco;
	}
	
	/**
	 * 对两个sco记录进行比较,返回一个新的sco记录;
	 */
	private OffLineScoData compareScoData(SCODataManager platformData,OffLineScoData offLineData)throws ScormException{
		
		if(platformData == null){
			return offLineData;
		}else if(offLineData == null){
			
			offLineData = new  OffLineScoData();
			offLineData.setScoID(this.sid);
			offLineData.setSuspend_data(platformData.getSuspendData().getSuspendData().getValue());
			offLineData.setLaunch_data(platformData.getLaunchData().getLaunchData().getValue());
			offLineData.setComments(platformData.getComments().getComments().getValue());
			offLineData.setCore(getOffLineCore(platformData.getCore()));
			offLineData.setCmiParL1(new ArrayList());
			offLineData.setCmiParL2(new ArrayList());
			offLineData.setStudent_data(new ArrayList());
			
			return offLineData;
		}
		
		CMICore pCore = platformData.getCore();
		
		OffLineScoData returnScoData = offLineData;
		
		List tempCore = offLineData.getCore();
		CMICore oCore = initCMICore(tempCore);  //生成离线的临时core用于比较;
		
		SCODataManager newScoData = new SCODataManager();
		CMICore newCore = new CMICore();
		CMIScore newScore = new CMIScore();
		
		
		if(StringUtils.isNotBlank(pCore.getStudentId().getValue())){ //pcore studnet_id 不为空,表示平台有在线记录;则初始化为在线的记录;
			newCore = pCore; //使用平台的进行初始化;
		}else{
			newCore = initCMICore(tempCore); // 否则初始化为离线的记录;
		}
			
			//判断学习状态; 
			if(StudyProgress.COMPLETED.equalsIgnoreCase(pCore.getLessonStatus().getValue()) || StudyProgress.COMPLETED.equalsIgnoreCase(oCore.getLessonStatus().getValue())){ //平台此sco学习完成,则取平台的数据;
				newCore.setLessonStatus(StudyProgress.COMPLETED.toLowerCase());
			}else if(COURSEWARE_NOT_ATTEMPTED.equalsIgnoreCase(pCore.getLessonStatus().getValue()) && COURSEWARE_NOT_ATTEMPTED.equalsIgnoreCase(oCore.getLessonStatus().getValue()) ||
					StringUtils.isBlank(pCore.getLessonStatus().getValue()) && COURSEWARE_NOT_ATTEMPTED.equalsIgnoreCase(oCore.getLessonStatus().getValue())){//离线此sco学习完成,则取离线数据;
					newCore.setLessonStatus(COURSEWARE_NOT_ATTEMPTED);
			}else{ //两边都没有完成;
				newCore.setLessonStatus(StudyProgress.INCOMPLETE.toLowerCase());
			}
			
			//判断成绩;
			CMIScore pScore = pCore.getScore();
			CMIScore oScore = oCore.getScore();
			
	 		String psValue = StringUtils.isBlank(pScore.getRaw().getValue())||"null".equals(pScore.getRaw().getValue())? "0.0" : pScore.getRaw().getValue(); 
			Double ps = Double.parseDouble(psValue);
			Double po = Double.parseDouble(oScore.getRaw().getValue());
			
			if(ps.compareTo(po)>=0){
				newScore.setRaw(String.valueOf(ps));
			}else{
				newScore.setRaw(String.valueOf(po));
			}
			
			
			//处理总时间; 以平台记录的总时间为基础 + 离线记录的uncommit_time;做为课件的总时间;
			  CMITime totalTime =
		            new CMITime( pCore.getTotalTime().getValue() );
			  
			  CMITime sessionTime =
		            new CMITime( oCore.getUncommitTime().getValue() ); //取离线的umcommit_time为 session_time;
			  
			  totalTime.add(sessionTime); //计算时间总合;
			  newCore.setTotalTime(totalTime.toString());
			  
			  //更新离线记录的uncommit_time;为0; 用于返回给离线课件;
			  newCore.setUncommitTime("00:00:00.0");
			  newCore.setSessionTime(newCore.getUncommitTime().getValue());
			  
			  //设置学习位置lesson_location
			  newCore.setLessonLocation(oCore.getLessonLocation().getValue());
			  
			  newCore.setScore(newScore);
			  
			  //返回scoData
			  newScoData.setCore(newCore);
			  
			  
			  //处理 scoData 中的commnets;
			  String newComments = this.getNewComments(platformData.getComments().getComments().getValue(),offLineData.getComments());
			  newScoData.getComments().setComments(newComments); 
			  
			  
			  //将新数据记录到数据库中;
			  ScormFactory scormFactory = ScormFactory.getInstance();
			  ScormManage scormManage = scormFactory.creatScormManage();
			  scormManage.putIntoDB(newScoData, this.studentID, this.courseID, this.sid,this.openCourseId);
			  
			  returnScoData.setCore(this.getOffLineCore(newCore));
			  
			  returnScoData.setComments(newComments);
			  
			  return returnScoData;
		
		
	}
	
	/**
	 * 根据在线与离线的comments生成新的comments
	 * @param platformComments
	 * @param offlineComments
	 * @return
	 */
	private String getNewComments(String platformComments,String offlineComments) {
		
		OfflineComments pComments = getOfflineComments(platformComments);
		OfflineComments oComments = getOfflineComments(offlineComments);
		
		//处理自测值; 
		List<InnerTest> list = new ArrayList<InnerTest>();
		list.addAll(pComments.getQuiz());
		
		InnerTest valueTest = null;
		for(int i =0;i<oComments.getQuiz().size();i++){
			boolean in = false;
			InnerTest oTest = oComments.getQuiz().get(i);
			for(int j = 0; j<list.size();j++){
				
				InnerTest pTest = list.get(j);
				 if(oTest.getName().equals(pTest.getName())){
					 
					 valueTest = new InnerTest();
					 valueTest.setName(oTest.getName());
					 String score = Double.parseDouble(oTest.getValue()) > Double.parseDouble(pTest.getValue()) ? oTest.getValue() : pTest.getValue();
					 valueTest.setValue(score);
					 
					 list.set(j, valueTest);
					 
					 in = true;
					 break;
				 }
			}
			
			if(!in){ //不存在则加入到list中;
				list.add(oTest);
			}
		}
		
		OfflineComments value = new OfflineComments();
		value.setLastStudyTime(oComments.getLastStudyTime());
		value.setQuiz(list);
		
		String co = JsonUtil.toJSONString(value);
		return  co;
	}

	private OfflineComments getOfflineComments(String comments) {
		
		OfflineComments value = null;
		
		if(StringUtils.isBlank(comments) || !comments.startsWith("{")){ 
			value = new OfflineComments();
			return value;
		}
		
		JSONObject obj = JSONObject.fromObject(comments);  
		if(obj.isNullObject()){
			return new OfflineComments();
		}
		
		
		value = new OfflineComments();
		if(obj.containsKey("lastStudyTime")){
			value.setLastStudyTime(obj.getString("lastStudyTime"));
		}
		JSONArray quizArray = null;
		if(obj.containsKey("quiz")){
			quizArray = JSONArray.fromObject(obj.getString("quiz")); 
		}
		List<InnerTest> list = new ArrayList<InnerTest>();
		if (quizArray != null) {
			InnerTest intest = null;
			for (int j = 0; j < quizArray.size(); j++) {
				JSONObject quObj = quizArray.getJSONObject(j);
				intest = new InnerTest(quObj.getString("name"), quObj
						.getString("value"));
				list.add(intest);
			}
		}
		
		value.setQuiz(list);
		
		return value;
		
	}

	//用离线的core初始化CMIcore;
	private CMICore initCMICore(List offineCore){
		CMICore core = new CMICore();
		for(int i = 0; offineCore!=null && i <offineCore.size();i++){
			OffLineElement obj = (OffLineElement)offineCore.get(i);
			String id = obj.getId();
			if(!"score".equals(id)){
				String value = fixNull((String)obj.getValue());
				if("student_id".equals(id)){
					core.setStudentId(value);
				}else if("student_name".equals(id)){
					core.setStudentName(value);
				}else if("lesson_location".equals(id)){
					core.setLessonLocation(value);
				}else if("credit".equals(id)){
					core.setCredit(value);
				}else if("lesson_status".equals(id)){
					core.setLessonStatus(value);
				}else if("entry".equals(id)){
					core.setEntry(value);
				}else if("total_time".equals(id)){
					core.setTotalTime(value);
				}else if("session_time".equals(id)){
					core.setSessionTime(value);
				}else if("uncommit_time".equals(id)){
					core.setUncommitTime(value);
				}
			}else{//处理成绩
				
				CMIScore score = new CMIScore();
//				List sList = (List)obj.getValue();  
				OffLineElement[] sList = (OffLineElement[])obj.getValue();
				for(OffLineElement el: sList){
					String scoreId = el.getId(); 
					String scoreValue = fixNull(String.valueOf(el.getValue())); 
	 				scoreValue = StringUtils.isBlank(scoreValue)? "0.0":scoreValue; //处理成绩为空的情况； 
					if("raw".equals(scoreId)){
						score.setRaw(scoreValue);
					}else if("min".equals(scoreId)){
						score.setMin(scoreValue);
					}else if("max".equals(scoreId)){
						score.setMax(scoreValue);
					}
				}
				
				core.setScore(score); //加入成绩;
			}
		}
		
		return core;
	}
	
	/**
	 * 从CMIcore 得到离线记录的core List;
	 * @param core
	 * @return
	 */
	public List getOffLineCore(Object bean)throws ScormException{
		
		List coreList = new ArrayList();
		OffLineElement el = null;
		
		String children = "";
		if(bean instanceof CMICore){
			children = ((CMICore)bean).getChildren();
		}else if(bean instanceof CMIScore){
			children = ((CMIScore)bean).getChildren();
		}
		try{
			Class clazz = bean.getClass();
			
			// 此处由于离线课件中要保证core中List的存储顺序，
			//将使用CMIcore中的getChildren()方法，getChildren()是按需要的顺序排好的；
			String[] child = children.split(",");
			for (String  fieldStr : child) { 
				el = new OffLineElement(); 
					String name = fieldStr;
					String fieldName = fieldStr;
					// 生成方法名
					String[] namespace = name.split("_");
					name = "get";
					for (String str : namespace) {
						
						name += str.substring(0, 1).toUpperCase()
								+ str.substring(1);
					}
					Method theMethod = clazz.getMethod(name);
					Object obj = theMethod.invoke(bean, null);
					if(obj instanceof Element){
						Element e = (Element)obj;
						el.setId(fieldName);
						el.setValue(e.getValue());
						el.setType(e.getType());
						el.setIo((e.isReadable()? "r":"") + (e.isWriteable()? "w": ""));
					}else if(obj instanceof CMIScore){
						el.setId(fieldName);
						List scoreList = this.getOffLineCore(obj);
						el.setValue(scoreList);
					}

				
				coreList.add(el);
			}
		}catch (NoSuchMethodException e) {
			throw new ScormException(e.getMessage());
		}catch (InvocationTargetException e) {
			throw new ScormException(e.getMessage());
		}catch (IllegalAccessException e) {
			throw new ScormException(e.getMessage());
		}
		
		return coreList;
	}
	
	

	public List<OffLineRecord> getOffLineRecordList() {
		return offLineRecordList;
	}

	public void setOffLineRecordList(List<OffLineRecord> offLineRecordList) {
		this.offLineRecordList = offLineRecordList;
	}

	public List<OffLineRecord> getTotalRecordList() {
		return totalRecordList;
	}

	public void setTotalRecordList(List<OffLineRecord> totalRecordList) {
		this.totalRecordList = totalRecordList;
	}
	
	
	//由于提交的json与javabean不一致; 因此单独此方法生成指定的对象;
	public static List<Object> getListFromJsonString(String str,int key){
		
		List returnList = null;
		
		switch (key) {
		case 0: //处理学生记录
			List<OffLineRecord> list = new ArrayList<OffLineRecord>();
			OffLineRecord record = null;
			JSONArray array = JSONArray.fromObject(str);
			   
			   for(int i = 0;i<array.size();i++){
					JSONObject obj = array.getJSONObject(i);
					
					record = new OffLineRecord();
					record.setSid(fixNull(obj.getString("sid")));
					record.setRid(fixNull(obj.getString("rid")));
					record.setStudentID(obj.getString("studentID"));
					record.setCourseID(obj.getString("courseID"));
					record.setTitle(fixNull(obj.getString("title")));
					record.setVisible(obj.getBoolean("visible"));
					record.setHref(fixNull(obj.getString("href")));
					record.setRTData(obj.getString("RTData"));
					
					List item = OffLineRecordManage.getListFromJsonString(obj.getString("items"), 1);
					record.setItems((List<OffLineSco>)item);
					
					list.add(record); 
					
			   }
			returnList = list;
			break;
		
		case 1: //处理单条记录;
			List<OffLineSco> sList = new ArrayList<OffLineSco>();
			OffLineSco sco = null;
			JSONArray scoArray = JSONArray.fromObject(str);
			
			for(int i = 0;i<scoArray.size();i++){
					JSONObject obj = scoArray.getJSONObject(i);
					
					sco = new OffLineSco();
					sco.setSid(fixNull(obj.getString("sid")));
					sco.setRid(fixNull(obj.getString("rid")));
					sco.setStudentID(obj.getString("studentID"));
					sco.setCourseID(obj.getString("courseID"));
					sco.setTitle(fixNull(obj.getString("title")));
					sco.setHref(fixNull(obj.getString("href")));
					sco.setVisible(obj.getBoolean("visible"));
					
					List items = OffLineRecordManage.getListFromJsonString("["+obj.getString("RTData")+"]", 2);
					if(CollectionUtils.isNotEmpty(items))
						sco.setRTData((OffLineScoData)items.get(0));
					
					sList.add(sco);
					
			   }
			returnList = sList;
			break;
			
		case 2: //处理SCODataManager
			List<OffLineScoData> scoDataList = new ArrayList<OffLineScoData>();
			OffLineScoData scoData = null;
			JSONArray scoDataArray = JSONArray.fromObject(str);
			
			for(int i = 0;i<scoDataArray.size();i++){
				JSONObject obj = scoDataArray.getJSONObject(i);
				
				scoData = new OffLineScoData();
				scoData.setSuspend_data(fixNull(obj.getString("suspend_data")));
				scoData.setLaunch_data(fixNull(obj.getString("launch_data")));
				String comments = obj.getString("comments");
				System.out.println("c: "+comments);
				scoData.setComments("\"\"".equals(comments) ? "":comments);  
				
				scoData.setScoID(obj.getString("scoID"));
				
				List items = OffLineRecordManage.getListFromJsonString(obj.getString("core"), 3);
				if(CollectionUtils.isNotEmpty(items))
					scoData.setCore(items);
				
				//处理cmiParL1
				List cmiParL1 = OffLineRecordManage.getListFromJsonString(obj.getString("cmiParL1"), 4);
				scoData.setCmiParL1(cmiParL1);
				
//				处理cmiParL2
				List cmiParL2= OffLineRecordManage.getListFromJsonString(obj.getString("cmiParL2"), 4);
				scoData.setCmiParL2(cmiParL2);
				
				//处理student_data
				List studentDataList= OffLineRecordManage.getListFromJsonString(obj.getString("student_data"), 3);
				scoData.setStudent_data(studentDataList);
				
				scoDataList.add(scoData);
				
		   }
		returnList = scoDataList;
		break;
		
		case 3:
			List coreList = new ArrayList();
			
			JSONArray coreArray = JSONArray.fromObject(str);
			for(int i = 0;i<coreArray.size();i++){
				JSONObject obj = coreArray.getJSONObject(i);
				
				String id = fixNull(obj.getString("id"));
				String value = fixNull(obj.getString("value"));
				String type = fixNull(obj.getString("type"));
				String io = fixNull(obj.getString("io"));
				OffLineElement el = new OffLineElement();
				
				if(!"score".equals(id)){
					el.setId(id);
					el.setValue(value);
					el.setType(type);
					el.setIo(io);
					
					coreList.add(el);
				}else {
					
					
					//处理成绩;
					JSONArray scoreArray = JSONArray.fromObject(obj.getString("value"));
					OffLineElement scoreEl = null;
					
					OffLineElement[] scoreList = new OffLineElement[scoreArray.size()];
					for(int m=0;m<scoreArray.size();m++){  
						scoreEl = new OffLineElement();
						JSONObject scoreObj = scoreArray.getJSONObject(m);
						String scoreId = fixNull(scoreObj.getString("id"));
						String scoreValue = fixNull(scoreObj.getString("value")); 
						String scoreType = fixNull(scoreObj.getString("type"));
						String scoreIo = fixNull(scoreObj.getString("io"));
						
						scoreEl.setId(scoreId);
						scoreEl.setValue(StringUtils.isBlank(scoreValue)? "0.0":scoreValue); //若为空，则为0.0;
						scoreEl.setType(scoreType);
						scoreEl.setIo(scoreIo);
						
						scoreList[m] = scoreEl; //添加成绩元素;
					}
					
					el.setId(id);
					el.setValue(scoreList);
					el.setType(type);
					el.setIo(io);
					
					coreList.add(el);
				}
		   }
			
			returnList = coreList;
		break;
		
		case 4:
			List<String> parL = new ArrayList<String>(); 
			JSONArray arrayParL = JSONArray.fromObject(str);
			List<String> l  = JSONArray.toList(arrayParL, String.class);
			for(int m = 0;m<l.size();m++){
				String v = l.get(m);
				parL.add(v);
			}
			
			returnList = parL;
		break;
		}
	
		return returnList;
	}
	
	private static String fixNull(String str){
		if(StringUtils.isBlank(str)){
			return str;
		}else{
			if("null".equals(str))
				return "";
			else
				return str;
		}
	}
	
	
	public static void main(String[]args)throws ScormException,PlatformException{
//		String message = "{\"courseID\":\"1554\",\"visible\":\"true\",\"href\":\"/1554/index.htm\"," +
//				"\"rid\":\"\",\"RTData\":{\"student_data\":null,\"suspend_data\":null,\"cmiParL2\":null," +
//				"\"core\":[{\"id\":\"student_id\",\"value\":\"206941\",\"parseJSON\":null,\"io\":\"r\",\"type\"" +
//				":\"checkIdentifier\",\"toJSONString\":null},{\"id\":\"student_name\",\"value\":\"\",\"parseJSON\":" +
//				"null,\"io\":\"r\",\"type\":\"checkString255\",\"toJSONString\":null},{\"id\":\"lesson_location\"," +
//				"\"value\":null,\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkString255\",\"toJSONString\":null}," +
//				"{\"id\":\"credit\",\"value\":null,\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkVocabulary\",\"toJSONString\":null}," +
//				"{\"id\":\"lesson_status\",\"value\":\"completed\",\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkVocabulary\",\"toJSONString\":null}," +
//				"{\"id\":\"entry\",\"value\":null,\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkVocabulary\"," +
//				"\"toJSONString\":null},{\"id\":\"score\",\"value\":[{\"id\":\"raw\",\"value\":\"66\",\"parseJSON\":null,\"io\":\"rw\"," +
//				"\"type\":\"checkScoreDecimal\",\"toJSONString\":null},{\"id\":\"min\",\"value\":null,\"parseJSON\":null," +
//				"\"io\":\"rw\",\"type\":\"checkScoreDecimal\",\"toJSONString\":null},{\"id\":\"max\",\"value\":null," +
//				"\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkScoreDecimal\",\"toJSONString\":null}],\"parseJSON\":null," +
//				"\"io\":null,\"type\":null,\"toJSONString\":null},{\"id\":\"total_time\",\"value\":\"00:00:34.88\",\"parseJSON\":null," +
//				"\"io\":\"r\",\"type\":\"checkTimespan\",\"toJSONString\":null},{\"id\":\"exit\",\"value\":null,\"parseJSON\":null," +
//				"\"io\":\"w\",\"type\":\"checkVocabulary\",\"toJSONString\":null},{\"id\":\"session_time\",\"value\":\"\",\"parseJSON\":null," +
//				"\"io\":\"w\",\"type\":\"checkTimespan\",\"toJSONString\":null},{\"id\":\"uncommit_time\",\"value\":\"\"," +
//				"\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkString255\",\"toJSONString\":null},{\"id\":\"lesson_mode\"," +
//				"\"value\":\"\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkVocabulary\",\"toJSONString\":null}]," +
//				"\"comments\":null,\"scoID\":null,\"parseJSON\":null,\"launch_data\":null,\"cmiParL1\":null,\"toJSONString\":null}," +
//				"\"studentID\":\"206941\",\"sid\":\"A\",\"items\":[{\"courseID\":\"1554\",\"visible\":\"true\"," +
//				"\"href\":\"/1554/lljx/frame.html?scoID=1\",\"rid\":\"\",\"RTData\":{\"student_data\":null,\"suspend_data\":null,\"cmiParL2\":null," +
//				"\"core\":[{\"id\":\"student_id\",\"value\":\"206941\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkIdentifier\"," +
//				"\"toJSONString\":null},{\"id\":\"student_name\",\"value\":\"\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkString255\"," +
//				"\"toJSONString\":null},{\"id\":\"lesson_location\",\"value\":null,\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkString255\"," +
//				"\"toJSONString\":null},{\"id\":\"credit\",\"value\":null,\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkVocabulary\"," +
//				"\"toJSONString\":null},{\"id\":\"lesson_status\",\"value\":\"incomplete\",\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkVocabulary\"," +
//				"\"toJSONString\":null},{\"id\":\"entry\",\"value\":null,\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkVocabulary\",\"toJSONString\":null}," +
//				"{\"id\":\"score\",\"value\":[{\"id\":\"raw\",\"value\":null,\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkScoreDecimal\",\"toJSONString\":null}," +
//				"{\"id\":\"min\",\"value\":null,\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkScoreDecimal\",\"toJSONString\":null}," +
//				"{\"id\":\"max\",\"value\":null,\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkScoreDecimal\",\"toJSONString\":null}]," +
//				"\"parseJSON\":null,\"io\":null,\"type\":null,\"toJSONString\":null},{\"id\":\"total_time\",\"value\":\"00:00:04.92\"," +
//				"\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkTimespan\",\"toJSONString\":null},{\"id\":\"exit\",\"value\":null," +
//				"\"parseJSON\":null,\"io\":\"w\",\"type\":\"checkVocabulary\",\"toJSONString\":null},{\"id\":\"session_time\",\"value\":\"\"," +
//				"\"parseJSON\":null,\"io\":\"w\",\"type\":\"checkTimespan\",\"toJSONString\":null},{\"id\":\"uncommit_time\",\"value\":\"\"," +
//				"\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkString255\",\"toJSONString\":null},{\"id\":\"lesson_mode\",\"value\":\"\"," +
//				"\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkVocabulary\",\"toJSONString\":null}],\"comments\":null,\"scoID\":null," +
//				"\"parseJSON\":null,\"launch_data\":null,\"cmiParL1\":null,\"toJSONString\":null},\"studentID\":\"206941\",\"sid\":\"A1\"," +
//				"\"items\":[{\"courseID\":\"1554\",\"visible\":\"true\",\"href\":\"/1554/lljx/frame.html?scoID=1001\",\"rid\":\"\",\"RTData\":{\"student_data\":null,\"suspend_data\":null,\"cmiParL2\":null,\"core\":[{\"id\":\"student_id\",\"value\":\"206941\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkIdentifier\",\"toJSONString\":null},{\"id\":\"student_name\",\"value\":\"\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkString255\",\"toJSONString\":null},{\"id\":\"lesson_location\",\"value\":null,\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkString255\",\"toJSONString\":null},{\"id\":\"credit\",\"value\":null,\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkVocabulary\",\"toJSONString\":null},{\"id\":\"lesson_status\",\"value\":\"incomplete\",\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkVocabulary\",\"toJSONString\":null},{\"id\":\"entry\",\"value\":null,\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkVocabulary\",\"toJSONString\":null},{\"id\":\"score\",\"value\":[{\"id\":\"raw\",\"value\":\"44\",\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkScoreDecimal\",\"toJSONString\":null},{\"id\":\"min\",\"value\":null,\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkScoreDecimal\",\"toJSONString\":null},{\"id\":\"max\",\"value\":null,\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkScoreDecimal\",\"toJSONString\":null}],\"parseJSON\":null,\"io\":null,\"type\":null,\"toJSONString\":null},{\"id\":\"total_time\",\"value\":\"00:00:49.22\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkTimespan\",\"toJSONString\":null},{\"id\":\"exit\",\"value\":null,\"parseJSON\":null,\"io\":\"w\",\"type\":\"checkVocabulary\",\"toJSONString\":null},{\"id\":\"session_time\",\"value\":\"\",\"parseJSON\":null,\"io\":\"w\",\"type\":\"checkTimespan\",\"toJSONString\":null},{\"id\":\"uncommit_time\",\"value\":\"\",\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkString255\",\"toJSONString\":null},{\"id\":\"lesson_mode\",\"value\":\"\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkVocabulary\",\"toJSONString\":null}],\"comments\":null,\"scoID\":null,\"parseJSON\":null,\"launch_data\":null,\"cmiParL1\":null,\"toJSONString\":null},\"studentID\":\"206941\",\"sid\":\"A1001\",\"items\":[],\"parseJSON\":\"\",\"toJSONString\":\"\",\"title\":\"知识点一　企业物流的概述\"},{\"courseID\":\"1554\",\"visible\":\"true\",\"href\":\"/1554/lljx/frame.html?scoID=1002\",\"rid\":\"\",\"RTData\":{\"student_data\":null,\"suspend_data\":null,\"cmiParL2\":null,\"core\":[{\"id\":\"student_id\",\"value\":\"206941\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkIdentifier\",\"toJSONString\":null},{\"id\":\"student_name\",\"value\":\"\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkString255\",\"toJSONString\":null},{\"id\":\"lesson_location\",\"value\":null,\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkString255\",\"toJSONString\":null},{\"id\":\"credit\",\"value\":null,\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkVocabulary\",\"toJSONString\":null},{\"id\":\"lesson_status\",\"value\":\"incomplete\",\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkVocabulary\",\"toJSONString\":null},{\"id\":\"entry\",\"value\":null,\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkVocabulary\",\"toJSONString\":null},{\"id\":\"score\",\"value\":[{\"id\":\"raw\",\"value\":null,\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkScoreDecimal\",\"toJSONString\":null},{\"id\":\"min\",\"value\":null,\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkScoreDecimal\",\"toJSONString\":null},{\"id\":\"max\",\"value\":null,\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkScoreDecimal\",\"toJSONString\":null}],\"parseJSON\":null,\"io\":null,\"type\":null,\"toJSONString\":null},{\"id\":\"total_time\",\"value\":\"00:00:05.69\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkTimespan\",\"toJSONString\":null},{\"id\":\"exit\",\"value\":null,\"parseJSON\":null,\"io\":\"w\",\"type\":\"checkVocabulary\",\"toJSONString\":null},{\"id\":\"session_time\",\"value\":\"\",\"parseJSON\":null,\"io\":\"w\",\"type\":\"checkTimespan\",\"toJSONString\":null},{\"id\":\"uncommit_time\",\"value\":\"\",\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkString255\",\"toJSONString\":null},{\"id\":\"lesson_mode\",\"value\":\"\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkVocabulary\",\"toJSONString\":null}],\"comments\":null,\"scoID\":null,\"parseJSON\":null,\"launch_data\":null,\"cmiParL1\":null,\"toJSONString\":null},\"studentID\":\"206941\",\"sid\":\"A1002\",\"items\":[],\"parseJSON\":\"\",\"toJSONString\":\"\",\"title\":\"知识点二　企业物流的分类\"},{\"courseID\":\"1554\",\"visible\":\"true\",\"href\":\"/1554/lljx/frame.html?scoID=1003\",\"rid\":\"\",\"RTData\":{\"student_data\":null,\"suspend_data\":\"\",\"cmiParL2\":null,\"core\":[{\"id\":\"student_id\",\"value\":\"\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkIdentifier\",\"toJSONString\":null},{\"id\":\"student_name\",\"value\":\"\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkString255\",\"toJSONString\":null},{\"id\":\"lesson_location\",\"value\":\"\",\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkString255\",\"toJSONString\":null},{\"id\":\"credit\",\"value\":\"\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkVocabulary\",\"toJSONString\":null},{\"id\":\"lesson_status\",\"value\":\"\",\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkVocabulary\",\"toJSONString\":null},{\"id\":\"entry\",\"value\":\"\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkVocabulary\",\"toJSONString\":null},{\"id\":\"score\",\"value\":[{\"id\":\"raw\",\"value\":\"\",\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkScoreDecimal\",\"toJSONString\":null},{\"id\":\"min\",\"value\":\"\",\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkScoreDecimal\",\"toJSONString\":null},{\"id\":\"max\",\"value\":\"\",\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkScoreDecimal\",\"toJSONString\":null}],\"parseJSON\":null,\"io\":null,\"type\":null,\"toJSONString\":null},{\"id\":\"total_time\",\"value\":\"\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkTimespan\",\"toJSONString\":null},{\"id\":\"exit\",\"value\":\"\",\"parseJSON\":null,\"io\":\"w\",\"type\":\"checkVocabulary\",\"toJSONString\":null},{\"id\":\"session_time\",\"value\":\"\",\"parseJSON\":null,\"io\":\"w\",\"type\":\"checkTimespan\",\"toJSONString\":null},{\"id\":\"uncommit_time\",\"value\":\"\",\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkString255\",\"toJSONString\":null},{\"id\":\"lesson_mode\",\"value\":\"\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkVocabulary\",\"toJSONString\":null}],\"comments\":\"\",\"scoID\":null,\"parseJSON\":null,\"launch_data\":\"\",\"cmiParL1\":null,\"toJSONString\":null},\"studentID\":\"206941\",\"sid\":\"A1003\",\"items\":[],\"parseJSON\":\"\",\"toJSONString\":\"\",\"title\":\"知识点三　企业物流的重要性\"},{\"courseID\":\"1554\",\"visible\":\"true\",\"href\":\"/1554/lljx/frame.html?scoID=1004\",\"rid\":\"\",\"RTData\":{\"student_data\":null,\"suspend_data\":\"\",\"cmiParL2\":null,\"core\":[{\"id\":\"student_id\",\"value\":\"\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkIdentifier\",\"toJSONString\":null},{\"id\":\"student_name\",\"value\":\"\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkString255\",\"toJSONString\":null},{\"id\":\"lesson_location\",\"value\":\"\",\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkString255\",\"toJSONString\":null},{\"id\":\"credit\",\"value\":\"\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkVocabulary\",\"toJSONString\":null},{\"id\":\"lesson_status\",\"value\":\"\",\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkVocabulary\",\"toJSONString\":null},{\"id\":\"entry\",\"value\":\"\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkVocabulary\",\"toJSONString\":null},{\"id\":\"score\",\"value\":[{\"id\":\"raw\",\"value\":\"\",\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkScoreDecimal\",\"toJSONString\":null},{\"id\":\"min\",\"value\":\"\",\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkScoreDecimal\",\"toJSONString\":null},{\"id\":\"max\",\"value\":\"\",\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkScoreDecimal\",\"toJSONString\":null}],\"parseJSON\":null,\"io\":null,\"type\":null,\"toJSONString\":null},{\"id\":\"total_time\",\"value\":\"\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkTimespan\",\"toJSONString\":null},{\"id\":\"exit\",\"value\":\"\",\"parseJSON\":null,\"io\":\"w\",\"type\":\"checkVocabulary\",\"toJSONString\":null},{\"id\":\"session_time\",\"value\":\"\",\"parseJSON\":null,\"io\":\"w\",\"type\":\"checkTimespan\",\"toJSONString\":null},{\"id\":\"uncommit_time\",\"value\":\"\",\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkString255\",\"toJSONString\":null},{\"id\":\"lesson_mode\",\"value\":\"\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkVocabulary\",\"toJSONString\":null}],\"comments\":\"\",\"scoID\":null,\"parseJSON\":null,\"launch_data\":\"\",\"cmiParL1\":null,\"toJSONString\":null},\"studentID\":\"206941\",\"sid\":\"A1004\",\"items\":[],\"parseJSON\":\"\",\"toJSONString\":\"\",\"title\":\"知识点四　企业物流功能和作业目标\"},{\"courseID\":\"1554\",\"visible\":\"true\",\"href\":\"/1554/lljx/frame.html?scoID=1005\",\"rid\":\"\",\"RTData\":{\"student_data\":null,\"suspend_data\":\"\",\"cmiParL2\":null,\"core\":[{\"id\":\"student_id\",\"value\":\"\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkIdentifier\",\"toJSONString\":null},{\"id\":\"student_name\",\"value\":\"\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkString255\",\"toJSONString\":null},{\"id\":\"lesson_location\",\"value\":\"\",\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkString255\",\"toJSONString\":null},{\"id\":\"credit\",\"value\":\"\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkVocabulary\",\"toJSONString\":null},{\"id\":\"lesson_status\",\"value\":\"\",\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkVocabulary\",\"toJSONString\":null},{\"id\":\"entry\",\"value\":\"\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkVocabulary\",\"toJSONString\":null},{\"id\":\"score\",\"value\":[{\"id\":\"raw\",\"value\":\"\",\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkScoreDecimal\",\"toJSONString\":null},{\"id\":\"min\",\"value\":\"\",\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkScoreDecimal\",\"toJSONString\":null},{\"id\":\"max\",\"value\":\"\",\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkScoreDecimal\",\"toJSONString\":null}],\"parseJSON\":null,\"io\":null,\"type\":null,\"toJSONString\":null},{\"id\":\"total_time\",\"value\":\"\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkTimespan\",\"toJSONString\":null},{\"id\":\"exit\",\"value\":\"\",\"parseJSON\":null,\"io\":\"w\",\"type\":\"checkVocabulary\",\"toJSONString\":null},{\"id\":\"session_time\",\"value\":\"\",\"parseJSON\":null,\"io\":\"w\",\"type\":\"checkTimespan\",\"toJSONString\":null},{\"id\":\"uncommit_time\",\"value\":\"\",\"parseJSON\":null,\"io\":\"rw\",\"type\":\"checkString255\",\"toJSONString\":null},{\"id\":\"lesson_mode\",\"value\":\"\",\"parseJSON\":null,\"io\":\"r\",\"type\":\"checkVocabulary\",\"toJSONString\":null}],\"comments\":\"\",\"scoID\":null,\"parseJSON\":null,\"launch_data\":\"\",\"cmiParL1\":null,\"toJSONString\":null},\"studentID\":\"206941\",\"sid\":\"A1005\",\"items\":[],\"parseJSON\":\"\",\"toJSONString\":\"\",\"title\":\"知识点五　企业物流管理的概念与内容\"}],\"parseJSON\":\"\",\"toJSONString\":\"\",\"title\":\"第一章　企业物流概述\"}],\"parseJSON\":\"\",\"toJSONString\":\"\",\"title\":\"企业物流管理 北京交通大学远程与继续教育学院\"}";

		String message = "";
		
		List list = OffLineRecordManage
		.getListFromJsonString(message, 0);
		OffLineRecordManage recordManage = new OffLineRecordManage(list);
		recordManage.dealOffLineRecord();
		List<OffLineRecord> newRecordList = recordManage
		.getTotalRecordList();
		System.out.println(JsonUtil.toJSONString(newRecordList));;
	}
	
	
	//=======================================重写的离线课件上传message,与平台进行比较,并返回综合后的结果;
	private String message;
	private Map classMap;
	
	private String sid;
	private String courseID;
	private String studentID;
	private String title;
	/**
	 * 根据给字json字符串初始化对象;
	 * @param message
	 */
	public OffLineRecordManage (String message){
		this.message = message;
		
		//指定初始化转换时需要的类;用于转成java对象;
		classMap = new HashMap();
		classMap.put("items", OffLineScoStruts.class);
		classMap.put("RTData", OffLineScoData.class);
		classMap.put("core", OffLineElement.class);
		classMap.put("student_data", OffLineElement.class);
		classMap.put("value", OffLineElement.class);
	}
	
	public OffLineScoStruts getComparedRecord()throws ScormException,PlatformException{
		
		OffLineScoStruts offlineRecord = null;//根据message生成的离线记录对象;
		OffLineScoStruts studyRecord = null;//返回的综合对象;
		
		if(StringUtils.isNotBlank(message)){ //若不为空;则与平台数据进行综合比较;
			
			//生成离线记录对象;
			Object obj = JsonUtil.fromStrToObject(message,OffLineScoStruts.class,this.classMap); 
			offlineRecord = (OffLineScoStruts)obj; 
			
			try {
				studyRecord = (OffLineScoStruts)recordHandle(offlineRecord); 
			} catch (ScormException e) {
				throw new ScormException(e.getMessage());
			}
			
		}else{ //若为空,则直接返回平台数据; 离线由于不提交数据无法获得学生帐号和课件id所以无法直接从平台获得信息;
			;
		}
		
		
		return offlineRecord;
	}

	/**
	 * 处理离线记录,并综合平台记录的数据;
	 * @param obj
	 * @return
	 */
	private Object recordHandle(Object obj)throws ScormException,PlatformException{
		//若为OffLineScoStruts
		if(obj instanceof OffLineScoStruts){
			OffLineScoStruts record = (OffLineScoStruts)obj;
			sid = record.getSid();
			courseID = record.getCourseID();
			studentID = record.getStudentID();
			title = record.getTitle();
			
			
//			判断学生登陆帐号并取得id;
			SsoFactory sso = SsoFactory.getInstance();
			SsoManage ssoManage = sso.creatSsoManage();
			SsoUser user =  ssoManage.getSsoUserByLogin(this.studentID);
			if(user == null){
				throw new ScormException("服务器中没有您的记录，请确定登陆帐号的正确性!");
			}else{
				this.studentID = user.getId();
			}
			
			//判断课件是否存在
			ScormFactory sf = ScormFactory.getInstance();
			ScormManage sm = sf.creatScormManage();
			ScormCourse sCourse = sm.getScormCourse(courseID);
			if(sCourse==null||sCourse.getCourseId()==null){
				throw new ScormException("服务器中没有该课件的记录，请确定课件的正确性!");
			}
			
			
			//获得离线核心数据;
			OffLineScoData offlineScoData = record.getRTData();
			if(offlineScoData != null){  
				offlineScoData = (OffLineScoData)recordHandle(offlineScoData);
				
			}else{  //去掉了为null时的处理,这里应该根据message生成的对象进行处理,若为null,则不做处理;
//				SCODataManager scoData = getPlatformScoDataManager(); //获得在线数据对象; 
//				
//				offlineScoData = compareScoData(scoData,null);
			}
			 
			//处理子级数据;
			List<OffLineScoStruts> items = new ArrayList<OffLineScoStruts>();
			if(record.getItems() != null && record.getItems().size()>0){ //
				for(OffLineScoStruts inRecord:record.getItems()){
					OffLineScoStruts tempRecord = (OffLineScoStruts)recordHandle(inRecord);
					items.add(tempRecord);
				}
			}
			
			record.setRTData(offlineScoData);
			record.setItems(items);
			
			return record;
			
		}else if(obj instanceof OffLineScoData){
			
			OffLineScoData offlineScoData = (OffLineScoData)obj; //离线
			
			SCODataManager scoData = getPlatformScoDataManager(); //获得在线数据对象; 
			
			//获得数据比较后的结构;
			offlineScoData = compareScoData(scoData,offlineScoData); 
			
			return offlineScoData;
	
		}else{
			throw new ScormException("对象不符合离线记录对象结构!");
		}
		
	}
	
	/**
	 * 获得在线数据;
	 * @return
	 */
	private SCODataManager getPlatformScoDataManager(){
//		获得在线平台数据对象;
		ScormFactory scormFacotry = ScormFactory.getInstance();
		ScormManage scormManage = scormFacotry.creatScormManage();
		SCODataManager scoData = null;;
		try {
			scoData = scormManage.getFromDB(studentID, courseID, sid);
		} catch (ScormException e) {
			;
		} //在线平台数据对象;
		return scoData;
	}
	
	
	List<OffLineScoStruts> offlineData;
	
//	直接获得平台数据;
	public void getPlatformData(String studentId,String courseId)throws PlatformException,ScormException{
		
		ScormFactory scormFactory = ScormFactory.getInstance();
		ScormManage scormManage = scormFactory.creatScormManage();
		List itemList = scormManage.getCoursesItems(courseId);
		List<OffLineScoStruts> scoList = new ArrayList<OffLineScoStruts>();
		OffLineScoStruts parentStruts = null;
		Map parentStrutsMap = new HashMap();
		 int previousLevel = -1;
		 int currLevel = -1;
		 
		 if(itemList != null && itemList.size()>0){
			 
			 ScormItem item = (ScormItem)itemList.get(0);
			 
			 SCODataManager scoData = scormManage.getFromDB(studentId, courseId, item.getId());
			 OffLineScoStruts offLineScoStruts = getDataFromScoData(scoData,studentId,courseId,item);
			 
			 parentStruts = offLineScoStruts;
			 parentStrutsMap.put(item.getTheLevel(), offLineScoStruts);
			 previousLevel = item.getTheLevel();
			 
			 for(int i =1;i<itemList.size();i ++){ 
				 
				 item = (ScormItem)itemList.get(i);
				 currLevel = item.getTheLevel();
				 
				 scoData = scormManage.getFromDB(studentId, courseId, item.getId());
				 offLineScoStruts = getDataFromScoData(scoData,studentId,courseId,item);
				 
				 if(currLevel == previousLevel){ //级别相同
					
					 int level = currLevel - 1;
					 parentStruts = (OffLineScoStruts)parentStrutsMap.get(level);
					 if(level == 0){//若没有父目录;
						 //把parent移动list;
						 scoList.add((OffLineScoStruts)parentStrutsMap.get(currLevel));
						 //重新设置map 的parent;
						 parentStrutsMap.put(currLevel, offLineScoStruts);
						 continue;
					 }
					 parentStruts.getItems().add(offLineScoStruts); 
					 
					 parentStrutsMap.put(level, parentStruts); //设置新值后重新放加map
					 parentStrutsMap.put(item.getTheLevel(), offLineScoStruts);//把新的对象加入到map;
					 
				 }else if(currLevel > previousLevel){//当前为子级
					 
					 if((currLevel-previousLevel) ==1){
						 
						 parentStruts = (OffLineScoStruts)parentStrutsMap.get(previousLevel);
						 parentStruts.getItems().add(offLineScoStruts);
						 
						 parentStrutsMap.put(previousLevel, parentStruts); //设置新值后重新放加map
						 parentStrutsMap.put(currLevel, offLineScoStruts);//把新的对象加入到map;
					 
					 }else{
						 
						 int j = currLevel - 2;
						 parentStruts = null;
						 while(j>=1){
							 parentStruts = (OffLineScoStruts)parentStrutsMap.get(j);
							 if(parentStruts != null){
								 break;
							 }
							 j -- ;
						 }
						 
						 parentStruts.getItems().add(offLineScoStruts);
						 
						 parentStrutsMap.put(j, parentStruts); //设置新值后重新放加map
						 parentStrutsMap.put(currLevel, offLineScoStruts);//把新的对象加入到map;
					 }
					 
					 previousLevel = currLevel;
					 
				 }else if (currLevel < previousLevel){
					 
					 int level = currLevel - 1;
					 parentStruts = (OffLineScoStruts)parentStrutsMap.get(level);
					 if(level==0){
						 //把parent移动list;
						 scoList.add((OffLineScoStruts)parentStrutsMap.get(currLevel));
						 //重新设置map 的parent;
						 parentStrutsMap.put(currLevel, offLineScoStruts);
						 continue;
					 }
					 parentStruts.getItems().add(offLineScoStruts);
					 
					 parentStrutsMap.put(level, parentStruts); //设置新值后重新放加map
					 parentStrutsMap.put(currLevel, offLineScoStruts);//把新的对象加入到map;
					 
					 previousLevel = currLevel;
				 }
				 
			 }
			 scoList.add((OffLineScoStruts)parentStrutsMap.get(1)); 
			// parentStruts = (OffLineScoStruts)parentStrutsMap.get(1);//获得水平为1的项；
			 //构造一层虚拟目录;
			 OffLineScoStruts struts = new OffLineScoStruts();
			 struts.setItems(scoList);
			 this.getOfflineData().add(struts);   
			 
		 }
	}
	
	
//	在线与离线scodata的转换;
	public OffLineScoStruts getDataFromScoData(SCODataManager scoData,String studentID,String courseID,ScormItem item)throws ScormException{
		
		OffLineScoStruts offLineScoStruts = new OffLineScoStruts();
		
		offLineScoStruts.setSid(item.getId());
		offLineScoStruts.setRid(""); //目录由于平台数据库中没有此字段,因此现在设置为"";
		offLineScoStruts.setCourseID(courseID);
		offLineScoStruts.setStudentID(studentID);
		offLineScoStruts.setTitle(checkText(item.getTitle()));
		offLineScoStruts.setHref(item.getLaunch());
		offLineScoStruts.setRTData(getOffLineScoDataFormPlatform(scoData));
		
		return offLineScoStruts;
		
	}
	
	/**
	 * 添加一个check 的方法，用于检查，title中是否有特殊字符；
	 * @param scoData
	 * @return
	 */
	private String checkText(String text){
		text = text.replace("\\", "\\\\");
		text = text.replace("\"", "\\\"");
		text = text.replace("<", "&lt;");
		text = text.replace(">", "&lg;");
		return text; 
	}
	
//	从SCODataManager 得到离线 OffLineScoData
	public OffLineScoData getOffLineScoDataFormPlatform(SCODataManager scoData){
		
		OffLineScoData offLineScoData = new OffLineScoData();
		
		try {
			offLineScoData.setCore(this.getOffLineCore(scoData.getCore()));
			offLineScoData.setSuspend_data(scoData.getSuspendData().getSuspendData().getValue());
			offLineScoData.setLaunch_data(scoData.getLaunchData().getLaunchData().getValue());
			offLineScoData.setComments(scoData.getComments().getComments().getValue());
		} catch (ScormException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return offLineScoData;
		
	}

	public List<OffLineScoStruts> getOfflineData() {
		return offlineData; 
	}

	public void setOfflineData(List<OffLineScoStruts> offlineData) {
		this.offlineData = offlineData;
	}


}
